/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are subject to the Mozilla Public License Version
 1.1 (the "License"); you may not use this file except in compliance with
 the License. You may obtain a copy of the License at
 http://www.mozilla.org/MPL/MPL-1.1.html
 
 Software distributed under the License is distributed on an "AS IS" basis,
 WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 for the specific language governing rights and limitations under the License.
 
 The Original Code is the "SensorML AbstractProcessing Engine".
 
 The Initial Developer of the Original Code is the VAST team at the University of Alabama in Huntsville (UAH). <http://vast.uah.edu> Portions created by the Initial Developer are Copyright (C) 2007 the Initial Developer. All Rights Reserved. Please Contact Mike Botts <mike.botts@uah.edu> for more information.
 
 Contributor(s): 
    Alexandre Robin <robin@nsstc.uah.edu>
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.sensorML.test;

import org.vast.process.*;
import org.vast.swe.SWEHelper;
import net.opengis.swe.v20.DataRecord;
import net.opengis.swe.v20.Quantity;


/**
 * <p>
 * Computes wind chill temperature in degrees Farenheit
 * using ambiant temperature in degrees Farenheit and
 * wind speed in miles per hour, using NWS formula:
 * Tc = 35.74 + 0.6215*T - 35.75*V^0.16 + 0.4275*T*V^0.16
 * </p>
 *
 * @author Alexandre Robin
 * @date Jan 20, 2006
 */
public class WindChill2_Process_WithPorts extends ExecutableProcessImpl
{
    public static final ProcessInfo INFO = new ProcessInfo("urn:test:windchill", "Windchill", null, WindChill2_Process_WithPorts.class);
    Quantity inputTemp;
    Quantity inputWindSpeed;
    Quantity outputTemp;


    public WindChill2_Process_WithPorts()
    {
        super(INFO);
        SWEHelper fac = new SWEHelper();
        
        // inputs
        DataRecord weatherRec = fac.newDataRecord();
        inputTemp = fac.newQuantity("http://sweet.jpl.nasa.gov/2.2/quanTemperature.owl#Temperature", "Air Temperature", null, "Cel");
        weatherRec.addField("temperature", inputTemp);
        inputWindSpeed = fac.newQuantity("http://sweet.jpl.nasa.gov/2.2/quanSpeed.owl#WindSpeed", "Wind Speed", null, "m/s");
        weatherRec.addField("windSpeed", inputWindSpeed);
        inputData.add("weather_inputs", weatherRec);
        
        // output
        outputTemp = fac.newQuantity("http://sweet.jpl.nasa.gov/2.2/quanTemperature.owl#WindChill", "Windchill Temperature", null, "Cel");
    }


    @Override
    public void execute() throws ProcessException
    {
        double T = inputTemp.getData().getDoubleValue();
        double V = inputWindSpeed.getData().getDoubleValue();

        double V16 = Math.pow(V, 0.16);
        double Tc = 35.74 + 0.6215 * T - 35.75 * V16 + 0.4275 * T * V16;

        outputTemp.getData().setDoubleValue(Tc);
    }
}
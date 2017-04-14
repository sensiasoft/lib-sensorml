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

import org.vast.data.*;
import org.vast.process.*;
import org.vast.sensorML.ExecutableProcessImpl;


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
public class WindChill_Process extends ExecutableProcessImpl
{
    DataValue inputTemp;
    DataValue inputWindSpeed;
    DataValue outputTemp;


    public WindChill_Process()
    {
    }


    /**
     * Initializes the process
     * Gets handles to input/output components
     */
    @Override
    public void init() throws SMLException
    {
        try
        {
            // I/O mappings
            inputTemp = (DataValue) inputData.getComponent("weather_inputs").getComponent("temperature");
            inputWindSpeed = (DataValue) inputData.getComponent("weather_inputs").getComponent("windSpeed");
            outputTemp = (DataValue) outputData.getComponent("windChill");
        }
        catch (Exception e)
        {
            throw new SMLException(IO_ERROR_MSG, e);
        }
    }


    /**
     * Executes process algorithm on inputs and set output data
     */
    @Override
    public void execute() throws SMLException
    {
        double T = inputTemp.getData().getDoubleValue();
        double V = inputWindSpeed.getData().getDoubleValue();

        double V16 = Math.pow(V, 0.16);
        double Tc = 35.74 + 0.6215 * T - 35.75 * V16 + 0.4275 * T * V16;

        outputTemp.getData().setDoubleValue(Tc);
    }
}
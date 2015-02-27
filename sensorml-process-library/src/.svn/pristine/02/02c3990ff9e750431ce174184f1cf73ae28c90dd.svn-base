/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are subject to the Mozilla Public License Version
 1.1 (the "License"); you may not use this file except in compliance with
 the License. You may obtain a copy of the License at
 http://www.mozilla.org/MPL/MPL-1.1.html
 
 Software distributed under the License is distributed on an "AS IS" basis,
 WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 for the specific language governing rights and limitations under the License.
 
 The Original Code is the "SensorML DataProcessing Engine".
 
 The Initial Developer of the Original Code is the VAST team at the University of Alabama in Huntsville (UAH). <http://vast.uah.edu> Portions created by the Initial Developer are Copyright (C) 2007 the Initial Developer. All Rights Reserved. Please Contact Mike Botts <mike.botts@uah.edu> for more information.
 
 Contributor(s): 
    Alexandre Robin <robin@nsstc.uah.edu>
 
******************************* END LICENSE BLOCK ***************************/

package org.sensorML.process;

import org.vast.data.*;
import org.vast.process.*;


/**
 * <p><b>Title:</b><br/>
 * DewPoint_Process
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Computes dew point temperature in degrees Celsius
 * using ambiant temperature in degrees Celsius and
 * relative humidity, using the following formulae:
 *      Td = (b*alpha)/(a-alpha)
 * with alpha = (a*T)/(b+T) + ln(RH), a = 17.27, b = 237.7
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Alexandre Robin
 * @date Jan 20, 2006
 * @version 1.0
 */
public class DewPoint_Process extends DataProcess
{
    DataValue inputTemp;
    DataValue inputRelHumidity;
    DataValue outputTemp;


    public DewPoint_Process()
    {
    }


    /**
     * Initializes the process
     * Gets handles to input/output components
     */
    public void init() throws ProcessException
    {
        try
        {
            // I/O mappings
            inputTemp = (DataValue) inputData.getComponent("ambiant_temperature");
            inputRelHumidity = (DataValue) inputData.getComponent("relative_humidity");
            outputTemp = (DataValue) outputData.getComponent("dewpoint_temperature");
        }
        catch (Exception e)
        {
            throw new ProcessException(ioError, e);
        }
    }


    /**
     * Executes process algorithm on inputs and set output data
     */
    public void execute() throws ProcessException
    {
        double T = inputTemp.getData().getDoubleValue();
        double RH = inputRelHumidity.getData().getDoubleValue();
        double a = 17.27;
        double b = 237.7;
        
        double alpha = (a*T)/(b+T) + Math.log(RH);
        double Td = (b*alpha)/(a-alpha);

        outputTemp.getData().setDoubleValue(Td);
    }
}
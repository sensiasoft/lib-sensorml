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

package org.sensorML.process;

import net.opengis.swe.v20.DataRecord;
import org.vast.process.*;
import org.vast.data.*;
import org.vast.physics.*;
import org.vast.sensorML.ExecutableProcessImpl;


/**
 * <p>
 * TODO ECEFToLLAProcess type description
 * </p>
 *
 * @author Alexandre Robin
 * @date Sep 2, 2005
 */
public class ECEFToLLA_Process extends ExecutableProcessImpl
{
    private DataValue xData, yData, zData;
    private DataValue latData, lonData, altData;
    private Datum datum;

    public ECEFToLLA_Process()
    {    	
    }

    
    @Override
    public void init() throws SMLProcessException
    {
    	try
        {
    		// input data containers
            DataRecord ecefData = (DataRecord)inputData.getComponent("ecefLocation");
            xData = (DataValue)ecefData.getComponent("x");
            yData = (DataValue)ecefData.getComponent("y");
            zData = (DataValue)ecefData.getComponent("z");
        	
    		// get output data containers + create appropriate Unit Converters
    		DataRecord locationData = (DataRecord)outputData.getComponent("geoLocation");
            latData = (DataValue)locationData.getComponent("latitude");            
            lonData = (DataValue)locationData.getComponent("longitude");
            altData = (DataValue)locationData.getComponent("altitude");
            
            datum = new Datum();
        }
        catch (Exception e)
        {
            throw new SMLProcessException(ioError, e);
        }
    }
   
    
    @Override
    public void execute() throws SMLProcessException
    {
    	double x = xData.getData().getDoubleValue();
        double y = yData.getData().getDoubleValue();
        double z = zData.getData().getDoubleValue();
    	
        double[] lla = MapProjection.ECFtoLLA(x, y, z, datum);
    	
        lonData.getData().setDoubleValue(lla[0]);
        latData.getData().setDoubleValue(lla[1]);        
        altData.getData().setDoubleValue(lla[2]);

    }
}

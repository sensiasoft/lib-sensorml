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

import org.vast.process.*;
import org.vast.data.*;
import org.vast.physics.*;


/**
 * <p><b>Title:</b><br/>
 * LLA To ECEF coordinate Transform Process
 * </p>
 *
 * <p><b>Description:</b><br/>
 * TODO LLAToECEFProcess type description
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Alexandre Robin & Gregoire Berthiau
 * @date Sep 2, 2005
 * @version 1.0
 */
public class LLAToECEF2_Process extends DataProcess
{
    private AbstractDataComponent latData, lonData, altData;
    private AbstractDataComponent xData, yData, zData;
    private Datum datum;


    public LLAToECEF2_Process()
    {
    }
    
    public void init() throws ProcessException
    {
    	try
        {
            // get input data containers + create appropriate Unit Converters
    		latData = (DataValue) inputData.getComponent("LLA_location").getComponent("latitude");
            lonData = (DataValue) inputData.getComponent("LLA_location").getComponent("longitude");
            altData = (DataValue) inputData.getComponent("LLA_location").getComponent("altitude");
            
            // get orientation data containers + create appropriate Unit Converters   
            xData = (DataValue) outputData.getComponent("ECEF_location").getComponent("x");
            yData = (DataValue) outputData.getComponent("ECEF_location").getComponent("y");
            zData = (DataValue) outputData.getComponent("ECEF_location").getComponent("z");
            
            datum = new Datum();
            
        }
        catch (Exception e)
        {
            throw new ProcessException(ioError, e);
        }
    }
    
    public void execute() throws ProcessException
    {
    	// get lat,lon,alt coordinates from input and convert to SI
    	double lat = latData.getData().getDoubleValue();
    	double lon = lonData.getData().getDoubleValue();
    	double alt = altData.getData().getDoubleValue();
        
        // convert to ECEF
        double[] ecefPos = MapProjection.LLAtoECF(lon, lat, alt, datum);
                
        xData.getData().setDoubleValue(ecefPos[0]);
		yData.getData().setDoubleValue(ecefPos[1]);
		zData.getData().setDoubleValue(ecefPos[2]);  

	}
}

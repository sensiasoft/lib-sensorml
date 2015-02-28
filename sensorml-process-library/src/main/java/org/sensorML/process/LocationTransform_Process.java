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

import net.opengis.swe.v20.DataArray;
import net.opengis.swe.v20.DataBlock;
import net.opengis.swe.v20.DataRecord;
import org.vast.data.*;
import org.vast.process.*;
import org.vast.sensorML.ExecutableProcessImpl;
import org.vast.math.*;


/**
 * <p>
 * Implementation of the Position Transformation Process
 * using homogeneous matrices
 * </p>
 *
 * @author Alexandre Robin
 * @date Sep 2, 2005
 */
public class LocationTransform_Process extends ExecutableProcessImpl
{
	private DataArray refPos;
    private DataValue inputX, inputY, inputZ;
    private DataValue outputX, outputY, outputZ;
    private Matrix4d refMatrix = new Matrix4d();
    private Vector3d loc = new Vector3d();
    
    
    public LocationTransform_Process()
    {    	
    }

    
    public void init() throws SMLProcessException
    {
    	try
        {
    	    // input data containers
            refPos = (DataArray) inputData.getComponent("referencePosition");
            DataRecord ecefData = (DataRecord)inputData.getComponent("location");
            inputX = (DataValue)ecefData.getComponent("x");
            inputY = (DataValue)ecefData.getComponent("y");
            inputZ = (DataValue)ecefData.getComponent("z");
            
            // get output data containers + create appropriate Unit Converters
            DataRecord locationData = (DataRecord)outputData.getComponent("location");
            outputX = (DataValue)locationData.getComponent("x");            
            outputY = (DataValue)locationData.getComponent("y");
            outputZ = (DataValue)locationData.getComponent("z");
        }
        catch (Exception e)
        {
            throw new SMLProcessException(ioError, e);
        }
    }
    

    public void execute() throws SMLProcessException
    {
        DataBlock refMatrixData = refPos.getData();
    	
    	// set ref matrix elements
        for (int i=0; i<16; i++)
        	refMatrix.setElement(i/4, i%4, refMatrixData.getDoubleValue(i));
        
        // set location vector coordinates
        loc.x = inputX.getData().getDoubleValue();
        loc.y = inputY.getData().getDoubleValue();
        loc.z = inputZ.getData().getDoubleValue();
        
        // transform vector
        loc.transform(refMatrix);
        
        // assign output values
        outputX.getData().setDoubleValue(loc.x);
        outputY.getData().setDoubleValue(loc.y);
        outputZ.getData().setDoubleValue(loc.z);
        
        //System.out.println("loc: " + loc.x + "," + loc.y + "," + loc.z);
    } 
}
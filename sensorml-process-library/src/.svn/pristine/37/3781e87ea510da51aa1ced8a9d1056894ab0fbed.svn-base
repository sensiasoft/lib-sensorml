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
    Gregoire Berthiau <berthiau@nsstc.uah.edu>
 
******************************* END LICENSE BLOCK ***************************/

package org.sensorML.process;

import org.vast.cdm.common.DataBlock;
import org.vast.data.*;
import org.vast.process.*;
import org.vast.math.*;


/**
 * <p><b>Title:</b><br/>
 * Vector by Matrix Multiplication Process
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Multiplication of a 3D vector by a 3D matrix
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Alexandre Robin & Gregoire Berthiau
 * @date Mar 7, 2007
 * @version 1.0
 */
public class VectorMatrixMultiplication_Process extends DataProcess
{
	AbstractDataComponent localMat;
    private DataValue vxData, vyData, vzData;
    private DataValue nvxData, nvyData, nvzData;

    
    public VectorMatrixMultiplication_Process()
    {
    	
    }

    
    public void init() throws ProcessException
    {
    	try
        {
            //I/O mappings
            vxData = (DataValue) inputData.getComponent("vector").getComponent("x");
            vyData = (DataValue) inputData.getComponent("vector").getComponent("y");
            vzData = (DataValue) inputData.getComponent("vector").getComponent("z");
         
            nvxData = (DataValue) outputData.getComponent("newVector").getComponent("x");
            nvyData = (DataValue) outputData.getComponent("newVector").getComponent("y");
            nvzData = (DataValue) outputData.getComponent("newVector").getComponent("z");
            
            localMat = inputData.getComponent("transformMatrix");
        }
        catch (Exception e)
        {
            throw new ProcessException(ioError, e);
        }
    }
    

    public void execute() throws ProcessException
    {
        double vx = 0.0;
        double vy = 0.0;
        double vz = 0.0;
        
        if (vxData != null)
            vx = vxData.getData().getDoubleValue();

        if (vyData != null)
            vy = vyData.getData().getDoubleValue();

        if (vzData != null)
            vz = vzData.getData().getDoubleValue();

    	Vector3d vector = new Vector3d(vx, vy, vz);
        DataBlock locMatrixData = localMat.getData();

        Matrix3d locMatrix = new Matrix3d();
        for (int i=0; i<9; i++)
        	locMatrix.setElement(i/3, i%3, locMatrixData.getDoubleValue(i));

        locMatrix.transform(vector);

        nvxData.getData().setDoubleValue(vector.x);
		nvyData.getData().setDoubleValue(vector.y);
		nvzData.getData().setDoubleValue(vector.z); 
		
    } 
}
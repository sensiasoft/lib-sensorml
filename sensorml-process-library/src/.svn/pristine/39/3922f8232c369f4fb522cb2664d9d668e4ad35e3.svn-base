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

import org.vast.cdm.common.DataBlock;
import org.vast.data.*;
import org.vast.process.*;
import org.vast.math.*;


/**
 * <p><b>Title:</b><br/>
 * Position Transform Process
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Implementation of the Position Transformation Process
 * using homogeneous matrices
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Alexandre Robin
 * @date Sep 2, 2005
 * @version 1.0
 */
public class PositionTransform_Process extends DataProcess
{
	AbstractDataComponent refPos, localPos, resultPos;
    
    
    public PositionTransform_Process()
    {
    	
    }

    
    public void init() throws ProcessException
    {   	
    	try
        {
            refPos = inputData.getComponent("referencePosition");
            localPos = inputData.getComponent("localPosition");
            
            resultPos = outputData.getComponent("position");
            resultPos.assignNewDataBlock();
        }
        catch (Exception e)
        {
            throw new ProcessException(ioError, e);
        }
    }
    

    public void execute() throws ProcessException
    {
        DataBlock refMatrixData = refPos.getData();
        DataBlock locMatrixData = localPos.getData();
    	
    	// compute transformed position
        Matrix4d refMatrix = new Matrix4d();
        for (int i=0; i<16; i++)
        	refMatrix.setElement(i/4, i%4, refMatrixData.getDoubleValue(i));
        
        Matrix4d locMatrix = new Matrix4d();
        for (int i=0; i<16; i++)
        	locMatrix.setElement(i/4, i%4, locMatrixData.getDoubleValue(i));
             
        refMatrix.mul(locMatrix);
        
        // assign output values
        DataBlock resultMatrixData = resultPos.getData();
        for (int i=0; i<16; i++)
        	resultMatrixData.setDoubleValue(i, refMatrix.getElement(i/4, i%4));
        
        //System.out.println(name + ":\n" + locMatrix);
    } 
}
/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are subject to the Mozilla Public License Version
 1.1 (the "License"); you may not use this file except in compliance with
 the License. You may obtain a copy of the License at
 http://www.mozilla.org/MPL/MPL-1.1.html
 
 Software distributed under the License is distributed on an "AS IS" basis,
 WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 for the specific language governing rights and limitations under the License.
 
 The Original Code is the "SensorML AbstractProcessing Engine".
 
 The Initial Developer of the Original Code is the VAST team at the
 
 Contributor(s): 
    Alexandre Robin <robin@nsstc.uah.edu>
 
******************************* END LICENSE BLOCK ***************************/

package org.sensorML.process;

import net.opengis.swe.v20.DataBlock;
import net.opengis.swe.v20.DataComponent;
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
public class PositionTransform_Process extends ExecutableProcessImpl
{
	DataComponent refPos, localPos, resultPos;
    
    
    public PositionTransform_Process()
    {
    	
    }

    
    public void init() throws SMLProcessException
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
            throw new SMLProcessException(ioError, e);
        }
    }
    

    public void execute() throws SMLProcessException
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
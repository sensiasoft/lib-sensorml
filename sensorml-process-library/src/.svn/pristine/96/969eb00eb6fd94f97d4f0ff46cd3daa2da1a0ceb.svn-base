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
import org.vast.math.*;
import org.vast.process.*;


/**
 * <p><b>Title:</b><br/>
 * 3D Matrix Construction process
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Construct a 3D matrix from the rotation angle about the 3 axis in the 
 * given order
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Alexandre Robin & Gregoire Berthiau
 * @date Mar 7, 2007
 * @version 1.0
 */
public class Pos2Matrix3_Process extends DataProcess
{
    private DataValue rxData, ryData, rzData;
    private DataArray outputMatrix;
    private char[] rotationOrder = {'X','Y','Z'};
    private Matrix3d newMatrix = new Matrix3d();
    
    
    public Pos2Matrix3_Process()
    {    	
    }
   
    @Override
    public void init() throws ProcessException
    {
        try
        {
            // input mappings
        
            DataGroup rotVector = (DataGroup)inputData.getComponent("orientation");
            rxData = (DataValue)rotVector.getComponent("x");
            ryData = (DataValue)rotVector.getComponent("y");
            rzData = (DataValue)rotVector.getComponent("z");
            
            // output mapping
            outputMatrix = (DataArray)outputData.getComponent("posMatrix");
            
            // figure out rotation order by order of orientation inputs
            int rxIndex = rotVector.getComponentIndex("x");
            rotationOrder[rxIndex] = 'X';
            int ryIndex = rotVector.getComponentIndex("y");
            rotationOrder[ryIndex] = 'Y';
            int rzIndex = rotVector.getComponentIndex("z");
            rotationOrder[rzIndex] = 'Z';
        }
        catch (RuntimeException e)
        {
            e.printStackTrace();
        }
    }
    
    @Override
    public void execute() throws ProcessException
    {
        double rx = 0.0;
        double ry = 0.0;
        double rz = 0.0;
        
        if (rxData != null)
           rx = rxData.getData().getDoubleValue();
  
        if (ryData != null)
            ry = ryData.getData().getDoubleValue();
  
        if (rzData != null)
            rz = rzData.getData().getDoubleValue();
  
        // set up rotation matrices
        newMatrix.setIdentity();
 
        // rotate in reverse order as the one given
        // because we want the opposite matrix
        for (int i=0; i<3; i++)
        {
            char axis = rotationOrder[2-i];
            
            switch (axis)
            {
                case 'X':
                    newMatrix.rotateX(-rx);
                    break;
                    
                case 'Y':
                    newMatrix.rotateY(-ry);
                    break;
                    
                case 'Z':
                    newMatrix.rotateZ(-rz);
                    break;
            }
        }
        
        // assign values to output matrix
        DataBlock data = outputMatrix.getData();
        for (int i=0; i<9; i++)
            data.setDoubleValue(i, newMatrix.getElement(i/3, i%3));
    }
}
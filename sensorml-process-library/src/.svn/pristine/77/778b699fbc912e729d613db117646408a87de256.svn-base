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
import org.vast.math.*;
import org.vast.process.*;


/**
 * <p><b>Title:</b><br/>
 * Generic Positioning Process
 * </p>
 *
 * <p><b>Description:</b><br/>
 * 
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Alexandre Robin
 * @date Sep 2, 2005
 * @version 1.0
 */
public class Pos2Matrix4_Process extends DataProcess
{
    private DataValue txData, tyData, tzData;
    private DataValue rxData, ryData, rzData;
    private DataArray outputMatrix;
    private char[] rotationOrder = {'X','Y','Z'};
    private Matrix4d newMatrix = new Matrix4d();
    
    
    public Pos2Matrix4_Process()
    {    	
    }

    
    @Override
    public void init() throws ProcessException
    {
        try
        {
            // input mappings
            DataGroup locVector = (DataGroup)inputData.getComponent("location");
            txData = (DataValue)locVector.getComponent("x");
            tyData = (DataValue)locVector.getComponent("y");
            tzData = (DataValue)locVector.getComponent("z");
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
        catch (Exception e)
        {
            throw new ProcessException(ioError, e);
        }
    }
    

    @Override
    public void execute() throws ProcessException
    {
        double tx = 0.0;
        double ty = 0.0;
        double tz = 0.0;
        double rx = 0.0;
        double ry = 0.0;
        double rz = 0.0;
        
        if (txData != null)
            tx = txData.getData().getDoubleValue();

        if (tyData != null)
            ty = tyData.getData().getDoubleValue();

        if (tzData != null)
            tz = tzData.getData().getDoubleValue();

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

        // translate
        newMatrix.setTranslation(tx, ty, tz);
        
        // assign values to output matrix
        DataBlock data = outputMatrix.getData();
        for (int i=0; i<16; i++)
            data.setDoubleValue(i, newMatrix.getElement(i/4, i%4));
    }
}
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

import org.vast.cdm.common.DataComponent;
import org.vast.data.*;
import org.vast.process.*;

public class GridBuilder_Process extends DataProcess
{
	
    private DataGroup groundPoint;
    private DataComponent outputGrid;
    private int elementCount, firstDimension, secondDimension;
    
    
    public GridBuilder_Process()
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
        	groundPoint = (DataGroup) inputData.getComponent("groundPoint");
        	outputGrid = outputData.getComponent("grid");
        	firstDimension = outputGrid.getComponentCount();
        	secondDimension = outputGrid.getComponent(0).getComponentCount();
        	elementCount = 0;
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

        groundPoint.getData();
        
        int i = elementCount/firstDimension;
        int j = elementCount%secondDimension;
        double x, y, z;
        
        x = groundPoint.getComponent(0).getData().getDoubleValue();
        y = groundPoint.getComponent(1).getData().getDoubleValue();
        z = groundPoint.getComponent(2).getData().getDoubleValue();
        
        //System.out.println("x: "+ x + "   y: " + y + "   z: " + z);
        
        outputGrid.getComponent(i).getComponent(j).getComponent(0).getData().setDoubleValue(x);
        outputGrid.getComponent(i).getComponent(j).getComponent(1).getData().setDoubleValue(y);
        outputGrid.getComponent(i).getComponent(j).getComponent(2).getData().setDoubleValue(z);
 
        elementCount++;
    	nextInputNeeded();
    	
    	if(elementCount==(firstDimension*secondDimension)){
    		nextOutput();
    		elementCount = 0;
    	}
    	
     } 	
    	
    protected void nextInputNeeded()
    {
    	inputConnections.get(0).setNeeded(true);
        outputConnections.get(0).setNeeded(false);
    }
    
    
    protected void nextOutput()
    {
        inputConnections.get(0).setNeeded(true);
        outputConnections.get(0).setNeeded(true);
    }
}
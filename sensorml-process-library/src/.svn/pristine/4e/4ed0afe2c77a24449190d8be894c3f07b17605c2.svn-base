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
import org.vast.cdm.common.DataComponent;
import org.vast.data.*;
import org.vast.process.*;


public class ArrayBuilder_Process extends DataProcess
{
    private DataComponent inputComponent;
    private DataArray outputArray;
    private DataValue inputSize, inputIndex, outputSize;
    private boolean needInit;
    private int elementCount, arraySize, sizeInputIndex;
    
    
    public ArrayBuilder_Process()
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
            sizeInputIndex = inputData.getComponentIndex("arraySize");
            inputSize = (DataValue) inputData.getComponent(sizeInputIndex);
            
            DataGroup arrayComponent = (DataGroup) inputData.getComponent("arrayComponent");
            inputIndex = (DataValue) arrayComponent.getComponent("index");
            inputComponent = arrayComponent.getComponent("data");

            DataGroup outputGroup = (DataGroup) outputData.getComponent("arrayData");
            outputSize = (DataValue) outputGroup.getComponent(0);
            outputArray = (DataArray) outputGroup.getComponent(1);
            
            needInit = true;
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
        // check if size input has changed
        // if so we have to start a new array
        if (needInit)
        {
            arraySize = inputSize.getData().getIntValue();
            outputSize.getData().setIntValue(arraySize);
            outputArray.updateSize();
            ((DataGroup)outputData.getComponent(0)).combineDataBlocks();            
            
            // set size input as not needed so that we can continue
            // building the array
            inputConnections.get(sizeInputIndex).setNeeded(false);
            outputConnections.get(0).setNeeded(false);
            
            elementCount = 0;
            needInit = false;
        }
        
        int index = inputIndex.getData().getIntValue();
        DataBlock data = inputComponent.getData();
        //System.out.print(elementCount + ",");
        for (int i=0; i<data.getAtomCount(); i++)
        {
            outputArray.getComponent(index).getData().setDoubleValue(i, data.getDoubleValue(i));
            //System.out.print(data.getDoubleValue(i) + ",");
        }
        //System.out.println();
        elementCount++;
        
        
        if (elementCount >= arraySize)
        {
            needInit = true;
            inputConnections.get(sizeInputIndex).setNeeded(true);
            outputConnections.get(0).setNeeded(true);
            this.setAvailability(inputConnections, false);
        }
    }
}
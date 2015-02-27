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

/**
 * <p><b>Title:</b><br/>
 * Counter_Process
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Implements a for loop style counter.
 * Variable is incremented from start value to stop value using
 * the provided step value. The pass-through data is duplicated
 * at every iteration. 
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Alexandre Robin
 * @date Jan 20, 2006
 * @version 1.0
 */
public class Counter_Process extends DataProcess
{
    protected DataComponent inputPassThrough, outputPassThrough;
    protected DataValue inputStart, inputStop, inputStep, inputStepCount;
    protected DataValue outputIndex, outputVariable, outputStepCount;
    protected int outputStepCountIndex;
    protected int count, stepCount;
    protected double start, stop, step, var;
    protected boolean useStepCount, done;
    
    
    public Counter_Process()
    {
    }


    /**
     * Initializes the process
     * Gets handles to input/output components
     */
    public void init() throws ProcessException
    {
        done = true;
        needSync = true;
        useStepCount = false;
        
        try
        {
            inputPassThrough = inputData.getComponent("pass-through");
            DataGroup group = (DataGroup) inputData.getComponent("countingRange");
            inputStart = (DataValue) group.getComponent("start");
            inputStop = (DataValue) group.getComponent("stop");
            inputStep = (DataValue) group.getComponent("stepSize");
            if (inputStep == null)
            {
                inputStepCount = (DataValue) group.getComponent("stepCount");
                useStepCount = true;
            }

            outputPassThrough = outputData.getComponent("pass-through");
            DataGroup countOutput = (DataGroup) outputData.getComponent("count");
            outputIndex = (DataValue) countOutput.getComponent("index");
            outputVariable = (DataValue) countOutput.getComponent("variable");
            
            outputStepCountIndex = outputData.getComponentIndex("stepCount");
            outputStepCount = (DataValue) outputData.getComponent(outputStepCountIndex);
            
            setNeededSignals(true);
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
        // get input variables only if previous loop is done
        if (done)
        {
            start = inputStart.getData().getDoubleValue();
            stop = inputStop.getData().getDoubleValue();
            if (useStepCount)
            {
                stepCount = inputStepCount.getData().getIntValue();
                step = (stop - start) / (stepCount - 1);
                outputStepCount.getData().setIntValue((int)stepCount);
            }
            else
            {
                step = inputStep.getData().getDoubleValue();
                stepCount = (int) ((stop - start) / step);
                outputStepCount.getData().setIntValue((int)stepCount);
            }
            
            //System.out.println(step + " - " + stepCount);
            
            // forward pass-through data
            if (inputPassThrough != null && outputPassThrough != null)
            {
                ((DataGroup)inputPassThrough).combineDataBlocks();
                outputPassThrough.setData(inputPassThrough.getData());//.clone()??);
            }
            
            // set some inputs/outputs as not needed so that we can continue looping
            setNeededSignals(false);
            this.setAvailability(outputConnections.get(outputStepCountIndex), true);
            
            // output first value
            var = start;
            count = 0;
            done = false;
        }
        
        // set output variable
        outputVariable.getData().setDoubleValue(var);
        outputIndex.getData().setIntValue(count);
        //System.out.println(name + ": " + "count = " + count + ", var = " + var);
        
        // reset stuffs if end of loop
        count++;
        var += step;
        if (count == stepCount)
        {
            done = true;           
            setNeededSignals(true);
        }
    }
    
    
    protected void setNeededSignals(boolean needed)
    {
        // set all inputs needed flag to specified value
        for (int i=0; i<inputConnections.size(); i++)
            inputConnections.get(i).setNeeded(needed);
        
        // set stepCount output needed flag to specified value
        outputConnections.get(outputStepCountIndex).setNeeded(needed);
    }
}
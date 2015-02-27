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

import org.vast.data.*;
import org.vast.process.*;


/**
 * <p><b>Title:</b><br/>
 * ScalarCompare_Process
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Compare input to threshold.
 * If input < threshold, input is sent to 'less' output.
 * If input > threshold, input is sent to 'more' output.
 * If input = threshold, input is sent to 'equal' output.
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Alexandre Robin
 * @date Jan 20, 2006
 * @version 1.0
 */
public class ScalarCompare_Process extends DataProcess
{
    private DataValue inputValue,tresholdValue;
    private DataValue outputValueLess, outputValueMore, outputValueEqual;
    private int lessOutputIndex, moreOutputIndex, equalOutputIndex;  
    private boolean more = false;
    private boolean less = false;
    private double input;
    

    public ScalarCompare_Process()
    {
        this.needSync = true;
    }


    @Override
    public void init() throws ProcessException
    {
        try
        {
            // Input mappings
            inputValue = (DataValue) inputData.getComponent("input");
            tresholdValue = (DataValue) inputData.getComponent("treshold");
            
            // Output mappings
            lessOutputIndex = outputData.getComponentIndex("less");
            outputValueLess = (DataValue) outputData.getComponent(lessOutputIndex);
            moreOutputIndex = outputData.getComponentIndex("more");
            outputValueMore = (DataValue) outputData.getComponent(moreOutputIndex);
            equalOutputIndex = outputData.getComponentIndex("equal");
            outputValueEqual = (DataValue) outputData.getComponent(equalOutputIndex);            
        }
        catch (Exception e)
        {
            throw new ProcessException(ioError, e);
        }
    }
    
    
    @Override
    public boolean canRun()
    {
        double ref = tresholdValue.getData().getDoubleValue();
        this.input = inputValue.getData().getDoubleValue();      
        
        this.less = (input < ref);
        this.more = (input > ref);
        
        outputConnections.get(lessOutputIndex).setNeeded(less);
        outputConnections.get(equalOutputIndex).setNeeded(!(less || more));
        outputConnections.get(moreOutputIndex).setNeeded(more);        
        
        // check that all needed outputs are available 
        return super.canRun();
    }


    @Override
    public void execute() throws ProcessException
    {
        if (less)
        {
            outputValueLess.getData().setDoubleValue(input);
        }
        else if (more)
        {
            outputValueMore.getData().setDoubleValue(input);
        }
        else //if (equal)
        {
            outputValueEqual.getData().setDoubleValue(input);
        }
    }
}
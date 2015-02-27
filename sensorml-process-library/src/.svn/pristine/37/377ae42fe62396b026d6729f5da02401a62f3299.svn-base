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

import java.util.*;
import org.vast.cdm.common.DataBlock;
import org.vast.data.*;
import org.vast.process.*;


/**
 * <p><b>Title:</b><br/>
 * Time Synchronization Process
 * </p>
 *
 * <p><b>Description:</b><br/>
 * This process synchronizes two asynchronous streams by interpolating
 * the slave data stream at times given by the master time stream
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Gregoire Berthiau
 * @date Nov 24, 2007
 * @version 1.0
 */
public class Atomizer_Process extends DataProcess
{
	int ArrayDataIndex, ValueDataIndex, ArrayElementCount, i=0;
	AbstractDataComponent ArrayData, ValueData;    
	double element;
	
    @Override
    public void init() throws ProcessException
    {
        try
        {
        	ArrayDataIndex = inputData.getComponentIndex("array");
        	ArrayData = inputData.getComponent(ArrayDataIndex);
        	ValueDataIndex = outputData.getComponentIndex("value");
        	ValueData = (DataValue)outputData.getComponent(ValueDataIndex);
            ArrayElementCount = ArrayData.getComponentCount();

        }
        catch (Exception e)
        {
            throw new ProcessException(ioError, e);
        }
    }
    
    @Override
    public void execute() throws ProcessException
    {
    	element = ArrayData.getData().getDoubleValue(i);
    	i++;
    	ValueData.getData().setDoubleValue(element);
    	
    	nextOutput();
    	
    	if(i==ArrayElementCount-1){
    		nextInputNeeded();
    		i = 0;
    	}
    	
     } 	
    	
    protected void nextInputNeeded()
    {
    	inputConnections.get(ArrayDataIndex).setNeeded(true);
        outputConnections.get(ValueDataIndex).setNeeded(false);
    }
    
    
    protected void nextOutput()
    {
        inputConnections.get(ArrayDataIndex).setNeeded(false);
        outputConnections.get(ValueDataIndex).setNeeded(true);
    }
}
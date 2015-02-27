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
import org.vast.cdm.common.DataComponent;
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
 * @author Alexandre Robin, Gregoire Berthiau
 * @date May 2, 2007
 * @version 1.0
 */
public class TimeSynchronizer_Process extends DataProcess
{
    protected enum Interpolation
	{
	    STEP,
	    LINEAR
	}
    
    protected int masterTimeIndex, slaveTimeIndex;
    protected int dataInSlaveIndex, dataOutSlaveIndex;
    protected DataValue masterTimeData, slaveTimeData;
    protected DataComponent dataInSlave, dataOutSlave;
    protected DataValue interpolationMethodData;
	
    protected boolean nextMasterTime, nextSlaveTime, outputReady;
    protected double masterTime;
    protected LinkedList<SlaveData> slaveDataStack = new LinkedList<SlaveData>();
    protected Interpolation interpolation;

    
	class SlaveData
	{
		public double time;
		public DataBlock data;
        
        public SlaveData(double time, DataBlock data)
        {
            this.time = time;
            this.data = data;
        }
	}

    
    @Override
    public void init() throws ProcessException
    {
        try
        {
            masterTimeIndex = inputData.getComponentIndex("masterTime");
            masterTimeData = (DataValue)inputData.getComponent(masterTimeIndex);
            slaveTimeIndex = inputData.getComponentIndex("slaveTime");
            slaveTimeData = (DataValue)inputData.getComponent(slaveTimeIndex);
            
            dataInSlaveIndex = inputData.getComponentIndex("dataIn");
            dataInSlave = inputData.getComponent(dataInSlaveIndex);
            dataOutSlaveIndex = outputData.getComponentIndex("dataOut");
        	dataOutSlave = outputData.getComponent(dataOutSlaveIndex);
        	
        	interpolationMethodData = (DataValue)paramData.getComponent("interpolationMethod");
        	if (interpolationMethodData != null)
        	{
        	    String method = interpolationMethodData.getData().getStringValue();
        	    if (method.equalsIgnoreCase("step"))
                    interpolation = Interpolation.STEP;
        	    else if (method.equalsIgnoreCase("linear"))
        	        interpolation = Interpolation.LINEAR;
        	}
        	else
        	    interpolation = Interpolation.STEP;
        	
        	// TODO check if dataIn and dataOut have the same structure
        }
        catch (Exception e)
        {
            throw new ProcessException(ioError, e);
        }
    }
    
    
    @Override
    public void reset()
    {
        bothTimeNeeded();
        outputReady = false;
        masterTime = Double.MAX_VALUE;
        slaveDataStack.clear();
    }
    

    @Override
    public void execute() throws ProcessException
    {    	   	
        if (interpolation == Interpolation.STEP)
    	{
            // keep getting slave time until slaveTime >= masterTime
            if (nextSlaveTime)
            {
                double slaveTime = slaveTimeData.getData().getDoubleValue();
                //System.out.println("Slave Time: " + slaveTime);
                DataBlock slaveBlock = dataInSlave.getData();
                SlaveData newData = new SlaveData(slaveTime, slaveBlock);
                slaveDataStack.add(newData);                
                
                // remove oldest item if stack size reached interp order
                if (slaveDataStack.size() > 2)
                    slaveDataStack.remove(0);
                           
                if (slaveTime >= masterTime)
                    lowerStep();
            }
    		
            // get next master time when needed
    		if (nextMasterTime)
            {
                masterTime = masterTimeData.getData().getDoubleValue();
                //System.out.println("Master Time: " + masterTime);
               
                if (!slaveDataStack.isEmpty() && slaveDataStack.getLast().time >= masterTime)
                    lowerStep();
                else
                    nextSlaveTimeNeeded();
            }
    	}	
    	
    	else if (interpolation == Interpolation.LINEAR)
    	{
    	    // keep getting slave time until slaveTime >= masterTime
            if (nextSlaveTime)
            {
                double slaveTime = slaveTimeData.getData().getDoubleValue();
                //System.out.println("Slave Time: " + slaveTime);
                DataBlock slaveBlock = dataInSlave.getData();
                SlaveData newData = new SlaveData(slaveTime, slaveBlock);
                slaveDataStack.add(newData);                
                
                // remove oldest item if stack size reached interp order
                if (slaveDataStack.size() > 2)
                    slaveDataStack.removeFirst();
                           
                if (slaveTime >= masterTime)
                    interpolateOrder1();
            }
            
            // get next master time when needed
            if (nextMasterTime)
            {
                masterTime = masterTimeData.getData().getDoubleValue();
                //System.out.println("Master Time: " + masterTime);
               
                if (!slaveDataStack.isEmpty() && slaveDataStack.getLast().time >= masterTime)
                    interpolateOrder1();
                else
                    nextSlaveTimeNeeded();
            }
    	}
    }
    
    
    @Override
    protected void setAvailability(List<ConnectionList> connections, boolean availability)
    {
        if (connections == outputConnections)
        {
            super.setAvailability(connections, outputReady);
            outputReady = false;
        }
        else
            super.setAvailability(connections, availability);
    }
    
    
    protected void lowerStep()
    {
        //System.out.println("Using data at time " + slaveDataStack.getFirst().time);
        dataOutSlave.setData(slaveDataStack.getFirst().data);            
        nextMasterTimeNeeded();
        outputReady = true;
    }
    
    
    protected void interpolateOrder1()
    {
        if (slaveDataStack.size() > 1)
        {
            double currentTime = slaveDataStack.get(1).time;
            DataBlock currentData = slaveDataStack.get(1).data;
            double previousTime = slaveDataStack.get(0).time;
            DataBlock previousData = slaveDataStack.get(0).data;
            
            // first order interpolation factor
            double a = (masterTime - previousTime) / (currentTime - previousTime);
            
            // interpolate all values
            int valueCount = dataInSlave.getData().getAtomCount();
            for (int i=0; i<valueCount; i++)
            {
                double currentValue = currentData.getDoubleValue(i);
                double previousValue = previousData.getDoubleValue(i);
                double outputValue = previousValue + a*(currentValue - previousValue);
                dataOutSlave.getData().setDoubleValue(i, outputValue);
            }
        }
        else
        {
            dataOutSlave.setData(slaveDataStack.getFirst().data);
        }
        
        nextMasterTimeNeeded();
        outputReady = true;
    }
    
    
    protected void bothTimeNeeded()
    {
        inputConnections.get(masterTimeIndex).setNeeded(true);
        inputConnections.get(slaveTimeIndex).setNeeded(true);
        inputConnections.get(dataInSlaveIndex).setNeeded(true);
        nextMasterTime = true;
        nextSlaveTime = true;
        //System.out.println("Waiting for next master time + slave time");
    }
    
    
    protected void nextMasterTimeNeeded()
    {
        inputConnections.get(masterTimeIndex).setNeeded(true);
        inputConnections.get(slaveTimeIndex).setNeeded(false);
        inputConnections.get(dataInSlaveIndex).setNeeded(false);
        nextMasterTime = true;
        nextSlaveTime = false;
        //System.out.println("Waiting for next master time");
    }
    
    
    protected void nextSlaveTimeNeeded()
    {
        inputConnections.get(masterTimeIndex).setNeeded(false);
        inputConnections.get(slaveTimeIndex).setNeeded(true);
        inputConnections.get(dataInSlaveIndex).setNeeded(true);
        nextMasterTime = false;
        nextSlaveTime = true;
        //System.out.println("Waiting for next slave time");
    }
}
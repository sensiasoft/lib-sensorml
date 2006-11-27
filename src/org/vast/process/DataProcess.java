/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are subject to the Mozilla Public License Version
 1.1 (the "License"); you may not use this file except in compliance with
 the License. You may obtain a copy of the License at
 http://www.mozilla.org/MPL/MPL-1.1.html
 
 Software distributed under the License is distributed on an "AS IS" basis,
 WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 for the specific language governing rights and limitations under the License.
 
 The Original Code is the "SensorML DataProcessing Engine".
 
 The Initial Developer of the Original Code is the
 University of Alabama in Huntsville (UAH).
 Portions created by the Initial Developer are Copyright (C) 2006
 the Initial Developer. All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin <robin@nsstc.uah.edu>
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.process;

import org.ogc.cdm.common.*;
import org.vast.data.*;
import org.vast.util.*;
import java.util.*;


/**
 * <p><b>Title:</b>
 * Data Process
 * </p>
 * 
 * <p><b>Description:</b><br/>
 * Abstract Implementation of the atomic ProcessModel
 * DataProcess can be run in a separate thread by calling start
 * or synchronously by calling execute or run. The execute() and
 * init() method need to be implemented by each sub class.
 * </p>
 * 
 * <p>Copyright (c) 2005</p>
 * @author Alexandre Robin
 * @date Aug 19, 2005
 * @version 1.0
 */
public abstract class DataProcess implements Runnable
{
    public static final String METADATA = "METADATA";
    protected static final String ioError = "Invalid I/O Structure";

    protected String name;
    protected String type;
    protected Hashtable<String, Object> properties = null;
    
    // internal process thread
    protected Thread processThread = null;

    // in/out/param data
    protected AbstractDataComponent inputData;
    protected AbstractDataComponent outputData;
    protected AbstractDataComponent paramData;

    // in/out/param connection lists (WARNING: these are lists of lists!)
    protected List<ConnectionList> inputConnections;
    protected List<ConnectionList> outputConnections;
    protected List<ConnectionList> paramConnections;
    
    // index of first needed signal
    protected int firstNeededInput = 0;
    protected int firstNeededOutput = 0;
    protected int firstNeededParam = 0;

    // boolean true when process thread is started
    protected boolean started = false;
    protected boolean usingQueueBuffers = false;
    protected boolean needSync = false;
        
    
    public DataProcess()
    {
    	this.inputData = new DataGroup(1);
    	this.outputData = new DataGroup(1);
    	this.paramData = new DataGroup(1);
        
        this.inputConnections = new ArrayList<ConnectionList>(0);
        this.outputConnections = new ArrayList<ConnectionList>(0);
        this.paramConnections = new ArrayList<ConnectionList>(0);
    }
    
    
    /**
     * Initialize the process and its internal variables (fixed parameters)
     * This is called only once before the process is executed.
     * @throws ProcessException
     */
    public abstract void init() throws ProcessException;
    
    
    /**
     * Execute is typically called several times on a process and should
     * contain all the logic to transform input/parameter values to 
     * output values. This method should be optimized as much as possible.
     * @throws ProcessException
     */
    public abstract void execute() throws ProcessException;
    
    
    /**
     * Override to dispose of all resources allocated
     * for the process (stop threads, OS resources, etc...)
     * Default method does nothing.
     */
    public void dispose()
    {        
    }
    
    
    /**
     * Check that all needed connections are ready for the process
     * to run in sync mode (not threaded).
     * @return true if so and false if at least one connection is not ready.
     */
    protected boolean canRun()
    {
        if (!checkAvailability(inputConnections, true))
            return false;
        
        if (!checkAvailability(paramConnections, true))
            return false;
        
        if (!checkAvailability(outputConnections, false))
            return false;
        
        return true;
    }
    
    
    /**
     * Checks if all input connections marked as needed
     * have the specified availability state.
     * @return true if all needed connections satisfy the condition, false otherwise
     */
    protected boolean checkAvailability(List<ConnectionList> allConnections, boolean availability)
    {
        // loop through all connection lists
        for (int i=0; i<allConnections.size(); i++)
        {
            ConnectionList connectionList = allConnections.get(i);
            
            if (connectionList.needed)
            {
                // loop through all connections in each list
                for (int j=0; j<connectionList.size(); j++)
                {
                    DataConnection connection = connectionList.get(j);                    
                    if (connection.dataAvailable != availability)
                        return false;
                }
            }
        }
        
        return true;
    }
    
    
    /**
     * Checks that the availability flags of all connections
     * in the list is equal to the specified state.
     * @param connectionList
     * @param availability
     * @return true if all connections satisfy the condition, false otherwise.
     */
    protected boolean checkAvailability(ConnectionList connectionList, boolean availability)
    {
        // loop through all connections in the list
        for (int j=0; j<connectionList.size(); j++)
        {
            DataConnection connection = connectionList.get(j);                    
            if (connection.dataAvailable != availability)
                return false;
        }
        
        return true;
    }
    
    
    /**
     * Sets i/o availability flags in sync mode (not threaded)
     * This default method just sets all needed flags to the
     * specified state. It must be overriden by processes needing
     * a different behavior.
     * @param allConnections (inputs, outputs or parameters)
     * @param flagState
     */
    protected void setAvailability(List<ConnectionList> allConnections, boolean availability)
    {
        // loop through all connection lists
        for (int i=0; i<allConnections.size(); i++)
        {
            ConnectionList connectionList = allConnections.get(i);
            
            if (connectionList.needed)
            {
                // loop through all connections in each list
                for (int j=0; j<connectionList.size(); j++)
                {
                    DataConnection connection = connectionList.get(j);
                    connection.dataAvailable = availability;
                }
            }
        }
    }
    
    
    /**
     * Sets the availability flags of all connections in the list
     * to the specified state. (even if not needed!)
     * @param connectionList
     * @param availability
     */
    protected void setAvailability(ConnectionList connectionList, boolean availability)
    {
        // loop through all connections in the list
        for (int j=0; j<connectionList.size(); j++)
        {
            DataConnection connection = connectionList.get(j);
            connection.dataAvailable = availability;
        }
    }
    
    
    /**
     * Fetch new data from input queues in async mode (threaded)
     * Thread will wait until each queue needing data receives next block
     */
    protected void fetchData(List<ConnectionList> allConnections) throws InterruptedException
    {
        // go through all connections and get next dataBlock from them
        for (int i=0; i<allConnections.size(); i++)
        {
            ConnectionList connectionList = allConnections.get(i);
            
            if (connectionList.needed)
            {
                // loop through all connections
                for (int j=0; j<connectionList.size(); j++)
                {
                    // get datablock from queue and assign it to destination component
                    DataQueue dataQueue = (DataQueue)connectionList.get(j);
                    DataComponent dataComponent = dataQueue.destinationComponent;
                    DataBlock block = dataQueue.get();
                    dataComponent.setData(block);
                }
            }
        }
    }
    
    
    /**
     * Writes new data to output queues in async mode (threaded)
     */
    protected void writeData(List<ConnectionList> allConnections) throws InterruptedException
    {
        for (int i=0; i<allConnections.size(); i++)
        {
            ConnectionList connectionList = allConnections.get(i);
            
            if (connectionList.needed)
            {
                // loop through all connections
                for (int j=0; j<connectionList.size(); j++)
                {
                    // get datablock from source component and add to queue
                    DataQueue dataQueue = (DataQueue)connectionList.get(j);
                    DataComponent dataComponent = dataQueue.sourceComponent;
                    DataBlock block = dataComponent.getData();
                    dataQueue.add(block);
                }
                
                // renew output dataBlock
                inputData.getComponent(i).renewDataBlock();
            }
        }
    }
    
    
    /**
     * Transfers input data in sync mode (not threaded)
     */
    protected void transferData(List<ConnectionList> allConnections)
    {
        // loop through all inputs
        for (int i=0; i<allConnections.size(); i++)
        {
            ConnectionList connectionList = allConnections.get(i);
            
            if (connectionList.needed)
            {
                // loop through all connections
                for (int j=0; j<connectionList.size(); j++)
                {
                    DataConnection connection = connectionList.get(j);
                    connection.transferDataBlocks();
                }
            }
        }
    }    
   
    
    /**
     * Creates new DataBlock for each output signal
     **/
    public void createNewOutputBlocks()
    {
    	int outputCount = this.outputData.getComponentCount();
        for (int i=0; i<outputCount; i++)
        {
            this.outputData.getComponent(i).renewDataBlock();
        }
    }
    
    
    /**
     * Creates new DataBlock for each input signal
     **/
    public void createNewInputBlocks()
    {
        int inputCount = this.inputData.getComponentCount();
        for (int i=0; i<inputCount; i++)
        {
            this.inputData.getComponent(i).renewDataBlock();
        }
    }
    
    
    /**
     * Process thread run method
     */
    public void run()
    {
        do
        {
            try
            {
            	// fetch inputs, execute process and write outputs
            	this.fetchData(inputConnections);
                this.fetchData(paramConnections);
                this.execute();
                this.writeData(outputConnections);                    
            }
            catch (ProcessException e)
            {
                ExceptionSystem.display(e);
            }
            catch (InterruptedException e)
            {
                started = false;
            }
        }
        while (started);       
    }


    /**
     * Start process thread
     * @throws ProcessException
     */
    public synchronized void start() throws ProcessException
    {
        if (!started)
        {
        	String processClass = getClass().getName();
            processClass = processClass.substring(processClass.lastIndexOf('.')+1);
            MessageSystem.display("Process " + this.name + " (" + processClass + ") Thread started", false);
        	
        	this.init();
        	started = true;
            usingQueueBuffers = true;
            processThread = new Thread(this);
            processThread.setName(this.name);
            processThread.start();
        }
    }


    /**
     * Stop process thread gracefully
     */
    public synchronized void stop()
    {
    	if (started)
    	{
    		String processClass = getClass().getName();
            processClass = processClass.substring(processClass.lastIndexOf('.')+1);
    		MessageSystem.display("Process " + this.name + " (" + processClass + ") Thread stopped", false);
    	    	
    		// set a stop flag and let the run method return cleanely
    		started = false;
        
	    	// make sure we exit the wait loop
	        if (processThread != null)
	            processThread.interrupt();
	        
	        processThread = null;
    	}
    }
    
    
    /**
     * Print process name and I/O info
     */
    public String toString()
    {
    	StringBuffer text = new StringBuffer();
    	String indent = "    ";
    	
        text.append("DataProcess: ");
        text.append(this.name);
        text.append(" (" + this.getClass().getName() + ")\n");
        
        text.append("\n  Inputs:\n");
        for (int i=0; i<inputData.getComponentCount(); i++)
        {
        	text.append(indent);
        	text.append(inputData.getComponent(i).toString(indent));
        }
        
        text.append("\n  Outputs:\n");
        for (int i=0; i<outputData.getComponentCount(); i++)
        {
        	text.append(indent);
        	text.append(outputData.getComponent(i).toString(indent));
        }
        
        text.append("\n  Parameters:\n");
        for (int i=0; i<paramData.getComponentCount(); i++)
        {
        	text.append(indent);
        	text.append(paramData.getComponent(i).toString(indent));
        }

        return text.toString();
    }


    //////////////////////////////////////
    // Get/Set methods for input/output //
    //////////////////////////////////////
	public String getName()
	{
		if (this.name == null)
            return this.getClass().getSimpleName();
        else
            return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
    
    
    public String getType()
    {
        if (this.type == null)
            return this.getClass().getSimpleName();
        else
            return type;
    }


    public void setType(String type)
    {
        this.type = type;
    }


	public void addInput(String name, DataComponent inputStructure)
	{
		this.inputData.addComponent(name, inputStructure);
        this.inputConnections.add(new ConnectionList());		
	}


	public void addOutput(String name, DataComponent outputStructure)
	{
		this.outputData.addComponent(name, outputStructure);
        this.outputConnections.add(new ConnectionList());		
	}


	public void addParameter(String name, DataComponent paramStructure)
	{
		this.paramData.addComponent(name, paramStructure);
        this.paramConnections.add(new ConnectionList());		
	}


	public void connectInput(String dataPath, DataConnection connection) throws ProcessException
	{
		IOSelector selector = new IOSelector(inputData, dataPath);
		connection.setDestinationComponent(selector.component);
		connection.setDestinationProcess(this);
        int inputIndex = selector.getComponentIndex();
		this.inputConnections.get(inputIndex).add(connection);
	}


	public void connectOutput(String dataPath, DataConnection connection) throws ProcessException
	{
		IOSelector selector = new IOSelector(outputData, dataPath);
		connection.setSourceComponent(selector.component);
		connection.setSourceProcess(this);
        int ouputIndex = selector.getComponentIndex();
		this.outputConnections.get(ouputIndex).add(connection);		
	}


	public void connectParameter(String dataPath, DataConnection connection) throws ProcessException
	{
		IOSelector selector = new IOSelector(paramData, dataPath);
		connection.setDestinationComponent(selector.component);
		connection.setDestinationProcess(this);
        int paramIndex = selector.getComponentIndex();
		this.paramConnections.get(paramIndex).add(connection);		
	}
    
    
    public boolean isInputConnected(String inputName)
    {
        int inputIndex = inputData.getComponentIndex(inputName);
        if (inputIndex < 0)
            return false;
        return !inputConnections.get(inputIndex).isEmpty();
    }


    public boolean isOutputConnected(String outputName)
    {
        int outputIndex = inputData.getComponentIndex(outputName);
        if (outputIndex < 0)
            return false;
        return !outputConnections.get(outputIndex).isEmpty();
    }


    public boolean isParamConnected(String paramName)
    {
        int paramIndex = inputData.getComponentIndex(paramName);
        if (paramIndex < 0)
            return false;
        return !paramConnections.get(paramIndex).isEmpty();
    }


	public List<ConnectionList> getInputConnections()
	{
		return this.inputConnections;
	}
	
	
	public List<ConnectionList> getOutputConnections()
	{
		return this.outputConnections;
	}
	
	
	public List<ConnectionList> getParamConnections()
	{
		return this.paramConnections;
	}


	public DataComponent getInputList()
	{
		return inputData;
	}


	public DataComponent getOutputList()
	{
		return outputData;
	}
	
	
	public DataComponent getParameterList()
	{
		return paramData;
	}


    public Object getProperty(String propName)
    {
        if (properties == null)
            return null;
        else
            return properties.get(propName);
    }


    public void setProperty(String propName, Object propValue)
    {
        if (properties == null)
            properties = new Hashtable<String, Object>(1, 1.0f);
        
        properties.put(propName, propValue);
    }


    public boolean isUsingQueueBuffers()
    {
        return usingQueueBuffers;
    }


    public void setUsingQueueBuffers(boolean usingQueueBuffers)
    {
        this.usingQueueBuffers = usingQueueBuffers;
    }


    public boolean needSync()
    {
        return needSync;
    }
}
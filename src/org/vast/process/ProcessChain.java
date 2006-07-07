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

import org.ogc.cdm.common.DataComponent;
import java.util.*;


/**
 * <p><b>Title:</b>
 * Process Chain
 * </p>
 *
 * <p><b>Description:</b><br/>
 * A ProcessChain contains one ore more child processes
 * interconnected by DataConnections. The ProcessChain contains
 * logic for sorting the processes and running them as needed.
 * </p>
 *
 * <p>Copyright (c) 2005</p>
 * @author Alexandre Robin
 * @date Aug 19, 2005
 * @version 1.0
 */
public class ProcessChain extends DataProcess
{
    protected Hashtable<String, DataProcess> processTable;
    protected List<DataProcess> processList;
    protected List<DataProcess> processExecList;
    protected boolean childrenThreadsOn = false;
    
    // internal in/out/param data queues: component name -> queue
    protected List<ConnectionList> internalInputConnections = null;
    protected List<ConnectionList> internalOutputConnections = null;
    protected List<ConnectionList> internalParamConnections = null;
    
    // list of all interconnections within the chain (helper)
    protected List<DataConnection> internalConnections = null;
    

    public ProcessChain()
    {
    	this(3);
    }
    
    
    public ProcessChain(int memberCount)
    {
        processTable = new Hashtable<String, DataProcess>(memberCount, 1.0f);
        processList = new ArrayList<DataProcess>(memberCount);
        processExecList = new ArrayList<DataProcess>(memberCount);
        
        this.internalInputConnections = new ArrayList<ConnectionList>();
        this.internalOutputConnections = new ArrayList<ConnectionList>();
        this.internalParamConnections = new ArrayList<ConnectionList>();
        this.internalConnections = new ArrayList<DataConnection>();
    }
    
    
    @Override
    public void init() throws ProcessException
    {        
        if (!childrenThreadsOn)
    	{
            // clear process execution list
	        processExecList.clear();
            
            // loop through all needed outputs (internally)
	        for (int i=0; i<internalOutputConnections.size(); i++)
	    	{
                ConnectionList connectionList = internalOutputConnections.get(i);
                
                if (connectionList.needed)
                {
                    // loop through all queues connected to this output
                    for (int j=0; j<connectionList.size(); j++)
                    {
                        DataProcess upStreamProcess = (DataProcess)connectionList.get(j).getSourceProcess();
                    
                        // order processes feeding this output
        	    		if (upStreamProcess != this)
        	    		{
        	    			processExecList.add(upStreamProcess);
                            addUpstreamProcesses(upStreamProcess, upStreamProcess.getInputConnections());
                            addUpstreamProcesses(upStreamProcess, upStreamProcess.getParameterConnections());
        	    		}
                    }
                }
	    	}
            
            // init needed processes
            for (int i=0; i<processExecList.size(); i++)
            {
                DataProcess process = processExecList.get(i);
                process.init();
                process.createNewOutputBlocks();
                
                if (process.needSync())
                    this.needSync = true;
            }
    	}
        else
        {
            // init all child processes and create output data blocks
            for (int i=0; i<processList.size(); i++)
            {
                DataProcess process = processList.get(i);
                process.init();
                process.createNewOutputBlocks();
            }
        }
    }
       
    
    private void addUpstreamProcesses(DataProcess process, List<ConnectionList> inputConnections)
    {
        // loop through all inputs
    	for (int i=0; i<inputConnections.size(); i++)
    	{
            List<DataConnection> connectionList = inputConnections.get(i);
            
            // loop through all queues connected to this input
            for (int j=0; j<connectionList.size(); j++)
            {
                DataProcess upStreamProcess = (DataProcess)connectionList.get(j).getSourceProcess();
        		
        		if (upStreamProcess != this)
        		{
        			if (!processExecList.contains(upStreamProcess))
    	    		{
    	    			processExecList.add(upStreamProcess);
    	    			addUpstreamProcesses(upStreamProcess, upStreamProcess.getInputConnections());
                        addUpstreamProcesses(upStreamProcess, upStreamProcess.getParameterConnections());
    	    		}
    	    		
    	    		ensureOrder(upStreamProcess, process);
        		}
            }
    	}   	
    }
    
    
    private void ensureOrder(DataProcess processBefore, DataProcess processAfter)
    {
    	int before = processExecList.indexOf(processBefore);
    	int after = processExecList.indexOf(processAfter);
    	
    	if (after < before)
    	{
    		processExecList.remove(processAfter);
    		processExecList.add(before, processAfter);
    	}
    }
    
    
    @Override
    public void execute() throws ProcessException
    {
    	try
		{
			// if child threads are off, run all child processes
			if (!childrenThreadsOn)
	    	{
                // execute all sub processes in order                
                if (processExecList != null)
                {
                    if (needSync)
                    {
                        boolean moreToRun;
                        
                        // loop until no more processes can run
                        do
                        {
                            moreToRun = false;                            
                            for (int i=0; i<processExecList.size(); i++)
        		    		{
        		    			DataProcess nextProcess = processExecList.get(i);
                                
                                // continue only if all input data is available
                                if (   nextProcess.transferInputData()
                                    && nextProcess.transferParamData())
                                {
                                    // run process only if all outputs are available
                                    if (nextProcess.canRun())
                                    {
                                        nextProcess.updateIOFlags();
                                        nextProcess.execute();
                                        moreToRun = true;
                                        //System.out.println("Running: " + nextProcess.getName());
                                    }
                                }
        		    		}
                        }
                        while (moreToRun);
                        
                        this.transferOutputData();
                    }
                    else
                    {
                        for (int i=0; i<processExecList.size(); i++)
                        {
                            DataProcess nextProcess = processExecList.get(i);                        
                            nextProcess.transferInputDataLight();
                            nextProcess.transferParamDataLight();
                            nextProcess.execute();
                        }
                        
                        this.transferOutputDataLight();
                    }
                    
                    
                }
                
                // determine what input are needed for next run
                
	    	}
            else
            {
                //super.writeOutputData(this.internalInputConnections);
                // TODO deal with params
                //super.fetchInputData(this.internalOutputConnections);
            }			
		}
		catch (InterruptedException e)
		{
		}
    }
    
    
    /**
     * Transfers data to chain output in sync mode (not threaded)
     * Also checks for data availability on each connection
     */
    protected void transferOutputData() throws InterruptedException
    {
        for (int i=0; i<internalOutputConnections.size(); i++)
        {
            ConnectionList connectionList = internalOutputConnections.get(i);
            
            if (connectionList.needed)
            {
                // loop through all connections
                for (int j=0; j<connectionList.size(); j++)
                {
                    // transfer datablock to destination component if needed
                    DataConnection connection = connectionList.get(j);
                    
                    if (!needSync || connection.dataAvailable == true)
                    {
                        connection.checkDataBlocks();
                        connection.dataAvailable = false;
                    }
                }
            }
        }
    }
    
    
    /**
     * Transfers data to chain output in sync mode (not threaded)
     * Should be used as a fast variant of the method above
     * when no check needs to be done (chains with no flow control)
     */
    protected void transferOutputDataLight() throws InterruptedException
    {
        for (int i=0; i<internalOutputConnections.size(); i++)
        {
            ConnectionList connectionList = internalOutputConnections.get(i);
            
            if (connectionList.needed)
            {
                // loop through all connections
                for (int j=0; j<connectionList.size(); j++)
                {
                    // transfer datablock to destination component if needed
                    DataConnection connection = connectionList.get(j);
                    connection.checkDataBlocks();
                    connection.dataAvailable = false;
                }
            }
        }
    }
    
    
    public void connectInternalInput(String dataPath, DataConnection connection) throws ProcessException
    {
    	IOSelector selector = new IOSelector(inputData, dataPath);
		connection.setSourceComponent(selector.component);
		connection.setSourceProcess(this);
        int inputIndex = selector.getComponentIndex();
		this.internalInputConnections.get(inputIndex).add(connection);
    }
    
    
    public void connectInternalOutput(String dataPath, DataConnection connection) throws ProcessException
    {
    	IOSelector selector = new IOSelector(outputData, dataPath);
		connection.setDestinationComponent(selector.component);
		connection.setDestinationProcess(this);
        int outputIndex = selector.getComponentIndex();
		this.internalOutputConnections.get(outputIndex).add(connection);
    }
    
    
    public void connectInternalParam(String dataPath, DataConnection connection) throws ProcessException
    {
    	IOSelector selector = new IOSelector(paramData, dataPath);
		connection.setSourceComponent(selector.component);
		connection.setSourceProcess(this);
        int paramIndex = selector.getComponentIndex();
		this.internalParamConnections.get(paramIndex).add(connection);
    }


    @Override
    public synchronized void start() throws ProcessException
    {
    	super.start();
    	
    	// start all child processes
    	if (childrenThreadsOn)
    	{
        	Enumeration childProcesses = processTable.elements();
            while (childProcesses.hasMoreElements())
            {
                DataProcess process = (DataProcess)childProcesses.nextElement();
                process.start();
            }
    	}
    }
    
    
    @Override
    public synchronized void stop()
    {
    	// stop all child processes
    	if (childrenThreadsOn)
    	{
	        Enumeration childProcesses = processTable.elements();
	        while (childProcesses.hasMoreElements())
	        {
	            DataProcess process = (DataProcess)childProcesses.nextElement();
	            process.stop();
	        }
    	}
    	
    	super.stop();
    }
    
    
    //////////////////////////////////////////////////////////////////
    //  Override addXXX methods to also create internal queue lists //
    //////////////////////////////////////////////////////////////////
    
    @Override
    public void addInput(String name, DataComponent inputStructure)
    {
        super.addInput(name, inputStructure);
        this.internalInputConnections.add(new ConnectionList());
    }
    
    
    @Override
    public void addOutput(String name, DataComponent outputStructure)
    {
        super.addOutput(name, outputStructure);
        this.internalOutputConnections.add(new ConnectionList());
    }
    
    
    @Override
    public void addParameter(String name, DataComponent paramStructure)
    {
        super.addParameter(name, paramStructure);
        this.internalParamConnections.add(new ConnectionList());
    }


    /**
     * Adds a process in the chain
     * @param name local name of process 
     * @param process DataProcess object to add in the chain
     */
    public void addProcess(String name, DataProcess process)
    {
        processTable.put(name, process);
        processList.add(process);
    }
    
    
    /**
     * Removes a process from the chain
     * TODO Also removes connections??
     * @param name
     */
    public void removeProcess(String name)
    {
        DataProcess process = processTable.get(name);
        processTable.remove(name);
        processList.remove(process);
    }
    
    
    /**
     * Retrieves a child DataProcess by its name
     * @param name local name of process to retrieve
     * @return DataProcess with given name
     */
    public DataProcess getProcess(String name)
    {
        return processTable.get(name);
    }
    
    
    /**
     * Retrieves the whole list of child processes
     * @return list of all DataProcess children
     */
    public List<DataProcess> getProcessList()
    {
    	return this.processList;
    }
    
    
    public String toString()
    {
    	StringBuffer text = new StringBuffer(super.toString());
    	
    	text.append("\n  Child Processes:\n");
    	Enumeration children = processTable.elements();
    	
    	while (children.hasMoreElements())
    	{
    		DataProcess child = (DataProcess)children.nextElement();
    		text.append("    " + child.getName() + "\n");
    	}
    	
    	return text.toString();
    }


	public boolean isChildrenThreadsOn()
	{
		return childrenThreadsOn;
	}


	public void setChildrenThreadsOn(boolean childrenThreadsOn)
	{
		this.childrenThreadsOn = childrenThreadsOn;
	}


    public List<DataConnection> getInternalConnections()
    {
        return internalConnections;
    }
    
    
    public void setOutputNeeded(int outputIndex, boolean needed)
    {
        internalOutputConnections.get(outputIndex).needed = needed;
    }
    
    
    public void setInputReady(int inputIndex)
    {
        ConnectionList connectionList = internalInputConnections.get(inputIndex);
        
        for (int i=0; i<connectionList.size(); i++)
        {
            DataConnection connection = connectionList.get(i);
            connection.dataAvailable = true;
        }
    }
}

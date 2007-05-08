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

import org.vast.cdm.common.DataComponent;
import org.vast.data.DataGroup;

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
        this.internalParamConnections = new ArrayList<ConnectionList>();
        this.internalOutputConnections = new ArrayList<ConnectionList>();
        this.internalConnections = new ArrayList<DataConnection>();
    }
    
    
    @Override
    public void init() throws ProcessException
    {        
        DataProcess childProcess = null;
        
        try
        {
            if (!childrenThreadsOn)
            {
                // build process execution list
                processExecList.clear();
                addUpstreamProcesses(this, internalOutputConnections);
                
                // init needed processes
                for (int i=0; i<processExecList.size(); i++)
                {
                    childProcess = processExecList.get(i);
                    childProcess.init();
                    childProcess.createNewOutputBlocks();
                    
                    if (childProcess.needSync())
                        this.needSync = true;
                }
                
                // clear all connections
                for (int i=0; i<internalConnections.size(); i++)
                    internalConnections.get(i).setDataAvailable(false);
            }
            else
            {
                // init all child processes and create output data blocks
                for (int i=0; i<processList.size(); i++)
                {
                    childProcess = processList.get(i);
                    childProcess.init();
                    childProcess.createNewOutputBlocks();
                }
            }
        }
        catch (Exception e)
        {
            String errMsg = initError + childProcess.getName() + " (" + childProcess.getType() + ")";
            throw new ProcessException(errMsg, e);
        }
    }
    
    
    @Override
    public void reset() throws ProcessException
    {
        for (int i=0; i<processList.size(); i++)
            processList.get(i).reset();
    }
    
    
    @Override
    public void dispose()
    {
        for (int i=0; i<processList.size(); i++)
            processList.get(i).dispose();
    }
       
    
    private void addUpstreamProcesses(DataProcess process, List<ConnectionList> connectionLists)
    {
        // loop through all inputs
    	for (int i=0; i<connectionLists.size(); i++)
    	{
            List<DataConnection> connectionList = connectionLists.get(i);
            
            // loop through all queues connected to this input
            for (int j=0; j<connectionList.size(); j++)
            {
                DataProcess upStreamProcess = (DataProcess)connectionList.get(j).getSourceProcess();
        		
        		if (upStreamProcess != this)
        		{
        			if (!processExecList.contains(upStreamProcess))
    	    		{
    	    			processExecList.add(upStreamProcess);
    	    			addUpstreamProcesses(upStreamProcess, upStreamProcess.inputConnections);
                        addUpstreamProcesses(upStreamProcess, upStreamProcess.paramConnections);
    	    		}
    	    		
                    if (process != this)
                        ensureOrder(upStreamProcess, process);
        		}
            }
    	}   	
    }
    
    
    /**
     * Ensure that sub processes are pre sorted in the exec list
     * Each time process1 has inputs connected to process2 outputs
     * process2 is placed before process1 in the list.
     * @param processBefore
     * @param processAfter
     */
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
        DataProcess childProcess = null;
        
        try
		{
			// if child threads are off, run all child processes
			if (!childrenThreadsOn)
	    	{
                // execute all sub processes in order
                if (processExecList != null)
                {
                    // combine input blocks
                    this.combineInputBlocks();
                    
                    if (needSync)
                    {
                        boolean moreToRun;
                        
                        // reset available flag for all needed internal inputs
                        // if chain can run it means values are available
                        for (int i=0; i<inputConnections.size(); i++)
                            this.setAvailability(internalInputConnections.get(i), inputConnections.get(i).isNeeded());
                        //this.setAvailability(internalInputConnections, true);
                        this.setAvailability(internalParamConnections, true);
                        
                        // reset available flag for all needed internal outputs
                        // if chain can run it means outputs are free (no value available)
                        this.setAvailability(internalOutputConnections, false);
                        
                        // loop until no more processes can run
                        do
                        {
                            moreToRun = false;
                            
                            // execute all child processes if they can run
                            for (int i=0; i<processExecList.size(); i++)
                            {
                                childProcess = processExecList.get(i);
                                
                                // continue only if process can run
                                if (childProcess.canRun())
                                {
                                    //System.out.println("--> Running: " + nextProcess.getName());
                                    childProcess.transferData(childProcess.inputConnections);
                                    childProcess.transferData(childProcess.paramConnections);
                                    childProcess.execute();
                                    childProcess.setAvailability(childProcess.inputConnections, false);
                                    childProcess.setAvailability(childProcess.paramConnections, false);
                                    childProcess.setAvailability(childProcess.outputConnections, true);
                                    moreToRun = true;
                                }
                                //else
                                //    System.out.println("--> Waiting: " + nextProcess.getName());
                            }
                        }
                        while (moreToRun);
                        
                        // transfer data to chain outputs when sub processes are done
                        this.transferData(internalOutputConnections);
                        
                        // combine output blocks
                        this.combineOutputBlocks();
                        
                        // determine what inputs are needed for next run
                        for (int i=0; i<inputConnections.size(); i++)
                            inputConnections.get(i).needed = this.checkAvailability(internalInputConnections.get(i), false);
                        
                        // determine what outputs are needed for next run
                        for (int i=0; i<outputConnections.size(); i++)
                            outputConnections.get(i).needed = this.checkAvailability(internalOutputConnections.get(i), true);
                    }
                    else
                    {
                        for (int i=0; i<processExecList.size(); i++)
                        {
                            childProcess = processExecList.get(i);
                            //System.out.println("--> Running: " + nextProcess.getName());
                            childProcess.transferData(childProcess.inputConnections);
                            childProcess.transferData(childProcess.paramConnections);
                            childProcess.execute();
                        }
                        
                        // transfer data to chain outputs when sub processes are done
                        this.transferData(internalOutputConnections);
                    }
                }
	    	}
            else
            {
                //super.writeOutputData(this.internalInputConnections);
                // TODO deal with params
                //super.fetchInputData(this.internalOutputConnections);
            }			
		}
		catch (Exception e)
		{
            String errMsg = execError + childProcess.getName() + " (" + childProcess.getType() + ")";
            throw new ProcessException(errMsg, e);
		}
    }
    
    
    @Override
    public void createNewOutputBlocks()
    {
        // make sure sub processes connected to the output
        // also uses these new DataBlocks for their output
        for (int i=0; i<internalOutputConnections.size(); i++)
        {
            ConnectionList connectionList = internalOutputConnections.get(i);
            
            if (connectionList.needed)
            {
                // loop through all connections
                for (int j=0; j<connectionList.size(); j++)
                {
                    // renew source process outputs
                    // they will be transfered to destination automatically during execution
                    DataConnection connection = connectionList.get(j);
                    connection.getSourceProcess().createNewOutputBlocks();
                }
            }
        }
    }
    
    
    public void combineInputBlocks()
    {
        for (int i=0; i<inputData.getComponentCount(); i++)
        {
            DataComponent input = inputData.getComponent(i);
            if (input instanceof DataGroup)
                ((DataGroup)input).combineDataBlocks();
        }
    }
    
    
    public void combineOutputBlocks()
    {
        // make sure sub processes connected to the output
        // also uses these new DataBlocks for their output
        for (int i=0; i<outputData.getComponentCount(); i++)
        {
            DataComponent output = outputData.getComponent(i);
            if (output instanceof DataGroup)
                ((DataGroup)output).combineDataBlocks();
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
        
        // TODO remove connections to and from this process ??
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
}

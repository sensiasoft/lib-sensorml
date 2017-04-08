/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2015 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.sensorML;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vast.cdm.common.CDMException;
import org.vast.data.DataRecordImpl;
import org.vast.process.DataConnectionList;
import org.vast.process.DataConnection;
import org.vast.process.DataQueue;
import org.vast.process.IProcessChainExec;
import org.vast.process.IProcessExec;
import org.vast.process.SMLException;
import org.vast.swe.SWEHelper;
import net.opengis.OgcPropertyList;
import net.opengis.sensorml.v20.AbstractProcess;
import net.opengis.sensorml.v20.AggregateProcess;
import net.opengis.sensorml.v20.Link;
import net.opengis.swe.v20.DataComponent;


/**
 * <p>
 * Implementation of an executable process chain.<br/>
 * This class can be associated with an
 * {@link net.opengis.sensorml.v20.AggregateProcess} to provide
 * execution capability, but this will only work if all components
 * of the aggregate also have an executable implementation associated
 * to them.
 * </p>
 *
 * @author Alex Robin
 * @since Feb 28, 2015
 */
public class ExecutableChainImpl extends ExecutableProcessImpl implements IProcessChainExec
{
    private final static Logger LOG = LoggerFactory.getLogger(ExecutableChainImpl.class.getName());
    
    // collections of executable child processes
    protected transient Map<String, IProcessExec> processTable;
    protected transient List<IProcessExec> processExecList;
    protected transient boolean childrenThreadsOn = false;
    
    // internal in/out/param data queues: component name -> queue
    protected transient List<DataConnectionList> internalInputConnections = null;
    protected transient List<DataConnectionList> internalOutputConnections = null;
    protected transient List<DataConnectionList> internalParamConnections = null;
    
    // list of all interconnections within the chain (helper)
    protected transient List<DataConnection> internalConnections = null;
    
    
    @Override
    protected void assignWrapperProcess(AbstractProcess wrapperProcess) throws SMLException
    {
        super.assignWrapperProcess(wrapperProcess);
        
        // prepare process collections
        OgcPropertyList<AbstractProcess> componentList = ((AggregateProcess)wrapperProcess).getComponentList();
        int numComponents = componentList.size();        
        processTable = new LinkedHashMap<String, IProcessExec>(numComponents);
        processExecList = new ArrayList<IProcessExec>(numComponents);
        
        for (int i = 0; i < numComponents; i++)
        {
            AbstractProcess process = componentList.get(i);
            String processName = componentList.getProperty(i).getName();
            if (process.isExecutable())
                processTable.put(processName, ((AbstractProcessImpl)process).executableProcess);
            else
                throw new SMLException("Child process " + processName + " is not executable");
        }
        
        // prepare internal connection lists
        int numInputs = wrapperProcess.getNumInputs();
        internalInputConnections = new ArrayList<DataConnectionList>(numInputs);
        for (int i = 0; i < numInputs; i++)
            internalInputConnections.add(new DataConnectionList());
        
        int numOutputs = wrapperProcess.getNumOutputs();
        internalOutputConnections = new ArrayList<DataConnectionList>(numOutputs);
        for (int i = 0; i < numOutputs; i++)
            internalOutputConnections.add(new DataConnectionList());
        
        int numParams = wrapperProcess.getNumParameters();
        internalParamConnections = new ArrayList<DataConnectionList>(numParams);
        for (int i = 0; i < numParams; i++)
            internalParamConnections.add(new DataConnectionList()); 
        
        // parse and create data queues between child processes
        int numConnections = ((AggregateProcess)wrapperProcess).getNumConnections();
        internalConnections = new ArrayList<DataConnection>(numConnections);
        for (Link link: ((AggregateProcess)wrapperProcess).getConnectionList())
        {
            DataQueue dataQueue = new DataQueue();
            connectSignal(dataQueue, link.getSource());
            connectSignal(dataQueue, link.getDestination());
            internalConnections.add(dataQueue);
        }
    }


    @Override
    public void init() throws SMLException
    {               
        // keep ref to generate exception message
        IProcessExec currentProcess = null;
        
        // init all child processes
        try
        {
            if (!childrenThreadsOn)
            {
                // build ordered process execution list starting from output signals
                processExecList.clear();
                addUpstreamProcesses(this, internalOutputConnections);
                
                // init needed processes
                for (IProcessExec childProcess: processExecList)
                {
                    currentProcess = childProcess;
                    childProcess.init();
                    childProcess.createNewOutputBlocks();                    
                    if (childProcess.needSync())
                        this.needSync = true;
                }

                reset();
            }
            else
            {
                // init all child processes and create output data blocks
                for (IProcessExec childProcess: processTable.values())
                {
                    currentProcess = childProcess;
                    childProcess.init();
                    childProcess.reset();
                    childProcess.createNewOutputBlocks();
                }
            }
        }
        catch (Exception e)
        {
            String errMsg = INIT_ERROR_MSG + currentProcess.getName() + " (" + currentProcess.getClass().getCanonicalName() + ")";
            LOG.debug(errMsg, e);
            throw new SMLException(errMsg, e);
        }
    }
        

    protected void connectSignal(DataQueue dataQueue, String linkString) throws SMLException
    {
        boolean internalConnection = false;
        IProcessExec selectedProcess = null;
        String processName = null;
        String portType = null;
        String portName = null;
        String dataPath = "";
        
        // parse link path
        int sep1 = linkString.indexOf(Link.PATH_SEPARATOR, 1);
        String part1 = linkString.substring(0, sep1++);
        int sep2 = linkString.indexOf(Link.PATH_SEPARATOR, sep1);
        if (sep2 < 0)
            sep2 = linkString.length();
        String part2 = linkString.substring(sep1, sep2++);
        
        // if we're linking to a component IO
        if ("components".equals(part1)) // support only links to components for now 
        {
            processName = part2;
            selectedProcess = processTable.get(processName);
            if (selectedProcess == null)
                throw new SMLException("Child process " + processName + " does't exist in aggregate process " + getName());
            
            int sep3 = linkString.indexOf(Link.PATH_SEPARATOR, sep2);
            portType = linkString.substring(sep2, sep3++);
            int sep4 = linkString.indexOf(Link.PATH_SEPARATOR, sep3);
            if (sep4 < 0)
                sep4 = linkString.length();
            portName = linkString.substring(sep3, sep4++);
            if (sep4 < linkString.length())
                dataPath = linkString.substring(sep4);
        }
        else
        {
            internalConnection = true;
            selectedProcess = this;
            portType = part1;
            portName = part2;
            int sep3 = linkString.indexOf(Link.PATH_SEPARATOR, sep2);
            if (sep3 > 0)
                dataPath = linkString.substring(sep3);
        }        
        
        // connect connection to input, output or parameter port
        if ("inputs".equals(portType))
        {
            try
            {
                if (internalConnection)
                    connectInternalInput(portName, dataPath, dataQueue);
                else
                    selectedProcess.connectInput(portName, dataPath, dataQueue);
            }
            catch (SMLException e)
            {
                throw new SMLException("Cannot connect input of process " + processName, e);
            }
        }
        else if ("outputs".equals(portType))
        {
            try
            {
                if (internalConnection)
                    connectInternalOutput(portName, dataPath, dataQueue);
                else
                    selectedProcess.connectOutput(portName, dataPath, dataQueue);
            }
            catch (SMLException e)
            {
                throw new SMLException("Cannot connect output of process " + processName, e);
            }
        }
        else if ("parameters".equals(portType))
        {
            try
            {
                if (internalConnection)
                    connectInternalParam(portName, dataPath, dataQueue);
                else
                    selectedProcess.connectParameter(portName, dataPath, dataQueue);
            }
            catch (SMLException e)
            {
                throw new SMLException("Cannot connect parameter of process " + processName, e);
            }
        }
        
        // make sure connection is ok
        try
        {
            dataQueue.check();
        }
        catch (SMLException e)
        {
            String srcName = dataQueue.getSourceProcess().getName();
            String destName = dataQueue.getDestinationProcess().getName();
            throw new SMLException("Connection " + linkString + " cannot be made between " + srcName + " and " + destName, e);
        }
    }  
    
    
    @Override
    public void reset() throws SMLException
    {
        // reset all sub-processes
        for (IProcessExec childProcess: processTable.values())
            childProcess.reset();
        
        // clear all internal connections
        for (int i=0; i<internalConnections.size(); i++)
            internalConnections.get(i).setDataAvailable(false);
    }
    
    
    @Override
    public void dispose()
    {
        if (processTable != null)
        {
            for (IProcessExec childProcess: processTable.values())
                childProcess.dispose();
        }
    }
       
    
    private void addUpstreamProcesses(IProcessExec process, List<DataConnectionList> connectionLists)
    {
        // loop through all inputs
        for (int i=0; i<connectionLists.size(); i++)
        {
            List<DataConnection> connectionList = connectionLists.get(i);
            
            // loop through all queues connected to this input
            for (int j=0; j<connectionList.size(); j++)
            {
                IProcessExec upStreamProcess = connectionList.get(j).getSourceProcess();
                
                if (upStreamProcess != this)
                {
                    if (!processExecList.contains(upStreamProcess))
                    {
                        processExecList.add(upStreamProcess);
                        addUpstreamProcesses(upStreamProcess, upStreamProcess.getInputConnections());
                        addUpstreamProcesses(upStreamProcess, upStreamProcess.getParamConnections());
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
    private void ensureOrder(IProcessExec processBefore, IProcessExec processAfter)
    {
        int before = processExecList.indexOf(processBefore);
        int after = processExecList.indexOf(processAfter);
        
        if (after < before)
        {
            processExecList.remove(after);
            processExecList.add(before, processAfter);
        }
    }
    
    
    @Override
    public void execute() throws SMLException
    {
        // keep ref to generate exception message
        IProcessExec currentProcess = null;
        
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
                        
                        // reset available flag for all needed internal input connections
                        // if chain can run it means values are available
                        for (int i=0; i<inputConnections.size(); i++)
                            if (inputConnections.get(i).isNeeded())
                                this.setAvailability(internalInputConnections.get(i), true);
                        
                        // reset available flag for all needed internal parameter connections
                        // if chain can run it means values are available
                        for (int i=0; i<paramConnections.size(); i++)
                            if (paramConnections.get(i).isNeeded())
                                this.setAvailability(internalParamConnections.get(i), true);
                        
                        // reset available flag for all needed internal outputs
                        // if chain can run it means outputs are free (no value available)
                        for (int i=0; i<outputConnections.size(); i++)
                            if (outputConnections.get(i).isNeeded())
                                this.setAvailability(internalOutputConnections.get(i), false);
                        
                        //System.out.println("Exec");
                        // loop until no more processes can run or all internalOutputs are full
                        do
                        {
                            //System.out.println("Cycle");
                            moreToRun = false;
                            
                            // execute all child processes if they can run
                            for (IProcessExec childProcess: processExecList)
                            {
                                currentProcess = childProcess;
                                
                                // continue only if process can run
                                if (childProcess.canRun())
                                {
                                    //System.out.println("--> Running: " + childProcess.getName());
                                    childProcess.transferData(childProcess.getInputConnections());
                                    childProcess.transferData(childProcess.getParamConnections());
                                    //childProcess.setAvailability(childProcess.outputConnections, true);
                                    childProcess.execute();
                                    childProcess.setAvailability(childProcess.getInputConnections(), false);
                                    childProcess.setAvailability(childProcess.getParamConnections(), false);
                                    childProcess.setAvailability(childProcess.getOutputConnections(), true);
                                    moreToRun = true;
                                }
                                //else
                                    //System.out.println("--> Waiting: " + childProcess.getName());
                            }
                        }
                        while (moreToRun && !this.checkAvailability(internalOutputConnections, true));
                        
                        // transfer data to chain outputs when sub processes are done
                        this.transferData(internalOutputConnections);
                        
                        // combine output blocks
                        this.combineOutputBlocks();
                        
                        // determine what inputs are needed for next run
                        for (int i=0; i<inputConnections.size(); i++)
                            inputConnections.get(i).setNeeded(this.checkAvailability(internalInputConnections.get(i), false));
                        
                        // determine what params are needed for next run
                        for (int i=0; i<paramConnections.size(); i++)
                            paramConnections.get(i).setNeeded(this.checkAvailability(internalParamConnections.get(i), false));
                        
                        // determine what outputs are needed for next run
                        for (int i=0; i<outputConnections.size(); i++)
                            outputConnections.get(i).setNeeded(this.checkAvailability(internalOutputConnections.get(i), true));
                    }
                    else
                    {
                        for (IProcessExec childProcess: processExecList)
                        {
                            currentProcess = childProcess;
                            //System.out.println("--> Running: " + childProcess.getName());
                            childProcess.transferData(childProcess.getInputConnections());
                            childProcess.transferData(childProcess.getParamConnections());
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
            String errMsg = EXEC_ERROR_MSG + currentProcess.getName() + " (" + currentProcess.getClass().getCanonicalName() + ")";
            throw new SMLException(errMsg, e);
        }
    }
    
    
    @Override
    public void createNewOutputBlocks()
    {
        // make sure sub processes connected to the output
        // also uses these new DataBlocks for their output
        for (int i=0; i<internalOutputConnections.size(); i++)
        {
            DataConnectionList connectionList = internalOutputConnections.get(i);
            
            if (connectionList.isNeeded())
            {
                // loop through all connections
                for (int j=0; j<connectionList.size(); j++)
                {
                    // renew source process outputs
                    // they will be transfered to destination automatically during execution
                    DataConnection connection = connectionList.get(j);
                    if (connection.getSourceProcess() != this)
                        connection.getSourceProcess().createNewOutputBlocks();
                }
            }
        }
    }
    
    
    public void combineInputBlocks()
    {
        for (int i = 0; i < inputData.size(); i++)
        {
            DataComponent input = inputData.getComponent(i);
            if (input instanceof DataRecordImpl)
                ((DataRecordImpl)input).combineDataBlocks();
        }
    }
    
    
    public void combineOutputBlocks()
    {
        // make sure sub processes connected to the output
        // also uses these new DataBlocks for their output
        for (int i = 0; i < outputData.size(); i++)
        {
            DataComponent output = outputData.getComponent(i);
            if (output instanceof DataRecordImpl)
                ((DataRecordImpl)output).combineDataBlocks();
        }
    }
    
 
    @Override
    public void connectInternalInput(String inputName, String dataPath, DataConnection connection) throws SMLException
    {
        try
        {
            int inputIndex = getSignalIndex(inputData, inputName);
            DataComponent input = inputData.getComponent(inputIndex);
            DataComponent src = SWEHelper.findComponentByPath(input, dataPath);
            connection.setSourceComponent(src);
            connection.setSourceProcess(this);
            internalInputConnections.get(inputIndex).add(connection);
        }
        catch (CDMException e)
        {
            throw new SMLException("Unable to connect internal signal to input '" + inputName + "'", e);
        }
    }
    
    
    @Override
    public void connectInternalOutput(String outputName, String dataPath, DataConnection connection) throws SMLException
    {
        try
        {
            int outputIndex = getSignalIndex(outputData, outputName);
            DataComponent output = outputData.getComponent(outputIndex);
            DataComponent dest = SWEHelper.findComponentByPath(output, dataPath);
            connection.setDestinationComponent(dest);
            connection.setDestinationProcess(this);
            internalOutputConnections.get(outputIndex).add(connection);
        }
        catch (CDMException e)
        {
            throw new SMLException("Unable to connect internal signal to output '" + outputName + "'", e);
        } 
    }
    
    
    @Override
    public void connectInternalParam(String paramName, String dataPath, DataConnection connection) throws SMLException
    {
        try
        {
            int paramIndex = getSignalIndex(paramData, paramName);
            DataComponent param = paramData.getComponent(paramIndex);
            DataComponent src = SWEHelper.findComponentByPath(param, dataPath);
            connection.setSourceComponent(src);
            connection.setSourceProcess(this);
            internalParamConnections.get(paramIndex).add(connection);
        }
        catch (CDMException e)
        {
            throw new SMLException("Unable to connect internal signal to parameter '" + paramName + "'", e);
        }
    }


    @Override
    public synchronized void start() throws SMLException
    {
        super.start();
        
        // start all child processes
        if (childrenThreadsOn)
        {
            for (IProcessExec childProcess: processTable.values())
                childProcess.start();
        }
    }
    
    
    @Override
    public synchronized void stop()
    {
        // stop all child processes
        if (childrenThreadsOn)
        {
            for (IProcessExec childProcess: processTable.values())
                childProcess.stop();
        }
        
        super.stop();
    }


    @Override
    public boolean isChildrenThreadsOn()
    {
        return childrenThreadsOn;
    }


    @Override
    public void setChildrenThreadsOn(boolean childrenThreadsOn)
    {
        this.childrenThreadsOn = childrenThreadsOn;
    }


    @Override
    public List<DataConnection> getInternalConnections()
    {
        return internalConnections;
    }
    
    
    @Override
    public void setOutputNeeded(int outputIndex, boolean needed)
    {
        internalOutputConnections.get(outputIndex).setNeeded(needed);
    }    
}

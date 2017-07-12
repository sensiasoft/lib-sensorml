/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2015 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.process;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.vast.data.AbstractRecordImpl;
import org.vast.swe.SWEHelper;
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
    public static final ProcessInfo INFO = new ProcessInfo(null, "Process Chain", null, ExecutableChainImpl.class);
        
    // internal in/out/param data queues: component name -> queue
    protected Map<String, DataConnectionList> internalInputConnections = new LinkedHashMap<>();
    protected Map<String, DataConnectionList> internalOutputConnections = new LinkedHashMap<>();
    protected Map<String, DataConnectionList> internalParamConnections = new LinkedHashMap<>();
    
    // executable child processes
    protected Map<String, IProcessExec> processTable = new LinkedHashMap<>();
    protected List<IProcessExec> processExecList = new ArrayList<>();
    protected boolean useChildrenThreads = false;
    protected boolean childrenThreadsStarted = false;
    protected boolean needSync;
    protected ExecutorService threadPool;
       

    public ExecutableChainImpl()
    {
        super(INFO);
    }
    
    
    public ExecutableChainImpl(boolean childrenThreadsOn)
    {
        this();
        this.useChildrenThreads = childrenThreadsOn;
    }


    @Override
    public Map<String, IProcessExec> getChildProcesses()
    {
        return Collections.unmodifiableMap(processTable);
    }


    @Override
    public IProcessExec addProcess(String name, IProcessExec process)
    {
        if (processTable.containsKey(name))
            throw new IllegalArgumentException("A process named '" + name + "' already exists in this process chain");
        
        // concatenate with chain instance name if set
        String procName = (instanceName != null) ? instanceName + '.' + name : name;
        process.setInstanceName(procName);
        process.setParentLogger(getLogger());
        
        processTable.put(name, process);
        return process;
    }


    @Override
    public void removeProcess(String name)
    {
        IProcessExec process = processTable.remove(name);
        process.dispose();
        
        // remove all connections to/from this process
        for (DataConnectionList connectionList: inputConnections.values())
        {
            for (IDataConnection conn: connectionList)
                disconnect(conn);
        }
        
        // remove all connections to process outputs
        for (DataConnectionList connectionList: outputConnections.values())
        {
            for (IDataConnection conn: connectionList)
                disconnect(conn);
        }
        
        // remove all connections to process parameters
        for (DataConnectionList connectionList: paramConnections.values())
        {
            for (IDataConnection conn: connectionList)
                disconnect(conn);
        }
    }


    @Override
    public Collection<IDataConnection> getInternalConnections()
    {
        HashSet<IDataConnection> allConnections = new LinkedHashSet<>();
        
        // add connections from internal input ports        
        for (DataConnectionList connectionList: internalInputConnections.values())
            allConnections.addAll(connectionList);
        for (DataConnectionList connectionList: internalParamConnections.values())
            allConnections.addAll(connectionList);
        
        // add inter-process connections
        for (IProcessExec process: processTable.values())
        {
            for (DataConnectionList connectionList: process.getInputConnections().values())
                allConnections.addAll(connectionList);
            for (DataConnectionList connectionList: process.getOutputConnections().values())
                allConnections.addAll(connectionList);
            for (DataConnectionList connectionList: process.getParamConnections().values())
                allConnections.addAll(connectionList);
        }
        
        // add connections to internal output ports        
        for (DataConnectionList connectionList: internalOutputConnections.values())
            allConnections.addAll(connectionList);
        
        return allConnections;
    }
    
    
    @Override
    public IDataConnection connect(IProcessExec srcProcess, DataComponent srcComponent,
                        IProcessExec destProcess, DataComponent destComponent) throws ProcessException
    {
        IDataConnection conn = useChildrenThreads ? new DataQueue() : new DataConnection();
                
        // connect source
        if (srcProcess == this)
            connectInternal(srcComponent, conn);
        else
            srcProcess.connect(srcComponent, conn);
            
        // connect destination
        if (destProcess == this)
            connectInternal(destComponent, conn);
        else
            destProcess.connect(destComponent, conn);
        
        return conn;
    }
    
    
    /*
     * Connects one of the process chain ports with an internal connection
     */
    public void connectInternal(DataComponent component, IDataConnection connection) throws ProcessException
    {
        DataComponent parentPort = SWEHelper.getRootComponent(component);
        Map<String, DataConnectionList> connectionGroup = findInternalConnectionGroup(parentPort);
        if (connectionGroup == null)
            throw new IllegalArgumentException("Cannot find component in any of this process chain ports");
        
        String portName = parentPort.getName();
        boolean isSource = (connectionGroup != internalOutputConnections);
        connect(portName, component, isSource, connection, connectionGroup);
    }
    
    
    /*
     * Find out which internal connection group this port belongs to
     */
    protected Map<String, DataConnectionList> findInternalConnectionGroup(DataComponent parentPort)
    {
        if (getInputList().contains(parentPort))
            return internalInputConnections;
        else if (getOutputList().contains(parentPort))
            return internalOutputConnections;
        else if (getParameterList().contains(parentPort))
            return internalParamConnections;
        else
            return null;
    }
    
    
    @Override
    public void removeInternalConnection(IDataConnection connection)
    {
        if (!getInternalConnections().contains(connection))
            throw new IllegalArgumentException("Cannot find connection in this process chain");
        
        disconnect(connection.getSourcePort(), connection);
        connection.setSource(null, null);
        
        disconnect(connection.getDestinationPort(), connection);
        connection.setDestination(null, null);
    }
    
    
    @Override
    protected void disconnect(DataComponent parentPort, IDataConnection connection)
    {
        Map<String, DataConnectionList> connectionGroup = findInternalConnectionGroup(parentPort);
        if (connectionGroup == null)
            connectionGroup = findConnectionGroup(parentPort);
        connectionGroup.get(parentPort.getName()).remove(connection);
    }


    @Override
    public void init() throws ProcessException
    {               
        // init all child processes
        try
        {
            if (!useChildrenThreads)
            {
                // build ordered process execution list starting from output signals
                processExecList.clear();
                addUpstreamProcesses(this, internalOutputConnections);
                
                // init needed processes
                for (IProcessExec childProcess: processExecList)
                {
                    childProcess.setParentLogger(getLogger());
                    childProcess.init();
                    if (childProcess.needSync())
                        this.needSync = true;
                }
            }
            else
            {
                // init all child processes
                for (IProcessExec childProcess: processTable.values())
                {
                    childProcess.setParentLogger(getLogger());
                    childProcess.init();
                }
            }
        }
        catch (Exception e)
        {
            getLogger().error(INIT_ERROR_MSG, e);
            throw new ProcessException(INIT_ERROR_MSG, e);
        }
        
        super.init();
    }
       
    
    /*
     * Add upstream processes starting from outputs
     */
    private void addUpstreamProcesses(IProcessExec process, Map<String, DataConnectionList> connectionGroup)
    {
        // loop through all input connections
        for (DataConnectionList connectionList: connectionGroup.values())
        {
            for (IDataConnection connection: connectionList)
            {
                IProcessExec upStreamProcess = connection.getSourceProcess();
                
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
    
    
    /*
     * Ensure that sub processes are correctly sorted in the exec list
     * Each time process1 has inputs connected to process2 outputs
     * process2 is placed before process1 in the list.
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
    public void execute() throws ProcessException
    {
        try
        {
            // combine input blocks
            combineInputBlocks();
            
            // make input data available inside chain
            transferInputData(inputConnections, internalInputConnections);
            transferInputData(paramConnections, internalParamConnections);
            
            // if child threads are off, run all child processes
            if (!useChildrenThreads)
            {
                // execute all sub processes in order
                if (processExecList != null)
                {
                    if (needSync)
                    {
                        // set internal outputs as needed if data is needed outside the chain
                        setNeededOutputs(outputConnections, internalOutputConnections);
                        
                        // loop until no more processes can run or all internalOutputs are full
                        boolean moreToRun;
                        do
                        {
                            moreToRun = false;
                            
                            // execute all child processes if they can run
                            for (IProcessExec childProcess: processExecList)
                            {
                                // continue only if process can run
                                if (childProcess.canRun())
                                {
                                    getLogger().debug("Running process '{}'", childProcess.getInstanceName());
                                    childProcess.run();
                                    moreToRun = true;
                                }
                            }
                        }
                        while (moreToRun && !checkAvailability(internalOutputConnections, true));                        
                        
                        // set external inputs as needed if data has been consumed
                        // inside the chain (i.e. available = false)
                        setNeededInputs(inputConnections, internalInputConnections);
                        setNeededInputs(paramConnections, internalParamConnections);
                    }
                    else
                    {
                        for (IProcessExec childProcess: processExecList)
                            childProcess.run();
                    }
                }
            }
            else
            {
                if (!childrenThreadsStarted)
                    startChildrenThreads();
            }
            
            //transferOutputData();
            consumeData(internalOutputConnections);
            combineOutputBlocks();
        }  
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();            
        }     
        catch (Exception e)
        {
            getLogger().error(EXEC_ERROR_MSG, e);
            throw new ProcessException(EXEC_ERROR_MSG, e);
        }
    }
    
    
    protected void transferInputData(
            Map<String, DataConnectionList> externalConnections,
            Map<String, DataConnectionList> internalConnections) throws InterruptedException
    {
        for (String portName: internalConnections.keySet())
        {
            DataConnectionList externalConnectionList = externalConnections.get(portName);
            DataConnectionList internalConnectionList = internalConnections.get(portName);
            
            if (externalConnectionList == null || externalConnectionList.isNeeded())
            {
                for (IDataConnection conn: internalConnectionList)
                    conn.publishData();
            }
        }
    }
    
    
    /*@Override
    protected void publishData() throws InterruptedException
    {
        // do nothing
        // data is published when available in transferOutputData
    }
    
    
    protected void transferOutputData() throws InterruptedException
    {
        for (String outputName: internalOutputConnections.keySet())
        {
            DataConnectionList externalConnectionList = outputConnections.get(outputName);
            DataConnectionList internalConnectionList = internalOutputConnections.get(outputName);
            
            if (externalConnectionList == null || externalConnectionList.isNeeded())
            {
                if (checkAvailability(internalConnectionList, true))
                {
                    for (IDataConnection conn: internalConnectionList)
                        conn.transferData();
                    
                    combineOutputBlocks(outputName);
                    publishData(outputName);
                }
            }
        }
    }*/
    
    
    protected void setNeededInputs(
            Map<String, DataConnectionList> externalConnections,
            Map<String, DataConnectionList> internalConnections)
    {
        for (String portName: externalConnections.keySet())
        {
            DataConnectionList externalConnectionList = externalConnections.get(portName);
            DataConnectionList internalConnectionList = internalConnections.get(portName);
            
            if (internalConnectionList != null)
            {
                boolean needed = checkAvailability(internalConnectionList, false);
                externalConnectionList.setNeeded(needed);
            }
        }
    }
    
    
    protected void setNeededOutputs(
            Map<String, DataConnectionList> externalConnections,
            Map<String, DataConnectionList> internalConnections)
    {
        for (String portName: internalConnections.keySet())
        {
            DataConnectionList externalConnectionList = externalConnections.get(portName);
            DataConnectionList internalConnectionList = internalConnections.get(portName);
            
            if (externalConnectionList == null || externalConnectionList.isNeeded())
                internalConnectionList.setNeeded(true);
        }
    }
    
    
    @Override
    public synchronized void start() throws ProcessException
    {
        if (useChildrenThreads)
        {
            threadPool = Executors.newCachedThreadPool();
            start(threadPool);
        }
        else
            super.start();
    }
    
    
    @Override
    public synchronized void stop()
    {
        super.stop();
        
        if (childrenThreadsStarted)
        {
            for (final IProcessExec process: processTable.values())
                process.stop();
        }
        
        if (threadPool != null)
        {            
            threadPool.shutdownNow();
            threadPool = null;
        }
        
        childrenThreadsStarted = false;
    }
    
    
    protected synchronized void startChildrenThreads() throws ProcessException
    {
        childrenThreadsStarted = true;
        
        // create new thread pool if needed
        if (threadPool == null)
            threadPool = Executors.newCachedThreadPool();
        
        for (final IProcessExec process: processTable.values())
            process.start(threadPool);
    }
    
    
    public void combineInputBlocks()
    {
        for (int i = 0; i < inputData.size(); i++)
        {
            DataComponent input = inputData.getComponent(i);
            if (input instanceof AbstractRecordImpl)
                ((AbstractRecordImpl<?>)input).combineDataBlocks();
        }
    }
    
    
    public void combineOutputBlocks()
    {
        // make sure sub processes connected to the output
        // also uses these new DataBlocks for their output
        for (int i = 0; i < outputData.size(); i++)
        {
            DataComponent output = outputData.getComponent(i);
            if (output instanceof AbstractRecordImpl)
                ((AbstractRecordImpl<?>)output).combineDataBlocks();
        }
    }
    
    
    protected void combineOutputBlocks(String outputName)
    {
        DataComponent output = outputData.getComponent(outputName);
        if (output instanceof AbstractRecordImpl)
            ((AbstractRecordImpl<?>)output).combineDataBlocks();
    }


    public boolean isUseChildrenThreads()
    {
        return useChildrenThreads;
    }
    
    
    public void setUseChildrenThreads(boolean useThreads)
    {
        this.useChildrenThreads = useThreads;
    }
    

    @Override
    public boolean needSync()
    {
        return needSync;
    }
    
    
    @Override
    public void setOutputNeeded(String outputName, boolean needed)
    {
        internalOutputConnections.get(outputName).setNeeded(needed);
    }
    
    
    @Override
    public void dispose()
    {
        stop();
        for (IProcessExec child: processTable.values())
            child.dispose();
    }


    @Override
    public String toString()
    {
        StringBuilder text = new StringBuilder(super.toString());
        
        text.append("\n** Child Processes **\n\n");
        for (IProcessExec child: processTable.values())
            text.append(child.toString()).append('\n');
                
        return text.toString();
    }
}

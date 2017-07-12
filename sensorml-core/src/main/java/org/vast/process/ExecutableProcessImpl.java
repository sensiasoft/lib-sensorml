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

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vast.data.AbstractRecordImpl;
import org.vast.swe.SWEHelper;
import org.vast.util.Asserts;
import org.vast.util.LoggerWrapper;
import net.opengis.sensorml.v20.IOPropertyList;
import net.opengis.swe.v20.DataComponent;


/**
 * <p>
 * Abstract base for all executable process implementations.
 * </p>
 *
 * @author Alex Robin
 * @since Feb 28, 2015
 */
public abstract class ExecutableProcessImpl implements IProcessExec
{
    private Logger log;

    protected static final String IO_ERROR_MSG = "Invalid I/O Structure";
    protected static final String INIT_ERROR_MSG = "Error during process initialization";
    protected static final String EXEC_ERROR_MSG = "Error during process execution";

    protected IOPropertyList inputData = new IOPropertyList();
    protected IOPropertyList outputData = new IOPropertyList();
    protected IOPropertyList paramData = new IOPropertyList();
    protected Map<String, DataConnectionList> inputConnections = new LinkedHashMap<>();
    protected Map<String, DataConnectionList> outputConnections = new LinkedHashMap<>();
    protected Map<String, DataConnectionList> paramConnections = new LinkedHashMap<>();
    
    protected ProcessInfo processInfo;
    protected String instanceName;
    protected boolean initialized = false;
    protected boolean started = false;
    protected boolean usingOutputQueues = false;
    protected Future<?> execFuture;

    
    protected ExecutableProcessImpl(ProcessInfo processInfo)
    {
        Asserts.checkNotNull(processInfo);
        this.processInfo = processInfo;
    }
    
    
    @Override
    public void init() throws ProcessException
    {
        initPortData();
        initialized = true;
    }
    
    
    protected void initPortData()
    {
        // create input data blocks
        for (int i = 0; i < inputData.size(); i++)
        {
            DataComponent comp = inputData.getComponent(i);
            initPortData(comp);
        }
        
        // create param data blocks
        for (int i = 0; i < paramData.size(); i++)
        {
            DataComponent comp = paramData.getComponent(i);
            initPortData(comp);
        }
        
        // create output data blocks
        for (int i = 0; i < outputData.size(); i++)
        {
            DataComponent comp = outputData.getComponent(i);
            initPortData(comp);
        }
    }
    
    
    protected void initPortData(DataComponent port)
    {
        if (!port.hasData())
        {
            if (port instanceof AbstractRecordImpl)
                ((AbstractRecordImpl<?>)port).combineDataBlocks();
            else
                port.assignNewDataBlock();
        }
    }
    
    
    protected void checkInitialized()
    {
        if (!initialized)
            throw new IllegalStateException("Process is not fully initialized");
    }
    
    
    @Override
    public boolean canRun()
    {
        return initialized &&
               checkAvailability(inputConnections, true) &&
               checkAvailability(paramConnections, true) &&
               checkAvailability(outputConnections, false);
    }


    /*
     * Checks if all connections in the group and marked as needed
     * have the specified availability state.
     */
    protected boolean checkAvailability(Map<String, DataConnectionList> connectionGroup, boolean availability)
    {
        for (DataConnectionList connectionList: connectionGroup.values())
        {
            if (connectionList.isNeeded())
            {
                for (IDataConnection connection: connectionList)
                {
                    if (connection.isDataAvailable() != availability)
                        return false;
                }
            }
        }

        return true;
    }


    /*
     * Checks that all connections in the list hava the specified availability state
     */
    protected boolean checkAvailability(DataConnectionList connectionList, boolean availability)
    {
        // loop through all connections in the list
        for (IDataConnection connection: connectionList)
        {
            if (connection.isDataAvailable() != availability)
                return false;
        }

        return true;
    }


    @Override
    public void run() throws ProcessException
    {
        checkInitialized();
        
        try
        {
            // fetch inputs, execute process and publish outputs
            consumeData(inputConnections);
            consumeData(paramConnections);
            execute();
            if (!Thread.currentThread().isInterrupted())
                publishData();
        }
        catch (ProcessException e)
        {
            getLogger().error(EXEC_ERROR_MSG, e);
            throw new ProcessException(EXEC_ERROR_MSG, e);
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
    }
    

    @Override
    public void start() throws ProcessException
    {
        start(Executors.newSingleThreadExecutor());
    }


    @Override
    public synchronized void start(ExecutorService threadPool) throws ProcessException
    {
        if (started)
            return;
        
        if (!initialized)
            init();
        
        started = true;
        final ExecutableProcessImpl process = this;
        execFuture = threadPool.submit(new Runnable() {
            @Override
            public void run()
            {                    
                getLogger().debug("Process '{}': Thread started", process.getInstanceName());
                                                
                try
                {
                    while (started)
                        process.run();
                }
                catch (ProcessException e)
                {
                    getLogger().error(String.format("Error during execution of process '%s'", process.getInstanceName()), e);
                }
                
                getLogger().debug("Process '{}': Thread stopped", process.getInstanceName());
            }
        });
    }


    @Override
    public synchronized void stop()
    {
        started = false;
        
        if (execFuture != null)
        {
            execFuture.cancel(true);
            execFuture = null;
        }
    }


    /*
     * Consume data from all needed input ports
     */
    protected void consumeData(Map<String, DataConnectionList> connectionGroup) throws InterruptedException
    {
        // loop through all inputs
        for (DataConnectionList connectionList: connectionGroup.values())
        {
            if (connectionList.isNeeded())
            {
                // loop through all connections
                for (IDataConnection connection: connectionList)
                    connection.transferData();
            }
        }
    }


    /*
     * Publish data on all needed outputs ports
     */
    protected void publishData() throws InterruptedException
    {
        for (String outputName: outputConnections.keySet())
            publishData(outputName);
    }
    
    
    /*
     * Publish data on the given output port
     */
    protected void publishData(String outputName) throws InterruptedException
    {
        DataConnectionList connectionList = outputConnections.get(outputName);
        
        if (connectionList != null && connectionList.isNeeded())
        {
            for (IDataConnection connection: connectionList)
                connection.publishData();

            // renew output dataBlock
            if (usingOutputQueues)
            {
                DataComponent comp = outputData.getComponent(outputName);
                comp.renewDataBlock();
            }
        }
    }
    

    @Override
    public boolean needSync()
    {
        return false;
    }
    
    
    @Override
    public Map<String, DataConnectionList> getInputConnections()
    {
        return Collections.unmodifiableMap(inputConnections);
    }
    
    
    @Override
    public Map<String, DataConnectionList> getOutputConnections()
    {
        return Collections.unmodifiableMap(outputConnections);
    }
    
    
    @Override
    public Map<String, DataConnectionList> getParamConnections()
    {
        return Collections.unmodifiableMap(paramConnections);
    }
    
    
    @Override
    public void connect(DataComponent component, IDataConnection connection) throws ProcessException
    {
        DataComponent parentPort = SWEHelper.getRootComponent(component);
        Map<String, DataConnectionList> connectionGroup = findConnectionGroup(parentPort);
        String portName = parentPort.getName();
        boolean isSource = (connectionGroup == outputConnections);
        connect(portName, component, isSource, connection, connectionGroup);
    }
    
    
    /*
     * Find out which connection group this port belongs to
     */
    protected Map<String, DataConnectionList> findConnectionGroup(DataComponent parentPort)
    {
        if (getInputList().contains(parentPort))
            return inputConnections;
        else if (getOutputList().contains(parentPort))
            return outputConnections;
        else if (getParameterList().contains(parentPort))
            return paramConnections;
        else
            throw new IllegalArgumentException("Cannot find component in any of this process ports");
    }
    
    
    protected void connect(String portName, DataComponent component, boolean isSource,
                           IDataConnection connection, Map<String, DataConnectionList> connectionGroup) throws ProcessException
    {
        // get or create connection list for this port
        DataConnectionList connectionList = connectionGroup.get(portName);
        if (connectionList == null)
        {
            connectionList = new DataConnectionList();
            connectionGroup.put(portName, connectionList);
        }
        
        // set as source or destination depending on port type
        if (isSource)
        {
            // validate connection (i.e. check that structure and units are compatible)
            DataConnection.validate(component, connection.getDestinationComponent());            
            connection.setSource(this, component);
        }
        else
        {
            // only one incoming connection allowed to component or its parents
            for (IDataConnection otherConnection: connectionList)
            {
                DataComponent parent = component;
                while (parent != null)
                {
                    if (otherConnection.getDestinationComponent() == parent)
                        throw new ProcessException("A connection to component '" + parent.getName() + "' already exists");
                    parent = parent.getParent();
                }
            }
            
            // validate connection (i.e. check that structure and units are compatible)
            DataConnection.validate(connection.getSourceComponent(), component);            
            connection.setDestination(this, component);
        }
        
        // if everything ok, add connection to list
        connectionList.add(connection);
        if (connection instanceof DataQueue)
            usingOutputQueues = true;
    }
    
    
    @Override
    public void disconnect(IDataConnection connection)
    {
        DataComponent parentPort = null;
        
        if (connection.getSourceProcess() == this)
        {
            parentPort = connection.getSourcePort();
            connection.setSource(null, null);
        }
        else if (connection.getDestinationProcess() == this)
        {
            parentPort = connection.getDestinationPort();
            connection.setDestination(null, null);
        }
        else
            throw new IllegalArgumentException("Connection is not attached to this process");
            
        disconnect(parentPort, connection);
    }
    
    
    protected void disconnect(DataComponent parentPort, IDataConnection connection)
    {
        Map<String, DataConnectionList> connectionGroup = findConnectionGroup(parentPort);
        connectionGroup.get(parentPort.getName()).remove(connection);
    }
    
    
    @Override
    public void setParentLogger(Logger log)
    {
        this.log = new LoggerWrapper(log);
        setLoggerPrefix();
    }
    
    
    @Override
    public void setInstanceName(String name)
    {
        this.instanceName = name;
        setLoggerPrefix();
    }
    
    
    protected void setLoggerPrefix()
    {
        StringBuilder prefix = new StringBuilder("Process ");
        
        if (instanceName != null)
            prefix.append(instanceName).append(' ')
                  .append('(').append(processInfo.getName()).append(')');
        else
            prefix.append(processInfo.getName());
        
        prefix.append(": ");
       
        if (!(getLogger() instanceof LoggerWrapper))
            log = new LoggerWrapper(getLogger());
        ((LoggerWrapper)log).setPrefix(prefix.toString());
    }
    
    
    protected Logger getLogger()
    {
        // defaut logger is associated to the process class
        if (log == null)
            log = LoggerFactory.getLogger(getClass());
        
        return log;
    }
    
    
    @Override
    public String getInstanceName()
    {
        return instanceName;
    }
    
    
    @Override
    public ProcessInfo getProcessInfo()
    {
        return processInfo;
    }
    
    
    @Override
    public IOPropertyList getInputList()
    {
        return inputData;
    }
    
    
    @Override
    public IOPropertyList getOutputList()
    {
        return outputData;
    }
    
    
    @Override
    public IOPropertyList getParameterList()
    {
        return paramData;
    }
    
    
    @Override
    public void notifyParamChange()
    {        
    }
    
    
    @Override
    public void dispose()
    {
        initialized = false;
    }  


    @Override
    protected void finalize() throws Throwable
    {
        this.dispose();
        super.finalize();
    }


    @Override
    public String toString()
    {
        StringBuilder text = new StringBuilder();
        
        text.append("Executable Process ");
        if (instanceName != null)
            text.append(instanceName).append(' ');
        text.append('(').append(processInfo.getName()).append(')');
        text.append('\n');
        
        text.append("  Inputs: ");
        for (int i = 0; i < inputData.size(); i++)
        {
            if (i > 0)
                text.append(", ");
            text.append(inputData.getProperty(i).getName());
        }
        text.append('\n');
        
        text.append("  Outputs: ");
        for (int i = 0; i < outputData.size(); i++)
        {
            if (i > 0)
                text.append(", ");
            text.append(outputData.getProperty(i).getName());
        }
        text.append('\n');
        
        text.append("  Parameters: ");
        for (int i = 0; i < paramData.size(); i++)
        {
            if (i > 0)
                text.append(", ");
            text.append(paramData.getProperty(i).getName());
        }
        text.append('\n');

        return text.toString();
    }
}

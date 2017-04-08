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
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vast.cdm.common.CDMException;
import org.vast.data.AbstractDataComponentImpl;
import org.vast.process.DataConnectionList;
import org.vast.process.DataConnection;
import org.vast.process.DataQueue;
import org.vast.process.IProcessExec;
import org.vast.process.SMLException;
import org.vast.swe.SWEHelper;
import net.opengis.OgcPropertyList;
import net.opengis.sensorml.v20.AbstractProcess;
import net.opengis.sensorml.v20.IOPropertyList;
import net.opengis.swe.v20.DataBlock;
import net.opengis.swe.v20.DataComponent;


/**
 * <p>
 * Abstract base for all executable process implementations.<br/>
 * This class provides logic to run a process synchronously by simply calling
 * {@link #execute()} or in its own thread using {@link #start()} and {@link #stop()}.
 * </p>
 *
 * @author Alex Robin
 * @since Feb 28, 2015
 */
public abstract class ExecutableProcessImpl implements IProcessExec, Runnable
{
    static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutableProcessImpl.class.getName());

    protected static final String IO_ERROR_MSG = "Invalid I/O Structure";
    protected static final String INIT_ERROR_MSG = "Error while initializing process ";
    protected static final String EXEC_ERROR_MSG = "Error while executing process ";

    protected AbstractProcess wrapperProcess;
    protected IOPropertyList inputData = new IOPropertyList();
    protected IOPropertyList outputData = new IOPropertyList();
    protected IOPropertyList paramData = new IOPropertyList();

    protected transient List<DataConnectionList> inputConnections = null;
    protected transient List<DataConnectionList> outputConnections = null;
    protected transient List<DataConnectionList> paramConnections = null;
    protected transient Thread processThread = null; // internal process thread
    protected transient boolean started = false;
    protected transient boolean usingQueueBuffers = false;
    protected transient boolean needSync = false;


    protected void assignWrapperProcess(AbstractProcess wrapperProcess) throws SMLException
    {
        this.wrapperProcess = wrapperProcess;
        
        // prepare inputs
        IOPropertyList wrapperInputs = wrapperProcess.getInputList();
        if (inputData.size() != 0)
        {
            // replace wrapper inputs if defined by this exec impl
            wrapperInputs.clear();
            wrapperInputs.addAll(inputData);
        }
        inputData = wrapperProcess.getInputList();
        int numInputs = wrapperProcess.getNumInputs();
        inputConnections = new ArrayList<DataConnectionList>(numInputs);
        for (int i = 0; i < numInputs; i++)
            inputConnections.add(new DataConnectionList());
        
        // prepare outputs
        IOPropertyList wrapperOutputs = wrapperProcess.getOutputList();
        if (outputData.size() != 0)
        {
            // replace wrapper outputs if defined by this exec impl
            wrapperOutputs.clear();
            wrapperOutputs.addAll(outputData);
        }
        outputData = wrapperProcess.getOutputList();
        int numOutputs = wrapperProcess.getNumOutputs();
        outputConnections = new ArrayList<DataConnectionList>(numOutputs);
        for (int i = 0; i < numOutputs; i++)
            outputConnections.add(new DataConnectionList());
        
        // prepare parameters
        IOPropertyList wrapperParams = wrapperProcess.getParameterList();
        if (paramData.size() != 0)
        {
            // replace wrapper params if defined by this exec impl
            wrapperParams.clear();
            wrapperParams.addAll(paramData);
        }
        paramData = wrapperProcess.getParameterList();
        int numParams = wrapperProcess.getNumParameters();
        paramConnections = new ArrayList<DataConnectionList>(numParams);
        for (int i = 0; i < numParams; i++)
            paramConnections.add(new DataConnectionList());
    }


    @Override
    public abstract void init() throws SMLException;


    @Override
    public abstract void execute() throws SMLException;
    
    
    @Override
    public void dispose()
    {
    }
 

    @Override
    public void reset() throws SMLException
    {
    }
    
    
    @Override
    public String getName()
    {
        return wrapperProcess.getId();
    }


    @Override
    public boolean canRun()
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
     * Checks if all connections in the list and marked as needed
     * have the specified availability state.
     * @return true if all needed connections satisfy the condition, false otherwise
     */
    protected boolean checkAvailability(List<DataConnectionList> allConnections, boolean availability)
    {
        // loop through all connection lists
        for (int i = 0; i < allConnections.size(); i++)
        {
            DataConnectionList connectionList = allConnections.get(i);

            if (connectionList.isNeeded())
            {
                // loop through all connections in each list
                for (int j = 0; j < connectionList.size(); j++)
                {
                    DataConnection connection = connectionList.get(j);
                    if (connection.isDataAvailable() != availability)
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
    protected boolean checkAvailability(DataConnectionList connectionList, boolean availability)
    {
        // loop through all connections in the list
        for (int j = 0; j < connectionList.size(); j++)
        {
            DataConnection connection = connectionList.get(j);
            if (connection.isDataAvailable() != availability)
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
     * @availability availability flag
     */
    @Override
    public void setAvailability(List<DataConnectionList> allConnections, boolean availability)
    {
        // loop through all connection lists
        for (int i = 0; i < allConnections.size(); i++)
        {
            DataConnectionList connectionList = allConnections.get(i);

            if (connectionList.isNeeded())
                setAvailability(connectionList, availability);
        }
    }


    /**
     * Sets the availability flags of all connections in the list
     * to the specified state. (even if not needed!)
     * @param connectionList
     * @param availability
     */
    protected void setAvailability(DataConnectionList connectionList, boolean availability)
    {
        // loop through all connections in the list
        for (int j = 0; j < connectionList.size(); j++)
        {
            DataConnection connection = connectionList.get(j);
            connection.setDataAvailable(availability);
        }
    }


    /**
     * Fetch new data from input queues in async mode (threaded)
     * Thread will wait until each queue needing data receives next block
     */
    protected void fetchData(List<DataConnectionList> allConnections) throws InterruptedException
    {
        // go through all connections and get next dataBlock from them
        for (int i = 0; i < allConnections.size(); i++)
        {
            DataConnectionList connectionList = allConnections.get(i);

            if (connectionList.isNeeded())
            {
                // loop through all connections
                for (int j = 0; j < connectionList.size(); j++)
                {
                    // get datablock from queue and assign it to destination component
                    DataQueue dataQueue = (DataQueue) connectionList.get(j);
                    DataComponent dataComponent = dataQueue.getDestinationComponent();
                    DataBlock block = dataQueue.get();
                    dataComponent.setData(block);
                }
            }
        }
    }


    /**
     * Writes new data to output queues in async mode (threaded)
     */
    protected void writeData(List<DataConnectionList> allConnections) throws InterruptedException
    {
        for (int i = 0; i < allConnections.size(); i++)
        {
            DataConnectionList connectionList = allConnections.get(i);

            if (connectionList.isNeeded())
            {
                // loop through all connections
                for (int j = 0; j < connectionList.size(); j++)
                {
                    // get datablock from source component and add to queue
                    DataQueue dataQueue = (DataQueue) connectionList.get(j);
                    DataComponent dataComponent = dataQueue.getSourceComponent();
                    DataBlock block = dataComponent.getData();
                    dataQueue.add(block);
                }

                // renew output dataBlock
                DataComponent comp = inputData.getComponent(i);
                comp.renewDataBlock();
            }
        }
    }


    @Override
    public void transferData(List<DataConnectionList> allConnections)
    {
        // loop through all inputs
        for (int i = 0; i < allConnections.size(); i++)
        {
            DataConnectionList connectionList = allConnections.get(i);

            if (connectionList.isNeeded())
            {
                // loop through all connections
                for (int j = 0; j < connectionList.size(); j++)
                {
                    DataConnection connection = connectionList.get(j);
                    connection.transferDataBlocks();
                }
            }
        }
    }


    @Override
    public void createNewOutputBlocks()
    {
        for (int i = 0; i < outputData.size(); i++)
        {
            DataComponent comp = outputData.getComponent(i);
            comp.renewDataBlock();
        }
    }


    @Override
    public void createNewInputBlocks()
    {
        for (int i = 0; i < inputData.size(); i++)
        {
            DataComponent comp = inputData.getComponent(i);
            comp.renewDataBlock();
        }
    }


    @Override
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
            catch (SMLException e)
            {
                LOGGER.error("Error during execution of process " + getName(), e);
            }
            catch (InterruptedException e)
            {
                started = false;
            }
        }
        while (started);
    }


    @Override
    public synchronized void start() throws SMLException
    {
        if (!started)
        {
            if (LOGGER.isDebugEnabled())
            {
                String processClass = getClass().getName();
                processClass = processClass.substring(processClass.lastIndexOf('.') + 1);
                LOGGER.debug("Process " + getName() + " (" + processClass + ") Thread started");
            }

            this.init();
            started = true;
            usingQueueBuffers = true;
            processThread = new Thread(this);
            processThread.setName(getName());
            processThread.start();
        }
    }


    @Override
    public synchronized void stop()
    {
        if (started)
        {
            if (LOGGER.isDebugEnabled())
            {
                String processClass = getClass().getName();
                processClass = processClass.substring(processClass.lastIndexOf('.') + 1);
                LOGGER.debug("Process " + getName() + " (" + processClass + ") Thread stopped");
            }

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
    @Override
    public String toString()
    {
        StringBuffer text = new StringBuffer();
        String indent = "    ";

        text.append("Process: ");
        text.append(getName());
        text.append(" (" + this.getClass().getName() + ")\n");

        text.append("\n  Inputs:\n");
        for (int i = 0; i < inputData.size(); i++)
        {
            text.append(indent);
            text.append(((AbstractDataComponentImpl)inputData.getComponent(i)).toString(indent));
        }

        text.append("\n  Outputs:\n");
        for (int i = 0; i < outputData.size(); i++)
        {
            text.append(indent);
            text.append(((AbstractDataComponentImpl)outputData.getComponent(i)).toString(indent));
        }

        text.append("\n  Parameters:\n");
        for (int i = 0; i < paramData.size(); i++)
        {
            text.append(indent);
            text.append(((AbstractDataComponentImpl)paramData.getComponent(i)).toString(indent));
        }

        return text.toString();
    }


    @Override
    public void connectInput(String inputName, String dataPath, DataConnection connection) throws SMLException
    {
        try
        {
            int inputIndex = getSignalIndex(inputData, inputName);
            DataComponent input = inputData.getComponent(inputIndex);
            DataComponent dest = SWEHelper.findComponentByPath(input, dataPath);
            connection.setDestinationComponent(dest);
            connection.setDestinationProcess(this);
            inputConnections.get(inputIndex).add(connection);
        }
        catch (CDMException e)
        {
            throw new SMLException("Unable to connect signal to input '" + inputName + "'", e);
        }
    }


    @Override
    public void connectOutput(String outputName, String dataPath, DataConnection connection) throws SMLException
    {
        try
        {
            int outputIndex = getSignalIndex(outputData, outputName);
            DataComponent output = outputData.getComponent(outputIndex);
            DataComponent src = SWEHelper.findComponentByPath(output, dataPath);
            connection.setSourceComponent(src);
            connection.setSourceProcess(this);
            outputConnections.get(outputIndex).add(connection);
        }
        catch (CDMException e)
        {
            throw new SMLException("Unable to connect signal to output '" + outputName + "'", e);
        }
    }


    @Override
    public void connectParameter(String paramName, String dataPath, DataConnection connection) throws SMLException
    {
        try
        {
            int paramIndex = getSignalIndex(paramData, paramName);
            DataComponent param = paramData.getComponent(paramIndex);
            DataComponent dest = SWEHelper.findComponentByPath(param, dataPath);
            connection.setDestinationComponent(dest);
            connection.setDestinationProcess(this);
            paramConnections.get(paramIndex).add(connection);
        }
        catch (CDMException e)
        {
            throw new SMLException("Unable to connect signal to parameter '" + paramName + "'", e);
        }
    }


    @Override
    public boolean isInputConnected(String inputName)
    {
        int inputIndex = getSignalIndex(inputData, inputName);
        if (inputIndex < 0)
            return false;
        return !inputConnections.get(inputIndex).isEmpty();
    }


    @Override
    public boolean isOutputConnected(String outputName)
    {
        int outputIndex = getSignalIndex(outputData, outputName);
        if (outputIndex < 0)
            return false;
        return !outputConnections.get(outputIndex).isEmpty();
    }


    @Override
    public boolean isParamConnected(String paramName)
    {
        int paramIndex = getSignalIndex(paramData, paramName);
        if (paramIndex < 0)
            return false;
        return !paramConnections.get(paramIndex).isEmpty();
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
    public List<DataConnectionList> getInputConnections()
    {
        return inputConnections;
    }


    @Override
    public List<DataConnectionList> getParamConnections()
    {
        return outputConnections;
    }


    @Override
    public List<DataConnectionList> getOutputConnections()
    {
        return paramConnections;
    }


    protected int getSignalIndex(OgcPropertyList<?> ioList, String signalName)
    {
        for (int i = 0; i < ioList.size(); i++)
        {
            if (signalName.equals(ioList.getProperty(i).getName()))
                return i;
        }

        return -1;
    }


    @Override
    public boolean isUsingQueueBuffers()
    {
        return usingQueueBuffers;
    }


    @Override
    public void setUsingQueueBuffers(boolean usingQueueBuffers)
    {
        this.usingQueueBuffers = usingQueueBuffers;
    }


    @Override
    public boolean needSync()
    {
        return needSync;
    }


    @Override
    protected void finalize() throws Throwable
    {
        this.dispose();
        super.finalize();
    }
}

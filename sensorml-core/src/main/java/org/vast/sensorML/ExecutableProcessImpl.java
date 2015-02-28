
package org.vast.sensorML;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vast.cdm.common.CDMException;
import org.vast.data.AbstractDataComponentImpl;
import org.vast.data.DataComponentHelper;
import org.vast.process.DataConnectionList;
import org.vast.process.DataConnection;
import org.vast.process.DataQueue;
import org.vast.process.IProcessExec;
import org.vast.process.SMLProcessException;
import org.vast.util.ExceptionSystem;
import net.opengis.OgcPropertyList;
import net.opengis.sensorml.v20.AbstractProcess;
import net.opengis.sensorml.v20.IOPropertyList;
import net.opengis.swe.v20.DataBlock;
import net.opengis.swe.v20.DataComponent;


/**
 * <p>
 * Abstract base for all executable process implementations.<br/>
 * This class provides logic to run a process synchronously or in its
 * own thread using {@link #start()} and {@link #stop()}.
 * </p>
 *
 * @author Alex Robin <alex.robin@sensiasoftware.com>
 * @since Feb 28, 2015
 */
public abstract class ExecutableProcessImpl implements IProcessExec, Runnable
{
    static final long serialVersionUID = 1L;
    private final static Logger LOGGER = LoggerFactory.getLogger(ExecutableProcessImpl.class.getName());

    protected static final String ioError = "Invalid I/O Structure";
    protected static final String initError = "Error while initializing process ";
    protected static final String execError = "Error while executing process ";

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


    protected void assignWrapperProcess(AbstractProcess wrapperProcess) throws SMLProcessException
    {
        this.wrapperProcess = wrapperProcess;
        
        // prepare inputs
        this.inputData = wrapperProcess.getInputList();
        int numInputs = wrapperProcess.getNumInputs();
        inputConnections = new ArrayList<DataConnectionList>(numInputs);
        for (int i = 0; i < numInputs; i++)
            inputConnections.add(new DataConnectionList());
        
        // prepare outputs
        this.outputData = wrapperProcess.getOutputList();
        int numOutputs = wrapperProcess.getNumOutputs();
        outputConnections = new ArrayList<DataConnectionList>(numOutputs);
        for (int i = 0; i < numOutputs; i++)
            outputConnections.add(new DataConnectionList());
        
        // prepare parameters
        this.paramData = wrapperProcess.getParameterList();
        int numParams = wrapperProcess.getNumParameters();
        paramConnections = new ArrayList<DataConnectionList>(numParams);
        for (int i = 0; i < numParams; i++)
            paramConnections.add(new DataConnectionList());
    }


    @Override
    public abstract void init() throws SMLProcessException;


    @Override
    public abstract void execute() throws SMLProcessException;
    
    
    @Override
    public void dispose()
    {
    }
 

    @Override
    public void reset() throws SMLProcessException
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
            catch (SMLProcessException e)
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


    @Override
    public synchronized void start() throws SMLProcessException
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
    public void connectInput(String inputName, String dataPath, DataConnection connection) throws SMLProcessException
    {
        try
        {
            int inputIndex = getSignalIndex(inputData, inputName);
            DataComponent input = inputData.getComponent(inputIndex);
            DataComponent dest = DataComponentHelper.findComponentByPath(dataPath, input);
            connection.setDestinationComponent(dest);
            connection.setDestinationProcess(this);
            inputConnections.get(inputIndex).add(connection);
        }
        catch (CDMException e)
        {
            throw new SMLProcessException("Unable to connect signal to input '" + inputName + "'", e);
        }
    }


    @Override
    public void connectOutput(String outputName, String dataPath, DataConnection connection) throws SMLProcessException
    {
        try
        {
            int outputIndex = getSignalIndex(outputData, outputName);
            DataComponent output = outputData.getComponent(outputIndex);
            DataComponent src = DataComponentHelper.findComponentByPath(dataPath, output);
            connection.setSourceComponent(src);
            connection.setSourceProcess(this);
            outputConnections.get(outputIndex).add(connection);
        }
        catch (CDMException e)
        {
            throw new SMLProcessException("Unable to connect signal to output '" + outputName + "'", e);
        }
    }


    @Override
    public void connectParameter(String paramName, String dataPath, DataConnection connection) throws SMLProcessException
    {
        try
        {
            int paramIndex = getSignalIndex(paramData, paramName);
            DataComponent param = paramData.getComponent(paramIndex);
            DataComponent dest = DataComponentHelper.findComponentByPath(dataPath, param);
            connection.setDestinationComponent(dest);
            connection.setDestinationProcess(this);
            paramConnections.get(paramIndex).add(connection);
        }
        catch (CDMException e)
        {
            throw new SMLProcessException("Unable to connect signal to parameter '" + paramName + "'", e);
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
        super.finalize();
        this.dispose();
    }
}


package org.vast.sensorML;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.vast.cdm.common.CDMException;
import org.vast.data.AbstractDataComponentImpl;
import org.vast.data.DataComponentHelper;
import org.vast.process.DataConnectionList;
import org.vast.process.DataConnection;
import org.vast.process.DataQueue;
import org.vast.process.IProcessExec;
import org.vast.process.ProcessException;
import org.vast.util.ExceptionSystem;
import net.opengis.OgcPropertyList;
import net.opengis.gml.v32.Reference;
import net.opengis.sensorml.v20.AbstractModes;
import net.opengis.sensorml.v20.AbstractProcess;
import net.opengis.sensorml.v20.AbstractSettings;
import net.opengis.sensorml.v20.DataInterface;
import net.opengis.sensorml.v20.FeatureList;
import net.opengis.sensorml.v20.ObservableProperty;
import net.opengis.sensorml.v20.impl.DescribedObjectImpl;
import net.opengis.sensorml.v20.impl.FeatureListImpl;
import net.opengis.swe.v20.AbstractSWEIdentifiable;
import net.opengis.swe.v20.DataBlock;
import net.opengis.swe.v20.DataComponent;


/**
 * POJO class for XML type AbstractProcessType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public abstract class AbstractProcessImpl extends DescribedObjectImpl implements AbstractProcess, IProcessExec, Runnable
{
    static final long serialVersionUID = 1L;
    private final static Logger LOGGER = Logger.getLogger(AbstractProcessImpl.class.getName());

    protected static final String ioError = "Invalid I/O Structure";
    protected static final String initError = "Error while initializing process ";
    protected static final String execError = "Error while executing process ";

    protected Reference typeOf;
    protected AbstractSettings configuration;
    protected FeatureList featuresOfInterest;
    protected IOPropertyList inputData = new IOPropertyList();
    protected IOPropertyList outputData = new IOPropertyList();
    protected IOPropertyList paramData = new IOPropertyList();
    protected List<AbstractModes> modesList = new ArrayList<AbstractModes>();
    protected String definition;

    //////////////////////////////////
    // variables used for execution //
    //////////////////////////////////
    protected transient List<DataConnectionList> inputConnections = null;
    protected transient List<DataConnectionList> outputConnections = null;
    protected transient List<DataConnectionList> paramConnections = null;
    protected transient Thread processThread = null; // internal process thread
    protected transient boolean started = false;
    protected transient boolean usingQueueBuffers = false;
    protected transient boolean needSync = false;


    public AbstractProcessImpl()
    {
    }


    /* (non-Javadoc)
     * @see org.vast.process.IProcess#init()
     */
    @Override
    public abstract void init() throws ProcessException;


    /* (non-Javadoc)
     * @see org.vast.process.IProcess#reset()
     */
    @Override
    public void reset() throws ProcessException
    {
        // DO NOTHING BY DEFAULT
    }


    /* (non-Javadoc)
     * @see org.vast.process.IProcess#execute()
     */
    @Override
    public abstract void execute() throws ProcessException;


    /* (non-Javadoc)
     * @see org.vast.process.IProcess#dispose()
     */
    @Override
    public void dispose()
    {
    }


    /* (non-Javadoc)
     * @see org.vast.process.IProcess#canRun()
     */
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


    /**
     * Transfers input data in sync mode (not threaded)
     */
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


    /* (non-Javadoc)
     * @see org.vast.process.IProcess#createNewOutputBlocks()
     */
    @Override
    public void createNewOutputBlocks()
    {
        for (int i = 0; i < getNumOutputs(); i++)
        {
            DataComponent comp = outputData.getComponent(i);
            comp.renewDataBlock();
        }
    }


    /* (non-Javadoc)
     * @see org.vast.process.IProcess#createNewInputBlocks()
     */
    @Override
    public void createNewInputBlocks()
    {
        for (int i = 0; i < getNumInputs(); i++)
        {
            DataComponent comp = inputData.getComponent(i);
            comp.renewDataBlock();
        }
    }


    /* (non-Javadoc)
     * @see org.vast.process.IProcess#run()
     */
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


    /* (non-Javadoc)
     * @see org.vast.process.IProcess#start()
     */
    @Override
    public synchronized void start() throws ProcessException
    {
        if (!started)
        {
            if (LOGGER.isLoggable(Level.FINE))
            {
                String processClass = getClass().getName();
                processClass = processClass.substring(processClass.lastIndexOf('.') + 1);
                LOGGER.fine("Process " + id + " (" + processClass + ") Thread started");
            }

            this.init();
            started = true;
            usingQueueBuffers = true;
            processThread = new Thread(this);
            processThread.setName(this.id);
            processThread.start();
        }
    }


    /* (non-Javadoc)
     * @see org.vast.process.IProcess#stop()
     */
    @Override
    public synchronized void stop()
    {
        if (started)
        {
            if (LOGGER.isLoggable(Level.FINE))
            {
                String processClass = getClass().getName();
                processClass = processClass.substring(processClass.lastIndexOf('.') + 1);
                LOGGER.fine("Process " + id + " (" + processClass + ") Thread stopped");
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
        text.append(id);
        text.append(" (" + this.getClass().getName() + ")\n");

        text.append("\n  Inputs:\n");
        for (int i = 0; i < getNumInputs(); i++)
        {
            text.append(indent);
            text.append(((AbstractDataComponentImpl)inputData.getComponent(i)).toString(indent));
        }

        text.append("\n  Outputs:\n");
        for (int i = 0; i < getNumOutputs(); i++)
        {
            text.append(indent);
            text.append(((AbstractDataComponentImpl)outputData.getComponent(i)).toString(indent));
        }

        text.append("\n  Parameters:\n");
        for (int i = 0; i < getNumParameters(); i++)
        {
            text.append(indent);
            text.append(((AbstractDataComponentImpl)paramData.getComponent(i)).toString(indent));
        }

        return text.toString();
    }


    @Override
    public void connectInput(String inputName, String dataPath, DataConnection connection) throws ProcessException
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
            throw new ProcessException("Unable to connect signal to input '" + inputName + "'", e);
        }
    }


    @Override
    public void connectOutput(String outputName, String dataPath, DataConnection connection) throws ProcessException
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
            throw new ProcessException("Unable to connect signal to output '" + outputName + "'", e);
        }
    }


    @Override
    public void connectParameter(String paramName, String dataPath, DataConnection connection) throws ProcessException
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
            throw new ProcessException("Unable to connect signal to parameter '" + paramName + "'", e);
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


    /* ************************************ */
    /*  Auto-generated Getters and Setters  */
    /* ************************************ */

    /**
     * Gets the typeOf property
     */
    @Override
    public Reference getTypeOf()
    {
        return typeOf;
    }


    /**
     * Checks if typeOf is set
     */
    @Override
    public boolean isSetTypeOf()
    {
        return (typeOf != null);
    }


    /**
     * Sets the typeOf property
     */
    @Override
    public void setTypeOf(Reference typeOf)
    {
        this.typeOf = typeOf;
    }


    /**
     * Gets the configuration property
     */
    @Override
    public AbstractSettings getConfiguration()
    {
        return configuration;
    }


    /**
     * Checks if configuration is set
     */
    @Override
    public boolean isSetConfiguration()
    {
        return (configuration != null);
    }


    /**
     * Sets the configuration property
     */
    @Override
    public void setConfiguration(AbstractSettings configuration)
    {
        this.configuration = configuration;
    }


    /**
     * Gets the featuresOfInterest property
     */
    @Override
    public FeatureList getFeaturesOfInterest()
    {
        if (featuresOfInterest == null)
            featuresOfInterest = new FeatureListImpl();
        return featuresOfInterest;
    }


    /**
     * Checks if featuresOfInterest is set
     */
    @Override
    public boolean isSetFeaturesOfInterest()
    {
        return (featuresOfInterest != null);
    }


    /**
     * Sets the featuresOfInterest property
     */
    @Override
    public void setFeaturesOfInterest(FeatureList featuresOfInterest)
    {
        this.featuresOfInterest = featuresOfInterest;
    }


    @Override
    public OgcPropertyList<AbstractSWEIdentifiable> getInputList()
    {
        return inputData;
    }


    @Override
    public int getNumInputs()
    {
        return inputData.size();
    }


    @Override
    public AbstractSWEIdentifiable getInput(String name)
    {
        return inputData.get(name);
    }


    @Override
    public void addInput(String name, DataComponent input)
    {
        inputData.add(name, input);
    }


    @Override
    public void addInput(String name, ObservableProperty input)
    {
        inputData.add(name, input);
    }


    @Override
    public void addInput(String name, DataInterface input)
    {
        inputData.add(name, input);
    }
    
    
    @Override
    public OgcPropertyList<AbstractSWEIdentifiable> getOutputList()
    {
        return outputData;
    }


    @Override
    public int getNumOutputs()
    {
        return outputData.size();
    }


    @Override
    public AbstractSWEIdentifiable getOutput(String name)
    {
        return outputData.get(name);
    }


    @Override
    public void addOutput(String name, DataComponent output)
    {
        outputData.add(name, output);
    }


    @Override
    public void addOutput(String name, ObservableProperty output)
    {
        outputData.add(name, output);
    }


    @Override
    public void addOutput(String name, DataInterface output)
    {
        outputData.add(name, output);
    }


    @Override
    public int getNumParameters()
    {
        return paramData.size();
    }


    @Override
    public AbstractSWEIdentifiable getParameter(String name)
    {
        return paramData.get(name);
    }


    @Override
    public void addParameter(String name, DataComponent parameter)
    {
        paramData.add(name, parameter);
    }


    @Override
    public void addParameter(String name, ObservableProperty parameter)
    {
        paramData.add(name, parameter);
    }


    @Override
    public void addParameter(String name, DataInterface parameter)
    {
        paramData.add(name, parameter);
    }


    @Override
    public OgcPropertyList<AbstractSWEIdentifiable> getParameterList()
    {
        return paramData;
    }


    /**
     * Gets the list of modes properties
     */
    @Override
    public List<AbstractModes> getModesList()
    {
        return modesList;
    }


    /**
     * Returns number of modes properties
     */
    @Override
    public int getNumModes()
    {
        if (modesList == null)
            return 0;
        return modesList.size();
    }


    /**
     * Adds a new modes property
     */
    @Override
    public void addModes(AbstractModes modes)
    {
        this.modesList.add(modes);
    }


    /**
     * Gets the definition property
     */
    @Override
    public String getDefinition()
    {
        return definition;
    }


    /**
     * Checks if definition is set
     */
    @Override
    public boolean isSetDefinition()
    {
        return (definition != null);
    }


    /**
     * Sets the definition property
     */
    @Override
    public void setDefinition(String definition)
    {
        this.definition = definition;
    }
}

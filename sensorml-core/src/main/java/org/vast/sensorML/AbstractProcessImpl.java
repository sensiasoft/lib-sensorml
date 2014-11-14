package org.vast.sensorML;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.vast.cdm.common.CDMException;
import org.vast.data.AbstractDataComponentImpl;
import org.vast.data.DataSelector;
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
import net.opengis.sensorml.v20.FeatureList;
import net.opengis.sensorml.v20.InputList;
import net.opengis.sensorml.v20.OutputList;
import net.opengis.sensorml.v20.ParameterList;
import net.opengis.sensorml.v20.impl.DescribedObjectImpl;
import net.opengis.sensorml.v20.impl.FeatureListImpl;
import net.opengis.sensorml.v20.impl.InputListImpl;
import net.opengis.sensorml.v20.impl.OutputListImpl;
import net.opengis.sensorml.v20.impl.ParameterListImpl;
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
    protected InputListImpl inputs;
    protected OutputListImpl outputs;
    protected ParameterListImpl parameters;
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
        for (int i=0; i<allConnections.size(); i++)
        {
            DataConnectionList connectionList = allConnections.get(i);
            
            if (connectionList.isNeeded())
            {
                // loop through all connections in each list
                for (int j=0; j<connectionList.size(); j++)
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
        for (int j=0; j<connectionList.size(); j++)
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
        for (int i=0; i<allConnections.size(); i++)
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
        for (int j=0; j<connectionList.size(); j++)
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
        for (int i=0; i<allConnections.size(); i++)
        {
            DataConnectionList connectionList = allConnections.get(i);
            
            if (connectionList.isNeeded())
            {
                // loop through all connections
                for (int j=0; j<connectionList.size(); j++)
                {
                    // get datablock from queue and assign it to destination component
                    DataQueue dataQueue = (DataQueue)connectionList.get(j);
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
        for (int i=0; i<allConnections.size(); i++)
        {
            DataConnectionList connectionList = allConnections.get(i);
            
            if (connectionList.isNeeded())
            {
                // loop through all connections
                for (int j=0; j<connectionList.size(); j++)
                {
                    // get datablock from source component and add to queue
                    DataQueue dataQueue = (DataQueue)connectionList.get(j);
                    DataComponent dataComponent = dataQueue.getSourceComponent();
                    DataBlock block = dataComponent.getData();
                    dataQueue.add(block);
                }
                
                // renew output dataBlock
                DataComponent comp = (DataComponent)inputs.getInputList().get(i);
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
        for (int i=0; i<allConnections.size(); i++)
        {
            DataConnectionList connectionList = allConnections.get(i);
            
            if (connectionList.isNeeded())
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
   
    
    /* (non-Javadoc)
     * @see org.vast.process.IProcess#createNewOutputBlocks()
     */
    @Override
    public void createNewOutputBlocks()
    {
        int outputCount = outputs.getNumOutputs();
        for (int i=0; i<outputCount; i++)
        {
            DataComponent comp = (DataComponent)outputs.getOutputList().get(i);
            comp.renewDataBlock();
        }
    }
    
    
    /* (non-Javadoc)
     * @see org.vast.process.IProcess#createNewInputBlocks()
     */
    @Override
    public void createNewInputBlocks()
    {
        int inputCount = inputs.getNumInputs();
        for (int i=0; i<inputCount; i++)
        {
            DataComponent comp = (DataComponent)inputs.getInputList().get(i);
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
                processClass = processClass.substring(processClass.lastIndexOf('.')+1);
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
                processClass = processClass.substring(processClass.lastIndexOf('.')+1);
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
        for (int i=0; i<inputs.getNumInputs(); i++)
        {
            text.append(indent);
            text.append(((AbstractDataComponentImpl)getInputComponents().get(i)).toString(indent));
        }
        
        text.append("\n  Outputs:\n");
        for (int i=0; i<outputs.getNumOutputs(); i++)
        {
            text.append(indent);
            text.append(((AbstractDataComponentImpl)getOutputComponents().get(i)).toString(indent));
        }
        
        text.append("\n  Parameters:\n");
        for (int i=0; i<parameters.getNumParameters(); i++)
        {
            text.append(indent);
            text.append(((AbstractDataComponentImpl)getParameterComponents().get(i)).toString(indent));
        }

        return text.toString();
    }


    @Override
    public void connectInput(String inputName, String dataPath, DataConnection connection) throws ProcessException
    {
        try
        {
            int inputIndex = getSignalIndex(inputs.getInputList(), inputName);
            DataComponent input = (DataComponent)inputs.getInputList().get(inputIndex);
            DataComponent dest = new DataSelector().findComponent(input, dataPath);
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
            int outputIndex = getSignalIndex(outputs.getOutputList(), outputName);
            DataComponent output = (DataComponent)outputs.getOutputList().get(outputIndex);
            DataComponent src = new DataSelector().findComponent(output, dataPath);
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
            int paramIndex = getSignalIndex(parameters.getParameterList(), paramName);
            DataComponent param = (DataComponent)parameters.getParameterList().get(paramIndex);
            DataComponent dest = new DataSelector().findComponent(param, dataPath);
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
        int inputIndex = getSignalIndex(inputs.getInputList(), inputName);
        if (inputIndex < 0)
            return false;
        return !inputConnections.get(inputIndex).isEmpty();
    }


    @Override
    public boolean isOutputConnected(String outputName)
    {
        int outputIndex = getSignalIndex(outputs.getOutputList(), outputName);        
        if (outputIndex < 0)
            return false;
        return !outputConnections.get(outputIndex).isEmpty();
    }


    @Override
    public boolean isParamConnected(String paramName)
    {
        int paramIndex = getSignalIndex(parameters.getParameterList(), paramName);
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
        for (int i=0; i<ioList.size(); i++)
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
    
    
    public List<AbstractSWEIdentifiable> getInputComponents()
    {
        return inputs.getInputList();
    }
    
    
    public List<AbstractSWEIdentifiable> getParameterComponents()
    {
        return parameters.getParameterList();
    }
    
    
    public List<AbstractSWEIdentifiable> getOutputComponents()
    {
        return outputs.getOutputList();
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
    
    
    /**
     * Gets the inputs property
     */
    @Override
    public InputList getInputs()
    {
        if (inputs == null)
            inputs = new InputListImpl();
        return inputs;
    }
    
    
    /**
     * Checks if inputs is set
     */
    @Override
    public boolean isSetInputs()
    {
        return (inputs != null);
    }
    
    
    /**
     * Sets the inputs property
     */
    @Override
    public void setInputs(InputList inputs)
    {
        this.inputs = (InputListImpl)inputs;
    }
    
    
    /**
     * Gets the outputs property
     */
    @Override
    public OutputList getOutputs()
    {
        if (outputs == null)
            outputs = new OutputListImpl();
        return outputs;
    }
    
    
    /**
     * Checks if outputs is set
     */
    @Override
    public boolean isSetOutputs()
    {
        return (outputs != null);
    }
    
    
    /**
     * Sets the outputs property
     */
    @Override
    public void setOutputs(OutputList outputs)
    {
        this.outputs = (OutputListImpl)outputs;
    }
    
    
    /**
     * Gets the parameters property
     */
    @Override
    public ParameterList getParameters()
    {
        if (parameters == null)
            parameters = new ParameterListImpl();
        return parameters;
    }
    
    
    /**
     * Checks if parameters is set
     */
    @Override
    public boolean isSetParameters()
    {
        return (parameters != null);
    }
    
    
    /**
     * Sets the parameters property
     */
    @Override
    public void setParameters(ParameterList parameters)
    {
        this.parameters = (ParameterListImpl)parameters;
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

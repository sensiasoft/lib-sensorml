package org.vast.sensorML;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.vast.cdm.common.CDMException;
import org.vast.data.DataComponentHelper;
import org.vast.data.DataRecordImpl;
import org.vast.process.DataConnectionList;
import org.vast.process.DataConnection;
import org.vast.process.IProcessChainExec;
import org.vast.process.IProcessExec;
import org.vast.process.ProcessException;
import net.opengis.OgcProperty;
import net.opengis.OgcPropertyImpl;
import net.opengis.sensorml.v20.AggregateProcess;
import net.opengis.sensorml.v20.ComponentList;
import net.opengis.sensorml.v20.ConnectionList;
import net.opengis.sensorml.v20.impl.ComponentListImpl;
import net.opengis.sensorml.v20.impl.ConnectionListImpl;
import net.opengis.swe.v20.AbstractSWEIdentifiable;
import net.opengis.swe.v20.DataComponent;


/**
 * POJO class for XML type AggregateProcessType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public class AggregateProcessImpl extends AbstractProcessImpl implements AggregateProcess, IProcessChainExec, Serializable
{
    private static final long serialVersionUID = -4025996239623692585L;
    private final static Logger LOGGER = Logger.getLogger(AggregateProcessImpl.class.getName());
    
    protected OgcProperty<ComponentList> components;
    protected OgcProperty<ConnectionList> connections;
    
    //////////////////////////////////
    // variables used for execution //
    //////////////////////////////////
    protected transient Hashtable<String, IProcessExec> processTable;
    protected transient List<IProcessExec> processList;
    protected transient List<IProcessExec> processExecList;
    protected transient boolean childrenThreadsOn = false;
    
    // internal in/out/param data queues: component name -> queue
    protected transient List<DataConnectionList> internalInputConnections = null;
    protected transient List<DataConnectionList> internalOutputConnections = null;
    protected transient List<DataConnectionList> internalParamConnections = null;
    
    // list of all interconnections within the chain (helper)
    protected transient List<DataConnection> internalConnections = null;
    
    
    public AggregateProcessImpl()
    {
    }
    
    
    @Override
    public void init() throws ProcessException
    {        
        // TODO create real inter-process connections if not already done
        // TODO create internal i/o connections if not already done
        
        IProcessExec childProcess = null;        
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

                reset();
            }
            else
            {
                // init all child processes and create output data blocks
                for (int i=0; i<processList.size(); i++)
                {
                    childProcess = processList.get(i);
                    childProcess.init();
                    childProcess.reset();
                    childProcess.createNewOutputBlocks();
                }
            }
        }
        catch (Exception e)
        {
            String errMsg = initError + childProcess.getId() + " (" + childProcess.getClass().getCanonicalName() + ")";
            LOGGER.log(Level.FINE, errMsg, e);
            throw new ProcessException(errMsg, e);
        }
    }
    
    
    @Override
    public void reset() throws ProcessException
    {
        // reset all sub-processes
        for (int i=0; i<processList.size(); i++)
            processList.get(i).reset();
        
        // clear all internal connections
        for (int i=0; i<internalConnections.size(); i++)
            internalConnections.get(i).setDataAvailable(false);
    }
    
    
    @Override
    public void dispose()
    {
        if (processList != null)
        {
            for (int i=0; i<processList.size(); i++)
                processList.get(i).dispose();
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
            processExecList.remove(processAfter);
            processExecList.add(before, processAfter);
        }
    }
    
    
    @Override
    public void execute() throws ProcessException
    {
        IProcessExec childProcess = null;
        
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
                            for (int i=0; i<processExecList.size(); i++)
                            {
                                childProcess = processExecList.get(i);
                                
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
                        for (int i=0; i<processExecList.size(); i++)
                        {
                            childProcess = processExecList.get(i);
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
        catch (ProcessException e)
        {
            String errMsg = execError + childProcess.getId() + " (" + childProcess.getClass().getCanonicalName() + ")";
            LOGGER.log(Level.FINE, errMsg, e);
            throw new ProcessException(errMsg, e);
        }
        catch (Exception e)
        {
            e.printStackTrace();
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
        for (AbstractSWEIdentifiable input: inputs)
        {
            if (input instanceof DataRecordImpl)
                ((DataRecordImpl)input).combineDataBlocks();
        }
    }
    
    
    public void combineOutputBlocks()
    {
        // make sure sub processes connected to the output
        // also uses these new DataBlocks for their output
        for (AbstractSWEIdentifiable output: outputs)
        {
            if (output instanceof DataRecordImpl)
                ((DataRecordImpl)output).combineDataBlocks();
        }
    }
    
 
    @Override
    public void connectInternalInput(String inputName, String dataPath, DataConnection connection) throws ProcessException
    {
        try
        {
            int inputIndex = getSignalIndex(inputs, inputName);
            DataComponent input = (DataComponent)inputs.get(inputIndex);
            DataComponent src = DataComponentHelper.findComponentByPath(dataPath, input);
            connection.setSourceComponent(src);
            connection.setSourceProcess(this);
            internalInputConnections.get(inputIndex).add(connection);
        }
        catch (CDMException e)
        {
            throw new ProcessException("Unable to connect internal signal to input '" + inputName + "'", e);
        }
    }
    
    
    @Override
    public void connectInternalOutput(String outputName, String dataPath, DataConnection connection) throws ProcessException
    {
        try
        {
            int outputIndex = getSignalIndex(outputs, outputName);
            DataComponent output = (DataComponent)outputs.get(outputIndex);
            DataComponent dest = DataComponentHelper.findComponentByPath(dataPath, output);
            connection.setDestinationComponent(dest);
            connection.setDestinationProcess(this);
            internalOutputConnections.get(outputIndex).add(connection);
        }
        catch (CDMException e)
        {
            throw new ProcessException("Unable to connect internal signal to output '" + outputName + "'", e);
        } 
    }
    
    
    @Override
    public void connectInternalParam(String paramName, String dataPath, DataConnection connection) throws ProcessException
    {
        try
        {
            int paramIndex = getSignalIndex(parameters, paramName);
            DataComponent param = (DataComponent)parameters.get(paramName);
            DataComponent src = DataComponentHelper.findComponentByPath(dataPath, param);
            connection.setSourceComponent(src);
            connection.setSourceProcess(this);
            internalParamConnections.get(paramIndex).add(connection);
        }
        catch (CDMException e)
        {
            throw new ProcessException("Unable to connect internal signal to parameter '" + paramName + "'", e);
        }
    }


    @Override
    public synchronized void start() throws ProcessException
    {
        super.start();
        
        // start all child processes
        if (childrenThreadsOn)
        {
            Enumeration<IProcessExec> childProcesses = processTable.elements();
            while (childProcesses.hasMoreElements())
            {
                IProcessExec process = (IProcessExec)childProcesses.nextElement();
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
            Enumeration<IProcessExec> childProcesses = processTable.elements();
            while (childProcesses.hasMoreElements())
            {
                IProcessExec process = (IProcessExec)childProcesses.nextElement();
                process.stop();
            }
        }
        
        super.stop();
    }
   
    
    public String toString()
    {
        StringBuffer text = new StringBuffer(super.toString());
        
        text.append("\n  Child Processes:\n");
        for (int i=0; i<getComponents().getNumComponents(); i++)
        {
            text.append("    " + getComponents().getComponentList().getProperty(i).getName() + "\n");
        }
        
        return text.toString();
    }


    /* (non-Javadoc)
     * @see org.vast.process.(IProcessExec)Chain#isChildrenThreadsOn()
     */
    @Override
    public boolean isChildrenThreadsOn()
    {
        return childrenThreadsOn;
    }


    /* (non-Javadoc)
     * @see org.vast.process.(IProcessExec)Chain#setChildrenThreadsOn(boolean)
     */
    @Override
    public void setChildrenThreadsOn(boolean childrenThreadsOn)
    {
        this.childrenThreadsOn = childrenThreadsOn;
    }


    /* (non-Javadoc)
     * @see org.vast.process.(IProcessExec)Chain#getInternalConnections()
     */
    @Override
    public List<DataConnection> getInternalConnections()
    {
        return internalConnections;
    }
    
    
    public void setOutputNeeded(int outputIndex, boolean needed)
    {
        internalOutputConnections.get(outputIndex).setNeeded(needed);
    }
    
    
    /* ************************************ */
    /*  Auto-generated Getters and Setters  */    
    /* ************************************ */    
    
    /**
     * Gets the components property
     */
    @Override
    public ComponentList getComponents()
    {
        if (components == null)
            components = new OgcPropertyImpl<ComponentList>(new ComponentListImpl());
        return components.getValue();
    }
    
    
    /**
     * Gets extra info (name, xlink, etc.) carried by the components property
     */
    @Override
    public OgcProperty<ComponentList> getComponentsProperty()
    {
        if (components == null)
            components = new OgcPropertyImpl<ComponentList>();
        return components;
    }
    
    
    /**
     * Checks if components is set
     */
    @Override
    public boolean isSetComponents()
    {
        return (components != null && (components.hasValue() || components.hasHref()));
    }
    
    
    /**
     * Sets the components property
     */
    @Override
    public void setComponents(ComponentList components)
    {
        if (this.components == null)
            this.components = new OgcPropertyImpl<ComponentList>();
        this.components.setValue(components);
    }
    
    
    /**
     * Gets the connections property
     */
    @Override
    public ConnectionList getConnections()
    {
        if (connections == null)
            connections = new OgcPropertyImpl<ConnectionList>(new ConnectionListImpl());
        return connections.getValue();
    }
    
    
    /**
     * Gets extra info (name, xlink, etc.) carried by the connections property
     */
    @Override
    public OgcProperty<ConnectionList> getConnectionsProperty()
    {
        if (connections == null)
            connections = new OgcPropertyImpl<ConnectionList>();
        return connections;
    }
    
    
    /**
     * Checks if connections is set
     */
    @Override
    public boolean isSetConnections()
    {
        return (connections != null && (connections.hasValue() || connections.hasHref()));
    }
    
    
    /**
     * Sets the connections property
     */
    @Override
    public void setConnections(ConnectionList connections)
    {
        if (this.connections == null)
            this.connections = new OgcPropertyImpl<ConnectionList>();
        this.connections.setValue(connections);
    }


    @Override
    public void addProcess(String name, IProcessExec process)
    {
        // TODO Auto-generated method stub
        
    }


    @Override
    public void removeProcess(String name)
    {
        // TODO Auto-generated method stub
        
    }


    @Override
    public IProcessExec getProcess(String name)
    {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public List<IProcessExec> getProcessList()
    {
        // TODO Auto-generated method stub
        return null;
    }
}

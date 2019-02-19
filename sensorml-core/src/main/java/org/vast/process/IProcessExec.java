/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2013 Sensia Software LLC.
 All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.process;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import org.slf4j.Logger;
import net.opengis.sensorml.v20.IOPropertyList;
import net.opengis.swe.v20.DataComponent;


public interface IProcessExec
{

    /**
     * @return all process information (name, UID, etc.)
     */
    public ProcessInfo getProcessInfo();
    
    
    /**
     * Sets the name of this process instance
     * @param name
     */
    public void setInstanceName(String name);
    
    
    /**
     * @return name of this process instance
     */
    public String getInstanceName();
    
    
    /**
     * Initialize the process and its internal variables (fixed parameters).
     * This is called only once before the process is executed.
     * @throws ProcessException
     */
    public void init() throws ProcessException;


    /**
     * Check that all needed connections are ready for the process to run
     * @return true if so and false if at least one connection is not ready.
     */
    public boolean canRun();

    
    /**
     * Runs the process which includes fetching input data, calling execute
     * and producing output data
     */
    public void run() throws ProcessException;


    /**
     * Execute contains the logic to transform input/parameter values into 
     * output values. This method should be optimized as much as possible.
     * @throws ProcessException
     */
    public void execute() throws ProcessException;
    
    
    /**
     * Start process in its own thread
     * @throws ProcessException
     */
    public void start() throws ProcessException;
    
    
    /**
     * Start process using the given thread pool
     * @throws ProcessException
     */
    public void start(ExecutorService threadPool) throws ProcessException;


    /**
     * Stop process thread gracefully
     */
    public void stop();
    
    
    /**
     * Override to dispose of all resources allocated
     * for the process (stop threads, OS resources, etc...)
     */
    public void dispose();
    
    
    /**
     * @return List of inputs
     */
    public IOPropertyList getInputList();
    
    
    /**
     * @return List of outputs
     */
    public IOPropertyList getOutputList();
    
    
    /**
     * @return List of parameters
     */
    public IOPropertyList getParameterList();
    
    
    /**
     * @return Read-only list of connections input ports
     */
    public Map<String, DataConnectionList> getInputConnections();
    
    
    /**
     * @return Read-only list of connections output ports
     */
    public Map<String, DataConnectionList> getOutputConnections();
    
    
    /**
     * @return Read-only list of connections parameter ports
     */
    public Map<String, DataConnectionList> getParamConnections();
    
    
    /**
     * Connects one of this process ports with the given connection 
     * @param component input, parameter or output component to connect
     * @param connection connection object to use
     * @throws IllegalArgumentException if component is not part on this process ports
     * @throws ProcessException if connection cannot be validated 
     * (this usually happens because source and destination components are not compatible)
     */
    public void connect(DataComponent component, IDataConnection connection) throws ProcessException;
    
    
    /**
     * Detach connection from this process input, output or parameter
     * @param connection
     * @throws IllegalArgumentException if connection is not attached to any of this
     * process ports
     */
    public void disconnect(IDataConnection connection);


    /**
     * @return True if this process is asynchronous (i.e. outputs are not generated
     * everytime inputs are read) and thus need synchronization with other processes
     * in the chain
     */
    public boolean needSync();
    
    
    /**
     * Notifies the process that one or more parameter values have changed before
     * or during execution
     */
    public void notifyParamChange();
    
    
    /**
     * Set the parent logger for this process.<br/>
     * The parent logger is used to group log events occuring in all processes that are
     * part of the same processing component (e.g. a process chain for instance) while
     * still allowing to differentiate the actual process instance that issued the log.
     * @param log
     */
    public void setParentLogger(Logger log);

}
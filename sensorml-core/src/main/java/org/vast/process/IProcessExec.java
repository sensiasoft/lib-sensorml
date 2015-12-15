/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2013 Sensia Software LLC.
 All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.process;

import java.util.List;
import net.opengis.sensorml.v20.IOPropertyList;


public interface IProcessExec
{

    /**
     * @return process name
     */
    public String getName();
    
    
    /**
     * Initialize the process and its internal variables (fixed parameters).
     * This is called only once before the process is executed.
     * @throws SMLException
     */
    public void init() throws SMLException;


    /**
     * Execute is typically called several times on a process and should
     * contain all the logic to transform input/parameter values to 
     * output values. This method should be optimized as much as possible.
     * @throws SMLException
     */
    public void execute() throws SMLException;

    
    /**
     * Resets the process (especially asnchronous ones) before it can be run again.
     * This method should properly initialize all process state variables
     * @throws SMLException
     */
    public void reset() throws SMLException;
    
    
    /**
     * Override to dispose of all resources allocated
     * for the process (stop threads, OS resources, etc...)
     * Default method does nothing.
     */
    public void dispose();


    /**
     * Check that all needed connections are ready for the process
     * to run in sync mode (not threaded).
     * @return true if so and false if at least one connection is not ready.
     */
    public boolean canRun();


    /**
     * Creates new DataBlock for each output signal
     **/
    public void createNewOutputBlocks();


    /**
     * Creates new DataBlock for each input signal
     **/
    public void createNewInputBlocks();


    /**
     * Process thread run method
     */
    public void run();


    /**
     * Start process thread
     * @throws SMLException
     */
    public void start() throws SMLException;


    /**
     * Stop process thread gracefully
     */
    public void stop();
    
    
    /**
     * @return list of inputs
     */
    public IOPropertyList getInputList();
    
    
    /**
     * @return list of outputs
     */
    public IOPropertyList getOutputList();
    
    
    /**
     * @return list of parameters
     */
    public IOPropertyList getParameterList();
    
    
    /**
     * @return list of input connections to read data from
     */
    public List<DataConnectionList> getInputConnections();
    
    
    /**
     * @return list of parameter connections to read data from
     */
    public List<DataConnectionList> getParamConnections();
    
    
    /**
     * @return list of output connections to write data to
     */
    public List<DataConnectionList> getOutputConnections();


    /**
     * Connects one of this process inputs with the given connection 
     * @param inputName name of input to connect
     * @param dataPath path of component to connect
     * @param connection connection object whose destination will be set to the specified component
     * @throws SMLException
     */
    public void connectInput(String inputName, String dataPath, DataConnection connection) throws SMLException;


    /**
     * Connects one of this process outputs with the given connection 
     * @param outputName name of output to connect
     * @param dataPath path of component to connect
     * @param connection connection object whose source will be set to the specified component
     * @throws SMLException
     */
    public void connectOutput(String outputName, String dataPath, DataConnection connection) throws SMLException;


    /**
     * Connects one of this process parameters with the given connection 
     * @param paramName name of parameter to connect
     * @param dataPath path of component to connect
     * @param connection connection object whose destination will be set to the specified component
     * @throws SMLException
     */
    public void connectParameter(String paramName, String dataPath, DataConnection connection) throws SMLException;


    /**
     * Checks if the specified input has one or more connections
     * @param inputName name of input
     * @return true if at least one connection is made with the input
     */
    public boolean isInputConnected(String inputName);


    /**
     * Checks if the specified output has one or more connections
     * @param outputName name of output
     * @return true if at least one connection is made with the output
     */
    public boolean isOutputConnected(String outputName);


    /**
     * Checks if the specified parameter has one or more connections
     * @param paramName name of parameter
     * @return true if at least one connection is made with the parameter
     */
    public boolean isParamConnected(String paramName);


    /**
     * Checks if actual buffered queues are used to connect with this process
     * @return tru if queues are used
     */
    public boolean isUsingQueueBuffers();


    public void setUsingQueueBuffers(boolean usingQueueBuffers);


    public boolean needSync();
    
    
    public void setAvailability(List<DataConnectionList> allConnections, boolean availability);
    
    
    public void transferData(List<DataConnectionList> allConnections);

}
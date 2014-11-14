/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2013 Sensia Software LLC.
 All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.process;

import java.util.List;


public interface IProcessExec
{

    /**
     * Initialize the process and its internal variables (fixed parameters)
     * This is called only once before the process is executed.
     * @throws ProcessException
     */
    public void init() throws ProcessException;


    /**
     * Resets the process (especially asnchronous ones) before it can be run again
     * Should initialize all process state variables
     * @throws ProcessException
     */
    public void reset() throws ProcessException;


    /**
     * Execute is typically called several times on a process and should
     * contain all the logic to transform input/parameter values to 
     * output values. This method should be optimized as much as possible.
     * @throws ProcessException
     */
    public void execute() throws ProcessException;


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
     * @throws ProcessException
     */
    public void start() throws ProcessException;


    /**
     * Stop process thread gracefully
     */
    public void stop();
    
    
    public String getId();
    
    
    public List<DataConnectionList> getInputConnections();
    
    
    public List<DataConnectionList> getParamConnections();
    
    
    public List<DataConnectionList> getOutputConnections();


    public void connectInput(String inputName, String dataPath, DataConnection connection) throws ProcessException;


    public void connectOutput(String outputName, String dataPath, DataConnection connection) throws ProcessException;


    public void connectParameter(String paramName, String dataPath, DataConnection connection) throws ProcessException;


    public boolean isInputConnected(String inputName);


    public boolean isOutputConnected(String outputName);


    public boolean isParamConnected(String paramName);


    public boolean isUsingQueueBuffers();


    public void setUsingQueueBuffers(boolean usingQueueBuffers);


    public boolean needSync();
    
    
    public void setAvailability(List<DataConnectionList> allConnections, boolean availability);
    
    
    public void transferData(List<DataConnectionList> allConnections);

}
/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2013 Sensia Software LLC.
 All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.process;

import java.util.Collection;
import java.util.Map;
import net.opengis.swe.v20.DataComponent;


public interface IProcessChainExec extends IProcessExec
{

    /**
     * @return Read-only map of processes included in this chain
     */
    public Map<String, IProcessExec> getChildProcesses();
    
    
    /**
     * Adds a child process to this process chain
     * @param name
     * @param process
     * @return the added process
     */
    public IProcessExec addProcess(String name, IProcessExec process);
    
    
    /**
     * Removes a process from the chain and all connections to/from it
     * @param name
     */
    public void removeProcess(String name);
        
    
    /**
     * @return Read-only list of all connections between child processes
     */
    public Collection<IDataConnection> getInternalConnections();
    
    
    /**
     * Create connection between ports of two child processes
     * @param srcProcess
     * @param srcComponent
     * @param destProcess
     * @param destComponent
     * @throws ProcessException
     */
    public IDataConnection connect(IProcessExec srcProcess, DataComponent srcComponent,
                                   IProcessExec destProcess, DataComponent destComponent) throws ProcessException;


    /**
     * Completely remove an internal connection between two child processes
     * or between a chain internal port and a child process
     * @param connection
     */
    public void removeInternalConnection(IDataConnection connection);
    
    
    /**
     * Indicates if data from the specified output is needed or not
     * @param outputName
     * @param needed
     */
    public void setOutputNeeded(String outputName, boolean needed);

}
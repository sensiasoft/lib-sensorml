/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2013 Sensia Software LLC.
 All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.process;

import java.util.List;


public interface IProcessChain extends IProcess
{

    public void connectInternalInput(String dataPath, DataConnection connection) throws ProcessException;


    public void connectInternalOutput(String dataPath, DataConnection connection) throws ProcessException;


    public void connectInternalParam(String dataPath, DataConnection connection) throws ProcessException;


    /**
     * Adds a process in the chain
     * @param name local name of process 
     * @param process DataProcess object to add in the chain
     */
    public abstract void addProcess(String name, IProcess process);


    /**
     * Removes a process from the chain
     * TODO Also removes connections??
     * @param name
     */
    public abstract void removeProcess(String name);


    /**
     * Retrieves a child DataProcess by its name
     * @param name local name of process to retrieve
     * @return DataProcess with given name
     */
    public IProcess getProcess(String name);


    /**
     * Retrieves the whole list of child processes
     * @return list of all DataProcess children
     */
    public List<IProcess> getProcessList();


    public boolean isChildrenThreadsOn();


    public void setChildrenThreadsOn(boolean childrenThreadsOn);


    public List<DataConnection> getInternalConnections();
    
    
    public void setOutputNeeded(int outputIndex, boolean needed);

}
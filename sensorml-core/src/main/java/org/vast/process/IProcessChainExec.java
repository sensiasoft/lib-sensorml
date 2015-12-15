/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2013 Sensia Software LLC.
 All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.process;

import java.util.List;


public interface IProcessChainExec extends IProcessExec
{

    public void connectInternalInput(String inputName, String dataPath, DataConnection connection) throws SMLException;


    public void connectInternalOutput(String outputName, String dataPath, DataConnection connection) throws SMLException;


    public void connectInternalParam(String paramName, String dataPath, DataConnection connection) throws SMLException;


    public boolean isChildrenThreadsOn();


    public void setChildrenThreadsOn(boolean childrenThreadsOn);


    public List<DataConnection> getInternalConnections();
    
    
    public void setOutputNeeded(int outputIndex, boolean needed);

}
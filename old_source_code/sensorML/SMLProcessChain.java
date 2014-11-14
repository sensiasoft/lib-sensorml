/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are subject to the Mozilla Public License Version
 1.1 (the "License"); you may not use this file except in compliance with
 the License. You may obtain a copy of the License at
 http://www.mozilla.org/MPL/MPL-1.1.html
 
 Software distributed under the License is distributed on an "AS IS" basis,
 WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 for the specific language governing rights and limitations under the License.
 
 The Original Code is the "SensorML DataProcessing Engine".
 
 The Initial Developer of the Original Code is the VAST team at the
 University of Alabama in Huntsville (UAH). <http://vast.uah.edu>
 Portions created by the Initial Developer are Copyright (C) 2007
 the Initial Developer. All Rights Reserved.

 Please Contact Alexandre Robin <alex.robin@sensiasoftware.com> for more
 information.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.sensorML;

import java.util.List;
import javax.xml.namespace.QName;
import org.vast.process.DataConnection;
import org.vast.process.IProcess;
import org.vast.process.IProcessChain;
import org.vast.process.ProcessChain;
import org.vast.process.ProcessException;


/**
 * <p>
 * SensorML Process Chain
 * </p>
 *
 * <p>Copyright (c) 2012</p>
 * @author Alexandre Robin
 * @since Sep 8, 2013
 * @version 1.0
 */
public class SMLProcessChain extends SMLProcess implements IProcessChain
{
    private static final long serialVersionUID = -50470827505522315L;   


    public SMLProcessChain()
    {
        this(10);
    }
    
    
    public SMLProcessChain(int memberCount)
    {
        super(new ProcessChain(memberCount));
        this.qname = new QName("ProcessChain");
    }
    
    
    //// Methods delegated to underlying process chain ////
    
    public void connectInternalInput(String dataPath, DataConnection connection) throws ProcessException
    {
        ((ProcessChain)processImpl).connectInternalInput(dataPath, connection);
    }


    public void connectInternalOutput(String dataPath, DataConnection connection) throws ProcessException
    {
        ((ProcessChain)processImpl).connectInternalOutput(dataPath, connection);
    }


    public void connectInternalParam(String dataPath, DataConnection connection) throws ProcessException
    {
        ((ProcessChain)processImpl).connectInternalParam(dataPath, connection);
    }


    public void addProcess(String name, IProcess process)
    {
        ((ProcessChain)processImpl).addProcess(name, process);
    }


    public void removeProcess(String name)
    {
        ((ProcessChain)processImpl).removeProcess(name);
    }


    public IProcess getProcess(String name)
    {
        return ((ProcessChain)processImpl).getProcess(name);
    }


    public List<IProcess> getProcessList()
    {
        return ((ProcessChain)processImpl).getProcessList();
    }


    public boolean isChildrenThreadsOn()
    {
        return ((ProcessChain)processImpl).isChildrenThreadsOn();
    }


    public void setChildrenThreadsOn(boolean childrenThreadsOn)
    {
        ((ProcessChain)processImpl).setChildrenThreadsOn(childrenThreadsOn);
    }


    public List<DataConnection> getInternalConnections()
    {
        return ((ProcessChain)processImpl).getInternalConnections();
    }


    public void setOutputNeeded(int outputIndex, boolean needed)
    {
        ((ProcessChain)processImpl).setOutputNeeded(outputIndex, needed);
    }
}

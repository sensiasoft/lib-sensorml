/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2015 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.sensorML;

import java.util.ArrayList;
import java.util.List;
import org.vast.process.DataConnection;
import org.vast.process.IProcessChainExec;
import org.vast.process.SMLProcessException;
import net.opengis.OgcPropertyList;
import net.opengis.sensorml.v20.AbstractProcess;
import net.opengis.sensorml.v20.AggregateProcess;
import net.opengis.sensorml.v20.Link;


/**
 * POJO class for XML type AggregateProcessType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public class AggregateProcessImpl extends AbstractProcessImpl implements AggregateProcess, IProcessChainExec
{
    protected OgcPropertyList<AbstractProcess> components = new OgcPropertyList<AbstractProcess>(5);
    protected List<Link> connections = new ArrayList<Link>(5);
    
    
    public AggregateProcessImpl()
    {
        this.qName = AggregateProcess.DEFAULT_QNAME;
    }
    
 
    @Override
    public void connectInternalInput(String inputName, String dataPath, DataConnection connection) throws SMLProcessException
    {
        checkExecutable();
        ((IProcessChainExec)executableProcess).connectInternalInput(inputName, dataPath, connection);
    }
    
    
    @Override
    public void connectInternalOutput(String outputName, String dataPath, DataConnection connection) throws SMLProcessException
    {
        checkExecutable();
        ((IProcessChainExec)executableProcess).connectInternalOutput(outputName, dataPath, connection);
    }
    
    
    @Override
    public void connectInternalParam(String paramName, String dataPath, DataConnection connection) throws SMLProcessException
    {
        checkExecutable();
        ((IProcessChainExec)executableProcess).connectInternalParam(paramName, dataPath, connection);
    }


    public String toString()
    {
        StringBuffer text = new StringBuffer(super.toString());
        
        text.append("\n  Child Processes:\n");
        for (int i=0; i<getNumComponents(); i++)
        {
            text.append("    " + getComponentList().getProperty(i).getName() + "\n");
        }
        
        return text.toString();
    }


    @Override
    public boolean isChildrenThreadsOn()
    {
        if (executableProcess != null)
            return ((IProcessChainExec)executableProcess).isChildrenThreadsOn();
        else
            return false;
    }


    @Override
    public void setChildrenThreadsOn(boolean childrenThreadsOn)
    {
        if (executableProcess != null)
            ((IProcessChainExec)executableProcess).setChildrenThreadsOn(childrenThreadsOn);
    }


    @Override
    public List<DataConnection> getInternalConnections()
    {
        if (executableProcess != null)
            return ((IProcessChainExec)executableProcess).getInternalConnections();
        else
            return null;
    }
    
    
    public void setOutputNeeded(int outputIndex, boolean needed)
    {
        if (executableProcess != null)
            ((IProcessChainExec)executableProcess).setOutputNeeded(outputIndex, needed);
    }
    
    
    /* ************************************ */
    /*  Auto-generated Getters and Setters  */    
    /* ************************************ */    
    
    /**
     * Gets the list of components
     */
    public OgcPropertyList<AbstractProcess> getComponentList()
    {
        return components;
    }
    
    
    /**
     * Returns number of components
     */
    public int getNumComponents()
    {
        return components.size();
    }
    
    
    /**
     * Gets the component with the given name
     */
    public AbstractProcess getComponent(String name)
    {
        return components.get(name);
    }
    
    
    /**
     * Adds a new component
     */
    public void addComponent(String name, AbstractProcess component)
    {
        components.add(name, component);
    }
    
    
    /**
     * Gets the list of connections
     */
    public List<Link> getConnectionList()
    {
        return connections;
    }

    
    /**
     * Returns number of connections
     */
    public int getNumConnections()
    {
        return connections.size();
    }

    
    /**
     * Adds a new connection
     */
    public void addConnection(Link connection)
    {
        connections.add(connection);
    }

}

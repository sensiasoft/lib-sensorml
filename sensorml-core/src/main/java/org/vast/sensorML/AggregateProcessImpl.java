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
import java.util.Map.Entry;
import org.vast.process.IDataConnection;
import org.vast.process.IProcessChainExec;
import org.vast.process.IProcessExec;
import org.vast.process.ProcessException;
import org.vast.sensorML.SMLHelper.LinkTarget;
import org.vast.swe.SWEHelper;
import org.vast.util.Asserts;
import net.opengis.OgcProperty;
import net.opengis.OgcPropertyList;
import net.opengis.sensorml.v20.AbstractProcess;
import net.opengis.sensorml.v20.AggregateProcess;
import net.opengis.sensorml.v20.Link;


/**
 * POJO class for XML type AggregateProcessType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public class AggregateProcessImpl extends AbstractProcessImpl implements AggregateProcess
{
    private static final long serialVersionUID = -8513079782815270311L;
    
    protected OgcPropertyList<AbstractProcess> components = new OgcPropertyList<>(5);
    protected ArrayList<Link> connections = new ArrayList<>(5);
    
    
    public AggregateProcessImpl()
    {
        this.qName = AggregateProcess.DEFAULT_QNAME;
    }
    
    
    /**
     * This method handles the case of making an already built SensorML aggregate process
     * executable, hence it is not valid to provide an implementation of IProcessChainExec
     * that already contains child processes and/or connections.
     * @param processExec
     */
    @Override
    public void setExecutableImpl(IProcessExec processExec) throws ProcessException
    {
        Asserts.checkArgument(processExec instanceof IProcessChainExec,
                "Executable implementation must be a process chain implementation");
        
        // assign exec implementation
        IProcessChainExec processChain = (IProcessChainExec)processExec;
        super.setExecutableImpl(processChain);
        
        // case where SML description contains child process definitions
        // and chain exec implementation doesn't have any
        if (processChain.getChildProcesses().isEmpty() && processChain.getInternalConnections().isEmpty())
        {        
            // add child processes to exec impl
            for (OgcProperty<AbstractProcess> processProp: getComponentList().getProperties())
                addProcessToExec(processProp.getName(), processProp.getValue());
            
            // add connections to exec impl
            for (Link link: getConnectionList())
                addConnectionToExec(link);
        }
        
        // case where chain exec implementation contains executable child processes
        // and SML aggregate description doesn't have any
        else if (getNumComponents() == 0 && getNumConnections() == 0)
        {
            // wrap and add child processes to SML description
            for (Entry<String, IProcessExec> entry: processChain.getChildProcesses().entrySet())
            {
                AbstractProcess smlProcess = SMLUtils.wrapWithProcessDescription(entry.getValue());
                components.add(entry.getKey(), smlProcess);
            }
            
            // add links to SML description
            for (IDataConnection connection: processChain.getInternalConnections())
                addConnection(processChain, connection);
        }
        else
            throw new IllegalStateException("Executable implementation must be an empty process chain implementation");
    }
        
    
    protected void addConnection(IProcessChainExec parentChain, IDataConnection connection)
    {
        IProcessExec srcProcess = connection.getSourceProcess();
        IProcessExec destProcess = connection.getDestinationProcess();
                
        Link link = new LinkImpl();
        String srcPath = (srcProcess != parentChain) ? "components/" + srcProcess.getInstanceName() + SWEHelper.PATH_SEPARATOR : "";
        String destPath = (destProcess != parentChain) ? "components/" + destProcess.getInstanceName() + SWEHelper.PATH_SEPARATOR : "";
        link.setSource(srcPath + SMLHelper.getComponentPath(srcProcess, connection.getSourceComponent()));
        link.setDestination(destPath + SMLHelper.getComponentPath(destProcess, connection.getDestinationComponent()));
        connections.add(link);
    }
    
    
    /*@Override
    public Map<String, IProcessExec> getChildProcesses()
    {
        checkExecutable();
        return ((IProcessChainExec)executableProcess).getChildProcesses();
    }
    
    
    @Override
    public IProcessExec addProcess(String name, IProcessExec processExec)
    {
        AbstractProcessImpl smlProcess = SMLHelper.wrapWithProcessDescription(processExec);
        addComponent(name, smlProcess);
        return smlProcess;
    }


    @Override
    public void removeProcess(String name)
    {
        getComponentList().remove(name);
        if (isExecutable())
            ((IProcessChainExec)executableProcess).removeProcess(name);        
    }


    @Override
    public Collection<IDataConnection> getInternalConnections()
    {
        checkExecutable();
        return ((IProcessChainExec)executableProcess).getInternalConnections();
    }


    @Override
    public IDataConnection connect(IProcessExec srcProcess, DataComponent srcComponent, IProcessExec destProcess, DataComponent destComponent) throws ProcessException
    {
        checkExecutable();
        IDataConnection conn = ((IProcessChainExec)executableProcess).connect(srcProcess, srcComponent, destProcess, destComponent);
        
        // also add link object
        Link link = new LinkImpl();
        String srcPath = (srcProcess != this) ? "components/" + srcProcess.getInstanceName() + SWEHelper.PATH_SEPARATOR : "";
        String destPath = (destProcess != this) ? "components/" + srcProcess.getInstanceName() + SWEHelper.PATH_SEPARATOR : "";
        link.setSource(srcPath + SMLHelper.getComponentPath(srcProcess, srcComponent));
        link.setDestination(destPath + SMLHelper.getComponentPath(destProcess, destComponent));
        connections.add(link);
        
        return conn;
    }


    @Override
    public void removeInternalConnection(IDataConnection connection)
    {
        checkExecutable();
        ((IProcessChainExec)executableProcess).removeInternalConnection(connection);
        
        // also remove link object
        
    }


    @Override
    public void setOutputNeeded(String outputName, boolean needed)
    {
        checkExecutable();
        ((IProcessChainExec)executableProcess).setOutputNeeded(outputName, needed);
    }*/


    @Override
    public String toString()
    {
        StringBuilder text = new StringBuilder(super.toString());
        
        text.append("\n** Child Processes **\n\n");
        for (AbstractProcess child: getComponentList())
            text.append(child.toString()).append('\n');
                
        return text.toString();
    }
    
    
    /* ************************************ */
    /*  Auto-generated Getters and Setters  */    
    /* ************************************ */    
    
    /**
     * Gets the list of components
     */
    @Override
    public OgcPropertyList<AbstractProcess> getComponentList()
    {
        return components;
    }
    
    
    /**
     * Returns number of components
     */
    @Override
    public int getNumComponents()
    {
        return components.size();
    }
    
    
    /**
     * Gets the component with the given name
     */
    @Override
    public AbstractProcess getComponent(String name)
    {
        return components.get(name);
    }
    
    
    /**
     * Adds a new component
     */
    @Override
    public void addComponent(String name, AbstractProcess component)
    {
        components.add(name, component);
        addProcessToExec(name, component);
    }
    
    
    protected void addProcessToExec(String name, AbstractProcess component)
    {
        if (isExecutable() && component instanceof AbstractProcessImpl)
        {
            IProcessExec process = ((AbstractProcessImpl)component).executableProcess;
            ((IProcessChainExec)executableProcess).addProcess(name, process);
        }
    }
    
    
    /**
     * Gets the list of connections
     */
    @Override
    public List<Link> getConnectionList()
    {
        return connections;
    }

    
    /**
     * Returns number of connections
     */
    @Override
    public int getNumConnections()
    {
        return connections.size();
    }

    
    /**
     * Adds a new connection
     */
    @Override
    public void addConnection(Link link)
    {
        connections.add(link);
        if (executableProcess != null)
            addConnectionToExec(link);
    }
    
    
    protected void addConnectionToExec(Link link)
    {
        try
        {
            LinkTarget source = SMLHelper.findComponentByPath(this, link.getSource());
            LinkTarget dest = SMLHelper.findComponentByPath(this, link.getDestination());
            IProcessExec srcProcess = (source.process != this) ? (IProcessExec)source.process : executableProcess;
            IProcessExec destProcess = (dest.process != this) ? (IProcessExec)dest.process : executableProcess;
            ((IProcessChainExec)executableProcess).connect(srcProcess, source.component, destProcess, dest.component);
        }
        catch (Exception e)
        {
            String msg = String.format("Invalid link (%s -> %s)", link.getSource(), link.getDestination());
            throw new IllegalArgumentException(msg , e);
        }
    }
}

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
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import org.slf4j.Logger;
import org.vast.data.DataIterator;
import org.vast.process.DataConnectionList;
import org.vast.process.IDataConnection;
import org.vast.process.IProcessExec;
import org.vast.process.ProcessException;
import org.vast.process.ProcessInfo;
import org.vast.unit.Unit;
import org.vast.util.Asserts;
import net.opengis.gml.v32.Reference;
import net.opengis.sensorml.v20.AbstractModes;
import net.opengis.sensorml.v20.AbstractProcess;
import net.opengis.sensorml.v20.DataInterface;
import net.opengis.sensorml.v20.FeatureList;
import net.opengis.sensorml.v20.IOPropertyList;
import net.opengis.sensorml.v20.ObservableProperty;
import net.opengis.sensorml.v20.Settings;
import net.opengis.sensorml.v20.impl.DescribedObjectImpl;
import net.opengis.sensorml.v20.impl.FeatureListImpl;
import net.opengis.swe.v20.AbstractSWEIdentifiable;
import net.opengis.swe.v20.DataComponent;
import net.opengis.swe.v20.HasUom;


/**
 * <p>
 * This class implements both AbstractProcess from SensorML v2.0 bindings
 * and IProcessExec to allow execution if an executable implementation is
 * provided.<br/>
 * In order to provide execution capabilitiy, this class can wrap an
 * implementation of IProcessExec corresponding to a particular algorithm.
 * </p>
 *
 * @author Alex Robin
 * @since Feb 28, 2015
 */
public abstract class AbstractProcessImpl extends DescribedObjectImpl implements AbstractProcess, IProcessExec
{
    private static final long serialVersionUID = -6639992874400892845L;
    protected Reference typeOf;
    protected Settings configuration;
    protected FeatureList featuresOfInterest;
    protected IOPropertyList inputData = new IOPropertyList();
    protected IOPropertyList outputData = new IOPropertyList();
    protected IOPropertyList paramData = new IOPropertyList();
    protected ArrayList<AbstractModes> modesList = new ArrayList<>();
    protected String definition;
    
    protected transient IProcessExec executableProcess;


    public AbstractProcessImpl()
    {
    }


    @Override
    public String getName()
    {
        if (getNumNames() == 0 && isExecutable())
            return executableProcess.getProcessInfo().getName();
        else
            return super.getName();
    }
    
    
    @Override
    public String getDescription()
    {
        if (!isSetDescription() && isExecutable())
            return executableProcess.getProcessInfo().getDescription();
        else
            return super.getDescription();
    }


    public void setExecutableImpl(IProcessExec processExec) throws ProcessException
    {
        this.executableProcess = processExec;
        
        // merge input/output/parameter descriptions
        // we share the actual port lists between the description and the exec implementation
        inputData = mergePortDescriptors(inputData, processExec.getInputList());
        outputData = mergePortDescriptors(outputData, processExec.getOutputList());
        paramData = mergePortDescriptors(paramData, processExec.getParameterList());
    }
    
    
    protected IOPropertyList mergePortDescriptors(IOPropertyList smlPorts, IOPropertyList execPorts) throws ProcessException
    {
        // if no ports are defined in exec implementation, use ports defined in SML description
        // otherwise merge SML ports descriptions with ones defined in exec implementation 
        
        if (!execPorts.isEmpty())
        {
            for (String portName: smlPorts.getPropertyNames())
            {
                try
                {
                    DataComponent smlPort = smlPorts.getComponent(portName);
                    DataComponent execPort = execPorts.getComponent(portName);
                    mergePort(smlPort, execPort);                    
                }
                catch (IllegalArgumentException e)
                {
                    throw new ProcessException("Port '" + portName + "' is not defined in executable implementation", e);
                }
                catch (ProcessException e)
                {
                    String processName = getInstanceName() != null ? getInstanceName() : getProcessInfo().getName();
                    String msg = String.format("Description and executable implementation of process '%s'"
                            + " have incompatible definitions of port '%s'", processName, portName);
                    throw new ProcessException(msg, e);
                }
            }                
        }
        else
            execPorts.addAll(smlPorts);
        
        return execPorts;
    }
    
    
    /*
     * Merge port definitions from SML description and executable implementation, checking
     * if they are equivalent in terms of structure (including component names) and units.
     */
    protected void mergePort(DataComponent smlPort, DataComponent execPort) throws ProcessException
    {
        Asserts.checkNotNull(smlPort, DataComponent.class);
        Asserts.checkNotNull(execPort, DataComponent.class);
        
        DataIterator smlIt = new DataIterator(smlPort);
        DataIterator execIt = new DataIterator(execPort);
        while (smlIt.hasNext())
        {
            DataComponent smlComp = smlIt.next();
            
            if (!execIt.hasNext())
                throw new ProcessException(String.format("Component '%s' is missing", smlComp.getName()));
            
            DataComponent execComp = execIt.next();
            
            // check that component names are the same
            if (!Objects.equals(smlComp.getName(), execComp.getName()))
                throw new ProcessException(String.format("Expected component with name '%s' instead of '%s'", execComp.getName(), smlComp.getName()));
            
            // check that component type and size are the same
            if (smlComp.getClass() != execComp.getClass())
                throw new ProcessException(String.format("Component '%s' should be of type '%s'", smlComp.getName(), execComp.getClass().getInterfaces()[0].getSimpleName()));
            
            if (smlComp.getComponentCount() != execComp.getComponentCount())
                throw new ProcessException(String.format("Component '%s' should be of size '%d'", smlComp.getName(), execComp.getComponentCount()));
            
            // check that units are compatible
            if (smlComp instanceof HasUom && execComp instanceof HasUom)
            {
                Unit smlUom = ((HasUom)smlComp).getUom().getValue();
                Unit execUom = ((HasUom)execComp).getUom().getValue();
                
                if (execUom != null && (smlUom == null || !smlUom.isEquivalent(execUom)))
                    throw new ProcessException(String.format("Component '%s' should have unit '%s'", smlComp.getName(), execUom.getUCUMExpression()));
                                
                // if exec implementation is unit agnostic, use unit specified in description
                if (execUom == null && smlUom != null)
                    ((HasUom)execComp).setUom(((HasUom)smlComp).getUom());
            }
            
            // merge data
            if (smlComp.hasData())
                execComp.setData(smlComp.getData());
        }
    }
    
    
    public boolean isExecutable()
    {
        return (executableProcess != null);
    }
    
    
    @Override
    public ProcessInfo getProcessInfo()
    {
        checkExecutable();
        return executableProcess.getProcessInfo();
    }


    @Override
    public void init() throws ProcessException
    {
        checkExecutable();
        executableProcess.init();
    }


    @Override
    public boolean canRun()
    {
        checkExecutable();
        return executableProcess.canRun();
    }


    @Override
    public void run() throws ProcessException
    {
        checkExecutable();
        executableProcess.run();
    }


    @Override
    public void execute() throws ProcessException
    {
        checkExecutable();
        executableProcess.execute();
    }
    
    
    @Override
    public void start() throws ProcessException
    {
        checkExecutable();
        executableProcess.start();        
    }


    @Override
    public void start(ExecutorService threadPool) throws ProcessException
    {
        checkExecutable();
        executableProcess.start(threadPool);        
    }


    @Override
    public void stop()
    {
        checkExecutable();
        executableProcess.stop();        
    }


    @Override
    public void notifyParamChange()
    {
        if (executableProcess != null)
            executableProcess.notifyParamChange();
    }


    @Override
    public void dispose()
    {
        checkExecutable();
        executableProcess.dispose();
    }


    @Override
    public Map<String, DataConnectionList> getInputConnections()
    {
        checkExecutable();
        return executableProcess.getInputConnections();
    }


    @Override
    public Map<String, DataConnectionList> getParamConnections()
    {
        checkExecutable();
        return executableProcess.getParamConnections();
    }


    @Override
    public Map<String, DataConnectionList> getOutputConnections()
    {
        checkExecutable();
        return executableProcess.getOutputConnections();
    }
    
    
    @Override
    public void connect(DataComponent component, IDataConnection connection) throws ProcessException
    {
        checkExecutable();
        executableProcess.connect(component, connection);
    }


    @Override
    public void setInstanceName(String name)
    {
        checkExecutable();
        executableProcess.setInstanceName(name);
    }


    @Override
    public String getInstanceName()
    {
        if (isExecutable())
            return executableProcess.getInstanceName();
        else
            return null;
    }


    @Override
    public void disconnect(IDataConnection connection)
    {
        checkExecutable();
        executableProcess.disconnect(connection);
    }


    @Override
    public void setParentLogger(Logger log)
    {
        checkExecutable();
        executableProcess.setParentLogger(log);        
    }


    @Override
    public boolean needSync()
    {
        checkExecutable();
        return executableProcess.needSync();
    }
    
    
    protected void checkExecutable()
    {
        if (!isExecutable())
            throw new IllegalStateException("Process '" + getName() + "' is not executable");
    }
    

    @Override
    public String toString()
    {
        StringBuilder text = new StringBuilder();
        
        text.append("SML Process ");
        text.append('(').append(getName()).append(')');
        text.append('\n');
        
        text.append("  Inputs: ");
        for (int i = 0; i < inputData.size(); i++)
        {
            if (i > 0)
                text.append(", ");
            text.append(inputData.getProperty(i).getName());
        }
        text.append('\n');
        
        text.append("  Outputs: ");
        for (int i = 0; i < outputData.size(); i++)
        {
            if (i > 0)
                text.append(", ");
            text.append(outputData.getProperty(i).getName());
        }
        text.append('\n');
        
        text.append("  Parameters: ");
        for (int i = 0; i < paramData.size(); i++)
        {
            if (i > 0)
                text.append(", ");
            text.append(paramData.getProperty(i).getName());
        }
        text.append('\n');

        return text.toString();
    }


    /* ************************************ */
    /*  Auto-generated Getters and Setters  */
    /* ************************************ */

    /**
     * Gets the typeOf property
     */
    @Override
    public Reference getTypeOf()
    {
        return typeOf;
    }


    /**
     * Checks if typeOf is set
     */
    @Override
    public boolean isSetTypeOf()
    {
        return (typeOf != null);
    }


    /**
     * Sets the typeOf property
     */
    @Override
    public void setTypeOf(Reference typeOf)
    {
        this.typeOf = typeOf;
    }


    /**
     * Gets the configuration property
     */
    @Override
    public Settings getConfiguration()
    {
        return configuration;
    }


    /**
     * Checks if configuration is set
     */
    @Override
    public boolean isSetConfiguration()
    {
        return (configuration != null);
    }


    /**
     * Sets the configuration property
     */
    @Override
    public void setConfiguration(Settings configuration)
    {
        this.configuration = configuration;
    }


    /**
     * Gets the featuresOfInterest property
     */
    @Override
    public FeatureList getFeaturesOfInterest()
    {
        if (featuresOfInterest == null)
            featuresOfInterest = new FeatureListImpl();
        return featuresOfInterest;
    }


    /**
     * Checks if featuresOfInterest is set
     */
    @Override
    public boolean isSetFeaturesOfInterest()
    {
        return (featuresOfInterest != null);
    }


    /**
     * Sets the featuresOfInterest property
     */
    @Override
    public void setFeaturesOfInterest(FeatureList featuresOfInterest)
    {
        this.featuresOfInterest = featuresOfInterest;
    }


    @Override
    public IOPropertyList getInputList()
    {
        return inputData;
    }


    @Override
    public int getNumInputs()
    {
        return inputData.size();
    }


    @Override
    public AbstractSWEIdentifiable getInput(String name)
    {
        return inputData.get(name);
    }


    @Override
    public DataComponent getInputComponent(String name)
    {
        return SMLHelper.getIOComponent(getInput(name));
    }
    

    @Override
    public void addInput(String name, DataComponent input)
    {
        inputData.add(name, input);
    }


    @Override
    public void addInput(String name, ObservableProperty input)
    {
        inputData.add(name, input);
    }


    @Override
    public void addInput(String name, DataInterface input)
    {
        inputData.add(name, input);
    }
    
    
    @Override
    public IOPropertyList getOutputList()
    {
        return outputData;
    }


    @Override
    public int getNumOutputs()
    {
        return outputData.size();
    }


    @Override
    public AbstractSWEIdentifiable getOutput(String name)
    {
        return outputData.get(name);
    }
    
    
    @Override
    public DataComponent getOutputComponent(String name)
    {
        return SMLHelper.getIOComponent(getOutput(name));
    }


    @Override
    public void addOutput(String name, DataComponent output)
    {
        outputData.add(name, output);
    }


    @Override
    public void addOutput(String name, ObservableProperty output)
    {
        outputData.add(name, output);
    }


    @Override
    public void addOutput(String name, DataInterface output)
    {
        outputData.add(name, output);
    }


    @Override
    public int getNumParameters()
    {
        return paramData.size();
    }


    @Override
    public AbstractSWEIdentifiable getParameter(String name)
    {
        return paramData.get(name);
    }
    
    
    @Override
    public DataComponent getParameterComponent(String name)
    {
        return SMLHelper.getIOComponent(getParameter(name));
    }


    @Override
    public void addParameter(String name, DataComponent parameter)
    {
        paramData.add(name, parameter);
    }


    @Override
    public void addParameter(String name, ObservableProperty parameter)
    {
        paramData.add(name, parameter);
    }


    @Override
    public void addParameter(String name, DataInterface parameter)
    {
        paramData.add(name, parameter);
    }


    @Override
    public IOPropertyList getParameterList()
    {
        return paramData;
    }


    /**
     * Gets the list of modes properties
     */
    @Override
    public List<AbstractModes> getModesList()
    {
        return modesList;
    }


    /**
     * Returns number of modes properties
     */
    @Override
    public int getNumModes()
    {
        if (modesList == null)
            return 0;
        return modesList.size();
    }


    /**
     * Adds a new modes property
     */
    @Override
    public void addModes(AbstractModes modes)
    {
        this.modesList.add(modes);
    }


    /**
     * Gets the definition property
     */
    @Override
    public String getDefinition()
    {
        return definition;
    }


    /**
     * Checks if definition is set
     */
    @Override
    public boolean isSetDefinition()
    {
        return (definition != null);
    }


    /**
     * Sets the definition property
     */
    @Override
    public void setDefinition(String definition)
    {
        this.definition = definition;
    }
}

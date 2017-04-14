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
import java.util.Collections;
import java.util.List;
import org.vast.data.AbstractDataComponentImpl;
import org.vast.process.DataConnectionList;
import org.vast.process.DataConnection;
import org.vast.process.IProcessExec;
import org.vast.process.SMLException;
import net.opengis.OgcPropertyList;
import net.opengis.gml.v32.Reference;
import net.opengis.sensorml.v20.AbstractModes;
import net.opengis.sensorml.v20.AbstractProcess;
import net.opengis.sensorml.v20.AbstractSettings;
import net.opengis.sensorml.v20.DataInterface;
import net.opengis.sensorml.v20.FeatureList;
import net.opengis.sensorml.v20.IOPropertyList;
import net.opengis.sensorml.v20.ObservableProperty;
import net.opengis.sensorml.v20.impl.DescribedObjectImpl;
import net.opengis.sensorml.v20.impl.FeatureListImpl;
import net.opengis.swe.v20.AbstractSWEIdentifiable;
import net.opengis.swe.v20.DataComponent;


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
public abstract class AbstractProcessImpl extends DescribedObjectImpl implements AbstractProcess, IProcessExec, Runnable
{
    private static final long serialVersionUID = -6639992874400892845L;
    protected Reference typeOf;
    protected AbstractSettings configuration;
    protected FeatureList featuresOfInterest;
    protected IOPropertyList inputData = new IOPropertyList();
    protected IOPropertyList outputData = new IOPropertyList();
    protected IOPropertyList paramData = new IOPropertyList();
    protected ArrayList<AbstractModes> modesList = new ArrayList<AbstractModes>();
    protected String definition;
    
    protected transient IProcessExec executableProcess;


    public AbstractProcessImpl()
    {
    }
    
    
    public void setExecutableImpl(ExecutableProcessImpl processExec) throws SMLException
    {
        this.executableProcess = processExec;
        processExec.assignWrapperProcess(this);
    }
    
    
    @Override
    public boolean isExecutable()
    {
        return (executableProcess != null);
    }
    
    
    protected final void checkExecutable() throws SMLException
    {
        if (executableProcess == null)
            throw new SMLException("This process is not executable");
    }


    @Override
    public void init() throws SMLException
    {
        checkExecutable();
        executableProcess.init();
    }


    @Override
    public void reset() throws SMLException
    {
        checkExecutable();
        executableProcess.reset();
    }


    @Override
    public void execute() throws SMLException
    {
        checkExecutable();
        executableProcess.execute();
    }


    @Override
    public void dispose()
    {
        if (executableProcess != null)
            executableProcess.dispose();
    }


    @Override
    public boolean canRun()
    {
        if (executableProcess != null)
            return executableProcess.canRun();
        else
            return false;
    }


    @Override
    public void createNewOutputBlocks()
    {
        if (executableProcess != null)
            executableProcess.createNewOutputBlocks();
    }


    @Override
    public void createNewInputBlocks()
    {
        if (executableProcess != null)
            executableProcess.createNewInputBlocks();
    }


    @Override
    public void run()
    {
        if (executableProcess != null)
            executableProcess.run();
    }


    @Override
    public synchronized void start() throws SMLException
    {
        checkExecutable();
        executableProcess.start();
    }


    @Override
    public synchronized void stop()
    {
        if (executableProcess != null)
            executableProcess.stop();
    }


    /**
     * Print process name and I/O info
     */
    @Override
    public String toString()
    {
        StringBuffer text = new StringBuffer();
        String indent = "    ";

        text.append("Process: ");
        text.append(id);
        text.append(" (" + this.getClass().getName() + ")\n");

        text.append("\n  Inputs:\n");
        for (int i = 0; i < getNumInputs(); i++)
        {
            text.append(indent);
            text.append(((AbstractDataComponentImpl)inputData.getComponent(i)).toString(indent));
        }

        text.append("\n  Outputs:\n");
        for (int i = 0; i < getNumOutputs(); i++)
        {
            text.append(indent);
            text.append(((AbstractDataComponentImpl)outputData.getComponent(i)).toString(indent));
        }

        text.append("\n  Parameters:\n");
        for (int i = 0; i < getNumParameters(); i++)
        {
            text.append(indent);
            text.append(((AbstractDataComponentImpl)paramData.getComponent(i)).toString(indent));
        }

        return text.toString();
    }


    @Override
    public void connectInput(String inputName, String dataPath, DataConnection connection) throws SMLException
    {
        checkExecutable();
        executableProcess.connectInput(inputName, dataPath, connection);
    }


    @Override
    public void connectOutput(String outputName, String dataPath, DataConnection connection) throws SMLException
    {
        checkExecutable();
        executableProcess.connectOutput(outputName, dataPath, connection);
    }


    @Override
    public void connectParameter(String paramName, String dataPath, DataConnection connection) throws SMLException
    {
        checkExecutable();
        executableProcess.connectParameter(paramName, dataPath, connection);
    }


    @Override
    public boolean isInputConnected(String inputName)
    {
        if (executableProcess != null)
            return executableProcess.isInputConnected(inputName);
        else
            return false;
    }


    @Override
    public boolean isOutputConnected(String outputName)
    {
        if (executableProcess != null)
            return executableProcess.isOutputConnected(outputName);
        else
            return false;
    }


    @Override
    public boolean isParamConnected(String paramName)
    {
        if (executableProcess != null)
            return executableProcess.isParamConnected(paramName);
        else
            return false;
    }


    @Override
    public List<DataConnectionList> getInputConnections()
    {
        if (executableProcess != null)
            return executableProcess.getInputConnections();
        else
            return Collections.EMPTY_LIST;
    }


    @Override
    public List<DataConnectionList> getParamConnections()
    {
        if (executableProcess != null)
            return executableProcess.getParamConnections();
        else
            return Collections.EMPTY_LIST;
    }


    @Override
    public List<DataConnectionList> getOutputConnections()
    {
        if (executableProcess != null)
            return executableProcess.getOutputConnections();
        else
            return Collections.EMPTY_LIST;
    }
    
    
    @Override
    public boolean isUsingQueueBuffers()
    {
        if (executableProcess != null)
            return executableProcess.isUsingQueueBuffers();
        else
            return false;
    }


    @Override
    public void setUsingQueueBuffers(boolean usingQueueBuffers)
    {
        if (executableProcess != null)
            executableProcess.setUsingQueueBuffers(usingQueueBuffers);        
    }


    @Override
    public boolean needSync()
    {
        if (executableProcess != null)
            return executableProcess.needSync();
        else
            return false;
    }


    @Override
    public void setAvailability(List<DataConnectionList> allConnections, boolean availability)
    {
        if (executableProcess != null)
            executableProcess.setAvailability(allConnections, availability);        
    }


    @Override
    public void transferData(List<DataConnectionList> allConnections)
    {
        if (executableProcess != null)
            executableProcess.transferData(allConnections);        
    }


    protected int getSignalIndex(OgcPropertyList<?> ioList, String signalName)
    {
        for (int i = 0; i < ioList.size(); i++)
        {
            if (signalName.equals(ioList.getProperty(i).getName()))
                return i;
        }

        return -1;
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
    public AbstractSettings getConfiguration()
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
    public void setConfiguration(AbstractSettings configuration)
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

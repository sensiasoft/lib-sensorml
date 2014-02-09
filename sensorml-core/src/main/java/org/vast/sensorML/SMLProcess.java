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
import org.vast.cdm.common.DataComponent;
import org.vast.ogc.gml.FeatureImpl;
import org.vast.process.ConnectionList;
import org.vast.process.DataConnection;
import org.vast.process.IProcess;
import org.vast.process.ProcessException;
import org.vast.sensorML.configuration.Configuration;
import org.vast.sensorML.configuration.Mode;
import org.vast.sensorML.metadata.Metadata;


/**
 * <p>
 * SensorML Process
 * </p>
 *
 * <p>Copyright (c) 2012</p>
 * @author Alexandre Robin
 * @since Sep 8, 2013
 * @version 1.0
 */
public class SMLProcess extends FeatureImpl implements IProcess 
{
    private static final long serialVersionUID = -6253151260359858061L;

    protected IProcess processImpl;
    protected Metadata metadata;
    protected Configuration configuration;
    protected List<Mode> modes;


    public SMLProcess()
    {
        this(new DummyProcess());
    }
    
    
    public SMLProcess(IProcess processImpl)
    {
        super(new QName("Process"));
        this.processImpl = processImpl;
    }
    
    
    public Metadata getMetadata()
    {
        return this.metadata;
    }
    
    
    public void setMetadata(Metadata metadata)
    {
        this.metadata = metadata;
    }
    
    
    public Configuration getConfiguration()
    {
        return configuration;
    }


    public void setConfiguration(Configuration configuration)
    {
        this.configuration = configuration;
    }


    public List<Mode> getModes()
    {
        return modes;
    }


    public void setModes(List<Mode> modes)
    {
        this.modes = modes;
    }
    
    
    public SMLProcess getConfiguredInstance()
    {
        // TODO propagate configuration values to actual fields
        return null;
    }


    //// Methods delegated to underlying process implementation ////
    
    public void init() throws ProcessException
    {
        processImpl.init();
    }


    public void reset() throws ProcessException
    {
        processImpl.reset();
    }


    public void execute() throws ProcessException
    {
        processImpl.execute();
    }


    public void dispose()
    {
        processImpl.dispose();
    }


    public boolean canRun()
    {
        return processImpl.canRun();
    }


    public void createNewOutputBlocks()
    {
        processImpl.createNewOutputBlocks();
    }


    public void createNewInputBlocks()
    {
        processImpl.createNewInputBlocks();
    }


    public void run()
    {
        processImpl.run();
    }


    public void start() throws ProcessException
    {
        processImpl.start();
    }


    public void stop()
    {
        processImpl.stop();
    }


    public String getName()
    {
        return processImpl.getName();
    }


    public void setName(String name)
    {
        processImpl.setName(name);
    }


    public void addInput(String name, DataComponent inputStructure)
    {
        processImpl.addInput(name, inputStructure);
    }


    public void addOutput(String name, DataComponent outputStructure)
    {
        processImpl.addOutput(name, outputStructure);
    }


    public void addParameter(String name, DataComponent paramStructure)
    {
        processImpl.addParameter(name, paramStructure);
    }


    public void connectInput(String dataPath, DataConnection connection) throws ProcessException
    {
        processImpl.connectInput(dataPath, connection);
    }


    public void connectOutput(String dataPath, DataConnection connection) throws ProcessException
    {
        processImpl.connectOutput(dataPath, connection);
    }


    public void connectParameter(String dataPath, DataConnection connection) throws ProcessException
    {
        processImpl.connectParameter(dataPath, connection);
    }


    public boolean isInputConnected(String inputName)
    {
        return processImpl.isInputConnected(inputName);
    }


    public boolean isOutputConnected(String outputName)
    {
        return processImpl.isOutputConnected(outputName);
    }


    public boolean isParamConnected(String paramName)
    {
        return processImpl.isParamConnected(paramName);
    }


    public List<ConnectionList> getInputConnections()
    {
        return processImpl.getInputConnections();
    }


    public List<ConnectionList> getOutputConnections()
    {
        return processImpl.getOutputConnections();
    }


    public List<ConnectionList> getParamConnections()
    {
        return processImpl.getParamConnections();
    }


    public DataComponent getInputList()
    {
        return processImpl.getInputList();
    }


    public DataComponent getOutputList()
    {
        return processImpl.getOutputList();
    }


    public DataComponent getParameterList()
    {
        return processImpl.getParameterList();
    }


    public boolean isUsingQueueBuffers()
    {
        return processImpl.isUsingQueueBuffers();
    }


    public void setUsingQueueBuffers(boolean usingQueueBuffers)
    {
        processImpl.setUsingQueueBuffers(usingQueueBuffers);
    }


    public boolean needSync()
    {
        return processImpl.needSync();
    }


    public void setAvailability(List<ConnectionList> allConnections, boolean availability)
    {
        processImpl.setAvailability(allConnections, availability);
    }


    public void transferData(List<ConnectionList> allConnections)
    {
        processImpl.transferData(allConnections);
    }    
}

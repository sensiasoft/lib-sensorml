/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are subject to the Mozilla Public License Version
 1.1 (the "License"); you may not use this file except in compliance with
 the License. You may obtain a copy of the License at
 http://www.mozilla.org/MPL/MPL-1.1.html
 
 Software distributed under the License is distributed on an "AS IS" basis,
 WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 for the specific language governing rights and limitations under the License.
 
 The Original Code is the "SensorML DataProcessing Engine".
 
 The Initial Developer of the Original Code is the
 University of Alabama in Huntsville (UAH).
 Portions created by the Initial Developer are Copyright (C) 2006
 the Initial Developer. All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin <robin@nsstc.uah.edu>
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.sensorML.reader;

import org.w3c.dom.*;
import org.vast.process.*;
import org.vast.cdm.reader.DataComponentsReader;
import org.ogc.cdm.common.*;
import org.vast.data.*;
import org.vast.io.xml.DOMReader;
import org.vast.sensorML.Dummy_Process;
import org.vast.sensorML.SMLException;
import org.vast.sensorML.metadata.Metadata;
import org.vast.util.*;


/**
 * <p><b>Title:</b><br/>
 * Process Reader
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Reader for Process, ProcessModel, ProcessChain, ProcessList
 * Uses Reflection to create particular ProcessModel for plug'n play capability
 * </p>
 *
 * <p>Copyright (c) 2005</p>
 * @author Alexandre Robin
 * @version 1.0
 */
public class ProcessReader extends SMLReader
{
    protected static final String dataSeparator = "/"; 
    protected DataComponentsReader dataComponentReader;
    protected MetadataReader metadataReader;
    protected ProcessLoader processLoader;
    protected boolean readMetadata = false;
    protected boolean createExecutableProcess = true;
        
    
    /**
     * Constructs a ProcessReader using the specified DOMReader
     * @param dom
     */
    public ProcessReader(DOMReader dom)
    {
        this.dom = dom;
        dataComponentReader = new DataComponentsReader(this.dom);
        processLoader = new ProcessLoader();
    }

    
    /**
     * Reads a process property
     * @param propertyElement
     * @return
     * @throws SMLException
     */
    public DataProcess readProcessProperty(Element propertyElement) throws SMLException
    {
        Element processElement = dom.getFirstChildElement(propertyElement);
        DataProcess process = readProcess(processElement);
        process.setName(dataComponentReader.readName(propertyElement));
        return process;
    }
    
    
    /**
     * Reads a source property
     * @param propertyElement
     * @return
     * @throws SMLException
     */
    public DataProcess readSourceProperty(Element propertyElement) throws SMLException
    {
        Element dataSourceElement = dom.getFirstChildElement(propertyElement);
        DataProcess process = readDataSource(dataSourceElement);
        process.setName(dataComponentReader.readName(propertyElement));
        return process;
    }
    
    
    /**
     * Reads any Process (ProcessModel, ProcessChain or derived versions)
     * @param processDefElement
     * @return
     * @throws SMLException
     */
    public DataProcess readProcess(Element processElement) throws SMLException
    {
        DataProcess dataProcess;

        // read process core info (model or chain)
        if (dom.existElement(processElement, "processes"))
            dataProcess = readProcessChain(processElement);
        else
            dataProcess = readProcessModel(processElement);

        // read metadata if needed
        if (readMetadata)
        {
            if (metadataReader == null)
                metadataReader = new MetadataReader(dom);
            
            Metadata metadata = metadataReader.readMetadata(processElement);
            dataProcess.setProperty(DataProcess.METADATA, metadata);
        }
        
        dataProcess.setName(processElement.getLocalName());
            
        return dataProcess;
    }
    
    
    /**
     * Creates a DataProcess object corresponding to given method URN
     * @param processURN
     * @return
     * @throws SMLException
     */
    public DataProcess readProcessModel(Element processModelElement) throws SMLException
    {
        // get process urn
        String uri = dom.getAttributeValue(processModelElement, "method/href");
        
        // load process
        DataProcess newProcess;
        if (createExecutableProcess)
            newProcess = processLoader.loadProcess(uri);
        else
            newProcess = new Dummy_Process();
        
        // read output/output/parameter structures
        readProcessIO(processModelElement, newProcess);
        
        // set process type (=method uri)
        newProcess.setType(uri);
        
        return newProcess;
    }
    
    
    /**
     * Constructs a Data Source Process
     * @param dataSourceElement
     * @return
     * @throws SMLException
     */
    public DataProcess readDataSource(Element dataSourceElement) throws SMLException
    {
        // TODO parse DataSource structure
        return new Dummy_Process();
    }
    
    
    /**
     * Constructs a ProcessChain from XML structure
     * @param processChainElement Element the XML element to read from
     * @throws SMLException
     * @return ProcessChain
     */
    public ProcessChain readProcessChain(Element processChainElement) throws SMLException
    {
        return readProcessChain(processChainElement, null);
    }
    
    
    /**
     * Fills up process chain information using XML element content
     * @param processChainElement the XML element to read from
     * @param processChain the process chain to fill up
     * @return ProcessChain
     * @throws SMLException
     */
    protected ProcessChain readProcessChain(Element processChainElement, ProcessChain processChain) throws SMLException
    {
        NodeList processList, dataSourceList, connectionList;
        
        // get process list
        processList = dom.getElements(processChainElement, "processes/ProcessList/*");
        int memberCount = processList.getLength();
        
        // get dataSource list
        dataSourceList = dom.getElements(processChainElement, "dataSources/DataSourceList/*");
        memberCount += dataSourceList.getLength();
        
        // construct the process chain if it was not given
        if (processChain == null)
            processChain = new ProcessChain(memberCount);
        
        // read output/output/parameter structures
        readProcessIO(processChainElement, processChain);
        
        // parse and add each process
        for (int i = 0; i < processList.getLength(); i++)
        {
            Element processElement = (Element)processList.item(i);
            if (processElement != null)
            {
                DataProcess childProcess = readProcessProperty(processElement);
                processChain.addProcess(childProcess.getName(), childProcess);
            }
        }
        
        // parse and add each dataSource
        for (int i = 0; i < dataSourceList.getLength(); i++)
        {
            Element sourcePropertyElement = (Element)dataSourceList.item(i);
            if (sourcePropertyElement != null)
            {
                DataProcess childProcess = readDataSource(sourcePropertyElement);
                processChain.addProcess(childProcess.getName(), childProcess);
            }
        }
        
        // get connection list
        connectionList = dom.getElements(processChainElement, "connections/ConnectionList/connection/*");
        int connectionNumber = connectionList.getLength();

        // parse and create each DataQueue between processes
        for (int i = 0; i < connectionNumber; i++)
        {
            Element connectionElement = (Element)connectionList.item(i);
            String linkType = connectionElement.getLocalName();
            
            DataQueue dataQueue = null;
            
            if (linkType.equals("Link"))
            {
            	// create new DataQueue
                dataQueue = new DataQueue();
                
                try
                {
                    // parse source
                    String source = dom.getAttributeValue(connectionElement, "source/ref");
                    connectSignal(processChain, dataQueue, source);
                    
                    // parse destination
                    String dest = dom.getAttributeValue(connectionElement, "destination/ref");
                    connectSignal(processChain, dataQueue, dest);
                }
                catch (SMLException e)
                {
                    ExceptionSystem.display(e);
                }
                            
                // check source/destination coherency
                //dataQueue.check();
            }
            else if (linkType.equals("ArrayLink"))
            {
            	this.createMuxProcess(connectionElement);
            }
            else
            	throw new SMLException("Unkown link type: " + linkType);
            
            // also add the connection to the main list
            processChain.getInternalConnections().add(dataQueue);
        }
        
        return processChain;
    }
    
    
    /**
     * TODO Creates a multiplexing process implied by an ArrayLink connection
     * @param arrayLinkElement
     */
    public void createMuxProcess(Element arrayLinkElement)
    {
    	
    }
    
    
    /**
     * Connect a dataQueue to desired process input/output/param
     * @param processChain
     * @param dataQueue
     * @param linkString
     * @throws SMLException
     */
    public void connectSignal(ProcessChain processChain, DataQueue dataQueue, String linkString) throws SMLException
    {
        boolean internalConnection = false;
        DataProcess selectedProcess;        
        
        // parse link path
        int sep1 = linkString.indexOf(dataSeparator, 1);
        String processName = linkString.substring(0, sep1);
        
        int sep2 = linkString.indexOf(dataSeparator, sep1 + 1);
        String portType = linkString.substring(sep1+1, sep2);
        
        String portName = linkString.substring(sep2 + 1);
        //String [] names = portName.split(dataSeparator);
        
        
        // special case if 'this'
        if (processName.equalsIgnoreCase("this"))
        {
            // make internal connections if 'this'
            internalConnection = true;
            selectedProcess = processChain;
        }
        else
        {
            // find desired process in the chain
            selectedProcess = processChain.getProcess(processName);            
        }
        
        
        if (selectedProcess == null)
        {
            throw new SMLException("Process " + processName + " does't exist in ProcessChain");
        }
       
        
        try
        {
            if (portType.equals("inputs"))
            {
                if (internalConnection)
                {
                    processChain.connectInternalInput(portName, dataQueue);
                }
                else
                {
                    selectedProcess.connectInput(portName, dataQueue);
                }
            }
            else if (portType.equals("outputs"))
            {
                if (internalConnection)
                {
                    processChain.connectInternalOutput(portName, dataQueue);
                }
                else
                {
                    selectedProcess.connectOutput(portName, dataQueue);
                }
            }
            else if (portType.equals("parameters"))
            {
                if (internalConnection)
                {
                    //dataQueue.alwaysWait = false;
                    processChain.connectInternalParam(portName, dataQueue);
                }
                else
                {
                    selectedProcess.connectParameter(portName, dataQueue);
                }
            }
        }
        catch (ProcessException e)
        {
            throw new SMLException("No input, output or parameter named " + portName + " found in process " + processName);
        }
    }


    /**
     * Reads all input/output/parameter structure from the process element
     * and try to assign them to the given DataProcess
     * @param processElement
     * @param process
     * @throws SMLException
     */
    public void readProcessIO(Element processElement, DataProcess process) throws SMLException
    {
        try
		{
			Element dataElement;
			NodeList signalList;
			int signalCount;
			
			// read inputs
			signalList = dom.getElements(processElement, "inputs/InputList/*");
			signalCount = signalList.getLength();
			for (int i=0; i<signalCount; i++)
			{
			    dataElement = (Element)signalList.item(i);
			    AbstractDataComponent inputData = dataComponentReader.readComponentProperty(dataElement);
			    process.addInput(inputData.getName(), inputData);
			}
			
			// read outputs
			signalList = dom.getElements(processElement, "outputs/OutputList/*");
			signalCount = signalList.getLength();
			for (int i=0; i<signalCount; i++)
			{
			    dataElement = (Element)signalList.item(i);
			    AbstractDataComponent outputData = dataComponentReader.readComponentProperty(dataElement);
			    process.addOutput(outputData.getName(), outputData);
			}	        
			
			// read parameters
			signalList = dom.getElements(processElement, "parameters/ParameterList/*");
			signalCount = signalList.getLength();
			for (int i=0; i<signalCount; i++)
			{
			    dataElement = (Element)signalList.item(i);
			    AbstractDataComponent paramData = dataComponentReader.readComponentProperty(dataElement);
			    process.addParameter(paramData.getName(), paramData);
			}
		}
		catch (CDMException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }


    public boolean getReadMetadata()
    {
        return readMetadata;
    }


    public void setReadMetadata(boolean readMetadata)
    {
        this.readMetadata = readMetadata;
    }


    public boolean isCreateExecutableProcess()
    {
        return createExecutableProcess;
    }


    public void setCreateExecutableProcess(boolean createExecutableProcess)
    {
        this.createExecutableProcess = createExecutableProcess;
    }
}
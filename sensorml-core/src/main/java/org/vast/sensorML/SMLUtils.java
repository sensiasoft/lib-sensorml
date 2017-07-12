/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are subject to the Mozilla Public License Version
 1.1 (the "License"); you may not use this file except in compliance with
 the License. You may obtain a copy of the License at
 http://www.mozilla.org/MPL/MPL-1.1.html
 
 Software distributed under the License is distributed on an "AS IS" basis,
 WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 for the specific language governing rights and limitations under the License.
 
 The Original Code is the "SensorML DataProcessing Engine".
 
 The Initial Developer of the Original Code is the VAST team at the University of Alabama in Huntsville (UAH). <http://vast.uah.edu> Portions created by the Initial Developer are Copyright (C) 2007 the Initial Developer. All Rights Reserved. Please Contact Mike Botts <mike.botts@uah.edu> for more information.
 
 Contributor(s): 
    Alexandre Robin <robin@nsstc.uah.edu>
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.sensorML;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import net.opengis.OgcProperty;
import net.opengis.OgcPropertyList;
import net.opengis.gml.v32.Reference;
import net.opengis.sensorml.v20.AbstractProcess;
import net.opengis.sensorml.v20.AggregateProcess;
import net.opengis.sensorml.v20.ArraySetting;
import net.opengis.sensorml.v20.ConstraintSetting;
import net.opengis.sensorml.v20.Link;
import net.opengis.sensorml.v20.Settings;
import net.opengis.sensorml.v20.SimpleProcess;
import net.opengis.sensorml.v20.ValueSetting;
import net.opengis.swe.v20.DataArray;
import net.opengis.swe.v20.DataComponent;
import net.opengis.swe.v20.DataConstraint;
import net.opengis.swe.v20.HasConstraints;
import net.opengis.swe.v20.RangeComponent;
import net.opengis.swe.v20.ScalarComponent;
import org.vast.ogc.OGCRegistry;
import org.vast.process.ExecutableChainImpl;
import org.vast.process.IProcessChainExec;
import org.vast.process.IProcessExec;
import org.vast.process.ProcessException;
import org.vast.sensorML.SMLHelper.LinkTarget;
import org.vast.swe.SWEHelper;
import org.vast.util.Asserts;
import org.vast.xml.DOMHelper;
import org.vast.xml.XMLBindingsUtils;
import org.vast.xml.XMLReaderException;
import org.vast.xml.XMLWriterException;
import org.w3c.dom.Element;
import com.rits.cloning.Cloner;


/**
 * <p>
 * Helper class providing a version agnostic access to SensorML object readers
 * and writers as well as other utility methods.
 * This class delegates to version specific code whenever required.
 * </p>
 *
 * @author Alex Robin
 * @since Apr 10, 2007
 * */
public class SMLUtils extends XMLBindingsUtils
{
	public final static String IC;
	public final static String SENSORML;
    public final static String V2_0 = "2.0";
    
    IProcessFactory processFactory = new ProcessLoader();
    
    
    static
    {
        IC = "IC";
        SENSORML = "SensorML";
        loadRegistry();
    }
    
    
    public static void loadRegistry()
    {
    	String mapFileUrl = SMLUtils.class.getResource("SMLRegistry.xml").toString();
    	OGCRegistry.loadMaps(mapFileUrl, false);
    }
    
    
    enum ObjectType
    {
        Process { @Override public String toString() { return "SML Process"; } }
    }
    
    
    /**
     * Creates this helper for the specified SensorML version
     * @param version
     */
    public SMLUtils(String version)
    {
        // TODO select proper bindings for selected version
        staxBindings = new SMLStaxBindings();
    }
    
    
    public SMLUtils(SMLStaxBindings staxBindings)
    {
        this.staxBindings = staxBindings;
    }
    
    
    /**
     * Reads a SensorML process from a DOM element
     * @param dom DOM helper wrapping the XML document to read from
     * @param processElt DOM element to read from. Must be of one of the types derived from AbstractProcess
     * @return the process instance
     * @throws XMLReaderException if an error occured while reading the XML
     */
    public AbstractProcess readProcess(DOMHelper dom, Element processElt) throws XMLReaderException
    {
        return (AbstractProcess)readFromDom(dom, processElt, ObjectType.Process);
    }
    
    
    /**
     * Reads a SensorML process from an InputStream
     * The root element must be of one of the types derived from AbstractProcess
     * @param is Input stream to read from
     * @return the process instance
     * @throws XMLReaderException if an error occured while reading the XML
     */
    public AbstractProcess readProcess(InputStream is) throws XMLReaderException
    {
        return (AbstractProcess)readFromStream(is, ObjectType.Process);
    }
    
    
    /**
     * Reads a SensorML process from an InputStream
     * The root element must be of one of the types derived from AbstractProcess
     * @param is Input stream to read from
     * @param baseURI Base URI of the document being read (used to resolve relative xlinks)
     * @return the process instance
     * @throws XMLReaderException if an error occured while reading the XML
     */
    public AbstractProcess readProcess(InputStream is, URI baseURI) throws XMLReaderException
    {
        return (AbstractProcess)readFromStream(is, baseURI, ObjectType.Process);
    }
    
    
    /**
     * Serializes a SensorML process to a DOM element
     * @param dom DOM helper wrapping the XMl document to write to
     * @param process Process object to serialize
     * @return DOM element containing the process description (not attached to any parent)
     * @throws XMLWriterException if an error occurs while generating the DOM tree
     */
    public Element writeProcess(DOMHelper dom, AbstractProcess process) throws XMLWriterException
    {
        return writeToDom(dom, process, ObjectType.Process);
    }
    
    
    /**
     * Serializes a SensorML process to an OutputStream
     * @param os Output stream to write to
     * @param process Process object to serialize
     * @param indent Set to true to indent the output
     * @throws XMLWriterException if an error occurs while generating the XML content
     * @throws IOException if an error occurs while writing to output the stream
     */
    public void writeProcess(OutputStream os, AbstractProcess process, boolean indent) throws XMLWriterException
    {
        writeToStream(os, process, ObjectType.Process, indent);
    }
    
    
    /**
     * Logic to guess SensorML version from namespace
     * @param dom
     * @param smlElt DOM element containing the SensorML content
     * @return version string
     */
    public String getVersion(DOMHelper dom, Element smlElt)
    {
        // get version from the last part of namespace URI
        //String sweUri = dom.getXmlDocument().getNSUri("swe");
        String smlUri = smlElt.getNamespaceURI();
        String version = smlUri.substring(smlUri.lastIndexOf('/') + 1);
        
        // check if version is a valid version number otherwise defaults to 0
        if (!version.matches("^\\d+(\\.\\d+)?(\\.\\d+)?$"))
            version = "0.0";
        
        return version;
    }
    
    
    @Override
    protected Object readFromXmlStream(XMLStreamReader reader, Enum<?> eltType) throws XMLStreamException
    {
        reader.nextTag();
        SMLStaxBindings smlBindings = (SMLStaxBindings)staxBindings;
        
        switch ((ObjectType)eltType)
        {
            case Process:
                return smlBindings.readAbstractProcess(reader);
        }
        
        return null;
    }
    
    
    @Override
    protected void writeToXmlStream(XMLStreamWriter writer, Object sweObj, Enum<?> eltType) throws XMLStreamException
    {
        SMLStaxBindings smlBindings = (SMLStaxBindings)staxBindings;
        
        switch ((ObjectType)eltType)
        {
            case Process:
                smlBindings.writeAbstractProcess(writer, (AbstractProcess)sweObj);
                return;
        }
    }
    
    
    /*
      Helper methods for working with configurable and executable instances
    */
    
    
    public void setProcessFactory(IProcessFactory processFactory)
    {
        this.processFactory = processFactory;
    }
    
    
    /**
     * Create SML process description from process executable implementation
     * @param processExec
     * @return
     */
    public static AbstractProcessImpl wrapWithProcessDescription(IProcessExec processExec)
    {
        // if we're already wrapped
        if (processExec instanceof AbstractProcess)
            throw new IllegalArgumentException("Argument is already a SensorML process description");
        
        try
        {
            // create new SML process
            AbstractProcessImpl smlProcess;        
            if (processExec instanceof IProcessChainExec)
                smlProcess = new AggregateProcessImpl();
            else
                smlProcess = new SimpleProcessImpl();
            
            // assign exec implementation
            smlProcess.setExecutableImpl(processExec);            
            return smlProcess;
        }
        catch (ProcessException e)
        {
            throw new IllegalStateException("Cannot wrap process with SensorML description", e);
        }
    }
    
    
    /**
     * Validate connections between child processes of the given process chain
     * @param processChain
     * @throws SMLException
     */
    public static void validateConnections(AggregateProcess processChain) throws SMLException
    {
        for (Link link: processChain.getConnectionList())
        {
            try
            {
                SMLHelper.findComponentByPath(processChain, link.getSource());
                SMLHelper.findComponentByPath(processChain, link.getDestination());
            }
            catch (Exception e)
            {
                String msg = String.format("Invalid link path ('%s' -> '%s')", link.getSource(), link.getDestination());
                throw new SMLException(msg , e);
            }
        }
    }
    
    
    /**
     * Generate a new process description configured for runtime execution
     * @param process static, unconfigured process description (i.e. not executable)
     * @param useThreads true to use separate threads for child processes (only applicable to aggregate processes)
     * @return the new executable process instance
     * @throws SMLException if executable instance cannot be created (e.g. exec implementation not found)
     */
    public AbstractProcessImpl getExecutableInstance(AbstractProcessImpl process, boolean useThreads) throws SMLException
    {
        Cloner cloner = new Cloner();
        AbstractProcessImpl newInstance = cloner.deepClone(process);
        makeProcessExecutable(newInstance, useThreads);
        return newInstance;
    }
    
    
    /**
     * Makes a process executable by instantiating and wrapping an implementation of IProcessExec.<br/>
     * The actual implementation is found using the method or typeOf URI.
     * @param smlProcess process description to prepare for runtime execution
     * @param useThreads if true, run children processes in separate threads
     * @throws SMLException if process cannot be made executable
     */
    public void makeProcessExecutable(AbstractProcessImpl smlProcess, boolean useThreads) throws SMLException
    {
        // don't do anything if already executable
        if (smlProcess.isExecutable())
            return;
        
        try
        {
            if (smlProcess instanceof AggregateProcess)
            {
                AggregateProcessImpl processChain = (AggregateProcessImpl)smlProcess;
                
                // make child processes executable if not already
                for (AbstractProcess childProcess: processChain.getComponentList())
                {
                    if (!((AbstractProcessImpl)childProcess).isExecutable())
                        makeProcessExecutable((AbstractProcessImpl)childProcess, false);
                }
                
                processChain.setExecutableImpl(new ExecutableChainImpl(useThreads));
            }
            else if (smlProcess instanceof SimpleProcess)
            {
                String processUID = ((SimpleProcess)smlProcess).getMethodProperty().getHref();
                
                // if method is not set, try typeOf property
                if (processUID == null)
                    processUID = ((SimpleProcess)smlProcess).getTypeOf().getTitle();
                
                if (processUID == null)
                    processUID = ((SimpleProcess)smlProcess).getTypeOf().getHref();
                
                if (processUID == null)
                    throw new SMLException("No executable method specified for process " + smlProcess.getId());
                    
                IProcessExec processExec = processFactory.loadProcess(processUID);
                smlProcess.setExecutableImpl(processExec);
            }
        }
        catch (ProcessException e)
        {
            throw new SMLException(String.format("Cannot make process '%s' executable", smlProcess.getId()), e);
        }
        
        applyConfig(smlProcess, true);
    }
       
    
    /**
     * Generates a configured instance by copying I/Os definition from base description 
     * referenced by the typeOf property, and applying configuration settings.
     * @param process process with typeOf and configuration settings
     * @return new process instance with configuration values set
     * @throws SMLException if configuration is invalid or cannot be applied
     */
    public AbstractProcess getConfiguredInstance(AbstractProcess process) throws SMLException
    {
        AbstractProcess baseProcess = null;        
        Asserts.checkArgument(process.isSetTypeOf(), "Process must have a typeOf property");
        
        // retrieve base description by resolving typeOf reference
        Reference typeOf = process.getTypeOf();
        try
        {
            typeOf.resolveHref();
            baseProcess = (AbstractProcess)typeOf.getValue();
        }
        catch (Exception e)
        {
            throw new SMLException(
                    String.format("Cannot load base description of process '%s' from %s (uid: %s)",
                            process.getId(), typeOf.getHref(), typeOf.getTitle()), e);
        }
        
        // merge metadata
        mergeMetadataList(baseProcess.getIdentificationList(), process.getIdentificationList());
        mergeMetadataList(baseProcess.getClassificationList(), process.getClassificationList());
        mergeMetadataList(baseProcess.getCharacteristicsList(), process.getCharacteristicsList());
        mergeMetadataList(baseProcess.getCapabilitiesList(), process.getCapabilitiesList());
        mergeMetadataList(baseProcess.getContactsList(), process.getContactsList());
        
        // apply config
        applyConfig(baseProcess, process.getConfiguration());
                
        return baseProcess;
    }
    
    
    @SuppressWarnings("rawtypes")
    protected void mergeMetadataList(OgcPropertyList baseList, OgcPropertyList instanceList)
    {
        baseList.addAll(instanceList);
    }
    
    
    /**
     * Apply config to the specified process and optionally remove config when we're done
     * @param process
     * @param removeConfig if true, the configuration settings will be removed from
     * the process description
     * @throws SMLException
     */
    public void applyConfig(AbstractProcess process, boolean removeConfig) throws SMLException
    {
        // case of process chain
        if (process instanceof AggregateProcess)
        {
            // child processes must be resolved before config can be applied
            resolveLinkedProcesses((AggregateProcess)process, false);
            
            // apply config recursively on child processes
            for (AbstractProcess childProcess: ((AggregateProcess) process).getComponentList())
                applyConfig(childProcess, removeConfig);
        }
        
        // apply config on specified process
        applyConfig(process, process.getConfiguration());
        
        // remove config after it has been applied
        if (removeConfig)
            process.setConfiguration(null);
    }
    
    
    /**
     * Applies a configuration on a base process.<br/>
     * Note that the process must contain all properties that the configuration refers to.
     * @param process
     * @param settings
     * @throws SMLException 
     */
    protected void applyConfig(AbstractProcess process, Settings settings) throws SMLException
    {
        // stop here if no settings are specified
        if (settings == null)
            return;
        
        // value settings
        for (ValueSetting setting: settings.getSetValueList())
        {
            String refPath = setting.getRef();
            DataComponent comp = findTargetComponent(process, refPath);
            SWEHelper.getRootComponent(comp).assignNewDataBlock();
            
            if (comp instanceof ScalarComponent)
                comp.getData().setStringValue(setting.getValue());
            else if (comp instanceof RangeComponent)
            {
                String[] minMax = setting.getValue().split(" ");
                comp.getData().setStringValue(0, minMax[0]);
                comp.getData().setStringValue(1, minMax[1]);
            }
            else
                throw new SMLException(String.format("Value setting with path '%s' can only target a scalar or range component", refPath));
        }
        
        for (ArraySetting setting: settings.getSetArrayValuesList())
        {
            String refPath = setting.getRef();
            DataComponent comp = findTargetComponent(process, refPath);
            SWEHelper.getRootComponent(comp).assignNewDataBlock();
            
            if (!(comp instanceof DataArray))
                throw new SMLException(String.format("Array setting with path '%s' can only target a DataArray", refPath));
        }        
        
        // constraint settings
        for (ConstraintSetting setting: settings.getSetConstraintList())
        {
            String refPath = setting.getRef();
            DataComponent comp = findTargetComponent(process, refPath);
            
            if (comp instanceof HasConstraints)
            {
                try
                {
                    // TODO restrict constraint instead of replacing
                    ((HasConstraints<DataConstraint>)comp).setConstraint(setting.getValue());
                }
                catch (Exception e)
                {
                    throw new SMLException(String.format("Invalid constraint for component '%s'", refPath), e);
                }
            }
            else
                throw new SMLException("A constraint setting can only target a non-boolean simple component");
        }
        
        // make sure changes are applied to executable process
        if (process instanceof AbstractProcessImpl)
            ((AbstractProcessImpl)process).notifyParamChange();
    }
    
    
    protected DataComponent findTargetComponent(AbstractProcess process, String refPath) throws SMLException
    {
        try
        {
            LinkTarget target = SMLHelper.findComponentByPath(process, refPath);
            return target.component;
        }
        catch (Exception e)
        {
            throw new SMLException(String.format("Invalid config path '%s'", refPath), e);
        }
    }
    
    
    /**
     * Resolves all child processes included in a process chain by reference
     * @param processChain
     * @throws SMLException if one of the process cannot be resolved
     */
    public void resolveLinkedProcesses(AggregateProcess processChain) throws SMLException
    {
        resolveLinkedProcesses(processChain, true);
    }
    
    
    protected void resolveLinkedProcesses(AggregateProcess processChain, boolean recursive) throws SMLException
    {
        for (OgcProperty<AbstractProcess> prop: processChain.getComponentList().getProperties())
        {
            try
            {
                // force resolve
                if (!prop.hasValue())
                    prop.resolveHref();
                
                // call recursively on nested process chains
                if (recursive && prop.getValue() instanceof AggregateProcess)
                    resolveLinkedProcesses((AggregateProcess)prop.getValue());
            }
            catch (IOException e)
            {
                throw new SMLException(String.format("Cannot resolve process '%s'", prop.getName()), e);
            }            
        }
    }
}

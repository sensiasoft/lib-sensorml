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

import java.io.OutputStream;
import org.vast.ogc.OGCRegistry;
import org.vast.process.IProcess;
import org.vast.sensorML.system.SMLComponent;
import org.vast.sensorML.system.SMLSystem;
import org.vast.xml.DOMHelper;
import org.vast.xml.IXMLWriterDOM;
import org.vast.xml.XMLReaderException;
import org.vast.xml.XMLWriterException;
import org.w3c.dom.Element;


/**
 * <p>
 * Helper class providing a version agnostic access to SensorML
 * Process/System/Metadata readers and writers. This class delegates
 * to version specific readers/writers.
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Alexandre Robin
 * @since Apr 10, 2007
 * @version 1.0
 */
public class SMLUtils
{
	public final static String IC;
	public final static String SENSORML;
    public final static String PROCESS = "Process";
    public final static String SYSTEM = "System";
    public final static String METADATA = "Metadata";
    
    private String version = "1.0";
    private boolean versionChanged;
    private boolean readProcessMetadata;
    private boolean createExecProcess;
    private DOMHelper previousDom;
    private ProcessReader processReader = null;
    private SystemReader systemReader = null;
    private IXMLWriterDOM<SMLProcess> processWriter = null;
    private IXMLWriterDOM<SMLProcess> systemWriter = null;	
    
    
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
    
    
    public SMLUtils()
    {        
    }
    
    
    public SMLUtils(String version)
    {
        this.version = version;
    }
    
    
    public IProcess readProcess(DOMHelper dom, Element processElement) throws XMLReaderException
    {
        ProcessReader reader = getProcessReader(dom, processElement);
        return reader.read(dom, processElement);
    }
    
    
    public SMLSystem readSystem(DOMHelper dom, Element systemElement) throws XMLReaderException
    {
        SystemReader reader = getSystemReader(dom, systemElement);
        return (SMLSystem)reader.read(dom, systemElement);
    }
    
    
    public Element writeProcess(DOMHelper dom, SMLProcess process) throws XMLWriterException
    {
        IXMLWriterDOM<SMLProcess> writer = getProcessWriter();
        return writer.write(dom, process);
    }
    
    
    public Element writeSystem(DOMHelper dom, SMLSystem system) throws XMLWriterException
    {
        IXMLWriterDOM<SMLProcess> writer = getSystemWriter();
        return writer.write(dom, system);
    }
    
    
    public void write(OutputStream os, SMLProcess smlObj) throws XMLWriterException
    {
        try
        {
            DOMHelper dom = new DOMHelper("sml");
            Element rootElt = null;
            
            if (smlObj instanceof SMLComponent || smlObj instanceof SMLSystem)
                rootElt = writeSystem(dom, (SMLSystem)smlObj);
            else if (smlObj instanceof SMLProcess || smlObj instanceof SMLProcessChain)
                rootElt = writeProcess(dom, (SMLProcess)smlObj);
            
            dom.serialize(rootElt, os, true);
        }
        catch (Exception e)
        {
            throw new XMLWriterException("Error writing SensorML document", e);
        }
    }
    
    
    /**
     * Reuses or creates the ProcessReader corresponding to
     * the version specified by the SensorML namespace URI
     * @param dom
     * @param processElt
     * @return
     */
    private ProcessReader getProcessReader(DOMHelper dom, Element processElt)
    {
        if (dom == previousDom && processReader != null)
        {
            return processReader;
        }
        else
        {
            ProcessReader reader = (ProcessReader)OGCRegistry.createReader(
                                                  SENSORML,
                                                  PROCESS,
                                                  getVersion(dom, processElt));
            processReader = reader;
            processReader.setReadMetadata(readProcessMetadata);
            processReader.setCreateExecutableProcess(createExecProcess);
            return reader;
        }
    }
    
    
    /**
     * Reuses or creates the SystemReader corresponding to
     * the version specified by the SensorML namespace URI
     * @param dom
     * @param systemElt
     * @return
     */
    private SystemReader getSystemReader(DOMHelper dom, Element systemElt)
    {
        if (dom == previousDom && systemReader != null)
        {
            return systemReader;
        }
        else
        {
            SystemReader reader = (SystemReader)OGCRegistry.createReader(
                                                SENSORML,
                                                SYSTEM,
                                                getVersion(dom, systemElt));
            systemReader = reader;
            systemReader.setReadMetadata(readProcessMetadata);
            systemReader.setCreateExecutableProcess(createExecProcess);            
            return reader;
        }
    }
    
    
    /**
     * Reuses or creates the ProcessWriter corresponding to
     * the specified version (previously set by setOutputVersion)
     * @return
     */
    private IXMLWriterDOM<SMLProcess> getProcessWriter()
    {
        if (!versionChanged && processWriter != null)
        {
            return processWriter;
        }
        else
        {
            IXMLWriterDOM<SMLProcess> writer = (IXMLWriterDOM<SMLProcess>)OGCRegistry.createWriter(
                                                  SENSORML,
                                                  PROCESS,
                                                  this.version);
            processWriter = writer;
            versionChanged = false;
            return writer;
        }
    }
    
    
    /**
     * Reuses or creates the SystemWriter corresponding to
     * the specified version (previously set by setOutputVersion)
     * @return
     */
    private IXMLWriterDOM<SMLProcess> getSystemWriter()
    {
        if (!versionChanged && processWriter != null)
        {
            return systemWriter;
        }
        else
        {
            IXMLWriterDOM<SMLProcess> writer = (IXMLWriterDOM<SMLProcess>)OGCRegistry.createWriter(
                                                  SENSORML,
                                                  SYSTEM,
                                                  this.version);
            systemWriter = writer;
            versionChanged = false;
            return writer;
        }
    }
    
    
    /**
     * Logic to guess SensorML version from namespace
     * @param dom
     * @return
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
        
        previousDom = dom;
        return version;
    }
    
    
    /**
     * Used to set the SensorML version to use for XML output
     * @param version
     */
    public void setOutputVersion(String version)
    {
        this.version = version;
        this.versionChanged = true;
    }
    
    
    public void setReadMetadata(boolean readMetadata)
    {
        this.readProcessMetadata = readMetadata;
    }


    public void setCreateExecutableProcess(boolean createExecProcess)
    {
        this.createExecProcess = createExecProcess;        
    }
}

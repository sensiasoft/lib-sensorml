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

import org.vast.ogc.OGCRegistry;
import org.vast.process.DataProcess;
import org.vast.sensorML.metadata.Metadata;
import org.vast.sensorML.system.SMLSystem;
import org.vast.xml.DOMHelper;
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
	public final static String SML = "SML";
	public final static String IC = "IC";
	public final static String SENSORML = "SensorML";
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
    private MetadataReader metadataReader = null;
    private ProcessWriter processWriter = null;
    private SystemWriter systemWriter = null;
    private MetadataWriter metadataWriter = null;	
    
    
    static
    {
    	loadRegistry();
    }
    
    
    public static void loadRegistry()
    {
    	String mapFileUrl = SMLUtils.class.getResource("SMLRegistry.xml").toString();
    	OGCRegistry.loadMaps(mapFileUrl, false);
    }
    
    
    public DataProcess readProcess(DOMHelper dom, Element processElement) throws XMLReaderException
    {
        ProcessReader reader = getProcessReader(dom, processElement);
        return reader.read(dom, processElement);
    }
    
    
    public SMLSystem readSystem(DOMHelper dom, Element systemElement) throws XMLReaderException
    {
        SystemReader reader = getSystemReader(dom, systemElement);
        return (SMLSystem)reader.read(dom, systemElement);
    }
    
    
    public Metadata readMetadata(DOMHelper dom, Element objectElement) throws XMLReaderException
    {
        MetadataReader reader = getMetadataReader(dom, objectElement);
        return reader.readMetadata(dom, objectElement);
    }
    
    
    public Element writeProcess(DOMHelper dom, DataProcess process) throws XMLWriterException
    {
        ProcessWriter writer = getProcessWriter();
        return writer.writeProcess(dom, process);
    }
    
    
    public Element writeSystem(DOMHelper dom, SMLSystem system) throws XMLWriterException
    {
        SystemWriter writer = getSystemWriter();
        return writer.writeSystem(dom, system);
    }


    public void writeMetadata(DOMHelper dom, Element parentElement, Metadata metadata) throws XMLWriterException
    {
        MetadataWriter writer = getMetadataWriter();
        writer.writeMetadata(dom, parentElement, metadata);        
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
     * Reuses or creates the MetadataReader corresponding to
     * the version specified by the SensorML namespace URI
     * @param dom
     * @param smlobjElt
     * @return
     */
    private MetadataReader getMetadataReader(DOMHelper dom, Element smlobjElt)
    {
        if (dom == previousDom && processReader != null)
        {
            return metadataReader;
        }
        else
        {
            MetadataReader reader = (MetadataReader)OGCRegistry.createReader(
                                                    SENSORML,
                                                    METADATA,
                                                    getVersion(dom, smlobjElt));
            metadataReader = reader;
            return reader;
        }
    }
    
    
    /**
     * Reuses or creates the ProcessWriter corresponding to
     * the specified version (previously set by setOutputVersion)
     * @return
     */
    private ProcessWriter getProcessWriter()
    {
        if (!versionChanged && processWriter != null)
        {
            return processWriter;
        }
        else
        {
            ProcessWriter writer = (ProcessWriter)OGCRegistry.createWriter(
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
    private SystemWriter getSystemWriter()
    {
        if (!versionChanged && processWriter != null)
        {
            return systemWriter;
        }
        else
        {
            SystemWriter writer = (SystemWriter)OGCRegistry.createWriter(
                                                  SENSORML,
                                                  SYSTEM,
                                                  this.version);
            systemWriter = writer;
            versionChanged = false;
            return writer;
        }
    }
    
    
    /**
     * Reuses or creates the MetadataWriter corresponding to
     * the specified version (previously set by setOutputVersion)
     * @return
     */
    private MetadataWriter getMetadataWriter()
    {
        if (!versionChanged && processWriter != null)
        {
            return metadataWriter;
        }
        else
        {
            MetadataWriter writer = (MetadataWriter)OGCRegistry.createWriter(
                                                  SENSORML,
                                                  METADATA,
                                                  this.version);
            metadataWriter = writer;
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

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
import net.opengis.sensorml.v20.AbstractProcess;
import org.vast.ogc.OGCRegistry;
import org.vast.xml.DOMHelper;
import org.vast.xml.IXMLReaderDOM;
import org.vast.xml.IXMLWriterDOM;
import org.vast.xml.XMLReaderException;
import org.vast.xml.XMLWriterException;
import org.w3c.dom.Element;


/**
 * <p>
 * Helper class providing a version agnostic access to SensorML
 * object readers and writers. This class delegates to version specific
 * readers/writers.
 * </p>
 *
 * <p>Copyright (c) 2014</p>
 * @author Alexandre Robin
 * @since Apr 10, 2007
 * @version 1.0
 */
public class SMLUtils
{
	public final static String IC;
	public final static String SENSORML;
    public final static String PROCESS = "Process";
    
    private String version = "2.0";
    
    
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
    
    
    public AbstractProcess readProcess(DOMHelper dom, Element processElt) throws XMLReaderException
    {
        IXMLReaderDOM<AbstractProcess> reader = (IXMLReaderDOM<AbstractProcess>)OGCRegistry.createReader(
                                                    SENSORML,
                                                    PROCESS,
                                                    getVersion(dom, processElt));
        return reader.read(dom, processElt);
    }
    
    
    public Element writeProcess(DOMHelper dom, AbstractProcess process) throws XMLWriterException
    {
        IXMLWriterDOM<AbstractProcess> writer = (IXMLWriterDOM<AbstractProcess>)OGCRegistry.createWriter(
                                                    SENSORML,
                                                    PROCESS,
                                                    this.version);
        return writer.write(dom, process);
    }
    
    
    public void write(OutputStream os, AbstractProcess smlObj) throws XMLWriterException
    {
        try
        {
            DOMHelper dom = new DOMHelper("sml");
            Element rootElt = writeProcess(dom, smlObj);            
            dom.serialize(rootElt, os, true);
        }
        catch (Exception e)
        {
            throw new XMLWriterException("Error writing SensorML document", e);
        }
    }
    
    
    /**
     * Logic to guess SensorML version from namespace
     * @param dom
     * @param smlElt 
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
    
    
    /**
     * Used to set the SensorML version to use for XML output
     * @param version
     */
    public void setOutputVersion(String version)
    {
        this.version = version;
    }
}

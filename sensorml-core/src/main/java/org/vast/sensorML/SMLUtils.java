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
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import net.opengis.sensorml.v20.AbstractProcess;
import org.vast.ogc.OGCRegistry;
import org.vast.xml.DOMHelper;
import org.vast.xml.XMLBindingsUtils;
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
 * @author Alex Robin
 * @since Apr 10, 2007
 * */
public class SMLUtils extends XMLBindingsUtils
{
	public final static String IC;
	public final static String SENSORML;
    public final static String V2_0 = "2.0";
    
    
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
     * Serializes a SensorMl process to an OutputStream
     * @param os Output stream to write to
     * @param process Process object to serialize
     * @param indent Set to true to indent the output
     * @throws XMLWriterException if an error occurs while generating the XML content
     * @throws IOException if an error occurs while writing to output the stream
     */
    public void writeProcess(OutputStream os, AbstractProcess process, boolean indent) throws XMLWriterException, IOException
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
}

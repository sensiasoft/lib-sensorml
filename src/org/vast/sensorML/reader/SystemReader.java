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

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.*;
import org.vast.process.*;
import org.vast.cdm.common.CDMException;
import org.vast.cdm.common.DataEncoding;
import org.vast.cdm.reader.EncodingReader;
import org.vast.io.xml.DOMReader;
import org.vast.sensorML.SMLException;
import org.vast.sensorML.system.InterfaceDef;
import org.vast.sensorML.system.Position;
import org.vast.sensorML.system.ProtocolDef;
import org.vast.sensorML.system.ReferenceFrame;
import org.vast.sensorML.system.SMLSystem;
import org.vast.sensorML.system.SMLComponent;


/**
 * <p><b>Title:</b><br/>
 * System Reader
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Reader for System, Component, Detector, Actuator.
 * </p>
 *
 * <p>Copyright (c) 2005</p>
 * @author Alexandre Robin
 * @version 1.0
 */
public class SystemReader extends ProcessReader
{
    protected static final String dataSeparator = "/";
    protected PositionReader positionReader;
    protected EncodingReader encodingReader;
        
    
    /**
     * Constructs a SystemReader using the specified DOMReader
     * @param dom
     */
    public SystemReader(DOMReader dom)
    {
        super(dom);
        positionReader = new PositionReader(dom);
        readMetadata = true;
    }
    
    
    @Override
    public ProcessChain readProcessChain(Element processChainElement) throws SMLException
    {
        boolean isSystem = false;
        
        // find out if it's a System
        if (processChainElement.getLocalName().equals("System"))
            isSystem = true;
        else if (dom.existElement(processChainElement, "referenceFrame"))
            isSystem = true;        
        else if (dom.existElement(processChainElement, "positions"))
            isSystem = true;
        else if (dom.existElement(processChainElement, "interfaces"))
            isSystem = true;
        
        // read System or Process Chain
        if (isSystem)
            return readSystem(processChainElement);
        else
            return super.readProcessChain(processChainElement);
    }



    @Override
    public DataProcess readProcessModel(Element processModelElement) throws SMLException
    {
        // first read common process model stuffs
        DataProcess newProcess = super.readProcessModel(processModelElement);
        
        // if it is a Component also read addtional stuffs
        if (newProcess instanceof SMLComponent)
        {
            SMLComponent component = (SMLComponent)newProcess;
            
            // read position
            Element positionElt = dom.getElement(processModelElement, "position");
            if (positionElt != null)
                component.setPosition(positionReader.readPositionProperty(positionElt));
            
            // read referenceFrames
            List<ReferenceFrame> frameList = readFrameList(processModelElement);
            component.setReferenceFrames(frameList);
            
            // also add frames to the map
            for (int i=0; i<frameList.size(); i++)
                SMLSystem.frameToObjectMap.put(frameList.get(i), component);
            
            // read interfaces
            List<InterfaceDef> interfaceList = readInterfaceList(processModelElement);
            component.setInterfaces(interfaceList);
        }
        
        return newProcess;
    }
    
    
    /**
     * Reads a System element
     * @param systemElement
     * @return
     * @throws SMLException
     */
    public SMLSystem readSystem(Element systemElement) throws SMLException
    {
        // first read common process chain stuffs
        SMLSystem system = new SMLSystem();
        super.readProcessChain(systemElement, system);
        
        // read referenceFrames
        List<ReferenceFrame> frameList = readFrameList(systemElement);
        system.setReferenceFrames(frameList);
        
        // also add frames to the map
        for (int i=0; i<frameList.size(); i++)
            SMLSystem.frameToObjectMap.put(frameList.get(i), system);
        
        // read interfaces
        List<InterfaceDef> interfaceList = readInterfaceList(systemElement);
        system.setInterfaces(interfaceList);
        
        // read positions
        List<Position> positionList = readPositionList(systemElement);
        system.setComponentPositions(positionList);
        
        return system;
    }
    
    
    /**
     * Reads the whole list of reference frames attached to a given Component
     * @param componentElement
     * @return
     */
    public List<ReferenceFrame> readFrameList(Element componentElement) throws SMLException
    {
        NodeList frameElts = dom.getElements(componentElement, "referenceFrame/*");
        int listSize = frameElts.getLength();
        List<ReferenceFrame> frameList = new ArrayList<ReferenceFrame>(listSize);
        
        for (int i=0; i<listSize; i++)
        {
            Element frameElt = (Element)frameElts.item(i);
            ReferenceFrame frame = readFrame(frameElt);
            frameList.add(frame);
        }
        
        return frameList;
    }
    
    
    /**
     * Reads the whole list of interfaces for a given Component
     * @param componentElement
     * @return
     * @throws SMLException
     */
    public List<InterfaceDef> readInterfaceList(Element componentElement) throws SMLException
    {
        NodeList interfaceElts = dom.getElements(componentElement, "interfaces/InterfaceList/interface");
        int listSize = interfaceElts.getLength();
        List<InterfaceDef> interfaceList = new ArrayList<InterfaceDef>(listSize);
        
        for (int i=0; i<listSize; i++)
        {
            Element propElt = (Element)interfaceElts.item(i);
            Element interfaceElt = dom.getFirstChildElement(propElt);
            InterfaceDef interfaceDef = readInterface(interfaceElt);            
            String name = dom.getAttributeValue(propElt, "name");
            interfaceDef.setName(name);            
            interfaceList.add(interfaceDef);
        }
        
        return interfaceList;
    }
    
    
    /**
     * Reads the whole list of positions for a given System
     * @param systemElement
     * @return
     * @throws SMLException
     */
    public List<Position> readPositionList(Element systemElement) throws SMLException
    {
        NodeList positionElts = dom.getElements(systemElement, "positions/PositionList/position");
        int listSize = positionElts.getLength();
        List<Position> positionList = new ArrayList<Position>(listSize);
        
        for (int i=0; i<listSize; i++)
        {
            Element positionElt = (Element)positionElts.item(i);
            Position position = positionReader.readPositionProperty(positionElt);
            positionList.add(position);
        }
        
        return positionList;
    }
    
    
    /**
     * Reads a Reference Frame description
     * @param frameElement
     * @return
     * @throws SMLException
     */
    public ReferenceFrame readFrame(Element frameElement) throws SMLException
    {
        ReferenceFrame frame = new ReferenceFrame();
        String value;
        
        // read gml:id
        value = dom.getAttributeValue(frameElement, "id");
        frame.setId(value);
        
        // read srs name
        value = dom.getElementValue(frameElement, "srsName");
        frame.setName(value);
        
        // read cs type
        value = dom.getAttributeValue(frameElement, "usesCS/href");
        frame.setCsType(value);
        
        // read datum name
        value = dom.getElementValue(frameElement, "usesEngineeringDatum/EngineeringDatum/datumName");
        frame.setDatumName(value);
        
        // read datum description
        value = dom.getElementValue(frameElement, "usesEngineeringDatum/EngineeringDatum/anchorPoint");
        frame.setDatumDescription(value);
        
        // also add the frame to the hashtable
        URI frameURI = this.getResolvedID(frameElement, "id");
        ReferenceFrame.uriToFrameMap.put(frameURI, frame);
        
        return frame;
    }
    
    
    /**
     * Reads an Interface description
     * @param interfaceElement
     * @return
     * @throws SMLException
     */
    public InterfaceDef readInterface(Element interfaceElement) throws SMLException
    {
        InterfaceDef interfaceDef = new InterfaceDef();
        Element propElt;
        
        propElt = dom.getElement(interfaceElement, "serviceLayer");
        interfaceDef.setServiceLayerProtocol(readProtocol(propElt));
        
        propElt = dom.getElement(interfaceElement, "applicationLayer");
        interfaceDef.setApplicationLayerProtocol(readProtocol(propElt));
        
        propElt = dom.getElement(interfaceElement, "presentationLayer");
        interfaceDef.setPresentationLayerProtocol(readProtocol(propElt));
        
        propElt = dom.getElement(interfaceElement, "sessionLayer");
        interfaceDef.setSessionLayerProtocol(readProtocol(propElt));
        
        propElt = dom.getElement(interfaceElement, "transportLayer");
        interfaceDef.setTransportLayerProtocol(readProtocol(propElt));
        
        propElt = dom.getElement(interfaceElement, "networkLayer");
        interfaceDef.setNetworkLayerProtocol(readProtocol(propElt));
        
        propElt = dom.getElement(interfaceElement, "dataLinkLayer");
        interfaceDef.setDataLinkLayerProtocol(readProtocol(propElt));
        
        propElt = dom.getElement(interfaceElement, "physicalLayer");
        interfaceDef.setPhysicalLayerProtocol(readProtocol(propElt));
        
        propElt = dom.getElement(interfaceElement, "mechanicalLayer");
        interfaceDef.setMechanicalLayerProtocol(readProtocol(propElt));
        
        return interfaceDef;
    }
    
    
    /**
     * Reads a Protocol description 
     * @param protocolElement
     * @return
     * @throws SMLException
     */
    public ProtocolDef readProtocol(Element protocolProperty) throws SMLException
    {
        if (protocolProperty == null)
            return null;
        
        // get child protocol element
        Element protocolElement = dom.getFirstChildElement(protocolProperty);
        ProtocolDef protocol = new ProtocolDef();     
        
        // read definition
        String protocolType = dom.getAttributeValue(protocolElement, "definition");
        protocol.setDefinition(protocolType);
        
        // read list of protocol properties
        NodeList propElts = dom.getElements(protocolElement, "property");
        protocol.setProperties(metadataReader.readPropertyList(propElts));
        
        // read encoding if present
        try
        {
            Element encodingElt = dom.getElement(protocolElement, "encoding");
            if (encodingElt != null)
            {
                DataEncoding encoding = encodingReader.readEncodingProperty(encodingElt);
                protocol.setEncoding(encoding);
            }
        }
        catch (CDMException e)
        {
            throw new SMLException(e.getMessage(), e.getCause());
        }
        
        return protocol;
    }
}
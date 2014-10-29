/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are subject to the Mozilla Public License Version
 1.1 (the "License"); you may not use this file except in compliance with
 the License. You may obtain a copy of the License at
 http://www.mozilla.org/MPL/MPL-1.1.html
 
 Software distributed under the License is distributed on an "AS IS" basis,
 WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 for the specific language governing rights and limitations under the License.
 
 The Original Code is the "SensorML DataProcessing Engine".
 
 The Initial Developer of the Original Code is the VAST team at the University of Alabama in Huntsville (UAH). <http://vast.uah.edu> Portions created by the Initial Developer are Copyright (C) 2007 the Initial Developer. All Rights Reserved. Please Contact Mike Botts <mike.botts@uah.edu> 
 or Alexandre Robin <alex.robin@sensiasoftware.com>for more information.
  
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.sensorML;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.*;
import org.vast.xml.DOMHelper;
import org.vast.xml.XMLReaderException;
import org.vast.sensorML.system.Position;
import org.vast.sensorML.system.ReferenceFrame;
import org.vast.sensorML.system.SMLSystem;
import org.vast.sensorML.system.SMLComponent;
import org.vast.sweCommon.SweEncodingReaderV20;


/**
 * <p>
 * Reader for System, Component, Detector, Actuator.
 * This is for SensorML v2.0
 * </p>
 *
 * <p>Copyright (c) 2014</p>
 * @author Alexandre Robin
 * @version 1.0
 */
public class SystemReaderV20 extends ProcessReaderV20 implements SystemReader
{
    protected static final String dataSeparator = "/";
    protected PositionReaderV1 positionReader;
    protected SweEncodingReaderV20 encodingReader;
        
    
    /**
     * Constructs a SystemReader using the specified DOMReader
     * @param dom
     */
    public SystemReaderV20()
    {
        positionReader = new PositionReaderV1();
        readMetadata = true;
    }
    
    
    @Override
    public SMLProcess read(DOMHelper dom, Element systemElement) throws XMLReaderException
    {
        // first read common process chain stuffs
        SMLSystem system = new SMLSystem();
        super.readProcessChain(dom, systemElement, system);
        
        // read referenceFrames
        List<ReferenceFrame> frameList = readFrameList(dom, systemElement);
        system.setReferenceFrames(frameList);
        
        // also add frames to the map
        for (int i=0; i<frameList.size(); i++)
            SMLSystem.frameToObjectMap.put(frameList.get(i), system);
        
        // read positions
        List<Position> positionList = readPositionList(dom, systemElement);
        system.setComponentPositions(positionList);
        
        return system;
    }



    @Override
    protected SMLProcess readProcessModel(DOMHelper dom, Element processModelElement) throws XMLReaderException
    {
        // first read common process model stuffs
        SMLProcess newProcess = super.readProcessModel(dom, processModelElement);
        
        // if it is a Component also read addtional stuffs
        if (newProcess instanceof SMLComponent)
        {
            SMLComponent component = (SMLComponent)newProcess;
            
            // read position
            Element positionElt = dom.getElement(processModelElement, "position");
            if (positionElt != null)
                component.setPosition(positionReader.readPositionProperty(dom, positionElt));
            
            // read referenceFrames
            List<ReferenceFrame> frameList = readFrameList(dom, processModelElement);
            component.setReferenceFrames(frameList);
            
            // also add frames to the map
            for (int i=0; i<frameList.size(); i++)
                SMLSystem.frameToObjectMap.put(frameList.get(i), component);
        }
        
        return newProcess;
    }
    
    
    /**
     * Reads the whole list of reference frames attached to a given Component
     * @param componentElement
     * @return
     */
    public List<ReferenceFrame> readFrameList(DOMHelper dom, Element componentElement) throws XMLReaderException
    {
        NodeList frameElts = dom.getElements(componentElement, "referenceFrame/*");
        int listSize = frameElts.getLength();
        List<ReferenceFrame> frameList = new ArrayList<ReferenceFrame>(listSize);
        
        for (int i=0; i<listSize; i++)
        {
            Element frameElt = (Element)frameElts.item(i);
            ReferenceFrame frame = readFrame(dom, frameElt);
            frameList.add(frame);
        }
        
        return frameList;
    }

    
    
    /**
     * Reads the whole list of positions for a given System
     * @param systemElement
     * @return
     * @throws SMLException
     */
    public List<Position> readPositionList(DOMHelper dom, Element systemElement) throws XMLReaderException
    {
        NodeList positionElts = dom.getElements(systemElement, "positions/PositionList/position");
        int listSize = positionElts.getLength();
        List<Position> positionList = new ArrayList<Position>(listSize);
        
        for (int i=0; i<listSize; i++)
        {
            Element positionElt = (Element)positionElts.item(i);
            Position position = positionReader.readPositionProperty(dom, positionElt);
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
    public ReferenceFrame readFrame(DOMHelper dom, Element frameElement) throws XMLReaderException
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
        URI frameURI = this.getResolvedID(dom, frameElement, "id");
        ReferenceFrame.uriToFrameMap.put(frameURI, frame);
        
        return frame;
    }

}
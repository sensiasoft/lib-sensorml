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

import java.net.URI;
import org.vast.cdm.common.CDMException;
import org.vast.cdm.common.DataComponent;
import org.vast.xml.DOMHelper;
import org.vast.process.DataProcess;
import org.vast.sensorML.system.Position;
import org.vast.sensorML.system.PositionData;
import org.vast.sensorML.system.PositionProcess;
import org.vast.sensorML.system.ReferenceFrame;
import org.vast.sensorML.system.SMLSystem;
import org.vast.sensorML.system.SMLComponent;
import org.vast.sweCommon.SWECommonUtils;
import org.w3c.dom.Element;


/**
 * <p><b>Title:</b><br/>
 * Position Reader
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Reader for Position Data
 * </p>
 *
 * <p>Copyright (c) 2005</p>
 * @author Alexandre Robin
 * @version 1.0
 */
public class PositionReaderV1 extends AbstractSMLReader
{
    protected SWECommonUtils utils;
    protected ProcessReaderV1 processReader;
        
    
    /**
     * Constructs a PositionReader using the specified DOMReader
     * @param dom
     */
    public PositionReaderV1()
    {
        utils = new SWECommonUtils();
        processReader = new ProcessReaderV1();
    }

    
    /**
     * Reads a property element containing a Process or Position Data 
     * @param positionPropertyElt
     * @return
     * @throws SMLException
     */
    public Position readPositionProperty(DOMHelper dom, Element positionPropertyElt) throws SMLException
    {
        Element positionElt = dom.getFirstChildElement(positionPropertyElt);
        Position position = readPosition(dom, positionElt);
        
        // read name
        String name = dom.getAttributeValue(positionPropertyElt, "name");
        position.setName(name);
        
        return position;
    }
    
    
    /**
     * Reads Process or Position Data element
     * @param positionElement
     * @return
     * @throws SMLException
     */
    public Position readPosition(DOMHelper dom, Element positionElement) throws SMLException
    {
        Position position;
        
        if (dom.existElement(positionElement, "outputs"))
            position = readPositionProcess(dom, positionElement);
        else
            position = readPositionData(dom, positionElement);
        
        return position;
    }
    
    
    /**
     * Reads a positioning process using a Process Reader
     * @param positionProcessElement
     * @return
     * @throws SMLException
     */
    public PositionProcess readPositionProcess(DOMHelper dom, Element positionProcessElement) throws SMLException
    {
        return null;
    }
    
    
    /**
     * Reads position data
     * @param positionDataElement
     * @return
     * @throws SMLException
     */
    public PositionData readPositionData(DOMHelper dom, Element positionDataElement) throws SMLException
    {
        PositionData position = new PositionData();
        
        try
        {
            DataComponent data = utils.readComponent(dom, positionDataElement);
            position.setData(data);
            
            // find reference frame object and assign it to Position
            URI refFrameURI = this.getResolvedIDRef(dom, positionDataElement, "referenceFrame");
            ReferenceFrame refFrame = ReferenceFrame.uriToFrameMap.get(refFrameURI);
            position.setReferenceFrame(refFrame);
            
            // find local frame object and assign it to Position
            URI locFrameURI = this.getResolvedIDRef(dom, positionDataElement, "localFrame");
            ReferenceFrame locFrame = ReferenceFrame.uriToFrameMap.get(locFrameURI);
            position.setLocalFrame(locFrame);
            
            // also assign this position to the corresponding component
            DataProcess component = SMLSystem.frameToObjectMap.get(locFrame);
            if (component instanceof SMLComponent)
                ((SMLComponent)component).setPosition(position);
        }
        catch (CDMException e)
        {
            throw new SMLException(e.getMessage(), e.getCause());
        }
        
        return position;
    }
}
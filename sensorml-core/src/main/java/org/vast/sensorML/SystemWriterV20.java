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

 Please Contact Alexandre Robin <alex.robin@sensiasoftware.com> or
 Mike Botts <mike.botts@uah.edu> for more information.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.sensorML;

import java.text.NumberFormat;
import org.vast.cdm.common.DataComponent;
import org.vast.ogc.OGCRegistry;
import org.vast.ogc.gml.GMLFeatureWriter;
import org.vast.ogc.gml.GMLTimeWriter;
import org.vast.ogc.gml.GMLUtils;
import org.vast.sensorML.metadata.KeywordList;
import org.vast.sensorML.metadata.Metadata;
import org.vast.sensorML.metadata.MetadataList;
import org.vast.sensorML.metadata.Term;
import org.vast.sweCommon.SWECommonUtils;
import org.vast.sweCommon.SweComponentWriterV20;
import org.vast.xml.DOMHelper;
import org.vast.xml.IXMLWriterDOM;
import org.vast.xml.XMLWriterException;
import org.w3c.dom.Element;


/**
 * <p>
 * System Writer for SensorML version 2.0
 * </p>
 *
 * <p>Copyright (c) 2014</p>
 * @author Alexandre Robin
 * @since Feb 6, 2014
 */
public class SystemWriterV20 implements IXMLWriterDOM<SMLProcess>
{
    private static String GML_VERSION = "3.2";
    protected GMLTimeWriter timeWriter;
    protected GMLFeatureWriter featureWriter;
    protected SweComponentWriterV20 sweWriter;
    protected int currentId;
    protected NumberFormat idFormatter;
    
    
    public SystemWriterV20()
    {
        timeWriter = new GMLTimeWriter(GML_VERSION);
        featureWriter = new GMLFeatureWriter();
        featureWriter.setGmlVersion(GML_VERSION);
        sweWriter = new SweComponentWriterV20();
        
        currentId = 1;
        idFormatter = NumberFormat.getNumberInstance();
        idFormatter.setMinimumIntegerDigits(3);
        idFormatter.setGroupingUsed(false);
    }
    
    
    public Element write(DOMHelper dom, SMLProcess smlProcess) throws XMLWriterException
    {
        dom.addUserPrefix("sml", OGCRegistry.getNamespaceURI(SMLUtils.SENSORML, "2.0"));
        dom.addUserPrefix("swe", OGCRegistry.getNamespaceURI(SWECommonUtils.SWE, "2.0"));
        dom.addUserPrefix("gml", OGCRegistry.getNamespaceURI(GMLUtils.GML, "3.2"));
        dom.addUserPrefix("xlink", OGCRegistry.getNamespaceURI(OGCRegistry.XLINK));
        
        Element sysElt = dom.createElement("sml:PhysicalSystem");
        
        if (smlProcess.getDescription() != null)
            dom.setElementValue(sysElt, "gml:description", smlProcess.getDescription());
        
        dom.setElementValue(sysElt, "gml:identifier", smlProcess.getIdentifier());
        
        if (smlProcess.getName() != null)
            dom.setElementValue(sysElt, "gml:name", smlProcess.getName());
        
        // metadata
        Metadata metadata = smlProcess.getMetadata();
        if (metadata != null)
        {
            writeKeywordList(dom, sysElt, "sml:keywords/sml:KeywordList", "sml:keyword", metadata.getKeywords());
            writeTermList(dom, sysElt, "sml:identification/sml:IdentifierList", "sml:identifier", metadata.getIdentifiers());
            writeTermList(dom, sysElt, "sml:classification/sml:ClassifierList", "sml:classifier", metadata.getClassifiers());
            writePropertyList(dom, sysElt, "sml:characteristics/sml:CharacteristicList", "sml:characteristic", metadata.getCharacteristics());
            writePropertyList(dom, sysElt, "sml:capability/sml:CapabilitiesList", "sml:capability", metadata.getCapabilities());
        }
        
        // IOs
        writeIOList(dom, sysElt, "sml:inputs/sml:InputList", "sml:input", smlProcess.getInputList());
        writeIOList(dom, sysElt, "sml:outputs/sml:OutputList", "sml:output", smlProcess.getOutputList());
        writeIOList(dom, sysElt, "sml:parameters/sml:ParameterList", "sml:parameter", smlProcess.getParameterList());
        
        return sysElt;
    }
    
    
    protected void writeCommonListProperties(DOMHelper dom, Element listElt, MetadataList<?> metadataList)
    {
        if (metadataList.getLocalId() != null)
            dom.setAttributeValue(listElt, "id", metadataList.getLocalId());
        
        if (metadataList.getIdentifier() != null)
            dom.setElementValue(listElt, "swe:identifier", metadataList.getIdentifier());
        
        if (metadataList.getLabel() != null)
            dom.setElementValue(listElt, "swe:label", metadataList.getLabel());
        
        if (metadataList.getDescription() != null)
            dom.setElementValue(listElt, "swe:description", metadataList.getDescription());
    }
    
    
    protected void writeKeywordList(DOMHelper dom, Element parentElt, String listPath, String itemName, KeywordList keywordList) throws XMLWriterException
    {
        if (keywordList == null)
            return;
        
        Element listElt = dom.addElement(parentElt, listPath);
        writeCommonListProperties(dom, listElt, keywordList);
        
        if (keywordList.getCodespace() != null)
            dom.setAttributeValue(listElt, "sml:codeSpace/xlink:href", keywordList.getCodespace());
        
        for (String keyword: keywordList)
            dom.setElementValue(listElt, "+"+itemName, keyword);
    }
    
    
    protected void writeTermList(DOMHelper dom, Element parentElt, String listPath, String itemName, MetadataList<Term> termList) throws XMLWriterException
    {
        if (termList == null)
            return;
        
        Element listElt = dom.addElement(parentElt, listPath);
        writeCommonListProperties(dom, listElt, termList);
        
        for (Term term: termList)
        {
            Element termElt = dom.addElement(listElt, "+" + itemName + "/sml:Term");
            
            if (term.getDefinition() != null)
                dom.setAttributeValue(termElt, "definition", term.getDefinition());
            dom.setElementValue(termElt, "sml:label", term.getName());            
            if (term.getCodespace() != null)
                dom.setAttributeValue(termElt, "codeSpace/xlink:href", term.getCodespace());
            dom.setElementValue(termElt, "sml:value", term.getValue());
        }
    }
    
    
    protected void writePropertyList(DOMHelper dom, Element parentElt, String listPath, String itemName, MetadataList<DataComponent> propertyList) throws XMLWriterException
    {
        if (propertyList == null)
            return;
        
        Element listElt = dom.addElement(parentElt, listPath);
        writeCommonListProperties(dom, listElt, propertyList);
        
        for (DataComponent comp: propertyList)
        {
            Element propElt = sweWriter.addComponentProperty(dom, listElt, "+"+itemName, comp, true);
            propElt.setAttribute("name", comp.getName());
        }
    }
    
    
    protected void writeIOList(DOMHelper dom, Element parentElt, String listPath, String itemName, DataComponent ioList) throws XMLWriterException
    {
        int numComponents = ioList.getComponentCount();
        if (ioList != null &&  numComponents > 0)
        {
            Element ioListElt = dom.addElement(parentElt, listPath);            
            for (int i = 0; i < numComponents; i++)
            {
                Element propElt = sweWriter.addComponentProperty(dom, ioListElt, "+"+itemName, ioList.getComponent(i), false);
                propElt.setAttribute("name", ioList.getComponent(i).getName());
            }
        }
    }
}

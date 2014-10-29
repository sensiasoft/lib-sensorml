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

package org.vast.sensorML.metadata;

import java.text.NumberFormat;
import org.vast.cdm.common.DataComponent;
import org.vast.ogc.gml.GMLFeatureWriter;
import org.vast.ogc.gml.GMLTimeWriter;
import org.vast.sweCommon.SweComponentWriterV20;
import org.vast.util.Contact;
import org.vast.util.ResponsibleParty;
import org.vast.xml.DOMHelper;
import org.vast.xml.XMLWriterException;
import org.w3c.dom.Element;


/**
 * <p>
 * Metadata writer for SensorML version 2.0
 * </p>
 *
 * <p>Copyright (c) 2014</p>
 * @author Alexandre Robin
 * @since Feb 6, 2014
 */
public class MetadataWriterV20
{
    private static String GML_VERSION = "3.2";
    private static String GMD_NS_URI = "http://www.isotc211.org/2005/gmd";
    private static String GCO_NS_URI = "http://www.isotc211.org/2005/gco";
    protected GMLTimeWriter timeWriter;
    protected GMLFeatureWriter featureWriter;
    protected SweComponentWriterV20 sweWriter;
    protected int currentId;
    protected NumberFormat idFormatter;
    
    
    public MetadataWriterV20()
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
    
    
    public void writeMetadata(DOMHelper dom, Element parentElt, Metadata metadata) throws XMLWriterException
    {
        for (IMetadataList<String> metadataList: metadata.getKeywordsLists())
            writeKeywordList(dom, parentElt, "+sml:keywords/sml:KeywordList", "sml:keyword", metadataList);
        
        for (IMetadataList<Term> metadataList: metadata.getIdentifiersLists())
            writeTermList(dom, parentElt, "+sml:identification/sml:IdentifierList", "sml:identifier", metadataList);
        
        for (IMetadataList<Term> metadataList: metadata.getClassifiersLists())
            writeTermList(dom, parentElt, "+sml:classification/sml:ClassifierList", "sml:classifier", metadataList);
        
        for (IMetadataList<DataComponent> metadataList: metadata.getCharacteristicsLists())
            writePropertyList(dom, parentElt, "+sml:characteristics/sml:CharacteristicList", "sml:characteristic", metadataList);
        
        for (IMetadataList<DataComponent> metadataList: metadata.getCapabilitiesLists())
            writePropertyList(dom, parentElt, "+sml:capabilities/sml:CapabilitiesList", "sml:capability", metadataList);
        
        for (IMetadataList<Contact> metadataList: metadata.getContactsLists())
            writeContactList(dom, parentElt, "+sml:contacts/sml:ContactList", "sml:contact", metadataList);
        
        for (IMetadataList<DocumentRef> metadataList: metadata.getDocumentsLists())
            writeDocumentList(dom, parentElt, "+sml:documentation/sml:DocumentList", "sml:document", metadataList);
    }
    
    
    protected void writeCommonListProperties(DOMHelper dom, Element listElt, IMetadataList<?> metadataList)
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
    
    
    public void writeKeywordList(DOMHelper dom, Element parentElt, String listPath, String itemName, IMetadataList<String> keywordList) throws XMLWriterException
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
    
    
    public void writeTermList(DOMHelper dom, Element parentElt, String listPath, String itemName, IMetadataList<Term> termList) throws XMLWriterException
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
    
    
    public void writePropertyList(DOMHelper dom, Element parentElt, String listPath, String itemName, IMetadataList<DataComponent> propertyList) throws XMLWriterException
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
    
    
    public void writeDocumentList(DOMHelper dom, Element parentElt, String listPath, String itemName, IMetadataList<DocumentRef> docList) throws XMLWriterException
    {
        if (docList == null)
            return;
        
        Element listElt = dom.addElement(parentElt, listPath);
        writeCommonListProperties(dom, listElt, docList);
        dom.addUserPrefix("gmd", GMD_NS_URI);
        dom.addUserPrefix("gco", GCO_NS_URI);
        
        for (DocumentRef docRef: docList)
        {
            Element docElt = dom.addElement(listElt, "+" + itemName + "/gmd:CI_OnlineResource");
            String val;
            
            if ((val = docRef.getFileLocation()) != null)
                dom.setElementValue(docElt, "gmd:linkage/gmd:URL", val);
            if ((val = docRef.getName()) != null)
                dom.setElementValue(docElt, "gmd:name/gco:CharacterString", val);
            if ((val = docRef.getDescription()) != null)
                dom.setElementValue(docElt, "gmd:description/gco:CharacterString", val);         
        }
    }
    
    
    public void writeContactList(DOMHelper dom, Element parentElt, String listPath, String itemName, IMetadataList<Contact> contactList) throws XMLWriterException
    {
        if (contactList == null)
            return;
        
        Element listElt = dom.addElement(parentElt, listPath);
        writeCommonListProperties(dom, listElt, contactList);
        dom.addUserPrefix("gmd", GMD_NS_URI);
        dom.addUserPrefix("gco", GCO_NS_URI);
        
        for (Contact contact: contactList)
        {
            if (contact instanceof ResponsibleParty)
            {
                ResponsibleParty respParty = (ResponsibleParty)contact;
                Element respPartyElt = dom.addElement(listElt, "+" + itemName + "/gmd:CI_ResponsibleParty");
                String val;
                
                if ((val =  respParty.getIndividualName()) != null)
                    dom.setElementValue(respPartyElt, "gmd:individualName/gco:CharacterString", val);
                if ((val =  respParty.getOrganizationName()) != null)
                    dom.setElementValue(respPartyElt, "gmd:organisationName/gco:CharacterString", val);                
                if ((val =  respParty.getPositionName()) != null)
                    dom.setElementValue(respPartyElt, "gmd:positionName/gco:CharacterString", val);
                
                String contactInfoPath = "gmd:contactInfo/gmd:CI_Contact/";
                for (String voiceNumber: respParty.getVoiceNumbers())
                    dom.setElementValue(respPartyElt, contactInfoPath + "gmd:phone/gmd:CI_Telephone/+gmd:voice/gco:CharacterString", voiceNumber);
                for (String faxNumber: respParty.getFaxNumbers())
                    dom.setElementValue(respPartyElt, contactInfoPath + "gmd:phone/gmd:CI_Telephone/+gmd:facsimile/gco:CharacterString", faxNumber);
                
                String addressPath = contactInfoPath + "gmd:address/gmd:CI_Address/";
                for (String deliveryPoint: respParty.getDeliveryPoints())
                    dom.setElementValue(respPartyElt, addressPath + "+gmd:deliveryPoint/gco:CharacterString", deliveryPoint);
                if ((val =  respParty.getCity()) != null)
                    dom.setElementValue(respPartyElt, addressPath + "gmd:city/gco:CharacterString", val);
                if ((val =  respParty.getAdministrativeArea()) != null)
                    dom.setElementValue(respPartyElt, addressPath + "gmd:administrativeArea/gco:CharacterString", val);
                if ((val =  respParty.getPostalCode()) != null)
                    dom.setElementValue(respPartyElt, addressPath + "gmd:postalCode/gco:CharacterString", val);
                if ((val =  respParty.getCountry()) != null)
                    dom.setElementValue(respPartyElt, addressPath + "gmd:country/gco:CharacterString", val);
                for (String email: respParty.getEmails())
                    dom.setElementValue(respPartyElt, addressPath + "+gmd:electronicMailAddress/gco:CharacterString", email);
                
                if ((val =  respParty.getWebsite()) != null)
                    dom.setElementValue(respPartyElt, contactInfoPath + "gmd:onlineResource/gmd:CI_OnlineResource/gmd:linkage/gmd:URL", val);
                if ((val =  respParty.getHoursOfService()) != null)
                    dom.setElementValue(respPartyElt, contactInfoPath + "gmd:hoursOfService/gco:CharacterString", val);
                if ((val =  respParty.getContactInstructions()) != null)
                    dom.setElementValue(respPartyElt, contactInfoPath + "gmd:contactInstructions/gco:CharacterString", val);
                
                String rolePath = "gmd:role/gmd:CI_RoleCode/";
                if ((val =  respParty.getRole()) != null)
                {
                    if (respParty.getRoleCodeList() != null)
                        dom.setAttributeValue(respPartyElt, rolePath + "@codeList", respParty.getRoleCodeList());                    
                    dom.setAttributeValue(respPartyElt, rolePath + "@codeListValue", val);
                }
            }
            else if (contact instanceof Person)
            {
                
            }
        }
    }
}

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

package org.vast.sensorML.metadata;

import java.text.ParseException;
import java.util.List;
import org.w3c.dom.*;
import org.vast.cdm.common.DataComponent;
import org.vast.xml.DOMHelper;
import org.vast.xml.XMLReaderException;
import org.vast.ogc.xlink.IReferenceResolver;
import org.vast.ogc.xlink.XlinkUtils;
import org.vast.sensorML.AbstractSMLReader;
import org.vast.sweCommon.SweComponentReaderV20;
import org.vast.util.*;


/**
 * <p>
 * Reader for Metadata group, including identifiers, classifiers,
 * constraints, contacts, documentation, characteristics and
 * capabilities for SensorML version 0.
 * </p>
 *
 * <p>Copyright (c) 2005</p>
 * @author Alexandre Robin
 * @version 1.0
 */
public class MetadataReaderV20 extends AbstractSMLReader
{
    protected SweComponentReaderV20 sweReader;


    /**
     * Constructs a MetadataReader using the specified DOMReader
     * @param parentReader
     */
    public MetadataReaderV20()
    {
        sweReader = new SweComponentReaderV20();
    }


    /* (non-Javadoc)
     * @see org.vast.sensorML.reader.MetadataReader#readMetadata(org.vast.xml.DOMHelper, org.w3c.dom.Element)
     */
    public Metadata readMetadata(DOMHelper dom, Element objElt) throws XMLReaderException
    {
        Metadata metadata = new Metadata();        
        readMetadataLists(dom, objElt, "keywords", metadata.getKeywordsLists());
        readMetadataLists(dom, objElt, "identification", metadata.getIdentifiersLists());
        readMetadataLists(dom, objElt, "classification", metadata.getClassifiersLists());
        readMetadataLists(dom, objElt, "characteristics", metadata.getCharacteristicsLists());
        readMetadataLists(dom, objElt, "capabilities", metadata.getCapabilitiesLists());
        readMetadataLists(dom, objElt, "documentation", metadata.getDocumentsLists());
        readMetadataLists(dom, objElt, "contacts", metadata.getContactsLists());
        return metadata;
    }
    
    
    @SuppressWarnings("rawtypes")
    protected void readMetadataLists(DOMHelper dom, Element parentElt, String listPath, List listGroup) throws XMLReaderException
    {
        NodeList listPropElts = dom.getElements(parentElt, listPath);
        for (int i=0; i<listPropElts.getLength(); i++)
        {        
            IMetadataList<?> metadataList;
            Element listPropElt = (Element)listPropElts.item(i);
            
            // if no children elements are present, try to parse xlink
            if (dom.getChildElements(listPropElt).getLength() == 0)
            {                
                metadataList = readListRef(dom, listPropElt);
            }
            
            // else parse child content
            else 
            {
                Element listElt = dom.getFirstChildElement(listPropElt);
                metadataList = readMetadataList(dom, listElt);
            }
            
            if (metadataList != null)
                listGroup.add(metadataList);
        }
    }
    
    
    @SuppressWarnings("rawtypes")
    public IMetadataList<?> readMetadataList(DOMHelper dom, Element listElt) throws XMLReaderException
    {        
        IMetadataList metadataList = null;
        String listName = listElt.getLocalName();
        String itemName = listName.substring(0, listName.indexOf("List")).toLowerCase();
        NodeList listItemElts = dom.getElements(listElt, itemName);
        int listSize = listItemElts.getLength();
        metadataList = new MetadataList(listSize);
        
        if (listName.equals("KeywordList")) 
        {
            metadataList.setCodespace(dom.getAttributeValue(listElt, "codeSpace/href"));
            
            for (int k=0; k<listSize; k++)
            {
                Element keywordElt = (Element) listItemElts.item(k);
                metadataList.add(dom.getElementValue(keywordElt));
            }
        }
        
        else if (listName.equals("IdentifierList") || listName.equals("ClassifierList"))
        {
            for (int i = 0; i < listSize; i++)
            {
                Element propElt = (Element) listItemElts.item(i);
                Element termElt = dom.getFirstChildElement(propElt);
                Term term = readTerm(dom, termElt);
                metadataList.add(term);
            }
        }
        
        else if (listName.equals("DocumentList"))
        {
            for (int i = 0; i < listSize; i++)
            {
                Element propElt = (Element) listItemElts.item(i);
                Element documentElt = dom.getFirstChildElement(propElt);
                DocumentRef document = readDocument(dom, documentElt);
                document.setRole(dom.getAttributeValue(propElt, "role"));
                metadataList.add(document);
            }
        }
        
        else if (listName.equals("ContactList"))
        {
            for (int i = 0; i < listSize; i++)
            {
                Element propElt = (Element) listItemElts.item(i);
                Element contactElt = dom.getFirstChildElement(propElt);
                Contact contact;
                if (contactElt != null)
                    contact = readResponsibleParty(dom, contactElt);
                else
                    contact = new ResponsibleParty();
                contact.setRole(dom.getAttributeValue(propElt, "role"));

                String uri;
                if ((uri = dom.getAttributeValue(propElt, "href")) != null)
                {
                    contact.setHrefPresent(true);
                    contact.setHrefUri(uri);
                }
                else
                    contact.setHrefPresent(false);

                metadataList.add(contact);
            }
        }
        
        else if (listName.equals("CharacteristicList") || listName.equals("CapabilityList"))
        {
            for (int i = 0; i < listSize; i++)
            {
                Element propElt = (Element) listItemElts.item(i);
                DataComponent data = sweReader.readComponentProperty(dom, propElt);
                metadataList.add(data);
            }
        }
        
        else
            throw new XMLReaderException("Invalid metadata content: " + listName);
        
        readCommonListProperties(dom, listElt, metadataList);
        return metadataList;
    }
    
    
    protected MetadataListRef<?> readListRef(DOMHelper dom, Element listPropElt)
    {
        IReferenceResolver<IMetadataList<?>> resolver = new IReferenceResolver<IMetadataList<?>>()
        {
            public IMetadataList<?> fetchTarget(String uri) throws RuntimeException
            {
                try
                {
                    DOMHelper targetDom = new DOMHelper(uri, false);
                    return readMetadataList(targetDom, targetDom.getBaseElement());
                }
                catch (Exception e)
                {
                    throw new RuntimeException("Error while parsing content fetched via xlink", e);
                }
            }            
        };
        
        @SuppressWarnings("rawtypes")
        MetadataListRef<?> listRef = new MetadataListRef(resolver);        
        XlinkUtils.readXlinkAttributes(dom, listPropElt, listRef);
        
        if (listRef.getHref() != null)
            return listRef;
        else 
            return null;        
    }
    
    
    protected void readCommonListProperties(DOMHelper dom, Element listElt, IMetadataList<?> metadataList)
    {
        metadataList.setLocalId(dom.getAttributeValue(listElt, "id"));
        metadataList.setDescription(dom.getElementValue(listElt, "description"));
        metadataList.setLabel(dom.getElementValue(listElt, "label"));
        metadataList.setIdentifier(dom.getElementValue(listElt, "identifier"));
    }


    protected Term readTerm(DOMHelper dom, Element termElt) throws XMLReaderException
    {
        Term term = new Term();        
        term.setDefinition(dom.getAttributeValue(termElt, "definition"));
        term.setCodespace(dom.getAttributeValue(termElt, "codeSpace/href"));
        term.setName(dom.getElementValue(termElt, "label"));
        term.setValue(dom.getElementValue(termElt, "value"));
        return term;
    }


    protected DocumentRef readDocument(DOMHelper dom, Element documentElement) throws XMLReaderException
    {
        DocumentRef document = new DocumentRef();
        String value;
        
        value = dom.getElementValue(documentElement, "description/*");
        document.setDescription(value);
        value = dom.getElementValue(documentElement, "name/*");
        document.setVersion(value);
        value = dom.getElementValue(documentElement, "linkage/*");
        document.setFileLocation(value);
        
        return document;
    }


    protected ResponsibleParty readResponsibleParty(DOMHelper dom, Element partyElement) throws XMLReaderException
    {
        ResponsibleParty party = new ResponsibleParty();
        String value;
        NodeList elts;
        
        value = dom.getElementValue(partyElement, "individualName/*");
        party.setIndividualName(value);
        value = dom.getElementValue(partyElement, "organisationName/*");
        party.setOrganizationName(value);
        value = dom.getElementValue(partyElement, "positionName/*");
        party.setPositionName(value);

        String contactPath = "contactInfo/CI_Contact/";
        elts = dom.getElements(partyElement, contactPath + "phone/CI_Telephone/voice/*");
        for (int i = 0; i < elts.getLength(); i++)
            party.getVoiceNumbers().add(dom.getElementValue((Element)elts.item(i)));
        elts = dom.getElements(partyElement, contactPath + "phone/CI_Telephone/fascimile/*");
        for (int i = 0; i < elts.getLength(); i++)
            party.getFaxNumbers().add(dom.getElementValue((Element)elts.item(i)));

        String addressPath = contactPath + "address/CI_Address/";
        elts = dom.getElements(partyElement, addressPath + "deliveryPoint/*");
        for (int i = 0; i < elts.getLength(); i++)
            party.getDeliveryPoints().add(dom.getElementValue((Element)elts.item(i)));

        value = dom.getElementValue(partyElement, addressPath + "city/*");
        party.setCity(value);
        value = dom.getElementValue(partyElement, contactPath + "administrativeArea/*");
        party.setAdministrativeArea(value);
        value = dom.getElementValue(partyElement, addressPath + "postalCode/*");
        party.setPostalCode(value);
        value = dom.getElementValue(partyElement, addressPath + "country/*");
        party.setCountry(value);
        
        // read address electronicMailAddress
        elts = dom.getElements(partyElement, addressPath + "electronicMailAddress/*");
        for (int i = 0; i < elts.getLength(); i++)
            party.getEmails().add(dom.getElementValue((Element)elts.item(i)));
        
        value = dom.getElementValue(partyElement, contactPath + "hoursOfService/*");
        party.setHoursOfService(value);
        value = dom.getElementValue(partyElement, contactPath + "contactInstructions/*");
        party.setContactInstructions(value);
        
        return party;
    }


    protected DateTime readDate(DOMHelper dom, Element dateElement) throws XMLReaderException
    {       
        try
        {
            String isoDate = dom.getElementValue(dateElement, "");
            return new DateTime(DateTimeFormat.parseIso(isoDate));
        }
        catch (ParseException e)
        {
            ExceptionSystem.display(e);
            return null;
        }
    }
}
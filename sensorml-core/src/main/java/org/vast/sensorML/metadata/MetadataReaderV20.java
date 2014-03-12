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
import org.w3c.dom.*;
import org.vast.cdm.common.DataComponent;
import org.vast.xml.DOMHelper;
import org.vast.xml.XMLReaderException;
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
    public Metadata readMetadata(DOMHelper dom, Element objectElement) throws XMLReaderException
    {
        Metadata metadata = new Metadata();
        Element listElt;
        
        // keywords
        listElt = dom.getElement(objectElement, "keywords/KeywordList");
        metadata.setKeywords(readKeywordList(dom, listElt, "keyword"));
        
        // identifiers
        listElt = dom.getElement(objectElement, "identification/IdentifierList");
        metadata.setIdentifiers(readTermList(dom, listElt, "identifier"));

        // classifiers
        listElt = dom.getElement(objectElement, "classification/ClassifierList");
        metadata.setClassifiers(readTermList(dom, listElt, "classifier"));

        // characteristics
        listElt = dom.getElement(objectElement, "characteristics/CharacteristicList");
        metadata.setCharacteristics(readPropertyList(dom, listElt, "characteristic"));

        // capabilities
        listElt = dom.getElement(objectElement, "capabilities/CapabilityList");
        metadata.setCapabilities(readPropertyList(dom, listElt, "capability"));

        // documents
        listElt = dom.getElement(objectElement, "documentation/DocumentList");
        metadata.setDocuments(readDocumentList(dom, listElt, "document"));

        // contacts
        listElt = dom.getElement(objectElement, "contact/ContactList");
        metadata.setContacts(readContactList(dom, listElt, "contact"));

        return metadata;
    }
    
    
    protected void readCommonListProperties(DOMHelper dom, Element listElt, MetadataList<?> metadataList)
    {
        metadataList.setLocalId(dom.getAttributeValue(listElt, "id"));
        metadataList.setDescription(dom.getElementValue(listElt, "description"));
        metadataList.setLabel(dom.getElementValue(listElt, "label"));
        metadataList.setIdentifier(dom.getElementValue(listElt, "identifier"));
    }


    protected KeywordList readKeywordList(DOMHelper dom, Element listElt, String itemName) throws XMLReaderException
    {
        if (listElt == null)
            return null;
        
        NodeList termPropertyElts = dom.getElements(listElt, itemName);
        int listSize = termPropertyElts.getLength();        
        KeywordList keywordList = new KeywordList(listSize);
        
        readCommonListProperties(dom, listElt, keywordList);
        keywordList.setCodespace(dom.getAttributeValue(listElt, "codeSpace/href"));
        
        for (int i = 0; i < listSize; i++)
        {
            Element keywordElt = (Element) termPropertyElts.item(i);
            keywordList.add(dom.getElementValue(keywordElt));
        }

        return keywordList;
    }
    
    
    protected MetadataList<Term> readTermList(DOMHelper dom, Element listElt, String itemName) throws XMLReaderException
    {
        if (listElt == null)
            return null;
        
        NodeList termPropertyElts = dom.getElements(listElt, itemName);
        int listSize = termPropertyElts.getLength();
        MetadataList<Term> termList = new MetadataList<Term>(listSize);
        readCommonListProperties(dom, listElt, termList);

        for (int i = 0; i < listSize; i++)
        {
            Element propElt = (Element) termPropertyElts.item(i);
            Element termElt = dom.getFirstChildElement(propElt);
            Term term = readTerm(dom, termElt);
            termList.add(term);
        }

        return termList;
    }


    protected MetadataList<DocumentRef> readDocumentList(DOMHelper dom, Element listElt, String itemName) throws XMLReaderException
    {
        if (listElt == null)
            return null;
        
        NodeList docPropertyElts = dom.getElements(listElt, itemName);
        int listSize = docPropertyElts.getLength();
        MetadataList<DocumentRef> docList = new MetadataList<DocumentRef>(listSize);
        readCommonListProperties(dom, listElt, docList);
        
        for (int i = 0; i < listSize; i++)
        {
            Element propElt = (Element) docPropertyElts.item(i);
            Element documentElt = dom.getFirstChildElement(propElt);
            DocumentRef document = readDocument(dom, documentElt);
            document.setRole(dom.getAttributeValue(propElt, "role"));
            docList.add(document);
        }

        return docList;
    }


    protected MetadataList<Contact> readContactList(DOMHelper dom, Element listElt, String itemName) throws XMLReaderException
    {
        if (listElt == null)
            return null;
        
        NodeList contactPropertyElts = dom.getElements(listElt, itemName);
        int listSize = contactPropertyElts.getLength();
        MetadataList<Contact> contactList = new MetadataList<Contact>(listSize);
        readCommonListProperties(dom, listElt, contactList);
        
        for (int i = 0; i < listSize; i++)
        {
            Element propElt = (Element) contactPropertyElts.item(i);
            Element contactElt = dom.getFirstChildElement(propElt);
            Contact contact = readContact(dom, contactElt);

            contact.setRole(dom.getAttributeValue(propElt, "role"));

            String uri;
            if ((uri = dom.getAttributeValue(propElt, "href")) != null)
            {
                contact.setHrefPresent(true);
                contact.setHrefUri(uri);
            }
            else
                contact.setHrefPresent(false);

            contactList.add(contact);
        }

        return contactList;
    }


    public MetadataList<DataComponent> readPropertyList(DOMHelper dom, Element listElt, String itemName) throws XMLReaderException
    {
        if (listElt == null)
            return null;
        
        NodeList propertyElts = dom.getElements(listElt, itemName);
        int listSize = propertyElts.getLength();
        MetadataList<DataComponent> propertyList = new MetadataList<DataComponent>(listSize);
        readCommonListProperties(dom, listElt, propertyList);
        
        for (int i = 0; i < listSize; i++)
        {
            Element propElt = (Element) propertyElts.item(i);
            DataComponent data = sweReader.readComponentProperty(dom, propElt);
            propertyList.add(data);
        }

        return propertyList;
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

        // read description
        String description = dom.getElementValue(documentElement, "description/Discussion");
        document.setDescription(description);

        // read date
        Element dateElement = dom.getElement(documentElement, "date");
        if (dateElement != null)
            document.setDate(readDate(dom, dateElement));

        // read version
        String version = dom.getAttributeValue(documentElement, "version");
        document.setVersion(version);

        // read format
        String format = dom.getElementValue(documentElement, "format");
        document.setFormat(format);

        // read contact
        Element contactElt = dom.getElement(documentElement, "contact/*");
        if (contactElt != null)
            document.setContact(readContact(dom, contactElt));

        // read file location
        String fileLocation = dom.getAttributeValue(documentElement, "fileLocation/href");
        document.setFileLocation(fileLocation);

        return document;
    }


    protected Contact readContact(DOMHelper dom, Element contactElement) throws XMLReaderException
    {
        Contact contact = null;

        if (contactElement.getLocalName().equals("Person"))
            contact = readPerson(dom, contactElement);
        else if (contactElement.getLocalName().equals("ResponsibleParty"))
            contact = readResponsibleParty(dom, contactElement);

        return contact;
    }


    protected Person readPerson(DOMHelper dom, Element personElement) throws XMLReaderException
    {
        Person person = new Person();
        String value;
        
        // read surname
        value = dom.getElementValue(personElement, "surname");
        person.setSurName(value);
        
        // read name
        value = dom.getElementValue(personElement, "name");
        person.setName(value);
        
        // read userID
        value = dom.getElementValue(personElement, "userID");
        person.setUserID(value);
        
        // read affiliation
        value = dom.getElementValue(personElement, "affiliation");
        person.setAffiliation(value);
        
        // read phoneNumber
        value = dom.getElementValue(personElement, "phoneNumber");
        person.setPhoneNumber(value);
        
        // read email
        value = dom.getElementValue(personElement, "email");
        person.setEmail(value);

        return person;
    }


    protected ResponsibleParty readResponsibleParty(DOMHelper dom, Element partyElement) throws XMLReaderException
    {
        ResponsibleParty party = new ResponsibleParty();
        String value;

        // read individualName
        value = dom.getElementValue(partyElement, "individualName");
        party.setIndividualName(value);

        // read organizationName
        value = dom.getElementValue(partyElement, "organizationName");
        party.setOrganizationName(value);

        // read positionName
        value = dom.getElementValue(partyElement, "positionName");
        party.setPositionName(value);

        // read phone number
        value = dom.getElementValue(partyElement, "contactInfo/phone/voice");
        party.setVoiceNumber(value);

        // read fax number
        value = dom.getElementValue(partyElement, "contactInfo/phone/fascimile");
        party.setFaxNumber(value);

        // read address deliveryPoint
        value = dom.getElementValue(partyElement, "contactInfo/address/deliveryPoint");
        party.setDeliveryPoint(value);

        // read address city
        value = dom.getElementValue(partyElement, "contactInfo/address/city");
        party.setCity(value);
        
        // read address administrativeArea
        value = dom.getElementValue(partyElement, "contactInfo/address/administrativeArea");
        party.setAdministrativeArea(value);

        // read address postalCode
        value = dom.getElementValue(partyElement, "contactInfo/address/postalCode");
        party.setPostalCode(value);
        
        // read address country
        value = dom.getElementValue(partyElement, "contactInfo/address/country");
        party.setCountry(value);
        
        // read address electronicMailAddress
        value = dom.getElementValue(partyElement, "contactInfo/address/electronicMailAddress");
        party.setEmail(value);
        
        // read hoursOfService
        value = dom.getElementValue(partyElement, "contactInfo/hoursOfService");
        party.setHoursOfService(value);
        
        // read contactInstructions
        value = dom.getElementValue(partyElement, "contactInfo/contactInstructions");
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
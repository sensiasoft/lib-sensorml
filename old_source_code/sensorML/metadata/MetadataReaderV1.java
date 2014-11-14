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
import org.vast.sweCommon.SWECommonUtils;
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
public class MetadataReaderV1 extends AbstractSMLReader
{
    protected SWECommonUtils utils;


    /**
     * Constructs a MetadataReader using the specified DOMReader
     * @param parentReader
     */
    public MetadataReaderV1()
    {
        utils = new SWECommonUtils();
    }


    /* (non-Javadoc)
     * @see org.vast.sensorML.reader.MetadataReader#readMetadata(org.vast.xml.DOMHelper, org.w3c.dom.Element)
     */
    public Metadata readMetadata(DOMHelper dom, Element objectElement) throws XMLReaderException
    {
        Metadata metadata = new Metadata();

        // read all identifier lists
        NodeList identifierElts = dom.getElements(objectElement, "identification/IdentifierList/identifier");
        metadata.getIdentifiersLists().add(readTermList(dom, identifierElts));

        // read all classifier lists
        NodeList classifierElts = dom.getElements(objectElement, "classification/ClassifierList/classifier");
        metadata.getClassifiersLists().add(readTermList(dom, classifierElts));

        // read all characteristics lists
        NodeList characteristicsElts = dom.getElements(objectElement, "characteristics/PropertyList/property");
        metadata.getCharacteristicsLists().add(readPropertyList(dom, characteristicsElts));

        // read all capabilities lists
        NodeList capabilitiesElts = dom.getElements(objectElement, "capabilities/PropertyList/property");
        metadata.getCapabilitiesLists().add(readPropertyList(dom, capabilitiesElts));

        // read all document lists
        NodeList documentElts = dom.getElements(objectElement, "documentation/DocumentList/member");
        metadata.getDocumentsLists().add(readDocumentList(dom, documentElts));

        // read all contact lists
        NodeList contactElts = dom.getElements(objectElement, "contact/ContactList/member");
        metadata.getContactsLists().add(readContactList(dom, contactElts));
        
        // read standalone documents
        NodeList docElts = dom.getElements(objectElement, "documentation/Document");
        MetadataList<DocumentRef> docList = new MetadataList<DocumentRef>(docElts.getLength());
        for (int i=0; i<docElts.getLength(); i++)
        {
            Element docElt = (Element)docElts.item(i);
            DocumentRef doc = readDocument(dom, docElt);
            docList.add(doc);
        }
        metadata.getDocumentsLists().add(docList);
        
        // read standalone contacts
        contactElts = dom.getElements(objectElement, "contact/*");
        MetadataList<Contact> contactList = new MetadataList<Contact>(docElts.getLength());
        for (int i=0; i<contactElts.getLength(); i++)
        {
            Element contactElt = (Element)contactElts.item(i);
            if (!contactElt.getLocalName().equals("ContactList"))
            {
                Contact contact = readContact(dom, contactElt);
                contactList.add(contact);
            }
        }
        metadata.getContactsLists().add(contactList);
        
        return metadata;
    }


    /* (non-Javadoc)
     * @see org.vast.sensorML.reader.MetadataReader#readTermList(org.vast.xml.DOMHelper, org.w3c.dom.NodeList)
     */
    public IMetadataList<Term> readTermList(DOMHelper dom, NodeList termPropertyElts) throws XMLReaderException
    {
        int listSize = termPropertyElts.getLength();
        MetadataList<Term> termList = new MetadataList<Term>(listSize);

        for (int i = 0; i < listSize; i++)
        {
            Element propElt = (Element) termPropertyElts.item(i);
            Element termElt = dom.getFirstChildElement(propElt);
            Term term = readTerm(dom, termElt);
            term.setName(dom.getAttributeValue(propElt, "name"));
            termList.add(term);
        }

        return termList;
    }


    /* (non-Javadoc)
     * @see org.vast.sensorML.reader.MetadataReader#readDocumentList(org.vast.xml.DOMHelper, org.w3c.dom.NodeList)
     */
    public MetadataList<DocumentRef> readDocumentList(DOMHelper dom, NodeList docPropertyElts) throws XMLReaderException
    {
        int listSize = docPropertyElts.getLength();
        MetadataList<DocumentRef> docList = new MetadataList<DocumentRef>(listSize);

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


    /* (non-Javadoc)
     * @see org.vast.sensorML.reader.MetadataReader#readContactList(org.vast.xml.DOMHelper, org.w3c.dom.NodeList)
     */
    public MetadataList<Contact> readContactList(DOMHelper dom, NodeList contactPropertyElts) throws XMLReaderException
    {
        int listSize = contactPropertyElts.getLength();
        MetadataList<Contact> contactList = new MetadataList<Contact>(listSize);

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


    /* (non-Javadoc)
     * @see org.vast.sensorML.reader.MetadataReader#readPropertyList(org.vast.xml.DOMHelper, org.w3c.dom.NodeList)
     */
    public IMetadataList<DataComponent> readPropertyList(DOMHelper dom, NodeList propertyElts) throws XMLReaderException
    {
        int listSize = propertyElts.getLength();
        MetadataList<DataComponent> propertyList = new MetadataList<DataComponent>(listSize);

        for (int i = 0; i < listSize; i++)
        {
            Element propElt = (Element) propertyElts.item(i);
            DataComponent data = utils.readComponentProperty(dom, propElt);
            propertyList.add(data);
        }

        return propertyList;
    }


    /* (non-Javadoc)
     * @see org.vast.sensorML.reader.MetadataReader#readTerm(org.vast.xml.DOMHelper, org.w3c.dom.Element)
     */
    public Term readTerm(DOMHelper dom, Element termElt) throws XMLReaderException
    {
        Term term = new Term();

        // read codespace href attribute
        String codespace = dom.getAttributeValue(termElt, "codeSpace/href");
        term.setCodespace(codespace);

        // read term value
        String value = dom.getElementValue(termElt, "value");
        term.setValue(value);

        return term;
    }


    /* (non-Javadoc)
     * @see org.vast.sensorML.reader.MetadataReader#readDocument(org.vast.xml.DOMHelper, org.w3c.dom.Element)
     */
    public DocumentRef readDocument(DOMHelper dom, Element documentElement) throws XMLReaderException
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


    /* (non-Javadoc)
     * @see org.vast.sensorML.reader.MetadataReader#readContact(org.vast.xml.DOMHelper, org.w3c.dom.Element)
     */
    public Contact readContact(DOMHelper dom, Element contactElement) throws XMLReaderException
    {
        Contact contact = null;

        if (contactElement.getLocalName().equals("Person"))
            contact = readPerson(dom, contactElement);
        else if (contactElement.getLocalName().equals("ResponsibleParty"))
            contact = readResponsibleParty(dom, contactElement);

        return contact;
    }


    /* (non-Javadoc)
     * @see org.vast.sensorML.reader.MetadataReader#readPerson(org.vast.xml.DOMHelper, org.w3c.dom.Element)
     */
    public Person readPerson(DOMHelper dom, Element personElement) throws XMLReaderException
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


    /* (non-Javadoc)
     * @see org.vast.sensorML.reader.MetadataReader#readResponsibleParty(org.vast.xml.DOMHelper, org.w3c.dom.Element)
     */
    public ResponsibleParty readResponsibleParty(DOMHelper dom, Element partyElement) throws XMLReaderException
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


    /* (non-Javadoc)
     * @see org.vast.sensorML.reader.MetadataReader#readDate(org.vast.xml.DOMHelper, org.w3c.dom.Element)
     */
    public DateTime readDate(DOMHelper dom, Element dateElement) throws XMLReaderException
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
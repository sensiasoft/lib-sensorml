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

import java.text.ParseException;
import java.util.*;
import org.w3c.dom.*;
import org.vast.cdm.common.CDMException;
import org.vast.cdm.common.DataComponent;
import org.vast.cdm.reader.DataComponentsReader;
import org.vast.io.xml.DOMReader;
import org.vast.sensorML.*;
import org.vast.sensorML.metadata.Contact;
import org.vast.sensorML.metadata.DocumentRef;
import org.vast.sensorML.metadata.Metadata;
import org.vast.sensorML.metadata.Person;
import org.vast.sensorML.metadata.ResponsibleParty;
import org.vast.sensorML.metadata.Term;
import org.vast.util.*;


/**
 * <p><b>Title:</b><br/>
 * Metadata Reader
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Reader for Metadata group, including identifiers, classifiers,
 * constraints, contacts, documentation, characteristics and
 * capabilities.
 * </p>
 *
 * <p>Copyright (c) 2005</p>
 * @author Alexandre Robin
 * @version 1.0
 */
public class MetadataReader extends SMLReader
{
    protected DataComponentsReader dataComponentReader;


    /**
     * Constructs a MetadataReader using the specified DOMReader
     * @param parentReader
     */
    public MetadataReader(DOMReader parentReader)
    {
        dom = parentReader;
        dataComponentReader = new DataComponentsReader(this.dom);
    }


    /**
     * Reads all available metadata
     * @param objectElement the object encapsulating the metadata to be read
     * @return
     * @throws SMLException
     */
    public Metadata readMetadata(Element objectElement) throws SMLException
    {
        Metadata metadata = new Metadata();

        // read all identifier lists
        NodeList identifierElts = dom.getElements(objectElement, "identification/IdentifierList/identifier");
        metadata.setIdentifiers(readTermList(identifierElts));

        // read all classifier lists
        NodeList classifierElts = dom.getElements(objectElement, "classification/ClassifierList/classifier");
        metadata.setClassifiers(readTermList(classifierElts));

        // read all characteristics lists
        NodeList characteristicsElts = dom.getElements(objectElement, "characteristics/PropertyList/property");
        metadata.setCharacteristics(readPropertyList(characteristicsElts));

        // read all capabilities lists
        NodeList capabilitiesElts = dom.getElements(objectElement, "capabilities/PropertyList/property");
        metadata.setCapabilities(readPropertyList(capabilitiesElts));

        // read all document lists
        NodeList documentElts = dom.getElements(objectElement, "documentation/DocumentList/member");
        metadata.setDocuments(readDocumentList(documentElts));

        // read all contact lists
        NodeList contactElts = dom.getElements(objectElement, "contact/ContactList/member");
        metadata.setContacts(readContactList(contactElts));
        
        // read standalone documents
        NodeList docElts = dom.getElements(objectElement, "documentation/Document");
        for (int i=0; i<docElts.getLength(); i++)
        {
            Element docElt = (Element)docElts.item(i);
            DocumentRef doc = readDocument(docElt);
            metadata.getDocuments().add(doc);
        }
        
        // read standalone contacts
        contactElts = dom.getElements(objectElement, "contact/*");
        for (int i=0; i<contactElts.getLength(); i++)
        {
            Element contactElt = (Element)contactElts.item(i);
            if (!contactElt.getLocalName().equals("ContactList"))
            {
                Contact contact = readContact(contactElt);
                metadata.getContacts().add(contact);
            }
        }

        return metadata;
    }


    /**
     * Reads a list of properties containing Term elements
     * @param termPropertyElts the NodeList of all properties to read
     * @return
     */
    public List<Term> readTermList(NodeList termPropertyElts) throws SMLException
    {
        int listSize = termPropertyElts.getLength();
        List<Term> termList = new ArrayList<Term>(listSize);

        for (int i = 0; i < listSize; i++)
        {
            Element propElt = (Element) termPropertyElts.item(i);
            Element termElt = dom.getFirstChildElement(propElt);
            Term term = readTerm(termElt);
            term.setName(dom.getAttributeValue(propElt, "name"));
            termList.add(term);
        }

        return termList;
    }


    /**
     * Reads a list of properties containing Document elements
     * @param docPropertyElts
     * @return
     */
    public List<DocumentRef> readDocumentList(NodeList docPropertyElts) throws SMLException
    {
        int listSize = docPropertyElts.getLength();
        List<DocumentRef> docList = new ArrayList<DocumentRef>(listSize);

        for (int i = 0; i < listSize; i++)
        {
            Element propElt = (Element) docPropertyElts.item(i);
            Element documentElt = dom.getFirstChildElement(propElt);
            DocumentRef document = readDocument(documentElt);
            document.setRole(dom.getAttributeValue(propElt, "role"));
            docList.add(document);
        }

        return docList;
    }


    /**
     * Reads a list of properties containing Contact elements
     * @param contactPropertyElts
     * @return
     */
    public List<Contact> readContactList(NodeList contactPropertyElts) throws SMLException
    {
        int listSize = contactPropertyElts.getLength();
        List<Contact> contactList = new ArrayList<Contact>(listSize);

        for (int i = 0; i < listSize; i++)
        {
            Element propElt = (Element) contactPropertyElts.item(i);
            Element contactElt = dom.getFirstChildElement(propElt);
            Contact contact = readContact(contactElt);

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


    /**
     * Reads a list of properties containing elements of the AnyData group
     * @param propertyElts the NodeList of all properties to read
     * @return
     */
    public List<DataComponent> readPropertyList(NodeList propertyElts) throws SMLException
    {
        int listSize = propertyElts.getLength();
        List<DataComponent> propertyList = new ArrayList<DataComponent>(listSize);

        try
        {
            for (int i = 0; i < listSize; i++)
            {
                Element propElt = (Element) propertyElts.item(i);
                DataComponent data = dataComponentReader.readComponentProperty(propElt);
                propertyList.add(data);
            }
        }
        catch (CDMException e)
        {
            throw new SMLException(e.getMessage());
        }

        return propertyList;
    }


    /**
     * Reads a Term element
     * @param termElt
     * @return
     */
    public Term readTerm(Element termElt) throws SMLException
    {
        Term term = new Term();

        // read qualifier attribute
        String qualifier = dom.getAttributeValue(termElt, "qualifier");
        term.setQualifier(qualifier);

        // read term value
        String value = dom.getElementValue(termElt, "");
        term.setValue(value);

        return term;
    }


    /**
     * Reads a Document element
     * @param documentElement
     * @return
     */
    public DocumentRef readDocument(Element documentElement) throws SMLException
    {
        DocumentRef document = new DocumentRef();

        // read description
        String description = dom.getElementValue(documentElement, "description/Discussion");
        document.setDescription(description);

        // read date
        Element dateElement = dom.getElement(documentElement, "date");
        if (dateElement != null)
            document.setDate(readDate(dateElement));

        // read version
        String version = dom.getAttributeValue(documentElement, "version");
        document.setVersion(version);

        // read format
        String format = dom.getElementValue(documentElement, "format");
        document.setFormat(format);

        // read contact
        Element contactElt = dom.getElement(documentElement, "contact/*");
        if (contactElt != null)
            document.setContact(readContact(contactElt));

        // read file location
        String fileLocation = dom.getAttributeValue(documentElement, "fileLocation/href");
        document.setFileLocation(fileLocation);

        return document;
    }


    /**
     * Reads a Contact (i.e. Person or ResponsibleParty element)
     * @param contactElement
     * @return
     */
    public Contact readContact(Element contactElement) throws SMLException
    {
        Contact contact = null;

        if (contactElement.getLocalName().equals("Person"))
            contact = readPerson(contactElement);
        else if (contactElement.getLocalName().equals("ResponsibleParty"))
            contact = readResponsibleParty(contactElement);

        return contact;
    }


    /**
     * Reads a Person element
     * @param personElement
     * @return
     * @throws SMLException
     */
    public Person readPerson(Element personElement) throws SMLException
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


    /**
     * Reads a ResponsibleParty element
     * @param partyElement
     * @return
     * @throws SMLException
     */
    public ResponsibleParty readResponsibleParty(Element partyElement) throws SMLException
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


    /**
     * Reads an element containing an ISO date value
     * @param dateElement
     * @return
     */
    public DateTime readDate(Element dateElement) throws SMLException
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
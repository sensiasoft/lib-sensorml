/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2015 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package org.isotc211.v2005.gmd.bind;

import java.util.Map;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import net.opengis.AbstractXMLStreamBindings;
import net.opengis.IDateTime;
import net.opengis.OgcProperty;
import net.opengis.OgcPropertyImpl;
import org.isotc211.v2005.gco.CodeListValue;
import org.isotc211.v2005.gmd.CIAddress;
import org.isotc211.v2005.gmd.CICitation;
import org.isotc211.v2005.gmd.CIContact;
import org.isotc211.v2005.gmd.CIDate;
import org.isotc211.v2005.gmd.CIOnlineResource;
import org.isotc211.v2005.gmd.CIResponsibleParty;
import org.isotc211.v2005.gmd.CISeries;
import org.isotc211.v2005.gmd.CITelephone;
import org.isotc211.v2005.gmd.MDConstraints;
import org.isotc211.v2005.gmd.MDIdentifier;
import org.isotc211.v2005.gmd.MDKeywords;
import org.isotc211.v2005.gmd.MDLegalConstraints;
import org.isotc211.v2005.gmd.Factory;


public class XMLStreamBindings extends AbstractXMLStreamBindings
{
    public final static String NS_URI = "http://www.isotc211.org/2005/gmd";
    
    org.isotc211.v2005.gco.bind.XMLStreamBindings ns1Bindings;
    Factory factory;
    
    
    public XMLStreamBindings(Factory factory, org.isotc211.v2005.gco.Factory ns1Factory)
    {
        this.factory = factory;
        this.ns1Bindings = new org.isotc211.v2005.gco.bind.XMLStreamBindings(ns1Factory);
    }
    
    
    /**
     * Read method for CIResponsiblePartyType complex type
     */
    public CIResponsibleParty readCIResponsiblePartyType(XMLStreamReader reader) throws XMLStreamException
    {
        CIResponsibleParty bean = factory.newCIResponsibleParty();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readCIResponsiblePartyTypeAttributes(attrMap, bean);
        
        reader.nextTag();
        this.readCIResponsiblePartyTypeElements(reader, bean);
        
        return bean;
    }
    
    
    /**
     * Reads attributes of CIResponsiblePartyType complex type
     */
    public void readCIResponsiblePartyTypeAttributes(Map<String, String> attrMap, CIResponsibleParty bean) throws XMLStreamException
    {
        ns1Bindings.readAbstractObjectTypeAttributes(attrMap, bean);
        
    }
    
    
    /**
     * Reads elements of CIResponsiblePartyType complex type
     */
    public void readCIResponsiblePartyTypeElements(XMLStreamReader reader, CIResponsibleParty bean) throws XMLStreamException
    {
        boolean found;
        
        // individualName
        found = checkElementName(reader, "individualName");
        if (found)
        {
            reader.nextTag();
            String individualName = ns1Bindings.readCharacterString(reader);
            if (individualName != null)
                bean.setIndividualName(individualName);
            
            reader.nextTag(); // end property tag
            reader.nextTag();
        }
        
        // organisationName
        found = checkElementName(reader, "organisationName");
        if (found)
        {
            reader.nextTag();
            String organisationName = ns1Bindings.readCharacterString(reader);
            if (organisationName != null)
                bean.setOrganisationName(organisationName);
            
            reader.nextTag(); // end property tag
            reader.nextTag();
        }
        
        // positionName
        found = checkElementName(reader, "positionName");
        if (found)
        {
            reader.nextTag();
            String positionName = ns1Bindings.readCharacterString(reader);
            if (positionName != null)
                bean.setPositionName(positionName);
            
            reader.nextTag(); // end property tag
            reader.nextTag();
        }
        
        // contactInfo
        found = checkElementName(reader, "contactInfo");
        if (found)
        {
            OgcProperty<CIContact> contactInfoProp = bean.getContactInfoProperty();
            readPropertyAttributes(reader, contactInfoProp);
            
            if (contactInfoProp.getHref() == null)
            {
                reader.nextTag();
                contactInfoProp.setValue(this.readCIContact(reader));
            }
            
            reader.nextTag(); // end property tag
            reader.nextTag();
        }
        
        // role
        found = checkElementName(reader, "role");
        if (found)
        {
            reader.nextTag();
            
            // only proceed if we actually have a child cause it can be nilled
            if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
            {
                CodeListValue role = this.readCIRoleCode(reader);
                if (role != null)
                    bean.setRole(role);
                
                reader.nextTag(); // end property tag
            }
            
            reader.nextTag();
        }
    }
    
    
    /**
     * Write method for CIResponsiblePartyType complex type
     */
    public void writeCIResponsiblePartyType(XMLStreamWriter writer, CIResponsibleParty bean) throws XMLStreamException
    {
        this.writeCIResponsiblePartyTypeAttributes(writer, bean);
        this.writeCIResponsiblePartyTypeElements(writer, bean);
    }
    
    
    /**
     * Writes attributes of CIResponsiblePartyType complex type
     */
    public void writeCIResponsiblePartyTypeAttributes(XMLStreamWriter writer, CIResponsibleParty bean) throws XMLStreamException
    {
        ns1Bindings.writeAbstractObjectTypeAttributes(writer, bean);
    }
    
    
    /**
     * Writes elements of CIResponsiblePartyType complex type
     */
    public void writeCIResponsiblePartyTypeElements(XMLStreamWriter writer, CIResponsibleParty bean) throws XMLStreamException
    {
        
        // individualName
        if (bean.isSetIndividualName())
        {
            writer.writeStartElement(NS_URI, "individualName");
            ns1Bindings.writeCharacterString(writer, bean.getIndividualName());
            writer.writeEndElement();
        }
        
        // organisationName
        if (bean.isSetOrganisationName())
        {
            writer.writeStartElement(NS_URI, "organisationName");
            ns1Bindings.writeCharacterString(writer, bean.getOrganisationName());
            writer.writeEndElement();
        }
        
        // positionName
        if (bean.isSetPositionName())
        {
            writer.writeStartElement(NS_URI, "positionName");
            ns1Bindings.writeCharacterString(writer, bean.getPositionName());
            writer.writeEndElement();
        }
        
        // contactInfo
        if (bean.isSetContactInfo())
        {
            writer.writeStartElement(NS_URI, "contactInfo");
            writePropertyAttributes(writer, bean.getContactInfoProperty());
            this.writeCIContact(writer, bean.getContactInfo());
            writer.writeEndElement();
        }
        
        // role
        writer.writeStartElement(NS_URI, "role");
        if (bean.getRole() != null)
            this.writeCIRoleCode(writer, bean.getRole());
        else
            writer.writeAttribute(org.isotc211.v2005.gco.bind.XMLStreamBindings.NS_URI, "nilReason", "inapplicable");
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for CICitationType complex type
     */
    public CICitation readCICitationType(XMLStreamReader reader) throws XMLStreamException
    {
        CICitation bean = factory.newCICitation();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readCICitationTypeAttributes(attrMap, bean);
        
        reader.nextTag();
        this.readCICitationTypeElements(reader, bean);
        
        return bean;
    }
    
    
    /**
     * Reads attributes of CICitationType complex type
     */
    public void readCICitationTypeAttributes(Map<String, String> attrMap, CICitation bean) throws XMLStreamException
    {
        ns1Bindings.readAbstractObjectTypeAttributes(attrMap, bean);
        
    }
    
    
    /**
     * Reads elements of CICitationType complex type
     */
    public void readCICitationTypeElements(XMLStreamReader reader, CICitation bean) throws XMLStreamException
    {
        boolean found;
        
        // title
        found = checkElementName(reader, "title");
        if (found)
        {
            reader.nextTag();
            String title = ns1Bindings.readCharacterString(reader);
            if (title != null)
                bean.setTitle(title);
            
            reader.nextTag(); // end property tag
            reader.nextTag();
        }
        
        // alternateTitle
        do
        {
            found = checkElementName(reader, "alternateTitle");
            if (found)
            {
                reader.nextTag();
                String alternateTitle = ns1Bindings.readCharacterString(reader);
                if (alternateTitle != null)
                    bean.addAlternateTitle(alternateTitle);
                
                reader.nextTag(); // end property tag
                reader.nextTag();
            }
        }
        while (found);
        
        // date
        do
        {
            found = checkElementName(reader, "date");
            if (found)
            {
                OgcProperty<CIDate> dateProp = new OgcPropertyImpl<CIDate>();
                readPropertyAttributes(reader, dateProp);
                
                if (dateProp.getHref() == null)
                {
                    reader.nextTag();
                    dateProp.setValue(this.readCIDate(reader));
                }
                bean.getDateList().add(dateProp);
                
                reader.nextTag(); // end property tag
                reader.nextTag();
            }
        }
        while (found);
        
        // edition
        found = checkElementName(reader, "edition");
        if (found)
        {
            reader.nextTag();
            String edition = ns1Bindings.readCharacterString(reader);
            if (edition != null)
                bean.setEdition(edition);
            
            reader.nextTag(); // end property tag
            reader.nextTag();
        }
        
        // editionDate
        found = checkElementName(reader, "editionDate");
        if (found)
        {
            reader.nextTag();
            IDateTime editionDate = ns1Bindings.readDateTime(reader);
            if (editionDate != null)
                bean.setEditionDate(editionDate);
            
            reader.nextTag();
            reader.nextTag();
        }
        
        // identifier
        do
        {
            found = checkElementName(reader, "identifier");
            if (found)
            {
                OgcProperty<MDIdentifier> identifierProp = new OgcPropertyImpl<MDIdentifier>();
                readPropertyAttributes(reader, identifierProp);
                
                if (identifierProp.getHref() == null)
                {
                    reader.nextTag();
                    identifierProp.setValue(this.readMDIdentifier(reader));
                }
                bean.getIdentifierList().add(identifierProp);
                
                reader.nextTag(); // end property tag
                reader.nextTag();
            }
        }
        while (found);
        
        // citedResponsibleParty
        do
        {
            found = checkElementName(reader, "citedResponsibleParty");
            if (found)
            {
                OgcProperty<CIResponsibleParty> citedResponsiblePartyProp = new OgcPropertyImpl<CIResponsibleParty>();
                readPropertyAttributes(reader, citedResponsiblePartyProp);
                
                if (citedResponsiblePartyProp.getHref() == null)
                {
                    reader.nextTag();
                    citedResponsiblePartyProp.setValue(this.readCIResponsibleParty(reader));
                }
                bean.getCitedResponsiblePartyList().add(citedResponsiblePartyProp);
                
                reader.nextTag(); // end property tag
                reader.nextTag();
            }
        }
        while (found);
        
        // presentationForm
        do
        {
            found = checkElementName(reader, "presentationForm");
            if (found)
            {
                reader.nextTag();
                CodeListValue presentationForm = this.readCIPresentationFormCode(reader);
                if (presentationForm != null)
                    bean.addPresentationForm(presentationForm);
                
                reader.nextTag(); // end property tag
                reader.nextTag();
            }
        }
        while (found);
        
        // series
        found = checkElementName(reader, "series");
        if (found)
        {
            OgcProperty<CISeries> seriesProp = bean.getSeriesProperty();
            readPropertyAttributes(reader, seriesProp);
            
            if (seriesProp.getHref() == null)
            {
                reader.nextTag();
                seriesProp.setValue(this.readCISeries(reader));
            }
            
            reader.nextTag(); // end property tag
            reader.nextTag();
        }
        
        // otherCitationDetails
        found = checkElementName(reader, "otherCitationDetails");
        if (found)
        {
            reader.nextTag();
            String otherCitationDetails = ns1Bindings.readCharacterString(reader);
            if (otherCitationDetails != null)
                bean.setOtherCitationDetails(otherCitationDetails);
            
            reader.nextTag(); // end property tag
            reader.nextTag();
        }
        
        // collectiveTitle
        found = checkElementName(reader, "collectiveTitle");
        if (found)
        {
            reader.nextTag();
            String collectiveTitle = ns1Bindings.readCharacterString(reader);
            if (collectiveTitle != null)
                bean.setCollectiveTitle(collectiveTitle);
            
            reader.nextTag(); // end property tag
            reader.nextTag();
        }
        
        // ISBN
        found = checkElementName(reader, "ISBN");
        if (found)
        {
            reader.nextTag();
            String ISBN = ns1Bindings.readCharacterString(reader);
            if (ISBN != null)
                bean.setISBN(ISBN);
            
            reader.nextTag(); // end property tag
            reader.nextTag();
        }
        
        // ISSN
        found = checkElementName(reader, "ISSN");
        if (found)
        {
            reader.nextTag();
            String ISSN = ns1Bindings.readCharacterString(reader);
            if (ISSN != null)
                bean.setISSN(ISSN);
            
            reader.nextTag(); // end property tag
            reader.nextTag();
        }
    }
    
    
    /**
     * Write method for CICitationType complex type
     */
    public void writeCICitationType(XMLStreamWriter writer, CICitation bean) throws XMLStreamException
    {
        this.writeCICitationTypeAttributes(writer, bean);
        this.writeCICitationTypeElements(writer, bean);
    }
    
    
    /**
     * Writes attributes of CICitationType complex type
     */
    public void writeCICitationTypeAttributes(XMLStreamWriter writer, CICitation bean) throws XMLStreamException
    {
        ns1Bindings.writeAbstractObjectTypeAttributes(writer, bean);
    }
    
    
    /**
     * Writes elements of CICitationType complex type
     */
    public void writeCICitationTypeElements(XMLStreamWriter writer, CICitation bean) throws XMLStreamException
    {
        int numItems;
        
        // title
        writer.writeStartElement(NS_URI, "title");
        ns1Bindings.writeCharacterString(writer, bean.getTitle());
        writer.writeEndElement();
        
        // alternateTitle
        numItems = bean.getAlternateTitleList().size();
        for (int i = 0; i < numItems; i++)
        {
            String item = bean.getAlternateTitleList().get(i);
            writer.writeStartElement(NS_URI, "alternateTitle");
            ns1Bindings.writeCharacterString(writer, item);
            writer.writeEndElement();
        }
        
        // date
        numItems = bean.getDateList().size();
        for (int i = 0; i < numItems; i++)
        {
            OgcProperty<CIDate> item = bean.getDateList().getProperty(i);
            writer.writeStartElement(NS_URI, "date");
            writePropertyAttributes(writer, item);
            this.writeCIDate(writer, item.getValue());
            writer.writeEndElement();
        }
        
        // edition
        if (bean.isSetEdition())
        {
            writer.writeStartElement(NS_URI, "edition");
            ns1Bindings.writeCharacterString(writer, bean.getEdition());
            writer.writeEndElement();
        }
        
        // editionDate
        if (bean.isSetEditionDate())
        {
            writer.writeStartElement(NS_URI, "editionDate");
            ns1Bindings.writeDateTime(writer, bean.getEditionDate());            
            writer.writeEndElement();
        }
        
        // identifier
        numItems = bean.getIdentifierList().size();
        for (int i = 0; i < numItems; i++)
        {
            OgcProperty<MDIdentifier> item = bean.getIdentifierList().getProperty(i);
            writer.writeStartElement(NS_URI, "identifier");
            writePropertyAttributes(writer, item);
            this.writeMDIdentifier(writer, item.getValue());
            writer.writeEndElement();
        }
        
        // citedResponsibleParty
        numItems = bean.getCitedResponsiblePartyList().size();
        for (int i = 0; i < numItems; i++)
        {
            OgcProperty<CIResponsibleParty> item = bean.getCitedResponsiblePartyList().getProperty(i);
            writer.writeStartElement(NS_URI, "citedResponsibleParty");
            writePropertyAttributes(writer, item);
            this.writeCIResponsibleParty(writer, item.getValue());
            writer.writeEndElement();
        }
        
        // presentationForm
        numItems = bean.getPresentationFormList().size();
        for (int i = 0; i < numItems; i++)
        {
            CodeListValue item = bean.getPresentationFormList().get(i);
            writer.writeStartElement(NS_URI, "presentationForm");
            this.writeCIPresentationFormCode(writer, item);
            writer.writeEndElement();
        }
        
        // series
        if (bean.isSetSeries())
        {
            writer.writeStartElement(NS_URI, "series");
            writePropertyAttributes(writer, bean.getSeriesProperty());
            this.writeCISeries(writer, bean.getSeries());
            writer.writeEndElement();
        }
        
        // otherCitationDetails
        if (bean.isSetOtherCitationDetails())
        {
            writer.writeStartElement(NS_URI, "otherCitationDetails");
            ns1Bindings.writeCharacterString(writer, bean.getOtherCitationDetails());
            writer.writeEndElement();
        }
        
        // collectiveTitle
        if (bean.isSetCollectiveTitle())
        {
            writer.writeStartElement(NS_URI, "collectiveTitle");
            ns1Bindings.writeCharacterString(writer, bean.getCollectiveTitle());
            writer.writeEndElement();
        }
        
        // ISBN
        if (bean.isSetISBN())
        {
            writer.writeStartElement(NS_URI, "ISBN");
            ns1Bindings.writeCharacterString(writer, bean.getISBN());
            writer.writeEndElement();
        }
        
        // ISSN
        if (bean.isSetISSN())
        {
            writer.writeStartElement(NS_URI, "ISSN");
            ns1Bindings.writeCharacterString(writer, bean.getISSN());
            writer.writeEndElement();
        }
    }
    
    
    /**
     * Read method for CIAddressType complex type
     */
    public CIAddress readCIAddressType(XMLStreamReader reader) throws XMLStreamException
    {
        CIAddress bean = factory.newCIAddress();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readCIAddressTypeAttributes(attrMap, bean);
        
        reader.nextTag();
        this.readCIAddressTypeElements(reader, bean);
        
        return bean;
    }
    
    
    /**
     * Reads attributes of CIAddressType complex type
     */
    public void readCIAddressTypeAttributes(Map<String, String> attrMap, CIAddress bean) throws XMLStreamException
    {
        ns1Bindings.readAbstractObjectTypeAttributes(attrMap, bean);
        
    }
    
    
    /**
     * Reads elements of CIAddressType complex type
     */
    public void readCIAddressTypeElements(XMLStreamReader reader, CIAddress bean) throws XMLStreamException
    {
        boolean found;
        
        // deliveryPoint
        do
        {
            found = checkElementName(reader, "deliveryPoint");
            if (found)
            {
                reader.nextTag();
                String deliveryPoint = ns1Bindings.readCharacterString(reader);
                if (deliveryPoint != null)
                    bean.addDeliveryPoint(deliveryPoint);
                
                reader.nextTag(); // end property tag
                reader.nextTag();
            }
        }
        while (found);
        
        // city
        found = checkElementName(reader, "city");
        if (found)
        {
            reader.nextTag();
            String city = ns1Bindings.readCharacterString(reader);
            if (city != null)
                bean.setCity(city);
            
            reader.nextTag(); // end property tag
            reader.nextTag();
        }
        
        // administrativeArea
        found = checkElementName(reader, "administrativeArea");
        if (found)
        {
            reader.nextTag();
            String administrativeArea = ns1Bindings.readCharacterString(reader);
            if (administrativeArea != null)
                bean.setAdministrativeArea(administrativeArea);
            
            reader.nextTag(); // end property tag
            reader.nextTag();
        }
        
        // postalCode
        found = checkElementName(reader, "postalCode");
        if (found)
        {
            reader.nextTag();
            String postalCode = ns1Bindings.readCharacterString(reader);
            if (postalCode != null)
                bean.setPostalCode(postalCode);
            
            reader.nextTag(); // end property tag
            reader.nextTag();
        }
        
        // country
        found = checkElementName(reader, "country");
        if (found)
        {
            reader.nextTag();
            String country = ns1Bindings.readCharacterString(reader);
            if (country != null)
                bean.setCountry(country);
            
            reader.nextTag(); // end property tag
            reader.nextTag();
        }
        
        // electronicMailAddress
        do
        {
            found = checkElementName(reader, "electronicMailAddress");
            if (found)
            {
                reader.nextTag();
                String electronicMailAddress = ns1Bindings.readCharacterString(reader);
                if (electronicMailAddress != null)
                    bean.addElectronicMailAddress(electronicMailAddress);
                
                reader.nextTag(); // end property tag
                reader.nextTag();
            }
        }
        while (found);
    }
    
    
    /**
     * Write method for CIAddressType complex type
     */
    public void writeCIAddressType(XMLStreamWriter writer, CIAddress bean) throws XMLStreamException
    {
        this.writeCIAddressTypeAttributes(writer, bean);
        this.writeCIAddressTypeElements(writer, bean);
    }
    
    
    /**
     * Writes attributes of CIAddressType complex type
     */
    public void writeCIAddressTypeAttributes(XMLStreamWriter writer, CIAddress bean) throws XMLStreamException
    {
        ns1Bindings.writeAbstractObjectTypeAttributes(writer, bean);
    }
    
    
    /**
     * Writes elements of CIAddressType complex type
     */
    public void writeCIAddressTypeElements(XMLStreamWriter writer, CIAddress bean) throws XMLStreamException
    {
        int numItems;
        
        // deliveryPoint
        numItems = bean.getDeliveryPointList().size();
        for (int i = 0; i < numItems; i++)
        {
            String item = bean.getDeliveryPointList().get(i);
            writer.writeStartElement(NS_URI, "deliveryPoint");
            ns1Bindings.writeCharacterString(writer, item);
            writer.writeEndElement();
        }
        
        // city
        if (bean.isSetCity())
        {
            writer.writeStartElement(NS_URI, "city");
            ns1Bindings.writeCharacterString(writer, bean.getCity());
            writer.writeEndElement();
        }
        
        // administrativeArea
        if (bean.isSetAdministrativeArea())
        {
            writer.writeStartElement(NS_URI, "administrativeArea");
            ns1Bindings.writeCharacterString(writer, bean.getAdministrativeArea());
            writer.writeEndElement();
        }
        
        // postalCode
        if (bean.isSetPostalCode())
        {
            writer.writeStartElement(NS_URI, "postalCode");
            ns1Bindings.writeCharacterString(writer, bean.getPostalCode());
            writer.writeEndElement();
        }
        
        // country
        if (bean.isSetCountry())
        {
            writer.writeStartElement(NS_URI, "country");
            ns1Bindings.writeCharacterString(writer, bean.getCountry());
            writer.writeEndElement();
        }
        
        // electronicMailAddress
        numItems = bean.getElectronicMailAddressList().size();
        for (int i = 0; i < numItems; i++)
        {
            String item = bean.getElectronicMailAddressList().get(i);
            writer.writeStartElement(NS_URI, "electronicMailAddress");
            ns1Bindings.writeCharacterString(writer, item);
            writer.writeEndElement();
        }
    }
    
    
    /**
     * Read method for CIOnlineResourceType complex type
     */
    public CIOnlineResource readCIOnlineResourceType(XMLStreamReader reader) throws XMLStreamException
    {
        CIOnlineResource bean = factory.newCIOnlineResource();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readCIOnlineResourceTypeAttributes(attrMap, bean);
        
        reader.nextTag();
        this.readCIOnlineResourceTypeElements(reader, bean);
        
        return bean;
    }
    
    
    /**
     * Reads attributes of CIOnlineResourceType complex type
     */
    public void readCIOnlineResourceTypeAttributes(Map<String, String> attrMap, CIOnlineResource bean) throws XMLStreamException
    {
        ns1Bindings.readAbstractObjectTypeAttributes(attrMap, bean);
        
    }
    
    
    /**
     * Reads elements of CIOnlineResourceType complex type
     */
    public void readCIOnlineResourceTypeElements(XMLStreamReader reader, CIOnlineResource bean) throws XMLStreamException
    {
        boolean found;
        
        // linkage
        found = checkElementName(reader, "linkage");
        if (found)
        {
            reader.nextTag();
            String linkage = this.readURL(reader);
            if (linkage != null)
                bean.setLinkage(linkage);
            
            reader.nextTag(); // end property tag
            reader.nextTag();
        }
        
        // protocol
        found = checkElementName(reader, "protocol");
        if (found)
        {
            reader.nextTag();
            String protocol = ns1Bindings.readCharacterString(reader);
            if (protocol != null)
                bean.setProtocol(protocol);
            
            reader.nextTag(); // end property tag
            reader.nextTag();
        }
        
        // applicationProfile
        found = checkElementName(reader, "applicationProfile");
        if (found)
        {
            reader.nextTag();
            String applicationProfile = ns1Bindings.readCharacterString(reader);
            if (applicationProfile != null)
                bean.setApplicationProfile(applicationProfile);
            
            reader.nextTag(); // end property tag
            reader.nextTag();
        }
        
        // name
        found = checkElementName(reader, "name");
        if (found)
        {
            reader.nextTag();
            String name = ns1Bindings.readCharacterString(reader);
            if (name != null)
                bean.setName(name);
            
            reader.nextTag(); // end property tag
            reader.nextTag();
        }
        
        // description
        found = checkElementName(reader, "description");
        if (found)
        {
            reader.nextTag();
            String description = ns1Bindings.readCharacterString(reader);
            if (description != null)
                bean.setDescription(description);
            
            reader.nextTag(); // end property tag
            reader.nextTag();
        }
        
        // function
        found = checkElementName(reader, "function");
        if (found)
        {
            reader.nextTag();
            CodeListValue function = this.readCIOnLineFunctionCode(reader);
            if (function != null)
                bean.setFunction(function);
            
            reader.nextTag(); // end property tag
            reader.nextTag();
        }
    }
    
    
    /**
     * Write method for CIOnlineResourceType complex type
     */
    public void writeCIOnlineResourceType(XMLStreamWriter writer, CIOnlineResource bean) throws XMLStreamException
    {
        this.writeCIOnlineResourceTypeAttributes(writer, bean);
        this.writeCIOnlineResourceTypeElements(writer, bean);
    }
    
    
    /**
     * Writes attributes of CIOnlineResourceType complex type
     */
    public void writeCIOnlineResourceTypeAttributes(XMLStreamWriter writer, CIOnlineResource bean) throws XMLStreamException
    {
        ns1Bindings.writeAbstractObjectTypeAttributes(writer, bean);
    }
    
    
    /**
     * Writes elements of CIOnlineResourceType complex type
     */
    public void writeCIOnlineResourceTypeElements(XMLStreamWriter writer, CIOnlineResource bean) throws XMLStreamException
    {
        
        // linkage
        writer.writeStartElement(NS_URI, "linkage");
        this.writeURL(writer, bean.getLinkage());
        writer.writeEndElement();
        
        // protocol
        if (bean.isSetProtocol())
        {
            writer.writeStartElement(NS_URI, "protocol");
            ns1Bindings.writeCharacterString(writer, bean.getProtocol());
            writer.writeEndElement();
        }
        
        // applicationProfile
        if (bean.isSetApplicationProfile())
        {
            writer.writeStartElement(NS_URI, "applicationProfile");
            ns1Bindings.writeCharacterString(writer, bean.getApplicationProfile());
            writer.writeEndElement();
        }
        
        // name
        if (bean.isSetName())
        {
            writer.writeStartElement(NS_URI, "name");
            ns1Bindings.writeCharacterString(writer, bean.getName());
            writer.writeEndElement();
        }
        
        // description
        if (bean.isSetDescription())
        {
            writer.writeStartElement(NS_URI, "description");
            ns1Bindings.writeCharacterString(writer, bean.getDescription());
            writer.writeEndElement();
        }
        
        // function
        if (bean.isSetFunction())
        {
            writer.writeStartElement(NS_URI, "function");
            this.writeCIOnLineFunctionCode(writer, bean.getFunction());
            writer.writeEndElement();
        }
    }
    
    
    /**
     * Read method for CIContactType complex type
     */
    public CIContact readCIContactType(XMLStreamReader reader) throws XMLStreamException
    {
        CIContact bean = factory.newCIContact();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readCIContactTypeAttributes(attrMap, bean);
        
        reader.nextTag();
        this.readCIContactTypeElements(reader, bean);
        
        return bean;
    }
    
    
    /**
     * Reads attributes of CIContactType complex type
     */
    public void readCIContactTypeAttributes(Map<String, String> attrMap, CIContact bean) throws XMLStreamException
    {
        ns1Bindings.readAbstractObjectTypeAttributes(attrMap, bean);
        
    }
    
    
    /**
     * Reads elements of CIContactType complex type
     */
    public void readCIContactTypeElements(XMLStreamReader reader, CIContact bean) throws XMLStreamException
    {
        boolean found;
        
        // phone
        found = checkElementName(reader, "phone");
        if (found)
        {
            OgcProperty<CITelephone> phoneProp = bean.getPhoneProperty();
            readPropertyAttributes(reader, phoneProp);
            
            if (phoneProp.getHref() == null)
            {
                reader.nextTag();
                phoneProp.setValue(this.readCITelephone(reader));
            }
            
            reader.nextTag(); // end property tag
            reader.nextTag();
        }
        
        // address
        found = checkElementName(reader, "address");
        if (found)
        {
            OgcProperty<CIAddress> addressProp = bean.getAddressProperty();
            readPropertyAttributes(reader, addressProp);
            
            if (addressProp.getHref() == null)
            {
                reader.nextTag();
                addressProp.setValue(this.readCIAddress(reader));
            }
            
            reader.nextTag(); // end property tag
            reader.nextTag();
        }
        
        // onlineResource
        found = checkElementName(reader, "onlineResource");
        if (found)
        {
            OgcProperty<CIOnlineResource> onlineResourceProp = bean.getOnlineResourceProperty();
            readPropertyAttributes(reader, onlineResourceProp);
            
            if (onlineResourceProp.getHref() == null)
            {
                reader.nextTag();
                onlineResourceProp.setValue(this.readCIOnlineResource(reader));
            }
            
            reader.nextTag(); // end property tag
            reader.nextTag();
        }
        
        // hoursOfService
        found = checkElementName(reader, "hoursOfService");
        if (found)
        {
            reader.nextTag();
            String hoursOfService = ns1Bindings.readCharacterString(reader);
            if (hoursOfService != null)
                bean.setHoursOfService(hoursOfService);
            
            reader.nextTag(); // end property tag
            reader.nextTag();
        }
        
        // contactInstructions
        found = checkElementName(reader, "contactInstructions");
        if (found)
        {
            reader.nextTag();
            String contactInstructions = ns1Bindings.readCharacterString(reader);
            if (contactInstructions != null)
                bean.setContactInstructions(contactInstructions);
            
            reader.nextTag(); // end property tag
            reader.nextTag();
        }
    }
    
    
    /**
     * Write method for CIContactType complex type
     */
    public void writeCIContactType(XMLStreamWriter writer, CIContact bean) throws XMLStreamException
    {
        this.writeCIContactTypeAttributes(writer, bean);
        this.writeCIContactTypeElements(writer, bean);
    }
    
    
    /**
     * Writes attributes of CIContactType complex type
     */
    public void writeCIContactTypeAttributes(XMLStreamWriter writer, CIContact bean) throws XMLStreamException
    {
        ns1Bindings.writeAbstractObjectTypeAttributes(writer, bean);
    }
    
    
    /**
     * Writes elements of CIContactType complex type
     */
    public void writeCIContactTypeElements(XMLStreamWriter writer, CIContact bean) throws XMLStreamException
    {
        
        // phone
        if (bean.isSetPhone())
        {
            writer.writeStartElement(NS_URI, "phone");
            writePropertyAttributes(writer, bean.getPhoneProperty());
            this.writeCITelephone(writer, bean.getPhone());
            writer.writeEndElement();
        }
        
        // address
        if (bean.isSetAddress())
        {
            writer.writeStartElement(NS_URI, "address");
            writePropertyAttributes(writer, bean.getAddressProperty());
            this.writeCIAddress(writer, bean.getAddress());
            writer.writeEndElement();
        }
        
        // onlineResource
        if (bean.isSetOnlineResource())
        {
            writer.writeStartElement(NS_URI, "onlineResource");
            writePropertyAttributes(writer, bean.getOnlineResourceProperty());
            this.writeCIOnlineResource(writer, bean.getOnlineResource());
            writer.writeEndElement();
        }
        
        // hoursOfService
        if (bean.isSetHoursOfService())
        {
            writer.writeStartElement(NS_URI, "hoursOfService");
            ns1Bindings.writeCharacterString(writer, bean.getHoursOfService());
            writer.writeEndElement();
        }
        
        // contactInstructions
        if (bean.isSetContactInstructions())
        {
            writer.writeStartElement(NS_URI, "contactInstructions");
            ns1Bindings.writeCharacterString(writer, bean.getContactInstructions());
            writer.writeEndElement();
        }
    }
    
    
    /**
     * Read method for CITelephoneType complex type
     */
    public CITelephone readCITelephoneType(XMLStreamReader reader) throws XMLStreamException
    {
        CITelephone bean = factory.newCITelephone();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readCITelephoneTypeAttributes(attrMap, bean);
        
        reader.nextTag();
        this.readCITelephoneTypeElements(reader, bean);
        
        return bean;
    }
    
    
    /**
     * Reads attributes of CITelephoneType complex type
     */
    public void readCITelephoneTypeAttributes(Map<String, String> attrMap, CITelephone bean) throws XMLStreamException
    {
        ns1Bindings.readAbstractObjectTypeAttributes(attrMap, bean);
        
    }
    
    
    /**
     * Reads elements of CITelephoneType complex type
     */
    public void readCITelephoneTypeElements(XMLStreamReader reader, CITelephone bean) throws XMLStreamException
    {
        boolean found;
        
        // voice
        do
        {
            found = checkElementName(reader, "voice");
            if (found)
            {
                reader.nextTag();
                String voice = ns1Bindings.readCharacterString(reader);
                if (voice != null)
                    bean.addVoice(voice);
                
                reader.nextTag(); // end property tag
                reader.nextTag();
            }
        }
        while (found);
        
        // facsimile
        do
        {
            found = checkElementName(reader, "facsimile");
            if (found)
            {
                reader.nextTag();
                String facsimile = ns1Bindings.readCharacterString(reader);
                if (facsimile != null)
                    bean.addFacsimile(facsimile);
                
                reader.nextTag(); // end property tag
                reader.nextTag();
            }
        }
        while (found);
    }
    
    
    /**
     * Write method for CITelephoneType complex type
     */
    public void writeCITelephoneType(XMLStreamWriter writer, CITelephone bean) throws XMLStreamException
    {
        this.writeCITelephoneTypeAttributes(writer, bean);
        this.writeCITelephoneTypeElements(writer, bean);
    }
    
    
    /**
     * Writes attributes of CITelephoneType complex type
     */
    public void writeCITelephoneTypeAttributes(XMLStreamWriter writer, CITelephone bean) throws XMLStreamException
    {
        ns1Bindings.writeAbstractObjectTypeAttributes(writer, bean);
    }
    
    
    /**
     * Writes elements of CITelephoneType complex type
     */
    public void writeCITelephoneTypeElements(XMLStreamWriter writer, CITelephone bean) throws XMLStreamException
    {
        int numItems;
        
        // voice
        numItems = bean.getVoiceList().size();
        for (int i = 0; i < numItems; i++)
        {
            String item = bean.getVoiceList().get(i);
            writer.writeStartElement(NS_URI, "voice");
            ns1Bindings.writeCharacterString(writer, item);
            writer.writeEndElement();
        }
        
        // facsimile
        numItems = bean.getFacsimileList().size();
        for (int i = 0; i < numItems; i++)
        {
            String item = bean.getFacsimileList().get(i);
            writer.writeStartElement(NS_URI, "facsimile");
            ns1Bindings.writeCharacterString(writer, item);
            writer.writeEndElement();
        }
    }
    
    
    /**
     * Read method for CIDateType complex type
     */
    public CIDate readCIDateType(XMLStreamReader reader) throws XMLStreamException
    {
        CIDate bean = factory.newCIDate();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readCIDateTypeAttributes(attrMap, bean);
        
        reader.nextTag();
        this.readCIDateTypeElements(reader, bean);
        
        return bean;
    }
    
    
    /**
     * Reads attributes of CIDateType complex type
     */
    public void readCIDateTypeAttributes(Map<String, String> attrMap, CIDate bean) throws XMLStreamException
    {
        ns1Bindings.readAbstractObjectTypeAttributes(attrMap, bean);
        
    }
    
    
    /**
     * Reads elements of CIDateType complex type
     */
    public void readCIDateTypeElements(XMLStreamReader reader, CIDate bean) throws XMLStreamException
    {
        boolean found;
        
        // date
        found = checkElementName(reader, "date");
        if (found)
        {
            reader.nextTag();
            String localName = reader.getName().getLocalPart();
            
            if (localName.equals("DateTime"))
            {
                IDateTime date = ns1Bindings.readDateTime(reader);
                bean.setDate(date);
            }
            else
                throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
            
            reader.nextTag();
            reader.nextTag();
        }
        
        // dateType
        found = checkElementName(reader, "dateType");
        if (found)
        {
            reader.nextTag();
            CodeListValue dateType = this.readCIDateTypeCode(reader);
            if (dateType != null)
                bean.setDateType(dateType);
            
            reader.nextTag(); // end property tag
            reader.nextTag();
        }
    }
    
    
    /**
     * Write method for CIDateType complex type
     */
    public void writeCIDateType(XMLStreamWriter writer, CIDate bean) throws XMLStreamException
    {
        this.writeCIDateTypeAttributes(writer, bean);
        this.writeCIDateTypeElements(writer, bean);
    }
    
    
    /**
     * Writes attributes of CIDateType complex type
     */
    public void writeCIDateTypeAttributes(XMLStreamWriter writer, CIDate bean) throws XMLStreamException
    {
        ns1Bindings.writeAbstractObjectTypeAttributes(writer, bean);
    }
    
    
    /**
     * Writes elements of CIDateType complex type
     */
    public void writeCIDateTypeElements(XMLStreamWriter writer, CIDate bean) throws XMLStreamException
    {
        
        // date
        writer.writeStartElement(NS_URI, "date");
        ns1Bindings.writeDateTime(writer, bean.getDate());
        writer.writeEndElement();
        
        // dateType
        writer.writeStartElement(NS_URI, "dateType");
        this.writeCIDateTypeCode(writer, bean.getDateType());
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for CISeriesType complex type
     */
    public CISeries readCISeriesType(XMLStreamReader reader) throws XMLStreamException
    {
        CISeries bean = factory.newCISeries();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readCISeriesTypeAttributes(attrMap, bean);
        
        reader.nextTag();
        this.readCISeriesTypeElements(reader, bean);
        
        return bean;
    }
    
    
    /**
     * Reads attributes of CISeriesType complex type
     */
    public void readCISeriesTypeAttributes(Map<String, String> attrMap, CISeries bean) throws XMLStreamException
    {
        ns1Bindings.readAbstractObjectTypeAttributes(attrMap, bean);
        
    }
    
    
    /**
     * Reads elements of CISeriesType complex type
     */
    public void readCISeriesTypeElements(XMLStreamReader reader, CISeries bean) throws XMLStreamException
    {
        boolean found;
        
        // name
        found = checkElementName(reader, "name");
        if (found)
        {
            reader.nextTag();
            String name = ns1Bindings.readCharacterString(reader);
            if (name != null)
                bean.setName(name);
            
            reader.nextTag(); // end property tag
            reader.nextTag();
        }
        
        // issueIdentification
        found = checkElementName(reader, "issueIdentification");
        if (found)
        {
            reader.nextTag();
            String issueIdentification = ns1Bindings.readCharacterString(reader);
            if (issueIdentification != null)
                bean.setIssueIdentification(issueIdentification);
            
            reader.nextTag(); // end property tag
            reader.nextTag();
        }
        
        // page
        found = checkElementName(reader, "page");
        if (found)
        {
            reader.nextTag();
            String page = ns1Bindings.readCharacterString(reader);
            if (page != null)
                bean.setPage(page);
            
            reader.nextTag(); // end property tag
            reader.nextTag();
        }
    }
    
    
    /**
     * Write method for CISeriesType complex type
     */
    public void writeCISeriesType(XMLStreamWriter writer, CISeries bean) throws XMLStreamException
    {
        this.writeCISeriesTypeAttributes(writer, bean);
        this.writeCISeriesTypeElements(writer, bean);
    }
    
    
    /**
     * Writes attributes of CISeriesType complex type
     */
    public void writeCISeriesTypeAttributes(XMLStreamWriter writer, CISeries bean) throws XMLStreamException
    {
        ns1Bindings.writeAbstractObjectTypeAttributes(writer, bean);
    }
    
    
    /**
     * Writes elements of CISeriesType complex type
     */
    public void writeCISeriesTypeElements(XMLStreamWriter writer, CISeries bean) throws XMLStreamException
    {
        
        // name
        if (bean.isSetName())
        {
            writer.writeStartElement(NS_URI, "name");
            ns1Bindings.writeCharacterString(writer, bean.getName());
            writer.writeEndElement();
        }
        
        // issueIdentification
        if (bean.isSetIssueIdentification())
        {
            writer.writeStartElement(NS_URI, "issueIdentification");
            ns1Bindings.writeCharacterString(writer, bean.getIssueIdentification());
            writer.writeEndElement();
        }
        
        // page
        if (bean.isSetPage())
        {
            writer.writeStartElement(NS_URI, "page");
            ns1Bindings.writeCharacterString(writer, bean.getPage());
            writer.writeEndElement();
        }
    }
    
    
    /**
     * Read method for MDKeywordsType complex type
     */
    public MDKeywords readMDKeywordsType(XMLStreamReader reader) throws XMLStreamException
    {
        MDKeywords bean = factory.newMDKeywords();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readMDKeywordsTypeAttributes(attrMap, bean);
        
        reader.nextTag();
        this.readMDKeywordsTypeElements(reader, bean);
        
        return bean;
    }
    
    
    /**
     * Reads attributes of MDKeywordsType complex type
     */
    public void readMDKeywordsTypeAttributes(Map<String, String> attrMap, MDKeywords bean) throws XMLStreamException
    {
        ns1Bindings.readAbstractObjectTypeAttributes(attrMap, bean);
        
    }
    
    
    /**
     * Reads elements of MDKeywordsType complex type
     */
    public void readMDKeywordsTypeElements(XMLStreamReader reader, MDKeywords bean) throws XMLStreamException
    {
        boolean found;
        
        // keyword
        do
        {
            found = checkElementName(reader, "keyword");
            if (found)
            {
                reader.nextTag();
                String keyword = ns1Bindings.readCharacterString(reader);
                if (keyword != null)
                    bean.addKeyword(keyword);
                
                reader.nextTag(); // end property tag
                reader.nextTag();
            }
        }
        while (found);
        
        // type
        found = checkElementName(reader, "type");
        if (found)
        {
            reader.nextTag();
            CodeListValue type = this.readMDKeywordTypeCode(reader);
            if (type != null)
                bean.setType(type);
            
            reader.nextTag(); // end property tag
            reader.nextTag();
        }
        
        // thesaurusName
        found = checkElementName(reader, "thesaurusName");
        if (found)
        {
            OgcProperty<CICitation> thesaurusNameProp = bean.getThesaurusNameProperty();
            readPropertyAttributes(reader, thesaurusNameProp);
            
            if (thesaurusNameProp.getHref() == null)
            {
                reader.nextTag();
                thesaurusNameProp.setValue(this.readCICitation(reader));
            }
            
            reader.nextTag(); // end property tag
            reader.nextTag();
        }
    }
    
    
    /**
     * Write method for MDKeywordsType complex type
     */
    public void writeMDKeywordsType(XMLStreamWriter writer, MDKeywords bean) throws XMLStreamException
    {
        this.writeMDKeywordsTypeAttributes(writer, bean);
        this.writeMDKeywordsTypeElements(writer, bean);
    }
    
    
    /**
     * Writes attributes of MDKeywordsType complex type
     */
    public void writeMDKeywordsTypeAttributes(XMLStreamWriter writer, MDKeywords bean) throws XMLStreamException
    {
        ns1Bindings.writeAbstractObjectTypeAttributes(writer, bean);
    }
    
    
    /**
     * Writes elements of MDKeywordsType complex type
     */
    public void writeMDKeywordsTypeElements(XMLStreamWriter writer, MDKeywords bean) throws XMLStreamException
    {
        int numItems;
        
        // keyword
        numItems = bean.getKeywordList().size();
        for (int i = 0; i < numItems; i++)
        {
            String item = bean.getKeywordList().get(i);
            writer.writeStartElement(NS_URI, "keyword");
            ns1Bindings.writeCharacterString(writer, item);
            writer.writeEndElement();
        }
        
        // type
        if (bean.isSetType())
        {
            writer.writeStartElement(NS_URI, "type");
            this.writeMDKeywordTypeCode(writer, bean.getType());
            writer.writeEndElement();
        }
        
        // thesaurusName
        if (bean.isSetThesaurusName())
        {
            writer.writeStartElement(NS_URI, "thesaurusName");
            writePropertyAttributes(writer, bean.getThesaurusNameProperty());
            this.writeCICitation(writer, bean.getThesaurusName());
            writer.writeEndElement();
        }
    }
    
    
    /**
     * Read method for MDIdentifierType complex type
     */
    public MDIdentifier readMDIdentifierType(XMLStreamReader reader) throws XMLStreamException
    {
        MDIdentifier bean = factory.newMDIdentifier();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readMDIdentifierTypeAttributes(attrMap, bean);
        
        reader.nextTag();
        this.readMDIdentifierTypeElements(reader, bean);
        
        return bean;
    }
    
    
    /**
     * Reads attributes of MDIdentifierType complex type
     */
    public void readMDIdentifierTypeAttributes(Map<String, String> attrMap, MDIdentifier bean) throws XMLStreamException
    {
        ns1Bindings.readAbstractObjectTypeAttributes(attrMap, bean);
        
    }
    
    
    /**
     * Reads elements of MDIdentifierType complex type
     */
    public void readMDIdentifierTypeElements(XMLStreamReader reader, MDIdentifier bean) throws XMLStreamException
    {
        boolean found;
        
        // authority
        found = checkElementName(reader, "authority");
        if (found)
        {
            OgcProperty<CICitation> authorityProp = bean.getAuthorityProperty();
            readPropertyAttributes(reader, authorityProp);
            
            if (authorityProp.getHref() == null)
            {
                reader.nextTag();
                authorityProp.setValue(this.readCICitation(reader));
            }
            
            reader.nextTag(); // end property tag
            reader.nextTag();
        }
        
        // code
        found = checkElementName(reader, "code");
        if (found)
        {
            reader.nextTag();
            String code = ns1Bindings.readCharacterString(reader);
            if (code != null)
                bean.setCode(code);
            
            reader.nextTag(); // end property tag
            reader.nextTag();
        }
    }
    
    
    /**
     * Write method for MDIdentifierType complex type
     */
    public void writeMDIdentifierType(XMLStreamWriter writer, MDIdentifier bean) throws XMLStreamException
    {
        this.writeMDIdentifierTypeAttributes(writer, bean);
        this.writeMDIdentifierTypeElements(writer, bean);
    }
    
    
    /**
     * Writes attributes of MDIdentifierType complex type
     */
    public void writeMDIdentifierTypeAttributes(XMLStreamWriter writer, MDIdentifier bean) throws XMLStreamException
    {
        ns1Bindings.writeAbstractObjectTypeAttributes(writer, bean);
    }
    
    
    /**
     * Writes elements of MDIdentifierType complex type
     */
    public void writeMDIdentifierTypeElements(XMLStreamWriter writer, MDIdentifier bean) throws XMLStreamException
    {
        
        // authority
        if (bean.isSetAuthority())
        {
            writer.writeStartElement(NS_URI, "authority");
            writePropertyAttributes(writer, bean.getAuthorityProperty());
            this.writeCICitation(writer, bean.getAuthority());
            writer.writeEndElement();
        }
        
        // code
        writer.writeStartElement(NS_URI, "code");
        ns1Bindings.writeCharacterString(writer, bean.getCode());
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for MDConstraintsType complex type
     */
    public MDConstraints readMDConstraintsType(XMLStreamReader reader) throws XMLStreamException
    {
        MDConstraints bean = factory.newMDConstraints();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readMDConstraintsTypeAttributes(attrMap, bean);
        
        reader.nextTag();
        this.readMDConstraintsTypeElements(reader, bean);
        
        return bean;
    }
    
    
    /**
     * Reads attributes of MDConstraintsType complex type
     */
    public void readMDConstraintsTypeAttributes(Map<String, String> attrMap, MDConstraints bean) throws XMLStreamException
    {
        ns1Bindings.readAbstractObjectTypeAttributes(attrMap, bean);
        
    }
    
    
    /**
     * Reads elements of MDConstraintsType complex type
     */
    public void readMDConstraintsTypeElements(XMLStreamReader reader, MDConstraints bean) throws XMLStreamException
    {
        boolean found;
        
        // useLimitation
        do
        {
            found = checkElementName(reader, "useLimitation");
            if (found)
            {
                reader.nextTag();
                String useLimitation = ns1Bindings.readCharacterString(reader);
                if (useLimitation != null)
                    bean.addUseLimitation(useLimitation);
                
                reader.nextTag(); // end property tag
                reader.nextTag();
            }
        }
        while (found);
    }
    
    
    /**
     * Write method for MDConstraintsType complex type
     */
    public void writeMDConstraintsType(XMLStreamWriter writer, MDConstraints bean) throws XMLStreamException
    {
        this.writeMDConstraintsTypeAttributes(writer, bean);
        this.writeMDConstraintsTypeElements(writer, bean);
    }
    
    
    /**
     * Writes attributes of MDConstraintsType complex type
     */
    public void writeMDConstraintsTypeAttributes(XMLStreamWriter writer, MDConstraints bean) throws XMLStreamException
    {
        ns1Bindings.writeAbstractObjectTypeAttributes(writer, bean);
    }
    
    
    /**
     * Writes elements of MDConstraintsType complex type
     */
    public void writeMDConstraintsTypeElements(XMLStreamWriter writer, MDConstraints bean) throws XMLStreamException
    {
        int numItems;
        
        // useLimitation
        numItems = bean.getUseLimitationList().size();
        for (int i = 0; i < numItems; i++)
        {
            String item = bean.getUseLimitationList().get(i);
            writer.writeStartElement(NS_URI, "useLimitation");
            ns1Bindings.writeCharacterString(writer, item);
            writer.writeEndElement();
        }
    }
    
    
    /**
     * Read method for MDLegalConstraintsType complex type
     */
    public MDLegalConstraints readMDLegalConstraintsType(XMLStreamReader reader) throws XMLStreamException
    {
        MDLegalConstraints bean = factory.newMDLegalConstraints();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readMDLegalConstraintsTypeAttributes(attrMap, bean);
        
        reader.nextTag();
        this.readMDLegalConstraintsTypeElements(reader, bean);
        
        return bean;
    }
    
    
    /**
     * Reads attributes of MDLegalConstraintsType complex type
     */
    public void readMDLegalConstraintsTypeAttributes(Map<String, String> attrMap, MDLegalConstraints bean) throws XMLStreamException
    {
        this.readMDConstraintsTypeAttributes(attrMap, bean);
        
    }
    
    
    /**
     * Reads elements of MDLegalConstraintsType complex type
     */
    public void readMDLegalConstraintsTypeElements(XMLStreamReader reader, MDLegalConstraints bean) throws XMLStreamException
    {
        this.readMDConstraintsTypeElements(reader, bean);
        
        boolean found;
        
        // accessConstraints
        do
        {
            found = checkElementName(reader, "accessConstraints");
            if (found)
            {
                reader.nextTag();
                CodeListValue accessConstraints = this.readMDRestrictionCode(reader);
                if (accessConstraints != null)
                    bean.addAccessConstraints(accessConstraints);
                
                reader.nextTag(); // end property tag
                reader.nextTag();
            }
        }
        while (found);
        
        // useConstraints
        do
        {
            found = checkElementName(reader, "useConstraints");
            if (found)
            {
                reader.nextTag();
                CodeListValue useConstraints = this.readMDRestrictionCode(reader);
                if (useConstraints != null)
                    bean.addUseConstraints(useConstraints);
                
                reader.nextTag(); // end property tag
                reader.nextTag();
            }
        }
        while (found);
        
        // otherConstraints
        do
        {
            found = checkElementName(reader, "otherConstraints");
            if (found)
            {
                reader.nextTag();
                String otherConstraints = ns1Bindings.readCharacterString(reader);
                if (otherConstraints != null)
                    bean.addOtherConstraints(otherConstraints);
                
                reader.nextTag(); // end property tag
                reader.nextTag();
            }
        }
        while (found);
    }
    
    
    /**
     * Write method for MDLegalConstraintsType complex type
     */
    public void writeMDLegalConstraintsType(XMLStreamWriter writer, MDLegalConstraints bean) throws XMLStreamException
    {
        this.writeMDLegalConstraintsTypeAttributes(writer, bean);
        this.writeMDLegalConstraintsTypeElements(writer, bean);
    }
    
    
    /**
     * Writes attributes of MDLegalConstraintsType complex type
     */
    public void writeMDLegalConstraintsTypeAttributes(XMLStreamWriter writer, MDLegalConstraints bean) throws XMLStreamException
    {
        this.writeMDConstraintsTypeAttributes(writer, bean);
    }
    
    
    /**
     * Writes elements of MDLegalConstraintsType complex type
     */
    public void writeMDLegalConstraintsTypeElements(XMLStreamWriter writer, MDLegalConstraints bean) throws XMLStreamException
    {
        this.writeMDConstraintsTypeElements(writer, bean);
        int numItems;
        
        // accessConstraints
        numItems = bean.getAccessConstraintsList().size();
        for (int i = 0; i < numItems; i++)
        {
            CodeListValue item = bean.getAccessConstraintsList().get(i);
            writer.writeStartElement(NS_URI, "accessConstraints");
            this.writeMDRestrictionCode(writer, item);
            writer.writeEndElement();
        }
        
        // useConstraints
        numItems = bean.getUseConstraintsList().size();
        for (int i = 0; i < numItems; i++)
        {
            CodeListValue item = bean.getUseConstraintsList().get(i);
            writer.writeStartElement(NS_URI, "useConstraints");
            this.writeMDRestrictionCode(writer, item);
            writer.writeEndElement();
        }
        
        // otherConstraints
        numItems = bean.getOtherConstraintsList().size();
        for (int i = 0; i < numItems; i++)
        {
            String item = bean.getOtherConstraintsList().get(i);
            writer.writeStartElement(NS_URI, "otherConstraints");
            ns1Bindings.writeCharacterString(writer, item);
            writer.writeEndElement();
        }
    }
    
    
    /**
     * Read method for Country elements
     */
    public CodeListValue readCountry(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "Country");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return ns1Bindings.readCodeListValueType(reader);
    }
    
    
    /**
     * Write method for Country element
     */
    public void writeCountry(XMLStreamWriter writer, CodeListValue bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "Country");
        this.writeNamespaces(writer);
        ns1Bindings.writeCodeListValueType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for CIResponsibleParty elements
     */
    public CIResponsibleParty readCIResponsibleParty(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "CI_ResponsibleParty");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return this.readCIResponsiblePartyType(reader);
    }
    
    
    /**
     * Write method for CIResponsibleParty element
     */
    public void writeCIResponsibleParty(XMLStreamWriter writer, CIResponsibleParty bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "CI_ResponsibleParty");
        this.writeNamespaces(writer);
        this.writeCIResponsiblePartyType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for CICitation elements
     */
    public CICitation readCICitation(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "CI_Citation");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return this.readCICitationType(reader);
    }
    
    
    /**
     * Write method for CICitation element
     */
    public void writeCICitation(XMLStreamWriter writer, CICitation bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "CI_Citation");
        this.writeNamespaces(writer);
        this.writeCICitationType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for CIAddress elements
     */
    public CIAddress readCIAddress(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "CI_Address");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return this.readCIAddressType(reader);
    }
    
    
    /**
     * Write method for CIAddress element
     */
    public void writeCIAddress(XMLStreamWriter writer, CIAddress bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "CI_Address");
        this.writeNamespaces(writer);
        this.writeCIAddressType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for CIOnlineResource elements
     */
    public CIOnlineResource readCIOnlineResource(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "CI_OnlineResource");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return this.readCIOnlineResourceType(reader);
    }
    
    
    /**
     * Write method for CIOnlineResource element
     */
    public void writeCIOnlineResource(XMLStreamWriter writer, CIOnlineResource bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "CI_OnlineResource");
        this.writeNamespaces(writer);
        this.writeCIOnlineResourceType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for CIContact elements
     */
    public CIContact readCIContact(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "CI_Contact");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return this.readCIContactType(reader);
    }
    
    
    /**
     * Write method for CIContact element
     */
    public void writeCIContact(XMLStreamWriter writer, CIContact bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "CI_Contact");
        this.writeNamespaces(writer);
        this.writeCIContactType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for CITelephone elements
     */
    public CITelephone readCITelephone(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "CI_Telephone");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return this.readCITelephoneType(reader);
    }
    
    
    /**
     * Write method for CITelephone element
     */
    public void writeCITelephone(XMLStreamWriter writer, CITelephone bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "CI_Telephone");
        this.writeNamespaces(writer);
        this.writeCITelephoneType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for CIDate elements
     */
    public CIDate readCIDate(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "CI_Date");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return this.readCIDateType(reader);
    }
    
    
    /**
     * Write method for CIDate element
     */
    public void writeCIDate(XMLStreamWriter writer, CIDate bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "CI_Date");
        this.writeNamespaces(writer);
        this.writeCIDateType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for CISeries elements
     */
    public CISeries readCISeries(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "CI_Series");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return this.readCISeriesType(reader);
    }
    
    
    /**
     * Write method for CISeries element
     */
    public void writeCISeries(XMLStreamWriter writer, CISeries bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "CI_Series");
        this.writeNamespaces(writer);
        this.writeCISeriesType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for CIRoleCode elements
     */
    public CodeListValue readCIRoleCode(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "CI_RoleCode");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return ns1Bindings.readCodeListValueType(reader);
    }
    
    
    /**
     * Write method for CIRoleCode element
     */
    public void writeCIRoleCode(XMLStreamWriter writer, CodeListValue bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "CI_RoleCode");
        this.writeNamespaces(writer);
        ns1Bindings.writeCodeListValueType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for CIPresentationFormCode elements
     */
    public CodeListValue readCIPresentationFormCode(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "CI_PresentationFormCode");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return ns1Bindings.readCodeListValueType(reader);
    }
    
    
    /**
     * Write method for CIPresentationFormCode element
     */
    public void writeCIPresentationFormCode(XMLStreamWriter writer, CodeListValue bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "CI_PresentationFormCode");
        this.writeNamespaces(writer);
        ns1Bindings.writeCodeListValueType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for CIOnLineFunctionCode elements
     */
    public CodeListValue readCIOnLineFunctionCode(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "CI_OnLineFunctionCode");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return ns1Bindings.readCodeListValueType(reader);
    }
    
    
    /**
     * Write method for CIOnLineFunctionCode element
     */
    public void writeCIOnLineFunctionCode(XMLStreamWriter writer, CodeListValue bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "CI_OnLineFunctionCode");
        this.writeNamespaces(writer);
        ns1Bindings.writeCodeListValueType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for CIDateTypeCode elements
     */
    public CodeListValue readCIDateTypeCode(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "CI_DateTypeCode");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return ns1Bindings.readCodeListValueType(reader);
    }
    
    
    /**
     * Write method for CIDateTypeCode element
     */
    public void writeCIDateTypeCode(XMLStreamWriter writer, CodeListValue bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "CI_DateTypeCode");
        this.writeNamespaces(writer);
        ns1Bindings.writeCodeListValueType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for MDKeywords elements
     */
    public MDKeywords readMDKeywords(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "MD_Keywords");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return this.readMDKeywordsType(reader);
    }
    
    
    /**
     * Write method for MDKeywords element
     */
    public void writeMDKeywords(XMLStreamWriter writer, MDKeywords bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "MD_Keywords");
        this.writeNamespaces(writer);
        this.writeMDKeywordsType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for MDKeywordTypeCode elements
     */
    public CodeListValue readMDKeywordTypeCode(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "MD_KeywordTypeCode");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return ns1Bindings.readCodeListValueType(reader);
    }
    
    
    /**
     * Write method for MDKeywordTypeCode element
     */
    public void writeMDKeywordTypeCode(XMLStreamWriter writer, CodeListValue bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "MD_KeywordTypeCode");
        this.writeNamespaces(writer);
        ns1Bindings.writeCodeListValueType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for MDIdentifier elements
     */
    public MDIdentifier readMDIdentifier(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "MD_Identifier");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return this.readMDIdentifierType(reader);
    }
    
    
    /**
     * Write method for MDIdentifier element
     */
    public void writeMDIdentifier(XMLStreamWriter writer, MDIdentifier bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "MD_Identifier");
        this.writeNamespaces(writer);
        this.writeMDIdentifierType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for MDConstraints elements
     */
    public MDConstraints readMDConstraints(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "MD_Constraints");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return this.readMDConstraintsType(reader);
    }
    
    
    /**
     * Write method for MDConstraints element
     */
    public void writeMDConstraints(XMLStreamWriter writer, MDConstraints bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "MD_Constraints");
        this.writeNamespaces(writer);
        this.writeMDConstraintsType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for MDLegalConstraints elements
     */
    public MDLegalConstraints readMDLegalConstraints(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "MD_LegalConstraints");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return this.readMDLegalConstraintsType(reader);
    }
    
    
    /**
     * Write method for MDLegalConstraints element
     */
    public void writeMDLegalConstraints(XMLStreamWriter writer, MDLegalConstraints bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "MD_LegalConstraints");
        this.writeNamespaces(writer);
        this.writeMDLegalConstraintsType(writer, bean);
        writer.writeEndElement();
    }    
    
    
    /**
     * Read method for MDRestrictionCode elements
     */
    public CodeListValue readMDRestrictionCode(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "MD_RestrictionCode");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return ns1Bindings.readCodeListValueType(reader);
    }
    
    
    /**
     * Write method for MDRestrictionCode element
     */
    public void writeMDRestrictionCode(XMLStreamWriter writer, CodeListValue bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "MD_RestrictionCode");
        this.writeNamespaces(writer);
        ns1Bindings.writeCodeListValueType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for URL elements
     */
    public String readURL(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "URL");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        String val = trimStringValue(reader.getElementText());
        return val;
    }
    
    
    /**
     * Write method for URL element
     */
    public void writeURL(XMLStreamWriter writer, String bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "URL");
        this.writeNamespaces(writer);
        writer.writeCharacters(bean);
        writer.writeEndElement();
    }
}

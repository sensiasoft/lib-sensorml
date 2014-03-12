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

import org.vast.cdm.common.DataComponent;
import org.vast.util.Contact;
import org.vast.util.DateTime;


/**
 * <p>
 * Objects containing all elements of the SensorML metadata group.
 * This includes identifiers, classifiers, constraints, contacts,
 * documentation, characteristics and capabilities.
 * </p>
 *
 * <p>Copyright (c) 2005</p>
 * @author Alexandre Robin
 * @since Feb 16, 2006
 * @version 1.0
 */
public class Metadata
{
    protected KeywordList keywords;
    protected MetadataList<Term> identifiers;
    protected MetadataList<Term> classifiers;
    protected MetadataList<DocumentRef> documents;
    protected MetadataList<Contact> contacts;
    protected MetadataList<DataComponent> characteristics;
    protected MetadataList<DataComponent> capabilities;
    protected MetadataList<HistoryEvent> events;
    protected MetadataList<LegalConstraint> legalConstraints;
    protected SecurityConstraint securityConstraint;
    protected DateTime validityBegin;
    protected DateTime validityEnd;    


    public Metadata()
    {
    }
    
    
    public KeywordList getKeywords()
    {
        return keywords;
    }


    public void setKeywords(KeywordList keywords)
    {
        this.keywords = keywords;
    }


    public MetadataList<DataComponent> getCapabilities()
    {
        return capabilities;
    }


    public void setCapabilities(MetadataList<DataComponent> capabilities)
    {
        this.capabilities = capabilities;
    }


    public MetadataList<DataComponent> getCharacteristics()
    {
        return characteristics;
    }


    public void setCharacteristics(MetadataList<DataComponent> characteristics)
    {
        this.characteristics = characteristics;
    }


    public MetadataList<Term> getClassifiers()
    {
        return classifiers;
    }


    public void setClassifiers(MetadataList<Term> classifiers)
    {
        this.classifiers = classifiers;
    }


    public MetadataList<Contact> getContacts()
    {
        return contacts;
    }


    public void setContacts(MetadataList<Contact> contacts)
    {
        this.contacts = contacts;
    }


    public MetadataList<DocumentRef> getDocuments()
    {
        return documents;
    }


    public void setDocuments(MetadataList<DocumentRef> documents)
    {
        this.documents = documents;
    }


    public MetadataList<Term> getIdentifiers()
    {
        return identifiers;
    }


    public void setIdentifiers(MetadataList<Term> identifiers)
    {
        this.identifiers = identifiers;
    }


    public MetadataList<HistoryEvent> getEvents()
    {
        return events;
    }


    public void setEvents(MetadataList<HistoryEvent> events)
    {
        this.events = events;
    }


    public MetadataList<LegalConstraint> getLegalConstraints()
    {
        return legalConstraints;
    }


    public void setLegalConstraints(MetadataList<LegalConstraint> legalConstraints)
    {
        this.legalConstraints = legalConstraints;
    }


    public SecurityConstraint getSecurityConstraint()
    {
        return securityConstraint;
    }


    public void setSecurityConstraint(SecurityConstraint securityConstraint)
    {
        this.securityConstraint = securityConstraint;
    }


    public DateTime getValidityBegin()
    {
        return validityBegin;
    }


    public void setValidityBegin(DateTime validityBegin)
    {
        this.validityBegin = validityBegin;
    }


    public DateTime getValidityEnd()
    {
        return validityEnd;
    }


    public void setValidityEnd(DateTime validityEnd)
    {
        this.validityEnd = validityEnd;
    }
}

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

import java.util.ArrayList;
import java.util.List;
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
    protected List<IMetadataList<String>> keywordsLists;
    protected List<IMetadataList<Term>> identifiersLists;
    protected List<IMetadataList<Term>> classifiersLists;
    protected List<IMetadataList<DocumentRef>> documentsLists;
    protected List<IMetadataList<Contact>> contactsLists;
    protected List<IMetadataList<DataComponent>> characteristicsLists;
    protected List<IMetadataList<DataComponent>> capabilitiesLists;
    protected List<IMetadataList<HistoryEvent>> eventsLists;
    protected List<IMetadataList<LegalConstraint>> legalConstraintsLists;
    protected SecurityConstraint securityConstraints;
    protected DateTime validityBegin;
    protected DateTime validityEnd;


    public Metadata()
    {
        keywordsLists = new ArrayList<IMetadataList<String>>(3);
        identifiersLists = new ArrayList<IMetadataList<Term>>(3);
        classifiersLists = new ArrayList<IMetadataList<Term>>(3);
        documentsLists = new ArrayList<IMetadataList<DocumentRef>>(3);
        contactsLists = new ArrayList<IMetadataList<Contact>>(3);
        characteristicsLists = new ArrayList<IMetadataList<DataComponent>>(3);
        capabilitiesLists = new ArrayList<IMetadataList<DataComponent>>(3);
        eventsLists = new ArrayList<IMetadataList<HistoryEvent>>(3);
        legalConstraintsLists = new ArrayList<IMetadataList<LegalConstraint>>(3);
    }


    public List<IMetadataList<String>> getKeywordsLists()
    {
        return keywordsLists;
    }


    public void setKeywordsLists(List<IMetadataList<String>> keywordsLists)
    {
        this.keywordsLists = keywordsLists;
    }


    public List<IMetadataList<Term>> getIdentifiersLists()
    {
        return identifiersLists;
    }


    public void setIdentifiersLists(List<IMetadataList<Term>> identifiersLists)
    {
        this.identifiersLists = identifiersLists;
    }


    public List<IMetadataList<Term>> getClassifiersLists()
    {
        return classifiersLists;
    }


    public void setClassifiersLists(List<IMetadataList<Term>> classifiersLists)
    {
        this.classifiersLists = classifiersLists;
    }


    public List<IMetadataList<DocumentRef>> getDocumentsLists()
    {
        return documentsLists;
    }


    public void setDocumentsLists(List<IMetadataList<DocumentRef>> documentsLists)
    {
        this.documentsLists = documentsLists;
    }


    public List<IMetadataList<Contact>> getContactsLists()
    {
        return contactsLists;
    }


    public void setContactsLists(List<IMetadataList<Contact>> contactsLists)
    {
        this.contactsLists = contactsLists;
    }


    public List<IMetadataList<DataComponent>> getCharacteristicsLists()
    {
        return characteristicsLists;
    }


    public void setCharacteristicsLists(List<IMetadataList<DataComponent>> characteristicsLists)
    {
        this.characteristicsLists = characteristicsLists;
    }


    public List<IMetadataList<DataComponent>> getCapabilitiesLists()
    {
        return capabilitiesLists;
    }


    public void setCapabilitiesLists(List<IMetadataList<DataComponent>> capabilitiesLists)
    {
        this.capabilitiesLists = capabilitiesLists;
    }


    public List<IMetadataList<HistoryEvent>> getEventsLists()
    {
        return eventsLists;
    }


    public void setEventsLists(List<IMetadataList<HistoryEvent>> eventsLists)
    {
        this.eventsLists = eventsLists;
    }


    public List<IMetadataList<LegalConstraint>> getLegalConstraintsLists()
    {
        return legalConstraintsLists;
    }


    public void setLegalConstraintsLists(List<IMetadataList<LegalConstraint>> legalConstraintsLists)
    {
        this.legalConstraintsLists = legalConstraintsLists;
    }


    public SecurityConstraint getSecurityConstraints()
    {
        return securityConstraints;
    }


    public void setSecurityConstraints(SecurityConstraint securityConstraints)
    {
        this.securityConstraints = securityConstraints;
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

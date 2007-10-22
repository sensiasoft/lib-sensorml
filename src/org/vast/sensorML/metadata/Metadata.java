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

import java.util.List;

import org.vast.cdm.common.DataComponent;
import org.vast.util.DateTime;


/**
 * <p><b>Title:</b><br/>
 * Metadata
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Objects containing all elements of the SensorML metadata group.
 * This includes identifiers, classifiers, constraints, contacts,
 * documentation, characteristics and capabilities.
 * </p>
 *
 * <p>Copyright (c) 2005</p>
 * @author Alexandre Robin
 * @date Feb 16, 2006
 * @version 1.0
 */
public class Metadata
{
    protected List<String> descriptions;
    protected List<Term> identifiers;
    protected List<Term> classifiers;
    protected List<DocumentRef> documents;
    protected List<Contact> contacts;
    protected List<DataComponent> characteristics;
    protected List<DataComponent> capabilities;
    protected List<HistoryEvent> events;
    protected List<LegalConstraint> legalConstraints;
    protected SecurityConstraint securityConstraint;
    protected DateTime validityBegin;
    protected DateTime validityEnd;    


    public Metadata()
    {
    }
    
    
    public List<String> getDescriptions()
    {
        return descriptions;
    }


    public void setDescriptions(List<String> descriptions)
    {
        this.descriptions = descriptions;
    }
    
    
    public List<DataComponent> getCapabilities()
    {
        return capabilities;
    }


    public void setCapabilities(List<DataComponent> capabilities)
    {
        this.capabilities = capabilities;
    }


    public List<DataComponent> getCharacteristics()
    {
        return characteristics;
    }


    public void setCharacteristics(List<DataComponent> characteristics)
    {
        this.characteristics = characteristics;
    }


    public List<Term> getClassifiers()
    {
        return classifiers;
    }


    public void setClassifiers(List<Term> classifiers)
    {
        this.classifiers = classifiers;
    }


    public List<Contact> getContacts()
    {
        return contacts;
    }


    public void setContacts(List<Contact> contacts)
    {
        this.contacts = contacts;
    }


    public List<DocumentRef> getDocuments()
    {
        return documents;
    }


    public void setDocuments(List<DocumentRef> documents)
    {
        this.documents = documents;
    }


    public List<Term> getIdentifiers()
    {
        return identifiers;
    }


    public void setIdentifiers(List<Term> identifiers)
    {
        this.identifiers = identifiers;
    }


    public List<HistoryEvent> getEvents()
    {
        return events;
    }


    public void setEvents(List<HistoryEvent> events)
    {
        this.events = events;
    }


    public List<LegalConstraint> getLegalConstraints()
    {
        return legalConstraints;
    }


    public void setLegalConstraints(List<LegalConstraint> legalConstraints)
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

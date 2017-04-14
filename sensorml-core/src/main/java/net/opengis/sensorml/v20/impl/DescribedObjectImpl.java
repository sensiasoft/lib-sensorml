/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2015 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package net.opengis.sensorml.v20.impl;

import java.util.ArrayList;
import java.util.List;
import net.opengis.OgcPropertyList;
import net.opengis.gml.v32.AbstractTimeGeometricPrimitive;
import net.opengis.gml.v32.TimeInstant;
import net.opengis.gml.v32.TimePeriod;
import net.opengis.gml.v32.impl.AbstractFeatureImpl;
import net.opengis.sensorml.v20.CapabilityList;
import net.opengis.sensorml.v20.CharacteristicList;
import net.opengis.sensorml.v20.ClassifierList;
import net.opengis.sensorml.v20.ContactList;
import net.opengis.sensorml.v20.DescribedObject;
import net.opengis.sensorml.v20.DocumentList;
import net.opengis.sensorml.v20.EventList;
import net.opengis.sensorml.v20.IdentifierList;
import net.opengis.sensorml.v20.KeywordList;
import org.isotc211.v2005.gmd.MDLegalConstraints;


/**
 * POJO class for XML type DescribedObjectType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public abstract class DescribedObjectImpl extends AbstractFeatureImpl implements DescribedObject
{
    private static final long serialVersionUID = 8176080766971346738L;
    protected ArrayList<Object> extensionList = new ArrayList<Object>();
    protected OgcPropertyList<KeywordList> keywordsList = new OgcPropertyList<KeywordList>();
    protected OgcPropertyList<IdentifierList> identificationList = new OgcPropertyList<IdentifierList>();
    protected OgcPropertyList<ClassifierList> classificationList = new OgcPropertyList<ClassifierList>();
    protected ArrayList<AbstractTimeGeometricPrimitive> validTimeList = new ArrayList<AbstractTimeGeometricPrimitive>();
    protected ArrayList<Object> securityConstraintsList = new ArrayList<Object>();
    protected OgcPropertyList<MDLegalConstraints> legalConstraintsList = new OgcPropertyList<MDLegalConstraints>();
    protected OgcPropertyList<CharacteristicList> characteristicsList = new OgcPropertyList<CharacteristicList>();
    protected OgcPropertyList<CapabilityList> capabilitiesList = new OgcPropertyList<CapabilityList>();
    protected OgcPropertyList<ContactList> contactsList = new OgcPropertyList<ContactList>();
    protected OgcPropertyList<DocumentList> documentationList = new OgcPropertyList<DocumentList>();
    protected OgcPropertyList<EventList> historyList = new OgcPropertyList<EventList>();
    protected String lang;
    
    
    public DescribedObjectImpl()
    {
    }
    
    
    /**
     * Gets the list of extension properties
     */
    @Override
    public List<Object> getExtensionList()
    {
        return extensionList;
    }
    
    
    /**
     * Returns number of extension properties
     */
    @Override
    public int getNumExtensions()
    {
        if (extensionList == null)
            return 0;
        return extensionList.size();
    }
    
    
    /**
     * Adds a new extension property
     */
    @Override
    public void addExtension(Object extension)
    {
        this.extensionList.add(extension);
    }
    
    
    /**
     * Gets the list of keywords properties
     */
    @Override
    public OgcPropertyList<KeywordList> getKeywordsList()
    {
        return keywordsList;
    }
    
    
    /**
     * Returns number of keywords properties
     */
    @Override
    public int getNumKeywords()
    {
        if (keywordsList == null)
            return 0;
        return keywordsList.size();
    }
    
    
    /**
     * Adds a new keywords property
     */
    @Override
    public void addKeywords(KeywordList keywords)
    {
        this.keywordsList.add(keywords);
    }
    
    
    /**
     * Gets the list of identification properties
     */
    @Override
    public OgcPropertyList<IdentifierList> getIdentificationList()
    {
        return identificationList;
    }
    
    
    /**
     * Returns number of identification properties
     */
    @Override
    public int getNumIdentifications()
    {
        if (identificationList == null)
            return 0;
        return identificationList.size();
    }
    
    
    /**
     * Adds a new identification property
     */
    @Override
    public void addIdentification(IdentifierList identification)
    {
        this.identificationList.add(identification);
    }
    
    
    /**
     * Gets the list of classification properties
     */
    @Override
    public OgcPropertyList<ClassifierList> getClassificationList()
    {
        return classificationList;
    }
    
    
    /**
     * Returns number of classification properties
     */
    @Override
    public int getNumClassifications()
    {
        if (classificationList == null)
            return 0;
        return classificationList.size();
    }
    
    
    /**
     * Adds a new classification property
     */
    @Override
    public void addClassification(ClassifierList classification)
    {
        this.classificationList.add(classification);
    }
    
    
    /**
     * Gets the list of validTime properties
     */
    @Override
    public List<AbstractTimeGeometricPrimitive> getValidTimeList()
    {
        return validTimeList;
    }
    
    
    /**
     * Returns number of validTime properties
     */
    @Override
    public int getNumValidTimes()
    {
        if (validTimeList == null)
            return 0;
        return validTimeList.size();
    }
    
    
    /**
     * Adds a new validTimeAsTimePeriod property
     */
    @Override
    public void addValidTimeAsTimePeriod(TimePeriod validTime)
    {
        this.validTimeList.add(validTime);
    }
    
    
    /**
     * Adds a new validTimeAsTimeInstant property
     */
    @Override
    public void addValidTimeAsTimeInstant(TimeInstant validTime)
    {
        this.validTimeList.add(validTime);
    }
    
    
    /**
     * Gets the list of securityConstraints properties
     */
    @Override
    public List<Object> getSecurityConstraintsList()
    {
        return securityConstraintsList;
    }
    
    
    /**
     * Returns number of securityConstraints properties
     */
    @Override
    public int getNumSecurityConstraints()
    {
        if (securityConstraintsList == null)
            return 0;
        return securityConstraintsList.size();
    }
    
    
    /**
     * Adds a new securityConstraints property
     */
    @Override
    public void addSecurityConstraints(Object securityConstraints)
    {
        this.securityConstraintsList.add(securityConstraints);
    }
    
    
    /**
     * Gets the list of legalConstraints properties
     */
    @Override
    public OgcPropertyList<MDLegalConstraints> getLegalConstraintsList()
    {
        return legalConstraintsList;
    }
    
    
    /**
     * Returns number of legalConstraints properties
     */
    @Override
    public int getNumLegalConstraints()
    {
        if (legalConstraintsList == null)
            return 0;
        return legalConstraintsList.size();
    }
    
    
    /**
     * Adds a new legalConstraints property
     */
    @Override
    public void addLegalConstraints(MDLegalConstraints legalConstraints)
    {
        this.legalConstraintsList.add(legalConstraints);
    }
    
    
    /**
     * Gets the list of characteristics properties
     */
    @Override
    public OgcPropertyList<CharacteristicList> getCharacteristicsList()
    {
        return characteristicsList;
    }
    
    
    /**
     * Returns number of characteristics properties
     */
    @Override
    public int getNumCharacteristics()
    {
        if (characteristicsList == null)
            return 0;
        return characteristicsList.size();
    }
    
    
    /**
     * Gets the characteristics property with the given name
     */
    @Override
    public CharacteristicList getCharacteristics(String name)
    {
        if (characteristicsList == null)
            return null;
        return characteristicsList.get(name);
    }
    
    
    /**
     * Adds a new characteristics property
     */
    @Override
    public void addCharacteristics(String name, CharacteristicList characteristics)
    {
        this.characteristicsList.add(name, characteristics);
    }
    
    
    /**
     * Gets the list of capabilities properties
     */
    @Override
    public OgcPropertyList<CapabilityList> getCapabilitiesList()
    {
        return capabilitiesList;
    }
    
    
    /**
     * Returns number of capabilities properties
     */
    @Override
    public int getNumCapabilities()
    {
        if (capabilitiesList == null)
            return 0;
        return capabilitiesList.size();
    }
    
    
    /**
     * Gets the capabilities property with the given name
     */
    @Override
    public CapabilityList getCapabilities(String name)
    {
        if (capabilitiesList == null)
            return null;
        return capabilitiesList.get(name);
    }
    
    
    /**
     * Adds a new capabilities property
     */
    @Override
    public void addCapabilities(String name, CapabilityList capabilities)
    {
        this.capabilitiesList.add(name, capabilities);
    }
    
    
    /**
     * Gets the list of contacts properties
     */
    @Override
    public OgcPropertyList<ContactList> getContactsList()
    {
        return contactsList;
    }
    
    
    /**
     * Returns number of contacts properties
     */
    @Override
    public int getNumContacts()
    {
        if (contactsList == null)
            return 0;
        return contactsList.size();
    }
    
    
    /**
     * Adds a new contacts property
     */
    @Override
    public void addContacts(ContactList contacts)
    {
        this.contactsList.add(contacts);
    }
    
    
    /**
     * Gets the list of documentation properties
     */
    @Override
    public OgcPropertyList<DocumentList> getDocumentationList()
    {
        return documentationList;
    }
    
    
    /**
     * Returns number of documentation properties
     */
    @Override
    public int getNumDocumentations()
    {
        if (documentationList == null)
            return 0;
        return documentationList.size();
    }
    
    
    /**
     * Adds a new documentation property
     */
    @Override
    public void addDocumentation(DocumentList documentation)
    {
        this.documentationList.add(documentation);
    }
    
    
    /**
     * Gets the list of history properties
     */
    @Override
    public OgcPropertyList<EventList> getHistoryList()
    {
        return historyList;
    }
    
    
    /**
     * Returns number of history properties
     */
    @Override
    public int getNumHistorys()
    {
        if (historyList == null)
            return 0;
        return historyList.size();
    }
    
    
    /**
     * Adds a new history property
     */
    @Override
    public void addHistory(EventList history)
    {
        this.historyList.add(history);
    }
    
    
    /**
     * Gets the lang property
     */
    @Override
    public String getLang()
    {
        return lang;
    }
    
    
    /**
     * Checks if lang is set
     */
    @Override
    public boolean isSetLang()
    {
        return (lang != null);
    }
    
    
    /**
     * Sets the lang property
     */
    @Override
    public void setLang(String lang)
    {
        this.lang = lang;
    }
}

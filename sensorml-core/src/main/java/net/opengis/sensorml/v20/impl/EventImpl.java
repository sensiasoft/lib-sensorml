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

import net.opengis.OgcPropertyList;
import net.opengis.gml.v32.AbstractTimeGeometricPrimitive;
import net.opengis.gml.v32.TimeInstant;
import net.opengis.gml.v32.TimePeriod;
import net.opengis.sensorml.v20.AbstractSettings;
import net.opengis.sensorml.v20.ClassifierList;
import net.opengis.sensorml.v20.ContactList;
import net.opengis.sensorml.v20.DocumentList;
import net.opengis.sensorml.v20.Event;
import net.opengis.sensorml.v20.IdentifierList;
import net.opengis.swe.v20.DataComponent;
import org.isotc211.v2005.gmd.MDKeywords;
import org.vast.data.AbstractSWEIdentifiableImpl;


/**
 * POJO class for XML type EventType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public class EventImpl extends AbstractSWEIdentifiableImpl implements Event
{
    private static final long serialVersionUID = 1119218889164694333L;
    protected OgcPropertyList<MDKeywords> keywordsList = new OgcPropertyList<MDKeywords>();
    protected OgcPropertyList<IdentifierList> identificationList = new OgcPropertyList<IdentifierList>();
    protected OgcPropertyList<ClassifierList> classificationList = new OgcPropertyList<ClassifierList>();
    protected OgcPropertyList<ContactList> contactsList = new OgcPropertyList<ContactList>();
    protected OgcPropertyList<DocumentList> documentationList = new OgcPropertyList<DocumentList>();
    protected AbstractTimeGeometricPrimitive time;
    protected OgcPropertyList<DataComponent> propertyList = new OgcPropertyList<DataComponent>();
    protected AbstractSettings configuration;
    protected String definition;
    
    
    public EventImpl()
    {
    }
    
    
    /**
     * Gets the list of keywords properties
     */
    @Override
    public OgcPropertyList<MDKeywords> getKeywordsList()
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
    public void addKeywords(MDKeywords keywords)
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
     * Gets the time property
     */
    @Override
    public AbstractTimeGeometricPrimitive getTime()
    {
        return time;
    }
    
    
    /**
     * Sets the timeAsTimePeriod property
     */
    @Override
    public void setTimeAsTimePeriod(TimePeriod time)
    {
        this.time = time;
    }
    
    
    /**
     * Sets the timeAsTimeInstant property
     */
    @Override
    public void setTimeAsTimeInstant(TimeInstant time)
    {
        this.time = time;
    }
    
    
    /**
     * Gets the list of property properties
     */
    @Override
    public OgcPropertyList<DataComponent> getPropertyList()
    {
        return propertyList;
    }
    
    
    /**
     * Returns number of property properties
     */
    @Override
    public int getNumPropertys()
    {
        if (propertyList == null)
            return 0;
        return propertyList.size();
    }
    
    
    /**
     * Adds a new property property
     */
    @Override
    public void addProperty(DataComponent property)
    {
        this.propertyList.add(property);
    }
    
    
    /**
     * Gets the configuration property
     */
    @Override
    public AbstractSettings getConfiguration()
    {
        return configuration;
    }
    
    
    /**
     * Checks if configuration is set
     */
    @Override
    public boolean isSetConfiguration()
    {
        return (configuration != null);
    }
    
    
    /**
     * Sets the configuration property
     */
    @Override
    public void setConfiguration(AbstractSettings configuration)
    {
        this.configuration = configuration;
    }
    
    
    /**
     * Gets the definition property
     */
    @Override
    public String getDefinition()
    {
        return definition;
    }
    
    
    /**
     * Checks if definition is set
     */
    @Override
    public boolean isSetDefinition()
    {
        return (definition != null);
    }
    
    
    /**
     * Sets the definition property
     */
    @Override
    public void setDefinition(String definition)
    {
        this.definition = definition;
    }
}

/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2015 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package net.opengis.sensorml.v20;

import java.util.List;
import net.opengis.OgcPropertyList;
import net.opengis.gml.v32.AbstractFeature;
import net.opengis.gml.v32.AbstractTimeGeometricPrimitive;
import net.opengis.gml.v32.TimeInstant;
import net.opengis.gml.v32.TimePeriod;
import org.isotc211.v2005.gmd.MDLegalConstraints;


/**
 * POJO class for XML type DescribedObjectType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
@SuppressWarnings("javadoc")
public interface DescribedObject extends AbstractFeature
{
    public final static String DEFAULT_NS_URI = "http://www.opengis.net/sensorml/2.0";
    
        
    /**
     * Gets the list of extension properties
     */
    public List<Object> getExtensionList();
    
    
    /**
     * Returns number of extension properties
     */
    public int getNumExtensions();
    
    
    /**
     * Adds a new extension property
     */
    public void addExtension(Object extension);
    
    
    /**
     * Gets the list of keywords properties
     */
    public OgcPropertyList<KeywordList> getKeywordsList();
    
    
    /**
     * Returns number of keywords properties
     */
    public int getNumKeywords();
    
    
    /**
     * Adds a new keywords property
     */
    public void addKeywords(KeywordList keywords);
    
    
    /**
     * Gets the list of identification properties
     */
    public OgcPropertyList<IdentifierList> getIdentificationList();
    
    
    /**
     * Returns number of identification properties
     */
    public int getNumIdentifications();
    
    
    /**
     * Adds a new identification property
     */
    public void addIdentification(IdentifierList identification);
    
    
    /**
     * Gets the list of classification properties
     */
    public OgcPropertyList<ClassifierList> getClassificationList();
    
    
    /**
     * Returns number of classification properties
     */
    public int getNumClassifications();
    
    
    /**
     * Adds a new classification property
     */
    public void addClassification(ClassifierList classification);
    
    
    /**
     * Gets the list of validTime properties
     */
    public List<AbstractTimeGeometricPrimitive> getValidTimeList();
    
    
    /**
     * Returns number of validTime properties
     */
    public int getNumValidTimes();
    
    
    /**
     * Adds a new validTimeAsTimePeriod property
     */
    public void addValidTimeAsTimePeriod(TimePeriod validTime);
    
    
    /**
     * Adds a new validTimeAsTimeInstant property
     */
    public void addValidTimeAsTimeInstant(TimeInstant validTime);
    
    
    /**
     * Gets the list of securityConstraints properties
     */
    public List<Object> getSecurityConstraintsList();
    
    
    /**
     * Returns number of securityConstraints properties
     */
    public int getNumSecurityConstraints();
    
    
    /**
     * Adds a new securityConstraints property
     */
    public void addSecurityConstraints(Object securityConstraints);
    
    
    /**
     * Gets the list of legalConstraints properties
     */
    public OgcPropertyList<MDLegalConstraints> getLegalConstraintsList();
    
    
    /**
     * Returns number of legalConstraints properties
     */
    public int getNumLegalConstraints();
    
    
    /**
     * Adds a new legalConstraints property
     */
    public void addLegalConstraints(MDLegalConstraints legalConstraints);
    
    
    /**
     * Gets the list of characteristics properties
     */
    public OgcPropertyList<CharacteristicList> getCharacteristicsList();
    
    
    /**
     * Returns number of characteristics properties
     */
    public int getNumCharacteristics();
    
    
    /**
     * Gets the characteristics property with the given name
     */
    public CharacteristicList getCharacteristics(String name);
    
    
    /**
     * Adds a new characteristics property
     */
    public void addCharacteristics(String name, CharacteristicList characteristics);
    
    
    /**
     * Gets the list of capabilities properties
     */
    public OgcPropertyList<CapabilityList> getCapabilitiesList();
    
    
    /**
     * Returns number of capabilities properties
     */
    public int getNumCapabilities();
    
    
    /**
     * Gets the capabilities property with the given name
     */
    public CapabilityList getCapabilities(String name);
    
    
    /**
     * Adds a new capabilities property
     */
    public void addCapabilities(String name, CapabilityList capabilities);
    
    
    /**
     * Gets the list of contacts properties
     */
    public OgcPropertyList<ContactList> getContactsList();
    
    
    /**
     * Returns number of contacts properties
     */
    public int getNumContacts();
    
    
    /**
     * Adds a new contacts property
     */
    public void addContacts(ContactList contacts);
    
    
    /**
     * Gets the list of documentation properties
     */
    public OgcPropertyList<DocumentList> getDocumentationList();
    
    
    /**
     * Returns number of documentation properties
     */
    public int getNumDocumentations();
    
    
    /**
     * Adds a new documentation property
     */
    public void addDocumentation(DocumentList documentation);
    
    
    /**
     * Gets the list of history properties
     */
    public OgcPropertyList<EventList> getHistoryList();
    
    
    /**
     * Returns number of history properties
     */
    public int getNumHistorys();
    
    
    /**
     * Adds a new history property
     */
    public void addHistory(EventList history);
    
    
    /**
     * Gets the lang property
     */
    public String getLang();
    
    
    /**
     * Checks if lang is set
     */
    public boolean isSetLang();
    
    
    /**
     * Sets the lang property
     */
    public void setLang(String lang);
}

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

import net.opengis.OgcPropertyList;
import net.opengis.gml.v32.AbstractTimeGeometricPrimitive;
import net.opengis.gml.v32.TimeInstant;
import net.opengis.gml.v32.TimePeriod;
import net.opengis.swe.v20.DataComponent;
import net.opengis.swe.v20.AbstractSWEIdentifiable;
import org.isotc211.v2005.gmd.MDKeywords;


/**
 * POJO class for XML type EventType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
@SuppressWarnings("javadoc")
public interface Event extends AbstractSWEIdentifiable
{
    
    
    /**
     * Gets the list of keywords properties
     */
    public OgcPropertyList<MDKeywords> getKeywordsList();
    
    
    /**
     * Returns number of keywords properties
     */
    public int getNumKeywords();
    
    
    /**
     * Adds a new keywords property
     */
    public void addKeywords(MDKeywords keywords);
    
    
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
     * Gets the time property
     */
    public AbstractTimeGeometricPrimitive getTime();
    
    
    /**
     * Sets the timeAsTimePeriod property
     */
    public void setTimeAsTimePeriod(TimePeriod time);
    
    
    /**
     * Sets the timeAsTimeInstant property
     */
    public void setTimeAsTimeInstant(TimeInstant time);
    
    
    /**
     * Gets the list of property properties
     */
    public OgcPropertyList<DataComponent> getPropertyList();
    
    
    /**
     * Returns number of property properties
     */
    public int getNumPropertys();
    
    
    /**
     * Adds a new property property
     */
    public void addProperty(DataComponent property);
    
    
    /**
     * Gets the configuration property
     */
    public AbstractSettings getConfiguration();
    
    
    /**
     * Checks if configuration is set
     */
    public boolean isSetConfiguration();
    
    
    /**
     * Sets the configuration property
     */
    public void setConfiguration(AbstractSettings configuration);
    
    
    /**
     * Gets the definition property
     */
    public String getDefinition();
    
    
    /**
     * Checks if definition is set
     */
    public boolean isSetDefinition();
    
    
    /**
     * Sets the definition property
     */
    public void setDefinition(String definition);
}

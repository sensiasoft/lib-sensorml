/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2015 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package org.isotc211.v2005.gmd;

import java.util.List;
import net.opengis.IDateTime;
import net.opengis.OgcProperty;
import net.opengis.OgcPropertyList;
import org.isotc211.v2005.gco.AbstractObject;
import org.isotc211.v2005.gco.CodeListValue;


/**
 * POJO class for XML type CI_Citation_Type(@http://www.isotc211.org/2005/gmd).
 *
 * This is a complex type.
 */
public interface CICitation extends AbstractObject
{
    
    
    /**
     * Gets the title property
     */
    public String getTitle();
    
    
    /**
     * Sets the title property
     */
    public void setTitle(String title);
    
    
    /**
     * Gets the list of alternateTitle properties
     */
    public List<String> getAlternateTitleList();
    
    
    /**
     * Returns number of alternateTitle properties
     */
    public int getNumAlternateTitles();
    
    
    /**
     * Adds a new alternateTitle property
     */
    public void addAlternateTitle(String alternateTitle);
    
    
    /**
     * Gets the list of date properties
     */
    public OgcPropertyList<CIDate> getDateList();
    
    
    /**
     * Returns number of date properties
     */
    public int getNumDates();
    
    
    /**
     * Adds a new date property
     */
    public void addDate(CIDate date);
    
    
    /**
     * Gets the edition property
     */
    public String getEdition();
    
    
    /**
     * Checks if edition is set
     */
    public boolean isSetEdition();
    
    
    /**
     * Sets the edition property
     */
    public void setEdition(String edition);
    
    
    /**
     * Gets the editionDate property
     */
    public IDateTime getEditionDate();
    
    
    /**
     * Checks if editionDate is set
     */
    public boolean isSetEditionDate();
    
    
    /**
     * Sets the editionDateAsDateTime property
     */
    public void setEditionDate(IDateTime editionDate);
    
    
    /**
     * Gets the list of identifier properties
     */
    public OgcPropertyList<MDIdentifier> getIdentifierList();
    
    
    /**
     * Returns number of identifier properties
     */
    public int getNumIdentifiers();
    
    
    /**
     * Adds a new identifier property
     */
    public void addIdentifier(MDIdentifier identifier);
    
    
    /**
     * Gets the list of citedResponsibleParty properties
     */
    public OgcPropertyList<CIResponsibleParty> getCitedResponsiblePartyList();
    
    
    /**
     * Returns number of citedResponsibleParty properties
     */
    public int getNumCitedResponsiblePartys();
    
    
    /**
     * Adds a new citedResponsibleParty property
     */
    public void addCitedResponsibleParty(CIResponsibleParty citedResponsibleParty);
    
    
    /**
     * Gets the list of presentationForm properties
     */
    public List<CodeListValue> getPresentationFormList();
    
    
    /**
     * Returns number of presentationForm properties
     */
    public int getNumPresentationForms();
    
    
    /**
     * Adds a new presentationForm property
     */
    public void addPresentationForm(CodeListValue presentationForm);
    
    
    /**
     * Gets the series property
     */
    public CISeries getSeries();
    
    
    /**
     * Gets extra info (name, xlink, etc.) carried by the series property
     */
    public OgcProperty<CISeries> getSeriesProperty();
    
    
    /**
     * Checks if series is set
     */
    public boolean isSetSeries();
    
    
    /**
     * Sets the series property
     */
    public void setSeries(CISeries series);
    
    
    /**
     * Gets the otherCitationDetails property
     */
    public String getOtherCitationDetails();
    
    
    /**
     * Checks if otherCitationDetails is set
     */
    public boolean isSetOtherCitationDetails();
    
    
    /**
     * Sets the otherCitationDetails property
     */
    public void setOtherCitationDetails(String otherCitationDetails);
    
    
    /**
     * Gets the collectiveTitle property
     */
    public String getCollectiveTitle();
    
    
    /**
     * Checks if collectiveTitle is set
     */
    public boolean isSetCollectiveTitle();
    
    
    /**
     * Sets the collectiveTitle property
     */
    public void setCollectiveTitle(String collectiveTitle);
    
    
    /**
     * Gets the isbn property
     */
    public String getISBN();
    
    
    /**
     * Checks if isbn is set
     */
    public boolean isSetISBN();
    
    
    /**
     * Sets the isbn property
     */
    public void setISBN(String isbn);
    
    
    /**
     * Gets the issn property
     */
    public String getISSN();
    
    
    /**
     * Checks if issn is set
     */
    public boolean isSetISSN();
    
    
    /**
     * Sets the issn property
     */
    public void setISSN(String issn);
}

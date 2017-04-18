/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2015 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package org.isotc211.v2005.gmd.impl;

import java.util.ArrayList;
import java.util.List;
import net.opengis.IDateTime;
import net.opengis.OgcProperty;
import net.opengis.OgcPropertyImpl;
import net.opengis.OgcPropertyList;
import org.isotc211.v2005.gco.CodeListValue;
import org.isotc211.v2005.gco.impl.AbstractObjectImpl;
import org.isotc211.v2005.gmd.CICitation;
import org.isotc211.v2005.gmd.CIDate;
import org.isotc211.v2005.gmd.CIResponsibleParty;
import org.isotc211.v2005.gmd.CISeries;
import org.isotc211.v2005.gmd.MDIdentifier;


/**
 * POJO class for XML type CI_Citation_Type(@http://www.isotc211.org/2005/gmd).
 *
 * This is a complex type.
 */
public class CICitationImpl extends AbstractObjectImpl implements CICitation
{
    private static final long serialVersionUID = 7608369373096811440L;
    protected String title = "";
    protected ArrayList<String> alternateTitleList = new ArrayList<String>();
    protected OgcPropertyList<CIDate> dateList = new OgcPropertyList<CIDate>();
    protected String edition;
    protected IDateTime editionDate;
    protected OgcPropertyList<MDIdentifier> identifierList = new OgcPropertyList<MDIdentifier>();
    protected OgcPropertyList<CIResponsibleParty> citedResponsiblePartyList = new OgcPropertyList<CIResponsibleParty>();
    protected ArrayList<CodeListValue> presentationFormList = new ArrayList<CodeListValue>();
    protected OgcProperty<CISeries> series;
    protected String otherCitationDetails;
    protected String collectiveTitle;
    protected String isbn;
    protected String issn;
    
    
    public CICitationImpl()
    {
    }
    
    
    /**
     * Gets the title property
     */
    @Override
    public String getTitle()
    {
        return title;
    }
    
    
    /**
     * Sets the title property
     */
    @Override
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    
    /**
     * Gets the list of alternateTitle properties
     */
    @Override
    public List<String> getAlternateTitleList()
    {
        return alternateTitleList;
    }
    
    
    /**
     * Returns number of alternateTitle properties
     */
    @Override
    public int getNumAlternateTitles()
    {
        if (alternateTitleList == null)
            return 0;
        return alternateTitleList.size();
    }
    
    
    /**
     * Adds a new alternateTitle property
     */
    @Override
    public void addAlternateTitle(String alternateTitle)
    {
        this.alternateTitleList.add(alternateTitle);
    }
    
    
    /**
     * Gets the list of date properties
     */
    @Override
    public OgcPropertyList<CIDate> getDateList()
    {
        return dateList;
    }
    
    
    /**
     * Returns number of date properties
     */
    @Override
    public int getNumDates()
    {
        if (dateList == null)
            return 0;
        return dateList.size();
    }
    
    
    /**
     * Adds a new date property
     */
    @Override
    public void addDate(CIDate date)
    {
        this.dateList.add(date);
    }
    
    
    /**
     * Gets the edition property
     */
    @Override
    public String getEdition()
    {
        return edition;
    }
    
    
    /**
     * Checks if edition is set
     */
    @Override
    public boolean isSetEdition()
    {
        return (edition != null);
    }
    
    
    /**
     * Sets the edition property
     */
    @Override
    public void setEdition(String edition)
    {
        this.edition = edition;
    }
    
    
    /**
     * Gets the editionDate property
     */
    @Override
    public IDateTime getEditionDate()
    {
        return editionDate;
    }
    
    
    /**
     * Checks if editionDate is set
     */
    @Override
    public boolean isSetEditionDate()
    {
        return (editionDate != null);
    }
    
    
    /**
     * Sets the editionDateAsDateTime property
     */
    @Override
    public void setEditionDate(IDateTime editionDate)
    {
        this.editionDate = editionDate;
    }
    
    
    /**
     * Gets the list of identifier properties
     */
    @Override
    public OgcPropertyList<MDIdentifier> getIdentifierList()
    {
        return identifierList;
    }
    
    
    /**
     * Returns number of identifier properties
     */
    @Override
    public int getNumIdentifiers()
    {
        if (identifierList == null)
            return 0;
        return identifierList.size();
    }
    
    
    /**
     * Adds a new identifier property
     */
    @Override
    public void addIdentifier(MDIdentifier identifier)
    {
        this.identifierList.add(identifier);
    }
    
    
    /**
     * Gets the list of citedResponsibleParty properties
     */
    @Override
    public OgcPropertyList<CIResponsibleParty> getCitedResponsiblePartyList()
    {
        return citedResponsiblePartyList;
    }
    
    
    /**
     * Returns number of citedResponsibleParty properties
     */
    @Override
    public int getNumCitedResponsiblePartys()
    {
        if (citedResponsiblePartyList == null)
            return 0;
        return citedResponsiblePartyList.size();
    }
    
    
    /**
     * Adds a new citedResponsibleParty property
     */
    @Override
    public void addCitedResponsibleParty(CIResponsibleParty citedResponsibleParty)
    {
        this.citedResponsiblePartyList.add(citedResponsibleParty);
    }
    
    
    /**
     * Gets the list of presentationForm properties
     */
    @Override
    public List<CodeListValue> getPresentationFormList()
    {
        return presentationFormList;
    }
    
    
    /**
     * Returns number of presentationForm properties
     */
    @Override
    public int getNumPresentationForms()
    {
        if (presentationFormList == null)
            return 0;
        return presentationFormList.size();
    }
    
    
    /**
     * Adds a new presentationForm property
     */
    @Override
    public void addPresentationForm(CodeListValue presentationForm)
    {
        this.presentationFormList.add(presentationForm);
    }
    
    
    /**
     * Gets the series property
     */
    @Override
    public CISeries getSeries()
    {
        return series.getValue();
    }
    
    
    /**
     * Gets extra info (name, xlink, etc.) carried by the series property
     */
    @Override
    public OgcProperty<CISeries> getSeriesProperty()
    {
        if (series == null)
            series = new OgcPropertyImpl<CISeries>();
        return series;
    }
    
    
    /**
     * Checks if series is set
     */
    @Override
    public boolean isSetSeries()
    {
        return (series != null && series.getValue() != null);
    }
    
    
    /**
     * Sets the series property
     */
    @Override
    public void setSeries(CISeries series)
    {
        if (this.series == null)
            this.series = new OgcPropertyImpl<CISeries>();
        this.series.setValue(series);
    }
    
    
    /**
     * Gets the otherCitationDetails property
     */
    @Override
    public String getOtherCitationDetails()
    {
        return otherCitationDetails;
    }
    
    
    /**
     * Checks if otherCitationDetails is set
     */
    @Override
    public boolean isSetOtherCitationDetails()
    {
        return (otherCitationDetails != null);
    }
    
    
    /**
     * Sets the otherCitationDetails property
     */
    @Override
    public void setOtherCitationDetails(String otherCitationDetails)
    {
        this.otherCitationDetails = otherCitationDetails;
    }
    
    
    /**
     * Gets the collectiveTitle property
     */
    @Override
    public String getCollectiveTitle()
    {
        return collectiveTitle;
    }
    
    
    /**
     * Checks if collectiveTitle is set
     */
    @Override
    public boolean isSetCollectiveTitle()
    {
        return (collectiveTitle != null);
    }
    
    
    /**
     * Sets the collectiveTitle property
     */
    @Override
    public void setCollectiveTitle(String collectiveTitle)
    {
        this.collectiveTitle = collectiveTitle;
    }
    
    
    /**
     * Gets the isbn property
     */
    @Override
    public String getISBN()
    {
        return isbn;
    }
    
    
    /**
     * Checks if isbn is set
     */
    @Override
    public boolean isSetISBN()
    {
        return (isbn != null);
    }
    
    
    /**
     * Sets the isbn property
     */
    @Override
    public void setISBN(String isbn)
    {
        this.isbn = isbn;
    }
    
    
    /**
     * Gets the issn property
     */
    @Override
    public String getISSN()
    {
        return issn;
    }
    
    
    /**
     * Checks if issn is set
     */
    @Override
    public boolean isSetISSN()
    {
        return (issn != null);
    }
    
    
    /**
     * Sets the issn property
     */
    @Override
    public void setISSN(String issn)
    {
        this.issn = issn;
    }
}

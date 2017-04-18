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

import net.opengis.OgcProperty;
import net.opengis.OgcPropertyImpl;
import org.isotc211.v2005.gco.CodeListValue;
import org.isotc211.v2005.gco.impl.AbstractObjectImpl;
import org.isotc211.v2005.gmd.CIContact;
import org.isotc211.v2005.gmd.CIResponsibleParty;


/**
 * POJO class for XML type CI_ResponsibleParty_Type(@http://www.isotc211.org/2005/gmd).
 *
 * This is a complex type.
 */
public class CIResponsiblePartyImpl extends AbstractObjectImpl implements CIResponsibleParty
{
    private static final long serialVersionUID = -8875933513981991132L;
    protected String individualName;
    protected String organisationName;
    protected String positionName;
    protected OgcProperty<CIContact> contactInfo;
    protected CodeListValue role;
    
    
    public CIResponsiblePartyImpl()
    {
    }
    
    
    /**
     * Gets the individualName property
     */
    @Override
    public String getIndividualName()
    {
        return individualName;
    }
    
    
    /**
     * Checks if individualName is set
     */
    @Override
    public boolean isSetIndividualName()
    {
        return (individualName != null);
    }
    
    
    /**
     * Sets the individualName property
     */
    @Override
    public void setIndividualName(String individualName)
    {
        this.individualName = individualName;
    }
    
    
    /**
     * Gets the organisationName property
     */
    @Override
    public String getOrganisationName()
    {
        return organisationName;
    }
    
    
    /**
     * Checks if organisationName is set
     */
    @Override
    public boolean isSetOrganisationName()
    {
        return (organisationName != null);
    }
    
    
    /**
     * Sets the organisationName property
     */
    @Override
    public void setOrganisationName(String organisationName)
    {
        this.organisationName = organisationName;
    }
    
    
    /**
     * Gets the positionName property
     */
    @Override
    public String getPositionName()
    {
        return positionName;
    }
    
    
    /**
     * Checks if positionName is set
     */
    @Override
    public boolean isSetPositionName()
    {
        return (positionName != null);
    }
    
    
    /**
     * Sets the positionName property
     */
    @Override
    public void setPositionName(String positionName)
    {
        this.positionName = positionName;
    }
    
    
    /**
     * Gets the contactInfo property
     */
    @Override
    public CIContact getContactInfo()
    {
        if (contactInfo == null)
            contactInfo = new OgcPropertyImpl<CIContact>(new CIContactImpl());
        return contactInfo.getValue();
    }
    
    
    /**
     * Gets extra info (name, xlink, etc.) carried by the contactInfo property
     */
    @Override
    public OgcProperty<CIContact> getContactInfoProperty()
    {
        if (contactInfo == null)
            contactInfo = new OgcPropertyImpl<CIContact>();
        return contactInfo;
    }
    
    
    /**
     * Checks if contactInfo is set
     */
    @Override
    public boolean isSetContactInfo()
    {
        return (contactInfo != null && contactInfo.getValue() != null);
    }
    
    
    /**
     * Sets the contactInfo property
     */
    @Override
    public void setContactInfo(CIContact contactInfo)
    {
        if (this.contactInfo == null)
            this.contactInfo = new OgcPropertyImpl<CIContact>();
        this.contactInfo.setValue(contactInfo);
    }
    
    
    /**
     * Gets the role property
     */
    @Override
    public CodeListValue getRole()
    {
        return role;
    }
    
    
    /**
     * Sets the role property
     */
    @Override
    public void setRole(CodeListValue role)
    {
        this.role = role;
    }
}

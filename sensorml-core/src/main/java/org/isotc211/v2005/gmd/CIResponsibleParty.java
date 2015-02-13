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

import net.opengis.OgcProperty;
import org.isotc211.v2005.gco.AbstractObject;
import org.isotc211.v2005.gco.CodeListValue;


/**
 * POJO class for XML type CI_ResponsibleParty_Type(@http://www.isotc211.org/2005/gmd).
 *
 * This is a complex type.
 */
public interface CIResponsibleParty extends AbstractObject
{
    
    
    /**
     * Gets the individualName property
     */
    public String getIndividualName();
    
    
    /**
     * Checks if individualName is set
     */
    public boolean isSetIndividualName();
    
    
    /**
     * Sets the individualName property
     */
    public void setIndividualName(String individualName);
    
    
    /**
     * Gets the organisationName property
     */
    public String getOrganisationName();
    
    
    /**
     * Checks if organisationName is set
     */
    public boolean isSetOrganisationName();
    
    
    /**
     * Sets the organisationName property
     */
    public void setOrganisationName(String organisationName);
    
    
    /**
     * Gets the positionName property
     */
    public String getPositionName();
    
    
    /**
     * Checks if positionName is set
     */
    public boolean isSetPositionName();
    
    
    /**
     * Sets the positionName property
     */
    public void setPositionName(String positionName);
    
    
    /**
     * Gets the contactInfo property
     */
    public CIContact getContactInfo();
    
    
    /**
     * Gets extra info (name, xlink, etc.) carried by the contactInfo property
     */
    public OgcProperty<CIContact> getContactInfoProperty();
    
    
    /**
     * Checks if contactInfo is set
     */
    public boolean isSetContactInfo();
    
    
    /**
     * Sets the contactInfo property
     */
    public void setContactInfo(CIContact contactInfo);
    
    
    /**
     * Gets the role property
     */
    public CodeListValue getRole();
    
    
    /**
     * Sets the role property
     */
    public void setRole(CodeListValue role);
}

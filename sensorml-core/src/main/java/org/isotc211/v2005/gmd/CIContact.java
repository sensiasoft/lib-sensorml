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


/**
 * POJO class for XML type CI_Contact_Type(@http://www.isotc211.org/2005/gmd).
 *
 * This is a complex type.
 */
public interface CIContact extends AbstractObject
{
    
    
    /**
     * Gets the phone property
     */
    public CITelephone getPhone();
    
    
    /**
     * Gets extra info (name, xlink, etc.) carried by the phone property
     */
    public OgcProperty<CITelephone> getPhoneProperty();
    
    
    /**
     * Checks if phone is set
     */
    public boolean isSetPhone();
    
    
    /**
     * Sets the phone property
     */
    public void setPhone(CITelephone phone);
    
    
    /**
     * Gets the address property
     */
    public CIAddress getAddress();
    
    
    /**
     * Gets extra info (name, xlink, etc.) carried by the address property
     */
    public OgcProperty<CIAddress> getAddressProperty();
    
    
    /**
     * Checks if address is set
     */
    public boolean isSetAddress();
    
    
    /**
     * Sets the address property
     */
    public void setAddress(CIAddress address);
    
    
    /**
     * Gets the onlineResource property
     */
    public CIOnlineResource getOnlineResource();
    
    
    /**
     * Gets extra info (name, xlink, etc.) carried by the onlineResource property
     */
    public OgcProperty<CIOnlineResource> getOnlineResourceProperty();
    
    
    /**
     * Checks if onlineResource is set
     */
    public boolean isSetOnlineResource();
    
    
    /**
     * Sets the onlineResource property
     */
    public void setOnlineResource(CIOnlineResource onlineResource);
    
    
    /**
     * Gets the hoursOfService property
     */
    public String getHoursOfService();
    
    
    /**
     * Checks if hoursOfService is set
     */
    public boolean isSetHoursOfService();
    
    
    /**
     * Sets the hoursOfService property
     */
    public void setHoursOfService(String hoursOfService);
    
    
    /**
     * Gets the contactInstructions property
     */
    public String getContactInstructions();
    
    
    /**
     * Checks if contactInstructions is set
     */
    public boolean isSetContactInstructions();
    
    
    /**
     * Sets the contactInstructions property
     */
    public void setContactInstructions(String contactInstructions);
}

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
import org.isotc211.v2005.gco.impl.AbstractObjectImpl;
import org.isotc211.v2005.gmd.CIAddress;
import org.isotc211.v2005.gmd.CIContact;
import org.isotc211.v2005.gmd.CIOnlineResource;
import org.isotc211.v2005.gmd.CITelephone;


/**
 * POJO class for XML type CI_Contact_Type(@http://www.isotc211.org/2005/gmd).
 *
 * This is a complex type.
 */
public class CIContactImpl extends AbstractObjectImpl implements CIContact
{
    private static final long serialVersionUID = 5712212318068712104L;
    protected OgcProperty<CITelephone> phone;
    protected OgcProperty<CIAddress> address;
    protected OgcProperty<CIOnlineResource> onlineResource;
    protected String hoursOfService;
    protected String contactInstructions;
    
    
    public CIContactImpl()
    {
    }
    
    
    /**
     * Gets the phone property
     */
    @Override
    public CITelephone getPhone()
    {
        if (phone == null)
            phone = new OgcPropertyImpl<CITelephone>(new CITelephoneImpl());
        return phone.getValue();
    }
    
    
    /**
     * Gets extra info (name, xlink, etc.) carried by the phone property
     */
    @Override
    public OgcProperty<CITelephone> getPhoneProperty()
    {
        if (phone == null)
            phone = new OgcPropertyImpl<CITelephone>();
        return phone;
    }
    
    
    /**
     * Checks if phone is set
     */
    @Override
    public boolean isSetPhone()
    {
        return (phone != null && phone.getValue() != null);
    }
    
    
    /**
     * Sets the phone property
     */
    @Override
    public void setPhone(CITelephone phone)
    {
        if (this.phone == null)
            this.phone = new OgcPropertyImpl<CITelephone>();
        this.phone.setValue(phone);
    }
    
    
    /**
     * Gets the address property
     */
    @Override
    public CIAddress getAddress()
    {
        if (address == null)
            address = new OgcPropertyImpl<CIAddress>(new CIAddressImpl());
        return address.getValue();
    }
    
    
    /**
     * Gets extra info (name, xlink, etc.) carried by the address property
     */
    @Override
    public OgcProperty<CIAddress> getAddressProperty()
    {
        if (address == null)
            address = new OgcPropertyImpl<CIAddress>();
        return address;
    }
    
    
    /**
     * Checks if address is set
     */
    @Override
    public boolean isSetAddress()
    {
        return (address != null && address.getValue() != null);
    }
    
    
    /**
     * Sets the address property
     */
    @Override
    public void setAddress(CIAddress address)
    {
        if (this.address == null)
            this.address = new OgcPropertyImpl<CIAddress>();
        this.address.setValue(address);
    }
    
    
    /**
     * Gets the onlineResource property
     */
    @Override
    public CIOnlineResource getOnlineResource()
    {
        return onlineResource.getValue();
    }
    
    
    /**
     * Gets extra info (name, xlink, etc.) carried by the onlineResource property
     */
    @Override
    public OgcProperty<CIOnlineResource> getOnlineResourceProperty()
    {
        if (onlineResource == null)
            onlineResource = new OgcPropertyImpl<CIOnlineResource>();
        return onlineResource;
    }
    
    
    /**
     * Checks if onlineResource is set
     */
    @Override
    public boolean isSetOnlineResource()
    {
        return (onlineResource != null && onlineResource.getValue() != null);
    }
    
    
    /**
     * Sets the onlineResource property
     */
    @Override
    public void setOnlineResource(CIOnlineResource onlineResource)
    {
        if (this.onlineResource == null)
            this.onlineResource = new OgcPropertyImpl<CIOnlineResource>();
        this.onlineResource.setValue(onlineResource);
    }
    
    
    /**
     * Gets the hoursOfService property
     */
    @Override
    public String getHoursOfService()
    {
        return hoursOfService;
    }
    
    
    /**
     * Checks if hoursOfService is set
     */
    @Override
    public boolean isSetHoursOfService()
    {
        return (hoursOfService != null);
    }
    
    
    /**
     * Sets the hoursOfService property
     */
    @Override
    public void setHoursOfService(String hoursOfService)
    {
        this.hoursOfService = hoursOfService;
    }
    
    
    /**
     * Gets the contactInstructions property
     */
    @Override
    public String getContactInstructions()
    {
        return contactInstructions;
    }
    
    
    /**
     * Checks if contactInstructions is set
     */
    @Override
    public boolean isSetContactInstructions()
    {
        return (contactInstructions != null);
    }
    
    
    /**
     * Sets the contactInstructions property
     */
    @Override
    public void setContactInstructions(String contactInstructions)
    {
        this.contactInstructions = contactInstructions;
    }
}

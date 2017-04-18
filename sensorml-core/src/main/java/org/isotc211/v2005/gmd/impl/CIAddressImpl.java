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
import org.isotc211.v2005.gco.impl.AbstractObjectImpl;
import org.isotc211.v2005.gmd.CIAddress;


/**
 * POJO class for XML type CI_Address_Type(@http://www.isotc211.org/2005/gmd).
 *
 * This is a complex type.
 */
public class CIAddressImpl extends AbstractObjectImpl implements CIAddress
{
    private static final long serialVersionUID = -4556347952298276291L;
    protected ArrayList<String> deliveryPointList = new ArrayList<String>();
    protected String city;
    protected String administrativeArea;
    protected String postalCode;
    protected String country;
    protected ArrayList<String> electronicMailAddressList = new ArrayList<String>();
    
    
    public CIAddressImpl()
    {
    }
    
    
    /**
     * Gets the list of deliveryPoint properties
     */
    @Override
    public List<String> getDeliveryPointList()
    {
        return deliveryPointList;
    }
    
    
    /**
     * Returns number of deliveryPoint properties
     */
    @Override
    public int getNumDeliveryPoints()
    {
        if (deliveryPointList == null)
            return 0;
        return deliveryPointList.size();
    }
    
    
    /**
     * Adds a new deliveryPoint property
     */
    @Override
    public void addDeliveryPoint(String deliveryPoint)
    {
        this.deliveryPointList.add(deliveryPoint);
    }
    
    
    /**
     * Gets the city property
     */
    @Override
    public String getCity()
    {
        return city;
    }
    
    
    /**
     * Checks if city is set
     */
    @Override
    public boolean isSetCity()
    {
        return (city != null);
    }
    
    
    /**
     * Sets the city property
     */
    @Override
    public void setCity(String city)
    {
        this.city = city;
    }
    
    
    /**
     * Gets the administrativeArea property
     */
    @Override
    public String getAdministrativeArea()
    {
        return administrativeArea;
    }
    
    
    /**
     * Checks if administrativeArea is set
     */
    @Override
    public boolean isSetAdministrativeArea()
    {
        return (administrativeArea != null);
    }
    
    
    /**
     * Sets the administrativeArea property
     */
    @Override
    public void setAdministrativeArea(String administrativeArea)
    {
        this.administrativeArea = administrativeArea;
    }
    
    
    /**
     * Gets the postalCode property
     */
    @Override
    public String getPostalCode()
    {
        return postalCode;
    }
    
    
    /**
     * Checks if postalCode is set
     */
    @Override
    public boolean isSetPostalCode()
    {
        return (postalCode != null);
    }
    
    
    /**
     * Sets the postalCode property
     */
    @Override
    public void setPostalCode(String postalCode)
    {
        this.postalCode = postalCode;
    }
    
    
    /**
     * Gets the country property
     */
    @Override
    public String getCountry()
    {
        return country;
    }
    
    
    /**
     * Checks if country is set
     */
    @Override
    public boolean isSetCountry()
    {
        return (country != null);
    }
    
    
    /**
     * Sets the country property
     */
    @Override
    public void setCountry(String country)
    {
        this.country = country;
    }
    
    
    /**
     * Gets the list of electronicMailAddress properties
     */
    @Override
    public List<String> getElectronicMailAddressList()
    {
        return electronicMailAddressList;
    }
    
    
    /**
     * Returns number of electronicMailAddress properties
     */
    @Override
    public int getNumElectronicMailAddress()
    {
        if (electronicMailAddressList == null)
            return 0;
        return electronicMailAddressList.size();
    }
    
    
    /**
     * Adds a new electronicMailAddress property
     */
    @Override
    public void addElectronicMailAddress(String electronicMailAddress)
    {
        this.electronicMailAddressList.add(electronicMailAddress);
    }
}

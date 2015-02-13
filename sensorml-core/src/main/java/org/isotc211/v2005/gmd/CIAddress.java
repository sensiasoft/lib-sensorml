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
import org.isotc211.v2005.gco.AbstractObject;


/**
 * POJO class for XML type CI_Address_Type(@http://www.isotc211.org/2005/gmd).
 *
 * This is a complex type.
 */
public interface CIAddress extends AbstractObject
{
    
    
    /**
     * Gets the list of deliveryPoint properties
     */
    public List<String> getDeliveryPointList();
    
    
    /**
     * Returns number of deliveryPoint properties
     */
    public int getNumDeliveryPoints();
    
    
    /**
     * Adds a new deliveryPoint property
     */
    public void addDeliveryPoint(String deliveryPoint);
    
    
    /**
     * Gets the city property
     */
    public String getCity();
    
    
    /**
     * Checks if city is set
     */
    public boolean isSetCity();
    
    
    /**
     * Sets the city property
     */
    public void setCity(String city);
    
    
    /**
     * Gets the administrativeArea property
     */
    public String getAdministrativeArea();
    
    
    /**
     * Checks if administrativeArea is set
     */
    public boolean isSetAdministrativeArea();
    
    
    /**
     * Sets the administrativeArea property
     */
    public void setAdministrativeArea(String administrativeArea);
    
    
    /**
     * Gets the postalCode property
     */
    public String getPostalCode();
    
    
    /**
     * Checks if postalCode is set
     */
    public boolean isSetPostalCode();
    
    
    /**
     * Sets the postalCode property
     */
    public void setPostalCode(String postalCode);
    
    
    /**
     * Gets the country property
     */
    public String getCountry();
    
    
    /**
     * Checks if country is set
     */
    public boolean isSetCountry();
    
    
    /**
     * Sets the country property
     */
    public void setCountry(String country);
    
    
    /**
     * Gets the list of electronicMailAddress properties
     */
    public List<String> getElectronicMailAddressList();
    
    
    /**
     * Returns number of electronicMailAddress properties
     */
    public int getNumElectronicMailAddress();
    
    
    /**
     * Adds a new electronicMailAddress property
     */
    public void addElectronicMailAddress(String electronicMailAddress);
}

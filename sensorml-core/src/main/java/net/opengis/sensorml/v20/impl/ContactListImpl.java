/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2015 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package net.opengis.sensorml.v20.impl;

import net.opengis.OgcPropertyList;
import net.opengis.sensorml.v20.ContactList;
import org.isotc211.v2005.gmd.CIResponsibleParty;


/**
 * POJO class for XML type ContactListType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public class ContactListImpl extends AbstractMetadataListImpl implements ContactList
{
    private static final long serialVersionUID = -811587242228194684L;
    protected OgcPropertyList<CIResponsibleParty> contactList = new OgcPropertyList<CIResponsibleParty>();
    
    
    public ContactListImpl()
    {
    }
    
    
    /**
     * Gets the list of contact properties
     */
    @Override
    public OgcPropertyList<CIResponsibleParty> getContactList()
    {
        return contactList;
    }
    
    
    /**
     * Returns number of contact properties
     */
    @Override
    public int getNumContacts()
    {
        if (contactList == null)
            return 0;
        return contactList.size();
    }
    
    
    /**
     * Adds a new contact property
     */
    @Override
    public void addContact(CIResponsibleParty contact)
    {
        this.contactList.add(contact);
    }
}

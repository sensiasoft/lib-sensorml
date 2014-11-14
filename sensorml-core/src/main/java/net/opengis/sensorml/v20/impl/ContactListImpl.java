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
    static final long serialVersionUID = 1L;
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

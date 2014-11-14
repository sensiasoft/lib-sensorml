package net.opengis.sensorml.v20;

import net.opengis.OgcPropertyList;
import org.isotc211.v2005.gmd.CIResponsibleParty;


/**
 * POJO class for XML type ContactListType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public interface ContactList extends AbstractMetadataList
{
    
    
    /**
     * Gets the list of contact properties
     */
    public OgcPropertyList<CIResponsibleParty> getContactList();
    
    
    /**
     * Returns number of contact properties
     */
    public int getNumContacts();
    
    
    /**
     * Adds a new contact property
     */
    public void addContact(CIResponsibleParty contact);
}

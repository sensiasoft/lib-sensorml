package net.opengis.sensorml.v20;

import java.util.List;


/**
 * POJO class for XML type IdentifierListType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public interface IdentifierList extends AbstractMetadataList
{
    
    
    /**
     * Gets the list of identifier2 properties
     */
    public List<Term> getIdentifier2List();
    
    
    /**
     * Returns number of identifier2 properties
     */
    public int getNumIdentifier2s();
    
    
    /**
     * Adds a new identifier2 property
     */
    public void addIdentifier2(Term identifier2);
}

package net.opengis.sensorml.v20;

import net.opengis.swe.v20.AbstractSWEIdentifiable;


/**
 * POJO class for XML type AbstractMetadataListType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public interface AbstractMetadataList extends AbstractSWEIdentifiable
{
    
    
    /**
     * Gets the definition property
     */
    public String getDefinition();
    
    
    /**
     * Checks if definition is set
     */
    public boolean isSetDefinition();
    
    
    /**
     * Sets the definition property
     */
    public void setDefinition(String definition);
}

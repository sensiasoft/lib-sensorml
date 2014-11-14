package net.opengis.sensorml.v20;

import net.opengis.OgcPropertyList;
import net.opengis.swe.v20.AbstractSWEIdentifiable;


/**
 * POJO class for XML type SpatialFrameType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public interface SpatialFrame extends AbstractSWEIdentifiable
{
    
    
    /**
     * Gets the origin property
     */
    public String getOrigin();
    
    
    /**
     * Sets the origin property
     */
    public void setOrigin(String origin);
    
    
    /**
     * Gets the list of axis properties
     */
    public OgcPropertyList<String> getAxisList();
    
    
    /**
     * Returns number of axis properties
     */
    public int getNumAxis();
    
    
    /**
     * Gets the axis property with the given name
     */
    public String getAxis(String name);
    
    
    /**
     * Adds a new axis property
     */
    public void addAxis(String name, String axisDesc);
}

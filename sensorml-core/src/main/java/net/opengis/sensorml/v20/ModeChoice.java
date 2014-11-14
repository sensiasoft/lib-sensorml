package net.opengis.sensorml.v20;

import net.opengis.OgcPropertyList;


/**
 * POJO class for XML type ModeChoiceType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public interface ModeChoice extends AbstractModes
{
    
    
    /**
     * Gets the list of mode properties
     */
    public OgcPropertyList<Mode> getModeList();
    
    
    /**
     * Returns number of mode properties
     */
    public int getNumModes();
    
    
    /**
     * Adds a new mode property
     */
    public void addMode(Mode mode);
}

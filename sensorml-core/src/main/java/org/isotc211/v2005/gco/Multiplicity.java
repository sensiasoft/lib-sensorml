package org.isotc211.v2005.gco;

import net.opengis.OgcPropertyList;


/**
 * POJO class for XML type Multiplicity_Type(@http://www.isotc211.org/2005/gco).
 *
 * This is a complex type.
 */
public interface Multiplicity extends AbstractObject
{
    
    
    /**
     * Gets the list of range properties
     */
    public OgcPropertyList<MultiplicityRange> getRangeList();
    
    
    /**
     * Returns number of range properties
     */
    public int getNumRanges();
    
    
    /**
     * Adds a new range property
     */
    public void addRange(MultiplicityRange range);
}

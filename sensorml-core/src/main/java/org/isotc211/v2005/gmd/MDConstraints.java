package org.isotc211.v2005.gmd;

import java.util.List;
import org.isotc211.v2005.gco.AbstractObject;


/**
 * POJO class for XML type MD_Constraints_Type(@http://www.isotc211.org/2005/gmd).
 *
 * This is a complex type.
 */
public interface MDConstraints extends AbstractObject
{
    
    
    /**
     * Gets the list of useLimitation properties
     */
    public List<String> getUseLimitationList();
    
    
    /**
     * Returns number of useLimitation properties
     */
    public int getNumUseLimitations();
    
    
    /**
     * Adds a new useLimitation property
     */
    public void addUseLimitation(String useLimitation);
}

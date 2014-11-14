package org.isotc211.v2005.gmd;

import java.util.List;
import org.isotc211.v2005.gco.AbstractObject;


/**
 * POJO class for XML type CI_Telephone_Type(@http://www.isotc211.org/2005/gmd).
 *
 * This is a complex type.
 */
public interface CITelephone extends AbstractObject
{
    
    
    /**
     * Gets the list of voice properties
     */
    public List<String> getVoiceList();
    
    
    /**
     * Returns number of voice properties
     */
    public int getNumVoices();
    
    
    /**
     * Adds a new voice property
     */
    public void addVoice(String voice);
    
    
    /**
     * Gets the list of facsimile properties
     */
    public List<String> getFacsimileList();
    
    
    /**
     * Returns number of facsimile properties
     */
    public int getNumFacsimiles();
    
    
    /**
     * Adds a new facsimile property
     */
    public void addFacsimile(String facsimile);
}

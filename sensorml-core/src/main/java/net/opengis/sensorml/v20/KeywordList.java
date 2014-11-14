package net.opengis.sensorml.v20;

import java.util.List;
import net.opengis.gml.v32.Reference;


/**
 * POJO class for XML type KeywordListType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public interface KeywordList extends AbstractMetadataList
{
    
    
    /**
     * Gets the codeSpace property
     */
    public Reference getCodeSpace();
    
    
    /**
     * Checks if codeSpace is set
     */
    public boolean isSetCodeSpace();
    
    
    /**
     * Sets the codeSpace property
     */
    public void setCodeSpace(Reference codeSpace);
    
    
    /**
     * Gets the list of keyword properties
     */
    public List<String> getKeywordList();
    
    
    /**
     * Returns number of keyword properties
     */
    public int getNumKeywords();
    
    
    /**
     * Adds a new keyword property
     */
    public void addKeyword(String keyword);
}

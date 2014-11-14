package org.isotc211.v2005.gmd;

import java.util.List;
import net.opengis.OgcProperty;
import org.isotc211.v2005.gco.AbstractObject;
import org.isotc211.v2005.gco.CodeListValue;


/**
 * POJO class for XML type MD_Keywords_Type(@http://www.isotc211.org/2005/gmd).
 *
 * This is a complex type.
 */
public interface MDKeywords extends AbstractObject
{
    
    
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
    
    
    /**
     * Gets the type property
     */
    public CodeListValue getType();
    
    
    /**
     * Checks if type is set
     */
    public boolean isSetType();
    
    
    /**
     * Sets the type property
     */
    public void setType(CodeListValue type);
    
    
    /**
     * Gets the thesaurusName property
     */
    public CICitation getThesaurusName();
    
    
    /**
     * Gets extra info (name, xlink, etc.) carried by the thesaurusName property
     */
    public OgcProperty<CICitation> getThesaurusNameProperty();
    
    
    /**
     * Checks if thesaurusName is set
     */
    public boolean isSetThesaurusName();
    
    
    /**
     * Sets the thesaurusName property
     */
    public void setThesaurusName(CICitation thesaurusName);
}

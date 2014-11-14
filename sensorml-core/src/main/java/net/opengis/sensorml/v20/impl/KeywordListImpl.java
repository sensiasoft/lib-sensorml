package net.opengis.sensorml.v20.impl;

import java.util.ArrayList;
import java.util.List;
import net.opengis.gml.v32.Reference;
import net.opengis.sensorml.v20.KeywordList;


/**
 * POJO class for XML type KeywordListType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public class KeywordListImpl extends AbstractMetadataListImpl implements KeywordList
{
    static final long serialVersionUID = 1L;
    protected Reference codeSpace;
    protected List<String> keywordList = new ArrayList<String>();
    
    
    public KeywordListImpl()
    {
    }
    
    
    /**
     * Gets the codeSpace property
     */
    @Override
    public Reference getCodeSpace()
    {
        return codeSpace;
    }
    
    
    /**
     * Checks if codeSpace is set
     */
    @Override
    public boolean isSetCodeSpace()
    {
        return (codeSpace != null);
    }
    
    
    /**
     * Sets the codeSpace property
     */
    @Override
    public void setCodeSpace(Reference codeSpace)
    {
        this.codeSpace = codeSpace;
    }
    
    
    /**
     * Gets the list of keyword properties
     */
    @Override
    public List<String> getKeywordList()
    {
        return keywordList;
    }
    
    
    /**
     * Returns number of keyword properties
     */
    @Override
    public int getNumKeywords()
    {
        if (keywordList == null)
            return 0;
        return keywordList.size();
    }
    
    
    /**
     * Adds a new keyword property
     */
    @Override
    public void addKeyword(String keyword)
    {
        this.keywordList.add(keyword);
    }
}

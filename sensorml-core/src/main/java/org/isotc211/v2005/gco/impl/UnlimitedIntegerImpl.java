package org.isotc211.v2005.gco.impl;

import org.isotc211.v2005.gco.UnlimitedInteger;


/**
 * POJO class for XML type UnlimitedInteger_Type(@http://www.isotc211.org/2005/gco).
 *
 */
public class UnlimitedIntegerImpl implements UnlimitedInteger
{
    static final long serialVersionUID = 1L;
    protected Boolean isInfinite;
    protected int value;
    
    
    public UnlimitedIntegerImpl()
    {
    }
    
    
    /**
     * Gets the isInfinite property
     */
    @Override
    public boolean getIsInfinite()
    {
        return isInfinite;
    }
    
    
    /**
     * Checks if isInfinite is set
     */
    @Override
    public boolean isSetIsInfinite()
    {
        return (isInfinite != null);
    }
    
    
    /**
     * Sets the isInfinite property
     */
    @Override
    public void setIsInfinite(boolean isInfinite)
    {
        this.isInfinite = isInfinite;
    }
    
    
    /**
     * Unsets the isInfinite property
     */
    @Override
    public void unSetIsInfinite()
    {
        this.isInfinite = null;
    }
    
    
    /**
     * Gets the inline value
     */
    @Override
    public int getValue()
    {
        return value;
    }
    
    
    /**
     * Sets the inline value
     */
    @Override
    public void setValue(int value)
    {
        this.value = value;
    }
}

package org.isotc211.v2005.gco.impl;

import org.isotc211.v2005.gco.MultiplicityRange;
import org.isotc211.v2005.gco.UnlimitedInteger;


/**
 * POJO class for XML type MultiplicityRange_Type(@http://www.isotc211.org/2005/gco).
 *
 * This is a complex type.
 */
public class MultiplicityRangeImpl extends AbstractObjectImpl implements MultiplicityRange
{
    static final long serialVersionUID = 1L;
    protected int lower;
    protected UnlimitedInteger upper;
    
    
    public MultiplicityRangeImpl()
    {
    }
    
    
    /**
     * Gets the lower property
     */
    @Override
    public int getLower()
    {
        return lower;
    }
    
    
    /**
     * Sets the lower property
     */
    @Override
    public void setLower(int lower)
    {
        this.lower = lower;
    }
    
    
    /**
     * Gets the upper property
     */
    @Override
    public UnlimitedInteger getUpper()
    {
        return upper;
    }
    
    
    /**
     * Sets the upper property
     */
    @Override
    public void setUpper(UnlimitedInteger upper)
    {
        this.upper = upper;
    }
}

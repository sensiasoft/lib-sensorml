package org.isotc211.v2005.gco.impl;

import org.isotc211.v2005.gco.RecordType;


/**
 * POJO class for XML type RecordType_Type(@http://www.isotc211.org/2005/gco).
 *
 */
public class RecordTypeImpl extends net.opengis.OgcPropertyImpl<Object> implements RecordType
{
    static final long serialVersionUID = 1L;
    protected String value;
    
    
    public RecordTypeImpl()
    {
    }
    
    
    /**
     * Gets the inline value
     */
    @Override
    public String getValue()
    {
        return value;
    }
    
    
    /**
     * Sets the inline value
     */
    @Override
    public void setValue(String value)
    {
        this.value = value;
    }
}

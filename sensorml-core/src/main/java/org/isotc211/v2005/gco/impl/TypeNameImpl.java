package org.isotc211.v2005.gco.impl;

import org.isotc211.v2005.gco.TypeName;


/**
 * POJO class for XML type TypeName_Type(@http://www.isotc211.org/2005/gco).
 *
 * This is a complex type.
 */
public class TypeNameImpl extends AbstractObjectImpl implements TypeName
{
    static final long serialVersionUID = 1L;
    protected String aName = "";
    
    
    public TypeNameImpl()
    {
    }
    
    
    /**
     * Gets the aName property
     */
    @Override
    public String getAName()
    {
        return aName;
    }
    
    
    /**
     * Sets the aName property
     */
    @Override
    public void setAName(String aName)
    {
        this.aName = aName;
    }
}

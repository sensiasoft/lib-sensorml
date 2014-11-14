package org.isotc211.v2005.gco.impl;

import org.isotc211.v2005.gco.AbstractObject;


/**
 * POJO class for XML type AbstractObject_Type(@http://www.isotc211.org/2005/gco).
 *
 * This is a complex type.
 */
public abstract class AbstractObjectImpl implements AbstractObject
{
    static final long serialVersionUID = 1L;
    protected String id;
    protected String uuid;
    
    
    public AbstractObjectImpl()
    {
    }
    
    
    /**
     * Gets the id property
     */
    @Override
    public String getId()
    {
        return id;
    }
    
    
    /**
     * Checks if id is set
     */
    @Override
    public boolean isSetId()
    {
        return (id != null);
    }
    
    
    /**
     * Sets the id property
     */
    @Override
    public void setId(String id)
    {
        this.id = id;
    }
    
    
    /**
     * Gets the uuid property
     */
    @Override
    public String getUuid()
    {
        return uuid;
    }
    
    
    /**
     * Checks if uuid is set
     */
    @Override
    public boolean isSetUuid()
    {
        return (uuid != null);
    }
    
    
    /**
     * Sets the uuid property
     */
    @Override
    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }
}

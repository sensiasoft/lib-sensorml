package net.opengis.sensorml.v20.impl;

import net.opengis.sensorml.v20.AbstractAlgorithm;


/**
 * POJO class for XML type AbstractAlgorithmType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public abstract class AbstractAlgorithmImpl implements AbstractAlgorithm
{
    static final long serialVersionUID = 1L;
    protected String id;
    
    
    public AbstractAlgorithmImpl()
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
}

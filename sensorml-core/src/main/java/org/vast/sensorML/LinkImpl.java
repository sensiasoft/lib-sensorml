package org.vast.sensorML;

import net.opengis.sensorml.v20.Link;


/**
 * POJO class for XML type LinkType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public class LinkImpl implements Link
{
    static final long serialVersionUID = 1L;
    protected String source;
    protected String destination;
    protected String id;
    
    
    public LinkImpl()
    {
    }
    
    
    /**
     * Gets the source property
     */
    @Override
    public String getSource()
    {
        return source;
    }
    
    
    /**
     * Sets the source property
     */
    @Override
    public void setSource(String source)
    {
        this.source = source;
    }
    
    
    /**
     * Gets the destination property
     */
    @Override
    public String getDestination()
    {
        return destination;
    }
    
    
    /**
     * Sets the destination property
     */
    @Override
    public void setDestination(String destination)
    {
        this.destination = destination;
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

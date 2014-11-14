package net.opengis.sensorml.v20.impl;

import org.vast.data.AbstractSWEIdentifiableImpl;
import net.opengis.OgcPropertyList;
import net.opengis.sensorml.v20.SpatialFrame;


/**
 * POJO class for XML type SpatialFrameType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public class SpatialFrameImpl extends AbstractSWEIdentifiableImpl implements SpatialFrame
{
    static final long serialVersionUID = 1L;
    protected String origin = "";
    protected OgcPropertyList<String> axisList = new OgcPropertyList<String>();
    
    
    public SpatialFrameImpl()
    {
    }
    
    
    /**
     * Gets the origin property
     */
    @Override
    public String getOrigin()
    {
        return origin;
    }
    
    
    /**
     * Sets the origin property
     */
    @Override
    public void setOrigin(String origin)
    {
        this.origin = origin;
    }
    
    
    /**
     * Gets the list of axis properties
     */
    @Override
    public OgcPropertyList<String> getAxisList()
    {
        return axisList;
    }
    
    
    /**
     * Returns number of axis properties
     */
    @Override
    public int getNumAxis()
    {
        if (axisList == null)
            return 0;
        return axisList.size();
    }
    
    
    /**
     * Gets the axis property with the given name
     */
    @Override
    public String getAxis(String name)
    {
        if (axisList == null)
            return null;
        return axisList.get(name);
    }
    
    
    /**
     * Adds a new axis property
     */
    @Override
    public void addAxis(String name, String axisDesc)
    {
        this.axisList.add(name, axisDesc);
    }
}

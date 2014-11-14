package net.opengis.sensorml.v20.impl;

import org.vast.data.AbstractSWEIdentifiableImpl;
import net.opengis.sensorml.v20.TemporalFrame;


/**
 * POJO class for XML type TemporalFrameType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public class TemporalFrameImpl extends AbstractSWEIdentifiableImpl implements TemporalFrame
{
    static final long serialVersionUID = 1L;
    protected String origin = "";
    
    
    public TemporalFrameImpl()
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
}

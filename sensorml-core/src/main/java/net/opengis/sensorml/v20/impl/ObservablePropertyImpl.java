package net.opengis.sensorml.v20.impl;

import org.vast.data.AbstractSWEIdentifiableImpl;
import net.opengis.sensorml.v20.ObservableProperty;


/**
 * POJO class for XML type ObservablePropertyType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public class ObservablePropertyImpl extends AbstractSWEIdentifiableImpl implements ObservableProperty
{
    static final long serialVersionUID = 1L;
    protected String definition = "";
    
    
    public ObservablePropertyImpl()
    {
    }
    
    
    /**
     * Gets the definition property
     */
    @Override
    public String getDefinition()
    {
        return definition;
    }
    
    
    /**
     * Sets the definition property
     */
    @Override
    public void setDefinition(String definition)
    {
        this.definition = definition;
    }
}

package net.opengis.sensorml.v20.impl;

import org.vast.data.AbstractSWEIdentifiableImpl;
import net.opengis.sensorml.v20.AbstractMetadataList;


/**
 * POJO class for XML type AbstractMetadataListType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public class AbstractMetadataListImpl extends AbstractSWEIdentifiableImpl implements AbstractMetadataList
{
    static final long serialVersionUID = 1L;
    protected String definition;
    
    
    public AbstractMetadataListImpl()
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
     * Checks if definition is set
     */
    @Override
    public boolean isSetDefinition()
    {
        return (definition != null);
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

package net.opengis.sensorml.v20.impl;

import net.opengis.OgcProperty;
import net.opengis.OgcPropertyImpl;
import net.opengis.sensorml.v20.Mode;
import net.opengis.sensorml.v20.Settings;


/**
 * POJO class for XML type ModeType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public class ModeImpl extends DescribedObjectImpl implements Mode
{
    static final long serialVersionUID = 1L;
    protected OgcProperty<Settings> configuration = new OgcPropertyImpl<Settings>();
    
    
    public ModeImpl()
    {
    }
    
    
    /**
     * Gets the configuration property
     */
    @Override
    public Settings getConfiguration()
    {
        return configuration.getValue();
    }
    
    
    /**
     * Gets extra info (name, xlink, etc.) carried by the configuration property
     */
    @Override
    public OgcProperty<Settings> getConfigurationProperty()
    {
        if (configuration == null)
            configuration = new OgcPropertyImpl<Settings>();
        return configuration;
    }
    
    
    /**
     * Sets the configuration property
     */
    @Override
    public void setConfiguration(Settings configuration)
    {
        this.configuration.setValue(configuration);
    }
}

package net.opengis.sensorml.v20;

import net.opengis.OgcProperty;


/**
 * POJO class for XML type ModeType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public interface Mode extends DescribedObject
{
    
    
    /**
     * Gets the configuration property
     */
    public Settings getConfiguration();
    
    
    /**
     * Gets extra info (name, xlink, etc.) carried by the configuration property
     */
    public OgcProperty<Settings> getConfigurationProperty();
    
    
    /**
     * Sets the configuration property
     */
    public void setConfiguration(Settings configuration);
}

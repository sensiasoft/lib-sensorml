package net.opengis.sensorml.v20;

import net.opengis.swe.v20.SimpleComponent;


/**
 * POJO class for XML type ValueSettingPropertyType(@http://www.opengis.net/sensorml/2.0).
 *
 */
public interface ValueSetting extends ConfigSetting<SimpleComponent>
{
      
    /**
     * Gets the inline value
     */
    public String getValue();
    
    
    /**
     * Sets the inline value
     */
    public void setValue(String value);
}

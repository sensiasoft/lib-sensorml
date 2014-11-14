package net.opengis.sensorml.v20;


/**
 * POJO class for XML type ModeSettingPropertyType(@http://www.opengis.net/sensorml/2.0).
 *
 */
public interface ModeSetting extends ConfigSetting<ModeChoice>
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

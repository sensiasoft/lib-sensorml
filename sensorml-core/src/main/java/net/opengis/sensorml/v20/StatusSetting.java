package net.opengis.sensorml.v20;

import net.opengis.swe.v20.DataComponent;


/**
 * POJO class for XML type StatusSettingPropertyType(@http://www.opengis.net/sensorml/2.0).
 *
 */
public interface StatusSetting extends ConfigSetting<DataComponent>
{
    
    /**
     * Gets the inline value
     */
    public Status getValue();
    
    
    /**
     * Sets the inline value
     */
    public void setValue(Status value);
}

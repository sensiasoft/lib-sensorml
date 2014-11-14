package net.opengis.sensorml.v20;

import net.opengis.swe.v20.AllowedTimes;
import net.opengis.swe.v20.AllowedTokens;
import net.opengis.swe.v20.AllowedValues;
import net.opengis.swe.v20.DataConstraint;
import net.opengis.swe.v20.SimpleComponent;


/**
 * POJO class for XML type ValueSettingPropertyType(@http://www.opengis.net/sensorml/2.0).
 *
 */
public interface ConstraintSetting extends ConfigSetting<SimpleComponent>
{
    
    /**
     * Gets the constraint property
     */
    public DataConstraint getValue();
    
    
    /**
     * Sets value as AllowedTimes constraint
     */
    public void setValueAsAllowedTimes(AllowedTimes constraint);
    
    
    /**
     * Sets value as AllowedToken constraint
     */
    public void setValueAsAllowedTokens(AllowedTokens constraint);
    
    
    /**
     * Sets value as AllowedValues constraint
     */
    public void setValueAsAllowedValues(AllowedValues constraint);
}

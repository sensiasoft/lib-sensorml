package net.opengis.sensorml.v20.impl;

import net.opengis.sensorml.v20.ModeChoice;
import net.opengis.sensorml.v20.ModeSetting;


/**
 * POJO class for XML type ModeSettingPropertyType(@http://www.opengis.net/sensorml/2.0).
 *
 */
public class ModeSettingImpl implements ModeSetting
{
    static final long serialVersionUID = 1L;
    protected String ref = "";
    protected String value;
    protected transient ModeChoice refModes;
    
    
    public ModeSettingImpl()
    {
    }
    
    
    public ModeSettingImpl(String ref, String modeName)
    {
        this.ref = ref;
        this.value = modeName;
    }
    
    
    /**
     * Gets the ref property
     */
    @Override
    public String getRef()
    {
        return ref;
    }
    
    
    /**
     * Sets the ref property
     */
    @Override
    public void setRef(String ref)
    {
        this.ref = ref;
    }
    
    
    /**
     * Gets the inline value
     */
    @Override
    public String getValue()
    {
        return value;
    }
    
    
    /**
     * Sets the inline value
     */
    @Override
    public void setValue(String value)
    {
        this.value = value;
    }


    @Override
    public ModeChoice getReferencedObject()
    {
        return refModes;
    }


    @Override
    public void setReferencedObject(ModeChoice refObj)
    {
        this.refModes = refObj;        
    }
}

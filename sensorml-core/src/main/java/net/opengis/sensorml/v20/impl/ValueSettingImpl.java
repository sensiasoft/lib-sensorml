package net.opengis.sensorml.v20.impl;

import net.opengis.sensorml.v20.ValueSetting;
import net.opengis.swe.v20.SimpleComponent;


/**
 * POJO class for XML type ValueSettingPropertyType(@http://www.opengis.net/sensorml/2.0).
 *
 */
public class ValueSettingImpl implements ValueSetting
{
    static final long serialVersionUID = 1L;
    protected String ref = "";
    protected String value;
    protected transient SimpleComponent refComponent;
    
    
    public ValueSettingImpl()
    {
    }
    
    
    public ValueSettingImpl(String ref, String value)
    {
        this.ref = ref;
        this.value = value;
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
    public SimpleComponent getReferencedObject()
    {
        return refComponent;
    }


    @Override
    public void setReferencedObject(SimpleComponent refObj)
    {
        this.refComponent = refObj;        
    }
}

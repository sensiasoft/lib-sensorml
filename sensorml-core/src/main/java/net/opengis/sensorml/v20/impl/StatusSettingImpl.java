package net.opengis.sensorml.v20.impl;

import net.opengis.sensorml.v20.Status;
import net.opengis.sensorml.v20.StatusSetting;
import net.opengis.swe.v20.DataComponent;


/**
 * POJO class for XML type StatusSettingPropertyType(@http://www.opengis.net/sensorml/2.0).
 *
 */
public class StatusSettingImpl implements StatusSetting
{
    static final long serialVersionUID = 1L;
    protected String ref = "";
    protected Status value;
    protected transient DataComponent refComponent;
    
    
    public StatusSettingImpl()
    {
    }
    
    
    public StatusSettingImpl(String ref, Status value)
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
    public Status getValue()
    {
        return value;
    }
    
    
    /**
     * Sets the inline value
     */
    @Override
    public void setValue(Status value)
    {
        this.value = value;
    }


    @Override
    public DataComponent getReferencedObject()
    {
        return refComponent;
    }


    @Override
    public void setReferencedObject(DataComponent refObj)
    {
        this.refComponent = refObj;        
    }
}

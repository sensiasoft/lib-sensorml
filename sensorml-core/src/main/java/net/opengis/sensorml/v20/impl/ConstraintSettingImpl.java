package net.opengis.sensorml.v20.impl;

import net.opengis.sensorml.v20.ConstraintSetting;
import net.opengis.swe.v20.AbstractSWE;
import net.opengis.swe.v20.AllowedTimes;
import net.opengis.swe.v20.AllowedTokens;
import net.opengis.swe.v20.AllowedValues;
import net.opengis.swe.v20.DataConstraint;
import net.opengis.swe.v20.SimpleComponent;


/**
 * POJO class for XML type ConstraintSettingPropertyType(@http://www.opengis.net/sensorml/2.0).
 *
 */
public class ConstraintSettingImpl implements ConstraintSetting
{
    static final long serialVersionUID = 1L;
    protected String ref = "";
    protected DataConstraint value;
    protected transient SimpleComponent refComponent;
    
    
    public ConstraintSettingImpl()
    {
    }
    
    
    public ConstraintSettingImpl(String ref, DataConstraint value)
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
     * Gets the constraint value
     */
    @Override
    public DataConstraint getValue()
    {
        return value;
    }
    
    
    /**
     * Adds a new setConstraintAsAllowedTimes property
     */
    @Override
    public void setValueAsAllowedTimes(AllowedTimes constraint)
    {
        this.value = constraint;
    }
    
    
    /**
     * Adds a new setConstraintAsAllowedTokens property
     */
    @Override
    public void setValueAsAllowedTokens(AllowedTokens constraint)
    {
        this.value = constraint;
    }
    
    
    /**
     * Adds a new setConstraintAsAllowedValues property
     */
    @Override
    public void setValueAsAllowedValues(AllowedValues constraint)
    {
        this.value = constraint;
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

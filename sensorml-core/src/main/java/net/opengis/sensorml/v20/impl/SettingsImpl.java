package net.opengis.sensorml.v20.impl;

import java.util.ArrayList;
import java.util.List;
import net.opengis.sensorml.v20.ArraySetting;
import net.opengis.sensorml.v20.ConstraintSetting;
import net.opengis.sensorml.v20.ModeSetting;
import net.opengis.sensorml.v20.Settings;
import net.opengis.sensorml.v20.StatusSetting;
import net.opengis.sensorml.v20.ValueSetting;


/**
 * POJO class for XML type SettingsType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public class SettingsImpl extends AbstractSettingsImpl implements Settings
{
    static final long serialVersionUID = 1L;
    protected List<ValueSetting> setValueList = new ArrayList<ValueSetting>();
    protected List<ArraySetting> setArrayValuesList = new ArrayList<ArraySetting>();
    protected List<ConstraintSetting> setConstraintList = new ArrayList<ConstraintSetting>();
    protected List<ModeSetting> setModeList = new ArrayList<ModeSetting>();
    protected List<StatusSetting> setStatusList = new ArrayList<StatusSetting>();
    
    
    public SettingsImpl()
    {
    }
    
    
    /**
     * Gets the list of setValue properties
     */
    @Override
    public List<ValueSetting> getSetValueList()
    {
        return setValueList;
    }
    
    
    /**
     * Returns number of setValue properties
     */
    @Override
    public int getNumSetValues()
    {
        if (setValueList == null)
            return 0;
        return setValueList.size();
    }
    
    
    /**
     * Adds a new setValue property
     */
    @Override
    public void addSetValue(ValueSetting setValue)
    {
        this.setValueList.add(setValue);
    }
    
    
    /**
     * Gets the list of setArrayValues properties
     */
    @Override
    public List<ArraySetting> getSetArrayValuesList()
    {
        return setArrayValuesList;
    }
    
    
    /**
     * Returns number of setArrayValues properties
     */
    @Override
    public int getNumSetArrayValues()
    {
        if (setArrayValuesList == null)
            return 0;
        return setArrayValuesList.size();
    }
    
    
    /**
     * Adds a new setArrayValues property
     */
    @Override
    public void addSetArrayValues(ArraySetting setArrayValues)
    {
        this.setArrayValuesList.add(setArrayValues);
    }
    
    
    /**
     * Gets the list of setConstraint properties
     */
    @Override
    public List<ConstraintSetting> getSetConstraintList()
    {
        return setConstraintList;
    }
    
    
    /**
     * Returns number of setConstraint properties
     */
    @Override
    public int getNumSetConstraints()
    {
        if (setConstraintList == null)
            return 0;
        return setConstraintList.size();
    }
    
    
    /**
     * Adds a new setConstraint property
     */
    @Override
    public void addSetConstraint(ConstraintSetting setConstraint)
    {
        this.setConstraintList.add(setConstraint);
    }
    
    
    /**
     * Gets the list of setMode properties
     */
    @Override
    public List<ModeSetting> getSetModeList()
    {
        return setModeList;
    }
    
    
    /**
     * Returns number of setMode properties
     */
    @Override
    public int getNumSetModes()
    {
        if (setModeList == null)
            return 0;
        return setModeList.size();
    }
    
    
    /**
     * Adds a new setMode property
     */
    @Override
    public void addSetMode(ModeSetting setMode)
    {
        this.setModeList.add(setMode);
    }
    
    
    /**
     * Gets the list of setStatus properties
     */
    @Override
    public List<StatusSetting> getSetStatusList()
    {
        return setStatusList;
    }
    
    
    /**
     * Returns number of setStatus properties
     */
    @Override
    public int getNumSetStatus()
    {
        if (setStatusList == null)
            return 0;
        return setStatusList.size();
    }
    
    
    /**
     * Adds a new setStatus property
     */
    @Override
    public void addSetStatus(StatusSetting setStatus)
    {
        this.setStatusList.add(setStatus);
    }
}

/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2015 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

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
    private static final long serialVersionUID = 6527575157568622311L;
    protected ArrayList<ValueSetting> setValueList = new ArrayList<ValueSetting>();
    protected ArrayList<ArraySetting> setArrayValuesList = new ArrayList<ArraySetting>();
    protected ArrayList<ConstraintSetting> setConstraintList = new ArrayList<ConstraintSetting>();
    protected ArrayList<ModeSetting> setModeList = new ArrayList<ModeSetting>();
    protected ArrayList<StatusSetting> setStatusList = new ArrayList<StatusSetting>();
    
    
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

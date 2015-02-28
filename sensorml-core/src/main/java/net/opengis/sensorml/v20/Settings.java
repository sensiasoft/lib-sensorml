/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2015 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package net.opengis.sensorml.v20;

import java.util.List;


/**
 * POJO class for XML type SettingsType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
@SuppressWarnings("javadoc")
public interface Settings extends AbstractSettings
{
    
    
    /**
     * Gets the list of setValue properties
     */
    public List<ValueSetting> getSetValueList();
    
    
    /**
     * Returns number of setValue properties
     */
    public int getNumSetValues();
    
    
    /**
     * Adds a new setValue property
     */
    public void addSetValue(ValueSetting setValue);
    
    
    /**
     * Gets the list of setArrayValues properties
     */
    public List<ArraySetting> getSetArrayValuesList();
    
    
    /**
     * Returns number of setArrayValues properties
     */
    public int getNumSetArrayValues();
    
    
    /**
     * Adds a new setArrayValues property
     */
    public void addSetArrayValues(ArraySetting setArrayValues);
    
    
    /**
     * Gets the list of setConstraint properties
     */
    public List<ConstraintSetting> getSetConstraintList();
    
    
    /**
     * Returns number of setConstraint properties
     */
    public int getNumSetConstraints();
    
    
    /**
     * Adds a new setConstraint property
     */
    public void addSetConstraint(ConstraintSetting setConstraint);    
    
    
    /**
     * Gets the list of setMode properties
     */
    public List<ModeSetting> getSetModeList();
    
    
    /**
     * Returns number of setMode properties
     */
    public int getNumSetModes();
    
    
    /**
     * Adds a new setMode property
     */
    public void addSetMode(ModeSetting setMode);
    
    
    /**
     * Gets the list of setStatus properties
     */
    public List<StatusSetting> getSetStatusList();
    
    
    /**
     * Returns number of setStatus properties
     */
    public int getNumSetStatus();
    
    
    /**
     * Adds a new setStatus property
     */
    public void addSetStatus(StatusSetting setStatus);
}

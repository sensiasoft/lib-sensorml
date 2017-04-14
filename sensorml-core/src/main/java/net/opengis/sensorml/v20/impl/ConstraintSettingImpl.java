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

import net.opengis.sensorml.v20.ConstraintSetting;
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
    private static final long serialVersionUID = -2737873597162699720L;
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

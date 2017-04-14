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

import net.opengis.sensorml.v20.ModeChoice;
import net.opengis.sensorml.v20.ModeSetting;


/**
 * POJO class for XML type ModeSettingPropertyType(@http://www.opengis.net/sensorml/2.0).
 *
 */
public class ModeSettingImpl implements ModeSetting
{
    private static final long serialVersionUID = 5542806985588353890L;
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

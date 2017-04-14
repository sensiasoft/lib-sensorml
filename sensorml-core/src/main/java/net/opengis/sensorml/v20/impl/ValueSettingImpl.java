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

import net.opengis.sensorml.v20.ValueSetting;
import net.opengis.swe.v20.SimpleComponent;


/**
 * POJO class for XML type ValueSettingPropertyType(@http://www.opengis.net/sensorml/2.0).
 *
 */
public class ValueSettingImpl implements ValueSetting
{
    private static final long serialVersionUID = 2511592737466764476L;
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

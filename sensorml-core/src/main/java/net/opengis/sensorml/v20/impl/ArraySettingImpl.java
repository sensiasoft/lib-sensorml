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

import org.vast.data.TextEncodingImpl;
import net.opengis.sensorml.v20.ArraySetting;
import net.opengis.swe.v20.DataArray;
import net.opengis.swe.v20.DataEncoding;
import net.opengis.swe.v20.EncodedValues;


/**
 * POJO class for XML type ArraySettingPropertyType(@http://www.opengis.net/sensorml/2.0)
 */
public class ArraySettingImpl implements ArraySetting
{
    private static final long serialVersionUID = 2565506524298543395L;
    protected String ref = "";
    protected DataEncoding encoding;
    protected EncodedValues value;
    protected transient DataArray refArray;
    
    
    public ArraySettingImpl()
    {
    }
    
    
    public ArraySettingImpl(String ref, DataEncoding encoding, EncodedValues values)
    {
        this.ref = ref;
        this.encoding = encoding;
        this.value = values;
        this.encoding = new TextEncodingImpl();
    }
    
    
    /**
     * Constructor with default text encoding
     * @param ref 
     * @param values 
     */
    public ArraySettingImpl(String ref, EncodedValues values)
    {
        this(ref, new TextEncodingImpl(), values);
    }
    
    
    @Override
    public String getRef()
    {
        return ref;
    }


    @Override
    public void setRef(String ref)
    {
        this.ref = ref;
    }


    @Override
    public DataEncoding getEncoding()
    {
        return encoding;
    }


    @Override
    public void setEncoding(DataEncoding encoding)
    {
        this.encoding = encoding;
    }


    @Override
    public EncodedValues getValue()
    {
        return value;
    }


    @Override
    public void setValue(EncodedValues value)
    {
        this.value = value;
    }


    @Override
    public DataArray getReferencedObject()
    {
        return refArray;
    }


    @Override
    public void setReferencedObject(DataArray refObj)
    {
        this.refArray = refObj;        
    }

}

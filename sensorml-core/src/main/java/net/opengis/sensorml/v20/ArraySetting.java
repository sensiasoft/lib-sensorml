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

import net.opengis.swe.v20.DataArray;
import net.opengis.swe.v20.DataEncoding;
import net.opengis.swe.v20.EncodedValues;


/**
 * POJO class for XML type ArraySettingPropertyType(@http://www.opengis.net/sensorml/2.0).
 *
 */
@SuppressWarnings("javadoc")
public interface ArraySetting extends ConfigSetting<DataArray>
{
    
    /**
     * Gets the encoding property
     */
    public DataEncoding getEncoding();
    
    
    /**
     * Sets the encoding property
     */
    public void setEncoding(DataEncoding encoding);
    
    
    /**
     * Gets the inline value
     */
    public EncodedValues getValue();
    
    
    /**
     * Sets the inline value
     */
    public void setValue(EncodedValues value);
}

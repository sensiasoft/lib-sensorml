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

import net.opengis.swe.v20.AllowedTimes;
import net.opengis.swe.v20.AllowedTokens;
import net.opengis.swe.v20.AllowedValues;
import net.opengis.swe.v20.DataConstraint;
import net.opengis.swe.v20.SimpleComponent;


/**
 * POJO class for XML type ValueSettingPropertyType(@http://www.opengis.net/sensorml/2.0).
 *
 */
@SuppressWarnings("javadoc")
public interface ConstraintSetting extends ConfigSetting<SimpleComponent>
{
    
    /**
     * Gets the constraint property
     */
    public DataConstraint getValue();
    
    
    /**
     * Sets value as AllowedTimes constraint
     */
    public void setValueAsAllowedTimes(AllowedTimes constraint);
    
    
    /**
     * Sets value as AllowedToken constraint
     */
    public void setValueAsAllowedTokens(AllowedTokens constraint);
    
    
    /**
     * Sets value as AllowedValues constraint
     */
    public void setValueAsAllowedValues(AllowedValues constraint);
}

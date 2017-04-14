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

import net.opengis.OgcPropertyList;
import net.opengis.swe.v20.AbstractSWEIdentifiable;


/**
 * <p>
 * Specialized property list with an ID as used in SensorML.
 * </p>
 *
 * @author Alex Robin
 * @param <ValueType> 
 * @since Nov 9, 2014
 */
public class SMLPropertyList<ValueType extends AbstractSWEIdentifiable> extends OgcPropertyList<ValueType>
{
    private static final long serialVersionUID = 8779504965074930700L;
    protected String id;
    
    
    public SMLPropertyList()
    {
    }


    public String getId()
    {
        return id;
    }


    public void setId(String id)
    {
        this.id = id;
    }
}

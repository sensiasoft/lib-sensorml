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

import net.opengis.OgcPropertyList;
import net.opengis.sensorml.v20.CharacteristicList;
import net.opengis.swe.v20.DataComponent;


/**
 * POJO class for XML type CharacteristicListType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public class CharacteristicListImpl extends AbstractMetadataListImpl implements CharacteristicList
{
    private static final long serialVersionUID = 900221429219021927L;
    protected OgcPropertyList<DataComponent> characteristicList = new OgcPropertyList<DataComponent>();
    
    
    public CharacteristicListImpl()
    {
    }
    
    
    /**
     * Gets the list of characteristic properties
     */
    @Override
    public OgcPropertyList<DataComponent> getCharacteristicList()
    {
        return characteristicList;
    }
    
    
    /**
     * Returns number of characteristic properties
     */
    @Override
    public int getNumCharacteristics()
    {
        if (characteristicList == null)
            return 0;
        return characteristicList.size();
    }
    
    
    /**
     * Gets the characteristic property with the given name
     */
    @Override
    public DataComponent getCharacteristic(String name)
    {
        if (characteristicList == null)
            return null;
        return characteristicList.get(name);
    }
    
    
    /**
     * Adds a new characteristic property
     */
    @Override
    public void addCharacteristic(String name, DataComponent characteristic)
    {
        this.characteristicList.add(name, characteristic);
    }
}

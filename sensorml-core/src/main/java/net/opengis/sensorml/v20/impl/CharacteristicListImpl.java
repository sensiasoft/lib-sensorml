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
    static final long serialVersionUID = 1L;
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

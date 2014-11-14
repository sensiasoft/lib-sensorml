package net.opengis.sensorml.v20;

import net.opengis.OgcPropertyList;
import net.opengis.swe.v20.DataComponent;


/**
 * POJO class for XML type CharacteristicListType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public interface CharacteristicList extends AbstractMetadataList
{
    
    
    /**
     * Gets the list of characteristic properties
     */
    public OgcPropertyList<DataComponent> getCharacteristicList();
    
    
    /**
     * Returns number of characteristic properties
     */
    public int getNumCharacteristics();
    
    
    /**
     * Gets the characteristic property with the given name
     */
    public DataComponent getCharacteristic(String name);
    
    
    /**
     * Adds a new characteristic property
     */
    public void addCharacteristic(String name, DataComponent characteristic);
}

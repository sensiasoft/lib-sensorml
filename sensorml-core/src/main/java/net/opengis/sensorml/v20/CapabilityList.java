package net.opengis.sensorml.v20;

import net.opengis.OgcPropertyList;
import net.opengis.swe.v20.DataComponent;


/**
 * POJO class for XML type CapabilityListType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public interface CapabilityList extends AbstractMetadataList
{
    
    
    /**
     * Gets the list of capability properties
     */
    public OgcPropertyList<DataComponent> getCapabilityList();
    
    
    /**
     * Returns number of capability properties
     */
    public int getNumCapabilitys();
    
    
    /**
     * Gets the capability property with the given name
     */
    public DataComponent getCapability(String name);
    
    
    /**
     * Adds a new capability property
     */
    public void addCapability(String name, DataComponent capability);
}

package net.opengis.sensorml.v20;

import net.opengis.OgcPropertyList;
import net.opengis.swe.v20.AbstractSWE;


/**
 * POJO class for XML type ComponentListType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public interface ComponentList extends AbstractSWE
{
    
    
    /**
     * Gets the list of component properties
     */
    public OgcPropertyList<AbstractProcess> getComponentList();
    
    
    /**
     * Returns number of component properties
     */
    public int getNumComponents();
    
    
    /**
     * Gets the component property with the given name
     */
    public AbstractProcess getComponent(String name);
    
    
    /**
     * Adds a new component property
     */
    public void addComponent(String name, AbstractProcess component);
}

package net.opengis.sensorml.v20.impl;

import org.vast.data.AbstractSWEImpl;
import net.opengis.OgcPropertyList;
import net.opengis.sensorml.v20.AbstractProcess;
import net.opengis.sensorml.v20.ComponentList;


/**
 * POJO class for XML type ComponentListType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public class ComponentListImpl extends AbstractSWEImpl implements ComponentList
{
    static final long serialVersionUID = 1L;
    protected OgcPropertyList<AbstractProcess> componentList = new OgcPropertyList<AbstractProcess>();
    
    
    public ComponentListImpl()
    {
    }
    
    
    /**
     * Gets the list of component properties
     */
    @Override
    public OgcPropertyList<AbstractProcess> getComponentList()
    {
        return componentList;
    }
    
    
    /**
     * Returns number of component properties
     */
    @Override
    public int getNumComponents()
    {
        if (componentList == null)
            return 0;
        return componentList.size();
    }
    
    
    /**
     * Gets the component property with the given name
     */
    @Override
    public AbstractProcess getComponent(String name)
    {
        if (componentList == null)
            return null;
        return componentList.get(name);
    }
    
    
    /**
     * Adds a new component property
     */
    @Override
    public void addComponent(String name, AbstractProcess component)
    {
        this.componentList.add(name, component);
    }
}

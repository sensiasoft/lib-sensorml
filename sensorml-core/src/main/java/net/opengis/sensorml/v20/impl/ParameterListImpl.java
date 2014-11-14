package net.opengis.sensorml.v20.impl;

import org.vast.data.AbstractSWEImpl;
import net.opengis.OgcPropertyList;
import net.opengis.sensorml.v20.DataInterface;
import net.opengis.sensorml.v20.ObservableProperty;
import net.opengis.sensorml.v20.ParameterList;
import net.opengis.swe.v20.DataComponent;
import net.opengis.swe.v20.AbstractSWEIdentifiable;


/**
 * POJO class for XML type ParameterListType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public class ParameterListImpl extends AbstractSWEImpl implements ParameterList
{
    static final long serialVersionUID = 1L;
    protected OgcPropertyList<AbstractSWEIdentifiable> parameterList = new OgcPropertyList<AbstractSWEIdentifiable>();
    
    
    public ParameterListImpl()
    {
    }
    
    
    /**
     * Gets the list of parameter properties
     */
    @Override
    public OgcPropertyList<AbstractSWEIdentifiable> getParameterList()
    {
        return parameterList;
    }
    
    
    /**
     * Returns number of parameter properties
     */
    @Override
    public int getNumParameters()
    {
        if (parameterList == null)
            return 0;
        return parameterList.size();
    }
    
    
    /**
     * Gets the parameter property with the given name
     */
    @Override
    public AbstractSWEIdentifiable getParameter(String name)
    {
        if (parameterList == null)
            return null;
        return parameterList.get(name);
    }
    
    
    /**
     * Adds a new parameterAsAbstractDataComponent property
     */
    @Override
    public void addParameterAsAbstractDataComponent(String name, DataComponent parameter)
    {
        this.parameterList.add(name, parameter);
    }
    
    
    /**
     * Adds a new parameterAsObservableProperty property
     */
    @Override
    public void addParameterAsObservableProperty(String name, ObservableProperty parameter)
    {
        this.parameterList.add(name, parameter);
    }
    
    
    /**
     * Adds a new parameterAsDataInterface property
     */
    @Override
    public void addParameterAsDataInterface(String name, DataInterface parameter)
    {
        this.parameterList.add(name, parameter);
    }
}

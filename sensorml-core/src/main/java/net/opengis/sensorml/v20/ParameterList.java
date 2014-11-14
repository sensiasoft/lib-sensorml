package net.opengis.sensorml.v20;

import net.opengis.OgcPropertyList;
import net.opengis.swe.v20.DataComponent;
import net.opengis.swe.v20.AbstractSWE;
import net.opengis.swe.v20.AbstractSWEIdentifiable;


/**
 * POJO class for XML type ParameterListType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public interface ParameterList extends AbstractSWE
{
    
    
    /**
     * Gets the list of parameter properties
     */
    public OgcPropertyList<AbstractSWEIdentifiable> getParameterList();
    
    
    /**
     * Returns number of parameter properties
     */
    public int getNumParameters();
    
    
    /**
     * Gets the parameter property with the given name
     */
    public AbstractSWEIdentifiable getParameter(String name);
    
    
    /**
     * Adds a new parameterAsAbstractDataComponent property
     */
    public void addParameterAsAbstractDataComponent(String name, DataComponent parameter);
    
    
    /**
     * Adds a new parameterAsObservableProperty property
     */
    public void addParameterAsObservableProperty(String name, ObservableProperty parameter);
    
    
    /**
     * Adds a new parameterAsDataInterface property
     */
    public void addParameterAsDataInterface(String name, DataInterface parameter);
}

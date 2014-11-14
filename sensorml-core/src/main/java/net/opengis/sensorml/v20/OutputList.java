package net.opengis.sensorml.v20;

import net.opengis.OgcPropertyList;
import net.opengis.swe.v20.DataComponent;
import net.opengis.swe.v20.AbstractSWE;
import net.opengis.swe.v20.AbstractSWEIdentifiable;


/**
 * POJO class for XML type OutputListType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public interface OutputList extends AbstractSWE
{
    
    
    /**
     * Gets the list of output properties
     */
    public OgcPropertyList<AbstractSWEIdentifiable> getOutputList();
    
    
    /**
     * Returns number of output properties
     */
    public int getNumOutputs();
    
    
    /**
     * Gets the output property with the given name
     */
    public AbstractSWEIdentifiable getOutput(String name);
    
    
    /**
     * Adds a new outputAsAbstractDataComponent property
     */
    public void addOutputAsAbstractDataComponent(String name, DataComponent output);
    
    
    /**
     * Adds a new outputAsObservableProperty property
     */
    public void addOutputAsObservableProperty(String name, ObservableProperty output);
    
    
    /**
     * Adds a new outputAsDataInterface property
     */
    public void addOutputAsDataInterface(String name, DataInterface output);
}

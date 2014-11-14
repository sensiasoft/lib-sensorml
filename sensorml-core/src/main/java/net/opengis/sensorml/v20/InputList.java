package net.opengis.sensorml.v20;

import net.opengis.OgcPropertyList;
import net.opengis.swe.v20.DataComponent;
import net.opengis.swe.v20.AbstractSWE;
import net.opengis.swe.v20.AbstractSWEIdentifiable;


/**
 * POJO class for XML type InputListType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public interface InputList extends AbstractSWE
{
    
    
    /**
     * Gets the list of input properties
     */
    public OgcPropertyList<AbstractSWEIdentifiable> getInputList();
    
    
    /**
     * Returns number of input properties
     */
    public int getNumInputs();
    
    
    /**
     * Gets the input property with the given name
     */
    public AbstractSWEIdentifiable getInput(String name);
    
    
    /**
     * Adds a new inputAsAbstractDataComponent property
     */
    public void addInputAsAbstractDataComponent(String name, DataComponent input);
    
    
    /**
     * Adds a new inputAsObservableProperty property
     */
    public void addInputAsObservableProperty(String name, ObservableProperty input);
    
    
    /**
     * Adds a new inputAsDataInterface property
     */
    public void addInputAsDataInterface(String name, DataInterface input);
}

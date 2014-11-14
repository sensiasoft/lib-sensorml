package net.opengis.sensorml.v20.impl;

import org.vast.data.AbstractSWEImpl;
import net.opengis.OgcPropertyList;
import net.opengis.sensorml.v20.DataInterface;
import net.opengis.sensorml.v20.InputList;
import net.opengis.sensorml.v20.ObservableProperty;
import net.opengis.swe.v20.DataComponent;
import net.opengis.swe.v20.AbstractSWEIdentifiable;


/**
 * POJO class for XML type InputListType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public class InputListImpl extends AbstractSWEImpl implements InputList
{
    static final long serialVersionUID = 1L;
    protected OgcPropertyList<AbstractSWEIdentifiable> inputList = new OgcPropertyList<AbstractSWEIdentifiable>();
    
    
    public InputListImpl()
    {
    }
    
    
    /**
     * Gets the list of input properties
     */
    @Override
    public OgcPropertyList<AbstractSWEIdentifiable> getInputList()
    {
        return inputList;
    }
    
    
    /**
     * Returns number of input properties
     */
    @Override
    public int getNumInputs()
    {
        if (inputList == null)
            return 0;
        return inputList.size();
    }
    
    
    /**
     * Gets the input property with the given name
     */
    @Override
    public AbstractSWEIdentifiable getInput(String name)
    {
        if (inputList == null)
            return null;
        return inputList.get(name);
    }
    
    
    /**
     * Adds a new inputAsAbstractDataComponent property
     */
    @Override
    public void addInputAsAbstractDataComponent(String name, DataComponent input)
    {
        this.inputList.add(name, input);
    }
    
    
    /**
     * Adds a new inputAsObservableProperty property
     */
    @Override
    public void addInputAsObservableProperty(String name, ObservableProperty input)
    {
        this.inputList.add(name, input);
    }
    
    
    /**
     * Adds a new inputAsDataInterface property
     */
    @Override
    public void addInputAsDataInterface(String name, DataInterface input)
    {
        this.inputList.add(name, input);
    }
}

package net.opengis.sensorml.v20.impl;

import org.vast.data.AbstractSWEImpl;
import net.opengis.OgcPropertyList;
import net.opengis.sensorml.v20.DataInterface;
import net.opengis.sensorml.v20.ObservableProperty;
import net.opengis.sensorml.v20.OutputList;
import net.opengis.swe.v20.DataComponent;
import net.opengis.swe.v20.AbstractSWEIdentifiable;


/**
 * POJO class for XML type OutputListType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public class OutputListImpl extends AbstractSWEImpl implements OutputList
{
    static final long serialVersionUID = 1L;
    protected OgcPropertyList<AbstractSWEIdentifiable> outputList = new OgcPropertyList<AbstractSWEIdentifiable>();
    
    
    public OutputListImpl()
    {
    }
    
    
    /**
     * Gets the list of output properties
     */
    @Override
    public OgcPropertyList<AbstractSWEIdentifiable> getOutputList()
    {
        return outputList;
    }
    
    
    /**
     * Returns number of output properties
     */
    @Override
    public int getNumOutputs()
    {
        if (outputList == null)
            return 0;
        return outputList.size();
    }
    
    
    /**
     * Gets the output property with the given name
     */
    @Override
    public AbstractSWEIdentifiable getOutput(String name)
    {
        if (outputList == null)
            return null;
        return outputList.get(name);
    }
    
    
    /**
     * Adds a new outputAsAbstractDataComponent property
     */
    @Override
    public void addOutputAsAbstractDataComponent(String name, DataComponent output)
    {
        this.outputList.add(name, output);
    }
    
    
    /**
     * Adds a new outputAsObservableProperty property
     */
    @Override
    public void addOutputAsObservableProperty(String name, ObservableProperty output)
    {
        this.outputList.add(name, output);
    }
    
    
    /**
     * Adds a new outputAsDataInterface property
     */
    @Override
    public void addOutputAsDataInterface(String name, DataInterface output)
    {
        this.outputList.add(name, output);
    }
}

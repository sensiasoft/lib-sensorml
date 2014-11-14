package net.opengis.sensorml.v20.impl;

import org.vast.data.AbstractSWEIdentifiableImpl;
import net.opengis.OgcProperty;
import net.opengis.OgcPropertyImpl;
import net.opengis.sensorml.v20.DataInterface;
import net.opengis.swe.v20.DataRecord;
import net.opengis.swe.v20.DataStream;


/**
 * POJO class for XML type DataInterfaceType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public class DataInterfaceImpl extends AbstractSWEIdentifiableImpl implements DataInterface
{
    static final long serialVersionUID = 1L;
    protected OgcProperty<DataStream> data = new OgcPropertyImpl<DataStream>();
    protected OgcProperty<DataRecord> interfaceParameters;
    
    
    public DataInterfaceImpl()
    {
    }
    
    
    /**
     * Gets the data property
     */
    @Override
    public DataStream getData()
    {
        return data.getValue();
    }
    
    
    /**
     * Gets extra info (name, xlink, etc.) carried by the data property
     */
    @Override
    public OgcProperty<DataStream> getDataProperty()
    {
        if (data == null)
            data = new OgcPropertyImpl<DataStream>();
        return data;
    }
    
    
    /**
     * Sets the data property
     */
    @Override
    public void setData(DataStream data)
    {
        this.data.setValue(data);
    }
    
    
    /**
     * Gets the interfaceParameters property
     */
    @Override
    public DataRecord getInterfaceParameters()
    {
        return interfaceParameters.getValue();
    }
    
    
    /**
     * Gets extra info (name, xlink, etc.) carried by the interfaceParameters property
     */
    @Override
    public OgcProperty<DataRecord> getInterfaceParametersProperty()
    {
        if (interfaceParameters == null)
            interfaceParameters = new OgcPropertyImpl<DataRecord>();
        return interfaceParameters;
    }
    
    
    /**
     * Checks if interfaceParameters is set
     */
    @Override
    public boolean isSetInterfaceParameters()
    {
        return (interfaceParameters != null && (interfaceParameters.hasValue() || interfaceParameters.hasHref()));
    }
    
    
    /**
     * Sets the interfaceParameters property
     */
    @Override
    public void setInterfaceParameters(DataRecord interfaceParameters)
    {
        if (this.interfaceParameters == null)
            this.interfaceParameters = new OgcPropertyImpl<DataRecord>();
        this.interfaceParameters.setValue(interfaceParameters);
    }
}

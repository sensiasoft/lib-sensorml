package net.opengis.sensorml.v20;

import net.opengis.OgcProperty;
import net.opengis.swe.v20.AbstractSWEIdentifiable;
import net.opengis.swe.v20.DataRecord;
import net.opengis.swe.v20.DataStream;


/**
 * POJO class for XML type DataInterfaceType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public interface DataInterface extends AbstractSWEIdentifiable
{
    
    
    /**
     * Gets the data property
     */
    public DataStream getData();
    
    
    /**
     * Gets extra info (name, xlink, etc.) carried by the data property
     */
    public OgcProperty<DataStream> getDataProperty();
    
    
    /**
     * Sets the data property
     */
    public void setData(DataStream data);
    
    
    /**
     * Gets the interfaceParameters property
     */
    public DataRecord getInterfaceParameters();
    
    
    /**
     * Gets extra info (name, xlink, etc.) carried by the interfaceParameters property
     */
    public OgcProperty<DataRecord> getInterfaceParametersProperty();
    
    
    /**
     * Checks if interfaceParameters is set
     */
    public boolean isSetInterfaceParameters();
    
    
    /**
     * Sets the interfaceParameters property
     */
    public void setInterfaceParameters(DataRecord interfaceParameters);
}

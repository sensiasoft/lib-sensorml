package net.opengis.sensorml.v20;

import net.opengis.OgcProperty;


/**
 * POJO class for XML type PhysicalComponentType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public interface PhysicalComponent extends AbstractPhysicalProcess
{
    
    
    /**
     * Gets the method property
     */
    public ProcessMethod getMethod();
    
    
    /**
     * Gets extra info (name, xlink, etc.) carried by the method property
     */
    public OgcProperty<ProcessMethod> getMethodProperty();
    
    
    /**
     * Checks if method is set
     */
    public boolean isSetMethod();
    
    
    /**
     * Sets the method property
     */
    public void setMethod(ProcessMethod method);
}

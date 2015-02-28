package org.vast.sensorML;

import net.opengis.OgcProperty;
import net.opengis.OgcPropertyImpl;
import net.opengis.sensorml.v20.ProcessMethod;
import net.opengis.sensorml.v20.SimpleProcess;


/**
 * POJO class for XML type SimpleProcessType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public class SimpleProcessImpl extends AbstractProcessImpl implements SimpleProcess
{
    protected OgcProperty<ProcessMethod> method;
    
    
    public SimpleProcessImpl()
    {
    }
    
    
    /**
     * Gets the method property
     */
    @Override
    public ProcessMethod getMethod()
    {
        return method.getValue();
    }
    
    
    /**
     * Gets extra info (name, xlink, etc.) carried by the method property
     */
    @Override
    public OgcProperty<ProcessMethod> getMethodProperty()
    {
        if (method == null)
            method = new OgcPropertyImpl<ProcessMethod>();
        return method;
    }
    
    
    /**
     * Checks if method is set
     */
    @Override
    public boolean isSetMethod()
    {
        return (method != null && (method.hasValue() || method.hasHref()));
    }
    
    
    /**
     * Sets the method property
     */
    @Override
    public void setMethod(ProcessMethod method)
    {
        if (this.method == null)
            this.method = new OgcPropertyImpl<ProcessMethod>();
        this.method.setValue(method);
    }
}

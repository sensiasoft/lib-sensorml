package net.opengis.sensorml.v20;

import net.opengis.gml.v32.Reference;
import net.opengis.swe.v20.AbstractSWE;


/**
 * POJO class for XML type TermType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public interface Term extends AbstractSWE
{
    
    
    /**
     * Gets the label property
     */
    public String getLabel();
    
    
    /**
     * Sets the label property
     */
    public void setLabel(String label);
    
    
    /**
     * Gets the codeSpace property
     */
    public Reference getCodeSpace();
    
    
    /**
     * Checks if codeSpace is set
     */
    public boolean isSetCodeSpace();
    
    
    /**
     * Sets the codeSpace property
     */
    public void setCodeSpace(Reference codeSpace);
    
    
    /**
     * Gets the value property
     */
    public String getValue();
    
    
    /**
     * Sets the value property
     */
    public void setValue(String value);
    
    
    /**
     * Gets the definition property
     */
    public String getDefinition();
    
    
    /**
     * Checks if definition is set
     */
    public boolean isSetDefinition();
    
    
    /**
     * Sets the definition property
     */
    public void setDefinition(String definition);
}

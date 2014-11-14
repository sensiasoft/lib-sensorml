package net.opengis.sensorml.v20;

import java.util.List;
import net.opengis.swe.v20.AbstractSWEIdentifiable;


/**
 * POJO class for XML type ProcessMethodType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public interface ProcessMethod extends AbstractSWEIdentifiable
{
    
    
    /**
     * Gets the list of algorithm properties
     */
    public List<AbstractAlgorithm> getAlgorithmList();
    
    
    /**
     * Returns number of algorithm properties
     */
    public int getNumAlgorithms();
    
    
    /**
     * Adds a new algorithm property
     */
    public void addAlgorithm(AbstractAlgorithm algorithm);
}

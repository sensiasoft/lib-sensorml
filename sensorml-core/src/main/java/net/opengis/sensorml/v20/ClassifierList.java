package net.opengis.sensorml.v20;

import java.util.List;


/**
 * POJO class for XML type ClassifierListType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public interface ClassifierList extends AbstractMetadataList
{
    
    
    /**
     * Gets the list of classifier properties
     */
    public List<Term> getClassifierList();
    
    
    /**
     * Returns number of classifier properties
     */
    public int getNumClassifiers();
    
    
    /**
     * Adds a new classifier property
     */
    public void addClassifier(Term classifier);
}

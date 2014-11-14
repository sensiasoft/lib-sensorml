package net.opengis.sensorml.v20.impl;

import java.util.ArrayList;
import java.util.List;
import net.opengis.sensorml.v20.ClassifierList;
import net.opengis.sensorml.v20.Term;


/**
 * POJO class for XML type ClassifierListType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public class ClassifierListImpl extends AbstractMetadataListImpl implements ClassifierList
{
    static final long serialVersionUID = 1L;
    protected List<Term> classifierList = new ArrayList<Term>();
    
    
    public ClassifierListImpl()
    {
    }
    
    
    /**
     * Gets the list of classifier properties
     */
    @Override
    public List<Term> getClassifierList()
    {
        return classifierList;
    }
    
    
    /**
     * Returns number of classifier properties
     */
    @Override
    public int getNumClassifiers()
    {
        if (classifierList == null)
            return 0;
        return classifierList.size();
    }
    
    
    /**
     * Adds a new classifier property
     */
    @Override
    public void addClassifier(Term classifier)
    {
        this.classifierList.add(classifier);
    }
}

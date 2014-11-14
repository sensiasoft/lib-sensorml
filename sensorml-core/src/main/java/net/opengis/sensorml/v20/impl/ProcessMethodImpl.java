package net.opengis.sensorml.v20.impl;

import java.util.ArrayList;
import java.util.List;
import org.vast.data.AbstractSWEIdentifiableImpl;
import net.opengis.sensorml.v20.AbstractAlgorithm;
import net.opengis.sensorml.v20.ProcessMethod;


/**
 * POJO class for XML type ProcessMethodType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public class ProcessMethodImpl extends AbstractSWEIdentifiableImpl implements ProcessMethod
{
    static final long serialVersionUID = 1L;
    protected List<AbstractAlgorithm> algorithmList = new ArrayList<AbstractAlgorithm>();
    
    
    public ProcessMethodImpl()
    {
    }
    
    
    /**
     * Gets the list of algorithm properties
     */
    @Override
    public List<AbstractAlgorithm> getAlgorithmList()
    {
        return algorithmList;
    }
    
    
    /**
     * Returns number of algorithm properties
     */
    @Override
    public int getNumAlgorithms()
    {
        if (algorithmList == null)
            return 0;
        return algorithmList.size();
    }
    
    
    /**
     * Adds a new algorithm property
     */
    @Override
    public void addAlgorithm(AbstractAlgorithm algorithm)
    {
        this.algorithmList.add(algorithm);
    }
}

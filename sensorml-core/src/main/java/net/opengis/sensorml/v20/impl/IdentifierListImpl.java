package net.opengis.sensorml.v20.impl;

import java.util.ArrayList;
import java.util.List;
import net.opengis.sensorml.v20.IdentifierList;
import net.opengis.sensorml.v20.Term;


/**
 * POJO class for XML type IdentifierListType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public class IdentifierListImpl extends AbstractMetadataListImpl implements IdentifierList
{
    static final long serialVersionUID = 1L;
    protected List<Term> identifier2List = new ArrayList<Term>();
    
    
    public IdentifierListImpl()
    {
    }
    
    
    /**
     * Gets the list of identifier2 properties
     */
    @Override
    public List<Term> getIdentifier2List()
    {
        return identifier2List;
    }
    
    
    /**
     * Returns number of identifier2 properties
     */
    @Override
    public int getNumIdentifier2s()
    {
        if (identifier2List == null)
            return 0;
        return identifier2List.size();
    }
    
    
    /**
     * Adds a new identifier2 property
     */
    @Override
    public void addIdentifier2(Term identifier2)
    {
        this.identifier2List.add(identifier2);
    }
}

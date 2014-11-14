package org.isotc211.v2005.gco.impl;

import net.opengis.OgcPropertyList;
import org.isotc211.v2005.gco.Multiplicity;
import org.isotc211.v2005.gco.MultiplicityRange;


/**
 * POJO class for XML type Multiplicity_Type(@http://www.isotc211.org/2005/gco).
 *
 * This is a complex type.
 */
public class MultiplicityImpl extends AbstractObjectImpl implements Multiplicity
{
    static final long serialVersionUID = 1L;
    protected OgcPropertyList<MultiplicityRange> rangeList = new OgcPropertyList<MultiplicityRange>();
    
    
    public MultiplicityImpl()
    {
    }
    
    
    /**
     * Gets the list of range properties
     */
    @Override
    public OgcPropertyList<MultiplicityRange> getRangeList()
    {
        return rangeList;
    }
    
    
    /**
     * Returns number of range properties
     */
    @Override
    public int getNumRanges()
    {
        if (rangeList == null)
            return 0;
        return rangeList.size();
    }
    
    
    /**
     * Adds a new range property
     */
    @Override
    public void addRange(MultiplicityRange range)
    {
        this.rangeList.add(range);
    }
}

package org.isotc211.v2005.gmd.impl;

import java.util.ArrayList;
import java.util.List;
import org.isotc211.v2005.gco.impl.AbstractObjectImpl;
import org.isotc211.v2005.gmd.MDConstraints;


/**
 * POJO class for XML type MD_Constraints_Type(@http://www.isotc211.org/2005/gmd).
 *
 * This is a complex type.
 */
public class MDConstraintsImpl extends AbstractObjectImpl implements MDConstraints
{
    static final long serialVersionUID = 1L;
    protected List<String> useLimitationList = new ArrayList<String>();
    
    
    public MDConstraintsImpl()
    {
    }
    
    
    /**
     * Gets the list of useLimitation properties
     */
    @Override
    public List<String> getUseLimitationList()
    {
        return useLimitationList;
    }
    
    
    /**
     * Returns number of useLimitation properties
     */
    @Override
    public int getNumUseLimitations()
    {
        if (useLimitationList == null)
            return 0;
        return useLimitationList.size();
    }
    
    
    /**
     * Adds a new useLimitation property
     */
    @Override
    public void addUseLimitation(String useLimitation)
    {
        this.useLimitationList.add(useLimitation);
    }
}

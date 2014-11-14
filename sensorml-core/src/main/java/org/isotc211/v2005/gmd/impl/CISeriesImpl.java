package org.isotc211.v2005.gmd.impl;

import org.isotc211.v2005.gco.impl.AbstractObjectImpl;
import org.isotc211.v2005.gmd.CISeries;


/**
 * POJO class for XML type CI_Series_Type(@http://www.isotc211.org/2005/gmd).
 *
 * This is a complex type.
 */
public class CISeriesImpl extends AbstractObjectImpl implements CISeries
{
    static final long serialVersionUID = 1L;
    protected String name;
    protected String issueIdentification;
    protected String page;
    
    
    public CISeriesImpl()
    {
    }
    
    
    /**
     * Gets the name property
     */
    @Override
    public String getName()
    {
        return name;
    }
    
    
    /**
     * Checks if name is set
     */
    @Override
    public boolean isSetName()
    {
        return (name != null);
    }
    
    
    /**
     * Sets the name property
     */
    @Override
    public void setName(String name)
    {
        this.name = name;
    }
    
    
    /**
     * Gets the issueIdentification property
     */
    @Override
    public String getIssueIdentification()
    {
        return issueIdentification;
    }
    
    
    /**
     * Checks if issueIdentification is set
     */
    @Override
    public boolean isSetIssueIdentification()
    {
        return (issueIdentification != null);
    }
    
    
    /**
     * Sets the issueIdentification property
     */
    @Override
    public void setIssueIdentification(String issueIdentification)
    {
        this.issueIdentification = issueIdentification;
    }
    
    
    /**
     * Gets the page property
     */
    @Override
    public String getPage()
    {
        return page;
    }
    
    
    /**
     * Checks if page is set
     */
    @Override
    public boolean isSetPage()
    {
        return (page != null);
    }
    
    
    /**
     * Sets the page property
     */
    @Override
    public void setPage(String page)
    {
        this.page = page;
    }
}

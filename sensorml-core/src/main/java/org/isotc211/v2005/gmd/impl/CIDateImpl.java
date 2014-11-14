package org.isotc211.v2005.gmd.impl;

import net.opengis.IDateTime;
import org.isotc211.v2005.gco.CodeListValue;
import org.isotc211.v2005.gco.impl.AbstractObjectImpl;
import org.isotc211.v2005.gmd.CIDate;


/**
 * POJO class for XML type CI_Date_Type(@http://www.isotc211.org/2005/gmd).
 *
 * This is a complex type.
 */
public class CIDateImpl extends AbstractObjectImpl implements CIDate
{
    static final long serialVersionUID = 1L;
    protected Object date;
    protected CodeListValue dateType;
    
    
    public CIDateImpl()
    {
    }
    
    
    /**
     * Gets the date property
     */
    @Override
    public Object getDate()
    {
        return date;
    }
    
    
    /**
     * Sets the dateAsDate property
     */
    @Override
    public void setDateAsDate(Object date)
    {
        this.date = date;
    }
    
    
    /**
     * Sets the dateAsDateTime property
     */
    @Override
    public void setDateAsDateTime(IDateTime date)
    {
        this.date = date;
    }
    
    
    /**
     * Gets the dateType property
     */
    @Override
    public CodeListValue getDateType()
    {
        return dateType;
    }
    
    
    /**
     * Sets the dateType property
     */
    @Override
    public void setDateType(CodeListValue dateType)
    {
        this.dateType = dateType;
    }
}

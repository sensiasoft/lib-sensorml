package org.isotc211.v2005.gmd;

import net.opengis.IDateTime;
import org.isotc211.v2005.gco.AbstractObject;
import org.isotc211.v2005.gco.CodeListValue;


/**
 * POJO class for XML type CI_Date_Type(@http://www.isotc211.org/2005/gmd).
 *
 * This is a complex type.
 */
public interface CIDate extends AbstractObject
{
    
    
    /**
     * Gets the date property
     */
    public Object getDate();
    
    
    /**
     * Sets the dateAsDate property
     */
    public void setDateAsDate(Object date);
    
    
    /**
     * Sets the dateAsDateTime property
     */
    public void setDateAsDateTime(IDateTime date);
    
    
    /**
     * Gets the dateType property
     */
    public CodeListValue getDateType();
    
    
    /**
     * Sets the dateType property
     */
    public void setDateType(CodeListValue dateType);
}

package org.isotc211.v2005.gmd;

import net.opengis.OgcProperty;
import org.isotc211.v2005.gco.AbstractObject;


/**
 * POJO class for XML type MD_Identifier_Type(@http://www.isotc211.org/2005/gmd).
 *
 * This is a complex type.
 */
public interface MDIdentifier extends AbstractObject
{
    
    
    /**
     * Gets the authority property
     */
    public CICitation getAuthority();
    
    
    /**
     * Gets extra info (name, xlink, etc.) carried by the authority property
     */
    public OgcProperty<CICitation> getAuthorityProperty();
    
    
    /**
     * Checks if authority is set
     */
    public boolean isSetAuthority();
    
    
    /**
     * Sets the authority property
     */
    public void setAuthority(CICitation authority);
    
    
    /**
     * Gets the code property
     */
    public String getCode();
    
    
    /**
     * Sets the code property
     */
    public void setCode(String code);
}

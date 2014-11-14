package org.isotc211.v2005.gmd.impl;

import net.opengis.OgcProperty;
import net.opengis.OgcPropertyImpl;
import org.isotc211.v2005.gco.impl.AbstractObjectImpl;
import org.isotc211.v2005.gmd.CICitation;
import org.isotc211.v2005.gmd.MDIdentifier;


/**
 * POJO class for XML type MD_Identifier_Type(@http://www.isotc211.org/2005/gmd).
 *
 * This is a complex type.
 */
public class MDIdentifierImpl extends AbstractObjectImpl implements MDIdentifier
{
    static final long serialVersionUID = 1L;
    protected OgcProperty<CICitation> authority;
    protected String code = "";
    
    
    public MDIdentifierImpl()
    {
    }
    
    
    /**
     * Gets the authority property
     */
    @Override
    public CICitation getAuthority()
    {
        return authority.getValue();
    }
    
    
    /**
     * Gets extra info (name, xlink, etc.) carried by the authority property
     */
    @Override
    public OgcProperty<CICitation> getAuthorityProperty()
    {
        if (authority == null)
            authority = new OgcPropertyImpl<CICitation>();
        return authority;
    }
    
    
    /**
     * Checks if authority is set
     */
    @Override
    public boolean isSetAuthority()
    {
        return (authority != null && authority.getValue() != null);
    }
    
    
    /**
     * Sets the authority property
     */
    @Override
    public void setAuthority(CICitation authority)
    {
        if (this.authority == null)
            this.authority = new OgcPropertyImpl<CICitation>();
        this.authority.setValue(authority);
    }
    
    
    /**
     * Gets the code property
     */
    @Override
    public String getCode()
    {
        return code;
    }
    
    
    /**
     * Sets the code property
     */
    @Override
    public void setCode(String code)
    {
        this.code = code;
    }
}

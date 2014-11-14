package org.isotc211.v2005.gco;

import net.opengis.OgcProperty;


/**
 * POJO class for XML type MemberName_Type(@http://www.isotc211.org/2005/gco).
 *
 * This is a complex type.
 */
public interface MemberName extends AbstractObject
{
    
    
    /**
     * Gets the aName property
     */
    public String getAName();
    
    
    /**
     * Sets the aName property
     */
    public void setAName(String aName);
    
    
    /**
     * Gets the attributeType property
     */
    public TypeName getAttributeType();
    
    
    /**
     * Gets extra info (name, xlink, etc.) carried by the attributeType property
     */
    public OgcProperty<TypeName> getAttributeTypeProperty();
    
    
    /**
     * Sets the attributeType property
     */
    public void setAttributeType(TypeName attributeType);
}

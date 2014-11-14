package org.isotc211.v2005.gmd;

import org.isotc211.v2005.gco.AbstractObject;
import org.isotc211.v2005.gco.CodeListValue;


/**
 * POJO class for XML type CI_OnlineResource_Type(@http://www.isotc211.org/2005/gmd).
 *
 * This is a complex type.
 */
public interface CIOnlineResource extends AbstractObject
{
    
    
    /**
     * Gets the linkage property
     */
    public String getLinkage();
    
    
    /**
     * Sets the linkage property
     */
    public void setLinkage(String linkage);
    
    
    /**
     * Gets the protocol property
     */
    public String getProtocol();
    
    
    /**
     * Checks if protocol is set
     */
    public boolean isSetProtocol();
    
    
    /**
     * Sets the protocol property
     */
    public void setProtocol(String protocol);
    
    
    /**
     * Gets the applicationProfile property
     */
    public String getApplicationProfile();
    
    
    /**
     * Checks if applicationProfile is set
     */
    public boolean isSetApplicationProfile();
    
    
    /**
     * Sets the applicationProfile property
     */
    public void setApplicationProfile(String applicationProfile);
    
    
    /**
     * Gets the name property
     */
    public String getName();
    
    
    /**
     * Checks if name is set
     */
    public boolean isSetName();
    
    
    /**
     * Sets the name property
     */
    public void setName(String name);
    
    
    /**
     * Gets the description property
     */
    public String getDescription();
    
    
    /**
     * Checks if description is set
     */
    public boolean isSetDescription();
    
    
    /**
     * Sets the description property
     */
    public void setDescription(String description);
    
    
    /**
     * Gets the function property
     */
    public CodeListValue getFunction();
    
    
    /**
     * Checks if function is set
     */
    public boolean isSetFunction();
    
    
    /**
     * Sets the function property
     */
    public void setFunction(CodeListValue function);
}

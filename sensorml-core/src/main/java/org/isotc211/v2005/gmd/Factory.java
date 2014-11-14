package org.isotc211.v2005.gmd;


public interface Factory
{
    
    public CIResponsibleParty newCIResponsibleParty();
    
    
    public CICitation newCICitation();
    
    
    public CIAddress newCIAddress();
    
    
    public CIOnlineResource newCIOnlineResource();
    
    
    public CIContact newCIContact();
    
    
    public CITelephone newCITelephone();
    
    
    public CIDate newCIDate();
    
    
    public CISeries newCISeries();
    
    
    public MDKeywords newMDKeywords();
    
    
    public MDIdentifier newMDIdentifier();
    
    
    public MDConstraints newMDConstraints();
    
    
    public MDLegalConstraints newMDLegalConstraints();
    
}

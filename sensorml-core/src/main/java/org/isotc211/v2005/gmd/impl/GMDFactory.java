package org.isotc211.v2005.gmd.impl;

import org.isotc211.v2005.gmd.*;


public class GMDFactory implements Factory
{       
    
    public GMDFactory()
    {
    }
    
    
    public CIResponsibleParty newCIResponsibleParty()
    {
        return new CIResponsiblePartyImpl();
    }
    
    
    public CICitation newCICitation()
    {
        return new CICitationImpl();
    }
    
    
    public CIAddress newCIAddress()
    {
        return new CIAddressImpl();
    }
    
    
    public CIOnlineResource newCIOnlineResource()
    {
        return new CIOnlineResourceImpl();
    }
    
    
    public CIContact newCIContact()
    {
        return new CIContactImpl();
    }
    
    
    public CITelephone newCITelephone()
    {
        return new CITelephoneImpl();
    }
    
    
    public CIDate newCIDate()
    {
        return new CIDateImpl();
    }
    
    
    public CISeries newCISeries()
    {
        return new CISeriesImpl();
    }
    
    
    public MDKeywords newMDKeywords()
    {
        return new MDKeywordsImpl();
    }
    
    
    public MDIdentifier newMDIdentifier()
    {
        return new MDIdentifierImpl();
    }
    
    
    public MDConstraints newMDConstraints()
    {
        return new MDConstraintsImpl();
    }
    
    
    public MDLegalConstraints newMDLegalConstraints()
    {
        return new MDLegalConstraintsImpl();
    }
}

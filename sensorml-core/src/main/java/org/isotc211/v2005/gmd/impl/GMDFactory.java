/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2015 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package org.isotc211.v2005.gmd.impl;

import org.isotc211.v2005.gmd.*;


public class GMDFactory implements Factory
{       
    
    public GMDFactory()
    {
    }
    
    
    @Override
    public CIResponsibleParty newCIResponsibleParty()
    {
        return new CIResponsiblePartyImpl();
    }
    
    
    @Override
    public CICitation newCICitation()
    {
        return new CICitationImpl();
    }
    
    
    @Override
    public CIAddress newCIAddress()
    {
        return new CIAddressImpl();
    }
    
    
    @Override
    public CIOnlineResource newCIOnlineResource()
    {
        return new CIOnlineResourceImpl();
    }
    
    
    @Override
    public CIContact newCIContact()
    {
        return new CIContactImpl();
    }
    
    
    @Override
    public CITelephone newCITelephone()
    {
        return new CITelephoneImpl();
    }
    
    
    @Override
    public CIDate newCIDate()
    {
        return new CIDateImpl();
    }
    
    
    @Override
    public CISeries newCISeries()
    {
        return new CISeriesImpl();
    }
    
    
    @Override
    public MDKeywords newMDKeywords()
    {
        return new MDKeywordsImpl();
    }
    
    
    @Override
    public MDIdentifier newMDIdentifier()
    {
        return new MDIdentifierImpl();
    }
    
    
    @Override
    public MDConstraints newMDConstraints()
    {
        return new MDConstraintsImpl();
    }
    
    
    @Override
    public MDLegalConstraints newMDLegalConstraints()
    {
        return new MDLegalConstraintsImpl();
    }
}

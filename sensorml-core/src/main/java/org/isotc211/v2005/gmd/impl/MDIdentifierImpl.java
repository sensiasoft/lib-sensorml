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
    private static final long serialVersionUID = -2545552291638995782L;
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

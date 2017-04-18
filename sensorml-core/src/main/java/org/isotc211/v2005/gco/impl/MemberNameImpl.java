/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2015 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package org.isotc211.v2005.gco.impl;

import net.opengis.OgcProperty;
import net.opengis.OgcPropertyImpl;
import org.isotc211.v2005.gco.MemberName;
import org.isotc211.v2005.gco.TypeName;


/**
 * POJO class for XML type MemberName_Type(@http://www.isotc211.org/2005/gco).
 *
 * This is a complex type.
 */
public class MemberNameImpl extends AbstractObjectImpl implements MemberName
{
    private static final long serialVersionUID = 4272284789581033537L;
    protected String aName = "";
    protected OgcProperty<TypeName> attributeType = new OgcPropertyImpl<TypeName>();
    
    
    public MemberNameImpl()
    {
    }
    
    
    /**
     * Gets the aName property
     */
    @Override
    public String getAName()
    {
        return aName;
    }
    
    
    /**
     * Sets the aName property
     */
    @Override
    public void setAName(String aName)
    {
        this.aName = aName;
    }
    
    
    /**
     * Gets the attributeType property
     */
    @Override
    public TypeName getAttributeType()
    {
        return attributeType.getValue();
    }
    
    
    /**
     * Gets extra info (name, xlink, etc.) carried by the attributeType property
     */
    @Override
    public OgcProperty<TypeName> getAttributeTypeProperty()
    {
        if (attributeType == null)
            attributeType = new OgcPropertyImpl<TypeName>();
        return attributeType;
    }
    
    
    /**
     * Sets the attributeType property
     */
    @Override
    public void setAttributeType(TypeName attributeType)
    {
        this.attributeType.setValue(attributeType);
    }
}

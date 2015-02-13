/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2015 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

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

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

import org.isotc211.v2005.gco.TypeName;


/**
 * POJO class for XML type TypeName_Type(@http://www.isotc211.org/2005/gco).
 *
 * This is a complex type.
 */
public class TypeNameImpl extends AbstractObjectImpl implements TypeName
{
    private static final long serialVersionUID = -3883723533125572446L;
    protected String aName = "";
    
    
    public TypeNameImpl()
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
}

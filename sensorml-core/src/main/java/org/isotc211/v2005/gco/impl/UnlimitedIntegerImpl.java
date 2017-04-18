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

import org.isotc211.v2005.gco.UnlimitedInteger;


/**
 * POJO class for XML type UnlimitedInteger_Type(@http://www.isotc211.org/2005/gco).
 *
 */
public class UnlimitedIntegerImpl implements UnlimitedInteger
{
    private static final long serialVersionUID = -3216758152221505100L;
    protected Boolean isInfinite;
    protected int value;
    
    
    public UnlimitedIntegerImpl()
    {
    }
    
    
    /**
     * Gets the isInfinite property
     */
    @Override
    public boolean getIsInfinite()
    {
        return isInfinite;
    }
    
    
    /**
     * Checks if isInfinite is set
     */
    @Override
    public boolean isSetIsInfinite()
    {
        return (isInfinite != null);
    }
    
    
    /**
     * Sets the isInfinite property
     */
    @Override
    public void setIsInfinite(boolean isInfinite)
    {
        this.isInfinite = isInfinite;
    }
    
    
    /**
     * Unsets the isInfinite property
     */
    @Override
    public void unSetIsInfinite()
    {
        this.isInfinite = null;
    }
    
    
    /**
     * Gets the inline value
     */
    @Override
    public int getValue()
    {
        return value;
    }
    
    
    /**
     * Sets the inline value
     */
    @Override
    public void setValue(int value)
    {
        this.value = value;
    }
}

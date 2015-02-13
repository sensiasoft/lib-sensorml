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

import org.isotc211.v2005.gco.Binary;


/**
 * POJO class for XML type Binary_Type(@http://www.isotc211.org/2005/gco).
 *
 */
public class BinaryImpl implements Binary
{
    static final long serialVersionUID = 1L;
    protected String src;
    protected String value;
    
    
    public BinaryImpl()
    {
    }
    
    
    /**
     * Gets the src property
     */
    @Override
    public String getSrc()
    {
        return src;
    }
    
    
    /**
     * Checks if src is set
     */
    @Override
    public boolean isSetSrc()
    {
        return (src != null);
    }
    
    
    /**
     * Sets the src property
     */
    @Override
    public void setSrc(String src)
    {
        this.src = src;
    }
    
    
    /**
     * Gets the inline value
     */
    @Override
    public String getValue()
    {
        return value;
    }
    
    
    /**
     * Sets the inline value
     */
    @Override
    public void setValue(String value)
    {
        this.value = value;
    }
}

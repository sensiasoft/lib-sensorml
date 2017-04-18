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

import org.isotc211.v2005.gco.MultiplicityRange;
import org.isotc211.v2005.gco.UnlimitedInteger;


/**
 * POJO class for XML type MultiplicityRange_Type(@http://www.isotc211.org/2005/gco).
 *
 * This is a complex type.
 */
public class MultiplicityRangeImpl extends AbstractObjectImpl implements MultiplicityRange
{
    private static final long serialVersionUID = -1203149370629963634L;
    protected int lower;
    protected UnlimitedInteger upper;
    
    
    public MultiplicityRangeImpl()
    {
    }
    
    
    /**
     * Gets the lower property
     */
    @Override
    public int getLower()
    {
        return lower;
    }
    
    
    /**
     * Sets the lower property
     */
    @Override
    public void setLower(int lower)
    {
        this.lower = lower;
    }
    
    
    /**
     * Gets the upper property
     */
    @Override
    public UnlimitedInteger getUpper()
    {
        return upper;
    }
    
    
    /**
     * Sets the upper property
     */
    @Override
    public void setUpper(UnlimitedInteger upper)
    {
        this.upper = upper;
    }
}

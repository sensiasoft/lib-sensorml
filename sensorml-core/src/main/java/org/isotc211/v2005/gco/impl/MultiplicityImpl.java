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

import net.opengis.OgcPropertyList;
import org.isotc211.v2005.gco.Multiplicity;
import org.isotc211.v2005.gco.MultiplicityRange;


/**
 * POJO class for XML type Multiplicity_Type(@http://www.isotc211.org/2005/gco).
 *
 * This is a complex type.
 */
public class MultiplicityImpl extends AbstractObjectImpl implements Multiplicity
{
    private static final long serialVersionUID = -6531215272881991153L;
    protected OgcPropertyList<MultiplicityRange> rangeList = new OgcPropertyList<MultiplicityRange>();
    
    
    public MultiplicityImpl()
    {
    }
    
    
    /**
     * Gets the list of range properties
     */
    @Override
    public OgcPropertyList<MultiplicityRange> getRangeList()
    {
        return rangeList;
    }
    
    
    /**
     * Returns number of range properties
     */
    @Override
    public int getNumRanges()
    {
        if (rangeList == null)
            return 0;
        return rangeList.size();
    }
    
    
    /**
     * Adds a new range property
     */
    @Override
    public void addRange(MultiplicityRange range)
    {
        this.rangeList.add(range);
    }
}

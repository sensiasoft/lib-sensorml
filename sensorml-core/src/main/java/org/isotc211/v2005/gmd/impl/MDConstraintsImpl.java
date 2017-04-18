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

import java.util.ArrayList;
import java.util.List;
import org.isotc211.v2005.gco.impl.AbstractObjectImpl;
import org.isotc211.v2005.gmd.MDConstraints;


/**
 * POJO class for XML type MD_Constraints_Type(@http://www.isotc211.org/2005/gmd).
 *
 * This is a complex type.
 */
public class MDConstraintsImpl extends AbstractObjectImpl implements MDConstraints
{
    private static final long serialVersionUID = -7611282966228372597L;
    protected ArrayList<String> useLimitationList = new ArrayList<String>();
    
    
    public MDConstraintsImpl()
    {
    }
    
    
    /**
     * Gets the list of useLimitation properties
     */
    @Override
    public List<String> getUseLimitationList()
    {
        return useLimitationList;
    }
    
    
    /**
     * Returns number of useLimitation properties
     */
    @Override
    public int getNumUseLimitations()
    {
        if (useLimitationList == null)
            return 0;
        return useLimitationList.size();
    }
    
    
    /**
     * Adds a new useLimitation property
     */
    @Override
    public void addUseLimitation(String useLimitation)
    {
        this.useLimitationList.add(useLimitation);
    }
}

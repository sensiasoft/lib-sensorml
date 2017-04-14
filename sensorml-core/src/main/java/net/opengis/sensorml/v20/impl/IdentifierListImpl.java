/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2015 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package net.opengis.sensorml.v20.impl;

import java.util.ArrayList;
import java.util.List;
import net.opengis.sensorml.v20.IdentifierList;
import net.opengis.sensorml.v20.Term;


/**
 * POJO class for XML type IdentifierListType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public class IdentifierListImpl extends AbstractMetadataListImpl implements IdentifierList
{
    private static final long serialVersionUID = -4763800903563662109L;
    protected ArrayList<Term> identifier2List = new ArrayList<Term>();
    
    
    public IdentifierListImpl()
    {
    }
    
    
    /**
     * Gets the list of identifier2 properties
     */
    @Override
    public List<Term> getIdentifier2List()
    {
        return identifier2List;
    }
    
    
    /**
     * Returns number of identifier2 properties
     */
    @Override
    public int getNumIdentifier2s()
    {
        if (identifier2List == null)
            return 0;
        return identifier2List.size();
    }
    
    
    /**
     * Adds a new identifier2 property
     */
    @Override
    public void addIdentifier2(Term identifier2)
    {
        this.identifier2List.add(identifier2);
    }
}

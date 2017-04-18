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
import org.isotc211.v2005.gco.CodeListValue;
import org.isotc211.v2005.gmd.MDLegalConstraints;


/**
 * POJO class for XML type MD_LegalConstraints_Type(@http://www.isotc211.org/2005/gmd).
 *
 * This is a complex type.
 */
public class MDLegalConstraintsImpl extends MDConstraintsImpl implements MDLegalConstraints
{
    private static final long serialVersionUID = 7206574193175714723L;
    protected ArrayList<CodeListValue> accessConstraintsList = new ArrayList<CodeListValue>();
    protected ArrayList<CodeListValue> useConstraintsList = new ArrayList<CodeListValue>();
    protected ArrayList<String> otherConstraintsList = new ArrayList<String>();
    
    
    public MDLegalConstraintsImpl()
    {
    }
    
    
    /**
     * Gets the list of accessConstraints properties
     */
    @Override
    public List<CodeListValue> getAccessConstraintsList()
    {
        return accessConstraintsList;
    }
    
    
    /**
     * Returns number of accessConstraints properties
     */
    @Override
    public int getNumAccessConstraints()
    {
        if (accessConstraintsList == null)
            return 0;
        return accessConstraintsList.size();
    }
    
    
    /**
     * Adds a new accessConstraints property
     */
    @Override
    public void addAccessConstraints(CodeListValue accessConstraints)
    {
        this.accessConstraintsList.add(accessConstraints);
    }
    
    
    /**
     * Gets the list of useConstraints properties
     */
    @Override
    public List<CodeListValue> getUseConstraintsList()
    {
        return useConstraintsList;
    }
    
    
    /**
     * Returns number of useConstraints properties
     */
    @Override
    public int getNumUseConstraints()
    {
        if (useConstraintsList == null)
            return 0;
        return useConstraintsList.size();
    }
    
    
    /**
     * Adds a new useConstraints property
     */
    @Override
    public void addUseConstraints(CodeListValue useConstraints)
    {
        this.useConstraintsList.add(useConstraints);
    }
    
    
    /**
     * Gets the list of otherConstraints properties
     */
    @Override
    public List<String> getOtherConstraintsList()
    {
        return otherConstraintsList;
    }
    
    
    /**
     * Returns number of otherConstraints properties
     */
    @Override
    public int getNumOtherConstraints()
    {
        if (otherConstraintsList == null)
            return 0;
        return otherConstraintsList.size();
    }
    
    
    /**
     * Adds a new otherConstraints property
     */
    @Override
    public void addOtherConstraints(String otherConstraints)
    {
        this.otherConstraintsList.add(otherConstraints);
    }
}

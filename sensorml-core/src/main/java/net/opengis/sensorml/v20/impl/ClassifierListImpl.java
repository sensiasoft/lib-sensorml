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
import net.opengis.sensorml.v20.ClassifierList;
import net.opengis.sensorml.v20.Term;


/**
 * POJO class for XML type ClassifierListType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public class ClassifierListImpl extends AbstractMetadataListImpl implements ClassifierList
{
    private static final long serialVersionUID = 1462708215269569157L;
    protected ArrayList<Term> classifierList = new ArrayList<Term>();
    
    
    public ClassifierListImpl()
    {
    }
    
    
    /**
     * Gets the list of classifier properties
     */
    @Override
    public List<Term> getClassifierList()
    {
        return classifierList;
    }
    
    
    /**
     * Returns number of classifier properties
     */
    @Override
    public int getNumClassifiers()
    {
        if (classifierList == null)
            return 0;
        return classifierList.size();
    }
    
    
    /**
     * Adds a new classifier property
     */
    @Override
    public void addClassifier(Term classifier)
    {
        this.classifierList.add(classifier);
    }
}

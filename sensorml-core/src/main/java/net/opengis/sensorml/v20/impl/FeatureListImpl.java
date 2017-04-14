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

import net.opengis.OgcPropertyList;
import net.opengis.gml.v32.AbstractFeature;
import net.opengis.sensorml.v20.FeatureList;


/**
 * POJO class for XML type FeatureListType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public class FeatureListImpl extends AbstractMetadataListImpl implements FeatureList
{
    private static final long serialVersionUID = 559598216238665236L;
    protected OgcPropertyList<AbstractFeature> featureList = new OgcPropertyList<AbstractFeature>();
    
    
    public FeatureListImpl()
    {
    }
    
    
    /**
     * Gets the list of feature properties
     */
    @Override
    public OgcPropertyList<AbstractFeature> getFeatureList()
    {
        return featureList;
    }
    
    
    /**
     * Returns number of feature properties
     */
    @Override
    public int getNumFeatures()
    {
        if (featureList == null)
            return 0;
        return featureList.size();
    }
    
    
    /**
     * Adds a new feature property
     */
    @Override
    public void addFeature(AbstractFeature feature)
    {
        this.featureList.add(feature);
    }
}

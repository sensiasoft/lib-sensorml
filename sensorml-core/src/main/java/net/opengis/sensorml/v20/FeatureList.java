/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2015 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package net.opengis.sensorml.v20;

import net.opengis.OgcPropertyList;
import net.opengis.gml.v32.AbstractFeature;


/**
 * POJO class for XML type FeatureListType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
@SuppressWarnings("javadoc")
public interface FeatureList extends AbstractMetadataList
{
    
    
    /**
     * Gets the list of feature properties
     */
    public OgcPropertyList<AbstractFeature> getFeatureList();
    
    
    /**
     * Returns number of feature properties
     */
    public int getNumFeatures();
    
    
    /**
     * Adds a new feature property
     */
    public void addFeature(AbstractFeature feature);
}

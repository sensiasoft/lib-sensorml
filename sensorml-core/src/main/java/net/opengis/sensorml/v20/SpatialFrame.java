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
import net.opengis.swe.v20.AbstractSWEIdentifiable;


/**
 * POJO class for XML type SpatialFrameType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
@SuppressWarnings("javadoc")
public interface SpatialFrame extends AbstractSWEIdentifiable
{
    
    
    /**
     * Gets the origin property
     */
    public String getOrigin();
    
    
    /**
     * Sets the origin property
     */
    public void setOrigin(String origin);
    
    
    /**
     * Gets the list of axis properties
     */
    public OgcPropertyList<String> getAxisList();
    
    
    /**
     * Returns number of axis properties
     */
    public int getNumAxis();
    
    
    /**
     * Gets the axis property with the given name
     */
    public String getAxis(String name);
    
    
    /**
     * Adds a new axis property
     */
    public void addAxis(String name, String axisDesc);
}

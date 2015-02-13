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
import net.opengis.swe.v20.AbstractSWE;


/**
 * POJO class for XML type ComponentListType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public interface ComponentList extends AbstractSWE
{
    
    
    /**
     * Gets the list of component properties
     */
    public OgcPropertyList<AbstractProcess> getComponentList();
    
    
    /**
     * Returns number of component properties
     */
    public int getNumComponents();
    
    
    /**
     * Gets the component property with the given name
     */
    public AbstractProcess getComponent(String name);
    
    
    /**
     * Adds a new component property
     */
    public void addComponent(String name, AbstractProcess component);
}

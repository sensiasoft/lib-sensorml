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

import net.opengis.OgcProperty;


/**
 * POJO class for XML type PhysicalSystemType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public interface PhysicalSystem extends AbstractPhysicalProcess
{
    
    
    /**
     * Gets the components property
     */
    public ComponentList getComponents();
    
    
    /**
     * Gets extra info (name, xlink, etc.) carried by the components property
     */
    public OgcProperty<ComponentList> getComponentsProperty();
    
    
    /**
     * Checks if components is set
     */
    public boolean isSetComponents();
    
    
    /**
     * Sets the components property
     */
    public void setComponents(ComponentList components);
    
    
    /**
     * Gets the connections property
     */
    public ConnectionList getConnections();
    
    
    /**
     * Gets extra info (name, xlink, etc.) carried by the connections property
     */
    public OgcProperty<ConnectionList> getConnectionsProperty();
    
    
    /**
     * Checks if connections is set
     */
    public boolean isSetConnections();
    
    
    /**
     * Sets the connections property
     */
    public void setConnections(ConnectionList connections);
}

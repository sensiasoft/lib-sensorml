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

import java.util.List;
import javax.xml.namespace.QName;
import net.opengis.OgcPropertyList;


/**
 * POJO class for XML type AggregateProcessType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
@SuppressWarnings("javadoc")
public interface AggregateProcess extends AbstractProcess
{
    public final static QName DEFAULT_QNAME = new QName(DEFAULT_NS_URI, AggregateProcess.class.getSimpleName());
    
    
    /**
     * Gets the list of components
     */
    public OgcPropertyList<AbstractProcess> getComponentList();
    
    
    /**
     * Returns number of components
     */
    public int getNumComponents();
    
    
    /**
     * Gets the component with the given name
     */
    public AbstractProcess getComponent(String name);    
    
    
    /**
     * Adds a new component
     */
    public void addComponent(String name, AbstractProcess component);
    
    
    /**
     * Gets the list of connections
     */
    public List<Link> getConnectionList();

    
    /**
     * Returns number of connections
     */
    public int getNumConnections();

    
    /**
     * Adds a new connection
     */
    public void addConnection(Link connection);
}

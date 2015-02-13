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
import org.vast.data.AbstractSWEImpl;
import net.opengis.sensorml.v20.ConnectionList;
import net.opengis.sensorml.v20.Link;


/**
 * POJO class for XML type ConnectionListType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public class ConnectionListImpl extends AbstractSWEImpl implements ConnectionList
{
    static final long serialVersionUID = 1L;
    protected List<Link> connectionList = new ArrayList<Link>();
    
    
    public ConnectionListImpl()
    {
    }
    
    
    /**
     * Gets the list of connection properties
     */
    @Override
    public List<Link> getConnectionList()
    {
        return connectionList;
    }
    
    
    /**
     * Returns number of connection properties
     */
    @Override
    public int getNumConnections()
    {
        if (connectionList == null)
            return 0;
        return connectionList.size();
    }
    
    
    /**
     * Adds a new connection property
     */
    @Override
    public void addConnection(Link connection)
    {
        this.connectionList.add(connection);
    }
}

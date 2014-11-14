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

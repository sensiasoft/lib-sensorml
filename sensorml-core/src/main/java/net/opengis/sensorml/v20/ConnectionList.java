package net.opengis.sensorml.v20;

import java.util.List;
import net.opengis.swe.v20.AbstractSWE;


/**
 * POJO class for XML type ConnectionListType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public interface ConnectionList extends AbstractSWE
{
    
    
    /**
     * Gets the list of connection properties
     */
    public List<Link> getConnectionList();
    
    
    /**
     * Returns number of connection properties
     */
    public int getNumConnections();
    
    
    /**
     * Adds a new connection property
     */
    public void addConnection(Link connection);
}

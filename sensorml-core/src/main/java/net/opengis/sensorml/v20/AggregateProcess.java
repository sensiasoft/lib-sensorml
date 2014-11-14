package net.opengis.sensorml.v20;

import net.opengis.OgcProperty;


/**
 * POJO class for XML type AggregateProcessType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public interface AggregateProcess extends AbstractProcess
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

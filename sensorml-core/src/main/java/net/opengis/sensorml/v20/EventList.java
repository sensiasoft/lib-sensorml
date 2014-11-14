package net.opengis.sensorml.v20;

import net.opengis.OgcPropertyList;


/**
 * POJO class for XML type EventListType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public interface EventList extends AbstractMetadataList
{
    
    
    /**
     * Gets the list of event properties
     */
    public OgcPropertyList<Event> getEventList();
    
    
    /**
     * Returns number of event properties
     */
    public int getNumEvents();
    
    
    /**
     * Adds a new event property
     */
    public void addEvent(Event event);
}

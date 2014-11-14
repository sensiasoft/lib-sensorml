package net.opengis.sensorml.v20.impl;

import net.opengis.OgcPropertyList;
import net.opengis.sensorml.v20.Event;
import net.opengis.sensorml.v20.EventList;


/**
 * POJO class for XML type EventListType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public class EventListImpl extends AbstractMetadataListImpl implements EventList
{
    static final long serialVersionUID = 1L;
    protected OgcPropertyList<Event> eventList = new OgcPropertyList<Event>();
    
    
    public EventListImpl()
    {
    }
    
    
    /**
     * Gets the list of event properties
     */
    @Override
    public OgcPropertyList<Event> getEventList()
    {
        return eventList;
    }
    
    
    /**
     * Returns number of event properties
     */
    @Override
    public int getNumEvents()
    {
        if (eventList == null)
            return 0;
        return eventList.size();
    }
    
    
    /**
     * Adds a new event property
     */
    @Override
    public void addEvent(Event event)
    {
        this.eventList.add(event);
    }
}

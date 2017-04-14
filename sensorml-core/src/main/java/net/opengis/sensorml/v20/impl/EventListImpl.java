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
    private static final long serialVersionUID = -7647103509371734513L;
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

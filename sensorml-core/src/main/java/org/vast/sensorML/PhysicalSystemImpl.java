package org.vast.sensorML;

import java.util.ArrayList;
import java.util.List;
import net.opengis.OgcProperty;
import net.opengis.OgcPropertyImpl;
import net.opengis.OgcPropertyList;
import net.opengis.gml.v32.Point;
import net.opengis.gml.v32.Reference;
import net.opengis.sensorml.v20.AbstractProcess;
import net.opengis.sensorml.v20.ComponentList;
import net.opengis.sensorml.v20.ConnectionList;
import net.opengis.sensorml.v20.PhysicalSystem;
import net.opengis.sensorml.v20.SpatialFrame;
import net.opengis.sensorml.v20.TemporalFrame;
import net.opengis.sensorml.v20.impl.ComponentListImpl;
import net.opengis.sensorml.v20.impl.ConnectionListImpl;
import net.opengis.swe.v20.DataArray;
import net.opengis.swe.v20.DataRecord;
import net.opengis.swe.v20.Text;
import net.opengis.swe.v20.Time;
import net.opengis.swe.v20.Vector;


/**
 * POJO class for XML type PhysicalSystemType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public class PhysicalSystemImpl extends AggregateProcessImpl implements PhysicalSystem
{
    static final long serialVersionUID = 1L;
    protected Reference attachedTo;
    protected List<SpatialFrame> localReferenceFrameList = new ArrayList<SpatialFrame>();
    protected List<TemporalFrame> localTimeFrameList = new ArrayList<TemporalFrame>();
    protected OgcPropertyList<Object> positionList = new OgcPropertyList<Object>();
    protected OgcPropertyList<Time> timePositionList = new OgcPropertyList<Time>();
    protected OgcProperty<ComponentList> components;
    protected OgcProperty<ConnectionList> connections;
    
    
    public PhysicalSystemImpl()
    {
    }
    
    
    /**
     * Gets the attachedTo property
     */
    @Override
    public Reference getAttachedTo()
    {
        return attachedTo;
    }
    
    
    /**
     * Checks if attachedTo is set
     */
    @Override
    public boolean isSetAttachedTo()
    {
        return (attachedTo != null);
    }
    
    
    /**
     * Sets the attachedTo property
     */
    @Override
    public void setAttachedTo(Reference attachedTo)
    {
        this.attachedTo = attachedTo;
    }
    
    
    /**
     * Gets the list of localReferenceFrame properties
     */
    @Override
    public List<SpatialFrame> getLocalReferenceFrameList()
    {
        return localReferenceFrameList;
    }
    
    
    /**
     * Returns number of localReferenceFrame properties
     */
    @Override
    public int getNumLocalReferenceFrames()
    {
        if (localReferenceFrameList == null)
            return 0;
        return localReferenceFrameList.size();
    }
    
    
    /**
     * Adds a new localReferenceFrame property
     */
    @Override
    public void addLocalReferenceFrame(SpatialFrame localReferenceFrame)
    {
        this.localReferenceFrameList.add(localReferenceFrame);
    }
    
    
    /**
     * Gets the list of localTimeFrame properties
     */
    @Override
    public List<TemporalFrame> getLocalTimeFrameList()
    {
        return localTimeFrameList;
    }
    
    
    /**
     * Returns number of localTimeFrame properties
     */
    @Override
    public int getNumLocalTimeFrames()
    {
        if (localTimeFrameList == null)
            return 0;
        return localTimeFrameList.size();
    }
    
    
    /**
     * Adds a new localTimeFrame property
     */
    @Override
    public void addLocalTimeFrame(TemporalFrame localTimeFrame)
    {
        this.localTimeFrameList.add(localTimeFrame);
    }
    
    
    /**
     * Gets the list of position properties
     */
    @Override
    public OgcPropertyList<Object> getPositionList()
    {
        return positionList;
    }
    
    
    /**
     * Returns number of position properties
     */
    @Override
    public int getNumPositions()
    {
        if (positionList == null)
            return 0;
        return positionList.size();
    }
    
    
    /**
     * Adds a new positionAsText property
     */
    @Override
    public void addPositionAsText(Text position)
    {
        this.positionList.add(position);
    }
    
    
    /**
     * Adds a new positionAsPoint property
     */
    @Override
    public void addPositionAsPoint(Point position)
    {
        this.positionList.add(position);
    }
    
    
    /**
     * Adds a new positionAsVector property
     */
    @Override
    public void addPositionAsVector(Vector position)
    {
        this.positionList.add(position);
    }
    
    
    /**
     * Adds a new positionAsDataRecord property
     */
    @Override
    public void addPositionAsDataRecord(DataRecord position)
    {
        this.positionList.add(position);
    }
    
    
    /**
     * Adds a new positionAsDataArray1 property
     */
    @Override
    public void addPositionAsDataArray1(DataArray position)
    {
        this.positionList.add(position);
    }
    
    
    /**
     * Adds a new positionAsAbstractProcess property
     */
    @Override
    public void addPositionAsAbstractProcess(AbstractProcess position)
    {
        this.positionList.add(position);
    }
    
    
    /**
     * Gets the list of timePosition properties
     */
    @Override
    public OgcPropertyList<Time> getTimePositionList()
    {
        return timePositionList;
    }
    
    
    /**
     * Returns number of timePosition properties
     */
    @Override
    public int getNumTimePositions()
    {
        if (timePositionList == null)
            return 0;
        return timePositionList.size();
    }
    
    
    /**
     * Adds a new timePosition property
     */
    @Override
    public void addTimePosition(Time timePosition)
    {
        this.timePositionList.add(timePosition);
    }
    
    
    /**
     * Gets the components property
     */
    @Override
    public ComponentList getComponents()
    {
        if (components == null)
            components = new OgcPropertyImpl<ComponentList>(new ComponentListImpl());
        return components.getValue();
    }
    
    
    /**
     * Gets extra info (name, xlink, etc.) carried by the components property
     */
    @Override
    public OgcProperty<ComponentList> getComponentsProperty()
    {
        if (components == null)
            components = new OgcPropertyImpl<ComponentList>();
        return components;
    }
    
    
    /**
     * Checks if components is set
     */
    @Override
    public boolean isSetComponents()
    {
        return (components != null && (components.hasValue() || components.hasHref()));
    }
    
    
    /**
     * Sets the components property
     */
    @Override
    public void setComponents(ComponentList components)
    {
        if (this.components == null)
            this.components = new OgcPropertyImpl<ComponentList>();
        this.components.setValue(components);
    }
    
    
    /**
     * Gets the connections property
     */
    @Override
    public ConnectionList getConnections()
    {
        if (connections == null)
            connections = new OgcPropertyImpl<ConnectionList>(new ConnectionListImpl());
        return connections.getValue();
    }
    
    
    /**
     * Gets extra info (name, xlink, etc.) carried by the connections property
     */
    @Override
    public OgcProperty<ConnectionList> getConnectionsProperty()
    {
        if (connections == null)
            connections = new OgcPropertyImpl<ConnectionList>();
        return connections;
    }
    
    
    /**
     * Checks if connections is set
     */
    @Override
    public boolean isSetConnections()
    {
        return (connections != null && (connections.hasValue() || connections.hasHref()));
    }
    
    
    /**
     * Sets the connections property
     */
    @Override
    public void setConnections(ConnectionList connections)
    {
        if (this.connections == null)
            this.connections = new OgcPropertyImpl<ConnectionList>();
        this.connections.setValue(connections);
    }
}

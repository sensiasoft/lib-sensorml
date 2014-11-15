package org.vast.sensorML;

import net.opengis.sensorml.v20.*;
import net.opengis.sensorml.v20.impl.*;


public class SMLFactory implements Factory
{
    
    public SMLFactory()
    {
    }
    
    
    public SimpleProcess newSimpleProcess()
    {
        // TODO implement logic to instantiate the right process implementation!
        // how do we do this since parser doesn't know the method URI at this point?
        return new SimpleProcessImpl();
    }
    
    
    public ProcessMethod newProcessMethod()
    {
        return new ProcessMethodImpl();
    }
    
    
    public Mode newMode()
    {
        return new ModeImpl();
    }
    
    
    public ModeChoice newModeChoice()
    {
        return new ModeChoiceImpl();
    }
    
    
    public Settings newSettings()
    {
        return new SettingsImpl();
    }
    
    
    public ModeSetting newModeSettingProperty()
    {
        return new ModeSettingImpl();
    }
    
    
    public StatusSetting newStatusSettingProperty()
    {
        return new StatusSettingImpl();
    }
    
    
    public ValueSetting newValueSettingProperty()
    {
        return new ValueSettingImpl();
    }
    
    
    public ConstraintSetting newConstraintSettingProperty()
    {
        return new ConstraintSettingImpl();
    }
    
    
    public ArraySetting newArraySettingProperty()
    {
        return new ArraySettingImpl();
    }
    
    
    public PhysicalSystem newPhysicalSystem()
    {
        return new org.vast.sensorML.PhysicalSystemImpl();
    }
    
    
    public PhysicalComponent newPhysicalComponent()
    {
        return new org.vast.sensorML.PhysicalComponentImpl();
    }
    
    
    public TemporalFrame newTemporalFrame()
    {
        return new TemporalFrameImpl();
    }
    
    
    public SpatialFrame newSpatialFrame()
    {
        return new SpatialFrameImpl();
    }
    
    
    public AggregateProcess newAggregateProcess()
    {
        return new org.vast.sensorML.AggregateProcessImpl();
    }
    
    
    public ConnectionList newConnectionList()
    {
        return new ConnectionListImpl();
    }
    
    
    public ComponentList newComponentList()
    {
        return new ComponentListImpl();
    }
    
    
    public Link newLink()
    {
        return new org.vast.sensorML.LinkImpl();
    }
    
    
    public AbstractSettings newAbstractSettings()
    {
        return new AbstractSettingsImpl();
    }
    
    
    public IdentifierList newIdentifierList()
    {
        return new IdentifierListImpl();
    }
    
    
    public DocumentList newDocumentList()
    {
        return new DocumentListImpl();
    }
    
    
    public CharacteristicList newCharacteristicList()
    {
        return new CharacteristicListImpl();
    }
    
    
    public FeatureList newFeatureList()
    {
        return new FeatureListImpl();
    }
    
    
    public Event newEvent()
    {
        return new EventImpl();
    }
    
    
    public CapabilityList newCapabilityList()
    {
        return new CapabilityListImpl();
    }
    
    
    public EventList newEventList()
    {
        return new EventListImpl();
    }
    
    
    public AbstractMetadataList newAbstractMetadataList()
    {
        return new AbstractMetadataListImpl();
    }
    
    
    public ContactList newContactList()
    {
        return new ContactListImpl();
    }
    
    
    public KeywordList newKeywordList()
    {
        return new KeywordListImpl();
    }
    
    
    public Term newTerm()
    {
        return new TermImpl();
    }
    
    
    public ClassifierList newClassifierList()
    {
        return new ClassifierListImpl();
    }
    
    
    public AbstractModes newAbstractModes()
    {
        return new AbstractModesImpl();
    }
    
    
    public DataInterface newDataInterface()
    {
        return new DataInterfaceImpl();
    }
    
    
    public ObservableProperty newObservableProperty()
    {
        return new ObservablePropertyImpl();
    }
}

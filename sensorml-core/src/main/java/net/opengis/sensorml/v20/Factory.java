package net.opengis.sensorml.v20;



public interface Factory
{
    
    
    public SimpleProcess newSimpleProcess();
    
    
    public ProcessMethod newProcessMethod();
    
    
    public Mode newMode();
    
    
    public ModeChoice newModeChoice();
    
    
    public Settings newSettings();
    
    
    public ModeSetting newModeSettingProperty();
    
    
    public ValueSetting newValueSettingProperty();
    
    
    public ConstraintSetting newConstraintSettingProperty();
    
    
    public StatusSetting newStatusSettingProperty();
    
    
    public ArraySetting newArraySettingProperty();
    
    
    public PhysicalSystem newPhysicalSystem();
    
    
    public PhysicalComponent newPhysicalComponent();
    
    
    public TemporalFrame newTemporalFrame();
    
    
    public SpatialFrame newSpatialFrame();
    
    
    public AggregateProcess newAggregateProcess();
    
    
    public ConnectionList newConnectionList();
    
    
    public ComponentList newComponentList();
    
    
    public Link newLink();
    
    
    public AbstractSettings newAbstractSettings();
    
    
    public OutputList newOutputList();
    
    
    public IdentifierList newIdentifierList();
    
    
    public InputList newInputList();
    
    
    public DocumentList newDocumentList();
    
    
    public CharacteristicList newCharacteristicList();
    
    
    public FeatureList newFeatureList();
    
    
    public Event newEvent();
    
    
    public CapabilityList newCapabilityList();
    
    
    public EventList newEventList();
    
    
    public AbstractMetadataList newAbstractMetadataList();
    
    
    public ContactList newContactList();
    
    
    public KeywordList newKeywordList();
    
    
    public Term newTerm();
    
    
    public ClassifierList newClassifierList();
    
    
    public ParameterList newParameterList();
    
    
    public AbstractModes newAbstractModes();
    
    
    public DataInterface newDataInterface();
    
    
    public ObservableProperty newObservableProperty();
}

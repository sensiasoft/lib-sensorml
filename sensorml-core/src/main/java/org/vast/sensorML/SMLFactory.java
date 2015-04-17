/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2015 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.sensorML;

import net.opengis.sensorml.v20.*;
import net.opengis.sensorml.v20.impl.*;
import net.opengis.swe.v20.DataConstraint;


public class SMLFactory implements Factory
{
    
    public SMLFactory()
    {
    }
    
    
    public SimpleProcess newSimpleProcess()
    {
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
    
    
    public ModeSetting newModeSetting()
    {
        return new ModeSettingImpl();
    }
    
    
    public ModeSetting newModeSetting(String ref, String modeName)
    {
        return new ModeSettingImpl(ref, modeName);
    }
    
    
    public StatusSetting newStatusSetting()
    {
        return new StatusSettingImpl();
    }
    
    
    public StatusSetting newStatusSetting(String ref, Status value)
    {
        return new StatusSettingImpl(ref, value);
    }
    
    
    public ValueSetting newValueSetting()
    {
        return new ValueSettingImpl();
    }
    
    
    public ValueSetting newValueSetting(String ref, String value)
    {
        return new ValueSettingImpl(ref, value);
    }
    
    
    public ConstraintSetting newConstraintSetting()
    {
        return new ConstraintSettingImpl();
    }
    
    
    public ConstraintSetting newConstraintSetting(String ref, DataConstraint value)
    {
        return new ConstraintSettingImpl(ref, value);
    }
    
    
    public ArraySetting newArraySetting()
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

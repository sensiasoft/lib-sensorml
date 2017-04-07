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
    
    
    @Override
    public SimpleProcess newSimpleProcess()
    {
        return new SimpleProcessImpl();
    }
    
    
    @Override
    public ProcessMethod newProcessMethod()
    {
        return new ProcessMethodImpl();
    }
    
    
    @Override
    public Mode newMode()
    {
        return new ModeImpl();
    }
    
    
    @Override
    public ModeChoice newModeChoice()
    {
        return new ModeChoiceImpl();
    }
    
    
    @Override
    public Settings newSettings()
    {
        return new SettingsImpl();
    }
    
    
    @Override
    public ModeSetting newModeSetting()
    {
        return new ModeSettingImpl();
    }
    
    
    public ModeSetting newModeSetting(String ref, String modeName)
    {
        return new ModeSettingImpl(ref, modeName);
    }
    
    
    @Override
    public StatusSetting newStatusSetting()
    {
        return new StatusSettingImpl();
    }
    
    
    public StatusSetting newStatusSetting(String ref, Status value)
    {
        return new StatusSettingImpl(ref, value);
    }
    
    
    @Override
    public ValueSetting newValueSetting()
    {
        return new ValueSettingImpl();
    }
    
    
    public ValueSetting newValueSetting(String ref, String value)
    {
        return new ValueSettingImpl(ref, value);
    }
    
    
    @Override
    public ConstraintSetting newConstraintSetting()
    {
        return new ConstraintSettingImpl();
    }
    
    
    public ConstraintSetting newConstraintSetting(String ref, DataConstraint value)
    {
        return new ConstraintSettingImpl(ref, value);
    }
    
    
    @Override
    public ArraySetting newArraySetting()
    {
        return new ArraySettingImpl();
    }
    
    
    @Override
    public PhysicalSystem newPhysicalSystem()
    {
        return new org.vast.sensorML.PhysicalSystemImpl();
    }
    
    
    @Override
    public PhysicalComponent newPhysicalComponent()
    {
        return new org.vast.sensorML.PhysicalComponentImpl();
    }
    
    
    @Override
    public TemporalFrame newTemporalFrame()
    {
        return new TemporalFrameImpl();
    }
    
    
    @Override
    public SpatialFrame newSpatialFrame()
    {
        return new SpatialFrameImpl();
    }
    
    
    @Override
    public AggregateProcess newAggregateProcess()
    {
        return new org.vast.sensorML.AggregateProcessImpl();
    }
    
    
    @Override
    public Link newLink()
    {
        return new org.vast.sensorML.LinkImpl();
    }
    
    
    @Override
    public AbstractSettings newAbstractSettings()
    {
        return new AbstractSettingsImpl();
    }
    
    
    @Override
    public IdentifierList newIdentifierList()
    {
        return new IdentifierListImpl();
    }
    
    
    @Override
    public DocumentList newDocumentList()
    {
        return new DocumentListImpl();
    }
    
    
    @Override
    public CharacteristicList newCharacteristicList()
    {
        return new CharacteristicListImpl();
    }
    
    
    @Override
    public FeatureList newFeatureList()
    {
        return new FeatureListImpl();
    }
    
    
    @Override
    public Event newEvent()
    {
        return new EventImpl();
    }
    
    
    @Override
    public CapabilityList newCapabilityList()
    {
        return new CapabilityListImpl();
    }
    
    
    @Override
    public EventList newEventList()
    {
        return new EventListImpl();
    }
    
    
    @Override
    public AbstractMetadataList newAbstractMetadataList()
    {
        return new AbstractMetadataListImpl();
    }
    
    
    @Override
    public ContactList newContactList()
    {
        return new ContactListImpl();
    }
    
    
    @Override
    public KeywordList newKeywordList()
    {
        return new KeywordListImpl();
    }
    
    
    @Override
    public Term newTerm()
    {
        return new TermImpl();
    }
    
    
    @Override
    public ClassifierList newClassifierList()
    {
        return new ClassifierListImpl();
    }
    
    
    @Override
    public AbstractModes newAbstractModes()
    {
        return new AbstractModesImpl();
    }
    
    
    @Override
    public DataInterface newDataInterface()
    {
        return new DataInterfaceImpl();
    }
    
    
    @Override
    public ObservableProperty newObservableProperty()
    {
        return new ObservablePropertyImpl();
    }
}

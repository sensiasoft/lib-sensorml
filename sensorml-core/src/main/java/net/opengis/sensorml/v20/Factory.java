/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2015 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package net.opengis.sensorml.v20;


public interface Factory
{
    
    public SimpleProcess newSimpleProcess();
    
    
    public ProcessMethod newProcessMethod();
    
    
    public Mode newMode();
    
    
    public ModeChoice newModeChoice();
    
    
    public Settings newSettings();
    
    
    public ModeSetting newModeSetting();
    
    
    public ValueSetting newValueSetting();
    
    
    public ConstraintSetting newConstraintSetting();
    
    
    public StatusSetting newStatusSetting();
    
    
    public ArraySetting newArraySetting();
    
    
    public PhysicalSystem newPhysicalSystem();
    
    
    public PhysicalComponent newPhysicalComponent();
    
    
    public TemporalFrame newTemporalFrame();
    
    
    public SpatialFrame newSpatialFrame();
    
    
    public AggregateProcess newAggregateProcess();
    
    
    public Link newLink();
    
    
    public AbstractSettings newAbstractSettings();
    
    
    public IdentifierList newIdentifierList();
    
    
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
    
    
    public AbstractModes newAbstractModes();
    
    
    public DataInterface newDataInterface();
    
    
    public ObservableProperty newObservableProperty();
}

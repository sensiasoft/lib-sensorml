/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2015 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package net.opengis.sensorml.v20.bind;

import java.io.Serializable;
import java.util.Map;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import net.opengis.AbstractXMLStreamBindings;
import net.opengis.HrefResolverXML;
import net.opengis.OgcProperty;
import net.opengis.OgcPropertyImpl;
import net.opengis.gml.v32.AbstractFeature;
import net.opengis.gml.v32.AbstractTimeGeometricPrimitive;
import net.opengis.gml.v32.Point;
import net.opengis.gml.v32.Reference;
import net.opengis.gml.v32.TimeInstant;
import net.opengis.gml.v32.TimePeriod;
import net.opengis.sensorml.v20.AbstractAlgorithm;
import net.opengis.sensorml.v20.AbstractMetadataList;
import net.opengis.sensorml.v20.AbstractModes;
import net.opengis.sensorml.v20.AbstractPhysicalProcess;
import net.opengis.sensorml.v20.AbstractProcess;
import net.opengis.sensorml.v20.AbstractSettings;
import net.opengis.sensorml.v20.AggregateProcess;
import net.opengis.sensorml.v20.ArraySetting;
import net.opengis.sensorml.v20.CapabilityList;
import net.opengis.sensorml.v20.CharacteristicList;
import net.opengis.sensorml.v20.ClassifierList;
import net.opengis.sensorml.v20.ConstraintSetting;
import net.opengis.sensorml.v20.ContactList;
import net.opengis.sensorml.v20.DataInterface;
import net.opengis.sensorml.v20.DescribedObject;
import net.opengis.sensorml.v20.DocumentList;
import net.opengis.sensorml.v20.Event;
import net.opengis.sensorml.v20.EventList;
import net.opengis.sensorml.v20.FeatureList;
import net.opengis.sensorml.v20.IdentifierList;
import net.opengis.sensorml.v20.KeywordList;
import net.opengis.sensorml.v20.Link;
import net.opengis.sensorml.v20.Mode;
import net.opengis.sensorml.v20.ModeChoice;
import net.opengis.sensorml.v20.ModeSetting;
import net.opengis.sensorml.v20.ObservableProperty;
import net.opengis.sensorml.v20.PhysicalComponent;
import net.opengis.sensorml.v20.PhysicalSystem;
import net.opengis.sensorml.v20.ProcessMethod;
import net.opengis.sensorml.v20.Settings;
import net.opengis.sensorml.v20.SimpleProcess;
import net.opengis.sensorml.v20.SpatialFrame;
import net.opengis.sensorml.v20.Status;
import net.opengis.sensorml.v20.StatusSetting;
import net.opengis.sensorml.v20.TemporalFrame;
import net.opengis.sensorml.v20.Term;
import net.opengis.sensorml.v20.ValueSetting;
import net.opengis.swe.v20.DataComponent;
import net.opengis.swe.v20.DataConstraint;
import net.opengis.swe.v20.DataEncoding;
import net.opengis.swe.v20.AbstractSWEIdentifiable;
import net.opengis.swe.v20.AllowedTimes;
import net.opengis.swe.v20.AllowedTokens;
import net.opengis.swe.v20.AllowedValues;
import net.opengis.swe.v20.DataArray;
import net.opengis.swe.v20.DataRecord;
import net.opengis.swe.v20.DataStream;
import net.opengis.swe.v20.EncodedValues;
import net.opengis.swe.v20.Text;
import net.opengis.swe.v20.Time;
import net.opengis.swe.v20.Vector;
import org.isotc211.v2005.gmd.CIOnlineResource;
import org.isotc211.v2005.gmd.CIResponsibleParty;
import org.isotc211.v2005.gmd.MDKeywords;
import org.isotc211.v2005.gmd.MDLegalConstraints;
import org.vast.ogc.gml.GMLStaxBindings;
import net.opengis.sensorml.v20.Factory;


@SuppressWarnings("javadoc")
public class XMLStreamBindings extends AbstractXMLStreamBindings
{
    public final static String NS_URI = "http://www.opengis.net/sensorml/2.0";
    
    net.opengis.swe.v20.bind.XMLStreamBindings ns1Bindings;
    net.opengis.gml.v32.bind.XMLStreamBindings ns2Bindings;
    org.isotc211.v2005.gmd.bind.XMLStreamBindings ns3Bindings;
    Factory factory;
    
    
    public XMLStreamBindings(Factory factory, net.opengis.swe.v20.Factory ns1Factory, net.opengis.gml.v32.Factory ns2Factory,
            org.isotc211.v2005.gmd.Factory ns3Factory, org.isotc211.v2005.gco.Factory ns4Factory)
    {
        this.factory = factory;
        ns1Bindings = new net.opengis.swe.v20.bind.XMLStreamBindings(ns1Factory);
        ns2Bindings = new GMLStaxBindings(ns2Factory);
        ns3Bindings = new org.isotc211.v2005.gmd.bind.XMLStreamBindings(ns3Factory, ns4Factory);
    }
    
    
    /**
     * Read method for SimpleProcessType complex type
     */
    public SimpleProcess readSimpleProcessType(XMLStreamReader reader) throws XMLStreamException
    {
        SimpleProcess bean = factory.newSimpleProcess();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readSimpleProcessTypeAttributes(attrMap, bean);
        
        reader.nextTag();
        this.readSimpleProcessTypeElements(reader, bean);
        
        return bean;
    }
    
    
    /**
     * Reads attributes of SimpleProcessType complex type
     */
    public void readSimpleProcessTypeAttributes(Map<String, String> attrMap, SimpleProcess bean) throws XMLStreamException
    {
        this.readAbstractProcessTypeAttributes(attrMap, bean);
        
    }
    
    
    /**
     * Reads elements of SimpleProcessType complex type
     */
    public void readSimpleProcessTypeElements(XMLStreamReader reader, SimpleProcess bean) throws XMLStreamException
    {
        this.readAbstractProcessTypeElements(reader, bean);
        
        boolean found;
        
        // method
        found = checkElementName(reader, "method");
        if (found)
        {
            OgcProperty<ProcessMethod> methodProp = bean.getMethodProperty();
            readPropertyAttributes(reader, methodProp);
            
            reader.nextTag();
            if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
            {
                methodProp.setValue(this.readProcessMethod(reader));
                reader.nextTag(); // end property tag
            }
            
            reader.nextTag();
        }
    }
    
    
    /**
     * Write method for SimpleProcessType complex type
     */
    public void writeSimpleProcessType(XMLStreamWriter writer, SimpleProcess bean) throws XMLStreamException
    {
        this.writeSimpleProcessTypeAttributes(writer, bean);
        this.writeSimpleProcessTypeElements(writer, bean);
    }
    
    
    /**
     * Writes attributes of SimpleProcessType complex type
     */
    public void writeSimpleProcessTypeAttributes(XMLStreamWriter writer, SimpleProcess bean) throws XMLStreamException
    {
        this.writeAbstractProcessTypeAttributes(writer, bean);
    }
    
    
    /**
     * Writes elements of SimpleProcessType complex type
     */
    public void writeSimpleProcessTypeElements(XMLStreamWriter writer, SimpleProcess bean) throws XMLStreamException
    {
        this.writeAbstractProcessTypeElements(writer, bean);
        
        // method
        if (bean.isSetMethod())
        {
            writer.writeStartElement(NS_URI, "method");
            OgcProperty<ProcessMethod> methodProp = bean.getMethodProperty();
            writePropertyAttributes(writer, methodProp);
            if (methodProp.hasValue() && !methodProp.hasHref())
                this.writeProcessMethod(writer, bean.getMethod());
            writer.writeEndElement();
        }
    }
    
    
    /**
     * Read method for ProcessMethodType complex type
     */
    public ProcessMethod readProcessMethodType(XMLStreamReader reader) throws XMLStreamException
    {
        ProcessMethod bean = factory.newProcessMethod();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readProcessMethodTypeAttributes(attrMap, bean);
        
        reader.nextTag();
        this.readProcessMethodTypeElements(reader, bean);
        
        return bean;
    }
    
    
    /**
     * Reads attributes of ProcessMethodType complex type
     */
    public void readProcessMethodTypeAttributes(Map<String, String> attrMap, ProcessMethod bean) throws XMLStreamException
    {
        ns1Bindings.readAbstractSWEIdentifiableTypeAttributes(attrMap, bean);
        
    }
    
    
    /**
     * Reads elements of ProcessMethodType complex type
     */
    public void readProcessMethodTypeElements(XMLStreamReader reader, ProcessMethod bean) throws XMLStreamException
    {
        ns1Bindings.readAbstractSWEIdentifiableTypeElements(reader, bean);
        
        boolean found;
        
        // algorithm
        do
        {
            found = checkElementName(reader, "algorithm");
            if (found)
            {
                reader.nextTag();
                if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
                {
                    bean.addAlgorithm(this.readAbstractAlgorithm(reader));
                    reader.nextTag(); // end property tag
                }
                
                reader.nextTag();
            }
        }
        while (found);
    }
    
    
    /**
     * Write method for ProcessMethodType complex type
     */
    public void writeProcessMethodType(XMLStreamWriter writer, ProcessMethod bean) throws XMLStreamException
    {
        this.writeProcessMethodTypeAttributes(writer, bean);
        this.writeProcessMethodTypeElements(writer, bean);
    }
    
    
    /**
     * Writes attributes of ProcessMethodType complex type
     */
    public void writeProcessMethodTypeAttributes(XMLStreamWriter writer, ProcessMethod bean) throws XMLStreamException
    {
        ns1Bindings.writeAbstractSWEIdentifiableTypeAttributes(writer, bean);
    }
    
    
    /**
     * Writes elements of ProcessMethodType complex type
     */
    public void writeProcessMethodTypeElements(XMLStreamWriter writer, ProcessMethod bean) throws XMLStreamException
    {
        ns1Bindings.writeAbstractSWEIdentifiableTypeElements(writer, bean);
        int numItems;
        
        // algorithm
        numItems = bean.getAlgorithmList().size();
        for (int i = 0; i < numItems; i++)
        {
            AbstractAlgorithm item = bean.getAlgorithmList().get(i);
            writer.writeStartElement(NS_URI, "algorithm");
            this.writeAbstractAlgorithm(writer, item);
            writer.writeEndElement();
        }
    }
    
    
    /**
     * Reads attributes of AbstractAlgorithmType complex type
     */
    public void readAbstractAlgorithmTypeAttributes(Map<String, String> attrMap, AbstractAlgorithm bean) throws XMLStreamException
    {
        String val;
        
        // id
        val = attrMap.get("id");
        if (val != null)
            bean.setId(val);
    }
    
    
    /**
     * Writes attributes of AbstractAlgorithmType complex type
     */
    public void writeAbstractAlgorithmTypeAttributes(XMLStreamWriter writer, AbstractAlgorithm bean) throws XMLStreamException
    {
        
        // id
        if (bean.isSetId())
            writer.writeAttribute("id", getStringValue(bean.getId()));
    }
    
    
    /**
     * Read method for ModeType complex type
     */
    public Mode readModeType(XMLStreamReader reader) throws XMLStreamException
    {
        Mode bean = factory.newMode();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readModeTypeAttributes(attrMap, bean);
        
        reader.nextTag();
        this.readModeTypeElements(reader, bean);
        
        return bean;
    }
    
    
    /**
     * Reads attributes of ModeType complex type
     */
    public void readModeTypeAttributes(Map<String, String> attrMap, Mode bean) throws XMLStreamException
    {
        this.readDescribedObjectTypeAttributes(attrMap, bean);
        
    }
    
    
    /**
     * Reads elements of ModeType complex type
     */
    public void readModeTypeElements(XMLStreamReader reader, Mode bean) throws XMLStreamException
    {
        this.readDescribedObjectTypeElements(reader, bean);
        
        boolean found;
        
        // configuration
        found = checkElementName(reader, "configuration");
        if (found)
        {
            OgcProperty<Settings> configurationProp = bean.getConfigurationProperty();
            readPropertyAttributes(reader, configurationProp);
            
            reader.nextTag();
            if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
            {
                configurationProp.setValue(this.readSettings(reader));
                reader.nextTag(); // end property tag
            }
            
            reader.nextTag();
        }
    }
    
    
    /**
     * Write method for ModeType complex type
     */
    public void writeModeType(XMLStreamWriter writer, Mode bean) throws XMLStreamException
    {
        this.writeModeTypeAttributes(writer, bean);
        this.writeModeTypeElements(writer, bean);
    }
    
    
    /**
     * Writes attributes of ModeType complex type
     */
    public void writeModeTypeAttributes(XMLStreamWriter writer, Mode bean) throws XMLStreamException
    {
        this.writeDescribedObjectTypeAttributes(writer, bean);
    }
    
    
    /**
     * Writes elements of ModeType complex type
     */
    public void writeModeTypeElements(XMLStreamWriter writer, Mode bean) throws XMLStreamException
    {
        this.writeDescribedObjectTypeElements(writer, bean);
        
        // configuration
        writer.writeStartElement(NS_URI, "configuration");
        OgcProperty<Settings> configurationProp = bean.getConfigurationProperty();
        writePropertyAttributes(writer, configurationProp);
        if (configurationProp.hasValue() && !configurationProp.hasHref())
            this.writeSettings(writer, bean.getConfiguration());
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for ModeChoiceType complex type
     */
    public ModeChoice readModeChoiceType(XMLStreamReader reader) throws XMLStreamException
    {
        ModeChoice bean = factory.newModeChoice();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readModeChoiceTypeAttributes(attrMap, bean);
        
        reader.nextTag();
        this.readModeChoiceTypeElements(reader, bean);
        
        return bean;
    }
    
    
    /**
     * Reads attributes of ModeChoiceType complex type
     */
    public void readModeChoiceTypeAttributes(Map<String, String> attrMap, ModeChoice bean) throws XMLStreamException
    {
        this.readAbstractModesTypeAttributes(attrMap, bean);
        
    }
    
    
    /**
     * Reads elements of ModeChoiceType complex type
     */
    public void readModeChoiceTypeElements(XMLStreamReader reader, ModeChoice bean) throws XMLStreamException
    {
        this.readAbstractModesTypeElements(reader, bean);
        
        boolean found;
        
        // mode
        do
        {
            found = checkElementName(reader, "mode");
            if (found)
            {
                OgcProperty<Mode> modeProp = new OgcPropertyImpl<Mode>();
                readPropertyAttributes(reader, modeProp);
                
                reader.nextTag();
                if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
                {
                    modeProp.setValue(this.readMode(reader));
                    reader.nextTag(); // end property tag
                }
                
                bean.getModeList().add(modeProp);
                reader.nextTag();
            }
        }
        while (found);
    }
    
    
    /**
     * Write method for ModeChoiceType complex type
     */
    public void writeModeChoiceType(XMLStreamWriter writer, ModeChoice bean) throws XMLStreamException
    {
        this.writeModeChoiceTypeAttributes(writer, bean);
        this.writeModeChoiceTypeElements(writer, bean);
    }
    
    
    /**
     * Writes attributes of ModeChoiceType complex type
     */
    public void writeModeChoiceTypeAttributes(XMLStreamWriter writer, ModeChoice bean) throws XMLStreamException
    {
        this.writeAbstractModesTypeAttributes(writer, bean);
    }
    
    
    /**
     * Writes elements of ModeChoiceType complex type
     */
    public void writeModeChoiceTypeElements(XMLStreamWriter writer, ModeChoice bean) throws XMLStreamException
    {
        this.writeAbstractModesTypeElements(writer, bean);
        int numItems;
        
        // mode
        numItems = bean.getModeList().size();
        for (int i = 0; i < numItems; i++)
        {
            OgcProperty<Mode> item = bean.getModeList().getProperty(i);
            writer.writeStartElement(NS_URI, "mode");
            writePropertyAttributes(writer, item);
            if (item.hasValue() && !item.hasHref())
                this.writeMode(writer, item.getValue());
            writer.writeEndElement();
        }
    }
    
    
    /**
     * Read method for SettingsType complex type
     */
    public Settings readSettingsType(XMLStreamReader reader) throws XMLStreamException
    {
        Settings bean = factory.newSettings();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readSettingsTypeAttributes(attrMap, bean);
        
        reader.nextTag();
        this.readSettingsTypeElements(reader, bean);
        
        return bean;
    }
    
    
    /**
     * Reads attributes of SettingsType complex type
     */
    public void readSettingsTypeAttributes(Map<String, String> attrMap, Settings bean) throws XMLStreamException
    {
        this.readAbstractSettingsTypeAttributes(attrMap, bean);
        
    }
    
    
    /**
     * Reads elements of SettingsType complex type
     */
    public void readSettingsTypeElements(XMLStreamReader reader, Settings bean) throws XMLStreamException
    {
        this.readAbstractSettingsTypeElements(reader, bean);
        
        boolean found;
        
        // setValue
        do
        {
            found = checkElementName(reader, "setValue");
            if (found)
            {
                ValueSetting setValue = this.readValueSettingPropertyType(reader);
                if (setValue != null)
                    bean.addSetValue(setValue);
                reader.nextTag();
            }
        }
        while (found);
        
        // setArrayValues
        do
        {
            found = checkElementName(reader, "setArrayValues");
            if (found)
            {
                ArraySetting setArrayValues = this.readArraySettingPropertyType(reader);
                if (setArrayValues != null)
                    bean.addSetArrayValues(setArrayValues);                
                reader.nextTag();
            }
        }
        while (found);
        
        // setConstraint
        do
        {
            found = checkElementName(reader, "setConstraint");
            if (found)
            {
                ConstraintSetting setConstraint = this.readConstraintSettingPropertyType(reader);
                if (setConstraint != null)
                    bean.addSetConstraint(setConstraint);
                reader.nextTag();
            }
        }
        while (found);
        
        // setMode
        do
        {
            found = checkElementName(reader, "setMode");
            if (found)
            {
                ModeSetting setMode = this.readModeSettingPropertyType(reader);
                if (setMode != null)
                    bean.addSetMode(setMode);
                reader.nextTag();
            }
        }
        while (found);
        
        // setStatus
        do
        {
            found = checkElementName(reader, "setStatus");
            if (found)
            {
                StatusSetting setStatus = this.readStatusSettingPropertyType(reader);
                if (setStatus != null)
                    bean.addSetStatus(setStatus);
                reader.nextTag();
            }
        }
        while (found);
    }
    
    
    /**
     * Write method for SettingsType complex type
     */
    public void writeSettingsType(XMLStreamWriter writer, Settings bean) throws XMLStreamException
    {
        this.writeSettingsTypeAttributes(writer, bean);
        this.writeSettingsTypeElements(writer, bean);
    }
    
    
    /**
     * Writes attributes of SettingsType complex type
     */
    public void writeSettingsTypeAttributes(XMLStreamWriter writer, Settings bean) throws XMLStreamException
    {
        this.writeAbstractSettingsTypeAttributes(writer, bean);
    }
    
    
    /**
     * Writes elements of SettingsType complex type
     */
    public void writeSettingsTypeElements(XMLStreamWriter writer, Settings bean) throws XMLStreamException
    {
        this.writeAbstractSettingsTypeElements(writer, bean);
        int numItems;
        
        // setValue
        numItems = bean.getSetValueList().size();
        for (int i = 0; i < numItems; i++)
        {
            ValueSetting item = bean.getSetValueList().get(i);
            writer.writeStartElement(NS_URI, "setValue");
            this.writeValueSettingPropertyType(writer, item);
            writer.writeEndElement();
        }
        
        // setArrayValues
        numItems = bean.getSetArrayValuesList().size();
        for (int i = 0; i < numItems; i++)
        {
            ArraySetting item = bean.getSetArrayValuesList().get(i);
            writer.writeStartElement(NS_URI, "setArrayValues");
            this.writeArraySettingPropertyType(writer, item);
            writer.writeEndElement();
        }
        
        // setConstraint
        numItems = bean.getSetConstraintList().size();
        for (int i = 0; i < numItems; i++)
        {
            ConstraintSetting item = bean.getSetConstraintList().get(i);
            writer.writeStartElement(NS_URI, "setConstraint");
            this.writeConstraintSettingPropertyType(writer, item);
            writer.writeEndElement();
        }
        
        // setMode
        numItems = bean.getSetModeList().size();
        for (int i = 0; i < numItems; i++)
        {
            ModeSetting item = bean.getSetModeList().get(i);
            writer.writeStartElement(NS_URI, "setMode");
            this.writeModeSettingPropertyType(writer, item);
            writer.writeEndElement();
        }
        
        // setStatus
        numItems = bean.getSetStatusList().size();
        for (int i = 0; i < numItems; i++)
        {
            StatusSetting item = bean.getSetStatusList().get(i);
            writer.writeStartElement(NS_URI, "setStatus");
            this.writeStatusSettingPropertyType(writer, item);
            writer.writeEndElement();
        }
    }
    
    
    /**
     * Read method for ModeSettingPropertyType complex type
     */
    public ModeSetting readModeSettingPropertyType(XMLStreamReader reader) throws XMLStreamException
    {
        ModeSetting bean = factory.newModeSetting();
        String val;
        
        // ref
        Map<String, String> attrMap = collectAttributes(reader);
        val = attrMap.get("ref");
        if (val != null)
            bean.setRef(val);
        
        // inline value
        val = reader.getElementText();
        if (val != null)
            bean.setValue(trimStringValue(val));
        
        return bean;
    }
    
    
    /**
     * Write method for ModeSettingPropertyType complex type
     */
    public void writeModeSettingPropertyType(XMLStreamWriter writer, ModeSetting bean) throws XMLStreamException
    {
        // ref
        writer.writeAttribute("ref", getStringValue(bean.getRef()));
        
        // inline value
        writer.writeCharacters(getStringValue(bean.getValue()));
    }
    
    
    /**
     * Read method for ValueSettingPropertyType complex type
     */
    public ValueSetting readValueSettingPropertyType(XMLStreamReader reader) throws XMLStreamException
    {
        ValueSetting bean = factory.newValueSetting();
        String val;
        
        // ref        
        Map<String, String> attrMap = collectAttributes(reader);
        val = attrMap.get("ref");
        if (val != null)
            bean.setRef(val);
        
        // inline value
        val = reader.getElementText();
        if (val != null)
            bean.setValue(trimStringValue(val));
        
        return bean;
    }
    
    
    /**
     * Write method for ValueSettingPropertyType complex type
     */
    public void writeValueSettingPropertyType(XMLStreamWriter writer, ValueSetting bean) throws XMLStreamException
    {
        // ref
        writer.writeAttribute("ref", getStringValue(bean.getRef()));
        
        // inline value
        writer.writeCharacters(getStringValue(bean.getValue()));
    }
    
    
    /**
     * Read method for ConstraintSettingPropertyType complex type
     */
    public ConstraintSetting readConstraintSettingPropertyType(XMLStreamReader reader) throws XMLStreamException
    {
        ConstraintSetting bean = factory.newConstraintSetting();
        String val;
        
        // ref        
        Map<String, String> attrMap = collectAttributes(reader);
        val = attrMap.get("ref");
        if (val != null)
            bean.setRef(val);
        
        // constraint object
        reader.nextTag();
        String localName = reader.getName().getLocalPart();
        
        if ("AllowedTimes".equals(localName))
        {
            AllowedTimes constraint = ns1Bindings.readAllowedTimes(reader);
            bean.setValueAsAllowedTimes(constraint);
        }
        else if ("AllowedTokens".equals(localName))
        {
            AllowedTokens constraint = ns1Bindings.readAllowedTokens(reader);
            bean.setValueAsAllowedTokens(constraint);
        }
        else if ("AllowedValues".equals(localName))
        {
            AllowedValues constraint = ns1Bindings.readAllowedValues(reader);
            bean.setValueAsAllowedValues(constraint);
        }
        else
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        reader.nextTag();
        
        return bean;
    }
    
    
    /**
     * Write method for ConstraintSettingPropertyType complex type
     */
    public void writeConstraintSettingPropertyType(XMLStreamWriter writer, ConstraintSetting bean) throws XMLStreamException
    {
        // ref
        writer.writeAttribute("ref", getStringValue(bean.getRef()));
        
        // constraint object value
        DataConstraint constraint = bean.getValue();
        if (constraint instanceof AllowedTimes)
            ns1Bindings.writeAllowedTimes(writer, (AllowedTimes)constraint);
        else if (constraint instanceof AllowedTokens)
            ns1Bindings.writeAllowedTokens(writer, (AllowedTokens)constraint);
        else if (constraint instanceof AllowedValues)
            ns1Bindings.writeAllowedValues(writer, (AllowedValues)constraint, false);
    }

    
    /**
     * Read method for ValueSettingPropertyType complex type
     */
    public ArraySetting readArraySettingPropertyType(XMLStreamReader reader) throws XMLStreamException
    {
        ArraySetting bean = factory.newArraySetting();
        String val;
        boolean  found;
        
        // ref        
        Map<String, String> attrMap = collectAttributes(reader);
        val = attrMap.get("ref");
        if (val != null)
            bean.setRef(val);
        
        // content element
        reader.nextTag();
        reader.nextTag();
        
        // encoding
        found = checkElementName(reader, "encoding");
        if (found)
        {
            reader.nextTag();
            
            DataEncoding encoding = ns1Bindings.readAbstractEncoding(reader);
            if (encoding != null)
                bean.setEncoding(encoding);
            
            reader.nextTag();
            reader.nextTag();
        }
        
        // values
        found = checkElementName(reader, "value");
        if (found)
        {
            // TODO add proper support for delayed decoding of array setting encoded values
            EncodedValues values = ns1Bindings.readEncodedValuesPropertyType(reader, null, bean.getEncoding());
            if (values != null)
                bean.setValue(values);
            
            reader.nextTag();
        }
        
        reader.nextTag();
        return bean;
    }
    
    
    /**
     * Write method for ValueSettingPropertyType complex type
     */
    public void writeArraySettingPropertyType(XMLStreamWriter writer, ArraySetting bean) throws XMLStreamException
    {
        // ref
        writer.writeAttribute("ref", getStringValue(bean.getRef()));
        
        // content
        writer.writeStartElement(NS_URI, "ArrayValues");
        
        // encoding
        writer.writeStartElement(NS_URI, "encoding");
        ns1Bindings.writeAbstractEncoding(writer, bean.getEncoding());
        writer.writeEndElement();
        
        // inline value
        writer.writeStartElement(NS_URI, "value");
        ns1Bindings.writeEncodedValuesPropertyType(writer, bean.getReferencedObject(), bean.getEncoding(), bean.getValue());
        writer.writeEndElement();
        
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for PhysicalSystemType complex type
     */
    public PhysicalSystem readPhysicalSystemType(XMLStreamReader reader) throws XMLStreamException
    {
        PhysicalSystem bean = factory.newPhysicalSystem();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readPhysicalSystemTypeAttributes(attrMap, bean);
        
        reader.nextTag();
        this.readPhysicalSystemTypeElements(reader, bean);
        
        return bean;
    }
    
    
    /**
     * Reads attributes of PhysicalSystemType complex type
     */
    public void readPhysicalSystemTypeAttributes(Map<String, String> attrMap, PhysicalSystem bean) throws XMLStreamException
    {
        this.readAbstractPhysicalProcessTypeAttributes(attrMap, bean);
        
    }
    
    
    /**
     * Reads elements of PhysicalSystemType complex type
     */
    public void readPhysicalSystemTypeElements(XMLStreamReader reader, PhysicalSystem bean) throws XMLStreamException
    {
        this.readAbstractPhysicalProcessTypeElements(reader, bean);
        readComponentsAndConnections(reader, bean);
    }
    
    
    /**
     * Write method for PhysicalSystemType complex type
     */
    public void writePhysicalSystemType(XMLStreamWriter writer, PhysicalSystem bean) throws XMLStreamException
    {
        this.writePhysicalSystemTypeAttributes(writer, bean);
        this.writePhysicalSystemTypeElements(writer, bean);
    }
    
    
    /**
     * Writes attributes of PhysicalSystemType complex type
     */
    public void writePhysicalSystemTypeAttributes(XMLStreamWriter writer, PhysicalSystem bean) throws XMLStreamException
    {
        this.writeAbstractPhysicalProcessTypeAttributes(writer, bean);
    }
    
    
    /**
     * Writes elements of PhysicalSystemType complex type
     */
    public void writePhysicalSystemTypeElements(XMLStreamWriter writer, PhysicalSystem bean) throws XMLStreamException
    {
        this.writeAbstractPhysicalProcessTypeElements(writer, bean);
        this.writeComponentsAndConnections(writer, bean);
    }
    
    
    /**
     * Read method for PhysicalComponentType complex type
     */
    public PhysicalComponent readPhysicalComponentType(XMLStreamReader reader) throws XMLStreamException
    {
        PhysicalComponent bean = factory.newPhysicalComponent();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readPhysicalComponentTypeAttributes(attrMap, bean);
        
        reader.nextTag();
        this.readPhysicalComponentTypeElements(reader, bean);
        
        return bean;
    }
    
    
    /**
     * Reads attributes of PhysicalComponentType complex type
     */
    public void readPhysicalComponentTypeAttributes(Map<String, String> attrMap, PhysicalComponent bean) throws XMLStreamException
    {
        this.readAbstractPhysicalProcessTypeAttributes(attrMap, bean);
        
    }
    
    
    /**
     * Reads elements of PhysicalComponentType complex type
     */
    public void readPhysicalComponentTypeElements(XMLStreamReader reader, PhysicalComponent bean) throws XMLStreamException
    {
        this.readAbstractPhysicalProcessTypeElements(reader, bean);
        
        boolean found;
        
        // method
        found = checkElementName(reader, "method");
        if (found)
        {
            OgcProperty<ProcessMethod> methodProp = bean.getMethodProperty();
            readPropertyAttributes(reader, methodProp);
            
            reader.nextTag();
            if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
            {
                methodProp.setValue(this.readProcessMethod(reader));
                reader.nextTag(); // end property tag
            }
            
            reader.nextTag();
        }
    }
    
    
    /**
     * Write method for PhysicalComponentType complex type
     */
    public void writePhysicalComponentType(XMLStreamWriter writer, PhysicalComponent bean) throws XMLStreamException
    {
        this.writePhysicalComponentTypeAttributes(writer, bean);
        this.writePhysicalComponentTypeElements(writer, bean);
    }
    
    
    /**
     * Writes attributes of PhysicalComponentType complex type
     */
    public void writePhysicalComponentTypeAttributes(XMLStreamWriter writer, PhysicalComponent bean) throws XMLStreamException
    {
        this.writeAbstractPhysicalProcessTypeAttributes(writer, bean);
    }
    
    
    /**
     * Writes elements of PhysicalComponentType complex type
     */
    public void writePhysicalComponentTypeElements(XMLStreamWriter writer, PhysicalComponent bean) throws XMLStreamException
    {
        this.writeAbstractPhysicalProcessTypeElements(writer, bean);
        
        // method
        if (bean.isSetMethod())
        {
            writer.writeStartElement(NS_URI, "method");
            OgcProperty<ProcessMethod> methodProp = bean.getMethodProperty();
            writePropertyAttributes(writer, methodProp);
            if (methodProp.hasValue() && !methodProp.hasHref())
                this.writeProcessMethod(writer, bean.getMethod());
            writer.writeEndElement();
        }
    }
    
    
    /**
     * Reads attributes of AbstractPhysicalProcessType complex type
     */
    public void readAbstractPhysicalProcessTypeAttributes(Map<String, String> attrMap, AbstractPhysicalProcess bean) throws XMLStreamException
    {
        this.readAbstractProcessTypeAttributes(attrMap, bean);
        
    }
    
    
    /**
     * Reads elements of AbstractPhysicalProcessType complex type
     */
    public void readAbstractPhysicalProcessTypeElements(XMLStreamReader reader, AbstractPhysicalProcess bean) throws XMLStreamException
    {
        this.readAbstractProcessTypeElements(reader, bean);
        
        boolean found;
        
        // attachedTo
        found = checkElementName(reader, "attachedTo");
        if (found)
        {
            bean.setAttachedTo(ns2Bindings.readReferenceType(reader));
            reader.nextTag(); // end property tag
            reader.nextTag();
        }
        
        // localReferenceFrame
        do
        {
            found = checkElementName(reader, "localReferenceFrame");
            if (found)
            {
                reader.nextTag();
                if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
                {
                    bean.addLocalReferenceFrame(this.readSpatialFrame(reader));
                    reader.nextTag(); // end property tag
                }
                
                reader.nextTag();
            }
        }
        while (found);
        
        // localTimeFrame
        do
        {
            found = checkElementName(reader, "localTimeFrame");
            if (found)
            {
                reader.nextTag();
                if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
                {
                    bean.addLocalTimeFrame(this.readTemporalFrame(reader));
                    reader.nextTag(); // end property tag
                }
                
                reader.nextTag();
            }
        }
        while (found);
        
        // position
        do
        {
            found = checkElementName(reader, "position");
            if (found)
            {
                OgcProperty<Serializable> positionProp = new OgcPropertyImpl<Serializable>();
                readPropertyAttributes(reader, positionProp);
                
                reader.nextTag();
                if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
                {
                    String localName = reader.getName().getLocalPart();
                    
                    if ("Text".equals(localName))
                    {
                      Text position = ns1Bindings.readText(reader);
                      positionProp.setValue(position);
                    }
                    else if ("Point".equals(localName))
                    {
                      Point position = ns2Bindings.readPoint(reader);
                      positionProp.setValue(position);
                    }
                    else if ("Vector".equals(localName))
                    {
                      Vector position = ns1Bindings.readVector(reader);
                      positionProp.setValue(position);
                    }
                    else if ("DataRecord".equals(localName))
                    {
                      DataRecord position = ns1Bindings.readDataRecord(reader);
                      positionProp.setValue(position);
                    }
                    else if ("DataArray".equals(localName))
                    {
                      DataArray position = ns1Bindings.readDataArray(reader);
                      positionProp.setValue(position);
                    }
                    else if ("AbstractProcess".equals(localName))
                    {
                      AbstractProcess position = this.readAbstractProcess(reader);
                      positionProp.setValue(position);
                    }
                    else
                      throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
                    
                    reader.nextTag(); // end property tag
                }
                
                bean.getPositionList().add(positionProp);
                reader.nextTag();
            }
        }
        while (found);
        
        // timePosition
        do
        {
            found = checkElementName(reader, "timePosition");
            if (found)
            {
                OgcProperty<Time> timePositionProp = new OgcPropertyImpl<Time>();
                readPropertyAttributes(reader, timePositionProp);
                
                reader.nextTag();
                if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
                {
                    timePositionProp.setValue(ns1Bindings.readTime(reader));
                    reader.nextTag(); // end property tag
                }
                
                bean.getTimePositionList().add(timePositionProp);
                reader.nextTag();
            }
        }
        while (found);
    }
    
    
    /**
     * Writes attributes of AbstractPhysicalProcessType complex type
     */
    public void writeAbstractPhysicalProcessTypeAttributes(XMLStreamWriter writer, AbstractPhysicalProcess bean) throws XMLStreamException
    {
        this.writeAbstractProcessTypeAttributes(writer, bean);
    }
    
    
    /**
     * Writes elements of AbstractPhysicalProcessType complex type
     */
    public void writeAbstractPhysicalProcessTypeElements(XMLStreamWriter writer, AbstractPhysicalProcess bean) throws XMLStreamException
    {
        this.writeAbstractProcessTypeElements(writer, bean);
        int numItems;
        
        // attachedTo
        if (bean.isSetAttachedTo())
        {
            writer.writeStartElement(NS_URI, "attachedTo");
            ns2Bindings.writeReferenceType(writer, bean.getAttachedTo());
            writer.writeEndElement();
        }
        
        // localReferenceFrame
        numItems = bean.getLocalReferenceFrameList().size();
        for (int i = 0; i < numItems; i++)
        {
            SpatialFrame item = bean.getLocalReferenceFrameList().get(i);
            writer.writeStartElement(NS_URI, "localReferenceFrame");
            this.writeSpatialFrame(writer, item);
            writer.writeEndElement();
        }
        
        // localTimeFrame
        numItems = bean.getLocalTimeFrameList().size();
        for (int i = 0; i < numItems; i++)
        {
            TemporalFrame item = bean.getLocalTimeFrameList().get(i);
            writer.writeStartElement(NS_URI, "localTimeFrame");
            this.writeTemporalFrame(writer, item);
            writer.writeEndElement();
        }
        
        // position
        numItems = bean.getPositionList().size();
        for (int i = 0; i < numItems; i++)
        {
            OgcProperty<Serializable> item = bean.getPositionList().getProperty(i);
            writer.writeStartElement(NS_URI, "position");
            writePropertyAttributes(writer, item);
            
            if (item.hasValue() && !item.hasHref())
            {
                if (item.getValue() instanceof Text)
                    ns1Bindings.writeText(writer, (Text)item.getValue(), true);
                else if (item.getValue() instanceof Point)
                    ns2Bindings.writePoint(writer, (Point)item.getValue());
                else if (item.getValue() instanceof Vector)
                    ns1Bindings.writeVector(writer, (Vector)item.getValue(), true);
                else if (item.getValue() instanceof DataRecord)
                    ns1Bindings.writeDataRecord(writer, (DataRecord)item.getValue(), true);
                else if (item.getValue() instanceof DataArray)
                    ns1Bindings.writeDataArray(writer, (DataArray)item.getValue(), true);
                else if (item.getValue() instanceof AbstractProcess)
                    this.writeAbstractProcess(writer, (AbstractProcess)item.getValue());
            }
            
            writer.writeEndElement();
        }
        
        // timePosition
        numItems = bean.getTimePositionList().size();
        for (int i = 0; i < numItems; i++)
        {
            OgcProperty<Time> item = bean.getTimePositionList().getProperty(i);
            writer.writeStartElement(NS_URI, "timePosition");
            writePropertyAttributes(writer, item);
            if (item.hasValue() && !item.hasHref())
                ns1Bindings.writeTime(writer, item.getValue(), true);
            writer.writeEndElement();
        }
    }
    
    
    /**
     * Read method for TemporalFrameType complex type
     */
    public TemporalFrame readTemporalFrameType(XMLStreamReader reader) throws XMLStreamException
    {
        TemporalFrame bean = factory.newTemporalFrame();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readTemporalFrameTypeAttributes(attrMap, bean);
        
        reader.nextTag();
        this.readTemporalFrameTypeElements(reader, bean);
        
        return bean;
    }
    
    
    /**
     * Reads attributes of TemporalFrameType complex type
     */
    public void readTemporalFrameTypeAttributes(Map<String, String> attrMap, TemporalFrame bean) throws XMLStreamException
    {
        ns1Bindings.readAbstractSWEIdentifiableTypeAttributes(attrMap, bean);
        
    }
    
    
    /**
     * Reads elements of TemporalFrameType complex type
     */
    public void readTemporalFrameTypeElements(XMLStreamReader reader, TemporalFrame bean) throws XMLStreamException
    {
        ns1Bindings.readAbstractSWEIdentifiableTypeElements(reader, bean);
        
        boolean found;
        String val;
        
        // origin
        found = checkElementName(reader, "origin");
        if (found)
        {
            val = reader.getElementText();
            if (val != null)
                bean.setOrigin(trimStringValue(val));
            reader.nextTag();
        }
    }
    
    
    /**
     * Write method for TemporalFrameType complex type
     */
    public void writeTemporalFrameType(XMLStreamWriter writer, TemporalFrame bean) throws XMLStreamException
    {
        this.writeTemporalFrameTypeAttributes(writer, bean);
        this.writeTemporalFrameTypeElements(writer, bean);
    }
    
    
    /**
     * Writes attributes of TemporalFrameType complex type
     */
    public void writeTemporalFrameTypeAttributes(XMLStreamWriter writer, TemporalFrame bean) throws XMLStreamException
    {
        ns1Bindings.writeAbstractSWEIdentifiableTypeAttributes(writer, bean);
    }
    
    
    /**
     * Writes elements of TemporalFrameType complex type
     */
    public void writeTemporalFrameTypeElements(XMLStreamWriter writer, TemporalFrame bean) throws XMLStreamException
    {
        ns1Bindings.writeAbstractSWEIdentifiableTypeElements(writer, bean);
        
        // origin
        writer.writeStartElement(NS_URI, "origin");
        writer.writeCharacters(bean.getOrigin());
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for SpatialFrameType complex type
     */
    public SpatialFrame readSpatialFrameType(XMLStreamReader reader) throws XMLStreamException
    {
        SpatialFrame bean = factory.newSpatialFrame();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readSpatialFrameTypeAttributes(attrMap, bean);
        
        reader.nextTag();
        this.readSpatialFrameTypeElements(reader, bean);
        
        return bean;
    }
    
    
    /**
     * Reads attributes of SpatialFrameType complex type
     */
    public void readSpatialFrameTypeAttributes(Map<String, String> attrMap, SpatialFrame bean) throws XMLStreamException
    {
        ns1Bindings.readAbstractSWEIdentifiableTypeAttributes(attrMap, bean);
        
    }
    
    
    /**
     * Reads elements of SpatialFrameType complex type
     */
    public void readSpatialFrameTypeElements(XMLStreamReader reader, SpatialFrame bean) throws XMLStreamException
    {
        ns1Bindings.readAbstractSWEIdentifiableTypeElements(reader, bean);
        
        boolean found;
        String val;
        
        // origin
        found = checkElementName(reader, "origin");
        if (found)
        {
            val = reader.getElementText();
            if (val != null)
                bean.setOrigin(trimStringValue(val));
            reader.nextTag();
        }
        
     // axis
        do
        {
            found = checkElementName(reader, "axis");
            if (found)
            {
                OgcProperty<String> axisProp = new OgcPropertyImpl<String>();
                readPropertyAttributes(reader, axisProp);
                axisProp.setValue(this.readAxis(reader));
                bean.getAxisList().add(axisProp);                
                reader.nextTag();
            }
        }
        while (found);
    }
    
    
    /**
     * Write method for SpatialFrameType complex type
     */
    public void writeSpatialFrameType(XMLStreamWriter writer, SpatialFrame bean) throws XMLStreamException
    {
        this.writeSpatialFrameTypeAttributes(writer, bean);
        this.writeSpatialFrameTypeElements(writer, bean);
    }
    
    
    /**
     * Writes attributes of SpatialFrameType complex type
     */
    public void writeSpatialFrameTypeAttributes(XMLStreamWriter writer, SpatialFrame bean) throws XMLStreamException
    {
        ns1Bindings.writeAbstractSWEIdentifiableTypeAttributes(writer, bean);
    }
    
    
    /**
     * Writes elements of SpatialFrameType complex type
     */
    public void writeSpatialFrameTypeElements(XMLStreamWriter writer, SpatialFrame bean) throws XMLStreamException
    {
        ns1Bindings.writeAbstractSWEIdentifiableTypeElements(writer, bean);
        int numItems;
        
        // origin
        writer.writeStartElement(NS_URI, "origin");
        writer.writeCharacters(bean.getOrigin());
        writer.writeEndElement();
        
        // axis
        numItems = bean.getAxisList().size();
        for (int i = 0; i < numItems; i++)
        {
            OgcProperty<String> item = bean.getAxisList().getProperty(i);
            writer.writeStartElement(NS_URI, "axis");
            writePropertyAttributes(writer, item);
            if (item.hasValue() && !item.hasHref())
                this.writeAxis(writer, item.getValue());
            writer.writeEndElement();
        }
    }
    
    
    /**
     * Read method for AggregateProcessType complex type
     */
    public AggregateProcess readAggregateProcessType(XMLStreamReader reader) throws XMLStreamException
    {
        AggregateProcess bean = factory.newAggregateProcess();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readAggregateProcessTypeAttributes(attrMap, bean);
        
        reader.nextTag();
        this.readAggregateProcessTypeElements(reader, bean);
        
        return bean;
    }
    
    
    /**
     * Reads attributes of AggregateProcessType complex type
     */
    public void readAggregateProcessTypeAttributes(Map<String, String> attrMap, AggregateProcess bean) throws XMLStreamException
    {
        this.readAbstractProcessTypeAttributes(attrMap, bean);
        
    }
    
    
    /**
     * Reads elements of AggregateProcessType complex type
     */
    public void readAggregateProcessTypeElements(XMLStreamReader reader, AggregateProcess bean) throws XMLStreamException
    {
        this.readAbstractProcessTypeElements(reader, bean);
        readComponentsAndConnections(reader, bean);  
    }
    
    
    protected void readComponentsAndConnections(XMLStreamReader reader, AggregateProcess bean) throws XMLStreamException
    {
        boolean found;
        
        // components
        found = checkElementName(reader, "components");
        if (found)
        {
            reader.nextTag(); // skip ComponentList element
            reader.nextTag();
            
            if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
            {
                // component
                do
                {
                    found = checkElementName(reader, "component");
                    if (found)
                    {
                        OgcProperty<AbstractProcess> componentProp = new OgcPropertyImpl<AbstractProcess>();
                        readPropertyAttributes(reader, componentProp);
                        
                        reader.nextTag();
                        if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
                        {
                            componentProp.setValue(this.readAbstractProcess(reader));
                            reader.nextTag(); // end property tag
                        }
                        
                        bean.getComponentList().add(componentProp);
                        reader.nextTag();
                    }
                }
                while (found);
                reader.nextTag(); // end property tag
            }
            
            reader.nextTag();
        }
        
        // connections
        found = checkElementName(reader, "connections");
        if (found)
        {
            reader.nextTag(); // skip ConnectionList element
            reader.nextTag();
            
            if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
            {
                // connection
                do
                {
                    found = checkElementName(reader, "connection");
                    if (found)
                    {
                        reader.nextTag();
                        if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
                        {
                            bean.addConnection(this.readLink(reader));
                            reader.nextTag(); // end property tag
                        }
                        
                        reader.nextTag();
                    }
                }
                while (found);
                reader.nextTag(); // end property tag
            }
            
            reader.nextTag();
        }
    }
    
    
    /**
     * Write method for AggregateProcessType complex type
     */
    public void writeAggregateProcessType(XMLStreamWriter writer, AggregateProcess bean) throws XMLStreamException
    {
        this.writeAggregateProcessTypeAttributes(writer, bean);
        this.writeAggregateProcessTypeElements(writer, bean);
    }
    
    
    /**
     * Writes attributes of AggregateProcessType complex type
     */
    public void writeAggregateProcessTypeAttributes(XMLStreamWriter writer, AggregateProcess bean) throws XMLStreamException
    {
        this.writeAbstractProcessTypeAttributes(writer, bean);
    }
    
    
    /**
     * Writes elements of AggregateProcessType complex type
     */
    public void writeAggregateProcessTypeElements(XMLStreamWriter writer, AggregateProcess bean) throws XMLStreamException
    {
        this.writeAbstractProcessTypeElements(writer, bean);
        this.writeComponentsAndConnections(writer, bean);
    }
    
    
    protected void writeComponentsAndConnections(XMLStreamWriter writer, AggregateProcess bean) throws XMLStreamException
    {
        int numItems;
        
        // components
        if (bean.getNumComponents() > 0)
        {
            writer.writeStartElement(NS_URI, "components");
            writer.writeStartElement(NS_URI, "ComponentList");
            
            // component
            numItems = bean.getComponentList().size();
            for (int i = 0; i < numItems; i++)
            {
                OgcProperty<AbstractProcess> item = bean.getComponentList().getProperty(i);
                writer.writeStartElement(NS_URI, "component");
                writePropertyAttributes(writer, item);
                if (item.hasValue() && !item.hasHref())
                    this.writeAbstractProcess(writer, item.getValue());
                writer.writeEndElement();
            }
            
            writer.writeEndElement();
            writer.writeEndElement();
        }

        // connections
        if (bean.getNumComponents() > 0)
        {
            writer.writeStartElement(NS_URI, "connections");
            writer.writeStartElement(NS_URI, "ConnectionList");
            
            // connection
            numItems = bean.getConnectionList().size();
            for (int i = 0; i < numItems; i++)
            {
                Link item = bean.getConnectionList().get(i);
                writer.writeStartElement(NS_URI, "connection");
                this.writeLink(writer, item);
                writer.writeEndElement();
            }
            
            writer.writeEndElement();
            writer.writeEndElement();
        }
    }
    
    
    /**
     * Read method for LinkType complex type
     */
    public Link readLinkType(XMLStreamReader reader) throws XMLStreamException
    {
        Link bean = factory.newLink();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readLinkTypeAttributes(attrMap, bean);
        
        reader.nextTag();
        this.readLinkTypeElements(reader, bean);
        
        return bean;
    }
    
    
    /**
     * Reads attributes of LinkType complex type
     */
    public void readLinkTypeAttributes(Map<String, String> attrMap, Link bean) throws XMLStreamException
    {
        String val;
        
        // id
        val = attrMap.get("id");
        if (val != null)
            bean.setId(val);
    }
    
    
    /**
     * Reads elements of LinkType complex type
     */
    public void readLinkTypeElements(XMLStreamReader reader, Link bean) throws XMLStreamException
    {
        boolean found;
        
        // source
        found = checkElementName(reader, "source");
        if (found)
        {
            bean.setSource(this.readDataComponentRefPropertyType(reader));
            reader.nextTag(); // end property tag
            reader.nextTag();
        }
        
        // destination
        found = checkElementName(reader, "destination");
        if (found)
        {
            bean.setDestination(this.readDataComponentRefPropertyType(reader));
            reader.nextTag(); // end property tag
            reader.nextTag();
        }
    }
    
    
    /**
     * Write method for LinkType complex type
     */
    public void writeLinkType(XMLStreamWriter writer, Link bean) throws XMLStreamException
    {
        this.writeLinkTypeAttributes(writer, bean);
        this.writeLinkTypeElements(writer, bean);
    }
    
    
    /**
     * Writes attributes of LinkType complex type
     */
    public void writeLinkTypeAttributes(XMLStreamWriter writer, Link bean) throws XMLStreamException
    {
        
        // id
        if (bean.isSetId())
            writer.writeAttribute("id", getStringValue(bean.getId()));
    }
    
    
    /**
     * Writes elements of LinkType complex type
     */
    public void writeLinkTypeElements(XMLStreamWriter writer, Link bean) throws XMLStreamException
    {        
        // source
        writer.writeStartElement(NS_URI, "source");
        this.writeDataComponentRefPropertyType(writer, bean.getSource());
        writer.writeEndElement();
        
        // destination
        writer.writeStartElement(NS_URI, "destination");
        this.writeDataComponentRefPropertyType(writer, bean.getDestination());
        writer.writeEndElement();
    }
    
    
    /**
     * Reads attributes of DescribedObjectType complex type
     */
    public void readDescribedObjectTypeAttributes(Map<String, String> attrMap, DescribedObject bean) throws XMLStreamException
    {
        ns2Bindings.readAbstractFeatureTypeAttributes(attrMap, bean);
        
        String val;
        
        // lang
        val = attrMap.get("lang");
        if (val != null)
            bean.setLang(val);
    }
    
    
    /**
     * Reads elements of DescribedObjectType complex type
     */
    public void readDescribedObjectTypeElements(XMLStreamReader reader, DescribedObject bean) throws XMLStreamException
    {
        ns2Bindings.readAbstractFeatureTypeElements(reader, bean);
        
        boolean found;
        
        // extension
        do
        {
            found = checkElementName(reader, "extension");
            if (found)
            {
                reader.nextTag();
                if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
                {
                    Object extension = this.readExtension(reader);
                    if (extension != null)
                        bean.addExtension(extension);
                }
                
                reader.nextTag();
            }
        }
        while (found);
        
        // keywords
        do
        {
            found = checkElementName(reader, "keywords");
            if (found)
            {
                OgcProperty<KeywordList> keywordsProp = new OgcPropertyImpl<KeywordList>();
                readPropertyAttributes(reader, keywordsProp);
                
                reader.nextTag();
                if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
                {
                    keywordsProp.setValue(this.readKeywordList(reader));
                    reader.nextTag(); // end property tag
                }
                
                bean.getKeywordsList().add(keywordsProp);
                reader.nextTag();
            }
        }
        while (found);
        
        // identification
        do
        {
            found = checkElementName(reader, "identification");
            if (found)
            {
                OgcProperty<IdentifierList> identificationProp = new OgcPropertyImpl<IdentifierList>();
                readPropertyAttributes(reader, identificationProp);
                
                reader.nextTag();
                if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
                {
                    identificationProp.setValue(this.readIdentifierList(reader));
                    reader.nextTag(); // end property tag
                }
                
                bean.getIdentificationList().add(identificationProp);
                reader.nextTag();
            }
        }
        while (found);
        
        // classification
        do
        {
            found = checkElementName(reader, "classification");
            if (found)
            {
                OgcProperty<ClassifierList> classificationProp = new OgcPropertyImpl<ClassifierList>();
                readPropertyAttributes(reader, classificationProp);
                
                reader.nextTag();
                if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
                {
                    classificationProp.setValue(this.readClassifierList(reader));
                    reader.nextTag(); // end property tag
                }
                
                bean.getClassificationList().add(classificationProp);
                reader.nextTag();
            }
        }
        while (found);
        
        // validTime
        do
        {
            found = checkElementName(reader, "validTime");
            if (found)
            {
                reader.nextTag();
                if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
                {
                    String localName = reader.getName().getLocalPart();
                    
                    if ("TimePeriod".equals(localName))
                    {
                      TimePeriod validTime = ns2Bindings.readTimePeriod(reader);
                      bean.addValidTimeAsTimePeriod(validTime);
                    }
                    else if ("TimeInstant".equals(localName))
                    {
                      TimeInstant validTime = ns2Bindings.readTimeInstant(reader);
                      bean.addValidTimeAsTimeInstant(validTime);
                    }
                    else
                      throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
                    
                    reader.nextTag(); // end property tag
                }

                reader.nextTag();
            }
        }
        while (found);
        
        // securityConstraints
        do
        {
            found = checkElementName(reader, "securityConstraints");
            if (found)
            {
                reader.nextTag();
                if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
                {
                    Object sec = this.readSecurityConstraints(reader);
                    if (sec != null)
                        bean.addSecurityConstraints(sec);
                }
                
                reader.nextTag();
            }
        }
        while (found);
        
        // legalConstraints
        do
        {
            found = checkElementName(reader, "legalConstraints");
            if (found)
            {
                OgcProperty<MDLegalConstraints> legalConstraintsProp = new OgcPropertyImpl<MDLegalConstraints>();
                readPropertyAttributes(reader, legalConstraintsProp);
                
                reader.nextTag();
                if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
                {
                    legalConstraintsProp.setValue(ns3Bindings.readMDLegalConstraints(reader));
                    reader.nextTag(); // end property tag
                }
                
                bean.getLegalConstraintsList().add(legalConstraintsProp);
                reader.nextTag();
            }
        }
        while (found);
        
        // characteristics
        do
        {
            found = checkElementName(reader, "characteristics");
            if (found)
            {
                OgcProperty<CharacteristicList> characteristicsProp = new OgcPropertyImpl<CharacteristicList>();
                readPropertyAttributes(reader, characteristicsProp);
                
                reader.nextTag();
                if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
                {
                    characteristicsProp.setValue(this.readCharacteristicList(reader));
                    reader.nextTag(); // end property tag
                }
                
                bean.getCharacteristicsList().add(characteristicsProp);
                reader.nextTag();
            }
        }
        while (found);
        
        // capabilities
        do
        {
            found = checkElementName(reader, "capabilities");
            if (found)
            {
                OgcProperty<CapabilityList> capabilitiesProp = new OgcPropertyImpl<CapabilityList>();
                readPropertyAttributes(reader, capabilitiesProp);
                
                reader.nextTag();
                if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
                {
                    capabilitiesProp.setValue(this.readCapabilityList(reader));
                    reader.nextTag(); // end property tag
                }
                
                bean.getCapabilitiesList().add(capabilitiesProp);
                reader.nextTag();
            }
        }
        while (found);
        
        // contacts
        do
        {
            found = checkElementName(reader, "contacts");
            if (found)
            {
                OgcProperty<ContactList> contactsProp = new OgcPropertyImpl<ContactList>();
                readPropertyAttributes(reader, contactsProp);
                
                reader.nextTag();
                if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
                {
                    contactsProp.setValue(this.readContactList(reader));
                    reader.nextTag(); // end property tag
                }
                
                bean.getContactsList().add(contactsProp);
                reader.nextTag();
            }
        }
        while (found);
        
        // documentation
        do
        {
            found = checkElementName(reader, "documentation");
            if (found)
            {
                OgcProperty<DocumentList> documentationProp = new OgcPropertyImpl<DocumentList>();
                readPropertyAttributes(reader, documentationProp);
                
                reader.nextTag();
                if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
                {
                    documentationProp.setValue(this.readDocumentList(reader));
                    reader.nextTag(); // end property tag
                }
                
                bean.getDocumentationList().add(documentationProp);
                reader.nextTag();
            }
        }
        while (found);
        
        // history
        do
        {
            found = checkElementName(reader, "history");
            if (found)
            {
                OgcProperty<EventList> historyProp = new OgcPropertyImpl<EventList>();
                readPropertyAttributes(reader, historyProp);
                
                reader.nextTag();
                if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
                {
                    historyProp.setValue(this.readEventList(reader));
                    reader.nextTag(); // end property tag
                }
                
                bean.getHistoryList().add(historyProp);
                reader.nextTag();
            }
        }
        while (found);
    }
    
    
    /**
     * Writes attributes of DescribedObjectType complex type
     */
    public void writeDescribedObjectTypeAttributes(XMLStreamWriter writer, DescribedObject bean) throws XMLStreamException
    {
        ns2Bindings.writeAbstractFeatureTypeAttributes(writer, bean);
        
        // lang
        if (bean.isSetLang())
            writer.writeAttribute("xml:lang", getStringValue(bean.getLang()));
    }
    
    
    /**
     * Writes elements of DescribedObjectType complex type
     */
    public void writeDescribedObjectTypeElements(XMLStreamWriter writer, DescribedObject bean) throws XMLStreamException
    {
        ns2Bindings.writeAbstractFeatureTypeElements(writer, bean);
        int numItems;
        
        // extension
        numItems = bean.getExtensionList().size();
        for (int i = 0; i < numItems; i++)
        {
            Object item = bean.getExtensionList().get(i);
            if (canWriteExtension(item))
            {
                writer.writeStartElement(NS_URI, "extension");
                this.writeExtension(writer, item);
                writer.writeEndElement();
            }
        }
        
        // keywords
        numItems = bean.getKeywordsList().size();
        for (int i = 0; i < numItems; i++)
        {
            OgcProperty<KeywordList> item = bean.getKeywordsList().getProperty(i);
            writer.writeStartElement(NS_URI, "keywords");
            writePropertyAttributes(writer, item);
            if (item.hasValue() && !item.hasHref())
                this.writeKeywordList(writer, item.getValue());
            writer.writeEndElement();
        }
        
        // identification
        numItems = bean.getIdentificationList().size();
        for (int i = 0; i < numItems; i++)
        {
            OgcProperty<IdentifierList> item = bean.getIdentificationList().getProperty(i);
            writer.writeStartElement(NS_URI, "identification");
            writePropertyAttributes(writer, item);
            if (item.hasValue() && !item.hasHref())
                this.writeIdentifierList(writer, item.getValue());
            writer.writeEndElement();
        }
        
        // classification
        numItems = bean.getClassificationList().size();
        for (int i = 0; i < numItems; i++)
        {
            OgcProperty<ClassifierList> item = bean.getClassificationList().getProperty(i);
            writer.writeStartElement(NS_URI, "classification");
            writePropertyAttributes(writer, item);
            if (item.hasValue() && !item.hasHref())
                this.writeClassifierList(writer, item.getValue());
            writer.writeEndElement();
        }
        
        // validTime
        numItems = bean.getValidTimeList().size();
        for (int i = 0; i < numItems; i++)
        {
            AbstractTimeGeometricPrimitive item = bean.getValidTimeList().get(i);
            writer.writeStartElement(NS_URI, "validTime");
            
            if (item instanceof TimePeriod)
                ns2Bindings.writeTimePeriod(writer, (TimePeriod)item);
            else if (item instanceof TimeInstant)
                ns2Bindings.writeTimeInstant(writer, (TimeInstant)item);
            
            writer.writeEndElement();
        }
        
        // securityConstraints
        numItems = bean.getSecurityConstraintsList().size();
        for (int i = 0; i < numItems; i++)
        {
            Object item = bean.getSecurityConstraintsList().get(i);
            writer.writeStartElement(NS_URI, "securityConstraints");
            this.writeSecurityConstraints(writer, item);
            writer.writeEndElement();
        }
        
        // legalConstraints
        numItems = bean.getLegalConstraintsList().size();
        for (int i = 0; i < numItems; i++)
        {
            OgcProperty<MDLegalConstraints> item = bean.getLegalConstraintsList().getProperty(i);
            writer.writeStartElement(NS_URI, "legalConstraints");
            writePropertyAttributes(writer, item);
            if (item.hasValue() && !item.hasHref())
                ns3Bindings.writeMDLegalConstraints(writer, item.getValue());
            writer.writeEndElement();
        }
        
        // characteristics
        numItems = bean.getCharacteristicsList().size();
        for (int i = 0; i < numItems; i++)
        {
            OgcProperty<CharacteristicList> item = bean.getCharacteristicsList().getProperty(i);
            writer.writeStartElement(NS_URI, "characteristics");
            writePropertyAttributes(writer, item);
            if (item.hasValue() && !item.hasHref())
                this.writeCharacteristicList(writer, item.getValue());
            writer.writeEndElement();
        }
        
        // capabilities
        numItems = bean.getCapabilitiesList().size();
        for (int i = 0; i < numItems; i++)
        {
            OgcProperty<CapabilityList> item = bean.getCapabilitiesList().getProperty(i);
            writer.writeStartElement(NS_URI, "capabilities");
            writePropertyAttributes(writer, item);
            if (item.hasValue() && !item.hasHref())
                this.writeCapabilityList(writer, item.getValue());
            writer.writeEndElement();
        }
        
        // contacts
        numItems = bean.getContactsList().size();
        for (int i = 0; i < numItems; i++)
        {
            OgcProperty<ContactList> item = bean.getContactsList().getProperty(i);
            writer.writeStartElement(NS_URI, "contacts");
            writePropertyAttributes(writer, item);
            if (item.hasValue() && !item.hasHref())
                this.writeContactList(writer, item.getValue());
            writer.writeEndElement();
        }
        
        // documentation
        numItems = bean.getDocumentationList().size();
        for (int i = 0; i < numItems; i++)
        {
            OgcProperty<DocumentList> item = bean.getDocumentationList().getProperty(i);
            writer.writeStartElement(NS_URI, "documentation");
            writePropertyAttributes(writer, item);
            if (item.hasValue() && !item.hasHref())
                this.writeDocumentList(writer, item.getValue());
            writer.writeEndElement();
        }
        
        // history
        numItems = bean.getHistoryList().size();
        for (int i = 0; i < numItems; i++)
        {
            OgcProperty<EventList> item = bean.getHistoryList().getProperty(i);
            writer.writeStartElement(NS_URI, "history");
            writePropertyAttributes(writer, item);
            if (item.hasValue() && !item.hasHref())
                this.writeEventList(writer, item.getValue());
            writer.writeEndElement();
        }
    }
    
    
    /**
     * Reads attributes of AbstractProcessType complex type
     */
    public void readAbstractProcessTypeAttributes(Map<String, String> attrMap, AbstractProcess bean) throws XMLStreamException
    {
        this.readDescribedObjectTypeAttributes(attrMap, bean);
        
        String val;
        
        // definition
        val = attrMap.get("definition");
        if (val != null)
            bean.setDefinition(val);
    }
    
    
    /**
     * Reads elements of AbstractProcessType complex type
     */
    public void readAbstractProcessTypeElements(XMLStreamReader reader, AbstractProcess bean) throws XMLStreamException
    {
        this.readDescribedObjectTypeElements(reader, bean);
        
        boolean found;
        
        // typeOf
        found = checkElementName(reader, "typeOf");
        if (found)
        {
            bean.setTypeOf(ns2Bindings.readReferenceType(reader));
            reader.nextTag(); // end property tag
            reader.nextTag();
        }
        
        // configuration
        found = checkElementName(reader, "configuration");
        if (found)
        {
            reader.nextTag();
            if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
            {
                bean.setConfiguration(this.readAbstractSettings(reader));
                reader.nextTag(); // end property tag
            }
            
            reader.nextTag();
        }
        
        // featuresOfInterest
        found = checkElementName(reader, "featuresOfInterest");
        if (found)
        {
            reader.nextTag();
            if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
            {
                bean.setFeaturesOfInterest(this.readFeatureList(reader));
                reader.nextTag(); // end property tag
            }
            
            reader.nextTag();
        }
        
        // inputs
        found = checkElementName(reader, "inputs");
        if (found)
        {
            reader.nextTag(); // InputList
            reader.nextTag();
            
            if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
            {
                // input
                do
                {
                    found = checkElementName(reader, "input");
                    if (found)
                    {
                        final OgcProperty<AbstractSWEIdentifiable> inputProp = new OgcPropertyImpl<AbstractSWEIdentifiable>();
                        readPropertyAttributes(reader, inputProp);
                        
                        reader.nextTag();
                        if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
                        {
                            readIOChoice(reader, inputProp);
                            reader.nextTag(); // end property tag
                        }
                        else if (inputProp.hasHref())
                        {
                            inputProp.setHrefResolver(new HrefResolverXML() {
                                @Override
                                public void parseContent(XMLStreamReader reader) throws XMLStreamException
                                {
                                    readIOChoice(reader, inputProp);
                                }
                            });
                        }
                        
                        bean.getInputList().add(inputProp);
                        reader.nextTag();
                    }
                }
                while (found);
                reader.nextTag(); // end property tag
            }
            
            reader.nextTag();
        }
        
        // outputs
        found = checkElementName(reader, "outputs");
        if (found)
        {
            reader.nextTag(); // OutputList
            reader.nextTag();
            
            if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
            {
                // output
                do
                {
                    found = checkElementName(reader, "output");
                    if (found)
                    {
                        final OgcProperty<AbstractSWEIdentifiable> outputProp = new OgcPropertyImpl<AbstractSWEIdentifiable>();
                        readPropertyAttributes(reader, outputProp);
                        
                        reader.nextTag();
                        if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
                        {
                            readIOChoice(reader, outputProp);
                            reader.nextTag(); // end property tag
                        }
                        else if (outputProp.hasHref())
                        {
                            outputProp.setHrefResolver(new HrefResolverXML() {
                                @Override
                                public void parseContent(XMLStreamReader reader) throws XMLStreamException
                                {
                                    readIOChoice(reader, outputProp);
                                }
                            });
                        }
                        
                        bean.getOutputList().add(outputProp);
                        reader.nextTag();
                    }
                }
                while (found);
                reader.nextTag(); // end property tag
            }
            
            reader.nextTag();
        }
        
        // parameters
        found = checkElementName(reader, "parameters");
        if (found)
        {
            reader.nextTag(); // ParameterList
            reader.nextTag();
            if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
            {
                // parameter
                do
                {
                    found = checkElementName(reader, "parameter");
                    if (found)
                    {
                        final OgcProperty<AbstractSWEIdentifiable> parameterProp = new OgcPropertyImpl<AbstractSWEIdentifiable>();
                        readPropertyAttributes(reader, parameterProp);
                        
                        reader.nextTag();
                        if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
                        {
                            readIOChoice(reader, parameterProp);
                            reader.nextTag(); // end property tag
                        }
                        else if (parameterProp.hasHref())
                        {
                            parameterProp.setHrefResolver(new HrefResolverXML() {
                                @Override
                                public void parseContent(XMLStreamReader reader) throws XMLStreamException
                                {
                                    readIOChoice(reader, parameterProp);
                                }
                            });
                        }
                        
                        bean.getParameterList().add(parameterProp);
                        reader.nextTag();
                    }
                }
                while (found);
                
                reader.nextTag(); // end property tag
            }
            
            reader.nextTag(); 
        }
        
        // modes
        do
        {
            found = checkElementName(reader, "modes");
            if (found)
            {
                reader.nextTag();
                if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
                {
                    bean.addModes(this.readAbstractModes(reader));
                    reader.nextTag(); // end property tag
                }
                
                reader.nextTag();
            }
        }
        while (found);
    }
    
    
    /**
     * Writes attributes of AbstractProcessType complex type
     */
    public void writeAbstractProcessTypeAttributes(XMLStreamWriter writer, AbstractProcess bean) throws XMLStreamException
    {
        this.writeDescribedObjectTypeAttributes(writer, bean);
        
        // definition
        if (bean.isSetDefinition())
            writer.writeAttribute("definition", getStringValue(bean.getDefinition()));
    }
    
    
    /**
     * Writes elements of AbstractProcessType complex type
     */
    public void writeAbstractProcessTypeElements(XMLStreamWriter writer, AbstractProcess bean) throws XMLStreamException
    {
        this.writeDescribedObjectTypeElements(writer, bean);
        int numItems;
        
        // typeOf
        if (bean.isSetTypeOf())
        {
            writer.writeStartElement(NS_URI, "typeOf");
            ns2Bindings.writeReferenceType(writer, bean.getTypeOf());
            writer.writeEndElement();
        }
        
        // configuration
        if (bean.isSetConfiguration())
        {
            writer.writeStartElement(NS_URI, "configuration");
            this.writeAbstractSettings(writer, bean.getConfiguration());
            writer.writeEndElement();
        }
        
        // featuresOfInterest
        if (bean.isSetFeaturesOfInterest())
        {
            writer.writeStartElement(NS_URI, "featuresOfInterest");
            this.writeFeatureList(writer, bean.getFeaturesOfInterest());
            writer.writeEndElement();
        }
        
        // inputs
        if (bean.getNumInputs() > 0)
        {
            writer.writeStartElement(NS_URI, "inputs");
            writer.writeStartElement(NS_URI, "InputList");
            
            // input
            numItems = bean.getInputList().size();
            for (int i = 0; i < numItems; i++)
            {
                OgcProperty<AbstractSWEIdentifiable> item = bean.getInputList().getProperty(i);
                writer.writeStartElement(NS_URI, "input");
                writePropertyAttributes(writer, item);
                
                if (item.hasValue() && !item.hasHref())
                {
                    if (item.getValue() instanceof ObservableProperty)
                        this.writeObservableProperty(writer, (ObservableProperty)item.getValue());
                    else if (item.getValue() instanceof DataStream)
                        ns1Bindings.writeDataStream(writer, (DataStream)item.getValue());
                    else if (item.getValue() instanceof DataInterface)
                        this.writeDataInterface(writer, (DataInterface)item.getValue());
                    else if (item.getValue() instanceof DataComponent)
                        ns1Bindings.writeDataComponent(writer, (DataComponent)item.getValue(), false);
                }
                
                writer.writeEndElement();
            }
            
            writer.writeEndElement();
            writer.writeEndElement();
        }
        
        // outputs
        if (bean.getNumOutputs() > 0)
        {
            writer.writeStartElement(NS_URI, "outputs");
            writer.writeStartElement(NS_URI, "OutputList");
            
            // output
            numItems = bean.getOutputList().size();
            for (int i = 0; i < numItems; i++)
            {
                OgcProperty<AbstractSWEIdentifiable> item = bean.getOutputList().getProperty(i);
                writer.writeStartElement(NS_URI, "output");
                writePropertyAttributes(writer, item);
                
                if (item.hasValue() && !item.hasHref())
                {
                    if (item.getValue() instanceof ObservableProperty)
                        this.writeObservableProperty(writer, (ObservableProperty)item.getValue());
                    else if (item.getValue() instanceof DataStream)
                        ns1Bindings.writeDataStream(writer, (DataStream)item.getValue());
                    else if (item.getValue() instanceof DataInterface)
                        this.writeDataInterface(writer, (DataInterface)item.getValue());
                    else if (item.getValue() instanceof DataComponent)
                        ns1Bindings.writeDataComponent(writer, (DataComponent)item.getValue(), false);
                }
                
                writer.writeEndElement();
            }
            
            writer.writeEndElement();
            writer.writeEndElement();
        }
        
        // parameters
        if (bean.getNumParameters() > 0)
        {
            writer.writeStartElement(NS_URI, "parameters");
            writer.writeStartElement(NS_URI, "ParameterList");
            
            // parameter
            numItems = bean.getParameterList().size();
            for (int i = 0; i < numItems; i++)
            {
                OgcProperty<AbstractSWEIdentifiable> item = bean.getParameterList().getProperty(i);
                writer.writeStartElement(NS_URI, "parameter");
                writePropertyAttributes(writer, item);
                
                if (item.hasValue() && !item.hasHref())
                {
                    if (item.getValue() instanceof ObservableProperty)
                        this.writeObservableProperty(writer, (ObservableProperty)item.getValue());
                    else if (item.getValue() instanceof DataStream)
                        ns1Bindings.writeDataStream(writer, (DataStream)item.getValue());
                    else if (item.getValue() instanceof DataInterface)
                        this.writeDataInterface(writer, (DataInterface)item.getValue());
                    else if (item.getValue() instanceof DataComponent)
                        ns1Bindings.writeDataComponent(writer, (DataComponent)item.getValue(), true);
                }
                
                writer.writeEndElement();
            }
            
            writer.writeEndElement();
            writer.writeEndElement();
        }
        
        // modes
        numItems = bean.getModesList().size();
        for (int i = 0; i < numItems; i++)
        {
            AbstractModes item = bean.getModesList().get(i);
            writer.writeStartElement(NS_URI, "modes");
            this.writeAbstractModes(writer, item);
            writer.writeEndElement();
        }
    }
    
    
    /**
     * Read method for AbstractSettingsType complex type
     */
    public AbstractSettings readAbstractSettingsType(XMLStreamReader reader) throws XMLStreamException
    {
        AbstractSettings bean = factory.newAbstractSettings();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readAbstractSettingsTypeAttributes(attrMap, bean);
        
        reader.nextTag();
        this.readAbstractSettingsTypeElements(reader, bean);
        
        return bean;
    }
    
    
    /**
     * Reads attributes of AbstractSettingsType complex type
     */
    public void readAbstractSettingsTypeAttributes(Map<String, String> attrMap, AbstractSettings bean) throws XMLStreamException
    {
        ns1Bindings.readAbstractSWETypeAttributes(attrMap, bean);
        
    }
    
    
    /**
     * Reads elements of AbstractSettingsType complex type
     */
    public void readAbstractSettingsTypeElements(XMLStreamReader reader, AbstractSettings bean) throws XMLStreamException
    {
        ns1Bindings.readAbstractSWETypeElements(reader, bean);
        
    }
    
    
    /**
     * Write method for AbstractSettingsType complex type
     */
    public void writeAbstractSettingsType(XMLStreamWriter writer, AbstractSettings bean) throws XMLStreamException
    {
        this.writeAbstractSettingsTypeAttributes(writer, bean);
        this.writeAbstractSettingsTypeElements(writer, bean);
    }
    
    
    /**
     * Writes attributes of AbstractSettingsType complex type
     */
    public void writeAbstractSettingsTypeAttributes(XMLStreamWriter writer, AbstractSettings bean) throws XMLStreamException
    {
        ns1Bindings.writeAbstractSWETypeAttributes(writer, bean);
    }
    
    
    /**
     * Writes elements of AbstractSettingsType complex type
     */
    public void writeAbstractSettingsTypeElements(XMLStreamWriter writer, AbstractSettings bean) throws XMLStreamException
    {
        ns1Bindings.writeAbstractSWETypeElements(writer, bean);
    }
    
    
    /**
     * Read method for IdentifierListType complex type
     */
    public IdentifierList readIdentifierListType(XMLStreamReader reader) throws XMLStreamException
    {
        IdentifierList bean = factory.newIdentifierList();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readIdentifierListTypeAttributes(attrMap, bean);
        
        reader.nextTag();
        this.readIdentifierListTypeElements(reader, bean);
        
        return bean;
    }
    
    
    /**
     * Reads attributes of IdentifierListType complex type
     */
    public void readIdentifierListTypeAttributes(Map<String, String> attrMap, IdentifierList bean) throws XMLStreamException
    {
        this.readAbstractMetadataListTypeAttributes(attrMap, bean);
        
    }
    
    
    /**
     * Reads elements of IdentifierListType complex type
     */
    public void readIdentifierListTypeElements(XMLStreamReader reader, IdentifierList bean) throws XMLStreamException
    {
        this.readAbstractMetadataListTypeElements(reader, bean);
        
        boolean found;
        
        // identifier
        do
        {
            found = checkElementName(reader, "identifier");
            if (found)
            {
                reader.nextTag();
                if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
                {
                    bean.addIdentifier2(this.readTerm(reader));
                    reader.nextTag(); // end property tag
                }
                
                reader.nextTag();
            }
        }
        while (found);
    }
    
    
    /**
     * Write method for IdentifierListType complex type
     */
    public void writeIdentifierListType(XMLStreamWriter writer, IdentifierList bean) throws XMLStreamException
    {
        this.writeIdentifierListTypeAttributes(writer, bean);
        this.writeIdentifierListTypeElements(writer, bean);
    }
    
    
    /**
     * Writes attributes of IdentifierListType complex type
     */
    public void writeIdentifierListTypeAttributes(XMLStreamWriter writer, IdentifierList bean) throws XMLStreamException
    {
        this.writeAbstractMetadataListTypeAttributes(writer, bean);
    }
    
    
    /**
     * Writes elements of IdentifierListType complex type
     */
    public void writeIdentifierListTypeElements(XMLStreamWriter writer, IdentifierList bean) throws XMLStreamException
    {
        this.writeAbstractMetadataListTypeElements(writer, bean);
        int numItems;
        
        // identifier
        numItems = bean.getIdentifier2List().size();
        for (int i = 0; i < numItems; i++)
        {
            Term item = bean.getIdentifier2List().get(i);
            writer.writeStartElement(NS_URI, "identifier");
            this.writeTerm(writer, item);
            writer.writeEndElement();
        }
    }
    
    
    protected void readIOChoice(XMLStreamReader reader, OgcProperty<AbstractSWEIdentifiable> prop) throws XMLStreamException
    {
        String localName = reader.getName().getLocalPart();
        
        if ("ObservableProperty".equals(localName))
        {
            ObservableProperty signal = this.readObservableProperty(reader);
            prop.setValue(signal);
        }
        else if ("DataStream".equals(localName))
        {
            DataStream signal = ns1Bindings.readDataStream(reader);
            prop.setValue(signal);
        }
        else if ("DataInterface".equals(localName))
        {
            DataInterface signal = this.readDataInterface(reader);
            prop.setValue(signal);
        }
        else
        {
            DataComponent signal = ns1Bindings.readDataComponent(reader);
            prop.setValue(signal);
        }
    }
    
    
    /**
     * Read method for DocumentListType complex type
     */
    public DocumentList readDocumentListType(XMLStreamReader reader) throws XMLStreamException
    {
        DocumentList bean = factory.newDocumentList();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readDocumentListTypeAttributes(attrMap, bean);
        
        reader.nextTag();
        this.readDocumentListTypeElements(reader, bean);
        
        return bean;
    }
    
    
    /**
     * Reads attributes of DocumentListType complex type
     */
    public void readDocumentListTypeAttributes(Map<String, String> attrMap, DocumentList bean) throws XMLStreamException
    {
        this.readAbstractMetadataListTypeAttributes(attrMap, bean);
        
    }
    
    
    /**
     * Reads elements of DocumentListType complex type
     */
    public void readDocumentListTypeElements(XMLStreamReader reader, DocumentList bean) throws XMLStreamException
    {
        this.readAbstractMetadataListTypeElements(reader, bean);
        
        boolean found;
        
        // document
        do
        {
            found = checkElementName(reader, "document");
            if (found)
            {
                OgcProperty<CIOnlineResource> documentProp = new OgcPropertyImpl<CIOnlineResource>();
                readPropertyAttributes(reader, documentProp);
                
                reader.nextTag();
                if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
                {
                    documentProp.setValue(ns3Bindings.readCIOnlineResource(reader));
                    reader.nextTag(); // end property tag
                }
                
                bean.getDocumentList().add(documentProp);
                reader.nextTag();
            }
        }
        while (found);
    }
    
    
    /**
     * Write method for DocumentListType complex type
     */
    public void writeDocumentListType(XMLStreamWriter writer, DocumentList bean) throws XMLStreamException
    {
        this.writeDocumentListTypeAttributes(writer, bean);
        this.writeDocumentListTypeElements(writer, bean);
    }
    
    
    /**
     * Writes attributes of DocumentListType complex type
     */
    public void writeDocumentListTypeAttributes(XMLStreamWriter writer, DocumentList bean) throws XMLStreamException
    {
        this.writeAbstractMetadataListTypeAttributes(writer, bean);
    }
    
    
    /**
     * Writes elements of DocumentListType complex type
     */
    public void writeDocumentListTypeElements(XMLStreamWriter writer, DocumentList bean) throws XMLStreamException
    {
        this.writeAbstractMetadataListTypeElements(writer, bean);
        int numItems;
        
        // document
        numItems = bean.getDocumentList().size();
        for (int i = 0; i < numItems; i++)
        {
            OgcProperty<CIOnlineResource> item = bean.getDocumentList().getProperty(i);
            writer.writeStartElement(NS_URI, "document");
            writePropertyAttributes(writer, item);
            if (item.hasValue() && !item.hasHref())
                ns3Bindings.writeCIOnlineResource(writer, item.getValue());
            writer.writeEndElement();
        }
    }
    
    
    /**
     * Read method for CharacteristicListType complex type
     */
    public CharacteristicList readCharacteristicListType(XMLStreamReader reader) throws XMLStreamException
    {
        CharacteristicList bean = factory.newCharacteristicList();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readCharacteristicListTypeAttributes(attrMap, bean);
        
        reader.nextTag();
        this.readCharacteristicListTypeElements(reader, bean);
        
        return bean;
    }
    
    
    /**
     * Reads attributes of CharacteristicListType complex type
     */
    public void readCharacteristicListTypeAttributes(Map<String, String> attrMap, CharacteristicList bean) throws XMLStreamException
    {
        this.readAbstractMetadataListTypeAttributes(attrMap, bean);
        
    }
    
    
    /**
     * Reads elements of CharacteristicListType complex type
     */
    public void readCharacteristicListTypeElements(XMLStreamReader reader, CharacteristicList bean) throws XMLStreamException
    {
        this.readAbstractMetadataListTypeElements(reader, bean);
        
        boolean found;
        
        // characteristic
        do
        {
            found = checkElementName(reader, "characteristic");
            if (found)
            {
                OgcProperty<DataComponent> characteristicProp = new OgcPropertyImpl<DataComponent>();
                readPropertyAttributes(reader, characteristicProp);
                
                reader.nextTag();
                if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
                {
                    characteristicProp.setValue(ns1Bindings.readDataComponent(reader));
                    reader.nextTag(); // end property tag
                }
                
                bean.getCharacteristicList().add(characteristicProp);
                reader.nextTag();
            }
        }
        while (found);
    }
    
    
    /**
     * Write method for CharacteristicListType complex type
     */
    public void writeCharacteristicListType(XMLStreamWriter writer, CharacteristicList bean) throws XMLStreamException
    {
        this.writeCharacteristicListTypeAttributes(writer, bean);
        this.writeCharacteristicListTypeElements(writer, bean);
    }
    
    
    /**
     * Writes attributes of CharacteristicListType complex type
     */
    public void writeCharacteristicListTypeAttributes(XMLStreamWriter writer, CharacteristicList bean) throws XMLStreamException
    {
        this.writeAbstractMetadataListTypeAttributes(writer, bean);
    }
    
    
    /**
     * Writes elements of CharacteristicListType complex type
     */
    public void writeCharacteristicListTypeElements(XMLStreamWriter writer, CharacteristicList bean) throws XMLStreamException
    {
        this.writeAbstractMetadataListTypeElements(writer, bean);
        int numItems;
        
        // characteristic
        numItems = bean.getCharacteristicList().size();
        for (int i = 0; i < numItems; i++)
        {
            OgcProperty<DataComponent> item = bean.getCharacteristicList().getProperty(i);
            writer.writeStartElement(NS_URI, "characteristic");
            writePropertyAttributes(writer, item);
            if (item.hasValue() && !item.hasHref())
                ns1Bindings.writeDataComponent(writer, item.getValue(), true);
            writer.writeEndElement();
        }
    }
    
    
    /**
     * Read method for FeatureListType complex type
     */
    public FeatureList readFeatureListType(XMLStreamReader reader) throws XMLStreamException
    {
        FeatureList bean = factory.newFeatureList();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readFeatureListTypeAttributes(attrMap, bean);
        
        reader.nextTag();
        this.readFeatureListTypeElements(reader, bean);
        
        return bean;
    }
    
    
    /**
     * Reads attributes of FeatureListType complex type
     */
    public void readFeatureListTypeAttributes(Map<String, String> attrMap, FeatureList bean) throws XMLStreamException
    {
        this.readAbstractMetadataListTypeAttributes(attrMap, bean);
        
    }
    
    
    /**
     * Reads elements of FeatureListType complex type
     */
    public void readFeatureListTypeElements(XMLStreamReader reader, FeatureList bean) throws XMLStreamException
    {
        this.readAbstractMetadataListTypeElements(reader, bean);
        
        boolean found;
        
        // feature
        do
        {
            found = checkElementName(reader, "feature");
            if (found)
            {
                OgcProperty<AbstractFeature> featureProp = new OgcPropertyImpl<AbstractFeature>();
                readPropertyAttributes(reader, featureProp);
                
                reader.nextTag();
                if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
                {
                    featureProp.setValue(ns2Bindings.readAbstractFeature(reader));
                    reader.nextTag(); // end property tag
                }
                
                bean.getFeatureList().add(featureProp);
                reader.nextTag();
            }
        }
        while (found);
    }
    
    
    /**
     * Write method for FeatureListType complex type
     */
    public void writeFeatureListType(XMLStreamWriter writer, FeatureList bean) throws XMLStreamException
    {
        this.writeFeatureListTypeAttributes(writer, bean);
        this.writeFeatureListTypeElements(writer, bean);
    }
    
    
    /**
     * Writes attributes of FeatureListType complex type
     */
    public void writeFeatureListTypeAttributes(XMLStreamWriter writer, FeatureList bean) throws XMLStreamException
    {
        this.writeAbstractMetadataListTypeAttributes(writer, bean);
    }
    
    
    /**
     * Writes elements of FeatureListType complex type
     */
    public void writeFeatureListTypeElements(XMLStreamWriter writer, FeatureList bean) throws XMLStreamException
    {
        this.writeAbstractMetadataListTypeElements(writer, bean);
        int numItems;
        
        // feature
        numItems = bean.getFeatureList().size();
        for (int i = 0; i < numItems; i++)
        {
            OgcProperty<AbstractFeature> item = bean.getFeatureList().getProperty(i);
            writer.writeStartElement(NS_URI, "feature");
            writePropertyAttributes(writer, item);
            if (item.hasValue() && !item.hasHref())
                ns2Bindings.writeAbstractFeature(writer, item.getValue());
            writer.writeEndElement();
        }
    }
    
    
    /**
     * Read method for EventType complex type
     */
    public Event readEventType(XMLStreamReader reader) throws XMLStreamException
    {
        Event bean = factory.newEvent();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readEventTypeAttributes(attrMap, bean);
        
        reader.nextTag();
        this.readEventTypeElements(reader, bean);
        
        return bean;
    }
    
    
    /**
     * Reads attributes of EventType complex type
     */
    public void readEventTypeAttributes(Map<String, String> attrMap, Event bean) throws XMLStreamException
    {
        ns1Bindings.readAbstractSWEIdentifiableTypeAttributes(attrMap, bean);
        
        String val;
        
        // definition
        val = attrMap.get("definition");
        if (val != null)
            bean.setDefinition(val);
    }
    
    
    /**
     * Reads elements of EventType complex type
     */
    public void readEventTypeElements(XMLStreamReader reader, Event bean) throws XMLStreamException
    {
        ns1Bindings.readAbstractSWEIdentifiableTypeElements(reader, bean);
        
        boolean found;
        
        // keywords
        do
        {
            found = checkElementName(reader, "keywords");
            if (found)
            {
                OgcProperty<MDKeywords> keywordsProp = new OgcPropertyImpl<MDKeywords>();
                readPropertyAttributes(reader, keywordsProp);
                
                reader.nextTag();
                if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
                {
                    keywordsProp.setValue(ns3Bindings.readMDKeywords(reader));
                    reader.nextTag(); // end property tag
                }
                
                bean.getKeywordsList().add(keywordsProp);
                reader.nextTag();
            }
        }
        while (found);
        
        // identification
        do
        {
            found = checkElementName(reader, "identification");
            if (found)
            {
                OgcProperty<IdentifierList> identificationProp = new OgcPropertyImpl<IdentifierList>();
                readPropertyAttributes(reader, identificationProp);
                
                reader.nextTag();
                if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
                {
                    identificationProp.setValue(this.readIdentifierList(reader));
                    reader.nextTag(); // end property tag
                }
                
                bean.getIdentificationList().add(identificationProp);
                reader.nextTag();
            }
        }
        while (found);
        
        // classification
        do
        {
            found = checkElementName(reader, "classification");
            if (found)
            {
                OgcProperty<ClassifierList> classificationProp = new OgcPropertyImpl<ClassifierList>();
                readPropertyAttributes(reader, classificationProp);
                
                reader.nextTag();
                if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
                {
                    classificationProp.setValue(this.readClassifierList(reader));
                    reader.nextTag(); // end property tag
                }
                
                bean.getClassificationList().add(classificationProp);
                reader.nextTag();
            }
        }
        while (found);
        
        // contacts
        do
        {
            found = checkElementName(reader, "contacts");
            if (found)
            {
                OgcProperty<ContactList> contactsProp = new OgcPropertyImpl<ContactList>();
                readPropertyAttributes(reader, contactsProp);
                
                reader.nextTag();
                if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
                {
                    contactsProp.setValue(this.readContactList(reader));
                    reader.nextTag(); // end property tag
                }
                
                bean.getContactsList().add(contactsProp);
                reader.nextTag();
            }
        }
        while (found);
        
        // documentation
        do
        {
            found = checkElementName(reader, "documentation");
            if (found)
            {
                OgcProperty<DocumentList> documentationProp = new OgcPropertyImpl<DocumentList>();
                readPropertyAttributes(reader, documentationProp);
                
                reader.nextTag();
                if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
                {
                    documentationProp.setValue(this.readDocumentList(reader));
                    reader.nextTag(); // end property tag
                }
                
                bean.getDocumentationList().add(documentationProp);
                reader.nextTag();
            }
        }
        while (found);
        
        // time
        found = checkElementName(reader, "time");
        if (found)
        {
            reader.nextTag();
            if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
            {
                String localName = reader.getName().getLocalPart();
                
                if ("TimePeriod".equals(localName))
                {
                    TimePeriod time = ns2Bindings.readTimePeriod(reader);
                    bean.setTimeAsTimePeriod(time);
                }
                else if ("TimeInstant".equals(localName))
                {
                    TimeInstant time = ns2Bindings.readTimeInstant(reader);
                    bean.setTimeAsTimeInstant(time);
                }
                else
                    throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
                
                reader.nextTag(); // end property tag
            }
            
            reader.nextTag();
        }
        
        // property
        do
        {
            found = checkElementName(reader, "property");
            if (found)
            {
                OgcProperty<DataComponent> propertyProp = new OgcPropertyImpl<DataComponent>();
                readPropertyAttributes(reader, propertyProp);
                
                reader.nextTag();
                if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
                {
                    propertyProp.setValue(ns1Bindings.readDataComponent(reader));
                    reader.nextTag(); // end property tag
                }
                
                bean.getPropertyList().add(propertyProp);
                reader.nextTag();
            }
        }
        while (found);
        
        // configuration
        found = checkElementName(reader, "configuration");
        if (found)
        {
            reader.nextTag();
            if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
            {
                bean.setConfiguration(this.readAbstractSettings(reader));
                reader.nextTag(); // end property tag
            }
            
            reader.nextTag();
        }
    }
    
    
    /**
     * Write method for EventType complex type
     */
    public void writeEventType(XMLStreamWriter writer, Event bean) throws XMLStreamException
    {
        this.writeEventTypeAttributes(writer, bean);
        this.writeEventTypeElements(writer, bean);
    }
    
    
    /**
     * Writes attributes of EventType complex type
     */
    public void writeEventTypeAttributes(XMLStreamWriter writer, Event bean) throws XMLStreamException
    {
        ns1Bindings.writeAbstractSWEIdentifiableTypeAttributes(writer, bean);
        
        // definition
        if (bean.isSetDefinition())
            writer.writeAttribute("definition", getStringValue(bean.getDefinition()));
    }
    
    
    /**
     * Writes elements of EventType complex type
     */
    public void writeEventTypeElements(XMLStreamWriter writer, Event bean) throws XMLStreamException
    {
        ns1Bindings.writeAbstractSWEIdentifiableTypeElements(writer, bean);
        int numItems;
        
        // keywords
        numItems = bean.getKeywordsList().size();
        for (int i = 0; i < numItems; i++)
        {
            OgcProperty<MDKeywords> item = bean.getKeywordsList().getProperty(i);
            writer.writeStartElement(NS_URI, "keywords");
            writePropertyAttributes(writer, item);
            if (item.hasValue() && !item.hasHref())
                ns3Bindings.writeMDKeywords(writer, item.getValue());
            writer.writeEndElement();
        }
        
        // identification
        numItems = bean.getIdentificationList().size();
        for (int i = 0; i < numItems; i++)
        {
            OgcProperty<IdentifierList> item = bean.getIdentificationList().getProperty(i);
            writer.writeStartElement(NS_URI, "identification");
            writePropertyAttributes(writer, item);
            if (item.hasValue() && !item.hasHref())
                this.writeIdentifierList(writer, item.getValue());
            writer.writeEndElement();
        }
        
        // classification
        numItems = bean.getClassificationList().size();
        for (int i = 0; i < numItems; i++)
        {
            OgcProperty<ClassifierList> item = bean.getClassificationList().getProperty(i);
            writer.writeStartElement(NS_URI, "classification");
            writePropertyAttributes(writer, item);
            if (item.hasValue() && !item.hasHref())
                this.writeClassifierList(writer, item.getValue());
            writer.writeEndElement();
        }
        
        // contacts
        numItems = bean.getContactsList().size();
        for (int i = 0; i < numItems; i++)
        {
            OgcProperty<ContactList> item = bean.getContactsList().getProperty(i);
            writer.writeStartElement(NS_URI, "contacts");
            writePropertyAttributes(writer, item);
            if (item.hasValue() && !item.hasHref())
                this.writeContactList(writer, item.getValue());
            writer.writeEndElement();
        }
        
        // documentation
        numItems = bean.getDocumentationList().size();
        for (int i = 0; i < numItems; i++)
        {
            OgcProperty<DocumentList> item = bean.getDocumentationList().getProperty(i);
            writer.writeStartElement(NS_URI, "documentation");
            writePropertyAttributes(writer, item);
            if (item.hasValue() && !item.hasHref())
                this.writeDocumentList(writer, item.getValue());
            writer.writeEndElement();
        }
        
        // time
        writer.writeStartElement(NS_URI, "time");
        
        if (bean.getTime() instanceof TimePeriod)
            ns2Bindings.writeTimePeriod(writer, (TimePeriod)bean.getTime());
        else if (bean.getTime() instanceof TimeInstant)
            ns2Bindings.writeTimeInstant(writer, (TimeInstant)bean.getTime());
        
        writer.writeEndElement();
        
        // property
        numItems = bean.getPropertyList().size();
        for (int i = 0; i < numItems; i++)
        {
            OgcProperty<DataComponent> item = bean.getPropertyList().getProperty(i);
            writer.writeStartElement(NS_URI, "property");
            writePropertyAttributes(writer, item);
            if (item.hasValue() && !item.hasHref())
                ns1Bindings.writeDataComponent(writer, item.getValue(), true);
            writer.writeEndElement();
        }
        
        // configuration
        if (bean.isSetConfiguration())
        {
            writer.writeStartElement(NS_URI, "configuration");
            this.writeAbstractSettings(writer, bean.getConfiguration());
            writer.writeEndElement();
        }
    }
    
    
    /**
     * Read method for CapabilityListType complex type
     */
    public CapabilityList readCapabilityListType(XMLStreamReader reader) throws XMLStreamException
    {
        CapabilityList bean = factory.newCapabilityList();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readCapabilityListTypeAttributes(attrMap, bean);
        
        reader.nextTag();
        this.readCapabilityListTypeElements(reader, bean);
        
        return bean;
    }
    
    
    /**
     * Reads attributes of CapabilityListType complex type
     */
    public void readCapabilityListTypeAttributes(Map<String, String> attrMap, CapabilityList bean) throws XMLStreamException
    {
        this.readAbstractMetadataListTypeAttributes(attrMap, bean);
        
    }
    
    
    /**
     * Reads elements of CapabilityListType complex type
     */
    public void readCapabilityListTypeElements(XMLStreamReader reader, CapabilityList bean) throws XMLStreamException
    {
        this.readAbstractMetadataListTypeElements(reader, bean);
        
        boolean found;
        
        // capability
        do
        {
            found = checkElementName(reader, "capability");
            if (found)
            {
                OgcProperty<DataComponent> capabilityProp = new OgcPropertyImpl<DataComponent>();
                readPropertyAttributes(reader, capabilityProp);
                
                reader.nextTag();
                if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
                {
                    capabilityProp.setValue(ns1Bindings.readDataComponent(reader));
                    reader.nextTag(); // end property tag
                }
                
                bean.getCapabilityList().add(capabilityProp);
                reader.nextTag();
            }
        }
        while (found);
    }
    
    
    /**
     * Write method for CapabilityListType complex type
     */
    public void writeCapabilityListType(XMLStreamWriter writer, CapabilityList bean) throws XMLStreamException
    {
        this.writeCapabilityListTypeAttributes(writer, bean);
        this.writeCapabilityListTypeElements(writer, bean);
    }
    
    
    /**
     * Writes attributes of CapabilityListType complex type
     */
    public void writeCapabilityListTypeAttributes(XMLStreamWriter writer, CapabilityList bean) throws XMLStreamException
    {
        this.writeAbstractMetadataListTypeAttributes(writer, bean);
    }
    
    
    /**
     * Writes elements of CapabilityListType complex type
     */
    public void writeCapabilityListTypeElements(XMLStreamWriter writer, CapabilityList bean) throws XMLStreamException
    {
        this.writeAbstractMetadataListTypeElements(writer, bean);
        int numItems;
        
        // capability
        numItems = bean.getCapabilityList().size();
        for (int i = 0; i < numItems; i++)
        {
            OgcProperty<DataComponent> item = bean.getCapabilityList().getProperty(i);
            writer.writeStartElement(NS_URI, "capability");
            writePropertyAttributes(writer, item);
            if (item.hasValue() && !item.hasHref())
                ns1Bindings.writeDataComponent(writer, item.getValue(), true);
            writer.writeEndElement();
        }
    }
    
    
    /**
     * Read method for EventListType complex type
     */
    public EventList readEventListType(XMLStreamReader reader) throws XMLStreamException
    {
        EventList bean = factory.newEventList();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readEventListTypeAttributes(attrMap, bean);
        
        reader.nextTag();
        this.readEventListTypeElements(reader, bean);
        
        return bean;
    }
    
    
    /**
     * Reads attributes of EventListType complex type
     */
    public void readEventListTypeAttributes(Map<String, String> attrMap, EventList bean) throws XMLStreamException
    {
        this.readAbstractMetadataListTypeAttributes(attrMap, bean);
        
    }
    
    
    /**
     * Reads elements of EventListType complex type
     */
    public void readEventListTypeElements(XMLStreamReader reader, EventList bean) throws XMLStreamException
    {
        this.readAbstractMetadataListTypeElements(reader, bean);
        
        boolean found;
        
        // event
        do
        {
            found = checkElementName(reader, "event");
            if (found)
            {
                OgcProperty<Event> eventProp = new OgcPropertyImpl<Event>();
                readPropertyAttributes(reader, eventProp);
                
                reader.nextTag();
                if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
                {
                    eventProp.setValue(this.readEvent(reader));
                    reader.nextTag(); // end property tag
                }
                
                bean.getEventList().add(eventProp);
                reader.nextTag();
            }
        }
        while (found);
    }
    
    
    /**
     * Write method for EventListType complex type
     */
    public void writeEventListType(XMLStreamWriter writer, EventList bean) throws XMLStreamException
    {
        this.writeEventListTypeAttributes(writer, bean);
        this.writeEventListTypeElements(writer, bean);
    }
    
    
    /**
     * Writes attributes of EventListType complex type
     */
    public void writeEventListTypeAttributes(XMLStreamWriter writer, EventList bean) throws XMLStreamException
    {
        this.writeAbstractMetadataListTypeAttributes(writer, bean);
    }
    
    
    /**
     * Writes elements of EventListType complex type
     */
    public void writeEventListTypeElements(XMLStreamWriter writer, EventList bean) throws XMLStreamException
    {
        this.writeAbstractMetadataListTypeElements(writer, bean);
        int numItems;
        
        // event
        numItems = bean.getEventList().size();
        for (int i = 0; i < numItems; i++)
        {
            OgcProperty<Event> item = bean.getEventList().getProperty(i);
            writer.writeStartElement(NS_URI, "event");
            writePropertyAttributes(writer, item);
            if (item.hasValue() && !item.hasHref())
                this.writeEvent(writer, item.getValue());
            writer.writeEndElement();
        }
    }
    
    
    /**
     * Read method for AbstractMetadataListType complex type
     */
    public AbstractMetadataList readAbstractMetadataListType(XMLStreamReader reader) throws XMLStreamException
    {
        AbstractMetadataList bean = factory.newAbstractMetadataList();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readAbstractMetadataListTypeAttributes(attrMap, bean);
        
        reader.nextTag();
        this.readAbstractMetadataListTypeElements(reader, bean);
        
        return bean;
    }
    
    
    /**
     * Reads attributes of AbstractMetadataListType complex type
     */
    public void readAbstractMetadataListTypeAttributes(Map<String, String> attrMap, AbstractMetadataList bean) throws XMLStreamException
    {
        ns1Bindings.readAbstractSWEIdentifiableTypeAttributes(attrMap, bean);
        
        String val;
        
        // definition
        val = attrMap.get("definition");
        if (val != null)
            bean.setDefinition(val);
    }
    
    
    /**
     * Reads elements of AbstractMetadataListType complex type
     */
    public void readAbstractMetadataListTypeElements(XMLStreamReader reader, AbstractMetadataList bean) throws XMLStreamException
    {
        ns1Bindings.readAbstractSWEIdentifiableTypeElements(reader, bean);
        
    }
    
    
    /**
     * Write method for AbstractMetadataListType complex type
     */
    public void writeAbstractMetadataListType(XMLStreamWriter writer, AbstractMetadataList bean) throws XMLStreamException
    {
        this.writeAbstractMetadataListTypeAttributes(writer, bean);
        this.writeAbstractMetadataListTypeElements(writer, bean);
    }
    
    
    /**
     * Writes attributes of AbstractMetadataListType complex type
     */
    public void writeAbstractMetadataListTypeAttributes(XMLStreamWriter writer, AbstractMetadataList bean) throws XMLStreamException
    {
        ns1Bindings.writeAbstractSWEIdentifiableTypeAttributes(writer, bean);
        
        // definition
        if (bean.isSetDefinition())
            writer.writeAttribute("definition", getStringValue(bean.getDefinition()));
    }
    
    
    /**
     * Writes elements of AbstractMetadataListType complex type
     */
    public void writeAbstractMetadataListTypeElements(XMLStreamWriter writer, AbstractMetadataList bean) throws XMLStreamException
    {
        ns1Bindings.writeAbstractSWEIdentifiableTypeElements(writer, bean);
    }
    
    
    /**
     * Read method for ContactListType complex type
     */
    public ContactList readContactListType(XMLStreamReader reader) throws XMLStreamException
    {
        ContactList bean = factory.newContactList();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readContactListTypeAttributes(attrMap, bean);
        
        reader.nextTag();
        this.readContactListTypeElements(reader, bean);
        
        return bean;
    }
    
    
    /**
     * Reads attributes of ContactListType complex type
     */
    public void readContactListTypeAttributes(Map<String, String> attrMap, ContactList bean) throws XMLStreamException
    {
        this.readAbstractMetadataListTypeAttributes(attrMap, bean);
        
    }
    
    
    /**
     * Reads elements of ContactListType complex type
     */
    public void readContactListTypeElements(XMLStreamReader reader, ContactList bean) throws XMLStreamException
    {
        this.readAbstractMetadataListTypeElements(reader, bean);
        
        boolean found;
        
        // contact
        do
        {
            found = checkElementName(reader, "contact");
            if (found)
            {
                OgcProperty<CIResponsibleParty> contactProp = new OgcPropertyImpl<CIResponsibleParty>();
                readPropertyAttributes(reader, contactProp);
                
                reader.nextTag();
                if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
                {
                    contactProp.setValue(ns3Bindings.readCIResponsibleParty(reader));
                    reader.nextTag(); // end property tag
                }
                
                bean.getContactList().add(contactProp);
                reader.nextTag();
            }
        }
        while (found);
    }
    
    
    /**
     * Write method for ContactListType complex type
     */
    public void writeContactListType(XMLStreamWriter writer, ContactList bean) throws XMLStreamException
    {
        this.writeContactListTypeAttributes(writer, bean);
        this.writeContactListTypeElements(writer, bean);
    }
    
    
    /**
     * Writes attributes of ContactListType complex type
     */
    public void writeContactListTypeAttributes(XMLStreamWriter writer, ContactList bean) throws XMLStreamException
    {
        this.writeAbstractMetadataListTypeAttributes(writer, bean);
    }
    
    
    /**
     * Writes elements of ContactListType complex type
     */
    public void writeContactListTypeElements(XMLStreamWriter writer, ContactList bean) throws XMLStreamException
    {
        this.writeAbstractMetadataListTypeElements(writer, bean);
        int numItems;
        
        // contact
        numItems = bean.getContactList().size();
        for (int i = 0; i < numItems; i++)
        {
            OgcProperty<CIResponsibleParty> item = bean.getContactList().getProperty(i);
            writer.writeStartElement(NS_URI, "contact");
            writePropertyAttributes(writer, item);
            if (item.hasValue() && !item.hasHref())
                ns3Bindings.writeCIResponsibleParty(writer, item.getValue());
            writer.writeEndElement();
        }
    }
    
    
    /**
     * Read method for KeywordListType complex type
     */
    public KeywordList readKeywordListType(XMLStreamReader reader) throws XMLStreamException
    {
        KeywordList bean = factory.newKeywordList();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readKeywordListTypeAttributes(attrMap, bean);
        
        reader.nextTag();
        this.readKeywordListTypeElements(reader, bean);
        
        return bean;
    }
    
    
    /**
     * Reads attributes of KeywordListType complex type
     */
    public void readKeywordListTypeAttributes(Map<String, String> attrMap, KeywordList bean) throws XMLStreamException
    {
        this.readAbstractMetadataListTypeAttributes(attrMap, bean);
        
    }
    
    
    /**
     * Reads elements of KeywordListType complex type
     */
    public void readKeywordListTypeElements(XMLStreamReader reader, KeywordList bean) throws XMLStreamException
    {
        this.readAbstractMetadataListTypeElements(reader, bean);
        
        boolean found;
        String val;
        
        // codeSpace
        found = checkElementName(reader, "codeSpace");
        if (found)
        {
            Reference ref = ns2Bindings.readReferenceType(reader);
            bean.setCodeSpace(ref.getHref());
            reader.nextTag(); // end property tag
            reader.nextTag();
        }
        
        // keyword
        do
        {
            found = checkElementName(reader, "keyword");
            if (found)
            {
                val = reader.getElementText();
                if (val != null)
                    bean.addKeyword(trimStringValue(val));
                reader.nextTag();
            }
        }
        while (found);
    }
    
    
    /**
     * Write method for KeywordListType complex type
     */
    public void writeKeywordListType(XMLStreamWriter writer, KeywordList bean) throws XMLStreamException
    {
        this.writeKeywordListTypeAttributes(writer, bean);
        this.writeKeywordListTypeElements(writer, bean);
    }
    
    
    /**
     * Writes attributes of KeywordListType complex type
     */
    public void writeKeywordListTypeAttributes(XMLStreamWriter writer, KeywordList bean) throws XMLStreamException
    {
        this.writeAbstractMetadataListTypeAttributes(writer, bean);
    }
    
    
    /**
     * Writes elements of KeywordListType complex type
     */
    public void writeKeywordListTypeElements(XMLStreamWriter writer, KeywordList bean) throws XMLStreamException
    {
        this.writeAbstractMetadataListTypeElements(writer, bean);
        int numItems;
        
        // codeSpace
        if (bean.isSetCodeSpace())
        {
            writer.writeStartElement(NS_URI, "codeSpace");
            writer.writeAttribute(XLINK_NS_URI, "href", bean.getCodeSpace());
            writer.writeEndElement();
        }
        
        // keyword
        numItems = bean.getKeywordList().size();
        for (int i = 0; i < numItems; i++)
        {
            String item = bean.getKeywordList().get(i);
            writer.writeStartElement(NS_URI, "keyword");
            writer.writeCharacters(item);
            writer.writeEndElement();
        }
    }
    
    
    /**
     * Read method for TermType complex type
     */
    public Term readTermType(XMLStreamReader reader) throws XMLStreamException
    {
        Term bean = factory.newTerm();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readTermTypeAttributes(attrMap, bean);
        
        reader.nextTag();
        this.readTermTypeElements(reader, bean);
        
        return bean;
    }
    
    
    /**
     * Reads attributes of TermType complex type
     */
    public void readTermTypeAttributes(Map<String, String> attrMap, Term bean) throws XMLStreamException
    {
        ns1Bindings.readAbstractSWETypeAttributes(attrMap, bean);
        
        String val;
        
        // definition
        val = attrMap.get("definition");
        if (val != null)
            bean.setDefinition(val);
    }
    
    
    /**
     * Reads elements of TermType complex type
     */
    public void readTermTypeElements(XMLStreamReader reader, Term bean) throws XMLStreamException
    {
        ns1Bindings.readAbstractSWETypeElements(reader, bean);
        
        boolean found;
        String val;
        
        // label
        found = checkElementName(reader, "label");
        if (found)
        {
            val = reader.getElementText();
            if (val != null)
                bean.setLabel(trimStringValue(val));
            reader.nextTag();
        }
        
        // codeSpace
        found = checkElementName(reader, "codeSpace");
        if (found)
        {
            Reference ref = ns2Bindings.readReferenceType(reader);
            bean.setCodeSpace(ref.getHref());
            reader.nextTag(); // end property tag
            reader.nextTag();
        }
        
        // value
        found = checkElementName(reader, "value");
        if (found)
        {
            val = reader.getElementText();
            if (val != null)
                bean.setValue(val);
            reader.nextTag();
        }
    }
    
    
    /**
     * Write method for TermType complex type
     */
    public void writeTermType(XMLStreamWriter writer, Term bean) throws XMLStreamException
    {
        this.writeTermTypeAttributes(writer, bean);
        this.writeTermTypeElements(writer, bean);
    }
    
    
    /**
     * Writes attributes of TermType complex type
     */
    public void writeTermTypeAttributes(XMLStreamWriter writer, Term bean) throws XMLStreamException
    {
        ns1Bindings.writeAbstractSWETypeAttributes(writer, bean);
        
        // definition
        if (bean.isSetDefinition())
            writer.writeAttribute("definition", getStringValue(bean.getDefinition()));
    }
    
    
    /**
     * Writes elements of TermType complex type
     */
    public void writeTermTypeElements(XMLStreamWriter writer, Term bean) throws XMLStreamException
    {
        ns1Bindings.writeAbstractSWETypeElements(writer, bean);
        
        // label
        writer.writeStartElement(NS_URI, "label");
        writer.writeCharacters(bean.getLabel());
        writer.writeEndElement();
        
        // codeSpace
        if (bean.isSetCodeSpace())
        {
            writer.writeStartElement(NS_URI, "codeSpace");
            writer.writeAttribute(XLINK_NS_URI, "href", bean.getCodeSpace());
            writer.writeEndElement();
        }
        
        // value
        writer.writeStartElement(NS_URI, "value");
        writer.writeCharacters(bean.getValue());
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for ClassifierListType complex type
     */
    public ClassifierList readClassifierListType(XMLStreamReader reader) throws XMLStreamException
    {
        ClassifierList bean = factory.newClassifierList();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readClassifierListTypeAttributes(attrMap, bean);
        
        reader.nextTag();
        this.readClassifierListTypeElements(reader, bean);
        
        return bean;
    }
    
    
    /**
     * Reads attributes of ClassifierListType complex type
     */
    public void readClassifierListTypeAttributes(Map<String, String> attrMap, ClassifierList bean) throws XMLStreamException
    {
        this.readAbstractMetadataListTypeAttributes(attrMap, bean);
        
    }
    
    
    /**
     * Reads elements of ClassifierListType complex type
     */
    public void readClassifierListTypeElements(XMLStreamReader reader, ClassifierList bean) throws XMLStreamException
    {
        this.readAbstractMetadataListTypeElements(reader, bean);
        
        boolean found;
        
        // classifier
        do
        {
            found = checkElementName(reader, "classifier");
            if (found)
            {
                reader.nextTag();
                if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
                {
                    bean.addClassifier(this.readTerm(reader));
                    reader.nextTag(); // end property tag
                }
                
                reader.nextTag();
            }
        }
        while (found);
    }
    
    
    /**
     * Write method for ClassifierListType complex type
     */
    public void writeClassifierListType(XMLStreamWriter writer, ClassifierList bean) throws XMLStreamException
    {
        this.writeClassifierListTypeAttributes(writer, bean);
        this.writeClassifierListTypeElements(writer, bean);
    }
    
    
    /**
     * Writes attributes of ClassifierListType complex type
     */
    public void writeClassifierListTypeAttributes(XMLStreamWriter writer, ClassifierList bean) throws XMLStreamException
    {
        this.writeAbstractMetadataListTypeAttributes(writer, bean);
    }
    
    
    /**
     * Writes elements of ClassifierListType complex type
     */
    public void writeClassifierListTypeElements(XMLStreamWriter writer, ClassifierList bean) throws XMLStreamException
    {
        this.writeAbstractMetadataListTypeElements(writer, bean);
        int numItems;
        
        // classifier
        numItems = bean.getClassifierList().size();
        for (int i = 0; i < numItems; i++)
        {
            Term item = bean.getClassifierList().get(i);
            writer.writeStartElement(NS_URI, "classifier");
            this.writeTerm(writer, item);
            writer.writeEndElement();
        }
    }
    
    
    /**
     * Read method for AbstractModesType complex type
     */
    public AbstractModes readAbstractModesType(XMLStreamReader reader) throws XMLStreamException
    {
        AbstractModes bean = factory.newAbstractModes();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readAbstractModesTypeAttributes(attrMap, bean);
        
        reader.nextTag();
        this.readAbstractModesTypeElements(reader, bean);
        
        return bean;
    }
    
    
    /**
     * Reads attributes of AbstractModesType complex type
     */
    public void readAbstractModesTypeAttributes(Map<String, String> attrMap, AbstractModes bean) throws XMLStreamException
    {
        ns1Bindings.readAbstractSWETypeAttributes(attrMap, bean);
        
    }
    
    
    /**
     * Reads elements of AbstractModesType complex type
     */
    public void readAbstractModesTypeElements(XMLStreamReader reader, AbstractModes bean) throws XMLStreamException
    {
        ns1Bindings.readAbstractSWETypeElements(reader, bean);
        
    }
    
    
    /**
     * Write method for AbstractModesType complex type
     */
    public void writeAbstractModesType(XMLStreamWriter writer, AbstractModes bean) throws XMLStreamException
    {
        this.writeAbstractModesTypeAttributes(writer, bean);
        this.writeAbstractModesTypeElements(writer, bean);
    }
    
    
    /**
     * Writes attributes of AbstractModesType complex type
     */
    public void writeAbstractModesTypeAttributes(XMLStreamWriter writer, AbstractModes bean) throws XMLStreamException
    {
        ns1Bindings.writeAbstractSWETypeAttributes(writer, bean);
    }
    
    
    /**
     * Writes elements of AbstractModesType complex type
     */
    public void writeAbstractModesTypeElements(XMLStreamWriter writer, AbstractModes bean) throws XMLStreamException
    {
        ns1Bindings.writeAbstractSWETypeElements(writer, bean);
    }
    
    
    /**
     * Read method for DataInterfaceType complex type
     */
    public DataInterface readDataInterfaceType(XMLStreamReader reader) throws XMLStreamException
    {
        DataInterface bean = factory.newDataInterface();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readDataInterfaceTypeAttributes(attrMap, bean);
        
        reader.nextTag();
        this.readDataInterfaceTypeElements(reader, bean);
        
        return bean;
    }
    
    
    /**
     * Reads attributes of DataInterfaceType complex type
     */
    public void readDataInterfaceTypeAttributes(Map<String, String> attrMap, DataInterface bean) throws XMLStreamException
    {
        ns1Bindings.readAbstractSWEIdentifiableTypeAttributes(attrMap, bean);
        
    }
    
    
    /**
     * Reads elements of DataInterfaceType complex type
     */
    public void readDataInterfaceTypeElements(XMLStreamReader reader, DataInterface bean) throws XMLStreamException
    {
        ns1Bindings.readAbstractSWEIdentifiableTypeElements(reader, bean);
        
        boolean found;
        
        // data
        found = checkElementName(reader, "data");
        if (found)
        {
            OgcProperty<DataStream> dataProp = bean.getDataProperty();
            readPropertyAttributes(reader, dataProp);
            
            reader.nextTag();
            if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
            {
                dataProp.setValue(ns1Bindings.readDataStream(reader));
                reader.nextTag(); // end property tag
            }
            
            reader.nextTag();
        }
        
        // interfaceParameters
        found = checkElementName(reader, "interfaceParameters");
        if (found)
        {
            OgcProperty<DataRecord> interfaceParametersProp = bean.getInterfaceParametersProperty();
            readPropertyAttributes(reader, interfaceParametersProp);
            
            reader.nextTag();
            if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
            {
                interfaceParametersProp.setValue(ns1Bindings.readDataRecord(reader));
                reader.nextTag(); // end property tag
            }
            
            reader.nextTag();
        }
    }
    
    
    /**
     * Write method for DataInterfaceType complex type
     */
    public void writeDataInterfaceType(XMLStreamWriter writer, DataInterface bean) throws XMLStreamException
    {
        this.writeDataInterfaceTypeAttributes(writer, bean);
        this.writeDataInterfaceTypeElements(writer, bean);
    }
    
    
    /**
     * Writes attributes of DataInterfaceType complex type
     */
    public void writeDataInterfaceTypeAttributes(XMLStreamWriter writer, DataInterface bean) throws XMLStreamException
    {
        ns1Bindings.writeAbstractSWEIdentifiableTypeAttributes(writer, bean);
    }
    
    
    /**
     * Writes elements of DataInterfaceType complex type
     */
    public void writeDataInterfaceTypeElements(XMLStreamWriter writer, DataInterface bean) throws XMLStreamException
    {
        ns1Bindings.writeAbstractSWEIdentifiableTypeElements(writer, bean);
        
        // data
        writer.writeStartElement(NS_URI, "data");
        OgcProperty<DataStream> dataProp = bean.getDataProperty();
        writePropertyAttributes(writer, dataProp);
        if (dataProp.hasValue() && !dataProp.hasHref())
            ns1Bindings.writeDataStream(writer, bean.getData());
        writer.writeEndElement();
        
        // interfaceParameters
        if (bean.isSetInterfaceParameters())
        {
            writer.writeStartElement(NS_URI, "interfaceParameters");
            OgcProperty<DataRecord> interfaceParametersProp = bean.getInterfaceParametersProperty();
            writePropertyAttributes(writer, interfaceParametersProp);
            if (interfaceParametersProp.hasValue() && !interfaceParametersProp.hasHref())
                ns1Bindings.writeDataRecord(writer, bean.getInterfaceParameters(), true);
            writer.writeEndElement();
        }
    }
    
    
    /**
     * Read method for ObservablePropertyType complex type
     */
    public ObservableProperty readObservablePropertyType(XMLStreamReader reader) throws XMLStreamException
    {
        ObservableProperty bean = factory.newObservableProperty();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readObservablePropertyTypeAttributes(attrMap, bean);
        
        reader.nextTag();
        this.readObservablePropertyTypeElements(reader, bean);
        
        return bean;
    }
    
    
    /**
     * Reads attributes of ObservablePropertyType complex type
     */
    public void readObservablePropertyTypeAttributes(Map<String, String> attrMap, ObservableProperty bean) throws XMLStreamException
    {
        ns1Bindings.readAbstractSWEIdentifiableTypeAttributes(attrMap, bean);
        
        String val;
        
        // definition
        val = attrMap.get("definition");
        if (val != null)
            bean.setDefinition(val);
    }
    
    
    /**
     * Reads elements of ObservablePropertyType complex type
     */
    public void readObservablePropertyTypeElements(XMLStreamReader reader, ObservableProperty bean) throws XMLStreamException
    {
        ns1Bindings.readAbstractSWEIdentifiableTypeElements(reader, bean);
        
    }
    
    
    /**
     * Write method for ObservablePropertyType complex type
     */
    public void writeObservablePropertyType(XMLStreamWriter writer, ObservableProperty bean) throws XMLStreamException
    {
        this.writeObservablePropertyTypeAttributes(writer, bean);
        this.writeObservablePropertyTypeElements(writer, bean);
    }
    
    
    /**
     * Writes attributes of ObservablePropertyType complex type
     */
    public void writeObservablePropertyTypeAttributes(XMLStreamWriter writer, ObservableProperty bean) throws XMLStreamException
    {
        ns1Bindings.writeAbstractSWEIdentifiableTypeAttributes(writer, bean);
        
        // definition
        writer.writeAttribute("definition", getStringValue(bean.getDefinition()));
    }
    
    
    /**
     * Writes elements of ObservablePropertyType complex type
     */
    public void writeObservablePropertyTypeElements(XMLStreamWriter writer, ObservableProperty bean) throws XMLStreamException
    {
        ns1Bindings.writeAbstractSWEIdentifiableTypeElements(writer, bean);
    }
    
    
    /**
     * Read method for SimpleProcess elements
     */
    public SimpleProcess readSimpleProcess(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "SimpleProcess");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return this.readSimpleProcessType(reader);
    }
    
    
    /**
     * Write method for SimpleProcess element
     */
    public void writeSimpleProcess(XMLStreamWriter writer, SimpleProcess bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "SimpleProcess");
        this.writeNamespaces(writer);
        this.writeSimpleProcessType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for ProcessMethod elements
     */
    public ProcessMethod readProcessMethod(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "ProcessMethod");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return this.readProcessMethodType(reader);
    }
    
    
    /**
     * Write method for ProcessMethod element
     */
    public void writeProcessMethod(XMLStreamWriter writer, ProcessMethod bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "ProcessMethod");
        this.writeNamespaces(writer);
        this.writeProcessMethodType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Dispatcher method for reading elements derived from AbstractAlgorithm
     */
    public AbstractAlgorithm readAbstractAlgorithm(XMLStreamReader reader) throws XMLStreamException
    {
        throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
    }
    
    
    /**
     * Dispatcher method for writing classes derived from AbstractAlgorithm
     */
    public void writeAbstractAlgorithm(XMLStreamWriter writer, AbstractAlgorithm bean) throws XMLStreamException
    {
    }
    
    
    /**
     * Read method for Mode elements
     */
    public Mode readMode(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "Mode");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return this.readModeType(reader);
    }
    
    
    /**
     * Write method for Mode element
     */
    public void writeMode(XMLStreamWriter writer, Mode bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "Mode");
        this.writeNamespaces(writer);
        this.writeModeType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for ModeChoice elements
     */
    public ModeChoice readModeChoice(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "ModeChoice");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return this.readModeChoiceType(reader);
    }
    
    
    /**
     * Write method for ModeChoice element
     */
    public void writeModeChoice(XMLStreamWriter writer, ModeChoice bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "ModeChoice");
        this.writeNamespaces(writer);
        this.writeModeChoiceType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for Settings elements
     */
    public Settings readSettings(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "Settings");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return this.readSettingsType(reader);
    }
    
    
    /**
     * Write method for Settings element
     */
    public void writeSettings(XMLStreamWriter writer, Settings bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "Settings");
        this.writeNamespaces(writer);
        this.writeSettingsType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for PhysicalSystem elements
     */
    public PhysicalSystem readPhysicalSystem(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "PhysicalSystem");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return this.readPhysicalSystemType(reader);
    }
    
    
    /**
     * Write method for PhysicalSystem element
     */
    public void writePhysicalSystem(XMLStreamWriter writer, PhysicalSystem bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "PhysicalSystem");
        this.writeNamespaces(writer);
        this.writePhysicalSystemType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for PhysicalComponent elements
     */
    public PhysicalComponent readPhysicalComponent(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "PhysicalComponent");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return this.readPhysicalComponentType(reader);
    }
    
    
    /**
     * Write method for PhysicalComponent element
     */
    public void writePhysicalComponent(XMLStreamWriter writer, PhysicalComponent bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "PhysicalComponent");
        this.writeNamespaces(writer);
        this.writePhysicalComponentType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Dispatcher method for reading elements derived from AbstractPhysicalProcess
     */
    public AbstractPhysicalProcess readAbstractPhysicalProcess(XMLStreamReader reader) throws XMLStreamException
    {
        String localName = reader.getName().getLocalPart();
        
        if ("PhysicalSystem".equals(localName))
            return readPhysicalSystem(reader);
        else if ("PhysicalComponent".equals(localName))
            return readPhysicalComponent(reader);
        
        throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
    }
    
    
    /**
     * Dispatcher method for writing classes derived from AbstractPhysicalProcess
     */
    public void writeAbstractPhysicalProcess(XMLStreamWriter writer, AbstractPhysicalProcess bean) throws XMLStreamException
    {
        if (bean instanceof PhysicalSystem)
            writePhysicalSystem(writer, (PhysicalSystem)bean);
        else if (bean instanceof PhysicalComponent)
            writePhysicalComponent(writer, (PhysicalComponent)bean);
    }
    
    
    /**
     * Read method for TemporalFrame elements
     */
    public TemporalFrame readTemporalFrame(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "TemporalFrame");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return this.readTemporalFrameType(reader);
    }
    
    
    /**
     * Write method for TemporalFrame element
     */
    public void writeTemporalFrame(XMLStreamWriter writer, TemporalFrame bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "TemporalFrame");
        this.writeNamespaces(writer);
        this.writeTemporalFrameType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for SpatialFrame elements
     */
    public SpatialFrame readSpatialFrame(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "SpatialFrame");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return this.readSpatialFrameType(reader);
    }
    
    
    /**
     * Write method for SpatialFrame element
     */
    public void writeSpatialFrame(XMLStreamWriter writer, SpatialFrame bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "SpatialFrame");
        this.writeNamespaces(writer);
        this.writeSpatialFrameType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for AggregateProcess elements
     */
    public AggregateProcess readAggregateProcess(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "AggregateProcess");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return this.readAggregateProcessType(reader);
    }
    
    
    /**
     * Write method for AggregateProcess element
     */
    public void writeAggregateProcess(XMLStreamWriter writer, AggregateProcess bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "AggregateProcess");
        this.writeNamespaces(writer);
        this.writeAggregateProcessType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for Link elements
     */
    public Link readLink(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "Link");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return this.readLinkType(reader);
    }
    
    
    /**
     * Write method for Link element
     */
    public void writeLink(XMLStreamWriter writer, Link bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "Link");
        this.writeNamespaces(writer);
        this.writeLinkType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Dispatcher method for reading elements derived from DescribedObject
     */
    public DescribedObject readDescribedObject(XMLStreamReader reader) throws XMLStreamException
    {
        String localName = reader.getName().getLocalPart();
        
        if ("SimpleProcess".equals(localName))
            return readSimpleProcess(reader);
        else if ("Mode".equals(localName))
            return readMode(reader);
        else if ("PhysicalSystem".equals(localName))
            return readPhysicalSystem(reader);
        else if ("PhysicalComponent".equals(localName))
            return readPhysicalComponent(reader);
        else if ("AggregateProcess".equals(localName))
            return readAggregateProcess(reader);
        
        throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
    }
    
    
    /**
     * Dispatcher method for writing classes derived from DescribedObject
     */
    public void writeDescribedObject(XMLStreamWriter writer, DescribedObject bean) throws XMLStreamException
    {
        if (bean instanceof SimpleProcess)
            writeSimpleProcess(writer, (SimpleProcess)bean);
        else if (bean instanceof Mode)
            writeMode(writer, (Mode)bean);
        else if (bean instanceof PhysicalSystem)
            writePhysicalSystem(writer, (PhysicalSystem)bean);
        else if (bean instanceof PhysicalComponent)
            writePhysicalComponent(writer, (PhysicalComponent)bean);
        else if (bean instanceof AggregateProcess)
            writeAggregateProcess(writer, (AggregateProcess)bean);
    }
    
    
    /**
     * Dispatcher method for reading elements derived from AbstractProcess
     */
    public AbstractProcess readAbstractProcess(XMLStreamReader reader) throws XMLStreamException
    {
        String localName = reader.getName().getLocalPart();
        
        if ("SimpleProcess".equals(localName))
            return readSimpleProcess(reader);
        else if ("PhysicalSystem".equals(localName))
            return readPhysicalSystem(reader);
        else if ("PhysicalComponent".equals(localName))
            return readPhysicalComponent(reader);
        else if ("AggregateProcess".equals(localName))
            return readAggregateProcess(reader);
        
        throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
    }
    
    
    /**
     * Dispatcher method for writing classes derived from AbstractProcess
     */
    public void writeAbstractProcess(XMLStreamWriter writer, AbstractProcess bean) throws XMLStreamException
    {
        if (bean instanceof PhysicalComponent)
            writePhysicalComponent(writer, (PhysicalComponent)bean);
        else if (bean instanceof PhysicalSystem)
            writePhysicalSystem(writer, (PhysicalSystem)bean);
        else if (bean instanceof SimpleProcess)
            writeSimpleProcess(writer, (SimpleProcess)bean); 
        else if (bean instanceof AggregateProcess)
            writeAggregateProcess(writer, (AggregateProcess)bean);        
    }
    
    
    /**
     * Read method for AbstractSettings elements
     */
    public AbstractSettings readAbstractSettings(XMLStreamReader reader) throws XMLStreamException
    {
        String localName = reader.getName().getLocalPart();
        
        if ("Settings".equals(localName))
            return readSettings(reader);
        
        throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
    }
    
    
    /**
     * Write method for AbstractSettings element
     */
    public void writeAbstractSettings(XMLStreamWriter writer, AbstractSettings bean) throws XMLStreamException
    {
        if (bean instanceof Settings)
            writeSettings(writer, (Settings)bean);
    }
    
    
    /**
     * Read method for IdentifierList elements
     */
    public IdentifierList readIdentifierList(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "IdentifierList");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return this.readIdentifierListType(reader);
    }
    
    
    /**
     * Write method for IdentifierList element
     */
    public void writeIdentifierList(XMLStreamWriter writer, IdentifierList bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "IdentifierList");
        this.writeNamespaces(writer);
        this.writeIdentifierListType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for DocumentList elements
     */
    public DocumentList readDocumentList(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "DocumentList");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return this.readDocumentListType(reader);
    }
    
    
    /**
     * Write method for DocumentList element
     */
    public void writeDocumentList(XMLStreamWriter writer, DocumentList bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "DocumentList");
        this.writeNamespaces(writer);
        this.writeDocumentListType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for CharacteristicList elements
     */
    public CharacteristicList readCharacteristicList(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "CharacteristicList");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return this.readCharacteristicListType(reader);
    }
    
    
    /**
     * Write method for CharacteristicList element
     */
    public void writeCharacteristicList(XMLStreamWriter writer, CharacteristicList bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "CharacteristicList");
        this.writeNamespaces(writer);
        this.writeCharacteristicListType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for FeatureList elements
     */
    public FeatureList readFeatureList(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "FeatureList");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return this.readFeatureListType(reader);
    }
    
    
    /**
     * Write method for FeatureList element
     */
    public void writeFeatureList(XMLStreamWriter writer, FeatureList bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "FeatureList");
        this.writeNamespaces(writer);
        this.writeFeatureListType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for Event elements
     */
    public Event readEvent(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "Event");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return this.readEventType(reader);
    }
    
    
    /**
     * Write method for Event element
     */
    public void writeEvent(XMLStreamWriter writer, Event bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "Event");
        this.writeNamespaces(writer);
        this.writeEventType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for CapabilityList elements
     */
    public CapabilityList readCapabilityList(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "CapabilityList");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return this.readCapabilityListType(reader);
    }
    
    
    /**
     * Write method for CapabilityList element
     */
    public void writeCapabilityList(XMLStreamWriter writer, CapabilityList bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "CapabilityList");
        this.writeNamespaces(writer);
        this.writeCapabilityListType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for EventList elements
     */
    public EventList readEventList(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "EventList");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return this.readEventListType(reader);
    }
    
    
    /**
     * Write method for EventList element
     */
    public void writeEventList(XMLStreamWriter writer, EventList bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "EventList");
        this.writeNamespaces(writer);
        this.writeEventListType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for AbstractMetadataList elements
     */
    public AbstractMetadataList readAbstractMetadataList(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "AbstractMetadataList");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return this.readAbstractMetadataListType(reader);
    }
    
    
    /**
     * Write method for AbstractMetadataList element
     */
    public void writeAbstractMetadataList(XMLStreamWriter writer, AbstractMetadataList bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "AbstractMetadataList");
        this.writeNamespaces(writer);
        this.writeAbstractMetadataListType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for ContactList elements
     */
    public ContactList readContactList(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "ContactList");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return this.readContactListType(reader);
    }
    
    
    /**
     * Write method for ContactList element
     */
    public void writeContactList(XMLStreamWriter writer, ContactList bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "ContactList");
        this.writeNamespaces(writer);
        this.writeContactListType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for KeywordList elements
     */
    public KeywordList readKeywordList(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "KeywordList");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return this.readKeywordListType(reader);
    }
    
    
    /**
     * Write method for KeywordList element
     */
    public void writeKeywordList(XMLStreamWriter writer, KeywordList bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "KeywordList");
        this.writeNamespaces(writer);
        this.writeKeywordListType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for Term elements
     */
    public Term readTerm(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "Term");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return this.readTermType(reader);
    }
    
    
    /**
     * Write method for Term element
     */
    public void writeTerm(XMLStreamWriter writer, Term bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "Term");
        this.writeNamespaces(writer);
        this.writeTermType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for ClassifierList elements
     */
    public ClassifierList readClassifierList(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "ClassifierList");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return this.readClassifierListType(reader);
    }
    
    
    /**
     * Write method for ClassifierList element
     */
    public void writeClassifierList(XMLStreamWriter writer, ClassifierList bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "ClassifierList");
        this.writeNamespaces(writer);
        this.writeClassifierListType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for AbstractModes elements
     */
    public AbstractModes readAbstractModes(XMLStreamReader reader) throws XMLStreamException
    {
        String localName = reader.getName().getLocalPart();
        
        if ("ModeChoice".equals(localName))
            return readModeChoice(reader);
        
        throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
    }
    
    
    /**
     * Write method for AbstractModes element
     */
    public void writeAbstractModes(XMLStreamWriter writer, AbstractModes bean) throws XMLStreamException
    {
        if (bean instanceof ModeChoice)
            writeModeChoice(writer, (ModeChoice)bean);
    }
    
    
    /**
     * Read method for DataInterface elements
     */
    public DataInterface readDataInterface(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "DataInterface");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return this.readDataInterfaceType(reader);
    }
    
    
    /**
     * Write method for DataInterface element
     */
    public void writeDataInterface(XMLStreamWriter writer, DataInterface bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "DataInterface");
        this.writeNamespaces(writer);
        this.writeDataInterfaceType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for ObservableProperty elements
     */
    public ObservableProperty readObservableProperty(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "ObservableProperty");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return this.readObservablePropertyType(reader);
    }
    
    
    /**
     * Write method for ObservableProperty element
     */
    public void writeObservableProperty(XMLStreamWriter writer, ObservableProperty bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "ObservableProperty");
        this.writeNamespaces(writer);
        this.writeObservablePropertyType(writer, bean);
        writer.writeEndElement();
    }
    
    ///////////////////////////////////////////////////////////////////
    // Methods added manually for incorrectly generated simple types //
    ///////////////////////////////////////////////////////////////////
    
    public String readAxis(XMLStreamReader reader) throws XMLStreamException
    {
        return trimStringValue(reader.getElementText());
    }
    
    
    public void writeAxis(XMLStreamWriter writer, String value) throws XMLStreamException
    {
        writer.writeCharacters(value);        
    }
    
    
    public String readDataComponentRefPropertyType(XMLStreamReader reader) throws XMLStreamException
    {
        return reader.getAttributeValue(null, "ref");
    }
    
    
    public void writeDataComponentRefPropertyType(XMLStreamWriter writer, String ref) throws XMLStreamException
    {
        writer.writeAttribute("ref", ref);   
    }
    
    
    public StatusSetting readStatusSettingPropertyType(XMLStreamReader reader) throws XMLStreamException
    {
        StatusSetting bean = factory.newStatusSetting();
        String val;
        
        // ref        
        Map<String, String> attrMap = collectAttributes(reader);
        val = attrMap.get("ref");
        if (val != null)
            bean.setRef(val);
        
        // inline value
        val = reader.getElementText();
        if (val != null)
            bean.setValue(Status.fromString(val));
        
        return bean;
    }    
    
    
    public void writeStatusSettingPropertyType(XMLStreamWriter writer, StatusSetting bean) throws XMLStreamException
    {
        // ref
        writer.writeAttribute("ref", getStringValue(bean.getRef()));
        
        // inline value
        writer.writeCharacters(getStringValue(bean.getValue()));      
    }
    
    
    public Object readSecurityConstraints(XMLStreamReader reader) throws XMLStreamException
    {
        return readExtension(reader);
    }
    
    
    public void writeSecurityConstraints(XMLStreamWriter writer, Object item) throws XMLStreamException
    {
        writeExtension(writer, item);        
    }
}

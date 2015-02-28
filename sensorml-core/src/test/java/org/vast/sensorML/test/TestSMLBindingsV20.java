/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are subject to the Mozilla Public License Version
 1.1 (the "License"); you may not use this file except in compliance with
 the License. You may obtain a copy of the License at
 http://www.mozilla.org/MPL/MPL-1.1.html
 
 Software distributed under the License is distributed on an "AS IS" basis,
 WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 for the specific language governing rights and limitations under the License.
 
 The Original Code is the "OGC Service Framework".
 
 The Initial Developer of the Original Code is the VAST team at the
 University of Alabama in Huntsville (UAH). <http://vast.uah.edu>
 Portions created by the Initial Developer are Copyright (C) 2007
 the Initial Developer. All Rights Reserved.

 Please Contact Mike Botts <mike.botts@uah.edu>
 or Alexandre Robin <alex.robin@sensiasoftware.com> for more information.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.sensorML.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import net.opengis.NamespaceRegister;
import net.opengis.gml.v32.Point;
import net.opengis.gml.v32.impl.PointImpl;
import net.opengis.gml.v32.impl.ReferenceImpl;
import net.opengis.sensorml.v20.AbstractProcess;
import net.opengis.sensorml.v20.CharacteristicList;
import net.opengis.sensorml.v20.ContactList;
import net.opengis.sensorml.v20.Link;
import net.opengis.sensorml.v20.ObservableProperty;
import net.opengis.sensorml.v20.PhysicalComponent;
import net.opengis.sensorml.v20.PhysicalSystem;
import net.opengis.sensorml.v20.Settings;
import net.opengis.sensorml.v20.SpatialFrame;
import net.opengis.sensorml.v20.Status;
import net.opengis.sensorml.v20.impl.CharacteristicListImpl;
import net.opengis.sensorml.v20.impl.ConstraintSettingImpl;
import net.opengis.sensorml.v20.impl.ContactListImpl;
import net.opengis.sensorml.v20.impl.LinkImpl;
import net.opengis.sensorml.v20.impl.ModeSettingImpl;
import net.opengis.sensorml.v20.impl.ObservablePropertyImpl;
import net.opengis.sensorml.v20.impl.SettingsImpl;
import net.opengis.sensorml.v20.impl.SpatialFrameImpl;
import net.opengis.sensorml.v20.impl.StatusSettingImpl;
import net.opengis.sensorml.v20.impl.ValueSettingImpl;
import net.opengis.swe.v20.AllowedValues;
import net.opengis.swe.v20.DataRecord;
import net.opengis.swe.v20.Quantity;
import net.opengis.swe.v20.Time;
import org.custommonkey.xmlunit.Validator;
import org.custommonkey.xmlunit.XMLTestCase;
import org.custommonkey.xmlunit.XMLUnit;
import org.isotc211.v2005.gco.impl.CodeListValueImpl;
import org.isotc211.v2005.gmd.CIResponsibleParty;
import org.isotc211.v2005.gmd.impl.CIResponsiblePartyImpl;
import org.vast.data.AllowedValuesImpl;
import org.vast.data.DataRecordImpl;
import org.vast.data.QuantityImpl;
import org.vast.data.TimeImpl;
import org.vast.sensorML.PhysicalComponentImpl;
import org.vast.sensorML.PhysicalSystemImpl;
import org.vast.sensorML.SMLStaxBindings;
import org.vast.xml.IndentingXMLStreamWriter;
import org.xml.sax.InputSource;


public class TestSMLBindingsV20 extends XMLTestCase
{
    
    public void setUp() throws Exception
    {
        XMLUnit.setIgnoreComments(true);
        XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setNormalizeWhitespace(true);
        XMLUnit.setIgnoreAttributeOrder(true);
    }
    
    
    protected void validate(InputStream is, String schemaUrl) throws Exception
    {
        InputSource saxIs = new InputSource(is);
        Validator v = new Validator(saxIs);
        v.useXMLSchema(true);
        v.setJAXP12SchemaSource(schemaUrl);
        assertTrue(v.isValid());
    }
    
    
    protected void readWriteCompareProcessXml(String path) throws Exception
    {
        SMLStaxBindings smlHelper = new SMLStaxBindings();
                
        // read from file
        InputStream is = getClass().getResourceAsStream(path);
        XMLInputFactory input = XMLInputFactory.newInstance();//new com.ctc.wstx.stax.WstxInputFactory();
        XMLStreamReader reader = input.createXMLStreamReader(is);
        reader.nextTag();
        AbstractProcess smlObj = smlHelper.readAbstractProcess(reader);
        is.close();
        
        // write back to stdout and buffer
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        System.out.println();
        XMLOutputFactory output = XMLOutputFactory.newInstance();//new com.ctc.wstx.stax.WstxOutputFactory();
        NamespaceRegister nsContext = new NamespaceRegister();
        nsContext.registerNamespace("xlink", net.opengis.swe.v20.bind.XMLStreamBindings.XLINK_NS_URI);
        nsContext.registerNamespace("sml", net.opengis.sensorml.v20.bind.XMLStreamBindings.NS_URI);
        nsContext.registerNamespace("swe", net.opengis.swe.v20.bind.XMLStreamBindings.NS_URI);
        nsContext.registerNamespace("gml", net.opengis.gml.v32.bind.XMLStreamBindings.NS_URI);
        nsContext.registerNamespace("gco", org.isotc211.v2005.gco.bind.XMLStreamBindings.NS_URI);
        nsContext.registerNamespace("gmd", org.isotc211.v2005.gmd.bind.XMLStreamBindings.NS_URI);
        
        XMLStreamWriter writer = output.createXMLStreamWriter(os);
        writer.setNamespaceContext(nsContext);
        writer.writeStartDocument();
        smlHelper.declareNamespacesOnRootElement();
        smlHelper.writeAbstractProcess(writer, smlObj);
        writer.writeEndDocument();
        writer.close();
        
        writer = new IndentingXMLStreamWriter(output.createXMLStreamWriter(System.out));
        writer.setNamespaceContext(nsContext);
        writer.writeStartDocument();
        smlHelper.declareNamespacesOnRootElement();
        smlHelper.writeAbstractProcess(writer, smlObj);
        writer.writeEndDocument();        
        writer.close();
        
        // compare with original
        InputSource src1 = new InputSource(getClass().getResourceAsStream(path));
        InputSource src2 = new InputSource(new ByteArrayInputStream(os.toByteArray()));
        assertXMLEqual(src1, src2);
    }
    
    
    public void testReadWriteSimpleSensor() throws Exception
    {
        readWriteCompareProcessXml("examples_v20/SimpleSensor.xml");
    }
    
    
    public void testReadWriteGammaSensor() throws Exception
    {
        //readWriteCompareProcessXml("examples_v20/gamma2070.xml");
        readWriteCompareProcessXml("examples_v20/gamma2070_more.xml");
    }
    
    
    public void testReadWriteCameraSensor() throws Exception
    {
        readWriteCompareProcessXml("examples_v20/KCM-HD_Camera_inline.xml");
    }
    
    
    public void testReadWriteConfiguredCameraSensor() throws Exception
    {
        readWriteCompareProcessXml("examples_v20/KCM-HD Camera.xml");
    }
    
    
    public void testReadWriteSensorWithModes() throws Exception
    {
        readWriteCompareProcessXml("examples_v20/SensorWithModes.xml");
    } 
    
    
    public void testReadWriteDerivedInstance() throws Exception
    {
        readWriteCompareProcessXml("examples_v20/OwnerInstance.xml");
    }
    
    
    public void testReadWriteDavisSensor() throws Exception
    {
        readWriteCompareProcessXml("examples_v20/Davis_7817_complete.xml");
    } 
    
    
    public void testReadWriteWeatherStation() throws Exception
    {
        readWriteCompareProcessXml("examples_v20/WeatherStation.xml");
    } 
    
    
    public void testReadWriteModeInstance() throws Exception
    {
        readWriteCompareProcessXml("examples_v20/ModeInstance.xml");
    }
    
    
    public void testReadWriteSensorwithDataStreamOutput() throws Exception
    {
        readWriteCompareProcessXml("examples_v20/SimpleStreaming RS232.xml");
    }
    
    
    public void testGenerateInstance() throws Exception
    {
        SMLStaxBindings smlBindings = new SMLStaxBindings();
                        
        XMLOutputFactory output = XMLOutputFactory.newInstance();//new com.ctc.wstx.stax.WstxOutputFactory();
        XMLInputFactory input = XMLInputFactory.newInstance();//new com.ctc.wstx.stax.WstxInputFactory();
        System.out.println("Using " + output.getClass().getSimpleName());
        
        PhysicalSystem system = new PhysicalSystemImpl();
        system.setId("MY_SYSTEM");
        
        // characteristics
        CharacteristicList mechSpecs = new CharacteristicListImpl();
        Quantity weightSpec = new QuantityImpl();
        weightSpec.setDefinition("http://sweet.jpl.nasa.gov/2.3/propMass.owl#Mass");
        weightSpec.getUom().setCode("kg");
        weightSpec.setValue(12.3);
        mechSpecs.addCharacteristic("weight", weightSpec);
        system.addCharacteristics("mechanical", mechSpecs);
        
        // contact
        ContactList contacts = new ContactListImpl();
        CIResponsibleParty contact = new CIResponsiblePartyImpl();
        contact.setIndividualName("Gérard Blanquet");
        contact.setOrganisationName("Time Soft S.A.");
        contact.getContactInfo().getAddress().addDeliveryPoint("10 rue du Nord");
        contact.getContactInfo().getAddress().setPostalCode("75896");
        contact.getContactInfo().getAddress().setCity("Paris");
        contact.getContactInfo().getAddress().setCountry("FRANCE");
        contact.setRole(new CodeListValueImpl("operator"));
        contacts.addContact(contact);
        system.addContacts(contacts);
        
        // input
        ObservableProperty obs = new ObservablePropertyImpl();
        obs.setDefinition("http://mmisw.org/ont/cf/parameter/weather");
        system.addInput("weather_phenomena", obs);
        system.getInputList().add("rain", "http://remotedef.xml", null);
        
        // outputs                
        DataRecord rec = new DataRecordImpl();
        rec.setLabel("Weather Data Record");
        rec.setDescription("Record of synchronous weather measurements");   
        
        Time t = new TimeImpl();
        t.setDefinition("http://www.opengis.net/def/property/OGC/0/SamplingTime");
        t.setReferenceFrame("http://www.opengis.net/def/trs/OGC/0/GPS");
        t.setLabel("Sampling Time");
        t.getUom().setHref("http://www.opengis.net/def/uom/ISO-8601/0/Gregorian");
        rec.addField("time", t);
        
        Quantity q1 = new QuantityImpl();
        q1.setDefinition("http://mmisw.org/ont/cf/parameter/air_temperature");
        q1.setLabel("Air Temperature");
        q1.getUom().setCode("Cel");
        Quantity acc = new QuantityImpl();
        acc.setDefinition("http://mmisw.org/ont/cf/parameter/accuracy");
        acc.getUom().setCode("%");
        q1.addQuality(acc);
        rec.addField("temp", q1);
        
        Quantity q2 = new QuantityImpl();
        q2.setDefinition("http://mmisw.org/ont/cf/parameter/air_pressure_at_sea_level");
        q2.setLabel("Air Pressure");
        q2.getUom().setCode("mbar");
        rec.addField("press", q2);
        
        Quantity q3 = new QuantityImpl();
        q3.setDefinition("http://mmisw.org/ont/cf/parameter/wind_speed");
        q3.setLabel("Wind Speed");
        q3.getUom().setCode("km/h");
        rec.addField("wind_speed", q3);
        
        Quantity q4 = new QuantityImpl();
        q4.setDefinition("http://mmisw.org/ont/cf/parameter/wind_to_direction");
        q4.setLabel("Wind Direction");
        q4.getUom().setCode("deg");
        rec.addField("wind_dir", q4);
        
        system.addOutput("weather_data", rec);
        system.getOutputList().add("status_info", "http://remotedef.xml", null);
        
        // reference frame
        SpatialFrame systemFrame = new SpatialFrameImpl();
        systemFrame.setId("SYSTEM_FRAME");
        systemFrame.setLabel("System Reference Frame");
        systemFrame.setDescription("Cartesian reference frame attached to system assembly");
        systemFrame.setOrigin("Origin is located on the red marking at the bottom of the aluminum chassis");
        systemFrame.addAxis("x", "X axis is aligned with the horizontal edge of the chassis (see marking)");
        systemFrame.addAxis("y", "Y axis is orthogonal to both X and Y in order to form a direct orthogonal frame");
        systemFrame.addAxis("z", "Z axis is pointing toward the top of the assembly, aligned with the vertical edge of the aluminum frame");
        system.addLocalReferenceFrame(systemFrame);
        
        // position
        Point pos = new PointImpl();
        pos.setId("P01");
        pos.setSrsName("http://www.opengis.net/def/crs/EPSG/0/4326");
        pos.setPos(new double[] {45.6, 2.3});
        system.addPositionAsPoint(pos);
        
        // sub-component
        PhysicalComponent sensor = new PhysicalComponentImpl();
        sensor.setId("SENS01");
        sensor.setTypeOf(new ReferenceImpl("http://www.mymanufacturer.net/mysensor001.xml"));
        Settings config = new SettingsImpl();
        config.addSetValue(new ValueSettingImpl("parameters/samplingRate", "10.0"));
        config.addSetStatus(new StatusSettingImpl("parameters/active", Status.ENABLED));
        //config.addSetArrayValues(new ArraySettingImpl("parameters/calibrationTable", new EncodedValuesImpl("10.0, 20.0")));
        config.addSetMode(new ModeSettingImpl("modes/choice1", "highAccuracy"));
        AllowedValues newConstraint = new AllowedValuesImpl();
        newConstraint.addValue(5.0);
        newConstraint.addValue(10.0);
        newConstraint.addValue(20.0);
        config.addSetConstraint(new ConstraintSettingImpl("parameters/samplingRate", newConstraint));
        sensor.setConfiguration(config);
        system.addComponent("sensor1", sensor);
        
        // connections
        Link link = new LinkImpl();
        link.setSource("sensor1/outputs/temp");
        link.setDestination("outputs/weather_data/temp");
        system.addConnection(link);
        
        // write to byte array
        ByteArrayOutputStream os1 = new ByteArrayOutputStream(10000);
        XMLStreamWriter writer = output.createXMLStreamWriter(os1);
        smlBindings.setNamespacePrefixes(writer);
        writer.writeStartDocument();            
        smlBindings.declareNamespacesOnRootElement();
        smlBindings.writePhysicalSystem(writer, system);
        writer.writeEndDocument();
        
        // write to sysout
        /*for (byte b: os1.toByteArray())
            System.out.print((char)b);
        System.out.println();
        System.out.println();*/
        
        // read back
        ByteArrayInputStream is = new ByteArrayInputStream(os1.toByteArray());
        XMLStreamReader reader = input.createXMLStreamReader(is);
        reader.nextTag();
        system = smlBindings.readPhysicalSystem(reader);
        
        // write back to byte array
        ByteArrayOutputStream os2 = new ByteArrayOutputStream(10000);
        writer = output.createXMLStreamWriter(os2);
        smlBindings.setNamespacePrefixes(writer);
        writer.writeStartDocument();            
        smlBindings.declareNamespacesOnRootElement();
        smlBindings.writePhysicalSystem(writer, system);
        writer.writeEndDocument();
        
        // write back to sysout
        writer = new IndentingXMLStreamWriter(output.createXMLStreamWriter(System.out));
        smlBindings.setNamespacePrefixes(writer);
        writer.writeStartDocument();            
        smlBindings.declareNamespacesOnRootElement();
        smlBindings.writePhysicalSystem(writer, system);
        writer.writeEndDocument();
        System.out.println();
        
        // compare with original
        InputSource src1 = new InputSource(new ByteArrayInputStream(os1.toByteArray()));
        InputSource src2 = new InputSource(new ByteArrayInputStream(os2.toByteArray()));
        assertXMLEqual(src1, src2);
    }
}

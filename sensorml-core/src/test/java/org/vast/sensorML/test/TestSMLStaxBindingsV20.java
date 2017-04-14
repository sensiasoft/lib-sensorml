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
import net.opengis.gml.v32.Point;
import net.opengis.gml.v32.impl.GMLFactory;
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
import net.opengis.swe.v20.AllowedValues;
import net.opengis.swe.v20.DataRecord;
import net.opengis.swe.v20.Quantity;
import org.custommonkey.xmlunit.Validator;
import org.custommonkey.xmlunit.XMLTestCase;
import org.custommonkey.xmlunit.XMLUnit;
import org.isotc211.v2005.gco.impl.CodeListValueImpl;
import org.isotc211.v2005.gmd.CIResponsibleParty;
import org.vast.sensorML.SMLHelper;
import org.vast.sensorML.SMLUtils;
import org.vast.swe.SWEHelper;
import org.xml.sax.InputSource;


public class TestSMLStaxBindingsV20 extends XMLTestCase
{
    
    
    @Override
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
        SMLUtils smlUtils = new SMLUtils(SMLUtils.V2_0);
                
        // read from file
        InputStream is = getClass().getResourceAsStream(path);
        AbstractProcess smlObj = smlUtils.readProcess(is);
        is.close();
        
        // write back to stdout and buffer
        System.out.println();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        smlUtils.writeProcess(os, smlObj, false);
        smlUtils.writeProcess(System.out, smlObj, true);
        
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
        SMLUtils smlUtils = new SMLUtils(SMLUtils.V2_0);
        
        SMLHelper smlFac = new SMLHelper();
        SWEHelper sweFac = new SWEHelper();
        GMLFactory gmlFac = new GMLFactory();
        
        PhysicalSystem system = smlFac.newPhysicalSystem();
        system.setId("MY_SYSTEM");
        
        // characteristics
        CharacteristicList mechSpecs = smlFac.newCharacteristicList();
        Quantity weightSpec = sweFac.newQuantity("http://sweet.jpl.nasa.gov/2.3/propMass.owl#Mass", "Weight", null, "kg");
        weightSpec.setValue(12.3);
        mechSpecs.addCharacteristic("weight", weightSpec);
        system.addCharacteristics("mechanical", mechSpecs);
        
        // contact
        ContactList contacts = smlFac.newContactList();
        CIResponsibleParty contact = smlFac.newResponsibleParty();
        contact.setIndividualName("Gérard Blanquet");
        contact.setOrganisationName("Time Soft S.A.");
        contact.getContactInfo().getAddress().addDeliveryPoint("10 rue du Nord");
        contact.getContactInfo().getAddress().setPostalCode("75896");
        contact.getContactInfo().getAddress().setCity("Paris");
        contact.getContactInfo().getAddress().setCountry("FRANCE");
        contact.setRole(new CodeListValueImpl("operator"));
        contacts.addContact(contact);
        system.addContacts(contacts);
        
        // inputs
        ObservableProperty obs = smlFac.newObservableProperty();
        obs.setDefinition("http://mmisw.org/ont/cf/parameter/weather");
        system.addInput("weather_phenomena", obs);
        system.getInputList().add("rain", "http://remotedef.xml", null);
        
        // outputs
        // create output record and set description                
        DataRecord rec = sweFac.newDataRecord();
        rec.setLabel("Weather Data Record");
        rec.setDescription("Record of synchronous weather measurements");
                
        // sampling time
        rec.addField("time", sweFac.newTimeStampIsoUTC());
        
        // temperature measurement
        rec.addField("temp", sweFac.newQuantity("http://mmisw.org/ont/cf/parameter/air_temperature", "Air Temperature", null, "Cel"));
        
        // pressure
        rec.addField("press", sweFac.newQuantity("http://mmisw.org/ont/cf/parameter/air_pressure_at_sea_level", "Air Pressure", null, "mbar"));
        
        // wind speed
        rec.addField("wind_speed", sweFac.newQuantity("http://mmisw.org/ont/cf/parameter/wind_speed", "Wind Speed", null, "km/h"));
        
        // wind direction
        rec.addField("wind_dir", sweFac.newQuantity("http://mmisw.org/ont/cf/parameter/wind_to_direction", "Wind Direction", null, "deg"));
        
        // add accuracy info to temp output
        Quantity acc = sweFac.newQuantity("http://mmisw.org/ont/cf/parameter/accuracy", "Accuracy", null, "%");
        ((Quantity)rec.getField("temp")).addQuality(acc);
        
        // add as output
        system.addOutput("weather_data", rec);                      
        system.getOutputList().add("status_info", "http://remotedef.xml", null);
        
        // parameters
        system.addParameter("samplingPeriod", sweFac.newQuantity("http://sensorml.com/ont/swe/property/SamplingPeriod", "Sampling Period", null, "s"));
       
        
        // reference frame
        SpatialFrame systemFrame = smlFac.newSpatialFrame();
        systemFrame.setId("SYSTEM_FRAME");
        systemFrame.setLabel("System Reference Frame");
        systemFrame.setDescription("Cartesian reference frame attached to system assembly");
        systemFrame.setOrigin("Origin is located on the red marking at the bottom of the aluminum chassis");
        systemFrame.addAxis("x", "X axis is aligned with the horizontal edge of the chassis (see marking)");
        systemFrame.addAxis("y", "Y axis is orthogonal to both X and Y in order to form a direct orthogonal frame");
        systemFrame.addAxis("z", "Z axis is pointing toward the top of the assembly, aligned with the vertical edge of the aluminum frame");
        system.addLocalReferenceFrame(systemFrame);
        
        // position
        Point pos = gmlFac.newPoint();
        pos.setId("P01");
        pos.setSrsName("http://www.opengis.net/def/crs/EPSG/0/4326");
        pos.setPos(new double[] {45.6, 2.3});
        system.addPositionAsPoint(pos);
        
        
        // sub-component
        PhysicalComponent sensor = smlFac.newPhysicalComponent();
        sensor.setId("SENS01");
        sensor.setTypeOf(new ReferenceImpl("http://www.mymanufacturer.net/mysensor001.xml"));
        Settings config = smlFac.newSettings();
        config.addSetValue(smlFac.newValueSetting("parameters/samplingRate", "10.0"));
        config.addSetStatus(smlFac.newStatusSetting("parameters/active", Status.ENABLED));
        //config.addSetArrayValues(new ArraySettingImpl("parameters/calibrationTable", new EncodedValuesImpl("10.0, 20.0")));
        config.addSetMode(smlFac.newModeSetting("modes/choice1", "highAccuracy"));
        AllowedValues newConstraint = sweFac.newAllowedValues();
        newConstraint.addValue(5.0);
        newConstraint.addValue(10.0);
        newConstraint.addValue(20.0);
        config.addSetConstraint(smlFac.newConstraintSetting("parameters/samplingRate", newConstraint));
        sensor.setConfiguration(config);
        system.addComponent("sensor1", sensor);
        
        // connections
        Link link = smlFac.newLink();
        link.setSource("sensor1/outputs/temp");
        link.setDestination("outputs/weather_data/temp");
        system.addConnection(link);
        
        // write to byte array
        ByteArrayOutputStream os1 = new ByteArrayOutputStream(10000);
        smlUtils.writeProcess(os1, system, false);
        
        // write to sysout
        /*for (byte b: os1.toByteArray())
            System.out.print((char)b);
        System.out.println();
        System.out.println();*/
        
        // read back
        ByteArrayInputStream is = new ByteArrayInputStream(os1.toByteArray());
        system = (PhysicalSystem)smlUtils.readProcess(is);
        
        // write back to byte array
        ByteArrayOutputStream os2 = new ByteArrayOutputStream(10000);
        smlUtils.writeProcess(os2, system, false);
        
        // write back to sysout
        smlUtils.writeProcess(System.out, system, true);
        System.out.println();
        
        // compare with original
        InputSource src1 = new InputSource(new ByteArrayInputStream(os1.toByteArray()));
        InputSource src2 = new InputSource(new ByteArrayInputStream(os2.toByteArray()));
        assertXMLEqual(src1, src2);
    }
}

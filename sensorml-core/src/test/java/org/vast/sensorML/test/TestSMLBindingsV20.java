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
import org.custommonkey.xmlunit.Validator;
import org.custommonkey.xmlunit.XMLTestCase;
import org.custommonkey.xmlunit.XMLUnit;
import org.vast.data.ConstraintList;
import org.vast.ogc.om.IObservation;
import org.vast.ogc.om.OMUtils;
import org.vast.sensorML.SMLUtils;
import org.vast.sensorML.SystemReaderV20;
import org.vast.sensorML.configuration.Configuration;
import org.vast.sensorML.configuration.ConfigurationReaderV20;
import org.vast.sensorML.configuration.ConstraintSetting;
import org.vast.sensorML.configuration.Mode;
import org.vast.sensorML.configuration.ModeSetting;
import org.vast.sensorML.configuration.StatusSetting;
import org.vast.sensorML.configuration.ValueSetting;
import org.vast.sensorML.system.SMLPhysicalComponent;
import org.vast.sensorML.system.SMLSystem;
import org.vast.sweCommon.IntervalConstraint;
import org.vast.xml.DOMHelper;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;


public class TestSMLBindingsV20 extends XMLTestCase
{
    
    public void setUp() throws Exception
    {
        XMLUnit.setIgnoreComments(true);
        XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setNormalizeWhitespace(true);
    }
    
    
    protected void validate(InputStream is, String schemaUrl) throws Exception
    {
        InputSource saxIs = new InputSource(is);
        Validator v = new Validator(saxIs);
        v.useXMLSchema(true);
        v.setJAXP12SchemaSource(schemaUrl);
        assertTrue(v.isValid());
    }
    
    
    protected void readWriteCompareXml(String path) throws Exception
    {
        SMLUtils utils = new SMLUtils("2.0");
        utils.setReadMetadata(true);
        
        InputStream is = getClass().getResourceAsStream(path);
        DOMHelper dom1 = new DOMHelper(is, false);
        SMLSystem sys = utils.readSystem(dom1, dom1.getBaseElement());
        is.close();
        
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        System.out.println();
        utils.write(System.out, sys);
        utils.write(os, sys);
        os.close();
        
        DOMHelper dom2 = new DOMHelper(new ByteArrayInputStream(os.toByteArray()), false);
        assertXMLEqual(dom1.getDocument(), dom2.getDocument());
    }
    
    
    protected Configuration readSettings(String filePath, String domPath) throws Exception
    {
        ConfigurationReaderV20 reader = new ConfigurationReaderV20();        
        InputStream is = TestSMLBindingsV20.class.getResourceAsStream(filePath);        
        DOMHelper dom = new DOMHelper(is, false);
        Element settingsElt = dom.getElement(domPath);
        return reader.readSettings(dom, settingsElt);
    }
    
    
    protected Mode readMode(String filePath, String domPath) throws Exception
    {
        ConfigurationReaderV20 reader = new ConfigurationReaderV20();        
        InputStream is = TestSMLBindingsV20.class.getResourceAsStream(filePath);        
        DOMHelper dom = new DOMHelper(is, false);
        Element modeElt = dom.getElement(domPath);
        return reader.readMode(dom, modeElt);
    }
    
    
    protected SMLPhysicalComponent readSystem(String filePath) throws Exception
    {
        SystemReaderV20 reader = new SystemReaderV20();        
        InputStream is = TestSMLBindingsV20.class.getResourceAsStream(filePath);        
        DOMHelper dom = new DOMHelper(is, false);       
        return (SMLPhysicalComponent)reader.read(dom, dom.getBaseElement());        
    }
    
    
    public void testReadSettings() throws Exception
    {
        Configuration config;
        
        config = readSettings("/examples_v20/SensorWithModes.xml", "modes/ModeChoice/mode/Mode/configuration/Settings");
        assertEquals(2, config.size());
        assertEquals(ValueSetting.class, config.get(0).getClass());
        assertEquals(ValueSetting.class, config.get(1).getClass());
        assertEquals("parameters/settings/samplingRate", config.get(0).getComponentRef());
        assertEquals("parameters/settings/gain", config.get(1).getComponentRef());
        assertEquals(0.1, ((ValueSetting<Double>)config.get(0)).getValue());
        assertEquals(1.0, ((ValueSetting<Double>)config.get(1)).getValue());
        
        config = readSettings("/examples_v20/OwnerInstance.xml", "configuration/Settings");
        assertEquals(3, config.size());
        assertEquals(ValueSetting.class, config.get(0).getClass());
        assertEquals(ConstraintSetting.class, config.get(1).getClass());
        assertEquals(StatusSetting.class, config.get(2).getClass());
        assertEquals("base/components/raingauge/parameters/Averaging_Period", config.get(0).getComponentRef());
        assertEquals("base/components/raingauge/parameters/Sampling_Period", config.get(1).getComponentRef());
        assertEquals("base/components/raingauge", config.get(2).getComponentRef());
        assertEquals(60.0, ((ValueSetting<Double>)config.get(0)).getValue());
        ConstraintList constraints = (ConstraintList)((ConstraintSetting)config.get(1)).getConstraint();
        assertEquals(IntervalConstraint.class, constraints.get(0).getClass());
        assertEquals(30., ((IntervalConstraint)constraints.get(0)).getMin());
        assertEquals(60., ((IntervalConstraint)constraints.get(0)).getMax());
        assertEquals(true, ((StatusSetting)config.get(2)).getStatus());
        
        config = readSettings("/examples_v20/ModeInstance.xml", "configuration/Settings");
        assertEquals(1, config.size());
        assertEquals(ModeSetting.class, config.get(0).getClass());
        assertEquals("modes/threatLevel", config.get(0).getComponentRef());
        assertEquals("lowThreat", ((ModeSetting)config.get(0)).getMode());
    }
    
    
    public void testReadMode() throws Exception
    {
        Mode mode = readMode("/examples_v20/SensorWithModes.xml", "modes/ModeChoice/mode/Mode");
        assertEquals(2, mode.getConfiguration().size());
    }
    
    
    public void testReadSystem() throws Exception
    {
        readWriteCompareXml("/examples_v20/Davis_7817_complete.xml");
    }
    
}

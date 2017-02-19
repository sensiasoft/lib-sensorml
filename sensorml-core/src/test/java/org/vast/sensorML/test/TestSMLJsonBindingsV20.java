/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2015 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.sensorML.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import net.opengis.sensorml.v20.AbstractProcess;
import org.vast.sensorML.SMLStaxBindings;
import org.vast.sensorML.SMLUtils;
import org.vast.sensorML.json.SMLJsonStreamReader;
import org.vast.sensorML.json.SMLJsonStreamWriter;
import org.vast.xml.IndentingXMLStreamWriter;
import junit.framework.TestCase;
import static javax.xml.stream.XMLStreamReader.*;


public class TestSMLJsonBindingsV20 extends TestCase
{


    protected void writeJsonToStream(AbstractProcess process, OutputStream os, boolean indent) throws Exception
    {
        SMLStaxBindings smlHelper = new SMLStaxBindings();
        SMLJsonStreamWriter writer = new SMLJsonStreamWriter(os, "UTF-8");                
        smlHelper.setNamespacePrefixes(writer);
        writer.writeStartDocument();
        smlHelper.writeAbstractProcess(writer, process);
        writer.writeEndDocument();
        writer.flush();
    }
    
    
    protected void readWriteCompareProcessJson(String path) throws Exception
    {
        try
        {            
            SMLUtils smlUtils = new SMLUtils(SMLUtils.V2_0);
                    
            // read from file
            InputStream is = getClass().getResourceAsStream(path);
            AbstractProcess smlObj = smlUtils.readProcess(is);
            is.close();
            
            // write back to stdout and buffer
            System.out.println();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            writeJsonToStream(smlObj, System.out, true);
            writeJsonToStream(smlObj, os, false);
            
            // read back to check JSON is well formed
            is = new ByteArrayInputStream(os.toByteArray());
            SMLJsonStreamReader jsonReader = new SMLJsonStreamReader(is, "UTF-8");
            
            XMLOutputFactory output = new com.ctc.wstx.stax.WstxOutputFactory();
            XMLStreamWriter xmlWriter = output.createXMLStreamWriter(System.out);
            xmlWriter = new IndentingXMLStreamWriter(xmlWriter);
            
            while (jsonReader.hasNext())
            {
                switch (jsonReader.next())
                {
                    case START_ELEMENT:
                        String name = jsonReader.getLocalName();
                        xmlWriter.writeStartElement(name);
                        for(int i=0; i<jsonReader.getAttributeCount(); i++)
                        {
                            name = jsonReader.getAttributeLocalName(i);
                            String val = jsonReader.getAttributeValue(i);
                            xmlWriter.writeAttribute(name, val);
                        }
                        break;
                        
                    case END_ELEMENT:
                        xmlWriter.writeEndElement();
                        break;
                        
                    case CHARACTERS:
                        xmlWriter.writeCharacters(jsonReader.getText());
                        break;
                }
                
                xmlWriter.flush();
            }
            
            System.out.println("\n\n");
            // read back to check JSON is well formed
            //InputSource src1 = new InputSource(getClass().getResourceAsStream(path));
            //InputSource src2 = new InputSource(new ByteArrayInputStream(os.toByteArray()));
            //assertXMLEqual(src1, src2);
        }
        catch (Throwable e)
        {
            throw new Exception("Failed test " + path, e);
        }
    }
    
    
    public void testReadWriteSimpleSensor() throws Exception
    {
        readWriteCompareProcessJson("examples_v20/SimpleSensor.xml");
    }
    
    
    public void testReadWriteGammaSensor() throws Exception
    {
        //readWriteCompareProcessXml("examples_v20/gamma2070.xml");
        readWriteCompareProcessJson("examples_v20/gamma2070_more.xml");
    }
    
    
    public void testReadWriteCameraSensor() throws Exception
    {
        readWriteCompareProcessJson("examples_v20/KCM-HD_Camera_inline.xml");
    }
    
    
    public void testReadWriteConfiguredCameraSensor() throws Exception
    {
        readWriteCompareProcessJson("examples_v20/KCM-HD Camera.xml");
    }
    
    
    public void testReadWriteSensorWithModes() throws Exception
    {
        readWriteCompareProcessJson("examples_v20/SensorWithModes.xml");
    } 
    
    
    public void testReadWriteDerivedInstance() throws Exception
    {
        readWriteCompareProcessJson("examples_v20/OwnerInstance.xml");
    }
    
    
    public void testReadWriteDavisSensor() throws Exception
    {
        readWriteCompareProcessJson("examples_v20/Davis_7817_complete.xml");
    } 
    
    
    public void testReadWriteWeatherStation() throws Exception
    {
        readWriteCompareProcessJson("examples_v20/WeatherStation.xml");
    } 
    
    
    public void testReadWriteModeInstance() throws Exception
    {
        readWriteCompareProcessJson("examples_v20/ModeInstance.xml");
    }
    
    
    public void testReadWriteSensorwithDataStreamOutput() throws Exception
    {
        readWriteCompareProcessJson("examples_v20/SimpleStreaming RS232.xml");
    }
}

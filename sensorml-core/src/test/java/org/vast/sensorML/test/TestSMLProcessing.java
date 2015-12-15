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

import static org.junit.Assert.assertEquals;
import java.net.URL;
import net.opengis.swe.v20.DataComponent;
import org.junit.Before;
import org.junit.Test;
import org.vast.sensorML.AbstractProcessImpl;
import org.vast.sensorML.ProcessLoader;
import org.vast.sensorML.SMLHelper;
import org.vast.sensorML.SMLUtils;


public class TestSMLProcessing
{
    SMLUtils smlUtils = new SMLUtils(SMLUtils.V2_0);
    
    
    @Before
    public void init() throws Exception
    {
        URL processMapUrl = TestSMLProcessing.class.getResource("ProcessMap.xml");
        ProcessLoader.loadMaps(processMapUrl.toString(), true);
    }
    
    
    protected AbstractProcessImpl getExecutableProcess(String path) throws Exception
    {
        // parse SensorML file
        AbstractProcessImpl process = (AbstractProcessImpl)smlUtils.readProcess(TestSMLProcessing.class.getResourceAsStream(path));
        //smlUtils.writeProcess(System.out, process, true);
        
        // make process executable        
        SMLHelper.makeProcessExecutable(process);
        
        // init process
        process.createNewInputBlocks();
        process.createNewOutputBlocks();
        process.init();
        
        return process;
    }
    
    
    @Test
    public void testExecSimpleProcess() throws Exception
    {
        AbstractProcessImpl process = getExecutableProcess("examples_v20/WindChill.xml");
        
        for (int i=0; i<10; i++)
        {
            // set input values
            DataComponent tempIn = process.getInputComponent("weather_inputs").getComponent("temperature");
            tempIn.getData().setDoubleValue(23.5 + i);
            DataComponent windIn = process.getInputComponent("weather_inputs").getComponent("windSpeed");
            windIn.getData().setDoubleValue(4.1 + i);
            
            // execute process
            process.execute();
            
            // read output
            //System.out.println("windChill=" + process.getOutputComponent("windChill").getData().getDoubleValue());
        }
    }
    
    
    @Test
    public void testExecProcessChainNoThreads() throws Exception
    {
        AbstractProcessImpl process = getExecutableProcess("examples_v20/testAggregateProcess.xml");
        
        for (int i=0; i<10; i++)
        {
            // set input values
            double in = i*2;
            DataComponent valueIn = process.getInputComponent("valueIn");
            valueIn.getData().setDoubleValue(in);
            
            // execute process
            process.execute();
            
            // read output
            double out = process.getOutputComponent("valueOut").getData().getDoubleValue();
            //System.out.println("valueOut=" + out);        
            
            double expected = 2.3*in + 1.76;
            if (expected < 15.0)
                expected = Double.NaN;        
            assertEquals("Incorrect output value", expected, out, 1e-15);
        }
    }

}

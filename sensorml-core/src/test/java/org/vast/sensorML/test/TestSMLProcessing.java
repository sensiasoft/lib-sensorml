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

import static org.junit.Assert.*;
import java.net.URL;
import net.opengis.sensorml.v20.AbstractProcess;
import net.opengis.sensorml.v20.AggregateProcess;
import net.opengis.swe.v20.DataComponent;
import org.junit.Before;
import org.junit.Test;
import org.vast.process.DataConnection;
import org.vast.process.ExecutableChainImpl;
import org.vast.process.IProcessExec;
import org.vast.sensorML.AbstractProcessImpl;
import org.vast.sensorML.ProcessLoader;
import org.vast.sensorML.SMLException;
import org.vast.sensorML.SMLUtils;
import org.vast.swe.SWEHelper;
import org.vast.util.MsgUtils;


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
        return getExecutableProcess(path, false);
    }
    
    
    protected AbstractProcessImpl getExecutableProcess(String path, boolean useThreads) throws Exception
    {
        // parse SensorML file
        AbstractProcessImpl process = (AbstractProcessImpl)smlUtils.readProcess(TestSMLProcessing.class.getResourceAsStream(path));
        //smlUtils.writeProcess(System.out, process, true);
        
        // make process executable        
        smlUtils.makeProcessExecutable(process, useThreads);
        
        // init process
        process.init();
        
        return process;
    }
    
    
    @Test
    public void testConnectToProcessPorts() throws Exception
    {
        IProcessExec p0 = new AffineTransform1D_Process();
        
        DataConnection c = new DataConnection();
        p0.connect(p0.getInputList().getComponent("x"), c);
        assertTrue("Connection not added", p0.getInputConnections().get("x").contains(c));
        
        c = new DataConnection();
        p0.connect(p0.getOutputList().getComponent("y"), c);
        assertTrue("Connection not added", p0.getOutputConnections().get("y").contains(c));
        
        c = new DataConnection();
        p0.connect(p0.getParameterList().getComponent("slope"), c);
        assertTrue("Connection not added", p0.getParamConnections().get("slope").contains(c));
    }
    
    
    @Test
    public void testDisconnectPorts() throws Exception
    {
        IProcessExec p0 = new AffineTransform1D_Process();
        
        DataConnection c = new DataConnection();
        p0.connect(p0.getInputList().getComponent("x"), c);
        assertTrue("Connection not added", p0.getInputConnections().get("x").contains(c));
        
        p0.disconnect(c);
        assertTrue("Connection not removed", !p0.getInputConnections().get("x").contains(c));
    }
    
    
    @Test(expected=IllegalArgumentException.class)
    public void testAddProcessesWithSameName() throws Exception
    {
        ExecutableChainImpl chain = new ExecutableChainImpl();
        chain.addProcess("p0", new AffineTransform1D_Process());
        chain.addProcess("p0", new Clip_Process());
    }
    
    
    @Test(expected=IllegalArgumentException.class)
    public void testConnectToWrongComponent() throws Exception
    {
        ExecutableChainImpl chain = new ExecutableChainImpl();
        IProcessExec p0 = chain.addProcess("p0", new AffineTransform1D_Process());
        IProcessExec p1 = chain.addProcess("p1", new Clip_Process());
        p1.connect(p0.getInputList().getComponent(0), new DataConnection());
    }
    
    
    @Test
    public void testCreateAndExecSimpleProcess() throws Exception
    {
        IProcessExec p0 = new AffineTransform1D_Process();
        p0.init();
        
        p0.getParameterList().getComponent("slope").getData().setDoubleValue(2.0);
        p0.getParameterList().getComponent("intercept").getData().setDoubleValue(3.0);
        p0.getInputList().getComponent("x").getData().setDoubleValue(10.0);
        
        p0.execute();
        double output = p0.getOutputList().getComponent("y").getData().getDoubleValue();
        
        assertEquals("Invalid output", 10.0*2.0+3.0, output, 1e-10);        
    }
    
    
    @Test
    public void testCreateAndExecProcessChain() throws Exception
    {
        SWEHelper fac = new SWEHelper();
        
        ExecutableChainImpl chain = new ExecutableChainImpl();
        chain.getInputList().add("in", fac.newQuantity());
        chain.getOutputList().add("out", fac.newQuantity());
        
        IProcessExec p0 = chain.addProcess("affine", new AffineTransform1D_Process());
        IProcessExec p1 = chain.addProcess("clip", new Clip_Process());
        
        chain.connect(chain, chain.getInputList().getComponent("in"), p0, p0.getInputList().getComponent("x"));
        chain.connect(p0, p0.getOutputList().getComponent("y"), p1, p1.getInputList().getComponent("valueIn"));
        chain.connect(p1, p1.getOutputList().getComponent("passValue"), chain, chain.getOutputList().getComponent("out"));
        
        chain.init();
        
        p0.getParameterList().getComponent("slope").getData().setDoubleValue(2.0);
        p0.getParameterList().getComponent("intercept").getData().setDoubleValue(3.0);
        chain.getInputList().getComponent("in").getData().setDoubleValue(10.0);
        
        chain.execute();
        double output = chain.getOutputList().getComponent("out").getData().getDoubleValue();
        
        assertEquals("Invalid output", 10.0*2.0+3.0, output, 1e-10);        
    }
    
    
    @Test
    public void testReadSimpleProcessPortsDefined() throws Exception
    {
        getExecutableProcess("examples_v20/WindChill2.xml");
    }
    
    
    @Test(expected=SMLException.class)
    public void testReadSimpleProcessWrongPortName() throws Exception
    {
        try
        {
            getExecutableProcess("examples_v20/WindChill2_Error1.xml");
        }
        catch (Exception e)
        {
            System.out.println('\n' + MsgUtils.getFullExceptionMessage(e) + '\n');
            throw e;
        }
    }
    
    
    @Test(expected=SMLException.class)
    public void testReadSimpleProcessWrongComponentName() throws Exception
    {
        try
        {
            getExecutableProcess("examples_v20/WindChill2_Error2.xml");
        }
        catch (Exception e)
        {
            String msg = MsgUtils.getFullExceptionMessage(e);
            assertTrue("Wrong exception message:\n" + msg, msg.contains("name"));
            System.out.println('\n' + msg + '\n');
            throw e;
        }
    }
    
    
    @Test(expected=SMLException.class)
    public void testReadSimpleProcessWrongUom() throws Exception
    {
        try
        {
            getExecutableProcess("examples_v20/WindChill2_Error3.xml");
        }
        catch (Exception e)
        {
            String msg = MsgUtils.getFullExceptionMessage(e);
            assertTrue("Wrong exception message:\n" + msg, msg.contains("unit"));
            System.out.println('\n' + msg + '\n');
            throw e;
        }
    }
    
    
    @Test(expected=SMLException.class)
    public void testReadSimpleProcessWrongType() throws Exception
    {
        try
        {
            getExecutableProcess("examples_v20/WindChill2_Error4.xml");
        }
        catch (Exception e)
        {
            String msg = MsgUtils.getFullExceptionMessage(e);
            assertTrue("Wrong exception message:\n" + msg, msg.contains("type"));
            System.out.println('\n' + msg + '\n');
            throw e;
        }
    }
    
    
    @Test(expected=SMLException.class)
    public void testReadSimpleProcessWrongSize() throws Exception
    {
        try
        {
            getExecutableProcess("examples_v20/WindChill2_Error5.xml");
        }
        catch (Exception e)
        {
            String msg = MsgUtils.getFullExceptionMessage(e);
            assertTrue("Wrong exception message:\n" + msg, msg.contains("size"));
            System.out.println('\n' + msg + '\n');
            throw e;
        }
    }
    
    
    @Test(expected=IllegalArgumentException.class)
    public void testReadAggregateProcessWrongLinkPath() throws Exception
    {
        try
        {
            getExecutableProcess("examples_v20/AggregateProcessWithConfig_Error1.xml");
        }
        catch (Exception e)
        {
            String msg = MsgUtils.getFullExceptionMessage(e);
            assertTrue("Wrong exception message:\n" + msg, msg.contains("scale1"));
            System.out.println('\n' + msg + '\n');
            throw e;
        }
    }
    
    
    @Test(expected=SMLException.class)
    public void testReadAggregateProcessWrongConfigPath() throws Exception
    {
        try
        {
            getExecutableProcess("examples_v20/AggregateProcessWithConfig_Error2.xml");
        }
        catch (Exception e)
        {
            String msg = MsgUtils.getFullExceptionMessage(e);
            assertTrue("Wrong exception :" + e.toString() + "\n" + msg, msg.contains("slope1"));
            System.out.println('\n' + msg + '\n');
            throw e;
        }
    }
    
    
    @Test(expected=SMLException.class)
    public void testReadAggregateProcessWrongArrayConfig() throws Exception
    {
        try
        {
            getExecutableProcess("examples_v20/AggregateProcessWithConfig_Error3.xml");
        }
        catch (Exception e)
        {
            String msg = MsgUtils.getFullExceptionMessage(e);
            assertTrue("Wrong exception :" + e.toString() + "\n" + msg, msg.contains("DataArray"));
            System.out.println('\n' + msg + '\n');
            throw e;
        }
    }
    
    
    @Test
    public void testReadAndExecSimpleProcessNoPortsDefined() throws Exception
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
            System.out.println("windChill=" + process.getOutputComponent("windChill").getData().getDoubleValue());
        }
    }
    
    
    @Test
    public void testReadAndExecSimpleProcessWithPortsDefined() throws Exception
    {
        AbstractProcessImpl process = getExecutableProcess("examples_v20/LinearInterpolator.xml");
        
        for (int i=0; i<10; i++)
        {
            // set input value
            process.getInputComponent("x").getData().setDoubleValue(23.5 + i);
            
            // execute process
            process.execute();
            
            // read output
            System.out.println("y=" + process.getOutputComponent("y").getData().getDoubleValue());
        }
    }
    
    
    @Test
    public void testReadAndExecProcessChainNoThreads() throws Exception
    {
        AbstractProcessImpl process = getExecutableProcess("examples_v20/AggregateProcess.xml");
        
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
            
            double expected = 2.3*in + 1.76;
            if (expected < 15.0)
                expected = Double.NaN;        
            assertEquals("Incorrect output value", expected, out, 1e-15);
        }
    }
    
    
    @Test
    public void testReadAndExecProcessChainWithThreads() throws Exception
    {
        AbstractProcessImpl process = getExecutableProcess("examples_v20/AggregateProcess.xml", true);
        
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
            
            double expected = 2.3*in + 1.76;
            if (expected < 15.0)
                expected = Double.NaN;        
            assertEquals("Incorrect output value", expected, out, 1e-15);
        }
        
        process.dispose();
    }
    
    
    @Test
    public void testReadAndExecNestedChainsWithThreads() throws Exception
    {
        AbstractProcessImpl process = getExecutableProcess("examples_v20/AggregateProcess.xml", false);
        
        ExecutableChainImpl wrapperProcess = new ExecutableChainImpl(true);
        DataComponent wrapperInput = process.getInputComponent("valueIn").copy();
        DataComponent wrapperOutput = process.getOutputComponent("valueOut").copy();
        wrapperProcess.getInputList().add("valueIn", wrapperInput);
        wrapperProcess.getOutputList().add("valueOut", wrapperOutput);
        wrapperProcess.addProcess("p0", process);        
        wrapperProcess.connect(wrapperProcess, wrapperInput, process, process.getInputComponent("valueIn"));
        wrapperProcess.connect(process, process.getOutputComponent("valueOut"), wrapperProcess, wrapperOutput);
        wrapperProcess.init();
        
        for (int i=0; i<10; i++)
        {
            // set input values
            double in = i*2;
            wrapperInput.getData().setDoubleValue(in);
            
            // execute process
            wrapperProcess.execute();
            
            // read output
            double out = wrapperOutput.getData().getDoubleValue();
            
            double expected = 2.3*in + 1.76;
            if (expected < 15.0)
                expected = Double.NaN;        
            assertEquals("Incorrect output value", expected, out, 1e-15);
        }
        
        wrapperProcess.dispose();
    }
    
    
    @Test
    public void testReadAndExecConfiguredProcessChain() throws Exception
    {
        AbstractProcessImpl process = getExecutableProcess("examples_v20/AggregateProcessWithConfig.xml");
        
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
            
            double expected = 5.3*in + 12.5;
            if (expected < 55.0)
                expected = Double.NaN;        
            assertEquals("Incorrect output value", expected, out, 1e-15);
        }
    }
    
    
    @Test
    public void testSerializeExecutableProcessChain() throws Exception
    {
        SWEHelper fac = new SWEHelper();
        
        ExecutableChainImpl chain = new ExecutableChainImpl();
        chain.getInputList().add("in", fac.newQuantity());
        chain.getOutputList().add("out", fac.newQuantity());
        
        IProcessExec p0 = chain.addProcess("affine", new AffineTransform1D_Process());
        IProcessExec p1 = chain.addProcess("clip", new Clip_Process());
        
        chain.connect(chain, chain.getInputList().getComponent("in"), p0, p0.getInputList().getComponent("x"));
        chain.connect(p0, p0.getOutputList().getComponent("y"), p1, p1.getInputList().getComponent("valueIn"));
        chain.connect(p1, p1.getOutputList().getComponent("passValue"), chain, chain.getOutputList().getComponent("out"));
        
        AbstractProcess smlProcess = SMLUtils.wrapWithProcessDescription(chain);
        assertTrue("Process is not an aggregate", smlProcess instanceof AggregateProcess);
        assertEquals("Wrong number of components", 2, ((AggregateProcess)smlProcess).getNumComponents());
        assertEquals("Wrong number of connections", 3, ((AggregateProcess)smlProcess).getNumConnections());
        assertEquals("Wrong process name", AffineTransform1D_Process.INFO.getName(), ((AggregateProcess)smlProcess).getComponent("affine").getName());
        
        new SMLUtils(SMLUtils.V2_0).writeProcess(System.out, smlProcess, true);
    }
    
    
    @Test
    public void testReadAndConfigureProcess() throws Exception
    {
        URL docURL = TestSMLProcessing.class.getResource("examples_v20/OwnerInstance.xml");
        AbstractProcess p = smlUtils.readProcess(docURL.openStream(), docURL.toURI());
        p = new SMLUtils(SMLUtils.V2_0).getConfiguredInstance(p);
        new SMLUtils(SMLUtils.V2_0).writeProcess(System.out, p, true);
    }

}

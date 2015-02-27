/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are subject to the Mozilla Public License Version
 1.1 (the "License"); you may not use this file except in compliance with
 the License. You may obtain a copy of the License at
 http://www.mozilla.org/MPL/MPL-1.1.html
 
 Software distributed under the License is distributed on an "AS IS" basis,
 WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 for the specific language governing rights and limitations under the License.
 
 The Original Code is the "Space Time Toolkit".
 
 The Initial Developer of the Original Code is the VAST team at the
 University of Alabama in Huntsville (UAH). <http://vast.uah.edu>
 Portions created by the Initial Developer are Copyright (C) 2007
 the Initial Developer. All Rights Reserved.
 
 Please Contact Mike Botts <mike.botts@uah.edu> for more information.
 
 Contributor(s): 
    Alexandre Robin <robin@nsstc.uah.edu>
 
******************************* END LICENSE BLOCK ***************************/

package org.sensorML.process;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

import javax.xml.soap.SOAPException;

import org.vast.cdm.common.DataBlock;
import org.vast.cdm.common.DataComponent;
import org.vast.cdm.common.DataHandler;
import org.vast.cdm.common.DataStreamParser;
import org.vast.cdm.common.DataType;
import org.vast.data.*;
import org.vast.ogc.OGCRegistry;
import org.vast.ows.wps.DescribeProcessRequest;
import org.vast.ows.wps.DescribeProcessResponse;
import org.vast.ows.wps.ExecuteProcessRequest;
import org.vast.ows.wps.ExecuteProcessRequestWriter;
import org.vast.ows.wps.ExecuteProcessResponse;
import org.vast.ows.wps.ExecuteProcessResponseReader;
import org.vast.ows.wps.WPSUtils;
import org.vast.process.*;
import org.vast.sweCommon.SweConstants;
import org.vast.unit.UnitConversion;
import org.vast.unit.UnitConverter;


/**
 * <p><b>Title:</b><br/>
 * WPS_Process
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Issues a request to a WPS server using provided parameters
 * and input, and outputs the resulting data block.
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Gregoire Berthiau
 * @date Dec 3, 2008
 * @version 1.0
 */
public class WPS_Process extends DataProcess implements DataHandler
{
    protected DataComponent wpsInputData, wpsOutputData;
    protected InputStream describeProcessDataStream, executeProcessDataStream;
    protected DataStreamParser dataParser;
    protected Thread workerThread;
    protected boolean usePost; 
    protected boolean done, error, outputReady;
    protected Exception lastException;
    protected Hashtable<DataComponent, UnitConverter> converters;
    protected String url, version, requestMethod, requestFormat, offering;
    protected DataGroup wpsParams;
    protected ExecuteProcessRequest executeProcessRequest;
    protected DescribeProcessRequest describeProcessRequest;
    protected InputStream dataStream;
    protected ExecuteProcessRequestWriter executeProcessRequestWriter;
	private WPSUtils wpsUtils;
	
    
    public WPS_Process()
    {
    	describeProcessRequest = new DescribeProcessRequest();
    	executeProcessRequest = new ExecuteProcessRequest();
    	executeProcessRequestWriter = new ExecuteProcessRequestWriter();
        converters = new Hashtable<DataComponent, UnitConverter>();
    }


    @Override
    public void init() throws ProcessException
    {
        // Read I/O mappings
        try
        {
            // input Data mapping
        	if(inputData.getComponentCount()!=1)
        		throw new ProcessException("a WPS accept only one input," +
        				" that can be any SWE Common Data Type, but only one input");

            wpsInputData = inputData.getComponent("wpsInputData");
            
            // Output Data mapping
            wpsOutputData = outputData.getComponent("wpsOutputData");
            
            // Read parameters mappings + values
            wpsParams = (DataGroup)paramData.getComponent("wpsOptions");
            
            reset();
            
        }
        catch (Exception e)
        {
            throw new ProcessException(ioError, e);
        }
    }
            
    
    public void setRequest() throws ProcessException
    {
        // Read I/O mappings
        try
        {
            // service end point url
            url = wpsParams.getComponent("endPoint").getData().getStringValue();
            requestMethod = wpsParams.getComponent("requestMethod").getData().getStringValue();
                        
            describeProcessRequest.setPostServer(url);
            executeProcessRequest.setPostServer(url);
            usePost = true;
            
            // version
            version = wpsParams.getComponent("version").getData().getStringValue();
            describeProcessRequest.setVersion(version);
            executeProcessRequest.setVersion(version);

            // offering
            offering = wpsParams.getComponent("offering").getData().getStringValue();
            describeProcessRequest.setOffering(offering);
            executeProcessRequest.setOffering(offering);
            
            // request format
            requestFormat = wpsParams.getComponent("requestFormat").getData().getStringValue();
            describeProcessRequest.setRequestFormat(offering);
            executeProcessRequest.setRequestFormat(offering);
            checkData();            
    }
    
    catch (Exception e)
    {
        throw new ProcessException(ioError, e);
    }
}            

    
    
    @Override
    public void reset() throws ProcessException
    {
        endRequest();        
        outputReady = false;
        done = true;      
        error = false;
        lastException = null;
        for (int i=0; i<inputConnections.size(); i++)
            inputConnections.get(i).setNeeded(true);
    }
    
    
    protected void checkData() throws ProcessException
    {
        //TODO check that output is compatible with SOS data
    }
    
    
    @Override
    public void execute() throws ProcessException
    {
    	// get input variables only if previous request is done
        if (done)
        {
            setRequest();
            final DataHandler handler = this;
            
            Runnable initiateRequest = new Runnable()
            {
                public void run()
                {
                    try
                    {    
                        synchronized (handler)
                        {
                            // init request 
                        	DescribeProcessResponse describeProcessResponse = initRequest();
                        	
                        	executeProcessRequest.setInputDataComponent(describeProcessResponse.getInputDataComponent());
                        	executeProcessRequest.setInputDataEncoding(describeProcessResponse.getInputDataEncoding());
                        	
                        	ExecuteProcessResponse executeProcessResponse = ((ExecuteProcessResponse)(wpsUtils.getWPSResponse(executeProcessRequest)));
                        	
                        	executeProcessResponse.setDataEncoding(describeProcessResponse.getOutputDataEncoding());
                        	executeProcessResponse.setDataEncoding(describeProcessResponse.getOutputDataEncoding());
                        	dataStream = executeProcessResponse.getDataStream();

                        	ExecuteProcessResponseReader reader = (ExecuteProcessResponseReader)OGCRegistry.createReader(executeProcessRequest.getService(), executeProcessRequest.getOperation(), executeProcessRequest.getVersion());

                        	reader.setDataEncoding(executeProcessResponse.getDataEncoding());
                        	reader.setDataComponents(executeProcessResponse.getDataComponent());
                        	reader.parse(dataStream);
                        	
                            dataParser = reader.getDataParser();
                            dataParser.setDataHandler(handler);
                            
                            // parse data stream
                            dataParser.parse(dataStream);                            
                            done = true;
                        }
                    }
                    catch (Exception e)
                    {
                        error = true;
                        done = true;
                        lastException = e;
                        synchronized (handler) {handler.notify();}
                    }
                    finally
                    {
                        endRequest();
                    }       
                }
            };
            
            workerThread = new Thread(initiateRequest, "WPS Process Parser");
            workerThread.start();
            done = false;
            
            // set inputs as not needed so that we can continue looping
            for (int i=0; i<inputConnections.size(); i++)
                inputConnections.get(i).setNeeded(false);
        }

        // now let the worker thread run one more time
        synchronized (this)
        {
            try
            {
                // notify parser thread that next packet is needed
                outputReady = false;
                this.notify();
                //System.out.println("next " + name);
                
                // give parser control and wait for next block to be parsed
                while (!done && !error && !outputReady)
                    this.wait();
                //System.out.println("ready " + name + ": " + outputObsData.getData().getDoubleValue(0));
            }
            catch (InterruptedException e)
            {
            }
            
            // if an error occured in the worker thread
            if (error)
            {
                String server = executeProcessRequest.getPostServer();
                if (server == null)
                    server = executeProcessRequest.getGetServer();                
                throw new ProcessException("Error while reading data from SOS server: " + server, lastException);
            }
        
            // if parsing is done (at end of stream)
            if (done)
            {
                // set inputs as needed since loop has ended
                for (int i=0; i<inputConnections.size(); i++)
                    inputConnections.get(i).setNeeded(true);
            }
        }
    }    
    

	/**
     * Reads all input parameters and set up query accordingly

     */
    protected DescribeProcessResponse initRequest() throws ProcessException
    {
        // make sure previous request is cancelled
        endRequest();
        DescribeProcessResponse describeProcessResponse;
		try {
			describeProcessResponse = (DescribeProcessResponse)wpsUtils.getWPSResponse(describeProcessRequest);
		} catch (SOAPException e) {
			// TODO Auto-generated catch block
			throw new ProcessException(e.getMessage(), e);
		}
        return describeProcessResponse;
    }
    
    
    /**
     * Makes sure request is ended and stream is closed
     */
    protected void endRequest()
    {
        try
        {
            if (dataStream != null)
                dataStream.close();
            
            dataStream = null;
            
            if (dataParser != null)
                dataParser.stop();
            
            synchronized (this) {this.notify();}
        }
        catch (IOException e)
        {
        }
    }
    
    
    @Override
    public void dispose()
    {
        endRequest();
    }
    
    
    public void endData(DataComponent info, DataBlock data)
    {
        try
        {
            // give exec control and wait for the ok to continue
            while (outputReady)
                this.wait();

            wpsOutputData.setData(data);
            
            // notify exec thread that next packet has been parsed
            outputReady = true;
            this.notify();
        }
        catch (InterruptedException e)
        {
        }
    }


    public void endDataAtom(DataComponent info, DataBlock data)
    {
        DataType dataType = data.getDataType();
        
        // TODO unit conversion WILL be handled by global chain unit converters
        if (dataType != DataType.UTF_STRING && dataType != DataType.ASCII_STRING)
        {
            UnitConverter converter = converters.get(info);
            
            if (converter == null)
            {
                String uom = (String)info.getProperty(SweConstants.UOM_CODE);
                converter = UnitConversion.createConverterToSI(uom);
                converters.put(info, converter);
            }
            
            // convert to SI
            double newVal = converter.convert(data.getDoubleValue());
            data.setDoubleValue(newVal);
        }
        
        //System.out.println(info.getName() + ": " + data.getStringValue());
    }


    public void endDataBlock(DataComponent info, DataBlock data)
    {
    }


    public void startData(DataComponent info)
    {
    }


    public void startDataBlock(DataComponent info)
    {
    }


    public void beginDataAtom(DataComponent info)
    {       
    }
}
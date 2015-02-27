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
import java.net.URL;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.vast.cdm.common.DataComponent;
import org.vast.cdm.common.DataStreamParser;
import org.vast.data.DataArray;
import org.vast.data.DataGroup;
import org.vast.data.DataValue;
import org.vast.ows.OWSUtils;
import org.vast.ows.wcs.GetCoverageRequest;
import org.vast.ows.wcs.WCSResponseReader;
import org.vast.process.DataProcess;
import org.vast.process.ProcessException;
import org.vast.util.Bbox;


/**
 * <p><b>Title:</b><br/>
 * WCS Process
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Issues a request to a WCS server using provided parameters
 * and input bounding box and time, and outputs the resulting
 * coverage encapsulated in a data component structure.
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Alexandre Robin
 * @date Jan 20, 2006
 * @version 1.0
 */
public class WCS_Process extends DataProcess
{
    protected Log log = LogFactory.getLog(WCS_Process.class);
    protected DataValue bboxLat1, bboxLon1, bboxLat2, bboxLon2;
    protected DataValue outputWidth, outputLength;
    protected DataArray outputCoverage;
    protected DataGroup output;
    protected InputStream dataStream;
    protected GetCoverageRequest request;
    protected OWSUtils owsUtils;
    protected DataStreamParser dataParser;
    

    public WCS_Process()
    {
        request = new GetCoverageRequest();
        request.setBbox(new Bbox());
        owsUtils = new OWSUtils();
    }


    /**
     * Initializes the process
     * Gets handles to input/output components
     */
    public void init() throws ProcessException
    {
        int skipX, skipY, skipZ;
        DataComponent component;
        
        try
        {
            // Input mappings
            DataGroup input = (DataGroup)inputData.getComponent("bbox");
            bboxLat1 = (DataValue)input.getComponent("corner1").getComponent(0);
            bboxLon1 = (DataValue)input.getComponent("corner1").getComponent(1);
            bboxLat2 = (DataValue)input.getComponent("corner2").getComponent(0);
            bboxLon2 = (DataValue)input.getComponent("corner2").getComponent(1);
            input.assignNewDataBlock();
            
            // Output mappings
            output = (DataGroup)outputData.getComponent("coverageData");
            outputCoverage = (DataArray)output.getComponent("coverage");
            outputWidth = (DataValue)output.getComponent("width");
            outputLength = (DataValue)output.getComponent("length");
        }
        catch (Exception e)
        {
            throw new ProcessException("Invalid I/O structure", e);
        }
        
        try
        {
            // Read parameter values (must be fixed!)
            DataGroup wcsParams = (DataGroup)paramData.getComponent("wcsOptions");
            
            // service end point url
            String url = wcsParams.getComponent("endPoint").getData().getStringValue();
            String requestMethod = wcsParams.getComponent("requestMethod").getData().getStringValue();
            if (requestMethod.equalsIgnoreCase("post"))
                request.setPostServer(url);
            else
                request.setGetServer(url);
            
            // version
            String version = wcsParams.getComponent("version").getData().getStringValue();
            request.setVersion(version);
            
            // coverage ID
            String coverageID = wcsParams.getComponent("coverage").getData().getStringValue();
            request.setCoverage(coverageID);
            
            // image format
            String format = wcsParams.getComponent("format").getData().getStringValue();
            request.setFormat(format);
            
            // skip factor along X
            component = wcsParams.getComponent("skipX");
            if (component != null)
            {
                skipX = component.getData().getIntValue();
                request.setSkipX(skipX);
            }
            
            // skip factor along Y
            component = wcsParams.getComponent("skipY");
            if (component != null)
            {
                skipY = component.getData().getIntValue();
                request.setSkipY(skipY);
            }
            
            // skip factor along Z
            component = wcsParams.getComponent("skipZ");
            if (component != null)
            {
                skipZ = component.getData().getIntValue();
                request.setSkipZ(skipZ);
            }
            
            // coverage and bbox crs 
            request.getGridCrs().setBaseCrs("EPSG:4329");
            request.getBbox().setCrs("EPSG:4329");
        }
        catch (Exception e)
        {
            throw new ProcessException("Invalid Parameters", e);
        }
    }


    /**
     * Executes process algorithm on inputs and set output data
     */
    public void execute() throws ProcessException
    {
        URL url = null;

        try
        {
            initRequest();            
            
            // Hardwired to KVP currently
            if (log.isDebugEnabled())
                log.debug(owsUtils.buildURLQuery(request));
            dataStream = owsUtils.sendGetRequest(request).getInputStream();
                        
            // parse response
            WCSResponseReader reader = new WCSResponseReader();
            reader.parse(dataStream);

            // extract data from coverage array
            DataArray coverageArray = reader.getCoverageArray();
            int width = coverageArray.getComponent(0).getComponentCount();
            int length = coverageArray.getComponentCount();
            outputWidth.getData().setIntValue(width);
            outputLength.getData().setIntValue(length);
            outputCoverage.setData(coverageArray.getData());
            output.combineDataBlocks();
        }
        catch (Exception e)
        {
            throw new ProcessException("Error while requesting data from WCS server: " + url, e);
        }
        finally
        {
            endRequest();
        }
    }
        
    
    protected void initRequest()
    {
        // make sure previous request is cancelled
        endRequest();
        outputWidth.renewDataBlock();
        outputLength.renewDataBlock();
        
        // read lat/lon bbox
        double lon1 = bboxLon1.getData().getDoubleValue();///Math.PI*180;
        double lat1 = bboxLat1.getData().getDoubleValue();///Math.PI*180;
        double lon2 = bboxLon2.getData().getDoubleValue();///Math.PI*180;
        double lat2 = bboxLat2.getData().getDoubleValue();///Math.PI*180;
        
        double minX = Math.min(lon1, lon2);
        double maxX = Math.max(lon1, lon2);
        double minY = Math.min(lat1, lat2);
        double maxY = Math.max(lat1, lat2);
        
        request.getBbox().setMinX(minX);
        request.getBbox().setMaxX(maxX);
        request.getBbox().setMinY(minY);
        request.getBbox().setMaxY(maxY);
        
        double dX = Math.abs(maxX - minX);
        double dY = Math.abs(maxY - minY);
        
        // hack to automatically set skip factor according to BBOX
        request.setSkipX((int)Math.round(dY*20) + 1);
        request.setSkipY((int)Math.round(dX*20) + 1);
    }
    
    
    protected void endRequest()
    {
        try
        {
            if (dataStream != null)
                dataStream.close();
            
            dataStream = null;
            dataParser = null;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
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

import java.awt.image.DataBufferByte;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.vast.cdm.common.DataComponent;
import org.vast.cdm.common.DataType;
import org.vast.data.DataArray;
import org.vast.data.DataBlockByte;
import org.vast.data.DataGroup;
import org.vast.data.DataValue;
import org.vast.ogc.OGCExceptionReader;
import org.vast.ows.OWSUtils;
import org.vast.ows.wms.GetMapRequest;
import org.vast.ows.wms.WMSLayerCapabilities;
import org.vast.process.DataProcess;
import org.vast.process.ProcessException;
import org.vast.util.Bbox;
import com.sun.media.jai.codec.MemoryCacheSeekableStream;


/**
 * <p><b>Title:</b><br/>
 * WMS_Process
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Issues a request to a WMS server using provided parameters
 * and input bounding box and time, and outputs the resulting
 * image encapsulated in a data component structure.
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Alexandre Robin
 * @date Jan 20, 2006
 * @version 1.0
 */
public class WMS_Process extends DataProcess
{
    protected Log log = LogFactory.getLog(WMS_Process.class);
    protected DataValue bboxLat1, bboxLon1, bboxLat2, bboxLon2;
    protected DataValue outputWidth, outputHeight;
    protected DataArray outputImage;
    protected DataGroup output;
    protected InputStream dataStream;
    protected GetMapRequest request;
    protected OWSUtils owsUtils;
    protected int originalWidth;
    protected int originalHeight;
    protected boolean preserveAspectRatio = true;
    protected WMSLayerCapabilities caps;  //  need this for option chooser...
    

    public WMS_Process()
    {
        request = new GetMapRequest();
        request.setBbox(new Bbox());
        owsUtils = new OWSUtils();
    }


    /**
     * Initializes the process
     * Gets handles to input/output components
     */
    public void init() throws ProcessException
    {
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
            output = (DataGroup)outputData.getComponent("imageData");
            outputImage = (DataArray)output.getComponent("image");
            outputWidth = (DataValue)output.getComponent("width");
            outputHeight = (DataValue)output.getComponent("height");
            
            // image data type
            DataArray imgRowData = (DataArray)outputImage.getArrayComponent();
            DataGroup pixelData = (DataGroup)imgRowData.getArrayComponent();
            for (int i=0; i<pixelData.getComponentCount(); i++)
                ((DataValue)pixelData.getComponent(i)).setDataType(DataType.BYTE);
        }
        catch (Exception e)
        {
            throw new ProcessException("Invalid I/O structure", e);
        }
        
        try
        {
            // Read parameter values (must be fixed!)
            DataGroup wmsParams = (DataGroup)paramData.getComponent("wmsOptions");
            
            // service end point url
            String url = wmsParams.getComponent("endPoint").getData().getStringValue();
            String requestMethod = wmsParams.getComponent("requestMethod").getData().getStringValue();
            if (requestMethod.equalsIgnoreCase("post"))
                request.setPostServer(url);
            else
                request.setGetServer(url);
            
            // version
            String version = wmsParams.getComponent("version").getData().getStringValue();
            request.setVersion(version);
            
            // layer ID
            String layerID = wmsParams.getComponent("layer").getData().getStringValue();
            request.getLayers().add(layerID);
            
            // image format
            String format = wmsParams.getComponent("format").getData().getStringValue();
            request.setFormat(format);
            
            // image width
            originalWidth = wmsParams.getComponent("imageWidth").getData().getIntValue();
            request.setWidth(originalWidth);
            
            // image height
            originalHeight = wmsParams.getComponent("imageHeight").getData().getIntValue();
            request.setHeight(originalHeight);
            
            // image transparency
            boolean transparent = wmsParams.getComponent("imageTransparency").getData().getBooleanValue();
            request.setTransparent(transparent);
            
            request.setSrs("EPSG:4326");
            request.setExceptionType("application/vnd.ogc.se_xml");
        }
        catch (Exception e)
        {
            throw new ProcessException("Invalid Parameters", e);
        }
    }


    public void switchBytes(){
    	 //  need to dynamically alter process output....
        DataComponent pixel = output.getComponent("image").getComponent("row").getComponent("pixel");
        DataComponent red = pixel.getComponent("red");
        DataComponent green = pixel.getComponent("green");
        DataComponent blue = pixel.getComponent("blue");
        DataGroup pixArr = (DataGroup)pixel;
        pixArr.removeAllComponents();
        pixArr.addComponent(red);
        pixArr.addComponent(green);
        pixArr.addComponent(blue);
        output.renewDataBlock();
    }
    
    /**
     * Executes process algorithm on inputs and set output data
     */
    public void execute() throws ProcessException
    {
        RenderedImage renderedImage = null;
        
        try
        {
            initRequest();
            //switchBytes();
            
            if (log.isDebugEnabled())
                log.debug(owsUtils.buildURLQuery(request));
            URLConnection urlCon = owsUtils.sendGetRequest(request);
            
            //  Check on mimeType catches all three types (blank, inimage, xml)
            //  of OGC service exceptions
            String mimeType = urlCon.getContentType();
            if (mimeType.contains("xml") || mimeType.startsWith("application"))
            {
                OGCExceptionReader reader = new OGCExceptionReader();
                reader.parseException(urlCon.getInputStream());
            }
            else
            {
                // use JAI MemorySeekableStream for better performance
                dataStream = new MemoryCacheSeekableStream(urlCon.getInputStream());

                // Create the ParameterBlock and add the SeekableStream to it.
                ParameterBlock pb = new ParameterBlock();
                pb.add(dataStream);

                // decode image using JAI
                RenderedOp rop = JAI.create("stream", pb);
                
                if (rop != null)
                {
                    renderedImage = rop.createInstance();

                    // put data buffer in output datablock
                    byte[] data = ((DataBufferByte)renderedImage.getData().getDataBuffer()).getData();
                    ((DataBlockByte)outputImage.getData()).setUnderlyingObject(data);
                }
            }
            
            // adjust width and height of the output
            int width = 0;
            int height = 0;
            
            if (renderedImage != null)
            {
                width = renderedImage.getWidth();
                height = renderedImage.getHeight();
            }
            
            outputWidth.getData().setIntValue(width);
            outputHeight.getData().setIntValue(height);
            output.combineDataBlocks();
        }
        catch (Exception e)
        {
            throw new ProcessException("Error while requesting data from WMS server: " + request.getGetServer(), e);
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
        outputHeight.renewDataBlock();
        
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
        
        // adjust widht/height to match aspect ratio
        DataGroup wmsParams = (DataGroup)paramData.getComponent("wmsOptions");
        // image width
        originalWidth = wmsParams.getComponent("imageWidth").getData().getIntValue();
        
        // image height
        originalHeight = wmsParams.getComponent("imageHeight").getData().getIntValue();
        
        if (preserveAspectRatio)
        {
            int width = originalWidth;
            int height = originalHeight;
            
            // use largest measure as reference
            if (width >= height)
                height = (int)(width * (maxY - minY) / (maxX - minX));
            else
                width = (int)(height * (maxX - minX) / (maxY - minY));
            
            request.setWidth(width);
            request.setHeight(height);
        }
        
        //  reload other query options (since user can change them via GUI)
        // image format
        String format = wmsParams.getComponent("format").getData().getStringValue();
        request.setFormat(format);
        
        // image transparency
        boolean transparent = wmsParams.getComponent("imageTransparency").getData().getBooleanValue();
        request.setTransparent(transparent);
        
        request.setSrs("EPSG:4326");
    }
    
    
    protected void endRequest()
    {
        try
        {
            if (dataStream != null)
                dataStream.close();
            
            dataStream = null;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


	public boolean getPreserveAspectRatio() {
		return preserveAspectRatio;
	}


	public void setPreserveAspectRatio(boolean preserveAspectRatio) {
		this.preserveAspectRatio = preserveAspectRatio;
	}


	public WMSLayerCapabilities getCapabilities() {
		return caps;
	}


	public void setCapabilities(WMSLayerCapabilities caps) {
		this.caps = caps;
	}
}
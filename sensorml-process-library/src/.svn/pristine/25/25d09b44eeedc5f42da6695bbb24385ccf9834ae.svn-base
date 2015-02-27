/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are subject to the Mozilla Public License Version
 1.1 (the "License"); you may not use this file except in compliance with
 the License. You may obtain a copy of the License at
 http://www.mozilla.org/MPL/MPL-1.1.html
 
 Software distributed under the License is distributed on an "AS IS" basis,
 WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 for the specific language governing rights and limitations under the License.
 
 The Original Code is the "SensorML DataProcessing Engine".
 
 The Initial Developer of the Original Code is the VAST team at the University of Alabama in Huntsville (UAH). <http://vast.uah.edu> Portions created by the Initial Developer are Copyright (C) 2007 the Initial Developer. All Rights Reserved. Please Contact Mike Botts <mike.botts@uah.edu> for more information.
 
 Contributor(s): 
    Alexandre Robin <robin@nsstc.uah.edu>
 
******************************* END LICENSE BLOCK ***************************/

package org.sensorML.process;

import org.vast.process.*;
import org.vast.data.*;
import org.vast.math.*;
import org.vast.physics.*;
import org.vast.sweCommon.SweConstants;


/**
 * <p><b>Title:</b><br/>
 * LLA To ECEF coordinate Transform Process
 * </p>
 *
 * <p><b>Description:</b><br/>
 * TODO LLAToECEFProcess type description
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Alexandre Robin
 * @date Sep 2, 2005
 * @version 1.0
 */
public class LLAToECEF_Process extends DataProcess
{
    private AbstractDataComponent latData, lonData, altData;
    private AbstractDataComponent lrxData, lryData, lrzData;
    private AbstractDataComponent outputPos;
    private Matrix4d toEcefMatrix = new Matrix4d();;
    private Matrix3d rotMatrix = new Matrix3d();;
    private boolean nadirOriented = true;
    private int upAxis = 3;
    private int northAxis = 2;
    char[] rotationOrder = {'Z','X','Y'};
    private Datum datum;


    public LLAToECEF_Process()
    {
    }

    
    public void init() throws ProcessException
    {
    	try
        {
            // get input data containers + create appropriate Unit Converters
    		AbstractDataComponent locationData = inputData.getComponent("geoLocation");
            latData = locationData.getComponent("latitude");          
            lonData = locationData.getComponent("longitude");       
            altData = locationData.getComponent("altitude");
            
            // get orientation data containers + create appropriate Unit Converters
            AbstractDataComponent orientationData = inputData.getComponent("localOrientation");
            lrxData = orientationData.getComponent("x");          
            lryData = orientationData.getComponent("y");    
            lrzData = orientationData.getComponent("z");
            // read rotation order
            AbstractDataComponent rotComp = inputData.getComponent("rotationOrder");
            if(rotComp != null) {
	            String rotOrder = rotComp.getData().getStringValue();
	            if (rotOrder != null && rotOrder.length() == 3)
	            	rotationOrder = rotOrder.toCharArray();
            }
            // set up upAxis and northAxis depending on the frame (ENU, NED, etc...)
            String refFrame = (String)orientationData.getProperty(SweConstants.REF_FRAME);
            if (refFrame != null)
            {
            	if (refFrame.contains("NED"))
            	{
            		northAxis = 1;
            		upAxis = -3;
                    nadirOriented = true;
            	}
            	else if (refFrame.contains("ENU"))
            	{
            		northAxis = 2;
            		upAxis = 3;
                    nadirOriented = true;
            	}
            }
            
            // output data containers
        	outputPos = outputData.getComponent("ecefPosition");
        	outputPos.renewDataBlock();
        	
        	// set Datum to WGS84
        	datum = new Datum();
        }
        catch (Exception e)
        {
            throw new ProcessException(ioError, e);
        }
    }
   
    
    public void execute() throws ProcessException
    {
    	// get lat,lon,alt coordinates from input and convert to SI
    	double lat = latData.getData().getDoubleValue(0);
    	double lon = lonData.getData().getDoubleValue(0);
    	double alt = altData.getData().getDoubleValue(0);
        
        // convert to ECEF
        double[] ecefPos = MapProjection.LLAtoECF(lon, lat, alt, datum);
        toEcefMatrix.setIdentity();
                
        // compute nadir orientation if needed
        // default = north/east/up orientation
        if (nadirOriented == true)
        {
        	Vector3d ecfPosition;
        	ecfPosition = new Vector3d(ecefPos[0], ecefPos[1], ecefPos[2]);
            Vector3d toEcfNorth = NadirPointing.getEcfVectorToNorth(ecfPosition);
            Matrix3d nadirMatrix = NadirPointing.getRotationMatrix(ecfPosition, toEcfNorth, northAxis, upAxis);
            toEcefMatrix.setRotation(nadirMatrix);
        }
        
        // add translation coordinates
        for (int i=0; i<3; i++)
        	toEcefMatrix.setElement(i, 3, ecefPos[i]);
        
        // apply rotations in specified order
        computeRotMatrix();
        toEcefMatrix.mul(rotMatrix);
        
        // set output matrix values
        for (int i=0; i<15; i++)
        	outputPos.getData().setDoubleValue(i, toEcefMatrix.getElement(i/4, i%4));
    }
    
    
    /**
     * Computes attitude matrix with respect to local nadir orientation
     * @return 3x3 orientation matrix
     */
    protected void computeRotMatrix()
	{
        rotMatrix.setIdentity();
        
        // retrieve rotation values (converted to SI)
    	double rx = lrxData.getData().getDoubleValue();
    	double ry = lryData.getData().getDoubleValue();
    	double rz = lrzData.getData().getDoubleValue();

		// rotate in reverse order as the one given
        // because we want the opposite matrix
		for (int i=0; i<3; i++)
		{
			char axis = rotationOrder[2-i];
			
			switch (axis)
			{
				case 'X':
                    rotMatrix.rotateX(-rx);
					break;
					
				case 'Y':
                    rotMatrix.rotateY(-ry);
					break;
					
				case 'Z':
                    rotMatrix.rotateZ(rz); // reverse rz 
					break;
			}
		}
	}
}

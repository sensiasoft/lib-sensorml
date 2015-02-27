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

import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.vast.physics.Datum;
import org.vast.physics.MapProjection;
import org.vast.process.*;
import org.vast.data.*;


/**
 * <p><b>Title:</b><br/>
 * Ray Intersect Terrain Process
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Computes intersection of a 3D ray with terrain given as a
 * rectangular grid of altitude post in EPSG432ç projection.
 * It also allows height adjustment.
 * </p>
 *
 * <p>Copyright (c) 2009</p>
 * @author Alexandre Robin
 * @date Sep 2, 2007
 * @version 1.0
 */
public class RayIntersectTerrain_Process extends DataProcess
{
    protected Log log = LogFactory.getLog(RayIntersectTerrain_Process.class);
    
    protected DataValue xInput, yInput, zInput;
    protected DataValue heightAdjustmentParam;
    protected DataValue dxInput, dyInput, dzInput;
    protected DataValue xOutput, yOutput, zOutput;
    protected double heightAdjustment = 0;
    protected double[] R = new double[3];
    protected double[] U0 = new double[3];
    protected double[] U1 = new double[3];
    protected double[] P0 = new double[3];
    protected double[] P1 = new double[3];
    protected double[][] altitudeGrid;
    protected double xOrigin, yOrigin;
    protected double xOffset, yOffset;
    protected double meanGridAltitude = 0.0;
    protected SRTMUtil util = new SRTMUtil();
    

    public RayIntersectTerrain_Process()
    {
    }

    
    public void init() throws ProcessException
    {
    	try
        {	// get handle to ray origin vector data
    	    DataGroup rayOriginData = (DataGroup)inputData.getComponent("rayOrigin");
            xInput = (DataValue)rayOriginData.getComponent("x");           
            yInput = (DataValue)rayOriginData.getComponent("y");         
            zInput = (DataValue)rayOriginData.getComponent("z");
            
            // get handle to ray direction vector data
            DataGroup rayDirectionData = (DataGroup)inputData.getComponent("rayDirection");
            dxInput = (DataValue)rayDirectionData.getComponent("x");           
            dyInput = (DataValue)rayDirectionData.getComponent("y");         
            dzInput = (DataValue)rayDirectionData.getComponent("z");
            
    		// get handle to intersection point data
            DataGroup intersectionData = (DataGroup)outputData.getComponent("intersection");
            xOutput = (DataValue)intersectionData.getComponent("x");           
            yOutput = (DataValue)intersectionData.getComponent("y");         
            zOutput = (DataValue)intersectionData.getComponent("z");
            
            // get handle to heightAdjustment param
    		DataValue heightAdjustmentParam = (DataValue)paramData.getComponent("heightAdjustment");
    		if (heightAdjustmentParam != null)
    			heightAdjustment = heightAdjustmentParam.getData().getDoubleValue();
    		
    		// load DEM grid data
    		
        }
        catch (Exception e)
        {
            throw new ProcessException(ioError, e);
        }
    }
    
    
    public void execute() throws ProcessException
    {
        double maxError = 15; // 10m
        double error = 0.0;
        double altitude = meanGridAltitude;
        double x, y, z;
        Datum datum = new Datum();
        
        // get ray origin in global coordinate (from matrix)
        P0[0] = xInput.getData().getDoubleValue();
        P0[1] = yInput.getData().getDoubleValue();
        P0[2] = zInput.getData().getDoubleValue();       
        
        // get ray direction in local coordinates
        U0[0] = dxInput.getData().getDoubleValue();
        U0[1] = dyInput.getData().getDoubleValue();
        U0[2] = dzInput.getData().getDoubleValue();
        
        do
    	{
	        // compute new ellipsoid radius by adding altitude
	        R[0] = R[1] = datum.equatorRadius + heightAdjustment + altitude;
	        R[2] = datum.polarRadius + heightAdjustment + altitude;
    		
    		// scale vectors using ellipsoid radius
	        for (int i=0; i<3; i++)
	        {
	            P1[i] = P0[i] / R[i];
	            U1[i] = U0[i] / R[i];
	        }
	        
	        // computes polynomial coefficients (at^2 + bt + c = 0)
	        double a = 0.0;
	        double b = 0.0;
	        double c = -1.0;
	        for (int i=0; i<3; i++)
	        {
	            a += U1[i]*U1[i];
	            b += P1[i]*U1[i];
	            c += P1[i]*P1[i];
	        }
	
	        // computes discriminant
	        double dscrm = b * b - a * c;	        
	        double scalar = 0.0;
	        
	        // case of no valid solution
	        if (dscrm < 0.0 || c < 0.0)
	        {
	            // set max ray length to geocentric distance
	            scalar = Math.sqrt(c/a);
	            System.err.println("No intersection found");
	            
	            x = P0[0] + (U0[0] * scalar);
		        y = P0[1] + (U0[1] * scalar);
		        z = P0[2] + (U0[2] * scalar);
	        }
	        
	        // case of P exactly on ellipsoid surface
	        else if (c == 0.0)
	        {
	        	x = P0[0];
	        	y = P0[1];
	        	z = P0[2];
	        }
	        
	        // other normal cases
	        else
	        {
		        // always use smallest solution
		        if (b > 0.0)
		        	scalar = (-b + Math.sqrt(dscrm)) / a;
		        else
		            scalar = (-b - Math.sqrt(dscrm)) / a;
		        
		        x = P0[0] + (U0[0] * scalar);
		        y = P0[1] + (U0[1] * scalar);
		        z = P0[2] + (U0[2] * scalar);
	        }
	        
	        // get altitude at this position
	        double[] lla = MapProjection.ECFtoLLA(x, y, z, datum);
	        altitude = getAltitude(lla[0], lla[1]);
	        error = Math.abs(altitude - lla[2]);
    	}
    	while (error > maxError);
    	
        // assign new values to intersection point output
        xOutput.getData().setDoubleValue(x);
        yOutput.getData().setDoubleValue(y);
        zOutput.getData().setDoubleValue(z);
    }
    
    
    protected double getAltitude(double lon, double lat)
    {
        try
        {
            return util.getInterpolatedElevation(lat*180./Math.PI, lon*180./Math.PI);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return 0.0;
        }
    }
}

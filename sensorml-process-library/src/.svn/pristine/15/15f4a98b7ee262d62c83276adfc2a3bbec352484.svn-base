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


/**
 * <p><b>Title:</b><br/>
 * Ray Intersect Sphere Process
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Computes intersection of a 3D ray with an sphere which axes are
 * aligned with the axes of the referential of the ray. This process outputs
 * coordinates of the intersection point expressed in the same frame.
 * </p>
 * 

 * <p>Copyright (c) 2008</p>
 * @author Gregoire Berthiau
 * @date October 2008
 * @version 1.0
 */
public class RayIntersectSphere_Process extends DataProcess
{
    protected DataValue xInput, yInput, zInput;
    protected DataValue dxInput, dyInput, dzInput;
    protected DataValue xOutput, yOutput, zOutput;
    protected DataValue radiusData;
    protected double radius;
    protected double centerX, centerY, centerZ, originX, originY, originZ;
    protected double intersectionX, intersectionY, intersectionZ, directionX, directionY, directionZ;
    

    public RayIntersectSphere_Process()
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
            
            // get handle to ray direction vector data
            radiusData = (DataValue)paramData.getComponent("sphereRadius");
            
    		// get handle to intersection point data
            DataGroup intersectionData = (DataGroup)outputData.getComponent("intersection");
            xOutput = (DataValue)intersectionData.getComponent("x");           
            yOutput = (DataValue)intersectionData.getComponent("y");         
            zOutput = (DataValue)intersectionData.getComponent("z");
            
        }
        catch (Exception e)
        {
            throw new ProcessException(ioError, e);
        }
    }
    
    
    public void execute() throws ProcessException
    {
        
        // get ray origin in global coordinate (from matrix)
        centerX = xInput.getData().getDoubleValue();
        centerY = yInput.getData().getDoubleValue();
        centerZ = zInput.getData().getDoubleValue();       
        
        // get ray direction in local coordinates
        directionX = dxInput.getData().getDoubleValue();
        directionY = dyInput.getData().getDoubleValue();
        directionZ = dzInput.getData().getDoubleValue();             

        // get radius
        radius = radiusData.getData().getDoubleValue();
        
        double A = Math.pow(directionX, 2) + Math.pow(directionY, 2) + Math.pow(directionZ, 2);

        originX = centerX;
        originY = centerY;
        originZ = centerZ; 
        
        double B = 2 * (directionX * (originX - centerX) + directionY * (originY - centerY) + directionZ * (originZ - centerZ));
        double C = Math.pow((originX - centerX),2) + Math.pow((originY - centerY),2) + Math.pow((originZ - centerZ),2) - Math.pow(radius,2);
               
        double discriminant = B * B - 4 * A * C, t1 = 0, t2 = 0;
        double discriminantRoot = Math.pow(discriminant, 0.5);
               
        if (discriminant < 0.0)
            System.err.println("No intersection found");      	
        else if(discriminant==0)
        {
        	t1 = 0;
        	t2 = 0;
        }
        else
        {
        	t1 = - discriminantRoot/(2*A);
        	t2 = + discriminantRoot/(2*A);
        }
        
        double t = Math.min(t1,t2);
        
        intersectionX = centerX + t * directionX;
        intersectionY = centerY + t * directionY;
        intersectionZ = centerZ + t * directionZ; 
        
	xOutput.getData().setDoubleValue(intersectionX);
        yOutput.getData().setDoubleValue(intersectionY);
        zOutput.getData().setDoubleValue(intersectionZ);
    }
}

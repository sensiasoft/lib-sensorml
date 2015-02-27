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

import org.vast.physics.Datum;
import org.vast.process.*;
import org.vast.data.*;


/**
 * <p><b>Title:</b><br/>
 * Ray Intersect Ellipsoid Process
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Computes intersection of a 3D ray with an ellipsoid which axes are
 * aligned with the axes of the referential of the ray. This process outputs
 * coordinates of the intersection point expressed in the same frame.
 * </p>
 * 
 *  This version allows Height Above Ellipsoid adjustment
 *
 * <p>Copyright (c) 2007</p>
 * @author Alexandre Robin
 * @date Sep 2, 2007
 * @version 1.0
 */
public class RayIntersectEllipsoid2_Process extends DataProcess
{
    protected DataValue xInput, yInput, zInput, heightAboveEllipsoidData;
    protected DataValue dxInput, dyInput, dzInput;
    protected DataValue xOutput, yOutput, zOutput;
    protected double heightAboveEllipsoid = 0;
    protected double[] R = new double[3];
    protected double[] U0 = new double[3];
    protected double[] U1 = new double[3];
    protected double[] P0 = new double[3];
    protected double[] P1 = new double[3];
    

    public RayIntersectEllipsoid2_Process()
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
            
            // get handle to heightAboveEllipsoid data
    		DataValue heightAboveEllipsoidData = (DataValue)paramData.getComponent("heightAboveEllipsoid");
    		if (heightAboveEllipsoidData != null)
    		{
                heightAboveEllipsoid = heightAboveEllipsoidData.getData().getDoubleValue();
                System.out.println("RayIntersect2: heightAboveEllipsoid = "+heightAboveEllipsoid);
            }
            
            // read ellipsoid datum (or defaults to WGS84 if none provided)
            DataGroup datumData = (DataGroup)paramData.getComponent("datum");
            if (datumData != null)
            {
                R[0] = datumData.getComponent("xRadius").getData().getDoubleValue() + heightAboveEllipsoid;
                R[1] = datumData.getComponent("yRadius").getData().getDoubleValue() + heightAboveEllipsoid;
                R[2] = datumData.getComponent("zRadius").getData().getDoubleValue()+ heightAboveEllipsoid;
            }
            else
            {
            	// use WGS84
	           	 Datum datum = new Datum();
	           	 R[0] = datum.equatorRadius + heightAboveEllipsoid;
	           	 R[1] = R[0];
	           	 R[2] = datum.polarRadius + heightAboveEllipsoid;
            }
        }
        catch (Exception e)
        {
            throw new ProcessException(ioError, e);
        }
    }
    
    
    public void execute() throws ProcessException
    {
        
        // get ray origin in global coordinate (from matrix)
        P0[0] = xInput.getData().getDoubleValue();
        P0[1] = yInput.getData().getDoubleValue();
        P0[2] = zInput.getData().getDoubleValue();       
        
        // get ray direction in local coordinates
        U0[0] = dxInput.getData().getDoubleValue();
        U0[1] = dyInput.getData().getDoubleValue();
        U0[2] = dzInput.getData().getDoubleValue();             

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
        }
        
        // case of P exactly on ellipsoid surface
        else if (c == 0.0)
        {
            xOutput.getData().setDoubleValue(P0[0]);
            yOutput.getData().setDoubleValue(P0[1]);
            zOutput.getData().setDoubleValue(P0[2]);
            return;
        }
        
        // always use smallest solution
        else if (b > 0.0)
        {
            scalar = (-b + Math.sqrt(dscrm)) / a;
        }
        else
        {
            scalar = (-b - Math.sqrt(dscrm)) / a;
        }
        
        // assign new values to intersection point output
        xOutput.getData().setDoubleValue(P0[0] + (U0[0] * scalar));
        yOutput.getData().setDoubleValue(P0[1] + (U0[1] * scalar));
        zOutput.getData().setDoubleValue(P0[2] + (U0[2] * scalar));
    }
}

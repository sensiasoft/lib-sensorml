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
    Gregoire Berthiau <berthiau@nsstc.uah.edu>
 
******************************* END LICENSE BLOCK ***************************/

package org.sensorML.process;

import org.vast.data.*;
import org.vast.math.*;
import org.vast.physics.Datum;
import org.vast.physics.MapProjection;
import org.vast.process.*;


/**
 * <p><b>Title:</b><br/>
 * LLA to PTZ process for Axis camera
 * </p>
 *
 * <p><b>Description:</b><br/>
 * determines the PTZ parameters of the axis camera in function of the positions
 * of the camera, of the target and of the zero-pan zero-tilt point in geodetic coordinates
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Gregoire Berthiau
 * @date Feb 20, 2008
 * @version 1.0
 */
public class LLAToPTZ_Process extends DataProcess
{
    private DataValue latTData, lonTData, altTData, latCData, lonCData;
    private DataValue widthData, altCData, latZData, lonZData, altZData, panAtRefData;
    private DataValue panAngleData, tiltAngleData, zoomFactorData;
    boolean interpolation = true;
    private Datum datum;

    
    public LLAToPTZ_Process()
    {    	
    }
   
    @Override
    public void init() throws ProcessException
    {
        try
        {
            // input mappings
        
        	AbstractDataComponent targetData = inputData.getComponent("targetLocation");
            latTData = (DataValue)targetData.getComponent("latitude");          
            lonTData = (DataValue)targetData.getComponent("longitude");       
            altTData = (DataValue)targetData.getComponent("altitude");
            
            widthData = (DataValue)inputData.getComponent("width");           
            
            // parameter mappings
            
            AbstractDataComponent cameraData = paramData.getComponent("cameraLocation");
            latCData = (DataValue)cameraData.getComponent("latitude");          
            lonCData = (DataValue)cameraData.getComponent("longitude");       
            altCData = (DataValue)cameraData.getComponent("altitude");
            
            AbstractDataComponent zerosPTData = paramData.getComponent("referencePanLocation");
            latZData = (DataValue)zerosPTData.getComponent("latitude");          
            lonZData = (DataValue)zerosPTData.getComponent("longitude");       
            altZData = (DataValue)zerosPTData.getComponent("altitude");            
            
            panAtRefData = (DataValue)paramData.getComponent("panAngleAtReferenceLocation");

            // output mapping
            panAngleData = (DataValue)outputData.getComponent("panAngle");
            tiltAngleData = (DataValue)outputData.getComponent("tiltAngle");
            zoomFactorData = (DataValue)outputData.getComponent("zoomFactor");
            
            // set Datum to default WGS84
            datum = new Datum();
        }
        catch (RuntimeException e)
        {
            e.printStackTrace();
        }
    }
    
    @Override
    public void execute() throws ProcessException
    {
    	// get lat,lon,alt coordinates from input and convert to SI
    	double deg2rad = Math.PI/180;
    	double latC = latCData.getData().getDoubleValue()*deg2rad;
    	double lonC = lonCData.getData().getDoubleValue()*deg2rad;
    	double altC = altCData.getData().getDoubleValue();
    	
    	double latT = latTData.getData().getDoubleValue()*deg2rad;
    	double lonT = lonTData.getData().getDoubleValue()*deg2rad;
    	double altT = altTData.getData().getDoubleValue();
    	
    	double latZ = latZData.getData().getDoubleValue()*deg2rad;
    	double lonZ = lonZData.getData().getDoubleValue()*deg2rad;
    	double altZ = altZData.getData().getDoubleValue();
    	
    	double panAtRef = panAtRefData.getData().getDoubleValue();
    	double width = widthData.getData().getDoubleValue();
        
    	// convert to ECEF
        double[] ecefPosT = MapProjection.LLAtoECF(lonT, latT, altT, datum);
        double[] ecefPosC = MapProjection.LLAtoECF(lonC, latC, altC, datum);
        double[] ecefPosZ = MapProjection.LLAtoECF(lonZ, latZ, altZ, datum);
       
        double[] earthCenter = {1, 1, 1};
        double[] cctPlaneCoefficients = new double[4];
        double[] cczPlaneCoefficients = new double[4];
        
        cczPlaneCoefficients = determinePlaneEquation(ecefPosC, ecefPosZ, earthCenter);
        cctPlaneCoefficients = determinePlaneEquation(ecefPosC, ecefPosT, earthCenter);
        
        double a1, a2, b1, b2, c1, c2;
        
        a1 = cczPlaneCoefficients[0]; 
        b1 = cczPlaneCoefficients[1];
        c1 = cczPlaneCoefficients[2];

        a2 = cctPlaneCoefficients[0]; 
        b2 = cctPlaneCoefficients[1];
        c2 = cctPlaneCoefficients[2];
        
        double panAngle = Math.acos((a1*a2 + b1*b2 + c1*c2)/(Math.sqrt(a1*a1 + b1*b1 + c1*c1) * Math.sqrt(a2*a2 + b2*b2 + c2*c2)));
        System.out.println(-panAngle/deg2rad);
        panAngleData.getData().setDoubleValue(-panAngle/deg2rad + panAtRef);
        
        Vector3d ECCamera = new Vector3d(ecefPosC[0], ecefPosC[1], ecefPosC[2]);
        Vector3d ECTarget = new Vector3d(ecefPosT[0], ecefPosT[1], ecefPosT[2]);
        
        double earthRadiusAtCamera = Math.sqrt(ecefPosC[0]*ecefPosC[0] + ecefPosC[1]*ecefPosC[1] + ecefPosC[2]*ecefPosC[2]);
        
        ECCamera.normalize();
        ECTarget.normalize();
        double vectorDotProduct = ECCamera.dot(ECTarget);
        
        double altL = (earthRadiusAtCamera + altC)/vectorDotProduct - earthRadiusAtCamera;
        
        double[] ecefPosL = MapProjection.LLAtoECF(lonT, latT, altL, datum);
        
        Vector3d CT = new Vector3d(ecefPosT[0]-ecefPosC[0], ecefPosT[1]-ecefPosC[1], ecefPosT[2]-ecefPosC[2]);
        Vector3d CL = new Vector3d(ecefPosL[0]-ecefPosC[0], ecefPosL[1]-ecefPosC[1], ecefPosL[2]-ecefPosC[2]);
        double D = Math.sqrt(CT.x*CT.x + CT.y*CT.y + CT.z*CT.z);
        CT.normalize();
        CL.normalize();
        
        double angle = Math.acos(CL.dot(CT));
        double tiltAngle = 0;
        
        if (altL>=altT){
        	tiltAngle = Math.abs(angle);
        	}
        if (altL<altT){
        	tiltAngle = -Math.abs(angle);
        	}

        tiltAngleData.getData().setDoubleValue(-tiltAngle/deg2rad); 
        
        double alpha = Math.acos(vectorDotProduct);
        double DL = (altC + earthRadiusAtCamera) * Math.tan(alpha);       
        double oppositeAlpha = Math.PI/2-alpha;
        double Dopp = DL*Math.sin(oppositeAlpha);
        double alpha2 = Math.acos(Dopp/D);
        double tiltangle = alpha2 - alpha;

        double[] zoom = {9999, 7940, 6074, 3986, 3054, 1997, 1397, 919, 597, 399, 298, 205, 81, 1};
                
        double[] FOV = {0.047122426, 0.049170419, 0.05940875, 0.083967025, 0.106456723, 0.149312146, 0.19898833, 
        				0.26634455, 0.352850296, 0.457038489, 0.507277076, 0.64380847, 0.841955671, 0.982253087};
        
        double angleFOV = 2 * Math.atan(width/2/D);
        
        double zoomFactor = 0;
        
        if(angleFOV<=FOV[0]){
        	interpolation = false;
    		zoomFactor = zoom[0];
    		}
    	if(angleFOV>=FOV[FOV.length-1]){
    		interpolation = false;
    		zoomFactor = zoom[FOV.length-1];
    		}
        
    	int index = 0;
    	
    	if(interpolation){
    		for(int i=0; i<FOV.length-1; i++){
    			if(angleFOV>FOV[i] && angleFOV<=FOV[i+1]){
    				index = i;
    				break;
    				}
    			}
    		double slope = (zoom[index+1]-zoom[index])/(FOV[index+1]-FOV[index]);
    		zoomFactor = zoom[index] + slope * (FOV[index]-angleFOV);
    		}
    	
    	zoomFactorData.getData().setDoubleValue(zoomFactor);
    	
    }
    
    
    public double[] determinePlaneEquation(double[] point1, double[] point2, double[] point3) throws ProcessException
    {
    	double d = 1;
    	
    	double Det = point1[0]*(point2[1]*point3[2]-point3[1]*point2[2])
    				-point2[0]*(point1[1]*point3[2]-point3[1]*point1[2])
    				+point3[0]*(point1[1]*point2[2]-point2[1]*point1[2]);
    	
    	double a = -1*((point2[1]*point3[2]-point3[1]*point2[2])
    	                   -(point1[1]*point3[2]-point3[1]*point1[2])
    	                   +(point1[1]*point2[2]-point2[1]*point1[2]));
    	
    	double b = -1*(point1[0]*(1*point3[2]-1*point2[2])
						   -point2[0]*(1*point3[2]-1*point1[2])
				           +point3[0]*(1*point2[2]-1*point1[2]));
    	
    	double c = -1*(point1[0]*(point2[1]*1-point3[1]*1)
    					   -point2[0]*(point1[1]*1-point3[1]*1)
    					   +point3[0]*(point1[1]*1-point2[1]*1));
    	
    	double[] planeCoefficients = {a, b, c, d};    	
        return planeCoefficients;
    }   
    
}
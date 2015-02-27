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

import org.vast.data.*;
import org.vast.math.Vector3d;
import org.vast.process.*;


/**
 * <p><b>Title:</b><br/>
 * CSM_FrameCameraModel_Process
 * </p>
 *
 * <p><b>Description:</b><br/>
 * implement the Community Sensor Model for a frame camera
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Gregoire Berthiau
 * @date July 22, 2008
 * @version 1.0
 */
public class CSM_FrameCameraModel_Process extends DataProcess {



   public CSM_FrameCameraModel_Process() {
   }

   AbstractDataComponent focalLengthData, pixelGridCharacteristicsData, principalPointCoordinatesData;
   AbstractDataComponent affineDistortionCoefficientsData, radialDistortionCoefficientsData; 
   AbstractDataComponent decenteringCoefficientsData;
   AbstractDataComponent pixelGridCoordinatesData, viewVectorData;
   DataValue rData, cData, xData, yData, zData;   
   double numberOfRowsInImage, numberOfColumnsInImage, rowSpacing, columnSpacing;
   double x0, y0;
   double a1, b1, c1, a2, b2, c2;
   double k1, k2, k3;
   double p1, p2;
   AbstractDataComponent pointsData;
   DataValue intersectionExistsData;
   AbstractDataComponent URLongitudeData, URLatitudeData, LLLongitudeData, LLLatitudeData;
   boolean intersectionExists = false;
   
   /**
   * Initializes the process
   * Get handles to input/output components
   */
   public void init() throws ProcessException {

      try {

    	// initialize inputs mapping
    	  pixelGridCoordinatesData = inputData.getComponent("pixelGridCoordinates");
    	  rData = (DataValue)pixelGridCoordinatesData.getComponent("r");
    	  cData = (DataValue)pixelGridCoordinatesData.getComponent("c");
    	  
    	// initialize outputs mapping
    	  viewVectorData = outputData.getComponent("viewVector");
    	  xData = (DataValue)viewVectorData.getComponent("x");
    	  yData = (DataValue)viewVectorData.getComponent("y");
    	  zData = (DataValue)viewVectorData.getComponent("z");
    	  
         // initialize parameters mapping
    	  focalLengthData = paramData.getComponent("focalLength");
    	      	  
    	  pixelGridCharacteristicsData = paramData.getComponent("pixelGridCharacteristics");
    	  numberOfRowsInImage = pixelGridCharacteristicsData.getComponent("numberOfRowsInImage").getData().getDoubleValue();
    	  numberOfColumnsInImage = pixelGridCharacteristicsData.getComponent("numberOfColumnsInImage").getData().getDoubleValue();
    	  rowSpacing = pixelGridCharacteristicsData.getComponent("rowSpacing").getData().getDoubleValue();
    	  columnSpacing = pixelGridCharacteristicsData.getComponent("columnSpacing").getData().getDoubleValue();
    	  
    	  principalPointCoordinatesData = paramData.getComponent("principalPointCoordinates");
    	  x0 = principalPointCoordinatesData.getComponent("x0").getData().getDoubleValue();
    	  y0 = principalPointCoordinatesData.getComponent("y0").getData().getDoubleValue();
    	  
    	  affineDistortionCoefficientsData = paramData.getComponent("affineDistortionCoefficients");
    	  a1 = affineDistortionCoefficientsData.getComponent("a1").getData().getDoubleValue();
    	  a2 = affineDistortionCoefficientsData.getComponent("a2").getData().getDoubleValue();
    	  b1 = affineDistortionCoefficientsData.getComponent("b1").getData().getDoubleValue();
    	  b2 = affineDistortionCoefficientsData.getComponent("b2").getData().getDoubleValue();   	  
    	  c1 = affineDistortionCoefficientsData.getComponent("c1").getData().getDoubleValue();
    	  c2 = affineDistortionCoefficientsData.getComponent("c2").getData().getDoubleValue();
    	  
    	  radialDistortionCoefficientsData = paramData.getComponent("radialDistortionCoefficients");
    	  k1 = radialDistortionCoefficientsData.getComponent("k1").getData().getDoubleValue();
    	  k2 = radialDistortionCoefficientsData.getComponent("k2").getData().getDoubleValue();
    	  k3 = radialDistortionCoefficientsData.getComponent("k3").getData().getDoubleValue();
    	  
    	  decenteringCoefficientsData = paramData.getComponent("decenteringCoefficients");
    	  p1 = decenteringCoefficientsData.getComponent("p1").getData().getDoubleValue();
    	  p2 = decenteringCoefficientsData.getComponent("p2").getData().getDoubleValue();
    	  
      }
      catch (ClassCastException e) {
         throw new ProcessException("Invalid I/O data", e);
      }
   }

   /**
   * Executes the process
   * Get current values for all components and then executes
   */
   public void execute() throws ProcessException {
	   
	  // get the value of the inputs
	  double r = rData.getData().getDoubleValue();
	  double c = cData.getData().getDoubleValue();
	  double focalLength = focalLengthData.getData().getDoubleValue();
	  
	  double x = 0, y = 0, z = 0; 	
	  
	// goes from image pixel space with corner as origin to centered image pixel space
	  double l = (r - numberOfRowsInImage/2);
      double s = (c - numberOfColumnsInImage/2);
	  
      if(l<0)
    	  l = l + 0.5;
      else if(l>0)
    	  l = l - 0.5;
      
      if(s<0)
    	  s = s + 0.5;
      else if(l>0)
    	  s = s - 0.5;
      
      // account for affine distorsion
	//  double Xpixel = a1*l + b1*s + c1;
	//  double Ypixel = a2*l + b2*s + c2;
	  
	  // goes from image pixel space to image mm space
	  double X = l * rowSpacing; //Ypixel * rowSpacing;
	  double Y = s * columnSpacing;//Xpixel * columnSpacing;
	  
	  double xbar = X - x0;
      double ybar = Y - y0;
      double radial = Math.pow((xbar*xbar + ybar*ybar), 0.5);
      
      double x2 = X + xbar * (k1*Math.pow(radial,2) +k2*Math.pow(radial,4) + k3*Math.pow(radial,6)) 
      				+ p1 * (2*Math.pow(xbar,2) + Math.pow(radial,2)) + 2*p2*xbar*ybar;
   
      double y2 = Y + ybar * (k1*Math.pow(radial,2) +k2*Math.pow(radial,4) + k3*Math.pow(radial,6)) 
					+ p1 * (2*Math.pow(ybar,2) + Math.pow(radial,2)) + 2*p2*xbar*ybar;

	  x = x2;
	  y = y2;

	  z = focalLength;   
	  
	  // normalize the look vector (meb - 2007.07.24)
	  Vector3d v = new Vector3d(x,y,z);
	  v.normalize();
      
	  xData.getData().setDoubleValue(v.x);
	  yData.getData().setDoubleValue(v.y);
	  zData.getData().setDoubleValue(v.z);
      
	  // get the value to the outputs
//	  xData.getData().setDoubleValue(x);
//	  yData.getData().setDoubleValue(y);
//	  zData.getData().setDoubleValue(z);	  
	 }

}

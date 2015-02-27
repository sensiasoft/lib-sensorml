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

import org.vast.cdm.common.DataBlock;
import org.vast.cdm.common.DataType;
import org.vast.data.*;
import org.vast.process.*;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Polygon;


/**
 * <p><b>Title:</b><br/>
 * BBoxandPolygonIntersection_Process
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Computes the intersections of a bounding box with a set of polygons
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Gregoire Berthiau
 * @date Jan 31, 2008
 * @version 1.0
 */
public class BBoxPolygonIntersection2_Process extends DataProcess {



   public BBoxPolygonIntersection2_Process() {
   }

   AbstractDataComponent boundingBoxData, footprintData, intersectionData, numberOfPointsData;
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

         // initialize I/O components
    	  boundingBoxData = inputData.getComponent("boundingBox");
    	  footprintData = inputData.getComponent("footprintData");
    	  intersectionData = outputData.getComponent("intersection");
    	  intersectionExistsData = (DataValue)outputData.getComponent("intersectionExists");
    	  numberOfPointsData = intersectionData.getComponent("numberOfPoints");
    	  pointsData = intersectionData.getComponent("points");
    	  URLongitudeData = inputData.getComponent("boundingBox").getComponent("URLongitude");
  	   	  URLatitudeData = inputData.getComponent("boundingBox").getComponent("URLatitude");
  	      LLLongitudeData = inputData.getComponent("boundingBox").getComponent("LLLongitude");
  	      LLLatitudeData = inputData.getComponent("boundingBox").getComponent("LLLatitude");
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
	   
	// Set up the polygon against which line intersection will be tested
	    double LLLatitude = (LLLatitudeData.getData().getDoubleValue());
	   	double URLatitude = (URLatitudeData.getData().getDoubleValue());
	   	double URLongitude = (URLongitudeData.getData().getDoubleValue());
	   	double LLLongitude = (LLLongitudeData.getData().getDoubleValue());  	
	   	
	   	GeometryFactory geomFactoryBB = new GeometryFactory();
		Coordinate[] coordinatesBB = new Coordinate[] {
				new Coordinate(LLLatitude, LLLongitude), new Coordinate(LLLatitude, URLongitude),
				new Coordinate(URLatitude, URLongitude), new Coordinate(URLatitude, LLLongitude),
				new Coordinate(LLLatitude, LLLongitude) };
		Geometry boundingBox = geomFactoryBB.createPolygon(geomFactoryBB.createLinearRing(coordinatesBB), null);
		
		
	    int numberOfLines = inputData.getComponent("footprintData").getComponent(3).getData().getIntValue();
	    int numberOfPointsPerLine = inputData.getComponent("footprintData").getComponent(4).getData().getIntValue();
	    
	    int numberOfElements = 2 * numberOfLines * numberOfPointsPerLine;
	    double[] elements = new double[numberOfElements];
	    double [][] points = new double[numberOfElements/2][2];
	    int a = 0, b = 0, c = 0, d = 0, e = 0, w = 0;

	    for(int i=0; i<numberOfElements; i++){
	    elements[i] = inputData.getComponent("footprintData").getComponent(5).getData().getDoubleValue(i) / Math.PI * 180.0; 
	    a = i%2;
	    d = i%(numberOfLines*numberOfPointsPerLine);
	    b = ((int)Math.floor((double)i/2))%(2*numberOfPointsPerLine);
	    c = ((int)Math.floor((double)i/2/numberOfPointsPerLine))%(2*numberOfPointsPerLine*numberOfLines);

	    points[w][a] = elements[i];
	    if(a==1){
	    	w++;
	    	}
	    }
	    
	    GeometryFactory geomFactorySwath = new GeometryFactory();
	    double[] point = new double[2];
	    double[] firstPoint = new double[2];
	    Coordinate[] coordinates = new Coordinate[2*numberOfPointsPerLine+2*(numberOfLines-2)+1];

	    Geometry C = null, intersection = null;
	    intersectionExists = false;
	    
	    int m = 0;
	    double minLon = 300, maxLon = -300;
	    double minLat = 300, maxLat = -300;
    	boolean crossingDemarcationLine = false;
    	
	    // FIRST HALF OF THE POLYGON (AND GRID)
	    for(int j=0; j<numberOfLines-1; j++){    	
	    	if(j==0){
	    		for(int i=0; i<numberOfPointsPerLine; i++){
	    			if(i==0){
	    				firstPoint[0] = points[i][0];
			    		firstPoint[1] = points[i][1];	
	    			}
	    			point[0] = points[i][0];
		    		point[1] = points[i][1];
		    		coordinates[m] = new Coordinate(point[0], point[1]);
		    		m++;
		    		minLat = Math.min(minLat, point[0]);
			    	maxLat = Math.max(maxLat, point[0]);
		    		minLon = Math.min(minLon, point[1]);
			    	maxLon = Math.max(maxLon, point[1]);
	    		}
	    	}	    	
	    	if((j>0) && (j<numberOfLines-1)){
	    		point[0] = points[Integer.parseInt(Integer.toString(j)+"9")][0];
		    	point[1] = points[Integer.parseInt(Integer.toString(j)+"9")][1];
		    	coordinates[m] = new Coordinate(point[0], point[1]);
		    	m++;
		    	minLat = Math.min(minLat, point[0]);
		    	maxLat = Math.max(maxLat, point[0]);
		    	minLon = Math.min(minLon, point[1]);
		    	maxLon = Math.max(maxLon, point[1]);
	    	}
	    }
	    // SECOND HALF OF THE POLYGON (AND GRID)
	    for(int j=numberOfLines-1; j>0; j--){
	    	if(j==numberOfLines-1){
	    		for(int i=numberOfPointsPerLine-1; i>-1; i--){
	    			point[0] = points[Integer.parseInt(Integer.toString(j)+Integer.toString(i))][0];
		    		point[1] = points[Integer.parseInt(Integer.toString(j)+Integer.toString(i))][1];
		    		coordinates[m] = new Coordinate(point[0], point[1]);
		    		m++;
		    		minLat = Math.min(minLat, point[0]);
			    	maxLat = Math.max(maxLat, point[0]);
		    		minLon = Math.min(minLon, point[1]);
			    	maxLon = Math.max(maxLon, point[1]);
	    		}
	    	}	    	
	    	if((j>0) && (j<numberOfLines-1)){
	    		point[0] = points[Integer.parseInt(Integer.toString(j)+"0")][0];
		    	point[1] = points[Integer.parseInt(Integer.toString(j)+"0")][1];
		    	coordinates[m] = new Coordinate(point[0], point[1]);
		    	m++;
		    	minLat = Math.min(minLat, point[0]);
		    	maxLat = Math.max(maxLat, point[0]);
		    	minLon = Math.min(minLon, point[1]);
		    	maxLon = Math.max(maxLon, point[1]);
	    	}
	    }
		// 2007-02-13 6am
	    // 2007-02-14 6am
	    coordinates[m] = new Coordinate(firstPoint[0], firstPoint[1]);
	    
	    if(minLat<-85 && maxLat>85){
    		crossingDemarcationLine = true;
    	}
	    if(minLon*maxLon<-30625){
    		crossingDemarcationLine = true;
    	}
	    
	    Geometry swath = geomFactorySwath.createPolygon(geomFactorySwath.createLinearRing(coordinates), null);
	    
	    if(swath.intersects(boundingBox)){
    		intersectionExists = true;
    		intersection = swath.intersection(boundingBox); 	
    	}
	    
	    
	    if (intersectionExists) {
			C = intersection;
			numberOfPointsData.getData().setIntValue(C.getNumPoints());
			DataValue pointNumber = new DataValue("numberOfPoints",DataType.INT);
			DataBlockDouble latDBD = new DataBlockDouble(C.getNumPoints());
			DataBlockDouble lonDBD = new DataBlockDouble(C.getNumPoints());
			DataBlockDouble pointDBD = new DataBlockDouble(2 * C.getNumPoints());
			DataBlockInt elementDBI = new DataBlockInt(1);
			elementDBI.setIntValue(C.getNumPoints());
			pointNumber.setData(elementDBI);
			Coordinate[] coordinateIntersection = C.getCoordinates();
			Coordinate intersectionPoint = null;
			
			int n = 0;
			for (int i = 0; i < C.getNumPoints(); i++) {

				intersectionPoint = coordinateIntersection[i];
				latDBD.setDoubleValue(i, intersectionPoint.x);
				lonDBD.setDoubleValue(i, intersectionPoint.y);
				pointDBD.setDoubleValue(n, intersectionPoint.x);
				n++;
				pointDBD.setDoubleValue(n, intersectionPoint.y);
				n++;

			}

			int totalCount = 2 + C.getNumPoints() * 2;
			
			DataBlockString exists = new DataBlockString(1);
			exists.setStringValue(0, "yes");
			DataBlockInt number = new DataBlockInt(1);
			number.setIntValue(C.getNumPoints());
			
			DataBlockMixed outputBlock = new DataBlockMixed(3, totalCount);
			outputBlock.setBlock(0, exists);
			outputBlock.setBlock(1, number);
			outputBlock.setBlock(2, pointDBD);			
			outputData.setData(outputBlock);
			
			DataBlockMixed intersectionBlock = new DataBlockMixed(2, totalCount-1);
			intersectionBlock.setBlock(0, number);
			intersectionBlock.setBlock(1, pointDBD);			
			outputData.getComponent("intersection").setData(intersectionBlock);
					
			outputData.getComponent("intersection").getComponent("numberOfPoints").setData(number);
			outputData.getComponent("intersection").getComponent("points").setData(pointDBD);
			outputData.getComponent("intersection").getComponent("points").getComponent("point").setData(pointDBD);
			outputData.getComponent("intersection").getComponent("points").getComponent("point").getComponent("lon").setData(lonDBD);
			outputData.getComponent("intersection").getComponent("points").getComponent("point").getComponent("lat").setData(latDBD);
			
		}
	    
	    if (!intersectionExists) {

			DataBlockDouble latDBD = new DataBlockDouble(2);
			DataBlockDouble lonDBD = new DataBlockDouble(2);
			DataBlockDouble pointDBD = new DataBlockDouble(4);
			
			int nn = 0;
			for (int i = 0; i < 2; i++) {
				latDBD.setDoubleValue(i, -9999);
				lonDBD.setDoubleValue(i, -9999);
				pointDBD.setDoubleValue(nn, -9999);
				nn++;
				pointDBD.setDoubleValue(nn, -9999);
				nn++;
			}

			int totalCount = 6;
			
			DataBlockString exists = new DataBlockString(1);
			exists.setStringValue(0, "no");
			DataBlockInt number = new DataBlockInt(1);
			number.setIntValue(2);
			
			DataBlockMixed outputBlock = new DataBlockMixed(3, totalCount);
			outputBlock.setBlock(0, exists);
			outputBlock.setBlock(1, number);
			outputBlock.setBlock(2, pointDBD);			
			outputData.setData(outputBlock);
			
			DataBlockMixed intersectionBlock = new DataBlockMixed(2, totalCount-1);
			intersectionBlock.setBlock(0, number);
			intersectionBlock.setBlock(1, pointDBD);			
			outputData.getComponent("intersection").setData(intersectionBlock);
					
			outputData.getComponent("intersection").getComponent("numberOfPoints").setData(number);
			outputData.getComponent("intersection").getComponent("points").setData(pointDBD);
			outputData.getComponent("intersection").getComponent("points").getComponent("point").setData(pointDBD);
			outputData.getComponent("intersection").getComponent("points").getComponent("point").getComponent("lon").setData(lonDBD);
			outputData.getComponent("intersection").getComponent("points").getComponent("point").getComponent("lat").setData(latDBD);
			
		}
	
	 }

}

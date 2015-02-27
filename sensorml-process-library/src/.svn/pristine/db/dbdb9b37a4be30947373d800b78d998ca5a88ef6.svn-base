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

import org.vast.cdm.common.DataBlock;
import org.vast.data.*;
import org.vast.math.*;
import org.vast.physics.Datum;
import org.vast.physics.MapProjection;
import org.vast.process.*;


/**
 * <p><b>Title:</b><br/>
 * ENU Pointing Matrix Process
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Generates a rotation matrix corresponding to an ENU orientation
 * where the Z axis is aligned with nadir direction (normal to ellipsoid
 * surface) and pointing up, the Y axis orthogonal to Z and in the plane
 * of the vector to true north (i.e pointing north) and the X axis chosen
 * so that the result XYZ reference frame is direct (i.e. pointing east).
 * </p>
 *
 * <p>Copyright (c) 2010</p>
 * @author Alexandre Robin
 * @date Apr 19, 2010
 * @version 1.0
 */
public class ENUPointingMatrix3_Process extends DataProcess
{
    private DataValue latData, lonData;
    private DataArray outputMatrix;
    private Datum datum;
    
    
    public ENUPointingMatrix3_Process()
    {    	
    }

    
    @Override
    public void init() throws ProcessException
    {
        try
        {
            // input mappings
            DataGroup locVector = (DataGroup)inputData.getComponent("location");
            latData = (DataValue)locVector.getComponent("latitude");
            lonData = (DataValue)locVector.getComponent("longitude");
            
            // output mapping
            outputMatrix = (DataArray)outputData.getComponent("posMatrix");
            
            // set Datum to default
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
        double lat = 0.0;
        double lon = 0.0;
        
        if (latData != null)
            lat = latData.getData().getDoubleValue();

        if (lonData != null)
            lon = lonData.getData().getDoubleValue();

        Matrix3d newMatrix = null;
        Vector3d heading = new Vector3d();
        Vector3d other = new Vector3d();
        
        double[] ecf = MapProjection.LLAtoECF(lon, lat, 0.0, datum);
        double[] up_ecf = MapProjection.LLAtoECF(lon, lat, 1e3, datum);
        Vector3d location = new Vector3d(ecf[0], ecf[1], ecf[2]);
        Vector3d up = new Vector3d(up_ecf[0] - ecf[0], up_ecf[1] - ecf[1], up_ecf[2] - ecf[2]);
        up.normalize();
        
        // compute vector to true north
        double polarRadius = datum.polarRadius;
        Vector3d northPole = new Vector3d(0.0, 0.0, polarRadius);
        Vector3d toNorth = new Vector3d();
        toNorth.sub(northPole, location);

        // Y pointing north and Z pointing up
        other.cross(toNorth, up);
        other.normalize();
        heading.cross(up, other);
        newMatrix = new Matrix3d(other, heading, up);
        
        // assign values to output matrix
        DataBlock data = outputMatrix.getData();
        for (int i=0; i<9; i++)
            data.setDoubleValue(i, newMatrix.getElement(i/3, i%3));
    }
}
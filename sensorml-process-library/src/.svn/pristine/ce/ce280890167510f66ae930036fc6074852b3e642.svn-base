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
   Kevin Carter <kcarter@nsstc.uah.edu>
   Dr. Mike Botts <mike.botts@uah.edu>

******************************* END LICENSE BLOCK ***************************/

package org.sensorML.process;

import org.vast.data.*;
import org.vast.process.*;

/**
* <p><b>Title:RPC_Process</b><br/>
* 
* </p>
* <p><b>Description:</b><br/>
* 	Process for supporting Rational Polynomial Coefficients (RPC) 
* 	  assuming object-to-image mapping
* 	  (e.g. typically converting Latitude,Longitude,Altitude to image coordinates)
*
* </p>
* <p>Copyright (c) 2007</p>
* @author Kevin Carter
* @date March 10, 2006
* @version 1.0
* 
* ---------
* @author Mike Botts
* @date May 11, 2006
* corrected, cleaned up, and validated
*
* @date October 5, 2007 
* updated for revised schema
* 
* @date January 3, 2008
* updated for revised schema
*/
public class RPC_Process2 extends DataProcess
{
	DataValue inputX, inputY, inputZ;
	DataValue outputX, outputY;
	DataGroup rpcParamSet;

	public RPC_Process2(){
		
	}
	
	/**
	 * Initializes the process
	 * Gets handles to input/output components
	 */
	public void init() throws ProcessException
	{
		try
        {
            //I/O mappings
    		inputX = (DataValue) inputData.getComponent("target_location").getComponent("x");
    		inputY = (DataValue) inputData.getComponent("target_location").getComponent("y");
    		inputZ = (DataValue) inputData.getComponent("target_location").getComponent("z");
    		outputX = (DataValue) outputData.getComponent("image_location").getComponent("x");
    		outputY = (DataValue) outputData.getComponent("image_location").getComponent("y");
    		
    		//Parameter mappings
    		DataArray rpcParams = (DataArray) paramData.getComponent("rpc_parameter_series");
    		//System.out.println(rpcParams.getComponentCount());
    		rpcParamSet = (DataGroup) rpcParams.getComponent("rpc_parameter_set"); 
    		
       }
        catch (Exception e)
        {
            throw new ProcessException(ioError, e);
        }
	}
	
	/**
	 * Executes process algorithm on inputs and set output data
	 */
	public void execute() throws ProcessException
	{	
				
		double posX = inputX.getData().getDoubleValue();
		double posY = inputY.getData().getDoubleValue();
		double posZ = inputZ.getData().getDoubleValue();
		
		double xMin = rpcParamSet.getData().getDoubleValue(0);
		double yMin = rpcParamSet.getData().getDoubleValue(1);
		double xMax = rpcParamSet.getData().getDoubleValue(2);
		double yMax = rpcParamSet.getData().getDoubleValue(3);

		double so = rpcParamSet.getData().getDoubleValue(4);
		double ss = rpcParamSet.getData().getDoubleValue(5);
		double lo = rpcParamSet.getData().getDoubleValue(6);
		double ls = rpcParamSet.getData().getDoubleValue(7);
		
		double xo = rpcParamSet.getData().getDoubleValue(8);
		double xs = rpcParamSet.getData().getDoubleValue(9);
		double yo = rpcParamSet.getData().getDoubleValue(10);
		double ys = rpcParamSet.getData().getDoubleValue(11);
		double zo = rpcParamSet.getData().getDoubleValue(12);
		double zs = rpcParamSet.getData().getDoubleValue(13);
		
		// get all coefficient values
		double x_num_coef[] = new double[20];
		double x_den_coef[] = new double[20];
		double y_num_coef[] = new double[20];
		double y_den_coef[] = new double[20];
		
		for (int i=0; i<20; i++){
			x_num_coef[i] = rpcParamSet.getData().getDoubleValue(14+i);
			x_den_coef[i] = rpcParamSet.getData().getDoubleValue(34+i);
			y_num_coef[i] = rpcParamSet.getData().getDoubleValue(54+i);
			y_den_coef[i] = rpcParamSet.getData().getDoubleValue(74+i);
		}

		double errorBias = rpcParamSet.getData().getDoubleValue(94);
		double errorRandom = rpcParamSet.getData().getDoubleValue(95);
		
		//Scaling the object space coordinates to a range of ± 1	
		double scaleX = (posX-xo)/xs;
		double scaleY = (posY-yo)/ys;
		double scaleZ = (posZ-zo)/zs;
		
		//Ratio of two cubic functions calculated for sample and line
		double u_num=0;
			u_num+=x_num_coef[0];
			u_num+=x_num_coef[1]*scaleX;
			u_num+=x_num_coef[2]*scaleY;
			u_num+=x_num_coef[3]*scaleZ;
			u_num+=x_num_coef[4]*scaleX*scaleY;
			u_num+=x_num_coef[5]*scaleX*scaleZ;
			u_num+=x_num_coef[6]*scaleY*scaleZ;
			u_num+=x_num_coef[7]*scaleX*scaleX;
			u_num+=x_num_coef[8]*scaleY*scaleY;
			u_num+=x_num_coef[9]*scaleZ*scaleZ;
			u_num+=x_num_coef[10]*scaleX*scaleY*scaleZ;
			u_num+=x_num_coef[11]*scaleX*scaleX*scaleX;
			u_num+=x_num_coef[12]*scaleX*scaleY*scaleY;
			u_num+=x_num_coef[13]*scaleX*scaleZ*scaleZ;
			u_num+=x_num_coef[14]*scaleX*scaleX*scaleY;
			u_num+=x_num_coef[15]*scaleY*scaleY*scaleY;
			u_num+=x_num_coef[16]*scaleY*scaleZ*scaleZ;
			u_num+=x_num_coef[17]*scaleX*scaleX*scaleZ;
			u_num+=x_num_coef[18]*scaleY*scaleY*scaleZ;
			u_num+=x_num_coef[19]*scaleZ*scaleZ*scaleZ;	
		double u_den=0;
			u_den+=x_den_coef[0];
			u_den+=x_den_coef[1]*scaleX;
			u_den+=x_den_coef[2]*scaleY;
			u_den+=x_den_coef[3]*scaleZ;
			u_den+=x_den_coef[4]*scaleX*scaleY;
			u_den+=x_den_coef[5]*scaleX*scaleZ;
			u_den+=x_den_coef[6]*scaleY*scaleZ;
			u_den+=x_den_coef[7]*scaleX*scaleX;
			u_den+=x_den_coef[8]*scaleY*scaleY;
			u_den+=x_den_coef[9]*scaleZ*scaleZ;
			u_den+=x_den_coef[10]*scaleX*scaleY*scaleZ;
			u_den+=x_den_coef[11]*scaleX*scaleX*scaleX;
			u_den+=x_den_coef[12]*scaleX*scaleY*scaleY;
		 	u_den+=x_den_coef[13]*scaleX*scaleZ*scaleZ;
			u_den+=x_den_coef[14]*scaleX*scaleX*scaleY;
			u_den+=x_den_coef[15]*scaleY*scaleY*scaleY;
			u_den+=x_den_coef[16]*scaleY*scaleZ*scaleZ;
			u_den+=x_den_coef[17]*scaleX*scaleX*scaleZ;
			u_den+=x_den_coef[18]*scaleY*scaleY*scaleZ;
			u_den+=x_den_coef[19]*scaleZ*scaleZ*scaleZ;
		
        double v_num=0;
			v_num+=y_num_coef[0];
			v_num+=y_num_coef[1]*scaleX;
			v_num+=y_num_coef[2]*scaleY;
			v_num+=y_num_coef[3]*scaleZ;
			v_num+=y_num_coef[4]*scaleX*scaleY;
			v_num+=y_num_coef[5]*scaleX*scaleZ;
			v_num+=y_num_coef[6]*scaleY*scaleZ;
			v_num+=y_num_coef[7]*scaleX*scaleX;
			v_num+=y_num_coef[8]*scaleY*scaleY;
			v_num+=y_num_coef[9]*scaleZ*scaleZ;
			v_num+=y_num_coef[10]*scaleX*scaleY*scaleZ;
			v_num+=y_num_coef[11]*scaleX*scaleX*scaleX;
			v_num+=y_num_coef[12]*scaleX*scaleY*scaleY;
			v_num+=y_num_coef[13]*scaleX*scaleZ*scaleZ;
			v_num+=y_num_coef[14]*scaleX*scaleX*scaleY;
			v_num+=y_num_coef[15]*scaleY*scaleY*scaleY;
			v_num+=y_num_coef[16]*scaleY*scaleZ*scaleZ;
			v_num+=y_num_coef[17]*scaleX*scaleX*scaleZ;
			v_num+=y_num_coef[18]*scaleY*scaleY*scaleZ;
			v_num+=y_num_coef[19]*scaleZ*scaleZ*scaleZ;	
		double v_den=0;
			v_den+=y_den_coef[0];
			v_den+=y_den_coef[1]*scaleX;
			v_den+=y_den_coef[2]*scaleY;
			v_den+=y_den_coef[3]*scaleZ;
			v_den+=y_den_coef[4]*scaleX*scaleY;
			v_den+=y_den_coef[5]*scaleX*scaleZ;
			v_den+=y_den_coef[6]*scaleY*scaleZ;
			v_den+=y_den_coef[7]*scaleX*scaleX;
			v_den+=y_den_coef[8]*scaleY*scaleY;
			v_den+=y_den_coef[9]*scaleZ*scaleZ;
			v_den+=y_den_coef[10]*scaleX*scaleY*scaleZ;
			v_den+=y_den_coef[11]*scaleX*scaleX*scaleX;
			v_den+=y_den_coef[12]*scaleX*scaleY*scaleY;
			v_den+=y_den_coef[13]*scaleX*scaleZ*scaleZ;
			v_den+=y_den_coef[14]*scaleX*scaleX*scaleY;
			v_den+=y_den_coef[15]*scaleY*scaleY*scaleY;
			v_den+=y_den_coef[16]*scaleY*scaleZ*scaleZ;
			v_den+=y_den_coef[17]*scaleX*scaleX*scaleZ;
			v_den+=y_den_coef[18]*scaleY*scaleY*scaleZ;
			v_den+=y_den_coef[19]*scaleZ*scaleZ*scaleZ;
		
		double u = u_num / u_den;
		double v = v_num / v_den;
			
		//Denormalize the result to sample and line
		double S = u*ss+so;
		double L = v*ls+lo;

			
		//Set Output Data
		outputX.getData().setDoubleValue(S);
		outputY.getData().setDoubleValue(L);
	}

	public double getOutputY() {
		return outputY.getData().getDoubleValue();
	}

	public double getOutputX() {
		return outputX.getData().getDoubleValue();
	}
}
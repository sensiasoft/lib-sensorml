/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are subject to the Mozilla Public License Version
 1.1 (the "License"); you may not use this file except in compliance with
 the License. You may obtain a copy of the License at
 http://www.mozilla.org/MPL/MPL-1.1.html

 Software distributed under the License is distributed on an "AS IS" basis,
 WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 for the specific language governing rights and limitations under the License.

 The Original Code is the "SensorML DataProcessing Engine".

 The Initial Developer of the Original Code is the VAST team at the
 University of Alabama in Huntsville (UAH). <http://vast.uah.edu>
 Portions created by the Initial Developer are Copyright (C) 2007
 the Initial Developer. All Rights Reserved.
 Please Contact Mike Botts <mike.botts@uah.edu> for more information.

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
 * @date January 27, 2008
 * updated to account for coefs in any order! -> using coef names
 * 
 * * ---------
 * @author Alex Robin
 * @date May 11, 2006
 * corrected, cleaned up, and validated
 * TODO add support for series of coefs for multiple image regions
 */
public class RPC_Process extends DataProcess
{
	class RPCCoef
	{
		int xPow, yPow, zPow;
		double value;


		public RPCCoef(String name, double value)
		{
			this.value = value;

			for (int c = 0; c < name.length(); c++)
			{
				switch (name.charAt(c))
				{
					case 'x':
					case 'X':
						xPow++;
						break;

					case 'y':
					case 'Y':
						yPow++;
						break;

					case 'z':
					case 'Z':
						zPow++;
						break;
				}
			}
		}


		public double eval(double x, double y, double z)
		{
			double product = 1;
			
			for (int pow = 0; pow < xPow; pow++)
				product *= x;

			for (int pow = 0; pow < yPow; pow++)
				product *= y;

			for (int pow = 0; pow < zPow; pow++)
				product *= z;

			return value * product;
		}
	}

	DataValue inputX, inputY, inputZ;
	DataValue outputX, outputY;
	DataValue minX, minY, maxX, maxY; //  NOT CURRENTLY USED
	DataValue img_xo, img_xs, img_yo, img_ys;
	DataValue tar_xo, tar_xs, tar_yo, tar_ys, tar_zo, tar_zs;
	DataValue errorBias, errorRandom;

	RPCCoef[] x_num_coefs, x_den_coefs, y_num_coefs, y_den_coefs;


	public RPC_Process()
	{
	}


	public void setInputLatLonAlt(double lat, double lon, double alt)
	{
		inputX.getData().setDoubleValue(lon);
		inputY.getData().setDoubleValue(lat);
		inputZ.getData().setDoubleValue(alt);
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
			DataGroup rpcParamSet = (DataGroup) rpcParams.getComponent(0);

			// read image region
			DataGroup rpcImageRegion = (DataGroup) rpcParamSet.getComponent("image_region");
			minX = (DataValue) rpcImageRegion.getComponent("zone_minX");
			minY = (DataValue) rpcImageRegion.getComponent("zone_minY");
			maxX = (DataValue) rpcImageRegion.getComponent("zone_maxX");
			maxY = (DataValue) rpcImageRegion.getComponent("zone_maxY");

			// read image adjustments coefs
			DataGroup rpcImageAdj = (DataGroup) rpcParamSet.getComponent("image_adjustment");
			img_xo = (DataValue) rpcImageAdj.getComponent("image_x_offset");
			img_xs = (DataValue) rpcImageAdj.getComponent("image_x_scale");
			img_yo = (DataValue) rpcImageAdj.getComponent("image_y_offset");
			img_ys = (DataValue) rpcImageAdj.getComponent("image_y_scale");

			// read target adjustments coefs
			DataGroup rpcTargetAdj = (DataGroup) rpcParamSet.getComponent("target_adjustment");
			tar_xo = (DataValue) rpcTargetAdj.getComponent("target_x_offset");
			tar_xs = (DataValue) rpcTargetAdj.getComponent("target_x_scale");
			tar_yo = (DataValue) rpcTargetAdj.getComponent("target_y_offset");
			tar_ys = (DataValue) rpcTargetAdj.getComponent("target_y_scale");
			tar_zo = (DataValue) rpcTargetAdj.getComponent("target_z_offset");
			tar_zs = (DataValue) rpcTargetAdj.getComponent("target_z_scale");

			// read all coefs
			DataGroup rpcCoefsXNum = (DataGroup) rpcParamSet.getComponent("x_numerator_coefficients");
			x_num_coefs = new RPCCoef[rpcCoefsXNum.getComponentCount()];
			readCoefs(rpcCoefsXNum, x_num_coefs);

			DataGroup rpcCoefsXDen = (DataGroup) rpcParamSet.getComponent("x_denominator_coefficients");
			x_den_coefs = new RPCCoef[rpcCoefsXDen.getComponentCount()];
			readCoefs(rpcCoefsXDen, x_den_coefs);

			DataGroup rpcCoefsYNum = (DataGroup) rpcParamSet.getComponent("y_numerator_coefficients");
			y_num_coefs = new RPCCoef[rpcCoefsYNum.getComponentCount()];
			readCoefs(rpcCoefsYNum, y_num_coefs);

			DataGroup rpcCoefsYDen = (DataGroup) rpcParamSet.getComponent("y_denominator_coefficients");
			y_den_coefs = new RPCCoef[rpcCoefsYDen.getComponentCount()];
			readCoefs(rpcCoefsYDen, y_den_coefs);

			// read errors
			DataGroup rpcErrorParams = (DataGroup) rpcParamSet.getComponent("error_parameters");
			errorBias = (DataValue) rpcErrorParams.getComponent("error_bias");
			errorRandom = (DataValue) rpcErrorParams.getComponent("error_random");
		}
		catch (Exception e)
		{
			throw new ProcessException(ioError, e);
		}
	}


	protected void readCoefs(DataGroup coefGroup, RPCCoef[] coefArray)
	{
		for (int c = 0; c < coefArray.length; c++)
		{
			DataValue comp = (DataValue) coefGroup.getComponent(c);
			coefArray[c] = new RPCCoef(comp.getName(), comp.getData().getDoubleValue());
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

		double so = img_xo.getData().getDoubleValue();
		double ss = img_xs.getData().getDoubleValue();
		double lo = img_yo.getData().getDoubleValue();
		double ls = img_ys.getData().getDoubleValue();

		double xo = tar_xo.getData().getDoubleValue();
		double xs = tar_xs.getData().getDoubleValue();
		double yo = tar_yo.getData().getDoubleValue();
		double ys = tar_ys.getData().getDoubleValue();
		double zo = tar_zo.getData().getDoubleValue();
		double zs = tar_zs.getData().getDoubleValue();

		// Scaling the object space coordinates to a range of ± 1	
		double scaleX = (posX - xo) / xs;
		double scaleY = (posY - yo) / ys;
		double scaleZ = (posZ - zo) / zs;

		//Ratio of two cubic functions calculated for sample and line
		double sample_num = 0;
		for (int c = 0; c < x_num_coefs.length; c++)
			sample_num += x_num_coefs[c].eval(scaleX, scaleY, scaleZ);

		double sample_den = 0;
		for (int c = 0; c < x_den_coefs.length; c++)
			sample_den += x_den_coefs[c].eval(scaleX, scaleY, scaleZ);

		double line_num = 0;
		for (int c = 0; c < y_num_coefs.length; c++)
			line_num += y_num_coefs[c].eval(scaleX, scaleY, scaleZ);

		double line_den = 0;
		for (int c = 0; c < y_den_coefs.length; c++)
			line_den += y_den_coefs[c].eval(scaleX, scaleY, scaleZ);

		double sample = sample_num / sample_den;
		double line = line_num / line_den;

		//Denormalize the result to sample and line
		double S = sample * ss + so;
		double L = line * ls + lo;

		//Set Output Data
		outputX.getData().setDoubleValue(S);
		outputY.getData().setDoubleValue(L);
	}


	public double getOutputY()
	{
		return outputY.getData().getDoubleValue();
	}


	public double getOutputX()
	{
		return outputX.getData().getDoubleValue();
	}
}
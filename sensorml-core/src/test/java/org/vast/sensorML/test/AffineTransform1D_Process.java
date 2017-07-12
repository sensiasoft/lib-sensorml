/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are subject to the Mozilla Public License Version
 1.1 (the "License"); you may not use this file except in compliance with
 the License. You may obtain a copy of the License at
 http://www.mozilla.org/MPL/MPL-1.1.html
 
 Software distributed under the License is distributed on an "AS IS" basis,
 WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 for the specific language governing rights and limitations under the License.
 
 The Original Code is the "SensorML AbstractProcessing Engine".
 
 The Initial Developer of the Original Code is the VAST team at the University of Alabama in Huntsville (UAH). <http://vast.uah.edu> Portions created by the Initial Developer are Copyright (C) 2007 the Initial Developer. All Rights Reserved. Please Contact Mike Botts <mike.botts@uah.edu> for more information.
 
 Contributor(s): 
    Alexandre Robin <robin@nsstc.uah.edu>
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.sensorML.test;

import org.vast.process.ExecutableProcessImpl;
import org.vast.process.ProcessException;
import org.vast.process.ProcessInfo;
import org.vast.swe.SWEConstants;
import org.vast.swe.SWEHelper;
import net.opengis.swe.v20.Quantity;


/**
 * <p>
 * 1D affine transform y=ax+b
 * </p>
 *
 * @author Alexandre Robin
 * @date Jan 20, 2006
 */
public class AffineTransform1D_Process extends ExecutableProcessImpl
{
    public static final ProcessInfo INFO = new ProcessInfo("http://sensors.ws/process/affineTransform1D", "Affine Transform", null, AffineTransform1D_Process.class);
    Quantity xIn;
    Quantity yOut;
    Quantity aParam, bParam;


    public AffineTransform1D_Process()
    {
        super(INFO);        
        SWEHelper sweHelper = new SWEHelper();
        
        // input
        xIn = sweHelper.newQuantity(SWEConstants.DEF_DN, "Independent Variable", null, SWEConstants.UOM_ANY);
        inputData.add("x", xIn);
        
        // parameters
        aParam = sweHelper.newQuantity(SWEHelper.getPropertyUri("LinearSlope"), "Slope", null, SWEConstants.UOM_ANY);        
        paramData.add("slope", aParam);
        bParam = sweHelper.newQuantity(SWEHelper.getPropertyUri("LinearAxisIntercept"), "Intercept", null, SWEConstants.UOM_ANY);        
        paramData.add("intercept", bParam);
        
        // output
        yOut = sweHelper.newQuantity(SWEConstants.DEF_DN, "Dependent Variable", null, SWEConstants.UOM_ANY);
        outputData.add("y", yOut);
    }


    /**
     * Executes process algorithm on inputs and set output data
     */
    @Override
    public void execute() throws ProcessException
    {
        double x = xIn.getData().getDoubleValue();
        double a = aParam.getData().getDoubleValue();
        double b = bParam.getData().getDoubleValue();
        
        double y = a*x + b;
        getLogger().debug("Result = " + y);

        yOut.getData().setDoubleValue(y);
    }
}
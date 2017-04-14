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

import org.vast.data.*;
import org.vast.process.*;
import org.vast.sensorML.ExecutableProcessImpl;


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
    DataValue xIn;
    DataValue yOut;
    DataValue aParam, bParam;


    public AffineTransform1D_Process()
    {
    }


    /**
     * Initializes the process
     * Gets handles to input/output components
     */
    @Override
    public void init() throws SMLException
    {
        try
        {
            xIn = (DataValue) inputData.getComponent("x");
            yOut = (DataValue) outputData.getComponent("y");
            aParam = (DataValue) paramData.getComponent("slope");
            bParam = (DataValue) paramData.getComponent("intercept");
        }
        catch (Exception e)
        {
            throw new SMLException(IO_ERROR_MSG, e);
        }
    }


    /**
     * Executes process algorithm on inputs and set output data
     */
    @Override
    public void execute() throws SMLException
    {
        double x = xIn.getData().getDoubleValue();
        double a = aParam.getData().getDoubleValue();
        double b = bParam.getData().getDoubleValue();
        
        double y = a*x + b;

        yOut.getData().setDoubleValue(y);
    }
}
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

import org.vast.data.*;
import org.vast.process.*;


/**
 * <p><b>Title:</b><br/>
 * Time Transform Process
 * </p>
 *
 * <p><b>Description:</b><br/>
 * TODO TimeTransformProcess type description
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Alexandre Robin
 * @date Sep 2, 2005
 * @version 1.0
 */
public class TimeTransform_Process extends DataProcess
{
	AbstractDataComponent refTime, localTime, resultTime;
    
    
    public TimeTransform_Process()
    {    	
    }

    
    public void init() throws ProcessException
    {   	
    	try
        {
            refTime = inputData.getComponent("referenceTime");
            localTime = inputData.getComponent("localTime");
            resultTime = outputData.getComponent("time");
        }
        catch (Exception e)
        {
            throw new ProcessException(ioError, e);
        }
    }
    

    public void execute() throws ProcessException
    {
        // compute transformed time
        double time = refTime.getData().getDoubleValue() + localTime.getData().getDoubleValue();
        
        resultTime.getData().setDoubleValue(time);
        
        //System.out.println("refTime = " + refTime.getData().getDoubleValue() + ", localTime = " + localTime.getData().getDoubleValue());
    }    
}
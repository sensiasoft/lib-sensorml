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
import org.vast.process.*;


/**
 * <p><b>Title:</b><br/>
 * Binary Operation Process
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Implementation of a binary operation with respect to a parameter (addition, 
 * soustraction, multiplication, division, power)
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Alexandre Robin & Gregoire Berthiau
 * @date Mar 7, 2007
 * @version 1.0
 */
public class BinaryOperation_Process extends DataProcess
{
	AbstractDataComponent operatorData;
    private DataValue operand1, operand2, result;
    private OperatorEnum operator;
    enum OperatorEnum {ADD, MIN, MUL, DIV, POW}
    
    
    public BinaryOperation_Process()
    {
    	
    }

    
    public void init() throws ProcessException
    {
    	try
        {
            //I/O/P mappings
            operand1 = (DataValue) inputData.getComponent("operand1");
            operand2 = (DataValue) inputData.getComponent("operand2");

            result = (DataValue) outputData.getComponent("result");
            
            // read operator method
            DataValue operatorData = (DataValue)paramData.getComponent("operator");
 
            if (operatorData.getData().getStringValue().equalsIgnoreCase("addition"))
              	operator = OperatorEnum.ADD;
            // meb: seems some French sneaked in here
            else if (operatorData.getData().getStringValue().equalsIgnoreCase("soustraction"))
               	operator = OperatorEnum.MIN;
            // meb - 2010-01-27: added subtraction
            else if (operatorData.getData().getStringValue().equalsIgnoreCase("subtraction"))
               	operator = OperatorEnum.MIN;
            else if (operatorData.getData().getStringValue().equalsIgnoreCase("multiplication"))
               	operator = OperatorEnum.MUL;
            else if (operatorData.getData().getStringValue().equalsIgnoreCase("division"))
              	operator = OperatorEnum.DIV;
            else if (operatorData.getData().getStringValue().equalsIgnoreCase("power"))
               	operator = OperatorEnum.POW;
            
        }
        catch (RuntimeException e)
        {
            throw new ProcessException(ioError);
        }
    }
    

    public void execute() throws ProcessException
    {
        double N1 = 0.0;
        double N2 = 0.0;
        double Nr = 0.0;
        
        if (operand1 != null)
            N1 = operand1.getData().getDoubleValue();

        if (operand2 != null)
            N2 = operand2.getData().getDoubleValue();
        
        switch (operator)
        {
            case ADD:
                Nr = N1 + N2;
                break;
                
            case MIN:
                Nr = N1 - N2;
                break;
                
            case MUL:
                Nr = N1 * N2;
                break;
                
            case DIV:
                Nr = N1 / N2;
                break;
                
            case POW:
                Nr = Math.pow(N1, N2);
                break;
        }

        //System.out.println(operator + " = " + Nr);
        result.getData().setDoubleValue(Nr);
    } 
}
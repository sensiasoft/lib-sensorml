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
import com.bestcode.mathparser.IMathParser;
import com.bestcode.mathparser.MathParserFactory;

/**
 * <p><b>Title:</b><br/>
 * Equation Solver Process
 * </p>
 *
 * <p><b>Description:</b><br/>
 * This process solves equation inputed as a string. It handles many
 * mathematical functions.
 * It uses the jbcParser 3.3 library.
 * X and Y shall not be used as variable name.
 * For clarity, and parsing-conflict avoidance, the variables should
 * be named V1, V2, ...  
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Gregoire Berthiau
 * @date Jan 16, 2008
 * @version 1.0
 */
public class EquationSolver_Process extends DataProcess
{
	int numberOfInputs;
	double result;
	String equation;
	Error e;
	DataValue[] inputsData;
	AbstractDataComponent equationData, resultData;
	IMathParser parser;
	
    @Override
    public void init() throws ProcessException
    {
        try
        {
        	equationData = paramData.getComponent("equation");
        	resultData = outputData.getComponent("result");
        	numberOfInputs = inputData.getComponentCount();
        	
        	inputsData = new DataValue[numberOfInputs];        	
        	for(int i=0; i<numberOfInputs; i++){
        		inputsData[i] = (DataValue)inputData.getComponent(i);
        	}
        	
        	// init equation solver
        	equation = equationData.getData().getStringValue();
            if(equation.contains("=")){
                int equalSignPosition = equation.indexOf("=");
                equation = equation.substring(equalSignPosition+1);
            }
            
        	parser = MathParserFactory.create();
            parser.setExpression(equation);
        }
        catch (Exception e)
        {
            throw new ProcessException(ioError, e);
        }
    }
    
    @Override
    public void execute() throws ProcessException
    {
    	// get result from math expression
		try {
			for(int i = 0; i<numberOfInputs; i++){
				parser.setVariable(inputsData[i].getName(),inputsData[i].getData().getDoubleValue());
			}
			result = parser.getValue();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		if(String.valueOf(result).contains("infinity")){
			throw new ProcessException("The result is infinity, you must have divided by 0.", e);
		}
		
		resultData.getData().setDoubleValue(result);		
    }
}
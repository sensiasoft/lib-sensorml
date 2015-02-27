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

import java.util.*;
import org.vast.cdm.common.DataBlock;
import org.vast.data.*;
import org.vast.math.*;
import org.vast.process.*;
import org.vast.unit.UnitConverter;


/**
 * <p><b>Title:</b><br/>
 * Generic Positioning Process
 * </p>
 *
 * <p><b>Description:</b><br/>
 * 
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Alexandre Robin
 * @date Sep 2, 2005
 * @version 1.0
 */
public class GenericPositionProcess extends DataProcess
{
    AbstractDataComponent indexData;
    AbstractDataComponent outputTime, outputMatrix;
    char[] rotationOrder = {'X','Y','Z'};
    
    class AxisCurve
    {
    	public LookUpTable1D_Process process = new LookUpTable1D_Process();
    	public UnitConverter converter;
    	public int outputIndex = 0;
    }
    AxisCurve timeCurve, txCurve, tyCurve, tzCurve, rxCurve, ryCurve, rzCurve;
    DataProcess[] lookUpTables;
    
    
    public GenericPositionProcess()
    {    	
    }

    
    public void init() throws ProcessException
    {
        try
        {
            // input mappings
            indexData = inputData.getComponent(0);
            
            // output mappings
            outputTime = outputData.getComponent("time");
            if (outputTime != null)
            	outputTime.assignNewDataBlock();
            
            outputMatrix = outputData.getComponent("position");
            if (outputMatrix != null)
            	outputMatrix.assignNewDataBlock();
            
            // params mappings
            int paramCount = paramData.getComponentCount();
            ArrayList<DataProcess> processList = new ArrayList<DataProcess>(6);
            for (int i=0; i<paramCount; i++)
            {
            	AbstractDataComponent param = paramData.getComponent(i);
            	if (param.getName().equals("order"))
            	{
            		// read rotation order
                    String rotOrder = param.getData().getStringValue();
                    if (rotOrder != null && rotOrder.length() == 3)
                    	rotationOrder = rotOrder.toCharArray();// TODO parse rotation order
            	}
            	else
            	{
            		LookUpTable1D_Process lookUpProcess = new LookUpTable1D_Process();
            		lookUpProcess.addParameter("table", param);
            		lookUpProcess.setName(param.getName());
            		lookUpProcess.init();
            		processList.add(lookUpProcess);
            		
            		int outputCount = lookUpProcess.getOutputList().getComponentCount();
            		for (int j=0; j<outputCount; j++)
            		{
            			// read axis code
            			DataValue output = (DataValue)lookUpProcess.getOutputList().getComponent(j);
            			String axisCode = (String)output.getProperty("axisCode");
            			String def = (String)output.getProperty("definition");
            			//String uom = (String)output.getProperty("unit");
            			AxisCurve axis = createAxis(def, axisCode);
            			axis.process = lookUpProcess;
            			axis.outputIndex = j;
            			//axis.converter = UnitConversion.createConverterToSI(uom);
            		}
            	}
            }
            
            lookUpTables = new DataProcess[processList.size()];
            lookUpTables = processList.toArray(lookUpTables);
        }
        catch (Exception e)
        {
            throw new ProcessException(ioError, e);
        }
    }
    
    
    /**
     * Create axis data structure and set it up depending
     * on definition urn and axisCode
     * @param def
     * @param axisCode
     * @return
     */
    protected AxisCurve createAxis(String def, String axisCode)
    {
    	AxisCurve axis = new AxisCurve();
    	
    	if (def.contains("distance"))
    	{
	    	if (axisCode.equals("X"))
	    	{
	    		txCurve = axis;
	    	}
	    	else if (axisCode.equals("Y"))
	    	{
	    		tyCurve = axis;
	    	}
	    	else if (axisCode.equals("Z"))
	    	{
	    		tzCurve = axis;
	    	}
    	}
    	else if (def.contains("angle"))
    	{
    		if (axisCode.equals("X"))
	    	{
	    		rxCurve = axis;
	    	}
	    	else if (axisCode.equals("Y"))
	    	{
	    		ryCurve = axis;
	    	}
	    	else if (axisCode.equals("Z"))
	    	{
	    		rzCurve = axis;
	    	}
    	}
    	else if (def.contains("time"))
    	{
    		timeCurve = axis;
    	}
    	
    	return axis;
    }
    

    public void execute() throws ProcessException
    {
    	DataBlock data;
    	DataBlock index = indexData.getData();

    	// execute all look up table processes
    	for (int i=0; i<lookUpTables.length; i++)
    	{
    		DataProcess process = lookUpTables[i];
    		process.getInputList().getComponent(0).setData(index);
    		process.execute();
    	}
       
        // set output matrix values
        if (outputMatrix != null)
        {
        	Matrix4d outMatrix = computeMatrix();
        	data = outputMatrix.getData();
        	for (int i=0; i<16; i++)
            	data.setDoubleValue(i, outMatrix.getElement(i/4, i%4));
        	//System.out.println(outMatrix.getElement(0,3) + "," + outMatrix.getElement(1,3) + "," + outMatrix.getElement(2,3));
        }
                
        // set time matrix values
        if (outputTime != null)
        {
	        double time = computeTime();
        	data = outputTime.getData();
	        outputTime.getData().setDoubleValue(time);
	        //System.out.println("index = " + index.getDoubleValue() + ", time = " + time);
        }
    }
    
    
    /**
     * Computes time using time look up table output
     * @return
     */
    protected double computeTime()
    {
    	double time = 0.0;
    	
    	if (timeCurve != null)
    	{
			time = timeCurve.process.getOutputList().getComponent(timeCurve.outputIndex).getData().getDoubleValue();
			time = timeCurve.converter.convert(time);
    	}
    	
    	return time;
    }
    
    
    /**
     * Computes 4x4 matrix using look up table outputs
     * @return
     */
    protected Matrix4d computeMatrix()
	{
    	double tx = 0.0;
    	double ty = 0.0;
    	double tz = 0.0;
    	double rx = 0.0;
    	double ry = 0.0;
    	double rz = 0.0;
    	
    	Matrix4d newMatrix = new Matrix4d();
    	
		// get interpolated values from look up tables
		if (txCurve != null)
		{
			tx = txCurve.process.getOutputList().getComponent(txCurve.outputIndex).getData().getDoubleValue();
			tx = txCurve.converter.convert(tx);
		}
		if (tyCurve != null)
		{
			ty = tyCurve.process.getOutputList().getComponent(tyCurve.outputIndex).getData().getDoubleValue();
			ty = tyCurve.converter.convert(ty);
		}
		if (tzCurve != null)
		{
			tz = tzCurve.process.getOutputList().getComponent(tzCurve.outputIndex).getData().getDoubleValue();
			tz = tzCurve.converter.convert(tz);
		}
		if (rxCurve != null)
		{
			rx = rxCurve.process.getOutputList().getComponent(rxCurve.outputIndex).getData().getDoubleValue();
			rx = rxCurve.converter.convert(rx);
		}
		if (ryCurve != null)
		{
			ry = ryCurve.process.getOutputList().getComponent(ryCurve.outputIndex).getData().getDoubleValue();
			ry = ryCurve.converter.convert(ry);
		}
		if (rzCurve != null)
		{
			rz = rzCurve.process.getOutputList().getComponent(rzCurve.outputIndex).getData().getDoubleValue();
			rz = rzCurve.converter.convert(rz);
		}

		// set up rotation matrices
		Matrix4d xRotMatrix = new Matrix4d();
		Matrix4d yRotMatrix = new Matrix4d();
		Matrix4d zRotMatrix = new Matrix4d();
		xRotMatrix.rotX(rx);
		yRotMatrix.rotY(ry);
		zRotMatrix.rotZ(rz);

		// rotate in given order
		for (int i=0; i<3; i++)
		{
			char axis = rotationOrder[i];
			
			switch (axis)
			{
				case 'X':
					newMatrix.mul(xRotMatrix);
					break;
					
				case 'Y':
					newMatrix.mul(yRotMatrix);
					break;
					
				case 'Z':
					newMatrix.mul(zRotMatrix);
					break;
			}
		}

		// translate
		newMatrix.setTranslation(tx, ty, tz);
		
		return newMatrix;
	}
}
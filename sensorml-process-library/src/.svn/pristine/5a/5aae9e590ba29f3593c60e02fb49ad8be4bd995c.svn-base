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
import org.vast.util.DateTimeFormat;
import org.vast.cdm.common.*;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * <p><b>Title:</b><br/>
 * Printer Process
 * </p>
 *
 * <p><b>Description:</b><br/>
 * This process does not operates on the inputs, it just prints them to
 * a log.
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Gregoire Berthiau
 * @date November 10, 2007
 * @version 1.0
 */
public class Printer_Process extends DataProcess
{
	int InputCount, OutputCount, elementIndex = 0, run = 1, TimeZone = -6;
	AbstractDataComponent dataIn, dataOut;
    PrintWriter printLog = null;		
    String LogDate, LogDate2, filepath, indent = "  "; 
    String ioCorrespondanceError = "the inputs and outputs in the Printer_process must be identical";
    DataComponent [] InputComponents, OutputComponents;
    DataValue Time;
    
    public void init() throws ProcessException
    {
        try
        {
        	InputCount = inputData.getComponentCount();
        	OutputCount = outputData.getComponentCount();
        	
        	if(InputCount!=OutputCount){
        		throw new ProcessException(ioCorrespondanceError);
        	}
        	
        	InputComponents = new DataComponent[InputCount];
        	OutputComponents = new DataComponent[InputCount];
        	
        	for(int i=0; i<InputCount; i++){
        		InputComponents[i] = inputData.getComponent(i);
        		OutputComponents[i] = outputData.getComponent(i);
        	}
        	
        	filepath = paramData.getComponent("logFilePath").getData().getStringValue();       	
        	LogDate = DateTimeFormat.formatIso((double)System.currentTimeMillis()/1000.0, TimeZone);
        	String a = LogDate.substring(0,13);
    		String b = LogDate.substring(14,16);
    		String c = LogDate.substring(17,19);
    		String d = LogDate.substring(20,23); 		
    		LogDate2 = a + "h" + b + "m" + c + "s" + d;
    		
        	if(filepath.compareTo("default")==0){
        		
        		String logDirPath = "C:/SensorML components/Printer Process logs/";

                File logDir = new File(logDirPath);
                boolean DirExist = logDir.isDirectory();
                if(!DirExist){
                	logDir.mkdirs();
                } 
                
            	filepath = logDirPath + "PrinterLog_" + LogDate2 + ".log";
        	}
        	
        	System.out.println("Printer_Process Log Location:  " + filepath);
        	
        	File log = new File(filepath);
    		try {
    			printLog = new PrintWriter(log);
    		} catch (FileNotFoundException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        	
        	// TODO check if dataIn and dataOut have the same structure
        }
        catch (Exception e)
        {
            throw new ProcessException(ioError, e);
        }
    }

    public void execute() throws ProcessException
    {
    	for(int i=0; i<InputCount; i++){
    		outputData.getComponent(i).setData(inputData.getComponent(i).getData());
    	}
    	DataComponent a = InputComponents[0];
    	System.out.println(a.getComponentCount());
    	DataComponent b = InputComponents[0].getComponent(0);
    	System.out.println(b.getComponentCount());
    	System.out.println(b.getComponentCount());
    	DataComponent c = InputComponents[0].getComponent(0).getComponent(0);
    	System.out.println(c.getComponentCount());
    	
    	printHeader();
    	printLog.flush();
    	printData();
    	printLog.println(" ");
    	printLog.println(" ");
    	printLog.flush();
    	run++;   	
    	
    }
    
 /**   public boolean canRun()
    {
    	for (int i=0; i<inputConnections.size(); i++)
        {
            ConnectionList connectionList = inputConnections.get(i);

                // loop through all connections in each list
                for (int j=0; j<connectionList.size(); j++)
                {
                    DataConnection connection = connectionList.get(j);                    
                    if (connection.isDataAvailable() == true){
                        return true;
                    }     
                }
            }
    	return true;
    }   */
    
    protected void printHeader() {
    	printLog.println("LOG OF THE PRINTER_PROCESS OF THE PROCESS CHAIN EXECUTED AT " + LogDate);
    	printLog.println(" ");
    	printLog.println(" ");
    	printLog.println("DATA COMPONENTS:");
    	printLog.println(" ");
    	int a = 1;
    	
    	for (int i=0; i<inputConnections.size(); i++){
            ConnectionList IConnections = inputConnections.get(i);
            ConnectionList OConnections = outputConnections.get(i);

            for(int j=0; j<IConnections.size(); j++){
            	printLog.println("- component # " + a + ":");
            	printLog.println("	name : " + IConnections.get(j).getDestinationComponent().getName());
            	printLog.println("	type : " + IConnections.get(j).getDestinationComponent().getData().getDataType().toString());
            	printLog.println("	source process : " + IConnections.get(j).getSourceProcess().getName());
            	printLog.println("	destination process : " + OConnections.get(j).getDestinationProcess().getName()); 
            	printLog.println(" ");
            	a++;
            }
    	}			
	}
    
    protected void printData() {
    	printLog.println(" ");
    	printLog.println("DATA VALUE:");
    	printLog.println(" ");
    	printLog.println("- EXECUTION RUN # " + run + ":");
    	for (int i=0; i<InputCount; i++){
    		printLog.println(" ");
    		ComponentParser(InputComponents[i]);
    	}
    		
    	/**	int ComponentCount = InputComponents[i].getComponentCount();
    		
    		DataType Type = InputComponents[i].getData().getDataType();
    		int AtomCount = InputComponents[i].getData().getAtomCount();
    		DataComponent Component = InputComponents[i];
    		
    		if(AtomCount==1){
    			isAtomic(Component);
    		}
    		
    		if(AtomCount>1){
    			isMixed(Component);
    		}  */
    		
	}
    
    protected void ComponentParser(DataComponent Component) {
    	
    	int ComponentCount = Component.getComponentCount();
    	
    	if(ComponentCount==0){
    		isAtomic(Component);
    	}
    	
    	if(ComponentCount==1){
    		isArrayElement(Component);
    	}
    	
    	if(ComponentCount>1){
    		printLog.println("  - " + Component.getName() + " (multiple): ");
    		for(int j=0; j<ComponentCount; j++){
    			elementIndex = j + 1;
    			if(elementIndex%8==0){
    				printLog.println(" ");
    			}
    			ComponentParser(Component.getComponent(j));
    		}
    	}
    }

    protected void isArrayElement(DataComponent Component) {
    	
    	DataType Type = Component.getData().getDataType();
		
		if(Type == DataType.DOUBLE){
			printLog.print(indent + Component.getData().getDoubleValue() + "(" + elementIndex + ")  ");
		}
		
		if(Type == DataType.BOOLEAN){
			printLog.print(indent + Component.getData().getBooleanValue() + "(" + elementIndex + ")  ");
		}
		
		if(Type == DataType.FLOAT){
			printLog.print(indent + Component.getData().getFloatValue() + "(" + elementIndex + ")  ");
		}
		
		if((Type == DataType.ASCII_STRING)||(Type == DataType.UTF_STRING)){
			printLog.print(indent + Component.getData().getStringValue() + "(" + elementIndex + ")  ");
		}
		
		if((Type == DataType.BYTE)||(Type == DataType.UBYTE)){
			printLog.print(indent + Component.getData().getByteValue() + "(" + elementIndex + ")  ");
		}
		
		if((Type == DataType.INT)||(Type == DataType.UINT)){
			printLog.print(indent + Component.getData().getIntValue() + "(" + elementIndex + ")  ");
		}
		
		if((Type == DataType.LONG)||(Type == DataType.ULONG)){
			printLog.print(indent + Component.getData().getLongValue() + "(" + elementIndex + ")  ");
		}
		
		if((Type == DataType.SHORT)||(Type == DataType.USHORT)){
			printLog.print(indent + Component.getData().getShortValue() + "(" + elementIndex + ")  ");
		}
	}
    	
    
    protected void isAtomic(DataComponent Component) {
    	
    	DataType Type = Component.getData().getDataType();
		
		if(Type == DataType.DOUBLE){
			printLog.println("  " + Component.getName()+" (atomic): " + Component.getData().getDoubleValue());
		}
		
		if(Type == DataType.BOOLEAN){
			printLog.println("  " + Component.getName()+" (atomic): " + Component.getData().getBooleanValue());
		}
		
		if(Type == DataType.FLOAT){
			printLog.println("  " + Component.getName()+" (atomic): " + Component.getData().getFloatValue());
		}
		
		if((Type == DataType.ASCII_STRING)||(Type == DataType.UTF_STRING)){
			printLog.println("  " + Component.getName()+" (atomic): " + Component.getData().getStringValue());
		}
		
		if((Type == DataType.BYTE)||(Type == DataType.UBYTE)){
			printLog.println("  " + Component.getName()+" (atomic): " + Component.getData().getByteValue());
		}
		
		if((Type == DataType.INT)||(Type == DataType.UINT)){
			printLog.println("  " + Component.getName()+" (atomic): " + Component.getData().getIntValue());
		}
		
		if((Type == DataType.LONG)||(Type == DataType.ULONG)){
			printLog.println("  " + Component.getName()+" (atomic): " + Component.getData().getLongValue());
		}
		
		if((Type == DataType.SHORT)||(Type == DataType.USHORT)){
			printLog.println("  " + Component.getName()+" (atomic): " + Component.getData().getShortValue());
		}
	}

   
}
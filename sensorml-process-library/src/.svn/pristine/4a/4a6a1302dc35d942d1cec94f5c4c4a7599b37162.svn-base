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
    Alexandre Robin <robin@nsstc.uah.edu>
 
******************************* END LICENSE BLOCK ***************************/

package org.sensorML.process;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.LinkedList;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.vast.cdm.common.AsciiEncoding;
import org.vast.cdm.common.CDMException;
import org.vast.cdm.common.DataBlock;
import org.vast.cdm.common.DataComponent;
import org.vast.cdm.common.DataHandler;
import org.vast.cdm.common.DataStreamParser;
import org.vast.cdm.common.DataType;
import org.vast.data.*;
import org.vast.ows.sas.SASAlertReader;
import org.vast.process.*;
import org.vast.sweCommon.SweConstants;
import org.vast.unit.UnitConversion;
import org.vast.unit.UnitConverter;


public class XMPP_Process extends DataProcess implements DataHandler
{
	
    private DataGroup xmppParametersData;
    private XMPPConnection connection;
    private DataComponent input, timestampData, alertDataComponent, dataRecordData;
    private DataComponent endpointData, usernameData, serverPasswordData, roomPasswordData, conferenceData; 
    private String endpoint, username, roomPassword, serverPassword, conference;
    private String msgfilter ="<Alert", tokenSeparator, blockSeparator;
    private String alertData;
    private boolean firstRun = true;
    private MultiUserChat newmuc = null;
    private LinkedList<String> messageStack = new LinkedList<String>();
	private DataStreamParser dataParser;
	private SASAlertReader reader;
	private Message message;
	private Hashtable<DataComponent, UnitConverter> converters = new Hashtable<DataComponent, UnitConverter>();
	
    /**
     * Initializes the process
     * Gets handles to input/output components
     */
    public void init() throws ProcessException
    {    

    	try
        {    		
    		// inputs
    		input = inputData.getComponent(0);
    		
    		// XMPP parameters 
    		xmppParametersData = (DataGroup) paramData.getComponent("xmppParameters");
    		endpointData = xmppParametersData.getComponent("endpoint");  		
    		conferenceData = xmppParametersData.getComponent("chatroom");
    		usernameData = xmppParametersData.getComponent("login");   		
    		serverPasswordData = xmppParametersData.getComponent("serverPassword");
    		roomPasswordData = xmppParametersData.getComponent("roomPassword");
    		
    		// DataRecord parameters 
    		dataRecordData = (DataGroup) paramData.getComponent("dataRecordParameters");
    		tokenSeparator =  dataRecordData.getComponent("tokenSeparator").getData().getStringValue();  		
    		blockSeparator =  dataRecordData.getComponent("blockSeparator").getData().getStringValue();

    		// outputs 
    		timestampData = outputData.getComponent("timestamp");
    		alertDataComponent = (DataGroup) outputData.getComponent("alert");
    		
    		reader = new SASAlertReader();
    		reader.setDataComponents(alertDataComponent);
			AsciiEncoding dataEncoding = new AsciiEncoding(blockSeparator, tokenSeparator);
			reader.setDataEncoding(dataEncoding);
			dataParser = reader.getDataParser();
    		
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
    	
    	final DataHandler handler = this;
    	
    	if(firstRun)
    	{
    		try
            {
        		endpoint = endpointData.getData().getStringValue();
        		username = usernameData.getData().getStringValue();
        		conference = conferenceData.getData().getStringValue();
        		serverPassword = serverPasswordData.getData().getStringValue();
        		roomPassword = roomPasswordData.getData().getStringValue();
        		connection = new XMPPConnection(endpoint);
    			connection.connect();
    			connection.login(username, serverPassword);
    			newmuc = new MultiUserChat(connection,conference + "@conference." + endpoint);	
    			newmuc.join(username, roomPassword);
            }
    		catch (Exception e)
    	    {
    	       throw new ProcessException("the xmpp client cannot connect to the xmpp server properly", e);
    	    }
    		
    		PacketFilter packetFilter = new PacketFilter(){
    			public boolean accept(Packet p) {
    				if (p instanceof Message) {
    					Message m = (Message) p;
    					String from = m.getFrom();
    					int i = from.indexOf("/");
    					if (i > -1) {
    						from = from.substring(0,from.indexOf("/"));
    					}
					
    					if (m.getBody().startsWith(msgfilter) && from.equals(conference+"@conference."+ endpoint)) {
    						return true;
    					}
    				}
				
    				return false;
    			}
    		};
		
    		//add packet Listener
    		PacketListener packetListener = new PacketListener() {
    			public void processPacket(Packet p) {
    				if (p instanceof Message) {
    					Message m = (Message) p;
    					String data = m.getBody();
    					alertData = data;
    				}
    			}
    		};
		
    		connection.addPacketListener(packetListener, packetFilter);
    		alertData = null;
    		firstRun = false;
    		
    		message = new Message();
    		
    		try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
    		while(message != null)
    		{
    			message = newmuc.pollMessage();
    		}
    	}

   		message = newmuc.nextMessage();
    	messageStack.addLast(message.getBody());

    	ByteArrayInputStream in = new ByteArrayInputStream(messageStack.peekLast().getBytes());
    	parseAlert(in, handler);
    	noNewInputNeeded();
    	
     } 	

    private void parseAlert(InputStream in, DataHandler handler) throws ProcessException 
    {

    	try 
    	{	
			reader.parse(in);
			reader.getTimestamp();
			dataParser = reader.getDataParser();
			dataParser.setDataHandler(handler);
			dataParser.parse(reader.getDataStream());
						
		} 
    	catch (CDMException e) 
    	{
			throw new ProcessException(e.getMessage());
		}
    }

    
	protected void noNewInputNeeded()
    {
    	inputConnections.get(0).setNeeded(false);
        outputConnections.get(0).setNeeded(true);
    }


	public void beginDataAtom(DataComponent info) {
		// TODO Auto-generated method stub
		
	}


	public void endData(DataComponent info, DataBlock data) 
	{
		try 
		{
			timestampData.getData().setDoubleValue(reader.getTimestamp());
			alertDataComponent.setData(data);	
		} 
		catch (CDMException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }


	public void endDataAtom(DataComponent info, DataBlock data) {

		DataType dataType = data.getDataType();
        
        // TODO unit conversion WILL be handled by global chain unit converters
        if (dataType != DataType.UTF_STRING && dataType != DataType.ASCII_STRING)
        {
            UnitConverter converter = converters.get(info);
            
            if (converter == null)
            {
                String uom = (String)info.getProperty(SweConstants.UOM_CODE);
                converter = UnitConversion.createConverterToSI(uom);
                converters.put(info, converter);
            }
            
            // convert to SI
            double newVal = converter.convert(data.getDoubleValue());
            data.setDoubleValue(newVal);
        }
		
	}


	public void endDataBlock(DataComponent info, DataBlock data) {
		// TODO Auto-generated method stub
		
	}


	public void startData(DataComponent info) {
		// TODO Auto-generated method stub
		
	}


	public void startDataBlock(DataComponent info) {
		// TODO Auto-generated method stub
		
	}
    
}
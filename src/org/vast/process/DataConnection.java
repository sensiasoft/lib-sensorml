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

package org.vast.process;

import java.util.Hashtable;

import org.vast.cdm.common.DataBlock;
import org.vast.cdm.common.DataComponent;


/**
 * <p><b>Title:</b>
 * Data Connection
 * </p>
 *
 * <p><b>Description:</b><br/>
 *
 * </p>
 * 
 * <p>Copyright (c) 2005</p>
 * @author Alexandre Robin
 * @version 1.0
 */
public class DataConnection
{
    protected DataProcess sourceProcess;
    protected DataProcess destinationProcess;
    protected DataComponent sourceComponent;
    protected DataComponent destinationComponent;
    protected String name;
    protected boolean dataAvailable;
    protected Hashtable<String, Object> properties = null;
    
	
    /**
     * Makes sure source and destination datablocks are the same
     * This is used in synchronous mode
     */
    public void transferDataBlocks()
    {
        DataBlock srcBlock = sourceComponent.getData();
        DataBlock destBlock = destinationComponent.getData();
        if (destBlock != srcBlock)
            destinationComponent.setData(srcBlock);
    }
    
    
	public String getName()
	{
		return this.name;
	}

	
	public void setName(String name)
	{
		this.name = name;		
	}


	public DataComponent getDestinationComponent()
	{
		return destinationComponent;
	}


	public void setDestinationComponent(DataComponent destinationComponent)
	{
		this.destinationComponent = destinationComponent;
	}


	public DataProcess getDestinationProcess()
	{
		return destinationProcess;
	}


	public void setDestinationProcess(DataProcess destinationProcess)
	{
		this.destinationProcess = (DataProcess)destinationProcess;
	}


	public DataComponent getSourceComponent()
	{
		return sourceComponent;
	}


	public void setSourceComponent(DataComponent sourceComponent)
	{
		this.sourceComponent = sourceComponent;
	}


	public DataProcess getSourceProcess()
	{
		return sourceProcess;
	}


	public void setSourceProcess(DataProcess sourceProcess)
	{
		this.sourceProcess = (DataProcess)sourceProcess;
	}


    public boolean isDataAvailable()
    {
        return dataAvailable;
    }


    public void setDataAvailable(boolean dataAvailable)
    {
        this.dataAvailable = dataAvailable;
    }
    
    
    public Object getProperty(String propName)
    {
        if (properties == null)
            return null;
        else
            return properties.get(propName);
    }


    public void setProperty(String propName, Object propValue)
    {
        if (properties == null)
            properties = new Hashtable<String, Object>(1, 1.0f);
        
        properties.put(propName, propValue);
    }
}

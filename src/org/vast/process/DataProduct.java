/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are subject to the Mozilla Public License Version
 1.1 (the "License"); you may not use this file except in compliance with
 the License. You may obtain a copy of the License at
 http://www.mozilla.org/MPL/MPL-1.1.html
 
 Software distributed under the License is distributed on an "AS IS" basis,
 WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 for the specific language governing rights and limitations under the License.
 
 The Original Code is the "SensorML DataProcessing Engine".
 
 The Initial Developer of the Original Code is the
 University of Alabama in Huntsville (UAH).
 Portions created by the Initial Developer are Copyright (C) 2006
 the Initial Developer. All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin <robin@nsstc.uah.edu>
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.process;

import org.ogc.cdm.common.*;
import org.vast.data.*;


/**
 * <p><b>Title:</b><br/>
 * Data Product Block
 * </p>
 *
 * <p><b>Description:</b><br/>
 * TODO DataProduct type description
 * </p>
 *
 * <p>Copyright (c) 2005</p>
 * @author Alexandre Robin
 * @version 1.0
 */
public class DataProduct extends DataProcess
{
    protected int startIndex = 0;
	protected int stopIndex = 0;
	    

    public DataProduct()
    {
    	super();
    }
    
    
    public void init() throws ProcessException
    {
    	
    }
    
    
    public void execute() throws ProcessException
    {
    	// copy input blocks to corresponding storage array
    	
    	
    }
    
    
    public void addDataBlock(DataBlock dataBlock)
    {
    	
    }
    
    
    public void checkIOData() throws ProcessException
    {
    	
    }   
    
    
    public void addDataDesc(String name, AbstractDataComponent structure)
    {
        if (inputData.getComponentCount() == 1)
        	return;
    	
        DataList dataList = new DataList();
        dataList.addComponent(structure);
        
    	super.addInput(name, structure);
        super.addOutput(name, dataList);
    }
    
    
    public void addInput(String name, AbstractDataComponent inputStructure)
    {
    	this.addDataDesc(name, inputStructure);
    }
    
    
    public void addOutput(String name, AbstractDataComponent outputStructure)
    {
    	this.addDataDesc(name, outputStructure);
    }
    
    
    public void addParameter(String name, AbstractDataComponent paramStructure)
    {
    	this.addDataDesc(name, paramStructure);
    }
}

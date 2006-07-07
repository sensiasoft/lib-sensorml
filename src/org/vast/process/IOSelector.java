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


/**
 * <p><b>Title:</b>
 * IO Selector Implementation
 * </p>
 *
 * <p><b>Description:</b><br/>
 * TODO DataSelector type description
 * </p>
 *
 * <p>Copyright (c) 2005</p>
 * @author Alexandre Robin
 * @date Sep 27, 2005
 * @version 1.0
 */
public class IOSelector
{
	protected DataComponent component;
	protected String[] componentPath;
    protected int componentIndex;
	
	
	public IOSelector()
	{
	}
	
		
	public IOSelector(DataComponent parentComponent, String componentPath) throws ProcessException
	{
		this.componentPath = componentPath.split("/");
		this.component = findComponent(parentComponent, this.componentPath);		
	}
	
	
	public void setComponent(DataComponent dataComponent)
	{
		this.component = dataComponent;
	}


	public DataComponent getComponent()
	{
		return this.component;
	}


    public int getComponentIndex()
    {
        return componentIndex;
    }
	
	
	public DataComponent findComponent(DataComponent parent, String [] dataPath) throws ProcessException
    {
		DataComponent data = parent;
		
		for (int i=0; i<dataPath.length; i++)
        {
            if (i == 0)
                componentIndex = data.getComponentIndex(dataPath[i]);
            
            data = data.getComponent(dataPath[i]);
            
            if (data == null)
            	throw new ProcessException("Unknown component " + dataPath[i]);
        }
    	
    	return data;
    }
}

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

package org.vast.sensorML.system;

import org.ogc.cdm.common.DataComponent;


/**
 * <p><b>Title:</b><br/>
 * Position Data
 * </p>
 *
 * <p><b>Description:</b><br/>
 * SensorML Position Data backed up by a data component
 * </p>
 *
 * <p>Copyright (c) 2005</p>
 * @author Alexandre Robin
 * @date Feb 17, 2006
 * @version 1.0
 */
public class PositionData extends Position
{
    protected DataComponent data;


    public DataComponent getData()
    {
        return data;
    }


    public void setData(DataComponent data)
    {
        this.data = data;
    }
}

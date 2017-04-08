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

import java.util.ArrayList;


/**
 * <p>
 * Represents a list of external connections associated with
 * a given process input/output or parameter.
 * Derives from array list and adds a flag to specify that
 * input or output connections are needed for a process
 * to execute.
 * </p>
 * 
 * @author Alex Robin
 * */
public class DataConnectionList extends ArrayList<DataConnection>
{
    private static final long serialVersionUID = 3109406489044628512L;
    protected boolean needed = true;
    
    
    public DataConnectionList()
    {
        super(1);
    }


    public boolean isNeeded()
    {
        return needed;
    }


    public void setNeeded(boolean needed)
    {
        this.needed = needed;
    }
}

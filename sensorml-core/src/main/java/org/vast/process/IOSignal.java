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
 * Object representing an input, output or parameter signal
 * used by a process or process chain. Each signal can be linked
 * to one or more other process signals using one or more connections.
 * At the process level the separation between signals is important 
 * because only separate signals can send asynchronous data blocks. 
 * All connections coming to or from a single IOSignal are synchronous
 * and thus the corresponding data should be updated and read all at once.   
 * </p>
 *
 * @author Alex Robin <alex.robin@sensiasoftware.com>
 * @since Dec 4, 2006
 * */
public class IOSignal
{
    protected ArrayList<DataConnection> connections;
    protected boolean needed = true;


    public IOSignal()
    {
        connections = new ArrayList<DataConnection>();
    }
    
    
    public ArrayList<DataConnection> getConnections()
    {
        return connections;
    }


    public void setConnections(ArrayList<DataConnection> connections)
    {
        this.connections = connections;
    }


    public boolean isNeeded()
    {
        return needed;
    }


    public void setNeeded(boolean needed)
    {
        this.needed = needed;
    }
    
    
    public void receiveData()
    {
        
    }
    
    
    public void sendData()
    {
        
    }
}

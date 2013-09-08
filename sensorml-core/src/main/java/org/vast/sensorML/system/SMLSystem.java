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

package org.vast.sensorML.system;

import java.util.Hashtable;
import java.util.List;
import org.vast.process.ProcessChain;
import org.vast.process.DataProcess;


/**
 * <p><b>Title:</b><br/>
 * System
 * </p>
 *
 * <p><b>Description:</b><br/>
 * SensorML System
 * </p>
 *
 * <p>Copyright (c) 2005</p>
 * @author Alexandre Robin
 * @date Feb 16, 2006
 * @version 1.0
 */
public class SMLSystem extends ProcessChain
{
    private static final long serialVersionUID = -3907134965328967342L;
    public final static Hashtable<ReferenceFrame, DataProcess> frameToObjectMap = new Hashtable<ReferenceFrame, DataProcess>();
    
    protected List<ReferenceFrame> referenceFrames;
    protected List<InterfaceDef> interfaces;
    protected List<Position> componentPositions;
    protected Hashtable<ReferenceFrame, Position> frameToPosTable;


    public SMLSystem()
    {
    }
    
    
    public List<InterfaceDef> getInterfaces()
    {
        return interfaces;
    }


    public void setInterfaces(List<InterfaceDef> interfaces)
    {
        this.interfaces = interfaces;
    }


    public List<ReferenceFrame> getReferenceFrames()
    {
        return referenceFrames;
    }


    public void setReferenceFrames(List<ReferenceFrame> referenceFrames)
    {
        this.referenceFrames = referenceFrames;
    }


    public List<Position> getComponentPositions()
    {
        return componentPositions;
    }


    public void setComponentPositions(List<Position> componentPositions)
    {
        this.componentPositions = componentPositions;
    }


    public Hashtable<ReferenceFrame, Position> getFrameToPosTable()
    {
        return frameToPosTable;
    }


    public void setFrameToPosTable(Hashtable<ReferenceFrame, Position> frameToPosTable)
    {
        this.frameToPosTable = frameToPosTable;
    }
}

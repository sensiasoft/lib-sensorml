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

import java.util.List;

import org.vast.process.DataProcess;
import org.vast.process.ProcessException;


/**
 * <p><b>Title:</b><br/>
 * Component
 * </p>
 *
 * <p><b>Description:</b><br/>
 * SensorML Component
 * </p>
 *
 * <p>Copyright (c) 2005</p>
 * @author Alexandre Robin
 * @date Feb 16, 2006
 * @version 1.0
 */
public class SMLComponent extends DataProcess
{
    protected List<ReferenceFrame> referenceFrames;
    protected List<InterfaceDef> interfaces;
    protected Position position;
    

    public SMLComponent()
    {        
    }
    
    
    @Override
    public void execute() throws ProcessException
    {

    }


    @Override
    public void init() throws ProcessException
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


    public Position getPosition()
    {
        return position;
    }


    public void setPosition(Position position)
    {
        this.position = position;
    }
}

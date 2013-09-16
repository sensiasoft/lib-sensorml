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

import java.util.List;
import org.vast.sensorML.SMLProcess;


/**
 * <p>
 * SensorML Component
 * </p>
 *
 * <p>Copyright (c) 2005</p>
 * @author Alexandre Robin
 * @since Feb 16, 2006
 * @version 1.0
 */
public class SMLComponent extends SMLProcess implements SMLPhysicalComponent
{
    private static final long serialVersionUID = 8477804187421221012L;
    
    protected List<ReferenceFrame> referenceFrames;
    protected List<InterfaceDef> interfaces;
    protected Position position;
    

    public SMLComponent()
    {        
    }
    

    /* (non-Javadoc)
     * @see org.vast.sensorML.system.SMLPhysicalComponent#getInterfaces()
     */
    @Override
    public List<InterfaceDef> getInterfaces()
    {
        return interfaces;
    }


    /* (non-Javadoc)
     * @see org.vast.sensorML.system.SMLPhysicalComponent#setInterfaces(java.util.List)
     */
    @Override
    public void setInterfaces(List<InterfaceDef> interfaces)
    {
        this.interfaces = interfaces;
    }


    /* (non-Javadoc)
     * @see org.vast.sensorML.system.SMLPhysicalComponent#getReferenceFrames()
     */
    @Override
    public List<ReferenceFrame> getReferenceFrames()
    {
        return referenceFrames;
    }


    /* (non-Javadoc)
     * @see org.vast.sensorML.system.SMLPhysicalComponent#setReferenceFrames(java.util.List)
     */
    @Override
    public void setReferenceFrames(List<ReferenceFrame> referenceFrames)
    {
        this.referenceFrames = referenceFrames;
    }


    /* (non-Javadoc)
     * @see org.vast.sensorML.system.SMLPhysicalComponent#getPosition()
     */
    @Override
    public Position getPosition()
    {
        return position;
    }


    /* (non-Javadoc)
     * @see org.vast.sensorML.system.SMLPhysicalComponent#setPosition(org.vast.sensorML.system.Position)
     */
    @Override
    public void setPosition(Position position)
    {
        this.position = position;
    }
}

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

 Please Contact Alexandre Robin <alex.robin@sensiasoftware.com> or
 Mike Botts <mike.botts@uah.edu> for more information.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.sensorML;

import org.vast.ogc.OGCRegistry;
import org.vast.sensorML.system.SMLSystem;
import org.vast.xml.DOMHelper;
import org.vast.xml.XMLWriterException;
import org.w3c.dom.Element;


/**
 * <p>
 * System Writer for SensorML version 2.0
 * </p>
 *
 * <p>Copyright (c) 2014</p>
 * @author Alexandre Robin
 * @since Feb 6, 2014
 */
public class SystemWriterV20 extends ProcessWriterV20
{    
    
    public SystemWriterV20()
    {
        super();
    }
    
    
    public Element write(DOMHelper dom, SMLProcess smlProcess) throws XMLWriterException
    {
        Element procElt;
        dom.addUserPrefix("sml", OGCRegistry.getNamespaceURI(SMLUtils.SENSORML, "2.0"));
        
        if (smlProcess instanceof SMLSystem)
            procElt = dom.createElement("sml:PhysicalSystem");
        else
            procElt = dom.createElement("sml:PhysicalComponent");
        
        write(dom, procElt, smlProcess);        
        return procElt;
    }
    
    
    @Override
    protected void write(DOMHelper dom, Element procElt, SMLProcess smlProcess) throws XMLWriterException
    {
        super.write(dom, procElt, smlProcess);
        
        // TODO write ref frames, positions, etc
    }
}

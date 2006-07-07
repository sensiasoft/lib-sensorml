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

package org.vast.sensorML.reader;

import org.w3c.dom.*;
import org.vast.cdm.reader.DataComponentsReader;
import org.vast.io.xml.DOMReader;
import org.vast.sensorML.SMLException;
import org.vast.sensorML.system.ProcessMethod;


/**
 * <p><b>Title:</b><br/>
 * Process Method Reader
 * </p>
 *
 * <p><b>Description:</b><br/>
 * TODO ProcessMethodReader type description
 * </p>
 *
 * <p>Copyright (c) 2005</p>
 * @author Alexandre Robin
 * @date Feb 15, 2006
 * @version 1.0
 */
public class ProcessMethodReader extends SMLReader
{
    protected static final String dataSeparator = "/"; 
    protected DataComponentsReader dataComponentReader;
        
    
    public ProcessMethodReader(DOMReader dom)
    {
        this.dom = dom;
        dataComponentReader = new DataComponentsReader(this.dom);
    }

    
    public ProcessMethod readMethodProperty(Element propertyElement) throws SMLException
    {
        Element methodElement = dom.getFirstChildElement(propertyElement);
        ProcessMethod method = readMethod(methodElement);
        return method;
    }
    
    
    public ProcessMethod readMethod(Element processElement) throws SMLException
    {
        // TODO implement readMethod in ProcessMethodReader
        return null;
    }
}
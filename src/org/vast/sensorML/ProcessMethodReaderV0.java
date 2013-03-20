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

package org.vast.sensorML;

import org.w3c.dom.*;
import org.vast.xml.DOMHelper;
import org.vast.xml.XMLReaderException;
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
public class ProcessMethodReaderV0 extends AbstractSMLReader
{
    protected static final String dataSeparator = "/"; 
        
    
    public ProcessMethodReaderV0()
    {
    }

    
    public ProcessMethod readMethodProperty(DOMHelper dom, Element propertyElement) throws XMLReaderException
    {
        Element methodElement = dom.getFirstChildElement(propertyElement);
        ProcessMethod method = readMethod(dom, methodElement);
        return method;
    }
    
    
    public ProcessMethod readMethod(DOMHelper dom, Element processElement) throws XMLReaderException
    {
        // TODO implement readMethod in ProcessMethodReader
        return null;
    }
}
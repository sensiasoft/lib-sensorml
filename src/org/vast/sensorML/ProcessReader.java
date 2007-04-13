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

package org.vast.sensorML;

import org.vast.process.DataProcess;
import org.vast.xml.DOMHelper;
import org.w3c.dom.Element;


/**
 * <p><b>Title:</b>
 * Process Reader
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Base interface for SensorML Process reader of all versions
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Alexandre Robin
 * @date Apr 11, 2007
 * @version 1.0
 */
public interface ProcessReader
{
    /**
     * Reads content of any SensorML Process from the given DOM element
     * @param dom
     * @param processElement
     * @return
     * @throws SMLException
     */
    public DataProcess readProcess(DOMHelper dom, Element processElement) throws SMLException;
        
    
    /**
     * Reads data from an association containing or referencing a Process object
     * @param dom
     * @param propertyElement
     * @return
     * @throws SMLException
     */
    public DataProcess readProcessProperty(DOMHelper dom, Element propertyElement) throws SMLException;
    
    
    /**
     * Process Metadata will be read only if set to true
     * default is false to reduce parsing time 
     * @param readMetadata
     */
    public void setReadMetadata(boolean readMetadata);
    
    
    /**
     * Executable DataProcess objects will be instantiated if set to true
     * Default is true. Set to false for simply parsing the document to Dummy Processes.
     * @param createExecutableProcess
     */
    public void setCreateExecutableProcess(boolean createExecutableProcess);
}

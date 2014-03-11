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

import org.vast.xml.IXMLReaderDOM;


/**
 * <p>
 * Base interface for SensorML Process reader of all versions
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Alexandre Robin
 * @since Apr 11, 2007
 * @version 1.0
 */
public interface ProcessReader extends IXMLReaderDOM<SMLProcess>
{
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

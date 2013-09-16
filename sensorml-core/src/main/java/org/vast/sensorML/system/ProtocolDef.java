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

import org.vast.cdm.common.DataComponent;
import org.vast.cdm.common.DataEncoding;


/**
 * <p>
 * SensorML Protocol Definition. 
 * Provides protocol name, definition uri and parameters.
 * </p>
 *
 * <p>Copyright (c) 2005</p>
 * @author Alexandre Robin
 * @since Feb 17, 2006
 * @version 1.0
 */
public class ProtocolDef
{
    protected String definition;
    protected List<DataComponent> properties;
    protected DataEncoding encoding;


    public String getDefinition()
    {
        return definition;
    }


    public void setDefinition(String definition)
    {
        this.definition = definition;
    }


    public List<DataComponent> getProperties()
    {
        return properties;
    }


    public void setProperties(List<DataComponent> properties)
    {
        this.properties = properties;
    }


    public DataEncoding getEncoding()
    {
        return encoding;
    }


    public void setEncoding(DataEncoding encoding)
    {
        this.encoding = encoding;
    }
}

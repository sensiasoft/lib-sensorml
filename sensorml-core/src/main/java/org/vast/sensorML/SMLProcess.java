/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are subject to the Mozilla Public License Version
 1.1 (the "License"); you may not use this file except in compliance with
 the License. You may obtain a copy of the License at
 http://www.mozilla.org/MPL/MPL-1.1.html
 
 Software distributed under the License is distributed on an "AS IS" basis,
 WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 for the specific language governing rights and limitations under the License.
 
 The Initial Developer of the Original Code is SENSIA SOFTWARE LLC.
 Portions created by the Initial Developer are Copyright (C) 2012
 the Initial Developer. All Rights Reserved.

 Please Contact Alexandre Robin <alex.robin@sensiasoftware.com> for more
 information.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.sensorML;

import java.util.List;
import org.vast.process.DataProcess;
import org.vast.process.ProcessException;
import org.vast.sensorML.configuration.Configuration;
import org.vast.sensorML.configuration.Mode;
import org.vast.sensorML.metadata.Metadata;


/**
 * <p><b>Title:</b>
 * SMLProcess
 * </p>
 *
 * <p><b>Description:</b><br/>
 * SensorML Process
 * </p>
 *
 * <p>Copyright (c) 2012</p>
 * @author Alexandre Robin
 * @date Sep 8, 2013
 * @version 1.0
 */
public class SMLProcess extends DataProcess
{
    private static final long serialVersionUID = -6253151260359858061L;

    protected String uniqueID;
    protected Configuration configuration;
    protected List<Mode> modes;
    

    public String getUniqueID()
    {
        return uniqueID;
    }


    public void setUniqueID(String uniqueID)
    {
        this.uniqueID = uniqueID;
    }


    public Metadata getMetadata()
    {
        return (Metadata)this.getProperty(DataProcess.METADATA);
    }
    
    
    public void setMetadata(Metadata metadata)
    {
        this.setProperty(DataProcess.METADATA, metadata);
    }
    
    
    public Configuration getConfiguration()
    {
        return configuration;
    }


    public void setConfiguration(Configuration configuration)
    {
        this.configuration = configuration;
    }


    public List<Mode> getModes()
    {
        return modes;
    }


    public void setModes(List<Mode> modes)
    {
        this.modes = modes;
    }
    
    
    @Override
    public void init() throws ProcessException
    {        
    }


    @Override
    public void execute() throws ProcessException
    {        
    }

}

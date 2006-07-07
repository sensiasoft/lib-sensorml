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

import java.net.URI;
import java.util.Hashtable;


/**
 * <p><b>Title:</b><br/>
 * Reference Frame
 * </p>
 *
 * <p><b>Description:</b><br/>
 * SensorML Reference Frame
 * </p>
 *
 * <p>Copyright (c) 2005</p>
 * @author Alexandre Robin
 * @date Feb 17, 2006
 * @version 1.0
 */
public class ReferenceFrame
{
    public final static Hashtable<URI, ReferenceFrame> uriToFrameMap = new Hashtable<URI, ReferenceFrame>();
    
    protected String id;
    protected String name;
    protected String csType;
    protected String datumName;
    protected String datumDescription;


    public String getId()
    {
        return id;
    }


    public void setId(String id)
    {
        this.id = id;
    }


    public String getName()
    {
        return name;
    }


    public void setName(String name)
    {
        this.name = name;
    }
    
    
    public String getCsType()
    {
        return csType;
    }


    public void setCsType(String csType)
    {
        this.csType = csType;
    }


    public String getDatumName()
    {
        return datumName;
    }


    public void setDatumName(String datumName)
    {
        this.datumName = datumName;
    }
    
    
    public String getDatumDescription()
    {
        return datumDescription;
    }


    public void setDatumDescription(String datumDescription)
    {
        this.datumDescription = datumDescription;
    }   
}

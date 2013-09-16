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

package org.vast.sensorML.metadata;

import org.vast.util.Contact;
import org.vast.util.DateTime;


/**
 * <p>
 * SensorML DocumentPointer
 * </p>
 *
 * <p>Copyright (c) 2005</p>
 * @author Alexandre Robin
 * @since Feb 16, 2006
 * @version 1.0
 */
public class DocumentRef
{
    protected String description;
    protected String role;
    protected String version;
    protected DateTime date;
    protected Contact contact;
    protected String format;    
    protected String fileLocation;


    public Contact getContact()
    {
        return contact;
    }


    public void setContact(Contact contact)
    {
        this.contact = contact;
    }


    public DateTime getDate()
    {
        return date;
    }


    public void setDate(DateTime date)
    {
        this.date = date;
    }


    public String getDescription()
    {
        return description;
    }


    public void setDescription(String description)
    {
        this.description = description;
    }


    public String getFormat()
    {
        return format;
    }


    public void setFormat(String format)
    {
        this.format = format;
    }


    public String getFileLocation()
    {
        return fileLocation;
    }


    public void setFileLocation(String fileLocation)
    {
        this.fileLocation = fileLocation;
    }


    public String getRole()
    {
        return role;
    }


    public void setRole(String type)
    {
        this.role = type;
    }


    public String getVersion()
    {
        return version;
    }


    public void setVersion(String version)
    {
        this.version = version;
    }
}

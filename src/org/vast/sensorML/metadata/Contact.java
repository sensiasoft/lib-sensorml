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

package org.vast.sensorML.metadata;

/**
 * <p><b>Title:</b><br/>
 * Contact
 * </p>
 *
 * <p><b>Description:</b><br/>
 * SensorML Contact
 * </p>
 *
 * <p>Copyright (c) 2005</p>
 * @author Alexandre Robin
 * @date Feb 16, 2006
 * @version 1.0
 */
public abstract class Contact
{
    protected String role;
    protected String hrefUri;
    protected boolean hrefPresent = false;
    

    public String getRole()
    {
        return role;
    }


    public void setRole(String role)
    {
        this.role = role;
    }


    public boolean isHrefPresent()
    {
        return hrefPresent;
    }


    public void setHrefPresent(boolean hrefPresent)
    {
        this.hrefPresent = hrefPresent;
    }


    public String getHrefUri()
    {
        return hrefUri;
    }


    public void setHrefUri(String hrefUri)
    {
        this.hrefUri = hrefUri;
    }
}
/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are subject to the Mozilla Public License Version
 1.1 (the "License"); you may not use this file except in compliance with
 the License. You may obtain a copy of the License at
 http://www.mozilla.org/MPL/MPL-1.1.html
 
 Software distributed under the License is distributed on an "AS IS" basis,
 WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 for the specific language governing rights and limitations under the License.
 
 The Original Code is the "SensorML DataProcessing Engine".
 
 The Initial Developer of the Original Code is Sensia Software LLC.
 Portions created by the Initial Developer are Copyright (C) 2014
 the Initial Developer. All Rights Reserved.
 
 Please Contact Alexandre Robin <alex.robin@sensiasoftware.com> or
 Mike Botts <mike.botts@botts-inc.net> for more information.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.sensorML.metadata;

import java.util.ArrayList;


/**
 * <p>
 * Generic class for all metadata lists used in SensorML documents:
 * Keywords, Terms, Contacts, etc...  
 * </p>
 *
 * <p>Copyright (c) 2014 Sensia Software LLC</p>
 * @author Alexandre Robin <alex.robin@sensiasoftware.com>
 * @since Mar 12, 2014
 */
public class MetadataList<ItemType> extends ArrayList<ItemType>
{
    private static final long serialVersionUID = 4896366007675756619L;

    protected String localId;
    protected String identifier;
    protected String label;
    protected String description;


    public MetadataList(int listSize)
    {
        super(listSize);
    }
    
    
    public String getLocalId()
    {
        return localId;
    }


    public void setLocalId(String localId)
    {
        this.localId = localId;
    }


    public String getIdentifier()
    {
        return identifier;
    }


    public void setIdentifier(String identifier)
    {
        this.identifier = identifier;
    }


    public String getLabel()
    {
        return label;
    }


    public void setLabel(String label)
    {
        this.label = label;
    }


    public String getDescription()
    {
        return description;
    }


    public void setDescription(String description)
    {
        this.description = description;
    }

}

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
public class MetadataList<ItemType> extends ArrayList<ItemType> implements IMetadataList<ItemType>
{
    private static final long serialVersionUID = 4896366007675756619L;

    protected String localId;
    protected String identifier;
    protected String label;
    protected String description;
    protected String codespace;


    public MetadataList(int listSize)
    {
        super(listSize);
    }
    
    
    /* (non-Javadoc)
     * @see org.vast.sensorML.metadata.IMetadataList#getLocalId()
     */
    @Override
    public String getLocalId()
    {
        return localId;
    }


    /* (non-Javadoc)
     * @see org.vast.sensorML.metadata.IMetadataList#setLocalId(java.lang.String)
     */
    @Override
    public void setLocalId(String localId)
    {
        this.localId = localId;
    }


    /* (non-Javadoc)
     * @see org.vast.sensorML.metadata.IMetadataList#getIdentifier()
     */
    @Override
    public String getIdentifier()
    {
        return identifier;
    }


    /* (non-Javadoc)
     * @see org.vast.sensorML.metadata.IMetadataList#setIdentifier(java.lang.String)
     */
    @Override
    public void setIdentifier(String identifier)
    {
        this.identifier = identifier;
    }


    /* (non-Javadoc)
     * @see org.vast.sensorML.metadata.IMetadataList#getLabel()
     */
    @Override
    public String getLabel()
    {
        return label;
    }


    /* (non-Javadoc)
     * @see org.vast.sensorML.metadata.IMetadataList#setLabel(java.lang.String)
     */
    @Override
    public void setLabel(String label)
    {
        this.label = label;
    }


    /* (non-Javadoc)
     * @see org.vast.sensorML.metadata.IMetadataList#getDescription()
     */
    @Override
    public String getDescription()
    {
        return description;
    }


    /* (non-Javadoc)
     * @see org.vast.sensorML.metadata.IMetadataList#setDescription(java.lang.String)
     */
    @Override
    public void setDescription(String description)
    {
        this.description = description;
    }


    /* (non-Javadoc)
     * @see org.vast.sensorML.metadata.IMetadataList#getCodespace(java.lang.String)
     */
    @Override
    public String getCodespace()
    {
        return codespace;
    }


    /* (non-Javadoc)
     * @see org.vast.sensorML.metadata.IMetadataList#setCodespace(java.lang.String)
     */
    @Override
    public void setCodespace(String codespace)
    {
        this.codespace = codespace;        
    }

}

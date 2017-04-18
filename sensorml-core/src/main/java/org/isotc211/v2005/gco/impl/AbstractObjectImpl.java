/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2015 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package org.isotc211.v2005.gco.impl;

import org.isotc211.v2005.gco.AbstractObject;


/**
 * POJO class for XML type AbstractObject_Type(@http://www.isotc211.org/2005/gco).
 *
 * This is a complex type.
 */
public abstract class AbstractObjectImpl implements AbstractObject
{
    private static final long serialVersionUID = -5445391513034725710L;
    protected String id;
    protected String uuid;
    
    
    public AbstractObjectImpl()
    {
    }
    
    
    /**
     * Gets the id property
     */
    @Override
    public String getId()
    {
        return id;
    }
    
    
    /**
     * Checks if id is set
     */
    @Override
    public boolean isSetId()
    {
        return (id != null);
    }
    
    
    /**
     * Sets the id property
     */
    @Override
    public void setId(String id)
    {
        this.id = id;
    }
    
    
    /**
     * Gets the uuid property
     */
    @Override
    public String getUuid()
    {
        return uuid;
    }
    
    
    /**
     * Checks if uuid is set
     */
    @Override
    public boolean isSetUuid()
    {
        return (uuid != null);
    }
    
    
    /**
     * Sets the uuid property
     */
    @Override
    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }
}

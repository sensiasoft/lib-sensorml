/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2015 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package org.isotc211.v2005.gco;

import java.io.Serializable;

/**
 * POJO class for XML type AbstractObject_Type(@http://www.isotc211.org/2005/gco).
 *
 * This is a complex type.
 */
public interface AbstractObject extends Serializable
{
    
    
    /**
     * Gets the id property
     */
    public String getId();
    
    
    /**
     * Checks if id is set
     */
    public boolean isSetId();
    
    
    /**
     * Sets the id property
     */
    public void setId(String id);
    
    
    /**
     * Gets the uuid property
     */
    public String getUuid();
    
    
    /**
     * Checks if uuid is set
     */
    public boolean isSetUuid();
    
    
    /**
     * Sets the uuid property
     */
    public void setUuid(String uuid);
}

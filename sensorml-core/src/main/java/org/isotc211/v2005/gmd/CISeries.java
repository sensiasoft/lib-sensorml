/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2015 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package org.isotc211.v2005.gmd;

import org.isotc211.v2005.gco.AbstractObject;


/**
 * POJO class for XML type CI_Series_Type(@http://www.isotc211.org/2005/gmd).
 *
 * This is a complex type.
 */
public interface CISeries extends AbstractObject
{
    
    
    /**
     * Gets the name property
     */
    public String getName();
    
    
    /**
     * Checks if name is set
     */
    public boolean isSetName();
    
    
    /**
     * Sets the name property
     */
    public void setName(String name);
    
    
    /**
     * Gets the issueIdentification property
     */
    public String getIssueIdentification();
    
    
    /**
     * Checks if issueIdentification is set
     */
    public boolean isSetIssueIdentification();
    
    
    /**
     * Sets the issueIdentification property
     */
    public void setIssueIdentification(String issueIdentification);
    
    
    /**
     * Gets the page property
     */
    public String getPage();
    
    
    /**
     * Checks if page is set
     */
    public boolean isSetPage();
    
    
    /**
     * Sets the page property
     */
    public void setPage(String page);
}

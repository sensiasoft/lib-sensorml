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
 * POJO class for XML type UnlimitedInteger_Type(@http://www.isotc211.org/2005/gco).
 *
 */
public interface UnlimitedInteger extends Serializable
{
    
    
    /**
     * Gets the isInfinite property
     */
    public boolean getIsInfinite();
    
    
    /**
     * Checks if isInfinite is set
     */
    public boolean isSetIsInfinite();
    
    
    /**
     * Sets the isInfinite property
     */
    public void setIsInfinite(boolean isInfinite);
    
    
    /**
     * Unsets the isInfinite property
     */
    public void unSetIsInfinite();
    
    
    /**
     * Gets the inline value
     */
    public int getValue();
    
    
    /**
     * Sets the inline value
     */
    public void setValue(int value);
}

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

import java.time.OffsetDateTime;
import org.isotc211.v2005.gco.AbstractObject;
import org.isotc211.v2005.gco.CodeListValue;


/**
 * POJO class for XML type CI_Date_Type(@http://www.isotc211.org/2005/gmd).
 *
 * This is a complex type.
 */
public interface CIDate extends AbstractObject
{
    
    
    /**
     * Gets the date property
     */
    public OffsetDateTime getDate();
    
    
    /**
     * Sets the date property
     */
    public void setDate(OffsetDateTime date);
    
    
    /**
     * Gets the dateType property
     */
    public CodeListValue getDateType();
    
    
    /**
     * Sets the dateType property
     */
    public void setDateType(CodeListValue dateType);
}

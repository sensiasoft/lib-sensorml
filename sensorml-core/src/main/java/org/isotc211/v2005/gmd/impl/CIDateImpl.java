/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2015 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package org.isotc211.v2005.gmd.impl;

import net.opengis.IDateTime;
import org.isotc211.v2005.gco.CodeListValue;
import org.isotc211.v2005.gco.impl.AbstractObjectImpl;
import org.isotc211.v2005.gmd.CIDate;


/**
 * POJO class for XML type CI_Date_Type(@http://www.isotc211.org/2005/gmd).
 *
 * This is a complex type.
 */
public class CIDateImpl extends AbstractObjectImpl implements CIDate
{
    private static final long serialVersionUID = 6850359308276013387L;
    protected IDateTime date;
    protected CodeListValue dateType;
    
    
    public CIDateImpl()
    {
    }
    
    
    /**
     * Gets the date property
     */
    @Override
    public IDateTime getDate()
    {
        return date;
    }
    
    
    /**
     * Sets the date property
     */
    @Override
    public void setDate(IDateTime date)
    {
        this.date = date;
    }
    
    
    /**
     * Gets the dateType property
     */
    @Override
    public CodeListValue getDateType()
    {
        return dateType;
    }
    
    
    /**
     * Sets the dateType property
     */
    @Override
    public void setDateType(CodeListValue dateType)
    {
        this.dateType = dateType;
    }
}

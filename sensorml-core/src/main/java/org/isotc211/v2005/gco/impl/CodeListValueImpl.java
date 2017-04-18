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

import org.isotc211.v2005.gco.CodeListValue;


/**
 * POJO class for XML type CodeListValue_Type(@http://www.isotc211.org/2005/gco).
 *
 */
public class CodeListValueImpl implements CodeListValue
{
    private static final long serialVersionUID = -3041902128756050666L;
    protected String codeList = "";
    protected String codeListValue = "";
    protected String codeSpace;
    protected String value;
    
    
    public CodeListValueImpl()
    {
    }
    
    
    public CodeListValueImpl(String value)
    {
        this.value = value;
    }
    
    
    /**
     * Gets the codeList property
     */
    @Override
    public String getCodeList()
    {
        return codeList;
    }
    
    
    /**
     * Sets the codeList property
     */
    @Override
    public void setCodeList(String codeList)
    {
        this.codeList = codeList;
    }
    
    
    /**
     * Gets the codeListValue property
     */
    @Override
    public String getCodeListValue()
    {
        return codeListValue;
    }
    
    
    /**
     * Sets the codeListValue property
     */
    @Override
    public void setCodeListValue(String codeListValue)
    {
        this.codeListValue = codeListValue;
    }
    
    
    /**
     * Gets the codeSpace property
     */
    @Override
    public String getCodeSpace()
    {
        return codeSpace;
    }
    
    
    /**
     * Checks if codeSpace is set
     */
    @Override
    public boolean isSetCodeSpace()
    {
        return (codeSpace != null);
    }
    
    
    /**
     * Sets the codeSpace property
     */
    @Override
    public void setCodeSpace(String codeSpace)
    {
        this.codeSpace = codeSpace;
    }
    
    
    /**
     * Gets the inline value
     */
    @Override
    public String getValue()
    {
        return value;
    }
    
    
    /**
     * Sets the inline value
     */
    @Override
    public void setValue(String value)
    {
        this.value = value;
    }
}

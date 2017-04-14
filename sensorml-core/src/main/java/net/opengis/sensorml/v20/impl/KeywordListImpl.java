/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2015 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package net.opengis.sensorml.v20.impl;

import java.util.ArrayList;
import java.util.List;
import net.opengis.sensorml.v20.KeywordList;


/**
 * POJO class for XML type KeywordListType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public class KeywordListImpl extends AbstractMetadataListImpl implements KeywordList
{
    private static final long serialVersionUID = -733089128014753013L;
    protected String codeSpace;
    protected ArrayList<String> keywordList = new ArrayList<String>();
    
    
    public KeywordListImpl()
    {
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
     * Gets the list of keyword properties
     */
    @Override
    public List<String> getKeywordList()
    {
        return keywordList;
    }
    
    
    /**
     * Returns number of keyword properties
     */
    @Override
    public int getNumKeywords()
    {
        if (keywordList == null)
            return 0;
        return keywordList.size();
    }
    
    
    /**
     * Adds a new keyword property
     */
    @Override
    public void addKeyword(String keyword)
    {
        this.keywordList.add(keyword);
    }
}

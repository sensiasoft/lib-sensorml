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

import java.util.ArrayList;
import java.util.List;
import org.isotc211.v2005.gco.impl.AbstractObjectImpl;
import org.isotc211.v2005.gmd.CITelephone;


/**
 * POJO class for XML type CI_Telephone_Type(@http://www.isotc211.org/2005/gmd).
 *
 * This is a complex type.
 */
public class CITelephoneImpl extends AbstractObjectImpl implements CITelephone
{
    private static final long serialVersionUID = -5758289910414075116L;
    protected ArrayList<String> voiceList = new ArrayList<String>();
    protected ArrayList<String> facsimileList = new ArrayList<String>();
    
    
    public CITelephoneImpl()
    {
    }
    
    
    /**
     * Gets the list of voice properties
     */
    @Override
    public List<String> getVoiceList()
    {
        return voiceList;
    }
    
    
    /**
     * Returns number of voice properties
     */
    @Override
    public int getNumVoices()
    {
        if (voiceList == null)
            return 0;
        return voiceList.size();
    }
    
    
    /**
     * Adds a new voice property
     */
    @Override
    public void addVoice(String voice)
    {
        this.voiceList.add(voice);
    }
    
    
    /**
     * Gets the list of facsimile properties
     */
    @Override
    public List<String> getFacsimileList()
    {
        return facsimileList;
    }
    
    
    /**
     * Returns number of facsimile properties
     */
    @Override
    public int getNumFacsimiles()
    {
        if (facsimileList == null)
            return 0;
        return facsimileList.size();
    }
    
    
    /**
     * Adds a new facsimile property
     */
    @Override
    public void addFacsimile(String facsimile)
    {
        this.facsimileList.add(facsimile);
    }
}

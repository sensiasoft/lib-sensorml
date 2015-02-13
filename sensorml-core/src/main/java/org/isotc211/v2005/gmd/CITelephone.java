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

import java.util.List;
import org.isotc211.v2005.gco.AbstractObject;


/**
 * POJO class for XML type CI_Telephone_Type(@http://www.isotc211.org/2005/gmd).
 *
 * This is a complex type.
 */
public interface CITelephone extends AbstractObject
{
    
    
    /**
     * Gets the list of voice properties
     */
    public List<String> getVoiceList();
    
    
    /**
     * Returns number of voice properties
     */
    public int getNumVoices();
    
    
    /**
     * Adds a new voice property
     */
    public void addVoice(String voice);
    
    
    /**
     * Gets the list of facsimile properties
     */
    public List<String> getFacsimileList();
    
    
    /**
     * Returns number of facsimile properties
     */
    public int getNumFacsimiles();
    
    
    /**
     * Adds a new facsimile property
     */
    public void addFacsimile(String facsimile);
}

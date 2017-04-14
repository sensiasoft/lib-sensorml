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

import net.opengis.OgcPropertyList;
import net.opengis.sensorml.v20.Mode;
import net.opengis.sensorml.v20.ModeChoice;


/**
 * POJO class for XML type ModeChoiceType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public class ModeChoiceImpl extends AbstractModesImpl implements ModeChoice
{
    private static final long serialVersionUID = 3993897379800177350L;
    protected OgcPropertyList<Mode> modeList = new OgcPropertyList<Mode>();
    
    
    public ModeChoiceImpl()
    {
    }
    
    
    /**
     * Gets the list of mode properties
     */
    @Override
    public OgcPropertyList<Mode> getModeList()
    {
        return modeList;
    }
    
    
    /**
     * Returns number of mode properties
     */
    @Override
    public int getNumModes()
    {
        if (modeList == null)
            return 0;
        return modeList.size();
    }
    
    
    /**
     * Adds a new mode property
     */
    @Override
    public void addMode(Mode mode)
    {
        this.modeList.add(mode);
    }
}

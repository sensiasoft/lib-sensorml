/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2015 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.sensorML;

import org.isotc211.v2005.gmd.CIResponsibleParty;
import org.isotc211.v2005.gmd.impl.CIResponsiblePartyImpl;
import net.opengis.sensorml.v20.DataInterface;
import net.opengis.swe.v20.AbstractSWEIdentifiable;
import net.opengis.swe.v20.DataComponent;
import net.opengis.swe.v20.DataEncoding;
import net.opengis.swe.v20.DataStream;


public class SMLHelper extends SMLFactory
{
    
    public CIResponsibleParty newResponsibleParty()
    {
        return new CIResponsiblePartyImpl();
    }
    
    
    /**
     * Helper to get the input/output/parameter component description whether
     * the IO is wrapped in a DataInterface or DataStream or given as a raw
     * DataComponent.
     * @param ioDef
     * @return DataComponent
     */
    public static DataComponent getIOComponent(AbstractSWEIdentifiable ioDef)
    {
        if (ioDef instanceof DataInterface)
            return ((DataInterface)ioDef).getData().getElementType();
        else if (ioDef instanceof DataStream)
            return ((DataStream)ioDef).getElementType();
        else
            return (DataComponent)ioDef;
    }
    
    
    /**
     * Helper to get the input/output/parameter encoding description whether
     * the IO is wrapped in a DataInterface or DataStream.<br/>
     * This method returns null if the IO is described as a raw DataComponent.
     * @param ioDef
     * @return DataComponent
     */
    public static DataEncoding getIOEncoding(AbstractSWEIdentifiable ioDef)
    {
        if (ioDef instanceof DataInterface)
            return ((DataInterface)ioDef).getData().getEncoding();
        else if (ioDef instanceof DataStream)
            return ((DataStream)ioDef).getEncoding();
        else 
            return null;
    }
    
    
    /*public static DataComponent findComponentByPath(String path, DataComponent parent)
    {
        
    }*/
}

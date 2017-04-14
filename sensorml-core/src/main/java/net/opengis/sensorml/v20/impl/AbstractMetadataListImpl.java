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

import org.vast.data.AbstractSWEIdentifiableImpl;
import net.opengis.sensorml.v20.AbstractMetadataList;


/**
 * POJO class for XML type AbstractMetadataListType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public class AbstractMetadataListImpl extends AbstractSWEIdentifiableImpl implements AbstractMetadataList
{
    private static final long serialVersionUID = -7690755144431209738L;
    protected String definition;
    
    
    public AbstractMetadataListImpl()
    {
    }
    
    
    /**
     * Gets the definition property
     */
    @Override
    public String getDefinition()
    {
        return definition;
    }
    
    
    /**
     * Checks if definition is set
     */
    @Override
    public boolean isSetDefinition()
    {
        return (definition != null);
    }
    
    
    /**
     * Sets the definition property
     */
    @Override
    public void setDefinition(String definition)
    {
        this.definition = definition;
    }
}

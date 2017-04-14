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
import net.opengis.sensorml.v20.ObservableProperty;


/**
 * POJO class for XML type ObservablePropertyType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public class ObservablePropertyImpl extends AbstractSWEIdentifiableImpl implements ObservableProperty
{
    private static final long serialVersionUID = -1175251297426113987L;
    protected String definition = "";
    
    
    public ObservablePropertyImpl()
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
     * Sets the definition property
     */
    @Override
    public void setDefinition(String definition)
    {
        this.definition = definition;
    }
}

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

import net.opengis.sensorml.v20.AbstractAlgorithm;


/**
 * POJO class for XML type AbstractAlgorithmType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public abstract class AbstractAlgorithmImpl implements AbstractAlgorithm
{
    private static final long serialVersionUID = -3527550148916376048L;
    protected String id;
    
    
    public AbstractAlgorithmImpl()
    {
    }
    
    
    /**
     * Gets the id property
     */
    @Override
    public String getId()
    {
        return id;
    }
    
    
    /**
     * Checks if id is set
     */
    @Override
    public boolean isSetId()
    {
        return (id != null);
    }
    
    
    /**
     * Sets the id property
     */
    @Override
    public void setId(String id)
    {
        this.id = id;
    }
}

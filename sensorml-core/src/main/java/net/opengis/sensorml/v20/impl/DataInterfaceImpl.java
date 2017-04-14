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
import net.opengis.OgcProperty;
import net.opengis.OgcPropertyImpl;
import net.opengis.sensorml.v20.DataInterface;
import net.opengis.swe.v20.DataRecord;
import net.opengis.swe.v20.DataStream;


/**
 * POJO class for XML type DataInterfaceType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public class DataInterfaceImpl extends AbstractSWEIdentifiableImpl implements DataInterface
{
    private static final long serialVersionUID = 7322034740312533028L;
    protected OgcProperty<DataStream> data = new OgcPropertyImpl<DataStream>();
    protected OgcProperty<DataRecord> interfaceParameters;
    
    
    public DataInterfaceImpl()
    {
    }
    
    
    /**
     * Gets the data property
     */
    @Override
    public DataStream getData()
    {
        return data.getValue();
    }
    
    
    /**
     * Gets extra info (name, xlink, etc.) carried by the data property
     */
    @Override
    public OgcProperty<DataStream> getDataProperty()
    {
        if (data == null)
            data = new OgcPropertyImpl<DataStream>();
        return data;
    }
    
    
    /**
     * Sets the data property
     */
    @Override
    public void setData(DataStream data)
    {
        this.data.setValue(data);
    }
    
    
    /**
     * Gets the interfaceParameters property
     */
    @Override
    public DataRecord getInterfaceParameters()
    {
        return interfaceParameters.getValue();
    }
    
    
    /**
     * Gets extra info (name, xlink, etc.) carried by the interfaceParameters property
     */
    @Override
    public OgcProperty<DataRecord> getInterfaceParametersProperty()
    {
        if (interfaceParameters == null)
            interfaceParameters = new OgcPropertyImpl<DataRecord>();
        return interfaceParameters;
    }
    
    
    /**
     * Checks if interfaceParameters is set
     */
    @Override
    public boolean isSetInterfaceParameters()
    {
        return (interfaceParameters != null && (interfaceParameters.hasValue() || interfaceParameters.hasHref()));
    }
    
    
    /**
     * Sets the interfaceParameters property
     */
    @Override
    public void setInterfaceParameters(DataRecord interfaceParameters)
    {
        if (this.interfaceParameters == null)
            this.interfaceParameters = new OgcPropertyImpl<DataRecord>();
        this.interfaceParameters.setValue(interfaceParameters);
    }
}

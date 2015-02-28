/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2015 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package net.opengis.sensorml.v20;

import net.opengis.OgcProperty;
import net.opengis.swe.v20.AbstractSWEIdentifiable;
import net.opengis.swe.v20.DataRecord;
import net.opengis.swe.v20.DataStream;


/**
 * POJO class for XML type DataInterfaceType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
@SuppressWarnings("javadoc")
public interface DataInterface extends AbstractSWEIdentifiable
{
    
    
    /**
     * Gets the data property
     */
    public DataStream getData();
    
    
    /**
     * Gets extra info (name, xlink, etc.) carried by the data property
     */
    public OgcProperty<DataStream> getDataProperty();
    
    
    /**
     * Sets the data property
     */
    public void setData(DataStream data);
    
    
    /**
     * Gets the interfaceParameters property
     */
    public DataRecord getInterfaceParameters();
    
    
    /**
     * Gets extra info (name, xlink, etc.) carried by the interfaceParameters property
     */
    public OgcProperty<DataRecord> getInterfaceParametersProperty();
    
    
    /**
     * Checks if interfaceParameters is set
     */
    public boolean isSetInterfaceParameters();
    
    
    /**
     * Sets the interfaceParameters property
     */
    public void setInterfaceParameters(DataRecord interfaceParameters);
}

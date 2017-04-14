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

import net.opengis.OgcProperty;
import net.opengis.OgcPropertyImpl;
import net.opengis.sensorml.v20.Mode;
import net.opengis.sensorml.v20.Settings;


/**
 * POJO class for XML type ModeType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public class ModeImpl extends DescribedObjectImpl implements Mode
{
    private static final long serialVersionUID = -2912501634984471050L;
    protected OgcProperty<Settings> configuration = new OgcPropertyImpl<Settings>();
    
    
    public ModeImpl()
    {
    }
    
    
    /**
     * Gets the configuration property
     */
    @Override
    public Settings getConfiguration()
    {
        return configuration.getValue();
    }
    
    
    /**
     * Gets extra info (name, xlink, etc.) carried by the configuration property
     */
    @Override
    public OgcProperty<Settings> getConfigurationProperty()
    {
        if (configuration == null)
            configuration = new OgcPropertyImpl<Settings>();
        return configuration;
    }
    
    
    /**
     * Sets the configuration property
     */
    @Override
    public void setConfiguration(Settings configuration)
    {
        this.configuration.setValue(configuration);
    }

}

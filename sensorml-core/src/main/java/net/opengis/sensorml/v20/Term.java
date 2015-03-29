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

import net.opengis.swe.v20.AbstractSWE;


/**
 * POJO class for XML type TermType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
@SuppressWarnings("javadoc")
public interface Term extends AbstractSWE
{
    
    
    /**
     * Gets the label property
     */
    public String getLabel();
    
    
    /**
     * Sets the label property
     */
    public void setLabel(String label);
    
    
    /**
     * Gets the codeSpace URI
     */
    public String getCodeSpace();
    
    
    /**
     * Checks if codeSpace URI is set
     */
    public boolean isSetCodeSpace();
    
    
    /**
     * Sets the codeSpace URI
     */
    public void setCodeSpace(String codeSpace);
    
    
    /**
     * Gets the value property
     */
    public String getValue();
    
    
    /**
     * Sets the value property
     */
    public void setValue(String value);
    
    
    /**
     * Gets the definition property
     */
    public String getDefinition();
    
    
    /**
     * Checks if definition is set
     */
    public boolean isSetDefinition();
    
    
    /**
     * Sets the definition property
     */
    public void setDefinition(String definition);
}

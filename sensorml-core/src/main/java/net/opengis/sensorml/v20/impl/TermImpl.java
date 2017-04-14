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

import org.vast.data.AbstractSWEImpl;
import net.opengis.sensorml.v20.Term;


/**
 * POJO class for XML type TermType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public class TermImpl extends AbstractSWEImpl implements Term
{
    private static final long serialVersionUID = -8693907940605648415L;
    protected String label = "";
    protected String codeSpace;
    protected String value = "";
    protected String definition;
    
    
    public TermImpl()
    {
    }
    
    
    /**
     * Gets the label property
     */
    @Override
    public String getLabel()
    {
        return label;
    }
    
    
    /**
     * Sets the label property
     */
    @Override
    public void setLabel(String label)
    {
        this.label = label;
    }
    
    
    /**
     * Gets the codeSpace property
     */
    @Override
    public String getCodeSpace()
    {
        return codeSpace;
    }
    
    
    /**
     * Checks if codeSpace is set
     */
    @Override
    public boolean isSetCodeSpace()
    {
        return (codeSpace != null);
    }
    
    
    /**
     * Sets the codeSpace property
     */
    @Override
    public void setCodeSpace(String codeSpace)
    {
        this.codeSpace = codeSpace;
    }
    
    
    /**
     * Gets the value property
     */
    @Override
    public String getValue()
    {
        return value;
    }
    
    
    /**
     * Sets the value property
     */
    @Override
    public void setValue(String value)
    {
        this.value = value;
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

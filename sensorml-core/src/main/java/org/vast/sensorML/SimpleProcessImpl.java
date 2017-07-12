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

import org.vast.process.IProcessExec;
import org.vast.process.ProcessException;
import net.opengis.OgcProperty;
import net.opengis.OgcPropertyImpl;
import net.opengis.gml.v32.impl.ReferenceImpl;
import net.opengis.sensorml.v20.ProcessMethod;
import net.opengis.sensorml.v20.SimpleProcess;


/**
 * POJO class for XML type SimpleProcessType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public class SimpleProcessImpl extends AbstractProcessImpl implements SimpleProcess
{
    private static final long serialVersionUID = -8432209624947754205L;
    protected OgcProperty<ProcessMethod> method;
    
    
    public SimpleProcessImpl()
    {
        this.qName = SimpleProcess.DEFAULT_QNAME;
    }
    
    
    @Override
    public void setExecutableImpl(IProcessExec processExec) throws ProcessException
    {
        super.setExecutableImpl(processExec);
        
        if (!isSetTypeOf() && !isSetMethod())
            setTypeOf(new ReferenceImpl(processExec.getProcessInfo().getUri()));
    }


    /**
     * Gets the method property
     */
    @Override
    public ProcessMethod getMethod()
    {
        return method.getValue();
    }
    
    
    /**
     * Gets extra info (name, xlink, etc.) carried by the method property
     */
    @Override
    public OgcProperty<ProcessMethod> getMethodProperty()
    {
        if (method == null)
            method = new OgcPropertyImpl<ProcessMethod>();
        return method;
    }
    
    
    /**
     * Checks if method is set
     */
    @Override
    public boolean isSetMethod()
    {
        return (method != null && (method.hasValue() || method.hasHref()));
    }
    
    
    /**
     * Sets the method property
     */
    @Override
    public void setMethod(ProcessMethod method)
    {
        if (this.method == null)
            this.method = new OgcPropertyImpl<ProcessMethod>();
        this.method.setValue(method);
    }
}

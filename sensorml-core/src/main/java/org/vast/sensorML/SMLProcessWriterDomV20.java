/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are subject to the Mozilla Public License Version
 1.1 (the "License"); you may not use this file except in compliance with
 the License. You may obtain a copy of the License at
 http://www.mozilla.org/MPL/MPL-1.1.html
 
 Software distributed under the License is distributed on an "AS IS" basis,
 WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 for the specific language governing rights and limitations under the License.
 
 The Original Code is the "SensorML DataProcessing Engine".
 
 The Initial Developer of the Original Code is Sensia Software LLC.
 Portions created by the Initial Developer are Copyright (C) 2014
 the Initial Developer. All Rights Reserved.
 
 Please Contact Alexandre Robin <alex.robin@sensiasoftware.com> or
 Mike Botts <mike.botts@botts-inc.net> for more information.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.sensorML;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.dom.DOMResult;
import net.opengis.sensorml.v20.AbstractProcess;
import org.vast.xml.DOMHelper;
import org.vast.xml.IXMLWriterDOM;
import org.vast.xml.XMLWriterException;
import org.w3c.dom.Element;


/**
 * <p>
 * DOM writer for all types of SensorML 2.0 processes.
 * This is implemented as a wrapper on top of the StAX bindings.
 * </p>
 *
 * <p>Copyright (c) 2014 Sensia Software LLC</p>
 * @author Alexandre Robin <alex.robin@sensiasoftware.com>
 * @since Nov 14, 2014
 */
public class SMLProcessWriterDomV20 implements IXMLWriterDOM<AbstractProcess>
{
    SMLStaxBindings staxWriter = new SMLStaxBindings();
    
    
    @Override
    public Element write(DOMHelper dom, AbstractProcess process) throws XMLWriterException
    {
        try
        {
            DOMResult result = new DOMResult(dom.createElement("fragment"));
            XMLStreamWriter writer = XMLOutputFactory.newInstance().createXMLStreamWriter(result);
            staxWriter.setNamespacePrefixes(writer);
            staxWriter.declareNamespacesOnRootElement();
            staxWriter.writeAbstractProcess(writer, process);
            return (Element)result.getNode().getFirstChild();
        }
        catch (Exception e)
        {
            throw new XMLWriterException("Error while writing SensorML document", e);
        }
    }

}

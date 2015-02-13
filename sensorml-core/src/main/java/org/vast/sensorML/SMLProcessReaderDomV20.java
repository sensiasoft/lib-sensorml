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

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.dom.DOMSource;
import net.opengis.sensorml.v20.AbstractProcess;
import org.vast.xml.DOMHelper;
import org.vast.xml.IXMLReaderDOM;
import org.vast.xml.XMLImplFinder;
import org.vast.xml.XMLReaderException;
import org.w3c.dom.Element;


/**
 * <p>
 * DOM reader for all types of SensorML 2.0 processes.
 * This is implemented as a wrapper on top of the StAX bindings.
 * </p>
 *
 * @author Alex Robin <alex.robin@sensiasoftware.com>
 * @since Nov 14, 2014
 */
public class SMLProcessReaderDomV20 implements IXMLReaderDOM<AbstractProcess>
{
    SMLStaxBindings staxReader = new SMLStaxBindings();
    
    
    public SMLProcessReaderDomV20()
    {        
    }
    
    
    @Override
    public AbstractProcess read(DOMHelper dom, Element elt) throws XMLReaderException
    {
        try
        {
            DOMSource source = new DOMSource(elt);
            XMLStreamReader reader = XMLImplFinder.getStaxInputFactory().createXMLStreamReader(source);
            reader.nextTag();
            return staxReader.readAbstractProcess(reader);
        }
        catch (Exception e)
        {
            throw new XMLReaderException("Error while reading SensorML document", e);
        }
    }

}

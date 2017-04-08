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

import java.util.Arrays;
import java.util.Collection;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import net.opengis.gml.v32.AbstractFeature;
import net.opengis.gml.v32.impl.GMLFactory;
import net.opengis.sensorml.v20.AggregateProcess;
import net.opengis.sensorml.v20.PhysicalComponent;
import net.opengis.sensorml.v20.PhysicalSystem;
import net.opengis.sensorml.v20.SimpleProcess;
import net.opengis.sensorml.v20.bind.XMLStreamBindings;
import org.isotc211.v2005.gco.impl.GCOFactory;
import org.isotc211.v2005.gmd.impl.GMDFactory;
import org.vast.data.SWEFactory;
import org.vast.ogc.gml.IFeatureStaxBindings;


/**
 * <p>
 * Helper wrapping the auto-generated XML stream bindings
 * </p>
 *
 * @author Alex Robin
 * @since Sep 25, 2014
 */
public class SMLStaxBindings extends XMLStreamBindings implements IFeatureStaxBindings
{

    public SMLStaxBindings()
    {
        super(new SMLFactory(), new SWEFactory(), new GMLFactory(), new GMDFactory(), new GCOFactory());

        nsContext.registerNamespace("xlink", net.opengis.swe.v20.bind.XMLStreamBindings.XLINK_NS_URI);
        nsContext.registerNamespace("sml", net.opengis.sensorml.v20.bind.XMLStreamBindings.NS_URI);
        nsContext.registerNamespace("swe", net.opengis.swe.v20.bind.XMLStreamBindings.NS_URI);
        nsContext.registerNamespace("gml", net.opengis.gml.v32.bind.XMLStreamBindings.NS_URI);
        nsContext.registerNamespace("gco", org.isotc211.v2005.gco.bind.XMLStreamBindings.NS_URI);
        nsContext.registerNamespace("gmd", org.isotc211.v2005.gmd.bind.XMLStreamBindings.NS_URI);
    }


    @Override
    public Collection<QName> getSupportedFeatureTypes()
    {
        return Arrays.asList
        (
            SimpleProcess.DEFAULT_QNAME,
            AggregateProcess.DEFAULT_QNAME,
            PhysicalComponent.DEFAULT_QNAME,
            PhysicalSystem.DEFAULT_QNAME
        );
    }


    @Override
    public AbstractFeature readFeature(XMLStreamReader reader, QName qName) throws XMLStreamException
    {
        String eltName = qName.getLocalPart();
        
        if (eltName.equals(SimpleProcess.DEFAULT_QNAME.getLocalPart()))
            return this.readSimpleProcess(reader);
        
        else if (eltName.equals(AggregateProcess.DEFAULT_QNAME.getLocalPart()))
            return this.readAggregateProcess(reader);
        
        else if (eltName.equals(PhysicalComponent.DEFAULT_QNAME.getLocalPart()))
            return this.readPhysicalComponent(reader);
        
        else if (eltName.equals(PhysicalSystem.DEFAULT_QNAME.getLocalPart()))
            return this.readPhysicalSystem(reader);
        
        else
            throw new IllegalStateException("Unsupported feature type: " + qName);
    }


    @Override
    public void writeFeature(XMLStreamWriter writer, AbstractFeature bean) throws XMLStreamException
    {
        String eltName = bean.getQName().getLocalPart();
        ensurePrefix(writer, bean.getQName());
        
        if (eltName.equals(SimpleProcess.DEFAULT_QNAME.getLocalPart()))
            this.writeSimpleProcess(writer, (SimpleProcess)bean);
        
        else if (eltName.equals(AggregateProcess.DEFAULT_QNAME.getLocalPart()))
            this.writeAggregateProcess(writer, (AggregateProcess)bean);
        
        else if (eltName.equals(PhysicalComponent.DEFAULT_QNAME.getLocalPart()))
            this.writePhysicalComponent(writer, (PhysicalComponent)bean);
        
        else if (eltName.equals(PhysicalSystem.DEFAULT_QNAME.getLocalPart()))
            this.writePhysicalSystem(writer, (PhysicalSystem)bean);
        
        else
            throw new IllegalStateException("Unsupported feature type: " + bean.getQName());
    }

}

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

import net.opengis.gml.v32.impl.GMLFactory;
import net.opengis.sensorml.v20.bind.XMLStreamBindings;
import org.isotc211.v2005.gco.impl.GCOFactory;
import org.isotc211.v2005.gmd.impl.GMDFactory;
import org.vast.data.SWEFactory;


/**
 * <p>
 * Helper wrapping the auto-generated XML stream bindings
 * </p>
 *
 * @author Alex Robin <alex.robin@sensiasoftware.com>
 * @since Sep 25, 2014
 */
public class SMLStaxBindings extends XMLStreamBindings
{
        
    public SMLStaxBindings()
    {
        super(
            new SMLFactory(),
            new SWEFactory(),
            new GMLFactory(),
            new GMDFactory(),
            new GCOFactory()
        );
        
        nsContext.registerNamespace("xlink", net.opengis.swe.v20.bind.XMLStreamBindings.XLINK_NS_URI);
        nsContext.registerNamespace("sml", net.opengis.sensorml.v20.bind.XMLStreamBindings.NS_URI);
        nsContext.registerNamespace("swe", net.opengis.swe.v20.bind.XMLStreamBindings.NS_URI);
        nsContext.registerNamespace("gml", net.opengis.gml.v32.bind.XMLStreamBindings.NS_URI);
        nsContext.registerNamespace("gco", org.isotc211.v2005.gco.bind.XMLStreamBindings.NS_URI);
        nsContext.registerNamespace("gmd", org.isotc211.v2005.gmd.bind.XMLStreamBindings.NS_URI);
    }

}

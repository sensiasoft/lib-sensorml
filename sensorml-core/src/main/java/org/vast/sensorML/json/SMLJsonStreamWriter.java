/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2017 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.sensorML.json;

import java.io.OutputStream;
import org.vast.json.JsonStreamException;
import org.vast.sensorML.SMLStaxBindings;
import org.vast.swe.json.SWEJsonStreamWriter;


public class SMLJsonStreamWriter extends SWEJsonStreamWriter
{
        
    public SMLJsonStreamWriter(OutputStream os, String encoding) throws JsonStreamException
    {
        super(os, encoding);
        
        addSpecialNames(arrays, SMLStaxBindings.NS_URI,
                        "keywords", "identification", "classification",
                        "validTime", "securityConstraints", "legalConstraints",
                        "characteristics", "capabilities", "contacts",
                        "documentation", "history", "modes", "position",
                        "localReferenceFrame", "localTimeFrame", "timePosition",
                        "keyword", "input", "output", "parameter", "identifier",
                        "classifier", "characteristic", "capability",
                        "contact", "document", "event", "feature", "property",
                        "connection", "component", "axis", "algorithm",
                        "setValue", "setArrayValues", "setConstraint",
                        "setMode", "setStatus");
    }
    
    
    @Override
    public void writeAttribute(String localName, String value) throws JsonStreamException
    {
        if (localName.equals("codeSpace"))
            return;
        else
            super.writeAttribute(localName, value);
    }
}

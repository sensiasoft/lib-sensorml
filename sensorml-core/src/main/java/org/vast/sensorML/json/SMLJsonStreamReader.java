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

import java.io.InputStream;
import javax.xml.stream.XMLStreamException;
import org.vast.swe.json.SWEJsonStreamReader;


public class SMLJsonStreamReader extends SWEJsonStreamReader
{
        
    public SMLJsonStreamReader(InputStream is, String encoding) throws XMLStreamException
    {
        super(is, encoding);
        
        // XML attributes
        addSpecialNames(xmlAttNames, "ref");
    }
    
}

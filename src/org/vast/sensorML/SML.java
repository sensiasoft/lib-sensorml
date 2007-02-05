/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are subject to the Mozilla Public License Version
 1.1 (the "License"); you may not use this file except in compliance with
 the License. You may obtain a copy of the License at
 http://www.mozilla.org/MPL/MPL-1.1.html
 
 Software distributed under the License is distributed on an "AS IS" basis,
 WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 for the specific language governing rights and limitations under the License.
 
 The Original Code is the "SensorML DataProcessing Engine".
 
 The Initial Developer of the Original Code is the
 University of Alabama in Huntsville (UAH).
 Portions created by the Initial Developer are Copyright (C) 2006
 the Initial Developer. All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin <robin@nsstc.uah.edu>
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.sensorML;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import org.vast.sensorML.metadata.Metadata;
import org.vast.xml.DOMHelper;
import org.w3c.dom.Element;


/**
 * <p><b>Title:</b>
 * SML
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Implements the Root SensorML container and also provides
 * static helper methods to parse and write SensorML instance
 * documents, as well as creating SensorML objects.
 * </p>
 *
 * <p>Copyright (c) 2006</p>
 * @author Alexandre Robin
 * @date Dec 21, 2006
 * @version 1.0
 */
public class SML
{
    protected Metadata metadata;
    protected List<SMLObject> parts;
    
    
    public SML()
    {
        parts = new ArrayList<SMLObject>();
    }
    
    
    public Metadata getMetadata()
    {
        return metadata;
    }
    
    
    public void setMetadata(Metadata metadata)
    {
        this.metadata = metadata;
    }
    
    
    public List<SMLObject> getParts()
    {
        return parts;
    }
    
    
    public static SML read(DOMHelper dom, Element objElt, String version) throws SMLException
    {
        return new SML();
    }
    
    
    public static SML read(DOMHelper dom, Element objElt) throws SMLException
    {
        return read(dom, objElt, null);
    }
    
    
    public static Element write(DOMHelper dom, SMLObject obj, String version) throws SMLException
    {
        // TODO write content to DOM
        return null;
    }
    
    
    public static Element write(DOMHelper dom, SMLObject obj) throws SMLException
    {
        return write(dom, obj, null);
    }
    
    
    public static void serialize(OutputStream output, SMLObject obj) throws SMLException
    {
        
    }
}

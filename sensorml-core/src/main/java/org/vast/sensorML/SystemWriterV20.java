/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are subject to the Mozilla Public License Version
 1.1 (the "License"); you may not use this file except in compliance with
 the License. You may obtain a copy of the License at
 http://www.mozilla.org/MPL/MPL-1.1.html
 
 Software distributed under the License is distributed on an "AS IS" basis,
 WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 for the specific language governing rights and limitations under the License.
 
 The Original Code is the "SensorML DataProcessing Engine".
 
 The Initial Developer of the Original Code is the VAST team at the
 University of Alabama in Huntsville (UAH). <http://vast.uah.edu>
 Portions created by the Initial Developer are Copyright (C) 2007
 the Initial Developer. All Rights Reserved.

 Please Contact Alexandre Robin <alex.robin@sensiasoftware.com> or
 Mike Botts <mike.botts@uah.edu> for more information.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.sensorML;

import java.text.NumberFormat;
import org.vast.ogc.gml.GMLFeatureWriter;
import org.vast.ogc.gml.GMLTimeWriter;
import org.vast.sweCommon.SweComponentWriterV20;
import org.vast.xml.DOMHelper;
import org.vast.xml.IXMLWriterDOM;
import org.vast.xml.XMLWriterException;
import org.w3c.dom.Element;


/**
 * <p>
 * System Writer for SensorML version 2.0
 * </p>
 *
 * <p>Copyright (c) 2014</p>
 * @author Alexandre Robin
 * @since Feb 6, 2014
 */
public class SystemWriterV20 implements IXMLWriterDOM<SMLProcess>
{
    private static String GML_VERSION = "3.2";
    protected GMLTimeWriter timeWriter;
    protected GMLFeatureWriter featureWriter;
    protected SweComponentWriterV20 sweWriter;
    protected int currentId;
    protected NumberFormat idFormatter;
    
    
    public SystemWriterV20()
    {
        timeWriter = new GMLTimeWriter(GML_VERSION);
        featureWriter = new GMLFeatureWriter();
        featureWriter.setGmlVersion(GML_VERSION);
        sweWriter = new SweComponentWriterV20();
        
        currentId = 1;
        idFormatter = NumberFormat.getNumberInstance();
        idFormatter.setMinimumIntegerDigits(3);
        idFormatter.setGroupingUsed(false);
    }
    
    
    public Element write(DOMHelper dom, SMLProcess smlProcess) throws XMLWriterException
    {
        return null;
    }
}

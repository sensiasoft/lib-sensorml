/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are subject to the Mozilla Public License Version
 1.1 (the "License"); you may not use this file except in compliance with
 the License. You may obtain a copy of the License at
 http://www.mozilla.org/MPL/MPL-1.1.html
 
 Software distributed under the License is distributed on an "AS IS" basis,
 WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 for the specific language governing rights and limitations under the License.
 
 The Original Code is the "SensorML DataProcessing Engine".
 
 The Initial Developer of the Original Code is the VAST team at the University of Alabama in Huntsville (UAH). <http://vast.uah.edu> Portions created by the Initial Developer are Copyright (C) 2007 the Initial Developer. All Rights Reserved. Please Contact Mike Botts <mike.botts@uah.edu> for more information.
 
 Contributor(s): 
    Alexandre Robin <robin@nsstc.uah.edu>
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.sensorML;

import java.util.Hashtable;
import org.vast.xml.DOMHelper;


public class SMLReaderFactory
{
    public final static String defaultVersion = "_DEF_";    
    private final static Hashtable<String, Class<?>> metadataReaders =
        new Hashtable<String, Class<?>>();
    
    
    private Object getObject(Hashtable<String, Class<?>> classTable, String version)
    {
        Class<?> objType = classTable.get(version);
        Object objInstance = null;
        
        if (objType == null)
            objType = classTable.get(defaultVersion);
        
        if (objType == null)
            throw new IllegalStateException("Unsupported version " + version);
        
        try
        {
            objInstance = objType.newInstance();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return objInstance;
    }
    
    
    private String lookupVersion(DOMHelper dom)
    {
        return "0";
    }
    
    
    public MetadataReader getMetadataReader(String version)
    {
        MetadataReader reader = (MetadataReader)getObject(metadataReaders, version);
        return reader;
    }
    
    
    public MetadataReader getMetadataReader(DOMHelper dom)
    {
        String version = lookupVersion(dom);
        return getMetadataReader(version);
    }
}

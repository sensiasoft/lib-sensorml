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

 Please Contact Mike Botts <mike.botts@uah.edu> for more information.
 
 Contributor(s): 
    Alexandre Robin <robin@nsstc.uah.edu>
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.sensorML;

import org.w3c.dom.*;
import org.vast.xml.DOMHelper;
import org.vast.xml.DOMHelperException;
import org.vast.process.*;
import java.lang.reflect.*;
import java.util.Hashtable;


/**
 * <p>
 * This class loads the right process based on the method URI
 * provided in the SensorML description. If the URI is known locally,
 * the corresponding class is instantiated
 * </p>
 *
 * @author Alexandre Robin
 * @since Feb 15, 2006
 */
//TODO download new process implementation when not available locally.
public class ProcessLoader
{
    public static Hashtable<String,String> processMap = null;
    
    
    public ProcessLoader()
    {
        
    }    
        

    /**
     * Loads the DataProcess implementation corresponding to the given URI.
     * @param uri The URI of the method (i.e. the specific process to instantiate)
     * @return the newly created process object
     * @throws SMLProcessException
     */
    public synchronized IProcessExec loadProcess(String uri) throws SMLProcessException
    {
        // process map should be loaded at this point
        if (processMap == null)
            throw new SMLProcessException("No process map file loaded");
              
        // map URN to class name (full name including package)
        String className = processMap.get(uri);
        
        // if uri was not found, need to download...
        if (className == null)
        {
            // for now return a dummy process
            // TODO should actually resolve the URN and parse the ProcessMethod
            //return new Dummy_Process();
            throw new SMLProcessException("No executable process implementation found for URI " + uri);
        }
        
        // try to create process
        IProcessExec newProcess = null;
        
        try
        {
            // create corresponding DataProcess Object
            Class<?> readerClass = Class.forName(className);           
            Class<?>[] constArgTypes = {};
            Constructor<?> constMethod = readerClass.getConstructor(constArgTypes);
            Object[] constArgValues = {};
            newProcess = (IProcessExec)constMethod.newInstance(constArgValues);
        }
        catch (ClassNotFoundException e)
        {
            throw new SMLProcessException("Class '" + className + "' implementing process '" + uri  + "' was not found");
        }
        catch (NoSuchMethodException e)
        {
            throw new SMLProcessException("Invalid DataProcess", e);
        }
        catch (IllegalAccessException e)
        {
            throw new SMLProcessException("Invalid DataProcess", e);
        }
        catch (InstantiationException e)
        {
            throw new SMLProcessException("Invalid DataProcess", e);
        }
        catch (InvocationTargetException e)
        {
            if (e.getTargetException() instanceof RuntimeException)
                throw (RuntimeException)e.getTargetException();

            if (e.getTargetException() instanceof Error)
                throw (Error)e.getTargetException();

            e.getTargetException().printStackTrace();
            return null;
        }        
        
        return newProcess;
    }
    
    
    /**
     * Reloads the URI to Process Class map using the provided XML file
     * completely erases previous table.
     * @param libFileUrl Url to the file containing the mapping definitions
     * @throws SMLProcessException
     */
    public static synchronized void reloadMaps(String libFileUrl) throws SMLProcessException
    {
        // first clear old mappings
        if (processMap != null)
            processMap.clear();
        
        loadMaps(libFileUrl, true);
    }
    
    
    /**
     * Loads the URI to Process Class map using the provided XML file.
     * Existing entries are replaced only if the replace argument is true.
     * @param libFileUrl Url to the file containing the mapping definitions
     * @param replace If true, existing entries are replaced
     * @throws SMLProcessException
     */
    public static synchronized void loadMaps(String libFileUrl, boolean replace) throws SMLProcessException
    {
        try
        {
            // create process hashtable entries
            DOMHelper dom = new DOMHelper(libFileUrl, false);
            NodeList processElts = dom.getElements("Process");
            
            if (processMap == null)
                processMap = new Hashtable<String,String>(processElts.getLength());
            
            for (int i=0; i<processElts.getLength(); i++)
            {
                Element processElt = (Element)processElts.item(i);
                String className = dom.getElementValue(processElt, "class");
                
                NodeList uriElts = dom.getElements(processElt, "uri");         
                for (int j=0; j<uriElts.getLength(); j++)
                {
                    Element uriElt = (Element)uriElts.item(j);
                    String uri = dom.getElementValue(uriElt, "");                    
                    
                    if (replace || (processMap.get(uri) == null))
                        processMap.put(uri, className);
                }
            }
        }
        catch (DOMHelperException e)
        {
            throw new SMLProcessException("Error while reading Process Map File", e);
        }
    }
}
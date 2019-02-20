/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2017 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.sensorML;

import java.util.Arrays;
import org.isotc211.v2005.gmd.CIResponsibleParty;
import org.isotc211.v2005.gmd.impl.CIResponsiblePartyImpl;
import org.vast.process.IProcessExec;
import org.vast.swe.SWEHelper;
import org.vast.util.Asserts;
import net.opengis.OgcPropertyList;
import net.opengis.sensorml.v20.AbstractProcess;
import net.opengis.sensorml.v20.AggregateProcess;
import net.opengis.sensorml.v20.DataInterface;
import net.opengis.sensorml.v20.IdentifierList;
import net.opengis.sensorml.v20.PhysicalComponent;
import net.opengis.sensorml.v20.PhysicalSystem;
import net.opengis.sensorml.v20.Term;
import net.opengis.swe.v20.AbstractSWEIdentifiable;
import net.opengis.swe.v20.DataComponent;
import net.opengis.swe.v20.DataEncoding;
import net.opengis.swe.v20.DataStream;


/**
 * <p>
 * Helper class to generate sensor and process descriptions using the SensorML
 * model. It also defines standard properties that can easily be added to a
 * SensorML instance programmatically.
 * </p>
 *
 * @author Alex Robin
 * @since May 16, 2017
 */
public class SMLHelper extends SMLFactory
{
    public static final String LONG_NAME_DEF = SWEHelper.getPropertyUri("LongName");
    public static final String LONG_NAME_LABEL = "Long Name";
    public static final String SHORT_NAME_DEF = SWEHelper.getPropertyUri("ShortName");
    public static final String SHORT_NAME_LABEL = "Short Name";
    public static final String SERIAL_NUMBER_DEF = SWEHelper.getPropertyUri("SerialNumber");
    public static final String SERIAL_NUMBER_LABEL = "Serial Number";
    public static final String MODEL_NUMBER_DEF = SWEHelper.getPropertyUri("ModelNumber");
    public static final String MODEL_NUMBER_LABEL = "Model Number";
    public static final String MANUFACTURER_DEF = SWEHelper.getPropertyUri("Manufacturer");
    public static final String MANUFACTURER_LABEL = "Manufacturer Name";
    
    
    static class LinkTarget
    {
        public AbstractProcess process;
        public DataComponent component;
        
        public LinkTarget(AbstractProcess parent, DataComponent component)
        {
            this.process = parent;
            this.component = component;
        }
    }
    
    
    AbstractProcess process;
    
    
    public static SMLHelper createSimpleProcess(String uniqueID)
    {
        AbstractProcess process = new SMLFactory().newSimpleProcess();
        process.setUniqueIdentifier(uniqueID);
        return new SMLHelper(process);
    }
    
    
    public static SMLHelper createAggregateProcess(String uniqueID)
    {
        AggregateProcess process = new SMLFactory().newAggregateProcess();
        process.setUniqueIdentifier(uniqueID);
        return new SMLHelper(process);
    }
    
    
    public static SMLHelper createPhysicalComponent(String uniqueID)
    {
        PhysicalComponent process = new SMLFactory().newPhysicalComponent();
        process.setUniqueIdentifier(uniqueID);
        return new SMLHelper(process);
    }
    
    
    public static SMLHelper createPhysicalSystem(String uniqueID)
    {
        PhysicalSystem process = new SMLFactory().newPhysicalSystem();
        process.setUniqueIdentifier(uniqueID);
        return new SMLHelper(process);
    }
    
    
    public static SMLHelper edit(AbstractProcess process)
    {
        return new SMLHelper(process);
    }
    
    
    public SMLHelper(AbstractProcess process)
    {
        this.process = process;
    }
    
    
    public AbstractProcess getDescription()
    {
        return this.process;
    }
    
    
    public void addIdentifier(String label, String def, String value)
    {
        Asserts.checkNotNull(process);
        
        // ensure we have an identification section
        OgcPropertyList<IdentifierList> sectionList = process.getIdentificationList();
        IdentifierList idList;
        if (sectionList.isEmpty())
        {
            idList = newIdentifierList();
            sectionList.add(idList);
        }
        else
            idList = sectionList.get(0);
        
        Term term = newTerm();
        term.setDefinition(def);
        term.setLabel(label);
        term.setValue(value);
        idList.addIdentifier(term);
    }
    
    
    public void addLongName(String value)
    {
        addIdentifier(LONG_NAME_LABEL, LONG_NAME_DEF, value);
    }
    
    
    public void addShortName(String value)
    {
        addIdentifier(SHORT_NAME_LABEL, SHORT_NAME_DEF, value);
    }
    
    
    public void addSerialNumber(String value)
    {
        addIdentifier(SERIAL_NUMBER_LABEL, SERIAL_NUMBER_DEF, value);
    }
    
    
    public void addModelNumber(String value)
    {
        addIdentifier(MODEL_NUMBER_LABEL, MODEL_NUMBER_DEF, value);
    }
    
    
    public void addManufacturerName(String value)
    {
        addIdentifier(MANUFACTURER_LABEL, MANUFACTURER_DEF, value);
    }
        
    
    public CIResponsibleParty newResponsibleParty()
    {
        return new CIResponsiblePartyImpl();
    }
    
    
    /*
      Static helper methods for finding data components within the process hierarchy
    */    
    
    /**
     * Helper to get the input/output/parameter component description whether
     * the IO is wrapped in a DataInterface or DataStream or given as a raw
     * DataComponent.
     * @param ioDef
     * @return DataComponent
     */
    public static DataComponent getIOComponent(AbstractSWEIdentifiable ioDef)
    {
        if (ioDef instanceof DataInterface)
            return ((DataInterface)ioDef).getData().getElementType();
        else if (ioDef instanceof DataStream)
            return ((DataStream)ioDef).getElementType();
        else
            return (DataComponent)ioDef;
    }
    
    
    /**
     * Helper to get the input/output/parameter encoding description whether
     * the IO is wrapped in a DataInterface or DataStream.<br/>
     * This method returns null if the IO is described as a raw DataComponent.
     * @param ioDef
     * @return DataComponent
     */
    public static DataEncoding getIOEncoding(AbstractSWEIdentifiable ioDef)
    {
        if (ioDef instanceof DataInterface)
            return ((DataInterface)ioDef).getData().getEncoding();
        else if (ioDef instanceof DataStream)
            return ((DataStream)ioDef).getEncoding();
        else 
            return null;
    }
    
    
    /**
     * Finds a component in a process/component tree using a path.<br/>
     * Link path format is either '[components/{component_name}/...][inputs|outputs|parameters]/{name}/{name}/... 
     * @param parent process from which to start the search
     * @param linkString desired path as a String composed of process name, port type and data component names separated by {@value SWEHelper#PATH_SEPARATOR} characters
     * @return the process/component pair targeted by the given path or null if target process is not resolved
     * @throws SMLException if the specified path is incorrect
     */
    public static LinkTarget findComponentByPath(AbstractProcess parent, String linkString) throws SMLException
    {
        String processName = null;
        String portType = null;
        String portName = null;
        String[] dataPath = null;
        int portTypeIndex = 0;
        
        // parse link path
        String[] linkPath = linkString.split(SWEHelper.PATH_SEPARATOR);

        // if we're linking to a component IO
        // support only links to components for now 
        if ("components".equals(linkPath[0]))
        {
            if (!(parent instanceof AggregateProcess))
                throw new SMLException("Cannot refer to sub-components in a non-aggregate process");
            
            if (linkPath.length < 2)
                throw new SMLException("The name of a sub-component must be specified");
            
            processName = linkPath[1];
            portTypeIndex = 2;
        }

        // now extract port type, name and component path
        if (linkPath.length < portTypeIndex+1)
            throw new SMLException("At least a port type and name must be specified");
        portType = linkPath[portTypeIndex];
            
        if (linkPath.length > portTypeIndex+1)
            portName = linkPath[portTypeIndex+1];
        
        if (linkPath.length > portTypeIndex+2)
            dataPath = Arrays.copyOfRange(linkPath, portTypeIndex+2, linkPath.length);
        
        // find process
        AbstractProcess process;
        if (processName != null)
        {
            if (!((AggregateProcess)parent).getComponentList().hasProperty(processName))
                throw new SMLException(String.format("Cannot find process '%s'", processName));
            
            process = ((AggregateProcess)parent).getComponent(processName);
            
            // process can still be null if link to process was not resolved
            if (process == null)
                throw new SMLException(String.format("Process '%s' exists in the chain but hasn't been resolved yet", processName));
        }
        else
            process = parent;
        
        // find port
        DataComponent port;
        if ("inputs".equals(portType))
            port = process.getInputList().getComponent(portName);
        else if ("outputs".equals(portType))
            port = process.getOutputList().getComponent(portName);
        else if ("parameters".equals(portType))
            port = process.getParameterList().getComponent(portName);
        else
            throw new IllegalArgumentException(String.format("Invalid port type '%s'. Must be one of 'inputs', 'outputs' or 'parameters'", portType));

        // find sub component in port structure
        try
        {
            DataComponent component = (dataPath != null) ? SWEHelper.findComponentByPath(port, dataPath) : port;
            return new LinkTarget(process, component);
        }
        catch (Exception e)
        {
            StringBuilder path = new StringBuilder();
            for(String s : dataPath)
                path.append(s).append(SWEHelper.PATH_SEPARATOR);
            throw new SMLException(String.format("Cannot find data component '%s'", path.substring(0,path.length()-1)), e);
        }
    }
    
    
    /**
     * Constructs the path to link to the specified component
     * @param process the process where the data component resides
     * @param component the component to link to
     * @return
     */
    public static String getComponentPath(IProcessExec process, DataComponent component)
    {
        StringBuilder linkString = new StringBuilder();
        
        // find parent port
        DataComponent parentPort = SWEHelper.getRootComponent(component);
        
        // append port type
        if (process.getInputList().contains(parentPort))
            linkString.append("inputs/");
        else if (process.getOutputList().contains(parentPort))
            linkString.append("outputs/");
        else if (process.getParameterList().contains(parentPort))
            linkString.append("parameters/");
        else
            throw new IllegalArgumentException("Cannot find data component in the specified process");
        
        // append path within port structure
        linkString.append(SWEHelper.getComponentPath(component));
        return linkString.toString();
    }
}

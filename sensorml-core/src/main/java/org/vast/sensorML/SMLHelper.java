/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2015 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.sensorML;

import java.util.Arrays;
import org.isotc211.v2005.gmd.CIResponsibleParty;
import org.isotc211.v2005.gmd.impl.CIResponsiblePartyImpl;
import org.vast.cdm.common.CDMException;
import org.vast.process.SMLException;
import org.vast.swe.SWEHelper;
import org.vast.util.Asserts;
import net.opengis.OgcPropertyList;
import net.opengis.sensorml.v20.AbstractProcess;
import net.opengis.sensorml.v20.AggregateProcess;
import net.opengis.sensorml.v20.ConstraintSetting;
import net.opengis.sensorml.v20.DataInterface;
import net.opengis.sensorml.v20.IdentifierList;
import net.opengis.sensorml.v20.Settings;
import net.opengis.sensorml.v20.SimpleProcess;
import net.opengis.sensorml.v20.Term;
import net.opengis.sensorml.v20.ValueSetting;
import net.opengis.swe.v20.AbstractSWEIdentifiable;
import net.opengis.swe.v20.DataComponent;
import net.opengis.swe.v20.DataConstraint;
import net.opengis.swe.v20.DataEncoding;
import net.opengis.swe.v20.DataStream;
import net.opengis.swe.v20.HasConstraints;
import net.opengis.swe.v20.RangeComponent;
import net.opengis.swe.v20.ScalarComponent;


public class SMLHelper extends SMLFactory
{
    public final static String SERIAL_NUMBER_DEF = "http://sensorml.com/ont/swe/property/SerialNumber";
    public final static String SERIAL_NUMBER_LABEL = "Serial Number";
    
    AbstractProcess process;
    
    
    public SMLHelper()
    {        
    }
    
    
    public SMLHelper(AbstractProcess process)
    {
        this.process = process;
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
        idList.addIdentifier2(term);
    }    
    
    public void addSerialNumber(String value)
    {
        addIdentifier(SERIAL_NUMBER_LABEL, SERIAL_NUMBER_DEF, value);
    }
    
    
    
    // STATIC METHODS
    
    public CIResponsibleParty newResponsibleParty()
    {
        return new CIResponsiblePartyImpl();
    }
    
    
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
     * Finds a component in the process/component tree using a path 
     * @param parent process from which to start the search
     * @param path desired path as a String composed of process names, process sections and component names separated by {@value SWEHelper#PATH_SEPARATOR} characters
     * @return the component with the given path
     * @throws SMLException if the specified path is incorrect
     */
    public static DataComponent findComponentByPath(AbstractProcess parent, String path) throws SMLException
    {
        return findComponentByPath(parent, path.split(SWEHelper.PATH_SEPARATOR));
    }
    
    
    /**
     * Finds a component in a process/component tree using a path.<br/>
     * Link path format is either '[components/{component_name}/...][inputs|outputs|parameters]/{name}/{name}/... 
     * @param parent process from which to start the search
     * @param path desired path as a String array cotnaining a sequence of component names
     * @return the component with the given path
     * @throws SMLException if the specified path is incorrect
     */
    public static DataComponent findComponentByPath(AbstractProcess parent, String[] path) throws SMLException
    {
        int index = 0;
        
        // find sub-process
        String nextPart = path[index];
        while (nextPart.equals("components"))
        {
            if (!(parent instanceof AggregateProcess))
                throw new SMLException("Path " + Arrays.toString(path) + " references a sub-process from a non-aggregate process");
            
            String processName = path[++index];
            parent = ((AggregateProcess)parent).getComponent(processName);
            nextPart = path[++index];
        }
        
        try
        {
            if (nextPart.equals("inputs"))
            {
                String name = path[++index];
                path = Arrays.copyOfRange(path, ++index, path.length);
                DataComponent input = parent.getInputComponent(name);
                return SWEHelper.findComponentByPath(input, path);
            }
            else if (nextPart.equals("outputs"))
            {
                String name = path[++index];
                path = Arrays.copyOfRange(path, ++index, path.length);
                DataComponent output = parent.getOutputComponent(name);
                return SWEHelper.findComponentByPath(output, path);
            }
            else if (nextPart.equals("parameters"))
            {
                String name = path[++index];
                path = Arrays.copyOfRange(path, ++index, path.length);
                DataComponent param = parent.getParameterComponent(name);
                return SWEHelper.findComponentByPath(param, path);
            }
            
            throw new SMLException("Invalid path " + Arrays.toString(path));
        }
        catch (CDMException e)
        {
            throw new SMLException("Invalid path " + Arrays.toString(path), e);
        }
    }
    
    
    /**
     * Makes a process executable by instantiating and wrapping an implementation of IProcessExec.<br/>
     * The actual implementation is found using the method URI.
     * @param process
     * @throws SMLException
     */
    public static void makeProcessExecutable(AbstractProcessImpl process) throws SMLException
    {
        ProcessLoader processLoader = new ProcessLoader();
        makeProcessExecutable(process, processLoader);
    }
    
    
    public static void makeProcessExecutable(AbstractProcessImpl process, ProcessLoader processLoader) throws SMLException
    {
        if (process instanceof AggregateProcess)
        {
            // make child processes executable recursively
            for (AbstractProcess childProcess: ((AggregateProcess)process).getComponentList())
                makeProcessExecutable((AbstractProcessImpl)childProcess, processLoader);
            
            process.setExecutableImpl(new ExecutableChainImpl());
        }
        else if (process instanceof SimpleProcess)
        {
            String processUID = ((SimpleProcess) process).getMethodProperty().getHref();
            
            // if method is not set, try typeOf property
            if (processUID == null)
                processUID = ((SimpleProcess) process).getTypeOf().getHref();
            
            if (processUID == null)
                throw new SMLException("No executable method specified for process " + process.getId());
            
            /*String processUID = null;
            
            // try to read typeOf property
            if (process.isSetTypeOf())
                processUID = process.getTypeOf().getTitle();
            
            // otherwise use local identifier
            if (processUID == null)
                processUID = process.getUniqueIdentifier();
                
            if (processUID == null)
                throw new SMLException("No algorithm method found for process " + process.getId());*/
                
            ExecutableProcessImpl processExec = (ExecutableProcessImpl)processLoader.loadProcess(processUID);
            process.setExecutableImpl(processExec);
        }
    }
    
    
    /**
     * Generates a configured instance by copying I/Os definition from parent instance 
     * (if typeOf property is present), and applying configuration settings.
     * @param process process with typeOf and configuration settings
     * @param mergeMetadata if true, parent metadata will also be copied to the new instance
     * @return new process instance with configuration values set
     * @throws SMLException if configuration is invalid or cannot be applied
     */
    public static AbstractProcess getConfiguredInstance(AbstractProcess process, boolean mergeMetadata) throws SMLException
    {
        AbstractProcess baseProcess = null;
        
        // retrieve parent instance by resolving typeOf reference
        String typeOfUrl = process.getTypeOf().getHref();
        if (typeOfUrl != null)
        {
            // TODO load base process
            
            // merge metadata
            
            // apply config
            Settings settings = (Settings)process.getConfiguration();
            applyConfig(baseProcess, settings);
        }
                
        return baseProcess;
    }
    
    
    /**
     * Applies a configuration on a base process
     * @param process
     * @param settings
     * @throws SMLException 
     */
    public static void applyConfig(AbstractProcess process, Settings settings) throws SMLException
    {
        // value settings
        for (ValueSetting setting: settings.getSetValueList())
        {
            String refPath = setting.getRef();
            DataComponent comp = SMLHelper.findComponentByPath(process, refPath);
            
            if (comp instanceof ScalarComponent)
                comp.getData().setStringValue(setting.getValue());
            else if (comp instanceof RangeComponent)
            {
                String[] minMax = setting.getValue().split(" ");
                comp.getData().setStringValue(0, minMax[0]);
                comp.getData().setStringValue(1, minMax[1]);
            }
            else
                throw new SMLException("A value setting can only target a scalar or range component");
        }
        
        // array settings
        
        
        // constraint settings
        for (ConstraintSetting setting: settings.getSetConstraintList())
        {
            String refPath = setting.getRef();
            DataComponent comp = SMLHelper.findComponentByPath(process, refPath);
            
            if (comp instanceof HasConstraints)
            {
                try
                {
                    // TODO restrict constraint instead of replacing
                    ((HasConstraints<DataConstraint>)comp).setConstraint(setting.getValue());
                }
                catch (Exception e)
                {
                    throw new SMLException("Invalid constraint for component " + refPath, e);
                }
            }
            else
                throw new SMLException("A constraint setting can only target a non-boolean simple component");
        }
    }
}

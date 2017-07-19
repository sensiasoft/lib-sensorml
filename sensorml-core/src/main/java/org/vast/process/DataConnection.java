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
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.process;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import net.opengis.swe.v20.DataBlock;
import net.opengis.swe.v20.DataComponent;
import net.opengis.swe.v20.HasUom;
import org.vast.data.DataIterator;
import org.vast.data.DataValue;
import org.vast.data.ScalarIterator;
import org.vast.unit.Unit;
import org.vast.unit.UnitConversion;
import org.vast.unit.UnitConverter;


/**
 * <p>
 * Implementation of data connection for the processing engine.
 * This class is capable of automatically converting units if source and target
 * are not in the same unit (units have to be physically compatible)
 * </p>
 * 
 * @author Alex Robin
 * */
public class DataConnection
{
    private final static Logger LOGGER = Logger.getLogger(DataConnection.class.getName());
    
    protected IProcessExec sourceProcess;
    protected IProcessExec destinationProcess;
    protected DataComponent sourceComponent;
    protected DataComponent destinationComponent;
    protected transient boolean dataAvailable;
    protected transient boolean needsUnitConversion;
    protected transient List<ComponentConverter> componentConverters;
    protected Hashtable<String, Object> properties = null;
    
    
    protected class ComponentConverter
    {
        protected DataComponent src;
        protected DataComponent dest;
        protected UnitConverter converter;
        
        public ComponentConverter(DataComponent src, DataComponent dest, UnitConverter converter)
        {
            this.src = src;
            this.dest = dest;
            this.converter = converter;
        }
        
        public void convert()
        {
            double srcVal = src.getData().getDoubleValue();
            double newVal = converter.convert(srcVal);
            dest.getData().setDoubleValue(newVal);
        }
    }
    
    
    public DataConnection()
    {
        componentConverters = new ArrayList<ComponentConverter>();
    }
    
    
    protected void setupUnitConverters() throws SMLException
    {
        if (sourceComponent == null || destinationComponent == null)
            return;
        
        componentConverters.clear();
        ScalarIterator itSrc = new ScalarIterator(sourceComponent);
        ScalarIterator itDest = new ScalarIterator(destinationComponent);
        
        while (itSrc.hasNext())
        {
            DataComponent src = itSrc.next();
            DataComponent dest = itDest.next();
            UnitConverter conv = getUnitConverter(src, dest);
            
            if (conv != null)
            {
                componentConverters.add(new ComponentConverter(src, dest, conv));
                LOGGER.finer("Unit conversion setup from " + src.getName() + " to " + dest.getName());
            }
        }
    }
    
    
    protected UnitConverter getUnitConverter(DataComponent src, DataComponent dest) throws SMLException
    {
        if (src instanceof HasUom && dest instanceof HasUom)
        {
            Unit srcUom = ((HasUom)src).getUom().getValue();
            Unit destUom = ((HasUom)dest).getUom().getValue();
            
            if (srcUom == null || destUom == null || srcUom.isEquivalent(destUom))
                return null;
            
            return UnitConversion.getConverter(srcUom, destUom);
        }
        
        return null;
    }
    
	
    /**
     * Makes sure source and destination datablocks are the same
     * This is used in synchronous mode
     */
    public void transferDataBlocks()
    {
        DataBlock srcBlock = sourceComponent.getData();
        DataBlock destBlock = destinationComponent.hasData() ? destinationComponent.getData() : null;
        
        // apply unit conversion if needed
        if (!componentConverters.isEmpty())
        {
            if (destBlock == null)
                destinationComponent.assignNewDataBlock();
            
            Iterator<ComponentConverter> it = componentConverters.iterator();
            while (it.hasNext())
                it.next().convert();
        }
        
        // else just assign data block if needed
        else if (destBlock != srcBlock)
            destinationComponent.setData(srcBlock);
    }
    
    
    /**
     * Checks that source and destination components can be connected.
     * This validates compatibility of units and structure of aggregates.
     * @param src 
     * @param dest 
     * @return Warning message or null if no warning
     * @throws SMLException 
     */
    public static String check(DataComponent src, DataComponent dest) throws SMLException
    {
        if (src == null || dest == null)
            return null;
        
        //System.out.println("Checking connection of " + src.getName() + " to " + dest.getName());
        DataIterator it1 = new DataIterator(src);
        DataIterator it2 = new DataIterator(dest);
        StringBuffer msg = new StringBuffer();
        
        while (it1.hasNext())
        {
            if (!it2.hasNext())
                throw new SMLException("Structures of source and destination are not compatible");
            
            DataComponent c1 = it1.next();
            DataComponent c2 = it2.next();
            //System.out.println("Checking sub component " + c1.getName());
            
            // test that aggregates are the same
            if (!c1.getClass().isInstance(c2) || c1.getComponentCount() != c2.getComponentCount())
                throw new SMLException("Components '" + c1.getName() + "' and '" + c2.getName() + "' are not compatible");
            
            // check that scalars are compatible
            if (c1 instanceof HasUom && c2 instanceof HasUom)
            {
                Unit uom1 = ((HasUom)c1).getUom().getValue();
                Unit uom2 = ((HasUom)c2).getUom().getValue();
                
                if (uom1 != null && uom2 != null)
                {
                    if (!uom1.isCompatible(uom2))
                        throw new SMLException("Unit of component '" + c1.getName() + "' is not compatible with unit of '" + c2.getName() + "'");
                }
                
                if (((DataValue)c1).getDataType() != ((DataValue)c2).getDataType())
                    msg.append("Data types of source and destination component are different. Conversion may lead to loss of precision");
            }
        }
        
        if (msg.length() == 0)
            return null;
        else
            return msg.toString();
    }
    
    
    public String check() throws SMLException
    {
        return DataConnection.check(this.sourceComponent, this.destinationComponent);
    }


	public DataComponent getDestinationComponent()
	{
		return destinationComponent;
	}


	public void setDestinationComponent(DataComponent destinationComponent) throws SMLException
	{
		this.destinationComponent = destinationComponent;
		setupUnitConverters();
	}


	public IProcessExec getDestinationProcess()
	{
		return destinationProcess;
	}


	public void setDestinationProcess(IProcessExec destinationProcess)
	{
		this.destinationProcess = destinationProcess;
	}


	public DataComponent getSourceComponent()
	{
		return sourceComponent;
	}


	public void setSourceComponent(DataComponent sourceComponent) throws SMLException
	{
		this.sourceComponent = sourceComponent;
		setupUnitConverters();
	}


	public IProcessExec getSourceProcess()
	{
		return sourceProcess;
	}


	public void setSourceProcess(IProcessExec sourceProcess)
	{
		this.sourceProcess = sourceProcess;
	}


    public boolean isDataAvailable()
    {
        return dataAvailable;
    }


    public void setDataAvailable(boolean dataAvailable)
    {
        this.dataAvailable = dataAvailable;
    }
    
    
    public Object getProperty(String propName)
    {
        if (properties == null)
            return null;
        else
            return properties.get(propName);
    }


    public void setProperty(String propName, Object propValue)
    {
        if (properties == null)
            properties = new Hashtable<String, Object>(1, 1.0f);
        
        properties.put(propName, propValue);
    }
}

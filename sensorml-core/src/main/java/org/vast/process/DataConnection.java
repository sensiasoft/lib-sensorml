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
    Alexandre Robin
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.opengis.swe.v20.Boolean;
import net.opengis.swe.v20.Count;
import net.opengis.swe.v20.DataBlock;
import net.opengis.swe.v20.DataComponent;
import net.opengis.swe.v20.HasUom;
import net.opengis.swe.v20.Quantity;
import net.opengis.swe.v20.ScalarComponent;
import net.opengis.swe.v20.Text;
import net.opengis.swe.v20.Time;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vast.data.DataIterator;
import org.vast.data.DataValue;
import org.vast.data.ScalarIterator;
import org.vast.swe.SWEHelper;
import org.vast.unit.Unit;
import org.vast.unit.UnitConversion;
import org.vast.unit.UnitConverter;
import org.vast.util.Asserts;


/**
 * <p>
 * Implementation of data connection for the processing engine.<br/>
 * This class is capable of automatically converting units if source and target
 * are not in the same unit (units have to be physically compatible)<br/>
 * This class is used when running all processes of a processing chain
 * synchronously in a single thread
 * </p>
 * 
 * @author Alex Robin
 * */
public class DataConnection implements IDataConnection
{
    private final static Logger LOG = LoggerFactory.getLogger(DataConnection.class.getName());
    
    protected IProcessExec sourceProcess;
    protected IProcessExec destinationProcess;
    protected DataComponent sourceComponent;
    protected DataComponent destinationComponent;
    protected boolean dataAvailable;
    protected List<ComponentConverter> componentConverters;
    protected HashMap<String, Object> properties = null;
    
    
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
        componentConverters = new ArrayList<>();
    }
    
    
    protected void setupUnitConverters()
    {
        componentConverters.clear();
        if (sourceComponent == null || destinationComponent == null)
            return;
        
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
                LOG.debug("Unit conversion setup from " + src.getName() + " to " + dest.getName());
            }
        }
    }
    
    
    protected UnitConverter getUnitConverter(DataComponent src, DataComponent dest)
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
     * Checks that source and destination components can be connected.
     * This validates compatibility of units and structure of aggregates.
     * @param src 
     * @param dest 
     * @return Warning message or null if no warning
     * @throws ProcessException 
     */
    public static String validate(DataComponent src, DataComponent dest) throws ProcessException
    {
        if (src == null || dest == null)
            return null;
        
        DataIterator srcIt = new DataIterator(src);
        DataIterator destIt = new DataIterator(dest);
        StringBuilder msg = new StringBuilder();
        
        while (srcIt.hasNext())
        {
            DataComponent srcComp = srcIt.next();
            
            if (!destIt.hasNext())
                throw new ProcessException(String.format("Component '%s' is missing in destination", srcComp.getName()));
            
            DataComponent destComp = destIt.next();
            
            // check that components are of same size
            if (srcComp.getComponentCount() != destComp.getComponentCount())
                throw new ProcessException(String.format("Components '%s' and '%s' are not of the same size", srcComp.getName(), destComp.getName()));
            
            // check that scalar components are of compatible types
            if (srcComp instanceof ScalarComponent)
                validateScalar(srcComp, destComp, msg);
        }
        
        if (msg.length() == 0)
            return null;
        else
            return msg.toString();
    }
    
    
    protected static void validateScalar(DataComponent srcComp, DataComponent destComp, StringBuilder msg) throws ProcessException
    {
        if ((srcComp instanceof Boolean && !(destComp instanceof Boolean)) ||
            (srcComp instanceof Time && !(destComp instanceof Time || destComp instanceof Quantity)) ||
            (srcComp instanceof Count && !(destComp instanceof Count || destComp instanceof Quantity)) ||
            (srcComp instanceof Text && !(destComp instanceof Text)))
            throw new ProcessException(String.format("Components '%s' and '%s' are not of the same type", srcComp.getName(), destComp.getName()));
                
        // warning message if loss of precision
        if (((DataValue)srcComp).getDataType() != ((DataValue)destComp).getDataType())
            msg.append(String.format("Data types of components '%s' and '%s' are different. Conversion may lead to loss of precision", srcComp.getName(), destComp.getName()));
        
        // check that units are compatible
        if (srcComp instanceof HasUom && destComp instanceof HasUom)
        {
            Unit uom1 = ((HasUom)srcComp).getUom().getValue();
            Unit uom2 = ((HasUom)destComp).getUom().getValue();
            
            if (uom1 != null && uom2 != null && !uom1.isCompatible(uom2))
                throw new ProcessException(String.format("Units of components '%s' and '%s' are not compatible", srcComp.getName(), destComp.getName()));
        }
    }
    
    
    @Override
    public void publishData() throws InterruptedException
    {
        this.dataAvailable = true;
    }
    
	
    @Override
    public void transferData() throws InterruptedException
    {
        Asserts.checkState(sourceComponent.hasData(), "Source component has no data");
        
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
        
        this.dataAvailable = false;
    }


    @Override
    public void setSource(IProcessExec process, DataComponent component)
    {
        this.sourceProcess = process;
        this.sourceComponent = component;        
        setupUnitConverters();
    }


    @Override
    public IProcessExec getSourceProcess()
    {
        return sourceProcess;
    }
    
    
    @Override
    public DataComponent getSourcePort()
    {
        return SWEHelper.getRootComponent(sourceComponent);
    }


    @Override
    public DataComponent getSourceComponent()
    {
        return sourceComponent;
    }


    @Override
    public void setDestination(IProcessExec process, DataComponent component)
    {
        this.destinationProcess = process;
        this.destinationComponent = component;
        setupUnitConverters();
    }


	@Override
    public IProcessExec getDestinationProcess()
	{
		return destinationProcess;
	}
    
    
    @Override
    public DataComponent getDestinationPort()
    {
        return SWEHelper.getRootComponent(destinationComponent);
    }


    @Override
    public DataComponent getDestinationComponent()
    {
        return destinationComponent;
    }


    @Override
    public boolean isDataAvailable()
    {
        return dataAvailable;
    }


    @Override
    public void clear()
    {
        dataAvailable = false;        
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
            properties = new HashMap<>(1, 1.0f);
        
        properties.put(propName, propValue);
    }
}

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

package org.vast.process;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.vast.util.Asserts;
import net.opengis.swe.v20.DataBlock;


/**
 * <p>
 * A data queue is used to send data between processes.
 * It is synchronized to support processes in different
 * threads, even on different computers (grid computing).
 * Each element in the queue is called a data block.
 * The queue can also send status values and events.
 * </p>
 * 
 * @author Alex Robin
 * */
public class DataQueue extends DataConnection
{
	protected BlockingQueue<DataBlock> queue = new LinkedBlockingQueue<>();
	
	
	@Override
    public void publishData() throws InterruptedException
    {
	    Asserts.checkState(sourceComponent.hasData(), "Source component has no data");
	    DataBlock data = sourceComponent.getData();
        queue.put(data);
    }


    @Override
	public void transferData() throws InterruptedException
    {
        DataBlock srcBlock = queue.take();
        
        // apply unit conversion if needed
        // TODO add support for unit conversion in queues
        /*if (!componentConverters.isEmpty())
        {
            if (destBlock == null)
                destinationComponent.assignNewDataBlock();
            
            Iterator<ComponentConverter> it = componentConverters.iterator();
            while (it.hasNext())
                it.next().convert();
        }*/
        
        destinationComponent.setData(srcBlock);
    }
    
    
	@Override
    public synchronized void clear()
    {
        queue.clear();
    }


    @Override
    public boolean isDataAvailable()
    {
        return !queue.isEmpty();
    }
    
    
    @Override
	public String toString()
	{
		StringBuilder text = new StringBuilder("Queue size: " + queue.size() + "\n");

		for (DataBlock block: queue)
		{
			text.append(block.toString());
			text.append("\n");
		}

		return text.toString();
	}
}

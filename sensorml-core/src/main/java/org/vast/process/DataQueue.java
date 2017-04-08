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

import java.util.*;
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
 * TODO add support for unit conversion in queues
 * */
public class DataQueue extends DataConnection
{
	public int waitCount = 0;
	public boolean repeat = false;
	protected LinkedList<DataBlock> queue;
	protected int counter = 0;
    

	public DataQueue()
	{
		queue = new LinkedList<DataBlock>();
	}


	/**
	 * Adds DataBlock at the end of the list
	 * @param data
	 */
	public synchronized void add(DataBlock data)
	{
		queue.addLast(data);
		
		// notify process waiting in get method
		notifyAll();
	}


	/**
	 * Gets the first DataBlock from the queue
	 * @param timeout maximum time to wait until data is available, in ms
	 * @return DataBlock or null if nothing is available after time out
	 * @throws InterruptedException
	 */
	public synchronized DataBlock get(long timeout) throws InterruptedException
	{
		DataBlock dataBlock;
		
		if (counter == 0 && !repeat)
		{
			// wait until we have a dataBlock available
			while (queue.size() == 0)
				wait(timeout);

			if (queue.size() == 0)
				return null;

			dataBlock = queue.getFirst();
			queue.removeFirst();
		}
		else
		{
			// resend the same dataBlock
			dataBlock = destinationComponent.getData();
		}
		
		//System.err.println(counter + " " + dataBlock);
		
		// increment and reset wait loops counter
		counter++;
		if (counter > waitCount)
			counter = 0;

		return dataBlock;
	}


	/**
	 * Default version of previous method with timeout set to 0
	 * This will make us wait until a new value arrives on the queue
	 * @return DataBlock or null if nothing is available
	 * @throws InterruptedException
	 */
	public DataBlock get() throws InterruptedException
	{
		return this.get(0);
	}
	
	
	@Override
    public boolean isDataAvailable()
    {
        return (!queue.isEmpty());
    }


    /**
	 * Clear all data bocks in the queue
	 */
	public synchronized void clear()
	{
		this.queue.clear();
	}


	@Override
	public String toString()
	{
		ListIterator<DataBlock> it = queue.listIterator();
		StringBuffer text = new StringBuffer("Queue size: " + queue.size() + "\n");

		while (it.hasNext())
		{
			DataBlock block = it.next();
			text.append("Block: ");
			text.append(block.toString());
			text.append("\n");
		}

		return text.toString();
	}
}

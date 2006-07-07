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

package org.vast.sensorML.creator;

import org.apache.commons.jxpath.AbstractFactory;
import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.Pointer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class XpathDOMFactory extends AbstractFactory
{
	public XpathDOMFactory()
	{
	}


	public boolean createObject(JXPathContext context, Pointer pointer, Object parent, String name, int index)
	{
		if (parent instanceof Element)
		{
			return createChildElement(context, (Element) parent, name, index);
		}

		else if (parent instanceof Document)
		{
			return createRootElement(context, (Document) parent, name, index);
		}
		
		
		return false;
	}


	public boolean declareVariable(JXPathContext context, String name)
	{
		// TODO support variables
		return false;
	}


	private boolean createRootElement(JXPathContext context, Document parent, String name, int index)
	{
		if (index != 0)
		{
			//logger.error("exactly one root element allowed");
			return false;
		}

		// check for root element namespace
		int nsSeparator = name.indexOf(':');
		String nsUri = null;

		if (nsSeparator > -1)
		{
			// resolve namespace uri
			String nsPrefix = name.substring(0, nsSeparator);
			nsUri = context.getNamespaceURI(nsPrefix);

			if (nsUri == null)
			{
				//logger.error("namespace prefix unknown: " + nsPrefix);
				return false;
			}
		}

		// create root element and apply namespace declarations
		Element root = parent.createElementNS(nsUri, name);
		parent.appendChild(root);

		return true;
	}


	private boolean createChildElement(JXPathContext context, Element parent, String name, int index)
	{
		if (index > parent.getChildNodes().getLength())
		{
			//logger.error("child position too big: " + (index + 1));
			return false;
		}

		// check for child element namespace
		int nsSeparator = name.indexOf(':');
		String nsUri = null;

		if (nsSeparator > -1)
		{
			// resolve namespace uri
			String nsPrefix = name.substring(0, nsSeparator);
			nsUri = context.getNamespaceURI(nsPrefix);

			if (nsUri == null)
			{
				//logger.error("namespace prefix unknown: " + nsPrefix);
				return false;
			}
		}

		// create child element
		Element child = parent.getOwnerDocument().createElementNS(nsUri, name);
		parent.appendChild(child);

		return true;
	}
}

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
    Mike Botts <mike.botts@uah.edu>
    Amanda Wyatt <awyatt2nsstc.uah.edu>
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.sensorML;


import java.io.IOException;
import java.io.InputStream;
import org.vast.ogc.OGCRegistry;
import org.vast.ogc.gml.GMLUtils;
import org.vast.sweCommon.SWECommonUtils;
import org.vast.xml.DOMHelper;
import org.vast.xml.DOMHelperException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.apache.xml.serialize.*;


public class SMLSerializer extends XMLSerializer {
	
	protected DOMHelper dom;

	Element smlElt, systemElt;
	public enum smlObjectType {COMPONENT,SYSTEM, PROCESS_MODEL,PROCESS_CHAIN};
	
	public SMLSerializer(java.io.OutputStream output) {
		super(output,new OutputFormat("xml","UTF-8",true));
		//OutputFormat outFormat = new OutputFormat();
		//outFormat.setMethod("xml");
		//outFormat.setIndenting(true);
		//outFormat.setLineWidth(0);
		//this.setOutputFormat(outFormat);
	}
	
	public void setTemplate(DOMHelper dom){

		this.dom = dom;
		
		dom.addUserPrefix("gml", OGCRegistry.getNamespaceURI(GMLUtils.GML));
        //dom.addUserPrefix("om", OGCRegistry.getNamespaceURI(OGCRegistry.OM, "0.0"));
        dom.addUserPrefix("swe", OGCRegistry.getNamespaceURI(SWECommonUtils.SWE, "1.0"));
        dom.addUserPrefix("sml", OGCRegistry.getNamespaceURI(SMLUtils.SENSORML, "1.0"));
        dom.addUserPrefix("xlink", OGCRegistry.getNamespaceURI(OGCRegistry.XLINK, "1.0"));
        //NodeList elts = dom.getDocument().getElementsByTagNameNS("http://www.opengis.net/sml/1.0", "sml:System");
        NodeList elts = dom.getDocument().getElementsByTagNameNS("*", "System");
        
        if (elts!=null){
        	System.out.println("number of elements = " + elts.getLength());
        	smlElt = (Element)elts.item(0);
        }	
        else
        	System.out.println("SMLSerializer: did not find root: System");
        
        //TODO: Check SensorML type of member (e.g. System, Component, etc.) 
        //  and set the ENUM sensorMLObjectType; right now just use System
        //systemElt = dom.getElement(smlElt, "sml:member/sml:System");
        systemElt = smlElt;
	}
	
	/**
	 * Assign the template as an xml stream
	 * @param baseXML
	 */
	public void setTemplate(InputStream baseXML)
	{
		try
		{
			// load SensorML template document
            DOMHelper newDom = new DOMHelper(baseXML, false);
            setTemplate(newDom);
		}
		catch (DOMHelperException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Write xml to outputStream
	 */
	public void writeResponse() throws IOException
	{
		serialize(dom.getRootElement());
	}

	
	public Element getSystemElement() throws IOException
	{
		return systemElt;
	}
	
    /**
     * NOT USED IN SENSORML - EXAMPLE FROM SOSResponseSerializer
     * @param name
     * @param location
     */
 /*   public void setFoi(String name, Vector3d location)
    {
        try
        {
            Element foiElt = dom.addElement(obsElt, "om:featureOfInterest/swe:GeoReferenceableFeature");
            dom.setElementValue(foiElt, "gml:name", name);
            
            Element pointElt = dom.addElement(foiElt, "gml:location/gml:Point");
            dom.setAttributeValue(pointElt, "@srsName", "urn:ogc:def:crs:EPSG:6.1:4329");
            
            dom.setElementValue(pointElt, "gml:coordinates", location.x + " " + location.y + " " + location.z);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
*/

	// TODO: need to find a way to make sure these are put into the right location
	// of the SensorML instance
    /**
     * @param id
     */
    public void setID(String id){
        try
        {
           dom.setAttributeValue(systemElt, "gml:id", id);        
         }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
	
	// TODO: need to find a way to make sure these are put into the right location
	// of the SensorML instance
	/**
	 * @param name
	 */
	public void setName(String name){
        try
        {
           dom.setElementValue(systemElt, "gml:name", name);        
         }
        catch (Exception e)
        {
            e.printStackTrace();
        }
		
	}

	// TODO: need to find a way to make sure these are put into the right location
	// of the SensorML instance
	public void setDescription(String description){
        try
        {
            dom.setElementValue(systemElt, "gml:description", description);        
         }
        catch (Exception e)
        {
            e.printStackTrace();
        }
	}
	
	public void addIdentifier(String identifierName, String definitionUrl, String value){
	       try
	        {
	    	   // TODO If no identification element exist, this doesn't 
	    	   //     put it into the right place
	    	   // This adds the IdentifierList if it doesn't exist
	    	   // otherwise just finds and returns the element for reference
	    	    Element idListElt = dom.addElement(systemElt, "sml:identification/IdentifierList");
	
	    	    // append new element after the children
	    	    Element identElt = (Element)dom.appendChild((Node)idListElt, "sml:identifier");
	        	dom.setAttributeValue(identElt, "@name",identifierName);
	 	        dom.setAttributeValue(identElt, "sml:Term/@definition", definitionUrl); 
	 	        dom.setElementValue(identElt,"sml:Term/sml:value", value);
	         }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }	
	}
	
	public boolean setIdentifierValue(String identifierName, String value){
	       try
	        {
	            Element identElt = dom.getElement(systemElt, "sml:identification/sml:IdentifierList");
	            NodeList childs = dom.getChildElements(identElt);
	            
	            System.out.println("Number of children of IdentifierList = " + childs.getLength());
	            
	            for (int i=0; i<childs.getLength(); i++){	            	
	            	String nameVal = dom.getAttributeValue((Element)childs.item(i),"@name");
	            	
	            	System.out.println("identifer name = " + nameVal);
	            	
	            	// if exist, set the values in existing elements
	            	if (nameVal.equalsIgnoreCase(identifierName)){
	            		System.out.println("Found: " + nameVal);
	    	 	        dom.setElementValue((Element)childs.item(i),"sml:Term/sml:value", value);
	    	 	        return true;
	            	}
	            }
	         }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
        // if does not exist, return false
   		return false;
	}

	public void addClassifier(String classifierName, String definitionUrl, String value, String codespace){
	       try
	        {
	    	   // TODO If no classification element exist, this doesn't 
	    	   //     put it into the right place
	    	   // This adds the IdentifierList if it doesn't exist
	    	   // otherwise just finds and returns the element for reference
	            Element classifierElt = dom.addElement(systemElt, "sml:classification/sml:ClassifierList");

	            // append new element after the children
	    	    Element classElt = (Element)dom.appendChild((Node)classifierElt, "sml:classifier");          
	        	dom.setAttributeValue(classElt, "@name",classifierName);
	 	        dom.setAttributeValue(classElt, "sml:Term/@definition", definitionUrl); 
	 	        dom.setAttributeValue(classElt, "sml:Term/@codespace", codespace); 
			 	dom.setElementValue(classElt,"sml:Term/sml:value", value);
	         }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }					
	}

	
	public boolean setClassifierValue(String classifierName, String value){
	       try
	        {
	            Element classifierElt = dom.getElement(systemElt, "sml:classifier/sml:ClassifierList");
	            NodeList childs = dom.getChildElements(classifierElt);
	            
	            System.out.println("Number of children in ClassifierList = " + childs.getLength());
	            
	            for (int i=0; i<childs.getLength(); i++){	            	
	            	String nameVal = dom.getAttributeValue((Element)childs.item(i),"@name");
	            	
	            	System.out.println("classifier name = " + nameVal);
	            	
	            	// if exist, set the values in existing elements
	            	if (nameVal.equalsIgnoreCase(classifierName)){
	            		System.out.println("Found: " + nameVal);
	    	 	        dom.setElementValue((Element)childs.item(i),"sml:Term/sml:value", value);
	    	 	        return true;
	            	}
	            }
	         }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
       // if does not exist, return false
  		return false;
	}

	public void addContactValue(String role, String fullName, String organization, String street,
			String city, String state, String country, String zip, String phone, String email){

 	   // TODO If no classification element exist, this doesn't 
 	   //     put it into the right place
        Element contactElt = dom.addElement(systemElt, "sml:contact");
        if (!role.equalsIgnoreCase(""))
        	dom.setAttributeValue(contactElt, "@xlink:arcrole","urn:ogc:def:role:OGC::"+role);
        if (!fullName.equalsIgnoreCase(""))
        	dom.setElementValue(contactElt,"sml:ResponsibleParty/individualName", fullName);
        if (!organization.equalsIgnoreCase(""))
        	dom.setElementValue(contactElt,"sml:ResponsibleParty/organizationName", organization);
        Element contactInfoElt = dom.addElement(contactElt, "sml:ResponsibleParty/sml:contactInfo");
        if (!phone.equalsIgnoreCase(""))
        	dom.setElementValue(contactInfoElt,"phone/voice", phone);
        if (!street.equalsIgnoreCase(""))
        	dom.setElementValue(contactInfoElt,"address/deliveryPoint", street);
        if (!city.equalsIgnoreCase(""))
        	dom.setElementValue(contactInfoElt,"address/city", city);
        if (!state.equalsIgnoreCase(""))
        	dom.setElementValue(contactInfoElt,"address/administrativeArea", state);
        if (!zip.equalsIgnoreCase(""))
        	dom.setElementValue(contactInfoElt,"address/postalCode", zip);
        if (!country.equalsIgnoreCase(""))
        	dom.setElementValue(contactInfoElt,"address/country", country);
        if (!email.equalsIgnoreCase(""))
        	dom.setElementValue(contactInfoElt,"address/electronicMailAddress", email);
	}
	
	/*
	 public void setLocation(Vector3d location)
    {
        try
        {
            Element foiElt = dom.addElement(systemElt, "om:featureOfInterest/swe:GeoReferenceableFeature");
            dom.setElementValue(foiElt, "gml:name", name);
            
            Element pointElt = dom.addElement(foiElt, "gml:location/gml:Point");
            dom.setAttributeValue(pointElt, "@srsName", "urn:ogc:def:crs:EPSG:6.1:4329");
            
            dom.setElementValue(pointElt, "gml:coordinates", location.x + " " + location.y + " " + location.z);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }*/


}

	
	
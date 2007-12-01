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


import org.vast.ogc.OGCRegistry;
import org.vast.ows.SweResponseSerializer;
import org.vast.xml.DOMHelper;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.vast.math.Vector3d;

public class SMLSerializer extends SweResponseSerializer {
	
	Element smlElt, systemElt;
	public enum smlObjectType {COMPONENT,SYSTEM, PROCESS_MODEL,PROCESS_CHAIN};
	
	public SMLSerializer() {
		
	}
	
	public void setTemplate(DOMHelper dom){
        super.setTemplate(dom);        
        dom.addUserPrefix("gml", OGCRegistry.getNamespaceURI(OGCRegistry.GML));
        //dom.addUserPrefix("om", OGCRegistry.getNamespaceURI(OGCRegistry.OM, "0.0"));
        dom.addUserPrefix("swe", OGCRegistry.getNamespaceURI(OGCRegistry.SWE, "1.0.1"));
        dom.addUserPrefix("sml", OGCRegistry.getNamespaceURI(OGCRegistry.SML, "1.0.1"));
        dom.addUserPrefix("xlink", OGCRegistry.getNamespaceURI(OGCRegistry.XLINK, "1.0"));
        NodeList elts = dom.getDocument().getElementsByTagNameNS("http://www.opengis.net/sml/1.0.1", "SensorML");
        smlElt = (Element)elts.item(0);
        
        //TODO: Check SensorML type of member (e.g. System, Component, etc.) 
        //  and set the ENUM sensorMLObjectType; right now just use System
        systemElt = dom.getElement(smlElt, "sml:SensorML/sml:member/sml:System");

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
    public void setID(String id){
        try
        {
           dom.setAttributeValue(systemElt, "@gml:id", id);        
         }
        catch (Exception e)
        {
            e.printStackTrace();
        }
   	
    }
	
	// TODO: need to find a way to make sure these are put into the right location
	// of the SensorML instance
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
	
	// TODO: need to find a way to make sure these are put into the right location
	// of the SensorML instance
	public void addIdentifier(String identifierName, String definitionUrl, String value){
	       try
	        {
	            Element identElt = dom.addElement(systemElt, "sml:identification/IdentifierList/identfier");
	        	dom.setAttributeValue(identElt, "@name",identifierName);
	 	        dom.setAttributeValue(identElt, "sml:Term/@definition", definitionUrl); 
	 	        dom.setElementValue(identElt,"sml:Term/sml:value", value);
	         }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
		
	}
	
	public void setIdentifierValue(String identifierName, String value){
	       try
	        {
	            Element identElt = dom.getElement(systemElt, "sml:identification/IdentifierList");
	            NodeList childs = dom.getChildElements(identElt);
	            for (int i=0; i<childs.getLength(); i++){
	            	String nameVal = dom.getAttributeValue((Element)childs.item(i),"@name");
	            	// if exist, set the values in existing elements
	            	if (nameVal.equalsIgnoreCase(identifierName)){
	    	 	        dom.setElementValue(identElt,"sml:Term/sml:value", value);
	            	}
	            	// if does not exist, add elements and set the values as best as possible
	            	else addIdentifier(identifierName,"",value);
	            }
	         }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
	}

	// TODO: need to find a way to make sure these are put into the right location
	// of the SensorML instance
	public void addClassifier(String classifierName, String definitionUrl, String value, String codespace){
	       try
	        {
	            Element classElt = dom.addElement(systemElt, "sml:classification/ClassifierList/classifier");
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

	
	public void setClassifierValue(String classifierName, String value){
	       try
	        {
	            Element classElt = dom.getElement(systemElt, "sml:classification/ClassifierList");
	            NodeList childs = dom.getChildElements(classElt);
	            for (int i=0; i<childs.getLength(); i++){
	            	String nameVal = dom.getAttributeValue((Element)childs.item(i),"@name");
	            	// if exist, set the values in existing elements
	            	if (nameVal.equalsIgnoreCase(classifierName)){
	    	 	        dom.setElementValue(classElt,"sml:Term/sml:value", value);
	            	}
	            	// if does not exist, add elements and set the values as best as possible
	            	else addClassifier(classifierName,"",value,"");
	            }
	         }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
	}

	public void addContactValue(String role, String fullName, String organization, String street,
			String city, String state, String country, String zip, String phone, String email){

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

	
	
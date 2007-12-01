package org.vast.sensorML;

import org.vast.math.Vector3d;
import org.vast.ogc.OGCRegistry;
import org.vast.ows.SweResponseSerializer;
import org.vast.xml.DOMHelper;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class SMLSerializer extends SweResponseSerializer {
	
	Element smlElt;
	
	public SMLSerializer() {
		
	}
	
	public void setTemplate(DOMHelper dom){
        super.setTemplate(dom);        
        dom.addUserPrefix("gml", OGCRegistry.getNamespaceURI(OGCRegistry.GML));
        dom.addUserPrefix("om", OGCRegistry.getNamespaceURI(OGCRegistry.OM, "0.0"));
        dom.addUserPrefix("swe", OGCRegistry.getNamespaceURI(OGCRegistry.SWE, "1.0"));
        NodeList elts = dom.getDocument().getElementsByTagNameNS("http://www.opengis.net/om/0.0", "Observation");
        smlElt = (Element)elts.item(0);

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

    
    public void setID(String id){
        try
        {
           Element systemElt = dom.getElement(smlElt, "sml:SensorML/sml:member/sml:System");
           dom.setAttributeValue(systemElt, "@gml:id", id);        
         }
        catch (Exception e)
        {
            e.printStackTrace();
        }
   	
    }
	
	public void setName(String name){
        try
        {
           Element systemElt = dom.getElement(smlElt, "sml:SensorML/sml:member/sml:System");
           dom.setElementValue(systemElt, "gml:name", name);        
         }
        catch (Exception e)
        {
            e.printStackTrace();
        }
		
	}
	
	public void setDescription(String description){
        try
        {
            Element systemElt = dom.getElement(smlElt, "sml:SensorML/sml:member/sml:System");
            dom.setElementValue(smlElt, "gml:description", description);        
         }
        catch (Exception e)
        {
            e.printStackTrace();
        }
		
		
	}
	
	// TODO: need to find a way to make sure these are put into the right location
	// of the SensorML instance
	public void setIdentifier(String identifierName, String url, String value){
	       try
	        {
	            Element systemElt = dom.getElement(smlElt, "sml:SensorML/sml:member/sml:System");
	            Element identElt = dom.getElement(systemElt, "sml:identification/IdentifierList/identfier");
	        	dom.setAttributeValue(identElt, "@name",identifierName);
	 	        dom.setAttributeValue(identElt, "sml:Term/@definition", url); 
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
	            Element systemElt = dom.getElement(smlElt, "sml:SensorML/sml:member/sml:System");
	            Element identElt = dom.getElement(systemElt, "sml:identification/IdentifierList");
	            NodeList childs = dom.getChildElements(identElt);
	            for (int i=0; i<childs.getLength(); i++){
	            	String nameVal = dom.getAttributeValue((Element)childs.item(i),"@name");
	            	if (nameVal.equalsIgnoreCase(identifierName)){
	    	 	        dom.setElementValue(identElt,"sml:Term/sml:value", value);
	            		return true;
	            	}
	            }
	         }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
            return false;		
	}
	
	public void setClassifier(String classifierName, String url, String value, String codespace){
	       try
	        {
	            Element systemElt = dom.getElement(smlElt, "sml:SensorML/sml:member/sml:System");
	            Element classElt = dom.getElement(systemElt, "sml:classification/ClassifierList/classifier");
	        	dom.setAttributeValue(classElt, "@name",classifierName);
	 	        dom.setAttributeValue(classElt, "sml:Term/@definition", url); 
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
	            Element systemElt = dom.getElement(smlElt, "sml:SensorML/sml:member/sml:System");
	            Element classElt = dom.getElement(systemElt, "sml:classification/ClassifierList");
	            NodeList childs = dom.getChildElements(classElt);
	            for (int i=0; i<childs.getLength(); i++){
	            	String nameVal = dom.getAttributeValue((Element)childs.item(i),"@name");
	            	if (nameVal.equalsIgnoreCase(classifierName)){
	    	 	        dom.setElementValue(classElt,"sml:Term/sml:value", value);
	            		return true;
	            	}
	            }
	         }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
         return false;		
	}

	public void setContactValue(String firstName, String lastName, String street,
			String city, String state, String zip, String phone, String email){
		
	}

}

	
	
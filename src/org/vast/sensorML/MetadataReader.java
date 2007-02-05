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

package org.vast.sensorML;

import java.util.List;

import org.vast.cdm.common.DataComponent;
import org.vast.sensorML.metadata.Contact;
import org.vast.sensorML.metadata.DocumentRef;
import org.vast.sensorML.metadata.Metadata;
import org.vast.sensorML.metadata.Person;
import org.vast.sensorML.metadata.ResponsibleParty;
import org.vast.sensorML.metadata.Term;
import org.vast.util.DateTime;
import org.vast.xml.DOMHelper;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public interface MetadataReader
{

    /**
     * Reads the whole metadata group from the DOM
     * @param dom
     * @param objectElement The object carrying the metadata
     * @return the Metadata object filled with info from the DOM
     * @throws SMLException
     */
    public Metadata readMetadata(DOMHelper dom, Element objectElement) throws SMLException;


    /**
     * Reads a list of properties containing Term elements
     * @param termPropertyElts the NodeList of all properties to read
     * @return
     */
    public List<Term> readTermList(DOMHelper dom, NodeList termPropertyElts) throws SMLException;


    /**
     * Reads a list of properties containing Document elements
     * @param docPropertyElts
     * @return
     */
    public List<DocumentRef> readDocumentList(DOMHelper dom, NodeList docPropertyElts) throws SMLException;


    /**
     * Reads a list of properties containing Contact elements
     * @param contactPropertyElts
     * @return
     */
    public List<Contact> readContactList(DOMHelper dom, NodeList contactPropertyElts) throws SMLException;


    /**
     * Reads a list of properties containing elements of the AnyData group
     * @param propertyElts the NodeList of all properties to read
     * @return
     */
    public List<DataComponent> readPropertyList(DOMHelper dom, NodeList propertyElts) throws SMLException;


    /**
     * Reads a Term element
     * @param termElt
     * @return
     */
    public Term readTerm(DOMHelper dom, Element termElt) throws SMLException;


    /**
     * Reads a Document element
     * @param documentElement
     * @return
     */
    public DocumentRef readDocument(DOMHelper dom, Element documentElement) throws SMLException;


    /**
     * Reads a Contact (i.e. Person or ResponsibleParty element)
     * @param contactElement
     * @return
     */
    public Contact readContact(DOMHelper dom, Element contactElement) throws SMLException;


    /**
     * Reads a Person element
     * @param personElement
     * @return
     * @throws SMLException
     */
    public Person readPerson(DOMHelper dom, Element personElement) throws SMLException;


    /**
     * Reads a ResponsibleParty element
     * @param partyElement
     * @return
     * @throws SMLException
     */
    public ResponsibleParty readResponsibleParty(DOMHelper dom, Element partyElement) throws SMLException;


    /**
     * Reads an element containing an ISO date value
     * @param dateElement
     * @return
     */
    public DateTime readDate(DOMHelper dom, Element dateElement) throws SMLException;

}
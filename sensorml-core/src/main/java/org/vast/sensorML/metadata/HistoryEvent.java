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

package org.vast.sensorML.metadata;

import java.util.List;

import org.vast.util.Contact;
import org.vast.util.DateTime;


/**
 * <p>
 * SensorML History Event
 * </p>
 *
 * <p>Copyright (c) 2005</p>
 * @author Alexandre Robin
 * @since Feb 16, 2006
 * @version 1.0
 */
public class HistoryEvent
{
    protected String description;
    protected List<Term> identifiers;
    protected List<Term> classifiers;
    protected List<DocumentRef> documents;
    protected List<Contact> contacts;
    protected DateTime date;


    public List<Term> getClassifiers()
    {
        return classifiers;
    }


    public void setClassifiers(List<Term> classifiers)
    {
        this.classifiers = classifiers;
    }


    public List<Contact> getContacts()
    {
        return contacts;
    }


    public void setContacts(List<Contact> contacts)
    {
        this.contacts = contacts;
    }


    public String getDescription()
    {
        return description;
    }


    public void setDescription(String description)
    {
        this.description = description;
    }


    public List<DocumentRef> getDocuments()
    {
        return documents;
    }


    public void setDocuments(List<DocumentRef> documents)
    {
        this.documents = documents;
    }


    public List<Term> getIdentifiers()
    {
        return identifiers;
    }


    public void setIdentifiers(List<Term> identifiers)
    {
        this.identifiers = identifiers;
    }


    public DateTime getDate()
    {
        return date;
    }


    public void setDate(DateTime date)
    {
        this.date = date;
    }
}

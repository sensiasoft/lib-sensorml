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

package org.vast.sensorML.metadata;

/**
 * <p><b>Title:</b><br/>
 * Security Constraint
 * </p>
 *
 * <p><b>Description:</b><br/>
 * SensorML Security Constraint
 * </p>
 *
 * <p>Copyright (c) 2005</p>
 * @author Alexandre Robin
 * @date Feb 16, 2006
 * @version 1.0
 */
public abstract class SecurityConstraint
{
    protected String classification;
    protected String ownerProducer;
    protected String classificationReason;
    protected String classifiedBy;
    protected String declassDate;
    protected String SCIcontrols;
    protected String disseminationControls;
    protected String FGIsourceOpen;
    protected String releasableTo;


    public String getClassification()
    {
        return classification;
    }


    public void setClassification(String classification)
    {
        this.classification = classification;
    }


    public String getClassificationReason()
    {
        return classificationReason;
    }


    public void setClassificationReason(String classificationReason)
    {
        this.classificationReason = classificationReason;
    }


    public String getClassifiedBy()
    {
        return classifiedBy;
    }


    public void setClassifiedBy(String classifiedBy)
    {
        this.classifiedBy = classifiedBy;
    }


    public String getDeclassDate()
    {
        return declassDate;
    }


    public void setDeclassDate(String declassDate)
    {
        this.declassDate = declassDate;
    }


    public String getDisseminationControls()
    {
        return disseminationControls;
    }


    public void setDisseminationControls(String disseminationControls)
    {
        this.disseminationControls = disseminationControls;
    }


    public String getFGIsourceOpen()
    {
        return FGIsourceOpen;
    }


    public void setFGIsourceOpen(String isourceOpen)
    {
        FGIsourceOpen = isourceOpen;
    }


    public String getOwnerProducer()
    {
        return ownerProducer;
    }


    public void setOwnerProducer(String ownerProducer)
    {
        this.ownerProducer = ownerProducer;
    }


    public String getReleasableTo()
    {
        return releasableTo;
    }


    public void setReleasableTo(String releasableTo)
    {
        this.releasableTo = releasableTo;
    }


    public String getSCIcontrols()
    {
        return SCIcontrols;
    }


    public void setSCIcontrols(String icontrols)
    {
        SCIcontrols = icontrols;
    }
}

/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2015 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package net.opengis.sensorml.v20;

import java.io.Serializable;
import java.util.List;
import net.opengis.OgcPropertyList;
import net.opengis.gml.v32.Point;
import net.opengis.gml.v32.Reference;
import net.opengis.swe.v20.DataArray;
import net.opengis.swe.v20.DataRecord;
import net.opengis.swe.v20.Text;
import net.opengis.swe.v20.Time;
import net.opengis.swe.v20.Vector;


/**
 * POJO class for XML type AbstractPhysicalProcessType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
@SuppressWarnings("javadoc")
public interface AbstractPhysicalProcess extends AbstractProcess
{
    
    
    /**
     * Gets the attachedTo property
     */
    public Reference getAttachedTo();
    
    
    /**
     * Checks if attachedTo is set
     */
    public boolean isSetAttachedTo();
    
    
    /**
     * Sets the attachedTo property
     */
    public void setAttachedTo(Reference attachedTo);
    
    
    /**
     * Gets the list of localReferenceFrame properties
     */
    public List<SpatialFrame> getLocalReferenceFrameList();
    
    
    /**
     * Returns number of localReferenceFrame properties
     */
    public int getNumLocalReferenceFrames();
    
    
    /**
     * Adds a new localReferenceFrame property
     */
    public void addLocalReferenceFrame(SpatialFrame localReferenceFrame);
    
    
    /**
     * Gets the list of localTimeFrame properties
     */
    public List<TemporalFrame> getLocalTimeFrameList();
    
    
    /**
     * Returns number of localTimeFrame properties
     */
    public int getNumLocalTimeFrames();
    
    
    /**
     * Adds a new localTimeFrame property
     */
    public void addLocalTimeFrame(TemporalFrame localTimeFrame);
    
    
    /**
     * Gets the list of position properties
     */
    public OgcPropertyList<Serializable> getPositionList();
    
    
    /**
     * Returns number of position properties
     */
    public int getNumPositions();
    
    
    /**
     * Adds a new positionAsText property
     */
    public void addPositionAsText(Text position);
    
    
    /**
     * Adds a new positionAsPoint property
     */
    public void addPositionAsPoint(Point position);
    
    
    /**
     * Adds a new positionAsVector property
     */
    public void addPositionAsVector(Vector position);
    
    
    /**
     * Adds a new positionAsDataRecord property
     */
    public void addPositionAsDataRecord(DataRecord position);
    
    
    /**
     * Adds a new positionAsDataArray1 property
     */
    public void addPositionAsDataArray1(DataArray position);
    
    
    /**
     * Adds a new positionAsAbstractProcess property
     */
    public void addPositionAsAbstractProcess(AbstractProcess position);
    
    
    /**
     * Gets the list of timePosition properties
     */
    public OgcPropertyList<Time> getTimePositionList();
    
    
    /**
     * Returns number of timePosition properties
     */
    public int getNumTimePositions();
    
    
    /**
     * Adds a new timePosition property
     */
    public void addTimePosition(Time timePosition);
}

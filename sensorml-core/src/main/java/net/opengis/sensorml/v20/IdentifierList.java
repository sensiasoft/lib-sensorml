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

import java.util.List;


/**
 * POJO class for XML type IdentifierListType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
@SuppressWarnings("javadoc")
public interface IdentifierList extends AbstractMetadataList
{
    
    
    /**
     * Gets the list of identifier2 properties
     */
    public List<Term> getIdentifier2List();
    
    
    /**
     * Returns number of identifier2 properties
     */
    public int getNumIdentifier2s();
    
    
    /**
     * Adds a new identifier2 property
     */
    public void addIdentifier2(Term identifier2);
}

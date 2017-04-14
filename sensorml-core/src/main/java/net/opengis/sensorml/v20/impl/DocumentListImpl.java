/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2015 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package net.opengis.sensorml.v20.impl;

import net.opengis.OgcPropertyList;
import net.opengis.sensorml.v20.DocumentList;
import org.isotc211.v2005.gmd.CIOnlineResource;


/**
 * POJO class for XML type DocumentListType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public class DocumentListImpl extends AbstractMetadataListImpl implements DocumentList
{
    private static final long serialVersionUID = -6480644982926069220L;
    protected OgcPropertyList<CIOnlineResource> documentList = new OgcPropertyList<CIOnlineResource>();
    
    
    public DocumentListImpl()
    {
    }
    
    
    /**
     * Gets the list of document properties
     */
    @Override
    public OgcPropertyList<CIOnlineResource> getDocumentList()
    {
        return documentList;
    }
    
    
    /**
     * Returns number of document properties
     */
    @Override
    public int getNumDocuments()
    {
        if (documentList == null)
            return 0;
        return documentList.size();
    }
    
    
    /**
     * Adds a new document property
     */
    @Override
    public void addDocument(CIOnlineResource document)
    {
        this.documentList.add(document);
    }
}

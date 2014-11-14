package net.opengis.sensorml.v20;

import net.opengis.OgcPropertyList;
import org.isotc211.v2005.gmd.CIOnlineResource;


/**
 * POJO class for XML type DocumentListType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public interface DocumentList extends AbstractMetadataList
{
    
    
    /**
     * Gets the list of document properties
     */
    public OgcPropertyList<CIOnlineResource> getDocumentList();
    
    
    /**
     * Returns number of document properties
     */
    public int getNumDocuments();
    
    
    /**
     * Adds a new document property
     */
    public void addDocument(CIOnlineResource document);
}

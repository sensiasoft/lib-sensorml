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
    static final long serialVersionUID = 1L;
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

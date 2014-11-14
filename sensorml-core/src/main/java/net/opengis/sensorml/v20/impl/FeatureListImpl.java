package net.opengis.sensorml.v20.impl;

import net.opengis.OgcPropertyList;
import net.opengis.gml.v32.AbstractFeature;
import net.opengis.sensorml.v20.FeatureList;


/**
 * POJO class for XML type FeatureListType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public class FeatureListImpl extends AbstractMetadataListImpl implements FeatureList
{
    static final long serialVersionUID = 1L;
    protected OgcPropertyList<AbstractFeature> featureList = new OgcPropertyList<AbstractFeature>();
    
    
    public FeatureListImpl()
    {
    }
    
    
    /**
     * Gets the list of feature properties
     */
    @Override
    public OgcPropertyList<AbstractFeature> getFeatureList()
    {
        return featureList;
    }
    
    
    /**
     * Returns number of feature properties
     */
    @Override
    public int getNumFeatures()
    {
        if (featureList == null)
            return 0;
        return featureList.size();
    }
    
    
    /**
     * Adds a new feature property
     */
    @Override
    public void addFeature(AbstractFeature feature)
    {
        this.featureList.add(feature);
    }
}

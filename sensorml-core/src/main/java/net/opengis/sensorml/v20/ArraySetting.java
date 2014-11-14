/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2014 Sensia Software LLC.
 All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
******************************* END LICENSE BLOCK ***************************/

package net.opengis.sensorml.v20;

import net.opengis.swe.v20.DataArray;
import net.opengis.swe.v20.DataEncoding;
import net.opengis.swe.v20.EncodedValues;


/**
 * POJO class for XML type ArraySettingPropertyType(@http://www.opengis.net/sensorml/2.0).
 *
 */
public interface ArraySetting extends ConfigSetting<DataArray>
{
    
    /**
     * Gets the encoding property
     */
    public DataEncoding getEncoding();
    
    
    /**
     * Sets the encoding property
     */
    public void setEncoding(DataEncoding encoding);
    
    
    /**
     * Gets the inline value
     */
    public EncodedValues getValue();
    
    
    /**
     * Sets the inline value
     */
    public void setValue(EncodedValues value);
}

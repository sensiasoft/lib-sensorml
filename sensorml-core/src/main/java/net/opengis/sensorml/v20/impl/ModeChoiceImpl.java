package net.opengis.sensorml.v20.impl;

import net.opengis.OgcPropertyList;
import net.opengis.sensorml.v20.Mode;
import net.opengis.sensorml.v20.ModeChoice;


/**
 * POJO class for XML type ModeChoiceType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public class ModeChoiceImpl extends AbstractModesImpl implements ModeChoice
{
    static final long serialVersionUID = 1L;
    protected OgcPropertyList<Mode> modeList = new OgcPropertyList<Mode>();
    
    
    public ModeChoiceImpl()
    {
    }
    
    
    /**
     * Gets the list of mode properties
     */
    @Override
    public OgcPropertyList<Mode> getModeList()
    {
        return modeList;
    }
    
    
    /**
     * Returns number of mode properties
     */
    @Override
    public int getNumModes()
    {
        if (modeList == null)
            return 0;
        return modeList.size();
    }
    
    
    /**
     * Adds a new mode property
     */
    @Override
    public void addMode(Mode mode)
    {
        this.modeList.add(mode);
    }
}

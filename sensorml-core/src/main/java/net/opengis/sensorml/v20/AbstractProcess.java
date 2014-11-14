package net.opengis.sensorml.v20;

import java.util.List;
import net.opengis.gml.v32.Reference;


/**
 * POJO class for XML type AbstractProcessType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public interface AbstractProcess extends DescribedObject
{
    
    
    /**
     * Gets the typeOf property
     */
    public Reference getTypeOf();
    
    
    /**
     * Checks if typeOf is set
     */
    public boolean isSetTypeOf();
    
    
    /**
     * Sets the typeOf property
     */
    public void setTypeOf(Reference typeOf);
    
    
    /**
     * Gets the configuration property
     */
    public AbstractSettings getConfiguration();
    
    
    /**
     * Checks if configuration is set
     */
    public boolean isSetConfiguration();
    
    
    /**
     * Sets the configuration property
     */
    public void setConfiguration(AbstractSettings configuration);
    
    
    /**
     * Gets the featuresOfInterest property
     */
    public FeatureList getFeaturesOfInterest();
    
    
    /**
     * Checks if featuresOfInterest is set
     */
    public boolean isSetFeaturesOfInterest();
    
    
    /**
     * Sets the featuresOfInterest property
     */
    public void setFeaturesOfInterest(FeatureList featuresOfInterest);
    
    
    /**
     * Gets the inputs property
     */
    public InputList getInputs();
    
    
    /**
     * Checks if inputs is set
     */
    public boolean isSetInputs();
    
    
    /**
     * Sets the inputs property
     */
    public void setInputs(InputList inputs);
    
    
    /**
     * Gets the outputs property
     */
    public OutputList getOutputs();
    
    
    /**
     * Checks if outputs is set
     */
    public boolean isSetOutputs();
    
    
    /**
     * Sets the outputs property
     */
    public void setOutputs(OutputList outputs);
    
    
    /**
     * Gets the parameters property
     */
    public ParameterList getParameters();
    
    
    /**
     * Checks if parameters is set
     */
    public boolean isSetParameters();
    
    
    /**
     * Sets the parameters property
     */
    public void setParameters(ParameterList parameters);
    
    
    /**
     * Gets the list of modes properties
     */
    public List<AbstractModes> getModesList();
    
    
    /**
     * Returns number of modes properties
     */
    public int getNumModes();
    
    
    /**
     * Adds a new modes property
     */
    public void addModes(AbstractModes modes);
    
    
    /**
     * Gets the definition property
     */
    public String getDefinition();
    
    
    /**
     * Checks if definition is set
     */
    public boolean isSetDefinition();
    
    
    /**
     * Sets the definition property
     */
    public void setDefinition(String definition);
}

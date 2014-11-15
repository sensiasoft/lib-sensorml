package net.opengis.sensorml.v20;

import java.util.List;
import net.opengis.OgcPropertyList;
import net.opengis.gml.v32.Reference;
import net.opengis.swe.v20.AbstractSWEIdentifiable;
import net.opengis.swe.v20.DataComponent;


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
     * Gets the list of input properties
     */
    public OgcPropertyList<AbstractSWEIdentifiable> getInputList();
    
    
    /**
     * Returns number of input properties
     */
    public int getNumInputs();
    
    
    /**
     * Gets the input property with the given name
     */
    public AbstractSWEIdentifiable getInput(String name);
    
    
    /**
     * Adds a new inputAsAbstractDataComponent property
     */
    public void addInputAsAbstractDataComponent(String name, DataComponent input);
    
    
    /**
     * Adds a new inputAsObservableProperty property
     */
    public void addInputAsObservableProperty(String name, ObservableProperty input);
    
    
    /**
     * Adds a new inputAsDataInterface property
     */
    public void addInputAsDataInterface(String name, DataInterface input);
    
    
    /**
     * Gets the list of output properties
     */
    public OgcPropertyList<AbstractSWEIdentifiable> getOutputList();
    
    
    /**
     * Returns number of output properties
     */
    public int getNumOutputs();
    
    
    /**
     * Gets the output property with the given name
     */
    public AbstractSWEIdentifiable getOutput(String name);
    
    
    /**
     * Adds a new outputAsAbstractDataComponent property
     */
    public void addOutputAsAbstractDataComponent(String name, DataComponent output);
    
    
    /**
     * Adds a new outputAsObservableProperty property
     */
    public void addOutputAsObservableProperty(String name, ObservableProperty output);
    
    
    /**
     * Adds a new outputAsDataInterface property
     */
    public void addOutputAsDataInterface(String name, DataInterface output);
    
    
    /**
     * Gets the list of parameter properties
     */
    public OgcPropertyList<AbstractSWEIdentifiable> getParameterList();
    
    
    /**
     * Returns number of parameter properties
     */
    public int getNumParameters();
    
    
    /**
     * Gets the parameter property with the given name
     */
    public AbstractSWEIdentifiable getParameter(String name);
    
    
    /**
     * Adds a new parameterAsAbstractDataComponent property
     */
    public void addParameterAsAbstractDataComponent(String name, DataComponent parameter);
    
    
    /**
     * Adds a new parameterAsObservableProperty property
     */
    public void addParameterAsObservableProperty(String name, ObservableProperty parameter);
    
    
    /**
     * Adds a new parameterAsDataInterface property
     */
    public void addParameterAsDataInterface(String name, DataInterface parameter);
        
    
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

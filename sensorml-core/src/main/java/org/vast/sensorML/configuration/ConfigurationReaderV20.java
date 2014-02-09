/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2013 Sensia Software LLC.
 All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.sensorML.configuration;

import java.util.regex.Pattern;
import org.vast.cdm.common.DataEncoding;
import org.vast.data.ConstraintList;
import org.vast.sweCommon.SweComponentReaderV20;
import org.vast.sweCommon.SweEncodingReaderV20;
import org.vast.xml.DOMHelper;
import org.vast.xml.XMLReaderException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


/**
 * <p>
 * Reader for configuration section of SensorML 2.0
 * </p>
 *
 * <p>Copyright (c) 2014</p>
 * @author Alexandre Robin <alex.robin@sensiasoftware.com>
 * @since Feb 5, 2014
 */
public class ConfigurationReaderV20
{
    protected static Pattern REGEX_BOOL = Pattern.compile("true|false|1|0");
    protected static Pattern REGEX_INTEGER = Pattern.compile("[-+]?[0-9]*");
    protected static Pattern REGEX_DECIMAL = Pattern.compile("([-+]?[0-9]*\\.?[0-9]*([eE][-+]?[0-9]+)?)|NaN|\\+INF|\\-INF");
    
    SweComponentReaderV20 sweReader = new SweComponentReaderV20(); 
    SweEncodingReaderV20 encodingReader = new SweEncodingReaderV20();
    
    
    /**
     * Reads a sml:Settings schema element
     * @param dom
     * @param settingsElt
     * @return
     * @throws XMLReaderException
     */
    public Configuration readSettings(DOMHelper dom, Element settingsElt)  throws XMLReaderException
    {
        try
        {
            Configuration config = new Configuration();
            NodeList elts;
            
            elts = dom.getChildElements(settingsElt);
            for (int i=0; i<elts.getLength(); i++)
            {
                Element setElt = (Element)elts.item(i);
                String setType = setElt.getLocalName();
                AbstractSetting setting = null;
                                        
                // mode setting
                if (setType.equals("setMode"))
                {
                    setting = new ModeSetting();
                    String setValue = dom.getElementValue(setElt);
                    ((ModeSetting)setting).setMode(setValue);
                }
                
                // status setting
                else if (setType.equals("setStatus"))
                {
                    setting = new StatusSetting();
                    String setValue = dom.getElementValue(setElt);
                    ((StatusSetting)setting).setStatus(setValue.equals("enabled"));
                }
                
                // value setting
                else if (setType.equals("setValue"))
                {
                    String setValue = dom.getElementValue(setElt);
                    
                    // deal with different data types
                    if (REGEX_BOOL.matcher(setValue).matches())
                    {
                        setting = new ValueSetting<Boolean>();
                        ((ValueSetting<Boolean>)setting).setValue(Boolean.parseBoolean(setValue));
                    }
                    else if (REGEX_INTEGER.matcher(setValue).matches())
                    {
                        setting = new ValueSetting<Integer>();
                        ((ValueSetting<Integer>)setting).setValue(Integer.parseInt(setValue));
                    }
                    else if (REGEX_DECIMAL.matcher(setValue).matches())
                    {
                        setting = new ValueSetting<Double>();
                        ((ValueSetting<Double>)setting).setValue(Double.parseDouble(setValue));
                    }
                    else
                    {
                        setting = new ValueSetting<String>();
                        ((ValueSetting<String>)setting).setValue(setValue);
                    }
                }
                
                // constraint setting
                else if (setType.equals("setConstraint"))
                {
                    setting = new ConstraintSetting();
                    Element constraintElt = dom.getFirstChildElement(setElt);
                    ConstraintList constraints = sweReader.readConstraintList(dom, constraintElt);
                    ((ConstraintSetting)setting).setConstraint(constraints);
                }
                
                // array values setting
                else if (setType.equals("setArrayValues8"))
                {
                    setting = new ArrayValuesSetting();
                    
                    Element encodingElt = dom.getElement(setElt, "ArrayValues/encoding");
                    DataEncoding encoding = encodingReader.readEncodingProperty(dom, encodingElt);
                    ((ArrayValuesSetting)setting).setEncoding(encoding);
                    
                    Element valuesElt = dom.getElement(setElt, "ArrayValues/value");
                    ((ArrayValuesSetting)setting).setValues(dom.getElementValue(valuesElt));
                }
                
                setting.setComponentRef(dom.getAttributeValue(setElt, "ref"));
                config.add(setting);
            }
            
            return config;
        }
        catch (Exception e)
        {
            throw new XMLReaderException("Error while reading configuration settings", (Element)dom.getLastAccessedNode(), e);
        }
    }
    
    
    public Mode readMode(DOMHelper dom, Element modeElt)  throws XMLReaderException
    {
        Mode mode = new Mode();
        
        // read all described object common properties
        
        
        // read settings
        Element settingsElt = dom.getElement(modeElt, "configuration/*");
        Configuration config = readSettings(dom, settingsElt);
        mode.setConfiguration(config);
        
        return mode;
    }
}

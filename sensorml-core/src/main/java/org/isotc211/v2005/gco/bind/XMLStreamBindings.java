/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2015 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package org.isotc211.v2005.gco.bind;

import java.util.Map;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import net.opengis.AbstractXMLStreamBindings;
import net.opengis.IDateTime;
import net.opengis.OgcProperty;
import net.opengis.OgcPropertyImpl;
import org.isotc211.v2005.gco.AbstractObject;
import org.isotc211.v2005.gco.Binary;
import org.isotc211.v2005.gco.CodeListValue;
import org.isotc211.v2005.gco.MemberName;
import org.isotc211.v2005.gco.Multiplicity;
import org.isotc211.v2005.gco.MultiplicityRange;
import org.isotc211.v2005.gco.RecordType;
import org.isotc211.v2005.gco.TypeName;
import org.isotc211.v2005.gco.UnlimitedInteger;
import org.isotc211.v2005.gco.Factory;


public class XMLStreamBindings extends AbstractXMLStreamBindings
{
    public final static String NS_URI = "http://www.isotc211.org/2005/gco";
    
    Factory factory;
    
    
    public XMLStreamBindings(Factory factory)
    {
        this.factory = factory;
    }
    
    
    /**
     * Read method for TypeNameType complex type
     */
    public TypeName readTypeNameType(XMLStreamReader reader) throws XMLStreamException
    {
        TypeName bean = factory.newTypeName();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readTypeNameTypeAttributes(attrMap, bean);
        
        reader.nextTag();
        this.readTypeNameTypeElements(reader, bean);
        
        return bean;
    }
    
    
    /**
     * Reads attributes of TypeNameType complex type
     */
    public void readTypeNameTypeAttributes(Map<String, String> attrMap, TypeName bean) throws XMLStreamException
    {
        this.readAbstractObjectTypeAttributes(attrMap, bean);
        
    }
    
    
    /**
     * Reads elements of TypeNameType complex type
     */
    public void readTypeNameTypeElements(XMLStreamReader reader, TypeName bean) throws XMLStreamException
    {
        boolean found;
        
        // aName
        found = checkElementName(reader, "aName");
        if (found)
        {
            reader.nextTag();
            String aName = this.readCharacterString(reader);
            if (aName != null)
                bean.setAName(aName);
            
            reader.nextTag(); // end property tag
            reader.nextTag();
        }
    }
    
    
    /**
     * Write method for TypeNameType complex type
     */
    public void writeTypeNameType(XMLStreamWriter writer, TypeName bean) throws XMLStreamException
    {
        this.writeTypeNameTypeAttributes(writer, bean);
        this.writeTypeNameTypeElements(writer, bean);
    }
    
    
    /**
     * Writes attributes of TypeNameType complex type
     */
    public void writeTypeNameTypeAttributes(XMLStreamWriter writer, TypeName bean) throws XMLStreamException
    {
        this.writeAbstractObjectTypeAttributes(writer, bean);
    }
    
    
    /**
     * Writes elements of TypeNameType complex type
     */
    public void writeTypeNameTypeElements(XMLStreamWriter writer, TypeName bean) throws XMLStreamException
    {
        
        // aName
        writer.writeStartElement(NS_URI, "aName");
        this.writeCharacterString(writer, bean.getAName());
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for MemberNameType complex type
     */
    public MemberName readMemberNameType(XMLStreamReader reader) throws XMLStreamException
    {
        MemberName bean = factory.newMemberName();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readMemberNameTypeAttributes(attrMap, bean);
        
        reader.nextTag();
        this.readMemberNameTypeElements(reader, bean);
        
        return bean;
    }
    
    
    /**
     * Reads attributes of MemberNameType complex type
     */
    public void readMemberNameTypeAttributes(Map<String, String> attrMap, MemberName bean) throws XMLStreamException
    {
        this.readAbstractObjectTypeAttributes(attrMap, bean);
        
    }
    
    
    /**
     * Reads elements of MemberNameType complex type
     */
    public void readMemberNameTypeElements(XMLStreamReader reader, MemberName bean) throws XMLStreamException
    {
        boolean found;
        
        // aName
        found = checkElementName(reader, "aName");
        if (found)
        {
            reader.nextTag();
            String aName = this.readCharacterString(reader);
            if (aName != null)
                bean.setAName(aName);
            
            reader.nextTag(); // end property tag
            reader.nextTag();
        }
        
        // attributeType
        found = checkElementName(reader, "attributeType");
        if (found)
        {
            OgcProperty<TypeName> attributeTypeProp = bean.getAttributeTypeProperty();
            readPropertyAttributes(reader, attributeTypeProp);
            
            if (attributeTypeProp.getHref() == null)
            {
                reader.nextTag();
                attributeTypeProp.setValue(this.readTypeName(reader));
            }
            
            reader.nextTag(); // end property tag
            reader.nextTag();
        }
    }
    
    
    /**
     * Write method for MemberNameType complex type
     */
    public void writeMemberNameType(XMLStreamWriter writer, MemberName bean) throws XMLStreamException
    {
        this.writeMemberNameTypeAttributes(writer, bean);
        this.writeMemberNameTypeElements(writer, bean);
    }
    
    
    /**
     * Writes attributes of MemberNameType complex type
     */
    public void writeMemberNameTypeAttributes(XMLStreamWriter writer, MemberName bean) throws XMLStreamException
    {
        this.writeAbstractObjectTypeAttributes(writer, bean);
    }
    
    
    /**
     * Writes elements of MemberNameType complex type
     */
    public void writeMemberNameTypeElements(XMLStreamWriter writer, MemberName bean) throws XMLStreamException
    {
        
        // aName
        writer.writeStartElement(NS_URI, "aName");
        this.writeCharacterString(writer, bean.getAName());
        writer.writeEndElement();
        
        // attributeType
        writer.writeStartElement(NS_URI, "attributeType");
        writePropertyAttributes(writer, bean.getAttributeTypeProperty());
        this.writeTypeName(writer, bean.getAttributeType());
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for MultiplicityType complex type
     */
    public Multiplicity readMultiplicityType(XMLStreamReader reader) throws XMLStreamException
    {
        Multiplicity bean = factory.newMultiplicity();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readMultiplicityTypeAttributes(attrMap, bean);
        
        reader.nextTag();
        this.readMultiplicityTypeElements(reader, bean);
        
        return bean;
    }
    
    
    /**
     * Reads attributes of MultiplicityType complex type
     */
    public void readMultiplicityTypeAttributes(Map<String, String> attrMap, Multiplicity bean) throws XMLStreamException
    {
        this.readAbstractObjectTypeAttributes(attrMap, bean);
        
    }
    
    
    /**
     * Reads elements of MultiplicityType complex type
     */
    public void readMultiplicityTypeElements(XMLStreamReader reader, Multiplicity bean) throws XMLStreamException
    {
        boolean found;
        
        // range
        do
        {
            found = checkElementName(reader, "range");
            if (found)
            {
                OgcProperty<MultiplicityRange> rangeProp = new OgcPropertyImpl<MultiplicityRange>();
                readPropertyAttributes(reader, rangeProp);
                
                if (rangeProp.getHref() == null)
                {
                    reader.nextTag();
                    rangeProp.setValue(this.readMultiplicityRange(reader));
                }
                bean.getRangeList().add(rangeProp);
                
                reader.nextTag(); // end property tag
                reader.nextTag();
            }
        }
        while (found);
    }
    
    
    /**
     * Write method for MultiplicityType complex type
     */
    public void writeMultiplicityType(XMLStreamWriter writer, Multiplicity bean) throws XMLStreamException
    {
        this.writeMultiplicityTypeAttributes(writer, bean);
        this.writeMultiplicityTypeElements(writer, bean);
    }
    
    
    /**
     * Writes attributes of MultiplicityType complex type
     */
    public void writeMultiplicityTypeAttributes(XMLStreamWriter writer, Multiplicity bean) throws XMLStreamException
    {
        this.writeAbstractObjectTypeAttributes(writer, bean);
    }
    
    
    /**
     * Writes elements of MultiplicityType complex type
     */
    public void writeMultiplicityTypeElements(XMLStreamWriter writer, Multiplicity bean) throws XMLStreamException
    {
        int numItems;
        
        // range
        numItems = bean.getRangeList().size();
        for (int i = 0; i < numItems; i++)
        {
            OgcProperty<MultiplicityRange> item = bean.getRangeList().getProperty(i);
            writer.writeStartElement(NS_URI, "range");
            writePropertyAttributes(writer, item);
            this.writeMultiplicityRange(writer, item.getValue());
            writer.writeEndElement();
        }
    }
    
    
    /**
     * Read method for MultiplicityRangeType complex type
     */
    public MultiplicityRange readMultiplicityRangeType(XMLStreamReader reader) throws XMLStreamException
    {
        MultiplicityRange bean = factory.newMultiplicityRange();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readMultiplicityRangeTypeAttributes(attrMap, bean);
        
        reader.nextTag();
        this.readMultiplicityRangeTypeElements(reader, bean);
        
        return bean;
    }
    
    
    /**
     * Reads attributes of MultiplicityRangeType complex type
     */
    public void readMultiplicityRangeTypeAttributes(Map<String, String> attrMap, MultiplicityRange bean) throws XMLStreamException
    {
        this.readAbstractObjectTypeAttributes(attrMap, bean);
        
    }
    
    
    /**
     * Reads elements of MultiplicityRangeType complex type
     */
    public void readMultiplicityRangeTypeElements(XMLStreamReader reader, MultiplicityRange bean) throws XMLStreamException
    {
        boolean found;
        
        // lower
        found = checkElementName(reader, "lower");
        if (found)
        {
            reader.nextTag();
            int lower = this.readInteger(reader);
            bean.setLower(lower);
            
            reader.nextTag(); // end property tag
            reader.nextTag();
        }
        
        // upper
        found = checkElementName(reader, "upper");
        if (found)
        {
            reader.nextTag();
            UnlimitedInteger upper = this.readUnlimitedInteger(reader);
            if (upper != null)
                bean.setUpper(upper);
            
            reader.nextTag(); // end property tag
            reader.nextTag();
        }
    }
    
    
    /**
     * Write method for MultiplicityRangeType complex type
     */
    public void writeMultiplicityRangeType(XMLStreamWriter writer, MultiplicityRange bean) throws XMLStreamException
    {
        this.writeMultiplicityRangeTypeAttributes(writer, bean);
        this.writeMultiplicityRangeTypeElements(writer, bean);
    }
    
    
    /**
     * Writes attributes of MultiplicityRangeType complex type
     */
    public void writeMultiplicityRangeTypeAttributes(XMLStreamWriter writer, MultiplicityRange bean) throws XMLStreamException
    {
        this.writeAbstractObjectTypeAttributes(writer, bean);
    }
    
    
    /**
     * Writes elements of MultiplicityRangeType complex type
     */
    public void writeMultiplicityRangeTypeElements(XMLStreamWriter writer, MultiplicityRange bean) throws XMLStreamException
    {
        
        // lower
        writer.writeStartElement(NS_URI, "lower");
        this.writeInteger(writer, bean.getLower());
        writer.writeEndElement();
        
        // upper
        writer.writeStartElement(NS_URI, "upper");
        this.writeUnlimitedInteger(writer, bean.getUpper());
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for UnlimitedIntegerType complex type
     */
    public UnlimitedInteger readUnlimitedIntegerType(XMLStreamReader reader) throws XMLStreamException
    {
        UnlimitedInteger bean = factory.newUnlimitedInteger();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readUnlimitedIntegerTypeAttributes(attrMap, bean);
        
        String val = reader.getElementText();
        if (val != null)
            bean.setValue(getIntFromString(val));
        
        return bean;
    }
    
    
    /**
     * Reads attributes of UnlimitedIntegerType complex type
     */
    public void readUnlimitedIntegerTypeAttributes(Map<String, String> attrMap, UnlimitedInteger bean) throws XMLStreamException
    {
        String val;
        
        // isinfinite
        val = attrMap.get("isInfinite");
        if (val != null)
            bean.setIsInfinite(getBooleanFromString(val));
    }
    
    
    /**
     * Write method for UnlimitedIntegerType complex type
     */
    public void writeUnlimitedIntegerType(XMLStreamWriter writer, UnlimitedInteger bean) throws XMLStreamException
    {
        this.writeUnlimitedIntegerTypeAttributes(writer, bean);
        
        writer.writeCharacters(getStringValue(bean.getValue()));
    }
    
    
    /**
     * Writes attributes of UnlimitedIntegerType complex type
     */
    public void writeUnlimitedIntegerTypeAttributes(XMLStreamWriter writer, UnlimitedInteger bean) throws XMLStreamException
    {
        
        // isInfinite
        if (bean.isSetIsInfinite())
            writer.writeAttribute("isInfinite", getStringValue(bean.getIsInfinite()));
    }
    
    
    /**
     * Read method for RecordTypeType complex type
     */
    public RecordType readRecordTypeType(XMLStreamReader reader) throws XMLStreamException
    {
        RecordType bean = factory.newRecordType();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readRecordTypeTypeAttributes(attrMap, bean);
        
        String val = reader.getElementText();
        if (val != null)
            bean.setValue(trimStringValue(val));
        
        return bean;
    }
    
    
    /**
     * Reads attributes of RecordTypeType complex type
     */
    public void readRecordTypeTypeAttributes(Map<String, String> attrMap, RecordType bean) throws XMLStreamException
    {
        readPropertyAttributes(attrMap, bean);
        
    }
    
    
    /**
     * Write method for RecordTypeType complex type
     */
    public void writeRecordTypeType(XMLStreamWriter writer, RecordType bean) throws XMLStreamException
    {
        this.writeRecordTypeTypeAttributes(writer, bean);
        
        writer.writeCharacters(getStringValue(bean.getValue()));
    }
    
    
    /**
     * Writes attributes of RecordTypeType complex type
     */
    public void writeRecordTypeTypeAttributes(XMLStreamWriter writer, RecordType bean) throws XMLStreamException
    {
        writePropertyAttributes(writer, bean);
    }
    
    
    /**
     * Read method for BinaryType complex type
     */
    public Binary readBinaryType(XMLStreamReader reader) throws XMLStreamException
    {
        Binary bean = factory.newBinary();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readBinaryTypeAttributes(attrMap, bean);
        
        String val = reader.getElementText();
        if (val != null)
            bean.setValue(trimStringValue(val));
        
        return bean;
    }
    
    
    /**
     * Reads attributes of BinaryType complex type
     */
    public void readBinaryTypeAttributes(Map<String, String> attrMap, Binary bean) throws XMLStreamException
    {
        String val;
        
        // src
        val = attrMap.get("src");
        if (val != null)
            bean.setSrc(val);
    }
    
    
    /**
     * Write method for BinaryType complex type
     */
    public void writeBinaryType(XMLStreamWriter writer, Binary bean) throws XMLStreamException
    {
        this.writeBinaryTypeAttributes(writer, bean);
        
        writer.writeCharacters(getStringValue(bean.getValue()));
    }
    
    
    /**
     * Writes attributes of BinaryType complex type
     */
    public void writeBinaryTypeAttributes(XMLStreamWriter writer, Binary bean) throws XMLStreamException
    {
        
        // src
        if (bean.isSetSrc())
            writer.writeAttribute("src", getStringValue(bean.getSrc()));
    }
    
    
    /**
     * Reads attributes of AbstractObjectType complex type
     */
    public void readAbstractObjectTypeAttributes(Map<String, String> attrMap, AbstractObject bean) throws XMLStreamException
    {
        String val;
        
        // id
        val = attrMap.get("id");
        if (val != null)
            bean.setId(val);
        
        // uuid
        val = attrMap.get("uuid");
        if (val != null)
            bean.setUuid(val);
    }
    
    
    /**
     * Writes attributes of AbstractObjectType complex type
     */
    public void writeAbstractObjectTypeAttributes(XMLStreamWriter writer, AbstractObject bean) throws XMLStreamException
    {
        
        // id
        if (bean.isSetId())
            writer.writeAttribute("id", getStringValue(bean.getId()));
        
        // uuid
        if (bean.isSetUuid())
            writer.writeAttribute("uuid", getStringValue(bean.getUuid()));
    }
    
    
    /**
     * Read method for CodeListValueType complex type
     */
    public CodeListValue readCodeListValueType(XMLStreamReader reader) throws XMLStreamException
    {
        CodeListValue bean = factory.newCodeListValue();
        
        Map<String, String> attrMap = collectAttributes(reader);
        this.readCodeListValueTypeAttributes(attrMap, bean);
        
        String val = reader.getElementText();
        if (val != null)
            bean.setValue(trimStringValue(val));
        
        return bean;
    }
    
    
    /**
     * Reads attributes of CodeListValueType complex type
     */
    public void readCodeListValueTypeAttributes(Map<String, String> attrMap, CodeListValue bean) throws XMLStreamException
    {
        String val;
        
        // codelist
        val = attrMap.get("codeList");
        if (val != null)
            bean.setCodeList(val);
        
        // codelistvalue
        val = attrMap.get("codeListValue");
        if (val != null)
            bean.setCodeListValue(val);
        
        // codespace
        val = attrMap.get("codeSpace");
        if (val != null)
            bean.setCodeSpace(val);
    }
    
    
    /**
     * Write method for CodeListValueType complex type
     */
    public void writeCodeListValueType(XMLStreamWriter writer, CodeListValue bean) throws XMLStreamException
    {
        this.writeCodeListValueTypeAttributes(writer, bean);
        
        writer.writeCharacters(getStringValue(bean.getValue()));
    }
    
    
    /**
     * Writes attributes of CodeListValueType complex type
     */
    public void writeCodeListValueTypeAttributes(XMLStreamWriter writer, CodeListValue bean) throws XMLStreamException
    {
        
        // codeList
        writer.writeAttribute("codeList", getStringValue(bean.getCodeList()));
        
        // codeListValue
        writer.writeAttribute("codeListValue", getStringValue(bean.getCodeListValue()));
        
        // codeSpace
        if (bean.isSetCodeSpace())
            writer.writeAttribute("codeSpace", getStringValue(bean.getCodeSpace()));
    }
    
    
    /**
     * Read method for TypeName elements
     */
    public TypeName readTypeName(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "TypeName");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return this.readTypeNameType(reader);
    }
    
    
    /**
     * Write method for TypeName element
     */
    public void writeTypeName(XMLStreamWriter writer, TypeName bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "TypeName");
        this.writeNamespaces(writer);
        this.writeTypeNameType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for MemberName elements
     */
    public MemberName readMemberName(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "MemberName");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return this.readMemberNameType(reader);
    }
    
    
    /**
     * Write method for MemberName element
     */
    public void writeMemberName(XMLStreamWriter writer, MemberName bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "MemberName");
        this.writeNamespaces(writer);
        this.writeMemberNameType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for Multiplicity elements
     */
    public Multiplicity readMultiplicity(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "Multiplicity");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return this.readMultiplicityType(reader);
    }
    
    
    /**
     * Write method for Multiplicity element
     */
    public void writeMultiplicity(XMLStreamWriter writer, Multiplicity bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "Multiplicity");
        this.writeNamespaces(writer);
        this.writeMultiplicityType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for MultiplicityRange elements
     */
    public MultiplicityRange readMultiplicityRange(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "MultiplicityRange");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return this.readMultiplicityRangeType(reader);
    }
    
    
    /**
     * Write method for MultiplicityRange element
     */
    public void writeMultiplicityRange(XMLStreamWriter writer, MultiplicityRange bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "MultiplicityRange");
        this.writeNamespaces(writer);
        this.writeMultiplicityRangeType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for CharacterString elements
     */
    public String readCharacterString(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "CharacterString");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        String val = trimStringValue(reader.getElementText());
        return val;
    }
    
    
    /**
     * Write method for Integer element
     */
    public void writeCharacterString(XMLStreamWriter writer, String bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "CharacterString");
        this.writeNamespaces(writer);
        writer.writeCharacters(bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for Boolean elements
     */
    public Boolean readBoolean(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "Boolean");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        String val = reader.getElementText();
        if (val != null)
            return getBooleanFromString(val);
        else
            return null;
    }
    
    
    /**
     * Write method for Boolean element
     */
    public void writeBoolean(XMLStreamWriter writer, boolean bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "Boolean");
        this.writeNamespaces(writer);
        writer.writeCharacters(getStringValue(bean));
        writer.writeEndElement();
    }
    
    
      
    
    /**
     * Read method for Date elements
     */
    public IDateTime readDateTime(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "Date");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        String val = reader.getElementText();
        return getDateTimeFromString(val);
    }
    
    
    /**
     * Write method for Date element
     */
    public void writeDateTime(XMLStreamWriter writer, IDateTime bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "Date");
        this.writeNamespaces(writer);
        writer.writeCharacters(getStringValue(bean));
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for Decimal elements
     */
    public Double readDecimal(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "Decimal");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        String val = reader.getElementText();
        if (val != null)
            return getDoubleFromString(val);
        else
            return null;
    }
    
    
    /**
     * Write method for Decimal element
     */
    public void writeDecimal(XMLStreamWriter writer, double bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "Decimal");
        this.writeNamespaces(writer);
        writer.writeCharacters(getStringValue(bean));
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for Real elements
     */
    public double readReal(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "Real");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        String val = reader.getElementText();
        return getDoubleFromString(val);
    }
    
    
    /**
     * Write method for Real element
     */
    public void writeReal(XMLStreamWriter writer, double bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "Real");
        this.writeNamespaces(writer);
        writer.writeCharacters(getStringValue(bean));
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for Integer elements
     */
    public Integer readInteger(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "Integer");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        String val = reader.getElementText();
        if (val != null)
            return getIntFromString(val);
        else
            return null;
    }
    
    
    /**
     * Write method for Integer element
     */
    public void writeInteger(XMLStreamWriter writer, int bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "Integer");
        this.writeNamespaces(writer);
        writer.writeCharacters(getStringValue(bean));
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for UnlimitedInteger elements
     */
    public UnlimitedInteger readUnlimitedInteger(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "UnlimitedInteger");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return this.readUnlimitedIntegerType(reader);
    }
    
    
    /**
     * Write method for UnlimitedInteger element
     */
    public void writeUnlimitedInteger(XMLStreamWriter writer, UnlimitedInteger bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "UnlimitedInteger");
        this.writeNamespaces(writer);
        this.writeUnlimitedIntegerType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for RecordType elements
     */
    public RecordType readRecordType(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "RecordType");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return this.readRecordTypeType(reader);
    }
    
    
    /**
     * Write method for RecordType element
     */
    public void writeRecordType(XMLStreamWriter writer, RecordType bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "RecordType");
        this.writeNamespaces(writer);
        this.writeRecordTypeType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for RecordType elements
     */
    public Object readRecord(XMLStreamReader reader) throws XMLStreamException
    {
        // TODO read GCO Record of any type
        return null;
    }
    
    
    /**
     * Write method for RecordType element
     */
    public void writeRecord(XMLStreamWriter writer, Object bean) throws XMLStreamException
    {
        // TODO write GCO record of any type
    }
    
    
    /**
     * Read method for Binary elements
     */
    public Binary readBinary(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "Binary");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        return this.readBinaryType(reader);
    }
    
    
    /**
     * Write method for Binary element
     */
    public void writeBinary(XMLStreamWriter writer, Binary bean) throws XMLStreamException
    {
        writer.writeStartElement(NS_URI, "Binary");
        this.writeNamespaces(writer);
        this.writeBinaryType(writer, bean);
        writer.writeEndElement();
    }
    
    
    /**
     * Read method for GTS TM_PeriodDuration elements
     */
    public IDateTime readTMPeriodDuration(XMLStreamReader reader) throws XMLStreamException
    {
        boolean found = checkElementName(reader, "TM_PeriodDuration");
        if (!found)
            throw new XMLStreamException(ERROR_INVALID_ELT + reader.getName() + errorLocationString(reader));
        
        // TODO read xsd duration token for TM_PeriodDuration
        return null;
    }
    
    
    /**
     * Write method for GTS TM_PeriodDuration element
     */
    public void writeTMPeriodDuration(XMLStreamWriter writer, IDateTime bean) throws XMLStreamException
    {
        writer.writeStartElement("http://www.isotc211.org/2005/gts", "TM_PeriodDuration");
        this.writeNamespaces(writer);
        // TODO write xsd duration token for TM_PeriodDuration
        writer.writeEndElement();
    }
}

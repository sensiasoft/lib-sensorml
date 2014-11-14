package org.isotc211.v2005.gco.impl;

import org.isotc211.v2005.gco.Binary;
import org.isotc211.v2005.gco.CodeListValue;
import org.isotc211.v2005.gco.MemberName;
import org.isotc211.v2005.gco.Multiplicity;
import org.isotc211.v2005.gco.MultiplicityRange;
import org.isotc211.v2005.gco.RecordType;
import org.isotc211.v2005.gco.TypeName;
import org.isotc211.v2005.gco.UnlimitedInteger;
import org.isotc211.v2005.gco.Factory;


public class GCOFactory implements Factory
{       
    
    public GCOFactory()
    {
    }
    
    
    public TypeName newTypeName()
    {
        return new TypeNameImpl();
    }
    
    
    public MemberName newMemberName()
    {
        return new MemberNameImpl();
    }
    
    
    public Multiplicity newMultiplicity()
    {
        return new MultiplicityImpl();
    }
    
    
    public MultiplicityRange newMultiplicityRange()
    {
        return new MultiplicityRangeImpl();
    }
    
    
    public UnlimitedInteger newUnlimitedInteger()
    {
        return new UnlimitedIntegerImpl();
    }
    
    
    public RecordType newRecordType()
    {
        return new RecordTypeImpl();
    }
    
    
    public Binary newBinary()
    {
        return new BinaryImpl();
    }
    
    
    public CodeListValue newCodeListValue()
    {
        return new CodeListValueImpl();
    }
}

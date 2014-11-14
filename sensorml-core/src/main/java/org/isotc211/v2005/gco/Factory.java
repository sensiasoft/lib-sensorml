package org.isotc211.v2005.gco;



public interface Factory
{
    
    
    public TypeName newTypeName();
    
    
    public MemberName newMemberName();
    
    
    public Multiplicity newMultiplicity();
    
    
    public MultiplicityRange newMultiplicityRange();
    
    
    public UnlimitedInteger newUnlimitedInteger();
    
    
    public RecordType newRecordType();
    
    
    public Binary newBinary();
    
    
    public CodeListValue newCodeListValue();
}

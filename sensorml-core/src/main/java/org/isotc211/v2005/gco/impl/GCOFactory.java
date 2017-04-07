/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2015 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

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
    
    
    @Override
    public TypeName newTypeName()
    {
        return new TypeNameImpl();
    }
    
    
    @Override
    public MemberName newMemberName()
    {
        return new MemberNameImpl();
    }
    
    
    @Override
    public Multiplicity newMultiplicity()
    {
        return new MultiplicityImpl();
    }
    
    
    @Override
    public MultiplicityRange newMultiplicityRange()
    {
        return new MultiplicityRangeImpl();
    }
    
    
    @Override
    public UnlimitedInteger newUnlimitedInteger()
    {
        return new UnlimitedIntegerImpl();
    }
    
    
    @Override
    public RecordType newRecordType()
    {
        return new RecordTypeImpl();
    }
    
    
    @Override
    public Binary newBinary()
    {
        return new BinaryImpl();
    }
    
    
    @Override
    public CodeListValue newCodeListValue()
    {
        return new CodeListValueImpl();
    }
}

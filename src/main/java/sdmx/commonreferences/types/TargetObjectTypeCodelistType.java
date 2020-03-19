/*
    This file is part of sdmx-sax.

Copyright 2015 James Stanley Gardner
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"),
to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER 
DEALINGS IN THE SOFTWARE.

*/
package sdmx.commonreferences.types;

import java.util.ArrayList;
import java.util.List;
import sdmx.exception.TypeValueNotFoundException;

/**
 *	<xs:simpleType name="TargetObjectTypeCodelistType">
		<xs:annotation>
			<xs:documentation>TargetObjectTypeCodelistType provides an enumeration of all target object objects.</xs:documentation>
		</xs:annotation>
		<xs:restriction base="ComponentTypeCodelistType">
			<xs:enumeration value="ConstraintTarget"/>
			<xs:enumeration value="DataSetTarget"/>
			<xs:enumeration value="IdentifiableObjectTarget"/>
			<xs:enumeration value="DimensionDescriptorValuesTarget"/>
			<xs:enumeration value="ReportPeriodTarget"/>
		</xs:restriction>
	</xs:simpleType>

 * @author James
 */

public class TargetObjectTypeCodelistType extends ComponentTypeCodelistType {

    public static final List<TargetObjectTypeCodelistType> ENUM = new ArrayList<TargetObjectTypeCodelistType>();
    public static final List<String> STRING_ENUM = new ArrayList<String>();
    
    public static final String TARGET_CONSTRAINTARGET = addString("ConstraintTarget");
    public static final String TARGET_DATASETTARGET = addString("DataSetTarget");
    public static final String TARGET_IDENTIFIABLEOBJECTTARGET = addString("IdentifiableObjectTarget");
    public static final String TARGET_DIMENSIONDESCRIPTORVALUESTARGET = addString("DimensionDescriptorValuesTarget");
    public static final String TARGET_REPORTINGPERIODTARGET = addString("ReportPeriodTarget");

    public static final TargetObjectTypeCodelistType CONSTRAINTARGET = add(TARGET_CONSTRAINTARGET);
    public static final TargetObjectTypeCodelistType DATASETTARGET = add(TARGET_DATASETTARGET);
    public static final TargetObjectTypeCodelistType IDENTIFIABLEOBJECTTARGET = add(TARGET_IDENTIFIABLEOBJECTTARGET);
    public static final TargetObjectTypeCodelistType DIMENSIONDESCRIPTORVALUESTARGET = add(TARGET_DIMENSIONDESCRIPTORVALUESTARGET);
    public static final TargetObjectTypeCodelistType REPORTINGPERIODTARGET = add(TARGET_REPORTINGPERIODTARGET);
// Utility
    private static TargetObjectTypeCodelistType add(String s){
        TargetObjectTypeCodelistType b = new TargetObjectTypeCodelistType(s);
        ENUM.add(b);
        return b;
    }
    private static String addString(String s){
        STRING_ENUM.add(s);
        return s;
    }
    
    /**
     *
     * @param s
     * @return
     */
    public static TargetObjectTypeCodelistType fromString(String s) {
        for(int i=0;i<ENUM.size();i++) {
            if( ENUM.get(i).target.equals(s))return ENUM.get(i);
        }
        return null;
    }
    public static TargetObjectTypeCodelistType fromStringWithException(String s) throws TypeValueNotFoundException {
        for(int i=0;i<ENUM.size();i++) {
            if( ENUM.get(i).target.equals(s))return ENUM.get(i);
        }
        throw new TypeValueNotFoundException("Value:"+s+" not found in TargetObjectTypeCodelistType enumeration!");
    }
// Instance
    private String target = null;
    public TargetObjectTypeCodelistType(String s) {
        super(s);
        if( !STRING_ENUM.contains(s))throw new IllegalArgumentException(s+" is not a valid TargetObjectTypeCodelistType");
        this.target=s;
    }
    public String toString() { return target; }
}

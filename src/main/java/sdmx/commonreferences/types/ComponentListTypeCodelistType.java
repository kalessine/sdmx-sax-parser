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
import sdmx.commonreferences.types.ObjectTypeCodelistType;

/**
 *	<xs:simpleType name="ComponentListTypeCodelistType">
		<xs:annotation>
			<xs:documentation>ComponentListTypeCodelistType provides an enumeration of all component list objects.</xs:documentation>
		</xs:annotation>
		<xs:restriction base="ObjectTypeCodelistType">
			<xs:enumeration value="AttributeDescriptor"/>
			<xs:enumeration value="DimensionDescriptor"/>
			<xs:enumeration value="GroupDimensionDescriptor"/>
			<xs:enumeration value="MeasureDescriptor"/>
			<xs:enumeration value="MetadataTarget"/>
			<xs:enumeration value="ReportStructure"/>
		</xs:restriction>
	</xs:simpleType>

 * @author James
 */
public class ComponentListTypeCodelistType extends ObjectTypeCodelistType {

    public static final List<ComponentListTypeCodelistType> ENUM = new ArrayList<ComponentListTypeCodelistType>();
    public static final List<String> STRING_ENUM = new ArrayList<String>();

    public static final String TARGET_ATTRIBUTEDESCRIPTOR = addString("AttributeDescriptor");
    public static final String TARGET_DIMENSIONDESCRIPTOR = addString("DimensionDescriptor");
    public static final String TARGET_GROUPDIMENSIONDESCRIPTOR = addString("GroupDimensionDescriptor");
    public static final String TARGET_MEASUREDESCRIPTOR = addString("MeasureDescriptor");
    public static final String TARGET_METADATATARGET = addString("MetadataTarget");
    public static final String TARGET_REPORTSTRUCTURE = addString("ReportStructure");
    
    public static final ComponentListTypeCodelistType ATTRIBUTEDESCRIPTOR = add(TARGET_ATTRIBUTEDESCRIPTOR);
    public static final ComponentListTypeCodelistType DIMENSIONDESCRIPTOR = add(TARGET_DIMENSIONDESCRIPTOR);
    public static final ComponentListTypeCodelistType GROUPDIMENSIONDESCRIPTOR = add(TARGET_GROUPDIMENSIONDESCRIPTOR);
    public static final ComponentListTypeCodelistType MEASUREDESCRIPTOR = add(TARGET_MEASUREDESCRIPTOR);
    public static final ComponentListTypeCodelistType METADATATARGET = add(TARGET_METADATATARGET);
    public static final ComponentListTypeCodelistType REPORTSTRUCTURE = add(TARGET_REPORTSTRUCTURE);
// Utility
    private static ComponentListTypeCodelistType add(String s){
        ComponentListTypeCodelistType b = new ComponentListTypeCodelistType(s);
        ENUM.add(b);
        return b;
    }
    private static String addString(String s){
        STRING_ENUM.add(s);
        return s;
    }

    public static ComponentListTypeCodelistType fromString(String s) {
        for(int i=0;i<ENUM.size();i++) {
            if( ENUM.get(i).target.equals(s))return ENUM.get(i);
        }
        return null;
    }
    public static ComponentListTypeCodelistType fromStringWithException(String s) throws TypeValueNotFoundException {
        for(int i=0;i<ENUM.size();i++) {
            if( ENUM.get(i).target.equals(s))return ENUM.get(i);
        }
        throw new TypeValueNotFoundException("Value:"+s+" not found in ComponentListTypeCodelistType enumeration!");
    }
// Instance
    private String target = null;
    public ComponentListTypeCodelistType(String s) {
        super(s);
        if( !STRING_ENUM.contains(s))throw new IllegalArgumentException(s+" is not a valid CodeTypeCodelistType");
        this.target=s;
    }
    public String toString() { return target; }
}

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
 *	<xs:simpleType name="StructureTypeCodelistType">
		<xs:annotation>
			<xs:documentation>StructureTypeCodelistType provides an enumeration all structure objects</xs:documentation>
		</xs:annotation>
		<xs:restriction base="StructureOrUsageTypeCodelistType">
			<xs:enumeration value="DataStructure"/>
			<xs:enumeration value="MetadataStructure"/>
		</xs:restriction>
	</xs:simpleType>

 * @author James
 */

public class StructureTypeCodelistType extends StructureOrUsageCodelistType {

    public static final List<StructureTypeCodelistType> ENUM = new ArrayList<StructureTypeCodelistType>();
    public static final List<String> STRING_ENUM = new ArrayList<String>();
    
    public static final String TARGET_DATASTRUCTURE = addString("DataStructure");
    public static final String TARGET_METADATASTRUCTURE = addString("MetadataStructure");
    
    public static final StructureTypeCodelistType DATASTRUCTURE = add(TARGET_DATASTRUCTURE);
    public static final StructureTypeCodelistType METADATASTRUCTURE = add(TARGET_METADATASTRUCTURE);
    
// Utility
    private static StructureTypeCodelistType add(String s){
        StructureTypeCodelistType b = new StructureTypeCodelistType(s);
        ENUM.add(b);
        return b;
    }
    private static String addString(String s){
        STRING_ENUM.add(s);
        return s;
    }
    
    public static StructureTypeCodelistType fromString(String s) {
        for(int i=0;i<ENUM.size();i++) {
            if( ENUM.get(i).target.equals(s))return ENUM.get(i);
        }
        return null;
    }
    public static StructureTypeCodelistType fromStringWithException(String s) throws TypeValueNotFoundException {
        for(int i=0;i<ENUM.size();i++) {
            if( ENUM.get(i).target.equals(s))return ENUM.get(i);
        }
        throw new TypeValueNotFoundException("Value:"+s+" not found in StructureTypeCodelistType enumeration!");
    }
// Instance
    private String target = null;
    public StructureTypeCodelistType(String s) {
        super(s);
        if( !STRING_ENUM.contains(s))throw new IllegalArgumentException(s+" is not a valid StructureTypeCodelistType");
        this.target=s;
    }
    public String toString() { return target; }
}

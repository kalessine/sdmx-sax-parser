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
*	<xs:simpleType name="CodelistTypeCodelistType">
*		<xs:annotation>
*			<xs:documentation>CodelistTypeCodelistType provides an enumeration of all codelist objects.</xs:documentation>
*		</xs:annotation>
*		<xs:restriction base="MaintainableTypeCodelistType">
*			<xs:enumeration value="Codelist"/>
*			<xs:enumeration value="HierarchicalCodelist"/>
*		</xs:restriction>
*	</xs:simpleType>
*
 * @author James
 */

public class CodelistTypeCodelistType extends ObjectTypeCodelistType {

    public static final List<CodelistTypeCodelistType> ENUM = new ArrayList<CodelistTypeCodelistType>();
    public static final List<String> STRING_ENUM = new ArrayList<String>();

    public static final String TARGET_CODELIST = addString("Codelist");
    public static final String TARGET_HIERARCHICALCODELIST = addString("HierarchicalCodelist");

    public static final CodelistTypeCodelistType CODELIST = add(TARGET_CODELIST);
    public static final CodelistTypeCodelistType HIERARCHICALCODELIST = add(TARGET_HIERARCHICALCODELIST);

// Utility
    private static CodelistTypeCodelistType add(String s){
        CodelistTypeCodelistType b = new CodelistTypeCodelistType(s);
        ENUM.add(b);
        return b;
    }
    private static String addString(String s){
        STRING_ENUM.add(s);
        return s;
    }
    
    public static CodelistTypeCodelistType fromString(String s) {
        for(int i=0;i<ENUM.size();i++) {
            if( ENUM.get(i).target.equals(s))return ENUM.get(i);
        }
        return null;
    }
    public static CodelistTypeCodelistType fromStringWithException(String s) throws TypeValueNotFoundException {
        for(int i=0;i<ENUM.size();i++) {
            if( ENUM.get(i).target.equals(s))return ENUM.get(i);
        }
        throw new TypeValueNotFoundException("Value:"+s+" not found in CodelistTypeCodelistType enumeration!");
    }
// Instance
    private String target = null;
    public CodelistTypeCodelistType(String s) {
        super(s);
        if( !STRING_ENUM.contains(s))throw new IllegalArgumentException(s+" is not a valid CodeTypeCodelistType");
        this.target=s;
    }
    public String toString() { return target; }
}

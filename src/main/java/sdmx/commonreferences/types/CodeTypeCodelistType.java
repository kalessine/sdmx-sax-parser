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
 *	<xs:simpleType name="CodeTypeCodelistType">
 *		<xs:annotation>
 *			<xs:documentation>CodeTypeCodelistType provides an enumeration of all code objects.</xs:documentation>
 *		</xs:annotation>
 *		<xs:restriction base="ObjectTypeCodelistType">
 *			<xs:enumeration value="Code"/>
 *			<xs:enumeration value="HierarchicalCode"/>
 *		</xs:restriction>
 *	</xs:simpleType>
 * @author James
 */
public class CodeTypeCodelistType extends ObjectTypeCodelistType {

    public static final List<CodeTypeCodelistType> ENUM = new ArrayList<CodeTypeCodelistType>();
    public static final List<String> STRING_ENUM = new ArrayList<String>();

    public static final String TARGET_CODE = addString("Code");
    public static final String TARGET_HIERARCHICALCODE = addString("HierarchicalCode");

    public static final CodeTypeCodelistType CODE = add(TARGET_CODE);
    public static final CodeTypeCodelistType HIERARCHICALCODE = add(TARGET_HIERARCHICALCODE);

// Utility
    private static CodeTypeCodelistType add(String s){
        CodeTypeCodelistType b = new CodeTypeCodelistType(s);
        ENUM.add(b);
        return b;
    }
    private static String addString(String s){
        STRING_ENUM.add(s);
        return s;
    }
    
    public static CodeTypeCodelistType fromString(String s) {
        for(int i=0;i<ENUM.size();i++) {
            if( ENUM.get(i).target.equals(s))return ENUM.get(i);
        }
        return null;
    }
    public static CodeTypeCodelistType fromStringWithException(String s) throws TypeValueNotFoundException {
        for(int i=0;i<ENUM.size();i++) {
            if( ENUM.get(i).target.equals(s))return ENUM.get(i);
        }
        throw new TypeValueNotFoundException("Value:"+s+" not found in CodeTypeCodelistType enumeration!");
    }
// Instance
    private String target = null;
    public CodeTypeCodelistType(String s) {
        super(s);
        if( !STRING_ENUM.contains(s))throw new IllegalArgumentException(s+" is not a valid CodeTypeCodelistType");
        this.target=s;
    }
    public String toString() { return target; }
}

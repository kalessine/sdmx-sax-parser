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
 *	<xs:simpleType name="PackageTypeCodelistType">
*		<xs:annotation>
*			<xs:documentation>PackageTypeCodelistType provides an enumeration of all SDMX package names.</xs:documentation>
*		</xs:annotation>
*		<xs:restriction base="xs:string">
*			<xs:enumeration value="base"/>
*			<xs:enumeration value="datastructure"/>
*			<xs:enumeration value="metadatastructure"/>
*			<xs:enumeration value="process"/>
*			<xs:enumeration value="registry"/>
*			<xs:enumeration value="mapping"/>
*			<xs:enumeration value="codelist"/>
*			<xs:enumeration value="categoryscheme"/>
*			<xs:enumeration value="conceptscheme"/>
*		</xs:restriction>
*	</xs:simpleType>
 * @author James
 */
public class PackageTypeCodelistType {

    public static final List<PackageTypeCodelistType> ENUM = new ArrayList<PackageTypeCodelistType>();
    public static final List<String> STRING_ENUM = new ArrayList<String>();

    public static final String TARGET_BASE = addString("base");
    public static final String TARGET_DATASTRUCTURE = addString("datastructure");
    public static final String TARGET_METADATASTRUCTURE = addString("metadatastructure");
    public static final String TARGET_PROCESS = addString("process");
    public static final String TARGET_REGISTRY = addString("registry");
    public static final String TARGET_MAPPING = addString("mapping");
    public static final String TARGET_CODELIST = addString("codelist");
    public static final String TARGET_CATEGORYSCHEME = addString("categoryscheme");
    public static final String TARGET_CONCEPTSCHEME = addString("conceptscheme");

    public static final PackageTypeCodelistType BASE = add(TARGET_BASE);
    public static final PackageTypeCodelistType DATASTRUCTURE = add(TARGET_DATASTRUCTURE);
    public static final PackageTypeCodelistType METADATASTRUCTURE = add(TARGET_METADATASTRUCTURE);
    public static final PackageTypeCodelistType PROCESS = add(TARGET_PROCESS);
    public static final PackageTypeCodelistType REGISTRY = add(TARGET_REGISTRY);
    public static final PackageTypeCodelistType MAPPING = add(TARGET_MAPPING);
    public static final PackageTypeCodelistType CODELIST = add(TARGET_CODELIST);
    public static final PackageTypeCodelistType CATEGORYSCHEME = add(TARGET_CATEGORYSCHEME);
    public static final PackageTypeCodelistType CONCEPTSCHEME = add(TARGET_CONCEPTSCHEME);
// Utility
    private static PackageTypeCodelistType add(String s){
        PackageTypeCodelistType b = new PackageTypeCodelistType(s);
        ENUM.add(b);
        return b;
    }
    private static String addString(String s){
        STRING_ENUM.add(s);
        return s;
    }

    public static PackageTypeCodelistType fromString(String s) {
        for(int i=0;i<ENUM.size();i++) {
            if( ENUM.get(i).target.equals(s))return ENUM.get(i);
        }
        return null;
    }
    public static PackageTypeCodelistType fromStringWithException(String s) throws TypeValueNotFoundException {
        for(int i=0;i<ENUM.size();i++) {
            if( ENUM.get(i).target.equals(s))return ENUM.get(i);
        }
        throw new TypeValueNotFoundException("Value:"+s+" not found in PackageTypeCodelistType enumeration!");
    }
// Instance
    private String target = null;
    public PackageTypeCodelistType(String s) {
        if( !STRING_ENUM.contains(s))throw new IllegalArgumentException(s+" is not a valid CodeTypeCodelistType");
        this.target=s;
    }
    public String toString() { return target; }
}

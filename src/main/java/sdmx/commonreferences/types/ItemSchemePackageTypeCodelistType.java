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
import sdmx.commonreferences.types.PackageTypeCodelistType;

/**
 *	<xs:simpleType name="ItemSchemePackageTypeCodelistType">
		<xs:annotation>
			<xs:documentation>ItemSchemePackageTypeCodelistType provides an enumeration of all SDMX packages which contain item schemes.</xs:documentation>
		</xs:annotation>
		<xs:restriction base="PackageTypeCodelistType">
			<xs:enumeration value="base"/>
			<xs:enumeration value="codelist"/>
			<xs:enumeration value="categoryscheme"/>
			<xs:enumeration value="conceptscheme"/>
		</xs:restriction>
	</xs:simpleType>
 * @author James
 */
public class ItemSchemePackageTypeCodelistType extends PackageTypeCodelistType {
    public static final List<ItemSchemePackageTypeCodelistType> ENUM = new ArrayList<ItemSchemePackageTypeCodelistType>();
    public static final List<String> STRING_ENUM = new ArrayList<String>();

    public static final String TARGET_BASE = addString("base");
    public static final String TARGET_CODELIST = addString("codelist");
    public static final String TARGET_CATEGORYCHEME = addString("categoryscheme");
    public static final String TARGET_CONCEPTSCHEME = addString("conceptscheme");
    
    public static final ItemSchemePackageTypeCodelistType BASE = add(TARGET_BASE);
    public static final ItemSchemePackageTypeCodelistType CODELIST = add(TARGET_CODELIST);
    public static final ItemSchemePackageTypeCodelistType CATEGORYSCHEME = add(TARGET_CATEGORYSCHEME);
    public static final ItemSchemePackageTypeCodelistType CONCEPTSCHEME = add(TARGET_CONCEPTSCHEME);
    
    // This isn't listed as a valid value for this type, however
    // the 'Dataflow' type needs this to be here.
    public static final String TARGET_DATASTRUCTURE = addString("datastructure");
    public static final ItemSchemePackageTypeCodelistType DATASTRUCTURE = add(TARGET_DATASTRUCTURE);

// Utility
    private static ItemSchemePackageTypeCodelistType add(String s){
        ItemSchemePackageTypeCodelistType b = new ItemSchemePackageTypeCodelistType(s);
        ENUM.add(b);
        return b;
    }
    private static String addString(String s){
        STRING_ENUM.add(s);
        return s;
    }
    
    public static ItemSchemePackageTypeCodelistType fromString(String s) {
        for(int i=0;i<ENUM.size();i++) {
            if( ENUM.get(i).target.equals(s))return ENUM.get(i);
        }
        return null;
    }
    public static ItemSchemePackageTypeCodelistType fromStringWithException(String s) throws TypeValueNotFoundException {
        for(int i=0;i<ENUM.size();i++) {
            if( ENUM.get(i).target.equals(s))return ENUM.get(i);
        }
        throw new TypeValueNotFoundException("Value:"+s+" not found in ItemSchemePackageTypeCodelistType enumeration!");
    }
// Instance
    private String target = null;
    public ItemSchemePackageTypeCodelistType(String s) {
        super(s);
        if( !STRING_ENUM.contains(s))throw new IllegalArgumentException(s+" is not a valid ObjectTypeCodelistType");
        this.target=s;
        
    }
    public String toString() { return target; }
}

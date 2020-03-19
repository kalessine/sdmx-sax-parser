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
package sdmx.query.base;

import java.util.ArrayList;
import java.util.List;
import sdmx.exception.TypeValueNotFoundException;

/**
 *	<xs:simpleType name="StructureReturnDetailType">
		<xs:annotation>
			<xs:documentation>StructureReturnDetailType contains a set of enumerations that indicate how much detail should be returned for an object.</xs:documentation>
		</xs:annotation>
		<xs:restriction base="xs:string">
			<xs:enumeration value="Stub">
				<xs:annotation>
					<xs:documentation>Only the identification information and name should be returned.</xs:documentation>
				</xs:annotation>
			</xs:enumeration>
			<xs:enumeration value="CompleteStub">
				<xs:annotation>
					<xs:documentation>Identification information, name, description, and annotations should be returned.</xs:documentation>
				</xs:annotation>
			</xs:enumeration>
			<xs:enumeration value="Full">
				<xs:annotation>
					<xs:documentation>The entire detail of the object should be returned.</xs:documentation>
				</xs:annotation>
			</xs:enumeration>
			<xs:enumeration value="MatchedItems">
				<xs:annotation>
					<xs:documentation>For an item scheme, only the items matching the item where parameters will be returned. In the case that items are hierarchical, the entire hierarchy leading to the matched item will have to be returned.</xs:documentation>
				</xs:annotation>
			</xs:enumeration>
			<xs:enumeration value="CascadedMatchedItems">
				<xs:annotation>
					<xs:documentation>For an item scheme, only the items matching the item where parameters, and their hierarchical child items will be returned. In the case that items are hierarchical, the entire hierarchy leading to the matched item will have to be returned.</xs:documentation>
				</xs:annotation>
			</xs:enumeration>
		</xs:restriction>
	</xs:simpleType>

 * @author James
 */

public class StructureReturnDetailType {

    public static final List<StructureReturnDetailType> ENUM = new ArrayList<StructureReturnDetailType>();
    public static final List<String> STRING_ENUM = new ArrayList<String>();
    public static final String STUB_TEXT = addString("Stub");
    public static final String COMPLETESTUB_TEXT = addString("CompleteStub");
    public static final String FULL_TEXT = addString("Full");
    public static final String MATCHED_ITEMS_TEXT = addString("MatchedItems");
    public static final String CASCADED_MATCHED_ITEMS_TEXT = addString("CascadedMatchedItems");
    public static final StructureReturnDetailType STUB = add("Stub");
    public static final StructureReturnDetailType COMPLETESTUB = add("CompleteStub");
    public static final StructureReturnDetailType FULL = add("Full");
    public static final StructureReturnDetailType MATCHED_ITEMS = add("MatchedItems");
    public static final StructureReturnDetailType CASCADED_MATCHED_ITEMS = add("CascadedMatchedItems");
    // Utility

    private static StructureReturnDetailType add(String s) {
        StructureReturnDetailType b = new StructureReturnDetailType(s);
        ENUM.add(b);
        return b;
    }

    private static String addString(String s) {
        STRING_ENUM.add(s);
        return s;
    }

    public static StructureReturnDetailType fromString(String s) {
        for (int i = 0; i < ENUM.size(); i++) {
            if (ENUM.get(i).target.equals(s)) {
                return ENUM.get(i);
            }
        }
        return null;
    }

    public static StructureReturnDetailType fromStringWithException(String s) throws TypeValueNotFoundException {
        for (int i = 0; i < ENUM.size(); i++) {
            if (ENUM.get(i).target.equals(s)) {
                return ENUM.get(i);
            }
        }
        throw new TypeValueNotFoundException("Value:" + s + " not found in DataType enumeration!");
    }
// Instance
    private String target = null;

    public StructureReturnDetailType(String s) {
        if (!STRING_ENUM.contains(s)) {
            throw new IllegalArgumentException(s + " is not a valid ObjectTypeCodelistType");
        }
        this.target = s;

    }

    public String toString() {
        return target;
    }    
}

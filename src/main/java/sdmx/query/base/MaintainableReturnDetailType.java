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
 *	<xs:simpleType name="MaintainableReturnDetailType">
		<xs:annotation>
			<xs:documentation>MaintainableReturnDetailType contains a sub set of the enumerations defined in the ReturnDetailType. Enumerations relating specifically to item schemes are not included</xs:documentation>
		</xs:annotation>
		<xs:restriction base="StructureReturnDetailType">
			<xs:enumeration value="Stub"/>
			<xs:enumeration value="CompleteStub"/>
			<xs:enumeration value="Full"/>
		</xs:restriction>
	</xs:simpleType>
 * @author James
 */

public class MaintainableReturnDetailType {

    public static final List<MaintainableReturnDetailType> ENUM = new ArrayList<MaintainableReturnDetailType>();
    public static final List<String> STRING_ENUM = new ArrayList<String>();
    public static final String STUB_TEXT = addString("Stub");
    public static final String COMPLETESTUB_TEXT = addString("CompleteStub");
    public static final String FULL_TEXT = addString("Full");
    public static final MaintainableReturnDetailType STUB = add("Stub");
    public static final MaintainableReturnDetailType COMPLETESTUB = add("CompleteStub");
    public static final MaintainableReturnDetailType FULL = add("Full");
    // Utility

    private static MaintainableReturnDetailType add(String s) {
        MaintainableReturnDetailType b = new MaintainableReturnDetailType(s);
        ENUM.add(b);
        return b;
    }

    private static String addString(String s) {
        STRING_ENUM.add(s);
        return s;
    }

    public static MaintainableReturnDetailType fromString(String s) {
        for (int i = 0; i < ENUM.size(); i++) {
            if (ENUM.get(i).target.equals(s)) {
                return ENUM.get(i);
            }
        }
        return null;
    }

    public static MaintainableReturnDetailType fromStringWithException(String s) throws TypeValueNotFoundException {
        for (int i = 0; i < ENUM.size(); i++) {
            if (ENUM.get(i).target.equals(s)) {
                return ENUM.get(i);
            }
        }
        throw new TypeValueNotFoundException("Value:" + s + " not found in DataType enumeration!");
    }
// Instance
    private String target = null;

    public MaintainableReturnDetailType(String s) {
        if (!STRING_ENUM.contains(s)) {
            throw new IllegalArgumentException(s + " is not a valid ObjectTypeCodelistType");
        }
        this.target = s;

    }

    public String toString() {
        return target;
    }
}

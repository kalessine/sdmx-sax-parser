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
package sdmx.common;

import java.util.ArrayList;
import java.util.List;
import sdmx.exception.TypeValueNotFoundException;

/**
 *	<xs:simpleType name="TextOperatorType">
		<xs:annotation>
			<xs:documentation></xs:documentation>
		</xs:annotation>
		<xs:union memberTypes="SimpleOperatorType TextSearchOperatorType"/>
	</xs:simpleType>
 * @author James
 */
public class TextOperatorType {
    public static void main(String args[]) {}
    public static final List<TextOperatorType> ENUM = new ArrayList<TextOperatorType>();
    public static final List<String> STRING_ENUM = new ArrayList<String>();
// Simple Operator
    public static final String NOT_EQUAL_TEXT = addString("notEqual");
    public static final String EQUAL_TEXT = addString("equal");
// Text Operator
    public static final String CONTAINS_TEXT = addString("contains");;
    public static final String STARTS_WITH_TEXT = addString("startsWith");;
    public static final String ENDS_WITH_TEXT = addString("endsWith");;
    public static final String DOES_NOT_CONTAIN_TEXT = addString("doesNotContain");;
    public static final String DOES_NOT_START_WITH_TEXT = addString("doesNotStartWith");;
    public static final String DOES_NOT_END_WITH_TEXT = addString("doesNotEndWith");;

// Simple
    public static final TextOperatorType NOT_EQUAL = add("notEqual");
    public static final TextOperatorType EQUAL = add("equal");
// Text
    public static final TextOperatorType CONTAINS = add("contains");
    public static final TextOperatorType STARTS_WITH = add("startsWith");
    public static final TextOperatorType ENDS_WITH = add("endsWith");
    public static final TextOperatorType DOES_NOT_CONTAIN = add("doesNotContain");
    public static final TextOperatorType DOES_NOT_START_WITH = add("doesNotStartWith");
    public static final TextOperatorType DOES_NOT_END_WITH = add("doesNotEndWith");
    
// Utility
    private static TextOperatorType add(String s) {
        TextOperatorType b = new TextOperatorType(s);
        ENUM.add(b);
        return b;
    }

    private static String addString(String s) {
        STRING_ENUM.add(s);
        return s;
    }

    public static TextOperatorType fromString(String s) {
        for (int i = 0; i < ENUM.size(); i++) {
            if (ENUM.get(i).target.equals(s)) {
                return ENUM.get(i);
            }
        }
        return null;
    }

    /**
     *
     * @param s
     * @return
     * @throws TypeValueNotFoundException
     */
    public static TextOperatorType fromStringWithException(String s) throws TypeValueNotFoundException {
        for (int i = 0; i < ENUM.size(); i++) {
            if (ENUM.get(i).target.equals(s)) {
                return ENUM.get(i);
            }
        }
        throw new TypeValueNotFoundException("Value:" + s + " not found in SimpleDataType enumeration!");
    }
// Instance
    private String target = null;

    public TextOperatorType(String s) {
        if (!STRING_ENUM.contains(s)) {
            throw new IllegalArgumentException(s + " is not a valid TextOperatorType");
        }
        this.target = s;
    }

    public String toString() {
        return target;
    }

}

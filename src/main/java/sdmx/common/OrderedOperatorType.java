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
 *	<xs:simpleType name="OrderedOperatorType">
		<xs:annotation>
			<xs:documentation>OrderedOperatorType combines the SimpleOperatorType and the RangeOperatorType to provide a full range or operators for any ordered value.</xs:documentation>
		</xs:annotation>
		<xs:union memberTypes="SimpleOperatorType RangeOperatorType"/>
	</xs:simpleType>
 * @author James
 */
public class OrderedOperatorType {

    public static final List<OrderedOperatorType> ENUM = new ArrayList<OrderedOperatorType>();
    public static final List<String> STRING_ENUM = new ArrayList<String>();

    // Simple
    public static final String NOT_EQUAL_TEXT = addString("notEqual");
    public static final String EQUAL_TEXT = addString("equal");
    // Range
    public static final String GREATER_THAN_OR_EQUAL_TEXT = addString("greaterThanOrEqual");
    public static final String LESS_THAN_OR_EQUAL_TEXT = addString("lessThanOrEqual");
    public static final String GREATER_THAN_TEXT =addString("greaterThan");
    public static final String LESS_THAN_TEXT =addString("lessThan");    
    public static final OrderedOperatorType NOT_EQUAL = add("notEqual");
    public static final OrderedOperatorType EQUAL = add("equal");
    // Range
    public static final OrderedOperatorType GREATER_THAN_OR_EQUAL = add("greaterThanOrEqual");
    public static final OrderedOperatorType LESS_THAN_OR_EQUAL = add("lessThanOrEqual");
    public static final OrderedOperatorType GREATER_THAN =add("greaterThan");
    public static final OrderedOperatorType LESS_THAN =add("lessThan");    
// Utility
    
    private static OrderedOperatorType add(String s) {
        OrderedOperatorType b = new OrderedOperatorType(s);
        ENUM.add(b);
        return b;
    }

    private static String addString(String s) {
        STRING_ENUM.add(s);
        return s;
    }

    public static OrderedOperatorType fromString(String s) {
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
    public static OrderedOperatorType fromStringWithException(String s) throws TypeValueNotFoundException {
        for (int i = 0; i < ENUM.size(); i++) {
            if (ENUM.get(i).target.equals(s)) {
                return ENUM.get(i);
            }
        }
        throw new TypeValueNotFoundException("Value:" + s + " not found in SimpleDataType enumeration!");
    }
// Instance
    private String target = null;

    public OrderedOperatorType(String s) {
        if (!STRING_ENUM.contains(s)) {
            throw new IllegalArgumentException(s + " is not a valid OrderedOperatorType");
        }
        this.target = s;
    }

    public String toString() {
        return target;
    }

}

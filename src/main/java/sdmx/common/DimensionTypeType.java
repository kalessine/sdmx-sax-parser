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
 *<xs:simpleType name="DimensionTypeType">
		<xs:annotation>
			<xs:documentation>DimensionTypeType enumerates the sub-classes of a dimension.</xs:documentation>
		</xs:annotation>
		<xs:restriction base="xs:string">
			<xs:enumeration value="Dimension">
				<xs:annotation>
					<xs:documentation>An ordinary dimension.</xs:documentation>
				</xs:annotation>
			</xs:enumeration>
			<xs:enumeration value="MeasureDimension">
				<xs:annotation>
					<xs:documentation>A measure dimension.</xs:documentation>
				</xs:annotation>
			</xs:enumeration>
			<xs:enumeration value="TimeDimension">
				<xs:annotation>
					<xs:documentation>The time dimension.</xs:documentation>
				</xs:annotation>
			</xs:enumeration>
		</xs:restriction>
	</xs:simpleType>
 * @author James
 */
public class DimensionTypeType {
    public static final List<DimensionTypeType> ENUM = new ArrayList<DimensionTypeType>();
    public static final List<String> STRING_ENUM = new ArrayList<String>();

     public static final String DIMENSION_TEXT = addString("Dimension");
     public static final String MEASURE_TEXT = addString("MeasureDimension");
     public static final String TIME_TEXT = addString("TimeDimension");
     
     public static final DimensionTypeType DIMENSION = add(DIMENSION_TEXT);
     public static final DimensionTypeType MEASURE = add(MEASURE_TEXT);
     public static final DimensionTypeType TIME = add(TIME_TEXT);
    
// Utility
    private static DimensionTypeType add(String s) {
        DimensionTypeType b = new DimensionTypeType(s);
        ENUM.add(b);
        return b;
    }

    private static String addString(String s) {
        STRING_ENUM.add(s);
        return s;
    }

    public static DimensionTypeType fromString(String s) {
        for (int i = 0; i < ENUM.size(); i++) {
            if (ENUM.get(i).target.equals(s)) {
                return ENUM.get(i);
            }
        }
        return null;
    }

    public static DimensionTypeType fromStringWithException(String s) throws TypeValueNotFoundException {
        for (int i = 0; i < ENUM.size(); i++) {
            if (ENUM.get(i).target.equals(s)) {
                return ENUM.get(i);
            }
        }
        throw new TypeValueNotFoundException("Value:" + s + " not found in DimensionTypeType enumeration!");
    }
// Instance
    private String target = null;

    public DimensionTypeType(String s) {
        if (!STRING_ENUM.contains(s)) {
            throw new IllegalArgumentException(s + " is not a valid ObjectTypeCodelistType");
        }
        this.target = s;

    }

    public String toString() {
        return target;
    }
}

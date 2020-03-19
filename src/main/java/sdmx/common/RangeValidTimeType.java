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

import java.util.regex.Pattern;

/**
 *	<xs:simpleType name="RangeValidTimeType">
		<xs:annotation>
			<xs:documentation>RangeValidTimeType is a derivation of the RangeValidLeapYearType which validates that the time (if provided) is validly formatted. The base type will have provided basic validation already. The patterns below validate that the time falls between 00:00:00 and 24:00:00. Note that as the XML dateTime type does, seconds are required. It is also permissible to have fractions of seconds, but only within the boundaries of the range specified. This type is meant to be derived from for further validation.</xs:documentation>
		</xs:annotation>
		<xs:restriction base="RangeValidLeapYearType">
			<xs:pattern value=".{10}T(24:00:00(\.[0]+)?|((([0-1][0-9])|(2[0-3])):[0-5][0-9]:[0-5][0-9](\.\d+)?))(/|Z|\+|\-).+"/>
			<xs:pattern value="[^T]+/.+"/>
		</xs:restriction>
	</xs:simpleType>
 * @author James
 */
public class RangeValidTimeType extends RangeValidLeapYearType {
      public static final String PATTERN1 = ".{10}T(24:00:00(\\.[0]+)?|((([0-1][0-9])|(2[0-3])):[0-5][0-9]:[0-5][0-9](\\.\\d+)?))(/|Z|\\+|\\-).+";
      public static final String PATTERN2 = "[^T]+/.+";
    public static final Pattern REGEX_PATTERN1 = Pattern.compile(PATTERN1);
    public static final Pattern REGEX_PATTERN2 = Pattern.compile(PATTERN2);
    public static final Pattern[] PATTERN_ARRAY = new Pattern[]{REGEX_PATTERN1,REGEX_PATTERN2};
    public RangeValidTimeType(String s) {
        super(s);
    }

    public Pattern[] getPatternArray() {
        return PATTERN_ARRAY;
    }
}

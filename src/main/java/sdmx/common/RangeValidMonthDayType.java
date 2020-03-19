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
 *	<xs:simpleType name="RangeValidMonthDayType">
		<xs:annotation>
			<xs:documentation>RangeValidMonthDayType is a derivation of the BaseTimeRangeType which validates that the day provided is valid for the month, without regard to leap years. The base type will have provided basic validation already. The patterns below validate that there are up to 29 days in February, up to 30 days in April, June, September, and November and up to 31 days in January, March, May, July, August, October, and December. This type is meant to be derived from for further validation.</xs:documentation>
		</xs:annotation>
		<xs:restriction base="BaseTimeRangeType">
			<xs:pattern value=".{5}02\-(0[1-9]|[1-2][0-9]).+"/>
			<xs:pattern value=".{5}(04|06|09|11)\-(0[1-9]|[1-2][0-9]|30).+"/>
			<xs:pattern value=".{5}(01|03|05|07|08|10|12)\-(0[1-9]|[1-2][0-9]|3[0-1]).+"/>
		</xs:restriction>
	</xs:simpleType>
 * @author James
 */
public class RangeValidMonthDayType extends BaseTimeRangeType {
    public static final String PATTERN1 = ".{5}02\\-(0[1-9]|[1-2][0-9]).+";
    public static final String PATTERN2 = ".{5}(04|06|09|11)\\-(0[1-9]|[1-2][0-9]|30).+";
    public static final String PATTERN3 = ".{5}(01|03|05|07|08|10|12)\\-(0[1-9]|[1-2][0-9]|3[0-1]).+";
    public static final Pattern REGEX_PATTERN1 = Pattern.compile(PATTERN1);
    public static final Pattern REGEX_PATTERN2 = Pattern.compile(PATTERN2);
    public static final Pattern REGEX_PATTERN3 = Pattern.compile(PATTERN3);
    public static final Pattern[] PATTERN_ARRAY = new Pattern[]{REGEX_PATTERN1,REGEX_PATTERN2,REGEX_PATTERN3};
    public RangeValidMonthDayType(String s) {
        super(s);
    }

    public Pattern[] getPatternArray() {
        return PATTERN_ARRAY;
    }
}

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
 *	<xs:simpleType name="TimeRangeValidDateDurationType">
		<xs:annotation>
			<xs:documentation>TimeRangeValidDateDurationType is an abstract derivation of the RangeValidTimeType which validates that duration provided is generally valid, up to the time component.</xs:documentation>
		</xs:annotation>
		<xs:restriction base="RangeValidTimeZoneType">
			<xs:pattern value=".+/P(\d+Y)?(\d+M)?(\d+D)?(T.+)?"/>
		</xs:restriction>
	</xs:simpleType>
 * @author James
 */
public class TimeRangeValidDateDurationType extends RangeValidTimeZoneType {
    public static final String PATTERN = ".+/P(\\d+Y)?(\\d+M)?(\\d+D)?(T.+)?";
    public static final Pattern REGEX_PATTERN1 = Pattern.compile(PATTERN1);
    public static final Pattern[] PATTERN_ARRAY = new Pattern[]{REGEX_PATTERN1};

    public TimeRangeValidDateDurationType(String s) {
        super(s);
    }

    public Pattern[] getPatternArray() {
        return PATTERN_ARRAY;
    }
}

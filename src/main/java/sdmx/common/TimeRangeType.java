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
 *	<xs:simpleType name="TimeRangeType">
		<xs:annotation>
			<xs:documentation>TimeRangeType defines the structure of a time range in SDMX. The pattern of a time range can be generally described as [start date]\[duration], where start date is an date or dateTime type as defined in XML Schema and duration is a time duration as defined in XML Schema. Note that it is permissible for a time zone offset to be provided on the date or date time.</xs:documentation>
		</xs:annotation>
		<xs:restriction base="TimeRangeValidDateDurationType">
			<xs:pattern value=".+/P.*T(\d+H)?(\d+M)?(\d+(.\d+)?S)?"/>
			<xs:pattern value=".+/P[^T]+"/>
		</xs:restriction>
	</xs:simpleType>

 * @author James
 */
public class TimeRangeType extends TimeRangeValidDateDurationType {
     public static final String PATTERN1 = ".+/P.*T(\\d+H)?(\\d+M)?(\\d+(.\\d+)?S)?";
     public static final String PATTERN2 = ".+/P[^T]+";
    public static final Pattern REGEX_PATTERN1 = Pattern.compile(PATTERN1);
    public static final Pattern REGEX_PATTERN2 = Pattern.compile(PATTERN2);
    public static final Pattern[] PATTERN_ARRAY = new Pattern[]{REGEX_PATTERN1,REGEX_PATTERN2};

    public TimeRangeType(String s) {
        super(s);
    }

    public Pattern[] getPatternArray() {
        return PATTERN_ARRAY;
    }
}

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
 *	<xs:simpleType name="ReportingTrimesterType">
		<xs:annotation>
			<xs:documentation>ReportingTrimesterType defines a time period of 4 months (P4M) in relation to a reporting year which has a start day (day-month) specified in the specialized reporting year start day attribute. In the absence of a start day for the reporting year, a day of January 1 is assumed. The format of a reporting trimester is YYYY-Tt (e.g. 2000-T1), where s is either 1, 2, or 3.</xs:documentation>
		</xs:annotation>
		<xs:restriction base="ReportPeriodValidTimeZoneType">
			<xs:pattern value=".{5}T[1-3].*"/>
		</xs:restriction>
	</xs:simpleType>
 * @author James
 */

public class ReportingTrimesterType extends ReportingTimePeriodType
{
    public static final String PATTERN = ".{5}T[1-3].*";
    public static final Pattern REGEX_PATTERN = Pattern.compile(PATTERN);
    public static final Pattern[] PATTERN_ARRAY = new Pattern[]{REGEX_PATTERN};
// Override Me
    public Pattern[] getPatternArray() {
        return PATTERN_ARRAY;
    }
    public ReportingTrimesterType(String s) {
        super(s);
    }
}

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
 *	<xs:simpleType name="RangeValidLeapYearType">
		<xs:annotation>
			<xs:documentation>RangeValidLeapYearType is a derivation of the RangeValidMonthDayType which validates that a date of February 29 occurs in a valid leap year (i.e. if the year is divisible 4 and not by 100, unless it is also divisible by 400). This type is meant to be derived from for further validation.</xs:documentation>
		</xs:annotation>
		<xs:restriction base="RangeValidMonthDayType">
			<xs:pattern value="((\d{2}(04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96))|((00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)00))\-02\-29.+"/>
			<xs:pattern value=".{5}02\-(([0-1][0-9])|(2[^9])).+"/>
			<xs:pattern value=".{5}((0[1,3-9])|1[0-2]).+"/>
		</xs:restriction>
	</xs:simpleType>
 * @author James
 */
public class RangeValidLeapYearType extends RangeValidMonthDayType {
     public static final String PATTERN1 = "((\\d{2}(04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96))|((00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)00))\\-02\\-29.+";
     public static final String PATTERN2 = ".{5}02\\-(([0-1][0-9])|(2[^9])).+";
     public static final String PATTERN3 = ".{5}((0[1,3-9])|1[0-2]).+";
    public static final Pattern REGEX_PATTERN1 = Pattern.compile(PATTERN1);
    public static final Pattern REGEX_PATTERN2 = Pattern.compile(PATTERN2);
    public static final Pattern REGEX_PATTERN3 = Pattern.compile(PATTERN3);
    public static final Pattern[] PATTERN_ARRAY = new Pattern[]{REGEX_PATTERN1,REGEX_PATTERN2,REGEX_PATTERN3};
    public RangeValidLeapYearType(String s) {
        super(s);
    }

    public Pattern[] getPatternArray() {
        return PATTERN_ARRAY;
    }
}

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
import sdmx.xml.RegexXMLString;

/**
 *	<xs:simpleType name="ReportingTimePeriodType">
		<xs:annotation>
			<xs:documentation>ReportingTimePeriodType defines standard reporting periods in SDMX, which are all in relation to the start day (day-month) of a reporting year which is specified in the specialized reporting year start day attribute. If the reporting year start day is not defined, a day of January 1 is assumed. The reporting year must be epxressed as the year at the beginning of the period. Therfore, if the reproting year runs from April to March, any given reporting year is expressed as the year for April. The general format of a report period can be described as  [year]-[period][time zone]?, where the type of period is designated with a single character followed by a number representing the period. Note that all periods allow for an optional time zone offset. See the details of each member type for the specifics of its format.</xs:documentation>
		</xs:annotation>
		<xs:union memberTypes="ReportingYearType ReportingSemesterType ReportingTrimesterType ReportingQuarterType ReportingMonthType ReportingWeekType ReportingDayType"/>
	</xs:simpleType>	
 * @author James
 */

public class ReportingTimePeriodType {
    // Year
    public static final String PATTERN_YEAR = ".{5}A1.*";
    // Semester
    public static final String PATTERN_SEMESTER = ".{5}S[1-2].*";
    // Trimester
    public static final String PATTERN_TRIMESTER = ".{5}T[1-3].*";
    // Quarter
    public static final String PATTERN_QUARTER = ".{5}Q[1-4].*";
    // Month
    public static final String PATTERN_MONTH = ".{5}M(0[1-9]|1[0-2]).*";
    // Week
    public static final String PATTERN_WEEK = ".{5}W(0[1-9]|[1-4][0-9]|5[0-3]).*";
    // Day
    public static final String PATTERN_DAY = ".{5}D(0[0-9][1-9]|[1-2][0-9][0-9]|3[0-5][0-9]|36[0-6]).*";
    
    public static final Pattern REGEX_YEAR = Pattern.compile(PATTERN_YEAR);
    public static final Pattern REGEX_SEMESTER = Pattern.compile(PATTERN_SEMESTER);
    public static final Pattern REGEX_TRIMESTER = Pattern.compile(PATTERN_TRIMESTER);
    public static final Pattern REGEX_QUARTER = Pattern.compile(PATTERN_QUARTER);
    public static final Pattern REGEX_MONTH = Pattern.compile(PATTERN_MONTH);
    public static final Pattern REGEX_WEEK = Pattern.compile(PATTERN_WEEK);
    public static final Pattern REGEX_DAY = Pattern.compile(PATTERN_DAY);
    
    public static final int YEAR = 1;
    public static final int SEMESTER = 2;
    public static final int TRIMESTER = 3;
    public static final int QUARTER = 4;
    public static final int MONTH = 5;
    public static final int WEEK = 6;
    public static final int DAY = 7;
    
    private int state = YEAR;
    private String value = null;
    
    public ReportingTimePeriodType(String s) {
        value=s;
        if( REGEX_YEAR.matcher(s).matches()) {
            state=YEAR;
        }
        else if( REGEX_SEMESTER.matcher(s).matches()) {
            state=SEMESTER;
        }
        else if( REGEX_TRIMESTER.matcher(s).matches()) {
            state=TRIMESTER;
        }
        else if( REGEX_QUARTER.matcher(s).matches()) {
            state=QUARTER;
        }
        else if( REGEX_MONTH.matcher(s).matches()) {
            state=MONTH;
        }
        else if( REGEX_WEEK.matcher(s).matches()) {
            state=WEEK;
        }
        else if( REGEX_DAY.matcher(s).matches()) {
            state=DAY;
        }
    }
    public String toString() {
        return value;
    }
}

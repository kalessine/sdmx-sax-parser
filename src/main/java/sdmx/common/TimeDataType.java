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

/**
 *	<xs:simpleType name="TimeDataType">
		<xs:annotation>
			<xs:documentation>TimeDataType restricts SimpleDataType to specify the allowable data types for representing a time value.</xs:documentation>
		</xs:annotation>
		<xs:restriction base="SimpleDataType">
			<xs:enumeration value="ObservationalTimePeriod"/>
			<xs:enumeration value="StandardTimePeriod"/>
			<xs:enumeration value="BasicTimePeriod"/>
			<xs:enumeration value="GregorianTimePeriod"/>
			<xs:enumeration value="GregorianYear"/>
			<xs:enumeration value="GregorianYearMonth"/>
			<xs:enumeration value="GregorianDay"/>
			<xs:enumeration value="ReportingTimePeriod"/>
			<xs:enumeration value="ReportingYear"/>
			<xs:enumeration value="ReportingSemester"/>
			<xs:enumeration value="ReportingTrimester"/>
			<xs:enumeration value="ReportingQuarter"/>
			<xs:enumeration value="ReportingMonth"/>
			<xs:enumeration value="ReportingWeek"/>
			<xs:enumeration value="ReportingDay"/>
			<xs:enumeration value="DateTime"/>
			<xs:enumeration value="TimeRange"/>
		</xs:restriction>
	</xs:simpleType>	
 * @author James
 */
public class TimeDataType {
public static final String OBSERVATIONAL_TIME_PERIOD_TEXT = "ObservationalTimePeriod";
			public static final String STANDARD_TIME_PERIOD_TEXT = "StandardTimePeriod";
			public static final String BASIC_TIME_PERIOD_TEXT = "BasicTimePeriod";
			public static final String GREGORIAN_TIME_PERIOD_TEXT = "GregorianTimePeriod";
			public static final String GREGORIAN_YEAR_TEXT = "GregorianYear";
			public static final String GREGORIAN_YEAR_MONTH_TEXT = "GregorianYearMonth";
			public static final String GREGORIAN_DAY_TEXT = "GregorianDay";
			public static final String REPORTING_TIME_PERIOD_TEXT = "ReportingTimePeriod";
			public static final String REPORTING_YEAR_TEXT = "ReportingYear";
			public static final String REPORTING_SEMESTER_TEXT = "ReportingSemester";
			public static final String REPORTING_TRIMESTER_TEXT = "ReportingTrimester";
			public static final String REPORTING_QUARTER_TEXT = "ReportingQuarter";
			public static final String REPORTING_MONTH_TEXT = "ReportingMonth";
			public static final String REPORTING_WEEK_TEXT = "ReportingWeek";
			public static final String REPORTING_DAY_TEXT = "ReportingDay";
			public static final String DATETIME_TEXT = "DateTime";
			public static final String TIME_RANGE_TEXT = "TimeRange";
}

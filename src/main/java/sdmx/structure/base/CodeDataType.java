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
package sdmx.structure.base;

import java.util.ArrayList;
import java.util.List;
import sdmx.common.SimpleDataType;
import sdmx.exception.TypeValueNotFoundException;

/**
 *	<xs:simpleType name="CodeDataType">
		<xs:annotation>
			<xs:documentation>CodeDataType is a restriction of the basic data types that are applicable to codes. Although some of the higher level time period formats are perimitted, it should be noted that any value which contains time (which includes a time zone offset) is not allowable as a code identifier.</xs:documentation>
		</xs:annotation>
		<xs:restriction base="common:SimpleDataType">
			<xs:enumeration value="String"/>
			<xs:enumeration value="Alpha"/>
			<xs:enumeration value="AlphaNumeric"/>
			<xs:enumeration value="Numeric"/>
			<xs:enumeration value="BigInteger"/>
			<xs:enumeration value="Integer"/>
			<xs:enumeration value="Long"/>
			<xs:enumeration value="Short"/>
			<xs:enumeration value="Boolean"/>
			<xs:enumeration value="URI"/>
			<xs:enumeration value="Count"/>
			<xs:enumeration value="InclusiveValueRange"/>
			<xs:enumeration value="ExclusiveValueRange"/>
			<xs:enumeration value="Incremental"/>
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
			<xs:enumeration value="Month"/>
			<xs:enumeration value="MonthDay"/>
			<xs:enumeration value="Day"/>
			<xs:enumeration value="Duration"/>
		</xs:restriction>
	</xs:simpleType>

 * @author James
 */

public class CodeDataType extends SimpleDataType  {
    
    public static final List<CodeDataType> ENUM = new ArrayList<CodeDataType>();
    public static final List<String> STRING_ENUM = new ArrayList<String>();
    
    public static final String STRING_TEXT = addString("String");
    public static final String ALPHA_TEXT = addString("Alpha");
    public static final String ALPHANUMERIC_TEXT = addString("AlphaNumeric");
    public static final String NUMERIC_TEXT = addString("Numeric");
    public static final String BIGINTEGER_TEXT = addString("BigInteger");
    public static final String INTEGER_TEXT = addString("Integer");
    public static final String LONG_TEXT = addString("Long");
    public static final String SHORT_TEXT = addString("Short");
    public static final String BOOLEAN_TEXT = addString("Boolean");
    public static final String URI_TEXT = addString("URI");
    public static final String COUNT_TEXT = addString("Count");
    public static final String INCLUSIVE_VALUE_RANGE_TEXT = addString("InclusiveValueRange");
    public static final String EXCLUSIVE_VALUE_RANGE_TEXT = addString("ExclusiveValueRange");
    public static final String INCREMENTAL_TEXT = addString("Incremental");
    public static final String OBSERVATIONAL_TIME_PERIOD_TEXT = addString("ObservationalTimePeriod");
    public static final String STANDARD_TIME_PERIOD_TEXT = addString("StandardTimePeriod");
    public static final String BASIC_TIME_PERIOD_TEXT = addString("BasicTimePeriod");
    public static final String GREGORIAN_TIME_PERIOD_TEXT = addString("GregorianTimePeriod");
    public static final String GREGORIAN_YEAR_TEXT = addString("GregorianYear");
    public static final String GREGORIAN_YEAR_MONTH_TEXT = addString("GregorianYearMonth");
    public static final String GREGORIAN_DAY_TEXT = addString("GregorianDay");
    public static final String REPORTING_TIME_PERIOD_TEXT = addString("ReportingTimePeriod");
    public static final String REPORTING_YEAR_TEXT = addString("ReportingYear");
    public static final String REPORTING_SEMESTER_TEXT = addString("ReportingSemester");
    public static final String REPORTING_TRIMESTER_TEXT = addString("ReportingTrimester");
    public static final String REPORTING_QUARTER_TEXT = addString("ReportingQuarter");
    public static final String REPORTING_MONTH_TEXT = addString("ReportingMonth");
    public static final String REPORTING_WEEK_TEXT = addString("ReportingWeek");
    public static final String REPORTING_DAY_TEXT = addString("ReportingDay");
    public static final String MONTH_TEXT = addString("Month");
    public static final String MONTHDAY_TEXT = addString("MonthDay");
    public static final String DAY_TEXT = addString("Day");
    public static final String TIME_TEXT = addString("Time");
    public static final String DURATION_TEXT = addString("Duration");

    public static final CodeDataType STRING = add(STRING_TEXT);
    public static final CodeDataType ALPHA = add(ALPHA_TEXT);
    public static final CodeDataType ALPHANUMERIC = add(ALPHANUMERIC_TEXT);
    public static final CodeDataType NUMERIC = add(NUMERIC_TEXT);
    public static final CodeDataType BIGINTEGER = add(BIGINTEGER_TEXT);
    public static final CodeDataType INTEGER = add(INTEGER_TEXT);
    public static final CodeDataType LONG = add(LONG_TEXT);
    public static final CodeDataType SHORT = add(SHORT_TEXT);
    public static final CodeDataType BOOLEAN = add(BOOLEAN_TEXT);
    public static final CodeDataType URI = add(URI_TEXT);
    public static final CodeDataType COUNT = add(COUNT_TEXT);
    public static final CodeDataType INCLUSIVE_VALUE_RANGE = add(INCLUSIVE_VALUE_RANGE_TEXT);
    public static final CodeDataType EXCLUSIVE_VALUE_RANGE = add(EXCLUSIVE_VALUE_RANGE_TEXT);
    public static final CodeDataType INCREMENTAL = add(INCREMENTAL_TEXT);
    public static final CodeDataType OBSERVATIONAL_TIME_PERIOD = add(OBSERVATIONAL_TIME_PERIOD_TEXT);
    public static final CodeDataType STANDARD_TIME_PERIOD = add(STANDARD_TIME_PERIOD_TEXT);
    public static final CodeDataType BASIC_TIME_PERIOD = add(BASIC_TIME_PERIOD_TEXT);
    public static final CodeDataType GREGORIAN_TIME_PERIOD = add(GREGORIAN_TIME_PERIOD_TEXT);
    public static final CodeDataType GREGORIAN_YEAR = add(GREGORIAN_YEAR_TEXT);
    public static final CodeDataType GREGORIAN_YEAR_MONTH = add(GREGORIAN_YEAR_MONTH_TEXT);
    public static final CodeDataType GREGORIAN_DAY = add(GREGORIAN_DAY_TEXT);
    public static final CodeDataType REPORTING_TIME_PERIOD = add(REPORTING_TIME_PERIOD_TEXT);
    public static final CodeDataType REPORTING_YEAR = add(REPORTING_YEAR_TEXT);
    public static final CodeDataType REPORTING_SEMESTER = add(REPORTING_SEMESTER_TEXT);
    public static final CodeDataType REPORTING_TRIMESTER = add(REPORTING_TRIMESTER_TEXT);
    public static final CodeDataType REPORTING_QUARTER = add(REPORTING_QUARTER_TEXT);
    public static final CodeDataType REPORTING_MONTH = add(REPORTING_MONTH_TEXT);
    public static final CodeDataType REPORTING_WEEK = add(REPORTING_WEEK_TEXT);
    public static final CodeDataType REPORTING_DAY = add(REPORTING_DAY_TEXT);
    public static final CodeDataType MONTH = add(MONTH_TEXT);
    public static final CodeDataType MONTHDAY = add(MONTHDAY_TEXT);
    public static final CodeDataType DAY = add(DAY_TEXT);
    public static final CodeDataType TIME = add(TIME_TEXT);
    public static final CodeDataType DURATION = add(DURATION_TEXT);

// Utility
    private static CodeDataType add(String s) {
        CodeDataType b = new CodeDataType(s);
        ENUM.add(b);
        return b;
    }

    private static String addString(String s) {
        STRING_ENUM.add(s);
        return s;
    }

    public static CodeDataType fromString(String s) {
        for (int i = 0; i < ENUM.size(); i++) {
            if (ENUM.get(i).toString().equals(s)) {
                return ENUM.get(i);
            }
        }
        return null;
    }


    public static CodeDataType fromStringWithException(String s) throws TypeValueNotFoundException {
        for (int i = 0; i < ENUM.size(); i++) {
            if (ENUM.get(i).toString().equals(s)) {
                return ENUM.get(i);
            }
        }
        throw new TypeValueNotFoundException("Value:" + s + " not found in CodeDataType enumeration!");
    }

    public CodeDataType(String s) {
        super(s);
        if (!STRING_ENUM.contains(s)) {
            throw new IllegalArgumentException(s + " is not a valid CodeDataType");
        }
    }

}

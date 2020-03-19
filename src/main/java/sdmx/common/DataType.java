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
import sdmx.commonreferences.types.ItemSchemePackageTypeCodelistType;
import sdmx.exception.TypeValueNotFoundException;

/**
 * <xs:simpleType name="DataType"> <xs:annotation>
 * <xs:documentation>DataTypeType provides an enumerated list of the types of
 * data formats allowed as the for the representation of an
 * object.</xs:documentation> </xs:annotation> <xs:restriction
 * base="xs:NMTOKEN"> <xs:enumeration value="String"> <xs:annotation>
 * <xs:documentation>A string datatype corresponding to W3C XML Schema's
 * xs:string datatype.</xs:documentation> </xs:annotation> </xs:enumeration>
 * <xs:enumeration value="Alpha"> <xs:annotation> <xs:documentation>A string
 * datatype which only allows for the simple aplhabetic charcter set of
 * A-z.</xs:documentation> </xs:annotation> </xs:enumeration> <xs:enumeration
 * value="AlphaNumeric"> <xs:annotation> <xs:documentation>A string datatype
 * which only allows for the simple alphabetic character set of A-z plus the
 * simple numeric character set of 0-9.</xs:documentation> </xs:annotation>
 * </xs:enumeration> <xs:enumeration value="Numeric"> <xs:annotation>
 * <xs:documentation>A string datatype which only allows for the simple numeric
 * character set of 0-9. This format is not treated as an integer, and therefore
 * can having leading zeros.</xs:documentation> </xs:annotation>
 * </xs:enumeration> <xs:enumeration value="BigInteger"> <xs:annotation>
 * <xs:documentation>An integer datatype corresponding to W3C XML Schema's
 * xs:integer datatype.</xs:documentation> </xs:annotation> </xs:enumeration>
 * <xs:enumeration value="Integer"> <xs:annotation> <xs:documentation>An integer
 * datatype corresponding to W3C XML Schema's xs:int
 * datatype.</xs:documentation> </xs:annotation> </xs:enumeration>
 * <xs:enumeration value="Long"> <xs:annotation> <xs:documentation>A numeric
 * datatype corresponding to W3C XML Schema's xs:long
 * datatype.</xs:documentation> </xs:annotation> </xs:enumeration>
 * <xs:enumeration value="Short"> <xs:annotation> <xs:documentation>A numeric
 * datatype corresponding to W3C XML Schema's xs:short
 * datatype.</xs:documentation> </xs:annotation> </xs:enumeration>
 * <xs:enumeration value="Decimal"> <xs:annotation> <xs:documentation>A numeric
 * datatype corresponding to W3C XML Schema's xs:decimal
 * datatype.</xs:documentation> </xs:annotation> </xs:enumeration>
 * <xs:enumeration value="Float"> <xs:annotation> <xs:documentation>A numeric
 * datatype corresponding to W3C XML Schema's xs:float
 * datatype.</xs:documentation> </xs:annotation> </xs:enumeration>
 * <xs:enumeration value="Double"> <xs:annotation> <xs:documentation>A numeric
 * datatype corresponding to W3C XML Schema's xs:double
 * datatype.</xs:documentation> </xs:annotation> </xs:enumeration>
 * <xs:enumeration value="Boolean"> <xs:annotation> <xs:documentation>A datatype
 * corresponding to W3C XML Schema's xs:boolean datatype.</xs:documentation>
 * </xs:annotation> </xs:enumeration> <xs:enumeration value="URI">
 * <xs:annotation> <xs:documentation>A datatype corresponding to W3C XML
 * Schema's xs:anyURI datatype.</xs:documentation> </xs:annotation>
 * </xs:enumeration> <xs:enumeration value="Count"> <xs:annotation>
 * <xs:documentation>A simple incrementing Integer type. The isSequence facet
 * must be set to true, and the interval facet must be set to
 * "1".</xs:documentation> </xs:annotation> </xs:enumeration> <xs:enumeration
 * value="InclusiveValueRange"> <xs:annotation> <xs:documentation>This value
 * indicates that the startValue and endValue attributes provide the inclusive
 * boundaries of a numeric range of type xs:decimal.</xs:documentation>
 * </xs:annotation> </xs:enumeration> <xs:enumeration
 * value="ExclusiveValueRange"> <xs:annotation> <xs:documentation>This value
 * indicates that the startValue and endValue attributes provide the exclusive
 * boundaries of a numeric range, of type xs:decimal.</xs:documentation>
 * </xs:annotation> </xs:enumeration> <xs:enumeration value="Incremental">
 * <xs:annotation>8 <xs:documentation>This value indicates that the value
 * increments according to the value provided in the interval facet, and has a
 * true value for the isSequence facet.</xs:documentation> </xs:annotation>
 * </xs:enumeration> <xs:enumeration value="ObservationalTimePeriod">
 * <xs:annotation> <xs:documentation>Observational time periods are the superset
 * of all time periods in SDMX. It is the union of the standard time periods
 * (i.e. Gregorian time periods, the reporting time periods, and date time) and
 * a time range.</xs:documentation> </xs:annotation> </xs:enumeration>
 * <xs:enumeration value="StandardTimePeriod"> <xs:annotation>
 * <xs:documentation>Standard time periods is a superset of distinct time period
 * in SDMX. It is the union of the basic time periods (i.e. the Gregorian time
 * periods and date time) and the reporting time periods.</xs:documentation>
 * </xs:annotation> </xs:enumeration> <xs:enumeration value="BasicTimePeriod">
 * <xs:annotation> <xs:documentation>BasicTimePeriod time periods is a superset
 * of the Gregorian time periods and a date time.</xs:documentation>
 * </xs:annotation> </xs:enumeration> <xs:enumeration
 * value="GregorianTimePeriod"> <xs:annotation> <xs:documentation>Gregorian time
 * periods correspond to calendar periods and are represented in ISO-8601
 * formats. This is the union of the year, year month, and date
 * formats.</xs:documentation> </xs:annotation> </xs:enumeration>
 * <xs:enumeration value="GregorianYear"> <xs:annotation> <xs:documentation>A
 * Gregorian time period corresponding to W3C XML Schema's xs:gYear datatype,
 * which is based on ISO-8601.</xs:documentation> </xs:annotation>
 * </xs:enumeration> <xs:enumeration value="GregorianYearMonth"> <xs:annotation>
 * <xs:documentation>A time datatype corresponding to W3C XML Schema's
 * xs:gYearMonth datatype, which is based on ISO-8601.</xs:documentation>
 * </xs:annotation> </xs:enumeration> <xs:enumeration value="GregorianDay">
 * <xs:annotation> <xs:documentation>A time datatype corresponding to W3C XML
 * Schema's xs:date datatype, which is based on ISO-8601.</xs:documentation>
 * </xs:annotation> </xs:enumeration> <xs:enumeration
 * value="ReportingTimePeriod"> <xs:annotation> <xs:documentation>Reporting time
 * periods represent periods of a standard length within a reporting year, where
 * to start of the year (defined as a month and day) must be defined elsewhere
 * or it is assumed to be January 1. This is the union of the reporting year,
 * semester, trimester, quarter, month, week, and day.</xs:documentation>
 * </xs:annotation> </xs:enumeration> <xs:enumeration value="ReportingYear">
 * <xs:annotation> <xs:documentation>A reporting year represents a period of 1
 * year (P1Y) from the start date of the reporting year. This is expressed as
 * using the SDMX specific ReportingYearType.</xs:documentation>
 * </xs:annotation> </xs:enumeration> <xs:enumeration value="ReportingSemester">
 * <xs:annotation> <xs:documentation>A reporting semester represents a period of
 * 6 months (P6M) from the start date of the reporting year. This is expressed
 * as using the SDMX specific ReportingSemesterType.</xs:documentation>
 * </xs:annotation> </xs:enumeration> <xs:enumeration
 * value="ReportingTrimester"> <xs:annotation> <xs:documentation>A reporting
 * trimester represents a period of 4 months (P4M) from the start date of the
 * reporting year. This is expressed as using the SDMX specific
 * ReportingTrimesterType.</xs:documentation> </xs:annotation> </xs:enumeration>
 * <xs:enumeration value="ReportingQuarter"> <xs:annotation> <xs:documentation>A
 * reporting quarter represents a period of 3 months (P3M) from the start date
 * of the reporting year. This is expressed as using the SDMX specific
 * ReportingQuarterType.</xs:documentation> </xs:annotation> </xs:enumeration>
 * <xs:enumeration value="ReportingMonth"> <xs:annotation> <xs:documentation>A
 * reporting month represents a period of 1 month (P1M) from the start date of
 * the reporting year. This is expressed as using the SDMX specific
 * ReportingMonthType.</xs:documentation> </xs:annotation> </xs:enumeration>
 * <xs:enumeration value="ReportingWeek"> <xs:annotation> <xs:documentation>A
 * reporting week represents a period of 7 days (P7D) from the start date of the
 * reporting year. This is expressed as using the SDMX specific
 * ReportingWeekType.</xs:documentation> </xs:annotation> </xs:enumeration>
 * <xs:enumeration value="ReportingDay"> <xs:annotation> <xs:documentation>A
 * reporting day represents a period of 1 day (P1D) from the start date of the
 * reporting year. This is expressed as using the SDMX specific
 * ReportingDayType.</xs:documentation> </xs:annotation> </xs:enumeration>
 * <xs:enumeration value="DateTime"> <xs:annotation> <xs:documentation>A time
 * datatype corresponding to W3C XML Schema's xs:dateTime
 * datatype.</xs:documentation> </xs:annotation> </xs:enumeration>
 * <xs:enumeration value="TimeRange"> <xs:annotation>
 * <xs:documentation>TimeRange defines a time period by providing a distinct
 * start (date or date time) and a duration.</xs:documentation> </xs:annotation>
 * </xs:enumeration> <xs:enumeration value="Month"> <xs:annotation>
 * <xs:documentation>A time datatype corresponding to W3C XML Schema's xs:gMonth
 * datatype.</xs:documentation> </xs:annotation> </xs:enumeration>
 * <xs:enumeration value="MonthDay"> <xs:annotation> <xs:documentation>A time
 * datatype corresponding to W3C XML Schema's xs:gMonthDay
 * datatype.</xs:documentation> </xs:annotation> </xs:enumeration>
 * <xs:enumeration value="Day"> <xs:annotation> <xs:documentation>A time
 * datatype corresponding to W3C XML Schema's xs:gDay
 * datatype.</xs:documentation> </xs:annotation> </xs:enumeration>
 * <xs:enumeration value="Time"> <xs:annotation> <xs:documentation>A time
 * datatype corresponding to W3C XML Schema's xs:time
 * datatype.</xs:documentation> </xs:annotation> </xs:enumeration>
 * <xs:enumeration value="Duration"> <xs:annotation> <xs:documentation>A time
 * datatype corresponding to W3C XML Schema's xs:duration
 * datatype.</xs:documentation> </xs:annotation> </xs:enumeration>
 * <xs:enumeration value="XHTML"> <xs:annotation> <xs:documentation>This value
 * indicates that the content of the component can contain XHTML
 * markup.</xs:documentation> </xs:annotation> </xs:enumeration> <xs:enumeration
 * value="KeyValues"> <xs:annotation> <xs:documentation>This value indicates
 * that the content of the component will be data key (a set of dimension
 * references and values for the dimensions).</xs:documentation>
 * </xs:annotation> </xs:enumeration> <xs:enumeration
 * value="IdentifiableReference"> <xs:annotation> <xs:documentation>This value
 * indicates that the content of the component will be complete reference
 * (either URN or full set of reference fields) to an Identifiable object in the
 * SDMX Information Model.</xs:documentation> </xs:annotation> </xs:enumeration>
 * <xs:enumeration value="DataSetReference"> <xs:annotation>
 * <xs:documentation>This value indicates that the content of the component will
 * be reference to a data provider, which is actually a formal reference to a
 * data provider and a data set identifier value.</xs:documentation>
 * </xs:annotation> </xs:enumeration> <xs:enumeration
 * value="AttachmentConstraintReference"> <xs:annotation> <xs:documentation>This
 * value indicates that the content of the component will be reference to an
 * attachment constraint, which is actually a combination of a collection of
 * full or partial key values and a reference to a data set or data structure,
 * usage, or provision agreement.</xs:documentation> </xs:annotation>
 * </xs:enumeration> </xs:restriction> </xs:simpleType>
 *
 * @author James
 */
public class DataType {

    public static final List<DataType> ENUM = new ArrayList<DataType>();
    public static final List<String> STRING_ENUM = new ArrayList<String>();
    public static final String STRING_TEXT = addString("String");
    public static final String ALPHA_TEXT = addString("Alpha");
    public static final String ALPHANUMERIC_TEXT = addString("AlphaNumeric");
    public static final String NUMERIC_TEXT = addString("Numeric");
    public static final String BIGINTEGER_TEXT = addString("BigInteger");
    public static final String INTEGER_TEXT = addString("Integer");
    public static final String LONG_TEXT = addString("Long");
    public static final String SHORT_TEXT = addString("Short");
    public static final String DECIMAL_TEXT = addString("Decimal");
    public static final String FLOAT_TEXT = addString("Float");
    public static final String DOUBLE_TEXT = addString("Double");
    public static final String BOOLEAN_TEXT = addString("Boolean");
    public static final String URI_TEXT = addString("URI");
    public static final String COUNT_TEXT = addString("Count");
    public static final String INCLUSIVEVALUERANGE_TEXT = addString("InclusiveValueRange");
    public static final String EXCLUSIVEVALUERANGE_TEXT = addString("ExclusiveValueRange");
    public static final String INCREMENTAL_TEXT = addString("Incremental");
    public static final String OBSERVATIONAL_TIMEPERIOD_TEXT = addString("ObservationalTimePeriod");
    public static final String STANDARD_TIMEPERIOD_TEXT = addString("StandardTimePeriod");
    public static final String BASIC_TIMEPERIOD_TEXT = addString("BasicTimePeriod");
    public static final String GREGORIAN_TIMEPERIOD_TEXT = addString("GregorianTimePeriod");
    public static final String GREGORIAN_YEAR_TEXT = addString("GregorianYear");
    public static final String GREGORIAN_YEARMONTH_TEXT = addString("GregorianYearMonth");
    public static final String GREGORIAN_DAY_TEXT = addString("GregorianDay");
    public static final String REPORTING_TIMEPERIOD_TEXT = addString("ReportingTimePeriod");
    public static final String REPORTING_YEAR_TEXT = addString("ReportingYear");
    public static final String REPORTING_SEMESTER_TEXT = addString("ReportingSemester");
    public static final String REPORTING_TRIMESTER_TEXT = addString("ReportingTrimester");
    public static final String REPORTING_QUARTER_TEXT = addString("ReportingQuarter");
    public static final String REPORTING_MONTH_TEXT = addString("ReportingMonth");
    public static final String REPORTING_WEEK_TEXT = addString("ReportingWeek");
    public static final String REPORTING_DAY_TEXT = addString("ReportingDay");
    public static final String DATETIME_TEXT = addString("DateTime");
    public static final String TIMERANGE_TEXT = addString("TimeRange");
    public static final String MONTH_TEXT = addString("Month");
    public static final String MONTH_DAY_TEXT = addString("MonthDay");
    public static final String DAY_TEXT = addString("Day");
    public static final String TIME_TEXT = addString("Time");
    public static final String DURATION_TEXT = addString("Duration");
    public static final String XHTML_TEXT = addString("XHTML");
    public static final String KEYVALUES_TEXT = addString("KeyValues");
    public static final String IDENTIFIABLE_REFERENCE_TEXT = addString("IdentifiableReference");
    public static final String DATASET_REFERENCE_TEXT = addString("DataSetReference");
    public static final String ATTACHMENT_CONSTRAINT_REFERENCE_TEXT = addString("AttachmentConstraintReference");
    public static final DataType STRING = add("String");
    public static final DataType ALPHA = add("Alpha");
    public static final DataType ALPHANUMERIC = add("AlphaNumeric");
    public static final DataType NUMERIC = add("Numeric");
    public static final DataType BIGINTEGER = add("BigInteger");
    public static final DataType INTEGER = add("Integer");
    public static final DataType LONG = add("Long");
    public static final DataType SHORT = add("Short");
    public static final DataType DECIMAL = add("Decimal");
    public static final DataType FLOAT = add("Float");
    public static final DataType DOUBLE = add("Double");
    public static final DataType BOOLEAN = add("Boolean");
    public static final DataType URI = add("URI");
    public static final DataType COUNT = add("Count");
    public static final DataType INCLUSIVEVALUERANGE = add("InclusiveValueRange");
    public static final DataType EXCLUSIVEVALUERANGE = add("ExclusiveValueRange");
    public static final DataType INCREMENTAL = add("Incremental");
    public static final DataType OBSERVATIONAL_TIMEPERIOD = add("ObservationalTimePeriod");
    public static final DataType STANDARD_TIMEPERIOD = add("StandardTimePeriod");
    public static final DataType BASIC_TIMEPERIOD = add("BasicTimePeriod");
    public static final DataType GREGORIAN_TIMEPERIOD = add("GregorianTimePeriod");
    public static final DataType GREGORIAN_YEAR = add("GregorianYear");
    public static final DataType GREGORIAN_YEARMONTH = add("GregorianYearMonth");
    public static final DataType GREGORIAN_DAY = add("GregorianDay");
    public static final DataType REPORTING_TIMEPERIOD = add("ReportingTimePeriod");
    public static final DataType REPORTING_YEAR = add("ReportingYear");
    public static final DataType REPORTING_SEMESTER = add("ReportingSemester");
    public static final DataType REPORTING_TRIMESTER = add("ReportingTrimester");
    public static final DataType REPORTING_QUARTER = add("ReportingQuarter");
    public static final DataType REPORTING_MONTH = add("ReportingMonth");
    public static final DataType REPORTING_WEEK = add("ReportingWeek");
    public static final DataType REPORTING_DAY = add("ReportingDay");
    public static final DataType DATETIME = add("DateTime");
    public static final DataType TIMERANGE = add("TimeRange");
    public static final DataType MONTH = add("Month");
    public static final DataType MONTH_DAY = add("MonthDay");
    public static final DataType DAY = add("Day");
    public static final DataType TIME = add("Time");
    public static final DataType DURATION = add("Duration");
    public static final DataType XHTML = add("XHTML");
    public static final DataType KEYVALUES = add("KeyValues");
    public static final DataType IDENTIFIABLE_REFERENCE = add("IdentifiableReference");
    public static final DataType DATASET_REFERENCE = add("DataSetReference");
    public static final DataType ATTACHMENT_CONSTRAINT_REFERENCE = add("AttachmentConstraintReference");
// Utility

    private static DataType add(String s) {
        DataType b = new DataType(s);
        ENUM.add(b);
        return b;
    }

    private static String addString(String s) {
        STRING_ENUM.add(s);
        return s;
    }

    public static DataType fromString(String s) {
        for (int i = 0; i < ENUM.size(); i++) {
            if (ENUM.get(i).target.equals(s)) {
                return ENUM.get(i);
            }
        }
        return null;
    }

    public static DataType fromStringWithException(String s) throws TypeValueNotFoundException {
        for (int i = 0; i < ENUM.size(); i++) {
            if (ENUM.get(i).target.equals(s)) {
                return ENUM.get(i);
            }
        }
        throw new TypeValueNotFoundException("Value:" + s + " not found in DataType enumeration!");
    }
// Instance
    private String target = null;

    public DataType(String s) {
        if (!STRING_ENUM.contains(s)) {
            throw new IllegalArgumentException(s + " is not a valid ObjectTypeCodelistType");
        }
        this.target = s;

    }

    public String toString() {
        return target;
    }
}

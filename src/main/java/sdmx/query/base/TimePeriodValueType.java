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
package sdmx.query.base;

import sdmx.common.ObservationalTimePeriodType;
import sdmx.common.TimeOperatorType;

/**
 *	<xs:complexType name="TimePeriodValueType">
		<xs:annotation>
			<xs:documentation>TimePeriodValueType describes the structure of a time period query. A value is provided as the content in the SDMX time period format.</xs:documentation>
		</xs:annotation>
		<xs:simpleContent>
			<xs:extension base="common:ObservationalTimePeriodType">
				<xs:attribute name="operator" type="common:TimeOperatorType" default="equal">
					<xs:annotation>
						<xs:documentation>The operator attribute indicates the operator to apply to the value query, such as equal to or greater than.</xs:documentation>
					</xs:annotation>
				</xs:attribute>
				<xs:attribute name="reportingYearStartDay" type="ReportingYearStartDayQueryType" use="optional" default="Any">
					<xs:annotation>
						<xs:documentation>The reportingYearStartDay attribute allows a reporting year start day to be specified for the reporting period time value. If this time value provided is not a report period, this value can be ignored. If an explicit value is provided, this will effectively turn the time parameter into a distinct time range. For example if the time parameter value is "2010-Q1" and this attribute has a value of "--04-01", the parameter will be treated as "2010-04-01/2010-06-30". If a value of "Any" is provided, then data will be matched regardless of its reporting year start day. For example, a query of 2011-A1 would return all data that belongs to a reporting year of 2011, regardless of the start day of the reporting year. For the puroses of matching data reporting against a Gregorian period against a time parameter value that is a reporting period, a value of "Any" will be treated as a start day of January 1. Therefore, if the time paramter value was 2011-A1, data reported against 2011 would be matched but data reporting against '2011-06/P1Y' would not be matched.</xs:documentation>
					</xs:annotation>
				</xs:attribute>
			</xs:extension>
		</xs:simpleContent>
	</xs:complexType>
 * @author James
 */

public class TimePeriodValueType extends ObservationalTimePeriodType {
    private TimeOperatorType operator = TimeOperatorType.EQUAL;
    private ReportingYearStartDayQueryType reportingYearStartDay = null;
    public TimePeriodValueType(String s,TimeOperatorType operator){
        super(s);
        this.operator = operator;
    }

    /**
     * @return the operator
     */
    public TimeOperatorType getOperator() {
        return operator;
    }

    /**
     * @param operator the operator to set
     */
    public void setOperator(TimeOperatorType operator) {
        this.operator = operator;
    }

    /**
     * @return the reportingYearStartDay
     */
    public ReportingYearStartDayQueryType getReportingYearStartDay() {
        return reportingYearStartDay;
    }

    /**
     * @param reportingYearStartDay the reportingYearStartDay to set
     */
    public void setReportingYearStartDay(ReportingYearStartDayQueryType reportingYearStartDay) {
        this.reportingYearStartDay = reportingYearStartDay;
    }
}

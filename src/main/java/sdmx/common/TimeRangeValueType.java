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

import sdmx.common.choice.ComponentValueSetTypeChoice;
import sdmx.common.choice.CubeRegionKeyTypeChoice;
import sdmx.common.choice.MetadataTargetRegionKeyTypeChoice;
import sdmx.common.choice.TimeRangeValueTypeChoice;

/**
 *	<xs:complexType name="TimeRangeValueType">
		<xs:annotation>
			<xs:documentation>TimeRangeValueType allows a time period value to be expressed as a range. It can be expressed as the period before a period, after a period, or between two periods. Each of these properties can specify their inclusion in regards to the range.</xs:documentation>
		</xs:annotation>
		<xs:choice>
			<xs:element name="BeforePeriod" type="TimePeriodRangeType">
				<xs:annotation>
					<xs:documentation>BeforePeriod is the period before which the period is meant to cover. This date may be inclusive or exclusive in the range.</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element name="AfterPeriod" type="TimePeriodRangeType">
				<xs:annotation>
					<xs:documentation>AfterPeriod is the period after which the period is meant to cover. This date may be inclusive or exclusive in the range.</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:sequence>
				<xs:element name="StartPeriod" type="TimePeriodRangeType">
					<xs:annotation>
						<xs:documentation>StartPeriod is the start date or the range that the queried date must occur within. This date may be inclusive or exclusive in the range.</xs:documentation>
					</xs:annotation>
				</xs:element>
				<xs:element name="EndPeriod" type="TimePeriodRangeType">
					<xs:annotation>
						<xs:documentation>EndPeriod is the end period of the range. This date may be inclusive or exclusive in the range.</xs:documentation>
					</xs:annotation>
				</xs:element>
			</xs:sequence>
		</xs:choice>
	</xs:complexType>
 * @author James
 */
public class TimeRangeValueType implements ComponentValueSetTypeChoice,CubeRegionKeyTypeChoice,MetadataTargetRegionKeyTypeChoice {
    TimeRangeValueTypeChoice choice = null;
    public TimeRangeValueType(TimeRangeValueTypeChoice choice) {
        this.choice=choice;
    }
}

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

import sdmx.xml.DateTime;

/**
 *	<xs:complexType name="ReferencePeriodType">
		<xs:annotation>
			<xs:documentation>Specifies the inclusive start and end times.</xs:documentation>
		</xs:annotation>
		<xs:attribute name="startTime" type="xs:dateTime" use="required">
			<xs:annotation>
				<xs:documentation>The startTime attributes contains the inclusive start date for the reference period.</xs:documentation>
			</xs:annotation>
		</xs:attribute>
		<xs:attribute name="endTime" type="xs:dateTime" use="required">
			<xs:annotation>
				<xs:documentation>The endTime attributes contains the inclusive end date for the reference period.</xs:documentation>
			</xs:annotation>
		</xs:attribute>
	</xs:complexType>
 * @author James
 */
public class ReferencePeriodType {
    private DateTime startTime = null;
    private DateTime endTime = null;
    public ReferencePeriodType(DateTime start,DateTime end) {
        startTime =start;
        endTime=end;
    }

    /**
     * @return the startTime
     */
    public DateTime getStartTime() {
        return startTime;
    }

    /**
     * @return the endTime
     */
    public DateTime getEndTime() {
        return endTime;
    }

}

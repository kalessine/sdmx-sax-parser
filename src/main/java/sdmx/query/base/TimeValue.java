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

import sdmx.common.TimeOperatorType;

/**
 *	<xs:element name="TimeValue" type="TimePeriodValueType">
		<xs:annotation>
			<xs:documentation>TimeValue is used to query for the value of a concept or component based on time parameters. This is typically used when the value needs to be treated explicitly as a time, for example when searching for data after a particular point in time. If only a simple equality check is necessary, the Value element can be used.</xs:documentation>
		</xs:annotation>
	</xs:element>
 * @author James
 */

public class TimeValue extends TimePeriodValueType {
    public TimeValue(String s) {
        super(s,TimeOperatorType.EQUAL);
    }
    public TimeValue(String s, TimeOperatorType operator) {
        super(s,operator);
    }
}

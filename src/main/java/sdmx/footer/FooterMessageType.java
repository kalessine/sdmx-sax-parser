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
package sdmx.footer;

import sdmx.common.CodedStatusMessageType;

/**
 *	<xs:complexType name="FooterMessageType">
		<xs:annotation>
			<xs:documentation>FooterMessageType defines the structure of a message that is contained in the footer of a message. It is a status message that have a severity code of Error, Information, or Warning added to it.</xs:documentation>
		</xs:annotation>
		<xs:complexContent>
			<xs:extension base="common:CodedStatusMessageType">
				<xs:attribute name="severity" type="SeverityCodeType" use="optional"/>
			</xs:extension>
		</xs:complexContent>
	</xs:complexType>

 * @author James
 */

public class FooterMessageType extends CodedStatusMessageType {
    private SeverityCodeType severity;

    /**
     * @return the severity
     */
    public SeverityCodeType getSeverity() {
        return severity;
    }

    /**
     * @param severity the severity to set
     */
    public void setSeverity(SeverityCodeType severity) {
        this.severity = severity;
    }
}

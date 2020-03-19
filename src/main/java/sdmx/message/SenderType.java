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
package sdmx.message;

import sdmx.common.TimezoneType;

/**
 *	<xs:complexType name="SenderType">
		<xs:annotation>
			<xs:documentation>SenderType extends the basic party structure to add an optional time zone declaration.</xs:documentation>
		</xs:annotation>
		<xs:complexContent>
			<xs:extension base="PartyType">
				<xs:sequence>
					<xs:element name="Timezone" type="common:TimezoneType" minOccurs="0">
						<xs:annotation>
							<xs:documentation>Timezone specifies the time zone of the sender, and if specified can be applied to all un-time zoned time values in the message. In the absence of this, any dates without time zone are implied to be in an indeterminate "local time".</xs:documentation>
						</xs:annotation>
					</xs:element>					
				</xs:sequence>
			</xs:extension>
		</xs:complexContent>
	</xs:complexType>

 * @author James
 */

public class SenderType extends PartyType {
    private TimezoneType Timezone;

    /**
     * @return the Timezone
     */
    public TimezoneType getTimezone() {
        return Timezone;
    }

    /**
     * @param Timezone the Timezone to set
     */
    public void setTimezone(TimezoneType Timezone) {
        this.Timezone = Timezone;
    }
}

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

import sdmx.footer.FooterType;

/**
 *	<xs:complexType name="MessageType" abstract="true">
		<xs:annotation>
			<xs:documentation>MessageType is an abstract type which is used by all of the messages, to allow inheritance of common features. Every message consists of a mandatory header, followed by optional payload (which may occur multiple times), and finally an optional footer section for conveying error, warning, and informational messages.</xs:documentation>
		</xs:annotation>
		<xs:sequence>
			<xs:element name="Header" type="BaseHeaderType"/>
			<xs:any namespace="##targetNamespace" minOccurs="0" maxOccurs="unbounded"/>
			<xs:element ref="footer:Footer" minOccurs="0"/>
		</xs:sequence>
	</xs:complexType>	

 * @author James
 */

public abstract class MessageType {
    private BaseHeaderType header;
    private FooterType footer;

    /**
     * @return the header
     */
    public BaseHeaderType getHeader() {
        return header;
    }

    /**
     * @param header the header to set
     */
    public void setHeader(BaseHeaderType header) {
        this.header = header;
    }

    /**
     * @return the footer
     */
    public FooterType getFooter() {
        return footer;
    }

    /**
     * @param footer the footer to set
     */
    public void setFooter(FooterType footer) {
        this.footer = footer;
    }
}

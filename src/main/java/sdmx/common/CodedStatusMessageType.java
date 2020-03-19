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

import java.util.List;

/**
 *	<xs:complexType name="CodedStatusMessageType">
		<xs:annotation>
			<xs:documentation>CodedStatusMessageType describes the structure of an error or warning message which required a code.</xs:documentation>
		</xs:annotation>
		<xs:complexContent>
			<xs:restriction base="StatusMessageType">
				<xs:sequence>
					<xs:element name="Text" type="TextType" maxOccurs="unbounded"/>
				</xs:sequence>
				<xs:attribute name="code" type="xs:string" use="required"/>
			</xs:restriction>
		</xs:complexContent>
	</xs:complexType>

 * @author James
 */
public class CodedStatusMessageType extends StatusMessageType {
    public CodedStatusMessageType(List<TextType> texts,String code) {
        super(texts,code);
    }
    public CodedStatusMessageType(){}
}

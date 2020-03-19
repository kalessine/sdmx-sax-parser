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
 *	<xs:complexType name="DataKeyValueType">
		<xs:annotation>
			<xs:documentation>DataKeyValueType is a type for providing a dimension value for the purpose of defining a distinct data key. Only a single value can be provided for the dimension.</xs:documentation>
		</xs:annotation>
		<xs:complexContent>
			<xs:restriction base="DinstinctKeyValueType">
				<xs:sequence>
					<xs:element name="Value" type="SimpleKeyValueType"/>
				</xs:sequence>
				<xs:attribute name="id" type="SingleNCNameIDType" use="required"/>
			</xs:restriction>
		</xs:complexContent>
	</xs:complexType>
 * @author James
 */
public class DataKeyValueType extends DistinctKeyValueType {
    private SimpleKeyValueType value = null;
    private SingleNCNameIDType id = null;
    public DataKeyValueType(SimpleKeyValueType v,SingleNCNameIDType id) {
        super(v,id);
        this.value=v;
        this.id=id;
    }

}

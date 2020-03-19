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
import sdmx.common.choice.DistinctKeyValueTypeChoice;

/**
 *	<xs:complexType name="DinstinctKeyValueType" abstract="true">
		<xs:annotation>
			<xs:documentation>DinstinctKeyValueType is an abstract base type which defines a singular, required value for a key component.</xs:documentation>
		</xs:annotation>
		<xs:complexContent>
			<xs:restriction base="ComponentValueSetType">
				<xs:choice>
					<xs:element name="Value" type="SimpleKeyValueType"/>
					<xs:element name="DataSet" type="SetReferenceType"/>
					<xs:element name="DataKey" type="DataKeyType"/>
					<xs:element name="Object" type="ObjectReferenceType"/>
				</xs:choice>
				<xs:attribute name="id" type="SingleNCNameIDType" use="required"/>
				<xs:attribute name="include" type="xs:boolean" use="optional" fixed="true"/>
			</xs:restriction>
		</xs:complexContent>
	</xs:complexType>
 * @author James
 */
public class DistinctKeyValueType extends ComponentValueSetType {
       DistinctKeyValueTypeChoice choice = null;
       private SingleNCNameIDType id = null;
       private static final boolean include = true;

       public DistinctKeyValueType(DistinctKeyValueTypeChoice choice,SingleNCNameIDType id) {
           super(choice,null);
           this.choice=choice;
           this.id=id;
       }
}

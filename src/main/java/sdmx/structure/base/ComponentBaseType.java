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
package sdmx.structure.base;

import sdmx.commonreferences.IDType;
import sdmx.commonreferences.NCNameID;

/**
 *	<xs:complexType name="ComponentBaseType" abstract="true">
		<xs:annotation>
			<xs:documentation>ComponentBaseType is an abstract type that only serves the purpose of forming the base for the actual ComponentType. It only restricts the format of the id attribute to the NCNameIDType.</xs:documentation>
		</xs:annotation>
		<xs:complexContent>
			<xs:restriction base="IdentifiableType">
				<xs:sequence>
					<xs:element ref="common:Annotations" minOccurs="0"/>
				</xs:sequence>
				<xs:attribute name="id" type="common:NCNameIDType" use="optional">
					<xs:annotation>
						<xs:documentation>The id attribute holds an explicit identification of the component. If this identifier is not supplied, then it is assumed to be the same as the identifier of the concept referenced from the concept identity. Because structures require that every component be given a unique identifier, it may be necessary to assign an explicit identifier when more than one component in a structure reference concepts with same identifier. It is important to note that this applies strictly to the identifier of concept and not the URN. Therefore if two concepts with the same identifier from different concept schemes are referenced in the same structure, one of the components will have to provide a unique explicit identifier. The type of this identifier is restricted to the common:NCNameIDType. This is necessary, since component identifiers are used to create XML elements and attributes in data and metadata structure specific schemas and therefore must be compliant with the NCName type in XML Schema (see common:NCNameIDType for further details).</xs:documentation>
					</xs:annotation>
				</xs:attribute>
			</xs:restriction>
		</xs:complexContent>
	</xs:complexType>
 * @author James
 */

public class ComponentBaseType extends IdentifiableType {

}

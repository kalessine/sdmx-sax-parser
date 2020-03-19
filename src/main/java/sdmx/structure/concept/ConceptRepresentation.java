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
package sdmx.structure.concept;

import sdmx.structure.base.BasicComponentTextFormatType;
import sdmx.structure.base.RepresentationType;

/**
 *	<xs:complexType name="ConceptRepresentation">
		<xs:annotation>
			<xs:documentation>ConceptRepresentation defines the core representation that are allowed for a concept. The text format allowed for a concept is that which is allowed for any non-target object component.</xs:documentation>
		</xs:annotation>
		<xs:complexContent>
			<xs:restriction base="RepresentationType">
				<xs:choice>
					<xs:element name="TextFormat" type="BasicComponentTextFormatType"/>
					<xs:sequence>
						<xs:element name="Enumeration" type="common:CodelistReferenceType">
							<xs:annotation>
								<xs:documentation>Enumeration references a codelist which enumerates the possible values that can be used as the representation of this concept.</xs:documentation>
							</xs:annotation>
						</xs:element>
						<xs:element name="EnumerationFormat" type="CodededTextFormatType" minOccurs="0"/>
					</xs:sequence>
				</xs:choice>
			</xs:restriction>
		</xs:complexContent>
	</xs:complexType>

 * @author James
 */


public class ConceptRepresentation extends RepresentationType {
    BasicComponentTextFormatType textFormat = null;
    
}

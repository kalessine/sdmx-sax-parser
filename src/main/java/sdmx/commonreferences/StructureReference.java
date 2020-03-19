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
package sdmx.commonreferences;

import java.net.URI;
import sdmx.xml.anyURI;

/**
 *	<xs:complexType name="StructureReferenceType">
		<xs:annotation>
			<xs:documentation>StructureReferenceType is a specific type of MaintainableReference that is used for referencing any structure. It consists of a URN and/or a complete set of reference fields; agency, id, and version.</xs:documentation>
		</xs:annotation>
		<xs:complexContent>
			<xs:restriction base="StructureReferenceBaseType">
				<xs:choice>
					<xs:sequence>
						<xs:element name="Ref" type="StructureRefType" form="unqualified"/>
						<xs:element name="URN" type="xs:anyURI" form="unqualified" minOccurs="0"/>
					</xs:sequence>
					<xs:element name="URN" type="xs:anyURI" form="unqualified"/>
				</xs:choice>
			</xs:restriction>
		</xs:complexContent>
	</xs:complexType>
 * @author James
 */
public class StructureReference extends StructureReferenceBase {
    public StructureReference(StructureRef ref, anyURI urn) {
        super(ref,urn);
    }
    public StructureReference(anyURI urn) {
        super(urn);
    }

}

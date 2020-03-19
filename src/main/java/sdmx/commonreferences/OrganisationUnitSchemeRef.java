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

import sdmx.commonreferences.types.OrganisationSchemeTypeCodelistType;

/**
 *	<xs:complexType name="OrganisationUnitSchemeRefType">
		<xs:annotation>
			<xs:documentation>OrganisationUnitSchemeRefType contains a set of reference fields for an organisation unit scheme.</xs:documentation>
		</xs:annotation>
		<xs:complexContent>
			<xs:restriction base="OrganisationSchemeRefBaseType">
				<xs:attribute name="class" type="OrganisationSchemeTypeCodelistType" use="optional" fixed="OrganisationUnitScheme"/>
			</xs:restriction>
		</xs:complexContent>
	</xs:complexType>
 * @author James
 */
public class OrganisationUnitSchemeRef extends OrganisationSchemeRefBase {
    public OrganisationUnitSchemeRef(IDType id) {
        super(id,OrganisationSchemeTypeCodelistType.ORGANISATIONUNITSCHEME);
    }
    
}

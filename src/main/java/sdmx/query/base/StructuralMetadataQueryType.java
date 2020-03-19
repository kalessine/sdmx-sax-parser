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
package sdmx.query.base;

/**
 *	<xs:complexType name="StructuralMetadataQueryType" abstract="true">
		<xs:annotation>
			<xs:documentation>StructureWhereQueryType is an abstract base type that serves as the basis for any structural metadata query. Concrete instances of this type are implied to be an and-query. A structural object will be returned for any object where all of the conditions are met.</xs:documentation>
		</xs:annotation>
		<xs:sequence>
			<xs:element name="ReturnDetails" type="StructureReturnDetailsType"/>
			<xs:element ref="StructuralMetadataWhere"/>
		</xs:sequence>
	</xs:complexType>

 * @author James
 */

public class StructuralMetadataQueryType {
    StructureReturnDetailsType returnDetails = null;
    StructuralMetadataWhere query = null;
}

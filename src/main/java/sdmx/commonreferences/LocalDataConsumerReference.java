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

/**
 *	<xs:complexType name="LocalDataConsumerReferenceType">
		<xs:annotation>
			<xs:documentation>LocalDataConsumerReferenceType provides a simple reference to a data consumer, where the reference to the data consumer scheme which defines it is provided in another context.</xs:documentation>
		</xs:annotation>
		<xs:complexContent>
			<xs:restriction base="LocalOrganisationReferenceBaseType">
				<xs:sequence>
					<xs:element name="Ref" type="LocalDataConsumerRefType" form="unqualified"/>
				</xs:sequence>
			</xs:restriction>
		</xs:complexContent>
	</xs:complexType>
 * @author James
 */

public class LocalDataConsumerReference extends LocalOrganisationReferenceBase {
    public LocalDataConsumerReference(LocalDataConsumerRef ref){
        super(ref);
    }
}

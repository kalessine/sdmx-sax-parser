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
 *	<xs:complexType name="StructureReturnDetailsBaseType" abstract="true">
		<xs:annotation>
			<xs:documentation>StructureReturnDetailsBaseType is an abstract base type which forms the basis of StructureReturnDetailsType.</xs:documentation>
		</xs:annotation>
		<xs:complexContent>
			<xs:restriction base="ReturnDetailsBaseType">
				<xs:attribute name="detail" type="StructureReturnDetailType" default="Full">
					<xs:annotation>
						<xs:documentation>The detail attribute is used to indicate whether the response to the query should return the full details of matched objects, or just a subset of the information should be returned. A value of "Full" indicates that the full details of all matched objects should be returned. A value of "CompleteStub" indicates that the identification information, name, description, and annotations for the matched object should be returned. A value of "Stub" indicates that just the identification information and name should be returned for the matched objects. Note that this applies only to the object(s) matched by the query parameters. The References element has a separate field for indicating the level of detail returned for referenced objects.</xs:documentation>
					</xs:annotation>
				</xs:attribute>				
			</xs:restriction>
		</xs:complexContent>
	</xs:complexType>

 * @author James
 */

public class StructureReturnDetailsBaseType extends ReturnDetailsBaseType {
    private StructureReturnDetailType detail = null;
}

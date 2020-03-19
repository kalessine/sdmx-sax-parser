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

/**
 *	<xs:simpleType name="ContentConstraintTypeCodeType">
		<xs:annotation>
			<xs:documentation>ContentConstraintTypeCodeType defines a list of types for a content constraint. A content constraint can state which data is present or which content is allowed for the constraint attachment.</xs:documentation>
		</xs:annotation>
		<xs:restriction base="xs:string">
			<xs:enumeration value="Allowed">
				<xs:annotation>
					<xs:documentation>The constraint contains the allowed values for attachable object.</xs:documentation>
				</xs:annotation>
			</xs:enumeration>
			<xs:enumeration value="Actual">
				<xs:annotation>
					<xs:documentation>The constraints contains the actual data present for the attachable object.</xs:documentation>
				</xs:annotation>
			</xs:enumeration>
		</xs:restriction>
	</xs:simpleType>
 * @author James
 */

public class ContentConstraintTypeCodeType {
       public static final String ALLOWED = "Allowed";
       public static final String ACTUAL = "Actual";
}

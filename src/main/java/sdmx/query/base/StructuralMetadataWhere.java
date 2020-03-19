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
 *	<xs:element name="StructuralMetadataWhere" type="MaintainableWhereType" abstract="true">
		<xs:annotation>
			<xs:documentation>StructuralMetadataWhere is an abstract substitution head that forms the basis of any structural metadata query's details. This contains the actual parameters to be matched. These parameters are implicitly joined by an "and" connector (i.e. each of the parameters must be matched in order to satisfy the query). If it is necessary to supply "or" conditions for a parameter, this should be done by supplying multiple queries.</xs:documentation>
		</xs:annotation>
	</xs:element>
 * @author James
 */

public class StructuralMetadataWhere extends MaintainableWhereType {
    
}

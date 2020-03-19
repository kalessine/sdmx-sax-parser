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

import sdmx.commonreferences.types.DimensionEnumerationTypeCodelistType;
import sdmx.commonreferences.types.ItemSchemePackageTypeCodelistType;

/**
 *	<xs:complexType name="DataStructureEnumerationSchemeRefType">
		<xs:annotation>
			<xs:documentation>DataStructureEnumerationSchemeRefType contains the reference fields for referencing any item scheme that is allowable as the enumeration of the representation of a data structure definition component.</xs:documentation>
		</xs:annotation>
		<xs:complexContent>
			<xs:restriction base="ItemSchemeRefType">
				<xs:attribute name="class" type="DimensionEumerationSchemeTypeCodelistType" use="required"/>
				<xs:attribute name="package" type="ItemSchemePackageTypeCodelistType" use="required"/>
			</xs:restriction>
		</xs:complexContent>
	</xs:complexType>

 * @author James
 */

public class DataStructureEnumerationSchemeRef extends ItemSchemeRef {

    public DataStructureEnumerationSchemeRef(DimensionEnumerationTypeCodelistType clazz,ItemSchemePackageTypeCodelistType pack){
        super(null,null,null);
    }
    
}

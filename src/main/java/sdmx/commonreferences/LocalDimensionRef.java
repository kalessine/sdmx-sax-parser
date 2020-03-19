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

import sdmx.commonreferences.types.DimensionTypeCodelistType;
import sdmx.commonreferences.types.StructurePackageTypeCodelistType;

/**
 *	<xs:complexType name="LocalDimensionRefType">
		<xs:annotation>
			<xs:documentation>LocalDimensionRefType contains the reference fields for referencing a dimension locally.</xs:documentation>
		</xs:annotation>
		<xs:complexContent>
			<xs:restriction base="LocalComponentRefBaseType">
				<xs:attribute name="id" type="IDType" use="required"/>
				<xs:attribute name="class" type="DimensionTypeCodelistType" use="optional" default="Dimension">
					<xs:annotation>
						<xs:documentation>The class attribute is optional and provided a default value of Dimension. It is strongly recommended that if the time or measure dimension is referenced, that the proper value be set for this field. However, this is not absolutely necessary since all data structure definition components must have a unique identifier within the scope of the entire data structure. It does, however, allow systems which will treat such a reference as a URN to easily construct the URN without having to verify the object class of the referenced dimension.</xs:documentation>
					</xs:annotation>
				</xs:attribute>
				<xs:attribute name="package" type="StructurePackageTypeCodelistType" use="optional" fixed="datastructure"/>
			</xs:restriction>
		</xs:complexContent>
	</xs:complexType>

 * @author James
 */

public class LocalDimensionRef extends LocalComponentRefBase {
    public LocalDimensionRef(IDType id){
        super(null,id,DimensionTypeCodelistType.DIMENSION,StructurePackageTypeCodelistType.DATASTRUCTURE);
    }
    
}

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
 *	<xs:complexType name="TimeDimensionRefType">
		<xs:annotation>
			<xs:documentation>TimeDimensionRefType contains a reference to the time dimension within a data structure definition. Note that since there is only one key descriptor, the container reference fields are prohibited.</xs:documentation>
		</xs:annotation>
		<xs:complexContent>
			<xs:restriction base="ComponentRefBaseType">
				<xs:attribute name="containerID" type="IDType" use="prohibited"/>
				<xs:attribute name="id" type="IDType" use="required" fixed="TIME_PERIOD"/>
				<xs:attribute name="class" type="DimensionTypeCodelistType" use="optional" fixed="TimeDimension"/>
				<xs:attribute name="package" type="StructurePackageTypeCodelistType" use="optional" fixed="datastructure"/>
			</xs:restriction>
		</xs:complexContent>
	</xs:complexType>

 * @author James
 */

public class TimeDimensionRef extends ComponentRefBase {
    public TimeDimensionRef(IDType id){
        super(null,null,null,null,id,DimensionTypeCodelistType.TIMEDIMENSION,StructurePackageTypeCodelistType.DATASTRUCTURE);
    }
}

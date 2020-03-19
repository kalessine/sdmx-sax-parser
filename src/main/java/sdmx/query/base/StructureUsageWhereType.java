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

import sdmx.commonreferences.StructureReferenceBase;

/**
 *	<xs:complexType name="StructureUsageWhereType" abstract="true">
		<xs:annotation>
			<xs:documentation>StructureUsageWhereType is an abstract base type that serves as the basis for a query for a structure usage object.</xs:documentation>
		</xs:annotation>
		<xs:complexContent>
			<xs:extension base="MaintainableWhereType">
				<xs:sequence>
					<xs:element name="Structure" type="common:StructureReferenceBaseType" minOccurs="0">
						<xs:annotation>
							<xs:documentation>Structure is used to match the structure referenced by a structure usage object. Only structure usages which reference the supplied structure will be returned.</xs:documentation>
						</xs:annotation>
					</xs:element>
				</xs:sequence>
			</xs:extension>
		</xs:complexContent>
	</xs:complexType>
 * @author James
 */

public class StructureUsageWhereType extends MaintainableWhereType {
    private StructureReferenceBase structure = null;

    /**
     * @return the structure
     */
    public StructureReferenceBase getStructure() {
        return structure;
    }

    /**
     * @param structure the structure to set
     */
    public void setStructure(StructureReferenceBase structure) {
        this.structure = structure;
    }
    
}

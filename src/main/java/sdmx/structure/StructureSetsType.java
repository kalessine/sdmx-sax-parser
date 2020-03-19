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
package sdmx.structure;

import java.util.ArrayList;
import java.util.List;

/**
 *<xs:complexType name="StructureSetsType">
		<xs:annotation>
			<xs:documentation>StructureSetsType describes the structure of the structure sets container. It contains one or more structure set, which can be explicitly detailed or referenced from an external structure document or registry service.</xs:documentation>
		</xs:annotation>
		<xs:sequence>
			<xs:element name="StructureSet" type="StructureSetType" maxOccurs="unbounded">
				<xs:annotation>
					<xs:documentation>StructureSet provides the details or a structure set, which allows components in one structure, structure usage, or item scheme to be mapped to components in another structural component of the same type.</xs:documentation>
				</xs:annotation>
				<xs:unique name="StructureSetUniqueCategorySchemeMap">
					<xs:selector xpath="structure:CategorySchemeMap"/>
					<xs:field xpath="@id"/>
				</xs:unique>
				<xs:unique name="StructureSetUniqueCodelistMap">
					<xs:selector xpath="structure:CodelistMap"/>
					<xs:field xpath="@id"/>
				</xs:unique>
				<xs:unique name="StructureSetUniqueConceptsMap">
					<xs:selector xpath="structure:ConceptsMap"/>
					<xs:field xpath="@id"/>
				</xs:unique>
				<xs:unique name="StructureSetUniqueOrganisationSchemeMap">
					<xs:selector xpath="structure:OrganisationSchemeMap"/>
					<xs:field xpath="@id"/>
				</xs:unique>
				<xs:unique name="StructureSetUniqueStructureMap">
					<xs:selector xpath="structure:StructureMap"/>
					<xs:field xpath="@id"/>
				</xs:unique>
			</xs:element>
		</xs:sequence>
	</xs:complexType>
 * @author James
 */

public class StructureSetsType {
    private List<StructureSetType> structureSets = new ArrayList<StructureSetType>();

    /**
     * @return the structureSets
     */
    public List<StructureSetType> getStructureSets() {
        return structureSets;
    }

    /**
     * @param structureSets the structureSets to set
     */
    public void setStructureSets(List<StructureSetType> structureSets) {
        this.structureSets = structureSets;
    }
}

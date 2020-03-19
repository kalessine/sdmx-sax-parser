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

import java.net.URI;
import sdmx.xml.anyURI;

/**
 *		<xs:complexType name="StructureSpecificMetadataStructureType">
		<xs:annotation>
			<xs:documentation>StructureSpecificMetadataStructureType defines the structural information for a structured metadata message.</xs:documentation>
		</xs:annotation>
		<xs:complexContent>
			<xs:restriction base="MetadataStructureType">
				<xs:sequence>
					<xs:choice>
						<xs:element name="ProvisionAgrement" type="ProvisionAgreementReferenceType"/>
						<xs:element name="StructureUsage" type="MetadataflowReferenceType"/>
						<xs:element name="Structure" type="MetadataStructureReferenceType"/>
					</xs:choice>
				</xs:sequence>
				<xs:attribute name="namespace" type="xs:anyURI" use="required"/>
			</xs:restriction>
		</xs:complexContent>
	</xs:complexType>
 * @author James
 */
public class StructureSpecificMetadataStructureType extends MetadataStructureType {
    private MetadataflowReferenceType structureUsage = null;
    private MetadataStructureReferenceType structure = null;
    public StructureSpecificMetadataStructureType(anyURI namespace){
        super(namespace);
    }

    /**
     * @return the structureUsage
     */
    public MetadataflowReferenceType getStructureUsage() {
        return structureUsage;
    }

    /**
     * @param structureUsage the structureUsage to set
     */
    public void setStructureUsage(MetadataflowReferenceType structureUsage) {
        this.structureUsage = structureUsage;
    }

    /**
     * @return the structure
     */
    public MetadataStructureReferenceType getStructure() {
        return structure;
    }

    /**
     * @param structure the structure to set
     */
    public void setStructure(MetadataStructureReferenceType structure) {
        this.structure = structure;
    }

}

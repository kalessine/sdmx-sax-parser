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
 *	<xs:complexType name="MetadataStructureType" abstract="true">
		<xs:annotation>
			<xs:documentation>MetadataStructureType is an abstract base type the forms the basis of the structural information for any metadata message. A reference to the metadata structure definition or a metadataflow must be provided. This can be used to determine the structure of the message.</xs:documentation>
		</xs:annotation>
		<xs:complexContent>
			<xs:restriction base="PayloadStructureType">
				<xs:sequence>
					<xs:choice>
						<xs:element name="ProvisionAgrement" type="ProvisionAgreementReferenceType">
							<xs:annotation>
								<xs:documentation>ProvisionAgreement references a provision agreement which the metadata is reported against.</xs:documentation>
							</xs:annotation>
						</xs:element>
						<xs:element name="StructureUsage" type="MetadataflowReferenceType">
							<xs:annotation>
								<xs:documentation>StructureUsage references a metadataflow which the metadata is reported against.</xs:documentation>
							</xs:annotation>
						</xs:element>
						<xs:element name="Structure" type="MetadataStructureReferenceType">
							<xs:annotation>
								<xs:documentation>Structure references the metadata structure definition which defines the structure of the metadata.</xs:documentation>
							</xs:annotation>
						</xs:element>
					</xs:choice>
				</xs:sequence>
				<xs:attribute name="dimensionAtObservation" type="ObservationDimensionType" use="prohibited"/>
				<xs:attribute name="explicitMeasures" type="xs:boolean" use="prohibited"/>
			</xs:restriction>
		</xs:complexContent>
	</xs:complexType>
 * @author James
 */
public class MetadataStructureType extends PayloadStructureType {
    
    
    public MetadataStructureType(){
        super();
    }
    // Used by StructureSpecificMetadataStructureType
    // this class does not have a namespcae attribute!
    public MetadataStructureType(anyURI namespace){
        super.setNamespace(namespace);
    }

    /**
     * @return the structureUsage
     */
    @Override
    public MetadataflowReferenceType getStructureUsage() {
        return (MetadataflowReferenceType)super.getStructureUsage();
    }

    /**
     * @param structureUsage the structureUsage to set
     */
    public void setStructureUsage(MetadataflowReferenceType structureUsage) {
        super.setStructureUsage(structureUsage);
    }

    /**
     * @return the structure
     */
    public MetadataStructureReferenceType getStructure() {
        return (MetadataStructureReferenceType) super.getStructure();
    }

    /**
     * @param structure the structure to set
     */
    public void setStructure(MetadataStructureReferenceType structure) {
        super.setStructure(structure);
    }
    
}

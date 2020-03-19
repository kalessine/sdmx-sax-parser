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
import sdmx.common.ExternalReferenceAttributeGroup;
import sdmx.common.ObservationDimensionType;
import sdmx.common.PayloadStructureType;
import sdmx.commonreferences.ProvisionAgreementReference;
import sdmx.xml.ID;
import sdmx.commonreferences.DataStructureReference;
import sdmx.commonreferences.DataflowReference;
import sdmx.xml.anyURI;
/**
 *	<xs:complexType name="DataStructureType" abstract="true">
		<xs:annotation>
			<xs:documentation>DataStructureType is an abstract base type the forms the basis for the structural information for a data set.</xs:documentation>
		</xs:annotation>
		<xs:complexContent>
			<xs:restriction base="PayloadStructureType">
				<xs:sequence>
					<xs:choice>
						<xs:element name="ProvisionAgrement" type="ProvisionAgreementReferenceType">
							<xs:annotation>
								<xs:documentation>ProvisionAgreement references a provision agreement which the data is reported against.</xs:documentation>
							</xs:annotation>
						</xs:element>
						<xs:element name="StructureUsage" type="DataflowReferenceType">
							<xs:annotation>
								<xs:documentation>StructureUsage references a dataflow which the data is reported against.</xs:documentation>
							</xs:annotation>
						</xs:element>
						<xs:element name="Structure" type="DataStructureReferenceType">
							<xs:annotation>
								<xs:documentation>Structure references the data structure definition which defines the structure of the data.</xs:documentation>
							</xs:annotation>
						</xs:element>
					</xs:choice>
				</xs:sequence>
			</xs:restriction>
		</xs:complexContent>
	</xs:complexType>

 * @author James
 */
public class DataStructureType extends PayloadStructureType {
    private ProvisionAgreementReference provisionAgreement = null;
    private DataflowReference structureUsage = null;
    private DataStructureReference structure = null;
    public DataStructureType(ID struct,anyURI schema,anyURI namespace,ObservationDimensionType dim,boolean explicit,anyURI serviceURL, anyURI structureURL){
        super(struct,schema,namespace,dim,explicit,serviceURL,structureURL);
    }

    /**
     * @return the provisionAgreement
     */
    public ProvisionAgreementReference getProvisionAgreement() {
        return provisionAgreement;
    }

    /**
     * @param provisionAgreement the provisionAgreement to set
     */
    public void setProvisionAgreement(ProvisionAgreementReference provisionAgreement) {
        this.provisionAgreement = provisionAgreement;
    }

    /**
     * @return the structureUsage
     */
    public DataflowReference getStructureUsage() {
        return structureUsage;
    }

    /**
     * @param structureUsage the structureUsage to set
     */
    public void setStructureUsage(DataflowReference structureUsage) {
        this.structureUsage = structureUsage;
    }

    /**
     * @return the structure
     */
    public DataStructureReference getStructure() {
        return structure;
    }

    /**
     * @param structure the structure to set
     */
    public void setStructure(DataStructureReference structure) {
        this.structure = structure;
    }
}

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
package sdmx.structure.concept;

import sdmx.commonreferences.IDType;
import sdmx.commonreferences.NCNameID;
import sdmx.commonreferences.NestedNCNameID;
import sdmx.commonreferences.Version;
import sdmx.structure.base.ItemType;

/**
 *	<xs:complexType name="ConceptType">
		<xs:annotation>
			<xs:documentation>ConceptType describes the details of a concept. A concept is defined as a unit of knowledge created by a unique combination of characteristics. If a concept does not specify a TextFormat or a core representation, then the representation of the concept is assumed to be represented by any set of valid characters (corresponding to the xs:string datatype of W3C XML Schema).</xs:documentation>
		</xs:annotation>
		<xs:complexContent>
			<xs:extension base="ConceptBaseType">
				<xs:sequence>
					<xs:element name="CoreRepresentation" type="ConceptRepresentation" minOccurs="0">
						<xs:annotation>
							<xs:documentation></xs:documentation>
						</xs:annotation>
					</xs:element>
					<xs:element name="ISOConceptReference" type="ISOConceptReferenceType" minOccurs="0">
						<xs:annotation>
							<xs:documentation>Provides a reference to an ISO 11179 concept.</xs:documentation>
						</xs:annotation>					
					</xs:element>
				</xs:sequence>
			</xs:extension>
		</xs:complexContent>
	</xs:complexType>

 * @author James
 */

public class ConceptType extends ConceptBaseType {
    
    
    private NCNameID id = null;
    private ConceptRepresentation coreRep = null;
    private ISOConceptReferenceType isoConceptRef = null;
    
    private NestedNCNameID agencyID;
    
    private Version version = Version.ONE;
    
    
    
    public ConceptType() {
    }

    /**
     * @return the coreRep
     */
    public ConceptRepresentation getCoreRepresentation() {
        return coreRep;
    }

    /**
     * @param coreRep the coreRep to set
     */
    public void setCoreRepresentation(ConceptRepresentation coreRep) {
        this.coreRep = coreRep;
    }

    /**
     * @return the isoConceptRef
     */
    public ISOConceptReferenceType getIsoConceptRef() {
        return isoConceptRef;
    }

    /**
     * @param isoConceptRef the isoConceptRef to set
     */
    public void setIsoConceptRef(ISOConceptReferenceType isoConceptRef) {
        this.isoConceptRef = isoConceptRef;
    }
    /**
     * @return the agencyID
     */
    public NestedNCNameID getAgencyID() {
        return agencyID;
    }

    /**
     * @param agencyID the agencyID to set
     */
    public void setAgencyID(NestedNCNameID agencyId) {
        this.agencyID = agencyId;
    }
    /**
     * @return the version
     */
    public Version getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(Version version) {
        this.version = version;
    }
    // Sdmx 2.0 only field
    // this is the code used in the field for cross sectional measure
    private String code = null;

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }
    /**
     * @return the id
     */
    @Override
    public NCNameID getId() {
        return id;
    }

    @Override
    public void setId(IDType id) {
        this.id=(NCNameID)id;
    }
    /**
     * @param id the id to set
     */
    public void setId(NCNameID id) {
        this.id = id;
    }
}

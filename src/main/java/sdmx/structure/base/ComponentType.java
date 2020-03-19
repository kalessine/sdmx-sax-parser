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
package sdmx.structure.base;

import sdmx.commonreferences.ConceptReference;
import sdmx.commonreferences.IDType;

/**
 *	<xs:complexType name="ComponentType" abstract="true">
		<xs:annotation>
			<xs:documentation>ComponentType is an abstract base type for all components. It contains information pertaining to a component, including an optional reference to a concept, an optional role played by the concept, an optional text format description, and an optional local representation.</xs:documentation>
		</xs:annotation>
		<xs:complexContent>
			<xs:extension base="ComponentBaseType">
				<xs:sequence>
					<xs:element name="ConceptIdentity" type="common:ConceptReferenceType" minOccurs="0">
						<xs:annotation>
							<xs:documentation>ConceptIdentity allows for the referencing of a concept in a concept scheme. The component takes its semantic from this concept, and if an id is not specified, it takes its identification as well. If a representation (LocalRepresentation) is not supplied, then the representation of the component is also inherited from the concept. Note that in the case of the component representation being inherited from the concept, the allowable representations for the component still apply. Therefore, if a component references a concept with a core representation that is not allowed for the concept, that representation must be locally overridden. For components which can specify a concept role, it is implied that the concept which is referenced also identifies a role for the component.</xs:documentation>
						</xs:annotation>
					</xs:element>
					<xs:element name="LocalRepresentation" type="RepresentationType" minOccurs="0">
						<xs:annotation>
							<xs:documentation>LocalRepresentation references item schemes that may be used to create the representation of a component. The type of this must be refined such that a concrete item scheme reference is used.</xs:documentation>
						</xs:annotation>
					</xs:element>
				</xs:sequence>
			</xs:extension>
		</xs:complexContent>
	</xs:complexType>

 * @author James
 */

public class ComponentType extends ComponentBaseType {
    private ConceptReference conceptIdentity = null;
    private RepresentationType localRepresentation = null;

    /**
     * @return the id
     */
    public IDType getId() {
        if( super.getId()==null ) {
            if( conceptIdentity==null ) {
                //Thread.dumpStack();
                return new IDType("MISS");
                
            }
            return conceptIdentity.getId().asID();
        }
        return super.getId();
    }
    /**
     * @return the conceptIdentity
     */
    public ConceptReference getConceptIdentity() {
        return conceptIdentity;
    }

    /**
     * @param conceptIdentity the conceptIdentity to set
     */
    public void setConceptIdentity(ConceptReference conceptIdentity) {
        this.conceptIdentity = conceptIdentity;
    }

    /**
     * Don't use this function.. use the 
     * ComponentUtil.getRepresentation(registry,component);
     * unless you really need the local rep..
     * then use ComponentUtil.getLocalRepresentation(Component);
     * @return the localRepresentation
     */
    public RepresentationType getLocalRepresentation() {
        return localRepresentation;
    }

    /**
     * @param localRepresentation the localRepresentation to set
     */
    public void setLocalRepresentation(RepresentationType localRepresentation) {
        this.localRepresentation = localRepresentation;
    }
    
}

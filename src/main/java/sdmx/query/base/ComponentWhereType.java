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

import sdmx.commonreferences.ConceptReference;
import sdmx.commonreferences.ItemSchemeReferenceBase;

/**
 *	<xs:complexType name="ComponentWhereType" abstract="true">
		<xs:annotation>
			<xs:documentation>ComponentWhereType is an abstract base type that serves as the basis for a query for a component within a component list where or a structure query. A concept identity and a local representation condition are available to seek a component that utilizes a particular concept or representation scheme. The conditions within a component query are implied to be in an and-query. If an id and a concept identity condition are supplied, then both conditions will have to met in order for the component query to return true. If, for instance, a query based on names in multiple languages is required, then multiple instances of the element utilizing this type should be used within an or-query container.</xs:documentation>
		</xs:annotation>
		<xs:complexContent>
			<xs:extension base="IdentifiableWhereType">
				<xs:sequence>
					<xs:element name="ConceptIdentity" type="common:ConceptReferenceType" minOccurs="0">
						<xs:annotation>
							<xs:documentation>ConceptIdentity is used to query for a structure component based on the concept from which it takes its semantic.</xs:documentation>
						</xs:annotation>
					</xs:element>
					<xs:element name="Enumeration" type="common:ItemSchemeReferenceBaseType" minOccurs="0">
						<xs:annotation>
							<xs:documentation>Enumeration is used to query for a structure component based on the item scheme that is used as the enumeration for its representation. This enumeration may be explicit defined by the component (i.e. its local representation), or inherited from the concept from which the component takes its semantic (i.e. the concept core representation).</xs:documentation>
						</xs:annotation>
					</xs:element>
				</xs:sequence>
			</xs:extension>
		</xs:complexContent>
	</xs:complexType>

 * @author James
 */

public class ComponentWhereType extends IdentifiableWhereType {
    private ConceptReference conceptIdentity = null;
    private ItemSchemeReferenceBase enumeration = null;

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
     * @return the enumeration
     */
    public ItemSchemeReferenceBase getEnumeration() {
        return enumeration;
    }

    /**
     * @param enumeration the enumeration to set
     */
    public void setEnumeration(ItemSchemeReferenceBase enumeration) {
        this.enumeration = enumeration;
    }
}

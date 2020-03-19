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
package sdmx.structure.constraint;

/**
 *	<xs:complexType name="ContentConstraintBaseType" abstract="true">
		<xs:annotation>
			<xs:documentation>ContentConstraintBaseType is an abstract base type that forms the basis for the ContentConstraintType.</xs:documentation>
		</xs:annotation>
		<xs:complexContent>
			<xs:restriction base="ConstraintType">
				<xs:sequence>
					<xs:element ref="common:Annotations" minOccurs="0"/>
					<xs:element ref="common:Name" maxOccurs="unbounded"/>
					<xs:element ref="common:Description" minOccurs="0" maxOccurs="unbounded"/>
					<xs:element name="ConstraintAttachment" type="ContentConstraintAttachmentType" minOccurs="0">
						<xs:annotation>
							<xs:documentation>ConstraintAttachment describes the collection of constrainable artefacts that the constraint is attached to.</xs:documentation>
						</xs:annotation>
					</xs:element>
1					<xs:choice minOccurs="0" maxOccurs="unbounded">
						<xs:element name="DataKeySet" type="DataKeySetType">
							<xs:annotation>
								<xs:documentation>DataKeySet defines a collection of full or partial data keys.</xs:documentation>
							</xs:annotation>
						</xs:element>
						<xs:element name="MetadataKeySet" type="MetadataKeySetType">
							<xs:annotation>
								<xs:documentation>MetadataKeySet defines a collection of metadata keys.</xs:documentation>
							</xs:annotation>
						</xs:element>
						<xs:element name="CubeRegion" type="common:CubeRegionType">
							<xs:annotation>
								<xs:documentation>CubeRegion describes a set of dimension values which define a region and attributes which relate to the region for the purpose of describing a constraint.</xs:documentation>
							</xs:annotation>
						</xs:element>
						<xs:element name="MetadataTargetRegion" type="common:MetadataTargetRegionType">
							<xs:annotation>
								<xs:documentation>MetadataTargetRegion describes a set of target object values for a given report structure which define a region, and the metadata attribute which relate to the target for the purpose of describing a constraint.</xs:documentation>
							</xs:annotation>
						</xs:element>
					</xs:choice>
				</xs:sequence>
			</xs:restriction>
		</xs:complexContent>
	</xs:complexType>

 * @author James
 */

public class ContentConstraintBaseType extends ConstraintType {
    
}

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

import sdmx.common.QueryableDataSourceType;

/**
 *	<xs:complexType name="AttachmentConstraintAttachmentType">
		<xs:annotation>
			<xs:documentation>AttachmentConstraintAttachmentType defines the structure for specifying the object to which an attachment constraints applies.</xs:documentation>
		</xs:annotation>
		<xs:complexContent>
			<xs:restriction base="ConstraintAttachmentType">
				<xs:choice>
					<xs:element name="DataSet" type="common:SetReferenceType" maxOccurs="unbounded">
						<xs:annotation>
							<xs:documentation>DataSet is reference to a data set to which the constraint is attached. The referenced is provided as a URN and/or a full set of reference fields. Multiple instance can only be used if they have the same underlying structure.</xs:documentation>
						</xs:annotation>
					</xs:element>
					<xs:element name="MetadataSet" type="common:SetReferenceType" maxOccurs="unbounded">
						<xs:annotation>
							<xs:documentation>MetadataSet is reference to a metadata set to which the constraint is attached. The referenced is provided as a URN and/or a full set of reference fields. Multiple instance can only be used if they have the same underlying structure.</xs:documentation>
						</xs:annotation>
					</xs:element>
					<xs:element name="SimpleDataSource" type="xs:anyURI" maxOccurs="unbounded">
						<xs:annotation>
							<xs:documentation>SimpleDataSource describes a simple data source, which is a URL of a SDMX-ML data or metadata message. Multiple instance can only be used if they have the same underlying structure.</xs:documentation>
						</xs:annotation>
					</xs:element>
					<xs:choice>
						<xs:element name="DataStructure" type="common:DataStructureReferenceType" maxOccurs="unbounded"/>
						<xs:element name="MetadataStructure" type="common:MetadataStructureReferenceType" maxOccurs="unbounded"/>
						<xs:element name="Dataflow" type="common:DataflowReferenceType" maxOccurs="unbounded"/>
						<xs:element name="Metadataflow" type="common:MetadataflowReferenceType" maxOccurs="unbounded"/>
						<xs:element name="ProvisionAgreement" type="common:ProvisionAgreementReferenceType" maxOccurs="unbounded"/>
					</xs:choice>
				</xs:choice>
			</xs:restriction>
		</xs:complexContent>
	</xs:complexType>
 * @author James
 */

public class AttachmentConstraintAttachmentType extends ConstraintAttachmentType {



}

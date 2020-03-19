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

import java.util.List;
import sdmx.commonreferences.ProvisionAgreementReference;
import sdmx.common.QueryableDataSourceType;
import sdmx.commonreferences.DataProviderReference;
import sdmx.commonreferences.DataStructureReference;
import sdmx.commonreferences.MetadataStructureReference;
import sdmx.commonreferences.MetadataflowReference;
import sdmx.commonreferences.SetReference;
import sdmx.xml.anyURI;

/**
 *	<xs:complexType name="ContentConstraintAttachmentType">
		<xs:annotation>
			<xs:documentation>ContentConstraintAttachmentType defines the structure for specifying the target object(s) of a content constraint.</xs:documentation>
		</xs:annotation>
		<xs:complexContent>
			<xs:restriction base="ConstraintAttachmentType">
				<xs:choice>
					<xs:element name="DataProvider" type="common:DataProviderReferenceType"/>
					<xs:element name="DataSet" type="common:SetReferenceType"/>
					<xs:element name="MetadataSet" type="common:SetReferenceType"/>
					<xs:element name="SimpleDataSource" type="xs:anyURI"/>
					<xs:choice>
						<xs:sequence>
							<xs:element name="DataStructure" type="common:DataStructureReferenceType" maxOccurs="unbounded"/>
							<xs:element name="QueryableDataSource" type="common:QueryableDataSourceType" minOccurs="0" maxOccurs="unbounded"/>
						</xs:sequence>
						<xs:sequence>
							<xs:element name="MetadataStructure" type="common:MetadataStructureReferenceType" maxOccurs="unbounded"/>
							<xs:element name="QueryableDataSource" type="common:QueryableDataSourceType" minOccurs="0" maxOccurs="unbounded"/>
						</xs:sequence>
						<xs:sequence>
							<xs:element name="Dataflow" type="common:DataflowReferenceType" maxOccurs="unbounded"/>
							<xs:element name="QueryableDataSource" type="common:QueryableDataSourceType" minOccurs="0" maxOccurs="unbounded"/>
						</xs:sequence>
						<xs:sequence>
							<xs:element name="Metadataflow" type="common:MetadataflowReferenceType" maxOccurs="unbounded"/>
							<xs:element name="QueryableDataSource" type="common:QueryableDataSourceType" minOccurs="0" maxOccurs="unbounded"/>					
						</xs:sequence>
						<xs:sequence>
							<xs:element name="ProvisionAgreement" type="common:ProvisionAgreementReferenceType" maxOccurs="unbounded"/>
							<xs:element name="QueryableDataSource" type="common:QueryableDataSourceType" minOccurs="0" maxOccurs="unbounded"/>
						</xs:sequence>
					</xs:choice>
				</xs:choice>
			</xs:restriction>
		</xs:complexContent>
	</xs:complexType>
 * @author James
 */

public class ContentConstraintAttachmentType extends ConstraintAttachmentType {
  
  
}

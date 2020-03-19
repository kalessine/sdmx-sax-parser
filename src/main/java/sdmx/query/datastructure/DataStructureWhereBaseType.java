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
package sdmx.query.datastructure;

import sdmx.common.TimeRangeValueType;
import sdmx.commonreferences.ConceptReference;
import sdmx.commonreferences.DataStructureEnumerationSchemeReference;
import sdmx.commonreferences.VersionQuery;
import sdmx.commonreferences.types.MaintainableTypeCodelistType;
import sdmx.query.base.AnnotationWhereType;
import sdmx.query.base.QueryIDType;
import sdmx.query.base.QueryNestedIDType;
import sdmx.query.base.QueryTextType;
import sdmx.query.base.StructureWhereType;
import sdmx.xml.anyURI;

/**
 *	<xs:complexType name="DataStructureWhereBaseType" abstract="true">
		<xs:annotation>
			<xs:documentation>DataStructureWhereBaseType is an abstract base type that forms the basis of the DataStructureWhereType.</xs:documentation>
		</xs:annotation>
		<xs:complexContent>
			<xs:restriction base="StructureWhereType">
				<xs:sequence>
					<xs:element name="Annotation" type="AnnotationWhereType" minOccurs="0"/>
					<xs:element name="URN" type="xs:anyURI" minOccurs="0"/>
					<xs:element name="ID" type="QueryIDType" minOccurs="0"/>
					<xs:element name="Name" type="QueryTextType" minOccurs="0"/>
					<xs:element name="Description" type="QueryTextType" minOccurs="0"/>
					<xs:element name="Version" type="common:VersionQueryType" minOccurs="0"/>
					<xs:element name="VersionTo" type="common:TimeRangeValueType" minOccurs="0"/>
					<xs:element name="VersionFrom" type="common:TimeRangeValueType" minOccurs="0"/>
					<xs:element name="VersionActive" type="xs:boolean" minOccurs="0"/>
					<xs:element name="AgencyID" type="QueryNestedIDType" minOccurs="0"/>
					<xs:element name="UsedConcept" type="common:ConceptReferenceType" minOccurs="0" maxOccurs="unbounded"/>
					<xs:element name="UsedRepresentation" type="common:DataStructureEnumerationSchemeReferenceType" minOccurs="0" maxOccurs="unbounded"/>
					<xs:sequence minOccurs="0" maxOccurs="unbounded">
						<xs:element ref="GroupWhere"/>
					</xs:sequence>
				</xs:sequence>
				<xs:attribute name="type" type="common:MaintainableTypeCodelistType" use="optional" fixed="DataStructure"/>
			</xs:restriction>
		</xs:complexContent>
	</xs:complexType>

 * @author James
 */

public class DataStructureWhereBaseType extends StructureWhereType {
    public DataStructureWhereBaseType() {
        super.setType(MaintainableTypeCodelistType.DATASTRUCTURE);
    }
}

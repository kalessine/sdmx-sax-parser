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

import sdmx.common.MaintainableObjectTypeListType;

/**
 *	<xs:complexType name="ReferencesType">
		<xs:annotation>
			<xs:documentation>ReferencesType defines the structure for indicating which referenced objects should be returned in a structural metadata query. It is possible to return both objects which reference the object(s) matched by the query and objects referenced from the match object(s). The type(s) of reference objects to be returned consists of a choice between None, All, Default, or an explicit list of object types.</xs:documentation>
		</xs:annotation>
		<xs:choice>
			<xs:element name="None" type="common:EmptyType">
				<xs:annotation>
					<xs:documentation>None indicates that no reference objects should be returned.</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element name="All" type="common:EmptyType">
				<xs:annotation>
					<xs:documentation>All is a convenience to indicate that the sets indicated by the ParentsAndSiblings and Descendants should be returned.</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element name="Parents" type="common:EmptyType">
				<xs:annotation>
					<xs:documentation>Parents is a convenience to indicate that any object that refers to the matched object should be returned. This is typically used when the query the goal is to find object refer to a set of unknown objects.</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element name="ParentsAndSiblings" type="common:EmptyType">
				<xs:annotation>
					<xs:documentation>ParentsAndSiblings is a convenience to indicate that any object that refers to the matched object should be returned, along with any other objects referenced by those referring objects.</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element name="Children" type="common:EmptyType">
				<xs:annotation>
					<xs:documentation>Children is a convenience to indicate that all object referred to by the matched object should be returned.</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element name="Descendants" type="common:EmptyType">
				<xs:annotation>
					<xs:documentation>Descendants is a convenience to indicate that all object referred to by the matched object should be returned, along with any objects referenced by the referred objects, and so on. This is a deep resolution, where all outgoing references starting at the matched object are resolved.</xs:documentation>
				</xs:annotation>
			</xs:element>			
			<xs:element name="SpecificObjects" type="common:MaintainableObjectTypeListType">
				<xs:annotation>
					<xs:documentation>SpecificObjects is used to enumerate specific types of object to be returned. Theses objects will either refer to or are referred by the matched object. Only the maintainable objects listed here will be returned.</xs:documentation>
				</xs:annotation>
			</xs:element>		
		</xs:choice>
		<xs:attribute name="processConstraints" type="xs:boolean" default="false">
			<xs:annotation>
				<xs:documentation>The processConstraints attribute is used to request that the query process any relevant constraints for the match object(s) in order to return only the applicable portion of any referenced codelists. A value of "true" indicates that constraints should be processed.</xs:documentation>
			</xs:annotation>
		</xs:attribute>
		<xs:attribute name="detail" type="MaintainableReturnDetailType" default="Full">
			<xs:annotation>
				<xs:documentation>The detail attribute indicates the amount of detail that should be returned for reference objects.  A value of "Full" indicates that the full details of all reference objects should be returned. A value of "CompleteStub" indicates that the identification information, name, description, and annotations for the reference object should be returned. A value of "Stub" indicates that just the identification information and name should be returned for the reference objects.</xs:documentation>
			</xs:annotation>
		</xs:attribute>
	</xs:complexType>

 * @author James
 */

public class ReferencesType {
    Boolean none = false;
    Boolean all = false;
    Boolean parents = false;
    Boolean parentsAndSiblings = false;
    Boolean children = false;
    Boolean descendants = false;
    MaintainableObjectTypeListType specificObjects = null;
    Boolean processConstraints = null;
    MaintainableReturnDetailType detail = null;
}

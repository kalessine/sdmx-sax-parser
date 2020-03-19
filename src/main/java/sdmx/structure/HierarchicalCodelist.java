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
package sdmx.structure;

/**
 *	<xs:complexType name="HierarchicalCodelistsType">
		<xs:annotation>
			<xs:documentation>HierarchicalCodelistsType describes the structure of the hierarchical code lists container. It contains one or more hierarchical code list, which can be explicitly detailed or referenced from an external structure document or registry service.</xs:documentation>
		</xs:annotation>
		<xs:sequence>
			<xs:element name="HierarchicalCodelist" type="HierarchicalCodelistType" maxOccurs="unbounded">
				<xs:annotation>
					<xs:documentation>HierarchicalCodelist provides the details of a hierarchical code list, which is defined as an organised collection of codes that may participate in many parent/child relationships with other codes in the list, as defined by one or more hierarchy of the list.</xs:documentation>
				</xs:annotation>
				<xs:unique name="HierarchicalCodelist_UniqueHierarchy">
					<xs:selector xpath="structure:Hierarchy"/>
					<xs:field xpath="@id"/>
				</xs:unique>	
				<xs:unique name="HierarchicalCodelist_UniqueCodelistAlias">
					<xs:selector xpath="structure:IncludedCodelistReference"/>
					<xs:field xpath="@alias"/>
				</xs:unique>
			</xs:element>
		</xs:sequence>
	</xs:complexType>

 * @author James
 */

public class HierarchicalCodelist {
    
}

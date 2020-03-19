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
package sdmx.structure.metadatastructure;

/**
 *	<xs:element name="ReportStructure" type="ReportStructureType" substitutionGroup="ComponentList">
		<xs:annotation>
			<xs:documentation>ReportStructure defines a report structure, which comprises a set of metadata attributes that can be defined as a hierarchy, for reporting reference metadata about a target object. The identification of metadata attributes must be unique at any given level of the report structure. Although there are XML schema constraints to help enforce this, these only apply to explicitly assigned identifiers. Identifiers inherited from a concept from which a metadata attribute takes its identity cannot be validated against this constraint. Therefore, systems processing metadata structure definitions will have to perform this check outside of the XML validation.</xs:documentation>
		</xs:annotation>
		<xs:unique name="ReportStructure_Unique_MetadataAttribute">
			<xs:selector xpath="structure:MetadataAttribute"/>
			<xs:field xpath="@id"/>
		</xs:unique>
	</xs:element>

 * @author James
 */

public class ReportStructure extends ReportStructureType {
    
}

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
 *	<xs:element name="MetadataAttribute" type="MetadataAttributeType" substitutionGroup="Component">
		<xs:annotation>
			<xs:documentation>MetadataAttribute defines the a metadata attribute, which is the value of an attribute, such as the instance of a coded or uncoded attribute in a metadata structure definition.</xs:documentation>
		</xs:annotation>
		<xs:unique name="MetadataAttribute_Unique_MetadataAttribute">
			<xs:selector xpath="structure:MetadataAttribute"/>
			<xs:field xpath="@id"/>
		</xs:unique>
	</xs:element>

 * @author James
 */

public class MetadataAttribute extends MetadataAttributeType {
    
}

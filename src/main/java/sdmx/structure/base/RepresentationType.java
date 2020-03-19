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

import sdmx.commonreferences.ItemSchemeReferenceBase;

/**
 *	<xs:complexType name="RepresentationType" abstract="true">
		<xs:annotation>
			<xs:documentation>RepresentationType is an abstract type that defines a representation. Because the type of item schemes that are allowed as the an enumeration vary based on the object in which this is defined, this type is abstract to force that the enumeration reference be restricted to the proper type of item scheme reference.</xs:documentation>
		</xs:annotation>
		<xs:choice>
			<xs:element name="TextFormat" type="TextFormatType">
				<xs:annotation>
					<xs:documentation>TextFormat describes an uncoded textual format.</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:sequence>
				<xs:element name="Enumeration" type="common:ItemSchemeReferenceBaseType">
					<xs:annotation>
						<xs:documentation>Enumeration references an item scheme that enumerates the allowable values for this representation.</xs:documentation>
					</xs:annotation>
				</xs:element>
				<xs:element name="EnumerationFormat" type="CodededTextFormatType" minOccurs="0">
					<xs:annotation>
						<xs:documentation>EnumerationFormat describes the facets of the item scheme enumeration. This is for the most part, informational.</xs:documentation>
					</xs:annotation>
				</xs:element>
			</xs:sequence>
		</xs:choice>
	</xs:complexType>

 * @author James
 */

public class RepresentationType {
   
    private TextFormatType textFormat = null;
    private ItemSchemeReferenceBase enumeration = null;
    private CodededTextFormatType enumerationFormat = null;

    /**
     * @return the textFormat
     */
    public TextFormatType getTextFormat() {
        return textFormat;
    }

    /**
     * @param textFormat the textFormat to set
     */
    public void setTextFormat(TextFormatType textFormat) {
        this.textFormat = textFormat;
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

    /**
     * @return the enumerationForma
     */
    public CodededTextFormatType getEnumerationFormat() {
        return enumerationFormat;
    }

    /**
     * @param enumerationForma the enumerationForma to set
     */
    public void setEnumerationFormat(CodededTextFormatType enumerationForma) {
        this.enumerationFormat = enumerationForma;
    }

}

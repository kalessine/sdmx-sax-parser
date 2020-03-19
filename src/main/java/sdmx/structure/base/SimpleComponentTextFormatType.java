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

import sdmx.common.SimpleDataType;

/**
 *	<xs:complexType name="SimpleComponentTextFormatType">
		<xs:annotation>
			<xs:documentation>SimpleComponentTextFormatType is a restricted version of the BasicComponentTextFormatType that does not allow for multi-lingual values.</xs:documentation>
		</xs:annotation>
		<xs:complexContent>
			<xs:restriction base="BasicComponentTextFormatType">
				<xs:attribute name="textType" type="common:SimpleDataType" use="optional" default="String"/>
				<xs:attribute name="isMultiLingual" type="xs:boolean" use="prohibited"/>
			</xs:restriction>
		</xs:complexContent>
	</xs:complexType>
 * @author James
 */

public class SimpleComponentTextFormatType extends BasicComponentTextFormatType {
    private SimpleDataType textType = null;

    private Boolean isMultiLingual = false;// prohibited field

    /**
     * @return the textType
     */
    public SimpleDataType getTextType() {
        return textType;
    }

    /**
     * @param textType the textType to set
     */
    public void setTextType(SimpleDataType textType) {
        this.textType = textType;
    }

    /**
     * @return the isMultiLingual
     */
    @Override
    public Boolean isMultiLingual() {
        return Boolean.FALSE;
    }

    /**
     * @param isMultiLingual the isMultiLingual to set
     */
    public void setMultiLingual(Boolean isMultiLingual) {
        this.isMultiLingual = isMultiLingual;
    }
}

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

import sdmx.common.BasicComponentDataType;
import sdmx.common.DataType;

/**
 *	<xs:complexType name="BasicComponentTextFormatType">
		<xs:annotation>
			<xs:documentation>BasicComponentTextFormatType is a restricted version of the TextFormatType that restricts the text type to the representations allowed for all components except for target objects.</xs:documentation>
		</xs:annotation>
		<xs:complexContent>
			<xs:restriction base="TextFormatType">
				<xs:attribute name="textType" type="common:BasicComponentDataType" use="optional" default="String"/>
			</xs:restriction>
		</xs:complexContent>
	</xs:complexType>
 * @author James
 */

public class BasicComponentTextFormatType extends TextFormatType {
    private BasicComponentDataType textType =null;

    /**
     * @return the textType
     */
    @Override
    public BasicComponentDataType getTextType() {
        return textType;
    }

    /**
     * @param textType the textType to set
     */
    public void setTextType(BasicComponentDataType textType) {
        this.textType = textType;
    }
    /**
     * @param textType the textType to set
     */
    public void setTextType(DataType textType) {
        this.textType = (BasicComponentDataType)textType;
    }
}

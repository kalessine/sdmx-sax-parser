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

import sdmx.xml.positiveInteger;

/**
 *	<xs:complexType name="CodededTextFormatType">
		<xs:annotation>
			<xs:documentation>CodededTextFormatType is a restricted version of the SimpleComponentTextFormatType that only allows factets and text types applicable to codes. Although the time facets permit any value, an actual code identifier does not support the necessary characters for time. Therefore these facets should not contain time in their values.</xs:documentation>
		</xs:annotation>
		<xs:complexContent>
			<xs:restriction base="SimpleComponentTextFormatType">
				<xs:attribute name="textType" type="CodeDataType" use="optional"/>
				<xs:attribute name="isSequence" type="xs:boolean" use="optional"/>
				<xs:attribute name="interval" type="xs:integer" use="optional"/>
				<xs:attribute name="startValue" type="xs:integer" use="optional"/>
				<xs:attribute name="endValue" type="xs:integer" use="optional"/>
				<xs:attribute name="timeInterval" type="xs:duration" use="optional"/>
				<xs:attribute name="startTime" type="common:StandardTimePeriodType" use="optional"/>
				<xs:attribute name="endTime" type="common:StandardTimePeriodType" use="optional"/>
				<xs:attribute name="minLength" type="xs:positiveInteger" use="optional"/>
				<xs:attribute name="maxLength" type="xs:positiveInteger" use="optional"/>
				<xs:attribute name="minValue" type="xs:integer" use="optional"/>
				<xs:attribute name="maxValue" type="xs:integer" use="optional"/>
				<xs:attribute name="decimals" type="xs:positiveInteger" use="prohibited"/>
				<xs:attribute name="pattern" type="xs:string" use="optional"/>
			</xs:restriction>
		</xs:complexContent>
	</xs:complexType>
 * @author James
 */

public class CodededTextFormatType extends SimpleComponentTextFormatType {

    CodeDataType textType = null;
    
    /**
     * @return the decimals
     */
    public positiveInteger getDecimals() {
        throw new RuntimeException("Decimals is prohibited field in CodededTextFormatType");
    }

    /**
     * @param decimals the decimals to set
     */
    public void setDecimals(positiveInteger decimals) {
        throw new RuntimeException("Decimals is prohibited field in CodededTextFormatType");
    }
    
}

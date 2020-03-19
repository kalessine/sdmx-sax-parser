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

import sdmx.commonreferences.CodelistReference;

/**
 *	<xs:complexType name="CodeValueType">
		<xs:annotation>
			<xs:documentation>CodeValueType is used to query for data or reference metadata where a component which uses the referenced codelist as its representation enumeration has the value provided. Note that this is only applicable when the value is a coded value, which is to say that it does not apply to a codelist which is specified as the representation or an identifiable object target in a metadata target.</xs:documentation>
		</xs:annotation>
		<xs:sequence>
			<xs:element name="Codelist" type="common:CodelistReferenceType">
				<xs:annotation>
					<xs:documentation>Codelist references the codelist for which the coded value is being sought.</xs:documentation>
				</xs:annotation>
			</xs:element>
		</xs:sequence>
		<xs:attribute name="value" type="xs:string" use="required">
			<xs:annotation>
				<xs:documentation>The value attribute indicates the coded value that is to be queried for.</xs:documentation>
			</xs:annotation>
		</xs:attribute>
	</xs:complexType>

 * @author James
 */

public class CodeValueType {
    private CodelistReference codelist = null;
    private String value = null;

    /**
     * @return the codelist
     */
    public CodelistReference getCodelist() {
        return codelist;
    }

    /**
     * @param codelist the codelist to set
     */
    public void setCodelist(CodelistReference codelist) {
        this.codelist = codelist;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
    
}

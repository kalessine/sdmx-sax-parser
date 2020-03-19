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

/**
 *	<xs:complexType name="ReturnDetailsBaseType" abstract="true">
		<xs:annotation>
			<xs:documentation>ReturnDetailsBaseType is an abstract type that forms the basis for any query return details.</xs:documentation>
		</xs:annotation>
		<xs:attribute name="defaultLimit" type="xs:integer" use="optional">
			<xs:annotation>
				<xs:documentation>The defaultLimit attribute is the suggested maximum response size in kilobytes.</xs:documentation>
			</xs:annotation>
		</xs:attribute>
		<xs:attribute name="detail" type="xs:string" use="optional">
			<xs:annotation>
				<xs:documentation>>The detail attribute is used to indicate how much of the matched object should be returned.</xs:documentation>
			</xs:annotation>
		</xs:attribute>
	</xs:complexType>

 * @author James
 */

public class ReturnDetailsBaseType {
    private Integer defaultLimit=null;
    private String detail=null;

    /**
     * @return the defaultLimit
     */
    public Integer getDefaultLimit() {
        return defaultLimit;
    }

    /**
     * @param defaultLimit the defaultLimit to set
     */
    public void setDefaultLimit(Integer defaultLimit) {
        this.defaultLimit = defaultLimit;
    }

    /**
     * @return the detail
     */
    public String getDetail() {
        return detail;
    }

    /**
     * @param detail the detail to set
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }
}

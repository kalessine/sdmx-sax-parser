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
package sdmx.footer;

import static sdmx.footer.SeverityCodeType.CODE_WARNING;

/**
 *	<xs:simpleType name="SeverityCodeType">
		<xs:restriction base="xs:string">
			<xs:enumeration value="Error">
				<xs:annotation>
					<xs:documentation>Error indicates the status message coresponds to an error.</xs:documentation>
				</xs:annotation>
			</xs:enumeration>
			<xs:enumeration value="Warning">
				<xs:annotation>
					<xs:documentation>Warning indicates that the status message corresponds to a warning.</xs:documentation>
				</xs:annotation>
			</xs:enumeration>
			<xs:enumeration value="Information">
				<xs:annotation>
					<xs:documentation>Information indicates that the status message corresponds to an informational message.</xs:documentation>
				</xs:annotation>
			</xs:enumeration>
		</xs:restriction>
	</xs:simpleType>

 * @author James
 */

public class SeverityCodeType extends sdmx.xml.XMLString {
    public static final String CODE_ERROR ="Error";
    public static final String CODE_WARNING ="Warning";
    public static final String CODE_INFORMATION ="Information";
    public static final SeverityCodeType ERROR = new SeverityCodeType(CODE_ERROR);
    public static final SeverityCodeType WARNING = new SeverityCodeType(CODE_WARNING);
    public static final SeverityCodeType INFORMATION = new SeverityCodeType(CODE_INFORMATION);

    private SeverityCodeType(String str) {
        super(str);
    }
    public SeverityCodeType fromString(String s) {
        if( CODE_ERROR.equals(s) ) return ERROR;
        if( CODE_WARNING.equals(s) ) return WARNING;
        if( CODE_INFORMATION.equals(s)) return INFORMATION;
        throw new RuntimeException(s+" is not a Serverity CodeType");
    }
}
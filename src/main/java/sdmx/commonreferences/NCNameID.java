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
package sdmx.commonreferences;

import java.util.regex.Pattern;
import sdmx.SdmxIO;
import sdmx.common.ObservationDimensionType;

/**
 *	<xs:simpleType name="NCNameIDType">
		<xs:annotation>
			<xs:documentation>NCNameIDType restricts the IDType, so that the id may be used to generate valid XML components. IDs created from this type conform to the W3C XML Schema NCNAME type, and therefore can be used as element or attribute names.</xs:documentation>
		</xs:annotation>
		<xs:restriction base="IDType">
			<xs:pattern value="[A-z][A-z0-9_\-]*"/>
		</xs:restriction>
	</xs:simpleType>
 * @author James
 */

public class NCNameID extends IDType {
    public static void main(String args[]) {
        NCNameID id = new NCNameID("1");
        Pattern[] patterns = id.getPatternArray();
        for (int i = 0; i < patterns.length; i++) {
            System.out.println("Check Pattern:"+"1"+":'"+patterns[i]+"'"+patterns[i].matcher("1").matches());
            if (!patterns[i].matcher("1").matches()) {
                if (SdmxIO.isStrictRegex()) {
                    throw new ExceptionInInitializerError("Input " + "1" + " does not match Pattern:" + patterns[i].pattern());
                }else {
                    System.out.println("Input "+"1"+" does not match Pattern:"+patterns[i].pattern()+" for class:"+id.getClass().getSimpleName());
                }
            }
        }
    }
    public static final String PATTERN = "[A-z][A-z0-9_\\-]*";
    public static final Pattern REGEX_PATTERN = Pattern.compile(PATTERN);
    public static final Pattern[] PATTERN_ARRAY = new Pattern[]{REGEX_PATTERN};
    public NCNameID(String s) {
        super(s);
    }
    @Override
    public Pattern[] getPatternArray() {
        return PATTERN_ARRAY;
    }
   public boolean equals(NCNameID id) {
        return super.getString().equals(id.getString());
    }
    public boolean equals(String id) {
        return super.getString().equals(id);
    }
}

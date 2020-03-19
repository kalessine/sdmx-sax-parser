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
import sdmx.xml.RegexXMLString;

/**
 * <xs:simpleType name="NestedIDType">
 * <xs:annotation>
 * <xs:documentation>NestedIDType is the least restrictive form of an identifier
 * used throughout all SDMX-ML messages. It allows for a hierarchical
 * identifier, with each portion separated by the '.' character. For the
 * identifier portions, valid characters include A-Z, a-z, @, 0-9, _, -,
 * $.</xs:documentation>
 * </xs:annotation>
 * <xs:restriction base="xs:string">
 * <xs:pattern value="[A-z0-9_@$\-]+(\.[A-z0-9_@$\-]+)*"/>
 * </xs:restriction>
 * </xs:simpleType>
 *
 * @author James
 */
public class NestedID extends RegexXMLString {

    public static final String PATTERN = "[A-z0-9_@$\\-]+(\\.[A-z0-9_@$\\-]+)*";
    public static final Pattern REGEX_PATTERN = Pattern.compile(PATTERN);
    public static final Pattern[] PATTERN_ARRAY = new Pattern[]{REGEX_PATTERN};

    public NestedID(String s) {
        super(s);
    }
    @Override
    public Pattern[] getPatternArray() {
        return PATTERN_ARRAY;
    }

    public boolean equals(NestedID id) {
        if( id == null ) return false;
        return super.getString().equals(id.getString());
    }

    public boolean equals(String id) {
        return super.getString().equals(id);
    }

    public boolean equals(IDType id) {
        //System.out.println("left="+this.getString()+" right="+id.getString());
        return super.getString().equals(id.getString());
    }

    public IDType asID() {
        return new IDType(super.getString());
    }
}

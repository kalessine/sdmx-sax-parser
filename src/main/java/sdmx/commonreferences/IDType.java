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
import sdmx.xml.RegexXMLString;

/**
 * <xs:simpleType name="IDType">
 * <xs:annotation>
 * <xs:documentation>IDType provides a type which is used for restricting the
 * characters in codes and IDs throughout all SDMX-ML messages. Valid characters
 * include A-Z, a-z, @, 0-9, _, -, $.</xs:documentation>
 * </xs:annotation>
 * <xs:restriction base="NestedIDType">
 * <xs:pattern value="[A-z0-9_@$\-]+"/>
 * </xs:restriction>
 * </xs:simpleType>
 *
 * @author James
 */
public class IDType extends NestedID {

    public static final String PATTERN = "[A-z0-9_@$\\-]+";
    public static final Pattern REGEX_PATTERN = Pattern.compile(PATTERN);
    public static final Pattern[] PATTERN_ARRAY = new Pattern[]{REGEX_PATTERN};

    public IDType(String s) {
        super(s);
        if (s == null) {
            throw new RuntimeException("Null IDType:" + s);
        }
    }

    @Override
    public Pattern[] getPatternArray() {
        return PATTERN_ARRAY;
    }

    public boolean equals(IDType id) {
        if (super.getString() == null || id == null) {
            return false;
        }
        //System.out.println("left="+this.getString()+" right="+id.getString());
        if (SdmxIO.isIgnoreCase()) {
            return super.getString().equalsIgnoreCase(id.getString());
        } else {
            return super.getString().equals(id.getString());
        }
    }

    public boolean equals(String id) {
        if (super.getString() == null || id == null) {
            return false;
        }
        if( SdmxIO.isIgnoreCase() ) {
            return super.getString().equalsIgnoreCase(id);
        }else {
            return super.getString().equals(id);
        }
    }
}

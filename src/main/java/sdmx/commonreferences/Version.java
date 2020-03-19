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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.regex.Pattern;
import sdmx.xml.RegexXMLString;

/**
 *	<xs:simpleType name="VersionType">
		<xs:annotation>
			<xs:documentation>VersionType is used to communicate version information. The format is restricted to allow for simple incrementing and sorting of version number. The version consists of an unlimited set of numeric components, separated by the '.' character. When processing version, each numeric component (the number preceding and following any '.' character) should be parsed as an integer. Thus, a version of 1.3 and 1.03 would be equivalent, as both the '3' component and the '03' component would parse to an integer value of 3.</xs:documentation>
		</xs:annotation>
		<xs:restriction base="xs:string">
			<xs:pattern value="[0-9]+(\.[0-9]+)*"/>
		</xs:restriction>
	</xs:simpleType>
 * @author James
 */
public class Version extends RegexXMLString implements Comparable {
    public static final String PATTERN = "[0-9]+(\\.[0-9]+)*";
    public static final Pattern REGEX_PATTERN = Pattern.compile(PATTERN);
    public static final Pattern[] PATTERN_ARRAY = new Pattern[]{REGEX_PATTERN};
    public static final Version ONE = new Version("1.0");
    public static void main(String args[]) {}
    public Version(String s) {
        super(s);
    }
    @Override
    public Pattern[] getPatternArray() {
        return PATTERN_ARRAY;
    }
   public boolean equals(Version id) {
       //System.out.println("left="+this.getString()+" right="+id.getString());
        return super.getString().equals(id.getString());
    }
    public boolean equals(String id) {
        return super.getString().equals(id);
    }
    public int compareTo(Object o) {
        if( !(o instanceof Version ))return -1;
        double a1 = Double.parseDouble(o.toString());
        double a2 = Double.parseDouble(toString());
        return a1>a2?1:a1<a2?-1:0;
    }
    private void writeObject(ObjectOutputStream oos) throws IOException {
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        
    }
}

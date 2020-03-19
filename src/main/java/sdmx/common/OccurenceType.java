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
package sdmx.common;

/**
 *	<xs:simpleType name="OccurenceType">
		<xs:annotation>
			<xs:documentation>OccurenceType is used to express the maximum occurrence of an object. It combines an integer, greater than 1, and the literal text, "unbounded", for objects which have no upper limit on its occurrence.</xs:documentation>
		</xs:annotation>
		<xs:union memberTypes="MaxOccursNumberType UnboundedCodeType"/>
	</xs:simpleType>
 * @author James
 */
public class OccurenceType {
    public static final String UNBOUNDED_TEXT = "unbounded";
    public static final String ONE_TEXT = "1";
    public static final OccurenceType UNBOUNDED = new OccurenceType(UNBOUNDED_TEXT);
    public static final OccurenceType ONE = new OccurenceType(ONE_TEXT);

    String ocurrs = "1";
    public OccurenceType(String s) {
        this.ocurrs=s;
    }
    
    public int getMaxOccurence() {
        if( "unbounded".equals(ocurrs) ) return Integer.MAX_VALUE;
        else {
            return Integer.parseInt(ocurrs);
        }
    }
    public static OccurenceType fromString(String s) {
       if( s == null ) return null;
       if( UNBOUNDED_TEXT.equals(s)) return UNBOUNDED;
       try{
           Integer.parseInt(s);
           return new OccurenceType(s);
       }catch(NumberFormatException nfe) {
           throw new RuntimeException("Invalid OccurenceType:"+s);
       }  
    }
}

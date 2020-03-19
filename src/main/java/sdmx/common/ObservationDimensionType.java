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

import sdmx.commonreferences.NCNameID;

/**
 *	<xs:simpleType name="ObservationDimensionType">
		<xs:annotation>
			<xs:documentation>ObservationDimensionType allows for the dimension at the observation level to be specified by either providing the dimension identifier or using the explicit value "AllDimensions".</xs:documentation>
		</xs:annotation>
		<xs:union memberTypes="NCNameIDType ObsDimensionsCodeType"/>
	</xs:simpleType>
 * @author James
 */
public class ObservationDimensionType extends NCNameID {
    ObsDimensionsCodeType code;
    public ObservationDimensionType(String s) {
        super(s);
        if( ObsDimensionsCodeType.ALL_DIMENSIONS_TEXT.equals(s)){
           code = ObsDimensionsCodeType.fromString(s);
        } else if( ObsDimensionsCodeType.TIME_PERIOD_TEXT.equals(s)) {
           code = ObsDimensionsCodeType.fromString(s);
        }
    }
    public String toString() { return code!=null?code.toString():super.toString(); }
}

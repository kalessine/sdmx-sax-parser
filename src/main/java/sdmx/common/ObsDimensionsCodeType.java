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

import sdmx.xml.XMLString;

/**
 *	<xs:simpleType name="ObsDimensionsCodeType">
		<xs:annotation>
			<xs:documentation>ObsDimensionsCodeType is an enumeration containing the values "TimeDimension" and "AllDimensions"</xs:documentation>
		</xs:annotation>
		<xs:restriction base="xs:string">
			<xs:enumeration value="AllDimensions">
				<xs:annotation>
					<xs:documentation>AllDimensions notes that the cross sectional format shall be flat; that is all dimensions should be contained at the observation level.</xs:documentation>
				</xs:annotation>
			</xs:enumeration>
			<xs:enumeration value="TIME_PERIOD">
				<xs:annotation>
					<xs:documentation>TIME_PERIOD refers to the fixed identifier for the time dimension.</xs:documentation>
				</xs:annotation>
			</xs:enumeration>
		</xs:restriction>
	</xs:simpleType>
 * @author James
 */
public class ObsDimensionsCodeType extends XMLString {
      public static final String ALL_DIMENSIONS_TEXT = "AllDimensions";
      public static final String TIME_PERIOD_TEXT = "TIME_PERIOD";
      public static final ObsDimensionsCodeType ALL_DIMENSIONS = new ObsDimensionsCodeType(ALL_DIMENSIONS_TEXT);
      public static final ObsDimensionsCodeType TIME_PERIOD = new ObsDimensionsCodeType(TIME_PERIOD_TEXT);

      private ObsDimensionsCodeType(String s) {
         super(s);
      }
      public static ObsDimensionsCodeType fromString(String s) {
          if( ALL_DIMENSIONS_TEXT.equals(s) ) return ALL_DIMENSIONS;
          if( TIME_PERIOD_TEXT.equals(s)) return TIME_PERIOD;
          throw new RuntimeException(s+" is not an ObsDimensionsCodeType");
      }
}

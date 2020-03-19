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
package sdmx.structure.constraint;

/**
 *	<xs:complexType name="ReleaseCalendarType">
		<xs:annotation>
			<xs:documentation>ReleaseCalendarType describes information about the timing of releases of the constrained data. All of these values use the standard "P7D" - style format.</xs:documentation>
		</xs:annotation>
		<xs:sequence>
			<xs:element name="Periodicity" type="xs:string">
				<xs:annotation>
					<xs:documentation>Periodicity is the period between releases of the data set.</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element name="Offset" type="xs:string">
				<xs:annotation>
					<xs:documentation>Offset is the interval between January first and the first release of data within the year.</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element name="Tolerance" type="xs:string">
				<xs:annotation>
					<xs:documentation>Tolerance is the period after which the release of data may be deemed late.</xs:documentation>
				</xs:annotation>
			</xs:element>
		</xs:sequence>
	</xs:complexType>

 * @author James
 */

public class ReleaseCalendarType {
    private String periodicity = "";
    private String offset = "";
    private String tolerance = "";

    /**
     * @return the periodicity
     */
    public String getPeriodicity() {
        return periodicity;
    }

    /**
     * @param periodicity the periodicity to set
     */
    public void setPeriodicity(String periodicity) {
        this.periodicity = periodicity;
    }

    /**
     * @return the offset
     */
    public String getOffset() {
        return offset;
    }

    /**
     * @param offset the offset to set
     */
    public void setOffset(String offset) {
        this.offset = offset;
    }

    /**
     * @return the tolerance
     */
    public String getTolerance() {
        return tolerance;
    }

    /**
     * @param tolerance the tolerance to set
     */
    public void setTolerance(String tolerance) {
        this.tolerance = tolerance;
    }
    
}

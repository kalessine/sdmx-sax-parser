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

import sdmx.common.ReferencePeriodType;

/**
 *	<xs:complexType name="ContentConstraintType">
		<xs:annotation>
			<xs:documentation>ContentConstraintType describes the details of a content constraint by defining the content regions, key sets, or release information for the constraint attachment objects. Note that if the constraint is for a data provider, then only release calendar information is relevant, as there is no reliable way of determining which key family is being used to frame constraints in terms of cube regions or key sets.</xs:documentation>
		</xs:annotation>
		<xs:complexContent>
			<xs:extension base="ContentConstraintBaseType">
				<xs:sequence>
					<xs:element name="ReleaseCalendar" type="ReleaseCalendarType" minOccurs="0">
						<xs:annotation>
							<xs:documentation>ReleaseCalendar defines dates on which the constrained data is to be made available.</xs:documentation>
						</xs:annotation>
					</xs:element>
					<xs:element name="ReferencePeriod" type="common:ReferencePeriodType" minOccurs="0">
						<xs:annotation>
							<xs:documentation>ReferencePeriod is used to report start date and end date constraints.</xs:documentation>
						</xs:annotation>
					</xs:element>
				</xs:sequence>
				<xs:attribute name="type" type="common:ContentConstraintTypeCodeType" use="optional" default="Actual">
					<xs:annotation>
						<xs:documentation>The type attribute indicates whether this constraint states what data is actually present for the constraint attachment, or if it defines what content is allowed. The default value is "Actual", meaning the data actually present for the constraint attachment.</xs:documentation>
					</xs:annotation>
				</xs:attribute>
			</xs:extension>
		</xs:complexContent>
	</xs:complexType>

 * @author James
 */

public class ContentConstraintType extends ContentConstraintBaseType {
     private ReleaseCalendarType releaseCalendar=null;
     private ReferencePeriodType releasePeriod= null;

    /**
     * @return the releaseCalendar
     */
    public ReleaseCalendarType getReleaseCalendar() {
        return releaseCalendar;
    }

    /**
     * @param releaseCalendar the releaseCalendar to set
     */
    public void setReleaseCalendar(ReleaseCalendarType releaseCalendar) {
        this.releaseCalendar = releaseCalendar;
    }

    /**
     * @return the releasePeriod
     */
    public ReferencePeriodType getReleasePeriod() {
        return releasePeriod;
    }

    /**
     * @param releasePeriod the releasePeriod to set
     */
    public void setReleasePeriod(ReferencePeriodType releasePeriod) {
        this.releasePeriod = releasePeriod;
    }
     
}

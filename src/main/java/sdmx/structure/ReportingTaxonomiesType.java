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
package sdmx.structure;

import java.util.ArrayList;
import java.util.List;

/**
 *<xs:complexType name="ReportingTaxonomiesType">
		<xs:annotation>
			<xs:documentation>ReportingTaxonomiesType describes the structure of the reporting taxonomies container. It contains one or more reporting taxonomy, which can be explicitly detailed or referenced from an external structure document or registry service.</xs:documentation>
		</xs:annotation>
		<xs:sequence>
			<xs:element name="ReportingTaxonomy" type="ReportingTaxonomyType" maxOccurs="unbounded">
				<xs:annotation>
					<xs:documentation>ReportingTaxonomy provides the details of a reporting taxonomy, which is a scheme which defines the composition structure of a data report where each component can be described by an independent data or metadata flow definition.</xs:documentation>
				</xs:annotation>
				<xs:unique name="ReportingTaxonomy_UniqueReportingCategory">
					<xs:selector xpath="structure:ReportingCategory"/>
					<xs:field xpath="@id"/>
				</xs:unique>
			</xs:element>
		</xs:sequence>
	</xs:complexType>
 * @author James
 */

public class ReportingTaxonomiesType {
    private List<ReportingTaxonomy> reportingTaxonomies = new ArrayList<ReportingTaxonomy>();

    /**
     * @return the reportingTaxonomies
     */
    public List<ReportingTaxonomy> getReportingTaxonomies() {
        return reportingTaxonomies;
    }

    /**
     * @param reportingTaxonomies the reportingTaxonomies to set
     */
    public void setReportingTaxonomies(List<ReportingTaxonomy> reportingTaxonomies) {
        this.reportingTaxonomies = reportingTaxonomies;
    }
}

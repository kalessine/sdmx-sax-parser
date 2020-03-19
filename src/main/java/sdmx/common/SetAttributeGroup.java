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

import sdmx.xml.DateTime;
import sdmx.xml.IDRef;
import sdmx.xml.gYear;

/**
 *	<xs:attributeGroup name="SetAttributeGroup">
<xs:annotation>
<xs:documentation>The SetAttributeGroup defines a common set of attributes for use in data and reference metadata sets.</xs:documentation>
</xs:annotation>
<xs:attribute name="structureRef" type="xs:IDREF" use="required">
<xs:annotation>
<xs:documentation>The structureRef contains a reference to a structural specification in the header of a data or reference metadata message. The structural specification details which structure the data or reference metadata conforms to, as well as providing additional information such as how the data is structure (e.g. which dimension occurs at the observation level for a data set).</xs:documentation>
</xs:annotation>
</xs:attribute>
<xs:attribute name="setID" type="IDType" use="optional">
<xs:annotation>
<xs:documentation>The setID provides an identification of the data or metadata set.</xs:documentation>
</xs:annotation>
</xs:attribute>
<xs:attribute name="action" type="ActionType" use="optional">
<xs:annotation>
<xs:documentation>The action attribute indicates whether the file is appending, replacing, or deleting.</xs:documentation>
</xs:annotation>
</xs:attribute>
<xs:attribute name="reportingBeginDate" type="BasicTimePeriodType" use="optional">
<xs:annotation>
<xs:documentation>The reportingBeginDate indicates the inclusive start time of the data reported in the data or metadata set.</xs:documentation>
</xs:annotation>
</xs:attribute>
<xs:attribute name="reportingEndDate" type="BasicTimePeriodType" use="optional">
<xs:annotation>
<xs:documentation>The reportingEndDate indicates the inclusive end time of the data reported in the data or metadata set.</xs:documentation>
</xs:annotation>
</xs:attribute>
<xs:attribute name="validFromDate" type="xs:dateTime" use="optional">
<xs:annotation>
<xs:documentation>The validFromDate indicates the inclusive start time indicating the validity of the information in the data or metadata set.</xs:documentation>
</xs:annotation>
</xs:attribute>
<xs:attribute name="validToDate" type="xs:dateTime" use="optional">
<xs:annotation>
<xs:documentation>The validToDate indicates the inclusive end time indicating the validity of the information in the data or metadata set.</xs:documentation>
</xs:annotation>
</xs:attribute>
<xs:attribute name="publicationYear" type="xs:gYear" use="optional">
<xs:annotation>
<xs:documentation>The publicationYear holds the ISO 8601 four-digit year.</xs:documentation>
</xs:annotation>
</xs:attribute>
<xs:attribute name="publicationPeriod" type="ObservationalTimePeriodType" use="optional">
<xs:annotation>
<xs:documentation>The publicationPeriod specifies the period of publication of the data or metadata in terms of whatever provisioning agreements might be in force (i.e., "Q1 2005" if that is the time of publication for a data set published on a quarterly basis).</xs:documentation>
</xs:annotation>
</xs:attribute>
</xs:attributeGroup>
 * @author James
 */
public class SetAttributeGroup {

    private IDRef structureRef = null;
    private ActionType action = null;
    private BasicTimePeriodType reportingBeginDate = null;
    private BasicTimePeriodType reportingEndDate = null;
    private DateTime validFromDate = null;
    private DateTime validToDate = null;
    gYear publicationYear=null;
    ObservationalTimePeriodType publicationPeriod=null;

    public SetAttributeGroup(IDRef ref) {
        this.structureRef = ref;
    }

    /**
     * @return the structureRef
     */
    public IDRef getStructureRef() {
        return structureRef;
    }

    /**
     * @param structureRef the structureRef to set
     */
    public void setStructureRef(IDRef structureRef) {
        this.structureRef = structureRef;
    }

    /**
     * @return the action
     */
    public ActionType getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(ActionType action) {
        this.action = action;
    }

    /**
     * @return the reportingBeginDate
     */
    public BasicTimePeriodType getReportingBeginDate() {
        return reportingBeginDate;
    }

    /**
     * @param reportingBeginDate the reportingBeginDate to set
     */
    public void setReportingBeginDate(BasicTimePeriodType reportingBeginDate) {
        this.reportingBeginDate = reportingBeginDate;
    }

    /**
     * @return the reportingEndDate
     */
    public BasicTimePeriodType getReportingEndDate() {
        return reportingEndDate;
    }

    /**
     * @param reportingEndDate the reportingEndDate to set
     */
    public void setReportingEndDate(BasicTimePeriodType reportingEndDate) {
        this.reportingEndDate = reportingEndDate;
    }

    /**
     * @return the validFromDate
     */
    public DateTime getValidFromDate() {
        return validFromDate;
    }

    /**
     * @param validFromDate the validFromDate to set
     */
    public void setValidFromDate(DateTime validFromDate) {
        this.validFromDate = validFromDate;
    }

    /**
     * @return the validToDate
     */
    public DateTime getValidToDate() {
        return validToDate;
    }

    /**
     * @param validToDate the validToDate to set
     */
    public void setValidToDate(DateTime validToDate) {
        this.validToDate = validToDate;
    }
}

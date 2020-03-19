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
package sdmx.structure.base;

import sdmx.common.DataType;
import sdmx.common.SimpleDataType;
import sdmx.common.StandardTimePeriodType;
import sdmx.xml.duration;
import sdmx.xml.positiveInteger;

/**
 *	<xs:complexType name="NonFacetedTextFormatType">
		<xs:annotation>
			<xs:documentation>NonFacetedTextFormatType is a restricted version of the SimpleComponentTextFormatType that does not allow for any facets.</xs:documentation>
		</xs:annotation>
		<xs:complexContent>
			<xs:restriction base="SimpleComponentTextFormatType">
				<xs:attribute name="textType" type="common:SimpleDataType" use="optional"/>
				<xs:attribute name="isSequence" type="xs:boolean" use="prohibited"/>
				<xs:attribute name="interval" type="xs:decimal" use="prohibited"/>
				<xs:attribute name="startValue" type="xs:decimal" use="prohibited"/>
				<xs:attribute name="endValue" type="xs:decimal" use="prohibited"/>
				<xs:attribute name="timeInterval" type="xs:duration" use="prohibited"/>
				<xs:attribute name="startTime" type="common:BasicTimePeriodType" use="prohibited"/>
				<xs:attribute name="endTime" type="common:BasicTimePeriodType" use="prohibited"/>
				<xs:attribute name="minLength" type="xs:positiveInteger" use="prohibited"/>
				<xs:attribute name="maxLength" type="xs:positiveInteger" use="prohibited"/>
				<xs:attribute name="minValue" type="xs:decimal" use="prohibited"/>
				<xs:attribute name="maxValue" type="xs:decimal" use="prohibited"/>
				<xs:attribute name="decimals" type="xs:positiveInteger" use="prohibited"/>
				<xs:attribute name="pattern" type="xs:string" use="prohibited"/>
			</xs:restriction>
		</xs:complexContent>
	</xs:complexType>
 * @author James
 */

public class NonFacetedTextFormatType {
    private SimpleDataType textType = null;

    /**
     * @return the textType
     */
    public SimpleDataType getTextType() {
        return textType;
    }

    /**
     * @param textType the textType to set
     */
    public void setTextType(SimpleDataType textType) {
        this.textType = textType;
    }

    /**
     * @return the isSequence
     */
    public Boolean isSequence() {
        throw new RuntimeException("Prohibited field in NonFacetedTextFormatType");
    }

    /**
     * @param isSequence the isSequence to set
     */
    public void setSequence(Boolean isSequence) {
        throw new RuntimeException("Prohibited field in NonFacetedTextFormatType");
    }

    /**
     * @return the interval
     */
    public Double getInterval() {
        throw new RuntimeException("Prohibited field in NonFacetedTextFormatType");
    }

    /**
     * @param interval the interval to set
     */
    public void setInterval(Double interval) {
        throw new RuntimeException("Prohibited field in NonFacetedTextFormatType");
    }

    /**
     * @return the startValue
     */
    public Double getStartValue() {
        throw new RuntimeException("Prohibited field in NonFacetedTextFormatType");
    }

    /**
     * @param startValue the startValue to set
     */
    public void setStartValue(Double startValue) {
        throw new RuntimeException("Prohibited field in NonFacetedTextFormatType");
    }

    /**
     * @return the endValue
     */
    public Double getEndValue() {
        throw new RuntimeException("Prohibited field in NonFacetedTextFormatType");
    }

    /**
     * @param endValue the endValue to set
     */
    public void setEndValue(Double endValue) {
        throw new RuntimeException("Prohibited field in NonFacetedTextFormatType");
    }

    /**
     * @return the timeInterval
     */
    public duration getTimeInterval() {
        throw new RuntimeException("Prohibited field in NonFacetedTextFormatType");
    }

    /**
     * @param timeInterval the timeInterval to set
     */
    public void setTimeInterval(duration timeInterval) {
        throw new RuntimeException("Prohibited field in NonFacetedTextFormatType");
    }

    /**
     * @return the startTime
     */
    public StandardTimePeriodType getStartTime() {
        throw new RuntimeException("Prohibited field in NonFacetedTextFormatType");
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(StandardTimePeriodType startTime) {
        throw new RuntimeException("Prohibited field in NonFacetedTextFormatType");
    }

    /**
     * @return the endTime
     */
    public StandardTimePeriodType getEndTime() {
        throw new RuntimeException("Prohibited field in NonFacetedTextFormatType");
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(StandardTimePeriodType endTime) {
        throw new RuntimeException("Prohibited field in NonFacetedTextFormatType");
    }

    /**
     * @return the minLength
     */
    public positiveInteger getMinLength() {
        throw new RuntimeException("Prohibited field in NonFacetedTextFormatType");
    }

    /**
     * @param minLength the minLength to set
     */
    public void setMinLength(positiveInteger minLength) {
        throw new RuntimeException("Prohibited field in NonFacetedTextFormatType");
    }

    /**
     * @return the maxLength
     */
    public positiveInteger getMaxLength() {
        throw new RuntimeException("Prohibited field in NonFacetedTextFormatType");
    }

    /**
     * @param maxLength the maxLength to set
     */
    public void setMaxLength(positiveInteger maxLength) {
        throw new RuntimeException("Prohibited field in NonFacetedTextFormatType");
    }

    /**
     * @return the minValue
     */
    public Double getMinValue() {
        throw new RuntimeException("Prohibited field in NonFacetedTextFormatType");
    }

    /**
     * @param minValue the minValue to set
     */
    public void setMinValue(Double minValue) {
        throw new RuntimeException("Prohibited field in NonFacetedTextFormatType");
    }

    /**
     * @return the maxValue
     */
    public Double getMaxValue() {
        throw new RuntimeException("Prohibited field in NonFacetedTextFormatType");
    }

    /**
     * @param maxValue the maxValue to set
     */
    public void setMaxValue(Double maxValue) {
        throw new RuntimeException("Prohibited field in NonFacetedTextFormatType");
    }

    /**
     * @return the decimals
     */
    public positiveInteger getDecimals() {
        throw new RuntimeException("Prohibited field in NonFacetedTextFormatType");
    }

    /**
     * @param decimals the decimals to set
     */
    public void setDecimals(positiveInteger decimals) {
       throw new RuntimeException("Prohibited field in NonFacetedTextFormatType");
    }

    /**
     * @return the pattern
     */
    public String getPattern() {
        throw new RuntimeException("Prohibited field in NonFacetedTextFormatType");
    }

    /**
     * @param pattern the pattern to set
     */
    public void setPattern(String pattern) {
        throw new RuntimeException("Prohibited field in NonFacetedTextFormatType");
    }

    /**
     * @return the isMultiLingual
     */
    public Boolean isMultiLingual() {
        throw new RuntimeException("Prohibited field in NonFacetedTextFormatType");
    }

    /**
     * @param isMultiLingual the isMultiLingual to set
     */
    public void setMultiLingual(Boolean isMultiLingual) {
        throw new RuntimeException("Prohibited field in NonFacetedTextFormatType");
    }
}

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
import sdmx.common.StandardTimePeriodType;
import sdmx.xml.duration;
import sdmx.xml.positiveInteger;

/**
 *	<xs:complexType name="TextFormatType">
		<xs:annotation>
			<xs:documentation>TextFormatType defines the information for describing a full range of text formats and may place restrictions on the values of the other attributes, referred to as "facets".</xs:documentation>
		</xs:annotation>
		<xs:attribute name="textType" type="common:DataType" default="String">
			<xs:annotation>
				<xs:documentation>The textType attribute provides a description of the datatype. If it is not specified, any valid characters may be included in the text field (it corresponds to the xs:string datatype of W3C XML Schema) within the constraints of the facets.</xs:documentation>
			</xs:annotation>
		</xs:attribute>
		<xs:attribute name="isSequence" type="xs:boolean" use="optional">
			<xs:annotation>
				<xs:documentation>The isSequence attribute indicates whether the values are intended to be ordered, and it may work in combination with the interval, startValue, and endValue attributes or the timeInterval, startTime, and endTime, attributes. If this attribute holds a value of true, a start value or time and a numeric or time interval must supplied. If an end value is not given, then the sequence continues indefinitely.</xs:documentation>
			</xs:annotation>
		</xs:attribute>
		<xs:attribute name="interval" type="xs:decimal" use="optional">
			<xs:annotation>
				<xs:documentation>The interval attribute specifies the permitted interval (increment) in a sequence. In order for this to be used, the isSequence attribute must have a value of true.</xs:documentation>
			</xs:annotation>
		</xs:attribute>
		<xs:attribute name="startValue" type="xs:decimal" use="optional">
			<xs:annotation>
				<xs:documentation>The startValue attribute is used in conjunction with the isSequence and interval attributes (which must be set in order to use this attribute). This attribute is used for a numeric sequence, and indicates the starting  point of the sequence. This value is mandatory for a numeric sequence to be expressed.</xs:documentation>
			</xs:annotation>
		</xs:attribute>
		<xs:attribute name="endValue" type="xs:decimal" use="optional">
			<xs:annotation>
				<xs:documentation>The endValue attribute is used in conjunction with the isSequence and interval attributes (which must be set in order to use this attribute). This attribute is used for a numeric sequence, and indicates that ending point (if any) of the sequence.</xs:documentation>
			</xs:annotation>
		</xs:attribute>
		<xs:attribute name="timeInterval" type="xs:duration" use="optional">
			<xs:annotation>
				<xs:documentation>The timeInterval attribute indicates the permitted duration in a time sequence. In order for this to be used, the isSequence attribute must have a value of true.</xs:documentation>
			</xs:annotation>
		</xs:attribute>
		<xs:attribute name="startTime" type="common:StandardTimePeriodType" use="optional">
			<xs:annotation>
				<xs:documentation>The startTime attribute is used in conjunction with the isSequence and timeInterval attributes (which must be set in order to use this attribute). This attribute is used for a time sequence, and indicates the start time of the sequence. This value is mandatory for a time sequence to be expressed.</xs:documentation>
			</xs:annotation>
		</xs:attribute>
		<xs:attribute name="endTime" type="common:StandardTimePeriodType" use="optional">
			<xs:annotation>
				<xs:documentation>The endTime attribute is used in conjunction with the isSequence and timeInterval attributes (which must be set in order to use this attribute). This attribute is used for a time sequence, and indicates that ending point (if any) of the sequence.</xs:documentation>
			</xs:annotation>
		</xs:attribute>
		<xs:attribute name="minLength" type="xs:positiveInteger" use="optional">
			<xs:annotation>
				<xs:documentation>The minLength attribute specifies the minimum and length of the value in characters.</xs:documentation>
			</xs:annotation>
		</xs:attribute>
		<xs:attribute name="maxLength" type="xs:positiveInteger" use="optional">
			<xs:annotation>
				<xs:documentation>The maxLength attribute specifies the maximum length of the value in characters.</xs:documentation>
			</xs:annotation>
		</xs:attribute>
		<xs:attribute name="minValue" type="xs:decimal" use="optional">
			<xs:annotation>
				<xs:documentation>The minValue attribute is used for inclusive and exclusive ranges, indicating what the lower bound of the range is. If this is used with an inclusive range, a valid value will be greater than or equal to the value specified here. If the inclusive and exclusive data type is not specified (e.g. this facet is used with an integer data type), the value is assumed to be inclusive.</xs:documentation>
			</xs:annotation>
		</xs:attribute>
		<xs:attribute name="maxValue" type="xs:decimal" use="optional">
			<xs:annotation>
				<xs:documentation>The maxValue attribute is used for inclusive and exclusive ranges, indicating what the upper bound of the range is. If this is used with an inclusive range, a valid value will be less than or equal to the value specified here. If the inclusive and exclusive data type is not specified (e.g. this facet is used with an integer data type), the value is assumed to be inclusive.</xs:documentation>
			</xs:annotation>
		</xs:attribute>
		<xs:attribute name="decimals" type="xs:positiveInteger" use="optional">
			<xs:annotation>
				<xs:documentation>The decimals attribute indicates the number of characters allowed after the decimal separator.</xs:documentation>
			</xs:annotation>
		</xs:attribute>
		<xs:attribute name="pattern" type="xs:string" use="optional">
			<xs:annotation>
				<xs:documentation>The pattern attribute holds any regular expression permitted in the similar facet in W3C XML Schema.</xs:documentation>
			</xs:annotation>
		</xs:attribute>
		<xs:attribute name="isMultiLingual" type="xs:boolean" use="optional" default="true">
			<xs:annotation>
				<xs:documentation>The isMultiLingual attribute indicates for a text format of type "string", whether the value should allow for multiple values in different languages.</xs:documentation>
			</xs:annotation>
		</xs:attribute>
	</xs:complexType>

 * @author James
 */

public class TextFormatType {
    private DataType textType = null;
    private Boolean isSequence = null;
    private Double interval = null;
    private Double startValue = null;
    private Double endValue = null;
    private duration timeInterval = null;
    private StandardTimePeriodType startTime = null;
    private StandardTimePeriodType endTime = null;
    private positiveInteger minLength = null;
    private positiveInteger maxLength = null;
    private Double minValue = null;
    private Double maxValue = null;
    private positiveInteger decimals = null;
    private String pattern = null;
    private Boolean isMultiLingual = null;

    /**
     * @return the textType
     */
    public DataType getTextType() {
        return textType;
    }

    /**
     * @param textType the textType to set
     */
    public void setTextType(DataType textType) {
        this.textType = textType;
    }

    /**
     * @return the isSequence
     */
    public Boolean isSequence() {
        return isSequence;
    }

    /**
     * @param isSequence the isSequence to set
     */
    public void setSequence(Boolean isSequence) {
        this.isSequence = isSequence;
    }

    /**
     * @return the interval
     */
    public Double getInterval() {
        return interval;
    }

    /**
     * @param interval the interval to set
     */
    public void setInterval(Double interval) {
        this.interval = interval;
    }

    /**
     * @return the startValue
     */
    public Double getStartValue() {
        return startValue;
    }

    /**
     * @param startValue the startValue to set
     */
    public void setStartValue(Double startValue) {
        this.startValue = startValue;
    }

    /**
     * @return the endValue
     */
    public Double getEndValue() {
        return endValue;
    }

    /**
     * @param endValue the endValue to set
     */
    public void setEndValue(Double endValue) {
        this.endValue = endValue;
    }

    /**
     * @return the timeInterval
     */
    public duration getTimeInterval() {
        return timeInterval;
    }

    /**
     * @param timeInterval the timeInterval to set
     */
    public void setTimeInterval(duration timeInterval) {
        this.timeInterval = timeInterval;
    }

    /**
     * @return the startTime
     */
    public StandardTimePeriodType getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(StandardTimePeriodType startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public StandardTimePeriodType getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(StandardTimePeriodType endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the minLength
     */
    public positiveInteger getMinLength() {
        return minLength;
    }

    /**
     * @param minLength the minLength to set
     */
    public void setMinLength(positiveInteger minLength) {
        this.minLength = minLength;
    }

    /**
     * @return the maxLength
     */
    public positiveInteger getMaxLength() {
        return maxLength;
    }

    /**
     * @param maxLength the maxLength to set
     */
    public void setMaxLength(positiveInteger maxLength) {
        this.maxLength = maxLength;
    }

    /**
     * @return the minValue
     */
    public Double getMinValue() {
        return minValue;
    }

    /**
     * @param minValue the minValue to set
     */
    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    /**
     * @return the maxValue
     */
    public Double getMaxValue() {
        return maxValue;
    }

    /**
     * @param maxValue the maxValue to set
     */
    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * @return the decimals
     */
    public positiveInteger getDecimals() {
        return decimals;
    }

    /**
     * @param decimals the decimals to set
     */
    public void setDecimals(positiveInteger decimals) {
        this.decimals = decimals;
    }

    /**
     * @return the pattern
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * @param pattern the pattern to set
     */
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    /**
     * @return the isMultiLingual
     */
    public Boolean isMultiLingual() {
        return isMultiLingual;
    }

    /**
     * @param isMultiLingual the isMultiLingual to set
     */
    public void setMultiLingual(Boolean isMultiLingual) {
        this.isMultiLingual = isMultiLingual;
    }
}

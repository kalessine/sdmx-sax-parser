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
package sdmx.query.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import sdmx.common.TimeDataType;
import sdmx.common.TimeRangeValueType;
import sdmx.commonreferences.CategoryReference;
import sdmx.commonreferences.DataProviderReference;
import sdmx.commonreferences.DataStructureReference;
import sdmx.commonreferences.DataflowReference;
import sdmx.commonreferences.ProvisionAgreementReference;
import sdmx.data.ColumnMapper;
import sdmx.data.DataSet;
import sdmx.data.flat.FlatObs;
import sdmx.query.base.CodeValueType;
import sdmx.query.base.QueryIDType;

/**
 *	<xs:complexType name="DataParametersType" abstract="true">
		<xs:annotation>
			<xs:documentation>DataParametersType defines the parameters for querying for data. This structure is refined by separate And/Or constructs which make logical restrictions on which parameters apply in such cases.</xs:documentation>
		</xs:annotation>
		<xs:sequence>
			<xs:element name="DataSetID" type="QueryIDType" minOccurs="0" maxOccurs="unbounded">
				<xs:annotation>
					<xs:documentation>DataSetID is used to match the id of the data set. Only data from data sets with an identifier satisfying these conditions will be matched.</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element name="DataProvider" type="common:DataProviderReferenceType" minOccurs="0" maxOccurs="unbounded">
				<xs:annotation>
					<xs:documentation>DataProvider is used to match the provider of data to the referenced data provider. Only data from data sets provided by the referenced data provider will be matched.</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element name="DataStructure" type="common:DataStructureReferenceType" minOccurs="0" maxOccurs="unbounded">
				<xs:annotation>
					<xs:documentation>DataStructure is used to match the underlying structure of the data. Only data from data sets that conform to referenced data structure definition will be matched.</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element name="Dataflow" type="common:DataflowReferenceType" minOccurs="0" maxOccurs="unbounded">
				<xs:annotation>
					<xs:documentation>Dataflow is used to match the flow which data is reported against. Only data from data sets report against referenced dataflow will be matched.</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element name="ProvisionAgreement" type="common:ProvisionAgreementReferenceType" minOccurs="0" maxOccurs="unbounded">
				<xs:annotation>
					<xs:documentation>ProvisionAgreement is used to match the provision agreement which data is reported against. Only data from data sets report against the referenced provision agreement will be matched.</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element name="Category" type="common:CategoryReferenceType" minOccurs="0" maxOccurs="unbounded">
				<xs:annotation>
					<xs:documentation>Category is used to match a data based on the categorization of its underlying structure (data structure definition), or the usage of that structure (data flow). Only data whose underlying structure or structure usage are categorized against the referenced category will be matched.</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element name="Updated" type="common:TimeRangeValueType" minOccurs="0" maxOccurs="2">
				<xs:annotation>
					<xs:documentation>Updated is used to match data based on when it was last updated (including additions and deletions). Only data which satisfies the conditions for the last update parameters supplied here will be matched.</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element name="ConceptValue" type="ConceptValueType" minOccurs="0" maxOccurs="unbounded">
				<xs:annotation>
					<xs:documentation>ConceptValue is used to match data based on the value of a particular concept. This concept may be used as a dimension, attribute, or measure for the data. So long as the referenced concept has the specified value for a given data point, it will be matched.</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element name="RepresentationValue" type="CodeValueType" minOccurs="0" maxOccurs="unbounded">
				<xs:annotation>
					<xs:documentation>RepresentationValue is used to match data based on a representation scheme having a particular value. This representation scheme may be used as the representation of a dimension, attribute, or measure. So long as the value of the concept using the referenced codelist has the value specified, any data point for the concept will be matched.</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element name="DimensionValue" type="DimensionValueType" minOccurs="0" maxOccurs="unbounded">
				<xs:annotation>
					<xs:documentation>DimensionValue is used to match data based on the value of a dimension. Any data with the dimension with the supplied identifier satisfies the conditions supplied will be matched.</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element name="TimeDimensionValue" type="TimeDimensionValueType" minOccurs="0" maxOccurs="unbounded">
				<xs:annotation>
					<xs:documentation>TimeDimensionValue is used to match data based on the value of the time dimension. Any data with a time value satisfying the conditions supplied will be matched.</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element name="AttributeValue" type="AttributeValueType" minOccurs="0" maxOccurs="unbounded">
				<xs:annotation>
					<xs:documentation>AttributeValue is used to match data based on the value of an attribute. Any data with an attribute with the supplied identifier satisfies the conditions supplied will be matched.</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element name="PrimaryMeasureValue" type="PrimaryMeasureValueType" minOccurs="0" maxOccurs="unbounded">
				<xs:annotation>
					<xs:documentation>PrimaryMeasureValue is used to match data based on the value of the primary measure. Any data with its value satisfying the conditions supplied will be matched.</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element name="AttachmentConstraint" type="common:AttachmentConstraintReferenceType" minOccurs="0" maxOccurs="unbounded">
				<xs:annotation>
					<xs:documentation>AttachmentConstraint references an attachment constraint in order to match data which matches the effective data keys or cube regions defined in the constraint. Data will be returned by first matching data on the keys and cube regions that are marked as included (or all data if none), and then excluding the data that satisfies the conditions of the excluded keys and cube regions.</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element name="TimeFormat" type="common:TimeDataType" minOccurs="0" maxOccurs="unbounded">
				<xs:annotation>
					<xs:documentation>TimeFormat is used to match data when a frequency dimension is not explicitly defined. Only data reported against the supplied time data type will be returned.</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element name="Or" type="DataParametersOrType" minOccurs="0" maxOccurs="unbounded">
				<xs:annotation>
					<xs:documentation>Or contains a collection of additional parameters, any one of which can be satisfied to result in a match.</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element name="And" type="DataParametersAndType" minOccurs="0" maxOccurs="unbounded">
				<xs:annotation>
					<xs:documentation>And contains a collection of additional parameters, all of which must be satisfied to result in a match.</xs:documentation>
				</xs:annotation>
			</xs:element>
		</xs:sequence>
	</xs:complexType>

 * @author James
 */

public class DataParametersOrType {
    private List<QueryIDType> dataSetId = null;
    private List<DataProviderReference> dataProvider = null;
    private List<DataStructureReference> dataStructure = null;
    private List<DataflowReference> dataflow = null;
    private List<ProvisionAgreementReference> provisionAgreement = null;
    private List<CategoryReference> category = null;
    private List<TimeRangeValueType> updated = null;
    private List<ConceptValueType> conceptValue = null;
    private List<CodeValueType> representationValue = null;
    private List<DimensionValueType> dimensionValue = null;
    private List<TimeDimensionValueType> timeDimensionValue=null;
    private List<AttributeValueType> attributeValue = null;
    private List<PrimaryMeasureValueType> primaryMeasureValue = null;
    private List<AttachmentConstraintReferenceType> attachmentConstraint = null;
    private List<TimeDataType> timeFormat = null;
    private List<DataParametersAndType> and = null;

    /**
     * @return the dataSetId
     */
    public List<QueryIDType> getDataSetId() {
        return dataSetId;
    }

    /**
     * @param dataSetId the dataSetId to set
     */
    public void setDataSetId(List<QueryIDType> dataSetId) {
        this.dataSetId = dataSetId;
    }

    /**
     * @return the dataProvider
     */
    public List<DataProviderReference> getDataProvider() {
        return dataProvider;
    }

    /**
     * @param dataProvider the dataProvider to set
     */
    public void setDataProvider(List<DataProviderReference> dataProvider) {
        this.dataProvider = dataProvider;
    }

    /**
     * @return the dataStructure
     */
    public List<DataStructureReference> getDataStructure() {
        return dataStructure;
    }

    /**
     * @param dataStructure the dataStructure to set
     */
    public void setDataStructure(List<DataStructureReference> dataStructure) {
        this.dataStructure = dataStructure;
    }

    /**
     * @return the dataflow
     */
    public List<DataflowReference> getDataflow() {
        return dataflow;
    }

    /**
     * @param dataflow the dataflow to set
     */
    public void setDataflow(List<DataflowReference> dataflow) {
        this.dataflow = dataflow;
    }

    /**
     * @return the provisionAgreement
     */
    public List<ProvisionAgreementReference> getProvisionAgreement() {
        return provisionAgreement;
    }

    /**
     * @param provisionAgreement the provisionAgreement to set
     */
    public void setProvisionAgreement(List<ProvisionAgreementReference> provisionAgreement) {
        this.provisionAgreement = provisionAgreement;
    }

    /**
     * @return the category
     */
    public List<CategoryReference> getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(List<CategoryReference> category) {
        this.category = category;
    }

    /**
     * @return the updated
     */
    public List<TimeRangeValueType> getUpdated() {
        return updated;
    }

    /**
     * @param updated the updated to set
     */
    public void setUpdated(List<TimeRangeValueType> updated) {
        this.updated = updated;
    }

    /**
     * @return the conceptValue
     */
    public List<ConceptValueType> getConceptValue() {
        return conceptValue;
    }

    /**
     * @param conceptValue the conceptValue to set
     */
    public void setConceptValue(List<ConceptValueType> conceptValue) {
        this.conceptValue = conceptValue;
    }

    /**
     * @return the representationValue
     */
    public List<CodeValueType> getRepresentationValue() {
        return representationValue;
    }

    /**
     * @param representationValue the representationValue to set
     */
    public void setRepresentationValue(List<CodeValueType> representationValue) {
        this.representationValue = representationValue;
    }

    /**
     * @return the dimensionValue
     */
    public List<DimensionValueType> getDimensionValue() {
        return dimensionValue;
    }

    /**
     * @param dimensionValue the dimensionValue to set
     */
    public void setDimensionValue(List<DimensionValueType> dimensionValue) {
        this.dimensionValue = dimensionValue;
    }

    /**
     * @return the timeDimensionValue
     */
    public List<TimeDimensionValueType> getTimeDimensionValue() {
        return timeDimensionValue;
    }

    /**
     * @param timeDimensionValue the timeDimensionValue to set
     */
    public void setTimeDimensionValue(List<TimeDimensionValueType> timeDimensionValue) {
        this.timeDimensionValue = timeDimensionValue;
    }

    /**
     * @return the attributeValue
     */
    public List<AttributeValueType> getAttributeValue() {
        return attributeValue;
    }

    /**
     * @param attributeValue the attributeValue to set
     */
    public void setAttributeValue(List<AttributeValueType> attributeValue) {
        this.attributeValue = attributeValue;
    }

    /**
     * @return the primaryMeasureValue
     */
    public List<PrimaryMeasureValueType> getPrimaryMeasureValue() {
        return primaryMeasureValue;
    }

    /**
     * @param primaryMeasureValue the primaryMeasureValue to set
     */
    public void setPrimaryMeasureValue(List<PrimaryMeasureValueType> primaryMeasureValue) {
        this.primaryMeasureValue = primaryMeasureValue;
    }

    /**
     * @return the attachmentConstraint
     */
    public List<AttachmentConstraintReferenceType> getAttachmentConstraint() {
        return attachmentConstraint;
    }

    /**
     * @param attachmentConstraint the attachmentConstraint to set
     */
    public void setAttachmentConstraint(List<AttachmentConstraintReferenceType> attachmentConstraint) {
        this.attachmentConstraint = attachmentConstraint;
    }

    /**
     * @return the timeFormat
     */
    public List<TimeDataType> getTimeFormat() {
        return timeFormat;
    }

    /**
     * @param timeFormat the timeFormat to set
     */
    public void setTimeFormat(List<TimeDataType> timeFormat) {
        this.timeFormat = timeFormat;
    }

    /**
     * @return the and
     */
    public List<DataParametersAndType> getAnd() {
        return and;
    }

    /**
     * @param and the and to set
     */
    public void setAnd(List<DataParametersAndType> and) {
        this.and = and;
    }
    public boolean match(ColumnMapper mapper,DataSet set,int row) {
       for(int i=0;i<attributeValue.size();i++) {
           Object value = set.getValue(row, mapper.getColumnIndex(attributeValue.get(i).getId().toString()));
           if( value instanceof String ) {
               if( !attributeValue.get(i).match((String) value)) return true;
           }
           if( value instanceof CodeValueType ) {
               if( !attributeValue.get(i).match(((CodeValueType)value).getValue())) return true;
           }
        }
        for(int i=0;i<this.dimensionValue.size();i++) {
           Object value = set.getValue(row, mapper.getColumnIndex(attributeValue.get(i).getId().toString()));
           if( value instanceof String ) {
               if( !dimensionValue.get(i).match((String) value)) return true;
           }
           if( value instanceof CodeValueType ) {
               if( !dimensionValue.get(i).match(((CodeValueType)value).getValue())) return true;
           }
        }
        for(int i=0;i<this.primaryMeasureValue.size();i++) {
           Object value = set.getValue(row, mapper.getColumnIndex(attributeValue.get(i).getId().toString()));
           if( value instanceof String ) {
               if( !primaryMeasureValue.get(i).match((String) value)) return true;
           }
           if( value instanceof CodeValueType ) {
               if( !primaryMeasureValue.get(i).match(((CodeValueType)value).getValue())) return true;
           }
        }
        for(int i=0;i<this.timeDimensionValue.size();i++) {
           Object value = set.getValue(row, mapper.getColumnIndex(attributeValue.get(i).getId().toString()));
           if( value instanceof String ) {
               if( !timeDimensionValue.get(i).match((String) value)) return true;
           }
           if( value instanceof CodeValueType ) {
               if( !timeDimensionValue.get(i).match(((CodeValueType)value).getValue())) return true;
           }
        }
        return false;
     }
    public List<String> getDimensionParameters(String concept) {
        List<String> result = new ArrayList<String>();
        for(int i=0;dimensionValue!=null&&i<this.dimensionValue.size();i++) {
            if( dimensionValue.get(i).getId().equals(concept))result.add(dimensionValue.get(i).getValue());
        }
        for(int i=0;and!=null&&i<this.and.size();i++) {
            result.addAll(and.get(i).getDimensionParameters(concept));
        }        
        return result;
    }
}

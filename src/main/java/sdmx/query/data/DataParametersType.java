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
import sdmx.query.base.CodeValueType;
import sdmx.query.base.QueryIDType;

/**
 *
 * @author James
 */

public class DataParametersType  {
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
    private List<DataParametersOrType> or = null;
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
     * @return the or
     */
    public List<DataParametersOrType> getOr() {
        return or;
    }

    /**
     * @param or the or to set
     */
    public void setOr(List<DataParametersOrType> or) {
        this.or = or;
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
       for(int i=0;attributeValue!=null&&i<attributeValue.size();i++) {
           Object value = set.getValue(row, mapper.getColumnIndex(attributeValue.get(i).getId().toString()));
           if( value instanceof String ) {
               if( !attributeValue.get(i).match((String) value)) return false;
           }
           if( value instanceof CodeValueType ) {
               if( !attributeValue.get(i).match(((CodeValueType)value).getValue())) return false;
           }
        }
        for(int i=0;dimensionValue!=null&&i<this.dimensionValue.size();i++) {
           Object value = set.getValue(row, mapper.getColumnIndex(dimensionValue.get(i).getId().toString()));
           if( value instanceof String ) {
               if( !dimensionValue.get(i).match((String) value)) return false;
           }
           if( value instanceof CodeValueType ) {
               if( !dimensionValue.get(i).match(((CodeValueType)value).getValue())) return false;
           }
        }
        for(int i=0;primaryMeasureValue!=null&&i<this.primaryMeasureValue.size();i++) {
           Object value = set.getValue(row, mapper.getColumnIndex(primaryMeasureValue.get(i).getId().toString()));
           if( value instanceof String ) {
               if( !primaryMeasureValue.get(i).match((String) value)) return false;
           }
           if( value instanceof CodeValueType ) {
               if( !primaryMeasureValue.get(i).match(((CodeValueType)value).getValue())) return false;
           }
        }
        for(int i=0;timeDimensionValue!=null&&i<this.timeDimensionValue.size();i++) {
           Object value = set.getValue(row, mapper.getColumnIndex(timeDimensionValue.get(i).getId().toString()));
           if( value instanceof String ) {
               if( !timeDimensionValue.get(i).match((String) value)) return false;
           }
           if( value instanceof CodeValueType ) {
               if( !timeDimensionValue.get(i).match(((CodeValueType)value).getValue())) return false;
           }
        }
        return true;
    }   
    public List<String> getDimensionParameters(String concept) {
        List<String> result = new ArrayList<String>();
        for(int i=0;dimensionValue!=null&&i<this.dimensionValue.size();i++) {
            if( dimensionValue.get(i).getId().equals(concept))result.add(dimensionValue.get(i).getValue());
        }
        for(int i=0;and!=null&&i<this.and.size();i++) {
            result.addAll(and.get(i).getDimensionParameters(concept));
        }        
        for(int i=0;or!=null&&i<this.or.size();i++) {
            result.addAll(or.get(i).getDimensionParameters(concept));
        }
        return result;
    }
}

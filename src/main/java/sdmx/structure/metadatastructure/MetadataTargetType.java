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
package sdmx.structure.metadatastructure;

import java.util.List;
import sdmx.common.Annotations;
import sdmx.commonreferences.IDType;

/**
	<xs:complexType name="MetadataTargetType">
		<xs:annotation>
			<xs:documentation></xs:documentation>
		</xs:annotation>
		<xs:complexContent>
			<xs:extension base="MetadataTargetBaseType">
				<xs:choice maxOccurs="unbounded">
					<xs:element ref="KeyDescriptorValuesTarget"/>
					<xs:element ref="DataSetTarget"/>
					<xs:element ref="ConstraintContentTarget"/>
					<xs:element ref="ReportPeriodTarget"/>
					<xs:element ref="IdentifiableObjectTarget"/>
				</xs:choice>
			</xs:extension>
		</xs:complexContent>
	</xs:complexType>

 * @author James
 */

public class MetadataTargetType extends MetadataTargetBaseType {
    
    private List<KeyDescriptorValuesTarget> keyDescriptor = null;
    private List<DataSetTarget> dataSetTarget = null;
    private List<ConstraintContentTarget> constraintContentTarget = null;
    private List<ReportPeriodTarget> reportPeriodTarget = null;
    private List<IdentifiableObjectTarget> identifiableObjectTarget = null;

    public MetadataTargetType(Annotations annots, IDType id) {
        super(annots,id);
    }

    /**
     * @return the keyDescriptor
     */
    public List<KeyDescriptorValuesTarget> getKeyDescriptor() {
        return keyDescriptor;
    }

    /**
     * @param keyDescriptor the keyDescriptor to set
     */
    public void setKeyDescriptor(List<KeyDescriptorValuesTarget> keyDescriptor) {
        this.keyDescriptor = keyDescriptor;
    }

    /**
     * @return the dataSetTarget
     */
    public List<DataSetTarget> getDataSetTarget() {
        return dataSetTarget;
    }

    /**
     * @param dataSetTarget the dataSetTarget to set
     */
    public void setDataSetTarget(List<DataSetTarget> dataSetTarget) {
        this.dataSetTarget = dataSetTarget;
    }

    /**
     * @return the constraintContentTarget
     */
    public List<ConstraintContentTarget> getConstraintContentTarget() {
        return constraintContentTarget;
    }

    /**
     * @param constraintContentTarget the constraintContentTarget to set
     */
    public void setConstraintContentTarget(List<ConstraintContentTarget> constraintContentTarget) {
        this.constraintContentTarget = constraintContentTarget;
    }

    /**
     * @return the reportPeriodTarget
     */
    public List<ReportPeriodTarget> getReportPeriodTarget() {
        return reportPeriodTarget;
    }

    /**
     * @param reportPeriodTarget the reportPeriodTarget to set
     */
    public void setReportPeriodTarget(List<ReportPeriodTarget> reportPeriodTarget) {
        this.reportPeriodTarget = reportPeriodTarget;
    }

    /**
     * @return the identifiableObjectTarget
     */
    public List<IdentifiableObjectTarget> getIdentifiableObjectTarget() {
        return identifiableObjectTarget;
    }

    /**
     * @param identifiableObjectTarget the identifiableObjectTarget to set
     */
    public void setIdentifiableObjectTarget(List<IdentifiableObjectTarget> identifiableObjectTarget) {
        this.identifiableObjectTarget = identifiableObjectTarget;
    }
    
}

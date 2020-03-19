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

import java.util.ArrayList;
import java.util.List;
import sdmx.common.CubeRegionType;
import sdmx.common.MetadataTargetRegionType;
import sdmx.common.QueryableDataSourceType;

/**
 *	<xs:complexType name="ConstraintType" abstract="true">
		<xs:annotation>
			<xs:documentation>ConstraintType is an abstract base type that specific types of constraints (content and attachment) restrict and extend to describe their details. The inclusion of a key or region in a constraint is determined by first processing the included key sets, and then removing those keys defined in the excluded key sets. If no included key sets are defined, then it is assumed the all possible keys or regions are included, and any excluded key or regions are removed from this complete set.</xs:documentation>
		</xs:annotation>
		<xs:complexContent>
			<xs:extension base="ConstraintBaseType">
				<xs:sequence>
					<xs:element name="ConstraintAttachment" type="ConstraintAttachmentType" minOccurs="0">
						<xs:annotation>
							<xs:documentation>ConstraintAttachment describes the collection of constrainable artefacts that the constraint is attached to.</xs:documentation>
						</xs:annotation>
					</xs:element>
					<xs:choice minOccurs="0" maxOccurs="unbounded">
						<xs:element name="DataKeySet" type="DataKeySetType">
							<xs:annotation>
								<xs:documentation></xs:documentation>
							</xs:annotation>
						</xs:element>
						<xs:element name="MetadataKeySet" type="MetadataKeySetType">
							<xs:annotation>
								<xs:documentation></xs:documentation>
							</xs:annotation>
						</xs:element>
						<xs:element name="CubeRegion" type="common:CubeRegionType">
							<xs:annotation>
								<xs:documentation></xs:documentation>
							</xs:annotation>
						</xs:element>
						<xs:element name="MetadataTargetRegion" type="common:MetadataTargetRegionType">
							<xs:annotation>
								<xs:documentation></xs:documentation>
							</xs:annotation>
						</xs:element>
					</xs:choice>
				</xs:sequence>
			</xs:extension>
		</xs:complexContent>
	</xs:complexType>

 * @author James
 */

public class ConstraintType extends ConstraintBaseType {
    
    private ConstraintAttachmentType constraintAttachment=null;
    
// Choice of 1    
    private List<DataKeySetType> dataKeySet = null;
    private List<MetadataKeySetType> metaDataKeySet = null;
    private List<CubeRegionType> cubeRegion = null;
    private List<MetadataTargetRegionType> metadataTargetRegion = null;

    /**
     * @return the constraintAttachment
     */
    public ConstraintAttachmentType getConstraintAttachment() {
        return constraintAttachment;
    }

    /**
     * @param constraintAttachment the constraintAttachment to set
     */
    public void setConstraintAttachment(ConstraintAttachmentType constraintAttachment) {
        this.constraintAttachment = constraintAttachment;
    }

    /**
     * @return the dataKeySet
     */
    public List<DataKeySetType> getDataKeySet() {
        return dataKeySet;
    }

    /**
     * @param dataKeySet the dataKeySet to set
     */
    public void setDataKeySet(List<DataKeySetType> dataKeySet) {
        this.dataKeySet = dataKeySet;
    }

    /**
     * @return the metaDataKeySet
     */
    public List<MetadataKeySetType> getMetaDataKeySet() {
        return metaDataKeySet;
    }

    /**
     * @param metaDataKeySet the metaDataKeySet to set
     */
    public void setMetaDataKeySet(List<MetadataKeySetType> metaDataKeySet) {
        this.metaDataKeySet = metaDataKeySet;
    }

    /**
     * @return the cubeRegion
     */
    public List<CubeRegionType> getCubeRegion() {
        return cubeRegion;
    }

    /**
     * @param cubeRegion the cubeRegion to set
     */
    public void setCubeRegion(List<CubeRegionType> cubeRegion) {
        this.cubeRegion = cubeRegion;
    }

    /**
     * @return the metadataTargetRegion
     */
    public List<MetadataTargetRegionType> getMetadataTargetRegion() {
        return metadataTargetRegion;
    }

    /**
     * @param metadataTargetRegion the metadataTargetRegion to set
     */
    public void setMetadataTargetRegion(List<MetadataTargetRegionType> metadataTargetRegion) {
        this.metadataTargetRegion = metadataTargetRegion;
    }
    
}

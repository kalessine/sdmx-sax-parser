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
package sdmx.structure.dataflow;

import sdmx.commonreferences.DataStructureReference;
import sdmx.commonreferences.DataflowRef;
import sdmx.commonreferences.DataflowReference;
import sdmx.commonreferences.RefBase;
import sdmx.commonreferences.ReferenceType;
import sdmx.structure.base.StructureUsageType;

/**
 *	<xs:complexType name="DataflowType">
		<xs:annotation>
			<xs:documentation>DataflowType describes the structure of a data flow. A data flow is defined as the structure of data that will provided for different reference periods. If this type is not referenced externally, then a reference to a key family definition must be provided.</xs:documentation>
		</xs:annotation>
		<xs:complexContent>
			<xs:restriction base="StructureUsageType">
				<xs:sequence>
					<xs:element ref="common:Annotations" minOccurs="0"/>
					<xs:element ref="common:Name" maxOccurs="unbounded"/>
					<xs:element ref="common:Description" minOccurs="0" maxOccurs="unbounded"/>
					<xs:element name="Structure" type="common:DataStructureReferenceType" minOccurs="0">
						<xs:annotation>
							<xs:documentation>Structure provides a reference to the data structure definition which defines the structure of all data for this flow.</xs:documentation>
						</xs:annotation>
					</xs:element>
				</xs:sequence>
			</xs:restriction>
		</xs:complexContent>		
	</xs:complexType>
 * @author James
 */

public class DataflowType extends StructureUsageType {
    @Override
    public DataStructureReference getStructure() {
        return (DataStructureReference)super.getStructure();
    }

    public void dump() {
    }
    public DataflowReference asReference() {
        DataflowRef ref = new DataflowRef(this.getAgencyID(),getId(),getVersion());
        DataflowReference reference = new DataflowReference(ref,getUri());
        return reference;
    }
}

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
package sdmx.commonreferences;

import sdmx.common.choice.ComponentValueSetTypeChoice;
import sdmx.common.choice.DistinctKeyValueTypeChoice;
import sdmx.common.choice.MetadataKeyValueTypeChoice;
import sdmx.common.choice.MetadataTargetRegionKeyTypeChoice;
import sdmx.commonreferences.DataProviderReference;
import sdmx.commonreferences.IDType;

/**
 *	<xs:complexType name="SetReferenceType">
		<xs:annotation>
			<xs:documentation>SetReferenceType defines the structure of a reference to a data/metadata set. A full reference to a data provider and the identifier for the data set must be provided. Note that this is not derived from the base reference structure since data/metadata sets are not technically identifiable.</xs:documentation>
		</xs:annotation>
		<xs:sequence>
			<xs:element name="DataProvider" type="DataProviderReferenceType">
				<xs:annotation>
					<xs:documentation>DataProvider references a the provider of the data/metadata set. A URN and/or a complete set of reference fields must be provided.</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element name="ID" type="IDType">
				<xs:annotation>
					<xs:documentation>ID contains the identifier of the data/metadata set being referenced.</xs:documentation>
				</xs:annotation>
			</xs:element>
		</xs:sequence>
	</xs:complexType>
 * @author James
 */
public class SetReference implements ComponentValueSetTypeChoice,DistinctKeyValueTypeChoice,MetadataKeyValueTypeChoice,MetadataTargetRegionKeyTypeChoice {
    DataProviderReference ref;
    IDType id;
    public SetReference(DataProviderReference ref, IDType id){
        this.ref=ref;
        this.id=id;
    }
}

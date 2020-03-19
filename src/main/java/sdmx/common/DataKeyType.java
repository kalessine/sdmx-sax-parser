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

import java.util.ArrayList;
import java.util.List;
import sdmx.common.choice.ComponentValueSetTypeChoice;
import sdmx.common.choice.DistinctKeyValueTypeChoice;
import sdmx.common.choice.MetadataKeyValueTypeChoice;
import sdmx.common.choice.MetadataTargetRegionKeyTypeChoice;

/**
 *	<xs:complexType name="DataKeyType">
		<xs:annotation>
			<xs:documentation>DataKeyType is a region which defines a distinct full or partial data key. The key consists of a set of values, each referencing a dimension and providing a single value for that dimension. The purpose of the key is to define a subset of a data set (i.e. the observed value and data attribute) which have the dimension values provided in this definition. Any dimension not stated explicitly in this key is assumed to be wild carded, thus allowing for the definition of partial data keys.</xs:documentation>
		</xs:annotation>
		<xs:complexContent>
			<xs:restriction base="DistinctKeyType">
				<xs:sequence>
					<xs:element name="KeyValue" type="DataKeyValueType" maxOccurs="unbounded"/>
				</xs:sequence>
			</xs:restriction>
		</xs:complexContent>
	</xs:complexType>
 * @author James
 */
public class DataKeyType extends DistinctKeyType implements ComponentValueSetTypeChoice,DistinctKeyValueTypeChoice,MetadataKeyValueTypeChoice,MetadataTargetRegionKeyTypeChoice {
    private List<DataKeyValueType> keyValues = null;
    public DataKeyType(List<DataKeyValueType> choices, List<ComponentValueSetType> attrs) {
        super(attrs,toDVT(choices));
        this.keyValues=choices;
    }
    private static List<DistinctKeyValueType> toDVT(List<DataKeyValueType> keys) {
        List<DistinctKeyValueType> list = new ArrayList<DistinctKeyValueType>(keys.size());
        list.addAll(keys);
        return list;
    }
    public DataKeyValueType getDataKeyValueType(int i) {
        return keyValues.get(i);
    }
    public int getDataKeyValueTypeSize() { return keyValues.size(); }
}

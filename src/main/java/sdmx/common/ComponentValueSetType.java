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

import sdmx.commonreferences.NestedNCNameID;
import java.util.ArrayList;
import java.util.List;
import sdmx.common.choice.ComponentValueSetTypeChoice;

/**
 *	<xs:complexType name="ComponentValueSetType" abstract="true">
<xs:annotation>
<xs:documentation>ComponentValueSetType is an abstract base type which is used to provide a set of value for a referenced component. Implementations of this type will be based on a particular component type and refine the allowed values to reflect the types of values that are possible for that type of component.</xs:documentation>
</xs:annotation>
<xs:choice minOccurs="0">
<xs:element name="Value" type="SimpleValueType" maxOccurs="unbounded">
<xs:annotation>
<xs:documentation>Value provides a simple value for the component, such as a coded, numeric, or simple text value. This type of component value is applicable for dimensions and attributes.</xs:documentation>
</xs:annotation>
</xs:element>
<xs:element name="DataSet" type="SetReferenceType" maxOccurs="unbounded">
<xs:annotation>
<xs:documentation>DataSet provides a reference to a data set and is used to state a value for the data set target component in a metadata target.</xs:documentation>
</xs:annotation>
</xs:element>
<xs:element name="DataKey" type="DataKeyType" maxOccurs="unbounded">
<xs:annotation>
<xs:documentation>DataKey provides a set of dimension references and value, which form a full or partial data key. This is used to state a value for the key descriptor values target component in a metadata target.</xs:documentation>
</xs:annotation>
</xs:element>
<xs:element name="Object" type="ObjectReferenceType" maxOccurs="unbounded">
<xs:annotation>
<xs:documentation>Object provides a reference to an Identifiable object in the SDMX Information Model. This is used to state a value for an identifiable target component in a metadata target.</xs:documentation>
</xs:annotation>
</xs:element>
<xs:element name="TimeRange" type="TimeRangeValueType">
<xs:annotation>
<xs:documentation>TimeValue provides a value for a component which has a time representation. This is repeatable to allow for a range to be specified, although a single value can also be provided. An operator is available on this to indicate whether the specified value indicates an exact value or the beginning/end of a range (inclusive or exclusive).</xs:documentation>
</xs:annotation>
</xs:element>
</xs:choice>
<xs:attribute name="id" type="NestedNCNameIDType" use="required">
<xs:annotation>
<xs:documentation>The id attribute provides the identifier for the component for which values are being provided. This base type allows for a nested identifier to be provided, for the purpose of referencing a nested component (i.e. a metadata attribute). However, specific implementations will restrict this representation to only allow single level identifiers where appropriate.</xs:documentation>
</xs:annotation>
</xs:attribute>
<xs:attribute name="include" type="xs:boolean" use="optional" default="true">
<xs:annotation>
<xs:documentation>The include attribute indicates whether the values provided for the referenced component are to be included are excluded from the region in which they are defined.</xs:documentation>
</xs:annotation>
</xs:attribute>
</xs:complexType>	
 * @author James
 */
public class ComponentValueSetType {


    ComponentValueSetTypeChoice choice = null;

    private NestedNCNameID id=null;// Required
    private boolean include = true;

    public ComponentValueSetType(ComponentValueSetTypeChoice value,NestedNCNameID id) {
        this.choice=value;
        this.id=id;
    }
}

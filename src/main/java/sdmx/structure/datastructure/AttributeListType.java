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
package sdmx.structure.datastructure;

import java.util.ArrayList;
import java.util.List;
import sdmx.common.Annotations;
import sdmx.commonreferences.IDType;
import sdmx.structure.base.Component;
import sdmx.structure.base.ComponentListType;
import sdmx.structure.datastructure.AttributeType;
import sdmx.structure.datastructure.AttributeBaseType;

/**
 *	<xs:complexType name="AttributeListBaseType" abstract="true">
		<xs:annotation>
				<xs:documentation>AttributeListBaseType is an abstract base type used as the basis for the AttributeListType.</xs:documentation>
		</xs:annotation>		
		<xs:complexContent>	
			<xs:restriction base="ComponentListType">
				<xs:sequence>
					<xs:element ref="common:Annotations" minOccurs="0"/>
				</xs:sequence>
				<xs:attribute name="id" type="common:IDType" use="optional" fixed="AttributeDescriptor">
					<xs:annotation>
						<xs:documentation>The id attribute is provided in this case for completeness. However, its value is fixed to AttributeDescriptor.</xs:documentation>
					</xs:annotation>
				</xs:attribute>
			</xs:restriction>
		</xs:complexContent>
	</xs:complexType>
 * @author James
 */

public class AttributeListType extends AttributeListBaseType {
   List<AttributeType> attributes = new ArrayList<AttributeType>();
    
   public List<AttributeType> getAttributes() {
       return attributes;
   }
   
   public void setAttributes(List<AttributeType> at) {
       this.attributes=at;
   }
   
   public AttributeType getAttribute(int i) {
       return attributes.get(i);
   }
   public int size() { return attributes.size();}
   public void addAttribute(AttributeType at) {
       this.attributes.add(at);
   }
}

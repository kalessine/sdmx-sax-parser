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
package sdmx.structure.category;

import java.util.ArrayList;
import java.util.List;
import sdmx.commonreferences.IDType;
import sdmx.commonreferences.NCNameID;
import sdmx.structure.base.ItemSchemeType;
import sdmx.structure.base.ItemType;

/**
 *	<xs:complexType name="CategorySchemeType">
		<xs:annotation>
			<xs:documentation>CategorySchemeType describes the structure of a category scheme. A category scheme is the descriptive information for an arrangement or division of categories into groups based on characteristics, which the objects have in common. This provides for a simple, leveled hierarchy or categories.</xs:documentation>
		</xs:annotation>
		<xs:complexContent>
			<xs:restriction base="ItemSchemeType">
				<xs:sequence>
					<xs:element ref="common:Annotations" minOccurs="0"/>
					<xs:element ref="common:Name" maxOccurs="unbounded"/>
					<xs:element ref="common:Description" minOccurs="0" maxOccurs="unbounded"/>
					<xs:sequence minOccurs="0" maxOccurs="unbounded">
						<xs:element ref="Category"/>
					</xs:sequence>
				</xs:sequence>
				<xs:attribute name="id" type="common:NCNameIDType" use="required">
					<xs:annotation>
						<xs:documentation>The id attribute holds the identification of the category scheme. The type of this id is restricted to the common:NCNNameIDType. This is necessary, since the category scheme may be used to create simple types in data and metadata structure specific schemas and therefore must be compliant with the NCName type in XML Schema (see common:NCNameIDType for further details).</xs:documentation>
					</xs:annotation>
				</xs:attribute>
			</xs:restriction>
		</xs:complexContent>
	</xs:complexType>

 * @author James
 */

public class CategorySchemeType extends ItemSchemeType {
    public CategorySchemeType() {
        
    }
    public Category getCategory(int i){
        return (Category)super.getItem(i);
    }
    public void setCategory(int i,Category c){
        super.setItem(i,c);
    }
    public void setCategories(List<CategoryType> list) {
        List<ItemType> items = new ArrayList<ItemType>(list.size());
        items.addAll(list);
        super.setItems(items);
    }

    public CategoryType findCategory(String s) {
        return findCategory(new IDType(s));
    }
    public CategoryType findCategory(IDType id) {
        return (CategoryType)super.findItem(id);
    }
    
    public void dump() {

    }
}

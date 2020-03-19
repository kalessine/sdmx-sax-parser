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
package sdmx.query.base;

import java.util.List;
import sdmx.commonreferences.LocalItemReference;

/**
 *	<xs:complexType name="ItemWhereType" abstract="true">
		<xs:annotation>
			<xs:documentation>ItemQueryType is an abstract base type that serves as the basis for a query for an item within an item scheme query. A nested item where is provided to query for items nested within other items. The conditions within an item query are implied to be in an and-query. If an id and a child item where condition are supplied, then both conditions will have to met in order for the item query to return true. If, for instance, a query based on names in multiple languages is required, then multiple instances of the element utilizing this type should be used within an or-query container.</xs:documentation>
		</xs:annotation>
		<xs:complexContent>
			<xs:extension base="NameableWhereType">
				<xs:choice minOccurs="0">
					<xs:element name="Parent" type="common:LocalItemReferenceType">
						<xs:annotation>
							<xs:documentation>Parent is used to query for an item where it declares the item referenced here as its parent. This is used for items that are not nested in a hierarchy. If child items are sought for an item that is contained in a nested hierarchy (e.g. a category) on can query directly for the parent category and request that the child items be returned by specifying cascadeMatchedItems in the detail field of the return details.</xs:documentation>
						</xs:annotation>
					</xs:element>
					<xs:sequence maxOccurs="unbounded">
						<xs:element ref="ItemWhere"/>
					</xs:sequence>					
				</xs:choice>
			</xs:extension>
		</xs:complexContent>
	</xs:complexType>

 * @author James
 */

public class ItemWhereType extends NameableWhereType {
    private LocalItemReference parent = null;
    private List<ItemWhereType> itemWheres = null;

    /**
     * @return the parent
     */
    public LocalItemReference getParent() {
        return parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(LocalItemReference parent) {
        this.parent = parent;
    }

    /**
     * @return the itemWheres
     */
    public List<ItemWhereType> getItemWheres() {
        return itemWheres;
    }

    /**
     * @param itemWheres the itemWheres to set
     */
    public void setItemWheres(List<ItemWhereType> itemWheres) {
        this.itemWheres = itemWheres;
    }
    
}

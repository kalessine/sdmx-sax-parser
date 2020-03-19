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

/**
 *	<xs:complexType name="NameableWhereType" abstract="true">
		<xs:annotation>
			<xs:documentation>NameableWhereType is an abstract base type that serves as the basis for any query for a nameable object.</xs:documentation>
		</xs:annotation>
		<xs:complexContent>
			<xs:extension base="IdentifiableWhereType">
				<xs:sequence>
					<xs:element name="Name" type="QueryTextType" minOccurs="0">
						<xs:annotation>
							<xs:documentation>Name is used to match the name of the identified object. It may occur multiple times for its usage within an or-query or for multi-lingual searches, however if multiple values are supplied in an and-query (explicit or implicit), each name search will have to be found in order to constitute a match. The value here can either be an explicit value (exact match) or a regular expression pattern on which to match.</xs:documentation>
						</xs:annotation>
					</xs:element>
					<xs:element name="Description" type="QueryTextType" minOccurs="0">
						<xs:annotation>
							<xs:documentation>Description is used to match the description of the identified object. It may occur multiple times for its usage within an or-query or for multi-lingual searches, however if multiple values are supplied in an and-query (explicit or implicit), each description search will have to be found in order to constitute a match. The value here can either be an explicit value (exact match) or a regular expression pattern on which to match.</xs:documentation>
						</xs:annotation>
					</xs:element>					
				</xs:sequence>
			</xs:extension>
		</xs:complexContent>
	</xs:complexType>

 * @author James
 */

public class NameableWhereType extends IdentifiableWhereType {
    private List<QueryTextType> names = null;
    private List<QueryTextType> description = null;

    /**
     * @return the names
     */
    public List<QueryTextType> getNames() {
        return names;
    }

    /**
     * @param names the names to set
     */
    public void setNames(List<QueryTextType> names) {
        this.names = names;
    }

    /**
     * @return the description
     */
    public List<QueryTextType> getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(List<QueryTextType> description) {
        this.description = description;
    }
}

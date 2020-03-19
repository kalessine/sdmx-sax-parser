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
import sdmx.xml.anyURI;

/**
 *	<xs:complexType name="IdentifiableWhereType" abstract="true">
		<xs:annotation>
			<xs:documentation>IdentifiableWhereType is an abstract base type that serves as the basis for any query for an identifiable object.</xs:documentation>
		</xs:annotation>
		<xs:complexContent>
			<xs:extension base="AnnotableWhereType">
				<xs:sequence>
					<xs:element name="URN" type="xs:anyURI" minOccurs="0">
						<xs:annotation>
							<xs:documentation>URN is used to match the urn of any SDMX object.</xs:documentation>
						</xs:annotation>
					</xs:element>
					<xs:element name="ID" type="QueryIDType" minOccurs="0">
						<xs:annotation>
							<xs:documentation>ID is used to match the id of the identified object.</xs:documentation>
						</xs:annotation>
					</xs:element>
				</xs:sequence>
			</xs:extension>
		</xs:complexContent>
	</xs:complexType>

 * @author James
 */

public class IdentifiableWhereType extends AnnotableWhereType {
    private List<anyURI> urn = null;
    private List<QueryIDType> id = null;

    /**
     * @return the urn
     */
    public List<anyURI> getUrn() {
        return urn;
    }

    /**
     * @param urn the urn to set
     */
    public void setUrn(List<anyURI> urn) {
        this.urn = urn;
    }

    /**
     * @return the id
     */
    public List<QueryIDType> getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(List<QueryIDType> id) {
        this.id = id;
    }
}

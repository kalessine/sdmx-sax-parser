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

import sdmx.commonreferences.types.MaintainableTypeCodelistType;

/**
 *	<xs:complexType name="MaintainableWhereType" abstract="true">
		<xs:annotation>
			<xs:documentation>MaintainableQueryType is an abstract base type that serves as the basis for any query for a maintainable object.</xs:documentation>
		</xs:annotation>
		<xs:complexContent>
			<xs:extension base="VersionableWhereType">
				<xs:sequence>
					<xs:element name="AgencyID" type="QueryNestedIDType" minOccurs="0">
						<xs:annotation>
							<xs:documentation>AgencyID is used to match the agency id of the maintained object.</xs:documentation>
						</xs:annotation>
					</xs:element>
				</xs:sequence>
				<xs:attribute name="type" type="common:MaintainableTypeCodelistType" use="optional">
					<xs:annotation>
						<xs:documentation>The type attribute optionally defines the type of object being queried. For queries for distinct types of objects, a fixed value should be specified in the derived queries. For queries that serve to query for like types of objects, this should be required.</xs:documentation>
					</xs:annotation>
				</xs:attribute>
			</xs:extension>
		</xs:complexContent>
	</xs:complexType>

 * @author James
 */

public class MaintainableWhereType extends VersionableWhereType {
    private QueryNestedIDType agencyId = null;
    private MaintainableTypeCodelistType type = null;

    /**
     * @return the agencyId
     */
    public QueryNestedIDType getAgencyId() {
        return agencyId;
    }

    /**
     * @param agencyId the agencyId to set
     */
    public void setAgencyId(QueryNestedIDType agencyId) {
        this.agencyId = agencyId;
    }

    /**
     * @return the type
     */
    public MaintainableTypeCodelistType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(MaintainableTypeCodelistType type) {
        this.type = type;
    }
}

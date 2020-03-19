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

import java.net.URI;
import sdmx.xml.anyURI;

/**
 *	<xs:complexType name="MaintainableReferenceBaseType" abstract="true">
		<xs:annotation>
			<xs:documentation>MaintainableReferenceBaseType is an abstract base type for referencing a maintainable object. It consists of a URN and/or a complete set of reference fields; agency, id, and version.</xs:documentation>
		</xs:annotation>
		<xs:complexContent>
			<xs:restriction base="ReferenceType">
				<xs:choice>
					<xs:sequence>
						<xs:element name="Ref" type="MaintainableRefBaseType" form="unqualified"/>
						<xs:element name="URN" type="xs:anyURI" form="unqualified" minOccurs="0"/>
					</xs:sequence>
					<xs:element name="URN" type="xs:anyURI" form="unqualified"/>
				</xs:choice>
			</xs:restriction>
		</xs:complexContent>
	</xs:complexType>
 * @author James
 */

public class MaintainableReferenceBase extends ReferenceType {
     public MaintainableReferenceBase(MaintainableRefBase ref,anyURI urn){
         super(ref,urn);
     }
     public MaintainableReferenceBase(anyURI urn){
         super(urn);
     }
     public String toString() {
         if( getRef()!=null ) {
             StringBuilder builder = new StringBuilder();
             if( getAgencyId()!=null ) builder.append(getAgencyId().toString());
             if( getMaintainableParentId()!=null) builder.append(getMaintainableParentId().toString());
             if( getId()!=null)builder.append(","+getId());
             if( getVersion()!=null)builder.append(","+getVersion());
             return builder.toString();
         }
         if( this.getUrn()!=null ) {
             return this.getUrn().toString();
         }
         return "";
     }
}

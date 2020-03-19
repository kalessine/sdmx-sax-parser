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

import sdmx.commonreferences.types.CodeTypeCodelistType;
import sdmx.commonreferences.types.PackageTypeCodelistType;

/**
 *	<xs:complexType name="AnyLocalCodeRefType">
		<xs:annotation>
			<xs:documentation>AnyLocalCodeRefType provides a local reference to any code object.</xs:documentation>
		</xs:annotation>
		<xs:complexContent>
			<xs:restriction base="RefBaseType">
				<xs:attribute name="agencyID" type="NestedNCNameIDType" use="prohibited"/>
				<xs:attribute name="maintainableParentID" type="IDType" use="prohibited"/>
				<xs:attribute name="maintainableParentVersion" type="VersionType" use="prohibited"/>
				<xs:attribute name="containerID" type="IDType" use="optional">
					<xs:annotation>
						<xs:documentation>The containerID attribute references the hierarchy which defines the hierarchical code in the case that this reference is for a hierarchical code.</xs:documentation>
					</xs:annotation>
				</xs:attribute>
				<xs:attribute name="id" type="NestedIDType" use="required"/>
				<xs:attribute name="local" type="xs:boolean" use="optional" fixed="true"/>
				<xs:attribute name="class" type="CodeTypeCodelistType" use="optional"/>
				<xs:attribute name="package" type="PackageTypeCodelistType" use="optional" fixed="codelist"/>
			</xs:restriction>
		</xs:complexContent>
	</xs:complexType>
 * @author James
 */
public class AnyLocalCodeRef extends RefBase {
    public AnyLocalCodeRef(IDType containerId,NestedID id,CodeTypeCodelistType cl){
        super(null,id,null,null,null,containerId,true,cl,PackageTypeCodelistType.CODELIST);
    }
}

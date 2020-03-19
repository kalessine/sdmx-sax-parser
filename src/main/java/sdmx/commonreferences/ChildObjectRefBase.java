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

import sdmx.commonreferences.types.PackageTypeCodelistType;
import sdmx.commonreferences.types.ObjectTypeCodelistType;

/**
 *	<xs:complexType name="ChildObjectRefBaseType" abstract="true">
		<xs:annotation>
			<xs:documentation>ChildObjectRefBaseType is an abstract base type for referencing any child object defined directly within a maintainable object.</xs:documentation>
		</xs:annotation>
		<xs:complexContent>
			<xs:restriction base="RefBaseType">
				<xs:attribute name="agencyID" type="NestedNCNameIDType" use="required"/>
				<xs:attribute name="maintainableParentID" type="IDType" use="required"/>
				<xs:attribute name="maintainableParentVersion" type="VersionType" use="optional" default="1.0"/>
				<xs:attribute name="containerID" type="NestedIDType" use="prohibited"/>
				<xs:attribute name="id" type="NestedIDType" use="required"/>
				<xs:attribute name="version" type="VersionType" use="prohibited"/>
				<xs:attribute name="local" type="xs:boolean" use="optional" fixed="false"/>
				<xs:attribute name="class" type="ObjectTypeCodelistType" use="optional"/>
				<xs:attribute name="package" type="PackageTypeCodelistType" use="optional"/>
			</xs:restriction>
		</xs:complexContent>
	</xs:complexType>
 * @author James
 */
public class ChildObjectRefBase extends RefBase {
      public ChildObjectRefBase(NestedNCNameID agencyID,IDType mainParent,Version pvers,NestedID id,ObjectTypeCodelistType obs, PackageTypeCodelistType pack){ 
          super(agencyID,id,pvers,mainParent,null,null,false,obs,pack);
      }
}

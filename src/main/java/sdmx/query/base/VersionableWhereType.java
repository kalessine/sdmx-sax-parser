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

import sdmx.common.TimeRangeValueType;
import sdmx.commonreferences.VersionQuery;

/**
 *	<xs:complexType name="VersionableWhereType" abstract="true">
		<xs:annotation>
			<xs:documentation>VersionableQueryType is an abstract base type that serves as the basis for any query for a versionable object.</xs:documentation>
		</xs:annotation>
		<xs:complexContent>
			<xs:extension base="NameableWhereType">
				<xs:sequence>
					<xs:element name="Version" type="common:VersionQueryType" minOccurs="0">
						<xs:annotation>
							<xs:documentation>Version is used to match the version of the versioned object. The version can be specified as either an explicit version number, or a late bound query where the latest version of an object will be returned.</xs:documentation>
						</xs:annotation>
					</xs:element>
					<xs:element name="VersionTo" type="common:TimeRangeValueType" minOccurs="0">
						<xs:annotation>
							<xs:documentation>VersionTo is used to specify a range which the start date of the validity period of version should fall within to create a successful match.</xs:documentation>
						</xs:annotation>
					</xs:element>
					<xs:element name="VersionFrom" type="common:TimeRangeValueType" minOccurs="0">
						<xs:annotation>
							<xs:documentation>VersionFrom is used to specify a range which the end date of the validity period of version should fall within to create a successful match.</xs:documentation>
						</xs:annotation>
					</xs:element>
					<xs:element name="VersionActive" type="xs:boolean" minOccurs="0">
						<xs:annotation>
							<xs:documentation>VersionActive is used to request object with active or inactive versions, base on the version validity dates. A value of true indicates that only objects where the current date is within the validity period of the version will be matched.</xs:documentation>
						</xs:annotation>
					</xs:element>
				</xs:sequence>
			</xs:extension>
		</xs:complexContent>
	</xs:complexType>

 * @author James
 */

public class VersionableWhereType extends NameableWhereType {
    private VersionQuery version = null;
    private TimeRangeValueType versionTo = null;
    private TimeRangeValueType versionFrom = null;
    private Boolean versionActive = null;

    /**
     * @return the version
     */
    public VersionQuery getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(VersionQuery version) {
        this.version = version;
    }

    /**
     * @return the versionTo
     */
    public TimeRangeValueType getVersionTo() {
        return versionTo;
    }

    /**
     * @param versionTo the versionTo to set
     */
    public void setVersionTo(TimeRangeValueType versionTo) {
        this.versionTo = versionTo;
    }

    /**
     * @return the versionFrom
     */
    public TimeRangeValueType getVersionFrom() {
        return versionFrom;
    }

    /**
     * @param versionFrom the versionFrom to set
     */
    public void setVersionFrom(TimeRangeValueType versionFrom) {
        this.versionFrom = versionFrom;
    }

    /**
     * @return the versionActive
     */
    public Boolean getVersionActive() {
        return versionActive;
    }

    /**
     * @param versionActive the versionActive to set
     */
    public void setVersionActive(Boolean versionActive) {
        this.versionActive = versionActive;
    }
    
}

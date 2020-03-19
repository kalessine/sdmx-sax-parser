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
package sdmx.structure.base;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import sdmx.commonreferences.Version;
import sdmx.xml.DateTime;

/**
 *	<xs:complexType name="VersionableType" abstract="true">
		<xs:annotation>
			<xs:documentation>VersionableType is an abstract base type for all versionable objects.</xs:documentation>
		</xs:annotation>
		<xs:complexContent>
			<xs:extension base="NameableType">
				<xs:attribute name="version" type="common:VersionType" use="optional" default="1.0">
					<xs:annotation>
						<xs:documentation>This version attribute holds a version number in the format of #[.#]+ (see common:VersionType definition for details). If not supplied, the version number is defaulted to 1.0.</xs:documentation>
					</xs:annotation>
				</xs:attribute>
				<xs:attribute name="validFrom" type="xs:dateTime" use="optional">
					<xs:annotation>
						<xs:documentation>The validFrom attribute provides the inclusive start date for providing supplemental validity information about the version.</xs:documentation>
					</xs:annotation>
				</xs:attribute>
				<xs:attribute name="validTo" type="xs:dateTime" use="optional">
					<xs:annotation>
						<xs:documentation>The validTo attribute provides the inclusive end date for providing supplemental validity information about the version.</xs:documentation>
					</xs:annotation>
				</xs:attribute>
			</xs:extension>
		</xs:complexContent>
	</xs:complexType>

 * @author James
 */

public class VersionableType extends NameableType {
    public static void main(String args[]) {}
    private Version version = Version.ONE;
    private DateTime validFrom;
    private DateTime validTo;

    /**
     * @return the version
     */
    public Version getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(Version version) {
        this.version = version;
    }

    /**
     * @return the validFrom
     */
    public DateTime getValidFrom() {
        return validFrom;
    }

    /**
     * @param validFrom the validFrom to set
     */
    public void setValidFrom(DateTime validFrom) {
        this.validFrom = validFrom;
    }

    /**
     * @return the validTo
     */
    public DateTime getValidTo() {
        return validTo;
    }

    /**
     * @param validTo the validTo to set
     */
    public void setValidTo(DateTime validTo) {
        this.validTo = validTo;
    }
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.writeObject(version);
        oos.writeObject(validFrom);
        oos.writeObject(validTo);
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        this.version = (Version)ois.readObject();
        this.validFrom = (DateTime)ois.readObject();
        this.validTo = (DateTime)ois.readObject();
    }
}

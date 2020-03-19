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
package sdmx.message;

import java.util.List;
import sdmx.common.Name;
import sdmx.common.TextType;
import sdmx.xml.anyURI;

/**
 *<xs:complexType name="ContactType">
		<xs:annotation>
			<xs:documentation>ContactType provides defines the contact information about a party.</xs:documentation>
		</xs:annotation>
		<xs:sequence>
			<xs:element ref="common:Name" minOccurs="0" maxOccurs="unbounded">
				<xs:annotation>
					<xs:documentation>Name contains a human-readable name for the contact.</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element name="Department" type="common:TextType" minOccurs="0" maxOccurs="unbounded">
				<xs:annotation>
					<xs:documentation>Department is designation of the organisational structure by a linguistic expression, within which the contact person works.</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element name="Role" type="common:TextType" minOccurs="0" maxOccurs="unbounded">
				<xs:annotation>
					<xs:documentation>Role is the responsibility of the contact person with respect to the object for which this person is the contact.</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:choice minOccurs="0" maxOccurs="unbounded">
				<xs:element name="Telephone" type="xs:string">
					<xs:annotation>
						<xs:documentation>Telephone holds the telephone number for the contact person.</xs:documentation>
					</xs:annotation>
				</xs:element>
				<xs:element name="Fax" type="xs:string">
					<xs:annotation>
						<xs:documentation>Fax holds the fax number for the contact person.</xs:documentation>
					</xs:annotation>
				</xs:element>
				<xs:element name="X400" type="xs:string">
					<xs:annotation>
						<xs:documentation>X400 holds the X.400 address for the contact person.</xs:documentation>
					</xs:annotation>
				</xs:element>
				<xs:element name="URI" type="xs:anyURI">
					<xs:annotation>
						<xs:documentation>URI holds an information URL for the contact person.</xs:documentation>
					</xs:annotation>
				</xs:element>
				<xs:element name="Email" type="xs:string">
					<xs:annotation>
						<xs:documentation>Email holds the email address for the contact person.</xs:documentation>
					</xs:annotation>
				</xs:element>
			</xs:choice>
		</xs:sequence>
	</xs:complexType>
 * @author James
 */

public class ContactType {
			private List<Name> names;
			private List<TextType> departments;
			private List<TextType> roles;

                        private List<String> telephones;
                        private List<String> faxes;
                        private List<String> x400s;
			private List<anyURI> uris;
                        private List<String> emails;

    /**
     * @return the names
     */
    public List<Name> getNames() {
        return names;
    }

    /**
     * @param names the names to set
     */
    public void setNames(List<Name> names) {
        this.names = names;
    }

    /**
     * @return the departments
     */
    public List<TextType> getDepartments() {
        return departments;
    }

    /**
     * @param departments the departments to set
     */
    public void setDepartments(List<TextType> departments) {
        this.departments = departments;
    }

    /**
     * @return the roles
     */
    public List<TextType> getRoles() {
        return roles;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(List<TextType> roles) {
        this.roles = roles;
    }

    /**
     * @return the telephones
     */
    public List<String> getTelephones() {
        return telephones;
    }

    /**
     * @param telephones the telephones to set
     */
    public void setTelephones(List<String> telephones) {
        this.telephones = telephones;
    }

    /**
     * @return the faxes
     */
    public List<String> getFaxes() {
        return faxes;
    }

    /**
     * @param faxes the faxes to set
     */
    public void setFaxes(List<String> faxes) {
        this.faxes = faxes;
    }

    /**
     * @return the x400s
     */
    public List<String> getX400s() {
        return x400s;
    }

    /**
     * @param x400s the x400s to set
     */
    public void setX400s(List<String> x400s) {
        this.x400s = x400s;
    }

    /**
     * @return the uris
     */
    public List<anyURI> getUris() {
        return uris;
    }

    /**
     * @param uris the uris to set
     */
    public void setUris(List<anyURI> uris) {
        this.uris = uris;
    }

    /**
     * @return the emails
     */
    public List<String> getEmails() {
        return emails;
    }

    /**
     * @param emails the emails to set
     */
    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

}

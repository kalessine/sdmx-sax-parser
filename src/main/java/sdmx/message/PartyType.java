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
import sdmx.commonreferences.IDType;
import sdmx.structure.base.NameableType;

/**
 *	<xs:complexType name="PartyType">
		<xs:annotation>
			<xs:documentation>PartyType defines the information which is sent about various parties such as senders and receivers of messages.</xs:documentation>
		</xs:annotation>
		<xs:sequence>
			<xs:element ref="common:Name" minOccurs="0" maxOccurs="unbounded">
				<xs:annotation>
					<xs:documentation>Name is a human-readable name of the party.</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element name="Contact" type="ContactType" minOccurs="0" maxOccurs="unbounded">
				<xs:annotation>
					<xs:documentation>Contact provides contact information for the party in regard to the transmission of the message.</xs:documentation>
				</xs:annotation>
			</xs:element>
		</xs:sequence>
		<xs:attribute name="id" type="common:IDType" use="required">
			<xs:annotation>
				<xs:documentation>The id attribute holds the identification of the party.</xs:documentation>
			</xs:annotation>
		</xs:attribute>
	</xs:complexType>

 * @author James
 */

public class PartyType extends NameableType {
    private List<Name> names;
    private List<ContactType> contacts;
    private IDType id;
    public PartyType(){
        super();
    
    }

    /**
     * @return the name
     */
    public List<Name> getNames() {
        return names;
    }

    /**
     * @param name the name to set
     */
    public void setNames(List<Name> name) {
        this.names = name;
    }

    /**
     * @return the contact
     */
    public List<ContactType> getContacts() {
        return contacts;
    }

    /**
     * @param contact the contact to set
     */
    public void setContacts(List<ContactType> contact) {
        this.contacts = contact;
    }

    /**
     * @return the id
     */
    public IDType getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(IDType id) {
        this.id = id;
    }
}

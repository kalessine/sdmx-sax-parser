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
package sdmx.common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *	<xs:attributeGroup name="ExternalReferenceAttributeGroup">
		<xs:attribute name="serviceURL" type="xs:anyURI" use="optional">
			<xs:annotation>
				<xs:documentation>The serviceURL attribute indicates the URL of an SDMX SOAP web service from which the details of the object can be retrieved. Note that this can be a registry or and SDMX structural metadata repository, as they both implement that same web service interface.</xs:documentation>
			</xs:annotation>
		</xs:attribute>
		<xs:attribute name="structureURL" type="xs:anyURI" use="optional">
			<xs:annotation>
				<xs:documentation>The structureURL attribute indicates the URL of a SDMX-ML structure message (in the same version as the source document) in which the externally referenced object is contained. Note that this may be a URL of an SDMX RESTful web service which will return the referenced object.</xs:documentation>
			</xs:annotation>
		</xs:attribute>
	</xs:attributeGroup>
 * @author James
 */
public class ExternalReferenceAttributeGroup implements Serializable {
    private String serviceURL = null;
    private String structureURL = null;


    public ExternalReferenceAttributeGroup() {}
    public ExternalReferenceAttributeGroup(String service,String structure) {
        this.structureURL=structure;
    }

    /**
     * @return the serviceURL
     */
    public String getServiceURL() {
        return serviceURL;
    }

    /**
     * @param serviceURL the serviceURL to set
     */
    public void setServiceURL(String serviceURL) {
        this.serviceURL = serviceURL;
    }

    /**
     * @return the structureURL
     */
    public String getStructureURL() {
        return structureURL;
    }

    /**
     * @param structureURL the structureURL to set
     */
    public void setStructureURL(String structureURL) {
        this.structureURL = structureURL;
    }
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.writeUTF(serviceURL);
        oos.writeUTF(structureURL);
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        this.serviceURL = ois.readUTF();
        this.structureURL = ois.readUTF();
    }
}

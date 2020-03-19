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
package sdmx.structure.codelist;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import sdmx.commonreferences.LocalCodeReference;
import sdmx.structure.base.ItemType;
/*
	<xs:element name="Code" type="CodeType" substitutionGroup="Item">
		<xs:annotation>
			<xs:documentation>Code describes a code in a codelist. In addition to the identification and description of the code, basic presentational information is also available. Presentational information not present may be added through the use of annotations.</xs:documentation>
		</xs:annotation>
	</xs:element>
	
	<xs:complexType name="CodeType">
		<xs:annotation>
			<xs:documentation>CodeType describes the structure of a code. A code is defined as a language independent set of letters, numbers or symbols that represent a concept whose meaning is described in a natural language. Presentational information not present may be added through the use of annotations.</xs:documentation>
		</xs:annotation>
		<xs:complexContent>
			<xs:restriction base="ItemType">
				<xs:sequence>
					<xs:element ref="common:Annotations" minOccurs="0"/>
					<xs:element ref="common:Name" maxOccurs="unbounded">
						<xs:annotation>
							<xs:documentation>Name provides for a human-readable name for the object. This may be provided in multiple, parallel language-equivalent forms. It may be used in place of a short description.</xs:documentation>
						</xs:annotation>
					</xs:element>
					<xs:element ref="common:Description" minOccurs="0" maxOccurs="unbounded">
						<xs:annotation>
							<xs:documentation>Description provides a plain text, human-readable description of the code. This may be provided in multiple, parallel language-equivalent forms.</xs:documentation>
						</xs:annotation>
					</xs:element>
					<xs:choice minOccurs="0">
						<xs:element name="Parent" type="common:LocalCodeReferenceType">
							<xs:annotation>
								<xs:documentation>Parent provides the ability to describe simple hierarchies within a single codelist, by referencing the id value of another code in the same codelist.</xs:documentation>
							</xs:annotation>
						</xs:element>
					</xs:choice>
				</xs:sequence>
			</xs:restriction>
		</xs:complexContent>
	</xs:complexType>

*/
/**
 *
 * @author James
 */

public class CodeType extends ItemType {

    LocalCodeReference parent = null;

    public CodeType getCode(int i) {
        return (CodeType) super.getItem(i);
    }

    public void setCode(int i, CodeType c) {
        super.setItem(i, c);
    }

    public void setCodes(List<CodeType> codes) {
        List<ItemType> items = new ArrayList<ItemType>(codes.size());
        items.addAll(codes);
        super.setItems(items);
    }

    public String toString() {
        if (getDescriptions() != null && getDescriptions().size() > 0) {
            return getDescriptions().get(0).getText();
        }
        if (getNames() != null && getNames().size() > 0) {
            return getNames().get(0).getText();
        }
        return super.getId().getString();
    }

    public String toHTMLString(int spaces) {
        StringBuffer sb = new StringBuffer();
        for (int k = 0; k < spaces; k++) {
            sb.append(" ");
        }
        sb.append("Code:" + super.getId().toString() + "<BR>");
        if (getAnnotations() != null) {
            for (int i = 0; i < super.getAnnotations().size(); i++) {
                for (int k = 0; k < spaces; k++) {
                    sb.append(" ");
                }
                sb.append("Annotation Title:" + super.getAnnotations().getAnnotation(i).getAnnotationTitle() + "<BR>");
                for (int k = 0; k < spaces; k++) {
                    sb.append(" ");
                }
                sb.append("Type:" + super.getAnnotations().getAnnotation(i).getAnnotationType() + "<BR>");
                for (int k = 0; k < spaces; k++) {
                    sb.append(" ");
                }
                sb.append("URL:" + super.getAnnotations().getAnnotation(i).getAnnotationUrl() + "<BR>");
                for (int k = 0; k < spaces; k++) {
                    sb.append(" ");
                }
                for (int j = 0; j < super.getAnnotations().getAnnotation(i).getAnnotationText().size(); j++) {
                    for (int k = 0; k < spaces; k++) {
                        sb.append(" ");
                    }
                    sb.append(super.getAnnotations().getAnnotation(i).getAnnotationText().get(j).getLang());
                    sb.append(":");
                    sb.append(super.getAnnotations().getAnnotation(i).getAnnotationText().get(j).getText() + "<BR>");
                }
            }
        }
        sb.append("Descriptions " + getDescriptions().size()+"<BR>");
        for (int j = 0; j < getDescriptions().size(); j++) {
            for (int k = 0; k < spaces; k++) {
                sb.append(" ");
            }
            sb.append(getDescriptions().get(j).getLang());
            sb.append(":");
            sb.append(getDescriptions().get(j).getText() + "<BR>");
        }

        for (int i = 0; i < super.size(); i++) {
            sb.append(getCode(i).toHTMLString(spaces + 3));
        }
        return sb.toString();
    }

    public void dump() {
        System.out.println("Code:"+this.getId()+":"+this.findName("en")+":"+this.findDescription("en"));
    }
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.writeObject(parent);
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        parent = (LocalCodeReference)ois.readObject();
    }
}

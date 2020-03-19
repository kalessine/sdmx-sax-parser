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
import java.util.ArrayList;
import java.util.List;

/**
 * <xs:complexType name="AnnotationType">
 * <xs:annotation>
 * <xs:documentation>AnnotationType provides for non-documentation notes and
 * annotations to be embedded in data and structure messages. It provides
 * optional fields for providing a title, a type description, a URI, and the
 * text of the annotation.</xs:documentation>
 * </xs:annotation>
 * <xs:sequence>
 * <xs:element name="AnnotationTitle" type="xs:string" minOccurs="0">
 * <xs:annotation>
 * <xs:documentation>AnnotationTitle provides a title for the
 * annotation.</xs:documentation>
 * </xs:annotation>
 * </xs:element>
 * <xs:element name="AnnotationType" type="xs:string" minOccurs="0">
 * <xs:annotation>
 * <xs:documentation>AnnotationType is used to distinguish between annotations
 * designed to support various uses. The types are not enumerated, as these can
 * be specified by the user or creator of the annotations. The definitions and
 * use of annotation types should be documented by their
 * creator.</xs:documentation>
 * </xs:annotation>
 * </xs:element>
 * <xs:element name="AnnotationURL" type="xs:anyURI" minOccurs="0">
 * <xs:annotation>
 * <xs:documentation>AnnotationURL is a URI - typically a URL - which points to
 * an external resource which may contain or supplement the annotation. If a
 * specific behavior is desired, an annotation type should be defined which
 * specifies the use of this field more exactly.</xs:documentation>
 * </xs:annotation>
 * </xs:element>
 * <xs:element name="AnnotationText" type="TextType" minOccurs="0" maxOccurs="unbounded">
 * <xs:annotation>
 * <xs:documentation>AnnotationText holds a language-specific string containing
 * the text of the annotation.</xs:documentation>
 * </xs:annotation>
 * </xs:element>
 * </xs:sequence>
 * <xs:attribute name="id" type="xs:string" use="optional">
 * <xs:annotation>
 * <xs:documentation>The id attribute provides a non-standard identification of
 * an annotation. It can be used to disambiguate annotations.</xs:documentation>
 * </xs:annotation>
 * </xs:attribute>
 * </xs:complexType>
 *
 * @author James
 */
public class AnnotationType implements Serializable {

    private String annotationTitle = null;
    private String annotationType = null;
    private String annotationUrl = null;
    private List<TextType> annotationText = new ArrayList<TextType>();
    private String id = null;

    public AnnotationType() {
    }

    public AnnotationType(String tit, String ty, String url, List<TextType> text, String id) {
        this.annotationTitle = tit;
        this.annotationType = ty;
        this.annotationUrl = url;
        this.annotationText = text;
        this.id = id;
    }

    public AnnotationType(String tit, String ty, String url, List<TextType> text) {
        this.annotationTitle = tit;
        this.annotationType = ty;
        this.annotationUrl = url;
        this.annotationText = text;
    }

    /**
     * @return the annotationTitle
     */
    public String getAnnotationTitle() {
        return annotationTitle;
    }

    public void addAnnotationText(TextType tt) {
        this.annotationText.add(tt);
    }
    public TextType removeAnnotationText(TextType tt) {
        this.annotationText.remove(tt);
        return tt;
    }
    
    /**
     * @param annotationTitle the annotationTitle to set
     */
    public void setAnnotationTitle(String annotationTitle) {
        this.annotationTitle = annotationTitle;
    }

    /**
     * @return the annotationType
     */
    public String getAnnotationType() {
        return annotationType;
    }

    /**
     * @param annotationType the annotationType to set
     */
    public void setAnnotationType(String annotationType) {
        this.annotationType = annotationType;
    }

    /**
     * @return the annotationUrl
     */
    public String getAnnotationUrl() {
        return annotationUrl;
    }

    /**
     * @param annotationUrl the annotationUrl to set
     */
    public void setAnnotationUrl(String annotationUrl) {
        this.annotationUrl = annotationUrl;
    }

    /**
     * @return the annotationText
     */
    public List<TextType> getAnnotationText() {
        return annotationText;
    }

    /**
     * @param annotationText the annotationText to set
     */
    public void setAnnotationText(List<TextType> annotationText) {
        this.annotationText = annotationText;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        if (annotationTitle == null) {
            oos.writeUTF("");
        } else {
            oos.writeUTF(annotationTitle);
        }
        if (annotationType == null) {
            oos.writeUTF("");
        } else {
            oos.writeUTF(annotationType);
        }
        if (annotationUrl == null) {
            oos.writeUTF("");
        } else {
            oos.writeUTF(annotationUrl);
        }
        if (id == null) {
            oos.writeUTF("");
        } else {
            oos.writeUTF(id);
        }
        oos.writeObject(this.annotationText);
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        annotationTitle = ois.readUTF();
        annotationType = ois.readUTF();
        annotationUrl = ois.readUTF();
        id = ois.readUTF();
        annotationText = (List<TextType>) ois.readObject();
    }
}

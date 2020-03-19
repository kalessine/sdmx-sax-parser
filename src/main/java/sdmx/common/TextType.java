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
import java.util.List;

/**
 * <xs:complexType name="TextType">
 * <xs:annotation>
 * <xs:documentation>TextType provides for a set of language-specific alternates
 * to be provided for any human-readable constructs in the
 * instance.</xs:documentation>
 * </xs:annotation>
 * <xs:simpleContent>
 * <xs:extension base="xs:string">
 * <xs:attribute ref="xml:lang" default="en">
 * <xs:annotation>
 * <xs:documentation>The xml:lang attribute specifies a language code for the
 * text. If not supplied, the default language is assumed to be
 * English.</xs:documentation>
 * </xs:annotation>
 * </xs:attribute>
 * </xs:extension>
 * </xs:simpleContent>
 * </xs:complexType>
 *
 * @author James
 */
public class TextType implements Serializable {

    String lang = null;
    String text = null;

    public TextType(String lang1, String text1) {
        this.lang = lang1;
        this.text = text1;

    }

    public String getLang() {
        return lang;
    }

    public String getText() {
        return text;
    }

    public String toString() {
        return text;
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        if (lang == null) {
            oos.writeUTF("");
        } else {
            oos.writeUTF(lang);
        }
        if (text == null) {
            oos.writeUTF("");
        } else {
            oos.writeUTF(text);
        }
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        lang = ois.readUTF();
        text = ois.readUTF();
    }
}

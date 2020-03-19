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

import java.util.ArrayList;
import java.util.List;

/**
<xs:complexType name="XHTMLType" mixed="true">
<xs:annotation>
<xs:documentation>XHTMLType allows for mixed content of text and XHTML tags. When using this type, one will have to provide a reference to the XHTML schema, since the processing of the tags within this type is strict, meaning that they are validated against the XHTML schema provided.</xs:documentation>
</xs:annotation>
<xs:sequence minOccurs="0" maxOccurs="unbounded">
<xs:any namespace="http://www.w3.org/1999/xhtml" processContents="strict"/>
</xs:sequence>
<xs:attribute ref="xml:lang" default="en"/>
</xs:complexType>
 * @author James
 */
public class XHTMLType {

    List<String> htmlBlocks = new ArrayList<String>();
    String lang = "en";

    public XHTMLType(List<String> htmlBlocks) {
        this.htmlBlocks = htmlBlocks;
        this.lang = "en";
    }

    public XHTMLType(List<String> htmlBlocks, String xmlLang) {
        this.htmlBlocks = htmlBlocks;
        this.lang = xmlLang;
    }

    public List<String> getHTMLBlocks() {
        return htmlBlocks;
    }

    public String getHTMLBlock(int i) {
        return htmlBlocks.get(i);
    }

    public int getSize() {
        return htmlBlocks.size();
    }
}

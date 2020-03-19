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

/**
 *	<xs:complexType name="AnnotationWhereType">
		<xs:annotation>
			<xs:documentation>AnnotationWhereType defines the structure for querying the details of an annotation.</xs:documentation>
		</xs:annotation>
		<xs:sequence>
			<xs:element name="Type" type="QueryStringType" minOccurs="0">
				<xs:annotation>
					<xs:documentation>Type is a parameter for matching the type field of an annotation.</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element name="Title" type="QueryStringType" minOccurs="0">
				<xs:annotation>
					<xs:documentation>Title is a parameter for matching the title field of an annotation.</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element name="Text" type="QueryTextType" minOccurs="0">
				<xs:annotation>
					<xs:documentation>Text is a parameter for matching the text field of an annotation.</xs:documentation>
				</xs:annotation>
			</xs:element>
		</xs:sequence>
	</xs:complexType>

 * @author James
 */

public class AnnotationWhereType {
    private QueryStringType type = null;
    private QueryStringType title = null;
    private QueryStringType text = null;

    /**
     * @return the type
     */
    public QueryStringType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(QueryStringType type) {
        this.type = type;
    }

    /**
     * @return the title
     */
    public QueryStringType getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(QueryStringType title) {
        this.title = title;
    }

    /**
     * @return the text
     */
    public QueryStringType getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(QueryStringType text) {
        this.text = text;
    }
    
    
}

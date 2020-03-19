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
 *	<xs:complexType name="StatusMessageType">
		<xs:annotation>
			<xs:documentation>StatusMessageType describes the structure of an error or warning message. A message contains the text of the message, as well as an optional language indicator and an optional code.</xs:documentation>
		</xs:annotation>
		<xs:sequence>
			<xs:element name="Text" type="TextType" maxOccurs="unbounded">
				<xs:annotation>
					<xs:documentation>Text contains the text of the message, in parallel language values.</xs:documentation>
				</xs:annotation>
			</xs:element>
		</xs:sequence>
		<xs:attribute name="code" type="xs:string" use="optional">
			<xs:annotation>
				<xs:documentation>The code attribute holds an optional code identifying the underlying error that generated the message. This should be used if parallel language descriptions of the error are supplied, to distinguish which of the multiple error messages are for the same underlying error.</xs:documentation>
			</xs:annotation>
		</xs:attribute>
	</xs:complexType>
 * @author James
 */
public class StatusMessageType {
    private List<TextType> texts = new ArrayList<TextType>();
    private String code = null;


    public StatusMessageType(List<TextType> text,String code) {
        this.texts=texts;
        this.code=code;
    }
    public StatusMessageType(){}

    /**
     * @return the texts
     */
    public List<TextType> getTexts() {
        return texts;
    }

    public TextType getText(int i){ return texts.get(i);}
    public int getTextSize()  { return texts.size(); }

    /**
     * @param texts the texts to set
     */
    public void setTexts(List<TextType> texts) {
        this.texts = texts;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }
    
    

}

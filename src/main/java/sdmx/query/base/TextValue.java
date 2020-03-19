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

import sdmx.common.TextOperatorType;

/**
 *	<xs:element name="TextValue" type="QueryTextType">
		<xs:annotation>
			<xs:documentation>TextValue is used to query for the value of a concept or component based on textual parameters. The text value can be language specific (where  parallel multi-lingual values are available) and is qualified with an operator indicating how the supplied text should be matched against the sought components. If only a simple equality check is necessary, regardless of language, the Value element can be used.</xs:documentation>
		</xs:annotation>
	</xs:element>
 * @author James
 */

public class TextValue extends QueryTextType {
     public TextValue(String lang,String val, TextOperatorType operator) {
         super(lang,val,operator);
     }
}

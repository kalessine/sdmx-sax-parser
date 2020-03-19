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
 *	<xs:complexType name="AnnotableType" abstract="true">
		<xs:annotation>
				<xs:documentation>AnnotableType is an abstract base type used for all annotable artefacts. Any type that provides for annotations should extend this type.</xs:documentation>
		</xs:annotation>
		<xs:sequence>
			<xs:element ref="Annotations" minOccurs="0"/>
		</xs:sequence>
	</xs:complexType>
 * @author James
 */
public class AnnotableType implements Serializable {
    Annotations annotations = null;
    public AnnotableType(Annotations annots) {
        this.annotations=annots; 
    }
    public AnnotableType(){
    }
    public Annotations getAnnotations() { return annotations; }
    public void setAnnotations(Annotations annots) {
        this.annotations=annots;
    }
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.writeObject(annotations);
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        this.annotations = (Annotations)ois.readObject();
    }
}

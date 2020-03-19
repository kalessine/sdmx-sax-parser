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
import java.util.ArrayList;
import java.util.List;
import sdmx.commonreferences.LocalCodeReference;

/**
 *	<xs:complexType name="AnnotationsType">
		<xs:annotation>
			<xs:documentation>AnnotationsType provides for a list of annotations to be attached to data and structure messages.</xs:documentation>
		</xs:annotation>
		<xs:sequence>
			<xs:element name="Annotation" type="AnnotationType" maxOccurs="unbounded"/>
		</xs:sequence>
	</xs:complexType>
 * @author James
 */
public abstract class AnnotationsType {
    
    public AnnotationsType(List<AnnotationType> annots) {
        this.annots=annots;
    }
    public AnnotationsType(){
        this.annots = new ArrayList<AnnotationType>();
    }
    List<AnnotationType> annots = new ArrayList<AnnotationType>();
    public AnnotationType getAnnotation(int i){
        return annots.get(i);
    }
    public void addAnnotation(AnnotationType at) {
        annots.add(at);
    }
    public AnnotationType removeAnnotation(AnnotationType at) {
        annots.remove(at);
        return at;
    }
    public int size() { return annots.size(); }
    public List<AnnotationType> getAnnotations() { return this.annots; }
    public void setAnnotations(List<AnnotationType> list) {this.annots=list;}
    
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.writeObject(annots);
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        annots = (List<AnnotationType>)ois.readObject();
    }
}


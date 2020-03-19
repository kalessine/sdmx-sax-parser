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
package sdmx.commonreferences.types;

import java.util.ArrayList;
import java.util.List;
import sdmx.exception.TypeValueNotFoundException;

/**
 *	<xs:simpleType name="ConstraintTypeCodelistType">
		<xs:annotation>
			<xs:documentation>ConstraintTypeCodelistType provides an enumeration of all constraint objects.</xs:documentation>
		</xs:annotation>
		<xs:restriction base="MaintainableTypeCodelistType">
			<xs:enumeration value="AttachmentConstraint"/>
			<xs:enumeration value="ContentConstraint"/>
		</xs:restriction>
	</xs:simpleType>

 * @author James
 */

public class ConstraintTypeCodelistType extends MaintainableTypeCodelistType {
    public static final List<ConstraintTypeCodelistType> ENUM = new ArrayList<ConstraintTypeCodelistType>();
    public static final List<String> STRING_ENUM = new ArrayList<String>();

    public static final String TARGET_ATTACHMENT = addString("AttachmentConstraint");
    public static final String TARGET_CONTENT = addString("ContentConstraint");
    public static final ConstraintTypeCodelistType ATTACHMENT = add(TARGET_ATTACHMENT);
    public static final ConstraintTypeCodelistType CONTENT = add(TARGET_CONTENT);
            
// Utility
    private static ConstraintTypeCodelistType add(String s){
        ConstraintTypeCodelistType b = new ConstraintTypeCodelistType(s);
        ENUM.add(b);
        return b;
    }
    private static String addString(String s){
        STRING_ENUM.add(s);
        return s;
    }
    /**
     *
     * @param s
     * @return
     */
    
    public static ConstraintTypeCodelistType fromString(String s) {
        for(int i=0;i<ENUM.size();i++) {
            if( ENUM.get(i).toString().equals(s))return ENUM.get(i);
        }
        return null;
    }
    
    public static ConstraintTypeCodelistType fromStringWithException(String s) throws TypeValueNotFoundException {
        for(int i=0;i<ENUM.size();i++) {
            if( ENUM.get(i).toString().equals(s))return ENUM.get(i);
        }
        throw new TypeValueNotFoundException("Value:"+s+" not found in ConstraintTypeCodelistType enumeration!");
    }
// Instance
    public ConstraintTypeCodelistType(String s) {
        super(s);
        if( !STRING_ENUM.contains(s))throw new IllegalArgumentException(s+" is not a valid ObjectTypeCodelistType");
    }
   
}

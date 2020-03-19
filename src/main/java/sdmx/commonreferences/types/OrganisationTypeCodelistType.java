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
 *	<xs:simpleType name="OrganisationTypeCodelistType">
		<xs:annotation>
			<xs:documentation>OrganisationTypeCodelistType provides an enumeration of all organisation objects.</xs:documentation>
		</xs:annotation>
		<xs:restriction base="ItemTypeCodelistType">
			<xs:enumeration value="Agency"/>
			<xs:enumeration value="DataConsumer"/>
			<xs:enumeration value="DataProvider"/>
			<xs:enumeration value="OrganisationUnit"/>
		</xs:restriction>
	</xs:simpleType>
 * @author James
 */

public class OrganisationTypeCodelistType extends ItemTypeCodelistType {
    public static final List<OrganisationTypeCodelistType> ENUM = new ArrayList<OrganisationTypeCodelistType>();
    public static final List<String> STRING_ENUM = new ArrayList<String>();
    
    public static final String TARGET_AGENCY = addString("Agency");
    public static final String TARGET_DATACONSUMER = addString("DataConsumer");
    public static final String TARGET_DATAPROVIDER = addString("DataProvider");
    public static final String TARGET_ORGANISATIONUNIT = addString("OrganisationUnit");

    public static final OrganisationTypeCodelistType AGENCY = add(TARGET_AGENCY);
    public static final OrganisationTypeCodelistType DATACONSUMER = add(TARGET_DATACONSUMER);
    public static final OrganisationTypeCodelistType DATAPROVIDER = add(TARGET_DATAPROVIDER);
    public static final OrganisationTypeCodelistType ORGANISATIONUNI = add(TARGET_ORGANISATIONUNIT);

// Utility
    private static OrganisationTypeCodelistType add(String s){
        OrganisationTypeCodelistType b = new OrganisationTypeCodelistType(s);
        ENUM.add(b);
        return b;
    }
    private static String addString(String s){
        STRING_ENUM.add(s);
        return s;
    }
    
    public static OrganisationTypeCodelistType fromString(String s) {
        for(int i=0;i<ENUM.size();i++) {
            if( ENUM.get(i).target.equals(s))return ENUM.get(i);
        }
        return null;
    }
    public static OrganisationTypeCodelistType fromStringWithException(String s) throws TypeValueNotFoundException {
        for(int i=0;i<ENUM.size();i++) {
            if( ENUM.get(i).target.equals(s))return ENUM.get(i);
        }
        throw new TypeValueNotFoundException("Value:"+s+" not found in OrganisationTypeCodelistType enumeration!");
    }
// Instance
    private String target = null;
    public OrganisationTypeCodelistType(String s) {
        super(s);
        if( !STRING_ENUM.contains(s))throw new IllegalArgumentException(s+" is not a valid OrganisationTypeCodelistType");
        this.target=s;
    }
    
    public String toString() { return target; }
}

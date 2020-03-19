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
import sdmx.commonreferences.types.ComponentTypeCodelistType;

/**
 *	<xs:simpleType name="ComponentTypeCodelistType">
		<xs:annotation>
			<xs:documentation>ComponentTypeCodelistType provides an enumeration of all component objects.</xs:documentation>
		</xs:annotation>
		<xs:restriction base="ObjectTypeCodelistType">
			<xs:enumeration value="Attribute"/>
			<xs:enumeration value="ConstraintTarget"/>
			<xs:enumeration value="DataSetTarget"/>
			<xs:enumeration value="Dimension"/>
			<xs:enumeration value="IdentifiableObjectTarget"/>
			<xs:enumeration value="DimensionDescriptorValuesTarget"/>
			<xs:enumeration value="MeasureDimension"/>
			<xs:enumeration value="MetadataAttribute"/>
			<xs:enumeration value="PrimaryMeasure"/>
			<xs:enumeration value="ReportingYearStartDay"/>
			<xs:enumeration value="ReportPeriodTarget"/>
			<xs:enumeration value="TimeDimension"/>
		</xs:restriction>
	</xs:simpleType>
 * @author James
 */
public class ComponentTypeCodelistType extends ObjectTypeCodelistType {

    public static final List<ComponentTypeCodelistType> ENUM = new ArrayList<ComponentTypeCodelistType>();
    public static final List<String> STRING_ENUM = new ArrayList<String>();
    
    public static final String TARGET_ATTRIBUTE = addString("Attribute");
    public static final String TARGET_CONSTRAINTARGET = addString("ConstraintTarget");
    public static final String TARGET_DATASETTARGET = addString("DataSetTarget");
    public static final String TARGET_DIMENSION = addString("Dimension");
    public static final String TARGET_IDENTIFIABLEOBJECTTARGET = addString("IdentifiableObjectTarget");
    public static final String TARGET_DIMENSIONDESCRIPTORVALUESTARGET = addString("DimensionDescriptorValuesTarget");
    public static final String TARGET_MEASUREDIMENSION = addString("MeasureDimension");
    public static final String TARGET_METADATAATTRIBUTE = addString("MetadataAttribute");
    public static final String TARGET_PRIMARYMEASURE = addString("PrimaryMeasure");
    public static final String TARGET_REPORTINGYEARSTARTDAY = addString("ReportingYearStartDay");
    public static final String TARGET_REPORTINGPERIODTARGET = addString("ReportPeriodTarget");
    public static final String TARGET_TIMEDIMENSION = addString("TimeDimension");

    public static final ComponentTypeCodelistType ATTRIBUTE = add(TARGET_ATTRIBUTE);
    public static final ComponentTypeCodelistType CONSTRAINTARGET = add(TARGET_CONSTRAINTARGET);
    public static final ComponentTypeCodelistType DATASETTARGET = add(TARGET_DATASETTARGET);
    public static final ComponentTypeCodelistType DIMENSION = add(TARGET_DIMENSION);
    public static final ComponentTypeCodelistType IDENTIFIABLEOBJECTTARGET = add(TARGET_IDENTIFIABLEOBJECTTARGET);
    public static final ComponentTypeCodelistType DIMENSIONDESCRIPTORVALUESTARGET = add(TARGET_DIMENSIONDESCRIPTORVALUESTARGET);
    public static final ComponentTypeCodelistType MEASUREDIMENSION = add(TARGET_MEASUREDIMENSION);
    public static final ComponentTypeCodelistType METADATAATTRIBUTE = add(TARGET_METADATAATTRIBUTE);
    public static final ComponentTypeCodelistType PRIMARYMEASURE = add(TARGET_PRIMARYMEASURE);
    public static final ComponentTypeCodelistType REPORTINGYEARSTARTDAY = add(TARGET_REPORTINGYEARSTARTDAY);
    public static final ComponentTypeCodelistType REPORTINGPERIODTARGET = add(TARGET_REPORTINGPERIODTARGET);
    public static final ComponentTypeCodelistType TIMEDIMENSION = add(TARGET_TIMEDIMENSION);
// Utility
    private static ComponentTypeCodelistType add(String s){
        ComponentTypeCodelistType b = new ComponentTypeCodelistType(s);
        ENUM.add(b);
        return b;
    }
    private static String addString(String s){
        STRING_ENUM.add(s);
        return s;
    }
    
    public static ComponentTypeCodelistType fromString(String s) {
        for(int i=0;i<ENUM.size();i++) {
            if( ENUM.get(i).toString().equals(s))return ENUM.get(i);
        }
        return null;
    }
    public static ComponentTypeCodelistType fromStringWithException(String s) throws TypeValueNotFoundException {
        for(int i=0;i<ENUM.size();i++) {
            if( ENUM.get(i).toString().equals(s))return ENUM.get(i);
        }
        throw new TypeValueNotFoundException("Value:"+s+" not found in ComponentTypeCodelistType enumeration!");
    }
    public ComponentTypeCodelistType(String s) {
        super(s);
        if( !STRING_ENUM.contains(s))throw new IllegalArgumentException(s+" is not a valid ComponentTypeCodelistType");
    }
}

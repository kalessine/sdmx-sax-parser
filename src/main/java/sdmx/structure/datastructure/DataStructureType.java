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
package sdmx.structure.datastructure;

import javax.swing.UIManager;
import sdmx.commonreferences.DataStructureRef;
import sdmx.commonreferences.DataStructureReference;
import sdmx.commonreferences.IDType;
import sdmx.structure.base.Component;
import sdmx.structure.base.ComponentUtil;
import sdmx.structure.base.StructureType;
import sdmx.structure.dataflow.DataflowType;

/**
 * <xs:complexType name="DataStructureType">
 * <xs:annotation>
 * <xs:documentation>DataStructureType describes the structure of a data
 * structure definition. A data structure definition is defined as a collection
 * of metadata concepts, their structure and usage when used to collect or
 * disseminate data.</xs:documentation>
 * </xs:annotation>
 * <xs:complexContent>
 * <xs:restriction base="StructureType">
 * <xs:sequence>
 * <xs:element ref="common:Annotations" minOccurs="0"/>
 * <xs:element ref="common:Name" maxOccurs="unbounded"/>
 * <xs:element ref="common:Description" minOccurs="0" maxOccurs="unbounded"/>
 * <xs:sequence minOccurs="0">
 * <xs:element ref="DataStructureComponents"/>
 * </xs:sequence>
 * </xs:sequence>
 * </xs:restriction>
 * </xs:complexContent>
 * </xs:complexType>
 *
 * @author James
 */
public class DataStructureType extends StructureType {

    private DataStructureComponents components = new DataStructureComponents();

    /**
     * @return the components
     */
    public DataStructureComponents getDataStructureComponents() {
        return components;
    }

    /**
     * @param components the components to set
     */
    public void setDataStructureComponents(DataStructureComponents components) {
        this.components = components;
    }

    public void dump() {
        System.out.println("*** Data Structure ***");
        System.out.println("Dimensions");
        for (DimensionType dim : components.getDimensionList().getDimensions()) {
            System.out.print(dim.getConceptIdentity().getId());
            if (ComponentUtil.getLocalRepresentation(dim).getEnumeration() != null) {
                System.out.print(" Codelist:" + ComponentUtil.getLocalRepresentation(dim).getEnumeration().getAgencyId() + ":" + ComponentUtil.getLocalRepresentation(dim).getEnumeration().getMaintainableParentId() + ":" + ComponentUtil.getLocalRepresentation(dim).getEnumeration().getMaintainedParentVersion());
            }
            if (dim.getConceptIdentity() != null) {
                System.out.print(" Concept Identity:" + dim.getConceptIdentity().getAgencyId() + ":" + dim.getConceptIdentity().getMaintainableParentId() + ":" + dim.getConceptIdentity().getMaintainedParentVersion());
            } else {
                System.out.println("***** NULL CONCEPT IDENTITY ******");
            }
            if (ComponentUtil.getLocalRepresentation(dim) != null && ComponentUtil.getLocalRepresentation(dim).getTextFormat() != null) {
                System.out.print(" Text Format:" + ComponentUtil.getLocalRepresentation(dim).getTextFormat().getTextType());
            }
            System.out.println();
        }
        System.out.println("Attributes");
        for (AttributeType dim : components.getAttributeList().getAttributes()) {
            //System.out.print(dim.getConceptIdentity().getId());
            if (ComponentUtil.getLocalRepresentation(dim) != null && ComponentUtil.getLocalRepresentation(dim).getEnumeration() != null) {
                System.out.print(" Codelist:" + ComponentUtil.getLocalRepresentation(dim).getEnumeration().getAgencyId() + ":" + ComponentUtil.getLocalRepresentation(dim).getEnumeration().getMaintainableParentId() + ":" + ComponentUtil.getLocalRepresentation(dim).getEnumeration().getMaintainedParentVersion());
            }
            if (dim.getConceptIdentity() != null) {
                System.out.print(" Concept Identity:" + dim.getConceptIdentity().getAgencyId() + ":" + dim.getConceptIdentity().getMaintainableParentId() + ":" + dim.getConceptIdentity().getMaintainedParentVersion());
            } else {
                System.out.println("***** NULL CONCEPT IDENTITY ******");
            }
            if (ComponentUtil.getLocalRepresentation(dim) != null && ComponentUtil.getLocalRepresentation(dim).getTextFormat() != null) {
                System.out.print(" Text Format:" + ComponentUtil.getLocalRepresentation(dim).getTextFormat().getTextType());
            }
            System.out.println();
        }
        System.out.println("Measures");

        {
            MeasureDimensionType dim = components.getDimensionList().getMeasureDimension();
            //System.out.print(dim.getConceptIdentity().getId());
            if (ComponentUtil.getLocalRepresentation(dim) != null && ComponentUtil.getLocalRepresentation(dim).getEnumeration() != null) {
                System.out.print(" Codelist:" + ComponentUtil.getLocalRepresentation(dim).getEnumeration().getAgencyId() + ":" + ComponentUtil.getLocalRepresentation(dim).getEnumeration().getMaintainableParentId() + ":" + ComponentUtil.getLocalRepresentation(dim).getEnumeration().getMaintainedParentVersion());
            }
            if (dim!=null&&dim.getConceptIdentity() != null) {
                System.out.print(" Concept Identity:" + dim.getConceptIdentity().getAgencyId() + ":" + dim.getConceptIdentity().getMaintainableParentId() + ":" + dim.getConceptIdentity().getMaintainedParentVersion());
            } else {
                System.out.println("***** NULL CONCEPT IDENTITY ******");
            }
            if (dim!=null&&ComponentUtil.getLocalRepresentation(dim) != null && ComponentUtil.getLocalRepresentation(dim).getTextFormat() != null) {
                System.out.print(" Text Format:" + ComponentUtil.getLocalRepresentation(dim).getTextFormat().getTextType());
            }
            System.out.println();
        }
        System.out.println("Time");
        TimeDimensionType dim = components.getDimensionList().getTimeDimension();
        if (dim != null) {
            System.out.print(dim.getConceptIdentity().getId());
            if (ComponentUtil.getLocalRepresentation(dim) != null && ComponentUtil.getLocalRepresentation(dim).getEnumeration() != null) {
                System.out.print(" Codelist:" + ComponentUtil.getLocalRepresentation(dim).getEnumeration().getAgencyId() + ":" + ComponentUtil.getLocalRepresentation(dim).getEnumeration().getMaintainableParentId() + ":" + ComponentUtil.getLocalRepresentation(dim).getEnumeration().getMaintainedParentVersion());
            }
            if (dim.getConceptIdentity() != null) {
                System.out.print(" Concept Identity:" + dim.getConceptIdentity().getAgencyId() + ":" + dim.getConceptIdentity().getMaintainableParentId() + ":" + dim.getConceptIdentity().getMaintainedParentVersion());
            } else {
                System.out.println("***** NULL CONCEPT IDENTITY ******");
            }
            if (ComponentUtil.getLocalRepresentation(dim) != null && ComponentUtil.getLocalRepresentation(dim).getTextFormat() != null) {
                System.out.print(" Text Format:" + ComponentUtil.getLocalRepresentation(dim).getTextFormat().getTextType());
            }
            System.out.println();
        }
        PrimaryMeasure dim2 = components.getMeasureList().getPrimaryMeasure();
        if (dim2 != null) {
            System.out.print(dim2.getConceptIdentity().getId());
            if (ComponentUtil.getLocalRepresentation(dim2) != null && ComponentUtil.getLocalRepresentation(dim).getEnumeration() != null) {
                System.out.print(" Codelist:" + ComponentUtil.getLocalRepresentation(dim2).getEnumeration().getAgencyId() + ":" + ComponentUtil.getLocalRepresentation(dim2).getEnumeration().getMaintainableParentId() + ":" + ComponentUtil.getLocalRepresentation(dim2).getEnumeration().getMaintainedParentVersion());
            }
            if (dim2.getConceptIdentity() != null) {
                System.out.print(" Concept Identity:" + dim2.getConceptIdentity().getAgencyId() + ":" + dim2.getConceptIdentity().getMaintainableParentId() + ":" + dim2.getConceptIdentity().getMaintainedParentVersion());
            } else {
                System.out.println("***** NULL CONCEPT IDENTITY ******");
            }
            if (ComponentUtil.getLocalRepresentation(dim2) != null && ComponentUtil.getLocalRepresentation(dim2).getTextFormat() != null) {
                System.out.print(" Text Format:" + ComponentUtil.getLocalRepresentation(dim2).getTextFormat().getTextType());
            }
            System.out.println();
        }
    }

    public Component findComponent(String col) {
        return findComponent(new IDType(col));
    }

    public Component findComponent(IDType col) {
        for (DimensionType dim : components.getDimensionList().getDimensions()) {
            if (dim.identifiesMe(col)) {
                return dim;
            }
        }
        for (AttributeType dim : components.getAttributeList().getAttributes()) {
            if (dim.identifiesMe(col)) {
                return dim;
            }
        }
        //System.out.println("Measure3="+components.getMeasureList().getMeasures().size());
        if (components.getDimensionList().getMeasureDimension() != null && components.getDimensionList().getMeasureDimension().identifiesMe(col)) {
            return components.getDimensionList().getMeasureDimension();
        }
        TimeDimensionType dim = components.getDimensionList().getTimeDimension();
        if (dim.identifiesMe(col)) {
            return dim;
        }
        PrimaryMeasure dim2 = components.getMeasureList().getPrimaryMeasure();
        if (dim2.identifiesMe(col)) {
            return dim2;
        }
        return null;
    }

    public DataStructureReference asReference() {
        DataStructureRef ref = new DataStructureRef(getAgencyID(), getId(), getVersion());
        DataStructureReference reference = new DataStructureReference(ref, getUri());
        return reference;
    }

    public DataflowType asDataflow() {
        DataflowType dataFlow = new DataflowType();
        dataFlow.setNames(getNames());
        dataFlow.setDescriptions(getDescriptions());
        dataFlow.setStructure(asReference());
        dataFlow.setAnnotations(getAnnotations());
        dataFlow.setAgencyID(getAgencyID());
        dataFlow.setId(getId());
        dataFlow.setVersion(getVersion());
        return dataFlow;
    }
    public boolean isDimension(String s) {
        for(DimensionType d:this.getDataStructureComponents().getDimensionList().getDimensions()) {
            if( s.equals(d.getId().toString())){
                return true;
            }
        }
        if( s.equals(this.getDataStructureComponents().getDimensionList().getTimeDimension().getId().toString())){
            return true;
        }
        return false;
    }
    public boolean isTimeDimension(String s) {
        if( s.equals(this.getDataStructureComponents().getDimensionList().getTimeDimension().getId().toString())){
            return true;
        }
        return false;
    }
    public boolean isAttribute(String s) {
        for(AttributeType d:this.getDataStructureComponents().getAttributeList().getAttributes()) {
            if( s.equals(d.getId().toString())){
                return true;
            }
        }
        return false;
    }
    public boolean isPrimaryMeasure(String s) {
        if( "OBS_VALUE".equals(s))return true;
        else if( this.getDataStructureComponents().getMeasureList().getPrimaryMeasure().getId().toString().equals(s)){return true;}
        return false;
    }
    public int getKeyPosition(String s) {
        int i = 0;
        for(DimensionType d:this.getDataStructureComponents().getDimensionList().getDimensions()) {
            if( s.equals(d.getId().toString())){
                return i;
            }
            i++;
        }
        if( s.equals(this.getDataStructureComponents().getDimensionList().getTimeDimension().getId().toString())){
            return i;
        }
        throw new RuntimeException("Dimension:"+s+" not found in structure");
    }
}

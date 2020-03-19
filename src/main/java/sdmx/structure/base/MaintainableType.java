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
package sdmx.structure.base;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import sdmx.common.Annotations;
import sdmx.common.ExternalReferenceAttributeGroup;
import sdmx.commonreferences.IDType;
import sdmx.commonreferences.NestedID;
import sdmx.commonreferences.NestedNCNameID;
import sdmx.commonreferences.RefBase;
import sdmx.commonreferences.ReferenceType;
import sdmx.commonreferences.Version;

/**
 * <xs:complexType name="MaintainableType" abstract="true">
 * <xs:annotation>
 * <xs:documentation>MaintainableType is an abstract base type for all
 * maintainable objects.</xs:documentation>
 * </xs:annotation>
 * <xs:complexContent>
 * <xs:extension base="MaintainableBaseType">
 * <xs:attribute name="agencyID" type="common:NestedNCNameIDType"
 * use="required">
 * <xs:annotation>
 * <xs:documentation>The agencyID must be provided, and identifies the
 * maintenance agency of the object.</xs:documentation>
 * </xs:annotation>
 * </xs:attribute>
 * <xs:attribute name="isFinal" type="xs:boolean" use="optional"
 * default="false">
 * <xs:annotation>
 * <xs:documentation>The isFinal attribute indicates whether the object is
 * unchangeable without versioning. If the value is true, the object must be
 * versioned upon change. If the final attribute is not supplied, then the
 * object is assumed not to be final. Note that all production objects must be
 * final.</xs:documentation>
 * </xs:annotation>
 * </xs:attribute>
 * <xs:attribute name="isExternalReference" type="xs:boolean" use="optional"
 * default="false">
 * <xs:annotation>
 * <xs:documentation>The isExternalReference attribute, if true, indicates that
 * the actual object is not defined the corresponding element, rather its full
 * details are defined elsewhere - indicated by either the registryURL, the
 * repositoryURL, or the structureURL. The purpose of this is so that each
 * structure message does not have to redefine object that are already defined
 * elsewhere. If the isExternalReference attribute is not set, then it is
 * assumed to be false, and the object should contain the full definition of its
 * contents. If more than one of the registryURL, the repositoryURL, and the
 * structureURL are supplied, then the application processing the object can
 * choose the method it finds best suited to retrieve the details of the
 * object.</xs:documentation>
 * </xs:annotation>
 * </xs:attribute>
 * <xs:attributeGroup ref="common:ExternalReferenceAttributeGroup"/>
 * </xs:extension>
 * </xs:complexContent>
 * </xs:complexType>
 *
 * @author James
 */

public class MaintainableType extends MaintainableBaseType {

    private NestedNCNameID agencyID=null;
    private Boolean isFinal = null;
    private Boolean isExternalReference = null;
    private ExternalReferenceAttributeGroup externalReferences = null;

    /**
     * @return the agencyID
     */
    public NestedNCNameID getAgencyID() {
        return agencyID;
    }

    /**
     * @param agencyID the agencyID to set
     */
    public void setAgencyID(NestedNCNameID agencyID) {
        this.agencyID = agencyID;
    }

    /**
     * @return the isFinal
     */
    public Boolean isFinal() {
        return isFinal;
    }

    /**
     * @param isFinal the isFinal to set
     */
    public void setFinal(Boolean isFinal) {
        this.isFinal = isFinal;
    }

    /**
     * @return the isExternalReference
     */
    public Boolean isExternalReference() {
        return isExternalReference;
    }

    /**
     * @param isExternalReference the isExternalReference to set
     */
    public void setExternalReference(Boolean isExternalReference) {
        this.isExternalReference = isExternalReference;
    }

    /**
     * @return the externalReferences
     */
    public ExternalReferenceAttributeGroup getExternalReferences() {
        return externalReferences;
    }

    /**
     * @param externalReferences the externalReferences to set
     */
    public void setExternalReferences(ExternalReferenceAttributeGroup externalReferences) {
        this.externalReferences = externalReferences;
    }

    public boolean identifiesMe(String agency2, String id2, String vers2) {
        return identifiesMe(new NestedNCNameID(agency2), new IDType(id2), new Version(vers2));
    }

    public boolean identifiesMe(NestedNCNameID agency2, NestedID id2, Version vers2) {
        //System.out.println("Left=" + this.agencyID + "." + this.getId() + "." + this.getVersion());
        //System.out.println("Right=" + agency2 + "." + id2 + "." + vers2);
        if (vers2 == null||this.getVersion()==null) {
            if (this.agencyID.equals(agency2) && this.getId().equals(id2)) {
                return true;
            } else {
                //System.out.println("Doesn't Match!!");
                return false;
            }
        } else {
            if (this.agencyID.equals(agency2) && this.getId().equals(id2) && this.getVersion().equals(vers2)) {
                return true;
            } else {
                //System.out.println("Doesn't Match!!");
                return false;
            }
        }
    }

    public boolean identifiesMe(NestedNCNameID agency2, NestedID id2) {
        //System.out.println("Left=" + this.agencyID + "." + this.getId() + "." + this.getVersion());
        //System.out.println("Right=" + agency2 + "." + id2 + "." + vers2);
        if (this.agencyID.equals(agency2) && this.getId().equals(id2)) {
           // System.out.println("Match!"+agency2+":"+id2);
            return true;
        } else {
            //System.out.println("Doesn't Match!!");
            //System.out.println("My Agency:Id="+this.agencyID+":"+this.getId().toString());
            //System.out.println("Find Agency:Id="+agency2.toString()+":"+id2.toString());
            return false;
        }
    }
    public ReferenceType asReference() {
        RefBase ref = new RefBase(agencyID,getId(),getVersion(),null,null,null,false,null,null);
        ReferenceType reference = new ReferenceType(ref,getUri());
        return reference;
    }
    private void writeObject(ObjectOutputStream oos) throws IOException {
        if( agencyID==null ) {oos.writeUTF(null);}
        else {
            oos.writeUTF(agencyID.toString());
        }
        oos.writeObject(isFinal);
        oos.writeObject(isExternalReference);
        oos.writeObject(externalReferences);
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        String ag = ois.readUTF();
        if( ag != null ) {
            agencyID = new NestedNCNameID(ag);
        }
        isFinal = (Boolean)ois.readObject();
        isExternalReference = (Boolean)ois.readObject();
        externalReferences = (ExternalReferenceAttributeGroup)ois.readObject();
        
    }
}

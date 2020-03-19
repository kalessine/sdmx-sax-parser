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
package sdmx.commonreferences;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import sdmx.SdmxIO;
import sdmx.commonreferences.types.PackageTypeCodelistType;
import sdmx.commonreferences.types.ObjectTypeCodelistType;
import sdmx.xml.anyURI;

/**
 * <xs:complexType name="ReferenceType" abstract="true">
 * <xs:annotation>
 * <xs:documentation>ReferenceType is an abstract base type. It is used as the
 * basis for all references, to all for a top level generic object reference
 * that can be substituted with an explicit reference to any object. Any
 * reference can consist of a Ref (which contains all required reference fields
 * separately) and/or a URN. These must result in the identification of the same
 * object. Note that the Ref and URN elements are local and unqualified in order
 * to allow for refinement of this structure outside of the namespace. This
 * allows any reference to further refined by a different namespace. For
 * example, a metadata structure definition specific metadata set might wish to
 * restrict the URN to only allow for a value from an enumerated list. The
 * general URN structure, for the purpose of mapping the reference fields is as
 * follows:
 * urn:sdmx:org.package-name.class-name=agency-id:(maintainable-parent-object-id[maintainable-parent-object-version].)?(container-object-id.)?object-id([object-version])?.</xs:documentation>
 * </xs:annotation>
 * <xs:choice>
 * <xs:sequence>
 * <xs:element name="Ref" type="RefBaseType" form="unqualified">
 * <xs:annotation>
 * <xs:documentation>Ref is used to provide a complete set of reference fields.
 * Derived reference types will restrict the RefType so that the content of the
 * Ref element requires exactly what is needed for a complete
 * reference.</xs:documentation>
 * </xs:annotation>
 * </xs:element>
 * <xs:element name="URN" type="xs:anyURI" form="unqualified" minOccurs="0">
 * <xs:annotation>
 * <xs:documentation>URN is used to hold the URN of the referenced object. This
 * must be the same URN that would be constructed from the individual fields in
 * the Ref element.</xs:documentation>
 * </xs:annotation>
 * </xs:element>
 * </xs:sequence>
 * <xs:element name="URN" type="xs:anyURI" form="unqualified">
 * <xs:annotation>
 * <xs:documentation>URN is used to hold the URN of the referenced
 * object.</xs:documentation>
 * </xs:annotation>
 * </xs:element>
 * </xs:choice>
 * </xs:complexType>
 *
 * @author James
 */
public class ReferenceType implements Serializable {

    private RefBase ref = null;
    private anyURI urn = null;

    private transient PackageTypeCodelistType pack = null;
    private transient ObjectTypeCodelistType clazz = null;
    private transient NestedNCNameID agency = null;
    private transient IDType maintainedParentId = null;
    private transient Version maintainedParentVersion = null;
    private transient Version version = null;
    private transient IDType[] containedIds = null;
    private transient NestedID objectId = null;

    public ReferenceType(RefBase ref, anyURI urn) {
        this.ref = ref;
        this.urn = urn;
        if (this.ref != null) {
            //try {
            this.pack = ref.getPack();
            this.clazz = ref.getRefClass();
            this.agency = ref.getAgencyId();
            this.objectId = ref.getId();
            this.maintainedParentId = ref.getMaintainableParentId();
            this.maintainedParentVersion = ref.getMaintainableParentVersion();
            this.version = ref.getVersion();
            //} catch (URISyntaxException ex) {
            //    Logger.getLogger(ReferenceType.class.getName()).log(Level.SEVERE, null, ex);
            //}
        } else {
            parse();
        }
        if (this.urn == null) {
            try {
                //if (this.getAgencyId() != null) {
                produce();
                //}
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public ReferenceType(anyURI urn) {
        this.ref = null;
        this.urn = urn;
        parse();
    }

    /**
     * @return the ref
     */
    public RefBase getRef() {
        return ref;
    }

    /**
     * @param ref the ref to set
     */
    public void setRef(RefBase ref) {
        this.ref = ref;
    }

    /**
     * @return the urn
     */
    public anyURI getUrn() {
        return urn;
    }

    /**
     * @param urn the urn to set
     */
    public void setUrn(anyURI urn) {
        this.urn = urn;
    }

    private void produce() throws URISyntaxException {
        StringBuffer sb = new StringBuffer();
        sb.append(URN_START);
        sb.append(".infomodel.");
        if (getPack() != null) {
            sb.append(getPack().toString());
        }
        sb.append(".");
        if (getClazz() != null) {
            sb.append(getClazz().toString());
        }
        sb.append("=");
        if (getAgencyId() != null) {
            sb.append(getAgencyId().toString());
        }
        if (getMaintainableParentId() != null) {
            if (getMaintainableParentId() != null) {
                sb.append(":");
                sb.append(getMaintainableParentId().toString());
            }
            if (getVersion() != null) {
                sb.append("(" + getVersion().toString() + ")");
            }
            if (getId() != null) {
                sb.append(".");
                sb.append(getId().toString());
            }
        } else {
            if (getId() != null) {
                sb.append(".");
                sb.append(getId().toString());
            }
            if (getVersion() != null) {
                sb.append("(" + getVersion().toString() + ")");
            }
        }

        this.urn = new anyURI(sb.toString());
    }

    private static final String URN_START = "urn:sdmx:org.sdmx";

    public void parse() {
        String s = urn.getString();
        if (!s.startsWith(URN_START)) {
            System.out.println("Malformed SDMX URN:" + s);
            return;
        }
        int firstEquals = s.indexOf("=");
        String packageAndClass = s.substring(URN_START.length(), firstEquals);
        int firstDot = packageAndClass.lastIndexOf(".");
        String pack = packageAndClass.substring(packageAndClass.lastIndexOf(".", firstDot - 1) + 1, firstDot);
        if (SdmxIO.isCheckURN() && this.pack != null) {
            PackageTypeCodelistType pack2 = PackageTypeCodelistType.fromString(pack);
            if (this.pack != pack2) {
                throw new RuntimeException("Package in URN not same as Package in Ref " + this.pack.toString() + ":" + pack2.toString());
            } else {
                this.pack = pack2;
            }
        } else {
            this.pack = PackageTypeCodelistType.fromString(pack);
        }
        String clazz = packageAndClass.substring(firstDot + 1, packageAndClass.length());
        if (SdmxIO.isCheckURN() && this.clazz != null) {
            ObjectTypeCodelistType clazz2 = ObjectTypeCodelistType.fromString(clazz);
            if (this.clazz != clazz2) {
                throw new RuntimeException("Class in URN not same as Class in Ref " + this.clazz.toString() + ":" + clazz2.toString());
            } else {
                this.clazz = clazz2;
            }
        } else {
            this.clazz = ObjectTypeCodelistType.fromString(clazz);
        }
        String ag = s.substring(firstEquals + 1, s.indexOf(":", firstEquals));
        if (SdmxIO.isCheckURN() && this.agency != null) {
            NestedNCNameID agency2 = new NestedNCNameID(ag);
            if (!this.agency.equals(agency2)) {
                throw new RuntimeException("Agency in URN not equals to Agency in REf:" + this.agency.toString() + ":" + agency2.toString());
            }
        } else {
            this.agency = new NestedNCNameID(ag);
        }
        int loc = s.indexOf(ag) + ag.length() + 1;
        String idAndVersion = s.substring(loc, s.length());
        String id = idAndVersion.substring(0, idAndVersion.indexOf("("));
        if (SdmxIO.isCheckURN() && this.maintainedParentId != null) {
            IDType mparent2 = new IDType(id);
            if (!this.maintainedParentId.equals(mparent2)) {
                throw new RuntimeException("MaintainedParentId in URN not equal to Ref " + this.maintainedParentId.toString() + ":" + mparent2.toString());
            }
        } else {
            this.maintainedParentId = new IDType(id);
        }
        String vers = idAndVersion.substring(idAndVersion.indexOf("(") + 1, idAndVersion.indexOf(")"));
        if (SdmxIO.isCheckURN() && this.version != null) {
            Version vers2 = new Version(vers);
            if (!this.version.equals(vers2)) {
                throw new RuntimeException("VersionType in URN not equals to VersionType in Ref " + this.version.toString() + ":" + vers2.toString());
            }
        } else {
            this.version = new Version(vers);
        }
        if (idAndVersion.indexOf(").") != -1) {
            id = idAndVersion.substring(idAndVersion.indexOf(").") + 2, idAndVersion.length());
            String[] array = id.split("\\.");
            if (array.length > 0) {
                this.containedIds = new IDType[array.length - 1];
                for (int i = 0; i < array.length - 1; i++) {
                    this.containedIds[i] = new IDType(array[i]);
                }
                this.objectId = new IDType(array[array.length - 1]);
            } else {
                this.objectId = new IDType(id);
            }
         } else {
            this.objectId = maintainedParentId;
            //this.maintainedParentId = null;
        }
    }

    /**
     * @return the pack
     */
    public PackageTypeCodelistType getPack() {
        return pack;
    }

    /**
     * @return the clazz
     */
    public ObjectTypeCodelistType getRefClass() {
        return clazz;
    }

    /**
     * @return the clazz
     */
    public ObjectTypeCodelistType getClazz() {
        return clazz;
    }

    /**
     * @return the agency
     */
    public NestedNCNameID getAgencyId() {
        return agency;
    }

    /**
     * @return the maintainedObjectId
     */
    public IDType getMaintainableParentId() {
        return maintainedParentId;
    }

    /**
     * @return the maintainedObjectVersion
     */
    public Version getVersion() {
        return version;
    }

    /**
     * @return the objectId
     */
    public NestedID getId() {
        return objectId;
    }

    public IDType[] getContainedObjectIds() {
        return containedIds;
    }

    /**
     * @return the maintainedParentVersion
     */
    public Version getMaintainedParentVersion() {
        return maintainedParentVersion;
    }
    //public IDType getMainID() {
    //    if( this.maintainedParentId==null ) return objectId!=null?objectId.asID():null;
    //    else return maintainedParentId;
    //}

    public void dump() {
        System.out.println("Reference");
        System.out.println("Agency:" + this.getAgencyId());
        System.out.println("MID:" + this.getMaintainableParentId());
        System.out.println("MVers:" + this.getMaintainedParentVersion());
        System.out.println("ID:" + this.getId());
        System.out.println("Vers:" + this.getVersion());
        System.out.println("Class:" + this.getClazz());
        System.out.println("Pack:" + this.getPack());
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getName());
        sb.append(":" + this.getAgencyId());
        sb.append(":" + this.getMaintainableParentId());
        sb.append(":" + this.getMaintainedParentVersion());
        sb.append(":" + this.getId());
        sb.append(":" + this.getVersion());
        sb.append(":" + this.getClazz());
        sb.append(":" + this.getPack());
        return sb.toString();
    }
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.writeUTF(pack.toString());
        oos.writeUTF(clazz.toString());
        oos.writeUTF(agency!=null?agency.toString():"");
        oos.writeUTF(maintainedParentId!=null?maintainedParentId.toString():"");
        oos.writeUTF(maintainedParentVersion!=null?maintainedParentVersion.toString():"");
        oos.writeUTF(objectId!=null?objectId.toString():"");
        oos.writeUTF(version!=null?version.toString():"");
        oos.writeUTF(this.urn!=null?urn.toString():"");
        oos.writeObject(this.containedIds);
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        this.pack = PackageTypeCodelistType.fromString(ois.readUTF());
        this.clazz = ObjectTypeCodelistType.fromString(ois.readUTF());
        String ag = ois.readUTF();
        if( !"".equals(ag) ) {
            this.agency = new NestedNCNameID(ag);
        }
        String mid = ois.readUTF();
        if( !"".equals(mid) ) {
            this.maintainedParentId = new IDType(mid);
        }
        String mv = ois.readUTF();
        if( !"".equals(mv) ) {
            this.maintainedParentVersion=new Version(mv);
        }
        String oid = ois.readUTF();
        if( !"".equals(oid)) {
            this.objectId = new IDType(oid);
        }
        String v = ois.readUTF();
        if( !"".equals(v) ) {
            this.version = new Version(v);
        }
    }
}

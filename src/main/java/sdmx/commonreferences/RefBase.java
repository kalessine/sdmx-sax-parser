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

import sdmx.commonreferences.types.PackageTypeCodelistType;
import sdmx.commonreferences.types.ObjectTypeCodelistType;

/**
 *	<xs:complexType name="RefBaseType" abstract="true">
		<xs:annotation>
			<xs:documentation>RefBaseType is an abstract base type the defines the basis for any set of complete reference fields. This should be refined by derived types so that only the necessary fields are available and required as necessary. This can be used for both full and local references (when some of the values are implied from another context). A local reference is indicated with the local attribute. The values in this type correspond directly to the components of the URN structure, and thus can be used to compose a URN when the local attribute value is false. As this is the case, any reference components which are not part of the URN structure should not be present in the derived types.</xs:documentation>
		</xs:annotation>
		<xs:attribute name="agencyID" type="NestedNCNameIDType" use="optional">
			<xs:annotation>
				<xs:documentation>The agencyID attribute identifies the maintenance agency for the object being referenced (agency-id in the URN structure). This is optional to allow for local references (where the other reference fields are inferred from another context), but all complete references will require this.</xs:documentation>
			</xs:annotation>
		</xs:attribute>
		<xs:attribute name="maintainableParentID" type="IDType" use="optional">
			<xs:annotation>
				<xs:documentation>The maintainableParentID attribute identifies the maintainable object in which the referenced object is defined, if applicable (maintainable-parent-object-id in the URN structure). This is only used in references where the referenced object is not itself maintainable.</xs:documentation>
			</xs:annotation>
		</xs:attribute>
		<xs:attribute name="maintainableParentVersion" type="VersionType" use="optional">
			<xs:annotation>
				<xs:documentation>The maintainableParentVersion attribute identifies the version of the maintainable object in which the referenced object is defined (maintainable-parent-object-version in the URN structure). This is only used in references where the referenced object is not itself maintainable. This should only be used when the maintainableParentID is present. If this is available, a default of 1.0 will always apply.</xs:documentation>
			</xs:annotation>
		</xs:attribute>
		<xs:attribute name="containerID" type="NestedIDType" use="optional">
			<xs:annotation>
				<xs:documentation>The containerID attribute identifies the object within a maintainable object in which the referenced object is defined (container-object-id in the URN structure). This is only used in references where the referenced object is not contained directly within a maintainable object (e.g. a Component within a ComponentList, within a maintainable Structure). If the container has a fixed identifier, this attribute will not be present.</xs:documentation>
			</xs:annotation>
		</xs:attribute>
		<xs:attribute name="id" type="NestedIDType" use="required">
			<xs:annotation>
				<xs:documentation>The id attribute identifies the object being referenced, and is therefore always required.</xs:documentation>
			</xs:annotation>
		</xs:attribute>
		<xs:attribute name="version" type="VersionType" use="optional">
			<xs:annotation>
				<xs:documentation>The version attribute identifies the version of the object being reference, if applicable. If this is available, a default value of 1.0 will always apply.</xs:documentation>
			</xs:annotation>
		</xs:attribute>
		<xs:attribute name="local" type="xs:boolean" use="optional">
			<xs:annotation>
				<xs:documentation>The local attribute indicates whether this set of reference fields is meant for local referencing, in which case some of the reference fields will be implied from another context. Concrete instances of this class will always fix this value to either true or false, depending on their intended usage. If the value is fixed to true, then the complete set of reference fields will be required and a URN can be fully composed from the values.</xs:documentation>
			</xs:annotation>
		</xs:attribute>
		<xs:attribute name="class" type="ObjectTypeCodelistType" use="optional">
			<xs:annotation>
				<xs:documentation>The class attribute indicates the class name of the object being referenced. This attribute allows any reference to be processed generically from this definition. References derived from this should fix the value of this attribute to indicate the type of object that is being referenced, or in the case that a reference which allows specific types of fields, the representation should be sub-setted to the appropriate values.</xs:documentation>
			</xs:annotation>
		</xs:attribute>
		<xs:attribute name="package" type="PackageTypeCodelistType" use="optional">
			<xs:annotation>
				<xs:documentation>The package attribute indicates the package name for the object being referenced. This attribute allows any reference to be processed generically from this definition. References derived from this should fix the value of this attribute to indicate the type of object that is being referenced, or in the case that a reference which allows specific types of fields, the representation should be sub-setted to the appropriate values.</xs:documentation>
			</xs:annotation>
		</xs:attribute>
	</xs:complexType>
 * @author James
 */
/*
  James G 7/11/2014
  maintainedParentVersion is ignored, it only seems to be used for ChildObjectRef's..
  for ChilObjectRefs, the 'version' field is ignored.. there is currently no case where
  both version and maintainedParentVersion exist on a single reference...
  so I will reuse the 'version' field for both uses of the 'version' name..
  if you want to change this,  go to ChildObjectRef and change the 'vpar' constructor attribute
  to go into the 'maintainableParentVersion' field in the super(..) call on the first line.
  then start changing tests.. =D
*/
public class RefBase {
    private NestedNCNameID agencyId = null;
    private IDType maintainableParentId = null;
    private Version maintainableParentVersion = null;
    private NestedID containerId = null;
    private NestedID id = null;
    private Version version = null;
    private boolean local = false;
    private ObjectTypeCodelistType clazz = null;
    private PackageTypeCodelistType pack = null;

   public RefBase(NestedNCNameID agencyId,NestedID id, Version vers,IDType maintParent,Version mainVers,NestedID containId,boolean loc,ObjectTypeCodelistType obs,PackageTypeCodelistType pack) {
       this.agencyId=agencyId;
       this.maintainableParentId=maintParent;
       this.maintainableParentVersion=mainVers;
       this.containerId=containId;
       this.id=id;
       this.version=vers;
       this.local=local;
       this.clazz=obs;
       this.pack=pack;
   }

    public RefBase() {
    }

    /**
     * @return the agencyId
     */
    protected NestedNCNameID getAgencyId() {
        return agencyId;
    }

    /**
     * @return the maintainableParentId
     */
    protected IDType getMaintainableParentId() {
        return maintainableParentId;
    }

    /**
     * @return the maintainableParentVersion
     */
    protected Version getMaintainableParentVersion() {
        return maintainableParentVersion;
    }

    /**
     * @return the containerId
     */
    protected NestedID getContainerId() {
        return containerId;
    }

    /**
     * @return the id
     */
    protected NestedID getId() {
        return id;
    }

    /**
     * @return the version
     */
    protected Version getVersion() {
        return version;
    }

    /**
     * @return the local
     */
    protected boolean isLocal() {
        return local;
    }

    /**
     * @return the clazz
     */
    protected ObjectTypeCodelistType getRefClass() {
        return clazz;
    }

    /**
     * @return the pack
     */
    protected PackageTypeCodelistType getPack() {
        return pack;
    }

    /**
     * @param agencyId the agencyId to set
     */
    public void setAgencyId(NestedNCNameID agencyId) {
        this.agencyId = agencyId;
    }

    /**
     * @param maintainableParentId the maintainableParentId to set
     */
    public void setMaintainableParentId(IDType maintainableParentId) {
        this.maintainableParentId = maintainableParentId;
    }

    /**
     * @param maintainableParentVersion the maintainableParentVersion to set
     */
    public void setMaintainableParentVersion(Version maintainableParentVersion) {
        this.maintainableParentVersion = maintainableParentVersion;
    }

    /**
     * @param containerId the containerId to set
     */
    public void setContainerId(NestedID containerId) {
        this.containerId = containerId;
    }

    /**
     * @param id the id to set
     */
    public void setId(NestedID id) {
        this.id = id;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(Version version) {
        this.version = version;
    }

    /**
     * @param local the local to set
     */
    public void setLocal(boolean local) {
        this.local = local;
    }


}

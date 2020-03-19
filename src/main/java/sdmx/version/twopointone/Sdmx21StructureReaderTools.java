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
package sdmx.version.twopointone;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;
import org.apache.xmlbeans.GDuration;
import org.apache.xmlbeans.QNameSet;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlDocumentProperties;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.xml.stream.XMLInputStream;
import org.sdmx.resources.sdmxml.schemas.v21.common.ObjectTypeCodelistType.Enum;
import org.sdmx.resources.sdmxml.schemas.v21.structure.ConstraintContentTargetType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.DataStructureComponentsType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.IdentifiableObjectTargetType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.KeyDescriptorValuesTargetType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.MetadataAttributeType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.PrimaryMeasureType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.ReportPeriodTargetType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.ReportStructureType;
import org.w3c.dom.Node;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;
import sdmx.common.ActionType;
import sdmx.common.AnnotationType;
import sdmx.common.DataType;
import sdmx.common.Description;
import sdmx.common.DimensionTypeType;
import sdmx.common.ExternalReferenceAttributeGroup;
import sdmx.common.Name;
import sdmx.common.ObservationDimensionType;
import sdmx.common.ObservationalTimePeriodType;
import sdmx.common.OccurenceType;
import sdmx.common.PayloadStructureType;
import sdmx.common.StandardTimePeriodType;
import sdmx.common.TextType;
import sdmx.common.TimezoneType;
import sdmx.commonreferences.CategoryRef;
import sdmx.commonreferences.CategoryReference;
import sdmx.commonreferences.ConceptRef;
import sdmx.commonreferences.ConceptReference;
import sdmx.commonreferences.DataProviderRef;
import sdmx.commonreferences.DataProviderReference;
import sdmx.commonreferences.DataStructureRef;
import sdmx.commonreferences.DataStructureReference;
import sdmx.commonreferences.DataflowRef;
import sdmx.commonreferences.DataflowReference;
import sdmx.commonreferences.DimensionRef;
import sdmx.commonreferences.DimensionReference;
import sdmx.commonreferences.IDType;
import sdmx.commonreferences.ItemSchemeRefBase;
import sdmx.commonreferences.ItemSchemeReferenceBase;
import sdmx.commonreferences.LocalDimensionRef;
import sdmx.commonreferences.LocalDimensionReference;
import sdmx.commonreferences.LocalGroupKeyDescriptorRef;
import sdmx.commonreferences.LocalGroupKeyDescriptorReference;
import sdmx.commonreferences.LocalItemRefBase;
import sdmx.commonreferences.LocalItemReference;
import sdmx.commonreferences.LocalMetadataTargetRef;
import sdmx.commonreferences.LocalMetadataTargetReference;
import sdmx.commonreferences.LocalPrimaryMeasureRef;
import sdmx.commonreferences.LocalPrimaryMeasureReference;
import sdmx.commonreferences.MetadataStructureRef;
import sdmx.commonreferences.MetadataStructureReference;
import sdmx.commonreferences.NCNameID;
import sdmx.commonreferences.NestedID;
import sdmx.commonreferences.NestedNCNameID;
import sdmx.commonreferences.ObjectReference;
import sdmx.commonreferences.ProvisionAgreementRef;
import sdmx.commonreferences.ProvisionAgreementReference;
import sdmx.commonreferences.RefBase;
import sdmx.commonreferences.SetReference;
import sdmx.commonreferences.StructureRefBase;
import sdmx.commonreferences.StructureRef;
import sdmx.commonreferences.StructureReferenceBase;
import sdmx.commonreferences.StructureUsageRefBase;
import sdmx.commonreferences.StructureUsageReferenceBase;
import sdmx.commonreferences.Version;
import sdmx.commonreferences.types.ItemSchemePackageTypeCodelistType;
import sdmx.commonreferences.types.ItemSchemeTypeCodelistType;
import sdmx.commonreferences.types.ItemTypeCodelistType;
import sdmx.commonreferences.types.ObjectTypeCodelistType;
import sdmx.commonreferences.types.PackageTypeCodelistType;
import sdmx.exception.TypeValueNotFoundException;
import sdmx.message.BaseHeaderType;
import sdmx.message.ContactType;
import sdmx.message.HeaderTimeType;
import sdmx.message.PartyType;
import sdmx.message.SenderType;
import sdmx.message.StructureType;
import sdmx.structure.CategorisationsType;
import sdmx.structure.CategorySchemesType;
import sdmx.structure.CodelistsType;
import sdmx.structure.ConceptsType;
import sdmx.structure.ConstraintsType;
import sdmx.structure.DataStructuresType;
import sdmx.structure.DataflowsType;
import sdmx.structure.MetadataStructuresType;
import sdmx.structure.StructuresType;
import sdmx.structure.base.RepresentationType;
import sdmx.structure.base.TextFormatType;
import sdmx.structure.categorisation.CategorisationType;
import sdmx.structure.category.CategorySchemeType;
import sdmx.structure.category.CategoryType;
import sdmx.structure.codelist.CodeType;
import sdmx.structure.codelist.CodelistType;
import sdmx.structure.concept.ConceptRepresentation;
import sdmx.structure.concept.ConceptSchemeType;
import sdmx.structure.concept.ConceptType;
import sdmx.structure.concept.ISOConceptReferenceType;
import sdmx.structure.constraint.AttachmentConstraintAttachmentType;
import sdmx.structure.constraint.AttachmentConstraintType;
import sdmx.structure.constraint.ContentConstraintType;
import sdmx.structure.dataflow.DataflowType;
import sdmx.structure.datastructure.AttributeListType;
import sdmx.structure.datastructure.AttributeRelationshipType;
import sdmx.structure.datastructure.AttributeType;
import sdmx.structure.datastructure.DataStructureComponents;
import sdmx.structure.datastructure.DataStructureType;
import sdmx.structure.datastructure.DimensionListType;
import sdmx.structure.datastructure.DimensionType;
import sdmx.structure.datastructure.MeasureDimensionType;
import sdmx.structure.datastructure.MeasureListType;
import sdmx.structure.datastructure.PrimaryMeasure;
import sdmx.structure.datastructure.SimpleDataStructureRepresentationType;
import sdmx.structure.datastructure.TimeDimensionType;
import sdmx.structure.metadatastructure.ConstraintContentTarget;
import sdmx.structure.metadatastructure.DataSetTarget;
import sdmx.structure.metadatastructure.DataSetTargetType;
import sdmx.structure.metadatastructure.IdentifiableObjectTarget;
import sdmx.structure.metadatastructure.KeyDescriptorValuesTarget;
import sdmx.structure.metadatastructure.MetadataAttribute;
import sdmx.structure.metadatastructure.MetadataStructureType;
import sdmx.structure.metadatastructure.MetadataTargetType;
import sdmx.structure.metadatastructure.ReportPeriodRepresentationType;
import sdmx.structure.metadatastructure.ReportPeriodTarget;
import sdmx.structure.metadatastructure.ReportStructure;
import sdmx.xml.DateTime;
import sdmx.xml.DateType;
import sdmx.xml.ID;
import sdmx.xml.anyURI;
import sdmx.xml.duration;
import sdmx.xml.positiveInteger;

/**
 *
 * @author James
 * @todo Need sdmx 2.1 file with constraints to test with..
 */
public class Sdmx21StructureReaderTools {

    public static StructureType toStructureDocument(InputStream in) throws XmlException, IOException {
        org.sdmx.resources.sdmxml.schemas.v21.message.StructureDocument structDoc = null;
        try {
            structDoc = org.sdmx.resources.sdmxml.schemas.v21.message.StructureDocument.Factory.parse(in);
            return parseStructure(structDoc);
        } catch (TypeValueNotFoundException ex) {
            Logger.getLogger(Sdmx21StructureReaderTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static StructureType parseStructure(org.sdmx.resources.sdmxml.schemas.v21.message.StructureDocument structDoc) throws TypeValueNotFoundException {
        StructuresType struct = new StructuresType();
        BaseHeaderType header = null;
        try {
            struct.setDataflows(Sdmx21StructureReaderTools.toDataflows(structDoc.getStructure().getStructures().getDataflows()));
            struct.setCategorySchemes(Sdmx21StructureReaderTools.toCategorySchemes(structDoc.getStructure().getStructures().getCategorySchemes()));
            struct.setCategorisations(Sdmx21StructureReaderTools.toCategorisationsType(structDoc.getStructure().getStructures().getCategorisations()));
            struct.setCodelists(Sdmx21StructureReaderTools.toCodelistsType(structDoc.getStructure().getStructures().getCodelists()));
            struct.setConcepts(Sdmx21StructureReaderTools.toConcepts(structDoc.getStructure().getStructures().getConcepts()));
            struct.setDataStructures(Sdmx21StructureReaderTools.toDataStructures(structDoc.getStructure().getStructures().getDataStructures()));
            struct.setConstraints(Sdmx21StructureReaderTools.toConstraints(structDoc.getStructure().getStructures().getConstraints()));
            struct.setMetadataStructures(Sdmx21StructureReaderTools.toMetadataStructures(structDoc.getStructure().getStructures().getMetadataStructures()));
            header = toHeader(structDoc.getStructure().getHeader());
        } catch (URISyntaxException ex) {
            Logger.getLogger(Sdmx21StructureReaderTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        StructureType sd = new StructureType();
        sd.setStructures(struct);
        sd.setHeader(header);
        return sd;
    }

    public static BaseHeaderType toHeader(org.sdmx.resources.sdmxml.schemas.v21.message.BaseHeaderType header) throws URISyntaxException, TypeValueNotFoundException {
        BaseHeaderType header2 = new BaseHeaderType();
        header2.setDataProvider(toDataProviderReferenceType(header.getDataProvider()));
        header2.setDataSetAction(toActionType(header.getDataSetAction()));
        header2.setDataSetID(toIDTypeList(header.getDataSetIDArray()));
        header2.setEmbargoDate(toDateTime(header.getEmbargoDate()));
        header2.setExtracted(toDateTime(header.getExtracted()));
        header2.setId(header.getID());
        header2.setNames(toNames(header.getNameArray()));
        header2.setPrepared(toHeaderTimeType(header.getPrepared()));
        header2.setReceivers(toPartyTypeList(header.getReceiverArray()));
        header2.setReportingBegin(toObservationalTimePeriodType(header.getReportingBegin()));
        header2.setReportingEnd(toObservationalTimePeriodType(header.getReportingEnd()));
        header2.setSender(toSenderType(header.getSender()));
        header2.setSource(toTextType(header.getSourceArray()));
        header2.setStructures(toPayloadStructureList(header.getStructureArray()));
        header2.setTest(header.getTest());
        return header2;
    }

    public static HeaderTimeType toHeaderTimeType(Calendar c) {
        HeaderTimeType htt = new HeaderTimeType();
        htt.setDate(toDateTime(c));
        return htt;
    }

    public static ActionType toActionType(org.sdmx.resources.sdmxml.schemas.v21.common.ActionType.Enum at) {
        if (at == null) {
            return null;
        }
        return ActionType.fromString(at.toString());
    }

    public static List<IDType> toIDTypeList(String[] idtypes) {
        List<IDType> list = new ArrayList<IDType>();
        for (int i = 0; i < idtypes.length; i++) {
            list.add(toIDType(idtypes[i]));
        }
        return list;
    }

    public static NestedNCNameID toNCName(String s) {
        return new NestedNCNameID(s);
    }

    public static NCNameID toNCNameIDType(String s) {
        return new NCNameID(s);
    }

    public static IDType toIDType(String s) {
        if (s == null) {
            return null;
        }
        return new IDType(s);
    }

    public static ID toID(String s) {
        if (s == null) {
            return null;
        }
        return new ID(s);
    }

    public static sdmx.structure.CategorisationsType toCategorisationsType(org.sdmx.resources.sdmxml.schemas.v21.structure.CategorisationsType cat1) throws URISyntaxException, TypeValueNotFoundException {
        if (cat1 == null) {
            return null;
        }
        CategorisationsType cat2 = new CategorisationsType();
        List<CategorisationType> cats = new ArrayList<CategorisationType>(cat1.getCategorisationArray().length);
        for (int i = 0; i < cat1.getCategorisationArray().length; i++) {
            cats.add(toCategorisationType(cat1.getCategorisationArray(i)));
        }
        cat2.setCategorisations(cats);
        return cat2;
    }

    public static CategorisationType toCategorisationType(org.sdmx.resources.sdmxml.schemas.v21.structure.CategorisationType cat1) throws URISyntaxException, TypeValueNotFoundException {
        List<Name> names = new ArrayList<Name>();
        for (int i = 0; i < cat1.getNameArray().length; i++) {
            names.add(toName(cat1.getNameArray(i)));
        }
        return new CategorisationType(toNCName(cat1.getAgencyID()), toAnnotations(cat1.getAnnotations()), toDescriptions(cat1.getDescriptionArray()), toIDType(cat1.getId()), cat1.getIsExternalReference(), cat1.getIsFinal(), names, toObjectReferenceType(cat1.getSource()), toCategoryReferenceType(cat1.getTarget()), toAnyURI(cat1.getUri()), toAnyURI(cat1.getUrn()), toDateTime(cat1.getValidFrom()), toDateTime(cat1.getValidTo()), toVersionType(cat1.getVersion()));
    }

    public static sdmx.common.Annotations toAnnotations(org.sdmx.resources.sdmxml.schemas.v21.common.AnnotationsType annots1) {
        if (annots1 == null) {
            return null;
        }
        sdmx.common.Annotations annots2 = new sdmx.common.Annotations();
        for (int i = 0; i < annots1.getAnnotationArray().length; i++) {
            annots2.addAnnotation(toAnnotation(annots1.getAnnotationArray(i)));
        }
        return annots2;

    }

    public static sdmx.common.AnnotationType toAnnotation(org.sdmx.resources.sdmxml.schemas.v21.common.AnnotationType ann) {
        AnnotationType an = new AnnotationType();
        String id = ann.getId();
        an.setId(id);
        for (int i = 0; i < ann.getAnnotationTextArray().length; i++) {
            an.getAnnotationText().add(toTextType(ann.getAnnotationTextArray(i)));
        }
        an.setAnnotationTitle(ann.getAnnotationTitle());
        an.setAnnotationType(ann.getAnnotationType());
        an.setAnnotationUrl(ann.getAnnotationURL());
        return an;
    }

    public static sdmx.common.TextType toTextType(org.sdmx.resources.sdmxml.schemas.v21.common.TextType tt) {
        TextType tt1 = new TextType(tt.getLang(), tt.getStringValue());
        return tt1;

    }

    public static List<sdmx.common.TextType> toTextType(org.sdmx.resources.sdmxml.schemas.v21.common.TextType[] tt) {
        List<TextType> list = new ArrayList<TextType>();
        for (int i = 0; i < tt.length; i++) {
            list.add(toTextType(tt[i]));
        }
        return list;
    }

    public static List<sdmx.common.Description> toDescriptions(org.sdmx.resources.sdmxml.schemas.v21.common.TextType[] tt) {
        List<Description> descs = new ArrayList<Description>();
        for (int i = 0; i < tt.length; i++) {
            descs.add(toDescription(tt[i]));
        }
        return descs;
    }

    public static sdmx.common.Description toDescription(org.sdmx.resources.sdmxml.schemas.v21.common.TextType tt) {
        Description tt1 = new Description(tt.getLang(), tt.getStringValue());
        return tt1;

    }

    public static sdmx.common.Name toName(org.sdmx.resources.sdmxml.schemas.v21.common.TextType tt) {
        //System.out.println("lang="+tt.getLang()+":"+tt.getStringValue());
        Name tt1 = new Name(tt.getLang(), tt.getStringValue());
        return tt1;

    }

    public static ObjectReference toObjectReferenceType(org.sdmx.resources.sdmxml.schemas.v21.common.ObjectReferenceType ort1) throws URISyntaxException, TypeValueNotFoundException {
        if (ort1.getRef() != null) {
            ObjectReference ort2 = new ObjectReference(toRefBaseType(ort1.getRef()), toAnyURI(ort1.getURN()));
            return ort2;
        } else {
            ObjectReference ort2 = new ObjectReference(toAnyURI(ort1.getURN()));
            return ort2;
        }
    }

    public static CategoryReference toCategoryReferenceType(org.sdmx.resources.sdmxml.schemas.v21.common.CategoryReferenceType crt1) throws URISyntaxException, TypeValueNotFoundException {
        if (crt1.getRef() != null) {
            CategoryReference crt2 = new CategoryReference(toCategoryRefType(crt1.getRef()), toAnyURI(crt1.getURN()));
            return crt2;
        } else {
            CategoryReference crt2 = new CategoryReference(new anyURI(crt1.getURN()));
            return crt2;
        }
    }

    public static RefBase toRefBaseType(org.sdmx.resources.sdmxml.schemas.v21.common.RefBaseType ref1) throws TypeValueNotFoundException {
        ObjectTypeCodelistType obj = toObjectReferenceCodelistType(ref1.getClass1());
        RefBase ref2 = null;
        ref2 = new RefBase(toNCName(ref1.getAgencyID()), toNestedIDType(ref1.getId()), toVersionType(ref1.getVersion()), toIDType(ref1.getMaintainableParentID()), toVersionType(ref1.getMaintainableParentVersion()), toNestedIDType(ref1.getContainerID()), ref1.getLocal(), obj, toPackageTypeCodelistType(ref1.getPackage()));
        return ref2;
    }

    public static CategoryRef toCategoryRefType(org.sdmx.resources.sdmxml.schemas.v21.common.RefBaseType ref1) throws TypeValueNotFoundException {
        ItemTypeCodelistType obj = toItemTypeCodelistType(ref1.getClass1());
        CategoryRef ref2 = null;
        ref2 = new CategoryRef(toNestedNCNameIDType(ref1.getAgencyID()), toNestedIDType(ref1.getId()), toIDType(ref1.getMaintainableParentID()), toVersionType(ref1.getMaintainableParentVersion()), obj, toItemSchemePackageTypeCodelistType(ref1.getPackage()));
        return ref2;
    }

    public static Version toVersionType(String s) {
        if (s == null) {
            return null;
        }
        return new Version(s);
    }

    public static NestedID toNestedIDType(String s) {
        if (s == null) {
            return null;
        }
        return new NestedID(s);
    }

    public static PackageTypeCodelistType toPackageTypeCodelistType(org.sdmx.resources.sdmxml.schemas.v21.common.PackageTypeCodelistType.Enum pk) throws TypeValueNotFoundException {
        return PackageTypeCodelistType.fromStringWithException(pk.toString());
    }

    public static ItemSchemePackageTypeCodelistType toItemSchemePackageTypeCodelistType(org.sdmx.resources.sdmxml.schemas.v21.common.ItemSchemePackageTypeCodelistType.Enum pk) throws TypeValueNotFoundException {
        return ItemSchemePackageTypeCodelistType.fromStringWithException(pk.toString());
    }

    public static ObjectTypeCodelistType toObjectReferenceCodelistType(Enum class1) throws TypeValueNotFoundException {
        return ObjectTypeCodelistType.fromStringWithException(class1.toString());
    }

    public static anyURI toAnyURI(String s) throws URISyntaxException {
        if (s == null) {
            return null;
        }
        
        
/*****
 * WORKAROUND FOR CEPAL's MALFORMED OBS_VALUE URN!!!
 */
        if( "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0). OBS_VALUE".equals(s) ){
            return new anyURI("urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).OBS_VALUE");
        }
        return new anyURI(s);
    }

    public static DateTime toDateTime(Calendar c) {
        if (c == null) {
            return null;
        }
        return new DateTime(c);
    }

    public static sdmx.xml.DateType toDateType(Calendar c) {
        if (c == null) {
            return null;
        }
        return new DateType(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DATE));
    }

    public static ItemTypeCodelistType toItemTypeCodelistType(Enum class1) throws TypeValueNotFoundException {
        ItemTypeCodelistType item = ItemTypeCodelistType.fromStringWithException(class1.toString());
        return item;
    }

    public static CodelistsType toCodelistsType(org.sdmx.resources.sdmxml.schemas.v21.structure.CodelistsType cl1) throws URISyntaxException, TypeValueNotFoundException {
        if( cl1 == null ) return null;
        List<CodelistType> codelists = new ArrayList<CodelistType>();
        for (int i = 0; i < cl1.getCodelistArray().length; i++) {
            codelists.add(toCodelist(cl1.getCodelistArray(i)));
        }
        CodelistsType cl2 = new CodelistsType(codelists);
        return cl2;
    }

    public static CodelistType toCodelist(org.sdmx.resources.sdmxml.schemas.v21.structure.CodelistType cl1) throws URISyntaxException, TypeValueNotFoundException {
        CodelistType cl2 = new CodelistType();
        cl2.setId(toNCNameIDType(cl1.getId()));
        cl2.setAgencyID(toNestedNCNameIDType(cl1.getAgencyID()));
        cl2.setAnnotations(toAnnotations(cl1.getAnnotations()));
        cl2.setDescriptions(toDescriptions(cl1.getDescriptionArray()));
        // These three statements have defaults of 'false' set 
        // inside sdmx schema.. so we pick up false here if there is no value set in xml file
        cl2.setExternalReference(cl1.getIsExternalReference());
        cl2.setFinal(cl1.getIsFinal());
        cl2.setPartial(cl1.getIsPartial());
        cl2.setUri(toAnyURI(cl1.getUri()));
        cl2.setUrn(toAnyURI(cl1.getUrn()));
        cl2.setNames(toNames(cl1.getNameArray()));
        cl2.setCodes(toCodes(cl1.getCodeArray()));
        cl2.setVersion(toVersionType(cl1.getVersion()));
        cl2.setExternalReferences(toExternalReference(cl1.getServiceURL(), cl1.getStructureURL()));
        //cl1.getS
        return cl2;
    }

    public static ExternalReferenceAttributeGroup toExternalReference(String service, String struct) {
        if (service == null && struct == null) {
            return null;
        }
        return new ExternalReferenceAttributeGroup(service, struct);
    }

    public static List<CodeType> toCodes(org.sdmx.resources.sdmxml.schemas.v21.structure.CodeType[] codes) throws URISyntaxException, TypeValueNotFoundException {
        List<CodeType> codelist = new ArrayList<CodeType>();
        for (int i = 0; i < codes.length; i++) {
            codelist.add(toCode(codes[i]));
        }
        return codelist;
    }

    public static List<CodeType> toCodes(org.sdmx.resources.sdmxml.schemas.v21.structure.ItemType[] codes) throws URISyntaxException, TypeValueNotFoundException {
        List<CodeType> codelist = new ArrayList<CodeType>();
        for (int i = 0; i < codes.length; i++) {
            codelist.add(toCode(codes[i]));
        }
        return codelist;
    }

    public static NestedNCNameID toNestedNCNameIDType(String ncname) {
        return new NestedNCNameID(ncname);
    }

    public static List<sdmx.common.Name> toNames(org.sdmx.resources.sdmxml.schemas.v21.common.TextType[] tt) {
        List<Name> descs = new ArrayList<Name>();
        for (int i = 0; i < tt.length; i++) {
            descs.add(toName(tt[i]));
        }
        return descs;
    }

    public static CodeType toCode(org.sdmx.resources.sdmxml.schemas.v21.structure.CodeType cl1) throws URISyntaxException, TypeValueNotFoundException {
        CodeType cl2 = new CodeType();
        cl2.setAnnotations(toAnnotations(cl1.getAnnotations()));
        cl2.setNames(toNames(cl1.getNameArray()));
        cl2.setDescriptions(toDescriptions(cl1.getDescriptionArray()));
        cl2.setId(toIDType(cl1.getId().toString()));
        cl2.setUri(toAnyURI(cl1.getUri()));
        cl2.setUrn(toAnyURI(cl1.getUrn()));
        cl2.setCodes(toCodes(cl1.getItemArray()));
        cl2.setParent(toLocalItemReferenceType(cl1.getParent()));
        return cl2;
    }

    public static CodeType toCode(org.sdmx.resources.sdmxml.schemas.v21.structure.ItemType cl1) throws URISyntaxException, TypeValueNotFoundException {
        CodeType cl2 = new CodeType();
        cl2.setAnnotations(toAnnotations(cl1.getAnnotations()));
        cl2.setNames(toNames(cl1.getNameArray()));
        cl2.setDescriptions(toDescriptions(cl1.getDescriptionArray()));
        cl2.setId(toIDType(cl1.getId().toString()));
        cl2.setUri(toAnyURI(cl1.getUri()));
        cl2.setUrn(toAnyURI(cl1.getUrn()));
        cl2.setCodes(toCodes(cl1.getItemArray()));
        cl2.setParent(toLocalItemReferenceType(cl1.getParent()));
        return cl2;
    }

    public static DataflowsType toDataflows(org.sdmx.resources.sdmxml.schemas.v21.structure.DataflowsType df1) throws TypeValueNotFoundException, URISyntaxException {
        if (df1 == null) {
            return null;
        }
        DataflowsType df2 = new DataflowsType();
        List<DataflowType> dfs = new ArrayList<>();
        for (int i = 0; i < df1.getDataflowArray().length; i++) {
            dfs.add(toDataflow(df1.getDataflowArray(i)));
        }
        df2.setDataflows(dfs);
        return df2;
    }

    public static DataflowType toDataflow(org.sdmx.resources.sdmxml.schemas.v21.structure.DataflowType df1) throws TypeValueNotFoundException, URISyntaxException {
        if (df1 == null) {
            return null;
        }
        DataflowType df2 = new DataflowType();
        df2.setAgencyID(toNestedNCNameIDType(df1.getAgencyID()));
        df2.setAnnotations(toAnnotations(df1.getAnnotations()));
        df2.setDescriptions(toDescriptions(df1.getDescriptionArray()));
        df2.setExternalReference(df1.getIsExternalReference());
        df2.setFinal(df1.getIsFinal());
        df2.setNames(toNames(df1.getNameArray()));
        df2.setId(toIDType(df1.getId().toString()));
        df2.setStructure(toDataStructureReference(df1.getStructure()));
        df2.setVersion(toVersionType(df1.getVersion()));
        df2.setExternalReferences(toExternalReference(df1.getServiceURL(), df1.getStructureURL()));
        return df2;

    }

    public static StructureReferenceBase toStructureReference(org.sdmx.resources.sdmxml.schemas.v21.common.StructureReferenceBaseType srt1) throws TypeValueNotFoundException, URISyntaxException {
        if (srt1.getRef() != null) {
            StructureReferenceBase srt2 = new StructureReferenceBase(toStructureRefType(srt1.getRef()), toAnyURI(srt1.getURN()));
            return srt2;
        } else {
            StructureReferenceBase srt2 = new StructureReferenceBase(new anyURI(srt1.getURN()));
            return srt2;
        }
    }

    public static StructureRefBase toStructureRefType(org.sdmx.resources.sdmxml.schemas.v21.common.RefBaseType ref1) throws TypeValueNotFoundException {
        ItemTypeCodelistType obj = toItemTypeCodelistType(ref1.getClass1());
        StructureRef ref2 = null;
        ref2 = new StructureRef(toNestedNCNameIDType(ref1.getAgencyID()),toIDType(ref1.getId()),toVersionType(ref1.getVersion()),obj, toItemSchemePackageTypeCodelistType(ref1.getPackage()));
        return ref2;

    }

    public static DataStructureReference toDataStructureReference(org.sdmx.resources.sdmxml.schemas.v21.common.StructureReferenceBaseType srt1) throws TypeValueNotFoundException, URISyntaxException {
        if (srt1.getRef() != null) {
            DataStructureReference srt2 = new DataStructureReference(toDataStructureRefType(srt1.getRef()), toAnyURI(srt1.getURN()));
            return srt2;
        } else {
            DataStructureReference srt2 = new DataStructureReference(new anyURI(srt1.getURN()));
            return srt2;
        }
    }

    public static DataStructureRef toDataStructureRefType(org.sdmx.resources.sdmxml.schemas.v21.common.RefBaseType ref1) throws TypeValueNotFoundException {
        DataStructureRef ref2 = null;
        ref2 = new DataStructureRef(toNestedNCNameIDType(ref1.getAgencyID()), toIDType(ref1.getId()), toVersionType(ref1.getVersion()));
        return ref2;
    }

    public static CategorySchemesType toCategorySchemes(org.sdmx.resources.sdmxml.schemas.v21.structure.CategorySchemesType cat1) throws URISyntaxException {
        if (cat1 == null) {
            return null;
        }
        CategorySchemesType cat2 = new CategorySchemesType();
        List<CategorySchemeType> cats = new ArrayList<>();
        for (int i = 0; i < cat1.getCategorySchemeArray().length; i++) {
            cats.add(toCategoryScheme(cat1.getCategorySchemeArray(i)));
        }
        cat2.setCategorySchemes(cats);
        return cat2;
    }

    public static CategorySchemeType toCategoryScheme(org.sdmx.resources.sdmxml.schemas.v21.structure.CategorySchemeType cat1) throws URISyntaxException {
        if (cat1 == null) {
            return null;
        }
        CategorySchemeType cat2 = new CategorySchemeType();
        cat2.setAgencyID(toNestedNCNameIDType(cat1.getAgencyID()));
        cat2.setAnnotations(toAnnotations(cat1.getAnnotations()));
        cat2.setDescriptions(toDescriptions(cat1.getDescriptionArray()));
        cat2.setExternalReference(cat1.getIsExternalReference());
        cat2.setFinal(cat1.getIsFinal());
        cat2.setNames(toNames(cat1.getNameArray()));
        cat2.setId(toIDType(cat1.getId().toString()));
        cat2.setVersion(toVersionType(cat1.getVersion()));
        cat2.setExternalReferences(toExternalReference(cat1.getServiceURL(), cat1.getStructureURL()));
        List<CategoryType> cats = new ArrayList<>();
        for (int i = 0; i < cat1.getCategoryArray().length; i++) {
            cats.add(toCategory(cat1.getCategoryArray(i)));
        }
        cat2.setCategories(cats);
        return cat2;
    }

    public static CategoryType toCategory(org.sdmx.resources.sdmxml.schemas.v21.structure.CategoryType cat1) throws URISyntaxException {
        if (cat1 == null) {
            return null;
        }
        CategoryType cat2 = new CategoryType();
        cat2.setAnnotations(toAnnotations(cat1.getAnnotations()));
        cat2.setDescriptions(toDescriptions(cat1.getDescriptionArray()));
        cat2.setNames(toNames(cat1.getNameArray()));
        cat2.setId(toIDType(cat1.getId().toString()));
        List<CategoryType> cats = new ArrayList<>();
        for (int i = 0; i < cat1.getCategoryArray().length; i++) {
            cats.add(toCategory(cat1.getCategoryArray(i)));
        }
        cat2.setCategories(cats);
        cat2.setUri(toAnyURI(cat1.getUri()));
        cat2.setUrn(toAnyURI(cat1.getUrn()));
        return cat2;
    }

    public static ConceptsType toConcepts(org.sdmx.resources.sdmxml.schemas.v21.structure.ConceptsType con1) throws TypeValueNotFoundException, URISyntaxException {
        if (con1 == null) {
            return null;
        }
        ConceptsType con2 = new ConceptsType();
        List<ConceptSchemeType> cons = new ArrayList<>();
        for (int i = 0; i < con1.getConceptSchemeArray().length; i++) {
            cons.add(toConceptScheme(con1.getConceptSchemeArray(i)));
        }
        con2.setConceptSchemes(cons);
        return con2;
    }

    public static ConceptSchemeType toConceptScheme(org.sdmx.resources.sdmxml.schemas.v21.structure.ConceptSchemeType con1) throws TypeValueNotFoundException, URISyntaxException {
        if (con1 == null) {
            return null;
        }
        ConceptSchemeType con2 = new ConceptSchemeType();
        con2.setAgencyID(toNestedNCNameIDType(con1.getAgencyID()));
        con2.setAnnotations(toAnnotations(con1.getAnnotations()));
        con2.setDescriptions(toDescriptions(con1.getDescriptionArray()));
        con2.setExternalReference(con1.getIsExternalReference());
        con2.setFinal(con1.getIsFinal());
        con2.setNames(toNames(con1.getNameArray()));
        con2.setId(toIDType(con1.getId().toString()));
        con2.setVersion(toVersionType(con1.getVersion()));
        con2.setExternalReferences(toExternalReference(con1.getServiceURL(), con1.getStructureURL()));
        con2.setUri(toAnyURI(con1.getUri()));
        con2.setUrn(toAnyURI(con1.getUrn()));

        List<ConceptType> cons = new ArrayList<>();
        for (int i = 0; i < con1.getConceptArray().length; i++) {
            cons.add(toConceptType(con1.getConceptArray(i)));
        }
        con2.setConcepts(cons);
        Iterator<ConceptType> it = cons.iterator();
        while (it.hasNext()) {
            ConceptType con = it.next();
            if (con.getParent() != null) {
                ConceptType parent;
                parent = con2.findConcept(con.getParent().getId());
                if (parent == null) {
                    throw new RuntimeException("Cannot find parent concept referenced in concept:" + con1.getId() + ":parent:" + con.getParent().getId());
                } else {
                    it.remove();
                    parent.addConcept(con);
                }
            }
        }
        return con2;
    }

    public static positiveInteger toPositiveInteger(int i) {
        return new positiveInteger(i);
    }

    public static TextFormatType toTextFormatType(org.sdmx.resources.sdmxml.schemas.v21.structure.TextFormatType tft1) throws TypeValueNotFoundException {
        if (tft1 == null) {
            return null;
        }
        TextFormatType tft2 = new TextFormatType();
        if (tft1.getDecimals() != null) {
            tft2.setDecimals(toPositiveInteger(tft1.getDecimals().intValue()));
        }
        if (tft1.getEndTime() != null) {
            tft2.setEndTime(toStandardTimePeriod(tft1.getEndTime()));
        }
        if (tft1.getEndValue() != null) {
            tft2.setEndValue(tft1.getEndValue().doubleValue());
        }
        if (tft1.getInterval() != null) {
            tft2.setInterval(tft1.getInterval().doubleValue());
        }
        if (tft1.getMaxLength() != null) {
            tft2.setMaxLength(toPositiveInteger(tft1.getMaxLength().intValue()));
        }
        if (tft1.getMaxValue() != null) {
            tft2.setMaxValue(tft1.getMaxValue().doubleValue());
        }
        if (tft1.getMinValue() != null) {
            tft2.setMinValue(tft1.getMinValue().doubleValue());
        }
        if (tft1.getPattern() != null) {
            tft2.setPattern(tft1.getPattern());
        }
        if (tft1.getStartTime() != null) {
            tft2.setStartTime(toStandardTimePeriod(tft1.getStartTime()));
        }
        if (tft1.getStartValue() != null) {
            tft2.setStartValue(tft1.getStartValue().doubleValue());
        }
        if (tft1.getTextType() != null) {
            tft2.setTextType(DataType.fromStringWithException(tft1.getTextType().toString()));
        }
        if (tft1.getTimeInterval() != null) {
            tft2.setTimeInterval(toDuration(tft1.getTimeInterval()));
        }
        return tft2;
    }

    public static ConceptType toConceptType(org.sdmx.resources.sdmxml.schemas.v21.structure.ConceptType con1) throws TypeValueNotFoundException, URISyntaxException {
        if (con1 == null) {
            return null;
        }
        ConceptType con2 = new ConceptType();
        con2.setAnnotations(toAnnotations(con1.getAnnotations()));
        con2.setDescriptions(toDescriptions(con1.getDescriptionArray()));
        con2.setNames(toNames(con1.getNameArray()));
        con2.setUri(toAnyURI(con1.getUri()));
        con2.setUrn(toAnyURI(con1.getUrn()));
        con2.setId(toNCNameIDType(con1.getId().toString()));
        con2.setParent(toLocalItemReferenceType(con1.getParent()));
        con2.setCoreRepresentation(toConceptRepresentation(con1.getCoreRepresentation()));
        con2.setIsoConceptRef(toISOConceptRef(con1.getISOConceptReference()));
        return con2;
    }

    public static LocalItemReference toLocalItemReferenceType(org.sdmx.resources.sdmxml.schemas.v21.common.LocalItemReferenceType lrt1) throws TypeValueNotFoundException {
        if (lrt1 == null) {
            return null;
        }
        LocalItemReference lrt2 = new LocalItemReference(new LocalItemRefBase(toIDType(lrt1.getRef().getId()), ItemTypeCodelistType.fromStringWithException(lrt1.getRef().getClass1().toString()), ItemSchemePackageTypeCodelistType.fromStringWithException(lrt1.getRef().getPackage().toString())));
        return lrt2;
    }

    public static ConceptRepresentation toConceptRepresentation(org.sdmx.resources.sdmxml.schemas.v21.structure.ConceptRepresentation cor1) throws TypeValueNotFoundException, URISyntaxException {
        if (cor1 == null) {
            return null;
        }
        ConceptRepresentation cor2 = new ConceptRepresentation();
        cor2.setTextFormat(toTextFormatType(cor1.getTextFormat()));
        cor2.setEnumeration(toItemSchemeReference(cor1.getEnumeration()));
        return cor2;
    }

    public static ISOConceptReferenceType toISOConceptRef(org.sdmx.resources.sdmxml.schemas.v21.structure.ISOConceptReferenceType icr1) {
        if (icr1 == null) {
            return null;
        }
        ISOConceptReferenceType icr2 = new ISOConceptReferenceType();
        icr2.setAgencyId(icr1.getConceptAgency());
        icr2.setConceptId(icr1.getConceptID());
        icr2.setConceptSchemeId(icr1.getConceptSchemeID());
        return icr2;
    }

    public static duration toDuration(GDuration timeInterval) {
        System.out.println("DO ME:Sdmx21Tools.toDuration(GDuration)!!!");
        return null;
        //timeInterval.get
    }

    public static StandardTimePeriodType toStandardTimePeriod(Object time) {
        System.out.println("DO ME:Sdmx21Tools.toStandardTimePeriod!!!");
        System.out.println("EndTime=" + time);
        return null;
    }

    public static DataStructuresType toDataStructures(org.sdmx.resources.sdmxml.schemas.v21.structure.DataStructuresType ds1) throws TypeValueNotFoundException, URISyntaxException {
        if( ds1 == null ) return null;
        DataStructuresType ds2 = new DataStructuresType();
        List<DataStructureType> list = new ArrayList<DataStructureType>();
        for (int i = 0; i < ds1.getDataStructureArray().length; i++) {
            list.add(toDataStructure(ds1.getDataStructureArray()[i]));
        }
        ds2.setDataStructures(list);
        return ds2;
    }

    public static DataStructureType toDataStructure(org.sdmx.resources.sdmxml.schemas.v21.structure.DataStructureType ds1) throws TypeValueNotFoundException, URISyntaxException {
        DataStructureType ds2 = new DataStructureType();
        ds2.setAnnotations(toAnnotations(ds1.getAnnotations()));
        ds2.setDescriptions(toDescriptions(ds1.getDescriptionArray()));
        ds2.setNames(toNames(ds1.getNameArray()));
        ds2.setAgencyID(toNestedNCNameIDType(ds1.getAgencyID()));
        ds2.setId(toIDType(ds1.getId().toString()));
        ds2.setVersion(toVersionType(ds1.getVersion()));
        ds2.setDataStructureComponents(toDataStructureComponents(ds1.getDataStructureComponents()));
        ds2.setUri(toAnyURI(ds1.getUri()));
        ds2.setUrn(toAnyURI(ds1.getUrn()));
        return ds2;
    }

    public static DataStructureComponents toDataStructureComponents(DataStructureComponentsType ds1) throws TypeValueNotFoundException, URISyntaxException {
        if (ds1 == null) {
            return null;
        }
        DataStructureComponents ds2 = new DataStructureComponents();
        DimensionListType dimList = new DimensionListType();
        List<DimensionType> dims = new ArrayList<DimensionType>();
        for (int i = 0; i < ds1.getDimensionList().sizeOfDimensionArray(); i++) {
            dims.add(toDimensionType(ds1.getDimensionList().getDimensionArray()[i]));
        }
        dimList.setDimensions(dims);
        List<MeasureDimensionType> meas = new ArrayList<MeasureDimensionType>();
        for (int i = 0; i < ds1.getDimensionList().sizeOfMeasureDimensionArray(); i++) {
            dimList.setMeasureDimension(toMeasureDimension(ds1.getDimensionList().getMeasureDimensionArray()[i]));
        }
        MeasureListType measList = new MeasureListType();
        measList.setPrimaryMeasure(toPrimaryMeasure(ds1.getMeasureList().getPrimaryMeasure()));
        AttributeListType atl = new AttributeListType();
        List<AttributeType> atts = new ArrayList<AttributeType>();
        for (int i = 0; ds1.getAttributeList()!=null&&i < ds1.getAttributeList().getAttributeArray().length; i++) {
            atts.add(toAttribute(ds1.getAttributeList().getAttributeArray(i)));
        }
        if( ds1.getDimensionList().getTimeDimensionArray().length>0) {
            TimeDimensionType time = toTimeDimension(ds1.getDimensionList().getTimeDimensionArray()[0]);
            dimList.setTimeDimension(time);
        }
        atl.setAttributes(atts);
        ds2.setDimensionList(dimList);
        ds2.setAttributeList(atl);
        ds2.setMeasureList(measList);
        return ds2;
    }

    public static DimensionType toDimensionType(org.sdmx.resources.sdmxml.schemas.v21.structure.DimensionType ds1) throws TypeValueNotFoundException, URISyntaxException {
        DimensionType ds2 = new DimensionType();
        ds2.setId(toIDType(ds1.getId()));
        ds2.setAnnotations(toAnnotations(ds1.getAnnotations()));
        ds2.setConceptIdentity(toConceptReference(ds1.getConceptIdentity()));
        ds2.setPosition(ds1.getPosition());
        ds2.setType(DimensionTypeType.fromStringWithException(ds1.getType().toString()));
        ds2.setLocalRepresentation(toLocalRepresentation(ds1.getLocalRepresentation()));
        ds2.setUri(toAnyURI((ds1.getUri())));
        ds2.setUrn(toAnyURI((ds1.getUrn())));
        return ds2;
    }

    public static ConceptReference toConceptReference(org.sdmx.resources.sdmxml.schemas.v21.common.ConceptReferenceType srt1) throws TypeValueNotFoundException, URISyntaxException {
        if (srt1 == null) {
            return null;
        }
        if (srt1.getRef() != null) {
            ConceptReference srt2 = new ConceptReference(toConceptRefType(srt1.getRef()), toAnyURI(srt1.getURN()));
            return srt2;
        } else {
            ConceptReference srt2 = new ConceptReference(new anyURI(srt1.getURN()));
            return srt2;
        }
    }

    public static ConceptRef toConceptRefType(org.sdmx.resources.sdmxml.schemas.v21.common.RefBaseType ref1) {
        ConceptRef ref2 = new ConceptRef(toNestedNCNameIDType(ref1.getAgencyID()),toIDType(ref1.getMaintainableParentID()), toVersionType(ref1.getMaintainableParentVersion()), toIDType(ref1.getId()));
        // This field is not declared on ConceptRefType in the spec!!!
        return ref2;
    }

    public static RepresentationType toLocalRepresentation(org.sdmx.resources.sdmxml.schemas.v21.structure.RepresentationType lr1) throws TypeValueNotFoundException, URISyntaxException {
        if (lr1 == null) {
            return null;
        }
        if( lr1.getTextFormat()==null&&lr1.getEnumeration()==null) {
            return null;
        }
        RepresentationType lr2 = new RepresentationType();
        lr2.setTextFormat(toTextFormatType(lr1.getTextFormat()));
        lr2.setEnumeration(toItemSchemeReference(lr1.getEnumeration()));
        return lr2;
    }

    public static ReportPeriodRepresentationType toLocalReportPeriodRepresentation(org.sdmx.resources.sdmxml.schemas.v21.structure.RepresentationType lr1) throws TypeValueNotFoundException, URISyntaxException {
        if (lr1 == null) {
            return null;
        }
        ReportPeriodRepresentationType lr2 = new ReportPeriodRepresentationType();
        lr2.setTextFormat(toTextFormatType(lr1.getTextFormat()));
        lr2.setEnumeration(toItemSchemeReference(lr1.getEnumeration()));
        return lr2;
    }

    public static SimpleDataStructureRepresentationType toSimpleLocalRepresentatation(org.sdmx.resources.sdmxml.schemas.v21.structure.RepresentationType lr1) throws TypeValueNotFoundException, URISyntaxException {
        if (lr1 == null) {
            return null;
        }
        SimpleDataStructureRepresentationType lr2 = new SimpleDataStructureRepresentationType();
        lr2.setTextFormat(toTextFormatType(lr1.getTextFormat()));
        lr2.setEnumeration(toItemSchemeReference(lr1.getEnumeration()));
        return lr2;
    }

    public static ItemSchemeReferenceBase toItemSchemeReference(org.sdmx.resources.sdmxml.schemas.v21.common.ItemSchemeReferenceBaseType srt1) throws URISyntaxException, TypeValueNotFoundException {
        if (srt1 == null) {
            return null;
        }
        if (srt1.getRef() != null) {
            ItemSchemeReferenceBase srt2 = new ItemSchemeReferenceBase(toItemSchemeRefBaseType(srt1.getRef()), toAnyURI(srt1.getURN()));
            return srt2;
        } else {
            ItemSchemeReferenceBase srt2 = new ItemSchemeReferenceBase(new anyURI(srt1.getURN()));
            return srt2;
        }
    }

    public static ItemSchemeRefBase toItemSchemeRefBaseType(org.sdmx.resources.sdmxml.schemas.v21.common.RefBaseType ref1) throws TypeValueNotFoundException {
        ItemSchemeRefBase ref2;
        ref2 = new ItemSchemeRefBase(toNestedNCNameIDType(ref1.getAgencyID()), toIDType(ref1.getId()), toVersionType(ref1.getVersion()), toItemSchemeTypeCodelistType(ref1.getClass1()), toItemSchemePackageTypeCodelistType(ref1.getPackage()));
        return ref2;
    }

    public static ItemSchemeTypeCodelistType toItemSchemeTypeCodelistType(Enum class1) throws TypeValueNotFoundException {
        return ItemSchemeTypeCodelistType.fromStringWithException(class1.toString());
    }

    public static MeasureDimensionType toMeasureDimension(org.sdmx.resources.sdmxml.schemas.v21.structure.MeasureDimensionType ds1) throws TypeValueNotFoundException, URISyntaxException {
        MeasureDimensionType ds2 = new MeasureDimensionType();
        ds2.setId(toIDType(ds1.getId()));
        ds2.setAnnotations(toAnnotations(ds1.getAnnotations()));
        ds2.setConceptIdentity(toConceptReference(ds1.getConceptIdentity()));
        ds2.setPosition(ds1.getPosition());
        ds2.setType(DimensionTypeType.fromStringWithException(ds1.getType().toString()));
        ds2.setLocalRepresentation(toLocalRepresentation(ds1.getLocalRepresentation()));
        ds2.setUri(toAnyURI((ds1.getUri())));
        ds2.setUrn(toAnyURI((ds1.getUrn())));
        return ds2;
    }

    public static AttributeType toAttribute(org.sdmx.resources.sdmxml.schemas.v21.structure.AttributeType at1) throws TypeValueNotFoundException, URISyntaxException {
        AttributeType at2 = new AttributeType();
        at2.setId(toIDType(at1.getId()));
        at2.setAnnotations(toAnnotations(at1.getAnnotations()));
        at2.setConceptIdentity(toConceptReference(at1.getConceptIdentity()));
        at2.setLocalRepresentation(toSimpleLocalRepresentatation(at1.getLocalRepresentation()));
        at2.setUri(toAnyURI((at1.getUri())));
        at2.setUrn(toAnyURI((at1.getUrn())));
        at2.setRelationshipType(toAttributeRelationShipType(at1.getAttributeRelationship()));
        if( at1.getAssignmentStatus()!=null)at2.setAssignmentStatus(sdmx.structure.datastructure.UsageStatusType.fromStringWithException(at1.getAssignmentStatus().toString()));
        return at2;
    }

    public static AttributeRelationshipType toAttributeRelationShipType(org.sdmx.resources.sdmxml.schemas.v21.structure.AttributeRelationshipType at1) throws URISyntaxException, TypeValueNotFoundException {
        if (at1 == null) {
            return null;
        }
        AttributeRelationshipType at2 = new AttributeRelationshipType();
        if (at1.getNone() != null) {
            at2.setEmpty(true);
        } else if (at1.getDimensionArray().length > 0) {
            List<LocalDimensionReference> dims = new ArrayList<LocalDimensionReference>();
            for (int i = 0; i < at1.getDimensionArray().length; i++) {
                dims.add(toLocalDimensionReference(at1.getDimensionArray()[i]));
                toLocalDimensionReference(at1.getDimensionArray(i));
            }
            at2.setDimensions(dims);
            at2.setAttachGroup(toAttachGroup(at1.getGroup()));
        } else if (at1.getAttachmentGroupArray().length > 0) {
            List<LocalGroupKeyDescriptorReference> groups = new ArrayList<LocalGroupKeyDescriptorReference>();
            for (int i = 0; i < at1.getAttachmentGroupArray().length; i++) {
                groups.add(toAttachGroup(at1.getAttachmentGroupArray(i)));
            }
            at2.setGroups(groups);
        } else if (at1.getPrimaryMeasure() != null) {
            at2.setPrimaryMeasure(toLocalPrimaryMeasureType(at1.getPrimaryMeasure()));
        } else {
            System.out.println("Unknown Attribute Relationship Type!!!" + at1.toString());
            return null;
        }
        return at2;
    }

    public static DimensionReference toDimensionReference(org.sdmx.resources.sdmxml.schemas.v21.common.DimensionReferenceType drt1) throws URISyntaxException, TypeValueNotFoundException {
        if (drt1.getRef() != null) {
            DimensionReference drt2 = new DimensionReference(toDimensionRefType(drt1.getRef()), toAnyURI(drt1.getURN()));
            return drt2;
        } else {
            DimensionReference drt2 = new DimensionReference(new anyURI(drt1.getURN()));
            return drt2;
        }
    }

    public static DimensionRef toDimensionRefType(org.sdmx.resources.sdmxml.schemas.v21.common.RefBaseType ref1) {
        DimensionRef ref2 = new DimensionRef(toIDType(ref1.getId()));
        return ref2;
    }

    public static LocalDimensionReference toLocalDimensionReference(org.sdmx.resources.sdmxml.schemas.v21.common.LocalDimensionReferenceType drt1) throws URISyntaxException, TypeValueNotFoundException {
        LocalDimensionReference drt2 = new LocalDimensionReference(toLocalDimensionRefType(drt1.getRef()));
        return drt2;
    }

    public static LocalGroupKeyDescriptorReference toAttachGroup(org.sdmx.resources.sdmxml.schemas.v21.common.LocalGroupKeyDescriptorReferenceType lgk1) {
        if (lgk1 == null) {
            return null;
        }
        LocalGroupKeyDescriptorReference lgk2 = new LocalGroupKeyDescriptorReference(toLocalGroupKeyDescriptorRefType(lgk1.getRef()));
        return lgk2;
    }

    public static LocalGroupKeyDescriptorRef toLocalGroupKeyDescriptorRefType(org.sdmx.resources.sdmxml.schemas.v21.common.RefBaseType ref) {
        LocalGroupKeyDescriptorRef lgk2 = new LocalGroupKeyDescriptorRef(toIDType(ref.getId()));
        return lgk2;
    }

    public static LocalPrimaryMeasureReference toLocalPrimaryMeasureType(org.sdmx.resources.sdmxml.schemas.v21.common.LocalPrimaryMeasureReferenceType lpmr1) {
        LocalPrimaryMeasureReference lpmr2 = new LocalPrimaryMeasureReference(toLocalPrimaryMeasureRefType(lpmr1.getRef()));
        return lpmr2;
    }

    public static LocalPrimaryMeasureRef toLocalPrimaryMeasureRefType(org.sdmx.resources.sdmxml.schemas.v21.common.RefBaseType ref) {
        LocalPrimaryMeasureRef lpmrt = new LocalPrimaryMeasureRef(toIDType(ref.getId()));
        return lpmrt;
    }

    public static LocalDimensionRef toLocalDimensionRefType(org.sdmx.resources.sdmxml.schemas.v21.common.RefBaseType ref) {
        LocalDimensionRef ref2 = new LocalDimensionRef(toIDType(ref.getId().toString()));
        return ref2;

    }

    public static PrimaryMeasure toPrimaryMeasure(PrimaryMeasureType pm1) throws TypeValueNotFoundException, URISyntaxException {
        PrimaryMeasure pm2 = new PrimaryMeasure();
        pm2.setId(toIDType(pm1.getId()));
        pm2.setAnnotations(toAnnotations(pm1.getAnnotations()));
        pm2.setConceptIdentity(toConceptReference(pm1.getConceptIdentity()));
        pm2.setLocalRepresentation(toLocalRepresentation(pm1.getLocalRepresentation()));
        pm2.setUri(toAnyURI((pm1.getUri())));
        pm2.setUrn(toAnyURI((pm1.getUrn())));
        return pm2;
    }

    public static ConstraintsType toConstraints(org.sdmx.resources.sdmxml.schemas.v21.structure.ConstraintsType ct1) throws URISyntaxException, TypeValueNotFoundException {
        if (ct1 == null) {
            return null;
        }
        ConstraintsType ct2 = new ConstraintsType();
        List<AttachmentConstraintType> attaches = new ArrayList<AttachmentConstraintType>();
        for (int i = 0; i < ct1.getAttachmentConstraintArray().length; i++) {
            attaches.add(toAttachmentConstraint(ct1.getAttachmentConstraintArray(i)));
        }
        ct2.setAttachmentConstraints(attaches);
        List<ContentConstraintType> contents = new ArrayList<ContentConstraintType>();
        for (int i = 0; i < ct1.getContentConstraintArray().length; i++) {
        }
        ct2.setContentConstraints(contents);
        return ct2;
    }

    public static AttachmentConstraintType toAttachmentConstraint(org.sdmx.resources.sdmxml.schemas.v21.structure.AttachmentConstraintType at1) throws URISyntaxException, TypeValueNotFoundException {
        AttachmentConstraintType at2 = new AttachmentConstraintType();
        at2.setAnnotations(toAnnotations(at1.getAnnotations()));
        at2.setNames(toNames(at1.getNameArray()));
        at2.setDescriptions(toDescriptions(at1.getDescriptionArray()));
        at2.setConstraintAttachment(toAttachmentConstraint(at1.getConstraintAttachment()));
        return at2;
    }

    public static AttachmentConstraintAttachmentType toAttachmentConstraint(org.sdmx.resources.sdmxml.schemas.v21.structure.ConstraintAttachmentType act1) throws URISyntaxException, TypeValueNotFoundException {
        AttachmentConstraintAttachmentType act2 = new AttachmentConstraintAttachmentType();
        if (act1.getDataProvider() != null) {
            act2.setDataProvider(toDataProviderReferenceType(act1.getDataProvider()));
            return act2;
        } else if (act1.getDataSetArray().length > 0) {
            List<SetReference> dataSets = new ArrayList<SetReference>();
            for (int i = 0; i < act1.getDataSetArray().length; i++) {
                dataSets.add(toSetReferenceType(act1.getDataSetArray(i)));
            }
            act2.setDataSets(dataSets);
            return act2;
        } else if (act1.getMetadataSetArray().length > 0) {
            List<SetReference> mdataSets = new ArrayList<SetReference>();
            for (int i = 0; i < act1.getMetadataSetArray().length; i++) {
                mdataSets.add(toSetReferenceType(act1.getMetadataSetArray(i)));
            }
            act2.setMetadataSets(mdataSets);
            return act2;
        } else if (act1.getSimpleDataSourceArray().length > 0) {
            List<anyURI> uris = new ArrayList<anyURI>();
            for (int i = 0; i < act1.getSimpleDataSourceArray().length; i++) {
                uris.add(toAnyURI(act1.getSimpleDataSourceArray(i)));
            }
            act2.setSimpleDataSources(uris);
            return act2;
        } else if (act1.getDataStructureArray().length > 0) {
            List<DataStructureReference> dsrs = new ArrayList<DataStructureReference>();
            for (int i = 0; i < act1.getDataStructureArray().length; i++) {
                dsrs.add(toDataStructureReference(act1.getDataStructureArray(i)));
            }
            act2.setDataStructures(dsrs);
            return act2;
        } else if (act1.getDataflowArray().length > 0) {
            List<DataflowReference> dfs = new ArrayList<DataflowReference>();
            for (int i = 0; i < act1.getDataflowArray().length; i++) {
                dfs.add(toDataflowReference(act1.getDataflowArray(i)));
            }
            act2.setDataflows(dfs);
            return act2;
        } else if (act1.getMetadataflowArray().length > 0) {
            List<MetadataStructureReference> mfs = new ArrayList<MetadataStructureReference>();
            for (int i = 0; i < act1.getMetadataflowArray().length; i++) {
                mfs.add(toMetadataStructureReferenceType(act1.getMetadataStructureArray(i)));
            }

        } else if (act1.getProvisionAgreementArray().length > 0) {
        }
        return act2;
    }

    public static SetReference toSetReferenceType(org.sdmx.resources.sdmxml.schemas.v21.common.SetReferenceType srt1) throws URISyntaxException {
        if (srt1 == null) {
            return null;
        }
        SetReference srt2 = new SetReference(toDataProviderReferenceType(srt1.getDataProvider()), toIDType(srt1.getID()));
        return srt2;
    }

    public static DataProviderReference toDataProviderReferenceType(org.sdmx.resources.sdmxml.schemas.v21.common.DataProviderReferenceType dp1) throws URISyntaxException {
        if (dp1 == null) {
            return null;
        }
        if (dp1.getRef() != null) {
            DataProviderReference dp2 = new DataProviderReference(toDataProviderReferenceType(dp1.getRef()), toAnyURI(dp1.getURN()));
            return dp2;
        } else {
            DataProviderReference dp2 = new DataProviderReference(toAnyURI(dp1.getURN()));
            return dp2;
        }

    }

    public static DataProviderRef toDataProviderReferenceType(org.sdmx.resources.sdmxml.schemas.v21.common.RefBaseType ref) {
        if (ref == null) {
            return null;
        }
        DataProviderRef dp2 = new DataProviderRef(toIDType(ref.getId()));
        return dp2;
    }

    public static DataflowReference toDataflowReference(org.sdmx.resources.sdmxml.schemas.v21.common.DataflowReferenceType dft1) throws URISyntaxException {
        if (dft1 == null) {
            return null;
        }
        if (dft1.getRef() != null) {
            DataflowReference dft2;
            dft2 = new DataflowReference(toDataflowRef(dft1.getRef()), toAnyURI(dft1.getURN()));
            return dft2;
        } else {
            DataflowReference dft2 = new DataflowReference(toAnyURI(dft1.getURN()));
            return dft2;
        }
    }

    public static MetadataStructureReference toMetadataStructureReferenceType(org.sdmx.resources.sdmxml.schemas.v21.common.MetadataStructureReferenceType msr1) throws URISyntaxException {
        if (msr1 == null) {
            return null;
        }
        if (msr1.getRef() != null) {
            MetadataStructureReference msr2 = new MetadataStructureReference(toMetadataStructureRef(msr1.getRef()), toAnyURI(msr1.getURN()));
            return msr2;
        } else {
            MetadataStructureReference msr2 = new MetadataStructureReference(toMetadataStructureRef(msr1.getRef()), toAnyURI(msr1.getURN()));
            return msr2;
        }
    }

    public static MetadataStructureRef toMetadataStructureRef(org.sdmx.resources.sdmxml.schemas.v21.common.RefBaseType msr1) {
        if (msr1 == null) {
            return null;
        }
        MetadataStructureRef msr2 = new MetadataStructureRef(toIDType(msr1.getId()));
        return msr2;
    }

    public static DataflowRef toDataflowRef(org.sdmx.resources.sdmxml.schemas.v21.common.RefBaseType dft1) {
        DataflowRef dft2 = new DataflowRef(toNestedNCNameIDType(dft1.getAgencyID()),toIDType(dft1.getId()),toVersionType(dft1.getVersion()));
        return dft2;
    }

    public static MetadataStructuresType toMetadataStructures(org.sdmx.resources.sdmxml.schemas.v21.structure.MetadataStructuresType mst1) throws TypeValueNotFoundException, URISyntaxException {
        if (mst1 == null) {
            return null;
        }
        MetadataStructuresType mst2 = new MetadataStructuresType();
        List<MetadataStructureType> strucs = new ArrayList<MetadataStructureType>();
        for (int i = 0; i < mst1.getMetadataStructureArray().length; i++) {
            strucs.add(toMetadataStructure(mst1.getMetadataStructureArray(i)));
        }
        mst2.setMetadataStructures(strucs);
        return mst2;
    }

    public static MetadataStructureType toMetadataStructure(org.sdmx.resources.sdmxml.schemas.v21.structure.MetadataStructureType mst1) throws TypeValueNotFoundException, URISyntaxException {
        if (mst1 == null) {
            return null;
        }
        MetadataStructureType mst2 = new MetadataStructureType();
        mst2.setNames(toNames(mst1.getNameArray()));
        mst2.setDescriptions(toDescriptions(mst1.getDescriptionArray()));
        mst2.setAgencyID(toNestedNCNameIDType(mst1.getAgencyID()));
        mst2.setId(toIDType(mst1.getId()));
        mst2.setAnnotations(toAnnotations(mst1.getAnnotations()));
        mst2.setDescriptions(toDescriptions(mst1.getDescriptionArray()));
        mst2.setFinal(mst1.getIsFinal());
        mst2.setExternalReference(mst1.getIsExternalReference());
        mst2.setValidFrom(toDateTime(mst1.getValidFrom()));
        mst2.setValidTo(toDateTime(mst1.getValidTo()));
        List<MetadataTargetType> targets = new ArrayList<MetadataTargetType>();
        for (int i = 0; i < mst1.getMetadataStructureComponents().getMetadataTargetArray().length; i++) {
            targets.add(toMetadataTarget(mst1.getMetadataStructureComponents().getMetadataTargetArray((i))));
        }
        mst2.setTargets(targets);
        List<ReportStructure> reports = new ArrayList<ReportStructure>();
        for (int i = 0; i < mst1.getMetadataStructureComponents().getReportStructureArray().length; i++) {
            reports.add(toReportStructure(mst1.getMetadataStructureComponents().getReportStructureArray(i)));
        }
        mst2.setReports(reports);
        return mst2;
    }

    public static MetadataTargetType toMetadataTarget(org.sdmx.resources.sdmxml.schemas.v21.structure.MetadataTargetType t1) throws TypeValueNotFoundException, URISyntaxException {
        MetadataTargetType t2 = new MetadataTargetType(toAnnotations(t1.getAnnotations()), toIDType(t1.getId()));

        List<KeyDescriptorValuesTarget> kd = new ArrayList<KeyDescriptorValuesTarget>();
        for (int i = 0; i < t1.getKeyDescriptorValuesTargetArray().length; i++) {
            kd.add(toKeyDescriptorValuesTarget(t1.getKeyDescriptorValuesTargetArray(i)));
        }
        t2.setKeyDescriptor(kd);

        List<DataSetTarget> dst = new ArrayList<DataSetTarget>();
        for (int i = 0; i < t1.getDataSetTargetArray().length; i++) {
            dst.add(toDataSetTarget(t1.getDataSetTargetArray(i)));
        }
        t2.setDataSetTarget(dst);

        List<IdentifiableObjectTarget> idobs = new ArrayList<IdentifiableObjectTarget>();
        for (int i = 0; i < t1.getIdentifiableObjectTargetArray().length; i++) {
            idobs.add(toIdentifiableObjectTarget(t1.getIdentifiableObjectTargetArray(i)));
        }
        t2.setIdentifiableObjectTarget(idobs);

        List<ConstraintContentTarget> constraints = new ArrayList<ConstraintContentTarget>();
        for (int i = 0; i < t1.getConstraintContentTargetArray().length; i++) {
            constraints.add(toConstraintContentTarget(t1.getConstraintContentTargetArray(i)));
        }
        t2.setConstraintContentTarget(constraints);

        List<ReportPeriodTarget> reps = new ArrayList<ReportPeriodTarget>();
        for (int i = 0; i < t1.getReportPeriodTargetArray().length; i++) {
            reps.add(toReportPeriodTarget(t1.getReportPeriodTargetArray(i)));
        }
        t2.setReportPeriodTarget(reps);

        return t2;
    }

    public static KeyDescriptorValuesTarget toKeyDescriptorValuesTarget(KeyDescriptorValuesTargetType kd1) throws TypeValueNotFoundException, URISyntaxException {
        KeyDescriptorValuesTarget kd2 = new KeyDescriptorValuesTarget();
        kd2.setId(toIDType(kd1.getId()));
        kd2.setConceptIdentity(toConceptReference(kd1.getConceptIdentity()));
        kd2.setLocalRepresentation(toLocalRepresentation(kd1.getLocalRepresentation()));
        return kd2;
    }

    public static DataSetTarget toDataSetTarget(org.sdmx.resources.sdmxml.schemas.v21.structure.DataSetTargetType dst1) throws TypeValueNotFoundException, URISyntaxException {
        DataSetTarget dst2 = new DataSetTarget();
        dst2.setId(toIDType(dst1.getId()));
        dst2.setConceptIdentity(toConceptReference(dst1.getConceptIdentity()));
        dst2.setLocalRepresentation(toLocalRepresentation(dst1.getLocalRepresentation()));
        return dst2;
    }

    public static IdentifiableObjectTarget toIdentifiableObjectTarget(IdentifiableObjectTargetType iob1) throws TypeValueNotFoundException, URISyntaxException {
        IdentifiableObjectTarget iob2 = new IdentifiableObjectTarget();
        iob2.setId(toIDType(iob1.getId()));
        iob2.setConceptIdentity(toConceptReference(iob1.getConceptIdentity()));
        iob2.setLocalRepresentation(toLocalRepresentation(iob1.getLocalRepresentation()));
        iob2.setObjectType(ObjectTypeCodelistType.fromStringWithException(iob1.getObjectType().toString()));
        return iob2;
    }

    public static ConstraintContentTarget toConstraintContentTarget(ConstraintContentTargetType cct1) throws TypeValueNotFoundException, URISyntaxException {
        ConstraintContentTarget cct2 = new ConstraintContentTarget();
        cct2.setConceptIdentity(toConceptReference(cct1.getConceptIdentity()));
        cct2.setLocalRepresentation(toLocalRepresentation(cct1.getLocalRepresentation()));
        return cct2;
    }

    public static ReportPeriodTarget toReportPeriodTarget(ReportPeriodTargetType rpt1) throws TypeValueNotFoundException, URISyntaxException {
        ReportPeriodTarget rpt2 = new ReportPeriodTarget();
        rpt2.setConceptIdentity(toConceptReference(rpt1.getConceptIdentity()));
        rpt2.setLocalRepresentation(toLocalReportPeriodRepresentation(rpt1.getLocalRepresentation()));
        return rpt2;
    }

    public static ReportStructure toReportStructure(ReportStructureType rs1) throws TypeValueNotFoundException, URISyntaxException {
        ReportStructure rs2 = new ReportStructure();
        rs2.setId(toIDType(rs1.getId()));
        List<MetadataAttribute> attributes = new ArrayList<MetadataAttribute>();
        for (int i = 0; i < rs1.getMetadataAttributeArray().length; i++) {
            attributes.add(toMetadataAttribute(rs1.getMetadataAttributeArray(i)));
        }
        rs2.setMetadataAttributes(attributes);
        List<LocalMetadataTargetReference> targets = new ArrayList<LocalMetadataTargetReference>();
        for (int i = 0; i < rs1.getMetadataTargetArray().length; i++) {
            targets.add(toLocalMetadataTargetReferenceType(rs1.getMetadataTargetArray(i)));
        }
        rs2.setLocalMetadataTarget(targets);
        return rs2;
    }

    public static MetadataAttribute toMetadataAttribute(MetadataAttributeType ma1) throws TypeValueNotFoundException, URISyntaxException {
        MetadataAttribute ma2 = new MetadataAttribute();
        ma2.setId(toIDType(ma1.getId()));
        ma2.setAnnotations(toAnnotations(ma1.getAnnotations()));
        ma2.setConceptIdentity(toConceptReference(ma1.getConceptIdentity()));
        ma2.setLocalRepresentation(toLocalRepresentation(ma1.getLocalRepresentation()));
        ma2.setPresentational(ma1.getIsPresentational());
        ma2.setMaxOccurs(OccurenceType.fromString(ma1.getMaxOccurs().toString()));
        List<MetadataAttribute> attrs = new ArrayList<MetadataAttribute>();
        for (int i = 0; i < ma1.getMetadataAttributeArray().length; i++) {
            attrs.add(toMetadataAttribute(ma1.getMetadataAttributeArray(i)));
        }
        ma2.setMetadataAttributes(attrs);
        return ma2;
    }

    public static LocalMetadataTargetReference toLocalMetadataTargetReferenceType(org.sdmx.resources.sdmxml.schemas.v21.common.LocalMetadataTargetReferenceType mt1) {
        LocalMetadataTargetReference mt2 = new LocalMetadataTargetReference(toLocalMetadataRef(mt1.getRef()));
        return mt2;

    }

    public static LocalMetadataTargetRef toLocalMetadataRef(org.sdmx.resources.sdmxml.schemas.v21.common.RefBaseType ref) {
        LocalMetadataTargetRef ref2 = new LocalMetadataTargetRef(toIDType(ref.getId()));
        return ref2;
    }

    public static List<PartyType> toPartyTypeList(org.sdmx.resources.sdmxml.schemas.v21.message.PartyType[] receiverArray) throws URISyntaxException {
        List<PartyType> list = new ArrayList<PartyType>();
        for (int i = 0; i < receiverArray.length; i++) {
            PartyType pt = new PartyType();
            pt.setId(toIDType(receiverArray[i].getId()));
            pt.setContacts(toContactList(receiverArray[i].getContactArray()));
            pt.setNames(toNames(receiverArray[i].getNameArray()));
            list.add(pt);
        }
        return list;
    }

    public static List<ContactType> toContactList(org.sdmx.resources.sdmxml.schemas.v21.message.ContactType[] contactArray) throws URISyntaxException {
        List<ContactType> list = new ArrayList<ContactType>();
        for (int i = 0; i < contactArray.length; i++) {
            ContactType ct = new ContactType();
            ct.setNames(toNames(contactArray[i].getNameArray()));
            ct.setDepartments(toTextType(contactArray[i].getDepartmentArray()));
            ct.setEmails(toStringList(contactArray[i].getEmailArray()));
            ct.setFaxes(toStringList(contactArray[i].getFaxArray()));
            ct.setRoles(toTextType(contactArray[i].getRoleArray()));
            ct.setTelephones(toStringList(contactArray[i].getTelephoneArray()));
            ct.setX400s(toStringList(contactArray[i].getX400Array()));
            ct.setUris(toAnyURIList(contactArray[i].getURIArray()));
        }
        return list;
    }

    public static List<String> toStringList(String[] emailArray) {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < emailArray.length; i++) {
            list.add(emailArray[i]);
        }
        return list;
    }

    public static List<anyURI> toAnyURIList(String[] uriArray) throws URISyntaxException {
        List<anyURI> list = new ArrayList<anyURI>();
        for (int i = 0; i < uriArray.length; i++) {
            list.add(toAnyURI(uriArray[i]));
        }
        return list;
    }

    public static ObservationalTimePeriodType toObservationalTimePeriodType(Object reportingEnd) {
        return null;
    }

    public static SenderType toSenderType(org.sdmx.resources.sdmxml.schemas.v21.message.SenderType sender) throws URISyntaxException {
        if( sender == null ) return null;
        SenderType st = new SenderType();
        st.setContacts(toContactList(sender.getContactArray()));
        st.setId(toIDType(sender.getId()));
        st.setNames(toNames(sender.getNameArray()));
        st.setTimezone(toTimezoneType(sender.getTimezone()));
        return st;
    }

    public static TimezoneType toTimezoneType(String timezone) {
        if (timezone == null) {
            return null;
        }
        return new TimezoneType(timezone);
    }

    public static List<PayloadStructureType> toPayloadStructureList(org.sdmx.resources.sdmxml.schemas.v21.common.PayloadStructureType[] structureArray) throws URISyntaxException, TypeValueNotFoundException {
        List<PayloadStructureType> list = new ArrayList<PayloadStructureType>();
        for (int i = 0; i < structureArray.length; i++) {
            PayloadStructureType pst = new PayloadStructureType();
            pst.setDimensionAtObservation(toObservationDimensionType(structureArray[i].getDimensionAtObservation()));
            pst.setExplicitMeasures(structureArray[i].getExplicitMeasures());
            pst.setNamespace(toAnyURI(structureArray[i].getNamespace()));
            pst.setSchemaURL(toAnyURI(structureArray[i].getSchemaURL()));
            pst.setServiceURL(toAnyURI(structureArray[i].getServiceURL()));
            pst.setStructureURL(toAnyURI(structureArray[i].getStructureURL()));
            pst.setProvisionAgreement(toProvisionAgreement(structureArray[i].getProvisionAgrement()));
            pst.setStructureID(toID(structureArray[i].getStructureID()));
            pst.setStructure(toStructureReference(structureArray[i].getStructure()));
            pst.setStructureUsage(toStructureUsage(structureArray[i].getStructureUsage()));
            list.add(pst);
        }
        return list;
    }

    public static ObservationDimensionType toObservationDimensionType(String dimensionAtObservation) {
        if (dimensionAtObservation == null) {
            return null;
        }
        return new ObservationDimensionType(dimensionAtObservation);
    }

    public static ProvisionAgreementReference toProvisionAgreement(org.sdmx.resources.sdmxml.schemas.v21.common.ProvisionAgreementReferenceType provisionAgrement) throws URISyntaxException {
        if (provisionAgrement == null) {
            return null;
        }
        if (provisionAgrement.getRef() != null) {
            ProvisionAgreementReference part = new ProvisionAgreementReference(toProvisionAgreementRefType(provisionAgrement.getRef()), toAnyURI(provisionAgrement.getURN()));
            return part;
        } else {
            ProvisionAgreementReference part = new ProvisionAgreementReference(toAnyURI(provisionAgrement.getURN()));
            return part;
        }

    }

    public static ProvisionAgreementRef toProvisionAgreementRefType(org.sdmx.resources.sdmxml.schemas.v21.common.RefBaseType ref) {
        if (ref == null) {
            return null;
        }
        ProvisionAgreementRef pref = new ProvisionAgreementRef();
        return pref;
    }

    public static StructureUsageReferenceBase toStructureUsage(org.sdmx.resources.sdmxml.schemas.v21.common.StructureUsageReferenceBaseType structureUsage) throws URISyntaxException {
        if (structureUsage == null) {
            return null;
        }
        if (structureUsage.getRef() != null) {
            StructureUsageReferenceBase part = new StructureUsageReferenceBase(toStructureUsageRefType(structureUsage.getRef()), toAnyURI(structureUsage.getURN()));
            return part;
        } else {
            StructureUsageReferenceBase part = new StructureUsageReferenceBase(toAnyURI(structureUsage.getURN()));
            return part;
        }
    }

    public static StructureUsageRefBase toStructureUsageRefType(org.sdmx.resources.sdmxml.schemas.v21.common.RefBaseType ref) {
        if (ref == null) {
            return null;
        }
        StructureUsageRefBase ref2 = new StructureUsageRefBase(toNestedNCNameIDType(ref.getAgencyID()), toIDType(ref.getId()), toVersionType(ref.getVersion()), ObjectTypeCodelistType.fromString(ref.getClass1().toString()), PackageTypeCodelistType.fromString(ref.getPackage().toString()));
        return ref2;
    }

    public static TimeDimensionType toTimeDimension(org.sdmx.resources.sdmxml.schemas.v21.structure.TimeDimensionType time1) throws TypeValueNotFoundException, URISyntaxException {
        TimeDimensionType time2 = new TimeDimensionType();
        time2.setId(toIDType(time1.getId()));
        time2.setAnnotations(toAnnotations(time1.getAnnotations()));
        time2.setConceptIdentity(toConceptReference(time1.getConceptIdentity()));
        time2.setPosition(time1.getPosition());
        time2.setType(DimensionTypeType.fromStringWithException(time1.getType().toString()));
        time2.setLocalRepresentation(toLocalRepresentation(time1.getLocalRepresentation()));
        time2.setUri(toAnyURI((time1.getUri())));
        time2.setUrn(toAnyURI((time1.getUrn())));        
        return time2;
    }

    public static StructureType toStructureDocument(Reader in) throws XmlException, IOException {
        org.sdmx.resources.sdmxml.schemas.v21.message.StructureDocument structDoc = null;
        try {
            structDoc = org.sdmx.resources.sdmxml.schemas.v21.message.StructureDocument.Factory.parse(in);
            return parseStructure(structDoc);
        } catch (TypeValueNotFoundException ex) {
            Logger.getLogger(Sdmx21StructureReaderTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}

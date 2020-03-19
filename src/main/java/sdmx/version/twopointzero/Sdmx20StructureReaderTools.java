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
package sdmx.version.twopointzero;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import org.apache.xmlbeans.GDuration;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;
import org.sdmx.resources.sdmxml.schemas.v20.message.RegistryInterfaceDocument;
import org.sdmx.resources.sdmxml.schemas.v20.message.StructureDocument;
import org.sdmx.resources.sdmxml.schemas.v20.structure.ComponentsType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.CrossSectionalMeasureType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.KeyFamilyRefType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.PrimaryMeasureType;
import sdmx.Registry;
import sdmx.common.ActionType;
import sdmx.common.AnnotationType;
import sdmx.common.DataType;
import sdmx.common.Description;
import sdmx.common.DimensionTypeType;
import sdmx.common.Name;
import sdmx.common.ObservationalTimePeriodType;
import sdmx.common.PayloadStructureType;
import sdmx.common.StandardTimePeriodType;
import sdmx.common.TextType;
import sdmx.common.TimezoneType;
import sdmx.commonreferences.CodelistReference;
import sdmx.commonreferences.ConceptRef;
import sdmx.commonreferences.ConceptReference;
import sdmx.commonreferences.ConceptSchemeRef;
import sdmx.commonreferences.ConceptSchemeReference;
import sdmx.commonreferences.DataStructureRef;
import sdmx.commonreferences.DataStructureReference;
import sdmx.commonreferences.IDType;
import sdmx.commonreferences.ItemSchemeRefBase;
import sdmx.commonreferences.ItemSchemeReferenceBase;
import sdmx.commonreferences.LocalItemRefBase;
import sdmx.commonreferences.LocalItemReference;
import sdmx.commonreferences.LocalPrimaryMeasureRef;
import sdmx.commonreferences.LocalPrimaryMeasureReference;
import sdmx.commonreferences.NCNameID;
import sdmx.commonreferences.NestedNCNameID;
import sdmx.commonreferences.RefBase;
import sdmx.commonreferences.StructureRefBase;
import sdmx.commonreferences.StructureReferenceBase;
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
import sdmx.net.DoubleRegistry;
import sdmx.net.LocalRegistry;
import sdmx.structure.CodelistsType;
import sdmx.structure.ConceptsType;
import sdmx.structure.DataStructuresType;
import sdmx.structure.DataflowsType;
import sdmx.structure.StructuresType;
import sdmx.structure.base.ComponentUtil;
import sdmx.structure.base.RepresentationType;
import sdmx.structure.base.TextFormatType;
import sdmx.structure.codelist.CodeType;
import sdmx.structure.codelist.CodelistType;
import sdmx.structure.concept.ConceptRepresentation;
import sdmx.structure.concept.ConceptSchemeType;
import sdmx.structure.concept.ConceptType;
import sdmx.structure.concept.ISOConceptReferenceType;
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
import sdmx.structure.datastructure.UsageStatusType;
import sdmx.version.common.ParseParams;
import static sdmx.version.twopointone.Sdmx21StructureReaderTools.toContactList;
import static sdmx.version.twopointone.Sdmx21StructureReaderTools.toIDType;
import static sdmx.version.twopointone.Sdmx21StructureReaderTools.toNames;
import static sdmx.version.twopointone.Sdmx21StructureReaderTools.toTextType;
import static sdmx.version.twopointone.Sdmx21StructureReaderTools.toTimezoneType;
import sdmx.xml.DateTime;
import sdmx.xml.DateType;
import sdmx.xml.ID;
import sdmx.xml.anyURI;
import sdmx.xml.duration;
import sdmx.xml.positiveInteger;

/**
 *
 * @author James
 */

public class Sdmx20StructureReaderTools {

    ParseParams params = null;
    Registry registry = null;

    private String conceptSchemeName = "STANDALONE_CONCEPT_SCHEME";

    public Sdmx20StructureReaderTools() {
    }

    public Sdmx20StructureReaderTools(ParseParams params) {
        this.params = params;
        // Temporary Registry for loading structure into before loading into main registry.
        this.registry = params.getRegistry();
        /*
        if (this.params.isUseDataflowName()) {
            DataflowType flow = params.getDataflow();
            if (flow == null) {
                throw new RuntimeException("use data flow name is selected, yet data flow is not set in parse params!");
            }
            this.conceptSchemeName = flow.getId().toString() + "_CONCEPT_SCHEME";
        }
         */
    }
    org.sdmx.resources.sdmxml.schemas.v20.message.StructureDocument structDoc = null;
    org.sdmx.resources.sdmxml.schemas.v20.message.RegistryInterfaceDocument regDoc = null;
    NestedNCNameID mainAgencyId = null;
    NestedNCNameID currentKeyFamilyAgency = null;

    public StructureType parseStructure(InputStream in) throws XmlException, IOException, TypeValueNotFoundException {
        XmlOptions xmlOptions = new XmlOptions();
        //xmlOptions.setCharacterEncoding("utf-16");
        xmlOptions.setLoadStripComments();
        xmlOptions.setLoadTrimTextBuffer();
        xmlOptions.setLoadStripWhitespace();
        try {
            structDoc = org.sdmx.resources.sdmxml.schemas.v20.message.StructureDocument.Factory.parse(in, xmlOptions);
        } catch (Exception ex) {
            try {
                javax.xml.soap.SOAPMessage message = javax.xml.soap.MessageFactory.newInstance().createMessage(null, in);
                structDoc = (StructureDocument) message.getSOAPBody().getElementsByTagNameNS("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message", "Structure");
            } catch (javax.xml.soap.SOAPException ex1) {
                Logger.getLogger(Sdmx20StructureReaderTools.class.getName()).log(Level.SEVERE, null, ex1);
            }

        }
        mainAgencyId = new NestedNCNameID(structDoc.getStructure().getHeader().getSenderArray(0).getId());
        return parseStructure(structDoc);
    }

    public StructureType parseSOAPStructure(InputStream in) throws XmlException, IOException, TypeValueNotFoundException {
        try {
            XmlOptions xmlOptions = new XmlOptions();
            //xmlOptions.setCharacterEncoding("utf-16");
            xmlOptions.setLoadStripComments();
            xmlOptions.setLoadTrimTextBuffer();
            xmlOptions.setLoadStripWhitespace();
            MessageFactory messageFactory = MessageFactory.newInstance();
            SOAPMessage message = messageFactory.createMessage(null, in);
            SOAPBody body = message.getSOAPBody();
            structDoc = StructureDocument.Factory.parse(findStructDocNode(body));
            mainAgencyId = new NestedNCNameID(structDoc.getStructure().getHeader().getSenderArray(0).getId());
            return parseStructure(structDoc);
        } catch (SOAPException ex) {
            Logger.getLogger(Sdmx20StructureReaderTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Node findStructDocNode(SOAPElement n) {
        if(n.getLocalName().equals("Structure"))return n;
        Iterator<Node> it = n.getChildElements();
        while (it.hasNext()) {
            Node n2 = it.next();
            if (n2!=null&&n2.getLocalName().equals("Structure")) {
                return n2;
            }
        }
        return null;
    }

    public StructureType parseStructure(Reader in) throws XmlException, IOException, TypeValueNotFoundException {
        XmlOptions xmlOptions = new XmlOptions();
        //xmlOptions.setCharacterEncoding("utf-16");
        xmlOptions.setLoadStripComments();
        xmlOptions.setLoadTrimTextBuffer();
        xmlOptions.setLoadStripWhitespace();
        structDoc = org.sdmx.resources.sdmxml.schemas.v20.message.StructureDocument.Factory.parse(in, xmlOptions);
        mainAgencyId = new NestedNCNameID(structDoc.getStructure().getHeader().getSenderArray(0).getId());
        return parseStructure(structDoc);
    }

    StructureType sd = new StructureType();
    StructuresType struct = new StructuresType();

    public StructureType parseRegistry(InputStream in) throws XmlException, IOException, TypeValueNotFoundException {
        XmlOptions xmlOptions = new XmlOptions();
        //xmlOptions.setCharacterEncoding("utf-16");
        xmlOptions.setLoadStripComments();
        xmlOptions.setLoadTrimTextBuffer();
        xmlOptions.setLoadStripWhitespace();
        regDoc = org.sdmx.resources.sdmxml.schemas.v20.message.RegistryInterfaceDocument.Factory.parse(in, xmlOptions);
        mainAgencyId = new NestedNCNameID(regDoc.getRegistryInterface().getHeader().getSenderArray(0).getId());
        return parseRegistry(regDoc);
    }

    public StructureType parseRegistry(Reader in) throws XmlException, IOException, TypeValueNotFoundException {
        XmlOptions xmlOptions = new XmlOptions();
        //xmlOptions.setCharacterEncoding("utf-16");
        xmlOptions.setLoadStripComments();
        xmlOptions.setLoadTrimTextBuffer();
        xmlOptions.setLoadStripWhitespace();
        regDoc = org.sdmx.resources.sdmxml.schemas.v20.message.RegistryInterfaceDocument.Factory.parse(in, xmlOptions);
        mainAgencyId = new NestedNCNameID(regDoc.getRegistryInterface().getHeader().getSenderArray(0).getId());
        return parseRegistry(regDoc);
    }

    public StructureType parseStructure(org.sdmx.resources.sdmxml.schemas.v20.message.StructureDocument structDoc) throws TypeValueNotFoundException {
        if (!(registry instanceof DoubleRegistry)) {
            registry = new DoubleRegistry(struct, registry);
        }
        sd.setStructures(struct);
        try {
            struct.setCodelists(toCodelists(structDoc.getStructure().getCodeLists()));
            struct.setConcepts(toConcepts(structDoc.getStructure().getConcepts()));
            struct.setDataStructures(toDataStructures(structDoc.getStructure().getKeyFamilies()));
            struct.setDataflows(toDataflows(structDoc.getStructure().getDataflows()));
            sd.setHeader(toHeaderType(structDoc.getStructure().getHeader()));
        } catch (URISyntaxException ex) {
            //System.out.println("Exception!");
            ex.printStackTrace();
            Logger
                    .getLogger(Sdmx20StructureReaderTools.class
                            .getName()).log(Level.SEVERE, null, ex);
        }
        sd.setStructures(struct);
        return sd;
    }

    public NestedNCNameID toNCName(String s) {
        return new NestedNCNameID(s);
    }

    public NCNameID toNCNameIDType(String s) {
        return new NCNameID(s);
    }

    public IDType toIDType(String s) {
        if (s == null) {
            return null;
        }
        return new IDType(s);
    }

    public CodelistType toCodelist(org.sdmx.resources.sdmxml.schemas.v20.structure.CodeListType cl1) throws URISyntaxException {
        if (cl1 == null) {
            return null;
        }
        CodelistType cl2 = new CodelistType();
        cl2.setId(toNCNameIDType(cl1.getId()));
        cl2.setAgencyID(toNestedNCNameIDType(cl1.getAgencyID()));
        if (cl1.getVersion() != null) {
            cl2.setVersion(toVersionType(cl1.getVersion()));
        } else {
            cl2.setVersion(Version.ONE);
        }
        cl2.setAnnotations(toAnnotations(cl1.getAnnotations()));
        cl2.setDescriptions(toDescriptions(cl1.getDescriptionArray()));
        cl2.setExternalReference(cl1.getIsExternalReference());
        cl2.setFinal(cl1.getIsFinal());
        cl2.setUri(toAnyURI(cl1.getUri()));
        cl2.setUrn(toAnyURI(cl1.getUrn()));
// No Partial Attribute in sdmx 2.0
//        cl2.setPartial(cl1.getIsPartial());
        cl2.setNames(toNames(cl1.getNameArray()));
// no Hierarchical Codelists in sdmx 2.0
        cl2.setCodes(toCodes(cl1.getCodeArray()));
// No Version in sdmx 2.0
//        cl2.setVersion(toVersionType(cl1.getVersion()));
// No External Reference Url in sdmx 2.0
//        cl2.setExternalReferences(toExternalReference(cl1.getServiceURL(), cl1.getStructureURL()));
        //cl1.getS
        return cl2;
    }

    public List<CodeType> toCodes(org.sdmx.resources.sdmxml.schemas.v20.structure.CodeType[] codes) throws URISyntaxException {
        List<CodeType> codelist = new ArrayList<CodeType>();
        for (int i = 0; i < codes.length; i++) {
            CodeType cc = toCode(codes[i]);
            boolean duplicate = false;
            for (CodeType cc2 : codelist) {
                if (cc2.getId().equals(cc.getId())) {
                    duplicate = true;
                }
            }
            if (!duplicate) {
                codelist.add(toCode(codes[i]));
            } else {
                System.out.println("SDMX-SAX Warn - Ignoring Second Code with ID:" + cc.getId().toString());
            }
        }
        return codelist;
    }

    public sdmx.common.Annotations toAnnotations(org.sdmx.resources.sdmxml.schemas.v20.common.AnnotationsType annots1) {
        if (annots1 == null) {
            return null;
        }
        sdmx.common.Annotations annots2 = new sdmx.common.Annotations();
        for (int i = 0; i < annots1.getAnnotationArray().length; i++) {
            annots2.addAnnotation(toAnnotation(annots1.getAnnotationArray(i)));
        }
        return annots2;

    }

    public sdmx.common.AnnotationType toAnnotation(org.sdmx.resources.sdmxml.schemas.v20.common.AnnotationType ann) {
        AnnotationType an = new AnnotationType();
        // id field not in sdmx 2.0
        //String id = ann.getId();
        //an.setId(id);
        for (int i = 0; i < ann.getAnnotationTextArray().length; i++) {
            an.getAnnotationText().add(toTextType(ann.getAnnotationTextArray(i)));
        }
        an.setAnnotationTitle(ann.getAnnotationTitle());
        an.setAnnotationType(ann.getAnnotationType());
        an.setAnnotationUrl(ann.getAnnotationURL());
        return an;
    }

    public sdmx.common.TextType toTextType(org.sdmx.resources.sdmxml.schemas.v20.common.TextType tt) {
        TextType tt1 = new TextType(tt.getLang(), tt.getStringValue());
        return tt1;
    }

    public List<sdmx.common.TextType> toTextType(org.sdmx.resources.sdmxml.schemas.v20.common.TextType[] tt) {
        List<TextType> list = new ArrayList<TextType>();
        for (int i = 0; i < tt.length; i++) {
            list.add(toTextType(tt[i]));
        }
        return list;
    }

    public List<sdmx.common.Description> toDescriptions(org.sdmx.resources.sdmxml.schemas.v20.common.TextType[] tt) {
        List<Description> descs = new ArrayList<Description>();
        for (int i = 0; i < tt.length; i++) {
            descs.add(toDescription(tt[i]));
        }
        return descs;
    }

    public sdmx.common.Description toDescription(org.sdmx.resources.sdmxml.schemas.v20.common.TextType tt) {
        //System.out.println("ToDescription:"+tt.getLang()+":"+tt.getStringValue());
        Description tt1 = new Description(tt.getLang(), tt.getStringValue());
        return tt1;

    }

    public NestedNCNameID toNestedNCNameIDType(String ncname) {
        if (ncname == null) {
            return null;
        }
        return new NestedNCNameID(ncname);
    }

    public List<sdmx.common.Name> toNames(org.sdmx.resources.sdmxml.schemas.v20.common.TextType[] tt) {
        List<Name> descs = new ArrayList<Name>();
        for (int i = 0; i < tt.length; i++) {
            descs.add(toName(tt[i]));
        }
        return descs;
    }

    public sdmx.common.Name toName(org.sdmx.resources.sdmxml.schemas.v20.common.TextType tt) {
        //System.out.println("lang="+tt.getLang()+":"+tt.getStringValue());
        Name tt1 = new Name(tt.getLang(), tt.getStringValue());
        return tt1;
    }

    public CodeType toCode(org.sdmx.resources.sdmxml.schemas.v20.structure.CodeType cl1) throws URISyntaxException {
        CodeType cl2 = new CodeType();
        //System.out.println("Code="+cl1.toString());
        cl2.setAnnotations(toAnnotations(cl1.getAnnotations()));
        // No Name Array in sdmx 2.0
        //cl2.setNames(toNames(cl1.getNameArray()));
        //cl2.setDescriptions(toDescriptions(cl1.getDescriptionArray()));
        List<Name> names = new ArrayList<Name>(cl1.getDescriptionArray().length);
        List<Description> descs = toDescriptions(cl1.getDescriptionArray());
        Iterator<Description> it = descs.iterator();
        while (it.hasNext()) {
            Description desc = it.next();
            Name name = new Name(desc.getLang(), desc.getText());
            names.add(name);
        }
        cl2.setNames(names);
        // Codelist Id is 'Value' in sdmx 2.0
        cl2.setId(toIDType(cl1.getValue().toString()));
        // No URI in sdmx 2.0
        //        cl2.setUri(toAnyURI(cl1.getUrn()));
        cl2.setUrn(toAnyURI(cl1.getUrn()));
        if (cl1.getParentCode() != null) {
            cl2.setParent(toLocalItemReference(cl1.getParentCode(), ItemTypeCodelistType.CODE, ItemSchemePackageTypeCodelistType.CODELIST));
        }
        // No Hierarchical Codelists in sdmx 2.0
        //cl2.setCodes(toCodes(cl1.getItemArray()));
        return cl2;
    }

    public CodelistsType toCodelists(org.sdmx.resources.sdmxml.schemas.v20.structure.CodeListsType cl1) throws URISyntaxException {
        if (cl1 == null) {
            return null;
        }
        List<CodelistType> codelists = new ArrayList<CodelistType>();
        for (int i = 0; i < cl1.getCodeListArray().length; i++) {
            codelists.add(toCodelist(cl1.getCodeListArray(i)));
        }
        CodelistsType cl2 = new CodelistsType(codelists);
        return cl2;
    }

    public anyURI toAnyURI(String s) throws URISyntaxException {
        if (s == null) {
            return null;
        }
        return new anyURI(s);
    }

    public ConceptsType toConcepts(org.sdmx.resources.sdmxml.schemas.v20.structure.ConceptsType con1) throws TypeValueNotFoundException, URISyntaxException {
        if (con1 == null) {
            return null;
        }
        ConceptsType con2 = new ConceptsType();
        List<ConceptSchemeType> cons = new ArrayList<>();
        for (int i = 0; i < con1.getConceptSchemeArray().length; i++) {
            cons.add(toConceptScheme(con1.getConceptSchemeArray(i)));
        }
        for (int i = 0; i < con1.getConceptArray().length; i++) {
            ConceptType ct = toConcept(con1.getConceptArray(i));
            //System.out.println("Cons1="+cons);
            ConceptSchemeType cs = findStandaloneConceptScheme(cons, toNestedNCNameIDType(con1.getConceptArray(i).getAgencyID()));
            //System.out.println("Cons2="+cons);
            cs.addConcept(ct);
            //System.out.println("Added Concept To Standalone");
        }
        con2.setConceptSchemes(cons);
        //con2.dump();
        //System.out.println("Cons3="+con2.getConceptSchemes());
        return con2;
    }

    public ConceptSchemeType findStandaloneConceptScheme(List<ConceptSchemeType> list, NestedNCNameID agency) {
        ConceptSchemeType standalone = null;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(conceptSchemeName) && list.get(i).getAgencyID().equals(agency)) {
                return list.get(i);
            }
        }
        standalone = new ConceptSchemeType();
        standalone.setId(new IDType(conceptSchemeName));
        standalone.setAgencyID(agency);
        standalone.setVersion(new Version("1.0"));
        Name name = new Name("en", "Standalone Concept Scheme");
        standalone.setNames(Collections.singletonList(name));
        list.add(standalone);
        //System.out.println("Added Standalone");
        return standalone;
    }

    public ConceptSchemeType toConceptScheme(org.sdmx.resources.sdmxml.schemas.v20.structure.ConceptSchemeType con1) throws TypeValueNotFoundException, URISyntaxException {
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
        con2.setUri(toAnyURI(con1.getUri()));
        con2.setUrn(toAnyURI(con1.getUrn()));
        // No Version in sdmx 2.0
        //con2.setVersion(toVersionType(con1.getVersion()));
        // No External Reference in sdmx 2.0
        //con2.setExternalReferences(toExternalReference(con1.getServiceURL(), con1.getStructureURL()));
        List<ConceptType> cons = new ArrayList<>();
        for (int i = 0; i < con1.getConceptArray().length; i++) {
            ConceptType ct = toConceptType(con2, con1.getConceptArray(i));
            boolean duplicate = false;
            for (ConceptType ct2 : cons) {
                if (ct.getId().equals(ct2.getId())) {
                    duplicate = true;
                }
            }
            if (!duplicate) {
                cons.add(toConceptType(con2, con1.getConceptArray(i)));
            } else {
                System.out.println("SDMX-SAX Warn - Ignoring Duplicate Concept ID:ConceptScheme:" + con2.getAgencyID().toString() + ":" + con2.getId().toString() + ":" + con2.getVersion().toString() + ": concept id:" + ct.getId().toString());
            }
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

    public ConceptType toConceptType(ConceptSchemeType cscheme, org.sdmx.resources.sdmxml.schemas.v20.structure.ConceptType con1) throws TypeValueNotFoundException, URISyntaxException {
        if (con1 == null) {
            return null;
        }
        ConceptType con2 = new ConceptType();
        con2.setAnnotations(toAnnotations(con1.getAnnotations()));
        con2.setDescriptions(toDescriptions(con1.getDescriptionArray()));
        con2.setNames(toNames(con1.getNameArray()));
        con2.setId(toNCNameIDType(con1.getId().toString()));
        con2.setUri(toAnyURI(con1.getUri()));
        con2.setUrn(toAnyURI(con1.getUrn()));
        con2.setAgencyID(toNestedNCNameIDType(con1.getAgencyID()));
        con2.setVersion(toVersionType(con1.getVersion()));
        // Parent ID is stored as local item reference type with id.
        con2.setParent(toLocalItemReferenceType(con1.getParent()));
        // No CoreRepresentation in sdmx 2.0
        //con2.setCoreRepresentation(toConceptRepresentation(con1.getCoreRepresentation()));
        // No ISOConceptRef in sdmx 2.0
        //con2.setIsoConceptRef(toISOConceptRef(con1.getISOConceptReference()));
        return con2;
    }

    public LocalItemReference toLocalItemReferenceType(String id) throws TypeValueNotFoundException {
        if (id == null) {
            return null;
        }
        LocalItemReference lrt2 = new LocalItemReference(new LocalItemRefBase(toIDType(id), ItemTypeCodelistType.CONCEPT, ItemSchemePackageTypeCodelistType.CONCEPTSCHEME));
        return lrt2;
    }
    /*
     * This is for the case of a simple stand alone non hierarchical Concept (it is stored as a ConceptScheme)
     */

    public ConceptType toConcept(org.sdmx.resources.sdmxml.schemas.v20.structure.ConceptType con1) throws URISyntaxException {
        if (con1 == null) {
            return null;
        }
        ConceptType con2 = new ConceptType();
        con2.setAnnotations(toAnnotations(con1.getAnnotations()));
        con2.setDescriptions(toDescriptions(con1.getDescriptionArray()));
        //con2.setFinal(con1.getIsFinal());
        con2.setNames(toNames(con1.getNameArray()));
        con2.setId(toNCNameIDType(con1.getId()));
        con2.setUri(toAnyURI(con1.getUri()));
        con2.setUrn(toAnyURI(con1.getUrn()));
        con2.setAgencyID(toNestedNCNameIDType(con1.getAgencyID()));
        con2.setVersion(toVersionType(con1.getVersion()));
        // No Version in sdmx 2.0
        //con2.setVersion(toVersionType(con1.getVersion()));
        // No External Reference in sdmx 2.0
        //con2.setExternalReferences(toExternalReference(con1.getServiceURL(), con1.getStructureURL()));
        return con2;
    }

    public DataStructuresType toDataStructures(org.sdmx.resources.sdmxml.schemas.v20.structure.KeyFamiliesType kf) throws URISyntaxException, TypeValueNotFoundException {
        if (kf == null) {
            return null;
        }
        List<DataStructureType> dss = new ArrayList<DataStructureType>();
        DataStructuresType dst = new DataStructuresType();
        for (int i = 0; i < kf.sizeOfKeyFamilyArray(); i++) {
            dss.add(toDataStructure(kf.getKeyFamilyArray(i)));
        }
        dst.setDataStructures(dss);
        return dst;
    }

    public DataStructureType toDataStructure(org.sdmx.resources.sdmxml.schemas.v20.structure.KeyFamilyType kf) throws URISyntaxException, TypeValueNotFoundException {
        if (kf == null) {
            return null;
        }
        DataStructureType ds = new DataStructureType();
        ds.setNames(toNames(kf.getNameArray()));
        ds.setAnnotations(toAnnotations(kf.getAnnotations()));
        ds.setId(toIDType(kf.getId()));
        ds.setAgencyID(toNestedNCNameIDType(kf.getAgencyID()));
        currentKeyFamilyAgency = ds.getAgencyID();
        if (currentKeyFamilyAgency == null) {
            currentKeyFamilyAgency = mainAgencyId;
        }
        ds.setVersion(toVersionType(kf.getVersion()));
        ds.setDescriptions(toDescriptions(kf.getDescriptionArray()));
        ds.setExternalReference(kf.getIsExternalReference());
        ds.setFinal(kf.getIsFinal());
        ds.setUri(toAnyURI(kf.getUri()));
        ds.setUrn(toAnyURI(kf.getUrn()));
        ds.setDataStructureComponents(toDataStructureComponents(kf.getComponents()));

        return ds;
    }

    public Version toVersionType(String s) {
        if (s == null) {
            return Version.ONE;
            //return null;
        }
        return new Version(s);
    }

    private DataStructureComponents currentDataStructure = null;

    public DataStructureComponents toDataStructureComponents(org.sdmx.resources.sdmxml.schemas.v20.structure.ComponentsType c1) throws TypeValueNotFoundException, URISyntaxException {
        if (c1 == null) {
            return null;
        }
        currentDataStructure = new DataStructureComponents();
        currentDataStructure.setDimensionList(toDimensionListType(c1.getDimensionArray()));
        currentDataStructure.setAttributeList(toAttributeList(c1.getAttributeArray()));
        currentDataStructure.setMeasureList(toMeasureList(c1));
        if (currentDataStructure.getDimensionList() != null && c1.getTimeDimension() != null) {
            currentDataStructure.getDimensionList().setTimeDimension(toTimeDimension(c1.getTimeDimension()));
        }
        return currentDataStructure;
    }

    public DimensionListType toDimensionListType(org.sdmx.resources.sdmxml.schemas.v20.structure.DimensionType[] d1) throws TypeValueNotFoundException, URISyntaxException {
        DimensionListType dlt = new DimensionListType();
        List<DimensionType> d2 = new ArrayList<DimensionType>();
        for (int i = 0; i < d1.length; i++) {
            if (!d1[i].getIsMeasureDimension()) {
                d2.add(toDimensionType(d1[i]));
            } else {
                dlt.setMeasureDimension(toMeasureDimensionType(d1[i]));
            }
        }
        dlt.setDimensions(d2);
        return dlt;
    }

    public DimensionType toDimensionType(org.sdmx.resources.sdmxml.schemas.v20.structure.DimensionType d1) throws TypeValueNotFoundException, URISyntaxException {
        //System.out.println("CS Agency="+d1.getConceptAgency());
        //System.out.println("CS Ref="+d1.getConceptRef());
        //System.out.println("CT Ver="+d1.getConceptVersion());
        ConceptSchemeType cscheme = getConceptScheme(d1);
        //System.out.println("ConceptScheme=" + cscheme);
        ConceptType concept = getConcept(cscheme, d1);

        //System.out.println("Dimension=" + d1.getConceptRef());
        //System.out.println("Cscheme=" + cscheme);
        //System.out.println("Concept=" + concept);
        DimensionType d2 = new DimensionType();
        CodelistType code = getCodelist(d1);
        if (concept != null) {
            d2.setConceptIdentity(toConceptReference(cscheme, concept));
            //d2.setId(concept.getId());
            //System.out.println("Concept3="+d2.getConceptIdentity());
            //System.out.println("Concept4="+d2.getConceptIdentity().getId());
        }
        //System.out.println("Dim:"+d1.getConceptRef()+":code="+code);
        if (code != null) {
            d2.setLocalRepresentation(toLocalRepresentation(code, toTextFormatType(d1.getTextFormat())));
        }
        return d2;
    }

    public MeasureDimensionType toMeasureDimensionType(org.sdmx.resources.sdmxml.schemas.v20.structure.DimensionType d1) throws TypeValueNotFoundException, URISyntaxException {
        //System.out.println("CS Agency="+d1.getConceptSchemeAgency());
        //System.out.println("CS Ref="+d1.getConceptSchemeRef());
        //System.out.println("CT Id="+d1.getConceptRef());
        ConceptSchemeType cscheme = getConceptScheme(d1);
        //System.out.println("Concept="+cscheme);
        ConceptType concept = getConcept(cscheme, d1);
        //System.out.println("Dimension=" + d1.getConceptRef());
        //System.out.println("Cscheme=" + cscheme);
        //System.out.println("Concept=" + concept);
        MeasureDimensionType d2 = new MeasureDimensionType();
        if (concept != null) {
            d2.setConceptIdentity(toConceptReference(cscheme, concept));
            //d2.setId(concept.getId());
        }
        //System.out.println("Dim:"+d1.getConceptRef()+":code="+code);
        String id = d2.getId().toString();
        CodelistType codelist = getCodelist(d1);

        //if (!id.endsWith("_MEASURES")) {
        //    id = id + "_MEASURES";
        //}
        NestedNCNameID agency = codelist.getAgencyID();
        if (agency == null) {
            agency = mainAgencyId;
        }
        Version vers = codelist.getVersion();
        if (vers == null) {
            vers = Version.ONE;
        }
        ConceptSchemeReference ref = ConceptSchemeReference.create(agency, codelist.getId(), vers);
        ConceptSchemeType scheme = registry.find(ref);
        if (scheme == null) {
            scheme = new ConceptSchemeType();
            scheme.setAgencyID(agency);
            scheme.setId(codelist.getId());
            scheme.setVersion(vers);
            scheme.setNames(codelist.getNames());
            struct.getConcepts().getConceptSchemes().add(scheme);
            for (int i = 0; i < codelist.size(); i++) {
                ConceptType concept2 = new ConceptType();
                CodeType code = (CodeType) codelist.getItem(i);
                concept2.setNames(code.getNames());
                concept2.setDescriptions(code.getDescriptions());
                concept2.setAnnotations(code.getAnnotations());
                concept2.setId(new NCNameID(code.getId().toString()));
                ConceptRepresentation coreRep = new ConceptRepresentation();
                TextFormatType text = new TextFormatType();
                text.setTextType(DataType.DOUBLE);
                coreRep.setTextFormat(text);
                concept2.setCoreRepresentation(coreRep);
                scheme.addConcept(concept2);
            }
        }
        d2.setLocalRepresentation(toLocalRepresentation(scheme));
        return d2;
    }

    public ConceptReference toConceptReference(ConceptSchemeType csch, ConceptType cs) throws TypeValueNotFoundException, URISyntaxException {
        //System.out.println("ToConceptReference:"+csch.getAgencyID()+":"+csch.getId()+":"+csch.getVersion());
        //System.out.println("Concept1="+cs);
        //System.out.println("Concept2="+cs.getId());

        if (cs == null) {
            return null;
        }
        if (cs.getId() != null) {
            ConceptReference srt2 = new ConceptReference(toConceptRefType(csch, cs), cs.getUri());
            return srt2;
        } else {
            ConceptReference srt2 = new ConceptReference(cs.getUri());
            return srt2;
        }
    }

    public ConceptRef toConceptRefType(ConceptSchemeType csch, ConceptType cs) {
        // Sdmx2.0 concepts dont have maintainable parent Id's
        //System.out.println("Vers1:"+cs.getVersion());
        if (csch != null) {
            ConceptRef ref2 = new ConceptRef(csch.getAgencyID(), csch.getId(), csch.getVersion(), cs.getId());
            ref2.setAgencyId(csch.getAgencyID());
            //System.out.println("Cref=:"+ref2.getAgencyId()+":"+ref2.getMaintainableParentId().toString()+":"+ref2.getId()+":-"+registry.findConceptScheme(ref2.getAgencyId(), ref2.getMaintainableParentId()));
            //System.out.println("cs.getId()=="+cs.getId());
            return ref2;
        } else {
            ConceptRef ref2 = new ConceptRef(cs.getAgencyID(), new IDType(conceptSchemeName), cs.getVersion(), cs.getId());
            //System.out.println("Vers2:"+ref2.getVersion());
            return ref2;
        }
    }

    public void toCrossSectionalMeasure(CrossSectionalMeasureType d1) throws TypeValueNotFoundException, URISyntaxException {
        //System.out.println("CS Agency="+d1.getConceptSchemeAgency());
        //System.out.println("CS Ref="+d1.getConceptSchemeRef());
        //System.out.println("CT Id="+d1.getConceptRef());
        ConceptSchemeType cscheme = getConceptScheme(d1);
        //System.out.println("Concept="+cscheme);
        ConceptType concept = getConcept(cscheme, d1);
        /*
         if (cscheme.getId().equals("STANDALONE_CONCEPT_SCHEME")) {
         MeasureDimensionType measure = currentDataStructure.getMeasureList().getMeasures().get(0);
         ConceptSchemeType cs = registry.findConceptScheme(measure.getLocalRepresentation().getEnumeration().getAgencyId(), measure.getLocalRepresentation().getEnumeration().getMaintainableParentId());
         concept.setCode(d1.getCode());
         cscheme.removeItem(concept);
         cs.addConcept(concept);
         }*/
        return;
    }

    public ConceptSchemeType getConceptScheme(org.sdmx.resources.sdmxml.schemas.v20.structure.CrossSectionalMeasureType dim) {
        Logger.getLogger("sdmx").log(Level.FINE, "Sdmx20StructureReaderTools:getConceptScheme DimensionType " + dim.getConceptRef() + ":" + dim.getConceptAgency() + ":" + dim.getConceptSchemeRef() + ":" + dim.getConceptVersion());
        if ((dim.getConceptSchemeAgency() != null || dim.getConceptAgency() != null) && dim.getConceptSchemeRef() != null && dim.getConceptRef() != null) {
            NestedNCNameID csa = new NestedNCNameID(dim.getConceptSchemeAgency() == null ? dim.getConceptAgency() : dim.getConceptSchemeAgency());
            IDType csi = new IDType(dim.getConceptSchemeRef());
            Version version = dim.getConceptVersion() == null ? null : new Version(dim.getConceptVersion());
            ConceptSchemeReference ref = ConceptSchemeReference.create(csa, csi, version);
            return registry.find(ref);
        } else if (dim.getConceptSchemeRef() != null && dim.getConceptRef() != null) {
            NestedNCNameID csa = currentKeyFamilyAgency;
            IDType csi = new IDType(dim.getConceptSchemeRef());
            Version version = dim.getConceptVersion() == null ? null : new Version(dim.getConceptVersion());
            ConceptSchemeReference ref = ConceptSchemeReference.create(csa, csi, version);
            return registry.find(ref);
        } else if (dim.getConceptRef() != null && dim.getConceptAgency() == null) {
            NestedNCNameID csa = currentKeyFamilyAgency;
            IDType csi = new IDType(conceptSchemeName);
            Version version = dim.getConceptVersion() == null ? null : new Version(dim.getConceptVersion());
            ConceptSchemeReference ref = ConceptSchemeReference.create(csa, csi, version);
            ConceptSchemeType cst = registry.find(ref);
            ConceptType ct = cst != null ? cst.findConcept(new IDType(dim.getConceptRef())) : null;
            if (ct == null) {
                ct = findConcept(dim.getConceptRef());
                if (ct == null) {
                    return null;
                }
                ConceptSchemeReference ref2 = ConceptSchemeReference.create(ct.getAgencyID(), new IDType(conceptSchemeName), version);
                cst = registry.find(ref2);
            } else {
                ConceptSchemeReference ref2 = ConceptSchemeReference.create(mainAgencyId, new IDType(conceptSchemeName), version);
                cst = registry.find(ref2);
            }
            return cst;
        } else if (dim.getConceptRef() != null && dim.getConceptAgency() != null) {
            NestedNCNameID csa = new NestedNCNameID(dim.getConceptAgency());
            IDType csi = new IDType(conceptSchemeName);
            ConceptSchemeReference ref = ConceptSchemeReference.create(csa, csi, Version.ONE);
            ConceptSchemeType cst = registry.find(ref);
            return cst;
        } else {
            return null;
        }
    }

    public ConceptType getConcept(ConceptSchemeType scheme, org.sdmx.resources.sdmxml.schemas.v20.structure.CrossSectionalMeasureType dim) {
        if (scheme != null) {
            return scheme.findConcept(dim.getConceptRef());
        } else {
            return null;
        }
    }

    public RepresentationType toLocalRepresentation(CodelistType codelist, TextFormatType ttf) throws TypeValueNotFoundException, URISyntaxException {
        SimpleDataStructureRepresentationType lr2 = new SimpleDataStructureRepresentationType();
        lr2.setTextFormat(ttf);
        if (codelist != null) {
            lr2.setEnumeration(toItemSchemeReference(codelist));
        }
        return lr2;
    }

    public RepresentationType toLocalRepresentation(ConceptSchemeType cscheme) throws TypeValueNotFoundException, URISyntaxException {
        SimpleDataStructureRepresentationType lr2 = new SimpleDataStructureRepresentationType();
        lr2.setEnumeration(toConceptSchemeReference(cscheme));
        return lr2;
    }

    public RepresentationType toLocalRepresentation(TextFormatType tf) throws TypeValueNotFoundException, URISyntaxException {
        SimpleDataStructureRepresentationType lr2 = new SimpleDataStructureRepresentationType();
        lr2.setTextFormat(tf);
        return lr2;
    }

    public ConceptSchemeReference toConceptSchemeReference(ConceptSchemeType cscheme) {
        ConceptSchemeReference ref = new ConceptSchemeReference(toConceptSchemeRefType(cscheme), cscheme.getUri());
        return ref;
    }

    public ConceptSchemeRef toConceptSchemeRefType(ConceptSchemeType cscheme) {
        ConceptSchemeRef ref = new ConceptSchemeRef(cscheme.getAgencyID(), cscheme.getId(), cscheme.getVersion());
        return ref;
    }

    public SimpleDataStructureRepresentationType toSimpleDataStructureRepresentation(CodelistType codelist, TextFormatType ttf) throws TypeValueNotFoundException, URISyntaxException {
        if (codelist == null) {
            return null;
        }
        SimpleDataStructureRepresentationType lr2 = new SimpleDataStructureRepresentationType();
        lr2.setTextFormat(ttf);
        lr2.setEnumeration(toItemSchemeReference(codelist));
        return lr2;
    }

    public ItemSchemeReferenceBase toItemSchemeReference(CodelistType code) throws URISyntaxException, TypeValueNotFoundException {
        if (code == null) {
            return null;
        }
        ItemSchemeReferenceBase srt2 = new ItemSchemeReferenceBase(toItemSchemeRefBaseType(code), code.getUrn());
        return srt2;
    }

    public ItemSchemeRefBase toItemSchemeRefBaseType(CodelistType codelist) throws TypeValueNotFoundException {
        ItemSchemeRefBase ref2;
        ref2 = new ItemSchemeRefBase(codelist.getAgencyID(), codelist.getId(), codelist.getVersion(), ItemSchemeTypeCodelistType.CODELIST, ItemSchemePackageTypeCodelistType.CODELIST);
        return ref2;
    }

    public AttributeListType toAttributeList(org.sdmx.resources.sdmxml.schemas.v20.structure.AttributeType[] a1) throws TypeValueNotFoundException, URISyntaxException {
        AttributeListType atts = new AttributeListType();
        List<AttributeType> a2 = new ArrayList<AttributeType>();
        for (int i = 0; i < a1.length; i++) {
            a2.add(toAttributeType(a1[i]));
        }
        atts.setAttributes(a2);
        return atts;

    }

    public AttributeType toAttributeType(org.sdmx.resources.sdmxml.schemas.v20.structure.AttributeType a1) throws TypeValueNotFoundException, URISyntaxException {
        AttributeType a2 = new AttributeType();
        ConceptSchemeType cscheme = getConceptScheme(a1);
        ConceptType concept = getConcept(cscheme, a1);

        CodelistType code = getCodelist(a1);
        if (concept != null) {
            a2.setConceptIdentity(toConceptReference(cscheme, concept));
            //a2.setId(concept.getId());
            if (a1.getAssignmentStatus() != null) {
                a2.setAssignmentStatus(UsageStatusType.fromString(a1.getAssignmentStatus().toString()));
            } else {
                // There is not default assignment status set in schema, 
                // this is a required attribute!! but it should ben safe to put this in;
                a2.setAssignmentStatus(UsageStatusType.CONDITIONAL);
            }
        }
        if (code != null) {
            a2.setLocalRepresentation(toLocalRepresentation(code, toTextFormatType(a1.getTextFormat())));
            a2.getLocalRepresentation().setEnumeration(toItemSchemeReference(code));
        }
        AttributeRelationshipType rel = new AttributeRelationshipType();
        LocalPrimaryMeasureRef ref = new LocalPrimaryMeasureRef(new IDType("OBS_VALUE"));
        LocalPrimaryMeasureReference reference = new LocalPrimaryMeasureReference(ref);
        rel.setPrimaryMeasure(reference);
        a2.setRelationshipType(rel);
        return a2;
    }

    public TextFormatType toTextFormatType(org.sdmx.resources.sdmxml.schemas.v20.structure.TextFormatType tft1) throws TypeValueNotFoundException {
        if (tft1 == null) {
            return null;
        }
        TextFormatType tft2 = new TextFormatType();
        if (tft1.getDecimals() != null) {
            tft2.setDecimals(toPositiveInteger(tft1.getDecimals().intValue()));
        }
        if (tft1.isSetEndValue()) {
            tft2.setEndValue(tft1.getEndValue());
        }
        if (tft1.isSetIsSequence()) {
            tft2.setSequence(tft1.getIsSequence());
            if (tft1.isSetInterval()) {
                tft2.setInterval(tft1.getInterval());
            }
        }
        if (tft1.getMaxLength() != null) {
            tft2.setMaxLength(toPositiveInteger(tft1.getMaxLength().intValue()));
        }
        if (tft1.getPattern() != null) {
            tft2.setPattern(tft1.getPattern());
        }
        if (tft1.isSetStartValue()) {
            tft2.setStartValue(tft1.getStartValue());
        }
        if (tft1.getTextType() != null) {
            tft2.setTextType(DataType.fromStringWithException(tft1.getTextType().toString()));
        }
        if (tft1.isSetTimeInterval()) {
            tft2.setTimeInterval(toDuration(tft1.getTimeInterval()));
        }
        if (tft1.getTextType() != null) {
            tft2.setTextType(DataType.fromString(tft1.getTextType().toString()));
        }
        return tft2;
    }

    public positiveInteger toPositiveInteger(int i) {
        return new positiveInteger(i);
    }

    public StandardTimePeriodType toStandardTimePeriod(Object time) {
        System.out.println("DO ME:Sdmx20Tools.toStandardTimePeriod!!!");
        System.out.println("EndTime=" + time);
        return null;
    }

    public duration toDuration(GDuration timeInterval) {
        System.out.println("DO ME:Sdmx20Tools.toDuration(GDuration)!!!");
        return null;
        //timeInterval.get
    }

    public BaseHeaderType toHeaderType(org.sdmx.resources.sdmxml.schemas.v20.message.HeaderType header) throws URISyntaxException {
        if (header == null) {
            return null;
        }
        BaseHeaderType header2 = new BaseHeaderType();
        // No DataProvider in SDMX 2.0
        //header2.setDataProvider(toDataProviderReferenceType(header.get));
        header2.setDataSetAction(toActionType(header.getDataSetAction()));
        header2.setDataSetID(toDataSetIDTypeList(header.getDataSetID()));
        // No Embargodate in sdmx 2.0
        //header2.setEmbargoDate(toDateTime(header.getEmbargoDate()));
        header2.setExtracted(toDateTime(header.getExtracted()));
        header2.setId(header.getID());
        header2.setNames(toNames(header.getNameArray()));
        header2.setPrepared(toHeaderTimeType(header.getPrepared()));
        header2.setReceivers(toPartyTypeList(header.getReceiverArray()));
        header2.setReportingBegin(toObservationalTimePeriodType(header.getReportingBegin()));
        header2.setReportingEnd(toObservationalTimePeriodType(header.getReportingEnd()));
        header2.setSender(partyListToSender(toPartyTypeList(header.getSenderArray())));
        header2.setSource(toTextType(header.getSourceArray()));
// Data Structure        
        PayloadStructureType pst = new PayloadStructureType();
        pst.setStructureID(toID(header.getDataSetID()));
        StructureRefBase ref = new StructureRefBase(toNestedNCNameIDType(header.getKeyFamilyAgency()), toIDType(header.getKeyFamilyRef()), Version.ONE, ObjectTypeCodelistType.STRUCTURESET, PackageTypeCodelistType.DATASTRUCTURE);
        StructureReferenceBase reference = new StructureReferenceBase(ref, null);
        pst.setStructure(reference);
        List<PayloadStructureType> payload = new ArrayList<PayloadStructureType>();
        payload.add(pst);
        header2.setStructures(payload);

        header2.setTest(header.getTest());

        return header2;
    }

    public ActionType toActionType(org.sdmx.resources.sdmxml.schemas.v20.common.ActionType.Enum at) {
        if (at == null) {
            return null;
        }
        return ActionType.fromString(at.toString());
    }

    public List<IDType> toIDTypeList(String[] idtypes) {
        List<IDType> list = new ArrayList<IDType>();
        for (int i = 0; i < idtypes.length; i++) {
            list.add(toIDType(idtypes[i]));
        }
        return list;
    }

    public List<IDType> toDataSetIDTypeList(String idtype) {
        List<IDType> list = new ArrayList<IDType>();
        if (idtype == null) {
            return list;
        }
        list.add(toIDType(idtype));
        return list;
    }

    public DateTime toDateTime(Calendar c) {
        if (c == null) {
            return null;
        }
        return new DateTime(c);
    }

    public sdmx.xml.DateType toDateType(Calendar c) {
        if (c == null) {
            return null;
        }
        return new DateType(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DATE));
    }

    public HeaderTimeType toHeaderTimeType(Calendar c) {
        HeaderTimeType htt = new HeaderTimeType();
        htt.setDate(toDateTime(c));
        return htt;
    }

    public List<PartyType> toPartyTypeList(org.sdmx.resources.sdmxml.schemas.v20.message.PartyType[] receiverArray) throws URISyntaxException {
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

    public List<ContactType> toContactList(org.sdmx.resources.sdmxml.schemas.v20.message.ContactType[] contactArray) throws URISyntaxException {
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
            list.add(ct);
        }
        return list;
    }

    public List<String> toStringList(String[] emailArray) {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < emailArray.length; i++) {
            list.add(emailArray[i]);
        }
        return list;
    }

    public List<anyURI> toAnyURIList(String[] uriArray) throws URISyntaxException {
        List<anyURI> list = new ArrayList<anyURI>();
        for (int i = 0; i < uriArray.length; i++) {
            list.add(toAnyURI(uriArray[i]));
        }
        return list;
    }

    public ObservationalTimePeriodType toObservationalTimePeriodType(Object reportingEnd) {
        return null;
    }

    public SenderType partyListToSender(List<PartyType> list) {
        if (list.size() == 0) {
            return null;
        }
        SenderType pt = new SenderType();
        pt.setId(list.get(0).getId());
        pt.setNames(list.get(0).getNames());
        pt.setContacts(list.get(0).getContacts());
        return pt;
    }

    public ID toID(String s) {
        if (s == null) {
            return null;
        }
        return new ID(s);
    }

    public MeasureListType toMeasureList(ComponentsType c1) throws TypeValueNotFoundException, URISyntaxException {
        if (c1 == null) {
            return null;
        }
        //System.out.println("Measures="+measurelist.getMeasures().size());
        if (c1.getPrimaryMeasure() != null) {
            MeasureListType measurelist = new MeasureListType();
            measurelist.setPrimaryMeasure(toPrimaryMeasure(c1.getPrimaryMeasure()));
            return measurelist;
        }
        //System.out.println("Measures="+measurelist.getMeasures().size());
        return null;

    }

    public PrimaryMeasure toPrimaryMeasure(PrimaryMeasureType pm1) throws TypeValueNotFoundException, URISyntaxException {
        PrimaryMeasure pm2 = new PrimaryMeasure();
        ConceptSchemeType cscheme = getConceptScheme(pm1);
        ConceptType concept = getConcept(cscheme, pm1);
        CodelistType code = getCodelist(pm1);
        if (concept != null) {
            pm2.setConceptIdentity(toConceptReference(cscheme, concept));
            pm2.setId(concept.getId());
        }
        pm2.setLocalRepresentation(toLocalRepresentation(code, toTextFormatType(pm1.getTextFormat())));
        return pm2;
    }

    public TimeDimensionType toTimeDimension(org.sdmx.resources.sdmxml.schemas.v20.structure.TimeDimensionType td1) throws TypeValueNotFoundException, URISyntaxException {
        if (td1 == null) {
            return null;
        }
        TimeDimensionType td2 = new TimeDimensionType();
        ConceptSchemeType cscheme = getConceptScheme(td1);
        ConceptType concept = getConcept(cscheme, td1);
        CodelistType code = getCodelist(td1);
        if (concept != null) {
            td2.setConceptIdentity(toConceptReference(cscheme, concept));
            td2.setId(concept.getId());
        } else {
            //System.out.println("Time Dimension Concept Is Null");
        }

        if (code != null) {
            td2.setLocalRepresentation(toLocalRepresentation(code, toTextFormatType(td1.getTextFormat())));
            ComponentUtil.getLocalRepresentation(td2).setEnumeration(toItemSchemeReference(code));
        }
        if (td1.getTextFormat() == null) {
            TextFormatType tf = new TextFormatType();
            tf.setTextType(DataType.OBSERVATIONAL_TIMEPERIOD);
            td2.setLocalRepresentation(toLocalRepresentation(tf));
        } else {
            td2.setLocalRepresentation(toLocalRepresentation(toTextFormatType(td1.getTextFormat())));
        }
        return td2;
    }

    public ConceptSchemeType getConceptScheme(org.sdmx.resources.sdmxml.schemas.v20.structure.DimensionType dim) {
        Logger.getLogger("sdmx").log(Level.FINE, "Sdmx20StructureReaderTools:getConceptScheme DimensionType " + dim.getConceptRef() + ":" + dim.getConceptAgency() + ":" + dim.getConceptSchemeRef() + ":" + dim.getConceptVersion());
        if ((dim.getConceptSchemeAgency() != null || dim.getConceptAgency() != null) && dim.getConceptSchemeRef() != null && dim.getConceptRef() != null) {
            NestedNCNameID csa = new NestedNCNameID(dim.getConceptSchemeAgency() == null ? dim.getConceptAgency() : dim.getConceptSchemeAgency());
            IDType csi = new IDType(dim.getConceptSchemeRef());
            Version version = dim.getConceptVersion() == null ? null : new Version(dim.getConceptVersion());
            ConceptSchemeReference ref = ConceptSchemeReference.create(csa, csi, version);
            return registry.find(ref);
        } else if (dim.getConceptSchemeRef() != null && dim.getConceptRef() != null) {
            NestedNCNameID csa = currentKeyFamilyAgency;
            IDType csi = new IDType(dim.getConceptSchemeRef());
            Version version = dim.getConceptVersion() == null ? null : new Version(dim.getConceptVersion());
            ConceptSchemeReference ref = ConceptSchemeReference.create(csa, csi, version);
            return registry.find(ref);
        } else if (dim.getConceptRef() != null && dim.getConceptAgency() == null) {
            NestedNCNameID csa = currentKeyFamilyAgency;
            IDType csi = new IDType(conceptSchemeName);
            Version version = dim.getConceptVersion() == null ? null : new Version(dim.getConceptVersion());
            ConceptSchemeReference ref = ConceptSchemeReference.create(csa, csi, version);
            ConceptSchemeType cst = registry.find(ref);
            ConceptType ct = cst != null ? cst.findConcept(new IDType(dim.getConceptRef())) : null;
            if (ct == null) {
                ct = findConcept(dim.getConceptRef());
                if (ct == null) {
                    return null;
                }
                ConceptSchemeReference ref2 = ConceptSchemeReference.create(ct.getAgencyID(), new IDType(conceptSchemeName), version);
                cst = registry.find(ref2);
            } else {
                ConceptSchemeReference ref2 = ConceptSchemeReference.create(mainAgencyId, new IDType(conceptSchemeName), version);
                cst = registry.find(ref2);
            }
            return cst;
        } else if (dim.getConceptRef() != null && dim.getConceptAgency() != null) {
            NestedNCNameID csa = new NestedNCNameID(dim.getConceptAgency());
            IDType csi = new IDType(conceptSchemeName);
            ConceptSchemeReference ref = ConceptSchemeReference.create(csa, csi, Version.ONE);
            ConceptSchemeType cst = registry.find(ref);
            return cst;
        } else {
            return null;
        }
    }

    public ConceptType getConcept(ConceptSchemeType scheme, org.sdmx.resources.sdmxml.schemas.v20.structure.DimensionType dim) {
        //Logger.getLogger("sdmx").log(Level.WARNING, "Sdmx20StructureReaderTools:getConcept " + dim.getConceptRef());
        if (scheme != null) {
            return scheme.findConcept(dim.getConceptRef());
        } else {
            //System.out.println("scheme==null!!!"+dim.getConceptRef());
            return null;
        }
    }

    public ConceptSchemeType getConceptScheme(org.sdmx.resources.sdmxml.schemas.v20.structure.TimeDimensionType dim) {
        Logger.getLogger("sdmx").log(Level.FINE, "Sdmx20StructureReaderTools:getConceptScheme DimensionType " + dim.getConceptRef() + ":" + dim.getConceptAgency() + ":" + dim.getConceptSchemeRef() + ":" + dim.getConceptVersion());
        if ((dim.getConceptSchemeAgency() != null || dim.getConceptAgency() != null) && dim.getConceptSchemeRef() != null && dim.getConceptRef() != null) {
            NestedNCNameID csa = new NestedNCNameID(dim.getConceptSchemeAgency() == null ? dim.getConceptAgency() : dim.getConceptSchemeAgency());
            IDType csi = new IDType(dim.getConceptSchemeRef());
            Version version = dim.getConceptVersion() == null ? null : new Version(dim.getConceptVersion());
            ConceptSchemeReference ref = ConceptSchemeReference.create(csa, csi, version);
            return registry.find(ref);
        } else if (dim.getConceptSchemeRef() != null && dim.getConceptRef() != null) {
            NestedNCNameID csa = currentKeyFamilyAgency;
            IDType csi = new IDType(dim.getConceptSchemeRef());
            Version version = dim.getConceptVersion() == null ? null : new Version(dim.getConceptVersion());
            ConceptSchemeReference ref = ConceptSchemeReference.create(csa, csi, version);
            return registry.find(ref);
        } else if (dim.getConceptRef() != null && dim.getConceptAgency() == null) {
            NestedNCNameID csa = currentKeyFamilyAgency;
            IDType csi = new IDType(conceptSchemeName);
            Version version = dim.getConceptVersion() == null ? null : new Version(dim.getConceptVersion());
            ConceptSchemeReference ref = ConceptSchemeReference.create(csa, csi, version);
            ConceptSchemeType cst = registry.find(ref);
            ConceptType ct = cst != null ? cst.findConcept(new IDType(dim.getConceptRef())) : null;
            if (ct == null) {
                ct = findConcept(dim.getConceptRef());
                if (ct == null) {
                    return null;
                }
                ConceptSchemeReference ref2 = ConceptSchemeReference.create(ct.getAgencyID(), new IDType(conceptSchemeName), version);
                cst = registry.find(ref2);
            } else {
                ConceptSchemeReference ref2 = ConceptSchemeReference.create(mainAgencyId, new IDType(conceptSchemeName), version);
                cst = registry.find(ref2);
            }
            return cst;
        } else if (dim.getConceptRef() != null && dim.getConceptAgency() != null) {
            NestedNCNameID csa = new NestedNCNameID(dim.getConceptAgency());
            IDType csi = new IDType(conceptSchemeName);
            ConceptSchemeReference ref = ConceptSchemeReference.create(csa, csi, Version.ONE);
            ConceptSchemeType cst = registry.find(ref);
            return cst;
        } else {
            return null;
        }
    }

    public ConceptType getConcept(ConceptSchemeType scheme, org.sdmx.resources.sdmxml.schemas.v20.structure.TimeDimensionType dim) {
        if (scheme != null) {
            return scheme.findConcept(dim.getConceptRef());
        } else {
            return null;
        }
    }

    public ConceptSchemeType getConceptScheme(org.sdmx.resources.sdmxml.schemas.v20.structure.PrimaryMeasureType dim) {
        Logger.getLogger("sdmx").log(Level.FINE, "Sdmx20StructureReaderTools:getConceptScheme DimensionType " + dim.getConceptRef() + ":" + dim.getConceptAgency() + ":" + dim.getConceptSchemeRef() + ":" + dim.getConceptVersion());
        if ((dim.getConceptSchemeAgency() != null || dim.getConceptAgency() != null) && dim.getConceptSchemeRef() != null && dim.getConceptRef() != null) {
            NestedNCNameID csa = new NestedNCNameID(dim.getConceptSchemeAgency() == null ? dim.getConceptAgency() : dim.getConceptSchemeAgency());
            IDType csi = new IDType(dim.getConceptSchemeRef());
            Version version = dim.getConceptVersion() == null ? null : new Version(dim.getConceptVersion());
            ConceptSchemeReference ref = ConceptSchemeReference.create(csa, csi, version);
            return registry.find(ref);
        } else if (dim.getConceptSchemeRef() != null && dim.getConceptRef() != null) {
            NestedNCNameID csa = currentKeyFamilyAgency;
            IDType csi = new IDType(dim.getConceptSchemeRef());
            Version version = dim.getConceptVersion() == null ? null : new Version(dim.getConceptVersion());
            ConceptSchemeReference ref = ConceptSchemeReference.create(csa, csi, version);
            return registry.find(ref);
        } else if (dim.getConceptRef() != null && dim.getConceptAgency() == null) {
            NestedNCNameID csa = currentKeyFamilyAgency;
            IDType csi = new IDType(conceptSchemeName);
            Version version = dim.getConceptVersion() == null ? null : new Version(dim.getConceptVersion());
            ConceptSchemeReference ref = ConceptSchemeReference.create(csa, csi, version);
            ConceptSchemeType cst = registry.find(ref);
            ConceptType ct = cst != null ? cst.findConcept(new IDType(dim.getConceptRef())) : null;
            if (ct == null) {
                ct = findConcept(dim.getConceptRef());
                if (ct == null) {
                    return null;
                }
                ConceptSchemeReference ref2 = ConceptSchemeReference.create(ct.getAgencyID(), new IDType(conceptSchemeName), version);
                cst = registry.find(ref2);
            } else {
                ConceptSchemeReference ref2 = ConceptSchemeReference.create(mainAgencyId, new IDType(conceptSchemeName), version);
                cst = registry.find(ref2);
            }
            return cst;
        } else if (dim.getConceptRef() != null && dim.getConceptAgency() != null) {
            NestedNCNameID csa = new NestedNCNameID(dim.getConceptAgency());
            IDType csi = new IDType(conceptSchemeName);
            ConceptSchemeReference ref = ConceptSchemeReference.create(csa, csi, Version.ONE);
            ConceptSchemeType cst = registry.find(ref);
            return cst;
        } else {
            return null;
        }
    }

    public ConceptType getConcept(ConceptSchemeType scheme, org.sdmx.resources.sdmxml.schemas.v20.structure.PrimaryMeasureType dim) {
        if (scheme != null) {
            return scheme.findConcept(dim.getConceptRef());
        } else {
            return null;
        }
    }

    public ConceptSchemeType getConceptScheme(org.sdmx.resources.sdmxml.schemas.v20.structure.AttributeType dim) {
        Logger.getLogger("sdmx").log(Level.FINE, "Sdmx20StructureReaderTools:getConceptScheme DimensionType " + dim.getConceptRef() + ":" + dim.getConceptAgency() + ":" + dim.getConceptSchemeRef() + ":" + dim.getConceptVersion());
        if ((dim.getConceptSchemeAgency() != null || dim.getConceptAgency() != null) && dim.getConceptSchemeRef() != null && dim.getConceptRef() != null) {
            NestedNCNameID csa = new NestedNCNameID(dim.getConceptSchemeAgency() == null ? dim.getConceptAgency() : dim.getConceptSchemeAgency());
            IDType csi = new IDType(dim.getConceptSchemeRef());
            Version version = dim.getConceptVersion() == null ? null : new Version(dim.getConceptVersion());
            ConceptSchemeReference ref = ConceptSchemeReference.create(csa, csi, version);
            return registry.find(ref);
        } else if (dim.getConceptSchemeRef() != null && dim.getConceptRef() != null) {
            NestedNCNameID csa = currentKeyFamilyAgency;
            IDType csi = new IDType(dim.getConceptSchemeRef());
            Version version = dim.getConceptVersion() == null ? null : new Version(dim.getConceptVersion());
            ConceptSchemeReference ref = ConceptSchemeReference.create(csa, csi, version);
            return registry.find(ref);
        } else if (dim.getConceptRef() != null && dim.getConceptAgency() == null) {
            NestedNCNameID csa = currentKeyFamilyAgency;
            IDType csi = new IDType(conceptSchemeName);
            Version version = dim.getConceptVersion() == null ? null : new Version(dim.getConceptVersion());
            ConceptSchemeReference ref = ConceptSchemeReference.create(csa, csi, version);
            ConceptSchemeType cst = registry.find(ref);
            ConceptType ct = cst != null ? cst.findConcept(new IDType(dim.getConceptRef())) : null;
            if (ct == null) {
                ct = findConcept(dim.getConceptRef());
                if (ct == null) {
                    return null;
                }
                ConceptSchemeReference ref2 = ConceptSchemeReference.create(ct.getAgencyID(), new IDType(conceptSchemeName), version);
                cst = registry.find(ref2);
            } else {
                ConceptSchemeReference ref2 = ConceptSchemeReference.create(mainAgencyId, new IDType(conceptSchemeName), version);
                cst = registry.find(ref2);
            }
            return cst;
        } else if (dim.getConceptRef() != null && dim.getConceptAgency() != null) {
            NestedNCNameID csa = new NestedNCNameID(dim.getConceptAgency());
            IDType csi = new IDType(conceptSchemeName);
            ConceptSchemeReference ref = ConceptSchemeReference.create(csa, csi, Version.ONE);
            ConceptSchemeType cst = registry.find(ref);
            return cst;
        } else {
            return null;
        }
    }

    public ConceptType getConcept(ConceptSchemeType scheme, org.sdmx.resources.sdmxml.schemas.v20.structure.AttributeType dim) {
        if (scheme != null) {
            return scheme.findConcept(dim.getConceptRef());
        } else {
            return null;
        }
    }

    public ConceptType findConcept(String name) {
        // Infer agencyId from keyfamilyId
        NestedNCNameID agency = currentKeyFamilyAgency;
        IDType id = new IDType(name);
        ConceptReference ref = ConceptReference.create(agency, null, null, id);
        ConceptType ct = registry.find(ref);
        if (ct == null) {
            // We've really failed somewhere... 
            // now all we can use is the ConceptRef!!
            ref = ConceptReference.create(null, null, null, id);
            ct = registry.find(ref);
        }
        //System.out.println("Trying to find concept:" + name + " returning:" + ct);
        return ct;
    }

    public CodelistType getCodelist(org.sdmx.resources.sdmxml.schemas.v20.structure.DimensionType dim) {
        if (dim.getCodelist() == null) {
            return null;
        }
        CodelistType code = null;
        if (dim.getCodelistAgency() == null && dim.getCodelistVersion() == null) {
            // All we have is a codelist name
            CodelistReference ref = CodelistReference.create(mainAgencyId, new IDType(dim.getCodelist()), null);
            code = registry.find(ref);
        } else if (dim.getCodelistAgency() != null && dim.getCodelistVersion() != null) {
            CodelistReference ref = CodelistReference.create(new NestedNCNameID(dim.getCodelistAgency()), new IDType(dim.getCodelist()), new Version(dim.getCodelistVersion()));
            code = registry.find(ref);
        } else if (dim.getCodelistAgency() != null && dim.getCodelistVersion() == null) {
            // Only codelist and codelistAgency
            CodelistReference ref = CodelistReference.create(new NestedNCNameID(dim.getCodelistAgency()), new IDType(dim.getCodelist()), null);
            code = registry.find(ref);
        }
        return code;
    }

    public CodelistType getCodelist(org.sdmx.resources.sdmxml.schemas.v20.structure.AttributeType dim) {
        if (dim.getCodelist() == null) {
            return null;
        }
        CodelistType code = null;
        if (dim.getCodelistAgency() == null && dim.getCodelistVersion() == null) {
            // All we have is a codelist name
            CodelistReference ref = CodelistReference.create(mainAgencyId, new IDType(dim.getCodelist()), null);
            code = registry.find(ref);
        } else if (dim.getCodelistAgency() != null && dim.getCodelistVersion() != null) {
            CodelistReference ref = CodelistReference.create(new NestedNCNameID(dim.getCodelistAgency()), new IDType(dim.getCodelist()), new Version(dim.getCodelistVersion()));
            code = registry.find(ref);
        } else if (dim.getCodelistAgency() != null && dim.getCodelistVersion() == null) {
            // Only codelist and codelistAgency
            CodelistReference ref = CodelistReference.create(new NestedNCNameID(dim.getCodelistAgency()), new IDType(dim.getCodelist()), null);
            code = registry.find(ref);
        }
        return code;
    }

    public CodelistType getCodelist(org.sdmx.resources.sdmxml.schemas.v20.structure.TimeDimensionType dim) {
        if (dim.getCodelist() == null) {
            return null;
        }
        CodelistType code = null;
        if (dim.getCodelistAgency() == null && dim.getCodelistVersion() == null) {
            // All we have is a codelist name
            CodelistReference ref = CodelistReference.create(mainAgencyId, new IDType(dim.getCodelist()), null);
            code = registry.find(ref);
        } else if (dim.getCodelistAgency() != null && dim.getCodelistVersion() != null) {
            CodelistReference ref = CodelistReference.create(new NestedNCNameID(dim.getCodelistAgency()), new IDType(dim.getCodelist()), new Version(dim.getCodelistVersion()));
            code = registry.find(ref);
        } else if (dim.getCodelistAgency() != null && dim.getCodelistVersion() == null) {
            // Only codelist and codelistAgency
            CodelistReference ref = CodelistReference.create(new NestedNCNameID(dim.getCodelistAgency()), new IDType(dim.getCodelist()), null);
            code = registry.find(ref);
        }
        return code;
    }

    public CodelistType getCodelist(org.sdmx.resources.sdmxml.schemas.v20.structure.PrimaryMeasureType dim) {
        if (dim.getCodelist() == null) {
            return null;
        }
        CodelistType code = null;
        if (dim.getCodelistAgency() == null && dim.getCodelistVersion() == null) {
            // All we have is a codelist name
            CodelistReference ref = CodelistReference.create(mainAgencyId, new IDType(dim.getCodelist()), null);
            code = registry.find(ref);
        } else if (dim.getCodelistAgency() != null && dim.getCodelistVersion() != null) {
            CodelistReference ref = CodelistReference.create(new NestedNCNameID(dim.getCodelistAgency()), new IDType(dim.getCodelist()), new Version(dim.getCodelistVersion()));
            code = registry.find(ref);
        } else if (dim.getCodelistAgency() != null && dim.getCodelistVersion() == null) {
            // Only codelist and codelistAgency
            CodelistReference ref = CodelistReference.create(new NestedNCNameID(dim.getCodelistAgency()), new IDType(dim.getCodelist()), null);
            code = registry.find(ref);
        }
        return code;
    }

    public LocalItemReference toLocalItemReference(String parentCode, ItemTypeCodelistType obs, ItemSchemePackageTypeCodelistType pack) {
        LocalItemRefBase ref = new LocalItemRefBase(new IDType(parentCode), obs, pack);
        LocalItemReference reference = new LocalItemReference(ref);
        return reference;
    }

    public StructureType parseRegistry(RegistryInterfaceDocument regDoc) throws TypeValueNotFoundException {
        if (!(registry instanceof DoubleRegistry)) {
            registry = new DoubleRegistry(struct, registry);
        }
        sd.setStructures(struct);
        try {
            struct.setCodelists(toCodelists(regDoc.getRegistryInterface().getQueryStructureResponse().getCodeLists()));
            struct.setConcepts(toConcepts(regDoc.getRegistryInterface().getQueryStructureResponse().getConcepts()));
            struct.setDataStructures(toDataStructures(regDoc.getRegistryInterface().getQueryStructureResponse().getKeyFamilies()));
            struct.setDataflows(toDataflows(regDoc.getRegistryInterface().getQueryStructureResponse().getDataflows()));
            sd.setHeader(toHeaderType(regDoc.getRegistryInterface().getHeader()));
        } catch (URISyntaxException ex) {
            //System.out.println("Exception!");
            ex.printStackTrace();
            Logger
                    .getLogger(Sdmx20StructureReaderTools.class
                            .getName()).log(Level.SEVERE, null, ex);
        }
        sd.setStructures(struct);
        return sd;
    }

    public DataflowsType toDataflows(org.sdmx.resources.sdmxml.schemas.v20.structure.DataflowsType dataflows) throws URISyntaxException {
        if (dataflows == null) {
            return null;
        }
        DataflowsType flows = new DataflowsType();
        List<DataflowType> flowsList = new ArrayList<DataflowType>();
        for (int i = 0; i < dataflows.sizeOfDataflowArray(); i++) {
            flowsList.add(toDataflow(dataflows.getDataflowArray(i)));
        }
        flows.setDataflows(flowsList);
        return flows;
    }

    public DataflowType toDataflow(org.sdmx.resources.sdmxml.schemas.v20.structure.DataflowType df) throws URISyntaxException {
        DataflowType dataflow = new DataflowType();
        dataflow.setNames(toNames(df.getNameArray()));
        dataflow.setDescriptions(toDescriptions(df.getDescriptionArray()));
        dataflow.setAnnotations(toAnnotations(df.getAnnotations()));
        dataflow.setExternalReference(df.getIsExternalReference());
        dataflow.setFinal(df.getIsFinal());
        dataflow.setAgencyID(toNestedNCNameIDType(df.getAgencyID()));
        dataflow.setId(toIDType(df.getId()));
        dataflow.setVersion(toVersionType(df.getVersion()));
        dataflow.setStructure(toDataStructureRefeference(df.getKeyFamilyRef()));
        dataflow.setUri(toAnyURI(df.getUri()));
        dataflow.setUrn(toAnyURI(df.getUrn()));
        //dataflow.setValidFrom(toDateTime(df.getValidFrom()));
        //dataflow.setValidTo(toDateTime(df.getValidTo()));
        return dataflow;

    }

    public StructureReferenceBase toDataStructureRefeference(KeyFamilyRefType keyFamilyRef) throws URISyntaxException {
        DataStructureRef ref = new DataStructureRef(toNestedNCNameIDType(keyFamilyRef.getKeyFamilyAgencyID()), toIDType(keyFamilyRef.getKeyFamilyID()), toVersionType(keyFamilyRef.getVersion()));
        DataStructureReference reference = new DataStructureReference(ref, toAnyURI(keyFamilyRef.getURN()));
        return reference;
    }
}

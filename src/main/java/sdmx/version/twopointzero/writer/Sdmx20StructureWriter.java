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
package sdmx.version.twopointzero.writer;

import sdmx.version.twopointone.writer.*;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.impl.values.XmlAnyTypeImpl;
import org.sdmx.resources.sdmxml.schemas.v20.common.AnnotationType;
import org.sdmx.resources.sdmxml.schemas.v20.common.AnnotationsType;
import org.sdmx.resources.sdmxml.schemas.v20.common.TextType;
import org.sdmx.resources.sdmxml.schemas.v20.message.ContactType;
import org.sdmx.resources.sdmxml.schemas.v20.message.HeaderType;
import org.sdmx.resources.sdmxml.schemas.v20.message.PartyType;
import org.sdmx.resources.sdmxml.schemas.v20.message.StructureDocument;
import org.sdmx.resources.sdmxml.schemas.v20.message.StructureType;
import org.sdmx.resources.sdmxml.schemas.v20.query.CodelistType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.CodeListType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.CodeListsType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.CodeType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.ComponentsType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.ConceptSchemeType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.ConceptType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.ConceptsType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.DimensionType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.KeyFamiliesType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.KeyFamilyType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.PrimaryMeasureType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.TextFormatType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.TextTypeType;
import org.sdmx.resources.sdmxml.schemas.v20.structure.TimeDimensionType;
import sdmx.common.Annotations;
import sdmx.common.DataType;
import sdmx.common.Name;
import sdmx.commonreferences.DataStructureReference;
import sdmx.message.BaseHeaderType;
import sdmx.message.SenderType;
import sdmx.structure.base.ComponentUtil;
import sdmx.structure.base.ItemType;
import sdmx.structure.datastructure.DataStructureComponents;
import sdmx.structure.datastructure.DataStructureType;
import sdmx.structure.datastructure.MeasureDimensionType;
import sdmx.structure.datastructure.SimpleDataStructureRepresentationType;
import sdmx.version.twopointone.Sdmx21StructureReaderTools;

public class Sdmx20StructureWriter {

    public static void write(sdmx.message.StructureType structure, OutputStream out) throws IOException {
        StructureDocument doc = toStructureDocument(structure);
        XmlOptions options = new XmlOptions();
        Map namespaces = new HashMap<String, String>();
        namespaces.put("mes", "http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message");
        namespaces.put("str", "http://www.SDMX.org/resources/SDMXML/schemas/v2_0/structure");
        namespaces.put("com", "http://www.SDMX.org/resources/SDMXML/schemas/v2_0/common");
        options.setSaveSuggestedPrefixes(namespaces);
        options.setSaveAggressiveNamespaces();
        options.setSavePrettyPrint();
        options.setSavePrettyPrintIndent(4);
        options.setUnsynchronized();
        options.setCharacterEncoding("ISO-8859-1");
        doc.save(out, options);
    }

    public static StructureDocument toStructureDocument(sdmx.message.StructureType structure) {
        StructureDocument doc = StructureDocument.Factory.newInstance();
        doc.setStructure(toStructureType(structure));
        return doc;
    }

    public static StructureType toStructureType(sdmx.message.StructureType structure1) {
        StructureType structure2 = StructureType.Factory.newInstance();
        HeaderType header2 = structure2.addNewHeader();
        toHeader(header2, structure1.getHeader());
        if (structure1.getStructures().getCodelists() != null) {
            CodeListsType codelists = CodeListsType.Factory.newInstance();
            toCodelistsType(codelists, structure1.getStructures().getCodelists());
            structure2.setCodeLists(codelists);
        }
        if (structure1.getStructures().getConcepts() != null) {
            ConceptsType concepts = ConceptsType.Factory.newInstance();
            toConceptsType(concepts, structure1.getStructures().getConcepts());
            structure2.setConcepts(concepts);
        }
        if( structure1.getStructures().getDataStructures()!=null) {
            KeyFamiliesType dst = KeyFamiliesType.Factory.newInstance();
            toKeyFamiliesType(dst,structure1.getStructures().getDataStructures());
            structure2.setKeyFamilies(dst);
        }
        return structure2;
    }

    private static void toHeader(HeaderType header2, BaseHeaderType header) {
        if (header.getExtracted() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(header.getExtracted().getDate());
            header2.setExtracted(cal);
        }
        if (header.getId() != null) {
            header2.setID(header.getId().toString());
        }
        header2.setNameArray(nameToTextTypeArray(header.getNames()));
        if (header.getPrepared() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(header.getPrepared().getDate().getDate());
            header2.setPrepared(cal);
        }
        header2.setTest(header.getTest().booleanValue());
        header2.setSenderArray(new PartyType[]{toPartyType(header.getSender())});
        header2.setReceiverArray(toPartyTypeArray(header.getReceivers()));
        // No Truncated Value in SDMX 2.1
        //header2.setTruncated(false);
    }

    private static TextType[] toTextTypeArray(List<sdmx.common.TextType> names) {
        List<TextType> result = new ArrayList<TextType>();
        for (sdmx.common.TextType n : names) {
            result.add(toTextType(n));
        }
        TextType[] nameArray = new TextType[result.size()];
        for (int i = 0; i < nameArray.length; i++) {
            nameArray[i] = result.get(i);
        }
        return nameArray;
    }

    private static TextType[] nameToTextTypeArray(List<sdmx.common.Name> names) {
        List<TextType> result = new ArrayList<TextType>();
        for (sdmx.common.TextType n : names) {
            result.add(toTextType(n));
        }
        TextType[] nameArray = new TextType[result.size()];
        for (int i = 0; i < nameArray.length; i++) {
            nameArray[i] = result.get(i);
        }
        return nameArray;
    }

    private static TextType[] descToTextTypeArray(List<sdmx.common.Description> names) {
        if (names == null) {
            return new TextType[]{};
        }
        List<TextType> result = new ArrayList<TextType>();
        for (sdmx.common.TextType n : names) {
            result.add(toTextType(n));
        }
        TextType[] nameArray = new TextType[result.size()];
        for (int i = 0; i < nameArray.length; i++) {
            nameArray[i] = result.get(i);
        }
        return nameArray;
    }

    private static TextType toTextType(sdmx.common.TextType n) {
        TextType tt = TextType.Factory.newInstance();
        tt.setLang(n.getLang());
        tt.setStringValue(n.getText());
        return tt;
    }

    private static PartyType toPartyType(sdmx.message.PartyType sender) {
        PartyType pt = PartyType.Factory.newInstance();
        pt.setId(sender.getId().toString());
        pt.setNameArray(nameToTextTypeArray(sender.getNames()));
        pt.setContactArray(toContactArray(sender.getContacts()));
        return pt;
    }

    private static ContactType[] toContactArray(List<sdmx.message.ContactType> contacts) {
        ContactType[] contactArray = new ContactType[contacts.size()];
        for (int i = 0; i < contactArray.length; i++) {
            contactArray[i] = toContactType(contacts.get(i));
        }
        return contactArray;
    }

    private static ContactType toContactType(sdmx.message.ContactType ct) {
        ContactType ct2 = ContactType.Factory.newInstance();
        ct2.setDepartmentArray(toTextTypeArray(ct.getDepartments()));
        if (ct.getEmails() != null) {
            ct2.setEmailArray(ct.getEmails().toArray(new String[]{}));
        }
        if (ct.getFaxes() != null) {
            ct2.setFaxArray(ct.getFaxes().toArray(new String[]{}));
        }
        if (ct.getNames() != null) {
            ct2.setNameArray(nameToTextTypeArray(ct.getNames()));
        }
        if (ct.getRoles() != null) {
            ct2.setRoleArray(toTextTypeArray(ct.getRoles()));
        }
        if (ct.getTelephones() != null) {
            ct2.setTelephoneArray(ct.getTelephones().toArray(new String[]{}));
        }
        if (ct.getUris() != null) {
            ct2.setURIArray(ct.getUris().toArray(new String[]{}));
        }
        if (ct.getX400s() != null) {
            ct2.setX400Array(ct.getX400s().toArray(new String[]{}));
        }
        return ct2;
    }

    private static PartyType[] toPartyTypeArray(List<sdmx.message.PartyType> receivers) {
        PartyType[] pta = new PartyType[receivers.size()];
        for (int i = 0; i < receivers.size(); i++) {
            pta[i] = toPartyType(receivers.get(i));
        }
        return pta;
    }

    private static CodeListsType toCodelistsType(CodeListsType cl2, sdmx.structure.CodelistsType cl) {
        CodeListType[] result = new CodeListType[cl.getCodelists().size()];
        for (int i = 0; i < cl.getCodelists().size(); i++) {
            CodeListType codelist = CodeListType.Factory.newInstance();
            result[i] = toCodeList(codelist, cl.getCodelists().get(i));
        }
        cl2.setCodeListArray(result);
        return cl2;
    }

    private static CodeListType toCodeList(CodeListType codelist2, sdmx.structure.codelist.CodelistType codelist) {
        codelist2.setId(codelist.getId().toString());
        codelist2.setAgencyID(codelist.getAgencyID().toString());
        codelist2.setVersion(codelist.getVersion().toString());
        codelist2.setNameArray(nameToTextTypeArray(codelist.getNames()));
        codelist2.setDescriptionArray(descToTextTypeArray(codelist.getDescriptions()));
        codelist2.setIsFinal(codelist.isFinal());
        codelist2.setIsExternalReference(codelist.isExternalReference());
        if (codelist.getUri() != null) {
            codelist2.setUri(codelist.getUri().toString());
        }
        if (codelist.getUrn() != null) {
            codelist2.setUrn(codelist.getUrn().toString());
        }
        if (codelist.getAnnotations() != null) {
            codelist2.setAnnotations(toAnnotations(codelist.getAnnotations()));
        }
        CodeType[] codes = new CodeType[codelist.size()];
        for (int i = 0; i < codes.length; i++) {
            codes[i] = toCode(codelist.getCode(i));
        }
        codelist2.setCodeArray(codes);
        return codelist2;
    }

    private static AnnotationsType toAnnotations(Annotations annotations) {
        if (annotations == null) {
            return null;
        }
        AnnotationsType annot = AnnotationsType.Factory.newInstance();
        AnnotationType[] annots = new AnnotationType[annotations.size()];
        for (int i = 0; i < annots.length; i++) {
            annots[i] = toAnnotation(annotations.getAnnotation(i));
        }
        annot.setAnnotationArray(annots);
        return annot;
    }

    private static AnnotationType toAnnotation(sdmx.common.AnnotationType annotation) {
        AnnotationType annot = AnnotationType.Factory.newInstance();
        annot.setAnnotationTextArray(toTextTypeArray(annotation.getAnnotationText()));
        annot.setAnnotationTitle(annotation.getAnnotationTitle());
        annot.setAnnotationType(annotation.getAnnotationType());
        annot.setAnnotationURL(annotation.getAnnotationUrl());
        // ID Lost
        return annot;
    }

    private static CodeType toCode(sdmx.structure.codelist.CodeType code) {
        CodeType code2 = CodeType.Factory.newInstance();
        if (code.getAnnotations() != null) {
            code2.setAnnotations(toAnnotations(code.getAnnotations()));
        }
        code2.setDescriptionArray(nameToTextTypeArray(code.getNames()));
        if (code.getParent() != null) {
            code2.setParentCode(code.getParent().getId().toString());
        }
        if (code.getUrn() != null) {
            code2.setUrn(code.getUrn().toString());
        }
        code2.setValue(code.getId().toString());
        return code2;
    }

    private static void toConceptsType(ConceptsType concepts2, sdmx.structure.ConceptsType concepts) {
        List<ConceptType> standaloneConcepts = new ArrayList<ConceptType>();
        List<ConceptSchemeType> conceptSchemes = new ArrayList<ConceptSchemeType>();
        for (int i = 0; i < concepts.getConceptSchemes().size(); i++) {
            if (concepts.getConceptSchemes().get(i).getId().equals("STANDALONE_CONCEPT_SCHEME")) {
                sdmx.structure.concept.ConceptSchemeType cs = concepts.getConceptSchemes().get(i);
                for (int j = 0; j < cs.size(); j++) {
                    ConceptType ct = toConcept(cs.getConcept(j));
                    ct.setAgencyID(cs.getAgencyID().toString());
                    ct.setVersion(cs.getVersion().toString());
                    standaloneConcepts.add(ct);
                }
            } else {
                ConceptSchemeType cst2 = ConceptSchemeType.Factory.newInstance();
                sdmx.structure.concept.ConceptSchemeType cst = concepts.getConceptSchemes().get(i);
                cst2.setAgencyID(cst.getAgencyID().toString());
                cst2.setId(cst.getId().toString());
                cst2.setVersion(cst.getVersion().toString());
                cst2.setDescriptionArray(descToTextTypeArray(cst.getDescriptions()));
                cst2.setNameArray(nameToTextTypeArray(cst.getNames()));
                if( cst.isExternalReference()!=null){cst2.setIsExternalReference(cst.isExternalReference());}
                if( cst.isFinal()!=null){cst2.setIsFinal(cst.isFinal());}
                if( cst.getUri()!=null){cst2.setUri(cst.getUri().toString());}
                if( cst.getUrn()!=null){cst2.setUrn(cst.getUrn().toString());}
                cst2.setConceptArray(toConceptArray(cst.getItems()));
                conceptSchemes.add(cst2);
            }
        }
        ConceptType[] conceptsArray = new ConceptType[standaloneConcepts.size()];
        for(int i=0;i<conceptsArray.length;i++) {
            conceptsArray[i]=standaloneConcepts.get(i);
        }
        concepts2.setConceptArray(conceptsArray);
        ConceptSchemeType[] conceptSchemesArray = new ConceptSchemeType[conceptSchemes.size()];
        for(int i=0;i<conceptSchemesArray.length;i++) {
            conceptSchemesArray[i]=conceptSchemes.get(i);
        }
        concepts2.setConceptSchemeArray(conceptSchemesArray);

    }

    private static ConceptType[] toConceptArray(List<ItemType> items) {
        ConceptType[] concepts = new ConceptType[items.size()];
        for (int i = 0; i < concepts.length; i++) {
            concepts[i] = toConcept(items.get(i));
        }
        return concepts;
    }

    private static ConceptType toConcept(ItemType concept) {
        ConceptType ct = ConceptType.Factory.newInstance();
        ct.setNameArray(nameToTextTypeArray(concept.getNames()));
        ct.setDescriptionArray(descToTextTypeArray(concept.getDescriptions()));
        ct.setId(concept.getId().toString());
        return ct;
    }

    private static void toKeyFamiliesType(KeyFamiliesType dst, sdmx.structure.DataStructuresType dataStructures) {
        KeyFamilyType[] kfs = new KeyFamilyType[dataStructures.getDataStructures().size()];
        for(int i=0;i<kfs.length;i++) {
            KeyFamilyType kf = KeyFamilyType.Factory.newInstance();
            kfs[i]=toKeyFamily(kf,dataStructures.getDataStructures().get(i));
        }
        dst.setKeyFamilyArray(kfs);
    }

    private static KeyFamilyType toKeyFamily(KeyFamilyType kf2, DataStructureType kf) {
        kf2.setAgencyID(kf.getAgencyID().toString());
        kf2.setId(kf.getId().toString());
        if( kf.getVersion()!=null){kf2.setVersion(kf.getVersion().toString());}
        if( kf.getAnnotations()!=null){kf2.setAnnotations(toAnnotations(kf.getAnnotations()));}
        kf2.setDescriptionArray(descToTextTypeArray(kf.getDescriptions()));
        kf2.setNameArray(nameToTextTypeArray(kf.getNames()));
        kf2.setIsExternalReference(kf.isExternalReference());
        kf2.setIsFinal(kf.isFinal());
        if( kf.getUri()!=null){kf2.setUri(kf.getUri().toString());}
        if( kf.getUrn()!=null){kf2.setUrn(kf.getUrn().toString());}
        ComponentsType comps = ComponentsType.Factory.newInstance();
        kf2.setComponents(toComponentsType(comps,kf));
        return kf2;
    }

    private static ComponentsType toComponentsType(ComponentsType comps, DataStructureType kf) {
        comps.setDimensionArray(toDimensionArray(kf));
        comps.setTimeDimension(toTimeDimension(kf.getDataStructureComponents().getDimensionList().getTimeDimension()));
        comps.setPrimaryMeasure(toPrimaryMeasure(kf.getDataStructureComponents().getMeasureList().getPrimaryMeasure()));
        return comps;
    }

    private static DimensionType[] toDimensionArray(DataStructureType kf) {
        int size = kf.getDataStructureComponents().getDimensionList().size();
        if( kf.getDataStructureComponents().getDimensionList().getMeasureDimension()!=null) {
            size++;
        }
        DimensionType[] dimensions = new DimensionType[size];
        for(int i=0;i<kf.getDataStructureComponents().getDimensionList().size();i++) {
            dimensions[i]=toDimension(kf.getDataStructureComponents().getDimensionList().getDimension(i));
        }
        if( kf.getDataStructureComponents().getDimensionList().getMeasureDimension()!=null) {
            dimensions[dimensions.length-1]=toMeasureDimension(kf.getDataStructureComponents().getDimensionList().getMeasureDimension());
        }
        return dimensions;
    }

    private static DimensionType toDimension(sdmx.structure.datastructure.DimensionType dim) {
        DimensionType dim2 = DimensionType.Factory.newInstance();
        if( dim.getAnnotations()!=null)dim2.setAnnotations(toAnnotations(dim.getAnnotations()));
        if( ComponentUtil.getLocalRepresentation(dim).getEnumeration()!=null) {
            dim2.setCodelist(ComponentUtil.getLocalRepresentation(dim).getEnumeration().getMaintainableParentId().toString());
            dim2.setCodelistAgency(ComponentUtil.getLocalRepresentation(dim).getEnumeration().getAgencyId().toString());
            dim2.setCodelistVersion(ComponentUtil.getLocalRepresentation(dim).getEnumeration().getVersion().toString());
        }
        if( ComponentUtil.getLocalRepresentation(dim).getTextFormat()!=null ) {
            TextFormatType tft = TextFormatType.Factory.newInstance();
            tft.setDecimals(new BigInteger(Integer.toString(ComponentUtil.getLocalRepresentation(dim).getTextFormat().getDecimals().getValue())));
            tft.setEndValue(ComponentUtil.getLocalRepresentation(dim).getTextFormat().getEndValue());
            tft.setTextType(TextTypeType.Enum.forString(ComponentUtil.getLocalRepresentation(dim).getTextFormat().getTextType().toString()));
            dim2.setTextFormat(tft);
        }
        dim2.setConceptAgency(dim.getConceptIdentity().getAgencyId().toString());
        dim2.setConceptRef(dim.getConceptIdentity().getId().toString());
        dim2.setConceptVersion(dim.getConceptIdentity().getVersion().toString());
        if( !dim.getConceptIdentity().getMaintainableParentId().equals("STANDALONE_CONCEPT_SCHEME")){
            dim2.setConceptSchemeAgency(dim.getConceptIdentity().getAgencyId().toString());
            dim2.setConceptSchemeRef(dim.getConceptIdentity().getMaintainableParentId().toString());
        }
        return dim2;
    }

    private static DimensionType toMeasureDimension(MeasureDimensionType dim) {
        DimensionType dim2 = DimensionType.Factory.newInstance();
        if( dim.getAnnotations()!=null){dim2.setAnnotations(toAnnotations(dim.getAnnotations()));}
        if( ComponentUtil.getLocalRepresentation(dim).getEnumeration()!=null) {
            dim2.setCodelist(ComponentUtil.getLocalRepresentation(dim).getEnumeration().getMaintainableParentId().toString());
            dim2.setCodelistAgency(ComponentUtil.getLocalRepresentation(dim).getEnumeration().getAgencyId().toString());
            dim2.setCodelistVersion(ComponentUtil.getLocalRepresentation(dim).getEnumeration().getVersion().toString());
        }
        if( ComponentUtil.getLocalRepresentation(dim).getTextFormat()!=null ) {
            TextFormatType tft = TextFormatType.Factory.newInstance();
            tft.setDecimals(new BigInteger(Integer.toString(ComponentUtil.getLocalRepresentation(dim).getTextFormat().getDecimals().getValue())));
            tft.setEndValue(ComponentUtil.getLocalRepresentation(dim).getTextFormat().getEndValue());
            tft.setTextType(TextTypeType.Enum.forString(ComponentUtil.getLocalRepresentation(dim).getTextFormat().getTextType().toString()));
            dim2.setTextFormat(tft);
        }
        dim2.setConceptAgency(dim.getConceptIdentity().getAgencyId().toString());
        dim2.setConceptRef(dim.getConceptIdentity().getId().toString());
        dim2.setConceptVersion(dim.getConceptIdentity().getVersion().toString());
        if( !dim.getConceptIdentity().getMaintainableParentId().equals("STANDALONE_CONCEPT_SCHEME")){
            dim2.setConceptSchemeAgency(dim.getConceptIdentity().getAgencyId().toString());
            dim2.setConceptSchemeRef(dim.getConceptIdentity().getMaintainableParentId().toString());
        }
        dim2.setIsMeasureDimension(true);
        return dim2;
    }

    private static TimeDimensionType toTimeDimension(sdmx.structure.datastructure.TimeDimensionType dim) {
        TimeDimensionType dim2 = TimeDimensionType.Factory.newInstance();
        if( dim.getAnnotations()!=null)dim2.setAnnotations(toAnnotations(dim.getAnnotations()));
        if( ComponentUtil.getLocalRepresentation(dim).getEnumeration()!=null) {
            dim2.setCodelist(ComponentUtil.getLocalRepresentation(dim).getEnumeration().getMaintainableParentId().toString());
            dim2.setCodelistAgency(ComponentUtil.getLocalRepresentation(dim).getEnumeration().getAgencyId().toString());
            dim2.setCodelistVersion(ComponentUtil.getLocalRepresentation(dim).getEnumeration().getVersion().toString());
        }
        if( ComponentUtil.getLocalRepresentation(dim).getTextFormat()!=null ) {
            TextFormatType tft = TextFormatType.Factory.newInstance();
            if( ComponentUtil.getLocalRepresentation(dim).getTextFormat().getDecimals()!=null){tft.setDecimals(new BigInteger(Integer.toString(ComponentUtil.getLocalRepresentation(dim).getTextFormat().getDecimals().getValue())));}
            if( ComponentUtil.getLocalRepresentation(dim).getTextFormat().getEndValue()!=null){tft.setEndValue(ComponentUtil.getLocalRepresentation(dim).getTextFormat().getEndValue());}
            tft.setTextType(TextTypeType.Enum.forString(ComponentUtil.getLocalRepresentation(dim).getTextFormat().getTextType().toString()));
            dim2.setTextFormat(tft);
        }
        dim2.setConceptAgency(dim.getConceptIdentity().getAgencyId().toString());
        dim2.setConceptRef(dim.getConceptIdentity().getId().toString());
        dim2.setConceptVersion(dim.getConceptIdentity().getVersion().toString());
        if( !dim.getConceptIdentity().getMaintainableParentId().equals("STANDALONE_CONCEPT_SCHEME")){
            dim2.setConceptSchemeAgency(dim.getConceptIdentity().getAgencyId().toString());
            dim2.setConceptSchemeRef(dim.getConceptIdentity().getMaintainableParentId().toString());
        }
        return dim2;

    }

    public static org.sdmx.resources.sdmxml.schemas.v20.structure.PrimaryMeasureType toPrimaryMeasure(sdmx.structure.datastructure.PrimaryMeasure pm) {
        PrimaryMeasureType pm2 = PrimaryMeasureType.Factory.newInstance();
        pm2.setTextFormat(toTextFormatType(ComponentUtil.getLocalRepresentation(pm).getTextFormat()));
        if( ComponentUtil.getLocalRepresentation(pm).getEnumeration()!=null) {
           pm2.setCodelist(ComponentUtil.getLocalRepresentation(pm).getEnumeration().getId().toString());
           pm2.setCodelistAgency(ComponentUtil.getLocalRepresentation(pm).getEnumeration().getAgencyId().toString());
           pm2.setCodelistVersion(ComponentUtil.getLocalRepresentation(pm).getEnumeration().getVersion().toString());
        }
        pm2.setConceptRef(pm.getId().toString());
        pm2.setConceptSchemeAgency(pm.getConceptIdentity().getAgencyId().toString());
        pm2.setConceptSchemeRef(pm.getConceptIdentity().getMaintainableParentId().toString());
        pm2.setConceptVersion(pm.getConceptIdentity().getVersion().toString());
        return pm2;
    }

    private static TextFormatType toTextFormatType(sdmx.structure.base.TextFormatType textFormat) {
        TextFormatType tt = TextFormatType.Factory.newInstance();
        if( textFormat.getDecimals()!=null) { tt.setDecimals(BigInteger.valueOf(textFormat.getDecimals().getValue()));}
        if( textFormat.getEndValue()!=null) { tt.setEndValue(textFormat.getEndValue());}
        if( textFormat.isSequence()!=null) {
            tt.setIsSequence(textFormat.isSequence());
            if( textFormat.getInterval()!=null) { tt.setInterval(textFormat.getInterval());}
        }
        if( textFormat.getMaxLength()!=null) { tt.setMaxLength(BigInteger.valueOf(textFormat.getMaxLength().getValue()));}
        if( textFormat.getMinLength()!=null) { tt.setMinLength(BigInteger.valueOf(textFormat.getMinLength().getValue()));}
        if( textFormat.getPattern()!=null) {tt.setPattern(textFormat.getPattern());}
        if( textFormat.getStartValue()!=null) {tt.setStartValue(textFormat.getStartValue()); }
        if( textFormat.getTextType()!=null ) { tt.setTextType(TextTypeType.Enum.forString(textFormat.getTextType().toString() ) ) ;}
        return tt;
    }
    
}

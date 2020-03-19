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
package sdmx.structureddata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import sdmx.Registry;
import sdmx.common.DataType;
import sdmx.common.Name;
import sdmx.commonreferences.ConceptReference;
import sdmx.commonreferences.ConceptSchemeReference;
import sdmx.commonreferences.IDType;
import sdmx.commonreferences.ItemSchemeReferenceBase;
import sdmx.commonreferences.NestedID;
import sdmx.commonreferences.NestedNCNameID;
import sdmx.commonreferences.RefBase;
import sdmx.commonreferences.types.ItemSchemeTypeCodelistType;
import sdmx.commonreferences.types.ObjectTypeCodelistType;
import sdmx.data.flat.FlatDataSet;
import sdmx.structure.base.Component;
import sdmx.structure.base.ComponentUtil;
import sdmx.structure.base.ItemBaseType;
import sdmx.structure.base.ItemSchemeType;
import sdmx.structure.base.ItemType;
import sdmx.structure.base.RepresentationType;
import sdmx.structure.codelist.CodeType;
import sdmx.structure.codelist.CodelistType;
import sdmx.structure.concept.ConceptSchemeType;
import sdmx.structure.concept.ConceptType;
import sdmx.structure.datastructure.AttributeType;
import sdmx.structure.datastructure.DataStructureType;
import sdmx.structure.datastructure.DimensionType;
import sdmx.structure.datastructure.MeasureDimensionType;
import sdmx.structure.datastructure.PrimaryMeasure;
import sdmx.structure.datastructure.TimeDimensionType;

public class ValueTypeResolver {

    public static ItemType resolveCode(Registry registry, DataStructureType struct, String column, String value) {
        if (value == null) {
            return null;
        }
        Component dim = struct.getDataStructureComponents().findDimension(column);
        if (dim == null && "type".equals(column)) {
            dim = struct.getDataStructureComponents().getDimensionList().getMeasureDimension();
        }
        ConceptReference conceptRef = dim.getConceptIdentity();
        RepresentationType rep = null;
        ConceptType concept = null;
        if (conceptRef != null) {
            concept = registry.find(conceptRef);
            if (concept == null) {
                System.out.println("Cant find concept:" + dim.getConceptIdentity().getId());
                System.out.println(conceptRef.getAgencyId() + ":" + conceptRef.getMaintainableParentId() + ":" + conceptRef.getId() + ":" + conceptRef.getVersion());
                CodeType ct = new CodeType();
                ct.setId(new IDType(value));
                Name name = new Name("en", value);
                ct.setNames(Collections.singletonList(name));
                return ct;
            }
            rep = ComponentUtil.getRepresentation(registry,dim);
        }
        if (rep != null) {
            if (rep.getEnumeration() != null) {
                if (rep.getEnumeration().getRefClass().toInt() == ItemSchemeTypeCodelistType.CODELIST.toInt()) {
                    CodelistType codelist = registry.find(rep.getEnumeration().asCodelistReference());
                    IDType id = null;
                    try {
                        id = new IDType(value);
                    } catch (ExceptionInInitializerError ie) {
                        // Ignore
                    }
                    if( codelist==null ) {
                        throw new RuntimeException("Codelist is null Representation="+rep.getEnumeration().asCodelistReference().toString());
                    }
                    CodeType ct = null;
                    if (id != null) {
                        ct = codelist.findCode(id);
                    }
                    if (ct == null) {
                        CodeType ct2 = new CodeType();
                        ct2.setId(id);
                        Locale loc = Locale.getDefault();
                        Name name = new Name("en", "Missing Code:" + value);
                        ArrayList<Name> names = new ArrayList<Name>();
                        names.add(name);
                        ct2.setNames(names);
                        return ct2;
                    } else {
                        return ct;
                    }
                } else {
                    rep.getEnumeration().asConceptSchemeReference().dump();
                    ConceptSchemeType cs = registry.find(rep.getEnumeration().asConceptSchemeReference());
                    System.out.println("Cs="+cs);
                    cs.dump();
                    ConceptType conceptMeasure = null;
                    for (int i = 0; i < cs.size() && conceptMeasure == null; i++) {
                        ConceptType tempConcept = cs.getConcept(i);
                        if (tempConcept.getCode() != null && tempConcept.getCode().equals(value)) {
                            conceptMeasure = cs.getConcept(i);
                        } else if (tempConcept.getId().equals(value)) {
                            conceptMeasure = tempConcept;
                        }
                    }
                    if (conceptMeasure != null) {
                        //System.out.println("ConceptMeasure:"+conceptMeasure);
                        return conceptMeasure;

                    }
                    return null;
                }
            }
        } else {
            CodeType itm = new CodeType();
            Name name = new Name(Locale.getDefault().getLanguage(),value);
            List<Name> names = Collections.singletonList(name);
            itm.setNames(names);
            //throw new RuntimeException("Rep is null!");
            return itm;
        }
        return null;
    }

    public static ItemSchemeType getPossibleCodes(Registry registry, DataStructureType struct, String column) {
        Component dim = struct.getDataStructureComponents().findDimension(column);
        ConceptReference conceptRef = dim.getConceptIdentity();
        RepresentationType rep = null;
        ConceptType concept = null;
        if (conceptRef != null) {
            concept = registry.find(conceptRef);
            if (concept != null) {
                rep = concept.getCoreRepresentation();
            }
        }
        RepresentationType localRep = ComponentUtil.getRepresentation(registry,dim);
        if (localRep != null) {
            rep = localRep;
        }
        if (rep != null) {
            if (rep.getEnumeration() != null) {
                if (rep.getEnumeration().getRefClass() == ObjectTypeCodelistType.CONCEPTSCHEME) {
                    ConceptSchemeType cscheme = registry.find(rep.getEnumeration().asConceptSchemeReference());
                    if (cscheme == null) {
                        throw new RuntimeException("Can't find ConceptScheme!" + rep.getEnumeration().getMaintainableParentId().toString());
                    }
                    return cscheme;
                } else {
                    CodelistType codelist = registry.find(rep.getEnumeration().asCodelistReference());
                    if (codelist == null) {
                        throw new RuntimeException("Cant find codelist");
                    } else {
                        return codelist;
                    }
                }
            }
        }
        // Uncoded Dimension (probably Time)
        return null;
    }
}

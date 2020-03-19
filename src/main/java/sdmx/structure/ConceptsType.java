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
package sdmx.structure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import sdmx.commonreferences.ConceptReference;
import sdmx.commonreferences.ConceptSchemeReference;
import sdmx.commonreferences.IDType;
import sdmx.commonreferences.NestedID;
import sdmx.commonreferences.NestedNCNameID;
import sdmx.commonreferences.Version;
import sdmx.structure.categorisation.CategorisationType;
import sdmx.structure.concept.ConceptSchemeType;
import sdmx.structure.concept.ConceptType;
import sdmx.xml.anyURI;

/**
 * <xs:complexType name="ConceptsType">
 * <xs:annotation>
 * <xs:documentation>ConceptsType describes the structure of the concepts
 * container. It contains one or more stand-alone concept or concept scheme,
 * which can be explicitly detailed or referenced from an external structure
 * document or registry service. This container may contain a mix of both
 * stand-alone concepts and concept schemes.</xs:documentation>
 * </xs:annotation>
 * <xs:sequence>
 * <xs:element name="ConceptScheme" type="ConceptSchemeType" minOccurs="0"
 * maxOccurs="unbounded">
 * <xs:annotation>
 * <xs:documentation>ConceptScheme provides the details of a concept scheme,
 * which is the descriptive information for an arrangement or division of
 * concepts into groups based on characteristics, which the objects have in
 * common. It contains a collection of concept definitions, that may be arranged
 * in simple hierarchies.</xs:documentation>
 * </xs:annotation>
 * <xs:unique name="ConceptScheme_UniqueConcept">
 * <xs:selector xpath="structure:Concept"/>
 * <xs:field xpath="@id"/>
 * </xs:unique>
 * </xs:element>
 * </xs:sequence>
 * </xs:complexType>
 *
 * @author James
 */

public class ConceptsType {

    List<ConceptSchemeType> conceptSchemes = new ArrayList<ConceptSchemeType>();

    /**
     * @return the categorisations
     */
    public List<ConceptSchemeType> getConceptSchemes() {
        return conceptSchemes;
    }

    /**
     * @param categorisations the categorisations to set
     */
    public void setConceptSchemes(List<ConceptSchemeType> cons) {
        this.conceptSchemes = cons;
    }

    public ConceptSchemeType findConceptScheme(String agency, String id, String vers) {
        IDType findid = new IDType(id);
        NestedNCNameID ag = new NestedNCNameID(agency);
        Version ver = new Version(vers);
        return findConceptScheme(ag, findid, ver);
    }

    public ConceptSchemeType findConceptScheme(NestedNCNameID agency2, NestedID findid, Version ver) {
        for (int i = 0; i < conceptSchemes.size(); i++) {
            if (conceptSchemes.get(i).identifiesMe(agency2, findid, ver)) {
                return conceptSchemes.get(i);
            }
        }
        return null;
    }

    public void dump() {
        System.out.println("Dump Concepts");
        for (int i = 0; i < conceptSchemes.size(); i++) {
            conceptSchemes.get(i).dump();
        }
    }
    /*
     * This method is used in sdmx 2.0 parsing to find a concept with the correct ID..
     * this is because the Dimension in the KeyFamily does not contain a complete reference
     * only an ID.. we lookup the Concept by it's ID, when we find a match, we can make a 
     * ConceptReference out of it with it's AgencyID and Version.
     */

    public ConceptSchemeType findConceptSchemeById(NestedID id) {
        ConceptSchemeType cs = null;
        for (int i = 0; i < conceptSchemes.size(); i++) {
            if (conceptSchemes.get(i).identifiesMe(id)) {
                if (cs == null) {
                    cs = conceptSchemes.get(i);
                } else {
                    int j = cs.getVersion().compareTo(conceptSchemes.get(i).getVersion());
                    switch (j) {
                        case -1: // Less
                            break;
                        case 0:  // Equal
                            break;
                        case 1:
                            // Our found conceptscheme has a greater version number.
                            cs = conceptSchemes.get(i);
                            break;
                    }
                }
            }
        }
        return cs;
    }

    public ConceptSchemeType findConceptSchemeByUrn(anyURI urn) {
        ConceptSchemeType cs = null;
        for (int i = 0; i < conceptSchemes.size(); i++) {
            if (conceptSchemes.get(i).identifiesMe(urn)) {
                if (cs == null) {
                    cs = conceptSchemes.get(i);
                }
            }
        }
        return cs;
    }

    public ConceptSchemeType findConceptScheme(NestedNCNameID agency, ConceptReference ref) {
        //System.out.println("Looking for Ref:"+agency.toString()+":"+ref.getMaintainableParentId().toString()+":"+ref.getVersion());
        //ConceptSchemeType cs = findConceptScheme(agency, ref.getMaintainableParentId());
        //System.out.println("cs="+cs);
        return findConceptScheme(agency, ref.getMaintainableParentId(), ref.getVersion());
    }

    public ConceptType findConcept(NestedNCNameID agency, ConceptReference ref) {
        ConceptSchemeType cs = findConceptScheme(agency, ref);
        return cs.findConcept(ref.getId());
    }
    public ConceptType findConcept(IDType id) {
        for(int i=0;i<conceptSchemes.size();i++) {
            ConceptType ct = conceptSchemes.get(i).findConcept(id);
            if( ct!=null) {
                Logger.getLogger("sdmx").fine("ConceptsType found concept:"+id.toString());
                return ct;
            }
        }
        return null;
    }

    public ConceptSchemeType findConceptScheme(NestedNCNameID mainAgencyId, NestedID id) {
        for (int i = 0; i < conceptSchemes.size(); i++) {
            if (conceptSchemes.get(i).getAgencyID().equals(mainAgencyId) && conceptSchemes.get(i).getId().equals(id)) {
                return conceptSchemes.get(i);
            }
        }
        return null;
    }
    public ConceptSchemeType find(ConceptSchemeReference ref) {
        if( ref.getAgencyId()==null) {
            return this.findConceptSchemeById(ref.getMaintainableParentId());
        }
        for (int i = 0; i < conceptSchemes.size(); i++) {
            Logger.getLogger("sdmx").finest("comparing "+ref.getAgencyId()+":"+conceptSchemes.get(i).getAgencyID()+" anf "+ref.getMaintainableParentId()+":"+conceptSchemes.get(i).getId());
            if (conceptSchemes.get(i).getAgencyID().equals(ref.getAgencyId()) && conceptSchemes.get(i).getId().equals(ref.getMaintainableParentId())) {
                Logger.getLogger("sdmx").fine("ConceptsType:find(ConceptScheme)"+ref.toString()+": found:"+conceptSchemes.get(i).getName());
                return conceptSchemes.get(i);
            }
        }
        return null;
    }
    public ConceptType find(ConceptReference ref) {
        Logger.getLogger("sdmx").fine("ConceptsType find(ConceptReference)"+ref.toString());
        if( ref.getAgencyId()==null&&ref.getMaintainableParentId()==null) {
            ConceptType ct = findConcept(ref.getId().asID());
            Logger.getLogger("sdmx").fine("ConceptsType find(ConceptReference)"+ref.toString()+": returning"+ct);
            return ct;
        }
        if( ref.getMaintainableParentId()==null) {
            ConceptType ct = findConcept(ref.getId().asID());
            Logger.getLogger("sdmx").fine("ConceptsType find(ConceptReference)"+ref.toString()+": returning"+ct);
            return ct;
        }
        ConceptSchemeType conceptScheme = find(ConceptSchemeReference.create(ref.getAgencyId(), ref.getMaintainableParentId(), ref.getVersion()));
        if( conceptScheme!=null) {
            ConceptType ct = conceptScheme.findConcept(ref.getId());
            Logger.getLogger("sdmx").fine("ConceptsType find(ConceptReference)"+ref.toString()+": returning"+ct);
            return ct;
        } else {
            Logger.getLogger("sdmx").fine("ConceptsType find(ConceptReference)"+ref.toString()+": returning"+null);
            return null;
        }
    }

    public void merge(ConceptsType concepts) {
        List<ConceptSchemeType> done = new ArrayList<ConceptSchemeType>();
        for(ConceptSchemeType cst:this.conceptSchemes) {
            if( concepts.findConceptScheme(cst.getAgencyID(), cst.getId(), cst.getVersion())!=null) {
                cst.merge(concepts.findConceptScheme(cst.getAgencyID(), cst.getId(),cst.getVersion()));
                done.add(concepts.findConceptScheme(cst.getAgencyID(), cst.getId(),cst.getVersion()));
            }
        }
        List<ConceptSchemeType> add = concepts.getConceptSchemes();
        add.removeAll(done);
        this.getConceptSchemes().addAll(add);
    }
    public void addConceptScheme(ConceptSchemeType cs) {
        this.conceptSchemes.add(cs);
    }
}

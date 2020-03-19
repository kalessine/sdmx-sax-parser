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
package sdmx.message;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sdmx.Registry;
import sdmx.commonreferences.CodeReference;
import sdmx.commonreferences.CodelistReference;
import sdmx.commonreferences.ConceptReference;
import sdmx.commonreferences.ConceptSchemeReference;
import sdmx.commonreferences.DataStructureReference;
import sdmx.commonreferences.DataflowReference;
import sdmx.commonreferences.IDType;
import sdmx.commonreferences.ItemReference;
import sdmx.commonreferences.ItemSchemeReference;
import sdmx.commonreferences.ItemSchemeReferenceBase;
import sdmx.commonreferences.NestedID;
import sdmx.commonreferences.NestedNCNameID;
import sdmx.commonreferences.StructureReference;
import sdmx.commonreferences.Version;
import sdmx.structure.CategorySchemesType;
import sdmx.structure.CodelistsType;
import sdmx.structure.ConceptsType;
import sdmx.structure.DataStructuresType;
import sdmx.structure.DataflowsType;
import sdmx.structure.StructuresType;
import sdmx.structure.base.ItemSchemeType;
import sdmx.structure.base.ItemType;
import sdmx.structure.base.MaintainableType;
import sdmx.structure.codelist.CodeType;
import sdmx.structure.codelist.CodelistType;
import sdmx.structure.concept.ConceptSchemeType;
import sdmx.structure.concept.ConceptType;
import sdmx.structure.dataflow.DataflowType;
import sdmx.structure.datastructure.DataStructureType;
import sdmx.version.twopointone.writer.Sdmx21StructureWriter;

/**
 * *	<xs:complexType name="StructureType">
 * <xs:annotation>
 * <xs:documentation>StructureType defines the contents of a structure message.
 * The payload is optional since this message may be returned from a web service
 * with only information in the footer.</xs:documentation>
 * </xs:annotation>
 * <xs:complexContent>
 * <xs:restriction base="MessageType">
 * <xs:sequence>
 * <xs:element name="Header" type="StructureHeaderType"/>
 * <xs:element name="Structures" type="structure:StructuresType" minOccurs="0"/>
 * <xs:element ref="footer:Footer" minOccurs="0"/>
 * </xs:sequence>
 * </xs:restriction>
 * </xs:complexContent>
 * </xs:complexType>
 *
 * @author James
 */
public class StructureType extends MessageType implements Registry {
    private StructuresType structures = null;

    public StructureType() {
    }

    public StructureType(StructuresType structures) {
        this.structures = structures;
    }

    /**
     * @return the structures
     */
    public StructuresType getStructures() {
        return structures;
    }

    @Override
    public void clear() {
    }

    @Override
    public DataStructureType find(DataStructureReference ref) {
        return structures.find(ref);
    }

    @Override
    public DataflowType find(DataflowReference ref) {
        return structures.find(ref);
    }

    @Override
    public CodeType find(CodeReference ref) {
        return structures.find(ref);
    }

    @Override
    public CodelistType find(CodelistReference ref) {
        return structures.find(ref);
    }

    @Override
    public ConceptType find(ConceptReference ref) {
        return structures.find(ref);
    }

    @Override
    public ConceptSchemeType find(ConceptSchemeReference ref) {
        return structures.find(ref);
    }

    @Override
    public void load(StructureType struct) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void unload(StructureType struct) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DataflowType> listDataflows() {
        return structures.listDataflows();
    }

    public void setStructures(StructuresType struct) {
        this.structures = struct;
    }

    @Override
    public ItemType find(ItemReference ref) {
        return this.structures.find(ref);
    }

    @Override
    public ItemSchemeType find(ItemSchemeReferenceBase ref) {
        return this.structures.find(ref);
    }

    @Override
    public void save(OutputStream out) throws IOException {
        Sdmx21StructureWriter.write(this, out);
    }

    public void merge(StructureType struct) {
        if (struct.getStructures().getCategorySchemes() != null) {
            if (this.getStructures().getCategorySchemes() != null) {
                CategorySchemesType cs = this.getStructures().getCategorySchemes();
                cs.merge(struct.getStructures().getCategorySchemes());
            } else {
                this.getStructures().setCategorySchemes(struct.getStructures().getCategorySchemes());
            }
        }
        if (struct.getStructures().getCodelists()!= null) {
            if (this.getStructures().getCodelists()!= null) {
                CodelistsType cs = getStructures().getCodelists();
                cs.merge(struct.getStructures().getCodelists());
            } else {
                this.getStructures().setCodelists(struct.getStructures().getCodelists());
            }
        }
        if (struct.getStructures().getConcepts()!= null) {
            if (this.getStructures().getConcepts()!= null) {
                ConceptsType cs = getStructures().getConcepts();
                cs.merge(struct.getStructures().getConcepts());
            } else {
                this.getStructures().setConcepts(struct.getStructures().getConcepts());
            }
        }
        if (struct.getStructures().getDataStructures()!= null) {
            if (this.getStructures().getDataStructures()!= null) {
                DataStructuresType cs = getStructures().getDataStructures();
                cs.merge(struct.getStructures().getDataStructures());
            } else {
                this.getStructures().setDataStructures(struct.getStructures().getDataStructures());
            }
        }
        if (struct.getStructures().getDataflows()!= null) {
            if (this.getStructures().getDataflows()!= null) {
                DataflowsType cs = getStructures().getDataflows();
                cs.merge(struct.getStructures().getDataflows());
            } else {
                this.getStructures().setDataflows(struct.getStructures().getDataflows());
            }
        }
    }
   public void merge(){}

    @Override
    public List<DataStructureType> search(DataStructureReference ref) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<DataflowType> search(DataflowReference ref) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<CodeType> search(CodeReference ref) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<CodelistType> search(CodelistReference ref) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<ItemType> search(ItemReference ref) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<ItemSchemeType> search(ItemSchemeReferenceBase ref) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<ConceptType> search(ConceptReference ref) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<ConceptSchemeType> search(ConceptSchemeReference ref) {
        return Collections.EMPTY_LIST;
    }
    public List<StructureType> getCache(){
        return Collections.EMPTY_LIST;
    }
}

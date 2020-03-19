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
package sdmx;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import sdmx.commonreferences.CategorySchemeReference;
import sdmx.commonreferences.CodeReference;
import sdmx.commonreferences.CodelistReference;
import sdmx.commonreferences.ComponentReference;
import sdmx.commonreferences.ConceptReference;
import sdmx.commonreferences.ConceptSchemeReference;
import sdmx.commonreferences.ConstraintReference;
import sdmx.commonreferences.ConstraintTargetReference;
import sdmx.commonreferences.DataStructureReference;
import sdmx.commonreferences.DataflowReference;
import sdmx.commonreferences.DimensionReference;
import sdmx.commonreferences.HierarchicalCodelistReference;
import sdmx.commonreferences.IDType;
import sdmx.commonreferences.ItemReference;
import sdmx.commonreferences.ItemSchemeReference;
import sdmx.commonreferences.ItemSchemeReferenceBase;
import sdmx.commonreferences.NestedNCNameID;
import sdmx.commonreferences.StructureReference;
import sdmx.commonreferences.Version;
import sdmx.message.StructureType;
import sdmx.structure.HierarchicalCodelist;
import sdmx.structure.base.ComponentType;
import sdmx.structure.base.ItemSchemeType;
import sdmx.structure.base.ItemType;
import sdmx.structure.base.MaintainableType;
import sdmx.structure.category.CategorySchemeType;
import sdmx.structure.codelist.CodeType;
import sdmx.structure.codelist.CodelistType;
import sdmx.structure.concept.ConceptSchemeType;
import sdmx.structure.concept.ConceptType;
import sdmx.structure.constraint.ConstraintType;
import sdmx.structure.dataflow.DataflowType;
import sdmx.structure.datastructure.DataStructureType;
import sdmx.structure.datastructure.DimensionType;

/**
 *
 * @author James
 */
public interface Registry {
    public void load(StructureType struct);
    public void unload(StructureType struct);
    public void clear();
    public List<DataflowType> listDataflows();
    public DataStructureType find(DataStructureReference ref);
    public DataflowType find(DataflowReference ref);
    public CodeType find(CodeReference ref);
    public CodelistType find(CodelistReference ref);
    public ItemType find(ItemReference ref);
    public ItemSchemeType find(ItemSchemeReferenceBase ref);
    public ConceptType find(ConceptReference ref);
    public ConceptSchemeType find(ConceptSchemeReference ref);
    
    public List<DataStructureType> search(DataStructureReference ref);
    public List<DataflowType> search(DataflowReference ref);
    public List<CodeType> search(CodeReference ref);
    public List<CodelistType> search(CodelistReference ref);
    public List<ItemType> search(ItemReference ref);
    public List<ItemSchemeType> search(ItemSchemeReferenceBase ref);
    public List<ConceptType> search(ConceptReference ref);
    public List<ConceptSchemeType> search(ConceptSchemeReference ref);
    
    public void save(OutputStream out)throws IOException;
    public List<StructureType> getCache();
}

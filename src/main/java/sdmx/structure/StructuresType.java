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

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import sdmx.Registry;
import sdmx.commonreferences.CodeReference;
import sdmx.commonreferences.CodelistReference;
import sdmx.commonreferences.ConceptReference;
import sdmx.commonreferences.ConceptSchemeReference;
import sdmx.commonreferences.DataStructureRef;
import sdmx.commonreferences.DataStructureReference;
import sdmx.commonreferences.DataflowReference;
import sdmx.commonreferences.IDType;
import sdmx.commonreferences.ItemReference;
import sdmx.commonreferences.ItemSchemeReference;
import sdmx.commonreferences.ItemSchemeReferenceBase;
import sdmx.commonreferences.NestedID;
import sdmx.commonreferences.NestedNCNameID;
import sdmx.commonreferences.StructureOrUsageReference;
import sdmx.commonreferences.StructureReference;
import sdmx.commonreferences.Version;
import sdmx.commonreferences.types.ObjectTypeCodelistType;
import sdmx.commonreferences.types.PackageTypeCodelistType;
import sdmx.message.DataMessage;
import sdmx.message.DataQueryMessage;
import sdmx.message.DataStructure;
import sdmx.message.DataStructureQueryMessage;
import sdmx.message.StructureType;
import sdmx.structure.base.ItemSchemeType;
import sdmx.structure.base.ItemType;
import sdmx.structure.base.MaintainableType;
import sdmx.structure.codelist.CodeType;
import sdmx.structure.codelist.CodelistType;
import sdmx.structure.concept.ConceptSchemeType;
import sdmx.structure.concept.ConceptType;
import sdmx.structure.dataflow.DataflowType;
import sdmx.structure.datastructure.DataStructureType;

/**
 *
 * @author James
 */

public class StructuresType implements Registry {

    private List<StructureOrUsageReference> relatedStructure;
    private DataflowsType dataFlows = null;
    private MetadataflowsType metadataFlows = null;
    private CategorySchemesType categorySchemes = null;
    private CategorisationsType categorisations = null;
    private CodelistsType codelists = null;
    private HierarchicalCodelistsType hierarchicalCodelists = null;
    private ConceptsType concepts = null;
    private MetadataStructuresType metadataStructures = null;
    private DataStructuresType dataStructures = null;
    private StructureSetsType structureSets = null;
    private ReportingTaxonomiesType reportingTaxonomies = null;
    private ProcessesType processes = null;
    private ConstraintsType constraints = null;
    private ProvisionAgreementsType provisionAgreements = null;

    /**
     * @return the relatedStructure
     */
    public List<StructureOrUsageReference> getRelatedStructure() {
        return relatedStructure;
    }

    /**
     * @param relatedStructure the relatedStructure to set
     */
    public void setRelatedStructure(List<StructureOrUsageReference> relatedStructure) {
        this.relatedStructure = relatedStructure;
    }

    /**
     * @return the dataFlows
     */
    public DataflowsType getDataflows() {
        return dataFlows;
    }

    /**
     * @param dataFlows the dataFlows to set
     */
    public void setDataflows(DataflowsType dataFlows) {
        this.dataFlows = dataFlows;
    }

    /**
     * @return the metadataFlows
     */
    public MetadataflowsType getMetadataFlows() {
        return metadataFlows;
    }

    /**
     * @param metadataFlows the metadataFlows to set
     */
    public void setMetadataFlows(MetadataflowsType metadataFlows) {
        this.metadataFlows = metadataFlows;
    }

    /**
     * @return the categorySchemes
     */
    public CategorySchemesType getCategorySchemes() {
        return categorySchemes;
    }

    /**
     * @param categorySchemes the categorySchemes to set
     */
    public void setCategorySchemes(CategorySchemesType categorySchemes) {
        this.categorySchemes = categorySchemes;
    }

    /**
     * @return the categorisations
     */
    public CategorisationsType getCategorisations() {
        return categorisations;
    }

    /**
     * @param categorisations the categorisations to set
     */
    public void setCategorisations(CategorisationsType categorisations) {
        this.categorisations = categorisations;
    }

    /**
     * @return the codelists
     */
    public CodelistsType getCodelists() {
        return codelists;
    }

    /**
     * @param codelists the codelists to set
     */
    public void setCodelists(CodelistsType codelists) {
        this.codelists = codelists;
    }

    /**
     * @return the hierarchicalCodelists
     */
    public HierarchicalCodelistsType getHierarchicalCodelists() {
        return hierarchicalCodelists;
    }

    /**
     * @param hierarchicalCodelists the hierarchicalCodelists to set
     */
    public void setHierarchicalCodelists(HierarchicalCodelistsType hierarchicalCodelists) {
        this.hierarchicalCodelists = hierarchicalCodelists;
    }

    /**
     * @return the concepts
     */
    public ConceptsType getConcepts() {
        return concepts;
    }

    /**
     * @param concepts the concepts to set
     */
    public void setConcepts(ConceptsType concepts) {
        this.concepts = concepts;
    }

    /**
     * @return the metadataStructures
     */
    public MetadataStructuresType getMetadataStructures() {
        return metadataStructures;
    }

    /**
     * @param metadataStructures the metadataStructures to set
     */
    public void setMetadataStructures(MetadataStructuresType metadataStructures) {
        this.metadataStructures = metadataStructures;
    }

    /**
     * @return the dataStructures
     */
    public DataStructuresType getDataStructures() {
        return dataStructures;
    }

    /**
     * @param dataStructures the dataStructures to set
     */
    public void setDataStructures(DataStructuresType dataStructures) {
        this.dataStructures = dataStructures;
    }

    /**
     * @return the structureSets
     */
    public StructureSetsType getStructureSets() {
        return structureSets;
    }

    /**
     * @param structureSets the structureSets to set
     */
    public void setStructureSets(StructureSetsType structureSets) {
        this.structureSets = structureSets;
    }

    /**
     * @return the reportingTaxonomies
     */
    public ReportingTaxonomiesType getReportingTaxonomies() {
        return reportingTaxonomies;
    }

    /**
     * @param reportingTaxonomies the reportingTaxonomies to set
     */
    public void setReportingTaxonomies(ReportingTaxonomiesType reportingTaxonomies) {
        this.reportingTaxonomies = reportingTaxonomies;
    }

    /**
     * @return the processes
     */
    public ProcessesType getProcesses() {
        return processes;
    }

    /**
     * @param processes the processes to set
     */
    public void setProcesses(ProcessesType processes) {
        this.processes = processes;
    }

    /**
     * @return the constraints
     */
    public ConstraintsType getConstraints() {
        return constraints;
    }

    /**
     * @param constraints the constraints to set
     */
    public void setConstraints(ConstraintsType constraints) {
        this.constraints = constraints;
    }

    /**
     * @return the provisionAgreements
     */
    public ProvisionAgreementsType getProvisionAgreements() {
        return provisionAgreements;
    }

    /**
     * @param provisionAgreements the provisionAgreements to set
     */
    public void setProvisionAgreements(ProvisionAgreementsType provisionAgreements) {
        this.provisionAgreements = provisionAgreements;
    }

    @Override
    public void clear() {

    }

    @Override
    public DataStructureType find(DataStructureReference ref) {
        if( getDataStructures()==null) return null;
        return getDataStructures().find(ref);
    }

    @Override
    public DataflowType find(DataflowReference ref) {
        if( getDataflows()==null)return null;
        return getDataflows().find(ref);
    }

    @Override
    public CodeType find(CodeReference ref) {
        if( getCodelists()==null)return null;
        return getCodelists().find(ref);
    }

    @Override
    public CodelistType find(CodelistReference ref) {
        if( getCodelists()==null)return null;
        return getCodelists().find(ref);
    }

    @Override
    public ConceptType find(ConceptReference ref) {
        if( getConcepts()==null)return null;
        return getConcepts().find(ref);
    }

    @Override
    public ConceptSchemeType find(ConceptSchemeReference ref) {
        if( getConcepts()==null) return null;
        return getConcepts().find(ref);
    }

    @Override
    public void load(StructureType struct) {
        
    }

    @Override
    public void unload(StructureType struct) {
        
    }

    @Override
    public List<DataflowType> listDataflows() {
        if( getDataflows()==null) return Collections.EMPTY_LIST;
        return getDataflows().getDataflows();
    }

    @Override
    public ItemType find(ItemReference ref) {
        ConceptType concept = find(ConceptReference.create(ref.getAgencyId(), ref.getMaintainableParentId(), ref.getVersion(), ref.getId()));
        if( concept!=null) return concept;
        CodeType code = find(CodeReference.create(ref.getAgencyId(),ref.getMaintainableParentId(), ref.getVersion(), ref.getId()));
        return code;
        
    }

    @Override
    public ItemSchemeType find(ItemSchemeReferenceBase ref) {
        ConceptSchemeType concept = find(ConceptSchemeReference.create(ref.getAgencyId(), ref.getMaintainableParentId(), ref.getVersion()));
        if( concept!=null) return concept;
        CodelistType code = find(CodelistReference.create(ref.getAgencyId(),ref.getMaintainableParentId(), ref.getVersion()));
        return code;
    }

    @Override
    public void save(OutputStream out) throws IOException {
        // Do Nothing
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

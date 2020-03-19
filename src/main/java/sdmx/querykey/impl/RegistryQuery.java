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
package sdmx.querykey.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import org.apache.commons.lang3.StringUtils;
import sdmx.Registry;
import sdmx.querykey.Query;
import sdmx.querykey.QueryDimension;
import sdmx.querykey.QueryTime;
import sdmx.structure.datastructure.DataStructureType;

/**
 *
 * @author James
 */
public class RegistryQuery implements Query {

    private String updatedAfter;
    private int firstNObservations;
    private int lastNObservations;
    private String dimensionAtObservation;
    private String detail;
    private boolean includeHistory;
    private String flowRef;
    private String providerRef;

    List<QueryDimension> dims = new ArrayList<QueryDimension>();
    QueryTime time = null;

    public RegistryQuery(DataStructureType ds, Registry reg, String dataflowId) {
        this.flowRef = dataflowId;
        for (int i = 0; i < ds.getDataStructureComponents().getDimensionList().size(); i++) {
            dims.add(new RegistryQueryDimension(ds.getDataStructureComponents().getDimensionList().getDimension(i).getId().toString(), ds, reg));
        }
        if (ds.getDataStructureComponents().getDimensionList().getMeasureDimension() != null) {
            dims.add(new RegistryQueryDimension(ds.getDataStructureComponents().getDimensionList().getMeasureDimension().getId().toString(), ds, reg));
        }
        if (ds.getDataStructureComponents().getDimensionList().getTimeDimension() != null) {
            time = new RegistryQueryTime(ds.getDataStructureComponents().getDimensionList().getTimeDimension().getId().toString(), ds, reg);
        }
    }

    public List<String> getConceptNames() {
        List<String> result = new ArrayList<String>();
        for (int i = 0; i < dims.size(); i++) {
            result.add(dims.get(i).getConcept());
        }
        return result;
    }

    public QueryDimension findQueryDimensionByConceptId(String s) {
        QueryDimension qdim = null;
        for (int i = 0; i < dims.size(); i++) {
            if (dims.get(i).getConcept().equals(s)) {
                return dims.get(i);
            }
        }
        return qdim;
    }

    public QueryDimension getQueryDimension(int i) {
        return dims.get(i);
    }

    public List<QueryDimension> getQueryDimensions() {
        return this.dims;
    }

    public int size() {
        return this.dims.size();
    }

    public QueryTime getQueryTime() {
        return time;
    }

    public int getQuerySize() {
        int count = 0;
        for (int i = 0; i < this.dims.size(); i++) {
            count += dims.get(i).size();
        }
        return count;
    }

    /**
     * @return the updatedAfter
     */
    public String getUpdatedAfter() {
        return updatedAfter;
    }

    /**
     * @param updatedAfter the updatedAfter to set
     */
    public void setUpdatedAfter(String updatedAfter) {
        this.updatedAfter = updatedAfter;
    }

    /**
     * @return the firstNObservations
     */
    public int getFirstNObservations() {
        return firstNObservations;
    }

    /**
     * @param firstNObservations the firstNObservations to set
     */
    public void setFirstNObservations(int firstNObservations) {
        this.firstNObservations = firstNObservations;
    }

    /**
     * @return the lastNObservations
     */
    public int getLastNObservations() {
        return lastNObservations;
    }

    /**
     * @param lastNObservations the lastNObservations to set
     */
    public void setLastNObservations(int lastNObservations) {
        this.lastNObservations = lastNObservations;
    }

    /**
     * @return the dimensionAtObservation
     */
    public String getDimensionAtObservation() {
        return dimensionAtObservation;
    }

    /**
     * @param dimensionAtObservation the dimensionAtObservation to set
     */
    public void setDimensionAtObservation(String dimensionAtObservation) {
        this.dimensionAtObservation = dimensionAtObservation;
    }

    /**
     * @return the detail
     */
    public String getDetail() {
        return detail;
    }

    /**
     * @param detail the detail to set
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * @return the includeHistory
     */
    public boolean isIncludeHistory() {
        return includeHistory;
    }

    /**
     * @param includeHistory the includeHistory to set
     */
    public void setIncludeHistory(boolean includeHistory) {
        this.includeHistory = includeHistory;
    }

    /**
     * @return the flowRef
     */
    public String getFlowRef() {
        return flowRef;
    }

    /**
     * @param flowRef the flowRef to set
     */
    public void setFlowRef(String flowRef) {
        this.flowRef = flowRef;
    }

    /**
     * @return the queryString
     */
    public String getQueryString() {
        String q = "";
        for (int i = 0; i < dims.size(); i++) {
            q += dims.get(i).getQueryString();
            if (i < dims.size() - 1) {
                q += ".";
            }
        }
        return q;
    }

    /**
     * @return the providerRef
     */
    public String getProviderRef() {
        return providerRef;
    }

    /**
     * @param providerRef the providerRef to set
     */
    public void setProviderRef(String providerRef) {
        this.providerRef = providerRef;
    }

    public void fromString(String s) {
        String[] dims = StringUtils.splitPreserveAllTokens(s, ".");
        for (int i = 0; i < dims.length; i++) {
            String sr = dims[i];
            this.getQueryDimension(i).fromString(sr);
        }
    }
}

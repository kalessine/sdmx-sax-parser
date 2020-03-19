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
package sdmx.querykey;

import java.util.ArrayList;
import java.util.List;
import sdmx.Registry;
import sdmx.structure.datastructure.DataStructureType;

/**
 * This object should be all a repository needs to perform a query
 *
 * @author James
 */
public interface Query {
    public List<String> getConceptNames();
    public QueryDimension getQueryDimension(int i);
    public QueryDimension findQueryDimensionByConceptId(String s);
    public List<QueryDimension> getQueryDimensions();
    public int size();
    public QueryTime getQueryTime();
    public int getQuerySize();
    public String getUpdatedAfter();
    public void setUpdatedAfter(String updatedAfter);
    public int getFirstNObservations();
    public void setFirstNObservations(int firstNObservations);
    public int getLastNObservations();
    public void setLastNObservations(int lastNObservations);
    public String getDimensionAtObservation();
    public void setDimensionAtObservation(String dimensionAtObservation);
    public String getDetail();
    public void setDetail(String detail);
    public boolean isIncludeHistory();
    public void setIncludeHistory(boolean includeHistory);
    public String getFlowRef();
    public void setFlowRef(String flowRef);
    public String getQueryString();
    public String getProviderRef();
    public void setProviderRef(String providerRef);
}

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
package sdmx.util;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import sdmx.Registry;
import sdmx.commonreferences.DataStructureReference;
import sdmx.commonreferences.DataflowReference;
import sdmx.commonreferences.IDType;
import sdmx.commonreferences.NestedNCNameID;
import sdmx.message.DataQueryMessage;
import sdmx.query.base.TimeValue;
import sdmx.query.data.DataParametersAndType;
import sdmx.query.data.DataParametersOrType;
import sdmx.query.data.DataParametersType;
import sdmx.query.data.DataQuery;
import sdmx.query.data.DimensionValueType;
import sdmx.query.data.TimeDimensionValueType;
import sdmx.querykey.Query;
import sdmx.structure.dataflow.DataflowType;
import sdmx.structure.datastructure.DataStructureType;
import sdmx.structure.datastructure.DimensionType;
import sdmx.version.common.ParseParams;

/**
 *
 * @author James
 */
public class QueryStringUtils {
/*
    public static String[] toQueryString(ParseParams pparams, DataQueryMessage message) {
        Registry reg = pparams.getRegistry();
        IDType flowid = null;
        NestedNCNameID agency = null;
        if (pparams.getDataflow() != null) {
            flowid = pparams.getDataflow().getId();
            agency = pparams.getDataflow().getAgencyID();
        } else {
            flowid = message.getQuery().getDataWhere().getAnd().get(0).getDataflow().get(0).getMaintainableParentId();
            agency = new NestedNCNameID(message.getHeader().getReceivers().get(0).getId().toString());
        }
        DataStructureType dst = null;
        DataflowReference dfref = DataflowReference.create(agency, flowid, null);
        DataflowType df = reg.find(dfref);
        if (df == null) {
            List<DataflowType> dataflowList = reg.listDataflows();
            for (int i = 0; i < dataflowList.size(); i++) {
                if (dataflowList.get(i).getId().equals(flowid)) {
                    DataStructureReference ref = DataStructureReference.create(dataflowList.get(i).getStructure().getAgencyId(), dataflowList.get(i).getStructure().getMaintainableParentId(), dataflowList.get(i).getStructure().getMaintainedParentVersion());
                    dst = reg.find(ref);
                }
            }
        } else {
            dst = reg.find(df.getStructure());
        }
        DataStructureType structure = dst;
        StringBuilder q = new StringBuilder();
        for (int i = 0;
                i < structure.getDataStructureComponents()
                .getDimensionList().size(); i++) {
            DimensionType dim = structure.getDataStructureComponents().getDimensionList().getDimension(i);
            String concept = dim.getConceptIdentity().getId().toString();
            List<String> params = message.getQuery().getDataWhere().getAnd().get(0).getDimensionParameters(concept);
            if (params.size() > 0) {
                for (int j = 0; j < params.size(); j++) {
                    q.append(params.get(j));
                    if (j < params.size() - 1) {
                        q.append("+");
                    }
                }
            }
            if (i < structure.getDataStructureComponents().getDimensionList().size() - 1) {
                q.append(".");
            }
        }
        String startTime = message.getQuery().getDataWhere().getAnd().get(0).getTimeDimensionValue().get(0).getStart().toString();
        String endTime = message.getQuery().getDataWhere().getAnd().get(0).getTimeDimensionValue().get(0).getEnd().toString();
        return new String[]{flowid.toString(), q.toString() + "?startPeriod=" + startTime + "&endPeriod=" + endTime};
    }
*/
    /*
    public static DataQueryMessage toDataQueryMessage(ParseParams pparams, String query) {
        Registry reg = pparams.getRegistry();
        DataflowType df = pparams.getDataflow();
        DataStructureType struct = null;
        String flowid = df.getId().toString();
        if (df == null) {
            List<DataflowType> dataflowList = reg.listDataflows();
            for (int i = 0; i < dataflowList.size(); i++) {
                if (dataflowList.get(i).getId().equals(flowid)) {
                    DataStructureReference ref = DataStructureReference.create(dataflowList.get(i).getStructure().getAgencyId(), dataflowList.get(i).getStructure().getMaintainableParentId(), dataflowList.get(i).getStructure().getMaintainedParentVersion());
                    struct = reg.find(ref);
                }
            }
        } else {
            struct = reg.find(df.getStructure());
        }
        DataQueryMessage msg = new DataQueryMessage();
        DataQuery q = new DataQuery();
        DataParametersType dataWhere = new DataParametersType();
        DataParametersAndType dw = new DataParametersAndType();
        List<DataParametersOrType> ors = new ArrayList<DataParametersOrType>();
        dw.setDataflow(Collections.singletonList(df.asReference()));
        String quer = query;
        for (DimensionType d : struct.getDataStructureComponents().getDimensionList().getDimensions()) {
            //System.out.println("Quer="+quer);
            if (quer.indexOf(".") == -1) {
                break;
            }

            String valuesString = quer.substring(0, quer.indexOf("."));
            String[] values = valuesString.split("\\+");
            //System.out.println("Values Array="+Arrays.toString(values));
            DataParametersOrType or = new DataParametersOrType();
            List<DimensionValueType> dims = new ArrayList<DimensionValueType>();
            for (int i = 0; i < values.length; i++) {
                if (!"".equals(values[i])) {
                    dims.add(new DimensionValueType(d.getId().toString(), values[i]));
                }
            }
            or.setDimensionValue(dims);
            ors.add(or);
            quer = quer.substring(quer.indexOf(".") + 1, quer.length());
        }
        dw.setOr(ors);
        msg.setQuery(q);
        if (query.indexOf("startPeriod=") != -1 && query.indexOf("endPeriod=") != -1) {
            String startString = query.substring(query.indexOf("startPeriod=") + 12, query.length());
            String startPeriod = startString.indexOf("&") != -1 ? startString.substring(0, startString.indexOf("&")) : startString;
            String endString = query.substring(query.indexOf("endPeriod=") + 10, query.length());
            String endPeriod = endString.indexOf("&") != -1 ? endString.substring(0, endString.indexOf("&")) : endString;
            dw.setTimeDimensionValue(Collections.singletonList(new TimeDimensionValueType(new TimeValue(startPeriod), new TimeValue(endPeriod))));
        }
        dataWhere.setAnd(Collections.singletonList(dw));
        q.setDataWhere(dataWhere);
        msg.setQuery(q);
        return msg;
    }
    */
}

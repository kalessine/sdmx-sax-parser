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

import java.util.Locale;
import sdmx.Registry;
import sdmx.common.Name;
import sdmx.commonreferences.ConceptReference;
import sdmx.commonreferences.IDType;
import sdmx.commonreferences.NestedID;
import sdmx.commonreferences.NestedNCNameID;
import sdmx.commonreferences.Version;
import sdmx.data.DataSet;
import sdmx.structure.base.Component;
import sdmx.structure.concept.ConceptSchemeType;
import sdmx.structure.concept.ConceptType;
import sdmx.structure.datastructure.DataStructureType;

public class StructuredDataSet {

    private DataSet dataSet = null;
    private Registry registry = null;
    private DataStructureType structure = null;

    public StructuredDataSet(DataSet ds, Registry reg, DataStructureType struct) {
        this.dataSet = ds;
        this.registry = reg;
        this.structure = struct;
    }

    public StructuredValue getStructuredValue(int row, int column) {
        return new StructuredValue(getDataSet().getColumnName(column), getDataSet().getValue(row, column), registry, getStructure());
    }
    public int getColumnIndex(String id) {
        return getDataSet().getColumnIndex(id);
    }

    public String getColumnName(int i) {
        String conceptString = getDataSet().getColumnName(i);
        //System.out.println("Concept="+conceptString);
        //System.out.println("ds="+getStructure());
        Component c = getStructure().getDataStructureComponents().findDimension(conceptString);
        if (c == null && conceptString.equals("type")) {
            c = getStructure().getDataStructureComponents().getDimensionList().getMeasureDimension();
        }
        //System.out.println("Concept="+conceptString+": component="+c);
        // * Fixed in DataStructureComponentsType.findDimension
        //if( "TIME_PERIOD".equals(conceptString)&&c == null ) {
        //    // TIME_PERIOD in Data, Something else in Structure
        //   c = structure.getDataStructureComponents().getTimeDimension();
        //}
        if (c == null) {
            System.out.println("Component is null conceptRef:" + conceptString);
            return conceptString;
        }
        ConceptReference conceptRef = c.getConceptIdentity();
        ConceptType concept = null;
        if (conceptRef != null) {
            concept = registry.find(conceptRef);
            Locale loc = Locale.getDefault();
            Name name = concept == null ? null : concept.findName(loc.getLanguage());
            if (name == null) {
                return concept.getId().toString();
            }
            return name.getText();
        } else {
            throw new RuntimeException("Can't find Concept:" + conceptString);
        }
    }

    public int size() {
        return getDataSet().size();
    }

    public int getColumnCount() {
        return getDataSet().getColumnSize();
    }

    /**
     * @return the dataSet
     */
    public DataSet getDataSet() {
        return dataSet;
    }

    /**
     * @return the structure
     */
    public DataStructureType getStructure() {
        return structure;
    }
}

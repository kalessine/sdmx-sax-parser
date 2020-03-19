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
import java.util.List;
import java.util.logging.Logger;
import sdmx.commonreferences.DataStructureReference;
import sdmx.commonreferences.IDType;
import sdmx.commonreferences.NestedID;
import sdmx.commonreferences.NestedNCNameID;
import sdmx.commonreferences.Version;
import sdmx.structure.category.CategorySchemeType;
import sdmx.structure.datastructure.DataStructureType;

/**
 *
 * @author James
 */
public class DataStructuresType {

    private List<DataStructureType> dataStructures = new ArrayList<DataStructureType>();

    /**
     * @return the dataStructures
     */
    public List<DataStructureType> getDataStructures() {
        return dataStructures;
    }

    /**
     * @param dataStructures the dataStructures to set
     */
    public void setDataStructures(List<DataStructureType> dataStructures) {
        this.dataStructures = dataStructures;
    }

    public DataStructureType findDataStructure(String agency, String id, String vers) {
        IDType findid = new IDType(id);
        NestedNCNameID ag = new NestedNCNameID(agency);
        Version ver = new Version(vers);
        return findDataStructure(ag, findid, ver);
    }

    public DataStructureType findDataStructure(NestedNCNameID agency2, NestedID findid, Version ver) {
        for (int i = 0; i < dataStructures.size(); i++) {
            if (dataStructures.get(i).identifiesMe(agency2, findid, ver)) {
                return dataStructures.get(i);
            }
        }
        return null;
    }

    public DataStructureType findDataStructure(NestedNCNameID agency2, NestedID findid) {
        for (int i = 0; i < dataStructures.size(); i++) {
            if (dataStructures.get(i).identifiesMe(agency2, findid)) {
                return dataStructures.get(i);
            }
        }
        return null;
    }

    public void dump() {
        for (int i = 0; i < dataStructures.size(); i++) {
            dataStructures.get(i).dump();
        }
    }

    public DataStructureType find(DataStructureReference ref) {
        if (ref.getVersion() == null) {
            for (int i = 0; i < dataStructures.size(); i++) {
                if (dataStructures.get(i).identifiesMe(ref.getAgencyId(), ref.getMaintainableParentId())) {
                    return dataStructures.get(i);
                }
            }
        } else {
            for (int i = 0; i < dataStructures.size(); i++) {
                if (dataStructures.get(i).identifiesMe(ref.getAgencyId(), ref.getMaintainableParentId(), ref.getVersion())) {
                    return dataStructures.get(i);
                }
            }
        }
        return null;
    }

    public void merge(DataStructuresType otherDataStructures) {
        if( otherDataStructures==null ) return;
        this.getDataStructures().addAll(otherDataStructures.getDataStructures());
    }
    public void addDataStructure(DataStructureType dst) {
        this.dataStructures.add(dst);
    }
}

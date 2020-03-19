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
import sdmx.commonreferences.IDType;
import sdmx.commonreferences.NestedID;
import sdmx.commonreferences.NestedNCNameID;
import sdmx.commonreferences.Version;
import sdmx.structure.datastructure.DataStructureType;
import sdmx.structure.metadatastructure.MetadataStructureType;

/**
 *
 * @author James
 */

public class MetadataStructuresType {

    private List<MetadataStructureType> metadataStructures = new ArrayList<MetadataStructureType>();

    /**
     * @return the metadataStructures
     */
    public List<MetadataStructureType> getMetadataStructures() {
        return metadataStructures;
    }

    /**
     * @param metadataStructures the metadataStructures to set
     */
    public void setMetadataStructures(List<MetadataStructureType> metadataStructures) {
        this.metadataStructures = metadataStructures;
    }
public MetadataStructureType findMetadataStructure(String agency,String id,String vers) {
        IDType findid = new IDType(id);
        NestedNCNameID ag = new NestedNCNameID(agency);
        Version ver = new Version(vers);
        return findMetadataStructure(ag,findid,ver);
    }
    public MetadataStructureType findMetadataStructure(NestedNCNameID agency2,NestedID findid,Version ver) {
        for(int i=0;i<metadataStructures.size();i++) {
            if( metadataStructures.get(i).identifiesMe(agency2,findid,ver)) {
                return metadataStructures.get(i);
            }
        }
        return null;
    }
}

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

import java.util.ArrayList;
import java.util.List;
import sdmx.Registry;
import sdmx.commonreferences.DataStructureReference;
import sdmx.commonreferences.IDType;
import sdmx.commonreferences.NestedID;
import sdmx.commonreferences.NestedNCNameID;
import sdmx.commonreferences.Version;
import sdmx.message.DataMessage;
import sdmx.structure.dataflow.DataflowType;
import sdmx.structure.datastructure.DataStructureType;

public class StructuredDataMessage {

    private DataMessage dataMessage = null;
    private Registry registry = null;
    private DataflowType dataflow = null;

    private List<StructuredDataSet> list = new ArrayList<StructuredDataSet>();

    public StructuredDataMessage(DataMessage dat, Registry reg) {
        this.dataMessage = dat;
        this.registry = reg;
        for (int i = 0; i < dataMessage.getDataSets().size(); i++) {
            list.add(buildStructuredDataSet(i));
        }
    }

    public int size() {
        return getDataMessage().getDataSets().size();
    }

    public StructuredDataSet getStructuredDataSet(int i) {
        return list.get(i);
    }

    public StructuredDataSet buildStructuredDataSet(int i) {
        //dataMessage.getHeader().getStructures().get(0).getStructure().dump();
        //NestedNCNameID agency = dataMessage.getHeader().getStructures().get(0).getStructure().getAgencyId();
        //IDType id = dataMessage.getHeader().getStructures().get(0).getStructure().getMaintainableParentId();
        //Version vers = dataMessage.getHeader().getStructures().get(0).getStructure().getMaintainedParentVersion();
        //System.out.println("Ref="+agency+":"+id+":"+vers);
        DataStructureType structure = getRegistry().find(getDataMessage().getHeader().getStructures().get(0).getStructure().asDataStructureReference());
        //System.out.println("Structure="+structure);
        if (this.dataflow == null) {
            this.setDataflow(structure.asDataflow());
        }
        return new StructuredDataSet(getDataMessage().getDataSets().get(i), getRegistry(), structure);
    }

    /**
     * @return the dataMessage
     */
    public DataMessage getDataMessage() {
        return dataMessage;
    }

    /**
     * @return the registry
     */
    public Registry getRegistry() {
        return registry;
    }

    /**
     * @return the dataflow
     */
    public DataflowType getDataflow() {
        return dataflow;
    }

    /**
     * @param dataflow the dataflow to set
     */
    public void setDataflow(DataflowType dataflow) {
        this.dataflow = dataflow;
    }
}

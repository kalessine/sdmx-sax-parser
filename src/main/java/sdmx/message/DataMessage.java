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

import java.util.ArrayList;
import java.util.List;
import sdmx.common.PayloadStructureType;
import sdmx.common.StructureUsage;
import sdmx.commonreferences.DataStructureRef;
import sdmx.commonreferences.DataStructureReference;
import sdmx.commonreferences.StructureUsageRef;
import sdmx.commonreferences.StructureUsageReferenceBase;
import sdmx.commonreferences.StructureUsageReference;
import sdmx.commonreferences.types.StructurePackageTypeCodelistType;
import sdmx.commonreferences.types.StructureUsageTypeCodelistType;
import sdmx.data.DataSet;
import sdmx.data.flat.FlatDataSet;
import sdmx.structure.StructuresType;
import sdmx.structure.base.StructureUsageType;
import sdmx.xml.anyURI;

/**
	<xs:complexType name="GenericDataType">
		<xs:annotation>
			<xs:documentation>GenericDataType defines the contents of a generic data message.</xs:documentation>
		</xs:annotation>
		<xs:complexContent>
			<xs:restriction base="MessageType">
				<xs:sequence>
					<xs:element name="Header" type="GenericDataHeaderType"/>
					<xs:element name="DataSet" type="data:DataSetType" minOccurs="0" maxOccurs="unbounded"/>
					<xs:element ref="footer:Footer" minOccurs="0"/>
				</xs:sequence>
			</xs:restriction>
		</xs:complexContent>
	</xs:complexType>

 * @author James
 */

public class DataMessage extends MessageType {
    private String namespace = null;
    private String namespacePrefix = null;
    
    private List<DataSet> dataSets = null;
    public DataMessage(){
    }
    public DataMessage(List<DataSet> datasets){
        this.dataSets=datasets;
    }

    /**
     * @return the structures
     */
    public List<DataSet> getDataSets() {
        return dataSets;
    }

    /**
     * @param structures the structures to set
     */
    public void setDataSets(List<DataSet> datasets) {
        this.dataSets=datasets;
    }
    public void dump() {
        for(int i=0;i<dataSets.size();i++) {
            dataSets.get(i).dump();
        }
    }

    /**
     * @return the namespace
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * @param namespace the namespace to set
     */
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    /**
     * @return the namespacePrefix
     */
    public String getNamespacePrefix() {
        return namespacePrefix;
    }

    /**
     * @param namespacePrefix the namespacePrefix to set
     */
    public void setNamespacePrefix(String namespacePrefix) {
        this.namespacePrefix = namespacePrefix;
    }
    public void setDataStructure(DataStructureReference ref,anyURI uri) {
        List<PayloadStructureType> structures = this.getHeader().getStructures();
        if( structures == null ) {
            structures = new ArrayList<PayloadStructureType>();
        }
        PayloadStructureType payload = structures.size()==0?null:structures.get(0);
        if( payload!=null ) {
            //System.out.println("Payload Ref:");
            //payload.getStructure().dump();
            //System.out.println("New Ref");
            //ref.dump();
            
            return;
        }
        payload = new PayloadStructureType();
        payload.setStructure(ref);
        structures.add(payload);
        getHeader().setStructures(structures);
    }
}

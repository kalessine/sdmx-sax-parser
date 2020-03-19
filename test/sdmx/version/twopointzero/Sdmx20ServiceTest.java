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
package sdmx.version.twopointzero;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import sdmx.Registry;
import sdmx.SdmxIO;
import sdmx.commonreferences.DataStructureReference;
import sdmx.commonreferences.IDType;
import sdmx.commonreferences.NestedNCNameID;
import sdmx.commonreferences.Version;
import sdmx.message.DataMessage;
import sdmx.message.DataQueryMessage;
import sdmx.message.StructureType;
import sdmx.query.base.QueryIDType;
import sdmx.query.base.TimeValue;
import sdmx.query.data.AttributeValueType;
import sdmx.query.data.DataParametersAndType;
import sdmx.query.data.DataParametersOrType;
import sdmx.query.data.DataParametersType;
import sdmx.query.data.DataQuery;
import sdmx.query.data.DataQueryType;
import sdmx.query.data.DimensionValueType;
import sdmx.query.data.TimeDimensionValueType;
import sdmx.net.LocalRegistry;
import sdmx.net.service.sdw.Sdmx20SOAPQueryable;
import sdmx.structure.dataflow.DataflowType;
import sdmx.structure.datastructure.DataStructureType;
import sdmx.structureddata.ValueTypeResolver;

/**
 *
 * @author James
 */
public class Sdmx20ServiceTest {
/*
    @Test
    public void testFAOREGIONAL() throws IOException {
        RESTServiceRegistry registry = new RESTServiceRegistry("FAO", "http://www.fao.org/figis/sdmx");
        long t1 = System.currentTimeMillis();
        DataStructureType faoStruct = registry.findDataStructure(new NestedNCNameIDType("FAO"), new IDType("REGIONAL_CAPTURE_DATASTRUCTURE"), new VersionType("0.1"));
        long t2 = System.currentTimeMillis();
        System.out.println("Loaded FAO Capture Struct:"+(t2-t1)+" ms");
        InputStream in8 = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/fao-20/RECOFI_CAPTURE.xml");
        DataMessage data8 = SdmxIO.parseData(in8,true);
        //data8.dump();
        long t3 = System.currentTimeMillis();
        System.out.println("Read:"+data8.getDataSets().get(0).size()+" Observations "+(t3-t2)+" ms");
        ValueTypeResolver.resolveDataSet(registry, data8.getDataSets().get(0), faoStruct);
        long t4 = System.currentTimeMillis();
        System.out.println("Resolution:"+data8.getDataSets().get(0).size()+" Observations "+(t4-t3)+" ms");
        //data8.dump();
    }
    @Test
    public void testFAOFAOSTAT() throws IOException {
        //RESTServiceRegistry registry = new RESTServiceRegistry("FAO", "http://data.fao.org/sdmx");
        Registry registry = LocalRegistry.getDefaultWorkspace();
        InputStream in = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/fao-20/FAOSTAT_DATASTRUCTURE.xml");
        StructureType st = SdmxIO.parseStructure(registry, in);
        long t1 = System.currentTimeMillis();
        DataStructureType faoStruct = registry.findDataStructure(new NestedNCNameIDType("FAO"), new IDType("FAOSTAT"));
        System.out.println("FAO Struct="+faoStruct);
        long t2 = System.currentTimeMillis();
        System.out.println("Loaded FAOStat Struct:"+(t2-t1)+" ms");
        InputStream in9 = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/fao-20/CROP_PRODUCTION.xml");
        DataMessage data9 = SdmxIO.parseData(in9,true);
        //data9.dump();
        long t3 = System.currentTimeMillis();
        System.out.println("Read:"+data9.getDataSets().get(0).size()+" Observations "+(t3-t2)+" ms");
        ValueTypeResolver.resolveDataSet(registry, data9.getDataSets().get(0), faoStruct);
        long t4 = System.currentTimeMillis();
        System.out.println("Resolution:"+data9.getDataSets().get(0).size()+" Observations "+(t4-t3)+" ms");
        //data9.dump();
    }
    */ /*
    @Test
    public void testServiceParse3() {
        QueryableServiceRegistry registry = new QueryableServiceRegistry(new Sdmx20SOAPQueryable("ABS", "http://stat.abs.gov.au/sdmxws/sdmx.asmx"));
        registry.findDataStructure(new NestedNCNameIDType("ABS"), new IDType("CPI"),null);
        registry.findDataStructure(new NestedNCNameIDType("ABS"), new IDType("ALC"),null);
        registry.findDataStructure(new NestedNCNameIDType("ABS"), new IDType("BA_GCCSA"),null);
        registry.findDataStructure(new NestedNCNameIDType("ABS"), new IDType("BA_SA2"),null);
        registry.findDataStructure(new NestedNCNameIDType("ABS"), new IDType("BOP"),null);
        
        QueryableServiceRegistry registry2 = new QueryableServiceRegistry(new Sdmx20SOAPQueryable("OECD", "http://stats.oecd.org/SDMXWS/sdmx.asmx"));
        registry2.findDataStructure(new NestedNCNameIDType("OECD"), new IDType("CSPCUBE"),null);
        registry2.findDataStructure(new NestedNCNameIDType("OECD"), new IDType("CSP2012"),null);
        registry2.findDataStructure(new NestedNCNameIDType("OECD"), new IDType("SNA_TABLE1"),null);
        registry2.findDataStructure(new NestedNCNameIDType("OECD"), new IDType("SNA_TABLE10"),null);
        registry2.findDataStructure(new NestedNCNameIDType("OECD"), new IDType("AEO11_OVERVIEW_CHAPTER5_TAB3_EN"),null);
        QueryableServiceRegistry registry3 = new QueryableServiceRegistry(new Sdmx20SOAPQueryable("IMF", "http://sdmxws.imf.org/IMFStatWS_SDMX2/sdmx.asmx"));
         
        }*/
    /*
    @Test
    public void testABS8NR() throws IOException {
        QueryableServiceRegistry registry = new QueryableServiceRegistry(new Sdmx20SOAPQueryable("ABS", "http://stat.abs.gov.au/sdmxws/sdmx.asmx"));
        DataStructureType nrpver5Struct = null;
        try {
            nrpver5Struct = registry.findDataStructure(new NestedNCNameIDType("ABS"), new IDType("8NRPVER5"));
            System.out.println("Struct="+nrpver5Struct);
        } catch (Exception ex) {
            Logger.getLogger(Sdmx20DataParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        long t1 = System.currentTimeMillis();
        InputStream in4 = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/abs-20/8nrpver5-data.xml");
        DataMessage data4 = SdmxIO.parseData(in4);
        long t2 = System.currentTimeMillis();
        System.out.println("Read:"+data4.getDataSets().get(0).size()+" Observations "+(t2-t1)+" ms");
        ValueTypeResolver.resolveDataSet(registry, data4.getDataSets().get(0), nrpver5Struct);
        long t3 = System.currentTimeMillis();
        System.out.println("Resolution:"+data4.getDataSets().get(0).size()+" Observations "+(t3-t2)+" ms");
        //data4.dump();
    }*/
    /*
    @Test
    public void testLoad() throws IOException {
        Sdmx20SOAPQueryable queryable = new Sdmx20SOAPQueryable("ABS", "http://stat.abs.gov.au/sdmxws/sdmx.asmx");
        QueryableServiceRegistry registry = new QueryableServiceRegistry(queryable);

        DataStructureType cpiStruct = null;
        long t1 = System.currentTimeMillis();
        try {
            cpiStruct = registry.findDataStructure(new NestedNCNameIDType("ABS"), new IDType("CPI"));
            System.out.println("Struct="+cpiStruct);
        } catch (Exception ex) {
            Logger.getLogger(Sdmx20DataParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        long t2 = System.currentTimeMillis();
        System.out.println("Loaded Structure CPI "+(t2-t1)+" ms");
        DataQueryMessage query = new DataQueryMessage();
        query.setHeader(registry.getBaseHeader());
        DataQuery q = new DataQuery();
        DataParametersAndType dw = new DataParametersAndType();
        dw.setDataSetId(Collections.singletonList(new QueryIDType("CPI")));
        List<DimensionValueType> dims = new ArrayList<DimensionValueType>();
        dims.add(new DimensionValueType("REGION","50"));
        dims.add(new DimensionValueType("FREQUENCY","Q"));
        dw.setDimensionValue(dims);
        List<AttributeValueType> atts = new ArrayList<AttributeValueType>();
        atts.add(new AttributeValueType("TIME_FORMAT","P3M"));
        dw.setAttributeValue(atts);
        dw.setTimeDimensionValue(Collections.singletonList(new TimeDimensionValueType(new TimeValue("2011-Q1"),new TimeValue("2011-Q4"))));
        DataParametersOrType or1 = new DataParametersOrType();
        dims = new ArrayList<DimensionValueType>();
        dims.add(new DimensionValueType("MEASURE","1"));
        dims.add(new DimensionValueType("MEASURE","2"));
        dims.add(new DimensionValueType("MEASURE","3"));
        or1.setDimensionValue(dims);
        DataParametersOrType or2 = new DataParametersOrType();
        dims = new ArrayList<DimensionValueType>();
        dims.add(new DimensionValueType("INDEX","10001"));
        dims.add(new DimensionValueType("INDEX","20001"));
        dims.add(new DimensionValueType("INDEX","20006"));
        dims.add(new DimensionValueType("INDEX","20002"));
        dims.add(new DimensionValueType("INDEX","20003"));
        dims.add(new DimensionValueType("INDEX","20004"));
        dims.add(new DimensionValueType("INDEX","115486"));
        dims.add(new DimensionValueType("INDEX","20005"));
        dims.add(new DimensionValueType("INDEX","115488"));
        dims.add(new DimensionValueType("INDEX","115489"));
        dims.add(new DimensionValueType("INDEX","115493"));
        dims.add(new DimensionValueType("INDEX","126670"));
        dims.add(new DimensionValueType("INDEX","999901"));
        dims.add(new DimensionValueType("INDEX","999902"));
        dims.add(new DimensionValueType("INDEX","999903"));
        or2.setDimensionValue(dims);
        DataParametersOrType or3 = new DataParametersOrType();
        dims = new ArrayList<DimensionValueType>();
        dims.add(new DimensionValueType("TSEST","10"));
        dims.add(new DimensionValueType("TSEST","20"));
        or3.setDimensionValue(dims);
        List<DataParametersOrType> list = new ArrayList<>();
        list.add(or1);
        list.add(or2);
        list.add(or3);
        dw.setOr(list);
        DataParametersType dpt = new DataParametersType();
        dpt.setAnd(Collections.singletonList(dw));
        q.setDataWhere(dpt);
        query.setQuery(q);
        long t3 = System.currentTimeMillis();
        DataMessage dm = queryable.getCompactData(query);
        long t4 = System.currentTimeMillis();
        System.out.println("Got CompactData "+dm.getDataSets().get(0).size()+" observations "+(t4-t3)+" ms");
        dm.dump();
        
        ValueTypeResolver.resolveDataSet(registry, dm.getDataSets().get(0), cpiStruct);
        long t5 = System.currentTimeMillis();
        System.out.println("Resolution:"+dm.getDataSets().get(0).size()+" Observations "+(t5-t4)+" ms");
        dm.dump();
    }*/
    @Test
    public void testABSList() throws IOException {
        //System.out.println("Waiting 4 seconds");
        //try{Thread.sleep(4000);}catch(InterruptedException io) {}
        Sdmx20SOAPQueryable queryable = new Sdmx20SOAPQueryable("ABS", "http://stat.abs.gov.au/sdmxws/sdmx.asmx");
        queryable.setSoapNamespace("http://stats.oecd.org/OECDStatWS/SDMX/");
        Registry registry = queryable;
        List<DataflowType> list = registry.listDataflows();
        Iterator<DataflowType> it = list.iterator();
        while(it.hasNext()){
            DataflowType flow = it.next();
            System.out.println("Flow "+flow.getAgencyID()+":"+flow.getId()+":"+flow.getVersion());
            DataStructureReference ref = flow.getStructure();
            try{
            DataStructureType ds = registry.find(ref);
            ds.dump();
            }catch(Exception ie) {}
        }
    }
/*
    @Test
    public void testIMFList() throws IOException {
        Sdmx20SOAPQueryable queryable = new Sdmx20SOAPQueryable("IMF", "http://sdmxws.imf.org/IMFStatWS_SDMX2/sdmx.asmx");
        queryable.setSoapNamespace("http://stats.imf.org/DotStatWS/SDMX/");
        QueryableServiceRegistry registry = new QueryableServiceRegistry(queryable);
        List<DataStructureReferenceType> list = registry.listDataStructures();
        Iterator<DataStructureReferenceType> it = list.iterator();
        while(it.hasNext()){
            DataStructureReferenceType ref = it.next();
            System.out.println(ref.getRef().getAgencyId()+":"+ref.getRef().getId()+":"+ref.getRef().getVersion());
            DataStructureType ds = registry.findDataStructure(ref.getRef().getAgencyId(), new IDType(ref.getRef().getId().toString()),ref.getRef().getVersion());
            ds.dump();
            
        }
    }*/
    /*
    @Test
    public void testOECDList() throws IOException {
        Sdmx20SOAPQueryable queryable = new Sdmx20SOAPQueryable("OECD", "http://stats.oecd.org/SDMXWS/sdmx.asmx");
        QueryableServiceRegistry registry = new QueryableServiceRegistry(queryable);
        List<DataStructureReferenceType> list = registry.listDataSets();
        Iterator<DataStructureReferenceType> it = list.iterator();
        while(it.hasNext()){
            DataStructureReferenceType ref = it.next();
            DataStructureType ds = registry.findDataStructure(ref.getRef().getAgencyId(), new IDType(ref.getRef().getId().toString()),ref.getRef().getVersion());
            ds.dump();
            System.out.println(ref.getRef().getAgencyId()+":"+ref.getRef().getId()+":"+ref.getRef().getVersion());
        }
    }*/
}

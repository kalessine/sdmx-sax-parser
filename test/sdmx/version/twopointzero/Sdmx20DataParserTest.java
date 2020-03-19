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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import sdmx.Registry;
import sdmx.SdmxIO;
import sdmx.commonreferences.IDType;
import sdmx.commonreferences.NestedNCNameID;
import sdmx.commonreferences.Version;
import sdmx.exception.ParseException;
import sdmx.message.DataMessage;
import sdmx.message.StructureType;
import sdmx.net.LocalRegistry;
import sdmx.structure.base.Component;
import sdmx.structure.datastructure.DataStructureType;
import sdmx.structureddata.ValueTypeResolver;

/**
 *
 * @author James
 */
public class Sdmx20DataParserTest {
    @BeforeClass
    public static void setUpClass() {
        System.out.println("Start Test Data Parsing");
    }
    
    @AfterClass
    public static void tearDownClass() {
        System.out.println("Finish Test Data Parsing");
    }
    
    Registry registry = LocalRegistry.getDefaultWorkspace();
    
    @Before
    public void setUp() throws IOException {
        try {
            StructureType struct = null;
            try {
                InputStream in = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/sdmx20-samples/StructureSample.xml");
                struct = SdmxIO.parseStructure(in);
                LocalRegistry.getDefaultWorkspace().load(struct);
            } catch (IOException ex) {
                Logger.getLogger(Sdmx20DataParserTest.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(Sdmx20DataParserTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            long t1= System.currentTimeMillis();
            InputStream in = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/sdmx20-samples/CompactSample.xml");
            data = SdmxIO.parseData(in);
            long t2 = System.currentTimeMillis();
            System.out.println("Read:"+data.getDataSets().get(0).size()+" Observations "+(t2-t1)+" ms");
            data.dump();
        } catch (ParseException ex) {
            Logger.getLogger(Sdmx20DataParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of isSdmx20 method, of class Sdmx20StructureParserProvider.
     */
    DataMessage data = null;
    @Test
    public void testCompactSample1() throws IOException {
        String result = "JD014";
        assertEquals(result,data.getHeader().getId());
    }
    @Test
    public void testCompactSample2() throws IOException {
        Boolean result = Boolean.TRUE;
        assertEquals(result,data.getHeader().getTest());
    }
    @Test
    public void testCompactSample3() throws IOException {
        String result = "en";
        assertEquals(result,data.getHeader().getNames().get(0).getLang());
    }
    @Test
    public void testCompactSample4() throws IOException {
        String result = "Trans46305";
        assertEquals(result,data.getHeader().getNames().get(0).getText());
    }
    @Test
    public void testCompactSample5() throws IOException {
        String result = "2001-03-11T09:30:47-05:00";
        assertEquals(result,data.getHeader().getPrepared().getDate().toString());
    }
    @Test
    public void testCompactSample6() throws IOException {
        String result = "2001-03-11T09:30:47-05:00";
        assertEquals(result,data.getHeader().getPrepared().getDate().toString());
    }
    @Test
    public void testCompactSample7() throws IOException {
        String result = "BIS";
        assertEquals(result,data.getHeader().getSender().getId().toString());
    }
    @Test
    public void testCompactSample8() throws IOException {
        String result = "Bank for International Settlements";
        assertEquals(result,data.getHeader().getSender().getNames().get(0).getText());
    }
    @Test
    public void testCompactSample9() throws IOException {
        String result = "en";
        assertEquals(result,data.getHeader().getSender().getNames().get(0).getLang());
    }
    @Test
    public void testCompactSample10() throws IOException {
        assertNotNull(data.getHeader().getSender().getContacts());
    }
    @Test
    public void testCompactSample11() throws IOException {
        String result = "G.B. Smith";
        assertEquals(result,data.getHeader().getSender().getContacts().get(0).getNames().get(0).getText());
    }
    @Test
    public void testGeneric() throws IOException, ParseException {
        System.out.println("Test Generic");
        StructureType struct = null;
        try {
            InputStream in = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/sdmx20-samples/StructureSample.xml");
            struct = SdmxIO.parseStructure(in);
            LocalRegistry.getDefaultWorkspace().load(struct);
        } catch (IOException ex) {
            Logger.getLogger(Sdmx20DataParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        long t1= System.currentTimeMillis();
        InputStream in = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/sdmx20-samples/GenericSample.xml");
        DataMessage data = SdmxIO.parseData(in);
        long t2 = System.currentTimeMillis();
        System.out.println("Read:"+data.getDataSets().get(0).size()+" Observations "+(t2-t1)+" ms");
        data.dump();
    }

    @Test
    public void testABSCPI() throws IOException, ParseException {
        StructureType cpiStruct = null;
        try {
            InputStream in = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/abs-20/cpi-structure.xml");
            cpiStruct = SdmxIO.parseStructure(in);
            LocalRegistry.getDefaultWorkspace().load(cpiStruct);
        } catch (IOException ex) {
            Logger.getLogger(Sdmx20DataParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        long t1= System.currentTimeMillis();
        InputStream in2 = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/abs-20/cpi-data.xml");
        DataMessage data2 = SdmxIO.parseData(in2);
        long t2 = System.currentTimeMillis();
        System.out.println("Read:"+data2.getDataSets().get(0).size()+" Observations "+(t2-t1)+" ms");
        data2.dump();
    }
    @Test
    public void testIMFPGI() throws IOException, ParseException {
        StructureType pgiStruct = null;
        try {
            InputStream in = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/imf-20/PGIDSD.xml");
            pgiStruct = SdmxIO.parseStructure(in);
            LocalRegistry.getDefaultWorkspace().load(pgiStruct);
        } catch (IOException ex) {
            Logger.getLogger(Sdmx20DataParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        long t1= System.currentTimeMillis();
        InputStream in3 = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/imf-20/PGICompact.xml");
        DataMessage data3 = SdmxIO.parseData(in3);
        long t2 = System.currentTimeMillis();
        System.out.println("Read:"+data3.getDataSets().get(0).size()+" Observations "+(t2-t1)+" ms");
        //data3.dump();
    }

    @Test
    public void testABSALC() throws IOException, ParseException {
        StructureType alcStruct = null;
        try {
            InputStream in = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/abs-20/alc-structure.xml");
            alcStruct = SdmxIO.parseStructure(in);
            LocalRegistry.getDefaultWorkspace().load(alcStruct);
        } catch (IOException ex) {
            Logger.getLogger(Sdmx20DataParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        long t1 = System.currentTimeMillis();
        InputStream in5 = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/abs-20/alc-data.xml");
        DataMessage data5 = SdmxIO.parseData(in5);
        long t2 = System.currentTimeMillis();
        System.out.println("Read:"+data5.getDataSets().get(0).size()+" Observations "+(t2-t1)+" ms");
    }
    @Test
    public void testABSCensus() throws IOException, ParseException {
        StructureType cenStruct = null;
        try {
            InputStream in = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/abs-20/abs_census2011_t33.xml");
            cenStruct = SdmxIO.parseStructure(in);
            LocalRegistry.getDefaultWorkspace().load(cenStruct);
        } catch (IOException ex) {
            Logger.getLogger(Sdmx20DataParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Component c = cenStruct.getStructures().getDataStructures().getDataStructures().get(0).getDataStructureComponents().findDimension("INDP");
        assertNotNull(c);
    }
    @Test
    public void testABSCensus2() throws IOException, ParseException {
        StructureType cenStruct = null;
        try {
            InputStream in = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/abs-20/abs_census2011_b01.xml");
            cenStruct = SdmxIO.parseStructure(in);
            LocalRegistry.getDefaultWorkspace().load(cenStruct);
        } catch (IOException ex) {
            Logger.getLogger(Sdmx20DataParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Component c = cenStruct.getStructures().getDataStructures().getDataStructures().get(0).getDataStructureComponents().findDimension("INDP");
        System.out.println("Component="+c);
    }
    @Test
    public void testABSCensus3() throws IOException, ParseException {
        StructureType cenStruct = null;
        try {
            InputStream in = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/abs-20/abs_census2011_b03.xml");
            cenStruct = SdmxIO.parseStructure(in);
            LocalRegistry.getDefaultWorkspace().load(cenStruct);
        } catch (IOException ex) {
            Logger.getLogger(Sdmx20DataParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Component c = cenStruct.getStructures().getDataStructures().getDataStructures().get(0).getDataStructureComponents().findDimension("INDP");
        //System.out.println("Component="+c);
    }
    /* 
       This test is quite long....
       * */
/*
    @Test
    public void testUISStruct() throws IOException {
        StructureType uisStruct = null;
        try {
            InputStream in = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/uis-20/structure.xml");
            uisStruct = SdmxIO.parseStructure(in);
            LocalRegistry.getDefaultWorkspace().load(uisStruct);
        } catch (IOException ex) {
            Logger.getLogger(Sdmx20DataParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        long t1 = System.currentTimeMillis();
        InputStream in6 = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/uis-20/28b18979-129f-43bc-94ae-42f31add907a.xml");
        DataMessage data6 = SdmxIO.parseData(in6);
        long t2 = System.currentTimeMillis();
        System.out.println("Read:"+data6.getDataSets().get(0).size()+" Observations "+(t2-t1)+" ms");
        ValueTypeResolver.resolveDataSet(LocalRegistry.getDefaultWorkspace(), data6.getDataSets().get(0), uisStruct.getStructures().getDataStructures().getDataStructures().get(0));
        long t3 = System.currentTimeMillis();
        System.out.println("Resolution:"+data6.getDataSets().get(0).size()+" Observations "+(t3-t2)+" ms");
        //data6.dump();
    }*/
/*
    @Test
    public void testFAOCAPTURE() throws IOException {
        RESTServiceRegistry registry = new RESTServiceRegistry("FAO", "http://www.fao.org/figis/sdmx");
        long t1 = System.currentTimeMillis();
        DataStructureType faoStruct = registry.findDataStructure(new NestedNCNameIDType("FAO"), new IDType("CAPTURE_DATASTRUCTURE"), new VersionType("0.1"));
        long t2 = System.currentTimeMillis();
        System.out.println("Loaded FAO Capture Struct:"+(t2-t1)+" ms");
        InputStream in7 = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/fao-20/CAPTURE_DATA.xml");
        DataMessage data7 = SdmxIO.parseData(in7);
        long t3 = System.currentTimeMillis();
        System.out.println("Read:"+data7.getDataSets().get(0).size()+" Observations "+(t3-t2)+" ms");
        ValueTypeResolver.resolveDataSet(registry, data7.getDataSets().get(0), faoStruct);
        long t4 = System.currentTimeMillis();
        System.out.println("Resolution:"+data7.getDataSets().get(0).size()+" Observations "+(t4-t3)+" ms");
        data7.dump();
    }
    */
}

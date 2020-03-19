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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import sdmx.Registry;
import sdmx.structureddata.ValueTypeResolver;
import sdmx.message.DataMessage;
import sdmx.message.StructureType;
import sdmx.SdmxIO;
import sdmx.version.twopointzero.writer.CompactDataWriter;
import sdmx.net.LocalRegistry;
import sdmx.exception.ParseException;
import sdmx.version.common.ParseParams;
import sdmx.version.twopointzero.writer.StreamingCompactDataWriter;
import sdmx.version.twopointzero.writer.GenericDataWriter;
import sdmx.version.twopointzero.writer.StreamingGenericDataWriter;

/**
 *
 * @author James
 */
public class Sdmx20DataWriteTest {
    @BeforeClass
    public static void setUpClass() {
        System.out.println("Start Test Data Writing");
    }
    
    @AfterClass
    public static void tearDownClass() {
        System.out.println("Finish Test Data Writing");
    }
    
    Registry registry = LocalRegistry.getDefaultWorkspace();
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of isSdmx20 method, of class Sdmx20StructureParserProvider.
     */
    @Test
    public void testCompactSample() throws IOException, XMLStreamException, ParseException {
        long t1= System.currentTimeMillis();
        InputStream in = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/sdmx20-samples/CompactSample.xml");
        DataMessage data = SdmxIO.parseData(in);
        long t2 = System.currentTimeMillis();
        System.out.println("Read:"+data.getDataSets().get(0).size()+" Observations "+(t2-t1)+" ms");
        long t3 = System.currentTimeMillis();
        OutputStream out = new FileOutputStream("testOut/CompactSampleOut.xml");
        CompactDataWriter.write(data, out);
        out.close();
        data.dump();
    }
    @Test
    public void testCompactSample2() throws IOException, XMLStreamException, ParseException {
        long t1= System.currentTimeMillis();
        InputStream in = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/abs-20/DSID1230571/DSID1230571 - CompactData.xml");
        DataMessage data = SdmxIO.parseData(in);
        long t2 = System.currentTimeMillis();
        System.out.println("Read:"+data.getDataSets().get(0).size()+" Observations "+(t2-t1)+" ms");
        long t3 = System.currentTimeMillis();
        OutputStream out = new FileOutputStream("testOut/DSID1230571 - CompactDataOut.xml");
        CompactDataWriter.write(data, out);
        out.close();
        data.dump();
    }
    @Test
    public void testCompactSample3() throws IOException, XMLStreamException, ParseException {
        long t1= System.currentTimeMillis();
        InputStream in = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/abs-20/DSID1230571/DSID1230571 - GenericData.xml");
        DataMessage data = SdmxIO.parseData(in);
        long t2 = System.currentTimeMillis();
        System.out.println("Read:"+data.getDataSets().get(0).size()+" Observations "+(t2-t1)+" ms");
        long t3 = System.currentTimeMillis();
        OutputStream out = new FileOutputStream("testOut/Generic DSID1230571 - CompactOut.xml");
        CompactDataWriter.write(data, out);
        out.close();
        data.dump();
    }
    @Test
    public void testGenericCompactSample() throws IOException, XMLStreamException, ParseException {
        long t1= System.currentTimeMillis();
        InputStream in = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/sdmx20-samples/CompactSample.xml");
        InputStream structIn = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/sdmx20-samples/StructureSample.xml");
        LocalRegistry reg = LocalRegistry.getDefaultWorkspace();
        ParseParams params = new ParseParams();
        params.setRegistry(reg);
        StructureType struct = SdmxIO.parseStructure(params,structIn);
        DataMessage data = SdmxIO.parseData(in);
        long t2 = System.currentTimeMillis();
        System.out.println("Read:"+data.getDataSets().get(0).size()+" Observations "+(t2-t1)+" ms");
        long t3 = System.currentTimeMillis();
        OutputStream out = new FileOutputStream("testOut/CompactSample-GenericOut.xml");
        GenericDataWriter.write(data, out, struct.getStructures().getDataStructures().getDataStructures().get(0).asReference(), params.getRegistry());
        out.close();
        data.dump();
    }
    @Test
    public void testGenericSample2() throws IOException, XMLStreamException, ParseException {
        long t1= System.currentTimeMillis();
        InputStream in = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/abs-20/DSID1230571/DSID1230571 - CompactData.xml");
        InputStream structIn = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/abs-20/DSID1230571/DSID1230571 - DataSD.xml");
        LocalRegistry reg = LocalRegistry.getDefaultWorkspace();
        ParseParams params = new ParseParams();
        params.setRegistry(reg);
        StructureType struct = SdmxIO.parseStructure(params,structIn);
        
        DataMessage data = SdmxIO.parseData(in);
        long t2 = System.currentTimeMillis();
        System.out.println("Read:"+data.getDataSets().get(0).size()+" Observations "+(t2-t1)+" ms");
        long t3 = System.currentTimeMillis();
        OutputStream out = new FileOutputStream("testOut/DSID230571-out.xml");
        GenericDataWriter.write(data, out, struct.getStructures().getDataStructures().getDataStructures().get(0).asReference(), reg);
        out.close();
        data.dump();
    }
    
    @Test
    public void testGenericSample3() throws IOException, XMLStreamException, ParseException {
        long t1= System.currentTimeMillis();
        InputStream in = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/abs-20/DSID1230571/DSID1230571 - CompactData.xml");
        InputStream structIn = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/abs-20/DSID1230571/DSID1230571 - DataSD.xml");
        LocalRegistry reg = LocalRegistry.getDefaultWorkspace();
        ParseParams params = new ParseParams();
        params.setRegistry(reg);
        StructureType struct = SdmxIO.parseStructure(params,structIn);
        long t2 = System.currentTimeMillis();
        OutputStream out = new FileOutputStream("testOut/DSID230571-out-scdw.xml");
        StreamingCompactDataWriter w2 = StreamingCompactDataWriter.openWriter(out);
        params.setCallbackHandler(w2);
        SdmxIO.parseDataStream(w2,in);
        long t3 = System.currentTimeMillis();
        System.out.println("Time to Stream Compact 2.0 DSID1230571 - "+(t3-t2));
    }
    @Test
    public void testGenericSample4() throws IOException, XMLStreamException, ParseException {
        long t1= System.currentTimeMillis();
        InputStream in = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/abs-20/DSID1230571/DSID1230571 - CompactData.xml");
        InputStream structIn = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/abs-20/DSID1230571/DSID1230571 - DataSD.xml");
        LocalRegistry reg = LocalRegistry.getDefaultWorkspace();
        ParseParams params = new ParseParams();
        params.setRegistry(reg);
        StructureType struct = SdmxIO.parseStructure(params,structIn);
        long t2 = System.currentTimeMillis();
        OutputStream out = new FileOutputStream("testOut/DSID230571-out-sgdw.xml");
        StreamingGenericDataWriter w2 = StreamingGenericDataWriter.openWriter(out,registry);
        params.setCallbackHandler(w2);
        SdmxIO.parseDataStream(w2,in);
        long t3 = System.currentTimeMillis();
        System.out.println("Time to Stream Generic 2.0 DSID1230571 - "+(t3-t2));
    }    

}

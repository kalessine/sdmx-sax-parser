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
package sdmx.version.json;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import sdmx.Registry;
import sdmx.SdmxIO;
import sdmx.commonreferences.DataStructureReference;
import sdmx.exception.ParseException;
import sdmx.message.StructureType;
import sdmx.net.LocalRegistry;
import sdmx.structure.dataflow.DataflowType;
import sdmx.version.common.ParseDataCallbackHandler;
import sdmx.version.common.ParseParams;
import sdmx.version.twopointone.writer.Sdmx21StreamingDataWriterTest;

/**
 *
 * @author James
 */
public class JSONStreamingTest {
    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }
    @Before
    public void setUp() throws ParseException {
    }

    @After
    public void tearDown() {
    }
    @Test
    public void testStructureSpecific1() {
        try {
            Registry reg = LocalRegistry.getDefaultWorkspace();
            StructureType struct = SdmxIO.parseStructure(new FileInputStream("test/resources/sdmx21-samples/exr/ecb_exr_ng/ecb_exr_ng_full-edited.xml"));
            reg.load(struct);
            DataflowType flow = struct.getStructures().getDataStructures().getDataStructures().get(0).asDataflow();
            FileOutputStream fos = new FileOutputStream("testOut/ecb_exr_ng_ts-streaming.json");
            FileOutputStream fos2 = new FileOutputStream("testOut/ecb_exr_ng_full-edited.xml");
            ParseParams params = new ParseParams();
            params.setRegistry(reg);
            SdmxIO.writeStructure("application/vnd.sdmx.structure+xml;version=2.1", struct, fos2);

            ParseDataCallbackHandler cbHandler = SdmxIO.openForStreamWriting("application/vnd.sdmx.data+json;version=1.0.0-wd", fos, params);
            params.setCallbackHandler(cbHandler);
            FileInputStream fin = new FileInputStream("test/resources/sdmx21-samples/exr/ecb_exr_ng/structured/ecb_exr_ng_ts.xml");
            SdmxIO.parseData(fin);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Sdmx21StreamingDataWriterTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Sdmx21StreamingDataWriterTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Sdmx21StreamingDataWriterTest.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    @Test
    public void testUIS() throws IOException {
        StructureType uisStruct = null;
        ParseParams params = new ParseParams();
        params.setRegistry(new LocalRegistry());
        try {
            InputStream in = JSONStreamingTest.class.getResourceAsStream("/resources/uis-20/structure.xml");
            uisStruct = SdmxIO.parseStructure(params,in);
        } catch (IOException ex) {
            Logger.getLogger(JSONStreamingTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(JSONStreamingTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        InputStream dataIn = JSONStreamingTest.class.getResourceAsStream("/resources/uis-20/28b18979-129f-43bc-94ae-42f31add907a.xml");
        FileOutputStream fos = new FileOutputStream("testOut/uis-data.json");
        ParseDataCallbackHandler cbHandler = SdmxIO.openForStreamWriting("application/vnd.sdmx.data+json;version=1.0.0-wd", fos, params);
        long t1 = System.currentTimeMillis();
        try {
            SdmxIO.parseDataStream(cbHandler,dataIn);
            //data6.dump();
        } catch (ParseException ex) {
            Logger.getLogger(JSONStreamingTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        long t2 = System.currentTimeMillis();
        System.out.println("Time taken to convert uis sdmx to json = "+(t2-t1));
    }
}

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
package sdmx.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import sdmx.Registry;
import sdmx.message.DataMessage;
import sdmx.message.StructureType;
import sdmx.SdmxIO;
import sdmx.version.twopointzero.Sdmx20DataParserTest;
import sdmx.version.twopointzero.Sdmx20StructureParserTest;
import sdmx.exception.ParseException;
import sdmx.net.LocalRegistry;

/**
 *
 * @author James
 */
public class StructuredDataTest {
    Registry registry = LocalRegistry.getDefaultWorkspace();
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
        System.out.println("Finish Test Structured Data");
    }
    @Test
    public void testCompactSampleFlat() throws IOException, ParseException {
        System.out.println("Start Flat Benchmark");
        StructureType struct = null;
        try {
            InputStream in = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/sdmx20-samples/StructureSample.xml");
            struct = SdmxIO.parseStructure(in);
            LocalRegistry.getDefaultWorkspace().load(struct);
        } catch (IOException ex) {
            Logger.getLogger(Sdmx20DataParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println("Primary Measure="+struct.getStructures().getDataStructures().getDataStructures().get(0).getDataStructureComponents().getMeasureList().getPrimaryMeasure().getLocalRepresentation().getTextFormat().getTextType().toString());
        long t1= System.currentTimeMillis();
        InputStream in = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/sdmx20-samples/CompactSample.xml");
        DataMessage data = SdmxIO.parseData(in);
        long t2 = System.currentTimeMillis();
        System.out.println("Read:"+data.getDataSets().get(0).size()+" Observations "+(t2-t1)+" ms");
        data.dump();        
    }
    @Test
    public void testCompactSample() throws IOException, ParseException {
        System.out.println("Start Structured Test");
        StructureType struct = null;
        try {
            InputStream in = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/sdmx20-samples/StructureSample.xml");
            struct = SdmxIO.parseStructure(in);
            LocalRegistry.getDefaultWorkspace().load(struct);
        } catch (IOException ex) {
            Logger.getLogger(Sdmx20DataParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        long t1= System.currentTimeMillis();
        InputStream in = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/sdmx20-samples/CompactSample.xml");
        DataMessage data = SdmxIO.parseData(in);
        long t2 = System.currentTimeMillis();
        System.out.println("Read:"+data.getDataSets().get(0).size()+" Observations "+(t2-t1)+" ms");
        data.dump();
        
    }
}

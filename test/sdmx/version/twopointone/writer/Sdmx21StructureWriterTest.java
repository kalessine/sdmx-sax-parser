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
package sdmx.version.twopointone.writer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import sdmx.SdmxIO;
import sdmx.exception.ParseException;
import sdmx.message.BaseHeaderType;
import sdmx.message.StructureType;
import sdmx.version.twopointone.Sdmx21StructureParserTest;

public class Sdmx21StructureWriterTest {

    public Sdmx21StructureWriterTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }
    StructureType doc1 = null;
    StructureType doc2 = null;
    StructureType doc3 = null;

    @Before
    public void setUp() throws ParseException {
        try {
            InputStream in = Sdmx21StructureParserTest.class.getResourceAsStream("/resources/sdmx21-samples/demography/demography.xml");
            doc1 = SdmxIO.parseStructure(in);
        } catch (IOException ex) {
            Logger.getLogger(Sdmx21StructureParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            InputStream in = Sdmx21StructureParserTest.class.getResourceAsStream("/resources/abs-20/alc-structure.xml");
            doc2 = SdmxIO.parseStructure(in);
        } catch (IOException ex) {
            Logger.getLogger(Sdmx21StructureParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            InputStream in = Sdmx21StructureParserTest.class.getResourceAsStream("/resources/abs-20/8nrpver5-structure.xml");
            doc3 = SdmxIO.parseStructure(in);
        } catch (IOException ex) {
            Logger.getLogger(Sdmx21StructureParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @After
    public void tearDown() {
        doc1 = null;
        doc2 = null;
        doc3 = null;
    }

    @Test
    public void test1() {
        try {
            FileOutputStream fos = new FileOutputStream("testOut/structureOut1.xml");
            Sdmx21StructureWriter.write(doc1, fos);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Sdmx21StructureWriterTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Sdmx21StructureWriterTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    @Test
    public void test2() {
        try {
            FileOutputStream fos = new FileOutputStream("testOut/structureOut2.xml");
            Sdmx21StructureWriter.write(doc2, fos);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Sdmx21StructureWriterTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Sdmx21StructureWriterTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    @Test
    public void test3() {
        try {
            FileOutputStream fos = new FileOutputStream("testOut/structureOut3.xml");
            Sdmx21StructureWriter.write(doc3, fos);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Sdmx21StructureWriterTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Sdmx21StructureWriterTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}

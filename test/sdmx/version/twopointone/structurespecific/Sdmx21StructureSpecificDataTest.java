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
package sdmx.version.twopointone.structurespecific;

import sdmx.version.twopointone.structurespecific.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.xml.sax.Attributes;
import sdmx.SdmxIO;
import sdmx.exception.ParseException;
import sdmx.message.DataMessage;
import sdmx.message.HeaderTimeType;

/**
 *
 * @author James
 */
public class Sdmx21StructureSpecificDataTest {
    
    public Sdmx21StructureSpecificDataTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    DataMessage msg = null;
    @Before
    public void setUp() throws FileNotFoundException, IOException, ParseException {
        FileInputStream fis = new FileInputStream("test/resources/sdmx21-samples/demography/demography_xs.xml");
        msg = SdmxIO.parseData(fis);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testStructureSpecific1() throws IOException {
        String result = "DEMO_XS_EX";
        assertEquals(result,msg.getHeader().getId());
    }   
    @Test
    public void testStructureSpecific2() throws IOException {
        Boolean test = msg.getHeader().getTest();
        assertEquals(Boolean.TRUE,test);
    }   
    @Test
    public void testStructureSpecific3() throws IOException {
        HeaderTimeType prepared = msg.getHeader().getPrepared();
        Locale[] locs = Locale.getAvailableLocales();
        Locale loc = null;
        
        for(int i=0;i<locs.length;i++) {
            if( locs[i].getDisplayCountry().equals("Colombia")) loc = locs[i];
        }
        Calendar c=  Calendar.getInstance(loc);
        c.setTime(prepared.getDate().getDate());
        assertEquals(2011,c.get(Calendar.YEAR));
        assertEquals(Calendar.NOVEMBER,c.get(Calendar.MONTH));
        assertEquals(25,c.get(Calendar.DAY_OF_MONTH));
        assertEquals(1,c.get(Calendar.HOUR));
        assertEquals(21,c.get(Calendar.MINUTE));
        assertEquals(49,c.get(Calendar.SECOND));
    }   
    @Test
    public void testStructureSpecific4() throws IOException {
        assertEquals("ESTSAT",msg.getHeader().getSender().getId().toString());
    }   
    @Test
    public void testStructureSpecific5() throws IOException {
        assertEquals("STR1",msg.getHeader().getStructures().get(0).getStructureID().toString());
    }   
    @Test
    public void testStructureSpecific6() throws IOException {
        assertEquals("DEMO",msg.getHeader().getStructures().get(0).getDimensionAtObservation().toString());
    }   
    @Test
    public void testStructureSpecific7() throws IOException {
        assertEquals("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=ESTAT:DEMOGRAPHY(1.0):ObsLevelDim:DEMO:explicit",msg.getHeader().getStructures().get(0).getNamespace().getString());
    }   
    @Test
    public void testStructureSpecific8() throws IOException {
        assertEquals(Boolean.TRUE,msg.getHeader().getStructures().get(0).isExplicitMeasures());
    }   
    @Test
    public void testStructureSpecific9() throws IOException {
        assertEquals("ESTAT",msg.getHeader().getStructures().get(0).getStructure().getAgencyId().toString());
    }   
    @Test
    public void testStructureSpecific10() throws IOException {
        assertEquals("DEMOGRAPHY",msg.getHeader().getStructures().get(0).getStructure().getId().toString());
    }   
    @Test
    public void testStructureSpecific11() throws IOException {
        assertEquals("1.0",msg.getHeader().getStructures().get(0).getStructure().getVersion().toString());
    }   
}

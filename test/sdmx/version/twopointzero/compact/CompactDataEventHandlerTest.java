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
package sdmx.version.twopointzero.compact;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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

/**
 *
 * @author James
 */
public class CompactDataEventHandlerTest {
    
    public CompactDataEventHandlerTest() {
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
        FileInputStream fis = new FileInputStream("test/resources/sdmx20-samples/CompactSample.xml");
        msg = SdmxIO.parseData(fis);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testCompactSample1() throws IOException {
        String result = "JD014";
        assertEquals(result,msg.getHeader().getId());
    }   
    @Test
    public void testCompactSample2() throws IOException {
        Boolean result = Boolean.TRUE;
        assertEquals(result,msg.getHeader().getTest());
    }   
    @Test
    public void testCompactSample3() throws IOException {
        String result = "Trans46305";
        assertEquals(result,msg.getHeader().getNames().get(0).getText());
    }   
    @Test
    public void testCompactSample4() throws IOException {
        String result = "en";
        assertEquals(result,msg.getHeader().getNames().get(0).getLang());
    }   
    @Test
    public void testCompactSample5() throws IOException {
        String result = "BIS";
        assertEquals(result,msg.getHeader().getSender().getId().toString());
    }   
    @Test
    public void testCompactSample6() throws IOException {
        String result = "Bank for International Settlements";
        assertEquals(result,msg.getHeader().getSender().getNames().get(0).getText());
    }   
    @Test
    public void testCompactSample7() throws IOException {
        String result = "en";
        assertEquals(result,msg.getHeader().getSender().getNames().get(0).getLang());
    }   
    @Test
    public void testCompactSample8() throws IOException {
        String result = "G.B. Smith";
        assertEquals(result,msg.getHeader().getSender().getContacts().get(0).getNames().get(0).getText());
    }   
    @Test
    public void testCompactSample9() throws IOException {
        String result = "en";
        assertEquals(result,msg.getHeader().getSender().getContacts().get(0).getNames().get(0).getLang());
    }   
    @Test
    public void testCompactSample10() throws IOException {
        String result = "+000.000.0000";
        assertEquals(result,msg.getHeader().getSender().getContacts().get(0).getTelephones().get(0));
    }   
    @Test
    public void testCompactSample11() throws IOException {
        String result = "ECB";
        assertEquals(result,msg.getHeader().getReceivers().get(0).getId().toString());
    }   
    @Test
    public void testCompactSample12() throws IOException {
        String result = "European Central Bank";
        assertEquals(result,msg.getHeader().getReceivers().get(0).getNames().get(0).getText());
    }   
    @Test
    public void testCompactSample13() throws IOException {
        String result = "en";
        assertEquals(result,msg.getHeader().getReceivers().get(0).getNames().get(0).getLang());
    }   
    @Test
    public void testCompactSample14() throws IOException {
        String result = "B.S. Featherstone";
        assertEquals(result,msg.getHeader().getReceivers().get(0).getContacts().get(0).getNames().get(0).getText());
    }   
    @Test
    public void testCompactSample15() throws IOException {
        String result = "en";
        assertEquals(result,msg.getHeader().getReceivers().get(0).getContacts().get(0).getNames().get(0).getLang());
    }   
    @Test
    public void testCompactSample16() throws IOException {
        String result = "Statistics Division";
        assertEquals(result,msg.getHeader().getReceivers().get(0).getContacts().get(0).getDepartments().get(0).getText());
    }   
    @Test
    public void testCompactSample17() throws IOException {
        String result = "en";
        assertEquals(result,msg.getHeader().getReceivers().get(0).getContacts().get(0).getDepartments().get(0).getLang());
    }   
    @Test
    public void testCompactSample18() throws IOException {
        String result = "+000.000.0001";
        assertEquals(result,msg.getHeader().getReceivers().get(0).getContacts().get(0).getTelephones().get(0));
    }   
    @Test
    public void testCompactSample19() throws IOException {
        String result = "Append";
        assertEquals(result,msg.getHeader().getDataSetAction().toString());
    }   
    @Test
    public void testCompactSample20() throws IOException {
        String result = "2001-03-11T09:30:47-05:00";
        assertEquals(result,msg.getHeader().getExtracted().toString());
    }   
    @Test
    public void testCompactSample21() throws IOException {
        String result = "2000-01-01T00:00:00";
        assertEquals(result,msg.getHeader().getReportingBegin().toString());
    }   
    @Test
    public void testCompactSample22() throws IOException {
        String result = "2000-12-01T00:00:00";
        assertEquals(result,msg.getHeader().getReportingEnd().toString());
    }   
}

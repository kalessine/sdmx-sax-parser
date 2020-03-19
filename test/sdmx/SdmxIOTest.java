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
package sdmx;

import sdmx.SdmxIO;
import java.io.InputStream;
import java.io.PushbackInputStream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sdmx.message.StructureType;

/**
 *
 * @author James
 */
public class SdmxIOTest {
    
    public SdmxIOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of checkVersion method, of class SdmxIO.
     */
    @Test
    public void testCheckVersion1() throws Exception {
        System.out.println("checkVersion1");
        InputStream in = SdmxIOTest.class.getResourceAsStream("/resources/sdmx20-samples/CompactSample.xml");
        PushbackInputStream pushback = new PushbackInputStream(in,8192);
        int expResult = SdmxIO.VERSION20;
        int result = SdmxIO.checkVersion(pushback);
        assertEquals(expResult, result);
    }
    @Test
    public void testCheckVersion2() throws Exception {
        System.out.println("checkVersion2");
        InputStream in = SdmxIOTest.class.getResourceAsStream("/resources/sdmx20-samples/CrossSectionalSample.xml");
        PushbackInputStream pushback = new PushbackInputStream(in,8192);
        int expResult = SdmxIO.VERSION20;
        int result = SdmxIO.checkVersion(pushback);
        assertEquals(expResult, result);
    }
    @Test
    public void testCheckVersion3() throws Exception {
        System.out.println("checkVersion3");
        InputStream in = SdmxIOTest.class.getResourceAsStream("/resources/sdmx20-samples/GenericSample.xml");
        PushbackInputStream pushback = new PushbackInputStream(in,8192);
        int expResult = SdmxIO.VERSION20;
        int result = SdmxIO.checkVersion(pushback);
        assertEquals(expResult, result);
    }
    @Test
    public void testCheckVersion4() throws Exception {
        System.out.println("checkVersion4");
        InputStream in = SdmxIOTest.class.getResourceAsStream("/resources/sdmx21-samples/demography/demography_xs.xml");
        PushbackInputStream pushback = new PushbackInputStream(in,8192);
        int expResult = SdmxIO.VERSION21;
        int result = SdmxIO.checkVersion(pushback);
        assertEquals(expResult, result);
    }
}

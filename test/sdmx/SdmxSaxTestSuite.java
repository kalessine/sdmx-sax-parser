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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sdmx.commonreferences.ReferenceTypeTest;
import sdmx.cube.CubeTest;
import sdmx.data.StructuredDataTest;
import sdmx.message.StructureTypeTest;
import sdmx.registry.LocalRegistryTest;
import sdmx.version.common.SOAPStripperInputStreamTest;
import sdmx.version.twopointone.Sdmx21StructureParserTest;
import sdmx.version.twopointone.writer.Sdmx21StreamingDataWriterTest;
import sdmx.version.twopointzero.Sdmx20DataParserTest;
import sdmx.version.twopointzero.Sdmx20DataWriteTest;
import sdmx.version.twopointzero.Sdmx20ServiceTest;
import sdmx.version.twopointzero.Sdmx20StructureParserTest;
import sdmx.version.twopointzero.Sdmx20StructureWriteTest;
import sdmx.version.twopointzero.compact.CompactDataEventHandlerTest;
import sdmx.version.twopointzero.generic.GenericDataContentHandlerTest;

/**
 *
 * @author James
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    SdmxIOTest.class,
    Sdmx21StructureParserTest.class,
    Sdmx20StructureParserTest.class,
    Sdmx20DataParserTest.class,
    //Sdmx20ServiceTest.class,
    SOAPStripperInputStreamTest.class,
    StructuredDataTest.class,
    Sdmx20DataWriteTest.class,
    LocalRegistryTest.class,
    StructureTypeTest.class,
    CompactDataEventHandlerTest.class,
    GenericDataContentHandlerTest.class,
    CubeTest.class,
    ReferenceTypeTest.class,
    Sdmx20StructureWriteTest.class,
    Sdmx21StreamingDataWriterTest.class
})
public class SdmxSaxTestSuite {
    @BeforeClass
    public static void setUpClass() throws Exception {
    }
    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    @Before
    public void setUp() throws Exception {
    }
    @After
    public void tearDown() throws Exception {
    }

}

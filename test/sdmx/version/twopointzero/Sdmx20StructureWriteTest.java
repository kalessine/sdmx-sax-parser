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
import javax.xml.stream.XMLStreamException;
import org.junit.Test;
import sdmx.SdmxIO;
import sdmx.exception.ParseException;
import sdmx.message.DataMessage;
import sdmx.message.StructureType;
import sdmx.net.LocalRegistry;
import sdmx.version.common.ParseParams;
import sdmx.version.twopointzero.writer.GenericDataWriter;
import sdmx.version.twopointzero.writer.Sdmx20StructureWriter;

/**
 *
 * @author James
 */
public class Sdmx20StructureWriteTest {
    @Test
    public void testGenericSample2() throws IOException, XMLStreamException, ParseException {
        long t1= System.currentTimeMillis();
        InputStream structIn = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/abs-20/DSID1230571/DSID1230571 - DataSD.xml");
        LocalRegistry reg = LocalRegistry.getDefaultWorkspace();
        ParseParams params = new ParseParams();
        params.setRegistry(reg);
        StructureType struct = SdmxIO.parseStructure(params,structIn);

        OutputStream out = new FileOutputStream("testOut/DSID1230571 - DataSD-2.0.xml");
        Sdmx20StructureWriter.write(struct, out);
        out.close();
    }
    @Test
    public void testGenericSample1() throws IOException, XMLStreamException, ParseException {
        long t1= System.currentTimeMillis();
        InputStream structIn = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/abs-20/abs_census2011_t33.xml");
        LocalRegistry reg = LocalRegistry.getDefaultWorkspace();
        ParseParams params = new ParseParams();
        params.setRegistry(reg);
        StructureType struct = SdmxIO.parseStructure(params,structIn);
        OutputStream out = new FileOutputStream("testOut/abs_census2011_t33-out.xml");
        Sdmx20StructureWriter.write(struct, out);
        out.close();
    }
}


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

package sdmx.cube;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import javax.xml.stream.XMLStreamException;
import static org.junit.Assert.*;
import org.junit.Test;
import sdmx.SdmxIO;
import sdmx.exception.ParseException;
import sdmx.message.DataMessage;
import sdmx.message.StructureType;
import sdmx.version.twopointzero.Sdmx20StructureParserTest;
import sdmx.version.twopointzero.writer.CompactDataWriter;

/**
 *
 * @author James
 */
public class CubeTest {
    
    public CubeTest() {
    }

    @Test
    public void testCompactSample2() throws IOException, XMLStreamException, ParseException {
        long t1= System.currentTimeMillis();
        InputStream in = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/sdmx20-samples/CompactSample.xml");
        InputStream in2 = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/sdmx20-samples/StructureSample.xml");
        DataMessage data = SdmxIO.parseData(in);//Flat
        StructureType struct = SdmxIO.parseStructure(in2);
        long t2 = System.currentTimeMillis();
        System.out.println("Read:"+data.getDataSets().get(0).size()+" Observations "+(t2-t1)+" ms");
        Cube cube = new Cube(struct.getStructures().getDataStructures().getDataStructures().get(0),struct);
        cube = data.getDataSets().get(0).query(cube,null);
        long t3 = System.currentTimeMillis();
        System.out.println("Made Cube "+(t3-t2)+" ms");
        cube.getRootCubeDimension().dump();
    }
/*
    @Test
    public void testCompactSample3() throws IOException, XMLStreamException, ParseException {
        long t1= System.currentTimeMillis();
        InputStream in = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/uis-20/structure.xml");
        StructureType struct = SdmxIO.parseStructure(in);

        ZipFile zipFile = new ZipFile("./test/resources/uis-20/28b18979-129f-43bc-94ae-42f31add907a.zip");
        InputStream in2 = zipFile.getInputStream(zipFile.entries().nextElement());
        DataMessage data = SdmxIO.parseData(in2,true);//Flat
        
        long t2 = System.currentTimeMillis();
        System.out.println("Read:"+data.getDataSets().get(0).size()+" Observations "+(t2-t1)+" ms");
        Cube cube = new Cube(struct.getStructures().getDataStructures().getDataStructures().get(0));
        cube = data.getDataSets().get(0).query(cube,null);
        long t3 = System.currentTimeMillis();
        System.out.println("Made Cube "+(t3-t2)+" ms");
        //cube.getRootCubeDimension().dump();
    }
*/
}

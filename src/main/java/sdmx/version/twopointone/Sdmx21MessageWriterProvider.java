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
package sdmx.version.twopointone;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sdmx.SdmxIO;
import sdmx.message.DataMessage;
import sdmx.message.StructureType;
import sdmx.util.DataUtilities;
import sdmx.version.common.ParseParams;
import sdmx.version.common.SdmxWriterProvider;
import sdmx.version.twopointone.writer.Sdmx21StructureWriter;
import sdmx.version.twopointone.writer.StreamingStructureSpecificDataWriter;
import sdmx.version.twopointone.writer.StreamingStructureSpecificTimeSeriesWriter;
import sdmx.version.twopointzero.writer.StreamingGenericDataWriter;

/**
 *
 * @author James
 */
public class Sdmx21MessageWriterProvider implements SdmxWriterProvider {
    
    static{
        SdmxIO.register(new Sdmx21MessageWriterProvider());
    }
    
    List<String> supported = new ArrayList<String>();
    {
        //supported.add("application/vnd.sdmx.genericdata+xml;version=2.1");
        //supported.add("application/vnd.sdmx.generictimeseriesdata+xml;version=2.1");
        supported.add("application/vnd.sdmx.structurespecificdata+xml;version=2.1");
        supported.add("application/vnd.sdmx.structurespecifictimeseriesdata+xml;version=2.1");
        //supported.add("application/vnd.sdmx.genericmetadata+xml;version=2.1");
        //supported.add("application/vnd.sdmx.structurespecificmetadata+xml;version=2.1");
        supported.add("application/vnd.sdmx.structure+xml;version=2.1");
        //supported.add("application/vnd.sdmx.schema+xml;version=2.1");
    }
    @Override
    public List<String> getSupportedMIMETypes() {
        return Collections.unmodifiableList(supported);
    }

    @Override
    public void save(ParseParams params,String mime,OutputStream out, DataMessage message) {
        if( "application/vnd.sdmx.structurespecificdata+xml;version=2.1".equals(mime)) {
            DataUtilities.writeTo(message, new StreamingStructureSpecificDataWriter(out));
        }
        else if( "application/vnd.sdmx.structurespecifictimeseriesdata+xml;version=2.1".equals(mime)) {
            DataUtilities.writeTo(message, new StreamingStructureSpecificTimeSeriesWriter(out));
        }
        else if( "application/vnd.sdmx.genericdata+xml;version=2.1".equals(mime)) {
            DataUtilities.writeTo(message, new StreamingGenericDataWriter(out,params.getRegistry()));
        }else {
            throw new RuntimeException(mime+" not supported by "+getClass().getName());
        }

    }

    @Override
    public void save(ParseParams params,String mime,OutputStream out, StructureType message) throws IOException {
        if("application/vnd.sdmx.structure+xml;version=2.1".equals(mime)) {
                Sdmx21StructureWriter.write(message, out);
        }else {
            throw new RuntimeException(mime+" is not supported by "+getClass().getName());
        }
    }
    
}

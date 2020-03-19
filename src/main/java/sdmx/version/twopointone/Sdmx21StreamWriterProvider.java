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

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import sdmx.Registry;
import sdmx.SdmxIO;
import static sdmx.SdmxIO.SAVE_MIME_TYPES;
import sdmx.commonreferences.DataStructureReference;
import sdmx.structure.dataflow.DataflowType;
import sdmx.version.common.ParseDataCallbackHandler;
import sdmx.version.common.ParseParams;
import sdmx.version.common.SdmxStreamWriterProvider;
import sdmx.version.twopointone.writer.StreamingGeneric21DataWriter;
import sdmx.version.twopointone.writer.StreamingStructureSpecificDataWriter;
import sdmx.version.twopointone.writer.StreamingStructureSpecificTimeSeriesWriter;

/**
 *
 * @author James
 */
public class Sdmx21StreamWriterProvider implements SdmxStreamWriterProvider {
    static{
        SdmxIO.register(new Sdmx21StreamWriterProvider());
    }
    List<String> supported = new ArrayList<String>();
    {
        supported.add("application/vnd.sdmx.genericdata+xml;version=2.1");
        supported.add("application/vnd.sdmx.generictimeseriesdata+xml;version=2.1");
        supported.add("application/vnd.sdmx.structurespecificdata+xml;version=2.1");
        supported.add("application/vnd.sdmx.structurespecifictimeseriesdata+xml;version=2.1");
        //supported.add("application/vnd.sdmx.genericmetadata+xml;version=2.1");
        //supported.add("application/vnd.sdmx.structurespecificmetadata+xml;version=2.1");
        //supported.add("application/vnd.sdmx.structure+xml;version=2.1");
        //supported.add("application/vnd.sdmx.schema+xml;version=2.1");

    }
    
    @Override
    public List<String> getSupportedMIMETypes() {
        return Collections.unmodifiableList(supported);
    }

    @Override
    public ParseDataCallbackHandler openForWriting(ParseParams params,String mime, OutputStream out) {
        if("application/vnd.sdmx.genericdata+xml;version=2.1".equals(mime)){
            return new StreamingGeneric21DataWriter(out, params.getRegistry());
        }
        if("application/vnd.sdmx.generictimeseriesdata+xml;version=2.1".equals(mime)){
            return new StreamingGeneric21DataWriter(out, params.getRegistry());
        }
        if("application/vnd.sdmx.structurespecificdata+xml;version=2.1".equals(mime)){
            StreamingStructureSpecificDataWriter sssdw = new StreamingStructureSpecificDataWriter(out);
            sssdw.setRegistry(params.getRegistry());
            return sssdw;
        }
        if("application/vnd.sdmx.structurespecifictimeseriesdata+xml;version=2.1".equals(mime)){
            StreamingStructureSpecificTimeSeriesWriter sssdw = new StreamingStructureSpecificTimeSeriesWriter(out);
            sssdw.setRegistry(params.getRegistry());
            return sssdw;
        }
        throw new RuntimeException("MIME type:"+mime+" not supported by "+getClass().getName());
    }
    
}

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
import java.io.InputStream;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.xmlbeans.XmlException;
import org.xml.sax.SAXException;
import sdmx.Registry;
import sdmx.message.StructureType;
import sdmx.SdmxIO;
import sdmx.version.common.SdmxParserProvider;
import sdmx.data.flat.FlatDataSet;
import sdmx.message.DataMessage;
import sdmx.data.DataSetWriter;
import sdmx.data.flat.FlatDataSetReader;
import sdmx.data.flat.FlatDataSetWriter;
import sdmx.data.structured.StructuredDataWriter;
import sdmx.version.common.ParseDataCallbackHandler;
import sdmx.version.common.ParseParams;
import sdmx.version.twopointone.generic.GenericData21ContentHandler;
import sdmx.version.twopointone.generic.GenericData21EventHandler;
import sdmx.version.twopointone.structurespecific.StructureSpecificContentHandler;
import sdmx.version.twopointone.structurespecific.StructureSpecificEventHandler;
import sdmx.version.twopointzero.compact.CompactDataContentHandler;
import sdmx.version.twopointzero.compact.CompactDataEventHandler;

/**
 *
 * @author James
 */
public class Sdmx21ParserProvider implements SdmxParserProvider {

    static {
        SdmxIO.register(new Sdmx21ParserProvider());
    }

    @Override
    public int getVersionIdentifier() {
        return SdmxIO.VERSION21;
    }

    public static boolean isSdmx21(String header) {
        if (header.indexOf("StructureSpecificTimeSeriesData ") != -1) {
            return true;
        } else if (header.indexOf("GenericTimeSeriesData ") != -1) {
            return true;
        } else if (header.indexOf("GenericData ") != -1 && header.indexOf("\"http://www.sdmx.org/resources/sdmxml/schemas/v2_1/message\"") != -1) {
            return true;
        } else if (header.indexOf("StructureSpecificTimeSeriesData ") != -1) {
            return true;
        } else if (header.indexOf("StructureSpecificData ") != -1) {
            return true;
        } else if (header.indexOf("StructureSpecificData ") != -1) {
            return true;
        } else if (header.indexOf("Structure") != -1 && header.indexOf("http://www.sdmx.org/resources/sdmxml/schemas/v2_1/message") != -1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean canParse(String header) {
        return isSdmx21(header);
    }

    @Override
    public StructureType parseStructure(ParseParams params, InputStream in) {
        try {
            return Sdmx21StructureReaderTools.toStructureDocument(in);
        } catch (XmlException ex) {
            Logger.getLogger(Sdmx21ParserProvider.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Sdmx21ParserProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean isStructure(String header) {
        if (header.indexOf(":Structure ") != -1) {
            return true;
        } else if (header.indexOf("<Structure ") != -1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isData(String header) {
        if (header.indexOf("GenericData") != -1) {
            return true;
        } else if (header.indexOf("GenericTimeSeriesData") != -1) {
            return true;
        } else if (header.indexOf("StructureSpecificData") != -1) {
            return true;
        } else if (header.indexOf("StructureSpecificTimeSeriesData") != -1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isMetadata(String header) {
        if (header.indexOf("GenericMetadata") != -1) {
            return true;
        } else if (header.indexOf("StructureSpecificMetadata") != -1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isStructureSpecificData(String header) {
        if (header.indexOf("StructureSpecificData ") != -1) {
            return true;
        } else if (header.indexOf("StructureSpecificTimeSeriesData ") != -1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isGenericData(String header) {
        if (header.indexOf("GenericData ") != -1) {
            return true;
        } else if (header.indexOf("GenericTimeSeriesData ") != -1) {
            return true;
        } else {
            return false;
        }
    }

    public DataMessage parseData(ParseParams params, InputStream in) throws IOException {
        if (isStructureSpecificData(params.getHeader())) {
            return parseStructureSpecificData(in, params.getCallbackHandler());
        }
        if (isGenericData(params.getHeader())) {
            return parseGenericData(in, params.getCallbackHandler());
        }
        return null;
    }

    public DataMessage parseStructureSpecificData(InputStream in, ParseDataCallbackHandler cbHandler) throws IOException {
        System.out.println("parseStructureSpecificData(in,cbHandler)");
        DataSetWriter writer = cbHandler != null ? cbHandler.getDataSetWriter() : new StructuredDataWriter();
        StructureSpecificEventHandler event = new StructureSpecificEventHandler(writer);
        if (cbHandler != null) {
            event = new StructureSpecificEventHandler(cbHandler);
        } else {
            event = new StructureSpecificEventHandler(writer);
        }
        StructureSpecificContentHandler handler = new StructureSpecificContentHandler(in, event);
        try {
            return handler.parse();
        } catch (SAXException ex) {
            Logger.getLogger(Sdmx21ParserProvider.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Sdmx21ParserProvider.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        return null;
    }

    @Override
    public StructureType parseStructure(ParseParams params, Reader in) throws IOException {
        try {
            return Sdmx21StructureReaderTools.toStructureDocument(in);
        } catch (XmlException ex) {
            Logger.getLogger(Sdmx21ParserProvider.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Sdmx21ParserProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public DataMessage parseData(ParseParams params, Reader in) throws IOException {
        if (isStructureSpecificData(params.getHeader())) {
            return parseStructureSpecificData(in, params.getCallbackHandler());
        }
        if (isGenericData(params.getHeader())) {
            return parseGenericData(in, params.getCallbackHandler());
        }
        return null;
    }

    public DataMessage parseStructureSpecificData(Reader in, ParseDataCallbackHandler cbHandler) throws IOException {
        DataSetWriter writer = cbHandler != null ? cbHandler.getDataSetWriter() : new StructuredDataWriter();
        StructureSpecificEventHandler event = null;
        if (cbHandler != null) {
            event = new StructureSpecificEventHandler(cbHandler);
        } else {
            event = new StructureSpecificEventHandler(writer);
        }

        StructureSpecificContentHandler handler = new StructureSpecificContentHandler(in, event);
        try {
            return handler.parse();
        } catch (SAXException ex) {
            Logger.getLogger(Sdmx21ParserProvider.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Sdmx21ParserProvider.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        return null;
    }

    public DataMessage parseGenericData(InputStream in, ParseDataCallbackHandler cbHandler) throws IOException {
        DataSetWriter writer = cbHandler != null ? cbHandler.getDataSetWriter() : new StructuredDataWriter();
        GenericData21EventHandler event = null;
        if (cbHandler != null) {
            //event = new GenericData21EventHandler(cbHandler);
            event = new GenericData21EventHandler(cbHandler);
        } else {
            event = new GenericData21EventHandler(writer);
        }
        GenericData21ContentHandler handler = new GenericData21ContentHandler(in, event);
        try {
            return handler.parse();
        } catch (SAXException ex) {
            ex.printStackTrace();
            Logger.getLogger(Sdmx21ParserProvider.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(Sdmx21ParserProvider.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        return null;
    }

    public DataMessage parseGenericData(Reader in, ParseDataCallbackHandler cbHandler) throws IOException {
        DataSetWriter writer = cbHandler != null ? cbHandler.getDataSetWriter() : new StructuredDataWriter();
        GenericData21EventHandler event = new GenericData21EventHandler(writer);
        GenericData21ContentHandler handler = new GenericData21ContentHandler(in, event);
        try {
            return handler.parse();
        } catch (SAXException ex) {
            Logger.getLogger(Sdmx21ParserProvider.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Sdmx21ParserProvider.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        return null;
    }

    @Override
    public FlatDataSetReader getDataSetReader(Reader in) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

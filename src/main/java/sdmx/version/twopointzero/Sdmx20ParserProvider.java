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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;
import org.apache.xmlbeans.XmlException;
import org.jdom.Namespace;
import org.xml.sax.SAXException;
import sdmx.Registry;
import sdmx.data.DataSetWriter;
import sdmx.data.flat.FlatDataSet;
import sdmx.data.flat.FlatDataSetWriter;
import sdmx.data.structured.StructuredDataWriter;
import sdmx.exception.TypeValueNotFoundException;
import sdmx.message.DataMessage;
import sdmx.message.StructureType;
import sdmx.SdmxIO;
import sdmx.data.flat.FlatDataSetReader;
import sdmx.version.common.SdmxParserProvider;
import sdmx.version.twopointone.Sdmx21ParserProvider;
import sdmx.version.twopointzero.compact.CompactDataContentHandler;
import sdmx.version.twopointzero.compact.CompactDataEventHandler;
import sdmx.version.twopointzero.generic.GenericDataContentHandler;
import sdmx.version.twopointzero.generic.GenericDataEventHandler;
import sdmx.version.twopointzero.writer.CompactDataWriter;
import sdmx.net.LocalRegistry;
import sdmx.version.common.ParseDataCallbackHandler;
import sdmx.version.common.ParseParams;

/**
 *
 * @author James
 */

public class Sdmx20ParserProvider implements SdmxParserProvider {

    static {
        SdmxIO.register(new Sdmx20ParserProvider());
    }

    public static boolean isSdmx20(String header) {
        if (header.indexOf("CompactData") != -1) {
            return true;
        } else if (header.indexOf("MessageGroup") != -1) {
            return true;
        } else if (header.indexOf("GenericData") != -1) {
            return true;
        } else if (header.indexOf("RegistryInterface") != -1) {
            return true;
        } else if (header.indexOf("GenericData") != -1 && header.indexOf("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message") != -1) {
            System.out.println("IsGenericData 2.0");
            return true;
        } else if (header.indexOf("CrossSectionalData") != -1) {
            return true;
        } else if (header.indexOf("<Structure xmlns=\"http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message\"") != -1) {
            return true;
        } else if (header.indexOf(":Structure xmlns=\"http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message\"") != -1) {
            return true;
        } else if (header.indexOf("Structure") != -1 && header.indexOf("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message") != -1) {
            return true;
        } else if (header.indexOf("http://www.w3.org/2003/05/soap-envelope") != -1 && header.indexOf("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message") != -1) {
            return true;
        } else {
            System.out.println("IsNot 2.0");
            return false;
        }
    }

    @Override
    public int getVersionIdentifier() {
        return SdmxIO.VERSION20;
    }

    @Override
    public boolean canParse(String header) {
        if (isSdmx20(header)) {
            return true;
        }
        return false;
    }

    public StructureType parseStructure(ParseParams params, InputStream in) throws IOException {
        try {
            StructureType doc;
            
            if (params.getHeader().indexOf("http://schemas.xmlsoap.org/soap/envelope/") > -1) {
                doc = new Sdmx20StructureReaderTools(params).parseSOAPStructure(in);
            } else if (params.getHeader().indexOf("http://www.w3.org/2003/05/soap-envelope") > -1) {
                doc = new Sdmx20StructureReaderTools(params).parseSOAPStructure(in);
            } else if (!isRegistryInterface(params.getHeader())) {
                doc = new Sdmx20StructureReaderTools(params).parseStructure(in);
            } else {
                doc = new Sdmx20StructureReaderTools(params).parseRegistry(in);
            }
            return doc;
        } catch (XmlException | IOException ex) {
            Logger.getLogger(Sdmx20ParserProvider.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } catch (TypeValueNotFoundException ex) {
            Logger.getLogger(Sdmx20ParserProvider.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public StructureType parseStructure(ParseParams params, Reader in) {
        try {
            StructureType doc;
            if (!isRegistryInterface(params.getHeader())) {
                doc = new Sdmx20StructureReaderTools(params).parseStructure(in);
            } else {
                doc = new Sdmx20StructureReaderTools(params).parseRegistry(in);
            }
            return doc;
        } catch (XmlException | IOException ex) {
            Logger.getLogger(Sdmx20ParserProvider.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } catch (TypeValueNotFoundException ex) {
            Logger.getLogger(Sdmx20ParserProvider.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isStructure(String header) {
        if (header.indexOf("Structure ") != -1) {
            return true;
        } else if (header.indexOf("RegistryInterface ") != -1) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isRegistryInterface(String header) {
        if (header.indexOf("RegistryInterface ") != -1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isData(String header) {
        if (header.indexOf("CompactData") != -1) {
            return true;
        } else if (header.indexOf("GenericData") != -1) {
            return true;
        } else if (header.indexOf("CrossSectionalData") != -1) {
            return true;
        } else if (header.indexOf("MessageGroup") != -1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isCompactData(String header) {
        if (header.indexOf("CompactData") != -1) {
            return true;
        } else if (header.indexOf("GenericData") != -1) {
            return false;
        } else {
            return false;
        }
    }

    public boolean isGenericData(String header) {
        if (header.indexOf("GenericData") != -1) {
            return true;
        }
        if (header.indexOf("MessageGroup") != -1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isMetadata(String header) {
        return false;
    }

    @Override
    public DataMessage parseData(ParseParams params, InputStream in) throws IOException {
        if (params.getCallbackHandler() == null) {
            if (isCompactData(params.getHeader())) {
                return parseCompactData(in);
            }
            if (isGenericData(params.getHeader())) {
                return parseGenericData(in);
            }
        } else {
            if (isCompactData(params.getHeader())) {
                return parseCompactData(in, params.getCallbackHandler());
            }
            if (isGenericData(params.getHeader())) {
                return parseGenericData(in, params.getCallbackHandler());
            }
        }
        return null;
    }

    public DataMessage parseCompactData(InputStream in) throws IOException {
        CompactDataEventHandler event = new CompactDataEventHandler();
        CompactDataContentHandler handler = new CompactDataContentHandler(in, event);
        try {
            return handler.parse();
        } catch (SAXException ex) {
            Logger.getLogger(Sdmx20ParserProvider.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Sdmx20ParserProvider.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        return null;
    }

    public DataMessage parseGenericData(InputStream in) throws IOException {
        GenericDataContentHandler handler = new GenericDataContentHandler(in);
        try {
            return handler.parse();
        } catch (SAXException ex) {
            Logger.getLogger(Sdmx20ParserProvider.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Sdmx20ParserProvider.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        return null;
    }

    public DataMessage parseCompactData(InputStream in, ParseDataCallbackHandler cbHandler) throws IOException {
        DataSetWriter writer = cbHandler != null ? cbHandler.getDataSetWriter() : new StructuredDataWriter();
        CompactDataEventHandler event = new CompactDataEventHandler(writer);
        event.setCbHandler(cbHandler);
        CompactDataContentHandler handler = new CompactDataContentHandler(in, event);
        try {
            return handler.parse();
        } catch (SAXException ex) {
            Logger.getLogger(Sdmx20ParserProvider.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Sdmx20ParserProvider.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        return null;
    }

    public DataMessage parseGenericData(InputStream in, ParseDataCallbackHandler cbHandler) throws IOException {
        DataSetWriter writer = cbHandler != null ? cbHandler.getDataSetWriter() : new StructuredDataWriter();
        GenericDataEventHandler event = new GenericDataEventHandler(writer);
        GenericDataContentHandler handler = new GenericDataContentHandler(event, in);
        try {
            return handler.parse();
        } catch (SAXException ex) {
            Logger.getLogger(Sdmx20ParserProvider.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Sdmx20ParserProvider.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        return null;
    }

    public DataMessage parseCompactData(Reader in) throws IOException {
        CompactDataEventHandler event = new CompactDataEventHandler();
        CompactDataContentHandler handler = new CompactDataContentHandler(in, event);
        try {
            return handler.parse();
        } catch (SAXException ex) {
            Logger.getLogger(Sdmx20ParserProvider.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Sdmx20ParserProvider.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        return null;
    }

    public DataMessage parseGenericData(Reader in) throws IOException {
        GenericDataContentHandler handler = new GenericDataContentHandler(in);
        try {
            return handler.parse();
        } catch (SAXException ex) {
            Logger.getLogger(Sdmx20ParserProvider.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Sdmx20ParserProvider.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        return null;
    }

    public DataMessage parseCompactData(Reader in, ParseDataCallbackHandler cbHandler) throws IOException {
        DataSetWriter writer = cbHandler != null ? cbHandler.getDataSetWriter() : new StructuredDataWriter();
        CompactDataEventHandler event = new CompactDataEventHandler(writer);
        event.setCbHandler(cbHandler);
        CompactDataContentHandler handler = new CompactDataContentHandler(in, event);
        try {
            return handler.parse();
        } catch (SAXException ex) {
            Logger.getLogger(Sdmx20ParserProvider.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Sdmx20ParserProvider.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        return null;
    }

    public DataMessage parseGenericData(Reader in, ParseDataCallbackHandler cbHandler) throws IOException {
        DataSetWriter writer = cbHandler != null ? cbHandler.getDataSetWriter() : new StructuredDataWriter();
        GenericDataEventHandler event = new GenericDataEventHandler(writer);
        GenericDataContentHandler handler = new GenericDataContentHandler(event, in);
        try {
            return handler.parse();
        } catch (SAXException ex) {
            Logger.getLogger(Sdmx20ParserProvider.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Sdmx20ParserProvider.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        return null;
    }

    public DataMessage parseData(ParseParams params, Reader in) throws IOException {
        if (params.getCallbackHandler() == null) {
            if (isCompactData(params.getHeader())) {
                return parseCompactData(in);
            }
            if (isGenericData(params.getHeader())) {
                return parseGenericData(in);
            }
        } else {
            if (isCompactData(params.getHeader())) {
                return parseCompactData(in, params.getCallbackHandler());
            }
            if (isGenericData(params.getHeader())) {
                return parseGenericData(in, params.getCallbackHandler());
            }
        }
        return null;
    }

    public DataMessage parseData(String header, Reader in, ParseDataCallbackHandler cbHandler) throws IOException {
        if (isCompactData(header)) {
            return parseCompactData(in, cbHandler);
        }
        if (isGenericData(header)) {
            return parseGenericData(in, cbHandler);
        }
        return null;
    }

    @Override
    public FlatDataSetReader getDataSetReader(Reader in) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

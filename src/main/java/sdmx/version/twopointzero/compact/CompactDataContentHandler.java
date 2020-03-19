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

/**
 *
 * @author James
 */
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.*;
import org.xml.sax.helpers.XMLReaderFactory;
import sdmx.Registry;
import sdmx.data.DataSetWriter;
import sdmx.data.flat.FlatDataSet;
import sdmx.message.BaseHeaderType;
import sdmx.message.DataMessage;
import sdmx.message.StructureType;
import sdmx.version.twopointzero.Sdmx20ContentHandler;
import sdmx.version.twopointzero.generic.GenericDataContentHandler;

public class CompactDataContentHandler extends Sdmx20ContentHandler implements ContentHandler, ErrorHandler {

    InputStream in = null;
    Reader in2 = null;
    XMLReader reader = null;
    boolean parsed = false;
    // Delegate Events Here!
    CompactDataEventHandler eh = null;
    List<String> groupNames = null;

    public CompactDataContentHandler(InputStream in, CompactDataEventHandler handler) {
        super();
        this.in = in;
        this.eh = handler;
    }

    public CompactDataContentHandler(Reader in, CompactDataEventHandler handler) {
        super();
        this.in2 = in;
        this.eh = handler;
    }

    public CompactDataContentHandler(Registry registry, InputStream in, StructureType struct) {
        this(in, new CompactDataEventHandler());
    }

    public CompactDataContentHandler(Registry registry, Reader in, StructureType struct) {
        this(in, new CompactDataEventHandler());
    }

    public DataMessage parse() throws SAXException, IOException {
        parsed = true;
        XMLReader reader = XMLReaderFactory.createXMLReader();
        try{
        reader.setProperty(XMLConstants.FEATURE_SECURE_PROCESSING, false);
        }catch(Exception ex) {
           //Logger.getLogger(GenericDataContentHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        reader.setContentHandler(this);
        reader.setErrorHandler(this);
        /*
           Nomis API Crashes here because you are running this code with a security manager
           which causes FEATURE_SECURE_PROCESSING to be set in the XML parser,
           when you have a really large query, it is too long for the parser with this setting set
           the above reader.setProperty is an attempt to stop this from happening, but it doesn't work :(
           just run the sdmx-sax jar without a security manager, this typically happens on a web server.s
        
        
        */
        if (in != null) {
            reader.parse(new InputSource(in));
        } else {
            reader.parse(new InputSource(in2));
        }
        DataMessage doc = eh.getDataMessage();
        return doc;
    }

    public void setDocumentLocator(Locator locator) {
    }

    public void startDocument() throws SAXException {
    }

    public void endDocument() throws SAXException {
    }

    public void startPrefixMapping(String prefix, String uri) throws SAXException {
    }

    public void endPrefixMapping(String prefix) throws SAXException {
    }

    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        // SDMX Defined Elements
            /*
         * This is really useful!!!
         */
        /*
         System.out.println("localName=" + localName);
         for (int i = 0; i < atts.getLength(); i++) {
         System.out.println("Att=" + atts.getLocalName(i) + " val=" + atts.getValue(i));
         }
         System.out.println(groupNames);
         */
        if ("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message".equals(uri)) {
            if ("MessageGroup".equals(localName)) {
                eh.startRootElement(atts);
            } else if ("CompactData".equals(localName)) {
                eh.startRootElement(atts);
            } else if ("Header".equals(localName)) {
                eh.startHeader();
            } else if ("ID".equals(localName)) {
                eh.startHeaderID();
            } else if ("Test".equals(localName)) {
                eh.startHeaderTest();
            } else if ("Name".equals(localName)) {
                eh.startName(uri, atts);
            } else if ("Truncated".equals(localName)) {
                eh.startHeaderTruncated();
            } else if ("Prepared".equals(localName)) {
                eh.startHeaderPrepared();
            } else if ("Sender".equals(localName)) {
                eh.startHeaderSender(atts);
            } else if ("DataSetAction".equals(localName)) {
                eh.startDataSetAction(atts);
            } else if ("Extracted".equals(localName)) {
                eh.startExtracted(atts);
            } else if ("ReportingBegin".equals(localName)) {
                eh.startReportingBegin(atts);
            } else if ("ReportingEnd".equals(localName)) {
                eh.startReportingEnd(atts);
            } else if ("Contact".equals(localName)) {
                eh.startContact(atts);
            } else if ("Telephone".equals(localName)) {
                eh.startTelephone(atts);
            } else if ("Department".equals(localName)) {
                eh.startDepartment(atts);
            } else if ("X400".equals(localName)) {
                eh.startX400(atts);
            } else if ("Fax".equals(localName)) {
                eh.startFax(atts);
            } else if ("Receiver".equals(localName)) {
                eh.startReceiver(atts);
            } else if ("Role".equals(localName)) {
                eh.startRole(atts);
            } else if ("URI".equals(localName)) {
                eh.startURI(atts);
            } else if ("Email".equals(localName)) {
                eh.startEmail(atts);
            } // DataSet has no namespace!!!
            else if ("DataSet".equals(localName)) {
                try {
                    eh.startDataSet(uri, qName, atts);
                } catch (URISyntaxException ex) {
                    Logger.getLogger(CompactDataContentHandler.class.getName()).log(Level.SEVERE, null, ex);
                    ex.printStackTrace();
                }
            } else if ("Series".equals(localName)) {
                eh.startSeries(uri, atts);
            } else if ("Obs".equals(localName)) {
                eh.startObs(uri, atts);
            } else if (localName.indexOf("Group") != -1) {
                eh.startGroup(localName, atts);
            }
        } else if ("DataSet".equals(localName)) {
            try {
                eh.startDataSet(uri, qName, atts);
            } catch (URISyntaxException ex) {
                Logger.getLogger(CompactDataContentHandler.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            }
        } else if ("Series".equals(localName)) {
            eh.startSeries(uri, atts);
        } else if ("Obs".equals(localName)) {
            eh.startObs(uri, atts);
        } else if (localName.indexOf("Group") != -1) {
            eh.startGroup(localName, atts);
        }
    }

    public boolean isStringInList(List<String> list, String id) {
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String s = it.next();
            if (s.equals(id)) {
                return true;
            }
        }
        return false;
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        // SDMX Defined Elements
        if ("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message".equals(uri)) {
            if ("MessageGroup".equals(localName)) {
                eh.endRootElement();
            } else if ("CompactData".equals(localName)) {
                eh.endRootElement();
            } else if ("Header".equals(localName)) {
                eh.endHeader();
            } else if ("ID".equals(localName)) {
                eh.endHeaderID();
            } else if ("Test".equals(localName)) {
                eh.endHeaderTest();
            } else if ("Name".equals(localName)) {
                eh.endName();
            } else if ("Truncated".equals(localName)) {
                eh.endHeaderTruncated();
            } else if ("Prepared".equals(localName)) {
                eh.endHeaderPrepared();
            } else if ("Sender".equals(localName)) {
                eh.endHeaderSender();
            } else if ("DataSetAction".equals(localName)) {
                eh.endDataSetAction();
            } else if ("Extracted".equals(localName)) {
                eh.endExtracted();
            } else if ("ReportingBegin".equals(localName)) {
                eh.endReportingBegin();
            } else if ("ReportingEnd".equals(localName)) {
                eh.endReportingEnd();
            } else if ("Contact".equals(localName)) {
                eh.endContact();
            } else if ("Telephone".equals(localName)) {
                eh.endTelephone();
            } else if ("Department".equals(localName)) {
                eh.endDepartment();
            } else if ("X400".equals(localName)) {
                eh.endtX400();
            } else if ("Fax".equals(localName)) {
                eh.endFax();
            } else if ("Receiver".equals(localName)) {
                eh.endReceiver();
            } else if ("Role".equals(localName)) {
                eh.endRole();
            } else if ("URI".equals(localName)) {
                eh.endURI();
            } else if ("Email".equals(localName)) {
                eh.endEmail();
            } else if ("DataSet".equals(localName)) {
                eh.endDataSet();
            } else if ("Series".equals(localName)) {
                eh.endSeries();
            } else if ("Obs".equals(localName)) {
                eh.endObs();
            } else if (localName.indexOf("Group") != -1) {
                eh.endGroup();
            }
        } else if ("DataSet".equals(localName)) {
            eh.endDataSet();
        } else if ("Series".equals(localName)) {
            eh.endSeries();
        } else if ("Obs".equals(localName)) {
            eh.endObs();
        } else if (localName.indexOf("Group") != -1) {
            eh.endGroup();
        }
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        char[] c2 = new char[length];
        System.arraycopy(ch, start, c2, 0, length);
        eh.characters(c2);

    }

    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
    }

    public void processingInstruction(String target, String data) throws SAXException {
    }

    public void skippedEntity(String name) throws SAXException {
    }

    public void warning(SAXParseException exception) throws SAXException {
        System.out.println("Warning:" + exception);
    }

    public void error(SAXParseException exception) throws SAXException {
        System.out.println("Error:" + exception);
    }

    public void fatalError(SAXParseException exception) throws SAXException {
        System.out.println("Fatal:" + exception);
    }

}

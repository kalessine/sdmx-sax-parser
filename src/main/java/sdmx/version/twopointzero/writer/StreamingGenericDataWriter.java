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
package sdmx.version.twopointzero.writer;

/**
 *
 * @author James
 */
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.output.XMLOutputter;
import sdmx.Registry;
import sdmx.common.Name;
import sdmx.common.PayloadStructureType;
import sdmx.commonreferences.DataStructureReference;
import sdmx.data.ColumnMapper;
import sdmx.data.DataSet;
import sdmx.data.DataSetWriter;
import sdmx.data.Group;
import sdmx.structureddata.ValueTypeResolver;
import sdmx.data.structured.Obs;
import sdmx.data.structured.Series;
import sdmx.data.structured.StructuredColumnMapper;
import sdmx.data.structured.StructuredDataSet;
import sdmx.footer.FooterType;
import sdmx.message.*;
import sdmx.structure.base.NameableType;
import sdmx.structure.dataflow.DataflowType;
import sdmx.structure.datastructure.AttributeType;
import sdmx.structure.datastructure.DataStructureType;
import sdmx.structure.datastructure.DimensionType;
import sdmx.structure.datastructure.PrimaryMeasure;
import sdmx.structure.datastructure.TimeDimensionType;
import sdmx.version.common.ParseDataCallbackHandler;
import static sdmx.version.twopointzero.writer.GenericDataWriter.writeContact;
import static sdmx.version.twopointzero.writer.GenericDataWriter.writeName;

public class StreamingGenericDataWriter implements DataSetWriter, ParseDataCallbackHandler {

    public static StreamingGenericDataWriter openWriter(OutputStream out, Registry reg) throws XMLStreamException {
        //setup this like outputDocument
        return new StreamingGenericDataWriter(out, reg);
    }
    OutputStream out = null;
    XMLStreamWriter writer = null;
    private String namespace;
    private String namespaceprefix;
    BaseHeaderType header = null;

    private Registry registry = null;
    private DataStructureReference dataStructureReference = null;
    private DataStructureType struct = null;

    private boolean in_series_key = false;
    private boolean in_series_attributes = false;

    private boolean in_obs_attributes = false;

    public StreamingGenericDataWriter(OutputStream out, Registry reg) {
        try {
            this.registry = reg;
            this.out = out;
            XMLOutputFactory factory = XMLOutputFactory.newInstance();
            this.writer = factory.createXMLStreamWriter(out);
        } catch (XMLStreamException ex) {
            Logger.getLogger(StreamingGenericDataWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     */
    public void writeHeader() throws XMLStreamException {
        writer.writeStartDocument("1.0");
        writer.writeStartElement("GenericData");
        writer.writeDefaultNamespace("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message");
        writer.writeNamespace("message", "http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message");
        writer.writeNamespace("common", "http://www.SDMX.org/resources/SDMXML/schemas/v2_0/common");
        writer.writeNamespace("generic", "http://www.SDMX.org/resources/SDMXML/schemas/v2_0/generic");
        writer.writeNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
        writer.writeNamespace("schemaLocation", "http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message http://www.sdmx.org/docs/2_0/SDMXMessage.xsd");
        if (namespaceprefix != null && !namespaceprefix.startsWith("xmlns")) {
            //writer.writeNamespace(namespaceprefix, namespace);
        }
        writer.writeStartElement("Header");
        if (header.getId() != null && !"".equals(header.getId())) {
            writer.writeStartElement("ID");
            writer.writeCharacters(header.getId());
            writer.writeEndElement();
        }
        if (header.getTest() != null) {
            writer.writeStartElement("Test");
            writer.writeCharacters(header.getTest().toString());
            writer.writeEndElement();
        }
        if (header.getNames() != null && header.getNames().size() > 0) {
            for (int i = 0; i < header.getNames().size(); i++) {
                writer.writeStartElement("Name");
                if (header.getNames().get(i).getLang() != null) {
                    writer.writeAttribute("xml:lang", header.getNames().get(i).getLang());
                }
                writer.writeCharacters(header.getNames().get(i).getText());
                writer.writeEndElement();
            }
        }
        if (header.getPrepared() != null && !"".equals(header.getPrepared())) {
            writer.writeStartElement("Prepared");
            writer.writeCharacters(header.getPrepared().getDate().toString());
            writer.writeEndElement();
        }
        // Deprecated in SDMX 2.1
        //if (header.getTruncated() != null && !"".equals(header.getTruncated())) {
        //    Element trunc = new Element("Truncated");
        //    trunc.addContent(doc.getTruncated().toString());
        //    header.addContent(trunc);
        //}

        if (header.getSender() != null && !"".equals(header.getSender())) {
            writer.writeStartElement("Sender");
            writer.writeAttribute("id", header.getSender().getId().toString());
            if (header.getSender().getNames() != null) {
                for (Name n : header.getSender().getNames()) {
                    writeName(writer, n);
                }
            }
            if (header.getSender().getContacts() != null && header.getSender().getContacts().size() > 0) {
                writeContact(writer, header.getSender().getContacts().get(0));
            }
            writer.writeEndElement();
        }
        if (header.getReceivers() != null && header.getReceivers().size() > 0) {
            writer.writeStartElement("Receiver");
            writer.writeAttribute("id", header.getReceivers().get(0).getId().toString());
            if (header.getReceivers().get(0).getNames() != null) {
                for (Name n : header.getReceivers().get(0).getNames()) {
                    writeName(writer, n);
                }
            }
            if (header.getReceivers().get(0).getContacts() != null && header.getReceivers().get(0).getContacts().size() > 0) {
                writeContact(writer, header.getReceivers().get(0).getContacts().get(0));
            }

            writer.writeEndElement();
        }
        if (header.getDataSetAction() != null && !"".equals(header.getDataSetAction())) {
            writer.writeStartElement("DataSetAction");
            writer.writeCharacters(header.getDataSetAction().getString());
            writer.writeEndElement();
        }
        if (header.getExtracted() != null) {
            writer.writeStartElement("Extracted");
            writer.writeCharacters(header.getExtracted().toString());
            writer.writeEndElement();
        }
        if (header.getReportingBegin() != null) {
            writer.writeStartElement("ReportingBegin");
            writer.writeCharacters(header.getReportingBegin().toString());
            writer.writeEndElement();
        }
        if (header.getReportingEnd() != null) {
            writer.writeStartElement("ReportingEnd");
            writer.writeCharacters(header.getReportingEnd().toString());
            writer.writeEndElement();
        }
        writer.writeEndElement();
        if (header.getStructures() != null && header.getStructures().size() > 0) {
            for (Iterator<sdmx.common.PayloadStructureType> it = header.getStructures().iterator(); it.hasNext();) {
                PayloadStructureType st = (PayloadStructureType) it.next();
                this.dataStructureReference=(DataStructureReference)st.getStructure();
                this.struct=this.registry.find(dataStructureReference);
            }
        } 
        // Ignore.. no equivalient in SDMX 2.1
        //if (header.get) {
        //    dataSet.setAttribute("KeyFamilyURI", ds.getKeyFamilyURI());
        //}
        //if (header.getEmbargoDate() != null) {
        //    dataSet.setAttribute("PublicationPeriod", ds.getPublicationPeriod());
        //}
        //if (ds.getPublicationYear() != null) {
        //    dataSet.setAttribute("PublicationYear", ds.getPublicationYear());
        //}
    }

    private static boolean isStringInList(List<String> list, String id) {
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String s = it.next();
            if (s.equals(id)) {
                return true;
            }
        }
        return false;
    }

    private static void writeText(XMLStreamWriter writer, String element, String lang, String text) throws XMLStreamException {
        writer.writeStartElement(element);
        if (lang != null) {
            writer.writeAttribute("xml:lang", lang);
        }
        writer.writeCharacters(text);
        writer.writeEndElement();
    }

    public static void writeName(XMLStreamWriter writer, Name name) throws XMLStreamException {
        writeText(writer, "Name", name.getLang(), name.getText());
    }

    public static void writeContact(XMLStreamWriter writer, ContactType contact) throws XMLStreamException {
        writer.writeStartElement("Contact");
        if (contact.getNames() != null && contact.getNames().size() > 0) {
            writeText(writer, "Name", contact.getNames().get(0).getLang(), contact.getNames().get(0).getText());
        }
        if (contact.getDepartments() != null && contact.getDepartments().size() > 0) {
            writeText(writer, "Department", contact.getDepartments().get(0).getLang(), contact.getDepartments().get(0).getText());
        }
        if (contact.getTelephones() != null && contact.getTelephones().size() > 0) {
            writeText(writer, "Telephone", null, contact.getTelephones().get(0));
        }
        writer.writeEndElement();
    }

    public static void writeGroup(XMLStreamWriter writer, Group gr, String namespaceprefix, String namespace) throws XMLStreamException {
        if (namespaceprefix != null) {
            writer.writeStartElement(namespaceprefix, gr.getGroupName(), namespace);
        } else {
            writer.writeStartElement(namespace, gr.getGroupName());
        }
        Iterator<String> it = gr.getGroupKey().keySet().iterator();
        while (it.hasNext()) {
            String s = it.next();
            writer.writeAttribute(s, NameableType.toIDString(gr.getGroupKey().get(s)));
        }
        Iterator<String> it2 = gr.getGroupAttributes().keySet().iterator();
        while (it2.hasNext()) {
            String s = it2.next();
            writer.writeAttribute(s, NameableType.toIDString(gr.getGroupAttributes().get(s)));
        }
        writer.writeEndElement();
    }

    @Override
    public void newDataSet() {
        //System.out.println("New DataSet");
        try {                                  //http://www.SDMX.org/resources/SDMXML/schemas/v2_0/generic
            writer.writeStartElement("generic", "DataSet", "http://www.SDMX.org/resources/SDMXML/schemas/v2_0/generic");
        } catch (XMLStreamException ex) {
            Logger.getLogger(StreamingGenericDataWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (header.getDataProvider() != null && header.getDataProvider() != null && header.getDataProvider().getAgencyId() != null) {
            try {
                writer.writeAttribute("DataFlowAgencyID", header.getDataProvider().getAgencyId().toString());
            } catch (XMLStreamException ex) {
                Logger.getLogger(StreamingGenericDataWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (header.getDataSetID() != null && header.getDataSetID().size() > 0) {
            try {
                writer.writeAttribute("DataFlowID", header.getDataSetID().get(0).toString());
            } catch (XMLStreamException ex) {
                Logger.getLogger(StreamingGenericDataWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (header.getDataProvider() != null && header.getDataProvider() != null && header.getDataProvider().getAgencyId() != null) {
            try {
                writer.writeAttribute("DataProviderID", header.getDataProvider().getAgencyId().toString());
            } catch (XMLStreamException ex) {
                Logger.getLogger(StreamingGenericDataWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (header.getDataProvider() != null && header.getDataProvider() != null && header.getDataProvider().getAgencyId() != null) {
            try {
                writer.writeAttribute("DataProviderSchemeAgencyId", header.getDataProvider().getAgencyId().toString());
            } catch (XMLStreamException ex) {
                Logger.getLogger(StreamingGenericDataWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (header.getDataProvider() != null && header.getDataProvider() != null && header.getDataProvider().getAgencyId() != null) {
            try {
                writer.writeAttribute("DataProviderSchemeId", header.getDataProvider().getId().toString());
            } catch (XMLStreamException ex) {
                Logger.getLogger(StreamingGenericDataWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (header.getDataSetID() != null && header.getDataSetID().size() > 0) {
            try {
                writer.writeAttribute("DataSetID", header.getDataSetID().get(0).toString());
            } catch (XMLStreamException ex) {
                Logger.getLogger(StreamingGenericDataWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void newSeries() {
        try {
            //System.out.println("New Series");
            writer.writeStartElement("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/generic", "Series");
            writer.writeStartElement("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/generic", "SeriesKey");
        } catch (XMLStreamException ex) {
            Logger.getLogger(StreamingGenericDataWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        in_series_key = true;
    }

    @Override
    public void newObservation() {
        // System.out.println("New Obs");
        if (in_series_key) {
            try {
                //System.out.println("Ending Series Key");
                in_series_key = false;
                writer.writeEndElement();// SeriesKey
            } catch (XMLStreamException ex) {
                Logger.getLogger(StreamingGenericDataWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (in_series_attributes) {
            try {
                //System.out.println("Ending Series Atts");
                in_series_attributes = false;
                writer.writeEndElement(); // Attributes
            } catch (XMLStreamException ex) {
                Logger.getLogger(StreamingGenericDataWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            writer.writeStartElement("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/generic", "Obs");
        } catch (XMLStreamException ex) {
            Logger.getLogger(StreamingGenericDataWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        in_obs_attributes = false;
    }

    @Override
    public void writeDataSetComponent(String name, String val) {
        try {
            writer.writeAttribute(name, val);
        } catch (XMLStreamException ex) {
            Logger.getLogger(StreamingGenericDataWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void writeSeriesComponent(String name, String val) {
        if (in_series_key) {
            if (struct.findComponent(name) instanceof AttributeType) {
                try {
                    in_series_key = false;
                    in_series_attributes = true;
                    writer.writeEndElement();// SeriesKey
                    writer.writeStartElement("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/generic", "Attributes");
                } catch (XMLStreamException ex) {
                    Logger.getLogger(StreamingGenericDataWriter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (in_series_attributes && struct.findComponent(name) instanceof DimensionType) {
            throw new RuntimeException("Series Attributes must be written last!:" + name + ":" + val);
        }
        try {
            writer.writeStartElement("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/generic", "Value");
            writer.writeAttribute("concept", name);
            writer.writeAttribute("value", val);
            writer.writeEndElement();
        } catch (XMLStreamException ex) {
            Logger.getLogger(StreamingGenericDataWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void writeObservationComponent(String name, String val) {
        //System.out.println("N:" + name + "val:" + val);
        if (struct.findComponent(name) instanceof TimeDimensionType) {
            try {
                writer.writeStartElement("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/generic", "Time");
                writer.writeCharacters(val);
                writer.writeEndElement();
            } catch (XMLStreamException ex) {
                Logger.getLogger(StreamingGenericDataWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (struct.findComponent(name) instanceof PrimaryMeasure) {
            try {
                writer.writeStartElement("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/generic", "ObsValue");
                writer.writeAttribute("value", val);
                writer.writeEndElement();
            } catch (XMLStreamException ex) {
                Logger.getLogger(StreamingGenericDataWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            if (!in_obs_attributes) {
                in_obs_attributes = true;
                try {
                    writer.writeStartElement("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/generic", "Attributes");
                } catch (XMLStreamException ex) {
                    Logger.getLogger(StreamingGenericDataWriter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                writer.writeStartElement("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/generic", "Value");
                writer.writeAttribute("concept", name);
                writer.writeAttribute("value", val);
                writer.writeEndElement();
            } catch (XMLStreamException ex) {
                Logger.getLogger(StreamingGenericDataWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public void writeGroupValues(String name, HashMap<String, Object> group) {
//        writeGroup(writer, gr, namespaceprefix, namespace);
    }

    @Override
    public void finishObservation() {
        if (in_obs_attributes) {
            try {
                writer.writeEndElement();
            } catch (XMLStreamException ex) {
                Logger.getLogger(StreamingGenericDataWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //System.out.println("Fin Obs");
        try {
            writer.writeEndElement();

        } catch (XMLStreamException ex) {
            Logger.getLogger(StreamingGenericDataWriter.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void finishSeries() {
        //System.out.println("Fin Series");
        try {
            if (in_series_key || in_series_attributes) {
                writer.writeEndElement(); // SeriesKey or Attributes
            }
            writer.writeEndElement(); // Series
        } catch (XMLStreamException ex) {
            Logger.getLogger(StreamingGenericDataWriter.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public DataSet finishDataSet() {
        //System.out.println("Fin DataSet");
        try {
            writer.writeEndElement();

        } catch (XMLStreamException ex) {
            Logger.getLogger(StreamingGenericDataWriter.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void headerParsed(BaseHeaderType header) {
        this.header = header;
        try {
            writeHeader();
        } catch (XMLStreamException ex) {
            Logger.getLogger(StreamingGenericDataWriter.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void footerParsed(FooterType footer) {

    }

    @Override
    public DataSetWriter getDataSetWriter() {
        return this;
    }

    @Override
    public void setNamespace(String prefix, String namespace) {
        this.namespace = prefix;
        this.namespaceprefix = namespace;
        try {
            writer.writeNamespace(prefix, namespace);

        } catch (XMLStreamException ex) {
            Logger.getLogger(StreamingGenericDataWriter.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void documentFinished() {
        try {
            writer.writeEndDocument();
            writer.flush();
            writer.close();
            out.flush();
            out.close();

        } catch (XMLStreamException ex) {
            Logger.getLogger(StreamingGenericDataWriter.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StreamingGenericDataWriter.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    private String dimensionAtObservation = null;

    @Override
    public void setDimensionAtObservationHint(String s) {
        dimensionAtObservation = s;
    }

    @Override
    public String getDimensionAtObservationHint() {
        return dimensionAtObservation;
    }

    @Override
    public Registry getRegistry() {
        return registry;
    }

    @Override
    public void setRegistry(Registry reg) {
        this.registry = reg;
    }
}

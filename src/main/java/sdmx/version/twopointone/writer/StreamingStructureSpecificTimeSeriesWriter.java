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
package sdmx.version.twopointone.writer;

/**
 *
 * @author James
 */
import sdmx.version.twopointzero.writer.*;
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
import sdmx.version.common.ParseDataCallbackHandler;
import static sdmx.version.twopointzero.writer.GenericDataWriter.writeName;

public class StreamingStructureSpecificTimeSeriesWriter implements DataSetWriter, ParseDataCallbackHandler {

    public static StreamingStructureSpecificTimeSeriesWriter openWriter(OutputStream out) throws XMLStreamException {
        //setup this like outputDocument
        return new StreamingStructureSpecificTimeSeriesWriter(out);
    }
    OutputStream out = null;
    XMLStreamWriter writer = null;
    private String namespace;
    private String namespaceprefix;
    BaseHeaderType header = null;
    Registry registry = null;
    private DataflowType flow = null;
    DataStructureReference ref = null;

    public StreamingStructureSpecificTimeSeriesWriter(OutputStream out) {
        this.out = out;
        XMLOutputFactory factory = XMLOutputFactory.newInstance();
        try {
            writer = factory.createXMLStreamWriter(out);
            writer.writeStartDocument("1.0");
            writer.writeStartElement("message", "StructureSpecificTimeSeriesData", "http://www.sdmx.org/resources/sdmxml/schemas/v2_1/message");
            writer.writeDefaultNamespace("http://www.sdmx.org/resources/sdmxml/schemas/v2_1/message");
            writer.writeNamespace("data", "http://www.sdmx.org/resources/sdmxml/schemas/v2_1/data/structurespecific");
            writer.writeNamespace("message", "http://www.sdmx.org/resources/sdmxml/schemas/v2_1/message");
            writer.writeNamespace("common", "http://www.sdmx.org/resources/sdmxml/schemas/v2_1/common");
            writer.writeNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
            writer.writeNamespace("schemaLocation", "http://www.sdmx.org/resources/sdmxml/schemas/v2_1/message");
        } catch (XMLStreamException ex) {
            Logger.getLogger(StreamingStructureSpecificTimeSeriesWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     */
    public void writeHeader() throws XMLStreamException {
        writer.writeStartElement("message", "Header", "http://www.sdmx.org/resources/sdmxml/schemas/v2_1/message");
        if (header.getId() != null && !"".equals(header.getId())) {
            writer.writeStartElement("message", "ID", "http://www.sdmx.org/resources/sdmxml/schemas/v2_1/message");
            writer.writeCharacters(header.getId());
            writer.writeEndElement();
        }
        if (header.getTest() != null) {
            writer.writeStartElement("message", "Test", "http://www.sdmx.org/resources/sdmxml/schemas/v2_1/message");
            writer.writeCharacters(header.getTest().toString());
            writer.writeEndElement();
        }
        if (header.getNames() != null && header.getNames().size() > 0) {
            for (int i = 0; i < header.getNames().size(); i++) {

                writer.writeStartElement("common", "Name", "http://www.sdmx.org/resources/sdmxml/schemas/v2_1/common");
                if (header.getNames().get(i).getLang() != null) {
                    writer.writeAttribute("xml:lang", header.getNames().get(i).getLang());
                }
                writer.writeCharacters(header.getNames().get(i).getText());
                writer.writeEndElement();
            }
        }
        if (header.getPrepared() != null && !"".equals(header.getPrepared())) {
            writer.writeStartElement("message", "Prepared", "http://www.sdmx.org/resources/sdmxml/schemas/v2_1/message");
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
            writer.writeStartElement("message", "Sender", "http://www.sdmx.org/resources/sdmxml/schemas/v2_1/message");
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
            for (PartyType r : header.getReceivers()) {
                writer.writeStartElement("message", "Receiver", "http://www.sdmx.org/resources/sdmxml/schemas/v2_1/message");
                writer.writeAttribute("id", r.getId().toString());
                if (r.getNames() != null) {
                    for (Name n : r.getNames()) {
                        writeName(writer, n);
                    }
                }
                if (r.getContacts() != null && r.getContacts().size() > 0) {
                    for (ContactType c : r.getContacts()) {
                        writeContact(writer, c);
                    }

                }
                writer.writeEndElement();
            }
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

        if (header.getStructures() != null && header.getStructures().size() > 0) {
            //System.out.println("Writing PayLoadStructure");
            for (Iterator<sdmx.common.PayloadStructureType> it = header.getStructures().iterator(); it.hasNext();) {
                PayloadStructureType st = (PayloadStructureType) it.next();
                writeStructure(st);
            }
        } else {
            PayloadStructureType payload = new PayloadStructureType();
            payload.setStructure(ref.asDataStructureReference());
            writeStructure(payload);
        }
        writer.writeEndElement();
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
        try {
            if (namespaceprefix == null || namespace == null) {

                writer.writeStartElement("message", "DataSet", "http://www.sdmx.org/resources/sdmxml/schemas/v2_1/message");

            } else {
                writer.writeStartElement(namespaceprefix, "DataSet", namespace);
            }

        } catch (XMLStreamException ex) {
            Logger.getLogger(StreamingStructureSpecificTimeSeriesWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (header.getDataProvider() != null && header.getDataProvider() != null && header.getDataProvider().getAgencyId() != null) {
            try {
                writer.writeAttribute("DataFlowAgencyID", header.getDataProvider().getAgencyId().toString());
            } catch (XMLStreamException ex) {
                Logger.getLogger(StreamingStructureSpecificTimeSeriesWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (header.getDataSetID() != null && header.getDataSetID().size() > 0) {
            try {
                writer.writeAttribute("DataFlowID", header.getDataSetID().get(0).toString());
            } catch (XMLStreamException ex) {
                Logger.getLogger(StreamingStructureSpecificTimeSeriesWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (header.getDataProvider() != null && header.getDataProvider() != null && header.getDataProvider().getAgencyId() != null) {
            try {
                writer.writeAttribute("DataProviderID", header.getDataProvider().getAgencyId().toString());
            } catch (XMLStreamException ex) {
                Logger.getLogger(StreamingStructureSpecificTimeSeriesWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (header.getDataProvider() != null && header.getDataProvider() != null && header.getDataProvider().getAgencyId() != null) {
            try {
                writer.writeAttribute("DataProviderSchemeAgencyId", header.getDataProvider().getAgencyId().toString());
            } catch (XMLStreamException ex) {
                Logger.getLogger(StreamingStructureSpecificTimeSeriesWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (header.getDataProvider() != null && header.getDataProvider() != null && header.getDataProvider().getAgencyId() != null) {
            try {
                writer.writeAttribute("DataProviderSchemeId", header.getDataProvider().getId().toString());
            } catch (XMLStreamException ex) {
                Logger.getLogger(StreamingStructureSpecificTimeSeriesWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (header.getDataSetID() != null && header.getDataSetID().size() > 0) {
            try {
                writer.writeAttribute("DataSetID", header.getDataSetID().get(0).toString());
            } catch (XMLStreamException ex) {
                Logger.getLogger(StreamingStructureSpecificTimeSeriesWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void newSeries() {
        //System.out.println("New Series");
        if (namespaceprefix == null || namespace == null) {
            try {
                writer.writeStartElement("Series");
            } catch (XMLStreamException ex) {
                Logger.getLogger(StreamingStructureSpecificTimeSeriesWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                writer.writeStartElement(namespaceprefix, "Series", namespace);
            } catch (XMLStreamException ex) {
                Logger.getLogger(StreamingStructureSpecificTimeSeriesWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void newObservation() {
        //System.out.println("New Obs");
        if (namespaceprefix == null || namespace == null) {
            try {
                writer.writeStartElement("Obs");
            } catch (XMLStreamException ex) {
                Logger.getLogger(StreamingStructureSpecificTimeSeriesWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                writer.writeStartElement(namespaceprefix, "Obs", namespace);
            } catch (XMLStreamException ex) {
                Logger.getLogger(StreamingStructureSpecificTimeSeriesWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void writeDataSetComponent(String name, String val) {
        try {
            writer.writeAttribute(name, val);
        } catch (XMLStreamException ex) {
            Logger.getLogger(StreamingStructureSpecificTimeSeriesWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void writeSeriesComponent(String name, String val) {
        try {
            writer.writeAttribute(name, val);
        } catch (XMLStreamException ex) {
            Logger.getLogger(StreamingStructureSpecificTimeSeriesWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void writeObservationComponent(String name, String val) {
        //System.out.println("N:" + name + "val:" + val);
        try {
            writer.writeAttribute(name, val);
        } catch (XMLStreamException ex) {
            Logger.getLogger(StreamingStructureSpecificTimeSeriesWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void writeGroupValues(String name, HashMap<String, Object> group) {
//        writeGroup(writer, gr, namespaceprefix, namespace);
    }

    @Override
    public void finishObservation() {
        //System.out.println("Fin Obs");
        try {
            writer.writeEndElement();
        } catch (XMLStreamException ex) {
            Logger.getLogger(StreamingStructureSpecificTimeSeriesWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void finishSeries() {
        //System.out.println("Fin Series");
        try {
            writer.writeEndElement();
        } catch (XMLStreamException ex) {
            Logger.getLogger(StreamingStructureSpecificTimeSeriesWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public DataSet finishDataSet() {
       // System.out.println("Fin DataSet");
        try {
            writer.writeEndElement();
        } catch (XMLStreamException ex) {
            Logger.getLogger(StreamingStructureSpecificTimeSeriesWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void headerParsed(BaseHeaderType header) {
        //System.out.println("Header Parsed");
        this.header = header;
        try {
            writeHeader();
        } catch (XMLStreamException ex) {
            Logger.getLogger(StreamingStructureSpecificTimeSeriesWriter.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(StreamingStructureSpecificTimeSeriesWriter.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(StreamingStructureSpecificTimeSeriesWriter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StreamingStructureSpecificTimeSeriesWriter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    /*
     -<message:Structurenamespace="urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=ECB:ECB_EXR_NG(1.0):ObsLevelDim:TIME_PERIOD" dimensionAtObservation="TIME_PERIOD" structureID="STR1">


     -<common:Structure>

     <Ref id="ECB_EXR_NG" version="1.0" agencyID="ECB"/>

     </common:Structure>

     </message:Structure>
     */

    public void writeStructure(PayloadStructureType st) {
        try {
            writer.writeStartElement("message", "Structure", "http://www.sdmx.org/resources/sdmxml/schemas/v2_1/message");
            if (st.getNamespace() != null) {
                writer.writeAttribute("namespace", st.getNamespace().toString());
            }
            if (st.getDimensionAtObservation() != null) {
                writer.writeAttribute("dimensionAtObservation", st.getDimensionAtObservation().toString());
            } else if (this.dimensionAtObservation != null) {
                writer.writeAttribute("dimensionAtObservation", dimensionAtObservation);
            }
            if (st.getStructureID() != null) {
                writer.writeAttribute("structureID", st.getStructureID().toString());
            }
            writer.writeStartElement("common", "Structure", "http://www.sdmx.org/resources/sdmxml/schemas/v2_1/common");
            writer.writeStartElement("Ref");
            writer.writeAttribute("id", st.getStructure().getMaintainableParentId().toString());
            writer.writeAttribute("version", st.getStructure().getVersion().toString());
            writer.writeAttribute("agencyID", st.getStructure().getAgencyId().toString());
            writer.writeEndElement();
            writer.writeEndElement();
            writer.writeEndElement();
        } catch (XMLStreamException ex) {
            Logger.getLogger(StreamingStructureSpecificTimeSeriesWriter.class.getName()).log(Level.SEVERE, null, ex);
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

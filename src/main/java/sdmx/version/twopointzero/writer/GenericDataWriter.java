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

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.output.XMLOutputter;
import sdmx.Registry;
import sdmx.common.Name;
import sdmx.commonreferences.DataStructureReference;
import sdmx.data.DataSet;
import sdmx.structureddata.ValueTypeResolver;
import sdmx.data.structured.Obs;
import sdmx.data.structured.Series;
import sdmx.data.structured.StructuredColumnMapper;
import sdmx.data.structured.StructuredDataSet;
import sdmx.message.*;
import sdmx.structure.datastructure.AttributeType;
import sdmx.structure.datastructure.DataStructureType;
import sdmx.structure.datastructure.DimensionType;

public class GenericDataWriter {

    public static void write(DataMessage message, OutputStream out, DataStructureReference ref, Registry reg) throws XMLStreamException {
        //setup this like outputDocument
        XMLOutputFactory factory = XMLOutputFactory.newInstance();
        XMLStreamWriter writer = factory.createXMLStreamWriter(out);
        //output to a file
        writeDataMessage(message, writer, ref, reg);
        writer.close();
    }

    /**
     *
     */
    public static void writeDataMessage(DataMessage msg, XMLStreamWriter writer, DataStructureReference ref, Registry reg) throws XMLStreamException {
        // Create the root element
        String namespace = msg.getNamespace();
        String namespaceprefix = msg.getNamespacePrefix();
        writer.writeStartDocument();
        writer.writeStartElement("GenericData");
        writer.writeDefaultNamespace("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message");
        writer.writeNamespace("message", "http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message");
        writer.writeNamespace("common", "http://www.SDMX.org/resources/SDMXML/schemas/v2_0/common");
        writer.writeNamespace("generic", "http://www.SDMX.org/resources/SDMXML/schemas/v2_0/generic");
        writer.writeNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
        writer.writeNamespace("schemaLocation", "http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message http://www.sdmx.org/docs/2_0/SDMXMessage.xsd");
        //writer.writeNamespace(namespaceprefix, namespace);
        writer.writeStartElement("Header");
        if (msg.getHeader().getId() != null && !"".equals(msg.getHeader().getId())) {
            writer.writeStartElement("ID");
            writer.writeCharacters(msg.getHeader().getId());
            writer.writeEndElement();
        }
        if (msg.getHeader().getTest() != null) {
            writer.writeStartElement("Test");
            writer.writeCharacters(msg.getHeader().getTest().toString());
            writer.writeEndElement();
        }
        if (msg.getHeader().getNames() != null && msg.getHeader().getNames().size() > 0) {
            for (int i = 0; i < msg.getHeader().getNames().size(); i++) {
                writer.writeStartElement("Name");
                writer.writeAttribute("xml:lang", msg.getHeader().getNames().get(i).getLang());
                writer.writeCharacters(msg.getHeader().getNames().get(i).getText());
                writer.writeEndElement();
            }
        }
        if (msg.getHeader().getPrepared() != null && !"".equals(msg.getHeader().getPrepared())) {
            writer.writeStartElement("Prepared");
            writer.writeCharacters(msg.getHeader().getPrepared().getDate().toString());
            writer.writeEndElement();
        }
        // Deprecated in SDMX 2.1
        //if (msg.getHeader().getTruncated() != null && !"".equals(msg.getHeader().getTruncated())) {
        //    Element trunc = new Element("Truncated");
        //    trunc.addContent(doc.getTruncated().toString());
        //    header.addContent(trunc);
        //}

        if (msg.getHeader().getSender() != null && !"".equals(msg.getHeader().getSender())) {
            writer.writeStartElement("Sender");
            writer.writeAttribute("id", msg.getHeader().getSender().getId().toString());
            if( msg.getHeader().getSender().getNames()!=null ) {
                for(Name n:msg.getHeader().getSender().getNames()){
                    writeName(writer,n);
                }
            }
            if (msg.getHeader().getSender().getContacts() != null && msg.getHeader().getSender().getContacts().size() > 0) {
                writeContact(writer, msg.getHeader().getSender().getContacts().get(0));
            }
            writer.writeEndElement();
        }
        if (msg.getHeader().getReceivers() != null && msg.getHeader().getReceivers().size() > 0) {
            writer.writeStartElement("Receiver");
            writer.writeAttribute("id", msg.getHeader().getReceivers().get(0).getId().toString());
            if( msg.getHeader().getReceivers().get(0).getNames()!=null ) {
                for(Name n:msg.getHeader().getReceivers().get(0).getNames()){
                    writeName(writer,n);
                }
            }
            if (msg.getHeader().getReceivers().get(0).getContacts() != null && msg.getHeader().getReceivers().get(0).getContacts().size() > 0) {
                writeContact(writer, msg.getHeader().getReceivers().get(0).getContacts().get(0));
            }

            writer.writeEndElement();
        }
        if (msg.getHeader().getDataSetAction() != null && !"".equals(msg.getHeader().getDataSetAction())) {
            writer.writeStartElement("DataSetAction");
            writer.writeCharacters(msg.getHeader().getDataSetAction().getString());
            writer.writeEndElement();
        }
        if (msg.getHeader().getExtracted() != null) {
            writer.writeStartElement("Extracted");
            writer.writeCharacters(msg.getHeader().getExtracted().toString());
            writer.writeEndElement();
        }
        if (msg.getHeader().getReportingBegin() != null) {
            writer.writeStartElement("ReportingBegin");
            writer.writeCharacters(msg.getHeader().getReportingBegin().toString());
            writer.writeEndElement();
        }
        if (msg.getHeader().getReportingEnd() != null) {
            writer.writeStartElement("ReportingEnd");
            writer.writeCharacters(msg.getHeader().getReportingEnd().toString());
            writer.writeEndElement();
        }
        writer.writeEndElement(); // Header
        writer.writeStartElement("generic", "DataSet", "http://www.SDMX.org/resources/SDMXML/schemas/v2_0/generic");
        DataSet ds = msg.getDataSets().get(0);

        if (msg.getHeader().getDataProvider() != null && msg.getHeader().getDataProvider() != null && msg.getHeader().getDataProvider().getAgencyId() != null) {
            writer.writeAttribute("DataFlowAgencyID", msg.getHeader().getDataProvider().getAgencyId().toString());
        }
        if (msg.getHeader().getDataSetID() != null && msg.getHeader().getDataSetID().size() > 0) {
            writer.writeAttribute("DataFlowID", msg.getHeader().getDataSetID().get(0).toString());
        }
        if (msg.getHeader().getDataProvider() != null && msg.getHeader().getDataProvider() != null && msg.getHeader().getDataProvider().getAgencyId() != null) {
            writer.writeAttribute("DataProviderID", msg.getHeader().getDataProvider().getAgencyId().toString());
        }
        if (msg.getHeader().getDataProvider() != null && msg.getHeader().getDataProvider() != null && msg.getHeader().getDataProvider().getAgencyId() != null) {
            writer.writeAttribute("DataProviderSchemeAgencyId", msg.getHeader().getDataProvider().getAgencyId().toString());
        }
        if (msg.getHeader().getDataProvider() != null && msg.getHeader().getDataProvider().getRef() != null && msg.getHeader().getDataProvider().getAgencyId() != null) {
            writer.writeAttribute("DataProviderSchemeId", msg.getHeader().getDataProvider().getId().toString());
        }
        if (msg.getHeader().getDataSetID() != null && msg.getHeader().getDataSetID().size() > 0) {
            writer.writeAttribute("DataSetID", msg.getHeader().getDataSetID().get(0).toString());
        }
        // Ignore.. no equivalient in SDMX 2.1
        //if (msg.getHeader().get) {
        //    dataSet.setAttribute("KeyFamilyURI", ds.getKeyFamilyURI());
        //}
        //if (msg.getHeader().getEmbargoDate() != null) {
        //    dataSet.setAttribute("PublicationPeriod", ds.getPublicationPeriod());
        //}
        //if (ds.getPublicationYear() != null) {
        //    dataSet.setAttribute("PublicationYear", ds.getPublicationYear());
        //}

        //for (int i = 0; i < ds.getGroupSize(); i++) {
        //    Group g = ds.getGroup(i);
        //    Element ge = new Element("Group");
// Ignore Groups for now
        //}
        DataStructureType struct = reg.find(ref);
        if (ds instanceof StructuredDataSet) {
            StructuredDataSet sds = (StructuredDataSet) ds;
            StructuredColumnMapper mapper = (StructuredColumnMapper) sds.getColumnMapper();
            for (int i = 0; i < sds.getSeriesList().size(); i++) {
                Series s = sds.getSeriesList().get(i);
                writer.writeStartElement("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/generic", "Series");
                writer.writeStartElement("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/generic", "SeriesKey");
                for (int j = 0; j < mapper.size(); j++) {
                    if (mapper.isAttachedToSeries(j) && s.getValue(mapper.getColumnName(j)) != null) {
                        if (struct.findComponent(mapper.getColumnName(j)) instanceof DimensionType) {
                            writer.writeStartElement("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/generic", "Value");
                            writer.writeAttribute("concept", mapper.getColumnName(j));
                            writer.writeAttribute("value", s.getValue(mapper.getSeriesIndex(mapper.getColumnName(j))));
                            writer.writeEndElement();
                        }
                    }
                }
                writer.writeEndElement();

                boolean writeAttributes = false;
                for (int j = 0; j < mapper.size(); j++) {
                    if (mapper.isAttachedToSeries(j) && s.getValue(mapper.getColumnName(j)) != null) {
                        if (struct.findComponent(mapper.getColumnName(j)) instanceof AttributeType) {
                            if (!writeAttributes) {
                                writeAttributes = true;
                                writer.writeStartElement("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/generic", "Attributes");
                            }
                            writer.writeStartElement("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/generic", "Value");
                            writer.writeAttribute("concept", mapper.getColumnName(j));
                            writer.writeAttribute("value", s.getValue(mapper.getSeriesIndex(mapper.getColumnName(j))));
                            writer.writeEndElement();
                        }
                    }
                }
                if (writeAttributes) {
                    writer.writeEndElement();
                }
                for (int k = 0; k < s.getObservations().size(); k++) {
                    Obs o = s.getObservations().get(k);
                    writer.writeStartElement("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/generic", "Obs");
                    if (mapper.isAttachedToObservation(struct.getDataStructureComponents().getDimensionList().getTimeDimension().getId().toString())) {
                        writer.writeStartElement("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/generic", "Time");
                        writer.writeCharacters(o.getValue(mapper.getObservationIndex(struct.getDataStructureComponents().getDimensionList().getTimeDimension().getId().toString())));
                        writer.writeEndElement();
                    }
                    if (mapper.isAttachedToObservation(struct.getDataStructureComponents().getMeasureList().getPrimaryMeasure().getId().toString())) {
                        writer.writeStartElement("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/generic", "ObsValue");
                        writer.writeAttribute("value", o.getValue(mapper.getObservationIndex(struct.getDataStructureComponents().getMeasureList().getPrimaryMeasure().getId().toString())));
                        writer.writeEndElement();
                    }
                    for (int j = 0; j < mapper.size(); j++) {
                        if (mapper.isAttachedToObservation(j) && o.getValue(mapper.getColumnName(j)) != null) {
                            if (struct.findComponent(mapper.getColumnName(j)) instanceof DimensionType) {
                                writer.writeStartElement("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/generic", "Value");
                                writer.writeAttribute("concept", mapper.getColumnName(i));
                                writer.writeAttribute("value", o.getValue(mapper.getObservationIndex(mapper.getColumnName(i))));
                                writer.writeEndElement();
                            }
                        }
                    }
                    writeAttributes = false;

                    for (int j = 0; j < mapper.size(); j++) {
                        if (mapper.isAttachedToObservation(j) && o.getValue(mapper.getColumnName(j)) != null) {
                            if (struct.findComponent(mapper.getColumnName(j)) instanceof AttributeType) {
                                if (!writeAttributes) {
                                    writeAttributes = true;
                                    writer.writeStartElement("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/generic", "Attributes");
                                }
                                writer.writeStartElement("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/generic", "Value");
                                writer.writeAttribute("concept", mapper.getColumnName(j));
                                writer.writeAttribute("value", o.getValue(mapper.getObservationIndex(mapper.getColumnName(j))));
                                writer.writeEndElement();
                            }
                        }
                    }
                    if (writeAttributes) {
                        writer.writeEndElement();
                    }

                    writer.writeEndElement();

                }
                writer.writeEndElement();
            }
        }
        writer.writeEndElement();
        writer.writeEndElement();
        writer.writeEndDocument();
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
}

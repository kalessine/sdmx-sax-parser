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
package sdmx.version.json;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import sdmx.Registry;
import sdmx.commonreferences.DataStructureReference;
import sdmx.data.ColumnMapper;
import sdmx.data.DataSet;
import sdmx.data.DataSetWriter;
import sdmx.message.DataStructure;
import sdmx.structure.codelist.CodeType;
import sdmx.structure.datastructure.DataStructureType;
import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.stream.JsonWriter;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.data.time.RegularTimePeriod;
import sdmx.common.PayloadStructureType;
import sdmx.footer.FooterType;
import sdmx.message.BaseHeaderType;
import sdmx.message.ContactType;
import sdmx.message.PartyType;
import sdmx.structure.base.Component;
import sdmx.structure.base.ComponentUtil;
import sdmx.structure.base.ItemType;
import sdmx.structure.base.NameableType;
import sdmx.structure.concept.ConceptType;
import sdmx.structure.dataflow.DataflowType;
import sdmx.structureddata.ValueTypeResolver;
import sdmx.util.time.TimeUtil;
import sdmx.version.common.ParseDataCallbackHandler;
import sdmx.xml.anyURI;

/**
 *
 * @author James
 */
public class StreamingSdmxJSONWriter implements ParseDataCallbackHandler, DataSetWriter {

    public static final SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
    Registry registry = null;
    private DataflowType flow = null;
    DataStructureReference dsref = null;
    private DataStructureType struct = null;
    LinkedHashMap<String, List<String>> dataSetValues = new LinkedHashMap<String, List<String>>();
    LinkedHashMap<String, List<String>> seriesValues = new LinkedHashMap<String, List<String>>();
    LinkedHashMap<String, List<String>> obsValues = new LinkedHashMap<String, List<String>>();
    LinkedHashMap<String, List<String>> dataSetAtts = new LinkedHashMap<String, List<String>>();
    LinkedHashMap<String, List<String>> seriesAtts = new LinkedHashMap<String, List<String>>();
    LinkedHashMap<String, List<String>> obsAtts = new LinkedHashMap<String, List<String>>();
    private String dimensionAtObservation = null;
    private Locale locale = Locale.ENGLISH;
    private String requestURI = "";
    private String structureURI = "";

    JsonWriter writer = null;

    private boolean has_series = false;
    private boolean in_series = false;
    private boolean written_series = true;
    private boolean written_series_header = false;
    private boolean written_observations_header = false;

    List<Integer> dsKey = new ArrayList<Integer>();
    List<Integer> dsAtts = new ArrayList<Integer>();
    int seriesSize = 0;
    List<Integer> seriesKey = new ArrayList<Integer>();
    int seriesAttsSize = 0;
    List<Integer> seriesAttsKey = new ArrayList<Integer>();
    int obsSize = 0;
    List<Integer> obsKey = new ArrayList<Integer>();
    private double obsValue = 0d;
    int obsAttsSize = 0;
    List<Integer> obsAttsKey = new ArrayList<Integer>();

    public StreamingSdmxJSONWriter(OutputStream out, Registry reg) {
        this.registry = reg;
        writer = new JsonWriter(new OutputStreamWriter(out));
        try {
            writer.beginObject();
        } catch (IOException ex) {
            Logger.getLogger(StreamingSdmxJSONWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void newDataSet() {
        try {
            writer.beginObject();
            writer.name("action").value("Information");
            writer.name("links").beginObject();
            writer.name("href").value(structureURI);
            writer.name("rel").value("datastructure");
            writer.endObject();
        } catch (IOException ex) {
            Logger.getLogger(StreamingSdmxJSONWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void newSeries() {
        has_series = true;
        seriesKey.clear();
        for (int i = 0; i < seriesAttsKey.size(); i++) {
            seriesAttsKey.set(i, -1);
        }

        if (!written_series_header) {
            try {
                writer.name("series").beginObject();
            } catch (IOException ex) {
                Logger.getLogger(StreamingSdmxJSONWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
            written_series_header = true;
        }
        writeSeries();
        written_series = false;
    }

    @Override
    public void newObservation() {
        if (!has_series && !written_observations_header) {
            try {
                writer.name("observations").beginObject();
            } catch (IOException ex) {
                Logger.getLogger(StreamingSdmxJSONWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
            written_observations_header = true;
        }
        obsKey.clear();
        writeSeries();
    }

    public void writeSeries() {
        if (has_series && !written_series) {
            try {
                writer.name(toKeyString(seriesKey)).beginObject();
                writer.name("annotations").beginArray().endArray();
                writer.name("attributes");
                toAttributeArray(seriesAttsKey);
                writer.name("observations").beginObject();
            } catch (IOException ex) {
                Logger.getLogger(StreamingSdmxJSONWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
            written_series = true;
        }
    }

    @Override
    public void writeDataSetComponent(String name, String val) {
        if (struct.isDimension(name)) {
            List<String> list = dataSetValues.get(name);
            if (list == null) {
                list = new ArrayList<String>();
                dataSetValues.put(name, list);
            }
            dsKey.add(register(list, val));
        } else {
            List<String> list = dataSetAtts.get(name);
            if (list == null) {
                list = new ArrayList<String>();
                dataSetAtts.put(name, list);
            }
            dsAtts.add(register(list, val));
        }
    }

    @Override
    public void writeSeriesComponent(String name, String val) {
        if (struct.isDimension(name)) {
            List<String> list = seriesValues.get(name);
            if (list == null) {
                list = new ArrayList<String>();
                seriesValues.put(name, list);
            }
            seriesKey.add(register(list, val));
        } else {
            List<String> list = seriesAtts.get(name);
            if (list == null) {
                list = new ArrayList<String>();
                seriesAtts.put(name, list);
                seriesAttsSize++;
                seriesAttsKey.add(-1);
            }
            List<String> atts = new ArrayList<String>(seriesAtts.keySet());
            seriesAttsKey.set(atts.indexOf(name), register(list, val));
        }
    }

    @Override
    public void writeObservationComponent(String name, String val) {
        //System.out.println("WriteObsComponent"+name+":"+val);
        if (struct.isDimension(name)) {
            List<String> list = obsValues.get(name);
            if (list == null) {
                list = new ArrayList<String>();
                obsValues.put(name, list);
            }
            obsKey.add(register(list, val));
            //System.out.println("obsKey:"+toKeyString(obsKey));
        } else if (struct.isAttribute(name)) {
            List<String> list = obsAtts.get(name);
            if (list == null) {
                list = new ArrayList<String>();
                obsAtts.put(name, list);
                obsAttsSize++;
                obsAttsKey.add(-1);
            }
            List<String> atts = new ArrayList<String>(obsAtts.keySet());
            obsAttsKey.set(atts.indexOf(name), register(list, val));
        } else if (struct.isPrimaryMeasure(name)) {
            obsValue = Double.parseDouble(val);
        }
    }

    @Override
    public void writeGroupValues(String name, HashMap<String, Object> group) {

    }

    @Override
    public void finishObservation() {
        try {
            writer.name(toKeyString(obsKey));
            toObsAttributeArray(obsAttsKey);
        } catch (IOException ex) {
            Logger.getLogger(StreamingSdmxJSONWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void finishSeries() {
        writeSeries();
        try {
            writer.endObject(); // "observations"
            writer.endObject();
        } catch (IOException ex) {
            Logger.getLogger(StreamingSdmxJSONWriter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public DataSet finishDataSet() {
        if (has_series) {
            try {
                writer.endObject(); // "series"
            } catch (IOException ex) {
                Logger.getLogger(StreamingSdmxJSONWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (written_observations_header) {
            try {
                writer.endObject(); // "observations"
            } catch (IOException ex) {
                Logger.getLogger(StreamingSdmxJSONWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            writer.endObject(); // DataSet
        } catch (IOException ex) {
            Logger.getLogger(StreamingSdmxJSONWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void setNamespace(String prefix, String namespace) {
    }

    @Override
    public void headerParsed(BaseHeaderType header) {
        try {
            writer.name("header").beginObject();
            writer.name("id").value(header.getId());
            writer.name("prepared").value(DF.format(header.getPrepared().getDate().getDate()));
            if (header.getSender() != null) {
                writer.name("sender").beginObject();
                writer.name("id").value(header.getSender().getId().toString());
                writer.name("name").value(NameableType.toString(header.getSender(), locale));
                if (header.getSender().getContacts() != null) {
                    writer.name("contact").beginArray();
                    for (ContactType c : header.getSender().getContacts()) {
                        writer.beginObject();
                        if (c.getNames() != null) {
                            writer.name("name").value(NameableType.toString(c));
                        }
                        if (c.getDepartments() != null) {
                            writer.name("department").value(c.getDepartments().get(0).toString());
                        }
                        if (c.getRoles() != null) {
                            writer.name("role").value(c.getRoles().get(0).toString());
                        }
                        if (c.getTelephones() != null) {
                            writer.name("telephone").beginArray();
                            for (String s : c.getTelephones()) {
                                writer.value(s);
                            }
                            writer.endArray();
                        }
                        if (c.getFaxes() != null) {
                            writer.name("fax").beginArray();
                            for (String s : c.getFaxes()) {
                                writer.value(s);
                            }
                            writer.endArray();
                        }
                        if (c.getUris() != null) {
                            writer.name("uri").beginArray();
                            for (anyURI s : c.getUris()) {
                                writer.value(s.toString());
                            }
                            writer.endArray();
                        }
                        if (c.getEmails() != null) {
                            writer.name("email").beginArray();
                            for (String s : c.getEmails()) {
                                writer.value(s);
                            }
                            writer.endArray();
                        }
                        writer.endObject();
                    }
                    writer.endArray();
                }
                writer.endObject();
            }
            if (header.getReceivers() != null) {
                PartyType r = header.getReceivers().get(0);
                writer.name("receiver").beginObject();
                writer.name("id").value(header.getSender().getId().toString());
                writer.name("name").value(NameableType.toString(header.getSender(), locale));
                if (r.getContacts() != null) {
                    writer.name("contact").beginArray();
                    for (ContactType c : r.getContacts()) {
                        writer.beginObject();
                        if (c.getNames() != null) {
                            writer.name("name").value(NameableType.toString(c));
                        }
                        if (c.getDepartments() != null) {
                            writer.name("department").value(c.getDepartments().get(0).toString());
                        }
                        if (c.getRoles() != null) {
                            writer.name("role").value(c.getRoles().get(0).toString());
                        }
                        if (c.getTelephones() != null) {
                            writer.name("telephone").beginArray();
                            for (String s : c.getTelephones()) {
                                writer.value(s);
                            }
                            writer.endArray();
                        }
                        if (c.getFaxes() != null) {
                            writer.name("fax").beginArray();
                            for (String s : c.getFaxes()) {
                                writer.value(s);
                            }
                            writer.endArray();
                        }
                        if (c.getUris() != null) {
                            writer.name("uri").beginArray();
                            for (anyURI s : c.getUris()) {
                                writer.value(s.toString());
                            }
                            writer.endArray();
                        }
                        if (c.getEmails() != null) {
                            writer.name("email").beginArray();
                            for (String s : c.getEmails()) {
                                writer.value(s);
                            }
                            writer.endArray();
                        }
                        writer.endObject();
                    }
                    writer.endArray();
                }
                writer.endObject();
            }
            writer.endObject();
            writer.name("links").beginObject();
            writer.name("href").value(requestURI);
            writer.name("rel").value("request");
            writer.name("title").value("Link to the url that returns this response");
            writer.name("type").value("application/json");
            writer.endObject();
            writer.name("dataSets").beginArray();
            if (header.getStructures() != null && header.getStructures().size() > 0) {
                for (Iterator<sdmx.common.PayloadStructureType> it = header.getStructures().iterator(); it.hasNext();) {
                    PayloadStructureType st = (PayloadStructureType) it.next();
                    this.dsref = (DataStructureReference) st.getStructure();
                    this.struct = this.registry.find(dsref);
                }
            }else{
                System.out.println("DataMessage does not have a Structure!!!");
            }
        } catch (IOException ex) {
            Logger.getLogger(StreamingSdmxJSONWriter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void writeStructure() {
        try {
            writer.name("structure").beginObject();
            writer.name("links").beginArray();
            writer.beginObject();
            writer.name("href").value(this.structureURI);
            writer.name("rel").value("datastructure");
            writer.name("title").value("resolvable uri to datastructure");
            writer.endObject();
            writer.endArray();
            writer.name("name").value(NameableType.toString(flow, this.locale));
            if (struct.findDescription(locale.getLanguage()) != null) {
                writer.name("description").value(struct.findDescription(locale.getLanguage()).getText());
            }
            writer.name("dimensions").beginObject();
            writer.name("dataSet").beginArray();
            String dataSetDims[] = dataSetValues.keySet().toArray(new String[]{});
            for (int i = 0; i < dataSetDims.length; i++) {
                writer.beginObject();
                writer.name("id").value(dataSetDims[i]);
                Component c = struct.findComponent(dataSetDims[i]);
                ConceptType concept = registry.find(c.getConceptIdentity());
                writer.name("name").value(NameableType.toString(concept));
                if (concept.findDescription(locale.getLanguage()) != null) {
                    writer.name("description").value(concept.findDescription(locale.getLanguage()).getText());
                }
                writer.name("keyPosition").value(struct.getKeyPosition(dataSetDims[i]));
                writer.name("values").beginArray();
                List<String> vals = dataSetValues.get(dataSetDims[i]);
                for (String s : vals) {
                    if (ComponentUtil.getRepresentation(registry, struct.findComponent(dataSetDims[i])).getEnumeration() == null) {
                        if (struct.isTimeDimension(dataSetDims[i])) {
                            RegularTimePeriod time = TimeUtil.parseTime(null, s);
                            writer.beginObject();
                            writer.name("id").value(s);
                            writer.name("name").value(s);
                            writer.name("start").value(DF.format(time.getStart()));
                            writer.name("end").value(DF.format(time.getEnd()));
                            writer.endObject();
                        } else {
                            writer.beginObject();
                            writer.name("id").value(s);
                            writer.name("name").value(s);
                            writer.endObject();
                        }
                    } else {
                        ItemType item = ValueTypeResolver.resolveCode(registry, struct, dataSetDims[i], s);
                        if (item != null) {
                            writer.beginObject();
                            writer.name("id").value(item.getId().toString());
                            writer.name("name").value(NameableType.toString(item, this.locale));
                            writer.endObject();
                        }
                    }
                }
                writer.endArray();
                writer.endObject();
            }
            writer.endArray(); // "dataSet"
            if (has_series) {
                writer.name("series").beginArray();
                String seriesDims[] = seriesValues.keySet().toArray(new String[]{});
                for (int i = 0; i < seriesDims.length; i++) {
                    writer.beginObject();
                    writer.name("id").value(seriesDims[i]);
                    Component c = struct.findComponent(seriesDims[i]);
                    ConceptType concept = registry.find(c.getConceptIdentity());
                    writer.name("name").value(NameableType.toString(concept));
                    if (concept.findDescription(locale.getLanguage()) != null) {
                        writer.name("description").value(concept.findDescription(locale.getLanguage()).getText());
                    }
                    writer.name("keyPosition").value(struct.getKeyPosition(seriesDims[i]));

                    writer.name("values").beginArray();
                    List<String> vals = seriesValues.get(seriesDims[i]);
                    for (String s : vals) {
                        if (ComponentUtil.getRepresentation(registry, struct.findComponent(seriesDims[i])).getEnumeration() == null) {
                            if (struct.isTimeDimension(seriesDims[i])) {
                                RegularTimePeriod time = TimeUtil.parseTime(null, s);
                                writer.beginObject();
                                writer.name("id").value(s);
                                writer.name("name").value(s);
                                writer.name("start").value(DF.format(time.getStart()));
                                writer.name("end").value(DF.format(time.getEnd()));
                                writer.endObject();
                            } else {
                                writer.beginObject();
                                writer.name("id").value(s);
                                writer.name("name").value(s);
                                writer.endObject();
                            }
                        } else {
                            ItemType item = ValueTypeResolver.resolveCode(registry, struct, seriesDims[i], s);
                            if (item != null) {
                                writer.beginObject();
                                writer.name("id").value(item.getId().toString());
                                writer.name("name").value(NameableType.toString(item, this.locale));
                                writer.endObject();
                            }
                        }
                    }
                    writer.endArray();
                    writer.endObject();
                }
                writer.endArray();
            }
            writer.name("observations").beginArray();
            String obsDims[] = obsValues.keySet().toArray(new String[]{});
            for (int i = 0; i < obsDims.length; i++) {
                writer.beginObject();
                writer.name("id").value(obsDims[i]);
                Component c = struct.findComponent(obsDims[i]);
                ConceptType concept = registry.find(c.getConceptIdentity());
                writer.name("name").value(NameableType.toString(concept));
                if (concept.findDescription(locale.getLanguage()) != null) {
                    writer.name("description").value(concept.findDescription(locale.getLanguage()).getText());
                }
                writer.name("keyPosition").value(struct.getKeyPosition(obsDims[i]));

                writer.name("values").beginArray();
                List<String> vals = obsValues.get(obsDims[i]);
                for (String s : vals) {
                    if (struct.isPrimaryMeasure(obsDims[i])) {
                        continue;
                    }
                    if (ComponentUtil.getRepresentation(registry, struct.findComponent(obsDims[i])).getEnumeration() == null) {
                        if (struct.isTimeDimension(obsDims[i])) {
                            RegularTimePeriod time = TimeUtil.parseTime(null, s);
                            writer.beginObject();
                            writer.name("id").value(s);
                            writer.name("name").value(s);
                            writer.name("start").value(DF.format(time.getStart()));
                            writer.name("end").value(DF.format(time.getEnd()));
                            writer.endObject();
                        } else {
                            writer.beginObject();
                            writer.name("id").value(s);
                            writer.name("name").value(s);
                            writer.endObject();
                        }
                    } else {
                        ItemType item = ValueTypeResolver.resolveCode(registry, struct, obsDims[i], s);
                        if (item != null) {
                            writer.beginObject();
                            writer.name("id").value(item.getId().toString());
                            writer.name("name").value(NameableType.toString(item, this.locale));
                            writer.endObject();
                        }
                    }
                }
                writer.endArray();
                writer.endObject();
            }
            writer.endArray();
            writer.endObject(); // "dimensions"

            writer.name("attributes").beginObject();
            writer.name("dataSet").beginArray();
            String dataSetAttsArray[] = dataSetAtts.keySet().toArray(new String[]{});
            for (int i = 0; i < dataSetAttsArray.length; i++) {
                writer.beginObject();
                writer.name("id").value(dataSetAttsArray[i]);
                Component c = struct.findComponent(dataSetAttsArray[i]);
                ConceptType concept = registry.find(c.getConceptIdentity());
                writer.name("name").value(NameableType.toString(concept));
                if (concept.findDescription(locale.getLanguage()) != null) {
                    writer.name("description").value(concept.findDescription(locale.getLanguage()).getText());
                }
                //writer.name("keyPosition").value(struct.getKeyPosition(dataSetAttsArray[i]));
                writer.name("values").beginArray();
                List<String> vals = dataSetAtts.get(dataSetAttsArray[i]);
                for (String s : vals) {
                    if (ComponentUtil.getRepresentation(registry, struct.findComponent(dataSetAttsArray[i])).getEnumeration() == null) {
                        if (struct.isTimeDimension(dataSetAttsArray[i])) {
                            writer.beginObject();
                            RegularTimePeriod time = TimeUtil.parseTime(null, s);
                            writer.name("id").value(s);
                            writer.name("name").value(s);
                            writer.name("start").value(DF.format(time.getStart()));
                            writer.name("end").value(DF.format(time.getEnd()));
                            writer.endObject();
                        } else {
                            writer.beginObject();
                            writer.name("id").value(s);
                            writer.name("name").value(s);
                            writer.endObject();
                        }
                    } else {
                        ItemType item = ValueTypeResolver.resolveCode(registry, struct, dataSetAttsArray[i], s);
                        if (item != null) {
                            writer.beginObject();
                            writer.name("id").value(item.getId().toString());
                            writer.name("name").value(NameableType.toString(item, this.locale));
                            writer.endObject();
                        }
                    }
                }
                writer.endArray();
                writer.endObject();
            }
            writer.endArray(); // "dataSet"
            if (has_series) {
                writer.name("series").beginArray();
                String seriesAttsArray[] = seriesAtts.keySet().toArray(new String[]{});
                for (int i = 0; i < seriesAttsArray.length; i++) {
                    writer.beginObject();
                    writer.name("id").value(seriesAttsArray[i]);
                    Component c = struct.findComponent(seriesAttsArray[i]);
                    ConceptType concept = registry.find(c.getConceptIdentity());
                    writer.name("name").value(NameableType.toString(concept));
                    if (concept.findDescription(locale.getLanguage()) != null) {
                        writer.name("description").value(concept.findDescription(locale.getLanguage()).getText());
                    }
                    //writer.name("keyPosition").value(struct.getKeyPosition(seriesAttsArray[i]));

                    writer.name("values").beginArray();
                    List<String> vals = seriesAtts.get(seriesAttsArray[i]);
                    for (String s : vals) {
                        if (ComponentUtil.getRepresentation(registry, struct.findComponent(seriesAttsArray[i])).getEnumeration() == null) {
                            if (struct.isTimeDimension(seriesAttsArray[i])) {
                                RegularTimePeriod time = TimeUtil.parseTime(null, s);
                                writer.beginObject();
                                writer.name("id").value(s);
                                writer.name("name").value(s);
                                writer.name("start").value(DF.format(time.getStart()));
                                writer.name("end").value(DF.format(time.getEnd()));
                                writer.endObject();
                            } else {
                                writer.beginObject();
                                writer.name("id").value(s);
                                writer.name("name").value(s);
                                writer.endObject();
                            }
                        } else {
                            ItemType item = ValueTypeResolver.resolveCode(registry, struct, seriesAttsArray[i], s);
                            if (item != null) {
                                writer.beginObject();
                                writer.name("id").value(item.getId().toString());
                                writer.name("name").value(NameableType.toString(item, this.locale));
                                writer.endObject();
                            }
                        }
                    }
                    writer.endArray();
                    writer.endObject();
                }
                writer.endArray();
            }
            writer.name("observations").beginArray();
            String obsAttsArray[] = obsAtts.keySet().toArray(new String[]{});
            for (int i = 0; i < obsAttsArray.length; i++) {
                writer.beginObject();
                writer.name("id").value(obsAttsArray[i]);
                Component c = struct.findComponent(obsAttsArray[i]);
                ConceptType concept = registry.find(c.getConceptIdentity());
                writer.name("name").value(NameableType.toString(concept));
                if (concept == null) {
                    System.out.println("Null Concept=" + obsAttsArray[i]);
                }
                if (concept.findDescription(locale.getLanguage()) != null) {
                    writer.name("description").value(concept.findDescription(locale.getLanguage()).getText());
                }
                //writer.name("keyPosition").value(struct.getKeyPosition(obsDims[i]));

                writer.name("values").beginArray();
                List<String> vals = obsAtts.get(obsAttsArray[i]);
                for (String s : vals) {
                    if (struct.isPrimaryMeasure(obsAttsArray[i])) {
                        continue;
                    }
                    if (ComponentUtil.getRepresentation(registry, struct.findComponent(obsAttsArray[i])).getEnumeration() == null) {
                        if (struct.isTimeDimension(obsAttsArray[i])) {
                            writer.beginObject();
                            writer.name("id").value(s);
                            writer.name("name").value(s);
                            writer.name("start").value(s);
                            writer.name("end").value(s);
                            writer.endObject();
                        } else {
                            writer.beginObject();
                            writer.name("id").value(s);
                            writer.name("name").value(s);
                            writer.endObject();
                        }
                    } else {
                        ItemType item = ValueTypeResolver.resolveCode(registry, struct, obsAttsArray[i], s);
                        if (item != null) {
                            writer.beginObject();
                            writer.name("id").value(item.getId().toString());
                            writer.name("name").value(NameableType.toString(item, this.locale));
                            writer.endObject();
                        }
                    }
                }
                writer.endArray();
                writer.endObject();
            }
            writer.endArray();
            writer.endObject(); // "dimensions"

            writer.endObject();
        } catch (IOException ex) {
            Logger.getLogger(StreamingSdmxJSONWriter.class.getName()).log(Level.SEVERE, null, ex);
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
    public void documentFinished() {
        try {
            writer.endArray();// DataSets
            writeStructure();
            writer.endObject(); // Root Object
            writer.flush();
            writer.close();

        } catch (IOException ex) {
            Logger.getLogger(StreamingSdmxJSONWriter.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getDimensionAtObservationHint() {
        return dimensionAtObservation;
    }

    @Override
    public void setDimensionAtObservationHint(String s
    ) {
        this.dimensionAtObservation = s;
    }

    @Override
    public Registry getRegistry() {
        return this.registry;
    }

    @Override
    public void setRegistry(Registry reg
    ) {
        this.registry = reg;
    }

    /**
     * @return the locale
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * @param locale the locale to set
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void setRequestURI(String s) {
        requestURI = s;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public Integer register(List<String> map, String s) {
        if (map.contains(s)) {
            return map.indexOf(s);
        } else {
            map.add(s);
            return map.indexOf(s);
        }
    }

    public String toKeyString(List<Integer> list) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1) {
                sb.append(":");
            }
        }
        return sb.toString();
    }

    public void toAttributeArray(List<Integer> list) {
        try {
            writer.beginArray();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) != -1) {
                    writer.value(list.get(i));
                } else {
                    writer.nullValue();
                }
            }
            writer.endArray();
        } catch (IOException ex) {
            Logger.getLogger(StreamingSdmxJSONWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void toObsAttributeArray(List<Integer> list) {
        try {
            writer.beginArray();
            writer.value(obsValue);
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) != -1) {
                    writer.value(list.get(i));
                } else {
                    writer.nullValue();
                }
            }
            writer.endArray();
        } catch (IOException ex) {
            Logger.getLogger(StreamingSdmxJSONWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the structureURI
     */
    public String getStructureURI() {
        return structureURI;
    }

    /**
     * @param structureURI the structureURI to set
     */
    public void setStructureURI(String structureURI) {
        this.structureURI = structureURI;
    }
}

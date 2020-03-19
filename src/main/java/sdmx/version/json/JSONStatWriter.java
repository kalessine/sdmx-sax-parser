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

/**
 *
 * @author James
 */
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import sdmx.version.twopointzero.writer.*;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
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
import sdmx.cube.Cube;
import sdmx.cube.CubeObservation;
import sdmx.data.DataSet;
import sdmx.data.Group;
import sdmx.data.flat.FlatObs;
import sdmx.data.key.FullKey;
import sdmx.structureddata.ValueTypeResolver;
import sdmx.data.structured.Obs;
import sdmx.data.structured.Series;
import sdmx.data.structured.StructuredColumnMapper;
import sdmx.data.structured.StructuredDataSet;
import sdmx.message.*;
import sdmx.structure.base.NameableType;
import sdmx.structure.dataflow.DataflowType;
import sdmx.structure.datastructure.DataStructureType;
import sdmx.structure.datastructure.DimensionType;
import sdmx.structure.datastructure.TimeDimensionType;
import static sdmx.version.twopointzero.writer.GenericDataWriter.writeName;
import net.hamnaberg.jsonstat.util.IntCartesianProduct;
import sdmx.cube.CubeObs;
import sdmx.structure.datastructure.MeasureDimensionType;
import sdmx.util.DataUtilities;

public class JSONStatWriter {

    public static final SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd");

    public static void write(DataMessage message, Registry reg, Locale loc, OutputStream out) throws XMLStreamException, IOException {
        System.out.println("Write!");
        JsonWriter writer = null;
        writer = new JsonWriter(new OutputStreamWriter(out));
        try {
            writer.beginObject();
            writeJSONStatMessage(message, reg, loc, writer);
            writer.endObject();
        } catch (IOException ex) {
            Logger.getLogger(StreamingSdmxJSONWriter.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException io) {
                out.flush();
                out.close();
            }
        }
        System.out.println("Finished Writing");
    }

    /**
     *
     */
    public static void writeJSONStatMessage(DataMessage msg, Registry reg, Locale loc, JsonWriter writer) throws XMLStreamException, IOException {
        DataStructureType struct = reg.find(DataUtilities.getDataStructureReference(msg));
        writer.name(DataUtilities.getDataStructureReference(msg).getMaintainableParentId().toString()).beginObject();
        writer.name("label").value(NameableType.toString(struct, loc));
        writer.name("source").value(NameableType.toString(msg.getHeader().getSender(), loc));
        writer.name("updated").value(DF.format(msg.getHeader().getPrepared().getDate().getDate()));
        writer.name("extension").beginObject();
        if (msg.getHeader().getSender().getContacts() != null && msg.getHeader().getSender().getContacts().size() > 0) {
            writer.name("contact").value(msg.getHeader().getSender().getContacts().get(0).getEmails().get(0));
            writer.name("metadata").beginArray();
            writer.endArray();
        }
        writer.endObject();
        Cube cube = new Cube(struct,reg);
        msg.getDataSets().get(0).query(cube, null);
        Integer value_size = 1;
        for (int i = 0; i < struct.getDataStructureComponents().getDimensionList().getDimensions().size(); i++) {
            value_size *= cube.getValues(struct.getDataStructureComponents().getDimensionList().getDimensions().get(i).getId().toString()).size();
        }
        if (struct.getDataStructureComponents().getDimensionList().getTimeDimension() != null) {
            value_size *= cube.getValues(struct.getDataStructureComponents().getDimensionList().getTimeDimension().getId().toString()).size();
        }
        if (struct.getDataStructureComponents().getDimensionList().getMeasureDimension() != null) {
            value_size *= cube.getValues(struct.getDataStructureComponents().getDimensionList().getMeasureDimension().getId().toString()).size();
        }
        boolean value_labels = false;
        //System.out.println("Cube Size=" + cube.getSize());
        //System.out.println("Value Size=" + value_size);

        int[] lengths = new int[struct.getDataStructureComponents().getDimensionList().size() + (struct.getDataStructureComponents().getDimensionList().getTimeDimension() != null ? 1 : 0) + (struct.getDataStructureComponents().getDimensionList().getMeasureDimension() != null ? 1 : 0)];
        for (int i = 0; i < struct.getDataStructureComponents().getDimensionList().size(); i++) {
            lengths[i] = cube.getValues(struct.getDataStructureComponents().getDimensionList().getDimension(i).getId().toString()).size();
        }
        int timeDimensionIndex = lengths.length - 1 - (struct.getDataStructureComponents().getDimensionList().getMeasureDimension() != null ? 1 : 0);
        if (struct.getDataStructureComponents().getDimensionList().getTimeDimension() != null) {
            lengths[timeDimensionIndex] = cube.getValues(struct.getDataStructureComponents().getDimensionList().getTimeDimension().getId().toString()).size();
        }
        int measureDimensionIndex = lengths.length - 1;
        if (struct.getDataStructureComponents().getDimensionList().getMeasureDimension() != null) {
            lengths[lengths.length - 1] = cube.getValues(struct.getDataStructureComponents().getDimensionList().getMeasureDimension().getId().toString()).size();
        }
        IntCartesianProduct cartesianProduct = new IntCartesianProduct(lengths);
        int[] result = null;

        if (cube.getSize() < (value_size / 2)) {
            value_labels = true;
        }
        if (!value_labels) {
            writer.name("value").beginArray();
            for (int i = 1; i <= value_size; i++) {
                writeValue(i, cartesianProduct.next(), cube, struct, reg, value_labels, writer);
            }
            writer.endArray();
        } else {
            writer.name("value").beginObject();
            for (int i = 1; i <= value_size; i++) {
                writeValue(i, cartesianProduct.next(), cube, struct, reg, value_labels, writer);
            }
            writer.endObject();
        }
        writer.name("status").beginArray().endArray();
        writer.name("dimension").beginObject();
        writer.name("id").beginArray();
        for (int i = 0; i < struct.getDataStructureComponents().getDimensionList().size(); i++) {
            writer.value(struct.getDataStructureComponents().getDimensionList().getDimension(i).getId().toString());
        }
        if (struct.getDataStructureComponents().getDimensionList().getTimeDimension() != null) {
            writer.value(struct.getDataStructureComponents().getDimensionList().getTimeDimension().getId().toString());
        }
        writer.endArray();
        writer.name("size").beginArray();
        for (int i = 0; i < struct.getDataStructureComponents().getDimensionList().size(); i++) {
            writer.value(cube.getValues(struct.getDataStructureComponents().getDimensionList().getDimension(i).getId().toString()).size());
        }
        if (struct.getDataStructureComponents().getDimensionList().getTimeDimension() != null) {
            writer.value(cube.getValues(struct.getDataStructureComponents().getDimensionList().getTimeDimension().getId().toString()).size());
        }
        writer.endArray();
        writer.name("role").beginObject();
        if (struct.getDataStructureComponents().getDimensionList().getTimeDimension() != null) {
            writer.name("time").beginArray().value(struct.getDataStructureComponents().getDimensionList().getTimeDimension().getId().toString()).endArray();
        }
        if (struct.getDataStructureComponents().getDimensionList().getMeasureDimension() != null) {
            writer.name("concept").beginArray().value(struct.getDataStructureComponents().getDimensionList().getMeasureDimension().getId().toString()).endArray();
        }
        writer.endObject();//role

        for (int i = 0; i < struct.getDataStructureComponents().getDimensionList().size(); i++) {
            DimensionType dim = struct.getDataStructureComponents().getDimensionList().getDimension(i);
            writer.name(dim.getId().toString()).beginObject();
            writer.name("label").value(NameableType.toString(reg.find(dim.getConceptIdentity()), loc));
            writer.name("category").beginObject();
            writer.name("index").beginArray();
            int index = 0;
            for (String s : cube.getValues(dim.getId().toString())) {
                writer.value(s);
            }
            writer.endArray();
            writer.name("label").beginObject();
            for (String s : cube.getValues(dim.getId().toString())) {
                writer.name(s).value(NameableType.toString(ValueTypeResolver.resolveCode(reg, struct, dim.getId().toString(), s), loc));
            }
            writer.endObject();
            //writer.name("unit").beginObject();
            //writer.endObject();

            writer.endObject();
            writer.endObject();
        }
        if (struct.getDataStructureComponents().getDimensionList().getTimeDimension() != null) {
            TimeDimensionType dim = struct.getDataStructureComponents().getDimensionList().getTimeDimension();
            writer.name(dim.getId().toString()).beginObject();
            writer.name("label").value(NameableType.toString(reg.find(dim.getConceptIdentity()), loc));
            writer.name("category").beginObject();
            writer.name("index").beginObject();
            int index = 0;
            for (String s : cube.getValues(dim.getId().toString())) {
                writer.name(s).value(index++);
            }
            writer.endObject();
            //writer.name("label").beginObject();
            //for(String s:cube.getValidCodes(dim.getId().toString())){
            //    writer.name(s).value(s);
            //}
            //writer.endObject();
            //writer.name("unit").beginObject();
            //writer.endObject();

            writer.endObject();
            writer.endObject();
        }
        if (struct.getDataStructureComponents().getDimensionList().getMeasureDimension() != null) {
            MeasureDimensionType dim = struct.getDataStructureComponents().getDimensionList().getMeasureDimension();
            writer.name(dim.getId().toString()).beginObject();
            writer.name("label").value(NameableType.toString(reg.find(dim.getConceptIdentity()), loc));
            writer.name("category").beginObject();
            writer.name("index").beginArray();
            int index = 0;
            for (String s : cube.getValues(dim.getId().toString())) {
                writer.value(s);
            }
            writer.endArray();
            writer.name("label").beginObject();
            for (String s : cube.getValues(dim.getId().toString())) {
                writer.name(s).value(NameableType.toString(ValueTypeResolver.resolveCode(reg, struct, dim.getId().toString(), s), loc));
            }
            writer.endObject();
            //writer.name("unit").beginObject();
            //writer.endObject();

            writer.endObject();//Category
            writer.endObject();// Dimension
        }

        writer.endObject(); //Dimension
        writer.endObject(); //Document
    }

    public static void writeValue(int index, int[] result, Cube cube, DataStructureType struct,Registry reg, boolean labels, JsonWriter writer) throws IOException {
        FlatObs obs = null;
        try {
            obs = cube.findFlatObs(toFullKey(result, struct, reg, cube));
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        //if( obs == null ) {
        //    toFullKey(result, flow, reg, cube).dump();
        //}
        if (!labels) {
            if (obs == null) {
                writer.nullValue();
            } else {
                writer.value(Double.parseDouble(obs.getValue(cube.getColumnMapper().getColumnIndex(struct.getDataStructureComponents().getMeasureList().getPrimaryMeasure().getId().toString()))));
            }
        } else if (obs == null) {
        } else if (obs.getValue(cube.getColumnMapper().getColumnIndex(struct.getDataStructureComponents().getMeasureList().getPrimaryMeasure().getId().toString())) == null) {
            //writer.name(String.valueOf(index)).value(null);
        } else {
            writer.name(String.valueOf(index)).value(Double.parseDouble(obs.getValue(cube.getColumnMapper().getColumnIndex(struct.getDataStructureComponents().getMeasureList().getPrimaryMeasure().getId().toString()))));
        }
    }

    public static FullKey toFullKey(int[] result, DataStructureType struct, Registry reg, Cube cube) {
        LinkedHashMap<String, Object> key = new LinkedHashMap<String, Object>();
        int i = 0;
        for (; i < struct.getDataStructureComponents().getDimensionList().size(); i++) {
            DimensionType dim = struct.getDataStructureComponents().getDimensionList().getDimension(i);
            key.put(dim.getId().toString(), cube.getValues(dim.getId().toString()).get(result[i]));
        }
        if (struct.getDataStructureComponents().getDimensionList().getTimeDimension() != null) {
            TimeDimensionType td = struct.getDataStructureComponents().getDimensionList().getTimeDimension();
            key.put(td.getId().toString(), cube.getValues(td.getId().toString()).get(result[i++]));
        }
        if (struct.getDataStructureComponents().getDimensionList().getMeasureDimension() != null) {
            MeasureDimensionType md = struct.getDataStructureComponents().getDimensionList().getMeasureDimension();
            key.put(md.getId().toString(), cube.getValues(md.getId().toString()).get(result[i++]));
        }
        return new FullKey(key);
    }

}

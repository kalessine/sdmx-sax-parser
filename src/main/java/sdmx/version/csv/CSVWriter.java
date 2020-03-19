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
package sdmx.version.csv;

/**
 *
 * @author James
 */
import sdmx.version.json.*;
import com.google.gson.stream.JsonWriter;
import java.io.BufferedWriter;
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
import sdmx.message.*;
import sdmx.structure.base.NameableType;
import sdmx.structure.dataflow.DataflowType;
import sdmx.structure.datastructure.DataStructureType;
import sdmx.structure.datastructure.DimensionType;
import sdmx.structure.datastructure.TimeDimensionType;
import static sdmx.version.twopointzero.writer.GenericDataWriter.writeName;
import net.hamnaberg.jsonstat.util.IntCartesianProduct;
import sdmx.structure.base.ItemType;
import sdmx.structure.datastructure.MeasureDimensionType;
import sdmx.structureddata.StructuredDataMessage;
import sdmx.structureddata.StructuredDataSet;

public class CSVWriter {

    public static final SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd");

    public static void write(DataMessage message,Registry reg, Locale loc, OutputStream out) throws XMLStreamException, IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
        StructuredDataMessage sdm = new StructuredDataMessage(message, reg);
        StructuredDataSet ds = sdm.getStructuredDataSet(0);
        for (int i = 0; i < ds.size(); i++) {
            for (int j = 0; j < ds.getColumnCount(); j++) {
                ItemType itm = ds.getStructuredValue(i, j).getCode();
                if (itm == null) {
                    bw.write(ds.getStructuredValue(i, j).getValue());
                } else {
                    bw.write(NameableType.toString(itm,loc));
                }
                if( j < ds.size()) {
                    bw.write(",");
                }
            }
            bw.newLine();
        }
        bw.flush();
        bw.close();
    }
}

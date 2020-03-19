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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import sdmx.Registry;
import sdmx.SdmxIO;
import sdmx.cube.CubeObs;
import sdmx.data.AttachmentLevel;
import sdmx.data.ColumnMapper;
import sdmx.data.flat.FlatColumnMapper;
import sdmx.data.flat.FlatDataSetReader;
import sdmx.data.flat.FlatObs;
import sdmx.exception.ParseException;
import sdmx.message.StructureType;
import sdmx.structure.base.Component;
import sdmx.structure.base.ItemSchemeType;
import sdmx.structure.dataflow.DataflowType;
import sdmx.structure.datastructure.DataStructureType;

/**
 *
 * @author James
 */
public class CompactDataReader implements FlatDataSetReader {

    public static void main(String args[]) throws FileNotFoundException, IOException, ParseException {
        File f = new File("test/resources/sdmx20-samples/CompactSample.xml");
        File f2 = new File("test/resources/sdmx20-samples/StructureSample.xml");
        StructureType st = SdmxIO.parseStructure(new FileInputStream(f2));
        Registry reg = st;
        DataflowType df = st.getStructures().getDataStructures().getDataStructures().get(0).asDataflow();
        FileReader fr = new FileReader(f);
        CompactDataReader r = new CompactDataReader(fr, reg, df);
        while (r.hasNext()) {
            r.next();
            r.getObservation().dump();
            System.out.println();
        }
    }

    private Reader reader = null;
    private static XMLInputFactory factory = XMLInputFactory.newInstance();
    private XMLEventReader eventReader = null;
    boolean bDataSet = false;
    boolean bSeries = false;
    boolean bObservation = false;

    Registry reg = null;
    DataflowType df = null;

    FlatColumnMapper columns = new FlatColumnMapper();

    HashMap<String, String> dataSetAttributes = new HashMap<String, String>();
    HashMap<String, String> seriesAttributes = new HashMap<String, String>();
    HashMap<String, String> obsAttributes = new HashMap<String, String>();

    public CompactDataReader(Reader r, Registry reg, DataflowType df) {
        this.reader = r;
        this.reg = reg;
        this.df = df;
        init();
    }

    public boolean hasNext() {
        return eventReader.hasNext();
    }

    public void init() {
        DataStructureType struct = reg.find(this.df.getStructure());
        for (int i = 0; i < struct.getDataStructureComponents().getDimensionList().size(); i++) {
            Component c = struct.getDataStructureComponents().getDimensionList().getDimension(i);
            // We need to make sure the columns come in the right order!
            this.columns.registerColumn(c.getId().toString(), AttachmentLevel.OBSERVATION);
        }
        if (struct.getDataStructureComponents().getDimensionList().getTimeDimension() != null) {
            Component c = struct.getDataStructureComponents().getDimensionList().getTimeDimension();
            this.columns.registerColumn(c.getId().toString(), AttachmentLevel.OBSERVATION);
        }
        if (struct.getDataStructureComponents().getDimensionList().getMeasureDimension() != null) {
            this.columns.registerColumn(struct.getDataStructureComponents().getDimensionList().getMeasureDimension().getId().toString(), AttachmentLevel.OBSERVATION);
        }
        this.columns.registerColumn(struct.getDataStructureComponents().getMeasureList().getPrimaryMeasure().getId().toString(), AttachmentLevel.OBSERVATION);
        for (int k = 0; k < struct.getDataStructureComponents().getAttributeList().size(); k++) {
            columns.registerColumn(struct.getDataStructureComponents().getAttributeList().getAttribute(k).getId().toString(), AttachmentLevel.OBSERVATION);
        }

        try {
            eventReader = factory.createXMLEventReader(
                    reader);

            while (eventReader.hasNext() && !bObservation) {
                XMLEvent event = eventReader.nextEvent();
                switch (event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT:
                        StartElement startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();
                        if (qName.equalsIgnoreCase("DataSet")) {
                            dataSetAttributes.clear();
                            Iterator<Attribute> attributes = startElement.getAttributes();
                            while (attributes.hasNext()) {
                                Attribute at = attributes.next();
                                dataSetAttributes.put(at.getName().getLocalPart(), at.getValue());
                            }
                            bDataSet = true;
                        } else if (qName.equalsIgnoreCase("Series")) {
                            bSeries = true;
                            seriesAttributes.clear();
                            Iterator<Attribute> attributes = startElement.getAttributes();
                            while (attributes.hasNext()) {
                                Attribute at = attributes.next();
                                seriesAttributes.put(at.getName().getLocalPart(), at.getValue());
                            }
                        } else if (qName.equalsIgnoreCase("Obs")) {
                            bObservation = true;
                            Iterator<Attribute> attributes = startElement.getAttributes();
                            while (attributes.hasNext()) {
                                Attribute at = attributes.next();
                                System.out.println(at.getName() + ":" + at.getValue());
                            }
                        }
                        break;
                }
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CubeObs getObservation() {
        CubeObs c = new CubeObs(columns);
        Iterator<String> dsIterator = this.dataSetAttributes.keySet().iterator();
        while (dsIterator.hasNext()) {
            String s = dsIterator.next();
            String v = dataSetAttributes.get(s);
            c.addValue(s, v);
        }
        Iterator<String> sIterator = this.seriesAttributes.keySet().iterator();
        while (sIterator.hasNext()) {
            String s = sIterator.next();
            String v = seriesAttributes.get(s);
            c.addValue(s, v);
        }
        Iterator<String> oIterator = this.obsAttributes.keySet().iterator();
        while (oIterator.hasNext()) {
            String s = oIterator.next();
            String v = obsAttributes.get(s);
            c.addValue(s, v);
        }
        return c;
    }

    @Override
    public void next() {
        bObservation = false;
        eventReader.next();
        while (eventReader.hasNext() && !bObservation) {
            try {
                XMLEvent event = eventReader.nextEvent();
                switch (event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT:
                        StartElement startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();
                        if (qName.equalsIgnoreCase("Series")) {
                            bSeries = true;
                            seriesAttributes.clear();
                            Iterator<Attribute> attributes = startElement.getAttributes();
                            while (attributes.hasNext()) {
                                Attribute at = attributes.next();
                                seriesAttributes.put(at.getName().getLocalPart(), at.getValue());
                            }
                        } else if (qName.equalsIgnoreCase("Obs")) {
                            bObservation = true;
                            obsAttributes.clear();
                            Iterator<Attribute> attributes = startElement.getAttributes();
                            while (attributes.hasNext()) {
                                Attribute at = attributes.next();
                                obsAttributes.put(at.getName().getLocalPart(), at.getValue());
                            }
                        }
                        break;
                }
            } catch (XMLStreamException ex) {
                Logger.getLogger(CompactDataReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

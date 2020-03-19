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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import sdmx.commonreferences.DataflowReference;
import sdmx.message.DataQueryMessage;
import sdmx.message.DataStructureQueryMessage;
import sdmx.message.QueryMessage;
import sdmx.query.base.NumericValue;
import sdmx.query.base.QueryIDType;
import sdmx.query.base.TextValue;
import sdmx.query.base.TimeValue;
import sdmx.query.data.AttributeValueType;
import sdmx.query.data.DataParametersAndType;
import sdmx.query.data.DataParametersOrType;
import sdmx.query.data.DataParametersType;
import sdmx.query.data.DataQueryType;
import sdmx.query.data.DimensionValueType;
import sdmx.query.data.TimeDimensionValueType;
import sdmx.structure.dataflow.DataflowType;
import sdmx.xml.DateTime;

/**
 *
 * @author James
 */

public class Sdmx20QueryWriter {
    public static final SimpleDateFormat displayFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    public static Element getHeader(QueryMessage query) {
        Namespace message = Namespace.getNamespace("message", "http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message");
        Element header = new Element("Header");
        header.setNamespace(Namespace.getNamespace("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message"));
        if (query.getHeader().getId() != null && !"".equals(query.getHeader().getId())) {
            Element id = new Element("ID");
            id.addContent(query.getHeader().getId());
            id.setNamespace(message);
            header.addContent(id);
        }
        if (query.getHeader().getTest() != null && !"".equals(query.getHeader().getTest())) {
            Element test = new Element("Test");
            test.addContent(query.getHeader().getTest().toString());
            test.setNamespace(message);
            header.addContent(test);
        }
        if (query.getHeader().getNames() != null && !"".equals(query.getHeader().getNames())) {
            Element name = new Element("Name");
            name.addContent(query.getHeader().getNames().get(0).getText());
            name.setNamespace(message);
            header.addContent(name);
        }
        if (query.getHeader().getPrepared() != null && !"".equals(query.getHeader().getPrepared())) {
            Element prep = new Element("Prepared");
            prep.addContent(query.getHeader().getPrepared().getDate().toString());
            prep.setNamespace(message);
            header.addContent(prep);
        }
        if (query.getHeader().getSender() != null) {
            Element send = new Element("Sender");
            send.setAttribute("id", query.getHeader().getSender().getId().toString());
            send.setNamespace(message);
            header.addContent(send);
        }
        if (query.getHeader().getReceivers() != null) {
            Element rec = new Element("Receiver");
            rec.setAttribute("id", query.getHeader().getReceivers().get(0).getId().toString());
            rec.setNamespace(message);
            header.addContent(rec);
        }
        return header;
    }

    public static Document toDocument(DataStructureQueryMessage query) {
        Document doc = new Document();
        Element root = new Element("QueryMessage");
        root.setNamespace(Namespace.getNamespace("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/query"));
        Namespace message = Namespace.getNamespace("message", "http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message");
        Namespace queryNamespace = Namespace.getNamespace("query", "http://www.SDMX.org/resources/SDMXML/schemas/v2_0/query");
        root.setNamespace(message);
        Element queryElement = new Element("Query");
        queryElement.setNamespace(message);
        Element keyfamilyWhere = new Element("KeyFamilyWhere");
        Element and = new Element("And");
        keyfamilyWhere.setNamespace(queryNamespace);
        Element kf = new Element("KeyFamily");
        kf.setNamespace(queryNamespace);
        and.setNamespace(queryNamespace);
        if (query.getDataStructureWhereType() != null && query.getDataStructureWhereType().getId() != null) {
            kf.setText(query.getDataStructureWhereType().getId().get(0).toString());
            and.addContent(kf);
        }
        keyfamilyWhere.addContent(and);
        //or.addContent(kf);

        //keyfamilyWhere.addContent(or);
        queryElement.addContent(keyfamilyWhere);
        root.addContent(getHeader(query));
        root.addContent(queryElement);
        doc.setRootElement(root);
        return doc;
    }

    public static Document toListDataflows(DataStructureQueryMessage query) {
        Document doc = new Document();
        Element root = new Element("RegistryInterface");
        Namespace message = Namespace.getNamespace("message", "http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message");
        root.setNamespace(message);
        Namespace registryNamespace = Namespace.getNamespace("registry", "http://www.SDMX.org/resources/SDMXML/schemas/v2_0/registry");
        root.addContent(getHeader(query));
        Element req = new Element("QueryStructureRequest");
        req.setAttribute("resolveReferences", "false");
        req.setNamespace(message);
        Element dataflowWhere = new Element("DataflowRef");
        dataflowWhere.setNamespace(registryNamespace);
        req.addContent(dataflowWhere);
        root.addContent(req);
        doc.setRootElement(root);
        return doc;
    }

    public static Document toQueryDataStructure(DataStructureQueryMessage query) {
        Document doc = new Document();
        Element root = new Element("RegistryInterface");
        Namespace message = Namespace.getNamespace("message", "http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message");
        root.setNamespace(message);
        Namespace registryNamespace = Namespace.getNamespace("registry", "http://www.SDMX.org/resources/SDMXML/schemas/v2_0/registry");
        root.addContent(getHeader(query));
        Element req = new Element("QueryStructureRequest");
        req.setAttribute("resolveReferences", "true");
        req.setNamespace(message);
        Element kfWhere = new Element("KeyFamilyRef");
        kfWhere.setNamespace(registryNamespace);
        Element agency = new Element("AgencyID");
        agency.setText(query.getDataStructureWhereType().getAgencyId().toString());
        agency.setNamespace(registryNamespace);
        Element id = new Element("KeyFamilyID");
        id.setText(query.getDataStructureWhereType().getId().get(0).toString());
        id.setNamespace(registryNamespace);
        Element ver = new Element("Version");
        ver.setNamespace(registryNamespace);
        ver.setText(query.getDataStructureWhereType().getVersion().getString());

        kfWhere.addContent(agency);
        kfWhere.addContent(id);
        kfWhere.addContent(ver);
        req.addContent(kfWhere);
        root.addContent(req);
        doc.setRootElement(root);
        return doc;
    }

    public static Document toDocument(DataQueryMessage query) {
        Document doc = new Document();
        Element root = new Element("QueryMessage");
        Namespace message = Namespace.getNamespace("message", "http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message");
        Namespace queryNamespace = Namespace.getNamespace("query", "http://www.SDMX.org/resources/SDMXML/schemas/v2_0/query");
        root.setNamespace(message);
        Element queryElement = new Element("Query");
        queryElement.setNamespace(message);
        Element dataWhere = toDataWhere(query.getQuery().getDataWhere());
        queryElement.addContent(dataWhere);
        root.addContent(getHeader(query));
        root.addContent(queryElement);
        doc.setRootElement(root);
        return doc;
    }

    public static Document toNSIDocument(DataQueryMessage query) {
        Document doc = new Document();
        Element root = new Element("QueryMessage");
        Namespace message = Namespace.getNamespace("message", "http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message");
        Namespace queryNamespace = Namespace.getNamespace("query", "http://www.SDMX.org/resources/SDMXML/schemas/v2_0/query");
        root.setNamespace(message);
        Element queryElement = new Element("Query");
        queryElement.setNamespace(message);
        Element dataWhere = toNSIDataWhere(query.getQuery().getDataWhere());
        queryElement.addContent(dataWhere);
        root.addContent(getHeader(query));
        root.addContent(queryElement);
        doc.setRootElement(root);
        return doc;
    }

    public static Element toDataWhere(DataParametersType dw) {
        Element dataWhere = new Element("DataWhere");
        if (dw.getDataflow() != null) {
            dataWhere.addContent(toDataflow("DataSet", dw.getDataflow().get(0)));
        }
        if (dw.getAttributeValue() != null) {
            dataWhere.addContent(toAttributeValue(dw.getAttributeValue()));
        }
        if (dw.getDimensionValue() != null) {
            dataWhere.addContent(toDimensionValue(dw.getDimensionValue()));
        }
        if (dw.getTimeDimensionValue() != null) {
            dataWhere.addContent(toTimeDimensionElement(dw.getTimeDimensionValue()));
        }
        if (dw.getAnd() != null) {
            dataWhere.addContent(toAndElement(dw.getAnd()));
        }
        if (dw.getOr() != null) {
            dataWhere.addContent(toOrElement(dw.getOr()));
        }
        dataWhere.setNamespace(Namespace.getNamespace("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/query"));
        return dataWhere;
    }

    public static Element toNSIDataWhere(DataParametersType dw) {
        Element dataWhere = new Element("DataWhere");
        if (dw.getTimeDimensionValue() != null) {
            dataWhere.addContent(toNSITimeDimensionElement(dw.getTimeDimensionValue()));
        }
        if (dw.getDataflow() != null) {
            dataWhere.addContent(toDataflowType("Dataflow", dw.getDataflow()));
        }
        if (dw.getAttributeValue() != null) {
            dataWhere.addContent(toAttributeValue(dw.getAttributeValue()));
        }
        if (dw.getDimensionValue() != null) {
            dataWhere.addContent(toDimensionValue(dw.getDimensionValue()));
        }
        if (dw.getAnd() != null) {
            dataWhere.addContent(toNSIAndElement(dw.getAnd()));
        }
        if (dw.getOr() != null) {
            if (dw.getOr().size() > 0) {
                dataWhere.addContent(toNSIOrElement(dw.getOr()));
            }
        }
        dataWhere.setNamespace(Namespace.getNamespace("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/query"));
        return dataWhere;
    }

    public static List<Element> toOrElement(List<DataParametersOrType> q) {
        List<Element> list = new ArrayList<Element>();
        for (DataParametersOrType qi : q) {
            list.add(toOrElement(qi));
        }
        return list;
    }
    public static List<Element> toNSIOrElement(List<DataParametersOrType> q) {
        List<Element> list = new ArrayList<Element>();
        for (DataParametersOrType qi : q) {
            list.add(toNSIOrElement(qi));
        }
        return list;
    }

    public static Element toOrElement(DataParametersOrType dw) {
        Element e = new Element("Or");
        if (dw.getDataflow() != null) {
            e.addContent(toDataflow("DataSet", dw.getDataflow().get(0)));
        }
        if (dw.getAttributeValue() != null) {
            e.addContent(toAttributeValue(dw.getAttributeValue()));
        }
        if (dw.getDimensionValue() != null) {
            e.addContent(toDimensionValue(dw.getDimensionValue()));
        }
        if (dw.getTimeDimensionValue() != null) {
            e.addContent(toTimeDimensionElement(dw.getTimeDimensionValue()));
        }
        if (dw.getAnd() != null) {
            e.addContent(toAndElement(dw.getAnd()));
        }
        e.setNamespace(Namespace.getNamespace("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/query"));
        return e;
    }
    public static Element toNSIOrElement(DataParametersOrType dw) {
        Element e = new Element("Or");
        if (dw.getTimeDimensionValue() != null) {
            e.addContent(toNSITimeDimensionElement(dw.getTimeDimensionValue()));
        }
        if (dw.getDataflow() != null) {
            e.addContent(toDataflowType("Dataflow", dw.getDataflow()));
        }
        if (dw.getAttributeValue() != null) {
            e.addContent(toAttributeValue(dw.getAttributeValue()));
        }
        if (dw.getDimensionValue() != null) {
            e.addContent(toDimensionValue(dw.getDimensionValue()));
        }
        if (dw.getAnd() != null) {
            e.addContent(toNSIAndElement(dw.getAnd()));
        }
        e.setNamespace(Namespace.getNamespace("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/query"));
        return e;
    }

    public static List<Element> toAndElement(List<DataParametersAndType> q) {
        List<Element> list = new ArrayList<Element>();
        for (DataParametersAndType qi : q) {
            list.add(toAndElement(qi));
        }
        return list;
    }
    public static List<Element> toNSIAndElement(List<DataParametersAndType> q) {
        List<Element> list = new ArrayList<Element>();
        for (DataParametersAndType qi : q) {
            list.add(toNSIAndElement(qi));
        }
        return list;
    }

    public static Element toAndElement(DataParametersAndType dw) {
        Element e = new Element("And");
        if (dw.getDataflow() != null) {
            e.addContent(toDataflow("DataSet", dw.getDataflow().get(0)));
        }
        if (dw.getAttributeValue() != null) {
            e.addContent(toAttributeValue(dw.getAttributeValue()));
        }
        if (dw.getDimensionValue() != null) {
            e.addContent(toDimensionValue(dw.getDimensionValue()));
        }
        if (dw.getTimeDimensionValue() != null) {
            e.addContent(toTimeDimensionElement(dw.getTimeDimensionValue()));
        }
        if (dw.getOr() != null) {
            e.addContent(toOrElement(dw.getOr()));
        }
        e.setNamespace(Namespace.getNamespace("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/query"));
        return e;
    }
    public static Element toNSIAndElement(DataParametersAndType dw) {
        Element e = new Element("And");
        if (dw.getTimeDimensionValue() != null) {
            e.addContent(toNSITimeDimensionElement(dw.getTimeDimensionValue()));
        }
        if (dw.getDataflow() != null) {
            e.addContent(toDataflowType("Dataflow", dw.getDataflow()));
        }
        if (dw.getAttributeValue() != null) {
            e.addContent(toAttributeValue(dw.getAttributeValue()));
        }
        if (dw.getDimensionValue() != null) {
            e.addContent(toDimensionValue(dw.getDimensionValue()));
        }

        if (dw.getOr() != null) {
            e.addContent(toNSIOrElement(dw.getOr()));
        }
        e.setNamespace(Namespace.getNamespace("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/query"));
        return e;
    }

    public static List<Element> toQueryIDType(String s, List<QueryIDType> q) {
        List<Element> list = new ArrayList<Element>();
        for (QueryIDType qi : q) {
            list.add(toQueryIDType(s, qi));
        }
        return list;
    }
    public static List<Element> toDataflowType(String s, List<DataflowReference> q) {
        List<Element> list = new ArrayList<Element>();
        for (DataflowReference qi : q) {
            list.add(toDataflow(s, qi));
        }
        return list;
    }

    public static Element toQueryIDType(String s, QueryIDType q) {
        Element e = new Element(s);
        e.setText(q.getString());
        e.setNamespace(Namespace.getNamespace("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/query"));
        return e;
    }

    public static List<Element> toAttributeValue(List<AttributeValueType> attributeValues) {
        List<Element> list = new ArrayList<Element>();
        for (AttributeValueType av : attributeValues) {
            list.add(toAttributeValue(av));
        }
        return list;
    }

    public static Element toAttributeValue(AttributeValueType attributeValue) {
        Element e = new Element("Attribute");
        e.setAttribute("id", attributeValue.getId().getString());
        if (attributeValue.getNumericValues() != null && attributeValue.getNumericValues().size() > 0) {
            e.addContent(toNumericValues(attributeValue.getNumericValues()));
        } else if (attributeValue.getTextValues() != null && attributeValue.getTextValues().size() > 0) {
            e.addContent(toTextValues(attributeValue.getTextValues()));
        } else if (attributeValue.getTimeValues() != null && attributeValue.getTimeValues().size() > 0) {
            e.addContent(toTimeValues(attributeValue.getTimeValues()));
        } else {
            e.setText(attributeValue.getValue());
        }
        e.setNamespace(Namespace.getNamespace("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/query"));
        return e;
    }

    public static List<Element> toDimensionValue(List<DimensionValueType> dimValues) {
        List<Element> list = new ArrayList<Element>();
        for (DimensionValueType dm : dimValues) {
            list.add(toDimensionValue(dm));
        }
        return list;
    }

    public static Element toDimensionValue(DimensionValueType dimValue) {
        Element e = new Element("Dimension");
        e.setAttribute("id", dimValue.getId().getString());
        if (dimValue.getNumericValues() != null
                && dimValue.getNumericValues().size() > 0) {
            e.addContent(toNumericValues(dimValue.getNumericValues()));
        } else if (dimValue.getTextValues() != null
                && dimValue.getTextValues().size() > 0) {
            e.addContent(toTextValues(dimValue.getTextValues()));
        } else if (dimValue.getTimeValues() != null
                && dimValue.getTimeValues().size() > 0) {
            e.addContent(toTimeValues(dimValue.getTimeValues()));
        } else {
            e.setText(dimValue.getValue());
        }
        e.setNamespace(Namespace.getNamespace("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/query"));
        return e;
    }

    public static List<Element> toNumericValues(List<NumericValue> numericValues) {
        List<Element> list = new ArrayList<Element>();
        for (NumericValue nv : numericValues) {
            list.add(toNumericValue(nv));
        }
        return list;
    }

    public static Element toNumericValue(NumericValue nv) {
        Element e = new Element("NumericValue");
        e.setText(Double.toString(nv.getValue()));
        e.setNamespace(Namespace.getNamespace("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/query"));
        return e;
    }

    public static List<Element> toTextValues(List<TextValue> textValues) {
        List<Element> list = new ArrayList<Element>();
        for (TextValue tv : textValues) {
            list.add(toTextValue(tv));
        }
        return list;
    }

    public static Element toTextValue(TextValue tv) {
        Element e = new Element("TextValue");
        e.setAttribute("xml:lang", tv.getLang());
        e.setText(tv.getText());
        e.setNamespace(Namespace.getNamespace("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/query"));
        return e;
    }

    public static List<Element> toTimeValues(List<TimeValue> timeValues) {
        List<Element> list = new ArrayList<Element>();
        for (TimeValue tm : timeValues) {
            list.add(toTimeValue(tm));
        }
        return list;
    }

    public static Element toTimeValue(TimeValue tv) {
        Element e = new Element("TimeValue");
        e.setText(tv.toString());
        e.setNamespace(Namespace.getNamespace("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/query"));
        return e;
    }
    public static Element toNSITimeValue(TimeValue tv) {
        Element e = new Element("TimeValue");
        try {
            DateTime dt = DateTime.fromString(tv.toString());
            Calendar cal = Calendar.getInstance();
            cal.setTime(dt.getDate());
            e.setText(Integer.toString(cal.get(Calendar.YEAR)));
        } catch (ParseException ex) {
            Logger.getLogger(Sdmx20QueryWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        e.setNamespace(Namespace.getNamespace("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/query"));
        return e;
    }

    public static List<Element> toTimeDimensionElement(List<TimeDimensionValueType> timeDimensionValues) {
        List<Element> list = new ArrayList<Element>();
        for (TimeDimensionValueType tm : timeDimensionValues) {
            list.add(toTimeValueElement(tm));
        }
        return list;
    }
    public static List<Element> toNSITimeDimensionElement(List<TimeDimensionValueType> timeDimensionValues) {
        List<Element> list = new ArrayList<Element>();
        for (TimeDimensionValueType tm : timeDimensionValues) {
            list.add(toNSITimeValueElement(tm));
        }
        return list;
    }

    public static Element toTimeValueElement(TimeDimensionValueType tv) {
        Element e = new Element("Time");
        Element start = new Element("StartTime");
        System.out.println("Start Time="+tv.getStart().toString());
        System.out.println("End Time="+tv.getEnd().toString());
        start.setText(tv.getStart().toString());
        Element end = new Element("EndTime");
        end.setText(tv.getEnd().toString());
        start.setNamespace(Namespace.getNamespace("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/query"));
        end.setNamespace(Namespace.getNamespace("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/query"));
        e.addContent(start);
        e.addContent(end);
        e.setNamespace(Namespace.getNamespace("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/query"));
        return e;
    }
    public static Element toNSITimeValueElement(TimeDimensionValueType tv) {
        Element e = new Element("Time");
        Element start = new Element("StartTime");
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(displayFormat.parse(tv.getStart().toString()));
            start.setText(Integer.toString(cal.get(Calendar.YEAR)));
        } catch (ParseException ex) {
            Logger.getLogger(Sdmx20QueryWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        Element end = new Element("EndTime");
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(displayFormat.parse(tv.getEnd().toString()));
            end.setText(Integer.toString(cal.get(Calendar.YEAR)));
        } catch (ParseException ex) {
            Logger.getLogger(Sdmx20QueryWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        start.setNamespace(Namespace.getNamespace("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/query"));
        end.setNamespace(Namespace.getNamespace("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/query"));
        e.addContent(start);
        e.addContent(end);
        e.setNamespace(Namespace.getNamespace("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/query"));
        return e;
    }

    private static Element toDataflow(String s, DataflowReference qi) {
        Element elem = new Element(s);
        elem.setNamespace(Namespace.getNamespace("http://www.SDMX.org/resources/SDMXML/schemas/v2_0/query"));
        elem.setText(qi.getMaintainableParentId().toString());
        return elem;
    }
}

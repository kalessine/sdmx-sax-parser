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

import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import org.apache.xmlbeans.XmlObject;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import sdmx.SdmxIO;
import sdmx.common.ActionType;
import sdmx.common.DataType;
import sdmx.common.Name;
import sdmx.common.ObservationalTimePeriodType;
import sdmx.common.PayloadStructureType;
import sdmx.common.TextType;
import sdmx.commonreferences.ConceptReference;
import sdmx.commonreferences.IDType;
import sdmx.commonreferences.NestedID;
import sdmx.commonreferences.NestedNCNameID;
import sdmx.data.DataSet;
import sdmx.data.DataSetWriter;
import sdmx.data.flat.FlatDataSet;
import sdmx.data.flat.FlatDataSetWriter;
import sdmx.data.flat.FlatObs;
import sdmx.message.BaseHeaderType;
import sdmx.message.ContactType;
import sdmx.message.DataMessage;
import sdmx.message.HeaderTimeType;
import sdmx.message.PartyType;
import sdmx.message.SenderType;
import sdmx.message.StructureType;
import sdmx.structure.base.Component;
import sdmx.structure.base.RepresentationType;
import sdmx.structure.codelist.CodelistType;
import sdmx.structure.concept.ConceptSchemeType;
import sdmx.structure.concept.ConceptType;
import sdmx.structure.datastructure.AttributeListType;
import sdmx.structure.datastructure.AttributeType;
import sdmx.structure.datastructure.DataStructureComponents;
import sdmx.structure.datastructure.DataStructureType;
import sdmx.structure.datastructure.DimensionListType;
import sdmx.structure.datastructure.DimensionType;
import sdmx.structure.datastructure.MeasureListType;
import sdmx.version.common.ParseDataCallbackHandler;
import sdmx.version.twopointzero.Sdmx20EventHandler;
import sdmx.xml.DateTime;
import sdmx.xml.DateType;
import sdmx.xml.ID;
import sdmx.xml.anyURI;

/**
 *
 * @author James
 */
public class CompactDataEventHandler extends Sdmx20EventHandler {
    
    public static final int STATE_START = 0;
    public static final int STATE_HEADER = 1;
    public static final int STATE_HEADERID = 2;
    public static final int STATE_HEADERTEST = 3;
    public static final int STATE_HEADERTRUNCATED = 4;
    public static final int STATE_HEADERNAME = 5;
    public static final int STATE_HEADERPREPARED = 6;
    public static final int STATE_HEADERSENDER = 7;
    public static final int STATE_SENDERNAME = 8;
    public static final int STATE_SENDERNAMEEND = 9;
    public static final int STATE_RECEIVERNAME = 10;
    public static final int STATE_RECEIVERNAMEEND = 11;
    public static final int STATE_DATASETACTION = 12;
    public static final int STATE_DATASETACTIONEND = 13;
    public static final int STATE_EXTRACTED = 14;
    public static final int STATE_EXTRACTEDEND = 15;
    public static final int STATE_REPORTINGBEGIN = 16;
    public static final int STATE_REPORTINGBEGINEND = 17;
    public static final int STATE_REPORTINGEND = 18;
    public static final int STATE_REPORTINGENDEND = 19;
    public static final int STATE_HEADEREND = 20;
    public static final int STATE_DATASET = 21;
    public static final int STATE_DATASETEND = 22;
    public static final int STATE_SERIES = 23;
    public static final int STATE_SERIESEND = 24;
    public static final int STATE_OBS = 25;
    public static final int STATE_OBSEND = 26;
    public static final int STATE_FINISH = 27;
    public static final int STATE_GROUP = 28;
    public static final int STATE_GROUPEND = 29;
    public static final int STATE_HEADER_RECEIVER = 30;
    
    String namespace = null;
    String namespaceprefix = null;
    private BaseHeaderType header = new BaseHeaderType();
    private List<PayloadStructureType> payloads = new ArrayList<PayloadStructureType>();
    public int state = -1;
    private String freq = null;
    private boolean in_group = false;
    private boolean in_series = false;
    DataSetWriter writer = new FlatDataSetWriter();
    
    String xmlLang = null;
    Name name = null; // Temp Name
    List<String> telephones = null;
    boolean in_telephone = false;
    List<TextType> depts = null;
    boolean in_department = false;
    List<String> x400s = null;
    boolean in_x400 = false;
    List<String> faxes = null;
    boolean in_fax = false;
    List<TextType> roles = null;
    boolean in_role = false;
    List<anyURI> uris = null;
    boolean in_uri = false;
    List<String> emails = null;
    boolean in_email = false;
    
    DataStructureType keyFamily = null;
    
    List<Name> names = null;
    boolean in_name = false;
    boolean in_header = false;
    boolean in_sender = false;
    boolean in_receiver = false;
    
    SenderType sender = null;
    PartyType receiver = null;
    
    List<ContactType> contacts = null;
    boolean in_contact = false;
    ContactType contact = null;
    private ParseDataCallbackHandler cbHandler = null;
    
    List<DataSet> dataSets = new ArrayList<DataSet>();
    public CompactDataEventHandler() {
    }
    
    public CompactDataEventHandler(DataSetWriter writer) {
        this.writer = writer;
    }
    
    public DataMessage getDataMessage() {
        if (state != STATE_FINISH) {
            System.out.println("state=" + state);
            throw new RuntimeException("You can't get the document before i've finished parsing!");
        }
        DataMessage doc = new DataMessage();
        doc.setHeader(header);
        doc.setDataSets(dataSets);
        doc.setNamespace(namespace);
        doc.setNamespacePrefix(namespaceprefix);
        return doc;
    }
    
    public void startRootElement(Attributes atts) {
        state = STATE_START;
        if (atts.getValue("xsi:schemaLocation") != null || atts.getValue("schemaLocation") != null) {
            // ABS Data has this, UIS Does Not
            //throw new SAXException("Root Element does not have schema location!");
            /*
             if (atts.getValue("xsi:schemaLocation") != null) {
             header.setSchemaLocation(atts.getValue("xsi:schemaLocation"));
             }
             if (atts.getValue("schemaLocation") != null) {
             header.setSchemaLocation(atts.getValue("schemaLocation"));
             }
             */
        }
        
    }
    
    public void startHeader() {
        state = STATE_HEADER;
        in_header = true;
    }
    
    public void startHeaderID() {
        state = STATE_HEADERID;
    }
    
    public void endHeaderID() {
    }
    
    public void startHeaderTest() {
        state = STATE_HEADERTEST;
    }
    
    public void endHeaderTest() {
    }
    
    public void startHeaderTruncated() {
        state = STATE_HEADERTRUNCATED;
    }
    
    public void endHeaderTruncated() {
    }
    
    public void startHeaderPrepared() {
        state = STATE_HEADERPREPARED;
    }
    
    public void endHeaderPrepared() {
    }
    
    public void startHeaderSender(Attributes atts) {
        state = STATE_HEADERSENDER;
        sender = new SenderType();
        sender.setId(new IDType(atts.getValue("id")));
        header.setSender(sender);
        names = new ArrayList<Name>();
        name = null;
        contacts = new ArrayList<ContactType>();
        in_sender = true;
    }
    
    public void endHeaderSender() {
        in_sender = false;
        names = null;
        //System.out.println("Contacts="+contacts);
        sender.setContacts(contacts);
        header.setSender(sender);
        contacts = new ArrayList<ContactType>();
    }
    
    public void endHeader() {
        state = STATE_HEADEREND;
        //System.out.println("Header End");
        if( cbHandler!=null ) {
            //System.out.println("CB="+cbHandler);
            cbHandler.headerParsed(header);
        }
        // Insert witty assertions here
    }
    
    public void startDataSet(String uri, String qName, Attributes atts) throws URISyntaxException {
        state = STATE_DATASET;
        PayloadStructureType payload = new PayloadStructureType();
        if (atts.getValue("keyFamilyURI") != null) {
            payload.setStructureURL(new anyURI(atts.getValue("keyFamilyURI")));
        }
        namespace = uri;
        if (qName.indexOf(":") != -1) {
            namespaceprefix = qName.substring(0, qName.lastIndexOf(":DataSet"));
        } else {
            namespace = null;
            namespaceprefix = null;
        }
        payload.setStructureID(new ID("STR1"));
        /*
         if( keyFamily==null ) {
         NestedNCNameIDType agency = new NestedNCNameIDType(atts.getValue("agencyID"));
         IDType id = new IDType(atts.getValue("id"));
         init(registry.findDataStructure(agency, id));
         }*/
        writer.newDataSet();
        for (int i = 0; i < atts.getLength(); i++) {
            String name = atts.getLocalName(i);
            String val = atts.getValue(i);
            if (!"keyFamilyURI".equals(name) && !"datasetID".equals(name)) {
                writer.writeDataSetComponent(name, val);
            }
        }
        payloads.add(payload);
        
    }
    
    public void startGroup(String name, Attributes atts) {
        state = STATE_GROUP;
        HashMap<String, Object> g = new HashMap<String, Object>();
        for (int i = atts.getLength() - 1; i >= 0; i--) {
            String s = atts.getLocalName(i);
            String v = atts.getValue(i);
            g.put(s, v);
        }
        writer.writeGroupValues(name, g);
        in_group = true;
    }
    
    public void startSeries(String uri, Attributes atts) {
        state = STATE_SERIES;
        in_series = true;
        writer.newSeries();
        for (int i = 0; i < atts.getLength(); i++) {
            String name = atts.getLocalName(i);
            String val = atts.getValue(i);
            writer.writeSeriesComponent(name, val);
        }
    }
    
    public void startObs(String uri, Attributes atts) {
        if (state != STATE_SERIES && state != STATE_OBSEND) {
            throw new RuntimeException("Obs does not follow series, or obsend");
        }
        state = STATE_OBS;
        writer.newObservation();
        for (int i = atts.getLength() - 1; i >= 0; i--) {
            String name = atts.getLocalName(i);
            String val = atts.getValue(i);
            writer.writeObservationComponent(name, val);
        }
    }
    
    public void endObs() {
        if (state != STATE_OBS) {
            throw new RuntimeException("ObsEnd does not follow Obs");
        }
        state = STATE_OBSEND;
        writer.finishObservation();
        //System.out.println("Obs=" + Arrays.toString(obsValues.toArray()));
    }
    
    public void endSeries() {
        if (state != STATE_OBSEND && state != STATE_SERIES) {
            throw new RuntimeException("SeriesEnd does not follow Series State=" + state);
        }
        state = STATE_SERIESEND;
        in_series = false;
        writer.finishSeries();
    }
    
    public void endGroup() {
        state = STATE_GROUPEND;
        in_group = false;
    }
    
    public void endDataSet() {
        //if (state != STATE_SERIESEND||state!=STATE_DATASET||state!=STATE_OBSEND) {
        //    throw new RuntimeException("DataSet does not Series End!");
        //}
        state = STATE_DATASETEND;
        dataSets.add(writer.finishDataSet());
    }
    
    public void endRootElement() {
        state = STATE_FINISH;
        if( cbHandler!=null ) {cbHandler.documentFinished();}
    }
    
    public void startName(String uri, Attributes atts) {
        in_name = true;
        xmlLang = atts.getValue("xml:lang");
        if (names == null) {
            names = new ArrayList<Name>();
        }
    }
    
    public void endName() {
        in_name = false;
        if (state == STATE_HEADER || state == STATE_HEADERTRUNCATED) {
            header.setNames(names);
        }
        if (in_contact) {
            contact.setNames(names);
            return;
        }
        if (in_sender) {
            sender.setNames(names);
            return;
        }
        if (in_receiver) {
            receiver.setNames(names);
            return;
        }
        
    }
    
    public void characters(char[] c) {
        if (in_uri) {
            try {
                uris.add(new anyURI(new String(c)));
            } catch (URISyntaxException ex) {
                Logger.getLogger(CompactDataEventHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            return;
        }
        if (in_role) {
            roles.add(new TextType(xmlLang, new String(c)));
            return;
        }
        if (in_email) {
            emails.add(new String(c));
            return;
        }
        if (in_telephone) {
            telephones.add(new String(c));
            return;
        }
        if (in_department) {
            depts.add(new TextType(xmlLang, new String(c)));
            return;
        }
        if (in_x400) {
            x400s.add(new String(c));
            return;
        }
        if (in_fax) {
            faxes.add(new String(c));
            return;
        }
        if (in_name) {
            names.add(new Name(xmlLang, new String(c)));
            return;
        }
        switch (state) {
            case STATE_HEADERID:
                header.setId(new String(c));
                this.state = STATE_HEADER;
                break;
            case STATE_HEADERTEST:
                header.setTest(Boolean.parseBoolean(new String(c)));
                this.state = STATE_HEADER;
                break;
            case STATE_HEADERTRUNCATED:
                // No Truncated in SDMX 2.1
                //header.setTruncated(Boolean.parseBoolean(new String(c)));
                this.state = STATE_HEADER;
                break;
            case STATE_HEADERPREPARED: {
                try {
                    HeaderTimeType htt = new HeaderTimeType();
                    htt.setDate(DateTime.fromString(new String(c)));
                    header.setPrepared(htt);
                } catch (java.text.ParseException pex) {
                    HeaderTimeType htt2 = new HeaderTimeType();
                    htt2.setDate(new DateTime(new Date()));
                    header.setPrepared(htt2);
                }
                this.state = STATE_HEADER;
            }
            break;
            case STATE_DATASETACTION:
                header.setDataSetAction(ActionType.fromString(new String(c)));
                this.state = STATE_HEADER;
                break;
            case STATE_EXTRACTED:
                try {
                    header.setExtracted(DateTime.fromString(new String(c)));
                } catch (java.text.ParseException ex) {
                    header.setExtracted(new DateTime(new Date()));
                }
                this.state = STATE_HEADER;
                break;
            case STATE_REPORTINGBEGIN:
                header.setReportingBegin(new ObservationalTimePeriodType(new String(c)));
                this.state = STATE_HEADER;
                break;
            case STATE_REPORTINGEND:
                header.setReportingEnd(new ObservationalTimePeriodType(new String(c)));
                this.state = STATE_HEADER;
                break;
        }
    }
    
    void startDataSetAction(Attributes atts) {
        state = STATE_DATASETACTION;
    }
    
    void endDataSetAction() {
        state = STATE_DATASETACTIONEND;
    }
    
    void startExtracted(Attributes atts) {
        state = STATE_EXTRACTED;
    }
    
    void startReportingBegin(Attributes atts) {
        state = STATE_REPORTINGBEGIN;
    }
    
    void startReportingEnd(Attributes atts) {
        state = STATE_REPORTINGEND;
    }
    
    void endExtracted() {
        state = STATE_EXTRACTEDEND;
    }
    
    void endReportingBegin() {
        state = STATE_REPORTINGBEGINEND;
    }
    
    void endReportingEnd() {
        state = STATE_REPORTINGENDEND;
    }
    
    void startContact(Attributes atts) {
        contact = new ContactType();
        names = null;
        in_contact = true;
    }
    
    void startTelephone(Attributes atts) {
        in_telephone = true;
        telephones = contact.getTelephones();
        if (telephones == null) {
            telephones = new ArrayList<String>();
        }
        contact.setTelephones(telephones);
    }
    
    void startDepartment(Attributes atts) {
        in_department = true;
        depts = contact.getDepartments();
        if (depts == null) {
            depts = new ArrayList<TextType>();
        }
        contact.setDepartments(depts);
    }
    
    void startX400(Attributes atts) {
        in_x400 = true;
        x400s = contact.getX400s();
        if (x400s == null) {
            x400s = new ArrayList<String>();
        }
        contact.setX400s(x400s);
    }
    
    void startFax(Attributes atts) {
        in_fax = true;
        faxes = contact.getFaxes();
        if (faxes == null) {
            faxes = new ArrayList<String>();
        }
        contact.setFaxes(faxes);
    }
    
    void endContact() {
        in_contact = false;
        contact.setDepartments(depts);
        contact.setEmails(emails);
        contact.setFaxes(faxes);
        contact.setRoles(roles);
        contact.setUris(uris);
        contact.setX400s(x400s);
        contacts.add(contact);
        contact = null;
    }
    
    void endTelephone() {
        in_telephone = false;
        contact.setTelephones(telephones);
    }
    
    void endDepartment() {
        in_department = false;
        contact.setDepartments(depts);
    }
    
    void endtX400() {
        in_x400 = false;
        contact.setX400s(x400s);
    }
    
    void endFax() {
        in_fax = false;
        contact.setFaxes(faxes);
    }
    
    void startReceiver(Attributes atts) {
        state = STATE_HEADER_RECEIVER;
        receiver = new PartyType();
        receiver.setId(new IDType(atts.getValue("id")));
        names = new ArrayList<Name>();
        name = null;
        contacts = new ArrayList<ContactType>();
        in_receiver = true;
    }
    
    void endReceiver() {
        List<PartyType> receivers = header.getReceivers();
        if (receivers == null) {
            receivers = new ArrayList<PartyType>();
        }
        receiver.setContacts(contacts);
        receivers.add(receiver);
        header.setReceivers(receivers);
        in_receiver = false;
    }
    
    void startRole(Attributes atts) {
        in_role = true;
        roles = contact.getRoles();
        if (roles == null) {
            roles = new ArrayList<TextType>();
        }
        contact.setRoles(roles);
    }
    
    void startURI(Attributes atts) {
        in_uri = true;
        uris = contact.getUris();
        if (uris == null) {
            uris = new ArrayList<anyURI>();
        }
        contact.setUris(uris);
    }
    
    void startEmail(Attributes atts) {
        in_email = true;
        emails = contact.getEmails();
        if (emails == null) {
            emails = new ArrayList<String>();
        }
        contact.setEmails(emails);
    }
    
    void endRole() {
        in_role = false;
    }
    
    void endURI() {
        in_uri = false;
    }
    
    void endEmail() {
        in_email = false;
    }

    /**
     * @return the cbHandler
     */
    public ParseDataCallbackHandler getCbHandler() {
        return cbHandler;
    }

    /**
     * @param cbHandler the cbHandler to set
     */
    public void setCbHandler(ParseDataCallbackHandler cbHandler) {
        this.cbHandler = cbHandler;
    }
}

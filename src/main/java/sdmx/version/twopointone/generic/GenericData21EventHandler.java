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
package sdmx.version.twopointone.generic;

import sdmx.version.twopointzero.generic.*;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import sdmx.common.ActionType;
import sdmx.common.Name;
import sdmx.common.ObservationDimensionType;
import sdmx.common.ObservationalTimePeriodType;
import sdmx.common.PayloadStructureType;
import sdmx.common.TextType;
import sdmx.commonreferences.IDType;
import sdmx.commonreferences.NestedID;
import sdmx.commonreferences.NestedNCNameID;
import sdmx.commonreferences.StructureRef;
import sdmx.commonreferences.StructureReference;
import sdmx.commonreferences.Version;
import sdmx.commonreferences.types.ObjectTypeCodelistType;
import sdmx.commonreferences.types.PackageTypeCodelistType;
import sdmx.data.DataSet;
import sdmx.data.DataSetWriter;
import sdmx.data.flat.FlatDataSetWriter;
import sdmx.message.BaseHeaderType;
import sdmx.message.ContactType;
import sdmx.message.DataMessage;
import sdmx.message.HeaderTimeType;
import sdmx.message.PartyType;
import sdmx.message.SenderType;
import sdmx.version.common.ParseDataCallbackHandler;
import sdmx.version.twopointone.structurespecific.StructureSpecificEventHandler;
import sdmx.version.twopointzero.Sdmx20EventHandler;
import sdmx.version.twopointzero.compact.CompactDataEventHandler;
import sdmx.xml.DateTime;
import sdmx.xml.DateType;
import sdmx.xml.ID;
import sdmx.xml.anyURI;

/**
 *
 * @author James
 */
public class GenericData21EventHandler extends Sdmx20EventHandler {

    public static final int STATE_START = 0;
    public static final int STATE_HEADER = 1;
    public static final int STATE_HEADERID = 2;
    public static final int STATE_HEADERTEST = 3;
    public static final int STATE_HEADERTRUNCATED = 4;
    public static final int STATE_HEADERPREPARED = 5;
    public static final int STATE_HEADERSENDER = 6;
    public static final int STATE_HEADERNAME = 7;
    public static final int STATE_HEADEREND = 8;
    public static final int STATE_DATASET = 9;
    public static final int STATE_KEYFAMILYREF = 10;
    public static final int STATE_KEYFAMILYREFEND = 11;
    public static final int STATE_DATASETEND = 12;
    public static final int STATE_SERIES = 13;
    public static final int STATE_SERIESEND = 14;
    public static final int STATE_OBS = 15;
    public static final int STATE_OBSEND = 16;
    public static final int STATE_FINISH = 17;
    public static final int STATE_GROUP = 18;
    public static final int STATE_GROUPEND = 19;
    public static final int STATE_GROUPKEY = 20;
    public static final int STATE_SERIESKEY = 21;
    public static final int STATE_GROUPATTRIBUTES = 22;
    public static final int STATE_SERIESATTRIBUTES = 23;
    public static final int STATE_OBSATTRIBUTES = 24;
    public static final int STATE_TIME = 25;
    public static final int STATE_DATASETATTRIBUTES = 26;
    public static final int STATE_HEADER_RECEIVER = 27;
    public static final int STATE_DATASETACTION = 28;
    public static final int STATE_DATASETACTIONEND = 29;
    public static final int STATE_REPORTINGBEGIN = 30;
    public static final int STATE_REPORTINGBEGINEND = 31;
    public static final int STATE_REPORTINGEND = 32;
    public static final int STATE_REPORTINGENDEND = 33;
    public static final int STATE_EXTRACTED = 34;
    public static final int STATE_EXTRACTEDEND = 35;
    public static final int STATE_OBSDIMENSION = 36;
    public static final int STATE_URN = 37;

    BaseHeaderType header = new BaseHeaderType();
    private List<PayloadStructureType> payloads = new ArrayList<PayloadStructureType>();
    List<DataSet> datasets = new ArrayList<DataSet>();
    String crossSection = null;
    public int state = -1;
    private String freq = null;
    private boolean in_group = false;
    private boolean in_series = false;
    private ParseDataCallbackHandler cbHandler = null;
    DataSetWriter writer = null;

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

    public GenericData21EventHandler() {
    }

    public GenericData21EventHandler(DataSetWriter writer) {
        this.writer = writer;
    }

    public GenericData21EventHandler(ParseDataCallbackHandler cbHandler) {
        this.cbHandler = cbHandler;
        this.writer = cbHandler.getDataSetWriter();
    }

    public DataMessage getDataMessage() {
        //if (state != STATE_FINISH) {
        //    System.out.println("State=" + state);
        //    throw new RuntimeException("You can't get the document before i've finished parsing!");
        //}
        DataMessage dm = new DataMessage();
        dm.setDataSets(datasets);
        header.setStructures(payloads);
        dm.setHeader(header);
        return dm;
    }

    public void startRootElement(Attributes atts) {
        if (state != -1) {
            throw new RuntimeException("State not -1");
        }
        state = STATE_START;
    }

    public void startHeader() {
        state = STATE_HEADER;
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
        // Insert witty assertions here
        if (cbHandler != null) {
            cbHandler.headerParsed(header);
        }
    }

    public void startDataSet(String uri, Attributes atts) {
        state = STATE_DATASET;
        writer.newDataSet();

    }

    public void startGroup(String uri, Attributes atts) {
        state = STATE_GROUP;
        in_group = true;
    }

    public void startSeries(String uri, Attributes atts) {
        state = STATE_SERIES;
        in_series = true;
        writer.newSeries();
    }

    public void startObs(String uri, Attributes atts) {
        state = STATE_OBS;
        writer.newObservation();
    }

    public void endObs() throws SAXException {
        state = STATE_OBSEND;
        writer.finishObservation();
    }

    public void endSeries() {
        if (state != STATE_OBSEND) {
            throw new RuntimeException("SeriesEnd does not follow Series");
        }
        state = STATE_SERIESEND;
        writer.finishSeries();
        in_series = false;
    }

    public void endGroup() {
        state = STATE_GROUPEND;
        in_group = false;
    }

    public void endDataSet() {
        state = STATE_DATASETEND;
        DataSet ds = writer.finishDataSet();
        //System.out.println("Read DataSet: Size="+ds.size());
        datasets.add(ds);
    }

    public void endRootElement() {
        state = STATE_FINISH;
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
        if (state == STATE_HEADER) {
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
            case STATE_URN:
                anyURI uri = null;
                try {
                    uri = new anyURI(new String(c));
                } catch (URISyntaxException ex) {
                    Logger.getLogger(StructureSpecificEventHandler.class.getName()).log(Level.SEVERE, null, ex);
                    ex.printStackTrace();
                }
                StructureReference reference = new StructureReference(uri);
                payload.setStructure(reference);
                break;
        }
    }

    void startValue(String uri, Attributes atts) {
        //System.out.println("startvalue:concept:" + atts.getValue("concept"));
        //System.out.println("startvalue:value:" + atts.getValue("value"));
        switch (state) {
            case STATE_GROUPKEY:
                break;
            case STATE_GROUPATTRIBUTES:
                break;
            case STATE_SERIESKEY:
                writer.writeSeriesComponent(atts.getValue("id"), atts.getValue("value"));
                break;
            case STATE_SERIESATTRIBUTES:
                writer.writeSeriesComponent(atts.getValue("id"), atts.getValue("value"));
                break;
            case STATE_OBSATTRIBUTES:
                writer.writeObservationComponent(atts.getValue("id"), atts.getValue("value"));
                break;
            case STATE_DATASETATTRIBUTES:
                writer.writeDataSetComponent(atts.getValue("id"), atts.getValue("value"));
                break;
            case STATE_OBS:
                writer.writeObservationComponent(atts.getValue("id"), atts.getValue("value"));
                break;
        }

    }

    void startTime(String uri, Attributes atts) {
        //System.out.println("stime");
        state = STATE_TIME;
    }

    void startAttributes(String uri, Attributes atts) {
        //System.out.println("startattributes");
        switch (state) {
            case STATE_GROUPKEY:
                state = STATE_GROUPATTRIBUTES;
                break;
            case STATE_GROUP:
                state = STATE_GROUPATTRIBUTES;
                break;
            case STATE_SERIESKEY:
                state = STATE_SERIESATTRIBUTES;
                break;
            case STATE_SERIES:
                state = STATE_SERIESATTRIBUTES;
                break;
            case STATE_OBS:
                state = STATE_OBSATTRIBUTES;
                break;
            case STATE_DATASET:
                state = STATE_DATASETATTRIBUTES;
                break;
        }
    }

    void startSeriesKey(String uri, Attributes atts) {
        //System.out.println("startserieskey");
        state = STATE_SERIESKEY;
    }

    void startGroupKey(String uri, Attributes atts) {
        //System.out.println("startgroupkey");
        state = STATE_GROUPKEY;
    }

    void startObsValue(String uri, Attributes atts) {
        //System.out.println("startobsvalue:" + atts.getValue("value"));
        writer.writeObservationComponent("OBS_VALUE", atts.getValue("value"));
    }

    void endValue() {
        //System.out.println("endvalue");
    }

    void endTime() {
        state = STATE_OBS;

    }

    void endAttributes() {
        //System.out.println("endattributes");
    }

    void endSeriesKey() {
        //System.out.println("endserieskey");
    }

    void endGroupKey() {
        //System.out.println("endgroupkey");
    }

    void endObsValue() {
        //System.out.println("endobsvalue");
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

    void startDataSetAction(Attributes atts) {
        state = STATE_DATASETACTION;
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

    void endDataSetAction() {
        state = STATE_DATASETACTIONEND;
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

    PayloadStructureType payload = null;

    void startMessageStructure(Attributes atts) {
        payload = new PayloadStructureType();
        payload.setStructureID(new ID(atts.getValue("structureID")));
        payload.setDimensionAtObservation(new ObservationDimensionType(atts.getValue("dimensionAtObservation")));
        //payload.setExplicitMeasures(Boolean.valueOf(atts.getValue("explicitMeasures")));
    }

    public void startCommonStructure(Attributes atts) {
    }

    public void endMessageStructure() {
        payloads.add(payload);
    }

    public void startRef(Attributes atts) {
        StructureRef ref = new StructureRef(new NestedNCNameID(atts.getValue("agencyID")),
                new IDType(atts.getValue("id")), new Version(atts.getValue("version")), ObjectTypeCodelistType.DATASTRUCTURE, PackageTypeCodelistType.DATASTRUCTURE);
        StructureReference reference = new StructureReference(ref, null);
        payload.setStructure(reference);
    }

    public void endRef() {

    }

    public void startObsKey(String uri, Attributes atts) {
        state = STATE_OBS;
    }

    public void startObsDimension(String uri, Attributes atts) {
        //System.out.println("ObsDimension:"+atts.getValue("value"));
        String dimensionAtObservation = payload.getDimensionAtObservation().toString();
        writer.writeObservationComponent(dimensionAtObservation, atts.getValue("value"));
    }

    public void endCommonStructure() {

    }

    public void startURN(Attributes atts) {
        state = STATE_URN;
    }

    public void endURN() {
        state = STATE_HEADER;
    }

    public void endDocument() {
        if (cbHandler != null) {
            cbHandler.documentFinished();
        }

    }
}

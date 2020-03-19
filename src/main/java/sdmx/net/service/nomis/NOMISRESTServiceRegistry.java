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
package sdmx.net.service.nomis;

import java.io.File;
import sdmx.net.service.ilo.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jfree.data.time.RegularTimePeriod;
import sdmx.Registry;
import sdmx.Repository;
import sdmx.Queryable;
import sdmx.SdmxIO;
import sdmx.common.Description;
import sdmx.common.Name;
import sdmx.commonreferences.CodeReference;
import sdmx.commonreferences.CodelistReference;
import sdmx.commonreferences.ConceptReference;
import sdmx.commonreferences.ConceptSchemeReference;
import sdmx.commonreferences.DataStructureRef;
import sdmx.commonreferences.DataStructureReference;
import sdmx.commonreferences.DataflowReference;
import sdmx.commonreferences.IDType;
import sdmx.commonreferences.ItemReference;
import sdmx.commonreferences.ItemSchemeReference;
import sdmx.commonreferences.ItemSchemeReferenceBase;
import sdmx.commonreferences.NestedID;
import sdmx.commonreferences.NestedNCNameID;
import sdmx.commonreferences.StructureRef;
import sdmx.commonreferences.StructureReference;
import sdmx.commonreferences.Version;
import sdmx.commonreferences.types.PackageTypeCodelistType;
import sdmx.commonreferences.types.StructureTypeCodelistType;
import sdmx.exception.ParseException;
import sdmx.message.BaseHeaderType;
import sdmx.message.DataMessage;
import sdmx.message.DataQueryMessage;
import sdmx.message.DataStructureQueryMessage;
import sdmx.message.HeaderTimeType;
import sdmx.message.PartyType;
import sdmx.message.SenderType;
import sdmx.message.StructureType;
import sdmx.net.LocalRegistry;
import sdmx.net.service.RESTQueryable;
import static sdmx.net.service.RESTQueryable.displayFormat;
import sdmx.querykey.Query;
import sdmx.querykey.QueryDimension;
import sdmx.structure.DataflowsType;
import sdmx.structure.StructuresType;
import sdmx.structure.base.ItemSchemeType;
import sdmx.structure.base.ItemType;
import sdmx.structure.base.MaintainableType;
import sdmx.structure.base.NameableType;
import sdmx.structure.codelist.CodeType;
import sdmx.structure.codelist.CodelistType;
import sdmx.structure.concept.ConceptSchemeType;
import sdmx.structure.concept.ConceptType;
import sdmx.structure.dataflow.DataflowType;
import sdmx.structure.datastructure.DataStructureType;
import sdmx.structure.datastructure.DimensionType;
import sdmx.util.time.TimeUtil;
import sdmx.version.common.ParseDataCallbackHandler;
import sdmx.version.common.ParseParams;
import sdmx.version.common.SOAPStrippingInputStream;
import sdmx.version.twopointone.writer.Sdmx21StructureWriter;
import sdmx.version.twopointzero.QueryToSdmx20Query;
import sdmx.xml.DateTime;

/**
 *
 * @author James
 */
public class NOMISRESTServiceRegistry implements Registry, Repository, Queryable {

    public static void main(String args[]) {
        NOMISRESTServiceRegistry registry = new NOMISRESTServiceRegistry("NOMIS", "http://www.nomisweb.co.uk/api", "uid=0xad235cca367972d98bd642ef04ea259da5de264f");
        List<DataflowType> list = registry.listDataflows();
        Locale loc = Locale.getDefault();
        for (DataflowType df : list) {
            System.out.println(NameableType.toString(df, loc) + "," + df.getAgencyID() + "," + df.getId());
        }
    }
    private String agency = "";
    private String serviceURL = "";
    private String options = "";
    Registry local = new LocalRegistry();

    private List<DataflowType> dataflowList = null;

    public NOMISRESTServiceRegistry(String agency, String service, String options) {
        this.serviceURL = service;
        this.agency = agency;
        this.options = options;
    }

    public void load(StructureType struct) {
        System.out.println("Nomis Load:" + struct);
        local.load(struct);
    }

    public void unload(StructureType struct) {
        local.unload(struct);
    }
    /*
      This function ignores the version argument!!!
      ILO stat does not use version numbers.. simply take the latest
     */

    public DataStructureType find(DataStructureReference ref) {
        DataStructureType dst = local.find(ref);
        if (dst == null) {
            try {
                int geogIndex = ref.getMaintainableParentId().toString().lastIndexOf("_");;
                String geog = ref.getMaintainableParentId().toString().substring(geogIndex+1,ref.getMaintainableParentId().toString().length());
                String geography_string = "geography="+geog;
                if( "NOGEOG".equals(geog)){
                    geography_string="";
                }
                String id = ref.getMaintainableParentId().toString().substring(0,geogIndex);
                StructureType st = retrieve(getServiceURL() + "/v01/dataset/" + id + ".structure.sdmx.xml?"+geography_string);
                st.getStructures().getDataStructures().getDataStructures().get(0).setId(ref.getMaintainableParentId());
                st.getStructures().getDataStructures().getDataStructures().get(0).setVersion(ref.getVersion());
                load(st);
                return local.find(ref);
            } catch (MalformedURLException ex) {
                Logger.getLogger(NOMISRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            } catch (IOException ex) {
                Logger.getLogger(NOMISRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            } catch (ParseException ex) {
                Logger.getLogger(NOMISRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            }
        }
        return dst;
    }

    public ConceptSchemeType find(ConceptSchemeReference ref) {
        ConceptSchemeType dst = local.find(ref);
        return dst;
    }

    public CodelistType find(CodelistReference ref) {
        CodelistType dst = local.find(ref);
        if (dst == null) {
            try {
                StructureType st = retrieve(getServiceURL() + "/v01/codelist/" + ref.getMaintainableParentId() + ".def.sdmx.xml");
                load(st);
                return local.find(ref);
            } catch (MalformedURLException ex) {
                Logger.getLogger(NOMISRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(NOMISRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(NOMISRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return dst;
    }

    public ConceptType find(ConceptReference ref) {
        return local.find(ref);
        /*
        System.out.println("Find:"+ref.getAgencyId()+":"+ref.getMaintainableParentId()+":"+ref.getVersion()+":"+ref.getId());
        ConceptType dst = standalone.findConcept(ref.getId());
        if (dst == null) {
            try {
                StructureType st = retrieve(getServiceURL() +  "/v01/concept/"+ref.getId()+".def.sdmx.xml");
                for(ConceptSchemeType cst:st.getStructures().getConcepts().getConceptSchemes()){
                    for(int i=0;i<cst.size();i++) {
                        standalone.addConcept(cst.getConcept(i));
                    }
                }
                return standalone.findConcept(ref.getId());
            } catch (MalformedURLException ex) {
                Logger.getLogger(NOMISRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(NOMISRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(NOMISRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return dst;
         */
    }

    private StructureType retrieve(String urlString) throws MalformedURLException, IOException, ParseException {
        Logger.getLogger("sdmx").info("NOMISRESTServiceRegistry: retrieve " + urlString);
        String s = options;
        if (urlString.indexOf("?") == -1) {
            s = "?" + s + "&random=" + System.currentTimeMillis();
        } else {
            s = "&" + s + "&random=" + System.currentTimeMillis();
        }
        URL url = new URL(urlString + s);
        HttpURLConnection conn
                = (HttpURLConnection) url.openConnection();
        if (conn.getResponseCode() != 200) {
            throw new IOException(conn.getResponseMessage());
        }
        InputStream in = conn.getInputStream();
        if (SdmxIO.isSaveXml()) {
            String name = System.currentTimeMillis() + ".xml";
            FileOutputStream file = new FileOutputStream(name);
            IOUtils.copy(in, file);
            in = new FileInputStream(name);
        }
        //FileOutputStream temp = new FileOutputStream("temp.xml");
        //org.apache.commons.io.IOUtils.copy(in, temp);
        //temp.close();
        //in.close();
        //in = new FileInputStream("temp.xml");
        ParseParams params = new ParseParams();
        params.setRegistry(this);
        StructureType st = SdmxIO.parseStructure(params, in);
        if (st == null) {
            System.out.println("St is null!");
        } else if (SdmxIO.isSaveXml()) {
            String name = System.currentTimeMillis() + "-21.xml";
            FileOutputStream file = new FileOutputStream(name);
            Sdmx21StructureWriter.write(st, file);
        }
        return st;
    }
    /*
        Uses SdmxIO.parseStructure(InputStream) rather
        than SdmxIO.parseStructure(Registry,InputStream)
     */

    private StructureType retrieve3(String urlString) throws MalformedURLException, IOException, ParseException {
        Logger.getLogger("sdmx").info("NOMISRestServiceRegistry: retrieve3 " + urlString);
        String s = options;
        if (urlString.indexOf("?") == -1) {
            s = "?" + s + "&random=" + System.currentTimeMillis();
        } else {
            s = "&" + s + "&random=" + System.currentTimeMillis();;
        }

        URL url = new URL(urlString + s);
        HttpURLConnection conn
                = (HttpURLConnection) url.openConnection();
        if (conn.getResponseCode() != 200) {
            throw new IOException(conn.getResponseMessage());
        }
        InputStream in = conn.getInputStream();
        if (SdmxIO.isSaveXml()) {
            String name = System.currentTimeMillis() + ".xml";
            FileOutputStream file = new FileOutputStream(name);
            IOUtils.copy(in, file);
            in = new FileInputStream(name);
        }
        //FileOutputStream temp = new FileOutputStream("temp.xml");
        //org.apache.commons.io.IOUtils.copy(in, temp);
        //temp.close();
        //in.close();
        //in = new FileInputStream("temp.xml");
        ParseParams params = new ParseParams();
        StructureType st = SdmxIO.parseStructure(in);
        if (st == null) {
            System.out.println("St is null!");
        } else if (SdmxIO.isSaveXml()) {
            String name = System.currentTimeMillis() + "-21.xml";
            FileOutputStream file = new FileOutputStream(name);
            Sdmx21StructureWriter.write(st, file);
        }
        return st;
    }

    private StructureType retrieve2(String urlString) throws MalformedURLException, IOException, ParseException {
        Logger.getLogger("sdmx").info("ILORestServiceRegistry: retrieve " + urlString + "&" + options);
        String s = options;
        if (urlString.indexOf("?") == -1) {
            s = "?" + s;
        } else {
            s = "&" + s;
        }
        URL url = new URL(urlString + s);
        HttpURLConnection conn
                = (HttpURLConnection) url.openConnection();
        if (conn.getResponseCode() != 200) {
            throw new IOException(conn.getResponseMessage());
        }
        InputStream in = conn.getInputStream();
        //FileOutputStream temp = new FileOutputStream("temp.xml");
        //org.apache.commons.io.IOUtils.copy(in, temp);
        //temp.close();
        //in.close();
        //in = new FileInputStream("temp.xml");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ie) {
        }
        System.out.println("Parsing!");
        ParseParams params = new ParseParams();
        params.setRegistry(this);
        StructureType st = SdmxIO.parseStructure(params, in);
        if (st == null) {
            System.out.println("St is null!");
        }
        return st;
    }
    @Override
    public void query(Query q,ParseDataCallbackHandler handler) {
        if (SdmxIO.isDumpQuery()) {

        }
        String flowid = q.getFlowRef();
        String agency = q.getProviderRef();
        int geogIndex = flowid.lastIndexOf("_");;
        String geog = flowid.substring(geogIndex+1,flowid.length());
        String geography_string = "&geography="+geog;
        if( "NOGEOG".equals(geog)){
            geography_string="";
        }
        String id = flowid.substring(0,geogIndex);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q.size(); i++) {
            QueryDimension dim = q.getQueryDimension(i);
            boolean addedParam = false;
            String concept = dim.getConcept();
            List<String> params = dim.getValues();
            if (params.size() > 0) {
                addedParam = true;
                sb.append(concept + "=");
                for (int j = 0; j < params.size(); j++) {
                    sb.append(params.get(j));
                    if (j < params.size() - 1) {
                        sb.append(",");
                    }
                }
            }
            if (addedParam && i < q.size() - 1) {
                sb.append("&");
            }
            addedParam = false;
        }
        StringBuilder times = new StringBuilder();
        try {
            StructureType st = retrieve3(getServiceURL() + "/v01/dataset/" + id + "/time/def.sdmx.xml");
            CodelistType timesCL = st.getStructures().getCodelists().getCodelists().get(0);
            Date startTime = q.getQueryTime().getStartTime();
            Date endTime = q.getQueryTime().getEndTime();
            boolean comma = true;
            for (int i = 0; i < timesCL.size(); i++) {
                RegularTimePeriod rtp = TimeUtil.parseTime("", timesCL.getCode(i).getId().toString());
                if ((rtp.getStart().equals(startTime) || rtp.getStart().after(startTime)) && (rtp.getEnd().before(endTime) || rtp.getEnd().equals(endTime))) {
                    if (!comma) {
                        times.append(",");
                    }
                    times.append(timesCL.getCode(i).getId().toString());
                    comma = false;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(NOMISRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(NOMISRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            queryStream(getServiceURL() + "/v01/dataset/" + id + ".compact.sdmx.xml?" + sb.toString() + "&time=" + times.toString() + 
                    /*
                       We don't want the geography string at all, because its name clashes with a dimension name
                       this causes the nomis service to send each geography code in the message...
                       we will just specify the codes we want... and not specify the geography type.
                    */
                    //geography_string 
                    "&" + options,handler);
        } catch (IOException ex) {
            Logger.getLogger(NOMISRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(NOMISRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public DataMessage query(Query q) {
        if (SdmxIO.isDumpQuery()) {

        }
        String flowid = q.getFlowRef();
        String agency = q.getProviderRef();
        int geogIndex = flowid.lastIndexOf("_");;
        String geog = flowid.substring(geogIndex+1,flowid.length());
        String geography_string = "&geography="+geog;
        if( "NOGEOG".equals(geog)){
            geography_string="";
        }
        String id = flowid.substring(0,geogIndex);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q.size(); i++) {
            QueryDimension dim = q.getQueryDimension(i);
            boolean addedParam = false;
            String concept = dim.getConcept();
            List<String> params = dim.getValues();
            if (params.size() > 0) {
                addedParam = true;
                sb.append(concept + "=");
                for (int j = 0; j < params.size(); j++) {
                    sb.append(params.get(j));
                    if (j < params.size() - 1) {
                        sb.append(",");
                    }
                }
            }
            if (addedParam && i < q.size() - 1) {
                sb.append("&");
            }
            addedParam = false;
        }
        StringBuilder times = new StringBuilder();
        try {
            StructureType st = retrieve3(getServiceURL() + "/v01/dataset/" + id + "/time/def.sdmx.xml");
            CodelistType timesCL = st.getStructures().getCodelists().getCodelists().get(0);
            Date startTime = q.getQueryTime().getStartTime();
            Date endTime = q.getQueryTime().getEndTime();
            boolean comma = true;
            for (int i = 0; i < timesCL.size(); i++) {
                RegularTimePeriod rtp = TimeUtil.parseTime("", timesCL.getCode(i).getId().toString());
                if ((rtp.getStart().equals(startTime) || rtp.getStart().after(startTime)) && (rtp.getEnd().before(endTime) || rtp.getEnd().equals(endTime))) {
                    if (!comma) {
                        times.append(",");
                    }
                    times.append(timesCL.getCode(i).getId().toString());
                    comma = false;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(NOMISRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(NOMISRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }

        DataMessage msg = null;
        try {
            msg = queryBatch(getServiceURL() + "/v01/dataset/" + id + ".compact.sdmx.xml?" + sb.toString() + "&time=" + times.toString() + 
                    /*
                       We don't want the geography string at all, because its name clashes with a dimension name
                       this causes the nomis service to send each geography code in the message...
                       we will just specify the codes we want... and not specify the geography type.
                    */
                    //geography_string 
                    "&" + options);
        } catch (IOException ex) {
            Logger.getLogger(NOMISRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(NOMISRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }
        return msg;
    }
    public DataMessage queryBatch(String urlString) throws MalformedURLException, IOException, ParseException {
        Logger.getLogger("sdmx").log(Level.INFO, "Rest Queryable Query:" + urlString);
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(urlString);
        get.addHeader("Accept", "application/vnd.sdmx.structurespecificdata+xml;version=2.1");
        get.addHeader("User-Agent", "Sdmx-Sax");
        HttpResponse response = client.execute(get);
        InputStream in = response.getEntity().getContent();
        if (SdmxIO.isSaveXml()) {
            String name = System.currentTimeMillis() + ".xml";
            FileOutputStream file = new FileOutputStream(name);
            IOUtils.copy(in, file);
            in = new FileInputStream(name);
        }
        DataMessage msg = SdmxIO.parseData(in);
        if (msg == null) {
            System.out.println("Data is null!");
        }
        return msg;
    }
    public void queryStream(String urlString,ParseDataCallbackHandler handler) throws MalformedURLException, IOException, ParseException {
        ParseParams params = new ParseParams();
        params.setCallbackHandler(handler);
        Logger.getLogger("sdmx").log(Level.INFO, "Rest Queryable Query:" + urlString);
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(urlString);
        get.addHeader("Accept", "application/vnd.sdmx.structurespecificdata+xml;version=2.1");
        get.addHeader("User-Agent", "Sdmx-Sax");
        HttpResponse response = client.execute(get);
        InputStream in = response.getEntity().getContent();
        if (SdmxIO.isSaveXml()) {
            String name = System.currentTimeMillis() + ".xml";
            FileOutputStream file = new FileOutputStream(name);
            IOUtils.copy(in, file);
            in = new FileInputStream(name);
        }
        SdmxIO.parseDataStream(handler, in);
    }

    @Override
    public List<DataflowType> listDataflows() {
        if (dataflowList != null) {
            return dataflowList;
        }
        if (SdmxIO.getCacheDirectory()!=null&& new File(SdmxIO.getCacheDirectory(),"nomis.xml").exists()) {
            try {
                StructureType struct = SdmxIO.parseStructure(new FileInputStream(new File(SdmxIO.getCacheDirectory(),"nomis.xml")));
                this.dataflowList=struct.getStructures().getDataflows().getDataflows();
                return this.dataflowList;
            } catch (IOException ex) {
                Logger.getLogger(NOMISRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(NOMISRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
            }
            return Collections.EMPTY_LIST;
        } else {
            try {
                StructureType st = retrieve3(this.serviceURL + "/v01/dataset/def.sdmx.xml");
                List<DataStructureType> list = st.getStructures().getDataStructures().getDataStructures();
                List<DataflowType> dfs = new ArrayList<DataflowType>();
                for (DataStructureType dst : list) {
                    String cubeId = NameableType.toIDString(dst);
                    String cubeName = dst.findName("en").getText();
                    URL url = new URL(serviceURL + "/v01/dataset/" + cubeId + ".overview.xml");
                    Logger.getLogger("sdmx").log(Level.INFO, "retrieving: " + url.toString());
                    List<NOMISGeography> geogList = parseGeography(url.openStream(), cubeId, cubeName);
                    for (NOMISGeography geog : geogList) {
                        DataflowType dataFlow = new DataflowType();
                        dataFlow.setAgencyID(new NestedNCNameID((agency)));
                        dataFlow.setId(new IDType(cubeId + "_" + geog.getGeography()));
                        Name name = new Name("en", cubeName + "-" + geog.getGeographyName());
                        List<Name> names = Collections.singletonList(name);
                        dataFlow.setNames(names);
                        DataStructureReference ref = DataStructureReference.create(new NestedNCNameID(agency), dataFlow.getId(), Version.ONE);
                        dataFlow.setStructure(ref);
                        System.out.println("NOMIS Dataflow:" + NameableType.toString(dataFlow)+"(\'"+dataFlow.getId()+"\')");
                        dfs.add(dataFlow);
                    }
                    if( geogList.size()==0 ) {
                        DataflowType dataFlow = new DataflowType();
                        dataFlow.setAgencyID(new NestedNCNameID((agency)));
                        dataFlow.setId(new IDType(cubeId + "_NOGEOG"));
                        Name name = new Name("en", cubeName);
                        List<Name> names = Collections.singletonList(name);
                        dataFlow.setNames(names);
                        DataStructureReference ref = DataStructureReference.create(new NestedNCNameID(agency), dataFlow.getId(), Version.ONE);
                        dataFlow.setStructure(ref);
                        System.out.println("NOMIS Dataflow:" + NameableType.toString(dataFlow)+"(\'"+dataFlow.getId()+"\')");
                        dfs.add(dataFlow);
                    }
                }
                this.dataflowList = dfs;
                StructureType struct = new StructureType();
                StructuresType strucs = new StructuresType();
                DataflowsType dataflows = new DataflowsType();
                dataflows.setDataflows(dataflowList);
                strucs.setDataflows(dataflows);
                struct.setStructures(strucs);
                struct.setHeader(getBaseHeader());
                local.load(struct);
                ParseParams params = new ParseParams();
                SdmxIO.writeStructure("application/vnd.sdmx.structure+xml;version=2.1", struct, new FileOutputStream(new File(SdmxIO.getCacheDirectory(),"nomis.xml")));
            } catch (IOException ex) {
                Logger.getLogger(NOMISRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            } catch (ParseException ex) {
                Logger.getLogger(NOMISRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            } catch (XMLStreamException ex) {
                Logger.getLogger(NOMISRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
            }
            return dataflowList;
        }
    }

    @Override
    public DataflowType find(DataflowReference ref) {
        if (local.find(ref) != null) {
            return local.find(ref);
        }
        if (dataflowList == null) {
            listDataflows();
        }
        for (DataflowType df : dataflowList) {
            if (df.identifiesMe(ref.getMaintainableParentId())) {
                return df;
            }
        }
        return null;
    }

    /**
     * @return the agency
     */
    public String getAgencyId() {
        return agency;
    }

    /**
     * @param agency the agency to set
     */
    public void setAgencyId(String agency) {
        this.agency = agency;
    }

    /**
     * @return the serviceURL
     */
    public String getServiceURL() {
        return serviceURL;
    }

    /**
     * @param serviceURL the serviceURL to set
     */
    public void setServiceURL(String serviceURL) {
        this.serviceURL = serviceURL;
    }

    @Override
    public void clear() {
        local.clear();
    }

    @Override
    public CodeType find(CodeReference ref) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Registry getRegistry() {
        return this;
    }

    @Override
    public Repository getRepository() {
        return this;
    }

    @Override
    public ItemType find(ItemReference ref) {
        ConceptType concept = find(ConceptReference.create(ref.getAgencyId(), ref.getMaintainableParentId(), ref.getVersion(), ref.getId()));
        if (concept != null) {
            return concept;
        }
        CodeType code = find(CodeReference.create(ref.getAgencyId(), ref.getMaintainableParentId(), ref.getVersion(), ref.getId()));
        return code;

    }

    @Override
    public ItemSchemeType find(ItemSchemeReferenceBase ref) {
        ConceptSchemeType concept = find(ConceptSchemeReference.create(ref.getAgencyId(), ref.getMaintainableParentId(), ref.getVersion()));
        if (concept != null) {
            return concept;
        }
        CodelistType code = find(CodelistReference.create(ref.getAgencyId(), ref.getMaintainableParentId(), ref.getVersion()));
        return code;
    }

    @Override
    public void save(OutputStream out) throws IOException {
        local.save(out);
    }

    public BaseHeaderType getBaseHeader() {
        BaseHeaderType header = new BaseHeaderType();
        header.setId("none");
        header.setTest(false);
        SenderType sender = new SenderType();
        sender.setId(new IDType("Sdmx-Sax"));
        header.setSender(sender);
        PartyType receiver = new PartyType();
        receiver.setId(new IDType(agency));
        header.setReceivers(Collections.singletonList(receiver));
        HeaderTimeType htt = new HeaderTimeType();
        htt.setDate(DateTime.now());
        header.setPrepared(htt);
        return header;
    }

    @Override
    public List<DataStructureType> search(DataStructureReference ref) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<DataflowType> search(DataflowReference ref) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<CodeType> search(CodeReference ref) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<CodelistType> search(CodelistReference ref) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<ItemType> search(ItemReference ref) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<ItemSchemeType> search(ItemSchemeReferenceBase ref) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<ConceptType> search(ConceptReference ref) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<ConceptSchemeType> search(ConceptSchemeReference ref) {
        return Collections.EMPTY_LIST;
    }

    public static List<NOMISGeography> parseGeography(InputStream in, String cubeId, String cubeName) throws XMLStreamException {
        List<NOMISGeography> geogList = new ArrayList<NOMISGeography>();
        String tagContent = null;
        XMLInputFactory factory = XMLInputFactory.newInstance();

        XMLStreamReader reader
                = factory.createXMLStreamReader(in);
        int state = 0;
        String lastLang = null;
        while (reader.hasNext()) {
            int event = reader.next();
            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    if (reader.getLocalName().equals("Type")) {
                        NOMISGeography geog = new NOMISGeography();
                        geog.setCubeId(cubeId);
                        geog.setCubeName(cubeName);
                        geog.setGeography(reader.getAttributeValue("", "value"));
                        geog.setGeographyName(reader.getAttributeValue("", "name"));
                        geogList.add(geog);
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    break;
            }
        }
        return geogList;
    }
    public List<StructureType> getCache(){
        return this.local.getCache();
    }
}

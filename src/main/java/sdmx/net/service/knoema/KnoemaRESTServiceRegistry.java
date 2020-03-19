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
package sdmx.net.service.knoema;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import sdmx.Registry;
import sdmx.Repository;
import sdmx.Queryable;
import sdmx.SdmxIO;
import sdmx.commonreferences.CodeReference;
import sdmx.commonreferences.CodelistReference;
import sdmx.commonreferences.ConceptReference;
import sdmx.commonreferences.ConceptSchemeReference;
import sdmx.commonreferences.DataStructureReference;
import sdmx.commonreferences.DataflowReference;
import sdmx.commonreferences.IDType;
import sdmx.commonreferences.ItemReference;
import sdmx.commonreferences.ItemSchemeReference;
import sdmx.commonreferences.ItemSchemeReferenceBase;
import sdmx.commonreferences.NestedID;
import sdmx.commonreferences.NestedNCNameID;
import sdmx.commonreferences.StructureReference;
import sdmx.commonreferences.Version;
import sdmx.exception.ParseException;
import sdmx.message.DataMessage;
import sdmx.message.DataQueryMessage;
import sdmx.message.DataStructureQueryMessage;
import sdmx.message.StructureType;
import sdmx.net.LocalRegistry;
import sdmx.net.service.RESTQueryable;
import static sdmx.net.service.RESTQueryable.displayFormat;
import sdmx.querykey.Query;
import sdmx.querykey.QueryDimension;
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
import sdmx.version.common.ParseDataCallbackHandler;
import sdmx.version.common.ParseParams;
import sdmx.version.common.SOAPStrippingInputStream;
import sdmx.version.twopointone.writer.Sdmx21StructureWriter;

/**
 *
 * @author James
 */
public class KnoemaRESTServiceRegistry implements Registry,Repository,Queryable {
    public static final SimpleDateFormat displayFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    public static void main(String args[]) {
        KnoemaRESTServiceRegistry registry = new KnoemaRESTServiceRegistry("knoema", "http://knoema.com/api/1.0/sdmx");
        List<DataflowType> dfs = registry.listDataflows();
        for(DataflowType df:dfs) {
            System.out.println(NameableType.toString(df, Locale.getDefault()));
        }
    }
    private String agency = "";
    private String serviceURL = "";
    Registry local = new LocalRegistry();

    private List<DataflowType> dataflowList = null;

    public KnoemaRESTServiceRegistry(String agency, String service) {
        this.serviceURL = service;
        this.agency = agency;
        SdmxIO.setStrictRegex(false);
        //SdmxIO.setIgnoreCase(true);
    }

    public void load(StructureType struct) {
        local.load(struct);
    }

    public void unload(StructureType struct) {
        local.unload(struct);
    }

    public DataStructureType find(DataStructureReference ref) {
        DataStructureType dst = local.find(ref);
        if (dst == null) {
            try {
                StructureType st = retrieve(getServiceURL() + "/2.1/"+ref.getMaintainableParentId().toString());
                DataStructureType ds = st.getStructures().getDataStructures().getDataStructures().get(0);
                ds.setId(ref.getMaintainableParentId());
                ds.setAgencyID(ref.getAgencyId());
                load(st);
                return local.find(ref);
            } catch (MalformedURLException ex) {
                Logger.getLogger(KnoemaRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            } catch (IOException ex) {
                Logger.getLogger(KnoemaRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            } catch (ParseException ex) {
                Logger.getLogger(KnoemaRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
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
        return dst;
    }

    private StructureType retrieve(String urlString) throws MalformedURLException, IOException, ParseException {
        System.out.println("Retrieve:" + urlString);
        URL url = new URL(urlString);
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
        System.out.println("Parsing!");
        ParseParams params = new ParseParams();
        params.setRegistry(this);
        StructureType st = SdmxIO.parseStructure(params, in);
        if (st == null) {
            System.out.println("St is null!");
        } else {
            if (SdmxIO.isSaveXml()) {
                String name = System.currentTimeMillis() + "-21.xml";
                FileOutputStream file = new FileOutputStream(name);
                Sdmx21StructureWriter.write(st, file);
            }
        }
        return st;
    }
    /*
     This function retrieves and uses the local registry 
     instead of this when we call SdmxIO.parse(registry,in)
     this means that if the sdmx service sends sdmx 2.0 data structures
     the codelists dont have to be loaded.
     */

    private StructureType retrieve2(String urlString) throws MalformedURLException, IOException, ParseException {
        System.out.println("Retrieve:" + urlString);
        URL url = new URL(urlString);
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
    public List<DataflowType> listDataflows() {
        if (dataflowList != null) {
            return dataflowList;
        }
        try {
            StructureType st = retrieve2(getServiceURL());
            List<DataStructureType> dss = st.getStructures().getDataStructures().getDataStructures();
            List<DataflowType> dfs = new ArrayList<DataflowType>();
            for(DataStructureType ds:dss){
                DataflowType df = ds.asDataflow();
                dfs.add(df);
            }
            dataflowList = dfs;
        } catch (MalformedURLException ex) {
            Logger.getLogger(KnoemaRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
            //dataflowList = null;
        } catch (IOException ex) {
            Logger.getLogger(KnoemaRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
            //dataflowList = null;
        } catch (ParseException ex) {
            Logger.getLogger(KnoemaRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dataflowList;
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
    public DataflowType find(DataflowReference ref) {
        for(DataflowType df2:listDataflows()) {
            if( df2.identifiesMe(ref.getAgencyId(), ref.getMaintainableParentId(), ref.getVersion())){
                return df2;
            }
        }
        return local.find(ref);
    }

    @Override
    public CodeType find(CodeReference ref) {
        return local.find(ref);
    }

    @Override
    public ConceptType find(ConceptReference ref) {
        return local.find(ref);
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
        if( concept!=null) return concept;
        CodeType code = find(CodeReference.create(ref.getAgencyId(),ref.getMaintainableParentId(), ref.getVersion(), ref.getId()));
        return code;
        
    }

    @Override
    public ItemSchemeType find(ItemSchemeReferenceBase ref) {
        ConceptSchemeType concept = find(ConceptSchemeReference.create(ref.getAgencyId(), ref.getMaintainableParentId(), ref.getVersion()));
        if( concept!=null) return concept;
        CodelistType code = find(CodelistReference.create(ref.getAgencyId(),ref.getMaintainableParentId(), ref.getVersion()));
        return code;
    }

    @Override
    public void save(OutputStream out) throws IOException {
        local.save(out);
    }
    public void merge(){}
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
    public List<StructureType> getCache(){
        return this.local.getCache();
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
            file.flush();
            file.close();
            in = new FileInputStream(name);
        }
        DataMessage msg = SdmxIO.parseData(in);
        if (msg == null) {
            System.out.println("Data is null!");
        }
        return msg;
    }

    public void queryStream(String urlString, ParseDataCallbackHandler handler) throws MalformedURLException, IOException, ParseException {
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

    public void query(Query q, ParseDataCallbackHandler handler) {
        IDType flowid = new IDType(q.getFlowRef());
        NestedNCNameID agency = new NestedNCNameID(q.getProviderRef());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q.size(); i++) {
            QueryDimension dim = q.getQueryDimension(i);
            String concept = dim.getConcept();
            sb.append(concept+"=");
            List<String> params = dim.getValues();
            if (params.size() > 0) {
                for (int j = 0; j < params.size(); j++) {
                    sb.append(params.get(j));
                    if (j < params.size() - 1) {
                        sb.append("%2C");
                    }
                }
            }
            if (i < q.size()) {
                sb.append("&");
            }
        }
        Date startTime = q.getQueryTime().getStartTime();
        Date endTime = q.getQueryTime().getEndTime();
        try {
            this.queryStream(getServiceURL() + "/2.1/get?id=" + flowid + "&key=" + sb.toString() + "&startTime=" + displayFormat.format(startTime) + "&endTime=" + displayFormat.format(endTime), handler);
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(RESTQueryable.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(RESTQueryable.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    public DataMessage query(Query q) {
        IDType flowid = new IDType(q.getFlowRef());
        NestedNCNameID agency = new NestedNCNameID(q.getProviderRef());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q.size(); i++) {
            QueryDimension dim = q.getQueryDimension(i);
            String concept = dim.getConcept();
            List<String> params = dim.getValues();
            if (params.size() > 0) {
                for (int j = 0; j < params.size(); j++) {
                    sb.append(params.get(j));
                    if (j < params.size() - 1) {
                        sb.append("+");
                    }
                }
            }
            if (i < q.size()) {
                sb.append(".");
            }
        }
        Date startTime = q.getQueryTime().getStartTime();
        Date endTime = q.getQueryTime().getEndTime();
        try {
            return this.queryBatch(getServiceURL() + "/getdata?dataflow=" + flowid + "&key=" + sb.toString() + "&startTime=" + displayFormat.format(startTime) + "&endTime=" + displayFormat.format(endTime));
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(RESTQueryable.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(RESTQueryable.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        return null;
    }
}

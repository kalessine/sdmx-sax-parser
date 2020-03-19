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
package sdmx.net.service.ons;

import java.io.File;
import sdmx.net.service.nomis.*;
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
import org.xml.sax.SAXException;
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
import sdmx.structure.datastructure.AttributeType;
import sdmx.structure.datastructure.DataStructureType;
import sdmx.structure.datastructure.DimensionType;
import sdmx.structure.datastructure.PrimaryMeasure;
import sdmx.structure.datastructure.TimeDimensionType;
import sdmx.util.time.TimeUtil;
import sdmx.version.common.ParseDataCallbackHandler;
import sdmx.version.common.ParseParams;
import sdmx.version.common.SOAPStrippingInputStream;
import sdmx.version.twopointone.writer.Sdmx21StructureWriter;
import sdmx.version.twopointzero.Sdmx20QueryWriter;
import sdmx.xml.DateTime;

/**
 *
 * @author James
 */
public class ONSRESTServiceRegistry implements Registry, Repository, Queryable {

    public static void main(String args[]) throws IOException {
        ONSRESTServiceRegistry registry = new ONSRESTServiceRegistry("ONS", "http://data.ons.gov.uk/ons/api/data", "apikey=doFKbcgLtj");
        List<DataflowType> list = registry.listDataflows();
        //DataStructureReference ref = DataStructureReference.create(new NestedNCNameID("ONS"), new IDType("Social_SAPEDE_2013HEALTHH"), Version.ONE);
        //DataStructureType dst = registry.find(ref);
        //dst.dump();
        DataStructureType dst = registry.find(list.get(0).getStructure());
        dst.dump();
    }
    private String agency = "";
    private String serviceURL = "";
    private String options = "";
    Registry local = new LocalRegistry();

    private List<DataflowType> dataflowList = null;

    public ONSRESTServiceRegistry(String agency, String service, String options) {
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
                String id = ref.getMaintainableParentId().toString();
                int firstUnderscore = id.indexOf("_");
                String context = id.substring(0, firstUnderscore);
                int lastUnderscore = id.lastIndexOf("_");
                String sid = id.substring(firstUnderscore + 1, lastUnderscore);
                String geography = id.substring(lastUnderscore + 1, id.length());
                StructureType st = retrieve(getServiceURL() + "/dataset/" + sid + "/dsd.xml?context=" + context + "&geog=" + geography);
                // Emergency On-set Rewrite!!!!
                st.getStructures().getDataStructures().getDataStructures().get(0).setId(ref.getMaintainableParentId());
                DataStructureType dst2 = st.find(ref);
                for (int i = 0; i < dst2.getDataStructureComponents().getDimensionList().size(); i++) {
                    DimensionType dim = dst2.getDataStructureComponents().getDimensionList().getDimension(i);
                    ItemSchemeReferenceBase cref = dim.getLocalRepresentation().getEnumeration();
                    IDType mid = cref.getMaintainableParentId();
                    IDType nid = new IDType(context + "_" + mid.toString());
                    dim.getLocalRepresentation().setEnumeration(ItemSchemeReference.create(cref.getAgencyId(), nid, cref.getVersion()));
                }
                for (int i = 0; i < dst2.getDataStructureComponents().getAttributeList().size(); i++) {
                    AttributeType dim = dst2.getDataStructureComponents().getAttributeList().getAttribute(i);
                    ItemSchemeReferenceBase cref = dim.getLocalRepresentation().getEnumeration();
                    IDType mid = cref.getMaintainableParentId();
                    IDType nid = new IDType(context + "_" + mid.toString());
                    dim.getLocalRepresentation().setEnumeration(ItemSchemeReference.create(cref.getAgencyId(), nid, cref.getVersion()));
                }
                if (dst2.getDataStructureComponents().getDimensionList().getTimeDimension() != null) {
                    TimeDimensionType dim = dst2.getDataStructureComponents().getDimensionList().getTimeDimension();
                    ItemSchemeReferenceBase cref = dim.getLocalRepresentation().getEnumeration();
                    IDType mid = cref.getMaintainableParentId();
                    IDType nid = new IDType(context + "_" + mid.toString());
                    dim.getLocalRepresentation().setEnumeration(ItemSchemeReference.create(cref.getAgencyId(), nid, cref.getVersion()));
                }
                if (dst2.getDataStructureComponents().getMeasureList().getPrimaryMeasure()!= null) {
                    PrimaryMeasure dim = dst2.getDataStructureComponents().getMeasureList().getPrimaryMeasure();
                    ItemSchemeReferenceBase cref = dim.getLocalRepresentation().getEnumeration();
                    IDType mid = cref.getMaintainableParentId();
                    IDType nid = new IDType(context + "_" + mid.toString());
                    dim.getLocalRepresentation().setEnumeration(ItemSchemeReference.create(cref.getAgencyId(), nid, cref.getVersion()));
                }
                dst2.dump();
                load(st);
                return local.find(ref);
            } catch (MalformedURLException ex) {
                Logger.getLogger(ONSRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            } catch (IOException ex) {
                Logger.getLogger(ONSRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            } catch (ParseException ex) {
                Logger.getLogger(ONSRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
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
            String id = ref.getMaintainableParentId().toString();
            int firstUnderscore = id.indexOf("_");
            String context = id.substring(0, firstUnderscore);
            String mid = id.substring(firstUnderscore + 1, id.length());
            try {
                StructureType st = retrieve(getServiceURL() + "/v01/codelist/" + ref.getMaintainableParentId() + ".def.sdmx.xml?context=" + context + "&" + this.options);
                for (int i = 0; i < st.getStructures().getCodelists().getCodelists().size(); i++) {
                    IDType cid = st.getStructures().getCodelists().getCodelists().get(i).getId();
                    IDType nid = new IDType(context + "_" + cid.toString());
                    st.getStructures().getCodelists().getCodelists().get(i).setId(nid);
                }
                load(st);
                return local.find(ref);
            } catch (MalformedURLException ex) {
                Logger.getLogger(ONSRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ONSRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(ONSRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
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
            s = "?" + s;
        } else {
            s = "&" + s;
        }
        URL url = new URL(urlString + s);
        HttpURLConnection conn
                = (HttpURLConnection) url.openConnection();
        conn.addRequestProperty("Accept", "text/html, application/xhtml+xml, image/jxr, */*");
        conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.135 Safari/537.36 Edge/12.10240");
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

    private InputStream retrieve3(String urlString) throws MalformedURLException, IOException, ParseException {
        Logger.getLogger("sdmx").info("ONSRestServiceRegistry: retrieve3 " + urlString);
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
        return in;
    }

    public DataMessage query(ParseParams params, String urlString) throws MalformedURLException, IOException, ParseException {
        Logger.getLogger("sdmx").info("ILORestServiceRegistry: query " + urlString);
        String s = options;
        if (urlString.indexOf("?") == -1) {
            s = "?" + s;
        } else {
            s = "&" + s;
        }
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(urlString + s);
        get.addHeader("Accept", "text/html, application/xhtml+xml, image/jxr, */*");
        get.addHeader("User-Agent", "Sdmx-Sax");
        HttpResponse response = client.execute(get);
        /*
         URL url = new URL(urlString);
         HttpURLConnection conn
         = (HttpURLConnection) url.openConnection();
         //if (conn.getResponseCode() != 200) {
         //    return null;
         //}
         conn.setDoInput(true);
         conn.setDoOutput(false);
         conn.addRequestProperty("Accept", "application/vnd.sdmx.structurespecificdata+xml;version=2.1");
         conn.addRequestProperty("User-Agent", "Sdmx-Sax");
         conn.connect();
         InputStream in = conn.getInputStream();
         */
        InputStream in = response.getEntity().getContent();
        if (SdmxIO.isSaveXml()) {
            String name = System.currentTimeMillis() + ".xml";
            FileOutputStream file = new FileOutputStream(name);
            IOUtils.copy(in, file);
            in = new FileInputStream(name);
        }
        System.out.println("Parsing!");
        DataMessage msg = SdmxIO.parseData(in);
        if (msg == null) {
            System.out.println("Data is null!");
        }
        return msg;
    }
    /*
     This function retrieves and uses the local registry 
     instead of this when we call SdmxIO.parse(registry,in)
     this means that if the sdmx service sends sdmx 2.0 data structures
     the codelists dont have to be loaded.
     */

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

    public DataMessage query(ParseParams pparams, DataQueryMessage message) {
        if (SdmxIO.isDumpQuery()) {

        }
        IDType flowid = message.getQuery().getDataWhere().getAnd().get(0).getDataflow().get(0).getMaintainableParentId();
        String id = flowid.toString();
        int firstUnderscore = id.indexOf("_");
        String context = id.substring(0, firstUnderscore);
        int lastUnderscore = id.lastIndexOf("_");
        String sid = id.substring(firstUnderscore + 1, lastUnderscore);
        String geography = id.substring(lastUnderscore + 1, id.length());
        NestedNCNameID agency = new NestedNCNameID(this.getAgencyId());
        DataStructureType dst = null;
        if (dataflowList == null) {
            listDataflows();
        }
        for (int i = 0; i < dataflowList.size(); i++) {
            if (dataflowList.get(i).getId().equals(flowid)) {
                dst = find(dataflowList.get(i).getStructure());
            }
        }
        DataStructureType structure = dst;
        StringBuilder q = new StringBuilder();
        for (int i = 0; i < structure.getDataStructureComponents().getDimensionList().size(); i++) {
            DimensionType dim = structure.getDataStructureComponents().getDimensionList().getDimension(i);
            boolean addedParam = false;
            String concept = dim.getConceptIdentity().getId().toString();
            List<String> params = message.getQuery().getDataWhere().getAnd().get(0).getDimensionParameters(concept);
            if (params.size() > 0) {
                addedParam = true;
                q.append("dm/" + concept + "=");
                for (int j = 0; j < params.size(); j++) {
                    q.append(params.get(j));
                    if (j < params.size() - 1) {
                        q.append(",");
                    }
                }
            }
            if (addedParam && i < structure.getDataStructureComponents().getDimensionList().size() - 1) {
                q.append("&");
            }
            addedParam = false;
        }
        DataMessage msg = null;
        try {
            // http://www.ons.gov.uk/ons/api/data/dataset/AF001EW.xml?apikey=doFKbcgLtj&context=Census&apikey=&geog=2011STATH&totals=false&dm/2011STATH=K04000001
            msg = query(pparams, getServiceURL() + "/dataset/" + sid + ".xml?" + options + "&context=" + context + "&geog=" + geography + "&totals=false" + q.toString());
        } catch (IOException ex) {
            Logger.getLogger(NOMISRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(NOMISRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }
        return msg;
    }

    @Override
    public List<DataflowType> listDataflows() {
        if (dataflowList != null) {
            return dataflowList;
        }
        if (new File("ons.xml").exists()) {
            try {
                StructureType struct = SdmxIO.parseStructure(new FileInputStream("ons.xml"));
                this.dataflowList = struct.getStructures().getDataflows().getDataflows();
                return struct.getStructures().getDataflows().getDataflows();
            } catch (IOException ex) {
                Logger.getLogger(ONSRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(ONSRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
            }
            return Collections.EMPTY_LIST;
        } else {
            List<String> contexts = new ArrayList<String>();
            contexts.add("Census");
// This is supposed to work but it doesn't :(        
//contexts.add("Socio-Economic");

            contexts.add("Economic");
            contexts.add("Social");
            List<ONSCube> bigList = new ArrayList<ONSCube>();
            for (String s : contexts) {
                try {
                    InputStream in = retrieve3(serviceURL + "/collections.xml?context=" + s + "&" + options);
                    Name name = new Name("en", s);
                    List<Name> names = new ArrayList<Name>();
                    names.add(name);
                    List<ONSCube> list = parseCollections(in, s, names);
                    bigList.addAll(list);
                } catch (IOException ex) {
                    Logger.getLogger(ONSRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(ONSRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
                } catch (XMLStreamException ex) {
                    Logger.getLogger(ONSRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            List<ONSCube> bigList2 = new ArrayList<ONSCube>();
            for (ONSCube cube : bigList) {
                try {
                    InputStream in = retrieve3(serviceURL + "/hierarchies/" + cube.getId() + ".xml?context=" + cube.getContext() + "&" + options);
                    List<ONSCube> list = parseGeography(in, cube);
                    bigList2.addAll(list);
                } catch (IOException ex) {
                    Logger.getLogger(ONSRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(ONSRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
                } catch (XMLStreamException ex) {
                    Logger.getLogger(ONSRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            dataflowList = new ArrayList<DataflowType>();
            for (ONSCube cube : bigList2) {
                DataflowType df = new DataflowType();
                df.setId(new IDType(cube.getMainId()));
                df.setNames(cube.getMainNames());
                DataStructureReference struct = DataStructureReference.create(new NestedNCNameID(agency), df.getId(), Version.ONE);
                df.setStructure(struct);
                df.setAgencyID(new NestedNCNameID(agency));
                dataflowList.add(df);
            }
            StructureType struct = new StructureType();
            struct.setHeader(getBaseHeader());
            StructuresType structs = new StructuresType();
            DataflowsType dfs = new DataflowsType();
            dfs.setDataflows(dataflowList);
            structs.setDataflows(dfs);
            struct.setStructures(structs);
            try {
                Sdmx21StructureWriter.write(struct, new FileOutputStream("ons.xml"));
            } catch (IOException ex) {
                Logger.getLogger(ONSRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
            }
            return dataflowList;
        }
    }

    @Override
    public DataflowType find(DataflowReference ref
    ) {
        DataflowType dst = local.find(ref);
        if (dst == null) {
            try {
                StructureType st = retrieve(getServiceURL() + "/dataflow/" + ref.getAgencyId().toString() + "/" + ref.getMaintainableParentId().toString() + "/" + (ref.getVersion() != null ? ref.getVersion().toString() : "latest"));
                load(st);
                return local.find(ref);
            } catch (MalformedURLException ex) {
                Logger.getLogger(ONSRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ONSRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(ONSRESTServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            }
        }
        return dst;
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

    public static final int STATE_NOTHING = 0;
    public static final int STATE_ID = 1;
    public static final int STATE_NAME = 2;

    public static List<ONSCube> parseCollections(InputStream in, String context, List<Name> contextNames) throws XMLStreamException {
        List<ONSCube> cubeList = new ArrayList<ONSCube>();
        ONSCube currCube = null;
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
                    if (reader.getLocalName().equals("collection")) {
                        currCube = new ONSCube();
                        currCube.setContext(context);
                        currCube.setContextNames(contextNames);
                    }
                    if (reader.getLocalName().equals("id")) {
                        state = STATE_ID;
                    }
                    if (reader.getLocalName().equals("name")) {
                        state = STATE_NAME;
                        lastLang = reader.getAttributeValue(0);
                    }
                    break;
                case XMLStreamConstants.CHARACTERS:
                    if (state == STATE_ID) {
                        currCube.setId(reader.getText().trim());
                    }
                    if (state == STATE_NAME && currCube != null) {
                        List<Name> names = currCube.getIdNames();
                        if (names == null) {
                            names = new ArrayList<Name>();
                        }
                        Name n = new Name(lastLang, reader.getText().trim());
                        names.add(n);
                        //System.out.println("Added name:"+n.getLang()+":"+n.getText());
                        currCube.setIdNames(names);
                        state = STATE_NOTHING;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    if (reader.getLocalName().equals("collection")) {
                        cubeList.add(currCube);
                        currCube = null;
                    }
                    if (reader.getLocalName().equals("name")) {
                        lastLang = null;
                        state = STATE_NOTHING;
                    }
                    break;
            }
        }
        return cubeList;
    }

    public static List<ONSCube> parseGeography(InputStream in, ONSCube cube) throws XMLStreamException {
        List<ONSCube> cubeList = new ArrayList<ONSCube>();
        ONSCube currCube = null;
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
                    if (reader.getLocalName().equals("geographicalHierarchy")) {
                        currCube = new ONSCube();
                        currCube.setContext(cube.getContext());
                        currCube.setContextNames(cube.getContextNames());
                        currCube.setId(cube.getId());
                        currCube.setIdNames(cube.getIdNames());
                    }
                    if (reader.getLocalName().equals("id")) {
                        state = STATE_ID;
                    }
                    if (reader.getLocalName().equals("name")) {
                        state = STATE_NAME;
                        lastLang = reader.getAttributeValue(0);
                    }
                    break;
                case XMLStreamConstants.CHARACTERS:
                    if (state == STATE_ID && currCube != null) {
                        currCube.setGeography(reader.getText().trim());
                    }
                    if (state == STATE_NAME && currCube != null) {
                        List<Name> names = currCube.getGeographyNames();
                        if (names == null) {
                            names = new ArrayList<Name>();
                        }
                        Name n = new Name(lastLang, reader.getText().trim());
                        names.add(n);
                        //System.out.println("Added name:"+n.getLang()+":"+n.getText());
                        currCube.setGeographyNames(names);
                        state = STATE_NOTHING;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    if (reader.getLocalName().equals("geographicalHierarchy")) {
                        cubeList.add(currCube);
                        currCube = null;
                    }
                    if (reader.getLocalName().equals("name")) {
                        lastLang = null;
                        state = STATE_NOTHING;
                    }
                    break;
            }
        }
        return cubeList;
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

    public List<StructureType> getCache() {
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
        String id = q.getFlowRef();
        int firstUnderscore = id.indexOf("_");
        String context = id.substring(0, firstUnderscore);
        int lastUnderscore = id.lastIndexOf("_");
        String sid = id.substring(firstUnderscore + 1, lastUnderscore);
        String geography = id.substring(lastUnderscore + 1, id.length());
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
            if (i < q.getQuerySize()) {
                sb.append(".");
            }
        }
        Date startTime = q.getQueryTime().getStartTime();
        Date endTime = q.getQueryTime().getEndTime();
        try {
            this.queryStream(getServiceURL() + "/dataset/" + sid + ".xml?" + options + "&context=" + context + "&geog=" + geography + "&totals=false" + sb.toString(), handler);
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(RESTQueryable.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(RESTQueryable.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    public DataMessage query(Query q) {
        String id = q.getFlowRef();
        int firstUnderscore = id.indexOf("_");
        String context = id.substring(0, firstUnderscore);
        int lastUnderscore = id.lastIndexOf("_");
        String sid = id.substring(firstUnderscore + 1, lastUnderscore);
        String geography = id.substring(lastUnderscore + 1, id.length());
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
            if (i < q.getQuerySize()) {
                sb.append(".");
            }
        }
        Date startTime = q.getQueryTime().getStartTime();
        Date endTime = q.getQueryTime().getEndTime();
        try {
            return this.queryBatch(getServiceURL() + "/dataset/" + sid + ".xml?" + options + "&context=" + context + "&geog=" + geography + "&totals=false" + sb.toString());
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

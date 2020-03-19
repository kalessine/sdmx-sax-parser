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
package sdmx.net.service.cepal;

import sdmx.net.service.cepal.CEPALItem;
import sdmx.net.*;
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
import java.util.List;
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
import sdmx.Registry;
import sdmx.Repository;
import sdmx.Queryable;
import sdmx.SdmxIO;
import sdmx.common.Name;
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
import sdmx.net.service.nomis.NOMISGeography;
import sdmx.structure.base.ItemSchemeType;
import sdmx.structure.base.ItemType;
import sdmx.structure.base.MaintainableType;
import sdmx.structure.codelist.CodeType;
import sdmx.structure.codelist.CodelistType;
import sdmx.structure.concept.ConceptSchemeType;
import sdmx.structure.concept.ConceptType;
import sdmx.structure.dataflow.DataflowType;
import sdmx.structure.datastructure.DataStructureType;
import sdmx.structure.datastructure.DimensionType;
import sdmx.version.common.ParseParams;
import sdmx.version.common.SOAPStrippingInputStream;
import sdmx.version.twopointone.writer.Sdmx21StructureWriter;

/**
 *
 * @author James
 */
/*
    God i wish the staff at CEPAL returned my emails....
    i've sent 3.. and they just go into a black hole and never return...
    i guess they really aren't interested in anyone using their API :(
*/
public class CEPALQueryable implements Queryable, Registry, Repository {

    public static void main(String args[]) {
        CEPALQueryable q = new CEPALQueryable("CEPAL", "http://interwp.cepal.org/sisgen/ws/cepalstat/", "english");
        List<DataflowType> dfs = q.getRegistry().listDataflows();
        for (int i = 0; i < dfs.size(); i++) {
            System.out.println(dfs.get(i).getName());
        }
    }
    private boolean english = true;
    private String agency = "";
    private String serviceURL = "";
    Registry local = new LocalRegistry();

    private List<DataflowType> dataflowList = null;

    public CEPALQueryable(String agency, String service, String options) {
        this.serviceURL = service;
        this.agency = agency;
        if (options.equals("english")) {
            this.english = true;
        } else {
            this.english = false;
        }
    }

    public Registry getRegistry() {
        return this;
    }

    public Repository getRepository() {
        return this;
    }

    public void load(StructureType struct) {
        local.load(struct);
    }

    public void unload(StructureType struct) {
        local.unload(struct);
    }

    private StructureType retrieve(String urlString) throws MalformedURLException, IOException, ParseException {
        Logger.getLogger("sdmx").log(Level.INFO, "Rest Queryable Retrieve:" + urlString);
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

    public DataMessage query(ParseParams pparams, String urlString) throws MalformedURLException, IOException, ParseException {
        Logger.getLogger("sdmx").log(Level.INFO, "Rest Queryable Query:" + urlString);
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(urlString);
        get.addHeader("Accept", "application/vnd.sdmx.structurespecificdata+xml;version=2.1");
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
        DataMessage msg = SdmxIO.parseData(pparams, in);
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
    /*
     private StructureType retrieve(String urlString) throws MalformedURLException, IOException, ParseException {
     Logger.getLogger("sdmx").log(Level.INFO, "Rest Queryable Retrieve:" + urlString);
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
     StructureType st = SdmxIO.parseStructure(local, in);
     if (st == null) {
     System.out.println("St is null!");
     }
     return st;
     }*/

    @Override
    public DataMessage query(ParseParams pparams, DataQueryMessage message) {
        Logger.getLogger("sdmx").log(Level.INFO, "Rest Queryable Query: DataQueryMessage" + message);
        IDType flowid = message.getQuery().getDataWhere().getAnd().get(0).getDataflow().get(0).getMaintainableParentId();
        NestedNCNameID agency = new NestedNCNameID(this.getAgencyId());
        DataStructureType dst = null;
        DataflowReference dfref = DataflowReference.create(agency, flowid, null);
        DataflowType df = find(dfref);
        if (df == null) {
            listDataflows();
            for (int i = 0; i < dataflowList.size(); i++) {
                if (dataflowList.get(i).getId().equals(flowid)) {
                    DataStructureReference ref = DataStructureReference.create(dataflowList.get(i).getStructure().getAgencyId(), dataflowList.get(i).getStructure().getMaintainableParentId(), dataflowList.get(i).getStructure().getMaintainedParentVersion());
                    dst = find(ref);
                }
            }
        } else {
            dst = find(df.getStructure());
        }
        DataStructureType structure = dst;
        StringBuilder q = new StringBuilder();
        for (int i = 0; i < structure.getDataStructureComponents().getDimensionList().size(); i++) {
            DimensionType dim = structure.getDataStructureComponents().getDimensionList().getDimension(i);
            String concept = dim.getConceptIdentity().getId().toString();
            List<String> params = message.getQuery().getDataWhere().getAnd().get(0).getDimensionParameters(concept);
            if (i == 0) {
                q.append(concept + "=");
            } else {
                q.append("&" + concept + "=");
            }
            if (params.size() > 0) {
                for (int j = 0; j < params.size(); j++) {
                    q.append(params.get(j));
                    if (j < params.size() - 1) {
                        q.append(",");
                    }
                }
            }
        }
        DataMessage msg = null;
        if (dst.getDataStructureComponents().getDimensionList().getTimeDimension() != null) {
            String startTime = message.getQuery().getDataWhere().getAnd().get(0).getTimeDimensionValue().get(0).getStart().toString();
            String endTime = message.getQuery().getDataWhere().getAnd().get(0).getTimeDimensionValue().get(0).getEnd().toString();

            try {
                msg = query(pparams, getServiceURL() + "sdmx_dataset.asp" + "?IdIndicator=" + df.getId().toString() + "&language=" + (this.english ? "english" : "spanish"));
            } catch (IOException ex) {
                Logger.getLogger("sdmx").log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger("sdmx").log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                msg = query(pparams, getServiceURL() + "sdmx_dataset.asp" + "?IdIndicator=" + df.getId().toString() + "&language=" + (this.english ? "english" : "spanish"));
            } catch (IOException ex) {
                Logger.getLogger("sdmx").log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger("sdmx").log(Level.SEVERE, null, ex);
            }
        }

        return msg;
    }

    @Override
    public List<DataflowType> listDataflows() {
        if (this.dataflowList != null) {
            return this.dataflowList;
        }
        InputStream in = null;
        try {
            Logger.getLogger("sdmx").log(Level.FINE, "Rest Queryable listDataflows():");
            if (dataflowList != null) {
                return dataflowList;
            }
            URL url = null;
            try {
                url = new URL(serviceURL + "getThematicTree.asp" + (this.english ? "?language=english" : "?language=spanish"));
            } catch (MalformedURLException ex) {
                Logger.getLogger(CEPALQueryable.class.getName()).log(Level.SEVERE, null, ex);
            }
            in = url.openStream();
            List<CEPALItem> list = null;
            try {
                list = parseCEPALItem(in);
            } catch (XMLStreamException ex) {
                Logger.getLogger(CEPALQueryable.class.getName()).log(Level.SEVERE, null, ex);
            }
            dataflowList = new ArrayList<DataflowType>();
            for (CEPALItem ci : list) {
                DataflowType df = new DataflowType();
                Name name = new Name("en", ci.getName());
                List<Name> names = Collections.singletonList(name);
                df.setNames(names);
                df.setAgencyID(new NestedNCNameID(this.agency));
                df.setId(new IDType(ci.getIdIndicator()));
                DataStructureReference ref = DataStructureReference.create(df.getAgencyID(), df.getId(), Version.ONE);
                df.setStructure(ref);
                dataflowList.add(df);
            }
            return dataflowList;
        } catch (IOException ex) {
            Logger.getLogger(CEPALQueryable.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(CEPALQueryable.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return this.dataflowList;
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
    public DataStructureType find(DataStructureReference ref) {
        Logger.getLogger("sdmx").log(Level.FINE, "RESTQueryable find(DataStructureReference:" + ref.getAgencyId() + ":" + ref.getMaintainableParentId() + ":" + ref.getVersion());
        DataStructureType dst = local.find(ref);
        if (dst == null) {
            try {
                StructureType st = retrieve(getServiceURL() + "sdmx_structure.asp?idIndicator=" + ref.getMaintainableParentId().toString() + (this.english ? "&language=english" : "&language=spanish"));
                st.getStructures().getDataStructures().getDataStructures().get(0).setId(ref.getMaintainableParentId());
                load(st);
                return local.find(ref);
            } catch (MalformedURLException ex) {
                Logger.getLogger("sdmx").log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger("sdmx").log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger("sdmx").log(Level.SEVERE, null, ex);
            }
        }
        return dst;
    }

    @Override
    public DataflowType find(DataflowReference ref) {
        Logger.getLogger("sdmx").log(Level.FINE, "RESTQueryable find(DataflowReference:" + ref.getAgencyId() + ":" + ref.getMaintainableParentId() + ":" + ref.getVersion());
        List<DataflowType> dataflows = this.listDataflows();
        for (DataflowType df : dataflows) {
            if (ref.getMaintainableParentId().equals(df.getId())) {
                return df;
            }
        }
        return null;
    }

    @Override
    public CodeType find(CodeReference ref) {
        Logger.getLogger("sdmx").log(Level.FINE, "RESTQueryable find(CodeReference:" + ref.getAgencyId() + ":" + ref.getMaintainableParentId() + ":" + ref.getVersion());
        CodeType dst = local.find(ref);
        if (dst == null) {
            try {
                StructureType st = retrieve(getServiceURL() + "/codelist/" + ref.getAgencyId().toString() + "/" + ref.getMaintainableParentId().toString() + ref.getVersion() != null ? "/" + ref.getVersion().toString() : "/latest");
                load(st);
                return local.find(ref);
            } catch (MalformedURLException ex) {
                Logger.getLogger("sdmx").log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger("sdmx").log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger("sdmx").log(Level.SEVERE, null, ex);
            }
        }
        return dst;

    }

    @Override
    public CodelistType find(CodelistReference ref) {
        Logger.getLogger("sdmx").log(Level.FINE, "RESTQueryable find(CodelistReference:" + ref.getAgencyId() + ":" + ref.getMaintainableParentId() + ":" + ref.getVersion());
        CodelistType dst = local.find(ref);
        if (dst == null) {
            try {
                StructureType st = retrieve(getServiceURL() + "/codelist/" + ref.getAgencyId().toString() + "/" + ref.getMaintainableParentId().toString() + (ref.getVersion() != null ? "/" + ref.getVersion().toString() : "/latest"));
                load(st);
                return local.find(ref);
            } catch (MalformedURLException ex) {
                Logger.getLogger("sdmx").log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger("sdmx").log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger("sdmx").log(Level.SEVERE, null, ex);
            }
        }
        return dst;
    }

    @Override
    public ConceptType find(ConceptReference ref) {
        ConceptSchemeReference ref2 = ConceptSchemeReference.create(ref.getAgencyId(), ref.getMaintainableParentId(), ref.getVersion());
        ConceptSchemeType cs = find(ref2);
        return cs.findConcept(ref.getId());
    }

    @Override
    public ConceptSchemeType find(ConceptSchemeReference ref) {
        Logger.getLogger("sdmx").log(Level.FINE, "RESTQueryable find(ConceptSchemeReference:" + ref.getAgencyId() + ":" + ref.getMaintainableParentId() + ":" + ref.getVersion());
        ConceptSchemeType dst = local.find(ref);
        if (dst == null) {
            try {
                StructureType st = retrieve(getServiceURL() + "/conceptscheme/" + ref.getAgencyId().toString() + "/" + ref.getMaintainableParentId().toString() + "/" + ref.getVersion().toString());
                load(st);
                return local.find(ref);
            } catch (MalformedURLException ex) {
                Logger.getLogger("sdmx").log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger("sdmx").log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger("sdmx").log(Level.SEVERE, null, ex);
            }
        }
        return dst;
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

    public void merge() {
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

    public static List<CEPALItem> parseCEPALItem(InputStream in) throws XMLStreamException {
        List<CEPALItem> itemList = new ArrayList<CEPALItem>();
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
                    if (reader.getLocalName().equals("item") && reader.getAttributeValue("", "name") != null && reader.getAttributeValue("", "idIndicator") != null) {
                        String name = reader.getAttributeValue("", "name");
                        String idIndicator = reader.getAttributeValue("", "idIndicator");
                        CEPALItem item = new CEPALItem(name, idIndicator);
                        itemList.add(item);
                        //System.out.println("Name="+name+":"+idIndicator);
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    break;
            }
        }
        return itemList;
    }
    public List<StructureType> getCache(){
        return this.local.getCache();
    }    
}

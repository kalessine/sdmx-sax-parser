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
package sdmx.net.list;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sdmx.Registry;
import sdmx.Queryable;

/**
 *
 * @author James
 */
public class DataProvider {
    public static final int TYPE_SDW = 0;
    public static final int TYPE_NSI = 1;
    public static final int TYPE_REST = 2;
    public static final int TYPE_OPENSDMX = 3;
    public static final int TYPE_ILO = 4;
    public static final int TYPE_KNOEMA = 5;
    public static final int TYPE_NOMIS = 6;
    public static final int TYPE_ONS = 7;
    public static final int TYPE_INSEE = 8;
    public static final int TYPE_CEPAL = 9;
    
    
    private static final List<DataProvider> LIST = new ArrayList<DataProvider>();
    static{
        try {
            LIST.add(new SDWDataProvider(1,"ABS","http://stat.data.abs.gov.au/sdmxws/sdmx.asmx","http://stats.oecd.org/OECDStatWS/SDMX/",                    "Based on Australian Bureau of Statistics data","Based on Australian Bureau of Statistics data"));
            LIST.add(new SDWDataProvider(2,"I","http://dati.istat.it/SDMXWS/sdmx.asmx","http://stats.oecd.org/OECDStatWS/SDMX/",                        "Based on Italian National Institute of Statistics data","Based on Italian National Institute of Statistics data"));
            LIST.add(new SDWDataProvider(3,"OECD","http://stats.oecd.org/SDMXWS/sdmx.asmx","http://stats.oecd.org/OECDStatWS/SDMX/",                    "Based on OECD.Stat data","Based on OECD.Stat data"));
            LIST.add(new IMFDataProvider(4,"IMF"," http://dataservices.imf.org/sdmx21/SDMX_Web_Service21.asmx","http://stats.imf.org/DotStatWS/SDMX/",              "Based on International Monetary Fund data","Based on International Monetary Fund data"));
            LIST.add(new RESTDataProvider(5,"ESTAT","http://www.ec.europa.eu/eurostat/SDMX/diss-web/rest",                                              "Based on Eurostat data","Based on Eurostat data"));
            LIST.add(new RESTDataProvider(6,"ECB","http://a-sdw-wsrest.ecb.europa.eu/service",                                                          "Based on European Central Bank data","Based on European Central Bank data"));
            LIST.add(new NSIDataProvider(7,"UIS","http://data.un.org/ws/NSIStdV20Service.asmx","http://ec.europa.eu/eurostat/sri/service/2.0",          "Based on UNESCO Institute for Statistics data","Based on UNESCO Institute for Statistics data"));
            LIST.add(new NSIDataProvider(8,"INEGI","http://www.snieg.mx/opendata/NSIStdV20Service.asmx","http://ec.europa.eu/eurostat/sri/service/2.0", "Based on Instituto Nacional de Estadística y Geografía data","Based on Instituto Nacional de Estadística y Geografía data"));
            //LIST.add(new OpenSDMXDataProvider(9,"FAO","http://data.fao.org/sdmx",                                                                       "Based on Food and Agriculture Organisation of the United Nations data","Based on Food and Agriculture Organisation of the United Nations data"));
            LIST.add(new ILODataProvider(10,"ILO","http://www.ilo.org/ilostat/sdmx/ws/rest",                                                             "Based on International Labour Organisation data","Based on International Labour Organisation data"));
            LIST.add(new KnoemaDataProvider(11,"Knoema","http://knoema.com/api/1.0/sdmx",                                                               "Based on data provided by Knoema(various sources)","Based on data provided by Knoema(various sources)"));
            LIST.add(new KnoemaDataProvider(12,"AfDB","http://opendataforafrica.org/api/1.0/sdmx",                                                      "Based on African Development Bank Group data(www.opendataforafrica.org)","Based on African Development Bank Group data(www.opendataforafrica.org)"));
            //LIST.add(new SDWDataProvider(13,"NBB","http://stat.nbb.be/sdmxws/sdmx.asmx","http://stats.oecd.org/OECDStatWS/SDMX/",              "Based on National Bank of Belgium data","Based on National Bank of Belgium data"));
            LIST.add(new SDWDataProvider(14,"UKDS","https://stats.ukdataservice.ac.uk/sdmxws/sdmx.asmx","http://stats.oecd.org/OECDStatWS/SDMX/",              "Based on United Kingdom Data Service data","Based on United Kingdom Data Service data"));
            LIST.add(new NomisDataProvider(15,"NOMIS","http://www.nomisweb.co.uk/api","uid=0xad235cca367972d98bd642ef04ea259da5de264f","Based on NOMIS Data Service data","Based on NOMIS Data Service data"));
            LIST.add(new INSEERESTDataProvider(16,"FR1", "http://www.bdm.insee.fr/series/sdmx", "Based on INSEE.fr Data", "Based on INSEE.fr data"));
            //LIST.add(new CEPALDataProvider(17,"CEPAL", "http://interwp.cepal.org/sisgen/ws/cepalstat/","english", "CEPAL English", "CEPAL English"));
            //LIST.add(new CEPALDataProvider(18,"CEPAL", "http://interwp.cepal.org/sisgen/ws/cepalstat/","spanish", "CEPAL Spanish", "CEPAL Spanish"));
            //LIST.add(new ONSDataProvider(19,"ONS","http://data.ons.gov.uk/ons/api/data", "apikey=doFKbcgLtj","Based on ONS Data Service data","Based on ONS Data Service data"));
            //LIST.add(new SDWDataProvider(20,"ABS","http://govhack.abs.gov.au/sdmxws/sdmx.asmx","http://stats.oecd.org/OECDStatWS/SDMX/",                    "Based on Australian Bureau of Statistics data","Based on Australian Bureau of Statistics data"));
            //LIST.add(new NSIDataProvider(21,"UNDATA","http://data.un.org/ws/NSIStdV20Service.asmx","http://ec.europa.eu/eurostat/sri/service/2.0",    "Based on United Nations data","Based on United Nations data"));
            LIST.add(new NSIDataProvider(22,"I","http://sdmx.istat.it/SDMXWS/NSIStdV20Service.asmx","http://ec.europa.eu/eurostat/sri/service/2.0",   "Based on Italian National Institute of Statistics data","Based on Italian National Institute of Statistics data"));
            LIST.add(new NSIDataProvider(23,"I","http://sdmx.istat.it/WS_CENSAGR/NSIStdV20Service.asmx","http://ec.europa.eu/eurostat/sri/service/2.0",   "Based on Italian National Institute of Statistics data","Based on Italian National Institute of Statistics data"));
            LIST.add(new NSIDataProvider(24,"I","http://sdmx.istat.it/WS_CIS/NSIStdV20Service.asmx","http://ec.europa.eu/eurostat/sri/service/2.0",   "Based on Italian National Institute of Statistics data","Based on Italian National Institute of Statistics data"));
            LIST.add(new NSIDataProvider(25,"I","http://sdmx.istat.it/WS_CENSPOP/NSIStdV20Service.asmx","http://ec.europa.eu/eurostat/sri/service/2.0",   "Based on Italian National Institute of Statistics data","Based on Italian National Institute of Statistics data"));
            LIST.add(new WidukindDataProvider(26,"CEPREMAP","http://widukind-api.cepremap.org/api/v1/sdmx", "Based on CEPREMAP Statistics data","Based on CEPREMAP Statistics data"));
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(DataProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private int index = -1;
    private String attribution = "";
    private String htmlAttribution = "";
    public DataProvider(int indx,String attribution,String htmlAttribution) {
        this.index=indx;
        this.attribution=attribution;
        this.htmlAttribution=htmlAttribution;
    }
    public int getIndex() {
        return index;
    }
    public static List<DataProvider>  getList() { return LIST; }
    public Queryable getQueryable() {
        return null;
    }
    
    public String getAgencyId() { return ""; }
    public String toString() { return getAgencyId(); }
    public int getType() { return -1; }
    public String getServiceURL() { return ""; }
    public String getOptions() { return ""; }

    /**
     * @return the attribution
     */
    public String getAttribution() {
        return attribution;
    }

    /**
     * @return the htmlAttribution
     */
    public String getHtmlAttribution() {
        return htmlAttribution;
    }

    /**
     * @param htmlAttribution the htmlAttribution to set
     */
    public void setHtmlAttribution(String htmlAttribution) {
        this.htmlAttribution = htmlAttribution;
    }

}

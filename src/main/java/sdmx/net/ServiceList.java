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
package sdmx.net;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import sdmx.net.list.DataProvider;
import sdmx.net.list.ILODataProvider;
import sdmx.net.list.KnoemaDataProvider;
import sdmx.net.list.NSIDataProvider;
//import sdmx.net.list.NSIDataProvider;
import sdmx.net.list.NomisDataProvider;
//import sdmx.net.list.ONSDataProvider;
import sdmx.net.list.OpenSDMXDataProvider;
import sdmx.net.list.RESTDataProvider;
import sdmx.net.list.SDWDataProvider;

/**
 *
 * @author James
 */
public class ServiceList {
    
    public static DataProvider getDataProvider(int type, String agency,String serviceURL,String options,String attribution,String htmlAttribution) throws MalformedURLException {
        for(DataProvider dp:DataProvider.getList()){
            if( dp.getType() == type && agency.equals(dp.getAgencyId()) && dp.getServiceURL().equals(serviceURL)) {
                return dp;
            }
        }
        switch(type) {
            case DataProvider.TYPE_ILO:return new ILODataProvider(-1,agency, serviceURL,attribution,htmlAttribution);
            case DataProvider.TYPE_KNOEMA:return new KnoemaDataProvider(-1,agency, serviceURL,attribution,htmlAttribution);
            case DataProvider.TYPE_NSI:return new NSIDataProvider(-1,agency, serviceURL,options,attribution,htmlAttribution);
            case DataProvider.TYPE_SDW:return new SDWDataProvider(-1,agency, serviceURL,options,attribution,htmlAttribution);
            case DataProvider.TYPE_REST:return new RESTDataProvider(-1,agency, serviceURL,attribution,htmlAttribution);
            case DataProvider.TYPE_OPENSDMX:return new OpenSDMXDataProvider(-1,agency, serviceURL,attribution,htmlAttribution);
            case DataProvider.TYPE_NOMIS:return new NomisDataProvider(-1,agency, serviceURL,options,attribution,htmlAttribution);
            //case DataProvider.TYPE_ONS:return new ONSDataProvider(-1,agency, serviceURL,options,attribution,htmlAttribution);
        }
        return null;
    }
    public static List<DataProvider> listDataProviders() {
        return DataProvider.getList();
    }
}

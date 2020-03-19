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
import sdmx.Queryable;
import sdmx.net.service.nsi.Sdmx20NSIQueryable;

/**
 *
 * @author James
 */
public class NSIDataProvider extends DataProvider {

    private String agencyId = null;
    private URL serviceURL = null;
    private String soapNamespace = null;

    public NSIDataProvider(int indx,String agency, String serviceURL, String soapNamespace,String attribution,String htmlAttribution) throws MalformedURLException {
        super(indx,attribution,htmlAttribution);
        this.agencyId = agency;
        this.serviceURL = new URL(serviceURL);
        this.soapNamespace = soapNamespace;
    }

    @Override
    public Queryable getQueryable() {
        Sdmx20NSIQueryable q = new Sdmx20NSIQueryable(agencyId, serviceURL.toString());
        q.setSoapNamespace(soapNamespace);
        return q;
    }
    
    public String getAgencyId() {
        return agencyId;
    }
    public int getType() { return DataProvider.TYPE_NSI; }
    public String getServiceURL() { return this.serviceURL.toString(); }
    @Override
    public String getOptions() { return soapNamespace; }
}

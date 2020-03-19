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
package sdmx.version.common;

import java.util.Locale;
import sdmx.Registry;
import sdmx.commonreferences.DataStructureReference;
import sdmx.structure.dataflow.DataflowType;
import sdmx.structure.datastructure.DataStructureType;

/**
 *
 * @author James
 */
public class ParseParams {
    
    // SDMX 2.0 -> When parsing SDMX 2.0, 
    // concepts are not kept in concept schemes
    // so we have to create a concept scheme
    // if this flag is false, we create a concept scheme
    // called AgencyId:"STANDALONE_CONCEPT_SCHEME":1.0
    // if this flag is true, we create a concept scheme
    // calls AgencyId:DATAFLOW_ID+"_CONCEPT_SCHEME":1.0
    // private boolean useDataflowName = false;
     private Registry registry = null;
     private String header = null;
     private ParseDataCallbackHandler callbackHandler = null;
     private Locale locale = Locale.ENGLISH;
     private DataflowType dataflow = null;
     
     public ParseParams() {
     }
     public ParseParams(Registry reg){
         this.registry=reg;
     }
     
     /**
     * @return the useDataflowName
     */
     /*
    public boolean isUseDataflowName() {
        return useDataflowName;
    }

    /**
     * @param useDataflowName the useDataflowName to set
     */
     /*
    public void setUseDataflowName(boolean useDataflowName) {
        this.useDataflowName = useDataflowName;
    }*/

    /**
     * @return the registry
     */
    public Registry getRegistry() {
        return registry;
    }

    /**
     * @param registry the registry to set
     */
    public void setRegistry(Registry registry) {
        this.registry = registry;
    }

    /**
     * @return the header
     */
    public String getHeader() {
        return header;
    }

    /**
     * @param header the header to set
     */
    public void setHeader(String header) {
        this.header = header;
    }

    /**
     * @return the callbackHandler
     */
    public ParseDataCallbackHandler getCallbackHandler() {
        return callbackHandler;
    }

    /**
     * @param callbackHandler the callbackHandler to set
     */
    public void setCallbackHandler(ParseDataCallbackHandler callbackHandler) {
        this.callbackHandler = callbackHandler;
    }

    /**
     * @return the locale
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * @param locale the locale to set
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    /**
     * @return the dataFlow
     */
    public DataflowType getDataflow() {
        return dataflow;
    }
    /**
     * @param dataFlow the dataFlow to set
     */
    public void setDataflow(DataflowType dataFlow) {
        this.dataflow = dataFlow;
    }
}

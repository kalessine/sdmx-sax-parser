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
package sdmx.data;

import sdmx.Registry;
import sdmx.data.flat.FlatDataSetWriter;
import sdmx.data.structured.StructuredDataWriter;
import sdmx.footer.FooterType;
import sdmx.message.BaseHeaderType;
import sdmx.structure.dataflow.DataflowType;
import sdmx.version.common.ParseDataCallbackHandler;

/**
 *
 * @author James
 */
public class FlatParseDataCallbackHandler implements ParseDataCallbackHandler{

    @Override
    public void setNamespace(String prefix, String namespace) {
    }

    @Override
    public void headerParsed(BaseHeaderType header) {
    }

    @Override
    public void footerParsed(FooterType footer) {
    }

    @Override
    public DataSetWriter getDataSetWriter() {
        return new FlatDataSetWriter();
    }

    @Override
    public void documentFinished() {
    }

    @Override
    public String getDimensionAtObservationHint() {
        return null;
    }

    @Override
    public void setDimensionAtObservationHint(String s) {
    }

    @Override
    public Registry getRegistry() {
        return null;
    }

    @Override
    public void setRegistry(Registry reg) {
    }
    
}

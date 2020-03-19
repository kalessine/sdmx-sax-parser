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
package sdmx.querykey.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.apache.commons.lang3.StringUtils;
import sdmx.Registry;
import sdmx.querykey.QueryDimension;
import sdmx.structure.base.ItemType;
import sdmx.structure.datastructure.DataStructureType;
import sdmx.structureddata.ValueTypeResolver;

/**
 *
 * @author James
 */
public class RegistryQueryDimension implements QueryDimension {

    String concept = null;
    Registry reg = null;
    DataStructureType struct = null;
    List<String> values = null;

    public RegistryQueryDimension(String concept, DataStructureType struct, Registry reg) {
        this.concept = concept;
        this.struct = struct;
        this.reg = reg;
        this.values = new ArrayList<String>();
    }

    public int size() {
        return this.values.size();
    }

    public List<String> getValues() {
        return this.values;
    }

    public void addValue(String s) {
        this.values.add(s);
    }

    public void removeValue(String s) {
        this.values.remove(s);
    }

    public String getConcept() {
        return this.concept;
    }

    public List<ItemType> getPossibleValues() {
        return ValueTypeResolver.getPossibleCodes(reg, struct, concept).getItems();
    }

    public String getQueryString() {
        String q = "";
        for (int i = 0; i < this.values.size(); i++) {
            q += values.get(i);
            if (i < values.size() - 1) {
                q += "+";
            }
        }
        return q;
    }

    @Override
    public void fromString(String dim) {
        String[] codes = StringUtils.splitPreserveAllTokens(dim, "+");
        for (int i = 0; i < codes.length; i++) {
            String sr = codes[i];
            this.addValue(sr);
        }
    }
}

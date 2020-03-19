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
package sdmx.data.structured;

import java.util.ArrayList;
import java.util.List;
import sdmx.data.Attachable;
import sdmx.data.AttachmentLevel;

/**
 *
 * @author James
 */
public class Obs implements Attachable {

    private int rowId = 0;
    private StructuredColumnMapper columnMapper = null;
    List<String> columnValues = new ArrayList<>();

    @Override
    public String getValue(String s) {
        return columnValues.get(getColumnMapper().getObservationIndex(s));
    }

    @Override
    public void setValue(String s, String val) {
        if (!columnMapper.containsColumn(s)) {
            columnMapper.registerColumn(s, AttachmentLevel.OBSERVATION);
        }
        setValue(getColumnMapper().getObservationIndex(s), val);
    }

    @Override
    public String getValue(int i) {
        /*
         * Sometimes, if a data message has optional fields, 
         * a value on an observation may not be set, so the 
         * number of values in columnValues may not properly
         * reflect the total number of fields (since there is an optional one at the end)
         * in this case some observations may have the number of optional fields less
         * elements in columnValues than an Obs with the optional field(s) set. 
         */
        if (i >= columnValues.size()) {
            return null;
        }
        return columnValues.get(i);
    }

    public String getObservationValue(int i) {
        /*
         * Sometimes, if a data message has optional fields, 
         * a value on an observation may not be set, so the 
         * number of values in columnValues may not properly
         * reflect the total number of fields (since there is an optional one at the end)
         * in this case some observations may have the number of optional fields less
         * elements in columnValues than an Obs with the optional field(s) set. 
         */
        int idx = getColumnMapper().getObservationIndex(i);
        if (idx >= columnValues.size()) {
            return null;
        }
        return columnValues.get(idx);
    }

    @Override
    public void setValue(int i, String val) {
        if (columnValues.size() - 1 < i) {
            for (int j = columnValues.size(); (j - 1) < i; j++) {
                columnValues.add(null);
            }
        }
        columnValues.set(i, val);
    }

    @Override
    public AttachmentLevel getAttachmentLevel() {
        return AttachmentLevel.OBSERVATION;
    }

    /**
     * @return the columnMapper
     */
    public StructuredColumnMapper getColumnMapper() {
        return columnMapper;
    }

    /**
     * @param columnMapper the columnMapper to set
     */
    public void setColumnMapper(StructuredColumnMapper columnMapper) {
        this.columnMapper = columnMapper;
    }

    /**
     * @return the rowId
     */
    public int getRowId() {
        return rowId;
    }

    /**
     * @param rowId the rowId to set
     */
    public void setRowId(int rowId) {
        this.rowId = rowId;
    }
}

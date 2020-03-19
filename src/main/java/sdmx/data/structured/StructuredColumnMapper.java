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
import sdmx.data.ColumnMapper;
import sdmx.data.AttachmentLevel;

/**
 *
 * @author James
 */

public class StructuredColumnMapper implements ColumnMapper {

    List<Column> columns = new ArrayList<Column>();

    public int registerColumn(String s, AttachmentLevel attach) {
        columns.add(new Column(s, attach));
        return columns.indexOf(s);
    }

    public int getColumnIndex(String s) {
        for (int i = 0; i < columns.size(); i++) {
            if (columns.get(i).getName().equals(s)) {
                return i;
            }
        }
        return -1;
    }

    public String getColumnName(int i) {
        return columns.get(i).getName();
    }

    public int size() {
        return columns.size();
    }

    public boolean containsColumn(String name) {
        for (int i = 0; i < columns.size(); i++) {
            if (columns.get(i).getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public List<String> getColumns(AttachmentLevel attach) {
        List<String> result = new ArrayList<String>();
        for (int i = 0; i < columns.size(); i++) {
            if (columns.get(i).getAttachment() == attach) {
                result.add(columns.get(i).getName());
            }
        }
        return result;
    }

    public List<String> getObservationColumns() {
        return getColumns(AttachmentLevel.OBSERVATION);
    }

    public List<String> getSeriesColumns() {
        return getColumns(AttachmentLevel.SERIES);
    }

    public List<String> getDataSetColumns() {
        return getColumns(AttachmentLevel.DATASET);
    }

    public List<String> getGroupColumns() {
        return getColumns(AttachmentLevel.GROUP);
    }

    public int getDataSetIndex(String col) {
        List<String> cols = getDataSetColumns();
        int result = 0;
        for (int i = 0; i < cols.size(); i++) {
            if (cols.get(i).equals(col)) {
                return result;
            }
            result++;
        }
        return -1;
    }

    public int getSeriesIndex(String col) {
        List<String> cols = getSeriesColumns();
        int result = 0;
        for (int i = 0; i < cols.size(); i++) {
            if (cols.get(i).equals(col)) {
                return result;
            }
            result++;
        }
        return -1;
    }

    public int getObservationIndex(String col) {
        List<String> cols = getObservationColumns();
        int result = 0;
        for (int i = 0; i < cols.size(); i++) {
            if (cols.get(i).equals(col)) {
                return result;
            }
            result++;
        }
        return -1;
    }

    public int getObservationIndex(int col) {
        if (!isAttachedToObservation(col)) {
            throw new RuntimeException("Not attached to obs");
        }
        List<String> cols = getObservationColumns();
        //System.out.println("Get Observation Index:"+col);
        for (int i = 0; i < cols.size(); i++) {
            if (getColumnIndex(cols.get(i)) == col) {
                //System.out.println("Returning:"+i);
                return i;
            }
        }
        return -1;
    }

    @Override
    public List<String> getAllColumns() {
        List<String> result = new ArrayList<String>();
        for (int i = 0; i < columns.size(); i++) {
            result.add(columns.get(i).getName());
        }
        return result;
    }

    public AttachmentLevel getAttachmentLevel(String s) {
        for (int i = 0; i < columns.size(); i++) {
            if (columns.get(i).getName().equals(s)) {
                return columns.get(i).getAttachment();
            }
        }
        return AttachmentLevel.OBSERVATION;
    }

    public AttachmentLevel getAttachmentLevel(int i) {
        return columns.get(i).getAttachment();
    }

    public void sortColumns() {
        for (int i = 0; i < columns.size(); i++) {
            for (int j = 0; j < columns.size() - i - 1; j++) {
                if (columns.get(j).getAttachment().getId() < columns.get(j + 1).getAttachment().getId()) {
                    Column c = columns.get(j + 1);
                    columns.set(j + 1, columns.get(j));
                    columns.set(j, c);
                }
            }
        }
    }

    public void dump() {
        System.out.println("Column Mapping");
        for (int i = 0; i < columns.size(); i++) {
            System.out.println(columns.get(i).getName() + ":" + columns.get(i).getAttachment().getName());
        }
    }

    @Override
    public boolean isAttachedToDataSet(String s) {
        return getAttachmentLevel(s) == AttachmentLevel.DATASET;
    }

    @Override
    public boolean isAttachedToDataSet(int i) {
        return getAttachmentLevel(i) == AttachmentLevel.DATASET;
    }

    @Override
    public boolean isAttachedToSeries(String s) {
        return getAttachmentLevel(s) == AttachmentLevel.SERIES;
    }

    @Override
    public boolean isAttachedToSeries(int i) {
        return getAttachmentLevel(i) == AttachmentLevel.SERIES;
    }

    @Override
    public boolean isAttachedToObservation(String s) {
        return getAttachmentLevel(s) == AttachmentLevel.OBSERVATION;
    }

    @Override
    public boolean isAttachedToObservation(int i) {
        return getAttachmentLevel(i) == AttachmentLevel.OBSERVATION;
    }

    @Override
    public boolean isAttachedToGroup(String s) {
        return getAttachmentLevel(s) == AttachmentLevel.GROUP;
    }

    @Override
    public boolean isAttachedToGroup(int i) {
        return getAttachmentLevel(i) == AttachmentLevel.GROUP;
    }
}

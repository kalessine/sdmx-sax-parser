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
package sdmx.data.flat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import sdmx.data.ColumnMapper;
import sdmx.data.AttachmentLevel;

/**
 *
 * @author James
 */

public class FlatColumnMapper implements ColumnMapper {
    List<String> columns = new ArrayList<String>();
    List<String> groupColumns = new ArrayList<String>();
    public int registerColumn(String s,AttachmentLevel attach) {
        if( columns.contains(s)||groupColumns.contains(s)){
            throw new RuntimeException("Attempt to Register already registered Column!!");
        }
        if( attach!=AttachmentLevel.OBSERVATION&&attach!=AttachmentLevel.GROUP) {
            throw new RuntimeException("Attachment level is not OBSERVATION Or Group");
        }
        if( attach == AttachmentLevel.GROUP ) {
            groupColumns.add(s);
            columns.add(s);
            return columns.indexOf(s);
        }else {
            columns.add(s);
            return columns.indexOf(s);
        }
        
    }
    public int getColumnIndex(String s) {
        return columns.indexOf(s);
    }
    public String getColumnName(int i) {
        return columns.get(i);
    }
    public int size() {
        return columns.size();
    }
    public boolean containsColumn(String name) {
        for(int i=0;i<columns.size();i++) {
            if( columns.get(i).equals(name))return true;
        }
        return false;
    }

    @Override
    public List<String> getAllColumns() {
        List<String> result = new ArrayList<String>();
        for(int i=0;i<columns.size();i++) {
            result.add(columns.get(i));
        }
        return result;
    }

    @Override
    public List<String> getObservationColumns() {
        List<String> result = new ArrayList<String>();
        for(int i=0;i<columns.size();i++) {
            result.add(columns.get(i));
        }
        return result;

    }

    @Override
    public List<String> getSeriesColumns() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<String> getDataSetColumns() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<String> getGroupColumns() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public boolean isAttachedToDataSet(String s) {
        return false;
    }

    @Override
    public boolean isAttachedToDataSet(int i) {
        return false;
    }

    @Override
    public boolean isAttachedToSeries(String s) {
        return false;
    }

    @Override
    public boolean isAttachedToSeries(int i) {
        return false;
    }

    @Override
    public boolean isAttachedToObservation(String s) {
        return true;
    }

    @Override
    public boolean isAttachedToObservation(int i) {
        return true;
    }

    @Override
    public boolean isAttachedToGroup(String s) {
        return groupColumns.contains(s);
    }

    @Override
    public boolean isAttachedToGroup(int i) {
        return isAttachedToGroup(getColumnName(i));
    }

    @Override
    public void dump() {
        System.out.println("Column Mapper");
       for(int i=0;i<size();i++) {
          System.out.println(i+"="+getColumnName(i));
       }
       
    }
}

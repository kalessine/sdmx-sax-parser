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

import java.util.List;

/**
 *
 * @author James
 */

public interface ColumnMapper {
    public int registerColumn(String s,AttachmentLevel attach);
    public int getColumnIndex(String s);
    public String getColumnName(int i);
    public int size();
    public boolean containsColumn(String name);
    public List<String> getAllColumns();
    public List<String> getObservationColumns();
    public List<String> getSeriesColumns();
    public List<String> getDataSetColumns();
    public List<String> getGroupColumns();
    public boolean isAttachedToDataSet(String s);
    public boolean isAttachedToDataSet(int i);
    public boolean isAttachedToSeries(String s);
    public boolean isAttachedToSeries(int i);
    public boolean isAttachedToObservation(String s);
    public boolean isAttachedToObservation(int i);
    public boolean isAttachedToGroup(String s);
    public boolean isAttachedToGroup(int i);
    public void dump();
    
}

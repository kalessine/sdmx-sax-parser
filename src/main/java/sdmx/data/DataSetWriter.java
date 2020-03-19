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

import java.util.HashMap;
import sdmx.data.flat.FlatDataSet;

/**
 *
 * @author James
 */

public interface DataSetWriter {
    //public ColumnMapper getColumnMapper();
    public void newDataSet();
    public void newSeries();
    public void newObservation();
    public void writeDataSetComponent(String name,String val);
    public void writeSeriesComponent(String name,String val);
    public void writeObservationComponent(String name,String val);
    public void writeGroupValues(String name,HashMap<String,Object> group);
    public void finishObservation();
    public void finishSeries();
    public DataSet finishDataSet();
}

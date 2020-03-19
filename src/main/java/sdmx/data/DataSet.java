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

import sdmx.cube.Cube;
import java.util.List;
import sdmx.Registry;
import sdmx.common.ActionType;
import sdmx.commonreferences.DataStructureReference;
import sdmx.data.flat.FlatObs;
import sdmx.data.key.FullKey;
import sdmx.data.key.PartialKey;
import sdmx.query.data.DataQuery;

/**
 *
 * @author James
 */

public interface DataSet {
    public void dump();
    public String getColumnName(int i);
    public int getColumnIndex(String s);
    public int getColumnSize();
    public int size();
    public String getValue(int row, int col);
    public String getValue(int row, String col);
    public void setValue(int row, int col,String val);
    public void setValue(int row, String col,String val);
    public void writeTo(DataSetWriter writer);
    public FlatObs getFlatObs(int row);
    public Cube query(Cube cube,List<String> order);
    public ColumnMapper getColumnMapper();
    public void setGroups(List<Group> groups);
    public List<Group> getGroups();
    public int groupSize();
    public FlatObs find(FullKey key);
    }

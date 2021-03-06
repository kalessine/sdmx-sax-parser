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
package sdmx.cube;

import java.util.ArrayList;
import java.util.List;
import sdmx.data.AttachmentLevel;
import sdmx.data.ColumnMapper;
import sdmx.data.flat.FlatColumnMapper;

/**
 *
 * @author James
 */
public class CubeObs {
     List<String> values = new ArrayList<String>();
     ColumnMapper mapper = new FlatColumnMapper();
     public CubeObs(int i){
         values = new ArrayList<String>(i);
     }
     public CubeObs(List<String> vals){
         values = vals;
     }

    public CubeObs() {
        
    }
    public CubeObs(ColumnMapper c) {
        this.mapper=c;
    }
     public void setValue(int i, String o) {
         if( values.size()<=i) {
             for(int j=values.size();(j-1)<i;j++) {
                 values.add(null);
             }
         }
         values.set(i,o);
     }
     public String getValue(int i) {
         if( i>=values.size() ) return null;
         if( i == -1 ) return null;
         return values.get(i);
     }
     public void dump() {
         for(int i=0;i<values.size();i++) {
             System.out.print(values.get(i));
             if( i<values.size())System.out.print(" ");
         }
     }
     public int size() {
         return values.size();
     }
     public String getValue(String s) {
         return getValue(mapper.getColumnIndex(s));
     }
     public void setValue(String s,String v) {
         setValue(mapper.getColumnIndex(s),v);
     }
     public void addValue(String col,String val) {
         if(!mapper.containsColumn(col)){
             mapper.registerColumn(col, AttachmentLevel.OBSERVATION);
             setValue(mapper.getColumnIndex(col),val);
         }else{
             setValue(mapper.getColumnIndex(col),val);
         }
     }
     public ColumnMapper getColumnMapper() { return this.mapper; }
     public void setColumnMapper(ColumnMapper c) { this.mapper=c; }
}

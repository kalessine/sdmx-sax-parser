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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import sdmx.data.AttachmentLevel;
import sdmx.data.ColumnMapper;
import sdmx.data.DataSetWriter;
import sdmx.data.Group;
import sdmx.data.flat.FlatDataSet;
import sdmx.data.flat.FlatObs;

/**
 *
 * @author James
 */

public class StructuredDataWriter implements DataSetWriter {
    private StructuredColumnMapper mapper = new StructuredColumnMapper();
    private StructuredDataSet dataSet = null;

    private List<Series> seriesList = new ArrayList<Series>();
    private List<Obs> obsList = new ArrayList<Obs>();
    private List<Group> groups = null;
    
    private Series series = null;
    private boolean in_series = false;
    private Obs obs = null;
    
    public StructuredDataWriter() {}
    public StructuredDataWriter(StructuredColumnMapper mapper) {
        this.mapper=mapper;
    }
    
    @Override
    public void newDataSet() {
        //System.out.println("New DataSet");
        dataSet = new StructuredDataSet(mapper);
    }

    @Override
    public void newSeries() {
        //System.out.println("New Series");
        series = new Series();
        in_series = true;
        series.setMapper(mapper);
    }

    @Override
    public void newObservation() {
        obs = new Obs();
        obs.setColumnMapper(mapper);
    }
    @Override
    public void writeDataSetComponent(String name, String val) {
        //System.out.println("DS Name:"+name+":"+val);
        dataSet.setValue(name, val);
    }

    @Override
    public void writeSeriesComponent(String name, String val) {
     //   System.out.println("S Name:"+name+":"+val);
        series.setValue(name, val);
    }

    @Override
    public void writeObservationComponent(String name, String val) {
        //System.out.println("O Name:"+name+":"+val);
        obs.setValue(name, val);
    }

    @Override
    public void finishSeries() {
        seriesList.add(series);
        in_series=false;
        series = null;
    }

    @Override
    public void finishObservation() {
        if(obs==null){
            System.out.println("Finish Obs:"+obs+" is null!!!");
        }
        if( in_series ) {
            series.getObservations().add(obs);
            obs = null;
        }else {
            obsList.add(obs);
            obs = null;
        }
    }

    @Override
    public StructuredDataSet finishDataSet() {
        StructuredDataSet ds = dataSet;
        if( seriesList.size()>0){
            ds.setSeriesList(seriesList);
        }else {
            ds.setObservations(obsList);
        }
        ds.updateIndexes();
        ds.setGroups(groups);
        dataSet=null;
        return ds;
    }    

   //@Override
    public ColumnMapper getColumnMapper() {
        return mapper;
    }

    @Override
    public void writeGroupValues(String name,HashMap<String, Object> groupValues) {
        Group g = new Group(groupValues);
        g.setGroupName(name);
        if( groups==null ) groups = new ArrayList<Group>();
        groups.add(g);
        //Iterator<String> it = groupValues.keySet().iterator();
        //while(it.hasNext()) {
        //    String s = it.next();
        //    System.out.println("Value="+s+":"+groupValues.get(s));
        //}
    }
  
}

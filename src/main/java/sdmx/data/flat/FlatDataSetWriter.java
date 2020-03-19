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
import java.util.HashMap;
import java.util.List;
import sdmx.data.ColumnMapper;
import sdmx.data.DataSetWriter;
import sdmx.data.Group;

/**
 *
 * @author James
 */
public class FlatDataSetWriter implements DataSetWriter {

    private ColumnMapper mapper = new FlatColumnMapper();
    private FlatDataSet dataSet = null;
    private List<String> dataSetValues = null;
    private List<String> seriesValues = null;
    private List<String> obsValues = null;
    List<Group> groups = null;

    public FlatDataSetWriter() {
    }

    public FlatDataSetWriter(ColumnMapper mapper) {
        this.mapper = (ColumnMapper) mapper;
    }

    @Override
    public void newDataSet() {
        dataSet = new FlatDataSet();
        dataSetValues = new ArrayList<String>();
    }

    @Override
    public void newSeries() {
        seriesValues = new ArrayList<String>();
        for (int i = 0; i < dataSetValues.size(); i++) {
            seriesValues.add(dataSetValues.get(i));
        }
    }

    @Override
    public void newObservation() {
        obsValues = new ArrayList<String>();
        if (seriesValues != null) {
            for (int i = 0; i < seriesValues.size(); i++) {
                obsValues.add(seriesValues.get(i));
            }
        }
    }

    @Override
    public void writeDataSetComponent(String name, String val) {
        if (!dataSet.getColumnMapper().containsColumn(name)) {
            dataSet.registerColumn(name);
        }
        dataSetValues.add(val);
    }

    @Override
    public void writeSeriesComponent(String name, String val) {
        if (!dataSet.getColumnMapper().containsColumn(name)) {
            dataSet.registerColumn(name);
        }
        seriesValues.add(val);
    }

    @Override
    public void writeObservationComponent(String name, String val) {
        if (!dataSet.getColumnMapper().containsColumn(name)) {
            dataSet.registerColumn(name);
        }
        if (obsValues.size() <= dataSet.getColumnMapper().getColumnIndex(name)) {
            for (int j = obsValues.size(); (j - 1) < dataSet.getColumnIndex(name); j++) {
                obsValues.add(null);
            }
        }
        obsValues.set(dataSet.getColumnIndex(name), val);
    }

    @Override
    public void finishSeries() {

    }

    @Override
    public void finishObservation() {
        dataSet.addObservation(new FlatObs(obsValues));
    }

    @Override
    public FlatDataSet finishDataSet() {
        FlatDataSet ds = dataSet;
        ds.setGroups(groups);
        dataSet = null;
        return ds;
    }

    private void setDataSet(int i, String val) {
        if (dataSetValues.size() < i) {
            for (int j = dataSetValues.size(); j < i; j++) {
                dataSetValues.add(null);
            }
        }
        dataSetValues.set(i, val);
    }

    private void setSeries(int i, String val) {
        if (seriesValues.size() < i) {
            for (int j = seriesValues.size(); j < i; j++) {
                seriesValues.add(null);
            }
        }
        seriesValues.set(i, val);
    }

    private void setObservation(int i, String val) {
        if (obsValues.size() < i) {
            for (int j = obsValues.size(); j < i; j++) {
                obsValues.add(null);
            }
        }
        obsValues.set(i, val);
    }

    //@Override
    public ColumnMapper getColumnMapper() {
        return mapper;
    }

    @Override
    public void writeGroupValues(String name, HashMap<String, Object> groupValues) {
        Group group = new Group(groupValues);
        group.setGroupName(name);
        if (this.groups == null) {
            this.groups = new ArrayList<Group>();
        }
        groups.add(group);
    }
}

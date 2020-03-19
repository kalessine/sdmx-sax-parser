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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.hamnaberg.jsonstat.util.IntCartesianProduct;
import sdmx.Registry;
import sdmx.commonreferences.DataStructureReference;
import sdmx.commonreferences.IDType;
import sdmx.data.AttachmentLevel;
import sdmx.data.ColumnMapper;
import sdmx.data.DataSetWriter;
import sdmx.data.flat.FlatColumnMapper;
import sdmx.data.flat.FlatObs;
import sdmx.data.key.FullKey;
import sdmx.message.DataStructure;
import sdmx.structure.base.Component;
import sdmx.structure.base.ItemType;
import sdmx.structure.base.NameableType;
import sdmx.structure.codelist.CodeType;
import sdmx.structure.datastructure.AttributeType;
import sdmx.structure.datastructure.DataStructureType;
import sdmx.structure.datastructure.DimensionType;
import sdmx.structure.datastructure.MeasureDimensionType;
import sdmx.structure.datastructure.PrimaryMeasure;
import sdmx.structure.datastructure.TimeDimensionType;
import sdmx.structureddata.ValueTypeResolver;

public class Cube {

    private long size = 0;
    private long flatSize = 0;
    private List<String> order = null;
    DataStructureType struct = null;
    Registry reg = null;
    ColumnMapper flatObsMapper = new FlatColumnMapper();
    ColumnMapper cubeObsMapper = new FlatColumnMapper();

    private HashMap<String, List<String>> validCodes = new HashMap<String, List<String>>();
    private HashMap<String, Integer> distinctValuesCount = new HashMap<String, Integer>();
    private HashMap<String, Boolean> sorted = new HashMap<String, Boolean>();

    public Cube(DataStructureType struct, Registry reg) {
        this.struct = struct;
        this.reg = reg;
    }

    public DataStructureType getStructure() {
        return this.struct;
    }

    public DataStructureReference getStructureReference() {
        return this.struct.asReference();
    }

    private RootCubeDimension root = new RootCubeDimension();

    public RootCubeDimension getRootCubeDimension() {
        return root;
    }

    public void putObservation(List<String> order, ColumnMapper mapper, FlatObs obs) {
        CubeDimension dim = getRootCubeDimension();
        this.order = order;
        TimeCubeDimension time = null;
        CubeObservation cubeobs = null;
        for (int i = 0; i < struct.getDataStructureComponents().getDimensionList().size(); i++) {
            //if( struct.getDataStructureComponents().getDimensionList().getDimension(i).)
            // This line goes through the components in their datastructure order
            //IDType dimId = struct.getDataStructureComponents().getDimensionList().getDimension(i).getId();
            // This line goes through the components in their specified order
            IDType dimId = null;
            if (order != null) {
                dimId = new IDType(order.get(i));
            } else {
                dimId = struct.getDataStructureComponents().getDimensionList().getDimension(i).getId();
            }
            if (validCodes.get(dimId.toString()) == null) {
                validCodes.put(dimId.toString(), new ArrayList<String>());
                if (!getColumnMapper().containsColumn(dimId.toString())) {
                    getColumnMapper().registerColumn(dimId.toString(), AttachmentLevel.OBSERVATION);
                }
                if (!getCubeObsMapper().containsColumn(dimId.toString())) {
                    getCubeObsMapper().registerColumn(dimId.toString(), AttachmentLevel.OBSERVATION);
                }
            }
            /*
                If the data you are trying to make a cube from does not have a complete key
                with values for all dimensions, mapper.getColumnIndex(dimId.toString()) returns -1
                here (because there is no dimension of that name in the FlatObservation)
                this filters down into FlatObservation.getValue(-1) which causes an array index
                out of bounds exception!
             */
            CubeDimension myDim = dim.getSubDimension(obs.getValue(mapper.getColumnIndex(dimId.toString())));
            if (myDim == null) {
                //myDim = new HashMapCubeDimension(dimId.toString(), obs.getValue(mapper.getColumnIndex(dimId.toString())));
                myDim = new ListCubeDimension(dimId.toString(), obs.getValue(mapper.getColumnIndex(dimId.toString())));
                dim.putSubDimension(myDim);
                if (!validCodes.get(dimId.toString()).contains(myDim.getValue())) {
                    validCodes.get(dimId.toString()).add(myDim.getValue());
                    if (distinctValuesCount.get(dimId.toString()) == null) {
                        distinctValuesCount.put(dimId.toString(), 1);
                    } else {
                        distinctValuesCount.put(dimId.toString(), distinctValuesCount.get(dimId.toString()) + 1);
                    }
                }
            }
            dim = myDim;
        }
        CubeDimension myDim = null;
        IDType dimId = struct.getDataStructureComponents().getDimensionList().getTimeDimension().getId();

        if (validCodes.get(dimId.toString()) == null) {
            validCodes.put(dimId.toString(), new ArrayList<String>());
            if (!getColumnMapper().containsColumn(dimId.toString())) {
                getColumnMapper().registerColumn(dimId.toString(), AttachmentLevel.OBSERVATION);
            }
            if (!getCubeObsMapper().containsColumn(dimId.toString())) {
                getCubeObsMapper().registerColumn(dimId.toString(), AttachmentLevel.OBSERVATION);
            }
        }
        int i = mapper.getColumnIndex(dimId.toString());
        String s = obs.getValue(i);
        myDim = dim.getSubDimension(s);
        if (myDim == null) {
            myDim = new TimeCubeDimension(dimId.toString(), obs.getValue(mapper.getColumnIndex(dimId.toString())));
            size++;
            dim.putSubDimension(myDim);
            if (!validCodes.get(dimId.toString()).contains(myDim.getValue())) {
                validCodes.get(dimId.toString()).add(myDim.getValue());
                if (distinctValuesCount.get(dimId.toString()) == null) {
                    distinctValuesCount.put(dimId.toString(), 1);
                } else {
                    distinctValuesCount.put(dimId.toString(), distinctValuesCount.get(dimId.toString()) + 1);
                }
            }
        } else {
        }
        time = (TimeCubeDimension) myDim;
        String cross = null;
        IDType dimId2 = null;

        if (struct.getDataStructureComponents().getDimensionList().getMeasureDimension() != null) {
            dimId2 = struct.getDataStructureComponents().getDimensionList().getMeasureDimension().getId();
            if (validCodes.get(dimId2.toString()) == null) {
                validCodes.put(dimId2.toString(), new ArrayList<String>());
                if (!getColumnMapper().containsColumn(dimId2.toString())) {
                    getColumnMapper().registerColumn(dimId2.toString(), AttachmentLevel.OBSERVATION);
                }
            }
            cross = obs.getValue(mapper.getColumnIndex(dimId2.toString()));
            if (!validCodes.get(dimId2.toString()).contains(cross)) {
                validCodes.get(dimId2.toString()).add(cross);
                if (!getCubeObsMapper().containsColumn(cross)) {
                    getCubeObsMapper().registerColumn(cross, AttachmentLevel.OBSERVATION);
                }
                if (distinctValuesCount.get(dimId2.toString()) == null) {
                    distinctValuesCount.put(dimId2.toString(), 1);
                } else {
                    distinctValuesCount.put(dimId2.toString(), distinctValuesCount.get(dimId.toString()) + 1);
                }
            }
        }
        IDType dimId3 = struct.getDataStructureComponents().getMeasureList().getPrimaryMeasure().getId();
        if (dimId2 != null) {
            flatSize++;
            cubeobs = new CubeObservation(dimId2.toString(), cross, dimId3.toString(), obs.getValue(mapper.getColumnIndex(dimId3.toString())));
        } else {
            if (validCodes.get(dimId3.toString()) == null) {
                validCodes.put(dimId3.toString(), new ArrayList<String>());
                if (!getColumnMapper().containsColumn(dimId3.toString())) {
                    getColumnMapper().registerColumn(dimId3.toString(), AttachmentLevel.OBSERVATION);
                }

            }
            if (!validCodes.get(dimId3.toString()).contains(obs.getValue(mapper.getColumnIndex(dimId3.toString())))) {
                validCodes.get(dimId3.toString()).add(obs.getValue(mapper.getColumnIndex(dimId3.toString())));
                if (distinctValuesCount.get(dimId3.toString()) == null) {
                    distinctValuesCount.put(dimId3.toString(), 1);
                } else {
                    distinctValuesCount.put(dimId3.toString(), distinctValuesCount.get(dimId3.toString()) + 1);
                }
            }
            size++;
            flatSize++;
            cubeobs = new CubeObservation(null, null, dimId3.toString(), obs.getValue(mapper.getColumnIndex(dimId3.toString())));
        }

        time.putObservation(cubeobs);

        for (int k = 0; k < struct.getDataStructureComponents().getAttributeList().size(); k++) {
            String name = struct.getDataStructureComponents().getAttributeList().getAttribute(k).getId().toString();
            if (validCodes.get(name) == null) {
                validCodes.put(name, new ArrayList<String>());
                if (!mapper.containsColumn(name)) {
                    mapper.registerColumn(name, AttachmentLevel.OBSERVATION);
                }
            }
            String value = null;
            if (mapper.getColumnIndex(name) != -1) {
                value = obs.getValue(mapper.getColumnIndex(name));
                if (!validCodes.get(name).contains(value)) {
                    validCodes.get(name).add(value);
                    if (distinctValuesCount.get(name) == null) {
                        distinctValuesCount.put(name, 1);
                    } else {
                        distinctValuesCount.put(name, distinctValuesCount.get(name) + 1);
                    }
                }
                cubeobs.putAttribute(new CubeAttribute(name, value));
            }
        }

    }

    public CubeObs find(FullKey key) {
        return find(key, false);
    }

    public CubeObs find(FullKey key, boolean latest) {
        CubeDimension dim = getRootCubeDimension();
        CubeDimension oldDim = dim;
        for (int i = 0; i < this.struct.getDataStructureComponents().getDimensionList().size(); i++) {
            dim = dim.getSubDimension((String) key.getComponent(dim.getSubDimension()));
            if (dim == null) {
                //System.out.println("Can't find dim:"+key.getComponent(order.get(i))+":"+oldDim.getSubDimension());
                return null;
            }
            oldDim = dim;
        }
        TimeDimensionType time = this.struct.getDataStructureComponents().getDimensionList().getTimeDimension();
        if (time == null) {
            throw new RuntimeException("Time Dimension Is Null");
        } else if (latest) {
            Set times = dim.listDimensionValues();
            List<String> timesList = new ArrayList<String>(times);
            Collections.sort(timesList);
            String timeValue = timesList.get(timesList.size() - 1);
            TimeCubeDimension tcd = (TimeCubeDimension) dim.getSubDimension(timeValue);

            if (tcd == null) {
                //System.out.println("TCD null:"+key.getComponent(time.getId().toString()+":"+timeValue));
                //dim.dump();
                return null;
            }
            return tcd.toCubeObs(key, cubeObsMapper);
        } else {
            String timeValue = NameableType.toIDString((String) key.getComponent(time.getId().toString()));
            TimeCubeDimension tcd = (TimeCubeDimension) dim.getSubDimension(timeValue);

            if (tcd == null) {
                System.out.println("TCD null:" + key.getComponent(time.getId().toString() + ":" + timeValue));
                //dim.dump();
                return null;
            }
            return tcd.toCubeObs(key, cubeObsMapper);
        }
    }

    public FlatObs findFlatObs(FullKey key) {
        return findFlatObs(key, false);
    }

    public FlatObs findFlatObs(FullKey key, boolean latest) {
        CubeDimension dim = getRootCubeDimension();
        CubeDimension oldDim = dim;
        for (int i = 0; i < this.struct.getDataStructureComponents().getDimensionList().size(); i++) {
            dim = dim.getSubDimension((String) key.getComponent(dim.getSubDimension()));
            if (dim == null) {
                //System.out.println("Can't find dim:"+key.getComponent(order.get(i))+":"+oldDim.getSubDimension());
                return null;
            }
            oldDim = dim;
        }
        TimeDimensionType time = this.struct.getDataStructureComponents().getDimensionList().getTimeDimension();
        if (time == null) {
            throw new RuntimeException("Time Dimension Is Null");
        } else if (latest) {
            Set times = dim.listDimensionValues();
            List<String> timesList = new ArrayList<String>(times);
            Collections.sort(timesList);
            String timeValue = timesList.get(timesList.size() - 1);
            TimeCubeDimension tcd = (TimeCubeDimension) dim.getSubDimension(timeValue);
            if (tcd == null) {
                //System.out.println("TCD null:"+key.getComponent(time.getId().toString()+":"+timeValue));
                //dim.dump();
                return null;
            }
            CubeObservation cubeObs = tcd.getObservation((String) key.getComponent(tcd.getSubDimension()));
            return cubeObs.toFlatObs(key, getColumnMapper());
        } else {
            String timeValue = (String) key.getComponent(time.getId().toString());
            TimeCubeDimension tcd = (TimeCubeDimension) dim.getSubDimension(timeValue);
            if (tcd == null) {
                System.out.println("TCD null:" + key.getComponent(time.getId().toString() + ":" + timeValue));
                dim.dump();
                return null;
            }
            String measureDim = struct.getDataStructureComponents().getDimensionList().getMeasureDimension().getId().toString();
            if (measureDim == null) {
                CubeObservation cubeObs = tcd.getObservation(null);
                if (cubeObs == null) {
                    return null;
                }
                return cubeObs.toFlatObs(key, getColumnMapper());
            } else {
                CubeObservation cubeObs = tcd.getObservation((String) key.getComponent(measureDim));
                if (cubeObs == null) {
                    return null;
                }
                return cubeObs.toFlatObs(key, getColumnMapper());
            }
        }
    }

    public List<String> getValues(String col) {
        List<String> list = validCodes.get(col);
        if (list == null) {
            return Collections.EMPTY_LIST;
        }
        if (sorted.get(col) == null) {
            Collections.sort(list);
            sorted.put(col, Boolean.TRUE);
        }
        return list;
    }

    /**
     * @return the size
     */
    public long getSize() {
        return size;
    }

    public long getObservationCount() {
        return size;
    }

    public List<ItemType> getAllItems(String col) {
        Component com = this.getStructure().findComponent(col);
        return this.reg.find(com.getLocalRepresentation().getEnumeration()).getItems();
    }

    public List<String> getAllValues(String col) {
        Component com = this.getStructure().findComponent(col);
        List<String> result = new ArrayList<String>();
        List<ItemType> items = this.reg.find(com.getLocalRepresentation().getEnumeration()).getItems();
        for (int i = 0; i < items.size(); i++) {
            result.add(items.get(i).getId().toString());
        }
        return result;
    }

    public List<ItemType> getItems(String col) {
        Component com = this.getStructure().findComponent(col);
        List<ItemType> result = new ArrayList<ItemType>();
        List<ItemType> items = this.reg.find(com.getLocalRepresentation().getEnumeration()).getItems();
        List<String> codes = this.getValues(col);
        for (int i = 0; i < items.size(); i++) {
            for (int j = 0; j < codes.size(); j++) {
                if (codes.get(j).equals(items.get(i).getId().getString())) {
                    result.add(items.get(i));
                }
            }
        }
        return result;
    }

    public ColumnMapper getColumnMapper() {
        return this.flatObsMapper;
    }

    public ColumnMapper getCubeObsMapper() {
        return this.cubeObsMapper;
    }

    public int getFlatObsCount() {
        int c = getValues(flatObsMapper.getColumnName(0)).size();
        for (int i = 1; i < flatObsMapper.size(); i++) {
            c *= getValues(flatObsMapper.getColumnName(i)).size();
        }
        return c;
    }

    public double getSparsity() {
        return (((double) getObservationCount()) / ((double) getFlatObsCount()) * 100d);
    }         // %

    public Map<String, Integer> getDistinctValuesCount() {
        return this.distinctValuesCount;
    }

    public int[] getCubeObsLengths() {
        int[] lengths = new int[struct.getDataStructureComponents().getDimensionList().size() + 1];
        for (int i = 0; i < struct.getDataStructureComponents().getDimensionList().size(); i++) {
            lengths[i] = getValues(struct.getDataStructureComponents().getDimensionList().getDimension(i).getId().toString()).size();
        }
        int timeDimensionIndex = struct.getDataStructureComponents().getDimensionList().size();// -1 +1 cancel each other out
        if (struct.getDataStructureComponents().getDimensionList().getTimeDimension() != null) {
            lengths[timeDimensionIndex] = getValues(struct.getDataStructureComponents().getDimensionList().getTimeDimension().getId().toString()).size();
        }
        return lengths;
    }

    public int[] getFlatObsLengths() {
        if (struct.getDataStructureComponents().getDimensionList().getMeasureDimension() == null) {
            int[] lengths = new int[struct.getDataStructureComponents().getDimensionList().size() + 1];
            for (int i = 0; i < struct.getDataStructureComponents().getDimensionList().size(); i++) {
                lengths[i] = getValues(struct.getDataStructureComponents().getDimensionList().getDimension(i).getId().toString()).size();
            }
            int timeDimensionIndex = struct.getDataStructureComponents().getDimensionList().size();// -1 +1 cancel each other out
            if (struct.getDataStructureComponents().getDimensionList().getTimeDimension() != null) {
                lengths[timeDimensionIndex] = getValues(struct.getDataStructureComponents().getDimensionList().getTimeDimension().getId().toString()).size();
            }

            return lengths;
        } else {
            int[] lengths = new int[struct.getDataStructureComponents().getDimensionList().size() + 2];
            for (int i = 0; i < struct.getDataStructureComponents().getDimensionList().size(); i++) {
                lengths[i] = getValues(struct.getDataStructureComponents().getDimensionList().getDimension(i).getId().toString()).size();
            }
            int timeDimensionIndex = struct.getDataStructureComponents().getDimensionList().size();// -1 +1 cancel each other out
            if (struct.getDataStructureComponents().getDimensionList().getTimeDimension() != null) {
                lengths[timeDimensionIndex] = getValues(struct.getDataStructureComponents().getDimensionList().getTimeDimension().getId().toString()).size();
            }
            int measureDimensionIndex = struct.getDataStructureComponents().getDimensionList().size() + 1;
            lengths[measureDimensionIndex] = getValues(struct.getDataStructureComponents().getDimensionList().getMeasureDimension().getId().toString()).size();
            return lengths;
        }
    }

    public void writeTo(DataSetWriter dw) {
        System.out.println("Cube Size=" + size);
        System.out.println("Flat Size=" + flatSize);
        System.out.println("Flat Obs Lengths:" + getFlatObsLengths());
        dw.newDataSet();
        IntCartesianProduct cartesianProduct = new IntCartesianProduct(getFlatObsLengths());
        long i = 0;
        while (cartesianProduct.hasNext()) {
            int[] result = null;
            FlatObs obs = null;
            result = cartesianProduct.next();
            obs = findFlatObs(toFlatObsKey(result, struct, reg, this));
            if (obs != null) {
                dw.newObservation();
                for (int j = 0; j < obs.size(); j++) {
                    if (obs.getValue(j) != null && !obs.getValue(j).equals("")) {
                        dw.writeObservationComponent(getColumnMapper().getColumnName(j), obs.getValue(j));
                    }
                }
                dw.finishObservation();
            }
        }
        dw.finishDataSet();
    }

    public static FullKey toFullKey(int[] result, DataStructureType struct, Registry reg, Cube cube) {
        LinkedHashMap<String, Object> key = new LinkedHashMap<String, Object>();
        int i = 0;
        for (; i < struct.getDataStructureComponents().getDimensionList().size(); i++) {
            DimensionType dim = struct.getDataStructureComponents().getDimensionList().getDimension(i);
            key.put(dim.getId().toString(), cube.getValues(dim.getId().toString()).get(result[i]));
            //System.out.print(dim.getId().toString()+":"+result[i]+":"+cube.getValues(dim.getId().toString()).get(result[i])+" ");
        }
        if (struct.getDataStructureComponents().getDimensionList().getTimeDimension() != null) {
            TimeDimensionType td = struct.getDataStructureComponents().getDimensionList().getTimeDimension();
            key.put(td.getId().toString(), cube.getValues(td.getId().toString()).get(result[i]));
            //System.out.println(td.getId().toString()+":"+result[i]+":"+cube.getValues(td.getId().toString()).get(result[i]));
        }
        /*
        if (struct.getDataStructureComponents().getDimensionList().getMeasureDimension() != null) {
            MeasureDimensionType md = struct.getDataStructureComponents().getDimensionList().getMeasureDimension();
            key.put(md.getId().toString(), cube.getValues(md.getId().toString()).get(result[i++]));
        }*/
        return new FullKey(key);
    }

    public static FullKey toFlatObsKey(int[] result, DataStructureType struct, Registry reg, Cube cube) {
        LinkedHashMap<String, Object> key = new LinkedHashMap<String, Object>();
        int i = 0;
        for (; i < struct.getDataStructureComponents().getDimensionList().size(); i++) {
            DimensionType dim = struct.getDataStructureComponents().getDimensionList().getDimension(i);
            key.put(dim.getId().toString(), cube.getValues(dim.getId().toString()).get(result[i]));
            //System.out.print(dim.getId().toString()+":"+result[i]+":"+cube.getValues(dim.getId().toString()).get(result[i])+" ");
        }
        if (struct.getDataStructureComponents().getDimensionList().getTimeDimension() != null) {
            TimeDimensionType td = struct.getDataStructureComponents().getDimensionList().getTimeDimension();
            key.put(td.getId().toString(), cube.getValues(td.getId().toString()).get(result[i]));
            //System.out.println(td.getId().toString()+":"+result[i]+":"+cube.getValues(td.getId().toString()).get(result[i]));
        }
        i++;
        if (struct.getDataStructureComponents().getDimensionList().getMeasureDimension() != null) {
            MeasureDimensionType md = struct.getDataStructureComponents().getDimensionList().getMeasureDimension();
            key.put(md.getId().toString(), cube.getValues(md.getId().toString()).get(result[i]));
        }
        return new FullKey(key);
    }

    /**
     * @return the flatSize
     */
    public long getFlatSize() {
        return flatSize;
    }

}

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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import sdmx.data.ColumnMapper;
import sdmx.data.key.FullKey;
import sdmx.structure.base.Component;
import sdmx.structure.codelist.CodelistType;
import sdmx.structure.concept.ConceptType;

/*
     TimeDimension will only ever have cross sectional observation values here.
 */
public class TimeCubeDimension extends CubeDimension {

    List<CubeObservation> obs = Collections.synchronizedList(new LinkedList<CubeObservation>());

    public TimeCubeDimension(String concept, String value) {
        super(concept, value);
    }

    public Collection<CubeObservation> listObservations() {
        return obs;
    }

    public void putObservation(CubeObservation sub) {
        obs.add(sub);
    }

    public CubeObservation getObservation(String id) {
        Iterator<CubeObservation> it = obs.iterator();
        while (it.hasNext()) {
            CubeObservation c = it.next();
            if (c.getCrossSection() == null) {
                return c;
            }
            if (c.getCrossSection().equals(id)) {
                return c;
            }
        }
        return null;
    }

    public Set<String> listObservationValues() {
        TreeSet<String> crosssections = new TreeSet<String>();
        Iterator<CubeObservation> it = obs.iterator();
        while (it.hasNext()) {
            crosssections.add(it.next().getCrossSection());
        }
        return crosssections;
    }

    @Override
    public CubeDimension getSubDimension(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void putSubDimension(CubeDimension sub) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<CubeDimension> listSubDimensions() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Set<String> listDimensionValues() {
        return Collections.EMPTY_SET;
    }

    public void dump() {
        Iterator<CubeObservation> it = obs.iterator();
        while (it.hasNext()) {
            CubeObservation obs = it.next();
            System.out.println("Time:" + this.getValue() + ": Cross Section = " + obs.getCrossSection() + " Value=" + obs.getValue());
        }
    }

    public CubeObs toCubeObs(FullKey key, ColumnMapper mapper) {
        CubeObs f = new CubeObs(mapper);
        Iterator<String> it = key.keySet().iterator();
        while (it.hasNext()) {
            String s = it.next();
            String v = (String) key.getComponent(s);
            f.addValue(s, v);
        }
        // Cross Sectional Data
        boolean hasCrossSection = false;
        Iterator<CubeObservation> cubeObs = this.obs.iterator();
        if (this.obs.get(0).getCrossSection() != null) {
            hasCrossSection = true;
        }
        while (cubeObs.hasNext()) {
            CubeObservation o = cubeObs.next();
            if (o.getCrossSection() != null) {
                f.addValue(o.getCrossSection(), o.getValue());
                Iterator<String> it2 = o.map.keySet().iterator();
                while (it2.hasNext()) {
                    String s2 = it2.next();
                    CubeAttribute ca = (CubeAttribute) o.map.get(s2);
                    // Attributes
                    f.addValue(ca.getConcept(), ca.getValue());
                }
            }
        }
        if (hasCrossSection) {
            return f;
        }
        if (!hasCrossSection) {
            CubeObservation o = this.obs.get(0);
            if (this.obs.size() > 1) {
                throw new RuntimeException("Cross Sectional Observation in non cross sectional data!?");
            }
            // Observation has no cross section, drop out of this loop
            f.addValue(o.getObservationConcept(), o.getValue());
            Iterator<String> it2 = o.map.keySet().iterator();
            while (it2.hasNext()) {
                String s2 = it2.next();
                CubeAttribute ca = (CubeAttribute) o.map.get(s2);
                // Attributes
                f.addValue(ca.getConcept(), ca.getValue());
            }
        }
        return f;
    }
}

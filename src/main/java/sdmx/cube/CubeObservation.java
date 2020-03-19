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

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import sdmx.data.ColumnMapper;
import sdmx.data.flat.FlatObs;
import sdmx.data.key.FullKey;
import sdmx.structure.base.Component;
import sdmx.structure.codelist.CodelistType;
import sdmx.structure.concept.ConceptType;

public class CubeObservation {

    HashMap<String, CubeAttribute> map = new HashMap<>();

    private String concept = null;
    private String cross = null;
    private String observationConcept = null;
    private String value = null;

    public CubeObservation(String concept, String cross, String observationConcept, String value) {
        this.concept = concept;
        this.cross = cross;
        this.observationConcept = observationConcept;
        this.value = value;
    }

    public CubeAttribute getAttribute(String id) {
        return map.get(id);
    }

    public void putAttribute(CubeAttribute sub) {
        map.put(sub.getValue(), sub);
    }

    public Collection<CubeAttribute> listAttributes() {
        return map.values();
    }

    public Set<String> listAttributeNames() {
        return map.keySet();
    }

    /**
     * @return the concept
     */
    public String getCrossSection() {
        return cross;
    }

    /**
     * @param concept the concept to set
     */
    public void setCrossSection(String concept) {
        this.cross = concept;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the concept
     */
    public String getConcept() {
        return concept;
    }

    /**
     * @param concept the concept to set
     */
    public void setConcept(String concept) {
        this.concept = concept;
    }

    /**
     * @return the observationConcept
     */
    public String getObservationConcept() {
        return observationConcept;
    }

    /**
     * @param observationConcept the observationConcept to set
     */
    public void setObservationConcept(String observationConcept) {
        this.observationConcept = observationConcept;
    }

    public FlatObs toFlatObs(FullKey key,ColumnMapper mapper) {
        FlatObs f = new FlatObs(mapper.size());
        Iterator<String> it = mapper.getAllColumns().iterator();
        while(it.hasNext()) {
            String s = it.next();
            String v = (String)key.getComponent(s);
            f.setValue(mapper.getColumnIndex(s), v);
        }
        // Cross Sectional Data
        if(this.concept!=null){f.setValue(mapper.getColumnIndex(this.concept), this.cross);}
        f.setValue(mapper.getColumnIndex(this.observationConcept), this.value);
        Iterator<String> it2 = this.map.keySet().iterator();
        while(it2.hasNext()){
            String s2 = it2.next();
            CubeAttribute ca = (CubeAttribute)map.get(s2);
            // Attributes
            f.setValue(mapper.getColumnIndex(ca.getConcept()), ca.getValue());
        }
        return f;
    }
}

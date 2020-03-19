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
import sdmx.structure.base.Component;
import sdmx.structure.codelist.CodelistType;
import sdmx.structure.concept.ConceptType;

public class ListCubeDimension extends CubeDimension {

    List<CubeDimension> list = Collections.synchronizedList(new LinkedList<CubeDimension>());
    
    public ListCubeDimension(String concept,String value){
        super(concept,value);
        if( concept == null ) {
            System.out.println("Concept is null value = "+value);
        }
    }
    
    @Override
    public CubeDimension getSubDimension(String id) {
        Iterator<CubeDimension> it = list.iterator();
        for(int i=0;i<list.size();i++) {
            CubeDimension cd = list.get(i);
            if( cd.getValue().equals(id))return cd;
        }
        /*
        while(it.hasNext()){
            CubeDimension cd = it.next();
            if( cd.getValue().equals(id))return cd;
        }*/
        return null;
    }

    @Override
    public void putSubDimension(CubeDimension sub) {
        CubeDimension old = getSubDimension(sub.getValue());
        if( old!=null ) list.remove(old);
        list.add(sub);
        setSubDimension(sub.getConcept());
    }

    @Override
    public Collection<CubeDimension> listSubDimensions() {
        return list;
    }
    @Override
    public Set<String> listDimensionValues() {
        Set set = new TreeSet();
        for(int i=0;i<list.size();i++) {
            set.add(list.get(i).getValue());
        }
        return set;
    }
    public void dump() {
        System.out.println("Dim:"+getConcept()+":"+getValue());
        System.out.println("SubDims");
        Iterator<CubeDimension> it = this.listSubDimensions().iterator();
        while(it.hasNext()) {
            it.next().dump();
        }
        System.out.println("End Dim:"+getConcept()+":"+getValue());
    }
}

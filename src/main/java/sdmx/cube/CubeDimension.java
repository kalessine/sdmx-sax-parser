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
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.SortedSet;
import sdmx.common.DataType;
import sdmx.common.Description;
import sdmx.common.Name;
import sdmx.commonreferences.ConceptReference;
import sdmx.structure.base.Component;
import sdmx.structure.base.RepresentationType;
import sdmx.structure.codelist.CodeType;
import sdmx.structure.codelist.CodelistType;
import sdmx.structure.concept.ConceptSchemeType;
import sdmx.structure.concept.ConceptType;
import sdmx.structureddata.ValueTypeResolver;

public abstract class CubeDimension {

    private String concept = null;
    private String value = null;
   
    private String subDimension = null;
    
    public CubeDimension(String concept, String value){
        this.concept=concept;
        this.value=value;
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

    public abstract CubeDimension getSubDimension(String id);

    public abstract void putSubDimension(CubeDimension sub);

    public abstract Collection<CubeDimension> listSubDimensions();
    public abstract Set<String> listDimensionValues();

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
    public void dump() {
        System.out.println("Dim:"+this.concept+":"+this.value);
        System.out.println("SubDims");
        Iterator<CubeDimension> it = this.listSubDimensions().iterator();
        while(it.hasNext()) {
            it.next().dump();
        }
        System.out.println("End Dim:"+this.concept+":"+this.value);
    }

    /**
     * @return the subDimension
     */
    public String getSubDimension() {
        return subDimension;
    }

    /**
     * @param subDimension the subDimension to set
     */
    public void setSubDimension(String subDimension) {
        this.subDimension = subDimension;
    }
}

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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
public class RootCubeDimension extends CubeDimension {
    
    HashMap<String,CubeDimension> map = new HashMap<>();
    
    public RootCubeDimension(){
        super(null,null);
    }
    
    @Override
    public CubeDimension getSubDimension(String id) {
        return map.get(id);
    }

    @Override
    public void putSubDimension(CubeDimension sub) {
        this.setSubDimension(sub.getConcept());
        map.put(sub.getValue(),sub);
    }

    @Override
    public Collection<CubeDimension> listSubDimensions() {
        return map.values();
    }
    @Override
    public Set<String> listDimensionValues() {
        return map.keySet();
    }
   public void dump() {
        System.out.println("Root");
        System.out.println("SubDims");
        Iterator<CubeDimension> it = this.listSubDimensions().iterator();
        while(it.hasNext()) {
            it.next().dump();
        }
        System.out.println("End Root");
    }
}

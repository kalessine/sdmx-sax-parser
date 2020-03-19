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

package sdmx.data.key;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import sdmx.data.ColumnMapper;
import sdmx.data.flat.FlatObs;
import sdmx.structure.base.NameableType;

/**
 * FullKey matches only a single observation
 * @author James
 */

public class FullKey extends AbstractKey {
    public FullKey() {
    }
    public FullKey(LinkedHashMap<String,Object> map) {
        super(map);
    }
    public FullKey(LinkedHashMap<String,Object> map,LinkedHashMap<String,Object> attrs) {
        super(map,attrs);
    }
    public FullKey(FlatObs obs, ColumnMapper mapper) {
        for(int i=0;i<mapper.size();i++) {
            //System.out.println("Cname="+mapper.getColumnName(i)+":ov="+obs.getValue(i));
            setComponent(mapper.getColumnName(i),obs.getValue(i));
        }
    }
    public FullKey clone() {
        return new FullKey((LinkedHashMap<String,Object>)map.clone());
    }

    public void dump() {
        Iterator<String> it = map.keySet().iterator();
        while(it.hasNext()) {
            String s = it.next();
            System.out.print(s+":"+map.get(s));
            if(it.hasNext())System.out.print(",");
        }
        System.out.println();
    }
    public FullKey toStringKey(){
        FullKey key = new FullKey();
        Iterator<String> it = this.keySet().iterator();
        while(it.hasNext()) {
            String k = it.next();
            key.setComponent(k, NameableType.toIDString(this.getComponent(k)));
        }
        return key;
    }
    
}

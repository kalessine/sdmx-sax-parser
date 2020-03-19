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
import java.util.Set;
import sdmx.data.ColumnMapper;
import sdmx.data.flat.FlatObs;

/**
 *
 * @author James
 */

public class AbstractKey implements Key {

    protected LinkedHashMap<String,Object> map = new LinkedHashMap<String,Object>();
    protected LinkedHashMap<String,Object> attrs = new LinkedHashMap<String,Object>();

    public AbstractKey(){}
    public AbstractKey(LinkedHashMap<String,Object> map){
        this.map=map;
    }
    public AbstractKey(LinkedHashMap<String,Object> map,LinkedHashMap<String,Object> attrs){
        this.map=map;
        this.attrs=attrs;
    }
    
    @Override
    public Object getComponent(String dim) {
        return map.get(dim);
    }

    @Override
    public void setComponent(String dim, Object o) {
        map.put(dim, o);
    }
    @Override
    public Object getAttribute(String dim) {
        return attrs.get(dim);
    }

    @Override
    public void setAttribute(String dim, Object o) {
        attrs.put(dim, o);
    }
    public void clearAttributes(){
        attrs.clear();
    }

    @Override
    public boolean equals(Key key) {
        Iterator<String> it = map.keySet().iterator();
        while (it.hasNext()) {
            String s = it.next();
            if (!getComponent(s).equals(key.getComponent(s))) {
                return false;
            }
        }
        return true;
    }

    public boolean matches(FlatObs obs, ColumnMapper mapper) {
        Iterator<String> it = map.keySet().iterator();
        while (it.hasNext()) {
            String s = it.next();
            if (!getComponent(s).equals(obs.getValue(mapper.getColumnIndex(s)))) {
                return false;
            }

        }
        return true;
    }
    public boolean notMatches(FlatObs obs, ColumnMapper mapper) {
        Iterator<String> it = map.keySet().iterator();
        while (it.hasNext()) {
            String s = it.next();
            if (getComponent(s).equals(obs.getValue(mapper.getColumnIndex(s)))) {
                return true;
            }

        }
        return false;
    }
    public Set<String> keySet() {
        return map.keySet();
    }
    public Set<String> attributeKeySet() {
        return attrs.keySet();
    }
    public void dump() {
        System.out.println("Dump Key");
        Iterator<String> it = keySet().iterator();
        while(it.hasNext()) {
            String s = it.next();
            System.out.println("Key:"+s+":"+getComponent(s));
        }
        it = attrs.keySet().iterator();
        while(it.hasNext()) {
            String s = it.next();
            System.out.println("Attribute:"+s+":"+getAttribute(s));
        }
    }
    public LinkedHashMap<String,Object> getKeyMap() {
        return this.map;
    }
}

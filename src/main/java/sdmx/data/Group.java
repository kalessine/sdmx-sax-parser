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
package sdmx.data;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import sdmx.data.key.FullKey;
import sdmx.data.key.Key;
import sdmx.data.key.PartialKey;

/**
 *
 * @author James
 */

public class Group {
    private String groupName = null;
    private LinkedHashMap<String,Object> groupKey = new LinkedHashMap<String,Object>();
    private HashMap<String,Object> groupAttributes = new HashMap<String,Object>();

    HashMap<String, Object> map = new HashMap<String, Object>();

    public Group() {
    }

    public Group(HashMap<String, Object> groupValues) {
        this.map=groupValues;
    }

    public void putGroupValue(String concept, Object value) {
        map.put(concept, value);
    }

    public Object getGroupValue(String concept) {
        return groupAttributes.get(concept);
    }

    public void processGroupValues(DataSet ds) {
        groupAttributes = new HashMap<String, Object>();
        Iterator<String> it = map.keySet().iterator();
        while (it.hasNext()) {
            String s = it.next();
            if (ds.getColumnMapper().getColumnIndex(s) == -1||ds.getColumnMapper().isAttachedToGroup(s)) {
                //System.out.println("Group Dim:"+s);
                groupAttributes.put(s, map.get(s));
                if(!ds.getColumnMapper().isAttachedToGroup(s)){
                    //System.out.println("Register:"+s);
                    ds.getColumnMapper().registerColumn(s, AttachmentLevel.GROUP);
                }
                it.remove();
            }else {
                //System.out.println("Group Attr:"+s);
                getGroupKey().put(s, map.get(s));
                it.remove();
            }
        }
        this.map=null;
    }
    public boolean matches(FullKey key) {
        Iterator<String> it = getGroupKey().keySet().iterator();
        while(it.hasNext()){
            String s = it.next();
            Object gv = getGroupKey().get(s);
            if( gv!=null) {
                if( !gv.equals(key.getComponent(s))){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @return the groupKey
     */
    public LinkedHashMap<String,Object> getGroupKey() {
        return groupKey;
    }

    /**
     * @return the groupAttributes
     */
    public HashMap<String,Object> getGroupAttributes() {
        return groupAttributes;
    }

    /**
     * @return the groupName
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * @param groupName the groupName to set
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setGroupValue(String columnName, String val) {
        groupAttributes.put(columnName, val);
    }
}


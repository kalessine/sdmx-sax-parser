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
package sdmx.structure.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import sdmx.commonreferences.IDType;
import sdmx.commonreferences.LocalItemReference;
import sdmx.commonreferences.NestedID;

/**
 *
 * @author James
 */
public abstract class ItemType extends ItemBaseType {

    private LocalItemReference parent = null;
    private List<ItemType> items = new ArrayList<ItemType>(0);

    /**
     * @return the parent
     */
    public LocalItemReference getParent() {
        return parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(LocalItemReference parent) {
        this.parent = parent;
    }

    /**
     * @return the items
     */
    public List<ItemType> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(List<ItemType> items) {
        this.items = items;
    }

    public ItemType getItem(int i) {
        return items.get(i);
    }

    public void setItem(int i, ItemType it) {
        items.set(i, it);
    }

    public void removeItem(ItemType it) {
        items.remove(it);
    }

    public void addItem(ItemType it) {
        items.add(it);
    }

    public int size() {
        return items.size();
    }

    public ItemType findItem(String s) {
        return findItem(new IDType(s));
    }

    public ItemType findItem(IDType id) {
        if (id == null) {
            return null;
        }
        for (int i = 0; i < items.size(); i++) {
            ItemType itm = items.get(i).findItem(id);
            if (itm != null) {
                return itm;
            }
            if (items.get(i).identifiesMe(id)) {
                return items.get(i);
            }
        }
        return null;
    }

    public ItemType findItem(NestedID id) {
        if (id == null) {
            return null;
        }
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).identifiesMe(id)) {
                return items.get(i);
            }
            ItemType itm = items.get(i).findItem(id);
            if (itm != null) {
                return itm;
            }
        }
        return null;
    }

    public boolean equals(Object o) {
        if (o instanceof ItemType) {
            ItemType o1 = (ItemType) o;
            if (o1.getId().equals(this.getId()) && o1.getName().equals(this.getName())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}

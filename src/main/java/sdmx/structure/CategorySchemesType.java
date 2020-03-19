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
package sdmx.structure;

import java.util.ArrayList;
import java.util.List;
import sdmx.commonreferences.IDType;
import sdmx.commonreferences.NestedID;
import sdmx.commonreferences.NestedNCNameID;
import sdmx.commonreferences.Version;
import sdmx.structure.categorisation.CategorisationType;
import sdmx.structure.category.CategorySchemeType;

/**
 *
 * @author James
 */

public class CategorySchemesType {
    private List<CategorySchemeType> categorySchemes = new ArrayList<CategorySchemeType>();

    /**
     * @return the categorySchemes
     */
    public List<CategorySchemeType> getCategorySchemes() {
        return categorySchemes;
    }

    /**
     * @param categorySchemes the categorySchemes to set
     */
    public void setCategorySchemes(List<CategorySchemeType> categorySchemes) {
        this.categorySchemes = categorySchemes;
    }
    public CategorySchemeType findCategoryScheme(String agency,String id,String vers) {
        IDType findid = new IDType(id);
        NestedNCNameID ag = new NestedNCNameID(agency);
        Version ver = new Version(vers);
        return findCategoryScheme(ag,findid,ver);
    }
    public CategorySchemeType findCategoryScheme(NestedNCNameID agency2,NestedID findid,Version ver) {
        for(int i=0;i<categorySchemes.size();i++) {
            if( categorySchemes.get(i).identifiesMe(agency2,findid,ver)) {
                return categorySchemes.get(i);
            }
        }
        return null;
    }
    public void dump() {
        for(int i=0;i<categorySchemes.size();i++) {
            categorySchemes.get(i).dump();
        }
    }

    public void merge(CategorySchemesType categorySchemes) {
        this.getCategorySchemes().addAll(categorySchemes.getCategorySchemes());
    }
}

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
package sdmx.structure.datastructure;

import java.util.ArrayList;
import java.util.List;
import sdmx.structure.base.Component;
import sdmx.structure.base.ComponentListType;

/**
 *
 * @author James
 */

public class MeasureListType extends ComponentListType {
    private PrimaryMeasure primary = null;
/*
    private List<MeasureDimensionType> measures = new ArrayList<MeasureDimensionType>();
   public List<MeasureDimensionType> getMeasures() {
       return measures;
   }
   
   public void setMeasures(List<MeasureDimensionType> at) {
       measures = at;
   }
   
   public MeasureDimensionType getMeasure(int i) {
       return measures.get(i);
   }
  */  
    /**
     * @return the primary
     */
    public PrimaryMeasure getPrimaryMeasure() {
        return primary;
    }

    /**
     * @param primary the primary to set
     */
    public void setPrimaryMeasure(PrimaryMeasure primary) {
        this.primary = primary;
    }
    public int size() {
        return 1;
    }
    
}

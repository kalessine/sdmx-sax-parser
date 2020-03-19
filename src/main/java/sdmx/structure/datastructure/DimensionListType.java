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

/**
 *
 * @author James
 */

public class DimensionListType extends DimensionListBaseType {

    
    private List<DimensionType> dimensions = new ArrayList<DimensionType>();
    private MeasureDimensionType measureDimension = null;
    private TimeDimensionType timeDimension = null;

    public List<DimensionType> getDimensions() {
        return dimensions;
    }

    public void setDimensions(List<DimensionType> at) {
        this.dimensions = at;
    }

    public DimensionType getDimension(int i) {
        return dimensions.get(i);
    }

    /**
     * @return the timeDimension
     */
    public TimeDimensionType getTimeDimension() {
        return timeDimension;
    }

    /**
     * @param timeDimension the timeDimension to set
     */
    public void setTimeDimension(TimeDimensionType timeDimension) {
        this.timeDimension = timeDimension;
    }
    public int size() { return dimensions.size(); }

    /**
     * @return the measureDimension
     */
    public MeasureDimensionType getMeasureDimension() {
        return measureDimension;
    }

    /**
     * @param measureDimension the measureDimension to set
     */
    public void setMeasureDimension(MeasureDimensionType measureDimension) {
        this.measureDimension = measureDimension;
    }
    public void addDimension(DimensionType dim) {
        this.dimensions.add(dim);
    }
}

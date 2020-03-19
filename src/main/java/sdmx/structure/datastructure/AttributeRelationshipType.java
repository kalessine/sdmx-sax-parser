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

import java.util.List;
import sdmx.commonreferences.LocalDimensionReference;
import sdmx.commonreferences.LocalGroupKeyDescriptorReference;
import sdmx.commonreferences.LocalPrimaryMeasureReference;

/**
 *
 * @author James
 */

public class AttributeRelationshipType {
// One of;
    // a
    private Boolean empty = false;
    // b
    private List<LocalDimensionReference> dimensions = null;
    private LocalGroupKeyDescriptorReference attachGroup = null;
    // c
    private List<LocalGroupKeyDescriptorReference> groups = null;
    // d
    private LocalPrimaryMeasureReference primaryMeasure = null;

    /**
     * @return the empty
     */
    public boolean isEmpty() {
        return empty==null?false:empty.booleanValue();
    }

    /**
     * @param empty the empty to set
     */
    public void setEmpty(boolean empty) {
        this.empty = empty;
    }


    /**
     * @return the attachGroup
     */
    public LocalGroupKeyDescriptorReference getAttachGroup() {
        return attachGroup;
    }

    /**
     * @param attachGroup the attachGroup to set
     */
    public void setAttachGroup(LocalGroupKeyDescriptorReference attachGroup) {
        this.attachGroup = attachGroup;
    }

    /**
     * @return the primaryMeasure
     */
    public LocalPrimaryMeasureReference getPrimaryMeasure() {
        return primaryMeasure;
    }

    /**
     * @param primaryMeasure the primaryMeasure to set
     */
    public void setPrimaryMeasure(LocalPrimaryMeasureReference primaryMeasure) {
        this.primaryMeasure = primaryMeasure;
    }

    /**
     * @return the groups
     */
    public List<LocalGroupKeyDescriptorReference> getGroups() {
        return groups;
    }

    /**
     * @param groups the groups to set
     */
    public void setGroups(List<LocalGroupKeyDescriptorReference> groups) {
        this.groups = groups;
    }

    /**
     * @return the dimensions
     */
    public List<LocalDimensionReference> getDimensions() {
        return dimensions;
    }

    /**
     * @param dimensions the dimensions to set
     */
    public void setDimensions(List<LocalDimensionReference> dimensions) {
        this.dimensions = dimensions;
    }

  
    
    
    
}

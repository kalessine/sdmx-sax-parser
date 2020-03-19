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
package sdmx.util;

import sdmx.Registry;
import sdmx.common.ObservationDimensionType;
import sdmx.commonreferences.DataStructureReference;
import sdmx.message.DataMessage;
import sdmx.structure.datastructure.DataStructureType;
import sdmx.structure.datastructure.DimensionType;

/**
 *
 * @author James
 */
public class PostParseUtilities {
    // Sdmx 2.0 files frequently don't have Structure References to the Data Structure
    public static void setStructureReference(DataMessage data, DataStructureReference ref) {
        data.setDataStructure(ref, null);
    }
    // Sdmx 2.0 files don't have a dimension at observation
    public static void setDimensionAtObservation(DataMessage data,Registry reg, DataStructureReference ref) {
        DataStructureType struct = reg.find(ref);
        for(DimensionType d:struct.getDataStructureComponents().getDimensionList().getDimensions()){
            if( data.getDataSets().get(0).getColumnMapper().isAttachedToObservation(d.getId().toString())){
                data.getHeader().getStructures().get(0).setDimensionAtObservation(new ObservationDimensionType(d.getId().toString()));
            }
        }
        if( data.getDataSets().get(0).getColumnMapper().isAttachedToObservation(struct.getDataStructureComponents().getDimensionList().getTimeDimension().getId().toString())){
            data.getHeader().getStructures().get(0).setDimensionAtObservation(new ObservationDimensionType(struct.getDataStructureComponents().getDimensionList().getTimeDimension().getId().toString()));
        }
    }
}

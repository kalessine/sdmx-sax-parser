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
package sdmx.query.data;

import org.sdmx.resources.sdmxml.schemas.v21.query.ObservationActionCodeType;
import sdmx.common.DataStructureRequestType;

/**
 *
 * @author James
 */

public class DataReturnDetailsType extends DataReturnDetailsBaseType {
    private Integer firstNObservations = null;
    private Integer lastNObservations = null;
    private DataStructureRequestType structure = null;
    private ObservationActionCodeType observationAction = null;

    /**
     * @return the firstNObservations
     */
    public Integer getFirstNObservations() {
        return firstNObservations;
    }

    /**
     * @param firstNObservations the firstNObservations to set
     */
    public void setFirstNObservations(Integer firstNObservations) {
        this.firstNObservations = firstNObservations;
    }

    /**
     * @return the lastNObservations
     */
    public Integer getLastNObservations() {
        return lastNObservations;
    }

    /**
     * @param lastNObservations the lastNObservations to set
     */
    public void setLastNObservations(Integer lastNObservations) {
        this.lastNObservations = lastNObservations;
    }

    /**
     * @return the structure
     */
    public DataStructureRequestType getStructure() {
        return structure;
    }

    /**
     * @param structure the structure to set
     */
    public void setStructure(DataStructureRequestType structure) {
        this.structure = structure;
    }

    /**
     * @return the observationAction
     */
    public ObservationActionCodeType getObservationAction() {
        return observationAction;
    }

    /**
     * @param observationAction the observationAction to set
     */
    public void setObservationAction(ObservationActionCodeType observationAction) {
        this.observationAction = observationAction;
    }
}

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
package sdmx.query.datastructure;

import sdmx.message.QueryMessage;
import sdmx.query.base.MaintainableReturnDetailsType;

/**
 *
 * @author James
 */

public class DataStructureQueryType extends QueryMessage {
    private MaintainableReturnDetailsType returnDetails = null;
    private DataStructureWhereType dataStructureWhere = null;

    /**
     * @return the returnDetails
     */
    public MaintainableReturnDetailsType getReturnDetails() {
        return returnDetails;
    }

    /**
     * @param returnDetails the returnDetails to set
     */
    public void setReturnDetails(MaintainableReturnDetailsType returnDetails) {
        this.returnDetails = returnDetails;
    }

    /**
     * @return the dataStructureWhere
     */
    public DataStructureWhereType getDataStructureWhere() {
        return dataStructureWhere;
    }

    /**
     * @param dataStructureWhere the dataStructureWhere to set
     */
    public void setDataStructureWhere(DataStructureWhereType dataStructureWhere) {
        this.dataStructureWhere = dataStructureWhere;
    }
}

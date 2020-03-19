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

import java.util.List;
import sdmx.commonreferences.NCNameID;
import sdmx.query.base.NumericValue;
import sdmx.query.base.TextValue;
import sdmx.query.base.TimeValue;

/**
 *
 * @author James
 */

public class DataStructureComponentValueQueryType {
    private NCNameID id = null;
    private List<NumericValue> numericValues = null;
    private List<TextValue> textValues = null;
    private List<TimeValue> timeValues = null;
    private String value = null;

    /**
     * @return the id
     */
    public NCNameID getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(NCNameID id) {
        this.id = id;
    }

    /**
     * @return the numericValues
     */
    public List<NumericValue> getNumericValues() {
        return numericValues;
    }

    /**
     * @param numericValues the numericValues to set
     */
    public void setNumericValues(List<NumericValue> numericValues) {
        this.numericValues = numericValues;
    }

    /**
     * @return the textValue
     */
    public List<TextValue> getTextValues() {
        return textValues;
    }

    /**
     * @param textValue the textValue to set
     */
    public void setTextValues(List<TextValue> textValue) {
        this.textValues = textValue;
    }

    /**
     * @return the timeValue
     */
    public List<TimeValue> getTimeValues() {
        return timeValues;
    }

    /**
     * @param timeValue the timeValue to set
     */
    public void setTimeValues(List<TimeValue> timeValue) {
        this.timeValues = timeValue;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
}

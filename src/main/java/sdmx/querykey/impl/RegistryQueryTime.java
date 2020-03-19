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
package sdmx.querykey.impl;

import java.util.Date;
import org.jfree.data.time.RegularTimePeriod;
import sdmx.Registry;
import sdmx.querykey.QueryTime;
import sdmx.structure.datastructure.DataStructureType;
import sdmx.util.time.TimeUtil;

/**
 *
 * @author James
 */
public class RegistryQueryTime extends RegistryQueryDimension implements QueryTime {
    String concept = null;
    Registry reg = null;
    DataStructureType struct = null;
    Date startTime = new Date();
    Date endTime = new Date();
    public RegistryQueryTime(String concept,DataStructureType struct,Registry reg){
        super(concept,struct,reg);
        this.concept=concept;
        this.struct=struct;
        this.reg=reg;
        
    }
    public Date getStartTime() { return this.startTime; }
    public void setStartTime(Date d) { this.startTime=d; }
    public Date getEndTime() { return this.endTime; }
    public void setEndTime(Date d) { this.endTime=d; }    
    public void parseStartTime(String s) {
        RegularTimePeriod rtp = TimeUtil.parseTime(s, s);
        this.setStartTime(rtp.getStart());
    }
    public void parseEndTime(String s) {
        RegularTimePeriod rtp = TimeUtil.parseTime(s, s);
        this.setEndTime(rtp.getEnd());
    }
}

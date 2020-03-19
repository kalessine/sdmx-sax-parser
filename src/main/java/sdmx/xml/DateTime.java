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
package sdmx.xml;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import sdmx.common.BasicTimePeriodType;
import sdmx.version.twopointzero.compact.CompactDataEventHandler;

/**
 *
 * @author James
 */

public class DateTime implements Serializable {

    public static final SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
    public static final SimpleDateFormat DF2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private String baseString = null;
    java.util.Date date = null;
    Calendar calendar = null;

    public DateTime(Date d) {
        this.date = d;
        this.calendar = Calendar.getInstance();
        calendar.setTime(date);
    }

    public DateTime(Calendar c) {
        this.date = c.getTime();
        this.calendar = c;
    }

    public Calendar toCalendar() {
        return calendar;
    }

    public Date getDate() {
        return date;
    }

    public static DateTime fromString(String s) throws ParseException {
        if (s == null || "".equals(s)) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(DF.parse(s));
        } catch (ParseException ex) {
            try {
                cal.setTime(DF2.parse(s));
            } catch (ParseException ex2) {
                Logger.getLogger(CompactDataEventHandler.class
                        .getName()).log(Level.SEVERE, null, ex2);
            }
        }
        DateTime dt = new DateTime(cal);
        dt.setBaseString(s);
        return dt;
    }
    public String toString() {
        if( baseString!=null) return baseString;
        return DF.format(calendar.getTime());
    }
    public static DateTime now() {
        return new DateTime(Calendar.getInstance());
    }
    public void setBaseString(String s) {
        this.baseString=s;
    }
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.writeLong(date.getTime());
        oos.writeObject(calendar);
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        this.date = new Date(ois.readLong());
        this.calendar = (Calendar)ois.readObject();
    }
}

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
package sdmx.data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author James
 */

public class AttachmentLevel {
    private static final List<AttachmentLevel> LIST = new ArrayList<AttachmentLevel>();
    
    public static void main(String args[]) {}
    public static final int ATTACHMENT_DATASET = 0;
    public static final int ATTACHMENT_SERIES = 1;
    public static final int ATTACHMENT_OBSERVATION = 2;
    public static final int ATTACHMENT_GROUP = 3;
    public static final String ATTACHMENT_DATASET_STRING = "DataSet";
    public static final String ATTACHMENT_SERIES_STRING = "Series";
    public static final String ATTACHMENT_OBSERVATION_STRING = "Observation";
    public static final String ATTACHMENT_GROUP_STRING = "Group";
    public static final AttachmentLevel DATASET = new AttachmentLevel(ATTACHMENT_DATASET_STRING,ATTACHMENT_DATASET);
    public static final AttachmentLevel SERIES = new AttachmentLevel(ATTACHMENT_SERIES_STRING,ATTACHMENT_SERIES);
    public static final AttachmentLevel OBSERVATION = new AttachmentLevel(ATTACHMENT_OBSERVATION_STRING,ATTACHMENT_OBSERVATION);
    public static final AttachmentLevel GROUP = new AttachmentLevel(ATTACHMENT_GROUP_STRING,ATTACHMENT_GROUP);    
    
    private String name = null;
    private int id = 0;
    
    
    
    public AttachmentLevel(String s,int id) {
        this.name=s;
        this.id=id;
        LIST.add(this);
    }
    public String getName() { return name; }
    public int getId() {return id; }
    public static AttachmentLevel fromString(String s) {
        for(int i=0;i<LIST.size();i++) {
            if( LIST.get(i).getName().equals(s))return LIST.get(i);
        }
        return null;        
    }    
    public static AttachmentLevel fromId(int id) {
        for(int i=0;i<LIST.size();i++) {
            if( LIST.get(i).getId()==id ) return LIST.get(i);
        }
        return null;        
    }    
    
}

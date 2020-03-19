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
package sdmx.net.service.nomis;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import sdmx.common.Name;
import sdmx.common.TextType;

/**
 *
 * @author James
 */
public class NOMISGeography {
    private String cubeId = "";
    private String cubeName = "";
    private String geography = "";
    private String geographyName = "";

    /**
     * @return the id
     */
    public String getCubeId() {
        return cubeId;
    }

    /**
     * @param id the id to set
     */
    public void setCubeId(String id) {
        this.cubeId = id;
    }

    /**
     * @return the geography
     */
    public String getGeography() {
        return geography;
    }

    /**
     * @param geography the geography to set
     */
    public void setGeography(String geography) {
        this.geography = geography;
    }

    /**
     * @return the geographyName
     */
    public String getGeographyName() {
        return geographyName;
    }

    /**
     * @param geographyName the geographyName to set
     */
    public void setGeographyName(String geographyName) {
        this.geographyName = geographyName;
    }

    /**
     * @return the cubeName
     */
    public String getCubeName() {
        return cubeName;
    }

    /**
     * @param cubeName the cubeName to set
     */
    public void setCubeName(String cubeName) {
        this.cubeName = cubeName;
    }

}

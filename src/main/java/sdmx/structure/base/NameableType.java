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
package sdmx.structure.base;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import sdmx.SdmxIO;
import sdmx.common.Description;
import sdmx.common.Name;
import sdmx.commonreferences.IDType;
import sdmx.structure.concept.ConceptType;
import sdmx.xml.anyURI;

/**
 * <xs:complexType name="NameableType" abstract="true">
 * <xs:annotation>
 * <xs:documentation>NameableType is an abstract base type for all nameable
 * objects.</xs:documentation>
 * </xs:annotation>
 * <xs:complexContent>
 * <xs:extension base="IdentifiableType">
 * <xs:sequence>
 * <xs:element ref="common:Name" maxOccurs="unbounded">
 * <xs:annotation>
 * <xs:documentation>Name provides for a human-readable name for the object.
 * This may be provided in multiple, parallel language-equivalent
 * forms.</xs:documentation>
 * </xs:annotation>
 * </xs:element>
 * <xs:element ref="common:Description" minOccurs="0" maxOccurs="unbounded">
 * <xs:annotation>
 * <xs:documentation>Description provides for a longer human-readable
 * description of the object. This may be provided in multiple, parallel
 * language-equivalent forms.</xs:documentation>
 * </xs:annotation>
 * </xs:element>
 * </xs:sequence>
 * </xs:extension>
 * </xs:complexContent>
 * </xs:complexType>
 *
 * @author James
 */
public class NameableType extends IdentifiableType {

    private List<Name> names = null;
    private List<Description> descriptions = null;

    /**
     * @return the names
     */
    public List<Name> getNames() {
        return names;
    }

    /**
     * @param names the names to set
     */
    public void setNames(List<Name> names1) {
        this.names = names1;
    }

    /**
     * @return the descriptions
     */
    public List<Description> getDescriptions() {
        return descriptions;
    }

    /**
     * @param descriptions the descriptions to set
     */
    public void setDescriptions(List<Description> descriptions) {
        this.descriptions = descriptions;
    }

    public Name findName(String lang) {
        if (names == null) {
            return null;
        }
        Name def = null;
        for (int i = 0; i < names.size(); i++) {
            if (lang != null && lang.equals(names.get(i).getLang())) {
                return names.get(i);
            }
            if (names.get(i).getLang() == null) {
                def = names.get(i);
            }
        }
        if (def == null && !"en".equals(lang)) {
            def = findName("en");
        }
        return def;
    }

    public Description findDescription(String lang) {
        if (descriptions == null) {
            return null;
        }
        Description def = null;
        for (int i = 0; i < descriptions.size(); i++) {
            if (lang != null && lang.equals(descriptions.get(i).getLang())) {
                return descriptions.get(i);
            }
            if (descriptions.get(i).getLang() == null) {
                def = descriptions.get(i);
            }
        }
        if (!"en".equals(lang)) {
            return findDescription("en");
        }
        return def;
    }

    public String toString() {
        Locale loc = Locale.getDefault();
        Name name = findName(loc.getLanguage());
        if (name != null) {
            return name.toString();
        }
        Description desc = findDescription(loc.getLanguage());
        if (desc != null) {
            return desc.getText();
        }
        return "NameableType";
    }

    public String getName() {
        if (SdmxIO.isSanitiseNames()) {
            return sanitise(toString(this));
        } else {
            return toString(this);
        }
    }

    private static String toString(NameableType named) {
        Locale loc = Locale.getDefault();
        //if (concept.equals("FREQ")) {
        //    ItemType code2 = getCode();
        //    System.out.println("FREQ Code=" + code2);
        //}
        if (named == null) {
            return "";
        }
        Description desc = named.findDescription(loc.getLanguage());
        if (desc == null) {
            Name name = named.findName(loc.getLanguage());
            if (name == null) {
                return named.getId().toString();
            }
            return name.getText();
        }
        return desc.getText();
    }

    private static String toString(NameableType named, Locale loc) {
        //if (concept.equals("FREQ")) {
        //    ItemType code2 = getCode();
        //    System.out.println("FREQ Code=" + code2);
        //}
        if (named == null) {
            return "";
        }
        Name name = named.findName(loc.getLanguage());
        if (name == null) {
            Description desc = named.findDescription(loc.getLanguage());
            if (desc == null) {
                return named.getId().toString();
            }
            return desc.getText();
        }
        return name.getText();

    }
    public static String toString(Object o) {
        if (o == null) {
            return "";
        }
        if (o instanceof String) {
            return (String) o;
        }
        if (o instanceof NameableType) {
            return toString((NameableType) o);
        }
        throw new RuntimeException("Not String or Nameable " + o.getClass());
    }

    public static String toString(Object o, Locale loc) {
        if (o == null) {
            return "";
        }
        if (o instanceof String) {
            return (String) o;
        }
        if (o instanceof NameableType) {
            return toString((NameableType) o, loc);
        }
        throw new RuntimeException("Not String or Nameable " + o.getClass());
    }

    private static String toIDString(NameableType named) {
        return named.getId().toString();
    }

    public static String toIDString(Object o) {
        if (o == null) {
            return "";
        }
        if (o instanceof String) {
            return (String) o;
        }
        if (o instanceof NameableType) {
            return toIDString((NameableType) o);
        }
        throw new RuntimeException("Not String or Nameable " + o.getClass());
    }

    public static String sanitise(String s) {
        return s;
        /*
         if (s.contains("'")) {
         s = s.replace("'", "&apos;");
         }
         if (s.contains("\"")) {
         s = s.replace("\"", "&quot;");
         }
         return s;
         */

    }
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.writeObject(names);
        oos.writeObject(descriptions);
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        this.names  = (List<Name>)ois.readObject();
        this.descriptions = (List<Description>)ois.readObject();
    }
}

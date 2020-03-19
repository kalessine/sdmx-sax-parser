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

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import sdmx.common.Description;
import sdmx.common.Name;

/**
 *
 * @author James
 */
public class NameableTypeTest {
    
    public NameableTypeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getNames method, of class NameableType.
     */
    @Test
    public void testGetNames() {
        System.out.println("getNames");
        NameableType instance = new NameableType();
        Name name1 = new Name("en","Name");
        List<Name> names = new ArrayList<Name>();
        names.add(name1);
        instance.setNames(names);
        String expResult = "Name";
        assertEquals(expResult, instance.toString());
    }    
    @Test
    public void testGetNames2() {
        System.out.println("getNames");
        NameableType instance = new NameableType();
        Name name1 = new Name("en","Name");
        Name name2 = new Name("fr","Jaime");
        List<Name> names = new ArrayList<Name>();
        names.add(name1);
        names.add(name2);
        instance.setNames(names);
        String expResult = "Name";
        assertEquals(expResult, instance.findName("de").toString());
    }    
    @Test
    public void testGetNames3() {
        System.out.println("getNames");
        NameableType instance = new NameableType();
        Name name1 = new Name("en","Name");
        Name name2 = new Name("fr","Jaime");
        List<Name> names = new ArrayList<Name>();
        names.add(name1);
        names.add(name2);
        instance.setNames(names);
        String expResult = "Jaime";
        assertEquals(expResult, instance.findName("fr").toString());
    }    
}

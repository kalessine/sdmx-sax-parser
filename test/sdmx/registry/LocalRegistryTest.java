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
package sdmx.registry;

import sdmx.net.LocalRegistry;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sdmx.Registry;
import sdmx.commonreferences.ConceptRef;
import sdmx.commonreferences.ConceptReference;
import sdmx.commonreferences.DataStructureReference;
import sdmx.commonreferences.IDType;
import sdmx.commonreferences.ItemSchemeRef;
import sdmx.commonreferences.ItemSchemeReferenceBase;
import sdmx.commonreferences.NestedNCNameID;
import sdmx.commonreferences.Version;
import sdmx.commonreferences.types.ItemSchemePackageTypeCodelistType;
import sdmx.commonreferences.types.ItemSchemeTypeCodelistType;
import sdmx.message.StructureType;
import sdmx.structure.base.IdentifiableType;
import sdmx.structure.codelist.CodelistType;
import sdmx.structure.concept.ConceptSchemeType;
import sdmx.structure.concept.ConceptType;
import sdmx.structure.datastructure.DataStructureType;
import sdmx.SdmxIO;
import sdmx.commonreferences.CodelistReference;
import sdmx.commonreferences.ConceptSchemeReference;
import sdmx.version.twopointzero.Sdmx20StructureParserTest;
import sdmx.xml.anyURI;

/**
 *
 * @author James
 */
public class LocalRegistryTest {
    
    public LocalRegistryTest() {
    }
    
    public static final Registry doc = new LocalRegistry();
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("test registry interface on LocalRegistry");
        boolean expResult = false;
        InputStream in = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/sdmx20-samples/StructureSample.xml");
        try {
            doc.load(SdmxIO.parseStructure(in));
        } catch (Exception ex) {
            Logger.getLogger(Sdmx20StructureParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
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
     * Test of getDefaultWorkspace method, of class LocalRegistry.
     */
    @Test
    public void testGetDefaultWorkspace() {
        System.out.println("getDefaultWorkspace");
        LocalRegistry result = LocalRegistry.getDefaultWorkspace();
        assertNotNull(result);
    }
    /**
     * Test of load method, of class StructureType.
     */

    /**
     * Test of findDataStructure method, of class StructureType.
     */
    @Test
    public void testFindDataStructure_3args() {
        DataStructureReference ref = DataStructureReference.create(new NestedNCNameID("BIS"), new IDType("BIS_JOINT_DEBT"), new Version("1.0"));
        DataStructureType ds = doc.find(ref);
        assertNotNull(ds);
    }

    /**
     * Test of findDataStructure method, of class StructureType.
     */
    @Test
    public void testFindDataStructure_3args2() {
        DataStructureReference ref = DataStructureReference.create(new NestedNCNameID("BIS"), new IDType("BIS_JOINT_DEBT"), null);
        DataStructureType ds = doc.find(ref);
        assertNotNull(ds);
    }

    /**
     * Test of findDataStructure method, of class StructureType.
     */
    @Test
    public void testFindDataStructure_NestedNCNameIDType_IDType() {
        DataStructureReference ref = DataStructureReference.create(new NestedNCNameID("BIS"), new IDType("BIS_JOINT_DEBT"),null);
        DataStructureType ds = doc.find(ref);
        assertNotNull(ds);
    }

    /**
     * Test of findConceptScheme method, of class StructureType.
     */
    @Test
    public void testFindConceptScheme_NestedNCNameIDType_ConceptReferenceType() {
        ConceptReference ref = ConceptReference.create(new NestedNCNameID("BIS"),new IDType("STANDALONE_CONCEPT_SCHEME"), new Version("1.0"), new IDType("STOCKS"));
        ConceptType cs = doc.find(ref);
        assertNotNull(cs);
    }

    /**
     * Test of findCodelist method, of class StructureType.
     */
    @Test
    public void testFindCodelist_ItemSchemeReferenceBaseType() {
        CodelistReference ref = CodelistReference.create(new NestedNCNameID("BIS"), new IDType("CL_COLLECTION"),new Version("1.0"));
        CodelistType cl = doc.find(ref);
        assertNotNull(cl);
    }

    /**
     * Test of findCodelistById method, of class StructureType.
     */
    @Test
    public void testFindCodelistById() {
        System.out.println("findCodelistById");
        IDType id = new IDType("CL_COLLECTION");
        CodelistReference ref = CodelistReference.create(null, id, null);
        CodelistType cl = doc.find(ref);
        assertNotNull(cl);
    }

    /**
     * Test of findCodelist method, of class StructureType.
     */
    @Test
    public void testFindCodelist_3args_1() {
        System.out.println("findCodelist");
        NestedNCNameID codelistAgency = new NestedNCNameID("BIS");
        IDType codelist = new IDType("CL_COLLECTION");
        Version codelistVersion = new Version("1.0");
        CodelistReference ref = CodelistReference.create(codelistAgency, codelist, codelistVersion);
        CodelistType result = doc.find(ref);
        assertNotNull(result);
    }

    /**
     * Test of findCodelist method, of class StructureType.
     */
    @Test
    public void testFindCodelist_NestedNCNameIDType_IDType() {
        System.out.println("findCodelist");
        NestedNCNameID codelistAgency = new NestedNCNameID("BIS");
        IDType codelist = new IDType("CL_COLLECTION");
        StructureType instance = new StructureType();
        CodelistReference ref = CodelistReference.create(codelistAgency, codelist, null);
        CodelistType result = doc.find(ref);
        assertNotNull(result);
    }

    /**
     * Test of findCodelist method, of class StructureType.
     */
    @Test
    public void testFindCodelist_String_String() {
        System.out.println("findCodelist");
        String codelistAgency = "BIS";
        String codelist = "CL_COLLECTION";
        CodelistReference ref = CodelistReference.create(new NestedNCNameID(codelistAgency),new IDType(codelist), null);
        CodelistType result = doc.find(ref);
        assertNotNull(result);
    }

    /**
     * Test of findConceptScheme method, of class StructureType.
     */
    @Test
    public void testFindConceptScheme_NestedNCNameIDType_IDType() {
        System.out.println("findConceptScheme");
        NestedNCNameID csa = new NestedNCNameID("BIS");
        IDType csi = new IDType("STANDALONE_CONCEPT_SCHEME");
        ConceptSchemeReference ref = ConceptSchemeReference.create(csa, csi, null);
        ConceptSchemeType result = doc.find(ref);
        assertNotNull(result);
    }

    /**
     * Test of findConceptSchemeById method, of class StructureType.
     */
    @Test
    public void testFindConceptSchemeById() {
        System.out.println("findConceptSchemeById");
        IDType id = new IDType("JD_CATEGORY");
        ConceptSchemeReference ref = ConceptSchemeReference.create(null, id, null);
        ConceptSchemeType result = doc.find(ref);
        assertNotNull(id);
    }

    /**
     * Test of findConcept method, of class StructureType.
     */
    @Test
    public void testFindConcept_NestedNCNameIDType_IDType() {
        System.out.println("findConcept");
        NestedNCNameID agency = new NestedNCNameID("BIS");
        IDType id = new IDType("JD_CATEGORY");
        ConceptReference ref = ConceptReference.create(agency, null, null, id);
        ConceptType result = doc.find(ref);
        assertNotNull(result);
    }

    /**
     * Test of findConcept method, of class StructureType.
     */
    @Test
    public void testFindConcept_IDType() {
        System.out.println("findConcept");
        IDType id = new IDType("JD_CATEGORY");
        ConceptReference ref = ConceptReference.create(null, null, null, id);
        ConceptType result = doc.find(ref);
        assertNotNull(result);
    }

    /**
     * Test of listDataSets method, of class StructureType.
     */
    @Test
    public void testListDataSets() {
        System.out.println("listDataSets");
        try {
            assertEquals(0,doc.listDataflows().size());
        } catch (Exception ex) {
        }
    }

}

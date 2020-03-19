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
package sdmx.version.twopointzero;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import sdmx.SdmxIO;
import sdmx.commonreferences.ConceptRef;
import sdmx.commonreferences.RefBase;
import sdmx.commonreferences.Version;
import sdmx.message.StructureType;
import sdmx.structure.ConceptsType;
import sdmx.structure.base.ComponentUtil;
import sdmx.structure.base.RepresentationType;
import sdmx.structure.codelist.CodelistType;
import sdmx.structure.concept.ConceptSchemeType;
import sdmx.structure.concept.ConceptType;
import sdmx.structure.datastructure.DataStructureType;
import sdmx.version.twopointone.writer.Sdmx21StructureWriter;

/**
 *
 * @author James
 */
public class Sdmx20StructureParserTest {

    public Sdmx20StructureParserTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    StructureType doc = null;

    @Before
    public void setUp() {
        System.out.println("test SDMX 2.0 Parse");
        String header = "";
        boolean expResult = false;
        InputStream in = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/sdmx20-samples/StructureSample.xml");
        try {
            doc = SdmxIO.parseStructure(in);
        } catch (Exception ex) {
            Logger.getLogger(Sdmx20StructureParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            FileOutputStream fos = new FileOutputStream("testOut/StructureSampleOut.xml");
            Sdmx21StructureWriter.write(doc, fos);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Sdmx20StructureParserTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Sdmx20StructureParserTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
        }

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of isSdmx20 method, of class Sdmx20StructureParserProvider.
     */
    @Test
    public void testStructureSample() {
        assertNotNull(doc);
    }

    @Test
    public void testStructureSample2() {
        assertEquals("BIS_01", doc.getHeader().getId().toString());
    }

    @Test
    public void testStructureSample3() {
        assertTrue(doc.getHeader().getTest());
    }

    @Test
    public void testStructureSample4() {
        // No Truncated attribute in sdmx 2.1, so no test for it here.
        assertEquals("Trans46301", doc.getHeader().getNames().get(0).getText());
    }

    @Test
    public void testStructureSample5() {
        assertEquals("en", doc.getHeader().getNames().get(0).getLang());
    }

    @Test
    public void testStructureSample6() {
        Calendar c = Calendar.getInstance();
        c.setTime(doc.getHeader().getPrepared().getDate().getDate());
        assertEquals(c.get(Calendar.YEAR), 2002);
        assertEquals(c.get(Calendar.MONTH), 2);
        assertEquals(c.get(Calendar.DATE), 11);
        assertEquals(c.get(Calendar.HOUR), 10);
        assertEquals(c.get(Calendar.MINUTE), 30);
        assertEquals(c.get(Calendar.SECOND), 47);
        assertEquals(c.get(Calendar.ZONE_OFFSET), 28800000);
    }

    @Test
    public void testStructureSample7() {
        assertEquals("BIS", doc.getHeader().getSender().getId().toString());
    }

    @Test
    public void testStructureSample8() {
        assertEquals("Bank for International Settlements", doc.getHeader().getSender().getNames().get(0).getText());
    }

    @Test
    public void testStructureSample9() {
        assertEquals("en", doc.getHeader().getSender().getNames().get(0).getLang());
    }

    @Test
    public void testStructureSample10() {
        assertEquals("G.B. Smith", doc.getHeader().getSender().getContacts().get(0).getNames().get(0).getText());
    }

    @Test
    public void testStructureSample11() {
        assertEquals("en", doc.getHeader().getSender().getContacts().get(0).getNames().get(0).getLang());
    }

    @Test
    public void testStructureSample12() {
        assertEquals("+000.000.0000", doc.getHeader().getSender().getContacts().get(0).getTelephones().get(0));
    }

    @Test
    public void testStructureSample13() {
        assertEquals("ECB", doc.getHeader().getReceivers().get(0).getId().toString());
    }

    @Test
    public void testStructureSample14() {
        assertEquals("European Central Bank", doc.getHeader().getReceivers().get(0).getNames().get(0).getText());
    }

    @Test
    public void testStructureSample15() {
        assertEquals("en", doc.getHeader().getReceivers().get(0).getNames().get(0).getLang());
    }

    @Test
    public void testStructureSample16() {
        assertEquals("B.S. Featherstone", doc.getHeader().getReceivers().get(0).getContacts().get(0).getNames().get(0).getText());
    }

    @Test
    public void testStructureSample17() {
        assertEquals("en", doc.getHeader().getReceivers().get(0).getContacts().get(0).getNames().get(0).getLang());
    }

    @Test
    public void testStructureSample18() {
        assertEquals("Statistics Division", doc.getHeader().getReceivers().get(0).getContacts().get(0).getDepartments().get(0).getText());
    }

    @Test
    public void testStructureSample19() {
        assertEquals("en", doc.getHeader().getReceivers().get(0).getContacts().get(0).getDepartments().get(0).getLang());
    }

    @Test
    public void testStructureSample20() {
        assertEquals("+000.000.0001", doc.getHeader().getReceivers().get(0).getContacts().get(0).getTelephones().get(0));
    }

    @Test
    public void testStructureSample21() {
        Calendar c = doc.getHeader().getExtracted().toCalendar();
        assertEquals(c.get(Calendar.YEAR), 2002);
        assertEquals(c.get(Calendar.MONTH), 2);
        assertEquals(c.get(Calendar.DATE), 11);
        assertEquals(c.get(Calendar.HOUR), 9);
        assertEquals(c.get(Calendar.MINUTE), 30);
        assertEquals(c.get(Calendar.SECOND), 47);
        assertEquals(c.get(Calendar.ZONE_OFFSET), -18000000);
    }

    @Test
    public void testStructureSample23() {
        CodelistType cl = doc.getStructures().getCodelists().findCodelist("BIS", "CL_OBS_STATUS", "1.0");

        assertNotNull(cl);
    }

    @Test
    public void testStructureSample24() {
        CodelistType cl = doc.getStructures().getCodelists().findCodelist("BIS", "CL_OBS_STATUS", "1.0");
        assertEquals("http://www.bis.org/structure/codelists/cl_obs_status.xml", cl.getUri().getString());
    }

    @Test
    public void testStructureSample25() {
        CodelistType cl = doc.getStructures().getCodelists().findCodelist("BIS", "CL_OBS_STATUS", "1.0");
        assertEquals("Observation Status", cl.findName("en").toString());
    }

    @Test
    public void testStructureSample26() {
        CodelistType cl = doc.getStructures().getCodelists().findCodelist("BIS", "CL_OBS_STATUS", "1.0");
        assertEquals("Present", cl.findCode("A").findName("en").toString());
    }

    @Test
    public void testStructureSample27() {
        CodelistType cl = doc.getStructures().getCodelists().findCodelist("BIS", "CL_OBS_STATUS", "1.0");
        assertEquals("Missing", cl.findCode("M").findName("en").toString());
    }

    @Test
    public void testStructureSample28() {
        CodelistType cl = doc.getStructures().getCodelists().findCodelist("BIS", "CL_COLLECTION", "1.0");
        assertEquals("Average of observations through period", cl.findCode("A").findName("en").toString());
    }

    @Test
    public void testStructureSample29() {
        CodelistType cl = doc.getStructures().getCodelists().findCodelist("BIS", "CL_COLLECTION", "1.0");
        assertEquals("Beginning of period", cl.findCode("B").findName("en").toString());
    }

    @Test
    public void testStructureSample30() {
        CodelistType cl = doc.getStructures().getCodelists().findCodelist("BIS", "CL_COLLECTION", "1.0");
        assertEquals("End of period", cl.findCode("E").findName("en").toString());
    }

    @Test
    public void testStructureSample31() {
        CodelistType cl = doc.getStructures().getCodelists().findCodelist("BIS", "CL_COLLECTION", "1.0");
        assertEquals("Highest in period", cl.findCode("H").findName("en").toString());
    }

    @Test
    public void testStructureSample32() {
        CodelistType cl = doc.getStructures().getCodelists().findCodelist("BIS", "CL_COLLECTION", "1.0");
        assertEquals("Lowest in period", cl.findCode("L").findName("en").toString());
    }

    @Test
    public void testStructureSample33() {
        CodelistType cl = doc.getStructures().getCodelists().findCodelist("BIS", "CL_COLLECTION", "1.0");
        assertEquals("Middle of period", cl.findCode("M").findName("en").toString());
    }

    @Test
    public void testStructureSample34() {
        CodelistType cl = doc.getStructures().getCodelists().findCodelist("BIS", "CL_COLLECTION", "1.0");
        assertEquals("Summed through period", cl.findCode("S").findName("en").toString());
    }

    @Test
    public void testStructureSample35() {
        CodelistType cl = doc.getStructures().getCodelists().findCodelist("BIS", "CL_COLLECTION", "1.0");
        assertEquals("Unknown", cl.findCode("U").findName("en").toString());
    }

    @Test
    public void testStructureSample36() {
        CodelistType cl = doc.getStructures().getCodelists().findCodelist("BIS", "CL_COLLECTION", "1.0");
        assertEquals("Other", cl.findCode("V").findName("en").toString());
    }

    @Test
    public void testStructureSample37() {
        CodelistType cl = doc.getStructures().getCodelists().findCodelist("BIS", "CL_COLLECTION", "1.0");
        assertEquals("Annualised summed", cl.findCode("Y").findName("en").toString());
    }

    @Test
    public void testStructureSample38() {
        CodelistType cl = doc.getStructures().getCodelists().findCodelist("BIS", "CL_DECIMALS", "1.0");
        assertEquals("one", cl.findCode("1").findName("en").toString());
    }

    @Test
    public void testStructureSample39() {
        CodelistType cl = doc.getStructures().getCodelists().findCodelist("BIS", "CL_DECIMALS", "1.0");
        assertEquals("two", cl.findCode("2").findName("en").toString());
    }

    @Test
    public void testStructureSample40() {
        CodelistType cl = doc.getStructures().getCodelists().findCodelist("BIS", "CL_DECIMALS", "1.0");
        assertEquals("three", cl.findCode("3").findName("en").toString());
    }

    @Test
    public void testStructureSample41() {
        CodelistType cl = doc.getStructures().getCodelists().findCodelist("BIS", "CL_DECIMALS", "1.0");
        assertEquals("four", cl.findCode("4").findName("en").toString());
    }

    @Test
    public void testStructureSample42() {
        CodelistType cl = doc.getStructures().getCodelists().findCodelist("BIS", "CL_DECIMALS", "1.0");
        assertEquals("five", cl.findCode("5").findName("en").toString());
    }

    @Test
    public void testStructureSample43() {
        CodelistType cl = doc.getStructures().getCodelists().findCodelist("BIS", "CL_DECIMALS", "1.0");
        assertEquals("six", cl.findCode("6").findName("en").toString());
    }

    @Test
    public void testStructureSample44() {
        CodelistType cl = doc.getStructures().getCodelists().findCodelist("BIS", "CL_DECIMALS", "1.0");
        assertEquals("seven", cl.findCode("7").findName("en").toString());
    }

    @Test
    public void testStructureSample45() {
        CodelistType cl = doc.getStructures().getCodelists().findCodelist("BIS", "CL_DECIMALS", "1.0");
        System.out.println("Code=" + cl.findCode("8"));
        assertEquals("eight", cl.findCode("8").findName("en").toString());
    }

    @Test
    public void testStructureSample46() {
        CodelistType cl = doc.getStructures().getCodelists().findCodelist("BIS", "CL_BIS_OBS_CONF", "1.0");
        assertEquals("Observation confidentiality code list", cl.findName("en").toString());
    }

    @Test
    public void testStructureSample47() {
        CodelistType cl = doc.getStructures().getCodelists().findCodelist("BIS", "CL_BIS_OBS_CONF", "1.0");
        assertEquals("Non-publishable and confidential", cl.findCode("C").findName("en").toString());
    }

    @Test
    public void testStructureSample48() {
        CodelistType cl = doc.getStructures().getCodelists().findCodelist("BIS", "CL_BIS_OBS_CONF", "1.0");
        assertEquals("Free", cl.findCode("F").findName("en").toString());
    }

    @Test
    public void testStructureSample49() {
        CodelistType cl = doc.getStructures().getCodelists().findCodelist("BIS", "CL_BIS_OBS_CONF", "1.0");
        assertEquals("Non-publishable, but non-confidential", cl.findCode("N").findName("en").toString());
    }

    @Test
    public void testStructureSample50() {
        CodelistType cl = doc.getStructures().getCodelists().findCodelist("BIS", "CL_BIS_OBS_CONF", "1.0");
        assertEquals("Confidential statistical information due to identifiable respondents", cl.findCode("R").findName("en").toString());
    }

    @Test
    public void testStructureSample51() {
        CodelistType cl = doc.getStructures().getCodelists().findCodelist("BIS", "CL_BIS_UNIT", "1.0");
        assertEquals("BIS Unit", cl.findName("en").toString());
    }

    @Test
    public void testStructureSample52() {
        CodelistType cl = doc.getStructures().getCodelists().findCodelist("BIS", "CL_BIS_UNIT", "1.0");
        assertEquals("Swiss Francs", cl.findCode("CHF").findName("en").toString());
    }

    @Test
    public void testStructureSample53() {
        CodelistType cl = doc.getStructures().getCodelists().findCodelist("BIS", "CL_BIS_UNIT", "1.0");
        assertEquals("United States Dollars", cl.findCode("USD").findName("en").toString());
    }

    @Test
    public void testStructureSample54() {
        CodelistType cl = doc.getStructures().getCodelists().findCodelist("BIS", "CL_BIS_UNIT", "1.0");
        assertEquals("New Zealand Dollars", cl.findCode("NZD").findName("en").toString());
    }

    @Test
    public void testStructureSample55() {
        CodelistType cl = doc.getStructures().getCodelists().findCodelist("BIS", "CL_BIS_UNIT", "1.0");
        assertEquals("Euros", cl.findCode("EUR").findName("en").toString());
    }

    @Test
    public void testStructureSample56() {
        CodelistType cl = doc.getStructures().getCodelists().findCodelist("BIS", "CL_UNIT_MULT", "1.0");
        assertEquals("Unit multiplier", cl.findName("en").toString());
    }

    @Test
    public void testStructureSample57() {
        CodelistType cl = doc.getStructures().getCodelists().findCodelist("BIS", "CL_UNIT_MULT", "1.0");
        assertEquals("Unit multiplier", cl.findName("en").toString());
    }

    @Test
    public void testStructureSample58() {
        CodelistType cl = doc.getStructures().getCodelists().findCodelist("BIS", "CL_UNIT_MULT", "1.0");
        assertEquals("Units", cl.findCode("0").findName("en").toString());
    }

    @Test
    public void testStructureSample59() {
        CodelistType cl = doc.getStructures().getCodelists().findCodelist("BIS", "CL_UNIT_MULT", "1.0");
        assertEquals("Tens", cl.findCode("1").findName("en").toString());
    }

    @Test
    public void testStructureSample60() {
        CodelistType cl = doc.getStructures().getCodelists().findCodelist("BIS", "CL_UNIT_MULT", "1.0");
        assertEquals("Hundreds", cl.findCode("2").findName("en").toString());
    }

    @Test
    public void testStructureSample61() {
        CodelistType cl = doc.getStructures().getCodelists().findCodelist("BIS", "CL_UNIT_MULT", "1.0");
        assertEquals("Thousands", cl.findCode("3").findName("en").toString());
    }

    @Test
    public void testStructureSample62() {
        CodelistType cl = doc.getStructures().getCodelists().findCodelist("BIS", "CL_UNIT_MULT", "1.0");
        assertEquals("Ten Thousands", cl.findCode("4").findName("en").toString());
    }

    @Test
    public void testStructureSample63() {
        CodelistType cl = doc.getStructures().getCodelists().findCodelist("BIS", "CL_UNIT_MULT", "1.0");
        assertEquals("Millions", cl.findCode("5").findName("en").toString());
    }

    @Test
    public void testStructureSample64() {
        CodelistType cl = doc.getStructures().getCodelists().findCodelist("BIS", "CL_UNIT_MULT", "1.0");
        assertEquals("Billions", cl.findCode("6").findName("en").toString());
    }

    @Test
    public void testStructureSample65() {
        ConceptsType cons = doc.getStructures().getConcepts();
        ConceptType cs = cons.findConceptScheme("BIS", "STANDALONE_CONCEPT_SCHEME", "1.0").findConcept("TIME_PERIOD");

        assertEquals("http://www.bis.org/structure/concepts/VIS_CTY.xml", cs.getUri().getString());
    }

    @Test
    public void testStructureSample66() {
        ConceptsType cons = doc.getStructures().getConcepts();
        ConceptType cs = cons.findConceptScheme("BIS", "STANDALONE_CONCEPT_SCHEME", "1.0").findConcept("TIME_PERIOD");

        assertEquals("Time", cs.findName("en").toString());
    }

    @Test
    public void testStructureSample67() {
        ConceptsType cons = doc.getStructures().getConcepts();
        ConceptType cs = cons.findConceptScheme("BIS", "STANDALONE_CONCEPT_SCHEME", "1.0").findConcept("FREQ");
        assertEquals("http://www.bis.org/structure/concepts/FREQ.xml", cs.getUri().getString());
    }

    @Test
    public void testStructureSample68() {
        ConceptsType cons = doc.getStructures().getConcepts();
        ConceptType cs = cons.findConceptScheme("BIS", "STANDALONE_CONCEPT_SCHEME", "1.0").findConcept("FREQ");
        assertEquals("Frequency", cs.findName("en").toString());
    }

    @Test
    public void testStructureSample69() {
        ConceptsType cons = doc.getStructures().getConcepts();
        ConceptType cs = cons.findConceptScheme("BIS", "STANDALONE_CONCEPT_SCHEME", "1.0").findConcept("JD_TYPE");
        assertEquals("http://www.bis.org/structure/concepts/JD_TYPE.xml", cs.getUri().getString());
    }

    @Test
    public void testStructureSample70() {
        ConceptsType cons = doc.getStructures().getConcepts();
        ConceptType cs = cons.findConceptScheme("BIS", "STANDALONE_CONCEPT_SCHEME", "1.0").findConcept("JD_TYPE");
        assertEquals("Data Type (amounts outstanding, net disbursement or changes)", cs.findName("en").toString());
    }

    @Test
    public void testStructureSample71() {
        ConceptsType cons = doc.getStructures().getConcepts();
        ConceptType cs = cons.findConceptScheme("BIS", "STANDALONE_CONCEPT_SCHEME", "1.0").findConcept("JD_CATEGORY");

        assertEquals("http://www.bis.org/structure/concepts/JD_CATEGORY.xml", cs.getUri().getString());
    }

    @Test
    public void testStructureSample72() {
        ConceptsType cons = doc.getStructures().getConcepts();
        ConceptType cs = cons.findConceptScheme("BIS", "STANDALONE_CONCEPT_SCHEME", "1.0").findConcept("JD_CATEGORY");
        assertEquals("Debt category", cs.findName("en").toString());
    }

    @Test
    public void testStructureSample73() {
        ConceptsType cons = doc.getStructures().getConcepts();
        ConceptType cs = cons.findConceptScheme("BIS", "STANDALONE_CONCEPT_SCHEME", "1.0").findConcept("VIS_CTY");

        assertEquals("http://www.bis.org/structure/concepts/VIS_CTY.xml", cs.getUri().getString());
    }

    @Test
    public void testStructureSample74() {
        ConceptsType cons = doc.getStructures().getConcepts();
        ConceptType cs = cons.findConceptScheme("BIS", "STANDALONE_CONCEPT_SCHEME", "1.0").findConcept("VIS_CTY");
        assertEquals("Vis-a-vis country", cs.findName("en").toString());
    }

    @Test
    public void testStructureSample75() {
        ConceptsType cons = doc.getStructures().getConcepts();
        ConceptType cs = cons.findConceptScheme("BIS", "STANDALONE_CONCEPT_SCHEME", "1.0").findConcept("AVAILABILITY");
        assertEquals("http://www.bis.org/structure/concepts/AVAILABILITY.xml", cs.getUri().getString());
    }

    @Test
    public void testStructureSample76() {
        ConceptsType cons = doc.getStructures().getConcepts();
        ConceptType cs = cons.findConceptScheme("BIS", "STANDALONE_CONCEPT_SCHEME", "1.0").findConcept("AVAILABILITY");
        assertEquals("Availability", cs.findName("en").toString());
    }

    @Test
    public void testStructureSample77() {
        ConceptsType cons = doc.getStructures().getConcepts();
        ConceptType cs = cons.findConceptScheme("BIS", "STANDALONE_CONCEPT_SCHEME", "1.0").findConcept("COLLECTION");

        assertEquals("http://www.bis.org/structure/concepts/COLLECTION.xml", cs.getUri().getString());
    }

    @Test
    public void testStructureSample78() {
        ConceptsType cons = doc.getStructures().getConcepts();
        ConceptType cs = cons.findConceptScheme("BIS", "STANDALONE_CONCEPT_SCHEME", "1.0").findConcept("COLLECTION");
        assertEquals("Collection indicator", cs.findName("en").toString());
    }

    @Test
    public void testStructureSample79() {
        ConceptsType cons = doc.getStructures().getConcepts();
        ConceptType cs = cons.findConceptScheme("BIS", "STANDALONE_CONCEPT_SCHEME", "1.0").findConcept("DECIMALS");
        assertEquals("http://www.bis.org/structure/concepts/DECIMALS.xml", cs.getUri().getString());
    }

    @Test
    public void testStructureSample80() {
        ConceptsType cons = doc.getStructures().getConcepts();
        ConceptType cs = cons.findConceptScheme("BIS", "STANDALONE_CONCEPT_SCHEME", "1.0").findConcept("DECIMALS");
        assertEquals("Decimals", cs.findName("en").toString());
    }

    @Test
    public void testStructureSample81() {
        ConceptsType cons = doc.getStructures().getConcepts();
        ConceptType cs = cons.findConceptScheme("BIS", "STANDALONE_CONCEPT_SCHEME", "1.0").findConcept("OBS_CONF");
        assertEquals("http://www.bis.org/structure/concepts/OBS_CONF.xml", cs.getUri().getString());
    }

    @Test
    public void testStructureSample82() {
        ConceptsType cons = doc.getStructures().getConcepts();
        ConceptType cs = cons.findConceptScheme("BIS", "STANDALONE_CONCEPT_SCHEME", "1.0").findConcept("OBS_CONF");
        assertEquals("Observation confidentiality", cs.findName("en").toString());
    }

    @Test
    public void testStructureSample83() {
        ConceptsType cons = doc.getStructures().getConcepts();
        ConceptType cs = cons.findConceptScheme("BIS", "STANDALONE_CONCEPT_SCHEME", "1.0").findConcept("TIME_FORMAT");
        assertEquals("http://www.bis.org/structure/concepts/TIME_FORMAT.xml", cs.getUri().getString());
    }

    @Test
    public void testStructureSample84() {
        ConceptsType cons = doc.getStructures().getConcepts();
        ConceptType cs = cons.findConceptScheme("BIS", "STANDALONE_CONCEPT_SCHEME", "1.0").findConcept("TIME_FORMAT");
        assertEquals("Time Format", cs.findName("en").toString());
    }

    @Test
    public void testStructureSample85() {
        ConceptsType cons = doc.getStructures().getConcepts();
        ConceptType cs = cons.findConceptScheme("BIS", "STANDALONE_CONCEPT_SCHEME", "1.0").findConcept("OBS_STATUS");

        assertEquals("http://www.bis.org/structure/concepts/OBS_STATUS.xml", cs.getUri().getString());
    }

    @Test
    public void testStructureSample86() {
        ConceptsType cons = doc.getStructures().getConcepts();
        ConceptType cs = cons.findConceptScheme("BIS", "STANDALONE_CONCEPT_SCHEME", "1.0").findConcept("OBS_STATUS");
        assertEquals("Observation status", cs.findName("en").toString());
    }

    @Test
    public void testStructureSample87() {
        ConceptsType cons = doc.getStructures().getConcepts();
        ConceptType cs = cons.findConceptScheme("BIS", "STANDALONE_CONCEPT_SCHEME", "1.0").findConcept("OBS_PRE_BREAK");
        assertEquals("http://www.bis.org/structure/concepts/OBS_PRE_BREAK.xml", cs.getUri().getString());
    }

    @Test
    public void testStructureSample88() {
        ConceptsType cons = doc.getStructures().getConcepts();
        ConceptType cs = cons.findConceptScheme("BIS", "STANDALONE_CONCEPT_SCHEME", "1.0").findConcept("OBS_PRE_BREAK");
        assertEquals("Pre-break observation", cs.findName("en").toString());
    }

    @Test
    public void testStructureSample89() {
        ConceptsType cons = doc.getStructures().getConcepts();
        ConceptType cs = cons.findConceptScheme("BIS", "STANDALONE_CONCEPT_SCHEME", "1.0").findConcept("BIS_UNIT");
        assertEquals("http://www.bis.org/structure/concepts/UNIT.xml", cs.getUri().getString());
    }

    @Test
    public void testStructureSample90() {
        ConceptsType cons = doc.getStructures().getConcepts();
        ConceptType cs = cons.findConceptScheme("BIS", "STANDALONE_CONCEPT_SCHEME", "1.0").findConcept("BIS_UNIT");
        assertEquals("Unit", cs.findName("en").toString());
    }

    @Test
    public void testStructureSample91() {
        ConceptsType cons = doc.getStructures().getConcepts();
        ConceptType cs = cs = cons.findConceptScheme("BIS", "STANDALONE_CONCEPT_SCHEME", "1.0").findConcept("UNIT_MULT");

        assertEquals("http://www.bis.org/structure/concepts/UNIT_MULT.xml", cs.getUri().getString());
    }

    @Test
    public void testStructureSample92() {
        ConceptsType cons = doc.getStructures().getConcepts();
        ConceptType cs = cs = cons.findConceptScheme("BIS", "STANDALONE_CONCEPT_SCHEME", "1.0").findConcept("UNIT_MULT");

        assertEquals("Unit multiplier", cs.findName("en").toString());
    }

    @Test
    public void testStructureSample93() {
        ConceptsType cons = doc.getStructures().getConcepts();
        ConceptType cs = cons.findConceptScheme("BIS", "STANDALONE_CONCEPT_SCHEME", "1.0").findConcept("OBS_VALUE");
        assertEquals("http://www.bis.org/structure/concepts/OBS_VALUE.xml", cs.getUri().getString());
    }

    @Test
    public void testStructureSample94() {
        ConceptsType cons = doc.getStructures().getConcepts();
        ConceptType cs = cons.findConceptScheme("BIS", "STANDALONE_CONCEPT_SCHEME", "1.0").findConcept("OBS_VALUE");
        assertEquals("Observation value", cs.findName("en").toString());
    }

    @Test
    public void testStructureSample95() {
        //ConceptsType cons = doc.getStructures().getConcepts();
        //ConceptType cs = cons.findConceptScheme("BIS", "CL_JD_TYPE", "1.0").findConcept("P");
        //assertEquals("http://www.bis.org/structure/concepts/STOCKS.xml", cs.getUri().getString());
    }

    @Test
    public void testStructureSample96() {
        //ConceptsType cons = doc.getStructures().getConcepts();
        //ConceptType cs = cons.findConceptScheme("BIS", "CL_JD_TYPE", "1.0").findConcept("STOCKS");
        //assertEquals("Stocks as measure", cs.findName("en").toString());
    }

    @Test
    public void testStructureSample97() {
        //ConceptsType cons = doc.getStructures().getConcepts();
        //ConceptType cs = cons.findConceptScheme("BIS", "JD_TYPE", "1.0").findConcept("FLOWS");
        //assertEquals("http://www.bis.org/structure/concepts/FLOWS.xml", cs.getUri().getString());
    }

    @Test
    public void testStructureSample98() {
        //ConceptsType cons = doc.getStructures().getConcepts();
        //ConceptType cs = cons.findConceptScheme("BIS", "JD_TYPE", "1.0").findConcept("FLOWS");
        //assertEquals("Flows as measure", cs.findName("en").toString());
    }

    @Test
    public void testStructureSample99() {
        DataStructureType ds = doc.getStructures().getDataStructures().getDataStructures().get(0);
        // "BIS" is filled in from the conceptRef field
        RefBase ref = ds.getDataStructureComponents().getDimensionList().getDimension(0).getConceptIdentity().getRef();
        //System.out.println(ref.getMaintainableParentId()+":"+ref.getMaintainableParentVersion()+":"+ref.getAgencyId()+":"+ref.getId()+":"+ref.getVersion());
    }

    @Test
    public void testStructureSample100() {
        DataStructureType ds = doc.getStructures().getDataStructures().getDataStructures().get(0);
        assertEquals("BIS", ds.getDataStructureComponents().getDimensionList().getDimensions().get(0).getConceptIdentity().getAgencyId().toString());
    }

    @Test
    public void testStructureSample101() {
        DataStructureType ds = doc.getStructures().getDataStructures().getDataStructures().get(0);
        assertEquals("FREQ", ds.getDataStructureComponents().getDimensionList().getDimensions().get(0).getConceptIdentity().getId().toString());
    }

    @Test
    public void testStructureSample102() {
        DataStructureType ds = doc.getStructures().getDataStructures().getDataStructures().get(0);
        // 1.0 is filled in from the conceptRef field.
        assertEquals("1.0", ds.getDataStructureComponents().getDimensionList().getDimensions().get(0).getConceptIdentity().getVersion().toString());
    }

    @Test
    public void testStructureSample103() {
        DataStructureType ds = doc.getStructures().getDataStructures().getDataStructures().get(0);
        assertEquals("BIS", ds.getDataStructureComponents().getDimensionList().getMeasureDimension().getConceptIdentity().getAgencyId().toString());
    }

    @Test
    public void testStructureSample104() {
        DataStructureType ds = doc.getStructures().getDataStructures().getDataStructures().get(0);
        assertEquals("JD_TYPE", ds.getDataStructureComponents().getDimensionList().getMeasureDimension().getConceptIdentity().getId().toString());
    }

    @Test
    public void testStructureSample105() {
        DataStructureType ds = doc.getStructures().getDataStructures().getDataStructures().get(0);
        // 1.0 is filled in from the conceptRef field.
        assertEquals("1.0", ds.getDataStructureComponents().getDimensionList().getMeasureDimension().getConceptIdentity().getVersion().toString());
    }

    @Test
    public void testStructureSample106() {
        DataStructureType ds = doc.getStructures().getDataStructures().getDataStructures().get(0);
        RepresentationType rep = ComponentUtil.getLocalRepresentation(ds.getDataStructureComponents().getDimensionList().getDimensions().get(1));
        assertEquals("BIS", rep.getEnumeration().getAgencyId().toString());
    }
    /*
     @Test
     public void testStructureSample107() {
     DataStructureType ds = doc.getStructures().getDataStructures().getDataStructures().get(0);
     assertEquals("CL_JD_TYPE", ds.getDataStructureComponents().getDimensionList().getDimensions().get(1).getLocalRepresentation().getEnumeration().getRef().getId().toString());
     }

     @Test
     public void testStructureSample108() {
     DataStructureType ds = doc.getStructures().getDataStructures().getDataStructures().get(0);
     assertEquals("1.0", ds.getDataStructureComponents().getDimensionList().getDimensions().get(1).getLocalRepresentation().getEnumeration().getRef().getVersion().toString());
     }
     */

    @Test
    public void testStructureSample109() {
        DataStructureType ds = doc.getStructures().getDataStructures().getDataStructures().get(0);

        assertEquals("BIS", ds.getDataStructureComponents().getDimensionList().getDimensions().get(1).getConceptIdentity().getAgencyId().toString());
    }

    @Test
    public void testStructureSample110() {
        DataStructureType ds = doc.getStructures().getDataStructures().getDataStructures().get(0);
        assertEquals("JD_CATEGORY", ds.getDataStructureComponents().getDimensionList().getDimensions().get(1).getConceptIdentity().getId().toString());
    }

    @Test
    public void testStructureSample111() {
        DataStructureType ds = doc.getStructures().getDataStructures().getDataStructures().get(0);
        // 1.0 is filled in from the conceptRef field.
        assertEquals("1.0", ds.getDataStructureComponents().getDimensionList().getDimensions().get(1).getConceptIdentity().getVersion().toString());
    }

    @Test
    public void testStructureSample112() {
        DataStructureType ds = doc.getStructures().getDataStructures().getDataStructures().get(0);
        RepresentationType rep = ComponentUtil.getLocalRepresentation(ds.getDataStructureComponents().getDimensionList().getDimensions().get(1));
        assertEquals("BIS", rep.getEnumeration().getAgencyId().toString());
    }

    @Test
    public void testStructureSample113() {
        DataStructureType ds = doc.getStructures().getDataStructures().getDataStructures().get(0);
        RepresentationType rep = ComponentUtil.getLocalRepresentation(ds.getDataStructureComponents().getDimensionList().getDimensions().get(1));
        assertEquals("CL_JD_CATEGORY", rep.getEnumeration().getMaintainableParentId().toString());
    }

    @Test
    public void testStructureSample114() {
        DataStructureType ds = doc.getStructures().getDataStructures().getDataStructures().get(0);
        RepresentationType rep = ComponentUtil.getLocalRepresentation(ds.getDataStructureComponents().getDimensionList().getDimensions().get(1));
        assertEquals("1.0", rep.getEnumeration().getVersion().toString());
    }

    @Test
    public void testStructureSample115() {
        DataStructureType ds = doc.getStructures().getDataStructures().getDataStructures().get(0);

        assertEquals("BIS", ds.getDataStructureComponents().getDimensionList().getDimensions().get(2).getConceptIdentity().getAgencyId().toString());
    }

    @Test
    public void testStructureSample116() {
        DataStructureType ds = doc.getStructures().getDataStructures().getDataStructures().get(0);
        assertEquals("VIS_CTY", ds.getDataStructureComponents().getDimensionList().getDimensions().get(2).getConceptIdentity().getId().toString());
    }

    @Test
    public void testStructureSample117() {
        DataStructureType ds = doc.getStructures().getDataStructures().getDataStructures().get(0);
        assertEquals("1.0", ds.getDataStructureComponents().getDimensionList().getDimensions().get(2).getConceptIdentity().getVersion().toString());
    }

    @Test
    public void testStructureSample118() {
        DataStructureType ds = doc.getStructures().getDataStructures().getDataStructures().get(0);
        RepresentationType rep = ComponentUtil.getLocalRepresentation(ds.getDataStructureComponents().getDimensionList().getDimensions().get(2));
        assertEquals("BIS", rep.getEnumeration().getAgencyId().toString());
    }

    @Test
    public void testStructureSample119() {
        DataStructureType ds = doc.getStructures().getDataStructures().getDataStructures().get(0);
        RepresentationType rep = ComponentUtil.getLocalRepresentation(ds.getDataStructureComponents().getDimensionList().getDimensions().get(2));
        assertEquals("CL_BIS_IF_REF_AREA", rep.getEnumeration().getMaintainableParentId().toString());
    }

    @Test
    public void testStructureSample120() {
        DataStructureType ds = doc.getStructures().getDataStructures().getDataStructures().get(0);
        RepresentationType rep = ComponentUtil.getLocalRepresentation(ds.getDataStructureComponents().getDimensionList().getDimensions().get(2));
        assertEquals("1.0", rep.getEnumeration().getVersion().toString());
    }

    @Test
    public void testsABSStructures() {
        InputStream in1 = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/abs-20/8nrpver5-structure.xml");
        StructureType doc1 = null;
        try {
            doc1 = SdmxIO.parseStructure(in1);
        } catch (Exception ex) {
            Logger.getLogger(Sdmx20StructureParserTest.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        assertNotNull(doc1);
        InputStream in2 = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/abs-20/alc-structure.xml");
        StructureType doc2 = null;
        try {
            doc2 = SdmxIO.parseStructure(in2);
        } catch (Exception ex) {
            Logger.getLogger(Sdmx20StructureParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertNotNull(doc2);
        InputStream in3 = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/abs-20/ba_gccsa-structure.xml");
        StructureType doc3 = null;
        try {
            doc3 = SdmxIO.parseStructure(in3);
        } catch (Exception ex) {
            Logger.getLogger(Sdmx20StructureParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertNotNull(doc3);
        InputStream in4 = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/abs-20/ba_sa2-structure.xml");
        StructureType doc4 = null;
        try {
            doc4 = SdmxIO.parseStructure(in4);
        } catch (Exception ex) {
            Logger.getLogger(Sdmx20StructureParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertNotNull(doc4);
        InputStream in5 = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/abs-20/bop-structure.xml");
        StructureType doc5 = null;
        try {
            doc5 = SdmxIO.parseStructure(in5);
        } catch (Exception ex) {
            Logger.getLogger(Sdmx20StructureParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertNotNull(doc5);
        InputStream in6 = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/abs-20/capex-structure.xml");
        StructureType doc6 = null;
        try {
            doc6 = SdmxIO.parseStructure(in6);
        } catch (Exception ex) {
            Logger.getLogger(Sdmx20StructureParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertNotNull(doc6);
        InputStream in7 = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/abs-20/cpi-structure.xml");
        StructureType doc7 = null;
        try {
            doc7 = SdmxIO.parseStructure(in7);
        } catch (Exception ex) {
            Logger.getLogger(Sdmx20StructureParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertNotNull(doc7);
        InputStream in8 = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/abs-20/hf_structure.xml");
        StructureType doc8 = null;
        try {
            doc8 = SdmxIO.parseStructure(in8);
        } catch (Exception ex) {
            Logger.getLogger(Sdmx20StructureParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertNotNull(doc8);
        InputStream in9 = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/abs-20/house_price_indexes-structure.xml");
        StructureType doc9 = null;
        try {
            doc9 = SdmxIO.parseStructure(in9);
        } catch (Exception ex) {
            Logger.getLogger(Sdmx20StructureParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertNotNull(doc9);
        InputStream in10 = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/abs-20/ias_2-structure.xml");
        StructureType doc10 = null;
        try {
            doc10 = SdmxIO.parseStructure(in10);
        } catch (Exception ex) {
            Logger.getLogger(Sdmx20StructureParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertNotNull(doc10);
        InputStream in11 = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/abs-20/ias_3-structure.xml");
        StructureType doc11 = null;
        try {
            doc11 = SdmxIO.parseStructure(in11);
        } catch (Exception ex) {
            Logger.getLogger(Sdmx20StructureParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertNotNull(doc11);
        InputStream in12 = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/abs-20/ias_3-structure.xml");
        StructureType doc12 = null;
        try {
            doc12 = SdmxIO.parseStructure(in12);
        } catch (Exception ex) {
            Logger.getLogger(Sdmx20StructureParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertNotNull(doc12);
        InputStream in13 = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/abs-20/ias_4-structure.xml");
        StructureType doc13 = null;
        try {
            doc13 = SdmxIO.parseStructure(in13);
        } catch (Exception ex) {
            Logger.getLogger(Sdmx20StructureParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertNotNull(doc13);
        InputStream in14 = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/abs-20/ias_5-structure.xml");
        StructureType doc14 = null;
        try {
            doc14 = SdmxIO.parseStructure(in14);
        } catch (Exception ex) {
            Logger.getLogger(Sdmx20StructureParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertNotNull(doc14);
        InputStream in15 = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/abs-20/iip_dsid5671-structure.xml");
        StructureType doc15 = null;
        try {
            doc15 = SdmxIO.parseStructure(in15);
        } catch (Exception ex) {
            Logger.getLogger(Sdmx20StructureParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertNotNull(doc15);
        InputStream in16 = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/abs-20/itpi_export-structure.xml");
        StructureType doc16 = null;
        try {
            doc16 = SdmxIO.parseStructure(in16);
        } catch (Exception ex) {
            Logger.getLogger(Sdmx20StructureParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertNotNull(doc16);
        InputStream in17 = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/abs-20/itpi_import-structure.xml");
        StructureType doc17 = null;
        try {
            doc17 = SdmxIO.parseStructure(in17);
        } catch (Exception ex) {
            Logger.getLogger(Sdmx20StructureParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertNotNull(doc17);
        InputStream in18 = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/abs-20/labour_price_index-structure.xml");
        StructureType doc18 = null;
        try {
            doc18 = SdmxIO.parseStructure(in18);
        } catch (Exception ex) {
            Logger.getLogger(Sdmx20StructureParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertNotNull(doc18);
        InputStream in19 = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/abs-20/lf-structure.xml");
        StructureType doc19 = null;
        try {
            doc19 = SdmxIO.parseStructure(in19);
        } catch (Exception ex) {
            Logger.getLogger(Sdmx20StructureParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertNotNull(doc19);
        InputStream in20 = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/abs-20/merch_exp-structure.xml");
        StructureType doc20 = null;
        try {
            doc20 = SdmxIO.parseStructure(in20);
        } catch (Exception ex) {
            Logger.getLogger(Sdmx20StructureParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertNotNull(doc20);
        InputStream in21 = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/abs-20/merch_imp-structure.xml");
        StructureType doc21 = null;
        try {
            doc21 = SdmxIO.parseStructure(in21);
        } catch (Exception ex) {
            Logger.getLogger(Sdmx20StructureParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertNotNull(doc21);
        InputStream in22 = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/abs-20/newmvsales-structure.xml");
        StructureType doc22 = null;
        try {
            doc22 = SdmxIO.parseStructure(in22);
        } catch (Exception ex) {
            Logger.getLogger(Sdmx20StructureParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertNotNull(doc22);
        InputStream in23 = Sdmx20StructureParserTest.class.getResourceAsStream("/resources/abs-20/nrplga8-structure.xml");
        StructureType doc23 = null;
        try {
            doc23 = SdmxIO.parseStructure(in23);
        } catch (Exception ex) {
            Logger.getLogger(Sdmx20StructureParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertNotNull(doc23);
    }
}

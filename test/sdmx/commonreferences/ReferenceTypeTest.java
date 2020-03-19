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

package sdmx.commonreferences;

import java.net.URISyntaxException;
import static org.junit.Assert.*;
import org.junit.Test;
import sdmx.commonreferences.types.ObjectTypeCodelistType;
import sdmx.commonreferences.types.PackageTypeCodelistType;
import sdmx.xml.anyURI;

/**
 *
 * @author James
 */
public class ReferenceTypeTest {
    
    public ReferenceTypeTest() {
    }

    @Test
    public void test1() throws URISyntaxException {
        ReferenceType reference = new ReferenceType(new anyURI("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=ECB:ECB_FMD2(1.0)"));
        assertEquals(PackageTypeCodelistType.DATASTRUCTURE,reference.getPack());
        assertEquals(ObjectTypeCodelistType.DATASTRUCTURE,reference.getClazz());
        assertEquals(new NestedNCNameID("ECB").toString(),reference.getAgencyId().toString());
        assertEquals(new NestedID("ECB_FMD2").toString(),reference.getId().toString());
        assertEquals(Version.ONE.toString(),reference.getVersion().toString());
    }
    @Test
    public void test2() throws URISyntaxException {
        ReferenceType reference = new ReferenceType(new anyURI("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=TFFS:CRED_EXT_DEBT(1.0)"));
        assertEquals(PackageTypeCodelistType.DATASTRUCTURE,reference.getPack());
        assertEquals(ObjectTypeCodelistType.DATASTRUCTURE,reference.getClazz());
        assertEquals(new NestedNCNameID("TFFS").toString(),reference.getAgencyId().toString());
        assertEquals(new NestedID("CRED_EXT_DEBT").toString(),reference.getId().toString());
        assertEquals(Version.ONE.toString(),reference.getVersion().toString());
    }
    @Test
    public void test3() throws URISyntaxException {
        ReferenceType reference = new ReferenceType(new anyURI("urn:sdmx:org.sdmx.infomodel.codelist.Code=ISO:CL_3166A2(1.0).AR"));
        assertEquals(PackageTypeCodelistType.CODELIST,reference.getPack());
        assertEquals(ObjectTypeCodelistType.CODE,reference.getClazz());
        assertEquals(new NestedNCNameID("ISO").toString(),reference.getAgencyId().toString());
        assertEquals(new NestedID("CL_3166A2").toString(),reference.getMaintainableParentId().toString());
        assertEquals(new IDType("AR").toString(),reference.getId().toString());
        assertEquals(Version.ONE.toString(),reference.getVersion().toString());
    }
    @Test
    public void test4() throws URISyntaxException {
        ReferenceType reference = new ReferenceType(new anyURI("urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ESTAT:ESTAT_DATAFLOWS_SCHEME(1.0).STS.STSCONS"));
        assertEquals(PackageTypeCodelistType.CATEGORYSCHEME,reference.getPack());
        assertEquals(ObjectTypeCodelistType.CATEGORY,reference.getClazz());
        assertEquals(new NestedNCNameID("ESTAT").toString(),reference.getAgencyId().toString());
        assertEquals(new NestedID("ESTAT_DATAFLOWS_SCHEME").toString(),reference.getMaintainableParentId().toString());
        assertEquals(new IDType("STS").toString(),reference.getContainedObjectIds()[0].toString());
        assertEquals(new IDType("STSCONS").toString(),reference.getId().toString());
        assertEquals(Version.ONE.toString(),reference.getVersion().toString());
    }
    @Test
    public void test5() throws URISyntaxException {
        ReferenceType reference = new ReferenceType(new anyURI("urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).UNIT_MULT"));
        assertEquals(PackageTypeCodelistType.CONCEPTSCHEME,reference.getPack());
        assertEquals(ObjectTypeCodelistType.CONCEPT,reference.getClazz());
        assertEquals(new NestedNCNameID("SDMX").toString(),reference.getAgencyId().toString());
        assertEquals(new NestedID("CROSS_DOMAIN_CONCEPTS").toString(),reference.getMaintainableParentId().toString());
        assertEquals(new IDType("UNIT_MULT").toString(),reference.getId().toString());
        assertEquals(Version.ONE.toString(),reference.getVersion().toString());
    }

}

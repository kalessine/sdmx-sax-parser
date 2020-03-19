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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import sdmx.commonreferences.DataflowReference;
import sdmx.message.DataQueryMessage;
import sdmx.message.DataStructureQueryMessage;
import sdmx.message.QueryMessage;
import sdmx.query.base.NumericValue;
import sdmx.query.base.QueryIDType;
import sdmx.query.base.TextValue;
import sdmx.query.base.TimeValue;
import sdmx.query.data.AttributeValueType;
import sdmx.query.data.DataParametersAndType;
import sdmx.query.data.DataParametersOrType;
import sdmx.query.data.DataParametersType;
import sdmx.query.data.DataQueryType;
import sdmx.query.data.DimensionValueType;
import sdmx.query.data.TimeDimensionValueType;
import sdmx.querykey.Query;
import sdmx.structure.dataflow.DataflowType;
import sdmx.xml.DateTime;

/**
 *
 * @author James
 */
public class QueryToSdmx20Query {

    public static final SimpleDateFormat displayFormat = new SimpleDateFormat("yyyy-MM-dd");
    /*
<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:sdmx="http://stats.oecd.org/OECDStatWS/SDMX/">
   <soap:Body>
      <sdmx:GetDataStructureDefinition>
         <sdmx:QueryMessage><message:QueryMessage xmlns:message="http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message"><Header xmlns="http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message"><message:ID>none</message:ID><message:Test>false</message:Test><message:Prepared>2016-08-19T00:04:18+08:00</message:Prepared><message:Sender id="Sdmx-Sax" /><message:Receiver id="ABS" /></Header><message:Query><query:KeyFamilyWhere xmlns:query="http://www.SDMX.org/resources/SDMXML/schemas/v2_0/query"><query:And /></query:KeyFamilyWhere></message:Query></message:QueryMessage>
</sdmx:QueryMessage>
      </sdmx:GetDataStructureDefinition>
   </soap:Body>
</soap:Envelope>
     */

    public static String toGetDataStructureListQuery12(String providerRef, String soapNamespace) {
        StringBuffer sb = new StringBuffer();
        sb.append("<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:sdmx=\"http://stats.oecd.org/OECDStatWS/SDMX/\"><soap:Body><sdmx:GetDataStructureDefinition>"
                + "<sdmx:QueryMessage><message:QueryMessage xmlns:message=\"http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message\"><Header xmlns=\"http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message\"><message:ID>none</message:ID><message:Test>false</message:Test><message:Prepared>2016-08-19T00:04:18+08:00</message:Prepared><message:Sender id=\"Sdmx-Sax\" /><message:Receiver id=\"" + providerRef + "\" /></Header><message:Query><query:KeyFamilyWhere xmlns:query=\"http://www.SDMX.org/resources/SDMXML/schemas/v2_0/query\"><query:And /></query:KeyFamilyWhere></message:Query></message:QueryMessage>"
                + "</sdmx:QueryMessage></sdmx:GetDataStructureDefinition></soap:Body></soap:Envelope>");
        return sb.toString();
    }

    public static String toGetDataStructureListQuery11(String providerRef, String soapNamespace) {
        String s = "";
        s += "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:sdmx=\""+soapNamespace+"\">";
        s += "<soapenv:Header></soapenv:Header>";
        s += "<soapenv:Body>";
        s += "<sdmx:GetDataStructureDefinition>";
        s += "<sdmx:QueryMessage>";
        s += "<message:QueryMessage xmlns:message=\"http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message\"><Header xmlns=\"http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message\"><message:ID>none</message:ID><message:Test>false</message:Test><message:Prepared>2016-08-19T00:04:18+08:00</message:Prepared><message:Sender id=\"Sdmx-Sax\" /><message:Receiver id=\"" + providerRef + "\" /></Header><message:Query><query:KeyFamilyWhere xmlns:query=\"http://www.SDMX.org/resources/SDMXML/schemas/v2_0/query\"><query:And /></query:KeyFamilyWhere></message:Query></message:QueryMessage>";
        s += "</sdmx:QueryMessage>";
        s += "</sdmx:GetDataStructureDefinition>";
        s += "</soapenv:Body>";
        s += "</soapenv:Envelope>";
        return s;
    }

    public static String toGetDataStructureQuery(String keyFamily, String providerRef, String soapNamespace) {
        String s  = "";
        s += "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:sdmx=\"" + soapNamespace+"\">";
        s += "<soapenv:Header/>";
        s += "<soapenv:Body>";
        s += "<sdmx:GetDataStructureDefinition>";
        s += "<!--Optional:-->";
        s += "<sdmx:QueryMessage>";
        s += "<message:QueryMessage xsi:schemaLocation=\"http://www.SDMX.org/resources/SDMXML/schemas/v2_0/queryhttp://www.sdmx.org/docs/2_0/SDMXQuery.xsd http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message http://www.sdmx.org/docs/2_0/SDMXMessage.xsd\" xmlns=\"http://www.SDMX.org/resources/SDMXML/schemas/v2_0/query\" xmlns:message=\"http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">";
        s += "<Header xmlns=\"http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message\">";
        s += "<ID>none</ID>";
        s += "<Test>false</Test>";
        s += "<Prepared>2012-06-01T09:33:53</Prepared>";
        s += "<Sender id=\"YourID\">";
        s += "<Name xml:lang=\"en\">Your English Name</Name>";
        s += "</Sender>";
        s += "<Receiver id=\""+providerRef+"\">";
        s += "<Name xml:lang=\"en\">Australian Bureau of Statistics</Name>";
        s += "<Name xml:lang=\"fr\">Australian Bureau of Statistics</Name>";
        s += "</Receiver>";
        s += "</Header>";
        s += "<message:Query>";
        s += "<KeyFamilyWhere>";
        s += "<Or>";
        s += "<KeyFamily>" + keyFamily + "</KeyFamily>";
        s += "</Or>";
        s += "</KeyFamilyWhere>";
        s += "</message:Query>";
        s += "</message:QueryMessage>";
        s += "</sdmx:QueryMessage>";
        s += "</sdmx:GetDataStructureDefinition>";
        s += "</soapenv:Body>";
        s += "</soapenv:Envelope>";
        return s;
    }

    public static String toGetDataQuery(Query q, String soapNamespace) {
        StringBuffer sb = new StringBuffer();
        sb.append("<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n"
                + "  <soap12:Body>\n"
                + "    <GetCompactData xmlns=\"http://stats.oecd.org/OECDStatWS/SDMX/\">\n"
                + "      <QueryMessage><message:QueryMessage xmlns:message=\"http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message\"><Header xmlns=\"http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message\"><message:ID>none</message:ID><message:Test>false</message:Test><message:Prepared>2016-08-19T00:11:33+08:00</message:Prepared><message:Sender id=\"Sdmx-Sax\" /><message:Receiver id=\"" + q.getProviderRef() + "\" /></Header><message:Query><DataWhere xmlns=\"http://www.SDMX.org/resources/SDMXML/schemas/v2_0/query\"><And><DataSet>" + q.getFlowRef() + "</DataSet>"
                +(q.getQueryTime()!=null?("<Time><StartTime>" + displayFormat.format(q.getQueryTime().getStartTime()) + "</StartTime><EndTime>" + displayFormat.format(q.getQueryTime().getEndTime()) + "</EndTime></Time>"):""));
        for (int i = 0; i < q.size(); i++) {
            if (q.getQueryDimension(i).size() > 0) {
                sb.append("<Or>");
                for (int j = 0; j < q.getQueryDimension(i).size(); j++) {
                    sb.append("<Dimension id=\"" + q.getQueryDimension(i).getConcept() + "\">" + q.getQueryDimension(i).getValues().get(j) + "</Dimension>");
                }
                sb.append("</Or>");
            }
        }
        sb.append("</And></DataWhere></message:Query></message:QueryMessage>\n");
        sb.append("</QueryMessage>\n");
        sb.append("</GetCompactData>\n");
        sb.append("</soap12:Body>\n");
        sb.append("</soap12:Envelope>");
        return sb.toString();
    }

    public static String toNSIGetDataStructureListQuery(String providerRef, String soapNamespace) {
        StringBuffer sb = new StringBuffer();
        sb.append("<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:sdmx=\"http://ec.europa.eu/eurostat/sri/service/2.0\">");
        sb.append("<soap:Body>");
        sb.append("<sdmx:QueryStructure>");
        sb.append("<sdmx:Query>");
        sb.append("<message:RegistryInterface xmlns:message=\"http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message\">");
        sb.append("<Header xmlns=\"http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message\">");
        sb.append("<message:ID>none</message:ID>");
        sb.append("<message:Test>false</message:Test>");
        sb.append("<message:Prepared>2016-08-19T19:32:41+08:00</message:Prepared>");
        sb.append("<message:Sender id=\"Sdmx-Sax\"/>");
        sb.append("<message:Receiver id=\"" + providerRef + "\"/>");
        sb.append("</Header>");
        sb.append("<message:QueryStructureRequest resolveReferences=\"false\">");
        sb.append("<registry:DataflowRef xmlns:registry=\"http://www.SDMX.org/resources/SDMXML/schemas/v2_0/registry\"/>");
        sb.append("</message:QueryStructureRequest>");
        sb.append("</message:RegistryInterface>");
        sb.append("</sdmx:Query>");
        sb.append("</sdmx:QueryStructure>");
        sb.append("</soap:Body>");
        sb.append("</soap:Envelope>");
        return sb.toString();
    }

    public static String toNSIGetDataStructureQuery(String keyFamily, String providerRef, String soapNamespace, String version) {
        StringBuffer sb = new StringBuffer();
        sb.append("<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:sdmx=\"http://ec.europa.eu/eurostat/sri/service/2.0\">\n"
                + "   <soap:Body>\n"
                + "      <sdmx:QueryStructure>\n"
                + "         <sdmx:Query>\n"
                + "            <message:RegistryInterface xmlns:message=\"http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message\">\n"
                + "               <Header xmlns=\"http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message\">\n"
                + "                  <message:ID>none</message:ID>\n"
                + "                  <message:Test>false</message:Test>\n"
                + "                  <message:Prepared>2016-09-23T21:21:56+08:00</message:Prepared>\n"
                + "                  <message:Sender id=\"Sdmx-Sax\"/>\n"
                + "                  <message:Receiver id=\"" + providerRef + "\"/>\n"
                + "               </Header>\n"
                + "               <message:QueryStructureRequest resolveReferences=\"true\">\n"
                + "                  <registry:KeyFamilyRef xmlns:registry=\"http://www.SDMX.org/resources/SDMXML/schemas/v2_0/registry\">\n"
                + "                     <registry:AgencyID>" + providerRef + "</registry:AgencyID>\n"
                + "                     <registry:KeyFamilyID>" + keyFamily + "</registry:KeyFamilyID>\n"
                + "                     <registry:Version>" + version + "</registry:Version>\n"
                + "                  </registry:KeyFamilyRef>\n"
                + "               </message:QueryStructureRequest>\n"
                + "            </message:RegistryInterface>\n"
                + "         </sdmx:Query>\n"
                + "      </sdmx:QueryStructure>\n"
                + "   </soap:Body>\n"
                + "</soap:Envelope>");
        return sb.toString();
    }

    public static String toNSIGetDataQuery(Query q, String soapNamespace) {
        StringBuffer sb = new StringBuffer();
        sb.append("<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">");
        sb.append("<soap12:Body>");
        sb.append("<GetCompactData xmlns=\"http://ec.europa.eu/eurostat/sri/service/2.0\">");
        sb.append("<Query><message:QueryMessage xmlns:message=\"http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message\"><Header xmlns=\"http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message\"><message:ID>none</message:ID><message:Test>false</message:Test><message:Prepared>2016-08-19T19:55:37+08:00</message:Prepared><message:Sender id=\"Sdmx-Sax\" /><message:Receiver id=\"" + q.getProviderRef() + "\" /></Header><message:Query><DataWhere xmlns=\"http://www.SDMX.org/resources/SDMXML/schemas/v2_0/query\"><And><Time><StartTime>" + displayFormat.format(q.getQueryTime().getStartTime()) + "</StartTime><EndTime>" + displayFormat.format(q.getQueryTime().getEndTime()) + "</EndTime></Time><Dataflow>" + q.getFlowRef() + "</Dataflow>");
        for (int i = 0; i < q.size(); i++) {
            if (q.getQueryDimension(i).size() > 0) {
                sb.append("<Or>");
                for (int j = 0; j < q.getQueryDimension(i).size(); j++) {
                    sb.append("<Dimension id=\"" + q.getQueryDimension(i).getConcept() + "\">" + q.getQueryDimension(i).getValues().get(j) + "</Dimension>");
                }
                sb.append("</Or>");
            }
        }
        sb.append("</And></DataWhere></message:Query></message:QueryMessage>");
        sb.append("</Query>");
        sb.append("</GetCompactData>");
        sb.append("</soap12:Body>");
        sb.append("</soap12:Envelope>");
        return sb.toString();
    }

    public static String toIMFGetDataStructureListQuery(String providerRef, String soapNamespace) {
        return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://www.sdmx.org/resources/sdmxml/schemas/v2_1/webservices\" xmlns:mes=\"http://www.sdmx.org/resources/sdmxml/schemas/v2_1/message\" xmlns:com=\"http://www.sdmx.org/resources/sdmxml/schemas/v2_1/common\" xmlns:quer=\"http://www.sdmx.org/resources/sdmxml/schemas/v2_1/query\">\n"
                + "   <soapenv:Header/>\n"
                + "   <soapenv:Body>\n"
                + "      <web:GetDataflow>\n"
                + "         <mes:DataflowQuery>\n"
                + "            <mes:Header>\n"
                + "               <mes:ID>?</mes:ID>\n"
                + "               <mes:Test>false</mes:Test>\n"
                + "               <mes:Prepared>?</mes:Prepared>\n"
                + "               <mes:Sender id=\"?\">\n"
                + "                  <!--Zero or more repetitions:-->\n"
                + "                  <com:Name xml:lang=\"en\">?</com:Name>\n"
                + "                  <!--Zero or more repetitions:-->\n"
                + "                  <mes:Contact>\n"
                + "                     <!--Zero or more repetitions:-->\n"
                + "                     <com:Name xml:lang=\"en\">?</com:Name>\n"
                + "                     <!--Zero or more repetitions:-->\n"
                + "                     <mes:Department xml:lang=\"en\">?</mes:Department>\n"
                + "                     <!--Zero or more repetitions:-->\n"
                + "                     <mes:Role xml:lang=\"en\">?</mes:Role>\n"
                + "                     <!--You have a CHOICE of the next 5 items at this level-->\n"
                + "                     <mes:Telephone>?</mes:Telephone>\n"
                + "                     <mes:Fax>?</mes:Fax>\n"
                + "                     <mes:X400>?</mes:X400>\n"
                + "                     <mes:URI>?</mes:URI>\n"
                + "                     <mes:Email>?</mes:Email>\n"
                + "                  </mes:Contact>\n"
                + "                  <!--Optional:-->\n"
                + "                  <mes:Timezone>?</mes:Timezone>\n"
                + "               </mes:Sender>\n"
                + "               <mes:Receiver id=\"?\">\n"
                + "                  <!--Zero or more repetitions:-->\n"
                + "                  <com:Name xml:lang=\"en\">?</com:Name>\n"
                + "                  <!--Zero or more repetitions:-->\n"
                + "                  <mes:Contact>\n"
                + "                     <!--Zero or more repetitions:-->\n"
                + "                     <com:Name xml:lang=\"en\">?</com:Name>\n"
                + "                     <!--Zero or more repetitions:-->\n"
                + "                     <mes:Department xml:lang=\"en\">?</mes:Department>\n"
                + "                     <!--Zero or more repetitions:-->\n"
                + "                     <mes:Role xml:lang=\"en\">?</mes:Role>\n"
                + "                     <!--You have a CHOICE of the next 5 items at this level-->\n"
                + "                     <mes:Telephone>?</mes:Telephone>\n"
                + "                     <mes:Fax>?</mes:Fax>\n"
                + "                     <mes:X400>?</mes:X400>\n"
                + "                     <mes:URI>?</mes:URI>\n"
                + "                     <mes:Email>?</mes:Email>\n"
                + "                  </mes:Contact>\n"
                + "               </mes:Receiver>\n"
                + "            </mes:Header>\n"
                + "            <mes:Query>\n"
                + "               <quer:ReturnDetails defaultLimit=\"?\" detail=\"Full\" returnMatchedArtefact=\"true\">\n"
                + "                  <quer:References processConstraints=\"false\" detail=\"Full\">\n"
                + "                     <!--You have a CHOICE of the next 7 items at this level-->\n"
                + "                     <quer:None/>\n"
                + "                     <quer:All/>\n"
                + "                     <quer:Parents/>\n"
                + "                     <quer:ParentsAndSiblings/>\n"
                + "                     <quer:Children/>\n"
                + "                     <quer:Descendants/>\n"
                + "                     <quer:SpecificObjects>\n"
                + "                        <!--You may enter the following 19 items in any order-->\n"
                + "                        <!--Optional:-->\n"
                + "                        <com:AgencyScheme/>\n"
                + "                        <!--Optional:-->\n"
                + "                        <com:AttachmentConstraint/>\n"
                + "                        <!--Optional:-->\n"
                + "                        <com:Categorisation/>\n"
                + "                        <!--Optional:-->\n"
                + "                        <com:CategoryScheme/>\n"
                + "                        <!--Optional:-->\n"
                + "                        <com:Codelist/>\n"
                + "                        <!--Optional:-->\n"
                + "                        <com:ConceptScheme/>\n"
                + "                        <!--Optional:-->\n"
                + "                        <com:ContentConstraint/>\n"
                + "                        <!--Optional:-->\n"
                + "                        <com:Dataflow/>\n"
                + "                        <!--Optional:-->\n"
                + "                        <com:DataConsumerScheme/>\n"
                + "                        <!--Optional:-->\n"
                + "                        <com:DataProviderScheme/>\n"
                + "                        <!--Optional:-->\n"
                + "                        <com:DataStructure/>\n"
                + "                        <!--Optional:-->\n"
                + "                        <com:HierarchicalCodelist/>\n"
                + "                        <!--Optional:-->\n"
                + "                        <com:Metadataflow/>\n"
                + "                        <!--Optional:-->\n"
                + "                        <com:MetadataStructure/>\n"
                + "                        <!--Optional:-->\n"
                + "                        <com:OrganisationUnitScheme/>\n"
                + "                        <!--Optional:-->\n"
                + "                        <com:Process/>\n"
                + "                        <!--Optional:-->\n"
                + "                        <com:ProvisionAgreement/>\n"
                + "                        <!--Optional:-->\n"
                + "                        <com:ReportingTaxonomy/>\n"
                + "                        <!--Optional:-->\n"
                + "                        <com:StructureSet/>\n"
                + "                     </quer:SpecificObjects>\n"
                + "                  </quer:References>\n"
                + "               </quer:ReturnDetails>\n"
                + "               <quer:DataflowWhere type=\"Dataflow\">\n"
                + "                  <!--Optional:-->\n"
                + "                  <quer:Annotation>\n"
                + "                     <!--Optional:-->\n"
                + "                     <quer:Type operator=\"equal\">?</quer:Type>\n"
                + "                     <!--Optional:-->\n"
                + "                     <quer:Title operator=\"equal\">?</quer:Title>\n"
                + "                     <!--Optional:-->\n"
                + "                     <quer:Text xml:lang=\"en\" operator=\"equal\">?</quer:Text>\n"
                + "                  </quer:Annotation>\n"
                + "                  <!--Optional:-->\n"
                + "                  <quer:URN>?</quer:URN>\n"
                + "                  <!--Optional:-->\n"
                + "                  <quer:ID operator=\"equal\">?</quer:ID>\n"
                + "                  <!--Optional:-->\n"
                + "                  <quer:Name xml:lang=\"en\" operator=\"equal\">?</quer:Name>\n"
                + "                  <!--Optional:-->\n"
                + "                  <quer:Description xml:lang=\"en\" operator=\"equal\">?</quer:Description>\n"
                + "                  <!--Optional:-->\n"
                + "                  <quer:Version>?</quer:Version>\n"
                + "                  <!--Optional:-->\n"
                + "                  <quer:VersionTo>\n"
                + "                     <!--You have a CHOICE of the next 3 items at this level-->\n"
                + "                     <com:BeforePeriod isInclusive=\"true\">?</com:BeforePeriod>\n"
                + "                     <com:AfterPeriod isInclusive=\"true\">?</com:AfterPeriod>\n"
                + "                     <com:StartPeriod isInclusive=\"true\">?</com:StartPeriod>\n"
                + "                     <com:EndPeriod isInclusive=\"true\">?</com:EndPeriod>\n"
                + "                  </quer:VersionTo>\n"
                + "                  <!--Optional:-->\n"
                + "                  <quer:VersionFrom>\n"
                + "                     <!--You have a CHOICE of the next 3 items at this level-->\n"
                + "                     <com:BeforePeriod isInclusive=\"true\">?</com:BeforePeriod>\n"
                + "                     <com:AfterPeriod isInclusive=\"true\">?</com:AfterPeriod>\n"
                + "                     <com:StartPeriod isInclusive=\"true\">?</com:StartPeriod>\n"
                + "                     <com:EndPeriod isInclusive=\"true\">?</com:EndPeriod>\n"
                + "                  </quer:VersionFrom>\n"
                + "                  <!--Optional:-->\n"
                + "                  <quer:VersionActive>?</quer:VersionActive>\n"
                + "                  <!--Optional:-->\n"
                + "                  <quer:AgencyID operator=\"equal\">?</quer:AgencyID>\n"
                + "                  <!--Optional:-->\n"
                + "                  <quer:Structure>\n"
                + "                     <!--You have a CHOICE of the next 2 items at this level-->\n"
                + "                     <Ref agencyID=\"?\" id=\"?\" version=\"1.0\" local=\"false\" class=\"DataStructure\" package=\"datastructure\"/>\n"
                + "                     <!--Optional:-->\n"
                + "                     <URN>?</URN>\n"
                + "                     <URN>?</URN>\n"
                + "                  </quer:Structure>\n"
                + "               </quer:DataflowWhere>\n"
                + "            </mes:Query>\n"
                + "         </mes:DataflowQuery>\n"
                + "      </web:GetDataflow>\n"
                + "   </soapenv:Body>\n"
                + "</soapenv:Envelope>";
    }

    public static String toIMFGetDataStructureQuery(String keyFamily, String providerRef, String soapNamespace) {
        return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://www.sdmx.org/resources/sdmxml/schemas/v2_1/webservices\" xmlns:mes=\"http://www.sdmx.org/resources/sdmxml/schemas/v2_1/message\" xmlns:com=\"http://www.sdmx.org/resources/sdmxml/schemas/v2_1/common\" xmlns:quer=\"http://www.sdmx.org/resources/sdmxml/schemas/v2_1/query\">\n"
                + "   <soapenv:Header/>\n"
                + "   <soapenv:Body>\n"
                + "      <web:GetDataStructure>\n"
                + "         <mes:DataStructureQuery>\n"
                + "            <mes:Header>\n"
                + "               <mes:ID>?</mes:ID>\n"
                + "               <mes:Test>false</mes:Test>\n"
                + "               <mes:Prepared>?</mes:Prepared>\n"
                + "               <mes:Sender id=\"?\">\n"
                + "                  <!--Zero or more repetitions:-->\n"
                + "                  <com:Name xml:lang=\"en\">?</com:Name>\n"
                + "                  <!--Zero or more repetitions:-->\n"
                + "                  <mes:Contact>\n"
                + "                     <!--Zero or more repetitions:-->\n"
                + "                     <com:Name xml:lang=\"en\">?</com:Name>\n"
                + "                     <!--Zero or more repetitions:-->\n"
                + "                     <mes:Department xml:lang=\"en\">?</mes:Department>\n"
                + "                     <!--Zero or more repetitions:-->\n"
                + "                     <mes:Role xml:lang=\"en\">?</mes:Role>\n"
                + "                     <!--You have a CHOICE of the next 5 items at this level-->\n"
                + "                     <mes:Telephone>?</mes:Telephone>\n"
                + "                     <mes:Fax>?</mes:Fax>\n"
                + "                     <mes:X400>?</mes:X400>\n"
                + "                     <mes:URI>?</mes:URI>\n"
                + "                     <mes:Email>?</mes:Email>\n"
                + "                  </mes:Contact>\n"
                + "                  <!--Optional:-->\n"
                + "                  <mes:Timezone>?</mes:Timezone>\n"
                + "               </mes:Sender>\n"
                + "               <mes:Receiver id=\"?\">\n"
                + "                  <!--Zero or more repetitions:-->\n"
                + "                  <com:Name xml:lang=\"en\">?</com:Name>\n"
                + "                  <!--Zero or more repetitions:-->\n"
                + "                  <mes:Contact>\n"
                + "                     <!--Zero or more repetitions:-->\n"
                + "                     <com:Name xml:lang=\"en\">?</com:Name>\n"
                + "                     <!--Zero or more repetitions:-->\n"
                + "                     <mes:Department xml:lang=\"en\">?</mes:Department>\n"
                + "                     <!--Zero or more repetitions:-->\n"
                + "                     <mes:Role xml:lang=\"en\">?</mes:Role>\n"
                + "                     <!--You have a CHOICE of the next 5 items at this level-->\n"
                + "                     <mes:Telephone>?</mes:Telephone>\n"
                + "                     <mes:Fax>?</mes:Fax>\n"
                + "                     <mes:X400>?</mes:X400>\n"
                + "                     <mes:URI>?</mes:URI>\n"
                + "                     <mes:Email>?</mes:Email>\n"
                + "                  </mes:Contact>\n"
                + "               </mes:Receiver>\n"
                + "            </mes:Header>\n"
                + "            <mes:Query>\n"
                + "               <quer:ReturnDetails defaultLimit=\"?\" detail=\"Full\" returnMatchedArtefact=\"true\">\n"
                + "                  <quer:References processConstraints=\"false\" detail=\"Full\">\n"
                + "                     <!--You have a CHOICE of the next 7 items at this level-->\n"
                + "                     <quer:Children/>\n"
                + "                  </quer:References>\n"
                + "               </quer:ReturnDetails>\n"
                + "               <quer:DataStructureWhere type=\"DataStructure\">\n"
                + "                  <quer:ID operator=\"equal\">" + keyFamily + "</quer:ID>\n"
                + "                   <quer:AgencyID operator=\"equal\">" + providerRef + "</quer:AgencyID>\n"
                + "               </quer:DataStructureWhere>\n"
                + "            </mes:Query>\n"
                + "         </mes:DataStructureQuery>\n"
                + "      </web:GetDataStructure>\n"
                + "   </soapenv:Body>\n"
                + "</soapenv:Envelope>";
    }

    public static String toIMFGetDataQuery(Query q, String soapNamespace) {
        StringBuffer sb = new StringBuffer();
        sb.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://www.sdmx.org/resources/sdmxml/schemas/v2_1/webservices\" xmlns:mes=\"http://www.sdmx.org/resources/sdmxml/schemas/v2_1/message\" xmlns:com=\"http://www.sdmx.org/resources/sdmxml/schemas/v2_1/common\" xmlns:quer=\"http://www.sdmx.org/resources/sdmxml/schemas/v2_1/query\">\n"
                + "   <soapenv:Header/>\n"
                + "   <soapenv:Body>\n"
                + "      <web:GetStructureSpecificData>\n"
                + "         <mes:StructureSpecificDataQuery>\n"
                + "            <mes:Header>\n"
                + "               <mes:ID>?</mes:ID>\n"
                + "               <mes:Test>false</mes:Test>\n"
                + "               <mes:Prepared>?</mes:Prepared>\n"
                + "               <mes:Sender id=\"?\">\n"
                + "                  <!--Zero or more repetitions:-->\n"
                + "                  <com:Name xml:lang=\"en\">?</com:Name>\n"
                + "                  <!--Zero or more repetitions:-->\n"
                + "                  <mes:Contact>\n"
                + "                     <!--Zero or more repetitions:-->\n"
                + "                     <com:Name xml:lang=\"en\">?</com:Name>\n"
                + "                     <!--Zero or more repetitions:-->\n"
                + "                     <mes:Department xml:lang=\"en\">?</mes:Department>\n"
                + "                     <!--Zero or more repetitions:-->\n"
                + "                     <mes:Role xml:lang=\"en\">?</mes:Role>\n"
                + "                     <!--You have a CHOICE of the next 5 items at this level-->\n"
                + "                     <mes:Telephone>?</mes:Telephone>\n"
                + "                     <mes:Fax>?</mes:Fax>\n"
                + "                     <mes:X400>?</mes:X400>\n"
                + "                     <mes:URI>?</mes:URI>\n"
                + "                     <mes:Email>?</mes:Email>\n"
                + "                  </mes:Contact>\n"
                + "                  <!--Optional:-->\n"
                + "                  <mes:Timezone>?</mes:Timezone>\n"
                + "               </mes:Sender>\n"
                + "               <mes:Receiver id=\"" + q.getProviderRef() + "\">\n"
                + "                  <!--Zero or more repetitions:-->\n"
                + "                  <com:Name xml:lang=\"en\">?</com:Name>\n"
                + "                  <!--Zero or more repetitions:-->\n"
                + "                  <mes:Contact>\n"
                + "                     <!--Zero or more repetitions:-->\n"
                + "                     <com:Name xml:lang=\"en\">?</com:Name>\n"
                + "                     <!--Zero or more repetitions:-->\n"
                + "                     <mes:Department xml:lang=\"en\">?</mes:Department>\n"
                + "                     <!--Zero or more repetitions:-->\n"
                + "                     <mes:Role xml:lang=\"en\">?</mes:Role>\n"
                + "                     <!--You have a CHOICE of the next 5 items at this level-->\n"
                + "                     <mes:Telephone>?</mes:Telephone>\n"
                + "                     <mes:Fax>?</mes:Fax>\n"
                + "                     <mes:X400>?</mes:X400>\n"
                + "                     <mes:URI>?</mes:URI>\n"
                + "                     <mes:Email>?</mes:Email>\n"
                + "                  </mes:Contact>\n"
                + "               </mes:Receiver>\n"
                + "            </mes:Header>\n"
                + "            <mes:Query>\n"
                + "               <quer:ReturnDetails defaultLimit=\"?\" detail=\"Full\" observationAction=\"Active\">\n"
                + "                  <!--Optional:-->\n"
                + "                  <quer:FirstNObservations>?</quer:FirstNObservations>\n"
                + "                  <!--Optional:-->\n"
                + "                  <quer:LastNObservations>?</quer:LastNObservations>\n"
                + "                  <!--Zero or more repetitions:-->\n"
                + "               </quer:ReturnDetails>\n"
                + "               <quer:DataWhere>\n"
                + "                  <!--Optional:-->\n"
                + "                  <quer:DataSetID operator=\"equal\">" + q.getFlowRef() + "</quer:DataSetID>\n"
                + "                  <quer:TimeDimensionValue>\n"
                + "                     <!--Optional:-->\n"
                + "                     <quer:ID>TIME_PERIOD</quer:ID>\n"
                + "                     <!--1 to 2 repetitions:-->\n"
                + "                        <quer:TimeValue operator=\"after\" reportingYearStartDay=\"" + displayFormat.format(q.getQueryTime().getStartTime()) + "\">?</quer:TimeValue>\n"
                + "                        <quer:TimeValue operator=\"before\" reportingYearStartDay=\"" + displayFormat.format(q.getQueryTime().getEndTime()) + "\">?</quer:TimeValue>\n"
                + "                  </quer:TimeDimensionValue>\n");
        for (int i = 0; i < q.size(); i++) {
            if (q.getQueryDimension(i).size() > 0) {
                sb.append("<Or>");
                for (int j = 0; j < q.getQueryDimension(i).size(); j++) {
                    sb.append("<Dimension id=\"" + q.getQueryDimension(i).getConcept() + "\">" + q.getQueryDimension(i).getValues().get(j) + "</Dimension>");
                }
                sb.append("</Or>");
            }
        }
        sb.append("               </quer:DataWhere>\n"
                + "            </mes:Query>\n"
                + "         </mes:StructureSpecificDataQuery>\n"
                + "      </web:GetStructureSpecificData>\n"
                + "   </soapenv:Body>\n"
                + "</soapenv:Envelope>");
        return sb.toString();
    }
}

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
package sdmx.message;

import java.util.Arrays;
import java.util.List;
import sdmx.common.ActionType;
import sdmx.common.Name;
import sdmx.common.ObservationalTimePeriodType;
import sdmx.common.PayloadStructureType;
import sdmx.common.TextType;
import sdmx.commonreferences.DataProviderReference;
import sdmx.commonreferences.IDType;
import sdmx.xml.DateTime;

/**
 *	<xs:complexType name="BaseHeaderType" abstract="true">
		<xs:annotation>
			<xs:documentation>BaseHeaderType in an abstract base type that defines the basis for all message headers. Specific message formats will refine this</xs:documentation>
		</xs:annotation>
		<xs:sequence>
			<xs:element name="ID" type="common:IDType">
				<xs:annotation>
					<xs:documentation>ID identifies an identification for the message, assigned by the sender.</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element name="Test" type="xs:boolean" default="false">
				<xs:annotation>
					<xs:documentation>Test indicates whether the message is for test purposes or not.</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element name="Prepared" type="HeaderTimeType">
				<xs:annotation>
					<xs:documentation>Prepared is the date the message was prepared.</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element name="Sender" type="SenderType">
				<xs:annotation>
					<xs:documentation>Sender is information about the party that is transmitting the message.</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element name="Receiver" type="PartyType" minOccurs="0" maxOccurs="unbounded">
				<xs:annotation>
					<xs:documentation>Receiver is information about the party that is the intended recipient of the message.</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element ref="common:Name" minOccurs="0" maxOccurs="unbounded">
				<xs:annotation>
					<xs:documentation>Name provides a name for the transmission. Multiple instances allow for parallel language values.</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element name="Structure" type="common:PayloadStructureType" minOccurs="0" maxOccurs="unbounded">
				<xs:annotation>
					<xs:documentation>Structure provides a reference to the structure (either explicitly or through a structure usage reference) that describes the format of data or reference metadata. In addition to the structure, it is required to also supply the namespace of the structure specific schema that defines the format of the data/metadata. For cross sectional data, additional information is also required to state which dimension is being used at the observation level. This information will allow the structure specific schema to be generated. For generic format messages, this is used to simply reference the underlying structure. It is not mandatory in these cases and the generic data/metadata sets will require this reference explicitly.</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element name="DataProvider" type="common:DataProviderReferenceType" minOccurs="0">
				<xs:annotation>
					<xs:documentation>DataProvider identifies the provider of the data for a data/reference metadata message.</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element name="DataSetAction" type="common:ActionType" minOccurs="0">
				<xs:annotation>
					<xs:documentation>DataSetAction code provides a code for determining whether the enclosed message is an Update or Delete message (not to be used with the UtilityData message).</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element name="DataSetID" type="common:IDType" minOccurs="0" maxOccurs="unbounded">
				<xs:annotation>
					<xs:documentation>DataSetID provides an identifier for a contained data set.</xs:documentation>
				</xs:annotation>
			</xs:element>	
			<xs:element name="Extracted" type="xs:dateTime" minOccurs="0">
				<xs:annotation>
					<xs:documentation>Extracted is a time-stamp from the system rendering the data.</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element name="ReportingBegin" type="common:ObservationalTimePeriodType" minOccurs="0">
				<xs:annotation>
					<xs:documentation>ReportingBegin provides the start of the time period covered by the message (in the case of data).</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element name="ReportingEnd" type="common:ObservationalTimePeriodType" minOccurs="0">
				<xs:annotation>
					<xs:documentation>ReportingEnd provides the end of the time period covered by the message (in the case of data).</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element name="EmbargoDate" type="xs:dateTime" minOccurs="0">
				<xs:annotation>
					<xs:documentation>EmbargoDate holds a time period before which the data included in this message is not available.</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element name="Source" type="common:TextType" minOccurs="0" maxOccurs="unbounded">
				<xs:annotation>
					<xs:documentation>Source provides human-readable information about the source of the data.</xs:documentation>
				</xs:annotation>
			</xs:element>
		</xs:sequence>
	</xs:complexType>

 * @author James
 */

public class BaseHeaderType {
    //private IDType id;
    private String id;
    private Boolean test;
    private HeaderTimeType prepared;
    private SenderType sender;
    private List<PartyType> receivers;
    private List<Name> names;
    private List<PayloadStructureType> structures;
    private DataProviderReference dataProvider;
    private ActionType dataSetAction;
    private List<IDType> dataSetID;
    private DateTime extracted;
    private ObservationalTimePeriodType reportingBegin;
    private ObservationalTimePeriodType reportingEnd;
    private DateTime embargoDate;
    private List<TextType> source;

    public BaseHeaderType(){}
    
    /**
     * @return the test
     */
    public Boolean getTest() {
        return test;
    }

    /**
     * @param test the test to set
     */
    public void setTest(Boolean test) {
        this.test = test;
    }

    /**
     * @return the prepared
     */
    public HeaderTimeType getPrepared() {
        return prepared;
    }

    /**
     * @param prepared the prepared to set
     */
    public void setPrepared(HeaderTimeType prepared) {
        this.prepared = prepared;
    }

    /**
     * @return the sender
     */
    public SenderType getSender() {
        return sender;
    }

    /**
     * @param sender the sender to set
     */
    public void setSender(SenderType sender) {
        this.sender = sender;
    }

    /**
     * @return the names
     */
    public List<Name> getNames() {
        return names;
    }

    /**
     * @param names the names to set
     */
    public void setNames(List<Name> names) {
        this.names = names;
    }

    /**
     * @return the dataProvider
     */
    public DataProviderReference getDataProvider() {
        return dataProvider;
    }

    /**
     * @param dataProvider the dataProvider to set
     */
    public void setDataProvider(DataProviderReference dataProvider) {
        this.dataProvider = dataProvider;
    }


    /**
     * @return the extracted
     */
    public DateTime getExtracted() {
        return extracted;
    }

    /**
     * @param extracted the extracted to set
     */
    public void setExtracted(DateTime extracted) {
        this.extracted = extracted;
    }

    /**
     * @return the reportingBegin
     */
    public ObservationalTimePeriodType getReportingBegin() {
        return reportingBegin;
    }

    /**
     * @param reportingBegin the reportingBegin to set
     */
    public void setReportingBegin(ObservationalTimePeriodType reportingBegin) {
        this.reportingBegin = reportingBegin;
    }

    /**
     * @return the reportingEnd
     */
    public ObservationalTimePeriodType getReportingEnd() {
        return reportingEnd;
    }

    /**
     * @param reportingEnd the reportingEnd to set
     */
    public void setReportingEnd(ObservationalTimePeriodType reportingEnd) {
        this.reportingEnd = reportingEnd;
    }

    /**
     * @return the embargoDate
     */
    public DateTime getEmbargoDate() {
        return embargoDate;
    }

    /**
     * @param embargoDate the embargoDate to set
     */
    public void setEmbargoDate(DateTime embargoDate) {
        this.embargoDate = embargoDate;
    }

    /**
     * @return the source
     */
    public List<TextType> getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(List<TextType> source) {
        this.source = source;
    }

    /**
     * @return the dataSetAction
     */
    public ActionType getDataSetAction() {
        return dataSetAction;
    }

    /**
     * @param dataSetAction the dataSetAction to set
     */
    public void setDataSetAction(ActionType dataSetAction) {
        this.dataSetAction = dataSetAction;
    }

    /**
     * @return the dataSetID
     */
    public List<IDType> getDataSetID() {
        return dataSetID;
    }

    /**
     * @param dataSetID the dataSetID to set
     */
    public void setDataSetID(List<IDType> dataSetID) {
        this.dataSetID = dataSetID;
    }

    /**
     * @return the structures
     */
    public List<PayloadStructureType> getStructures() {
        return structures;
    }

    /**
     * @param structures the structures to set
     */
    public void setStructures(List<PayloadStructureType> structures) {
        this.structures = structures;
    }

    /**
     * @return the receivers
     */
    public List<PartyType> getReceivers() {
        return receivers;
    }

    /**
     * @param receivers the receivers to set
     */
    public void setReceivers(List<PartyType> receivers) {
        this.receivers = receivers;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }
    
}

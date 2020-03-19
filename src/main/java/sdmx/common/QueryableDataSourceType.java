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
package sdmx.common;

/**
 *	<xs:complexType name="QueryableDataSourceType">
		<xs:annotation>
			<xs:documentation>QueryableDataSourceType describes a data source which is accepts an standard SDMX Query message and responds appropriately.</xs:documentation>
		</xs:annotation>
		<xs:sequence>
			<xs:element name="DataURL" type="xs:anyURI">
				<xs:annotation>
					<xs:documentation>DataURL contains the URL of the data source.</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element name="WSDLURL" type="xs:anyURI" minOccurs="0">
				<xs:annotation>
					<xs:documentation>WSDLURL provides the location of a WSDL instance on the internet which describes the queryable data source.</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element name="WADLURL" type="xs:anyURI" minOccurs="0">
				<xs:annotation>
					<xs:documentation>WADLURL provides the location of a WADL instance on the internet which describes the REST protocol of the queryable data source.</xs:documentation>
				</xs:annotation>
			</xs:element>
		</xs:sequence>
		<xs:attribute name="isRESTDatasource" type="xs:boolean" use="required">
			<xs:annotation>
				<xs:documentation>The isRESTDatasource attribute indicates, if true, that the queryable data source is accessible via the REST protocol.</xs:documentation>
			</xs:annotation>
		</xs:attribute>
		<xs:attribute name="isWebServiceDatasource" type="xs:boolean" use="required">
			<xs:annotation>
				<xs:documentation>The isWebServiceDatasource attribute indicates, if true, that the queryable data source is accessible via Web Services protocols.</xs:documentation>
			</xs:annotation>
		</xs:attribute>
	</xs:complexType>
 * @author James
 */
public class QueryableDataSourceType {
       private String dataURL = null;
       private String wsdlURL = null;
       private String wadlURL = null;
       private boolean isRestDatasource = false;
       private boolean isWebServiceDatasource = false;

       public QueryableDataSourceType(String data,String wsdl,String wadl,boolean rest, boolean web){
           this.dataURL=data;
           this.wsdlURL=wsdl;
           this.wadlURL=wadl;
           this.isRestDatasource=rest;
           this.isWebServiceDatasource=web;
       }


    /**
     * @return the dataURL
     */
    public String getDataURL() {
        return dataURL;
    }

    /**
     * @param dataURL the dataURL to set
     */
    public void setDataURL(String dataURL) {
        this.dataURL = dataURL;
    }

    /**
     * @return the wsdlURL
     */
    public String getWsdlURL() {
        return wsdlURL;
    }

    /**
     * @param wsdlURL the wsdlURL to set
     */
    public void setWsdlURL(String wsdlURL) {
        this.wsdlURL = wsdlURL;
    }

    /**
     * @return the wadlURL
     */
    public String getWadlURL() {
        return wadlURL;
    }

    /**
     * @param wadlURL the wadlURL to set
     */
    public void setWadlURL(String wadlURL) {
        this.wadlURL = wadlURL;
    }

    /**
     * @return the isRestDatasource
     */
    public boolean isIsRestDatasource() {
        return isRestDatasource;
    }

    /**
     * @param isRestDatasource the isRestDatasource to set
     */
    public void setIsRestDatasource(boolean isRestDatasource) {
        this.isRestDatasource = isRestDatasource;
    }

    /**
     * @return the isWebServiceDatasource
     */
    public boolean isIsWebServiceDatasource() {
        return isWebServiceDatasource;
    }

    /**
     * @param isWebServiceDatasource the isWebServiceDatasource to set
     */
    public void setIsWebServiceDatasource(boolean isWebServiceDatasource) {
        this.isWebServiceDatasource = isWebServiceDatasource;
    }
}

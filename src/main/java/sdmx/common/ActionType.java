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

import sdmx.xml.NMToken;

/**
 *	<xs:simpleType name="ActionType">
		<xs:annotation>
			<xs:documentation>ActionType provides a list of actions, describing the intention of the data transmission from the sender's side. Each action provided at the data or metadata set level applies to the entire data set for which it is given. Note that the actions indicated in the Message Header are optional, and used to summarize specific actions indicated with this data type for all registry interactions. The "Informational" value is used when the message contains information in response to a query, rather than being used to invoke a maintenance activity.</xs:documentation>
		</xs:annotation>
		<xs:restriction base="xs:NMTOKEN">
			<xs:enumeration value="Append">
				<xs:annotation>
					<xs:documentation>Append - this is an incremental update for an existing data/metadata set or the provision of new data or documentation (attribute values) formerly absent. If any of the supplied data or metadata is already present, it will not replace that data or metadata. This corresponds to the "Update" value found in version 1.0 of the SDMX Technical Standards.</xs:documentation>
				</xs:annotation>
			</xs:enumeration>
			<xs:enumeration value="Replace">
				<xs:annotation>
					<xs:documentation>Replace - data/metadata is to be replaced, and may also include additional data/metadata to be appended. The replacement occurs at the level of the observation - that is, it is not possible to replace an entire series.</xs:documentation>
				</xs:annotation>
			</xs:enumeration>
			<xs:enumeration value="Delete">
				<xs:annotation>
					<xs:documentation>Delete - data/metadata is to be deleted. Deletion occurs at the lowest level object. For instance, if a delete data message contains a series with no observations, then the entire series will be deleted. If the series contains observations, then only those observations specified will be deleted. The same basic concept applies for attributes. If a series or observation in a delete message contains attributes, then only those attributes will be deleted.</xs:documentation>
				</xs:annotation>
			</xs:enumeration>
			<xs:enumeration value="Information">
				<xs:annotation>
					<xs:documentation>Informational - data/metadata is being exchanged for informational purposes only, and not meant to update a system.</xs:documentation>
				</xs:annotation>
			</xs:enumeration>
		</xs:restriction>
	</xs:simpleType>

 * @author James
 */
public class ActionType extends NMToken {
         public static final String APPEND_TEXT = "Append";
         public static final String REPLACE_TEXT = "Replace";
         public static final String DELETE_TEXT = "Delete";
         public static final String INFORMATION_TEXT = "Information";
         public static final ActionType APPEND = new ActionType(APPEND_TEXT);
         public static final ActionType REPLACE = new ActionType(REPLACE_TEXT);
         public static final ActionType DELETE = new ActionType(DELETE_TEXT);
         public static final ActionType INFORMATION = new ActionType(INFORMATION_TEXT);
         
         public ActionType(String s) {
             super(s);
         }
         
         public static ActionType fromString(String s) {
             if( APPEND_TEXT.equals(s) ) return APPEND;
             if( REPLACE_TEXT.equals(s) ) return REPLACE;
             if( DELETE_TEXT.equals(s) ) return DELETE;
             if( INFORMATION_TEXT.equals(s) ) return INFORMATION;
             throw new RuntimeException(s+" is not an ActionType");
         }
         
}

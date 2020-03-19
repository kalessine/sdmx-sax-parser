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
package sdmx.structure.metadatastructure;

import java.util.List;
import sdmx.commonreferences.LocalMetadataTargetReference;

/**
 *	<xs:complexType name="ReportStructureType">
		<xs:annotation>
			<xs:documentation>ReportStructureType describes the structure of a report structure. It comprises a set of metadata attributes that can be defined as a hierarchy, and identifies the potential attachment of these attributes to an object by referencing a target identifier.</xs:documentation>
		</xs:annotation>		
		<xs:complexContent>	
			<xs:extension base="ReportStructureBaseType">
				<xs:sequence>
					<xs:element name="MetadataTarget" type="common:LocalMetadataTargetReferenceType" maxOccurs="unbounded">
						<xs:annotation>
							<xs:documentation>MetadataTarget references a metadata target defined in the metadata structure definition. A report structure can reference multiple metadata targets which allows a report structure to be reused for attaching metadata to different types of targets.</xs:documentation>
						</xs:annotation>
					</xs:element>
				</xs:sequence>
			</xs:extension>
		</xs:complexContent>		
	</xs:complexType>

 * @author James
 */

public class ReportStructureType extends ReportStructureBaseType {
    
    private List<LocalMetadataTargetReference> localMetadataTarget = null;

    /**
     * @return the localMetadataTarget
     */
    public List<LocalMetadataTargetReference> getLocalMetadataTarget() {
        return localMetadataTarget;
    }

    /**
     * @param localMetadataTarget the localMetadataTarget to set
     */
    public void setLocalMetadataTarget(List<LocalMetadataTargetReference> localMetadataTarget) {
        this.localMetadataTarget = localMetadataTarget;
    }
    
}

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
package sdmx.structure;

import sdmx.structure.organisation.DataConsumerSchemeType;
import sdmx.structure.organisation.AgencySchemeType;
import sdmx.structure.organisation.DataProviderSchemeType;
import sdmx.structure.organisation.OrganisationUnitSchemeType;

/**
 *
 * @author James
 */

public class OrganisationSchemesType {
     private AgencySchemeType agencyScheme = null;
     private DataConsumerSchemeType dataConsumerScheme = null;
     private DataProviderSchemeType dataProviderScheme = null;
     private OrganisationUnitSchemeType organisationUnitScheme = null;

    /**
     * @return the agencyScheme
     */
    public AgencySchemeType getAgencyScheme() {
        return agencyScheme;
    }

    /**
     * @param agencyScheme the agencyScheme to set
     */
    public void setAgencyScheme(AgencySchemeType agencyScheme) {
        this.agencyScheme = agencyScheme;
    }

    /**
     * @return the dataConsumerScheme
     */
    public DataConsumerSchemeType getDataConsumerScheme() {
        return dataConsumerScheme;
    }

    /**
     * @param dataConsumerScheme the dataConsumerScheme to set
     */
    public void setDataConsumerScheme(DataConsumerSchemeType dataConsumerScheme) {
        this.dataConsumerScheme = dataConsumerScheme;
    }

    /**
     * @return the dataProviderScheme
     */
    public DataProviderSchemeType getDataProviderScheme() {
        return dataProviderScheme;
    }

    /**
     * @param dataProviderScheme the dataProviderScheme to set
     */
    public void setDataProviderScheme(DataProviderSchemeType dataProviderScheme) {
        this.dataProviderScheme = dataProviderScheme;
    }

    /**
     * @return the organisationUnitScheme
     */
    public OrganisationUnitSchemeType getOrganisationUnitScheme() {
        return organisationUnitScheme;
    }

    /**
     * @param organisationUnitScheme the organisationUnitScheme to set
     */
    public void setOrganisationUnitScheme(OrganisationUnitSchemeType organisationUnitScheme) {
        this.organisationUnitScheme = organisationUnitScheme;
    }
     
}

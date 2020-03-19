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
package sdmx.query.datastructure;

import java.util.List;
import sdmx.commonreferences.ConceptReference;
import sdmx.query.base.ComponentWhereType;

/**
 *
 * @author James
 */

public class DimensionWhereType extends ComponentWhereType {
    private List<ConceptReference> roles = null;

    /**
     * @return the roles
     */
    public List<ConceptReference> getRoles() {
        return roles;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(List<ConceptReference> roles) {
        this.roles = roles;
    }
}

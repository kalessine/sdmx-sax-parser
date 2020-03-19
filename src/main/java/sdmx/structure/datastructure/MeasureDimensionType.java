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
package sdmx.structure.datastructure;

import sdmx.Registry;
import sdmx.commonreferences.ConceptReference;
import sdmx.commonreferences.IDType;
import sdmx.commonreferences.ItemSchemeReference;
import sdmx.commonreferences.ItemSchemeReferenceBase;
import sdmx.commonreferences.Version;
import sdmx.structure.concept.ConceptType;

/**
 *
 * @author James
 */

public class MeasureDimensionType extends BaseDimensionType {
      public ConceptType findConcept(Registry reg, IDType id) {
          ItemSchemeReferenceBase ref1 = this.getLocalRepresentation().getEnumeration();
          ConceptReference ref2 = ConceptReference.create(ref1.getAgencyId(), ref1.getMaintainableParentId(), ref1.getVersion(), id);
          return reg.find(ref2);
      }
      public ConceptType findConcept(Registry reg, String id) {
          ItemSchemeReferenceBase ref1 = this.getLocalRepresentation().getEnumeration();
          ConceptReference ref2 = ConceptReference.create(ref1.getAgencyId(), ref1.getMaintainableParentId(), ref1.getVersion(), new IDType(id));
          return reg.find(ref2);
      }
}

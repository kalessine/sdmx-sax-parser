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
package sdmx.structure.base;

import sdmx.Registry;
import sdmx.structure.concept.ConceptType;

/**
 *
 * @author James
 */
public class ComponentUtil {
    public static RepresentationType getRepresentation(Registry reg,Component c){
        RepresentationType rep = c.getLocalRepresentation();
        if( rep==null ) {
            ConceptType concept = reg.find(c.getConceptIdentity());
            return concept.getCoreRepresentation();
        }
        return c.getLocalRepresentation();
    }
    public static RepresentationType getLocalRepresentation(Component c) {
        if( c == null ) return null;
        return c.getLocalRepresentation();
    }
}

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

import sdmx.structure.constraint.AttachmentConstraintType;
import sdmx.structure.constraint.ContentConstraintType;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author James
 */

public class ConstraintsType {
    private List<AttachmentConstraintType> attachmentConstraints = new ArrayList<AttachmentConstraintType>();
    private List<ContentConstraintType> contentConstraints = new ArrayList<ContentConstraintType>();

    /**
     * @return the attachmentConstraints
     */
    public List<AttachmentConstraintType> getAttachmentConstraints() {
        return attachmentConstraints;
    }

    /**
     * @param attachmentConstraints the attachmentConstraints to set
     */
    public void setAttachmentConstraints(List<AttachmentConstraintType> attachmentConstraints) {
        this.attachmentConstraints = attachmentConstraints;
    }

    /**
     * @return the contentConstraints
     */
    public List<ContentConstraintType> getContentConstraints() {
        return contentConstraints;
    }

    /**
     * @param contentConstraints the contentConstraints to set
     */
    public void setContentConstraints(List<ContentConstraintType> contentConstraints) {
        this.contentConstraints = contentConstraints;
    }
}

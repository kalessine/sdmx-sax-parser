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
package sdmx.tasks;

import java.util.List;
import java.util.concurrent.RecursiveAction;
import sdmx.cube.Cube;
import sdmx.data.ColumnMapper;
import sdmx.data.DataSet;
import sdmx.data.flat.FlatObs;

/**
 *
 * @author James
 */
public class CreateCubeTask extends RecursiveAction {

    private int threshhold = 1000;
    private ColumnMapper mapper = null;
    private DataSet set = null;
    private Cube cube = null;
    private int start = 0;
    private int end = 0;

    public CreateCubeTask(ColumnMapper mapper,DataSet set,int start,int end, Cube cube) {
        this.mapper=mapper;
        this.set = set;
        this.cube = cube;
        this.start=start;
        this.end=end;
    }

    @Override
    protected void compute() {
        if ((end-start) < threshhold) {
            for(int i=start;i<end;i++) {
                FlatObs obs = set.getFlatObs(i);
                cube.putObservation(null,this.mapper , obs);
            }
            return;
        }

        int split = (end-start) / 2;
        invokeAll(new CreateCubeTask(this.mapper,set,start,start+split,cube),
                new CreateCubeTask(this.mapper,set,start+split,end,cube));
    }

}

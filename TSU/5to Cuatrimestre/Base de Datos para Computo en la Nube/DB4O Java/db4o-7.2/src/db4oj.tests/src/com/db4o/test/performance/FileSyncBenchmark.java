/* Copyright (C) 2004 - 2008  db4objects Inc.  http://www.db4o.com

This file is part of the db4o open source object database.

db4o is free software; you can redistribute it and/or modify it under
the terms of version 2 of the GNU General Public License as published
by the Free Software Foundation and as clarified by db4objects' GPL 
interpretation policy, available at
http://www.db4o.com/about/company/legalpolicies/gplinterpretation/
Alternatively you can write to db4objects, Inc., 1900 S Norfolk Street,
Suite 350, San Mateo, CA 94403, USA.

db4o is distributed in the hope that it will be useful, but WITHOUT ANY
WARRANTY; without even the implied warranty of MERCHANTABILITY or
FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
for more details.

You should have received a copy of the GNU General Public License along
with this program; if not, write to the Free Software Foundation, Inc.,
59 Temple Place - Suite 330, Boston, MA  02111-1307, USA. */
package com.db4o.test.performance;

import java.io.*;


public class FileSyncBenchmark {

    private static String FILE = "FileSyncBenchmark.file";
    
    private static final int COUNT = 1000;
    
    private static final int SIZE = 1000;

    public static void main(String[] args) throws IOException {
        new File(FILE).delete();
        RandomAccessFile raf = new RandomAccessFile(FILE, "rw");
        for (int i = 0; i < SIZE; i++) {
            raf.write(1);
        }
        raf.close();
        raf = new RandomAccessFile(FILE, "rw");
        long start = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
        	raf.write(1);
            raf.getFD().sync();
        }
        long stop = System.currentTimeMillis();
        long duration = stop - start;
        raf.close();
        new File(FILE).delete();
        System.out.println("Time for " + COUNT + " syncs of a " + SIZE + " bytes sized file:\n" + duration + "ms");
    }
}

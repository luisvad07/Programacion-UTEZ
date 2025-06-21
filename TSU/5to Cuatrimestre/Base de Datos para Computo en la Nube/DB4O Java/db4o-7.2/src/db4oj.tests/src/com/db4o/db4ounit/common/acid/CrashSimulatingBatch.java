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
package com.db4o.db4ounit.common.acid;

import java.io.*;

import com.db4o.foundation.*;
import com.db4o.foundation.io.*;


public class CrashSimulatingBatch {
    
    Collection4 writes = new Collection4();
    Collection4 currentWrite = new Collection4();
    
    public void add(byte[] bytes, long offset, int length){
        currentWrite.add(new CrashSimulatingWrite(bytes, offset, length));
    }

    public void sync() {
        writes.add(currentWrite);
        currentWrite = new Collection4();
    }

    public int numSyncs() {
    	return writes.size();
    }
    
    public int writeVersions(String file) throws IOException {
        
        int count = 0;
        int rcount = 0;
        
        String lastFileName = file + "0";
        
        String rightFileName = file + "R" ;
        
        File4.copy(lastFileName, rightFileName);
                
        Iterator4 syncIter = writes.iterator();
        while(syncIter.moveNext()){
            
            rcount++;
            
            Collection4 writesBetweenSync = (Collection4)syncIter.current();
            
            if(CrashSimulatingTestCase.LOG){
                System.out.println("Writing file " + rightFileName + rcount );
            }
            
            RandomAccessFile rightRaf = new RandomAccessFile(rightFileName, "rw");
            Iterator4 singleForwardIter = writesBetweenSync.iterator();
            while(singleForwardIter.moveNext()){
                CrashSimulatingWrite csw = (CrashSimulatingWrite)singleForwardIter.current();
                csw.write(rightRaf);
                
                if(CrashSimulatingTestCase.LOG){
                    System.out.println(csw);
                }
                
            }
            rightRaf.close();
                        
            Iterator4 singleBackwardIter = writesBetweenSync.iterator();
            while(singleBackwardIter.moveNext()){
                count ++;
                CrashSimulatingWrite csw = (CrashSimulatingWrite)singleBackwardIter.current();
                String currentFileName = file + "W" + count;
                File4.copy(lastFileName, currentFileName);
                
                RandomAccessFile raf = new RandomAccessFile(currentFileName, "rw");
                csw.write(raf);
                raf.close();
                lastFileName = currentFileName;
            }
            File4.copy(rightFileName, rightFileName+rcount);
            lastFileName = rightFileName;
        }
        return count;
    }

}

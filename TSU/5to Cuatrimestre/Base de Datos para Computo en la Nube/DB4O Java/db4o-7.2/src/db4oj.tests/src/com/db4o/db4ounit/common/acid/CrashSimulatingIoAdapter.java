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

import com.db4o.ext.*;
import com.db4o.io.*;


public class CrashSimulatingIoAdapter extends VanillaIoAdapter{
    
    CrashSimulatingBatch batch;
    
    long curPos;
    
    public CrashSimulatingIoAdapter(IoAdapter delegateAdapter) {
        super(delegateAdapter);
        batch = new CrashSimulatingBatch();
    }
    
    private CrashSimulatingIoAdapter(IoAdapter delegateAdapter, String path, boolean lockFile, long initialLength, boolean readOnly, CrashSimulatingBatch batch) throws Db4oIOException {
        super(delegateAdapter.open(path, lockFile, initialLength, readOnly));
        this.batch = batch;
    }
    
    public IoAdapter open(String path, boolean lockFile, long initialLength, boolean readOnly) throws Db4oIOException {
        return new CrashSimulatingIoAdapter(_delegate, path, lockFile, initialLength, readOnly, batch);
    }

    public void seek(long pos) throws Db4oIOException {
        curPos=pos;
        super.seek(pos);
    }
    
    public void write(byte[] buffer, int length) throws Db4oIOException {
        super.write(buffer, length);
        byte[] copy=new byte[buffer.length];
        System.arraycopy(buffer, 0, copy, 0, buffer.length);
        batch.add(copy, curPos, length);
    }
    
    public void sync() throws Db4oIOException {
        super.sync();
        batch.sync();
    }
}

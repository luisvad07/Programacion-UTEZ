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
package com.db4o.db4ounit.util;

import java.io.*;

import com.db4o.ext.*;
import com.db4o.internal.*;
import com.db4o.internal.activation.*;
import com.db4o.internal.marshall.*;


public class VersionServices {
    
    public static final byte HEADER_30_40 = 123;
    
    public static final byte HEADER_46_57 = 4;
    
    public static final byte HEADER_60 = 100;
    

    public static byte fileHeaderVersion(String testFile) throws IOException{
        RandomAccessFile raf = new RandomAccessFile(testFile, "r");
        byte[] bytes = new byte[1];
        raf.read(bytes);  // readByte() doesn't convert to .NET.
        byte db4oHeaderVersion = bytes[0]; 
        raf.close();
        return db4oHeaderVersion;
    }
    
    public static int slotHandlerVersion(ExtObjectContainer objectContainer, Object obj){
        int id = (int) objectContainer.getID(obj);
        ObjectInfo objectInfo = objectContainer.getObjectInfo(obj);
        ObjectContainerBase container = (ObjectContainerBase) objectContainer;
        Transaction trans = container.transaction();
        ByteArrayBuffer buffer = container.readReaderByID(trans, id);
        UnmarshallingContext context = new UnmarshallingContext(trans, (ObjectReference)objectInfo, Const4.TRANSIENT, false);
        context.buffer(buffer);
        context.persistentObject(obj);
        context.activationDepth(new LegacyActivationDepth(0));
        context.read();
        return context.handlerVersion();
    }


}

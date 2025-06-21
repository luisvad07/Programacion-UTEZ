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
package com.db4o.internal;

import java.io.*;

import com.db4o.ext.*;
import com.db4o.internal.mapping.*;
import com.db4o.internal.marshall.*;
import com.db4o.internal.slots.*;
import com.db4o.marshall.*;


/**
 * @exclude
 */
public class UntypedFieldHandler0 extends UntypedFieldHandler2 {

    public UntypedFieldHandler0(ObjectContainerBase container) {
        super(container);
    }
    
    public Object read(ReadContext context) {
        return context.readObject();
    }
    
    public ObjectID readObjectID(InternalReadContext context){
        int id = context.readInt();
        return id == 0 ? ObjectID.IS_NULL : new ObjectID(id);
    }
    
    public void defragment(DefragmentContext context) {
        int sourceId = context.sourceBuffer().readInt();
        if(sourceId == 0) {
            context.targetBuffer().writeInt(0);
            return;
        }
        int targetId = 0;
        try {
        	targetId = context.mappedID(sourceId);
        }
        catch(MappingNotFoundException exc) {
        	targetId = copyDependentSlot(context, sourceId);
        }
        context.targetBuffer().writeInt(targetId);
    }

	private int copyDependentSlot(DefragmentContext context, int sourceId) {
		try {
			ByteArrayBuffer sourceBuffer = context.sourceBufferById(sourceId);
			Slot targetPointerSlot = context.allocateMappedTargetSlot(sourceId, Const4.POINTER_LENGTH);
			Slot targetPayloadSlot = context.allocateTargetSlot(sourceBuffer.length());
			ByteArrayBuffer pointerBuffer = new ByteArrayBuffer(Const4.POINTER_LENGTH);
			pointerBuffer.writeInt(targetPayloadSlot.address());
			pointerBuffer.writeInt(targetPayloadSlot.length());
			context.targetWriteBytes(targetPointerSlot.address(), pointerBuffer);

			DefragmentContextImpl payloadContext = new DefragmentContextImpl(sourceBuffer, (DefragmentContextImpl) context);

			int clazzId = payloadContext.copyIDReturnOriginalID();
			TypeHandler4 payloadHandler = payloadContext.typeHandlerForId(clazzId);
			TypeHandler4 versionedPayloadHandler = payloadContext.correctHandlerVersion(payloadHandler);
			versionedPayloadHandler.defragment(payloadContext);
			
			payloadContext.writeToTarget(targetPayloadSlot.address());
			return targetPointerSlot.address();
		}
		catch (IOException ioexc) {
			throw new Db4oIOException(ioexc);
		}
	}

}

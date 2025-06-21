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
package com.db4o.db4ounit.jre5.collections.typehandler;

import java.util.*;

import com.db4o.ext.*;
import com.db4o.foundation.*;
import com.db4o.internal.*;
import com.db4o.internal.activation.*;
import com.db4o.internal.handlers.*;
import com.db4o.internal.marshall.*;
import com.db4o.internal.query.processor.*;
import com.db4o.marshall.*;
import com.db4o.reflect.*;
import com.db4o.reflect.generic.*;
import com.db4o.typehandlers.*;

public class ArrayListTypeHandler implements TypeHandler4 , FirstClassHandler, CanHoldAnythingHandler, VariableLengthTypeHandler, EmbeddedTypeHandler {

    public PreparedComparison prepareComparison(Context context, Object obj) {
        // TODO Auto-generated method stub
        return null;
    }

    public void write(WriteContext context, Object obj) {
        List list = (List)obj;
        writeClass(context, list);
        writeElementCount(context, list);
        writeElements(context, list);
        return;
    }
    
    @SuppressWarnings("unchecked")
	public Object read(ReadContext context) {
        ClassMetadata classMetadata = readClass(context);            
        List existing = null; //(List) ((UnmarshallingContext) context).persistentObject();
        List list = 
            existing != null ? 
                existing : 
                    (List) classMetadata.instantiateFromReflector(container(context));
        int elementCount = context.readInt();
        TypeHandler4 elementHandler = elementTypeHandler(context, list);
        for (int i = 0; i < elementCount; i++) {
            list.add(context.readObject(elementHandler));
        }
        return list;
    }
    
	private void writeElementCount(WriteContext context, List list) {
		context.writeInt(list.size());
	}

	private void writeElements(WriteContext context, List list) {
		TypeHandler4 elementHandler = elementTypeHandler(context, list);
        final Iterator elements = list.iterator();
        while (elements.hasNext()) {
            context.writeObject(elementHandler, elements.next());
        }
	}

	private void writeClass(WriteContext context, List list) {
		int classID = classID(context, list);
        context.writeInt(classID);
	}
    
    private int classID(WriteContext context, Object obj) {
        ObjectContainerBase container = container(context);
        GenericReflector reflector = container.reflector();
        ReflectClass claxx = reflector.forObject(obj);
        ClassMetadata classMetadata = container.produceClassMetadata(claxx);
        return classMetadata.getID();
    }

    private ObjectContainerBase container(Context context) {
        return ((InternalObjectContainer)context.objectContainer()).container();
    }
    
    private TypeHandler4 elementTypeHandler(Context context, List list){
        
        // TODO: If all elements in the list are of one type,
        //       it is possible to use a more specific handler
        
        return container(context).handlers().untypedObjectHandler();
    }        

	private ClassMetadata readClass(ReadContext context) {
		int classID = context.readInt();
        ClassMetadata classMetadata = container(context).classMetadataForId(classID);
		return classMetadata;
	}

    public void delete(DeleteContext context) throws Db4oIOException {
        // TODO Auto-generated method stub

    }

    public void defragment(DefragmentContext context) {
        // TODO Auto-generated method stub

    }

    public final void cascadeActivation(Transaction trans, Object onObject, ActivationDepth depth) {
		ObjectContainerBase container = trans.container();
		List list = (List) onObject;
		Iterator all = list.iterator();
		while (all.hasNext()) {
			final Object current = all.next();
			ActivationDepth elementDepth = descend(container, depth, current);
			if (elementDepth.requiresActivation()) {
				if (depth.mode().isDeactivate()) {
					container.stillToDeactivate(trans, current, elementDepth, false);
				}
				else {
					container.stillToActivate(trans, current, elementDepth);
				}
			}
		}
	}

	public TypeHandler4 readArrayHandler(Transaction a_trans, MarshallerFamily mf, ByteArrayBuffer[] a_bytes) {
		return this;
	}

	public void readCandidates(int handlerVersion, ByteArrayBuffer buffer, final QCandidates candidates) throws Db4oIOException {
        final Transaction trans = candidates.i_trans;
        buffer.readInt(); // skip class id
        int elementCount = buffer.readInt();
        TypeHandler4 elementHandler = trans.container().handlers().untypedObjectHandler();
        readSubCandidates(handlerVersion, buffer, candidates, elementCount, elementHandler);
	}

    private void readSubCandidates(int handlerVersion, ByteArrayBuffer reader, QCandidates candidates, int count, TypeHandler4 elementHandler) {
        QueryingReadContext context = new QueryingReadContext(candidates.transaction(), handlerVersion, reader);
        for (int i = 0; i < count; i++) {
            QCandidate qc = candidates.readSubCandidate(context, elementHandler);
            if(qc != null){
                candidates.addByIdentity(qc);
            }
        }
    }

    private ActivationDepth descend(ObjectContainerBase container, ActivationDepth depth, Object obj){
        if(obj == null){
            return new NonDescendingActivationDepth(depth.mode());
        }
        ClassMetadata cm = container.classMetadataForObject(obj);
        if(cm.isPrimitive()){
            return new NonDescendingActivationDepth(depth.mode());
        }
        return depth.descend(cm);
    }

}